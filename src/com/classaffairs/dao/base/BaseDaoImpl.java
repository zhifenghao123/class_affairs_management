package com.classaffairs.dao.base;

import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.framework.sdp.orm.query.PageImpl;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
/**
 * @author Haozhifeng
 *
 */
//@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Autowired
	public SqlSessionTemplate sqlSession;//Mybatis用到
	@Autowired
	private MongoTemplate mongoTemplate; //MongoDB用到
	private SessionFactory sessionFactory; //Hibernate用到
	
	private Class<T> entityClass;
	private String collectionName; //MongoDB用到
	
	public BaseDaoImpl(){
		Class clazz = getClass();
		Type type = clazz.getGenericSuperclass();
		if(type instanceof ParameterizedType){
			ParameterizedType p = (ParameterizedType) type;
			Class c = (Class) p.getActualTypeArguments()[0];
			this.entityClass = c;
		}else{
			this.entityClass = (Class<T>) type;
		}
		
		Annotation[] entityClassAnnotations = this.entityClass.getAnnotations();
		if(null != entityClassAnnotations && entityClassAnnotations.length >= 0){
			//Class annotationType = entityClassAnnotations[0].annotationType();
			for(Annotation entityClassAnnotation:entityClassAnnotations){
				Class classAnnotation = entityClassAnnotation.annotationType();
				if(classAnnotation.equals(org.springframework.data.mongodb.core.mapping.Document.class)){
					Method[] methods = classAnnotation.getDeclaredMethods();
					for(Method method:methods){
						if(method.getName().equals("collection")){
							if(!method.isAccessible()){  
								method.setAccessible(true);  
			                }  
							try {
								//System.out.println(method.invoke(entityClassAnnotation, null));
								this.collectionName = (String) method.invoke(entityClassAnnotation, null);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							} 
						}
					}
				}
			}
		}else{
			this.collectionName = "null";
		}
	 }
	
	public void setSessionFactory(SessionFactory sessionFactory){
	   this.sessionFactory = sessionFactory;
	}
	  
	public Session getSession(){
	   return this.sessionFactory.getCurrentSession();
	}
	  
	public void setSqlSession(SqlSessionTemplate sqlSession){
	   this.sqlSession = sqlSession;
	}
	  
	public void setMongoTemplate(MongoTemplate mongoTemplate){
	   this.mongoTemplate = mongoTemplate;
	}
	
	protected void mPrepareObjectForSaveOrUpdate(Object o) {}
	  
	private String mGetFindByPrimaryKeyQuery(String cls){
	   return cls + ".findById";
	}
	  
	private String mGetInsertQuery(String cls){
	   return cls + ".insert";
	}
	
	private String mGetInsertByBatchQuery(String cls){
		   return cls + ".insertByBatch";
	}
	  
	private String mGetUpdateQuery(String cls){
	   return cls + ".update";
	}
	
	private String mGetUpdateByBatchQuery(String cls){
		   return cls + ".updateByBatch";
	}
	
	private String mGetDeleteQuery(String cls){
	   return cls + ".delete";
	}
	  
	private String mGetCountQuery(String cls){
	   return cls + ".count";
	}
	  
	private String mGetFindAllQuery(String cls){
	   return cls + ".findAll";
	}
	
	@Override
	public int mSave(T paramObject) {
		mPrepareObjectForSaveOrUpdate(paramObject);
		System.out.println(entityClass.getName());
	    int rows = this.sqlSession.insert(mGetInsertQuery(entityClass.getName()), paramObject);
	    return rows;
	}

	@Override
	public int mSaveByBatch(List<T> paramObjectList) {
		String dynamicSqlId = mGetInsertByBatchQuery(entityClass.getName());
		int result = this.sqlSession.insert(dynamicSqlId, paramObjectList);
		return result;
	}

	@Override
	public int mUpdate(T paramObject) {
		mPrepareObjectForSaveOrUpdate(paramObject);
	    int rows = this.sqlSession.update(mGetUpdateQuery(entityClass.getName()), paramObject);
	    return rows;
	}
	@Override
	public int mUpdateByBatch(List<T> paramObjectList) {
		String dynamicSqlId = mGetUpdateByBatchQuery(entityClass.getName());
		mPrepareObjectForSaveOrUpdate(paramObjectList);
	    int rows = this.sqlSession.update(dynamicSqlId, paramObjectList);
	    return rows;
	}

	@Override
	public int mUpdateField(String dynamicSqlStatementId, Object paramObject) {
		int rows = this.sqlSession.update(entityClass.getName() + "." + dynamicSqlStatementId, paramObject);
	    return rows;
	}

	@Override
	public void mDeleteById(Object paramObject) {
		this.sqlSession.delete(mGetDeleteQuery(entityClass.getName()), paramObject);
	}

	@Override
	public int mDeleteByField(String dynamicSqlStatementId, Object paramObject) {
		return this.sqlSession.delete(entityClass.getName() + "." + dynamicSqlStatementId, paramObject);
	}

	@Override
	public Object mFindById(Object paramObject) {
		Object object = this.sqlSession.selectOne(mGetFindByPrimaryKeyQuery(entityClass.getName()), paramObject);
	    return object;
	}

	@Override
	public List<?> mFindAll() {
		return this.sqlSession.selectList(entityClass.getName() + ".findAll");
	}

	@Override
	public List<?> mFind(String dynamicSqlStatementId, Object paramObject) {
		return this.sqlSession.selectList(entityClass.getName() + "." + dynamicSqlStatementId, paramObject);
	}

	@Override
	public List<?> mSelectList(String dynamicSqlStatementId,
			Object paramObject, int offset, int limit) {
		return this.sqlSession.selectList(entityClass.getName() + "." + dynamicSqlStatementId, paramObject, new RowBounds(offset, limit));
	}

	@Override
	public Page<?> mPageQuery(String dynamicSqlStatementId, Object paramObject,
			int pagerOffset, int pageSize) {
		Page page = new PageImpl(pagerOffset, pageSize);
	    List totalList = mFind( dynamicSqlStatementId, paramObject);
	    Number totalCount = Integer.valueOf(totalList.size());
	    page.setTotalCount(totalCount.intValue());
	    List list = this.sqlSession.selectList(entityClass.getName() + "." + dynamicSqlStatementId, paramObject, new RowBounds(pagerOffset, pageSize));
	    page.setResult(list);
	    return page;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T mSelectOne(String dynamicSqlStatementId, Object paramObject) {
		Object obj = this.sqlSession.selectOne(entityClass.getName() + "." + dynamicSqlStatementId, paramObject);
	    return (T) obj;
	}

	
	
	@Override
	public boolean mgSaveDocument(Object paramObject)
			throws IllegalAccessException, InvocationTargetException,
			IntrospectionException {
		boolean re = false;
	    try{
	      this.mongoTemplate.save(paramObject, collectionName);
	      re = true;
	    }catch (MongoException e){
	      System.out.println("mongodb保存对象saveByDocument异常");
	      e.printStackTrace();
	    }
	    return re;
	}

	@Override
	public boolean mgUpdateDocument(Query query, Update updateSetValue) {
		boolean result = false;
	    WriteResult writeResult = this.mongoTemplate.updateFirst(query, updateSetValue, collectionName);
	    result = writeResult.isUpdateOfExisting();
	    return result;
	}

	@Override
	public boolean mgDeleteDocument(Query query) {
		boolean result = false;
	    this.mongoTemplate.remove(query, collectionName);
	    Object object = mgSelectOneDocument(query);
	    if (object == null) {
	      result = true;
	    }
	    return result;
	}

	@Override
	public List<?> mgSelectListDocument(Query query) {
		 List<?> result = null;
		 //System.out.print(entityClass);
		 result = this.mongoTemplate.find(query, entityClass, collectionName);
		 return result;
	}

	@Override
	public List<?> mgFindAllDocument() {
		List<?> result = null;
	    result = this.mongoTemplate.findAll(entityClass, collectionName);
	    return result;
	}

	@Override
	public Page<?> mgPageQueryDocument(Query query, int pageOffset,
			int pageSize) {
		 Page page = new PageImpl(pageOffset, pageSize);
		 Long totalCount = Long.valueOf(this.mongoTemplate.count(query, collectionName));
		 page.setTotalCount(totalCount.intValue());
		 query.skip(pageOffset);
		 query.limit(pageSize);
		 List<?> datas = mgSelectListDocument(query);
		 page.setResult(datas);
		 return page;
	}

	@Override
	public Object mgSelectOneDocument(Query query) {
		Object Object = null;
	    Object = this.mongoTemplate.findOne(query, entityClass);
	    return Object;
	}

}

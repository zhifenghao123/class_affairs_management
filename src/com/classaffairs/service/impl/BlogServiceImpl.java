/**
 * 
 */
package com.classaffairs.service.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.classaffairs.common.Resource;
import com.classaffairs.dao.BlogDao;
import com.classaffairs.entity.Blog;
import com.classaffairs.framework.core.utils.ConvertObject;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.BlogService;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;

/**
 * @author Haozhifeng
 *
 */
@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogDao itsBlogDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogService#addBlog(com.classaffairs.entity.Blog)
	 */
	@Override
	public boolean addBlog(Blog blog) {
		boolean result = false;
		try {
			result = itsBlogDao.mgSaveDocument(blog);
		} catch (DataAccessException dae) {
			Log.log.error("新增博客操作数据库异常,学生：" + blog.getOwnerId(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增博客异常,学生：" + blog.getOwnerId(), e);
			e.printStackTrace();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogService#updateBlog(com.classaffairs.entity.Blog)
	 */
	@Override
	public boolean updateBlog(Blog blog) {
		boolean result = false;
		try{
			Update update = new Update();
			update=ConvertObject.beanToUpdate(blog);
			Query query = Query.query(Criteria.where("_id").is(blog.getBlogId()));
			result = itsBlogDao.mgUpdateDocument(query,update);
		}catch(Exception e){
			Log.log.error("更新活动动态信息异常",e);
			e.printStackTrace();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogService#deleteBlog(java.lang.Long)
	 */
	@Override
	public boolean deleteBlog(Blog blog) {
		boolean result = false;
		Query q = Query.query(Criteria.where("_id").is(blog.getBlogId()));
		try{
			result = itsBlogDao.mgDeleteDocument(q);
			Resource resource = new Resource(blog.getContentPath());
			resource.delete();
		}catch(Exception e){
			Log.log.error("删除活动信息异常",e);
			e.printStackTrace();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogService#findBlogByBlogId(java.lang.Long)
	 */
	@Override
	public Blog findBlogByBlogId(BigInteger blogId) {
		Blog blog = null;
		Query q = Query.query(Criteria.where("blogId").is(blogId));
		
		try{
			blog = (Blog) itsBlogDao.mgSelectOneDocument(q);
		}catch(Exception e){
			Log.log.error("通过id获取活动动态信息异常",e);
			e.printStackTrace();
		}
		return blog;
	}

	@Override
	public Page<Blog> getBlogsByPageQuery(String blogTile, Long ownerId,String keyWordSearch,int pageOffset,int pageSize) {
		Page<Blog> cursor = null; 
		BasicDBObject queryObj = new BasicDBObject();   //构建查询条件 
				
		Query q = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*if(blogTile != null){
			queryObj.put("sportsId",ownerId);
		}
		//月份查询(多选)
		if(month!=null){
			BasicDBList valuesMonth = new BasicDBList();
			for(int i=0;i<month.length;i++){
				Calendar a=Calendar.getInstance();
				int year = a.get(Calendar.YEAR);
				int startYear=year,endYear=year;
				int startMonth=month[i],endMonth=month[i]+1;  
				int startDay=1,endDay=1; 
				if(month[i]==1){
					startYear = year-1;
				}else if(month[i]==12){
					endYear = year+1;
				}
					valuesMonth.add(new BasicDBObject("startDate",BasicDBObjectBuilder.start("$gte", new Date(startYear - 1900, startMonth - 1, startDay)).add("$lt", new Date(endYear - 1900, endMonth - 1, endDay)).get()));
				}
				queryObj.put("$or", valuesMonth);	
		}*/
				//运动类型
				if(ownerId != null){
					queryObj.put("ownerId",ownerId);
				}
				
				//关键字搜索
				if(keyWordSearch!=null&&!keyWordSearch.equals("")&&!keyWordSearch.equals("null")){
					Pattern pattern = Pattern.compile("^.*"+keyWordSearch+".*$", Pattern.CASE_INSENSITIVE);
					queryObj.put("title", pattern);
				}

				
				q = new BasicQuery(queryObj);
				int recordOffset = (pageOffset - 1) * pageSize;
				
				//cursor = MongodbConnectionUtil.getDBCollection(collectionName).find(queryObj).sort(new BasicDBObject("top", -1).append("commentsAve",-1)).skip(start).limit(pageSizes); //查询获取数据（根据评分排序）
				//cursor = (Page<Blog>) itsBlogDao.mgPageQueryDocument(q.with(new Sort(new Order(Direction.DESC, "top")).and(new Sort(new Order(Direction.DESC,"commentsAve")))), recordOffset, pageSize);
				cursor = (Page<Blog>) itsBlogDao.mgPageQueryDocument(q.with(new Sort(new Order(Direction.DESC, "createTime"))), recordOffset, pageSize);
							
				//Page<Blog> pageBlogs = (Page<Blog>) cursor;
				return cursor;
	}

}

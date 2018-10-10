package com.classaffairs.dao.base;

import java.util.List;

import com.classaffairs.framework.sdp.orm.query.Page;

public interface MybatisRepositoryInterface<T> {
	
	  public abstract int mSave(T paramObject);
	  
	  public abstract int mSaveByBatch(List<T> paramObjectList);
	  
	  public abstract int mUpdate(T paramObject);
	  
	  public abstract int mUpdateByBatch(List<T> paramObjectList);
	  
	  public abstract int mUpdateField(String dynamicSqlStatementId, Object paramObject);
	  
	  public abstract void mDeleteById(Object paramObject);
	  
	  public abstract int mDeleteByField(String dynamicSqlStatementId, Object paramObject);
	  
	  public abstract Object mFindById(Object paramObject);
	  
	  public abstract List<?> mFindAll();
	  
	  public abstract List<?> mFind(String dynamicSqlStatementId, Object paramObject);
	  
	  public abstract List<?> mSelectList(String dynamicSqlStatementId, Object paramObject, int offset, int limit);
	  
	  public abstract Page<?> mPageQuery(String dynamicSqlStatementId, Object paramObject, int pagerOffset, int pageSize);
	  
	  public abstract T mSelectOne(String dynamicSqlStatementId, Object paramObject);

}

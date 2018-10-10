package com.classaffairs.dao.base;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.classaffairs.framework.sdp.orm.query.Page;

public interface MongoInterface<T> {
	public abstract boolean mgSaveDocument(Object paramObject)
		    throws IllegalAccessException, InvocationTargetException, IntrospectionException;
	
	public abstract boolean mgUpdateDocument(Query query, Update updateSetValue);
	  
	public abstract boolean mgDeleteDocument(Query query);
		  
	public abstract List<?> mgSelectListDocument(Query query);
		  
	public abstract List<?> mgFindAllDocument();
		  
	public abstract Page<?> mgPageQueryDocument(Query query, int pageOffset, int pageSize);
		  
	public abstract Object mgSelectOneDocument(Query query);
		  
}

package com.classaffairs.framework.sdp.orm.support;

public class DynamicSqlStatementUtil {
	
	public static String getSqlStatementByClassAndSqlId(Class<?> cls,String sqlStatementId){
		return cls.getName() + "." + sqlStatementId;
	}
}

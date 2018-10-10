package com.classaffairs.dao.base;

import java.util.List;
import java.util.Map;

import com.classaffairs.framework.sdp.exception.OperationException;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.framework.sdp.orm.query.Pageable;
import com.classaffairs.framework.sdp.orm.query.Sort;

public interface HibernateRepositoryInterface {
	public abstract <T> long hCount(Class<T> paramClass)
		throws OperationException;
		  
	public abstract <T> boolean hDelete(Class<T> paramClass, String paramString)
		throws OperationException;
		  
	public abstract <T> boolean hDelete(Iterable<? extends T> paramIterable)
		throws OperationException;
		  
	public abstract <T> boolean hDelete(T paramT)
		throws OperationException;
		  
	public abstract <T> boolean hExists(Class<T> paramClass, String paramString)
		throws OperationException;
		  
	public abstract <T> List<T> hFindAll(Class<T> paramClass)
		throws OperationException;
		  
	public abstract <T> Page<T> hFindAll(Class<T> paramClass, Pageable paramPageable)
		throws OperationException;
		  
	public abstract <T> List<T> hFindAll(Class<T> paramClass, Sort paramSort)
		throws OperationException;
		  
	public abstract <T> List<T> hFindByProperty(Class<T> paramClass, String paramString, Object paramObject)
		throws OperationException;
		  
	public abstract <T> T hFindOne(Class<T> paramClass, String paramString)
		throws OperationException;
		  
	public abstract <S> List<S> hSave(Iterable<S> paramIterable)
		throws OperationException;
		  
	public abstract <T> void hSave(Iterable<T> paramIterable, int paramInt)
		throws OperationException;
		  
	public abstract <S> S hSave(S paramS)
		throws OperationException;
		  
	public abstract <S> S hSaveorupdate(S paramS)
		throws OperationException;
		  
	public abstract <S> S hUpdate(S paramS)
		throws OperationException;
		  
	public abstract <S> List<S> hUpdate(Iterable<S> paramIterable)
		throws OperationException;
		  
	public abstract List<Map<String, Object>> hExcute(String paramString)
		throws OperationException;
		  
	public abstract List<Map<String, Object>> hExcute(String paramString, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract long hCount(String paramString)
		throws OperationException;
		  
	public abstract long hCount(String paramString, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract long hCount(String paramString, List<Object> paramList)
		throws OperationException;
		  
	public abstract <T> List<T> hFindAll(String paramString)
		throws OperationException;
		  
	public abstract int hExecuteUpdate(String paramString, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract int hExecuteUpdateSql(String paramString, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract <T> List<T> hFindAll(String paramString, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract <T> List<T> hFindAll(String paramString, List<Object> paramList)
		throws OperationException;
		  
	public abstract <T> Page<T> hFindAll(String paramString, Pageable paramPageable)
		throws OperationException;
		  
	public abstract <T> Page<T> hFindAll(String paramString, Pageable paramPageable, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract <T> Page<T> hFindAll(String paramString, Pageable paramPageable, List<Object> paramList)
		throws OperationException;
		  
	public abstract <T> List<T> hFindAll(String paramString, Sort paramSort)
		throws OperationException;
		  
	public abstract <T> List<T> hFindAll(String paramString, Sort paramSort, List<Object> paramList)
		throws OperationException;
		  
	public abstract <T> List<T> hFindAll(String paramString, Sort paramSort, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract boolean hFelete(String paramString, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract long hNativeCount(String paramString, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract long hNativeCountAnd(String paramString)
		throws OperationException;
		  
	public abstract long hNativeCountOr(String paramString)
		throws OperationException;
		  
	public abstract List<Map<String, Object>> hFindBySql(String paramString)
		throws OperationException;
		  
	public abstract List<Map<String, Object>> hFindBySql(String paramString, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract List<Map<String, Object>> hFindBySql(String paramString, Sort paramSort)
		throws OperationException;
		  
	public abstract List<Map<String, Object>> hFindBySql(String paramString, Sort paramSort, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract Page hFindBySql(String paramString, Pageable paramPageable)
		throws OperationException;
		  
	public abstract Page hFindBySql(String paramString, Pageable paramPageable, Object... paramVarArgs)
		    throws OperationException;
		  
	public abstract Page hFindBySqlAnd(String paramString1, Pageable paramPageable, String paramString2)
		throws OperationException;
		  
	public abstract Page hFindBySqlOr(String paramString1, Pageable paramPageable, String paramString2)
		throws OperationException;
		  
	public abstract void hDeleteBySql(String paramString, Object... paramVarArgs)
		throws OperationException;
		  
	public abstract String hGetCountHql(String paramString);
}

package com.classaffairs.framework.sdp.orm.query;

import java.util.List;

public interface Pageable {
	
	public abstract int getPageNumber();
	  
	public abstract int getPageSize();
	  
	public abstract int getOffset();
	  
	public abstract Sort getSort();
	  
	public abstract long computeLastPageNumber(long paramLong);
	  
	public abstract long computePageNumber(long paramLong);
	  
	public abstract long computeLastPageNumber(long paramLong, int paramInt);
	  
	public abstract List<Integer> generateLinkPageNumbers(int paramInt1, long paramLong, int paramInt2);
	  
	public abstract int getFirstResult();
	  
	public abstract void setSort(Sort paramSort);
}

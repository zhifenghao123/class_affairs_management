package com.classaffairs.framework.sdp.orm.query;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface Page<Object> extends Iterable<Object> {
	
	  public abstract int getNumber();
	  
	  public abstract int getSize();
	  
	  public abstract int getTotalPages();
	  
	  public abstract int getNumberOfElements();
	  
	  public abstract long getTotal();
	  
	  public abstract boolean hasPreviousPage();
	  
	  public abstract boolean isFirstPage();
	  
	  public abstract boolean hasNextPage();
	  
	  public abstract boolean isLastPage();
	  
	  public abstract Iterator<Object> iterator();
	  
	  public abstract List<Object> getRows();
	  
	  public abstract boolean hasContent();
	  
	  public abstract Sort getSort();
	  
	  public abstract String getSortColumns();
	  
	  public abstract void setSortColumns(String paramString);
	  
	  public abstract void setTotalCount(int paramInt);
	  
	  public abstract void setResult(List paramList);
	  
	  public abstract List getResult();
	  
	  public abstract boolean isHasNextPage();
	  
	  public abstract boolean isHasPreviousPage();
	  
	  public abstract long getLastPageNumber();
	  
	  public abstract int getTotalCount();
	  
	  public abstract int getThisPageFirstElementNumber();
	  
	  public abstract long getThisPageLastElementNumber();
	  
	  public abstract int getNextPageNumber();
	  
	  public abstract int getPreviousPageNumber();
	  
	  public abstract int getPageSize();
	  
	  public abstract int getThisPageNumber();
	  
	  public abstract List<Integer> getLinkPageNumbers();
	  
	  public abstract int getFirstResult();
	  
	  public abstract Map getFilters();
	  
	  public abstract void setFilters(Map paramMap);
}

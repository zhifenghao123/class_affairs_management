package com.classaffairs.framework.sdp.orm.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PageImpl<Object> implements Page<Object>, Serializable {
	private static final long serialVersionUID = 867755909294344406L;
	  private List<Object> rows = new ArrayList();
	  private final Pageable pageable;
	  private long total = 0L;
	  private Map filters = new HashMap();
	  
	  public void setRows(List<Object> rows){
	    this.rows = rows;
	  }
	  
	  public PageImpl(){
	    this(0, 10);
	  }
	  
	  public PageImpl(Map filters){
	    this(0, 0, filters);
	  }
	  
	  public PageImpl(int pageNumber, int pageSize){
	    this(pageNumber, pageSize, null);
	  }
	  
	  public PageImpl(int pageNumber, int pageSize, Map filters){
	    this(pageNumber, pageSize, filters, null);
	  }
	  
	 /* public PageImpl(int pageNumber, int pageSize, String sortColumns){
	    this(pageNumber, pageSize, null, sortColumns);
	  }*/
	  
	  public PageImpl(int pageNumber, int pageSize, Map filters, String sortColumns){
	    if (pageSize <= 0) {
	      throw new IllegalArgumentException("[pageSize] must great than zero");
	    }
	    this.pageable = new PageRequest(pageNumber, pageSize);
	    setFilters(filters);
	    setSortColumns(sortColumns);
	  }
	  
	  public PageImpl(List<Object> content, Pageable pageable, long total)
	  {
	    if (content == null) {
	      throw new IllegalArgumentException("Content must not be null!");
	    }
	    this.rows.addAll(content);
	    this.total = total;
	    this.pageable = pageable;
	  }
	  
	  public PageImpl(List<Object> content)
	  {
	    this(content, null, content == null ? 0 : content.size());
	  }
	  
	  public int getPageNumber(){
	    return this.pageable.getPageNumber();
	  }
	  
	  @Override
	  public Map getFilters(){
	    if (this.filters == null) {
	      this.filters = new HashMap();
	    }
	    return this.filters;
	  }
	  @Override
	  public void setFilters(Map filters){
	    this.filters = filters;
	  }
	  
	  

	@Override
	public int getNumber() {
		return this.pageable == null ? 0 : this.pageable.getPageNumber();
	}

	@Override
	public int getSize() {
		return this.pageable == null ? 0 : this.pageable.getPageSize();
	}

	@Override
	public int getTotalPages() {
		return getSize() == 0 ? 1 : (int)Math.ceil((double)this.total / getSize());
	}

	@Override
	public int getNumberOfElements() {
		return this.rows.size();
	}

	@Override
	public long getTotal() {
		return this.total;
	}
	
	public void setTotal(int total)
	  {
	    this.total = total;
	  }

	@Override
	public boolean hasPreviousPage() {
		return getNumber() > 0;
	}

	@Override
	public boolean isFirstPage() {
		return !hasPreviousPage();
	}

	@Override
	public boolean hasNextPage() {
		return getNumber() + 1 < getTotalPages();
	}

	@Override
	public boolean isLastPage() {
		return !hasNextPage();
	}

	@Override
	public Iterator<Object> iterator() {
		return this.rows.iterator();
	}

	@Override
	public List<Object> getRows() {
		return Collections.unmodifiableList(this.rows);
	}

	@Override
	public boolean hasContent() {
		return !this.rows.isEmpty();
	}
	
	public String toString()
	  {
	    String contentType = "UNKNOWN";
	    if (this.rows.size() > 0) {
	      contentType = this.rows.get(0).getClass().getName();
	    }
	   /* return String.format("Page %s of %d containing %s instances", new Object[] {
	      Integer.valueOf(getNumber()), Integer.valueOf(getTotalPages()), contentType });*/
	    return String.format("Page %s of %d containing %s instances", Integer.valueOf(getNumber()), Integer.valueOf(getTotalPages()), contentType );
	  }
	public int hashCode()
	  {
	    int result = 17;
	    
	    result = 31 * result + (int)(this.total ^ this.total >>> 32);
	    result = 31 * result + (this.pageable == null ? 0 : this.pageable.hashCode());
	    result = 31 * result + this.rows.hashCode();
	    
	    return result;
	  }
	

	@Override
	  public Sort getSort()
	  {
	    return this.pageable == null ? null : this.pageable.getSort();
	  }
	  
	  public void setSort(Sort sort)
	  {
	    this.pageable.setSort(sort);
	  }

	@Override
	public String getSortColumns() {
		StringBuffer sortCloumns = new StringBuffer();
	    if (getSort() != null) {
	      for (Sort.Order order : getSort()) {
	        sortCloumns.append(order.getProperty()).append(" ").append(order.getDirection()).append(",");
	      }
	    }
	    return sortCloumns.toString();
	}

	@Override
	public void setSortColumns(String sortColumns) {
		if (sortColumns != null){
	      List<Sort.Order> list = new ArrayList();
	      String[] sortSegments = sortColumns.trim().split(",");
	      for (int i = 0; i < sortSegments.length; i++)
	      {
	        String sortSegment = sortSegments[i];
	        String[] array = sortSegment.split("\\s+");
	        
	        Sort.Order order = new Sort.Order(array.length == 2 ? Sort.Direction.fromString(array[1]) : Sort.Direction.ASC, array[0]);
	        list.add(order);
	      }
	      Sort sort = new Sort(list);
	      setSort(sort);
	    }

	}

	@Override
	public void setTotalCount(int total) {
		this.total = total;
	    setResult(new ArrayList(0));

	}

	@Override
	public void setResult(List elements) {
		if (elements == null) {
		   throw new IllegalArgumentException("'result' must be not null");
		}
		this.pageable.computePageNumber(this.total);
		this.rows = elements;

	}

	@Override
	public List getResult() {
		return this.rows;
	}

	@Override
	public boolean isHasNextPage() {
		 return hasNextPage();
	}

	@Override
	public boolean isHasPreviousPage() {
		return hasPreviousPage();
	}

	@Override
	public long getLastPageNumber() {
		return this.pageable.computeLastPageNumber(this.total, this.pageable.getPageSize());
	}

	@Override
	public int getTotalCount() {
		return Integer.parseInt(String.valueOf(this.total));
	}

	@Override
	public int getThisPageFirstElementNumber() {
		return (getThisPageNumber() - 1) * getPageSize() + 1;
	}

	@Override
	public long getThisPageLastElementNumber() {
		int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
	    return getTotalCount() < fullPage ? getTotalCount() : fullPage;
	}

	@Override
	public int getNextPageNumber() {
		return getThisPageNumber() + 1;
	}

	@Override
	public int getPreviousPageNumber() {
		return getThisPageNumber() - 1;
	}

	@Override
	public int getPageSize() {
		return this.pageable.getPageSize();
	}

	@Override
	public int getThisPageNumber() {
		return this.pageable.getPageNumber();
	}

	@Override
	public List<Integer> getLinkPageNumbers() {
		 return this.pageable.generateLinkPageNumbers(getThisPageNumber(), getLastPageNumber(), 10);
	}

	@Override
	public int getFirstResult() {
		return this.pageable.getFirstResult();
	}

}

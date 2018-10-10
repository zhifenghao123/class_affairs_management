package com.classaffairs.framework.sdp.orm.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageRequest implements Pageable, Serializable {
	
	private static final long serialVersionUID = 8280485938848398236L;
	  private int page;
	  private int size;
	  private Sort sort;
	  
	  
	  public PageRequest(int page, int size)
	  {
	    this(page, size, null);
	  }
	  
	  public PageRequest(int page, int size, Sort.Direction direction, String... properties)
	  {
	    this(page, size, new Sort(direction, properties));
	  }
	  
	  public PageRequest(int page, int size, Sort sort)
	  {
	    if (page < 0) {
	      throw new IllegalArgumentException(
	        "Page index must not be less than zero!");
	    }
	    if (size <= 0) {
	      throw new IllegalArgumentException(
	        "Page size must not be less than or equal to zero!");
	    }
	    this.page = getFirstResultCount(page);
	    this.size = size;
	    this.sort = sort;
	  }
	  
	  private static int getFirstResultCount(int page)
	  {
	    return page - 1;
	  }
	  

	@Override
	public int getPageNumber() {
		return this.page;
	}

	@Override
	public int getPageSize() {
		return this.size;
	}

	@Override
	public int getOffset() {
		return this.page * this.size;
	}

	@Override
	public Sort getSort() {
		return this.sort;
	}
	
	@Override
	public void setSort(Sort paramSort) {
		this.sort = sort;

	}
	
	public boolean equals(Object obj)
	  {
	    if (this == obj) {
	      return true;
	    }
	    if (!(obj instanceof PageRequest)) {
	      return false;
	    }
	    PageRequest that = (PageRequest)obj;
	    
	    boolean pageEqual = this.page == that.page;
	    boolean sizeEqual = this.size == that.size;
	    
	    boolean sortEqual = this.sort == null ? false : that.sort == null ? true : this.sort
	      .equals(that.sort);
	    
	    return (pageEqual) && (sizeEqual) && (sortEqual);
	  }
	  
	  public int hashCode()
	  {
	    int result = 17;
	    
	    result = 31 * result + this.page;
	    result = 31 * result + this.size;
	    result = 31 * result + (this.sort == null ? 0 : this.sort.hashCode());
	    
	    return result;
	  }
	  

	@Override
	public long computeLastPageNumber(long total) {
		long result = total % this.size == 0L ? total / this.size : total / this.size + 1L;
	    if (result <= 1L) {
	      result = 1L;
	    }
	    return result;
	}

	@Override
	public long computePageNumber(long total) {
		if (this.page <= 1) {
		      return 1L;
		    }
		    if ((2147483647 == this.page) || 
		      (this.page > computeLastPageNumber(total))) {
		      return computeLastPageNumber(total);
		    }
		    return this.page;
	}

	@Override
	public long computeLastPageNumber(long total, int size) {
		this.size = size;
	    long result = total % size == 0L ? 
	      total / size : 
	      total / size + 1L;
	    if (result <= 1L) {
	      result = 1L;
	    }
	    return result;
	}

	@Override
	public List<Integer> generateLinkPageNumbers(int currentPageNumber, long lastPageNumber,
			int count) {
		int avg = count / 2;
	    
	    long startPageNumber = currentPageNumber - avg;
	    if (startPageNumber <= 0L) {
	      startPageNumber = 1L;
	    }
	    long endPageNumber = startPageNumber + count - 1L;
	    if (endPageNumber > lastPageNumber) {
	      endPageNumber = lastPageNumber;
	    }
	    if (endPageNumber - startPageNumber < count)
	    {
	      startPageNumber = endPageNumber - count;
	      if (startPageNumber <= 0L) {
	        startPageNumber = 1L;
	      }
	    }
	    List<Integer> result = new ArrayList();
	    for (long i = startPageNumber; i <= endPageNumber; i += 1L) {
	      result.add(Integer.valueOf(Integer.parseInt(String.valueOf(i))));
	    }
	    return result;
	}

	@Override
	public int getFirstResult() {
		if (this.size <= 0) {
		      throw new IllegalArgumentException("[pageSize] must great than zero");
		    }
		    return this.page * this.size;
	}



}

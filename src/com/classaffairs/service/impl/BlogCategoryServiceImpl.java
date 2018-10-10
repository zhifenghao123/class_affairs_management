/**
 * 
 */
package com.classaffairs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.BlogCategoryDao;
import com.classaffairs.entity.BlogCategory;
import com.classaffairs.service.BlogCategoryService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {

	@Autowired
	private BlogCategoryDao itsBlogCategoryDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogCategoryService#addBlogCategory(com.classaffairs.entity.BlogCategory)
	 */
	@Override
	public boolean addBlogCategory(BlogCategory blogCategory) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogCategoryService#updateBlogCategory(com.classaffairs.entity.BlogCategory)
	 */
	@Override
	public boolean updateBlogCategory(BlogCategory blogCategory) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogCategoryService#deleteBlogCategory(java.lang.Long)
	 */
	@Override
	public boolean deleteBlogCategory(Long blogCategoryId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogCategoryService#findBlogCategoryByBlogCategoryId(java.lang.Long)
	 */
	@Override
	public BlogCategory findBlogCategoryByBlogCategoryId(Long blogCategoryId) {
		// TODO Auto-generated method stub
		return null;
	}

}

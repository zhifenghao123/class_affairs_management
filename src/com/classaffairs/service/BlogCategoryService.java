/**
 * 
 */
package com.classaffairs.service;

import com.classaffairs.entity.BlogCategory;

/**
 * @author Haozhifeng
 *
 */
public interface BlogCategoryService {
	
	boolean addBlogCategory(BlogCategory blogCategory);
	
	boolean updateBlogCategory(BlogCategory blogCategory);
	
	boolean deleteBlogCategory(Long blogCategoryId);
	
	BlogCategory findBlogCategoryByBlogCategoryId(Long blogCategoryId);

}

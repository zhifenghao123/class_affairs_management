/**
 * 
 */
package com.classaffairs.service;

import java.math.BigInteger;

import com.classaffairs.entity.Blog;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface BlogService {
	boolean addBlog(Blog blog);
	
	boolean updateBlog(Blog blog);
	
	boolean deleteBlog(Blog blog);
	
	Blog findBlogByBlogId(BigInteger blogId);
	
	Page<Blog> getBlogsByPageQuery(String blogTile,Long ownerId,String keyWordSearch,int page,int pageSize);
}

/**
 * 
 */
package com.classaffairs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.dao.BlogDao;
import com.classaffairs.entity.Blog;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.BlogService;

/**
 * @author Haozhifeng
 *
 */
//@Service
public class BlogServiceImpl_backup implements BlogService {

	@Autowired
	private BlogDao itsBlogDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogService#addBlog(com.classaffairs.entity.Blog)
	 */
	@Override
	public boolean addBlog(Blog blog) {
		try {
			int result = itsBlogDao.mSave(blog);
			return result == 1;
		} catch (DataAccessException dae) {
			Log.log.error("新增博客操作数据库异常,学生：" + blog.getOwnerId(), dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增博客异常,学生：" + blog.getOwnerId(), e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogService#updateBlog(com.classaffairs.entity.Blog)
	 */
	@Override
	public boolean updateBlog(Blog blog) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogService#deleteBlog(java.lang.Long)
	 */
	@Override
	public boolean deleteBlog(Long blogId) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.BlogService#findBlogByBlogId(java.lang.Long)
	 */
	@Override
	public Blog findBlogByBlogId(Long blogId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Blog> getBlogsByPageQuery(String blogTile, String page,
			String pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}

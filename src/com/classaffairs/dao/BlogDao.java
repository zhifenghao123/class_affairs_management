/**
 * 
 */
package com.classaffairs.dao;

import org.springframework.stereotype.Repository;

import com.classaffairs.dao.base.BaseDaoImpl;
import com.classaffairs.entity.Blog;

/**
 * @author Haozhifeng
 *
 */
@Repository
public class BlogDao extends BaseDaoImpl<Blog> {
	private String collectionName = "blog";
}

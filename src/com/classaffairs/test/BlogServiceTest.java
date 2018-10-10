/**
 * 
 */
package com.classaffairs.test;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.classaffairs.entity.Blog;
import com.classaffairs.framework.core.springtestcase.SpringTestCase;
import com.classaffairs.service.BlogService;

/**
 * @author Haozhifeng
 *
 */
public class BlogServiceTest extends SpringTestCase {
	@Autowired
	private BlogService itsBlogService;
	@Test
	@Rollback(false)
	public void testAddBlog() {
		Date currentTime = new Date();
		Blog blog = new Blog();
		blog.setBlogId(1000l);
		blog.setContentPath("\\g\\g\\g\\g\\j");
		blog.setTitle("测试");
		blog.setLinkUrl("http:\\dsf\\sfsd\\sdfs\\sd");
		blog.setCreateTime(currentTime);
		boolean re = itsBlogService.addBlog(blog);
		System.out.print(re);
	}
}

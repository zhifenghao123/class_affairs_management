/**
 * 
 */
package com.classaffairs.entity;

/**
 * @author Haozhifeng
 *
 */
public class BlogCategory {
	private Long blogCategoryId;	    
	private String name;
	private Integer level;
	public Long getBlogCategoryId() {
		return blogCategoryId;
	}
	public void setBlogCategoryId(Long blogCategoryId) {
		this.blogCategoryId = blogCategoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
}

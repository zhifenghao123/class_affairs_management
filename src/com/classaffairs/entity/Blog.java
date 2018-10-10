/**
 * 
 */
package com.classaffairs.entity;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Haozhifeng
 *
 */
@Document(collection="blog")
public class Blog {
	//private String _id;
	@Id()
	private BigInteger blogId;
	/*MongoMappingConverter按照下面的规则将java class对应到_id字段：
	1. 被标记为@Id (org.springframework.data.annotation.Id) 的字段或者属性。
	2. 没有标记，但是名字是id的字段或者属性（类型为string或者BigInteger）。*/
	//private Long blogCategoryId;
	private String[] tags;
	private Long ownerId;
	private String ownerName;
	private String title;
	private String referrenceBlogUrl;
	private String content;
	private String contentPath;
	private Date createTime;
	private Date updateTime;
	

	/*public String getId() {
		return _id;
	}
	public void setId(String id) {
		this._id = id;
	}*/
	public BigInteger getBlogId() {
		return blogId;
	}
	public void setBlogId(BigInteger blogId) {
		this.blogId = blogId;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	/*public Long getBlogCategoryId() {
		return blogCategoryId;
	}
	public void setBlogCategoryId(Long blogCategoryId) {
		this.blogCategoryId = blogCategoryId;
	}*/
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReferrenceBlogUrl() {
		return referrenceBlogUrl;
	}
	public void setReferrenceBlogUrl(String referrenceBlogUrl) {
		this.referrenceBlogUrl = referrenceBlogUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentPath() {
		return contentPath;
	}
	public void setContentPath(String contentPath) {
		this.contentPath = contentPath;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

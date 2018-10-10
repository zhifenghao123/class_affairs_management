/**
 * 
 */
package com.classaffairs.action;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.classaffairs.common.CommonPath;
import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.common.GetPageSize;
import com.classaffairs.common.FreeMarketTool.CreatHtmlForBlog;
import com.classaffairs.common.FreeMarketTool.GetDataByURL;
import com.classaffairs.entity.Blog;
import com.classaffairs.entity.Major;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.Blog;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.BlogService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class BlogAction {
	@Autowired
	private BlogService itsBlogService;
	
	@RequestMapping(value = "/blog/blogList.action")
	public ModelAndView blogList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		//String rows = request.getParameter("rows");
		
		String blogNoToSearch = request.getParameter("blogNoToSearch");
		String blogNameToSearch = request.getParameter("blogNameToSearch");
		String schoolToSearch = request.getParameter("schoolToSearch");
		String majorToSearch = request.getParameter("majorToSearch");
		String executiveClassToSearch = request.getParameter("executiveClassToSearch");
		
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
		}
		
		
		if(blogNoToSearch == null || blogNoToSearch.equals("")){
			blogNoToSearch = "";
		}else{
			blogNoToSearch = blogNoToSearch.trim();
		}
		if(blogNameToSearch == null || blogNameToSearch.equals("")){
			blogNameToSearch = "";
		}else{
			blogNameToSearch = blogNameToSearch.trim();
		}
		if(schoolToSearch == null || schoolToSearch.equals("")){
			schoolToSearch = "";
		}else{
			schoolToSearch = schoolToSearch.trim();
		}
		if(majorToSearch == null || majorToSearch.equals("")){
			majorToSearch = "";
		}else{
			majorToSearch = majorToSearch.trim();
		}
		if(executiveClassToSearch == null || executiveClassToSearch.equals("")){
			executiveClassToSearch = "";
		}else{
			executiveClassToSearch = executiveClassToSearch.trim();
		}
		Integer[] month = {1,2};
		ModelMap model = new ModelMap();
		List<Map<String,Object>> blogList = new ArrayList<Map<String,Object>>();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		//Page<Blog> blogPage = itsBlogService.getBlogsByPageQuery(blogTile, ownerId, keyWordSearch, mongth, page, pageSize);
		Page<Blog> blogPage = itsBlogService.getBlogsByPageQuery(null, null, null,Integer.valueOf(page), pageSize);
		//result.addProperty("total", blogPage.getTotalCount());
		//int i = blogPage.getTotalPages();
		if(blogPage != null){
			//List<Blog> blogList = blogPage.getResult();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(Blog blog:blogPage){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("blogId", blog.getBlogId());
				map.put("ownerName", blog.getOwnerName());
				map.put("title", blog.getTitle());
				map.put("createTime", sdf.format(blog.getCreateTime()));
				blogList.add(map);
			}
			
		}
		model.addAttribute("blogList", blogList);
		//model.addAttribute("totalPage", blogPage.getTotalPages());
		model.addAttribute("totalPage", 0);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/blog/blog_list", "blogInfo", model);
	}
	@RequestMapping(value = "/blog/myBlogList.action")
	public ModelAndView myBlogList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		//String rows = request.getParameter("rows");
		
		String blogNoToSearch = request.getParameter("blogNoToSearch");
		String blogNameToSearch = request.getParameter("blogNameToSearch");
		String schoolToSearch = request.getParameter("schoolToSearch");
		String majorToSearch = request.getParameter("majorToSearch");
		String executiveClassToSearch = request.getParameter("executiveClassToSearch");
		
		Long userId = (Long) request.getSession().getAttribute("studentId");
		
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
		}
		
		
		if(blogNoToSearch == null || blogNoToSearch.equals("")){
			blogNoToSearch = "";
		}else{
			blogNoToSearch = blogNoToSearch.trim();
		}
		if(blogNameToSearch == null || blogNameToSearch.equals("")){
			blogNameToSearch = "";
		}else{
			blogNameToSearch = blogNameToSearch.trim();
		}
		if(schoolToSearch == null || schoolToSearch.equals("")){
			schoolToSearch = "";
		}else{
			schoolToSearch = schoolToSearch.trim();
		}
		if(majorToSearch == null || majorToSearch.equals("")){
			majorToSearch = "";
		}else{
			majorToSearch = majorToSearch.trim();
		}
		if(executiveClassToSearch == null || executiveClassToSearch.equals("")){
			executiveClassToSearch = "";
		}else{
			executiveClassToSearch = executiveClassToSearch.trim();
		}
		Integer[] month = {1,2};
		ModelMap model = new ModelMap();
		List<Map<String,Object>> blogList = new ArrayList<Map<String,Object>>();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		//Page<Blog> blogPage = itsBlogService.getBlogsByPageQuery(blogTile, ownerId, keyWordSearch, mongth, page, pageSize);
		Page<Blog> blogPage = itsBlogService.getBlogsByPageQuery(null, userId, null,Integer.valueOf(page), pageSize);
		//result.addProperty("total", blogPage.getTotalCount());
		//int i = blogPage.getTotalPages();
		if(blogPage != null){
			//List<Blog> blogList = blogPage.getResult();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(Blog blog:blogPage){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("blogId", blog.getBlogId());
				map.put("ownerName", blog.getOwnerName());
				map.put("title", blog.getTitle());
				map.put("createTime", sdf.format(blog.getCreateTime()));
				blogList.add(map);
			}
			
		}
		model.addAttribute("blogList", blogList);
		//model.addAttribute("totalPage", blogPage.getTotalPages());
		model.addAttribute("totalPage", 0);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/blog/blog_home", "blogInfo", model);
	}
	
	/**
	 * 新增博客
	 */
	@RequestMapping(value = "/admin/addBlog")
	@ResponseBody
	public String addBlog(HttpServletRequest request){
		String blogIdStr = request.getParameter("blogId");
		//Long blogId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.BLOG_OBJECTTYPE);
		//String blogCategoryId;
		Long blogId = Long.valueOf(blogIdStr);
		Long studentId = (Long) request.getSession().getAttribute("studentId");
		String studentName = (String) request.getSession().getAttribute("studentName");
		//String ownerId = request.getParameter("ownerId");
		String title = request.getParameter("title");
		String referrenceBlogUrl = request.getParameter("referrenceBlogUrl");
		//String tags = request.getParameter("tags");
		String tags[] = request.getParameterValues("tags[]");
		//String contentPath = request.getParameter("contentPath");
		String path = CommonPath.getFtlPath();
		String introduce = request.getParameter("content");
		introduce = introduce.replaceAll("<pre[^>]+>", "");
		introduce = introduce.replaceAll("</pre>", "");
		CreatHtmlForBlog chb = new CreatHtmlForBlog(path, blogId, introduce, "blog");
		String introduceMemo = chb.getHtmlForBlog();
		Date createTime = new Date();
		
		JsonObject jo = new JsonObject();
		Blog blog = new Blog();
		blog.setBlogId(BigInteger.valueOf(blogId));
		blog.setOwnerId(studentId);
		blog.setOwnerName(studentName);
		blog.setTitle(title);
		blog.setTags(tags);
		blog.setContentPath(introduceMemo);
		blog.setContent(introduce);
		blog.setReferrenceBlogUrl(referrenceBlogUrl);
		blog.setCreateTime(createTime);
		
		boolean result = itsBlogService.addBlog(blog);
		jo.addProperty("msg", result);
		return jo.toString();
	}
	/**
	 * 删除博客
	 */
	@RequestMapping(value = "/admin/deleteBlog")
	@ResponseBody
	public String deleteBlog(HttpServletRequest request){
		JsonObject jo = new JsonObject();
		String blogId = (String) request.getParameter("blogId");
		Blog blog = itsBlogService.findBlogByBlogId(BigInteger.valueOf(Long.valueOf(blogId)));
		boolean result = itsBlogService.deleteBlog(blog);
		jo.addProperty("msg", result);
		return jo.toString();
	}
	/**
	 * 根据Id获取博客
	 */
	@RequestMapping(value = "/student/getBlog")
	@ResponseBody
	public String getBlogById(HttpServletRequest request){
		JsonObject jo = new JsonObject();
		String blogId = (String) request.getParameter("blogId");
		Blog blog = itsBlogService.findBlogByBlogId(BigInteger.valueOf(Long.valueOf(blogId)));
		/*private String[] tags;
		private Long ownerId;
		private String ownerName;
		private String title;
		private String referrenceBlogUrl;
		private String content;
		private String contentPath;
		private Date createTime;*/
		if(null != blog){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			jo.addProperty("exist", true);
			jo.addProperty("title", blog.getTitle());
			//jo.addProperty("blog", blog.getTags());
			if(null != blog.getTags() && blog.getTags().length > 0){
				JsonArray tags = new JsonArray();
				for(String tag:blog.getTags()){
					JsonObject tagJson = new JsonObject();
					tagJson.addProperty("tag", tag);
					tags.add(tagJson);
				}
				jo.add("tags", tags);
			}
			jo.addProperty("ownerName", blog.getOwnerName());
			String blogContentText = GetDataByURL.getHtml(blog.getContentPath());
			jo.addProperty("blogContentText", blogContentText);
			jo.addProperty("content", blog.getContent());
			jo.addProperty("contentPath", blog.getContentPath());
			jo.addProperty("referrenceBlogUrl", blog.getReferrenceBlogUrl());
			jo.addProperty("createTime", sdf.format(blog.getCreateTime()));
		}else{
			jo.addProperty("exist", false);
		}
		return jo.toString();
	}
	
	/**
	 * 更新博客
	 */
	@RequestMapping(value = "/admin/updateBlog")
	@ResponseBody
	public String updateBlog(HttpServletRequest request){
		String blogIdStr = request.getParameter("blogId");
		//Long blogId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.BLOG_OBJECTTYPE);
		//String blogCategoryId;
		Long blogId = Long.valueOf(blogIdStr);
		//Long studentId = (Long) request.getSession().getAttribute("studentId");
		//String studentName = (String) request.getSession().getAttribute("studentName");
		
		String title = request.getParameter("title");
		String referrenceBlogUrl = request.getParameter("referrenceBlogUrl");
		//String tags = request.getParameter("tags");
		String tags[] = request.getParameterValues("tags[]");
		//String contentPath = request.getParameter("contentPath");
		String path = CommonPath.getFtlPath();
		String introduce = request.getParameter("content");
		introduce = introduce.replaceAll("<pre[^>]+>", "");
		introduce = introduce.replaceAll("</pre>", "");
		CreatHtmlForBlog chb = new CreatHtmlForBlog(path, blogId, introduce, "blog");
		String introduceMemo = chb.getHtmlForBlog();
		Date createTime = new Date();
		
		JsonObject jo = new JsonObject();
		Blog blog = itsBlogService.findBlogByBlogId(BigInteger.valueOf(blogId));
		blog.setBlogId(BigInteger.valueOf(blogId));
		//blog.setOwnerId(studentId);
		//blog.setOwnerName(studentName);
		blog.setTitle(title);
		blog.setTags(tags);
		blog.setContentPath(introduceMemo);
		blog.setContent(introduce);
		blog.setReferrenceBlogUrl(referrenceBlogUrl);
		blog.setCreateTime(createTime);
		
		boolean result = itsBlogService.updateBlog(blog);
		jo.addProperty("msg", result);
		return jo.toString();
	}
}

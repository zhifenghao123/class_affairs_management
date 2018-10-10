/**
 * 
 */
package com.classaffairs.common.FreeMarketTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Haozhifeng
 *
 */
public class CreatHtmlForBlog {
	private String basepath;
	private String filename = "blog";
	private Long Id;
	private String description;
	private String name;
	public CreatHtmlForBlog(String path, Long Id, String description, String name) {
		this.basepath = path;
		this.Id = Id;
		this.description = description;
		this.name = name;
	}

	//生成简介大文本
	public String getHtmlForBlog() {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("Id", Id);
		root.put("description", description);
		String templateFile = "/" + filename + ".ftl";
		//String htmlFile = basepath.replace("ftl", name)+Id+"/"+filename+Id+".html";
		String htmlFile = basepath.replace("template", "html" + "/" + name) + Id + "/" + filename + Id + ".html";
		String dir = basepath.replace("template", "html" + "/" + name) + Id;
		FreeMarkertUtil.analysisTemplate(basepath, templateFile, htmlFile, root, dir);
		return "/html/" + name + "/" + Id + "/" + filename + Id + ".html";

	}
}

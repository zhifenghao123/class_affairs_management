/**
 * 
 */
package com.classaffairs.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.service.MenuService;
import com.google.gson.Gson;

/**
 * @author Haozhifeng
 *
 */
@Controller
@RequestMapping(value = "/admin/baseControlAction")
public class BaseControlAction {
	
	@Autowired
	private MenuService itsMenuService;
	
	@RequestMapping(params = "north")
	public String layoutNorth() {
		return "admin/north";
	}
	
	/**
	 * 系统管理左侧树的构造
	 */
	@RequestMapping(params = "menu")
	@ResponseBody
	public String tree(String id, HttpServletRequest request) {

		Gson gson = new Gson();

		return gson.toJson(itsMenuService.tree(id, request));
	}
}

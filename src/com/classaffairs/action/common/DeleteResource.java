/**
 * 
 */
package com.classaffairs.action.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.Resource;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class DeleteResource {
	@RequestMapping(value="/deleteResource.action")
	public @ResponseBody
	String deleterResource(HttpServletRequest request){
		String resourcePath = request.getParameter("resourcePath");
		JsonObject jo = new JsonObject();
		Resource resource = new Resource(resourcePath);
		boolean deleteResult = resource.delete();
		jo.addProperty("msg", deleteResult);
		return jo.toString();
	}
}

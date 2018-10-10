/**
 * 
 */
package com.classaffairs.common.md5;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class MD5DataAction {
	@RequestMapping(value = "/Md5Data.action")
	public @ResponseBody
	String getMd5Data(HttpServletRequest request) {
		JsonObject jo = new JsonObject();
		String password = request.getParameter("password");
		if (password != null && !password.equals("")) {
			password = MD5Data.encryption(password);
		}
		jo.addProperty("encryptionText", password);
		return jo.toString();
	}
}

/**
 * 
 */
package com.classaffairs.action;

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

import com.classaffairs.common.GetPageSize;
import com.classaffairs.entity.OnlineBehavior;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.OnlineBehaviorService;
import com.classaffairs.service.StudentService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class OnlineBehaviorAction {
	@Autowired
	private OnlineBehaviorService itsOnlineBehaviorService;
	@Autowired
	private StudentService itsStudentService;
	
	
	/**
	 * 分页获取所有学院信息
	 */
	@RequestMapping(value = "/admin/getOnlineBehaviorList")
	@ResponseBody
	public String getOnlineBehaviorList(HttpServletRequest request) {
		String page = request.getParameter("page");

		String rows = request.getParameter("rows");

		String userNo = request.getParameter("userNo");
		
		String type = request.getParameter("type");

		if (page == null || page.trim().equals("")) {

			page = "1";
		} else {
			page = page.trim();
		}
		if (rows == null || rows.trim().equals("")) {

			rows = "10";
		} else {
			rows = rows.trim();
		}


		if (userNo != null && !userNo.trim().equals(""))

			userNo = userNo.trim();
		if (type != null && !type.trim().equals(""))
				type = type.trim();
		else
			type = "0";
		JsonObject result = new JsonObject();

		Page<OnlineBehavior> onlineBehaviorPage = itsOnlineBehaviorService.getOnlineBehaviorsByPageQuery(userNo, Integer.valueOf(type), Integer.valueOf(page), Integer.valueOf(rows));

		result.addProperty("total", onlineBehaviorPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (onlineBehaviorPage != null) {

			List<OnlineBehavior> onlineBehaviorList = onlineBehaviorPage.getResult();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (OnlineBehavior onlineBehavior : onlineBehaviorList) {

				JsonObject re = new JsonObject();
				re.addProperty("onlineBehaviorId", onlineBehavior.getOnlineBehaviorId());
				re.addProperty("userNo", onlineBehavior.getUserNo());
				Student sutdent = itsStudentService.findStudentByStudentNo(onlineBehavior.getUserNo());
				if(null != sutdent)
					re.addProperty("userName", sutdent.getName());
				else
					re.addProperty("userName", "");
				re.addProperty("userIp", onlineBehavior.getUserIp());
				re.addProperty("onlineBehaviorName", onlineBehavior.getOnlineBehaviorName());
				re.addProperty("recordTime", sdf.format(onlineBehavior.getRecordTime()));

				array.add(re);

			}

		}
		result.add("rows", array);

		return result.toString();
	}
	
	@RequestMapping(value = "/student/onlineBehaviorList")
	public ModelAndView getOnlineBehavior(HttpServletRequest request) {
		String studentNo = (String)request.getSession().getAttribute("studentNo");
		String page = request.getParameter("page");
		if (page == null || page.trim().equals("")) {
			page = "1";
		} else {
			page = page.trim();
		}
		JsonObject result = new JsonObject();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		
		ModelMap model = new ModelMap();
		List<Map<String,Object>> onlineBehaviorList = new ArrayList<Map<String,Object>>();
		Page<OnlineBehavior> OnlineBehaviorPage = itsOnlineBehaviorService.getOnlineBehaviorsByPageQuery(studentNo, 0, Integer.valueOf(page), Integer.valueOf(pageSize));

		result.addProperty("total", OnlineBehaviorPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (OnlineBehaviorPage != null) {

			List<OnlineBehavior> regionList = OnlineBehaviorPage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for (OnlineBehavior onlineBehavior : regionList) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("onlineBehaviorId", onlineBehavior.getOnlineBehaviorId());
				map.put("userIp", onlineBehavior.getUserIp());
				map.put("onlineBehaviorName", onlineBehavior.getOnlineBehaviorName());
				map.put("recordTime", sdf.format(onlineBehavior.getRecordTime()));
				onlineBehaviorList.add(map);
			}
		}
		model.addAttribute("onlineBehaviorList", onlineBehaviorList);
		//model.addAttribute("totalPage", blogPage.getTotalPages());
		model.addAttribute("totalPage", OnlineBehaviorPage.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/onlineBehavior/onlineBehavior", "onlineBehaviorInfo", model);
	}
}

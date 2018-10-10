/**
 * 
 */
package com.classaffairs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.entity.Dormitory;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.DormitoryService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class DormitoryAction {
	@Autowired
	private DormitoryService itsDormitoryService;
	
	@RequestMapping(value = "/admin/getDormitoryList")
	@ResponseBody
	public String getDormitoryList(HttpServletRequest request){
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		String dormitoryNoToSearch = request.getParameter("dormitoryNoToSearch");
		String sexToSearch = request.getParameter("sexToSearch");
		
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
		}
		if(rows == null || rows.equals("")){
			rows = "1";
		}else{
			rows = rows.trim();
		}
		
		if(dormitoryNoToSearch == null || dormitoryNoToSearch.equals("")){
			dormitoryNoToSearch = "";
		}else{
			dormitoryNoToSearch = dormitoryNoToSearch.trim();
		}
		if(sexToSearch == null || sexToSearch.equals("")){
			sexToSearch = "0";
		}else{
			sexToSearch = sexToSearch.trim();
		}
		JsonObject result = new JsonObject();
		Page<Dormitory> dormitoryPage = itsDormitoryService.getDormitorysByPageQuery(dormitoryNoToSearch, Integer.valueOf(page), Integer.valueOf(rows));
		result.addProperty("total", dormitoryPage.getTotalCount());
		JsonArray ja = new JsonArray();
		if(dormitoryPage != null){
			List<Dormitory> dormitoryList = dormitoryPage.getResult();
			for(Dormitory dormitory:dormitoryList){
				JsonObject jo = new JsonObject();
				jo.addProperty("dormitoryId", dormitory.getDormitoryId());
				jo.addProperty("roomNo", dormitory.getRoomNo());	
				ja.add(jo);
			}
			
		}
		result.add("rows", ja);
		return result.toString();
		
	}
	
	/**
	 * 新增宿舍号
	 */
	@RequestMapping(value = "/admin/addDormitory")
	@ResponseBody
	public String addDormitory(HttpServletRequest request){
		//String dormitoryId = request.getParameter("dormitoryId");
		Long dormitoryId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.DORMITORY_OBJECTTYPE);
		
		//Long studentId = (Long) request.getSession().getAttribute("studentId");
		String apartmentId = request.getParameter("apartmentId");
		String roomNo = request.getParameter("roomNo");
		String gender = request.getParameter("gender");
		
		JsonObject jo = new JsonObject();
		Dormitory dormitory = new Dormitory();
		dormitory.setDormitoryId(Long.valueOf(dormitoryId));
		dormitory.setRoomNo(roomNo);
		dormitory.setApartmentId(Long.valueOf(apartmentId));
		
		boolean result = itsDormitoryService.addDormitory(dormitory);
		jo.addProperty("msg", result);
		return jo.toString();
	}
}

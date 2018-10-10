/**
 * 
 */
package com.classaffairs.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.entity.Apartment;
import com.classaffairs.entity.Apartment;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.Apartment;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ApartmentService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class ApartmentAction {
	@Autowired
	private ApartmentService itsApartmentService;
	
	@RequestMapping(value = "/admin/getApartmentList")
	@ResponseBody
	public String getApartmentList(HttpServletRequest request){
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		String apartmentNoToSearch = request.getParameter("apartmentNoToSearch");
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
		
		if(apartmentNoToSearch == null || apartmentNoToSearch.equals("")){
			apartmentNoToSearch = "";
		}else{
			apartmentNoToSearch = apartmentNoToSearch.trim();
		}
		if(sexToSearch == null || sexToSearch.equals("")){
			sexToSearch = "0";
		}else{
			sexToSearch = sexToSearch.trim();
		}
		JsonObject result = new JsonObject();
		Page<Apartment> apartmentPage = itsApartmentService.getApartmentsByPageQuery(apartmentNoToSearch, Integer.valueOf(sexToSearch), Integer.valueOf(page), Integer.valueOf(rows));
		result.addProperty("total", apartmentPage.getTotalCount());
		JsonArray ja = new JsonArray();
		if(apartmentPage != null){
			List<Apartment> apartmentList = apartmentPage.getResult();
			for(Apartment apartment:apartmentList){
				JsonObject jo = new JsonObject();
				jo.addProperty("apartmentId", apartment.getApartmentId());
				jo.addProperty("apartmentNo", apartment.getApartmentNo());	
				if(apartment.getGender() == Apartment.SEX_MALE){
					jo.addProperty("gender", "男");
				}else{
					jo.addProperty("gender", "女");
				}
					
				ja.add(jo);
			}
			
		}
		result.add("rows", ja);
		return result.toString();
		
	}
	
	/**
	 * 新增公寓
	 */
	@RequestMapping(value = "/admin/addApartment")
	@ResponseBody
	public String addApartment(HttpServletRequest request){
		//String apartmentId = request.getParameter("apartmentId");
		Long apartmentId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.APARTMENT_OBJECTTYPE);
		
		//Long studentId = (Long) request.getSession().getAttribute("studentId");
		String apartmentNo = request.getParameter("apartmentNo");
		String gender = request.getParameter("gender");
		
		JsonObject jo = new JsonObject();
		Apartment apartment = new Apartment();
		apartment.setApartmentId(Long.valueOf(apartmentId));
		apartment.setApartmentNo(apartmentNo);
		apartment.setGender(Integer.valueOf(gender));
		
		boolean result = itsApartmentService.addApartment(apartment);
		jo.addProperty("msg", result);
		return jo.toString();
	}
	
	@RequestMapping(value = "/admin/getApartmentById")
	@ResponseBody
	public String getApartmentById(String id){
		Long apartmentId = Long.valueOf(id);
		Apartment apartment = itsApartmentService.findApartmentByApartmentId(apartmentId);
		Apartment app = new Apartment();
		try{
			BeanUtils.copyProperties(app, apartment);
		}catch(Exception e){
			Log.log.error("应用bean转换异常", e);
			e.printStackTrace();
		}
		Gson gson = new Gson();
		return gson.toJson(app);
	}
	
	/**
	 * 获取所有公寓
	 */
	@RequestMapping(value = "/admin/getAllApartmentList")
	@ResponseBody
	public String getAllApartmentList() {

		List<Apartment> apartmentList = itsApartmentService.getAllApartments();

		List<Apartment> apartments = new ArrayList<Apartment>();
		for (Apartment s : apartmentList) {

			Apartment apartment = new Apartment();

			try {
				BeanUtils.copyProperties(apartment, s);
			} catch (Exception e) {
				Log.log.error("公寓转换异常", e);
				e.printStackTrace();
			}
			apartments.add(apartment);

		}
		Gson gson = new Gson();

		return gson.toJson(apartments);
	}
	
	@RequestMapping(value = "/admin/updateApartment")
	@ResponseBody
	public String updateApartment(Apartment apartment){
		JsonObject result = new JsonObject();
		boolean success = itsApartmentService.updateApartment(apartment);
		result.addProperty("msg", success);
		return result.toString();
	}
}

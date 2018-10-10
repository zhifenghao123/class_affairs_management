/**
 * 
 */
package com.classaffairs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.entity.Laboratory;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.School;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.LaboratoryService;
import com.classaffairs.service.SchoolService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class LaboratoryAction {

	@Autowired
	private LaboratoryService itsLaboratoryService;
	@Autowired
	private SchoolService itsSchoolService;
	
	/**
	 * 分页获取所有教研室（研究室）信息
	 */
	@RequestMapping(value = "/admin/getLaboratory")
	@ResponseBody
	public String getLaboratory(HttpServletRequest request) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String name = request.getParameter("name");

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

		if (name != null && !name.trim().equals(""))
			name = name.trim();

		JsonObject result = new JsonObject();
		Page<Laboratory> laboratoryPage = itsLaboratoryService.getLaboratorysByPageQuery(name, Integer.valueOf(page), Integer.valueOf(rows));
		result.addProperty("total", laboratoryPage.getTotalCount());

		JsonArray array = new JsonArray();
		if (laboratoryPage != null) {

			List<Laboratory> regionList = laboratoryPage.getResult();
			for (Laboratory laboratory : regionList) {
				JsonObject re = new JsonObject();
				re.addProperty("laboratoryId", laboratory.getLaboratoryId());
				re.addProperty("laboratoryNo", laboratory.getLaboratoryNo());
				re.addProperty("address", laboratory.getAddress());
				String schoolAbbreviationName = null;
				String schoolNo = null;
				if(laboratory.getSchoolNo() != null && !("").equals(laboratory.getSchoolNo())){
					School school = itsSchoolService.findSchoolBySchoolNo(laboratory.getSchoolNo());
					schoolAbbreviationName = school.getAbbreviation();
					schoolNo = school.getSchoolNo();
				}
				
				re.addProperty("schoolAbbreviationName", schoolAbbreviationName);
				re.addProperty("schoolNo", schoolNo);
				re.addProperty("laboratoryLeaderId", laboratory.getLaboratoryLeaderId());
				re.addProperty("laboratoryLeaderName", laboratory.getLaboratoryLeaderName());
				
				array.add(re);

			}

		}
		result.add("rows", array);

		return result.toString();
	}
	
	/**
	 * 新增有教研室（研究室）
	 */
	@RequestMapping(value = "/admin/addLaboratory")
	@ResponseBody
	public String addLaboratory(Laboratory laboratory, HttpServletRequest request) {

		String laboratoryNo = laboratory.getLaboratoryNo();
		laboratoryNo = laboratoryNo == null ? "" : laboratoryNo.trim();
		
		JsonObject result = new JsonObject();
		Laboratory laboratoryExist = itsLaboratoryService.findLaboratoryByLaboratoryNo(laboratoryNo);

		if (laboratoryExist != null) {
			result.addProperty("exsit", true);
			return result.toString();
		} else {
			result.addProperty("exsit", false);
		}
		Long laboratoryId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.Laboratory_OBJECTTYPE);
		laboratory.setLaboratoryId(laboratoryId);

		boolean success = itsLaboratoryService.addLaboratory(laboratory);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 通过学院id获取有教研室（研究室）
	 */
	@RequestMapping(value = "/admin/getLaboratoryById")
	@ResponseBody
	public String getLaboratoryById(String id) {

		Long laboratoryId = Long.valueOf(id.trim());

		Laboratory laboratory = itsLaboratoryService.findLaboratoryByLaboratoryId(laboratoryId);

		Laboratory app = new Laboratory();

		try {
			BeanUtils.copyProperties(app, laboratory);
		} catch (Exception e) {
			Log.log.error("学院bean转换异常", e);
			e.printStackTrace();
		}

		Gson gson = new Gson();

		return gson.toJson(app);
	}

	/**
	 * 更新有教研室（研究室）
	 */
	@RequestMapping(value = "/admin/updateLaboratory")
	@ResponseBody
	public String updateRole(Laboratory laboratory) {

		JsonObject result = new JsonObject();

		boolean success = itsLaboratoryService.updateLaboratory(laboratory);

		result.addProperty("success", success);

		return result.toString();
	}
	/**
	 * 根据学院代码获取所有有教研室（研究室）
	 */
	@RequestMapping(value = "/admin/getLaboratorysBySchoolNo")
	@ResponseBody
	public String getLaboratoryListBySchoolNo(String schoolNo) {

		JsonArray result = new JsonArray();

		List<Laboratory> laboratorys = itsLaboratoryService.findLaboratoryListBySchoolNo(schoolNo);

		for(Laboratory m : laboratorys){
			JsonObject jo = new JsonObject();
			jo.addProperty("laboratoryNo", m.getLaboratoryNo());
			jo.addProperty("address", m.getAddress());
			result.add(jo);
		}

		return result.toString();
	}
	/**
	 * 删除有教研室（研究室）
	 */
	@RequestMapping(value = "/admin/deleteLaboratoryById")
	@ResponseBody
	public String deleteLaboratoryById(String id) {

		JsonObject result = new JsonObject();

		Long laboratoryId = Long.valueOf(id.trim());

		boolean success = itsLaboratoryService.deleteLaboratory(laboratoryId);

		result.addProperty("success", success);

		return result.toString();
	}
}

/**
 * 
 */
package com.classaffairs.action;

import java.text.ParseException;
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
import com.classaffairs.entity.Activity;
import com.classaffairs.entity.Activity;
import com.classaffairs.entity.Participation;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ActivityService;
import com.classaffairs.service.ParticipationService;
import com.classaffairs.service.StudentService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class ActivityAction {
	@Autowired
	private ActivityService itsActivityService;
	
	@Autowired
	private ParticipationService itsParticipationService;
	
	@Autowired
	private StudentService itsStudentService;
	
	@RequestMapping(value = "/student/activityList")
	public ModelAndView getActivity(HttpServletRequest request) {
		String page = request.getParameter("page");

		String keyWordSearch = request.getParameter("keyWordSearch");
		String startTimeToSearchPublishTime = request.getParameter("startTimeToSearchPublishTime");
		String endTimeToSearchPublishTime = request.getParameter("endTimeToSearchPublishTime");
		if (page == null || page.trim().equals("")) {

			page = "1";
		} else {
			page = page.trim();
		}

		if (keyWordSearch != null && !keyWordSearch.trim().equals(""))
			keyWordSearch = keyWordSearch.trim();

		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		String executiveClassToSearch = currentStudent.getExecutiveClassName();
		JsonObject result = new JsonObject();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		
		ModelMap model = new ModelMap();
		List<Map<String,Object>> activityList = new ArrayList<Map<String,Object>>();
		Page<Activity> ActivityPage = itsActivityService.getActivitysByPageQuery(keyWordSearch, startTimeToSearchPublishTime, endTimeToSearchPublishTime,executiveClassToSearch, Integer.valueOf(page), pageSize);

		result.addProperty("total", ActivityPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (ActivityPage != null) {

			List<Activity> regionList = ActivityPage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDateTime = new Date();
			
			for (Activity activity : regionList) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("activityId", activity.getActivityId());
				map.put("type", activity.getType());
				map.put("name", activity.getName());
				map.put("description", activity.getDescription());
				map.put("responsorName", activity.getResponsorName());
				map.put("starTime", sdf.format(activity.getStarTime()));
				map.put("endTime", sdf.format(activity.getEndTime()));
				if(activity.getEnrollEndTime().after(currentDateTime)){
					map.put("enrollFlag", true);
				}else{
					map.put("enrollFlag", false);
				}
				map.put("publishTime", sdf.format(activity.getPublishTime()));
				activityList.add(map);
				
			}

		}
		model.addAttribute("activityList", activityList);
		//model.addAttribute("totalPage", blogPage.getTotalPages());
		model.addAttribute("totalPage", 0);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/activity/activity_list", "activityInfo", model);
	}
	@RequestMapping(value = "/student/searchActivityList")
	public @ResponseBody
	String searchActivityList(HttpServletRequest request) {
		String page = request.getParameter("page");

		String keyWordSearch = request.getParameter("keyWordSearch");
		String startTimeToSearchPublishTime = request.getParameter("startTimeToSearchPublishTime");
		String endTimeToSearchPublishTime = request.getParameter("endTimeToSearchPublishTime");
		if (page == null || page.trim().equals("")) {

			page = "1";
		} else {
			page = page.trim();
		}

		if (keyWordSearch != null && !keyWordSearch.trim().equals(""))
			keyWordSearch = keyWordSearch.trim();

		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		String executiveClassToSearch = currentStudent.getExecutiveClassName();
		
		int pageSize = GetPageSize.PAGESIZE_LIST();
		
		JsonObject result = new JsonObject();
		JsonArray ja = new JsonArray();
		Page<Activity> ActivityPage = itsActivityService.getActivitysByPageQuery(keyWordSearch, startTimeToSearchPublishTime, endTimeToSearchPublishTime, executiveClassToSearch,Integer.valueOf(page), pageSize);
		int totalCount = ActivityPage.getTotalCount();
		int totalPage = ((totalCount % pageSize != 0) ? (totalCount / pageSize + 1) : (totalCount / pageSize));
		totalPage = totalCount == 0 ? 1 : totalPage;
		result.addProperty("totalPage", totalPage);
		result.addProperty("currentPage", Integer.valueOf(page));
		result.addProperty("pageSize", pageSize);

		JsonArray array = new JsonArray();

		if (ActivityPage != null) {

			List<Activity> regionList = ActivityPage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDateTime = new Date();
			
			for (Activity activity : regionList) {
				JsonObject jo = new JsonObject();
				jo.addProperty("activityId", activity.getActivityId());
				jo.addProperty("type", activity.getType());
				jo.addProperty("name", activity.getName());
				jo.addProperty("description", activity.getDescription());
				jo.addProperty("responsorName", activity.getResponsorName());
				jo.addProperty("starTime", sdf.format(activity.getStarTime()));
				jo.addProperty("endTime", sdf.format(activity.getEndTime()));
				if(activity.getEnrollEndTime().before(currentDateTime)){
					jo.addProperty("enrollFlag", true);
				}else{
					jo.addProperty("enrollFlag", false);
				}
				jo.addProperty("publishTime", sdf.format(activity.getPublishTime()));
				ja.add(jo);
				
			}

		}
		result.add("record", ja);
		return result.toString();
	}
	@RequestMapping(value = "/student/userPublishedActivityList")
	public ModelAndView userPublishedActivityList(HttpServletRequest request) {
		String page = request.getParameter("page");

		String keyWordSearch = request.getParameter("keyWordSearch");
		String startTimeToSearchPublishTime = request.getParameter("startTimeToSearchPublishTime");
		String endTimeToSearchPublishTime = request.getParameter("endTimeToSearchPublishTime");
		
		String publisherUserNo = request.getSession().getAttribute("studentNo").toString();
		if (page == null || page.trim().equals("")) {

			page = "1";
		} else {
			page = page.trim();
		}

		if (keyWordSearch != null && !keyWordSearch.trim().equals(""))
			keyWordSearch = keyWordSearch.trim();

		JsonObject result = new JsonObject();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		
		ModelMap model = new ModelMap();
		List<Map<String,Object>> activityList = new ArrayList<Map<String,Object>>();
		Page<Activity> ActivityPage = itsActivityService.getUserPublishedActivityListsByPageQuery(keyWordSearch, startTimeToSearchPublishTime, endTimeToSearchPublishTime,publisherUserNo, Integer.valueOf(page), pageSize);

		result.addProperty("total", ActivityPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (ActivityPage != null) {

			List<Activity> regionList = ActivityPage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDateTime = new Date();
			
			for (Activity activity : regionList) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("activityId", activity.getActivityId());
				map.put("type", activity.getType());
				map.put("name", activity.getName());
				map.put("description", activity.getDescription());
				map.put("responsorName", activity.getResponsorName());
				map.put("starTime", sdf.format(activity.getStarTime()));
				map.put("endTime", sdf.format(activity.getEndTime()));
				if(activity.getEnrollEndTime().before(currentDateTime)){
					map.put("enrollFlag", true);
				}else{
					map.put("enrollFlag", false);
				}
				map.put("publishTime", sdf.format(activity.getPublishTime()));
				activityList.add(map);
				
			}

		}
		model.addAttribute("activityList", activityList);
		//model.addAttribute("totalPage", blogPage.getTotalPages());
		model.addAttribute("totalPage", 0);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/activity/activity_list_manage", "activityInfo", model);
	}
	
	@RequestMapping(value = "/student/searchUserPublishedActivityList")
	public @ResponseBody
	String searchUserPublishedActivityList(HttpServletRequest request) {
		String page = request.getParameter("page");

		String keyWordSearch = request.getParameter("keyWordSearch");
		String startTimeToSearchPublishTime = request.getParameter("startTimeToSearchPublishTime");
		String endTimeToSearchPublishTime = request.getParameter("endTimeToSearchPublishTime");
		
		String publisherUserNo = request.getSession().getAttribute("studentNo").toString();
		if (page == null || page.trim().equals("")) {

			page = "1";
		} else {
			page = page.trim();
		}

		if (keyWordSearch != null && !keyWordSearch.trim().equals(""))
			keyWordSearch = keyWordSearch.trim();

		int pageSize = GetPageSize.PAGESIZE_LIST();
		
		JsonObject result = new JsonObject();
		JsonArray ja = new JsonArray();
		Page<Activity> ActivityPage = itsActivityService.getUserPublishedActivityListsByPageQuery(keyWordSearch, startTimeToSearchPublishTime, endTimeToSearchPublishTime,publisherUserNo, Integer.valueOf(page), pageSize);
		int totalCount = ActivityPage.getTotalCount();
		int totalPage = ((totalCount % pageSize != 0) ? (totalCount / pageSize + 1) : (totalCount / pageSize));
		totalPage = totalCount == 0 ? 1 : totalPage;
		result.addProperty("totalPage", totalPage);
		result.addProperty("currentPage", Integer.valueOf(page));
		result.addProperty("pageSize", pageSize);

		JsonArray array = new JsonArray();

		if (ActivityPage != null) {

			List<Activity> regionList = ActivityPage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDateTime = new Date();
			
			for (Activity activity : regionList) {
				JsonObject jo = new JsonObject();
				jo.addProperty("activityId", activity.getActivityId());
				jo.addProperty("type", activity.getType());
				jo.addProperty("name", activity.getName());
				jo.addProperty("description", activity.getDescription());
				jo.addProperty("responsorName", activity.getResponsorName());
				jo.addProperty("starTime", sdf.format(activity.getStarTime()));
				jo.addProperty("endTime", sdf.format(activity.getEndTime()));
				if(activity.getEnrollEndTime().before(currentDateTime)){
					jo.addProperty("enrollFlag", true);
				}else{
					jo.addProperty("enrollFlag", false);
				}
				jo.addProperty("publishTime", sdf.format(activity.getPublishTime()));
				ja.add(jo);
				
			}

		}
		result.add("record", ja);
		return result.toString();
	}
	
	/**
	 * 新增活动
	 */
	@RequestMapping(value = "/admin/addActivity")
	@ResponseBody
	public String addActivity(HttpServletRequest request){
		//String activityId = request.getParameter("activityId");
		Long activityId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.ACTIVITY_OBJECTTYPE);
		
		//Long studentId = (Long) request.getSession().getAttribute("studentId");
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String responsorNo = request.getParameter("responsorNo");
		String responsorName = request.getParameter("responsorName");
		String type = request.getParameter("type");
		String enrollEndTime = request.getParameter("enrollEndTime");
		String starTime = request.getParameter("starTime");
		String endTime = request.getParameter("endTime");
		
		Date publishTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		                                                                                                                        
		String studentNo = request.getSession().getAttribute("studentNo").toString();
		String studentName = itsStudentService.findStudentByStudentNo(studentNo).getName();
		
		JsonObject jo = new JsonObject();
		Activity activity = new Activity();
		activity.setActivityId(Long.valueOf(activityId));
		activity.setName(name);
		activity.setDescription(description);
		activity.setResponsorNo(responsorNo);
		activity.setResponsorName(responsorName);
		activity.setType(Integer.valueOf(type));
		try {
			activity.setEnrollEndTime(sdf.parse(enrollEndTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			activity.setStarTime(sdf.parse(starTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			activity.setEndTime(sdf.parse(endTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		activity.setPublisherNo(studentNo);
		activity.setPublisherName(studentName);
		activity.setPublishTime(publishTime);
		
		boolean result = itsActivityService.addActivity(activity);
		jo.addProperty("msg", result);
		return jo.toString();
	}
	/**
	 * 根据Id获取Id信息
	 */
	@RequestMapping(value = "/student/getActivityById")
	@ResponseBody
	public String getActivityById(HttpServletRequest request){
		String activityId = request.getParameter("activityId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JsonObject jo = new JsonObject();
		Activity activity = itsActivityService.findActivityByActivityId(Long.valueOf(activityId));
		if(null != activity){
			jo.addProperty("msg", true);
			jo.addProperty("name", activity.getName());
			jo.addProperty("type", activity.getType());
			jo.addProperty("description", activity.getDescription());
			jo.addProperty("responsorUser", activity.getResponsorName()+"("+activity.getResponsorNo()+")");
			jo.addProperty("responsorNo", activity.getResponsorNo());
			jo.addProperty("responsorName", activity.getResponsorName());
			jo.addProperty("starTime", sdf.format(activity.getStarTime()));
			jo.addProperty("endTime", sdf.format(activity.getEndTime()));
			jo.addProperty("enrollEndTime", sdf.format(activity.getEnrollEndTime()));
			jo.addProperty("publisherName", activity.getPublisherName());
			jo.addProperty("publishTime", sdf.format(activity.getPublishTime()));
		}else{
			jo.addProperty("msg", false);
		}
		return jo.toString();
	}
	
	@RequestMapping(value = "/student/deleteActivity")
	@ResponseBody
	public String deleteActivity(HttpServletRequest request){
		String activityId = request.getParameter("activityId");
		JsonObject jo = new JsonObject();
		boolean result = itsActivityService.deleteActivity(Long.valueOf(activityId));
		jo.addProperty("msg", result);
		return jo.toString();
	}
	
	@RequestMapping(value = "/admin/updateActivity")
	@ResponseBody
	public String updateActivity(HttpServletRequest request){
		String activityId = request.getParameter("activityId");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String responsorNo = request.getParameter("responsorNo");
		String responsorName = request.getParameter("responsorName");
		String type = request.getParameter("type");
		String starTime = request.getParameter("starTime");
		String endTime = request.getParameter("endTime");
		
		Date publishTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		                                                                                                                        
		String studentNo = request.getSession().getAttribute("studentNo").toString();
		String studentName = itsStudentService.findStudentByStudentNo(studentNo).getName();
		
		JsonObject jo = new JsonObject();
		boolean result = false;
		Activity activity = itsActivityService.findActivityByActivityId(Long.valueOf(activityId));
		if(null != activity){
			activity.setActivityId(Long.valueOf(activityId));
			activity.setName(name);
			activity.setDescription(description);
			activity.setResponsorNo(responsorNo);
			activity.setResponsorName(responsorName);
			activity.setType(Integer.valueOf(type));
			try {
				activity.setStarTime(sdf.parse(starTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				activity.setEndTime(sdf.parse(endTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			activity.setPublisherNo(studentNo);
			activity.setPublisherName(studentName);
			activity.setPublishTime(publishTime);
			result = itsActivityService.updateActivity(activity);
		}
		jo.addProperty("msg", result);
		return jo.toString();
	}
	/**
	 * 学生申请参加活动
	 */
	@RequestMapping(value = "/admin/applyParticateActivity")
	@ResponseBody
	public String applyParticateActivity(HttpServletRequest request){
		//String activityId = request.getParameter("activityId");
		Long participationId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.Participation_OBJECTTYPE);
		
		String studentNo = (String) request.getSession().getAttribute("studentNo");
		String activityId = request.getParameter("activityId");
		
		Date currentTime = new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		JsonObject jo = new JsonObject();
		Participation participation = new Participation();
		participation.setParticipationId(participationId);
		participation.setUserNo(studentNo);
		participation.setActivityId(Long.valueOf(activityId));
		participation.setParticipateType(Participation.Participate_Type_Active);
		participation.setTime(currentTime);
		participation.setState(Participation.Participate_State_Apply);
		
		boolean result = itsParticipationService.addParticipation(participation);
		jo.addProperty("msg", result);
		return jo.toString();
	}
	
	/**
	 *批量指定学生参加活动
	 */
	@RequestMapping(value = "/admin/appointParticipation")
	@ResponseBody
	public String appointParticipation(HttpServletRequest request){
		String activityId = request.getParameter("activityId");
		String[] studentParticipants = request.getParameterValues("studentParticipants[]");
		
		boolean result = true; 
		
		//String studentNo = (String) request.getSession().getAttribute("studentNo");
		
		Date currentTime = new Date();
		
		JsonObject jo = new JsonObject();
		for(String studentNo:studentParticipants){
			Long participationId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.Participation_OBJECTTYPE);
			Participation participation = new Participation();
			participation.setParticipationId(participationId);
			participation.setUserNo(studentNo);
			participation.setActivityId(Long.valueOf(activityId));
			participation.setParticipateType(Participation.Participate_Type_Appointed);
			participation.setTime(currentTime);
			participation.setState(Participation.Participate_State_ExaminePass);
			boolean result0 = itsParticipationService.addParticipation(participation);
			if(!result0){
				result = false;
			}
		}
		jo.addProperty("msg", result);
		return jo.toString();	
	}
	
	@RequestMapping(value = "/student/myParticipatedActivityList")
	public ModelAndView myParticipatedActivityList(HttpServletRequest request) {
		String page = request.getParameter("page");
		String keyWordSearch = request.getParameter("keyWordSearch");
		String startTimeToSearchPublishTime = request.getParameter("startTimeToSearchPublishTime");
		String endTimeToSearchPublishTime = request.getParameter("endTimeToSearchPublishTime");
		
		String participatorUserNo = request.getSession().getAttribute("studentNo").toString();
		if (page == null || page.trim().equals("")) {
			page = "1";
		} else {
			page = page.trim();
		}

		if (keyWordSearch != null && !keyWordSearch.trim().equals(""))
			keyWordSearch = keyWordSearch.trim();

		JsonObject result = new JsonObject();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		
		ModelMap model = new ModelMap();
		List<Map<String,Object>> activityList = new ArrayList<Map<String,Object>>();
		Page<Activity> ActivityPage = itsActivityService.getUserParticipatedActivitysByPageQuery(keyWordSearch, startTimeToSearchPublishTime, endTimeToSearchPublishTime,participatorUserNo, Integer.valueOf(page), pageSize);

		result.addProperty("total", ActivityPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (ActivityPage != null) {

			List<Activity> regionList = ActivityPage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDateTime = new Date();
			
			for (Activity activity : regionList) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("activityId", activity.getActivityId());
				map.put("type", activity.getType());
				map.put("name", activity.getName());
				map.put("description", activity.getDescription());
				map.put("responsorName", activity.getResponsorName());
				map.put("starTime", sdf.format(activity.getStarTime()));
				map.put("endTime", sdf.format(activity.getEndTime()));
				if(activity.getEnrollEndTime().before(currentDateTime)){
					map.put("enrollFlag", true);
				}else{
					map.put("enrollFlag", false);
				}
				map.put("publishTime", sdf.format(activity.getPublishTime()));
				Participation participation = itsParticipationService.findParticipationUserNoAndActivityId(participatorUserNo, activity.getActivityId());
				map.put("participationTime", sdf.format(participation.getTime()));
				
				activityList.add(map);
				
			}

		}
		model.addAttribute("activityList", activityList);
		//model.addAttribute("totalPage", blogPage.getTotalPages());
		model.addAttribute("totalPage", 0);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/activity/my_participated_activityList", "activityInfo", model);
	}
	
	@RequestMapping(value = "/student/serachMyParticipatedActivityList")
	public @ResponseBody
	String serachMyParticipatedActivityList(HttpServletRequest request) {
		String page = request.getParameter("page");
		String keyWordSearch = request.getParameter("keyWordSearch");
		String startTimeToSearchPublishTime = request.getParameter("startTimeToSearchPublishTime");
		String endTimeToSearchPublishTime = request.getParameter("endTimeToSearchPublishTime");
		
		String participatorUserNo = request.getSession().getAttribute("studentNo").toString();
		if (page == null || page.trim().equals("")) {
			page = "1";
		} else {
			page = page.trim();
		}

		if (keyWordSearch != null && !keyWordSearch.trim().equals(""))
			keyWordSearch = keyWordSearch.trim();

		int pageSize = GetPageSize.PAGESIZE_LIST();
		
		JsonObject result = new JsonObject();
		JsonArray ja = new JsonArray();
		Page<Activity> ActivityPage = itsActivityService.getUserParticipatedActivitysByPageQuery(keyWordSearch, startTimeToSearchPublishTime, endTimeToSearchPublishTime,participatorUserNo, Integer.valueOf(page), pageSize);

		int totalCount = ActivityPage.getTotalCount();
		int totalPage = ((totalCount % pageSize != 0) ? (totalCount / pageSize + 1) : (totalCount / pageSize));
		totalPage = totalCount == 0 ? 1 : totalPage;
		result.addProperty("totalPage", totalPage);
		result.addProperty("currentPage", Integer.valueOf(page));
		result.addProperty("pageSize", pageSize);

		JsonArray array = new JsonArray();

		if (ActivityPage != null) {

			List<Activity> regionList = ActivityPage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currentDateTime = new Date();
			
			for (Activity activity : regionList) {
				JsonObject jo = new JsonObject();
				jo.addProperty("activityId", activity.getActivityId());
				jo.addProperty("type", activity.getType());
				jo.addProperty("name", activity.getName());
				jo.addProperty("description", activity.getDescription());
				jo.addProperty("responsorName", activity.getResponsorName());
				jo.addProperty("starTime", sdf.format(activity.getStarTime()));
				jo.addProperty("endTime", sdf.format(activity.getEndTime()));
				if(activity.getEnrollEndTime().before(currentDateTime)){
					jo.addProperty("enrollFlag", true);
				}else{
					jo.addProperty("enrollFlag", false);
				}
				jo.addProperty("publishTime", sdf.format(activity.getPublishTime()));
				ja.add(jo);
				
			}

		}
		result.add("record", ja);
		return result.toString();
	}
}

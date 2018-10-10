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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.classaffairs.common.GetPageSize;
import com.classaffairs.entity.Notice;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.NoticeService;
import com.classaffairs.service.StudentService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class NoticeAction {
	@Resource 
	private NoticeService itsNoticeService;
	@Autowired
	private StudentService itsStudentService;
	/**
	 * 分页获取所有班级通知信息
	 */
	@RequestMapping(value = "/student/noticeList")
	public ModelAndView getNotice(HttpServletRequest request) {
		String page = request.getParameter("page");

		String keyWordSearch = request.getParameter("keyWordSearch");
		String publisherNo = request.getParameter("publisherNo");
		String publisherName = request.getParameter("publisherName");
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
		List<Map<String,Object>> noticeList = new ArrayList<Map<String,Object>>();
		Page<Notice> NoticePage = itsNoticeService.getNoticesByPageQuery(keyWordSearch, publisherNo, publisherName, startTimeToSearchPublishTime, endTimeToSearchPublishTime, executiveClassToSearch,Integer.valueOf(page), pageSize);

		result.addProperty("total", NoticePage.getTotalCount());

		JsonArray array = new JsonArray();

		if (NoticePage != null) {

			List<Notice> regionList = NoticePage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (Notice notice : regionList) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("noticeId", notice.getNoticeId());
				map.put("type", notice.getType());
				map.put("receivingUnitNo", notice.getReceivingUnitNo());
				map.put("title", notice.getTitle());
				map.put("content", notice.getContent());
				map.put("path", notice.getPath());
				map.put("enclosureFile", notice.getEnclosureFile());
				map.put("publisher", notice.getPublisherName());
				map.put("publishTime", sdf.format(notice.getPublishTime()));
				noticeList.add(map);
				
			}

		}
		model.addAttribute("noticeList", noticeList);
		//model.addAttribute("totalPage", blogPage.getTotalPages());
		model.addAttribute("totalPage", 0);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/notice/notice_list", "noticeInfo", model);
	}
	@RequestMapping(value = "/student/searchNoticeList")
	public @ResponseBody
	String searchNoticeList(HttpServletRequest request) {
		String page = request.getParameter("page");

		String keyWordSearch = request.getParameter("keyWordSearch");
		String publisherNo = request.getParameter("publisherNo");
		String publisherName = request.getParameter("publisherName");
		String startTimeToSearchPublishTime = request.getParameter("startTimeToSearchPublishTime");
		String endTimeToSearchPublishTime = request.getParameter("endTimeToSearchPublishTime");
		if (page == null || page.trim().equals("")) {

			page = "1";
		} else {
			page = page.trim();
		}

		if (keyWordSearch != null && !keyWordSearch.trim().equals(""))
			keyWordSearch = keyWordSearch.trim();

		int pageSize = GetPageSize.PAGESIZE_LIST();
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		String executiveClassToSearch = currentStudent.getExecutiveClassName();
		JsonObject result = new JsonObject();
		
		JsonArray ja = new JsonArray();
		Page<Notice> NoticePage = itsNoticeService.getNoticesByPageQuery(keyWordSearch, publisherNo, publisherName, startTimeToSearchPublishTime, endTimeToSearchPublishTime, executiveClassToSearch,Integer.valueOf(page), pageSize);
		int totalCount = NoticePage.getTotalCount();
		int totalPage = ((totalCount % pageSize != 0) ? (totalCount / pageSize + 1) : (totalCount / pageSize));
		totalPage = totalCount == 0 ? 1 : totalPage;
		result.addProperty("totalPage", totalPage);
		result.addProperty("currentPage", Integer.valueOf(page));
		result.addProperty("pageSize", pageSize);
		
		JsonArray array = new JsonArray();

		if (NoticePage != null) {
			List<Notice> regionList = NoticePage.getResult();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (Notice notice : regionList) {
				JsonObject jo = new JsonObject();
				jo.addProperty("noticeId", notice.getNoticeId());
				jo.addProperty("type", notice.getType());
				jo.addProperty("receivingUnitNo", notice.getReceivingUnitNo());
				jo.addProperty("title", notice.getTitle());
				jo.addProperty("content", notice.getContent());
				jo.addProperty("path", notice.getPath());
				jo.addProperty("enclosureFile", notice.getEnclosureFile());
				jo.addProperty("publisher", notice.getPublisherName());
				jo.addProperty("publishTime", sdf.format(notice.getPublishTime()));
				ja.add(jo);
			}
		}
		result.add("record", ja);
		return result.toString();
	}
	/**
	 * 新增班级通知
	 */
	@RequestMapping(value = "/admin/addNotice")
	@ResponseBody
	public String addNotice(Notice notice, HttpServletRequest request) {

		JsonObject result = new JsonObject();
//		Long noticeId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.GRADE_OBJECTTYPE);
//
//		notice.setNoticeId(noticeId);
		//notice.setNoticeId(noticeNo);
		//notice.setEnrollDate(enrollDate);
        
		notice.setPublishTime(new Date());
		notice.setPublisherNo((String)request.getSession().getAttribute("studentNo"));
		notice.setPublisherName((String)request.getSession().getAttribute("studentName"));
		boolean success = itsNoticeService.addNotice(notice);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 通过应用id获取班级通知
	 */
	@RequestMapping(value = "/admin/getNoticeById")
	@ResponseBody
	public String getNoticeById(String id) {

		Long noticeId = Long.valueOf(id.trim());

		Notice notice = itsNoticeService.findNoticeByNoticeId(noticeId);

		Notice app = new Notice();

		try {
			BeanUtils.copyProperties(app, notice);
		} catch (Exception e) {
			Log.log.error("应用bean转换异常", e);
			e.printStackTrace();
		}

		Gson gson = new Gson();

		return gson.toJson(app);
	}

	/**
	 * 更新班级通知
	 */
	@RequestMapping(value = "/admin/updateNotice")
	@ResponseBody
	public String updateNotice(Notice notice) {

		JsonObject result = new JsonObject();

		boolean success = itsNoticeService.updateNotice(notice);

		result.addProperty("success", success);

		return result.toString();
	}
	/**
	 * 根据Id获取班级通知信息
	 */
	@RequestMapping(value = "/admin/getNoticeByNoticeId")
	@ResponseBody
	public String getNoticeByNoticeId(String noticeId) {

		JsonObject jo = new JsonObject();

		Notice notice = itsNoticeService.findNoticeByNoticeId(Long.valueOf(noticeId));
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != notice){
			jo.addProperty("msg", true);
			jo.addProperty("title", notice.getTitle());
			jo.addProperty("content", notice.getContent());
			jo.addProperty("enclosureFile", notice.getEnclosureFile());
			jo.addProperty("path", notice.getPath());
			jo.addProperty("publisherName", notice.getPublisherName());
			jo.addProperty("publishTime", sdf.format(notice.getPublishTime()));
		}else{
			jo.addProperty("msg", false);
		}
		return jo.toString();
	}
	/**
	 * 删除班级通知
	 */
	@RequestMapping(value = "/admin/deleteNoticeById")
	@ResponseBody
	public String deleteNoticeById(String noticeId) {

		JsonObject result = new JsonObject();

		Long noticeIdd = Long.valueOf(noticeId.trim());

		boolean success = itsNoticeService.deleteNotice(noticeIdd);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 分页获取所有班级通知信息
	 */
	@RequestMapping(value = "/admin/myPublishedNoticeList")
	public ModelAndView getMyPublishedNoticeList(HttpServletRequest request) {
		String page = request.getParameter("page");

		String keyWordSearch = request.getParameter("keyWordSearch");
		//String publisherNo = request.getParameter("publisherNo");
		String publisherNo = request.getSession().getAttribute("studentNo").toString();
		String publisherName = request.getParameter("publisherName");
		String startTimeToSearchPublishTime = request.getParameter("startTimeToSearchPublishTime");
		String endTimeToSearchPublishTime = request.getParameter("endTimeToSearchPublishTime");
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
		List<Map<String,Object>> noticeList = new ArrayList<Map<String,Object>>();
		Page<Notice> NoticePage = itsNoticeService.getNoticesByPageQuery(keyWordSearch, publisherNo, publisherName,startTimeToSearchPublishTime, endTimeToSearchPublishTime, null,Integer.valueOf(page), pageSize);

		result.addProperty("total", NoticePage.getTotalCount());

		JsonArray array = new JsonArray();

		if (NoticePage != null) {

			List<Notice> regionList = NoticePage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (Notice notice : regionList) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("noticeId", notice.getNoticeId());
				map.put("type", notice.getType());
				map.put("receivingUnitNo", notice.getReceivingUnitNo());
				map.put("title", notice.getTitle());
				map.put("content", notice.getContent());
				map.put("path", notice.getPath());
				map.put("enclosureFile", notice.getEnclosureFile());
				map.put("publisher", notice.getPublisherNo());
				map.put("publishTime", sdf.format(notice.getPublishTime()));
				noticeList.add(map);
				
			}

		}
		model.addAttribute("noticeList", noticeList);
		//model.addAttribute("totalPage", blogPage.getTotalPages());
		model.addAttribute("totalPage", 0);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/notice/notice_list_manage", "noticeInfo", model);
	}
	@RequestMapping(value = "/admin/searchMyPublishedNoticeList")
	public @ResponseBody
	String searchMyPublishedNoticeList(HttpServletRequest request) {
		String page = request.getParameter("page");

		String keyWordSearch = request.getParameter("keyWordSearch");
		//String publisherNo = request.getParameter("publisherNo");
		String publisherNo = request.getSession().getAttribute("studentNo").toString();
		String publisherName = request.getParameter("publisherName");
		String startTimeToSearchPublishTime = request.getParameter("startTimeToSearchPublishTime");
		String endTimeToSearchPublishTime = request.getParameter("endTimeToSearchPublishTime");
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
		Page<Notice> NoticePage = itsNoticeService.getNoticesByPageQuery(keyWordSearch, publisherNo, publisherName,startTimeToSearchPublishTime, endTimeToSearchPublishTime, null,Integer.valueOf(page), pageSize);
		int totalCount = NoticePage.getTotalCount();
		int totalPage = ((totalCount % pageSize != 0) ? (totalCount / pageSize + 1) : (totalCount / pageSize));
		totalPage = totalCount == 0 ? 1 : totalPage;
		result.addProperty("totalPage", totalPage);
		result.addProperty("currentPage", Integer.valueOf(page));
		result.addProperty("pageSize", pageSize);

		JsonArray array = new JsonArray();

		if (NoticePage != null) {

			List<Notice> regionList = NoticePage.getResult();
			
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (Notice notice : regionList) {
				JsonObject jo = new JsonObject();
				jo.addProperty("noticeId", notice.getNoticeId());
				jo.addProperty("type", notice.getType());
				jo.addProperty("receivingUnitNo", notice.getReceivingUnitNo());
				jo.addProperty("title", notice.getTitle());
				jo.addProperty("content", notice.getContent());
				jo.addProperty("path", notice.getPath());
				jo.addProperty("enclosureFile", notice.getEnclosureFile());
				jo.addProperty("publisher", notice.getPublisherName());
				jo.addProperty("publishTime", sdf.format(notice.getPublishTime()));
				ja.add(jo);
			}

		}
		result.add("record", ja);
		return result.toString();
	}
}

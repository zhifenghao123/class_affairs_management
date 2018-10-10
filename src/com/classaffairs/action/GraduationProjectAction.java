/**
 * 
 */
package com.classaffairs.action;

import java.io.IOException;
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
import com.classaffairs.common.md5.MD5Data;
import com.classaffairs.entity.Major;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.GraduationProject;
import com.classaffairs.entity.School;
import com.classaffairs.entity.GraduationProject;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.core.utils.ExcelResolveUtil;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.GraduationProjectService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class GraduationProjectAction {
	@Autowired
	private GraduationProjectService itsGraduationProjectService;
	
	@RequestMapping(value = "/admin/getGraduationProjectList")
	@ResponseBody
	public String getGraduationProjectList(HttpServletRequest request){
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		String graduationProjectGroup = request.getParameter("graduationProjectGroup");
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");
		
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
		
		if(studentNoToSearch == null || studentNoToSearch.equals("")){
			studentNoToSearch = "";
		}else{
			studentNoToSearch = studentNoToSearch.trim();
		}
		if(studentNameToSearch == null || studentNameToSearch.equals("")){
			studentNameToSearch = "";
		}else{
			studentNameToSearch = studentNameToSearch.trim();
		}
		
		JsonObject result = new JsonObject();
		Page<GraduationProject> graduationProjectPage = itsGraduationProjectService.getGraduationProjectsByPageQuery(graduationProjectGroup, studentNoToSearch, studentNameToSearch, page, rows);
		result.addProperty("total", graduationProjectPage.getTotalCount());
		JsonArray ja = new JsonArray();
		if(graduationProjectPage != null){
			List<GraduationProject> graduationProjectList = graduationProjectPage.getResult();
			for(GraduationProject graduationProject:graduationProjectList){
				JsonObject jo = new JsonObject();
				jo.addProperty("graduationProjectId", graduationProject.getGraduationProjectId());
				jo.addProperty("graduationProjectGroup", graduationProject.getGraduationProjectGroup());
				jo.addProperty("studentNo", graduationProject.getStudentNo());
				jo.addProperty("studentName", graduationProject.getStudentName());
				jo.addProperty("reseachTitle", graduationProject.getReseachTitle());
				jo.addProperty("topicSelectingReportScore", graduationProject.getTopicSelectingReportScore());
				jo.addProperty("guideTeacher", graduationProject.getGuideTeacher());
				jo.addProperty("memo", graduationProject.getMemo());
				ja.add(jo);
			}
			
		}
		result.add("rows", ja);
		return result.toString();
	}
	
	/**
	 * 批量导入学生信息
	 * @author Haozhifeng
	 * @param 
	 * @param graduationProjectBatchAddFile
	 * */
	@RequestMapping(value = "/admin/batchAddGraduationProject")
	@ResponseBody
	public String batchAddGraduationProject(HttpServletRequest request) {
		JsonObject result = new JsonObject();
		String uploadedFile = request.getParameter("graduationProjectBatchAddFile");
		String graduationProjectGroup = request.getParameter("graduationProjectGroup");
		List<List<String>> graduationProjectListsInfoOfBatchFile = new ArrayList<List<String>>();
		List<GraduationProject> graduationProjectList = new ArrayList<GraduationProject>();
		try {
			//String uploadedFileInServer = CommonPath.getWebappPath(uploadedFile);
			String uploadedFileInServer = CommonPath.getWebappPath("") + uploadedFile;
			graduationProjectListsInfoOfBatchFile =ExcelResolveUtil.readExcel(uploadedFileInServer);
			if(graduationProjectListsInfoOfBatchFile != null && graduationProjectListsInfoOfBatchFile.size() > 0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
				SimpleDateFormat sdfContinuesStr = new SimpleDateFormat("yyyyMM");
				for(int i = 0;i < graduationProjectListsInfoOfBatchFile.size();i++){
					List<String> graduationProjectInfo = graduationProjectListsInfoOfBatchFile.get(i);
					//for(int j = 0;j < graduationProjectInfo.size();j++){
						GraduationProject graduationProject = new GraduationProject();
						
						String schoolName = graduationProjectInfo.get(0).trim();
						String guideTeacherNo = graduationProjectInfo.get(1).trim();
						String guideTeacherName = graduationProjectInfo.get(2).trim();
						String studentNo = graduationProjectInfo.get(3).trim();
						String studentName = graduationProjectInfo.get(4).trim();
						String graduationProjectSex = graduationProjectInfo.get(5).trim();
						String majorNo = graduationProjectInfo.get(6).trim();
						String majorName = graduationProjectInfo.get(7).trim();
						String xuewei = graduationProjectInfo.get(8).trim();
						String quanzhiri = graduationProjectInfo.get(9).trim();
						String nianji = graduationProjectInfo.get(10).trim();
						String department = graduationProjectInfo.get(11).trim();
						String researchTitle = graduationProjectInfo.get(12).trim();
						
						
						Long graduationProjectId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.GraduationProject_OBJECTTYPE);
						
						graduationProject.setGraduationProjectId(graduationProjectId);
						graduationProject.setGraduationProjectGroup(graduationProjectGroup);
						graduationProject.setStudentNo(studentNo);
						graduationProject.setStudentName(studentName);
						graduationProject.setReseachTitle(researchTitle);
						graduationProject.setTopicSelectingReportScore(0.0f);
						graduationProject.setGuideTeacher(guideTeacherName);
						graduationProjectList.add(graduationProject);
					//}
				}
				boolean batchAddResult = itsGraduationProjectService.insertGraduationProjectsByBatch(graduationProjectList);
				result.addProperty("msg", batchAddResult);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}
	
	@RequestMapping(value = "/admin/getGraduationProjectById")
	@ResponseBody
	public String getGraduationProjectById(HttpServletRequest request){	
		
		String graduationProjectId = request.getParameter("graduationProjectId");
		JsonObject jo = new JsonObject();
		
		GraduationProject graduationProject = itsGraduationProjectService.findGraduationProjectByGraduationProjectId(Long.valueOf(graduationProjectId));
		if(null != graduationProject){
			jo.addProperty("exist", true);
			jo.addProperty("graduationProjectId", graduationProject.getGraduationProjectId());
			jo.addProperty("graduationProjectGroup", graduationProject.getGraduationProjectGroup());
			jo.addProperty("studentNo", graduationProject.getStudentNo());
			jo.addProperty("studentName", graduationProject.getStudentName());
			jo.addProperty("reseachTitle", graduationProject.getReseachTitle());
			jo.addProperty("topicSelectingReportScore", graduationProject.getTopicSelectingReportScore());
			jo.addProperty("guideTeacher", graduationProject.getGuideTeacher());
		}else{
			jo.addProperty("exist", false);
		}
		return jo.toString();
	}
	
	/**
	 * 新增学生
	 */
	@RequestMapping(value = "/admin/addGraduationProject")
	@ResponseBody
	public String addStudent(GraduationProject graduationProject) {
		JsonObject result = new JsonObject();

		Long studentId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.GraduationProject_OBJECTTYPE);
		graduationProject.setGraduationProjectId(studentId);


		boolean success = itsGraduationProjectService.addGraduationProject(graduationProject);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 更新学生
	 */
	@RequestMapping(value = "/admin/updateGraduationProject")
	@ResponseBody
	public String updateGraduationProject(GraduationProject graduationProject) {

		JsonObject result = new JsonObject();
		
		boolean success = itsGraduationProjectService.updateGraduationProject(graduationProject);

		result.addProperty("success", success);

		return result.toString();
	}
	
	@RequestMapping(value = "/student/graduationProjectList.action")
	public ModelAndView graduationProjectList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
		}
		
		//GraduationProject graduationProjectForCurrentStudent = itsGraduationProjectService.getGraduationProjectsByStudentNo(currentStudentNo);
		
		ModelMap model = new ModelMap();
		List<Map<String,Object>> graduationProjectList = new ArrayList<Map<String,Object>>();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		Page<GraduationProject> graduationProjectPage = itsGraduationProjectService.getGraduationProjectsByStudentNo(currentStudentNo, page, String.valueOf(pageSize));
		//result.addProperty("total", studentPage.getTotalCount());
		if(graduationProjectPage != null){
			List<GraduationProject> graduationProjectLists = graduationProjectPage.getResult();
			int seqNo = 0;
			for(GraduationProject graduationProject:graduationProjectPage){
				seqNo ++;
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("seqNo", (Integer.valueOf(page) - 1 ) * Integer.valueOf(pageSize) + seqNo);
				map.put("graduationProjectId", graduationProject.getGraduationProjectId());
				map.put("graduationProjectGroup", graduationProject.getGraduationProjectGroup());
				if(currentStudentNo.equals(graduationProject.getStudentNo())){
					map.put("studentNo", graduationProject.getStudentNo());
					map.put("studentName", graduationProject.getStudentName());
					map.put("reseachTitle", graduationProject.getReseachTitle());
				}else{
					map.put("studentNo", "***");
					map.put("studentName", "***");
					map.put("reseachTitle", "***");
				}
				
				
				map.put("topicSelectingReportScore", graduationProject.getTopicSelectingReportScore());
				map.put("guideTeacher", graduationProject.getGuideTeacher());
				map.put("memo", graduationProject.getMemo());

				graduationProjectList.add(map);
			}
			if(graduationProjectLists.size() < 1){
				model.addAttribute("graduationProjectGoup", "亲，你还没有学位毕业论文信息！");
			}else{
				model.addAttribute("graduationProjectGoup", graduationProjectLists.get(0).getGraduationProjectGroup());
			}
			
		}
		model.addAttribute("graduationProjectList", graduationProjectList);
		model.addAttribute("totalPage", graduationProjectPage.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/student/student_graduation_list", "graduationProjectInfo", model);
	}
	
	@RequestMapping(value = "/student/searchGraduationProjectList")
	@ResponseBody
	public String searchGraduationProjectList(HttpServletRequest request){
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		/*String graduationProjectGroup = request.getParameter("graduationProjectGroup");
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");*/
		
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		
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
		
	/*	if(studentNoToSearch == null || studentNoToSearch.equals("")){
			studentNoToSearch = "";
		}else{
			studentNoToSearch = studentNoToSearch.trim();
		}
		if(studentNameToSearch == null || studentNameToSearch.equals("")){
			studentNameToSearch = "";
		}else{
			studentNameToSearch = studentNameToSearch.trim();
		}*/
		
		//GraduationProject graduationProjectForCurrentStudent = itsGraduationProjectService.getGraduationProjectsByStudentNo(currentStudentNo);
		
		JsonObject result = new JsonObject();
		Page<GraduationProject> graduationProjectPage = itsGraduationProjectService.getGraduationProjectsByStudentNo(currentStudentNo, page, String.valueOf(rows));
		result.addProperty("total", graduationProjectPage.getTotalCount());
		JsonArray ja = new JsonArray();
		if(graduationProjectPage != null){
			List<GraduationProject> graduationProjectList = graduationProjectPage.getResult();
			for(GraduationProject graduationProject:graduationProjectList){
				JsonObject jo = new JsonObject();
				jo.addProperty("graduationProjectId", graduationProject.getGraduationProjectId());
				jo.addProperty("graduationProjectGroup", graduationProject.getGraduationProjectGroup());
				jo.addProperty("studentNo", graduationProject.getStudentNo());
				jo.addProperty("studentName", graduationProject.getStudentName());
				jo.addProperty("reseachTitle", graduationProject.getReseachTitle());
				jo.addProperty("topicSelectingReportScore", graduationProject.getTopicSelectingReportScore());
				jo.addProperty("guideTeacher", graduationProject.getGuideTeacher());
				jo.addProperty("memo", graduationProject.getMemo());
				ja.add(jo);
			}
			
		}
		result.add("rows", ja);
		return result.toString();
	}
	
}

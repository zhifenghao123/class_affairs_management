/**
 * 
 */
package com.classaffairs.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.classaffairs.common.CommonPath;
import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.common.GetIp;
import com.classaffairs.common.GetPageSize;
import com.classaffairs.common.md5.MD5Data;
import com.classaffairs.entity.ExecutiveClass;
import com.classaffairs.entity.Major;
import com.classaffairs.entity.OnlineBehavior;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.School;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.core.utils.ExcelResolveUtil;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ExecutiveClassService;
import com.classaffairs.service.MajorService;
import com.classaffairs.service.OnlineBehaviorService;
import com.classaffairs.service.SchoolService;
import com.classaffairs.service.StudentService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class StudentAction {
	@Autowired
	private StudentService itsStudentService;
	@Autowired
	private SchoolService itsSchoolService;
	@Autowired
	private MajorService itsMajorService;
	@Autowired
	private ExecutiveClassService itsExecutiveClassService;
	@Autowired
	private OnlineBehaviorService itsOnlineBehaviorService;
	
	@RequestMapping(value = "/admin/getStudentList")
	@ResponseBody
	public String getStudentList(HttpServletRequest request){
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");
		String gradeToSearch = request.getParameter("gradeToSearch");
		String schoolToSearch = request.getParameter("schoolToSearch");
		String majorToSearch = request.getParameter("majorToSearch");
		String executiveClassToSearch = request.getParameter("executiveClassToSearch");
		
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
		if(schoolToSearch == null || schoolToSearch.equals("")){
			schoolToSearch = "";
		}else{
			schoolToSearch = schoolToSearch.trim();
		}
		if(majorToSearch == null || majorToSearch.equals("")){
			majorToSearch = "";
		}else{
			majorToSearch = majorToSearch.trim();
		}
		if(executiveClassToSearch == null || executiveClassToSearch.equals("")){
			executiveClassToSearch = "";
		}else{
			executiveClassToSearch = executiveClassToSearch.trim();
		}
		
		JsonObject result = new JsonObject();
		Page<Student> studentPage = itsStudentService.getStudentsByPageQueryFromAdmin(studentNoToSearch, studentNameToSearch, gradeToSearch,schoolToSearch, majorToSearch, executiveClassToSearch, page, rows);
		result.addProperty("total", studentPage.getTotalCount());
		JsonArray ja = new JsonArray();
		if(studentPage != null){
			List<Student> studentList = studentPage.getResult();
			for(Student student:studentList){
				JsonObject jo = new JsonObject();
				jo.addProperty("studentId", student.getStudentId());
				jo.addProperty("studentNo", student.getStudentNo());
				jo.addProperty("name", student.getName());
				
				String sex = "";
				String schoolName = "";
				String majorName = "";
				String classExecutiveName = "";
				if(student.getSex() != null){
					if(1 == student.getSex()){
						sex = "男";
					}else if(2 == student.getSex()){
						sex = "女";
					}
				}
				if(student.getSchoolNo() != null && !("").equals(student.getSchoolNo())){
					School school = itsSchoolService.findSchoolBySchoolNo(student.getSchoolNo());
					schoolName = school.getName();
				}
				if(student.getMajorNo() != null && !("").equals(student.getMajorNo())){
					Major major = itsMajorService.findMajorByMajorNo(student.getMajorNo());
					if(null != major)
						majorName = major.getName();
				}
				
				jo.addProperty("sex", sex);
				jo.addProperty("gradeNo", student.getGradeNo()+"级");
				jo.addProperty("schoolName", schoolName);
				jo.addProperty("majorName", majorName);
				jo.addProperty("executiveClassName", student.getExecutiveClassName());
				if(student.getState() == Student.STATE_NORMAL){
					jo.addProperty("stateText", "在学");
				}else if(student.getState() == Student.STATE_ABNORMAL){
					jo.addProperty("stateText", "不正常");
				}
				
				ja.add(jo);
			}
			
		}
		result.add("rows", ja);
		return result.toString();
	}
	
	/**
	 * 新增学生
	 */
	@RequestMapping(value = "/admin/addStudent")
	@ResponseBody
	public String addStudent(Student student) {

		String studentNo = student.getStudentNo();

		studentNo = studentNo == null ? "" : studentNo.trim();

		JsonObject result = new JsonObject();

		Student studentExist = itsStudentService.findStudentByStudentNo(studentNo);

		if (studentExist != null) {
			result.addProperty("exsit", true);

			return result.toString();

		} else {

			result.addProperty("exsit", false);

		}
		Long studentId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.Student_OBJECTTYPE);
		String password = MD5Data.encryption(studentNo);
		student.setPassword(password);
		student.setStudentId(studentId);


		boolean success = itsStudentService.addStudent(student);

		result.addProperty("success", success);

		return result.toString();
	}
	
	/**
	 * 更新学生
	 */
	@RequestMapping(value = "/admin/updateStudent")
	@ResponseBody
	public String updateStudent(Student student) {

		Long studentId = student.getStudentId();
		String studentNo = student.getStudentNo();
		
		studentNo = studentNo == null ? "" : studentNo.trim();

		JsonObject result = new JsonObject();

		Student currentStudent = itsStudentService.findStudentByStudentId(studentId);
		if(currentStudent.getStudentNo().equals(studentNo)){//学号没有修改
			
		}else{
			Student studentExist = itsStudentService.findStudentByStudentNo(studentNo);
			if (studentExist != null) {
				result.addProperty("exsit", true);
				return result.toString();

			} else {
				result.addProperty("exsit", false);
			}
		}
		
		boolean success = itsStudentService.updateStudent(student);

		result.addProperty("success", success);

		return result.toString();
	}
	
	
	/**
	 * 批量导入学生信息
	 * @author Haozhifeng
	 * @param 
	 * @param studentBatchAddFile
	 * */
	@RequestMapping(value = "/admin/batchAddStudent")
	@ResponseBody
	public String batchAddStudent(HttpServletRequest request) {
		JsonObject result = new JsonObject();
		String uploadedFile = request.getParameter("studentBatchAddFile");
		List<List<String>> studentListsInfoOfBatchFile = new ArrayList<List<String>>();
		List<Student> studentList = new ArrayList<Student>();
		try {
			//String uploadedFileInServer = CommonPath.getWebappPath(uploadedFile);
			String uploadedFileInServer = CommonPath.getWebappPath("") + uploadedFile;
			studentListsInfoOfBatchFile =ExcelResolveUtil.readExcel(uploadedFileInServer);
			if(studentListsInfoOfBatchFile != null && studentListsInfoOfBatchFile.size() > 0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdfContinuesStr = new SimpleDateFormat("yyyyMM");
				for(int i = 0;i < studentListsInfoOfBatchFile.size();i++){
					List<String> studentInfo = studentListsInfoOfBatchFile.get(i);
					//for(int j = 0;j < studentInfo.size();j++){
						Student student = new Student();
						
						String infoSwqNo = studentInfo.get(0).trim();
						String studentNo = studentInfo.get(1).trim();
						String studentName = studentInfo.get(2).trim();
						String sex = studentInfo.get(3).trim();
						//String wechatNo = studentInfo.get(4).trim();
						String birthday = studentInfo.get(4).trim();
						String birthplace = studentInfo.get(5).trim();
						String homeAddress = studentInfo.get(6).trim();
						String ethnic = studentInfo.get(7).trim();
						String politicalLanscape = studentInfo.get(8).trim();
						String iDCardNo = studentInfo.get(9).trim();
						String accessType = studentInfo.get(10).trim();
						String graduationCollege = studentInfo.get(11).trim();
						String graduationDateStr = studentInfo.get(12).trim();
						String cet4Score = studentInfo.get(13).trim();
						String cet6Score = studentInfo.get(14).trim();
						String postgraduateEntranceDate = studentInfo.get(15).trim();
						String schoolNo = studentInfo.get(16).trim();
						String majorName = studentInfo.get(17).trim();
						String majorType = studentInfo.get(18).trim();
						String executiveClassName = studentInfo.get(19).trim();
						String tutorName = studentInfo.get(20).trim();
						String telephone1 = studentInfo.get(21).trim();
						String telephone2 = studentInfo.get(22).trim();
						String wechatNo = studentInfo.get(23).trim();
						
						Long studentId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.Student_OBJECTTYPE);
						String password = MD5Data.encryption(studentNo);
						
						student.setStudentId(studentId);
						student.setStudentNo(studentNo);
						student.setPassword(password);
						student.setName(studentName);
						if(sex.equals("男")){
							student.setSex(Student.SEX_MALE);
						}else if(sex.equals("女")){
							student.setSex(Student.SEX_FEMALE);
						}
						if(!("").equals(birthday)){
							try {
								student.setBirthday(sdf.parse(birthday));
							} catch (ParseException e) {
								System.out.println(studentNo + "_-->" + birthday);
								e.printStackTrace();
							}
						}
						student.setBirthplace(birthplace);
						student.setHomeAddress(homeAddress);
						//if(ethnic.contentEquals("族")){
							student.setEthnic(ethnic);
						//}else{
						//	student.setEthnic(ethnic + "族");
						//}
						
						//student.setPoliticalLandscape(politicalLanscape);
						switch(politicalLanscape){
							case "中国共产党党员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CCP);
								break;
							case "中共党员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CCP);
								break;
							case "中国共产党预备党员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_PROBATIONARY_CCP);
								break;
							case "中共预备党员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_PROBATIONARY_CCP);
								break;
							case "中国共产主义青年团团员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CCYL);
								break;
							case "共青团员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CCYL);
								break;
							case "中国国民党革命委员会会员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_RCCK);
								break;
							case "中国民主同盟盟员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CDA);
								break;
							case "中国民主建国会会员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CCYL);
								break;
							case "中国民主促进会会员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CDNCA);
								break;
							case "中国农工民主党党员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CAPD);
								break;
							case "中国致公党党员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CPWDP);
								break;
							case "九三学社社员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CZGD);
								break;
							case "台湾民主自治同盟盟员":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_JSS);
								break;
							case "无党派民主人士":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_TWDSGL);
								break;
							case "群众":
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_MASS);
								break;
							default:
								student.setPoliticalLandscape(Student.POLITICALLANDSCAPE_CCYL);
								break;
						}
						student.setIdCardNo(iDCardNo);
						/*if(isExeption.equals("")){
							student.setAccessType(Student.ACCESSTYPE_GENERALEXAM);
						}else if(isExeption.equals("推免生")){
							if(isCounselor.equals("")){
								student.setAccessType(Student.ACCESSTYPE_RECOMMEND);
							}else if(isCounselor.equals("辅导员")){
								student.setAccessType(Student.ACCESSTYPE_RECOMMEND_COUNSOLER);
							}
						}*/
						/*if(isExeption.equals("")){
							student.setAccessType(Student.ACCESSTYPE_GENERALEXAM);
						}else if(isExeption.equals("推免生")){
							if(isCounselor.equals("")){
								student.setAccessType(Student.ACCESSTYPE_RECOMMEND);
							}else if(isCounselor.equals("辅导员")){
								student.setAccessType(Student.ACCESSTYPE_RECOMMEND_COUNSOLER);
							}
						}*/
						switch(accessType){
							case "推免生":
								student.setAccessType(Student.ACCESSTYPE_RECOMMEND);
								break;
							case "推免考试":
								student.setAccessType(Student.ACCESSTYPE_RECOMMEND);
								break;
							case "推免“2+2”":
								student.setAccessType(Student.ACCESSTYPE_RECOMMEND_COUNSOLER);
								break;
							case "全国统考":
								student.setAccessType(Student.ACCESSTYPE_GENERALEXAM);
								break;
							default:
								student.setAccessType(Student.ACCESSTYPE_GENERALEXAM);
								break;
						}
						student.setUndergraduateCollege(graduationCollege);
						//student.setUndergraduateEnddate(graduationDate);
						/*if(!("").equals(graduationDate)){
							Calendar undergraduateEnddateCalendar = Calendar.getInstance();
							undergraduateEnddateCalendar.set(Integer.parseInt(graduationDate.substring(0, 1)), Integer.parseInt(graduationDate.substring(2, 3)), 1);
							student.setUndergraduateEnddate(undergraduateEnddateCalendar.getTime());
							}*/
						if(!("").equals(graduationDateStr)){
							Date graduationDate = null;
							try{
								graduationDate = sdfContinuesStr.parse(graduationDateStr);
								student.setUndergraduateEnddate(graduationDate);
							}catch(Exception e){
								System.out.println("本科毕业日期格式转换出现异常！");
							}
						}
						//student.setResearchDirection(researchDirection);
						student.setGradeNo(postgraduateEntranceDate);
						student.setSchoolNo(schoolNo);
						//student.setMajorNo(major);
						Major studentMajor = null;
						if(majorName.indexOf("（专业学位）") > 0){
							majorName = majorName.substring(0, majorName.indexOf("（"))+"（全日制专业学位）";
						}
						studentMajor = itsMajorService.findMajorByMajorName(majorName);
						//System.out.println(studentNo);
						student.setMajorNo(studentMajor.getMajorNo());
						if(majorType.equals("学术型硕士研究生") || majorType.equals("学术学位")){
							student.setCultivationType(Student.CULTIVATIONTYPE_ACADEMIC);
						}else if(majorType.equals("全日制工程硕士") || majorType.equals("专业学位")){
							student.setCultivationType(Student.CULTIVATIONTYPE_PROFESSIONAL);
						}
						student.setExecutiveClassName(executiveClassName);
						//student.setDepartmentNo(departmentNo);
						//student.setLaboratoryNo(laboratoryNo);
						student.setTutorName(tutorName);
						//student.setResearchDirection(researchDirection);
						student.setDegreeCourseScore(0.0f);
						student.setDegreeCourseRank(0);
						student.setAllCourseScore(0.0f);
						student.setAllCourseRank(0);
						if(null != cet4Score && !cet4Score.equals("")){
							student.setCet4Score(Integer.valueOf(cet4Score));
						}else{
							student.setCet4Score(0);
						}
						if(null != cet6Score && !cet6Score.equals("")){
							student.setCet6Score(Integer.valueOf(cet6Score));
						}else{
							student.setCet6Score(0);
						}
		                student.setState(Student.STATE_NORMAL);
						//student.setIsLaboratorLeader(isLaboratorLeader);
						//student.setiInstituteLeader(iInstituteLeader);
						//student.setIsMonitor(isMonitor);
						//student.setIsSchoolLeader(isSchoolLeader);
						student.setTelephoneNoFrequse(telephone1);
						student.setTelephoneNoFrequseIsPublic(Student.INFORMATION_AUTHORITY_SECRECY);
						student.setTelephoneNoBackup(telephone2);
						student.setTelephoneNoBackupIsPublic(Student.INFORMATION_AUTHORITY_SECRECY);
						//student.setEmaiNo(emaiNo);
						student.setEmaiNoIspublic(Student.INFORMATION_AUTHORITY_SECRECY);
						//student.setQqNo(qqNo);
						student.setQqNoIsPublic(Student.INFORMATION_AUTHORITY_SECRECY);
						student.setWechatNo(wechatNo);
						student.setWechatNoIsPublic(Student.INFORMATION_AUTHORITY_SECRECY);
						//student.setSinaweiboNo(sinaweiboNo);
						student.setSinaweiboNoIsPublic(Student.INFORMATION_AUTHORITY_SECRECY);
						//student.setApartmentNo(apartmentNo);
						//student.setRoomNo(roomNo);
						studentList.add(student);
					//}
				}
				boolean batchAddResult = itsStudentService.insertStudentsByBatch(studentList);
				result.addProperty("msg", batchAddResult);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}
	@RequestMapping(value = "/admin/getStudentById")
	@ResponseBody
	public String getStudentById(HttpServletRequest request){	
		
		String studentId = request.getParameter("studentId");
		JsonObject jo = new JsonObject();
		Student student = itsStudentService.findStudentByStudentId(Long.valueOf(studentId));
		if(null != student){
			jo.addProperty("exist", true);
			jo.addProperty("studentNo", student.getStudentNo());
			jo.addProperty("name", student.getName());
			jo.addProperty("sex", student.getSex());
			jo.addProperty("gradeNo", student.getGradeNo());
			jo.addProperty("schoolNo", student.getSchoolNo());
			jo.addProperty("majorNo", student.getMajorNo());
			jo.addProperty("executiveClassName", student.getExecutiveClassName());
			jo.addProperty("state", student.getState());
		}else{
			jo.addProperty("exist", false);
		}
		
		return jo.toString();
	}
	
	@RequestMapping(value = "/student/studentList.action")
	public ModelAndView studentList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		//String rows = request.getParameter("rows");
		
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");
		String sexToSearch = request.getParameter("sexToSearch");
		String birthplaceToSearch = request.getParameter("birthplaceToSearch");
		String politicalLandscapeToSearch = request.getParameter("politicalLandscapeToSearch");
		String accessTypeToSearch = request.getParameter("accessTypeToSearch");
		String majorNoToSearch = request.getParameter("majorNoToSearch");
		String cultivationTypeToSearch = request.getParameter("cultivationTypeToSearch");
		String executiveClassToSearch = null;
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
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
		if(sexToSearch == null || sexToSearch.equals("")){
			sexToSearch = "";
		}else{
			sexToSearch = sexToSearch.trim();
		}
		if(birthplaceToSearch == null || birthplaceToSearch.equals("")){
			birthplaceToSearch = "";
		}else{
			birthplaceToSearch = birthplaceToSearch.trim();
		}
		if(politicalLandscapeToSearch == null || politicalLandscapeToSearch.equals("")){
			politicalLandscapeToSearch = "";
		}else{
			politicalLandscapeToSearch = politicalLandscapeToSearch.trim();
		}
		if(accessTypeToSearch == null || accessTypeToSearch.equals("")){
			accessTypeToSearch = "";
		}else{
			accessTypeToSearch = accessTypeToSearch.trim();
		}
		if(majorNoToSearch == null || majorNoToSearch.equals("")){
			majorNoToSearch = "";
		}else{
			majorNoToSearch = majorNoToSearch.trim();
		}
		if(cultivationTypeToSearch == null || cultivationTypeToSearch.equals("")){
			cultivationTypeToSearch = "";
		}else{
			cultivationTypeToSearch = cultivationTypeToSearch.trim();
		}
		executiveClassToSearch = currentStudent.getExecutiveClassName();
		ModelMap model = new ModelMap();
		List<Map<String,Object>> studentList = new ArrayList<Map<String,Object>>();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		Page<Student> studentPage = itsStudentService.getStudentsByPageQueryFromStudent(studentNoToSearch, studentNameToSearch, sexToSearch, birthplaceToSearch, politicalLandscapeToSearch,accessTypeToSearch, majorNoToSearch, cultivationTypeToSearch,executiveClassToSearch, page, String.valueOf(pageSize));
		//result.addProperty("total", studentPage.getTotalCount());
		if(studentPage != null){
			//List<Student> studentList = studentPage.getResult();
			for(Student student:studentPage){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("studentId", student.getStudentId());
				map.put("studentNo", student.getStudentNo());
				map.put("name", student.getName());
				
				String sex = "";
				//String schoolName = "";
				String majorName = "";
				//String classExecutiveName = "";
				if(student.getSex() != null){
					if(1 == student.getSex()){
						sex = "男";
					}else if(2 == student.getSex()){
						sex = "女";
					}
				}
				/*if(student.getSchoolNo() != null && !("").equals(student.getSchoolNo())){
					School school = itsSchoolService.findSchoolBySchoolNo(student.getSchoolNo());
					schoolName = school.getName();
				}*/
				if(student.getMajorNo() != null && !("").equals(student.getMajorNo())){
					Major major = itsMajorService.findMajorByMajorNo(student.getMajorNo());
					if(null != major)
						majorName = major.getName();
				}
				
				map.put("sex", sex);
				//map.put("schoolName", schoolName);
				map.put("majorName", majorName);
				map.put("executiveClassName", student.getExecutiveClassName());
				if(student.getState() == Student.STATE_NORMAL){
					map.put("stateText", "在学");
				}else if(student.getState() == Student.STATE_ABNORMAL){
					map.put("stateText", "不正常");
				}
				if(student.getTelephoneNoFrequse() != null && !("").equals(student.getTelephoneNoFrequse())){
					if(student.getTelephoneNoFrequseIsPublic() != null && student.getTelephoneNoFrequseIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						map.put("telephone", student.getTelephoneNoFrequse());
					}else{
						map.put("telephone", "保密");
					}
				}else{
					map.put("telephone", "暂未填写");
				}
				if(student.getQqNo() != null && !("").equals(student.getQqNo())){
					if(student.getQqNoIsPublic() != null && student.getQqNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						map.put("qq", student.getQqNo());
					}else{
						map.put("qq", "保密");
					}
				}else{
					map.put("qq", "暂未填写");
				}
				if(student.getWechatNo() != null && !("").equals(student.getWechatNo())){
					if(student.getWechatNoIsPublic() != null && student.getWechatNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						map.put("wechat", student.getWechatNo());
					}else{
						map.put("wechat", "保密");
					}
				}else{
					map.put("wechat", "暂未填写");
				}
				if(student.getSinaweiboNo() != null && !("").equals(student.getSinaweiboNo())){
					if(student.getSinaweiboNoIsPublic() != null && student.getSinaweiboNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						map.put("sinaweibo", student.getSinaweiboNo());
					}else{
						map.put("sinaweibo", "保密");
					}
				}else{
					map.put("sinaweibo", "暂未填写");
				}
				studentList.add(map);
			}
			
		}
		model.addAttribute("studentList", studentList);
		model.addAttribute("totalPage", studentPage.getTotalPages());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/student/student_list", "studentInfo", model);
	}
	
	@RequestMapping(value = "/student/searchStudentList.action")
	public @ResponseBody
	String searchStudentList(HttpServletRequest request) {
		
		String page = request.getParameter("page");
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");
		String sexToSearch = request.getParameter("sexToSearch");
		String birthplaceToSearch = request.getParameter("birthplaceToSearch");
		String politicalLandscapeToSearch = request.getParameter("politicalLandscapeToSearch");
		String accessTypeToSearch = request.getParameter("accessTypeToSearch");
		String majorNoToSearch = request.getParameter("majorNoToSearch");
		String cultivationTypeToSearch = request.getParameter("cultivationTypeToSearch");
		String executiveClassToSearch = null;
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
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
		if(sexToSearch == null || sexToSearch.equals("")){
			sexToSearch = "";
		}else{
			sexToSearch = sexToSearch.trim();
		}
		if(birthplaceToSearch == null || birthplaceToSearch.equals("")){
			birthplaceToSearch = "";
		}else{
			birthplaceToSearch = birthplaceToSearch.trim();
		}
		if(politicalLandscapeToSearch == null || politicalLandscapeToSearch.equals("")){
			politicalLandscapeToSearch = "";
		}else{
			politicalLandscapeToSearch = politicalLandscapeToSearch.trim();
		}
		if(accessTypeToSearch == null || accessTypeToSearch.equals("")){
			accessTypeToSearch = "";
		}else{
			accessTypeToSearch = accessTypeToSearch.trim();
		}
		if(majorNoToSearch == null || majorNoToSearch.equals("")){
			majorNoToSearch = "";
		}else{
			majorNoToSearch = majorNoToSearch.trim();
		}
		if(cultivationTypeToSearch == null || cultivationTypeToSearch.equals("")){
			cultivationTypeToSearch = "";
		}else{
			cultivationTypeToSearch = cultivationTypeToSearch.trim();
		}
		executiveClassToSearch = currentStudent.getExecutiveClassName();
		
		
		int pageSize = GetPageSize.PAGESIZE_LIST();
		Page<Student> studentPage = itsStudentService.getStudentsByPageQueryFromStudent(studentNoToSearch, studentNameToSearch, sexToSearch, birthplaceToSearch,politicalLandscapeToSearch,  accessTypeToSearch, majorNoToSearch, cultivationTypeToSearch,executiveClassToSearch, page, String.valueOf(pageSize));
		JsonObject result = new JsonObject();
		int totalCount = studentPage.getTotalCount();
		int totalPage = ((totalCount % pageSize != 0) ? (totalCount / pageSize + 1) : (totalCount / pageSize));
		totalPage = totalCount == 0 ? 1 : totalPage;
		result.addProperty("totalPage", totalPage);
		result.addProperty("currentPage", Integer.valueOf(page));
		result.addProperty("pageSize", pageSize);
		JsonArray ja = new JsonArray();
		if (studentPage != null) {
			//List<Student> studentList = studentPage.getResult();
			for(Student student:studentPage){
				JsonObject jo = new JsonObject();
				jo.addProperty("studentId", student.getStudentId());
				jo.addProperty("studentNo", student.getStudentNo());
				jo.addProperty("name", student.getName());
				
				String sex = "";
				//String schoolName = "";
				String majorName = "";
				//String classExecutiveName = "";
				if(student.getSex() != null){
					if(1 == student.getSex()){
						sex = "男";
					}else if(2 == student.getSex()){
						sex = "女";
					}
				}
				/*if(student.getSchoolNo() != null && !("").equals(student.getSchoolNo())){
					School school = itsSchoolService.findSchoolBySchoolNo(student.getSchoolNo());
					schoolName = school.getName();
				}*/
				if(student.getMajorNo() != null && !("").equals(student.getMajorNo())){
					Major major = itsMajorService.findMajorByMajorNo(student.getMajorNo());
					if(null != major)
						majorName = major.getName();
				}
				
				jo.addProperty("sex", sex);
				//jo.addProperty("schoolName", schoolName);
				jo.addProperty("majorName", majorName);
				jo.addProperty("executiveClassName", student.getExecutiveClassName());
				if(student.getState() == Student.STATE_NORMAL){
					jo.addProperty("stateText", "在学");
				}else if(student.getState() == Student.STATE_ABNORMAL){
					jo.addProperty("stateText", "不正常");
				}
				if(student.getTelephoneNoFrequse() != null && !("").equals(student.getTelephoneNoFrequse())){
					if(student.getTelephoneNoFrequseIsPublic() != null && student.getTelephoneNoFrequseIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						jo.addProperty("telephone", student.getTelephoneNoFrequse());
					}else{
						jo.addProperty("telephone", "保密");
					}
				}else{
					jo.addProperty("telephone", "暂未填写");
				}
				if(student.getQqNo() != null && !("").equals(student.getQqNo())){
					if(student.getQqNoIsPublic() != null && student.getQqNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						jo.addProperty("qq", student.getQqNo());
					}else{
						jo.addProperty("qq", "保密");
					}
				}else{
					jo.addProperty("qq", "暂未填写");
				}
				if(student.getWechatNo() != null && !("").equals(student.getWechatNo())){
					if(student.getWechatNoIsPublic() != null && student.getWechatNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						jo.addProperty("wechat", student.getWechatNo());
					}else{
						jo.addProperty("wechat", "保密");
					}
				}else{
					jo.addProperty("wechat", "暂未填写");
				}
				if(student.getSinaweiboNo() != null && !("").equals(student.getSinaweiboNo())){
					if(student.getSinaweiboNoIsPublic() != null && student.getSinaweiboNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						jo.addProperty("sinaweibo", student.getSinaweiboNo());
					}else{
						jo.addProperty("sinaweibo", "保密");
					}
				}else{
					jo.addProperty("sinaweibo", "暂未填写");
				}
				ja.add(jo);
			}
			
		}

		result.add("record", ja);
		return result.toString();

	}
	
	@RequestMapping(value = "/student/exportSearchedStudentList.action")
	public void exportSearchedStudentList(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");
		String sexToSearch = request.getParameter("sexToSearch");
		String birthplaceToSearch = request.getParameter("birthplaceToSearch");
		String politicalLandscapeToSearch = request.getParameter("politicalLandscapeToSearch");
		String accessTypeToSearch = request.getParameter("accessTypeToSearch");
		String majorNoToSearch = request.getParameter("majorNoToSearch");
		String cultivationTypeToSearch = request.getParameter("cultivationTypeToSearch");
		String executiveClassToSearch = null;
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		
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
		if(sexToSearch == null || sexToSearch.equals("")){
			sexToSearch = "";
		}else{
			sexToSearch = sexToSearch.trim();
		}
		if(birthplaceToSearch == null || birthplaceToSearch.equals("")){
			birthplaceToSearch = "";
		}else{
			birthplaceToSearch = birthplaceToSearch.trim();
		}
		if(politicalLandscapeToSearch == null || politicalLandscapeToSearch.equals("")){
			politicalLandscapeToSearch = "";
		}else{
			politicalLandscapeToSearch = politicalLandscapeToSearch.trim();
		}
		if(accessTypeToSearch == null || accessTypeToSearch.equals("")){
			accessTypeToSearch = "";
		}else{
			accessTypeToSearch = accessTypeToSearch.trim();
		}
		if(majorNoToSearch == null || majorNoToSearch.equals("")){
			majorNoToSearch = "";
		}else{
			majorNoToSearch = majorNoToSearch.trim();
		}
		if(cultivationTypeToSearch == null || cultivationTypeToSearch.equals("")){
			cultivationTypeToSearch = "";
		}else{
			cultivationTypeToSearch = cultivationTypeToSearch.trim();
		}
		executiveClassToSearch = currentStudent.getExecutiveClassName();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		
		String[] excelHeader = {"序号", "学号", "姓名", "性别", "专业", "手机号", "QQ", "微信", "微博"};
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("学生");    
	    HSSFRow row = sheet.createRow((int) 0);    
	    HSSFCellStyle style = wb.createCellStyle();   
	    
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
	    for (int i = 0; i < excelHeader.length; i++) {    
            HSSFCell cell = row.createCell(i);    
            cell.setCellValue(excelHeader[i]);    
            cell.setCellStyle(style);    
            //sheet.autoSizeColumn(i);       
        } 
	    sheet.setColumnWidth(0, 8*256);
	    sheet.setColumnWidth(1, 15*256);
	    sheet.setColumnWidth(2, 10*256);
	    sheet.setColumnWidth(3, 8*256);
	    sheet.setColumnWidth(4, 30*256);
	    sheet.setColumnWidth(5, 15*256);
	    sheet.setColumnWidth(6, 15*256);
	    sheet.setColumnWidth(7, 15*256);
	    sheet.setColumnWidth(8, 15*256);
		List<Student> studentList = itsStudentService.getStudentsByQueryFromStudent(studentNoToSearch, studentNameToSearch, sexToSearch, birthplaceToSearch,politicalLandscapeToSearch,  accessTypeToSearch, majorNoToSearch, cultivationTypeToSearch, executiveClassToSearch);
		if (studentList != null) {
			for(int i=0;i < studentList.size();i++){
				row = sheet.createRow(i + 1);
				Student student = studentList.get(i);
				int sequenceNo = i + 1;
				String studentNo = student.getStudentNo();
				String name = student.getName();
				String sex = "";
				String majorName = "";
				String telephone = "";
				String qq = "";
				String wechat = "";
				String sinaweibo = "";
				if(student.getSex() != null){
					if(1 == student.getSex()){
						sex = "男";
					}else if(2 == student.getSex()){
						sex = "女";
					}
				}
				if(student.getMajorNo() != null && !("").equals(student.getMajorNo())){
					Major major = itsMajorService.findMajorByMajorNo(student.getMajorNo());
					if(null != major)
						majorName = major.getName();
				}
				
				if(student.getTelephoneNoFrequse() != null && !("").equals(student.getTelephoneNoFrequse())){
					if(student.getTelephoneNoFrequseIsPublic() != null && student.getTelephoneNoFrequseIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						telephone = student.getTelephoneNoFrequse();
					}else{
						telephone =  "保密";
					}
				}else{
					telephone = "暂未填写";
				}
				if(student.getQqNo() != null && !("").equals(student.getQqNo())){
					if(student.getQqNoIsPublic() != null && student.getQqNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						qq = student.getQqNo();
					}else{
						qq = "保密";
					}
				}else{
					qq = "暂未填写";
				}
				if(student.getWechatNo() != null && !("").equals(student.getWechatNo())){
					if(student.getWechatNoIsPublic() != null && student.getWechatNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						 wechat = student.getWechatNo();
					}else{
						 wechat = "保密";
					}
				}else{
					 wechat = "暂未填写";
				}
				if(student.getSinaweiboNo() != null && !("").equals(student.getSinaweiboNo())){
					if(student.getSinaweiboNoIsPublic() != null && student.getSinaweiboNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						 sinaweibo = student.getSinaweiboNo();
					}else{
						 sinaweibo = "保密";
					}
				}else{
					 sinaweibo = "暂未填写";
				}
				row.createCell(0).setCellValue(sequenceNo);    
	            row.createCell(1).setCellValue(studentNo);    
	            row.createCell(2).setCellValue(name); 
	            row.createCell(3).setCellValue(sex);    
	            row.createCell(4).setCellValue(majorName);    
	            row.createCell(5).setCellValue(telephone); 
	            row.createCell(6).setCellValue(qq); 
	            row.createCell(7).setCellValue(wechat);
	            row.createCell(8).setCellValue(sinaweibo);            
			}
		}
		response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition", "attachment;filename=Student.xls");  
        OutputStream ouputStream = response.getOutputStream();  
        wb.write(ouputStream);  
        ouputStream.flush();  
        ouputStream.close();  
	}
	
	@RequestMapping(value = "/student/getStudentByStudentNo")
	@ResponseBody
	public String getStudentByStudentNo(HttpServletRequest request){	
		
		String studentNo = request.getParameter("studentNo");
		String infoType = request.getParameter("infoType");
		JsonObject jo = new JsonObject();
		Student student = itsStudentService.findStudentByStudentNo(studentNo);
		if(null != student){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			jo.addProperty("exist", true);
			jo.addProperty("state", student.getState());
			jo.addProperty("name", student.getName());
			switch(infoType){
				case "1":
					//jo.addProperty("name", student.getName());
					if(student.getSex() != null){
						jo.addProperty("sexCode", student.getSex());
						if(1 == student.getSex()){
							//sex = "男";
							jo.addProperty("sexText", "男");
						}else if(2 == student.getSex()){
							//sex = "女";
							jo.addProperty("sexText", "女");
						}
					}
					if(null != student.getBirthday() && !("").equals(student.getBirthday())){
						jo.addProperty("birthday", sdf.format(student.getBirthday()));
					}else{
						jo.addProperty("birthday", "");
					}
					jo.addProperty("birthplace", student.getBirthplace());
					jo.addProperty("homeAddress", student.getHomeAddress());
					jo.addProperty("ethnic", student.getEthnic());
					//jo.addProperty("name", student.getPoliticalLandscape());
					//public static final int POLITICALLANDSCAPE_CPC = 1;//中国共产党党员
					//public static final int POLITICALLANDSCAPE_PROBATIONARY_CPC = 2;//中国共产党预备党员
					//public static final int POLITICALLANDSCAPE_CCYL = 3;//中国共产主义青年团团员
					//public static final int POLITICALLANDSCAPE_MASS = 13;//群众
					if(student.getPoliticalLandscape() != null){
						jo.addProperty("politicalLandscapeCode", student.getPoliticalLandscape());
						switch(student.getPoliticalLandscape()){
						case 1:
							jo.addProperty("politicalLandscapeText", "中共党员");
							break;
						case 2:
							jo.addProperty("politicalLandscapeText", "中共预备党员");
							break;
						case 3:
							jo.addProperty("politicalLandscapeText", "共青团员");
							break;
						case 13:
							jo.addProperty("politicalLandscapeText", "群众");
							break;
						default:
							jo.addProperty("politicalLandscapeText", "群众");
							break;
						}
					}
					jo.addProperty("IdCardNo", student.getIdCardNo());
					//jo.addProperty("name", student.getAccessType());
					//public static final int ACCESSTYPE_RECOMMEND = 1; //入学方式：推免
					//public static final int ACCESSTYPE_RECOMMEND_COUNSOLER = 2; //入学方式：推免"2+2"
					//public static final int ACCESSTYPE_GENERALEXAM = 3; //入学方式：统考
					if(null != student.getUndergraduateEnddate() && !("").equals(student.getUndergraduateEnddate())){
						jo.addProperty("undergraduateEnddate", sdf.format(student.getUndergraduateEnddate()));
					}else{
						jo.addProperty("undergraduateEnddate", "");
					}
					jo.addProperty("undergraduateCollege", student.getUndergraduateCollege());
					//jo.addProperty("undergraduateEnddate", sdf.format(student.getUndergraduateEnddate()));
					jo.addProperty("researchDirection", student.getResearchDirection());
					if(student.getAccessType() != null){
						jo.addProperty("accessTypeCode", student.getAccessType());
						switch(student.getAccessType()){
						case 1:
							jo.addProperty("accessTypeText", "推免");
							break;
						case 2:
							jo.addProperty("accessTypeText", "推免（2+2）");
							break;
						case 3:
							jo.addProperty("accessTypeText", "全国统考");
							break;
						default:
							jo.addProperty("accessTypeText", "全国统考");
							break;
						}
					}
					if(student.getCultivationType() != null){
						jo.addProperty("cultivationTypeCode", student.getCultivationType());
						switch(student.getCultivationType()){
						case 1:
							jo.addProperty("cultivationTypeText", "学术性研究生");
							break;
						case 2:
							jo.addProperty("cultivationTypeText", "专业性研究生");
							break;
						default:
							jo.addProperty("cultivationTypeText", "-------");
							break;
						}
					}
					
				break;
				case "2":
					//jo.addProperty("name", student.getSchoolNo());
					//jo.addProperty("name", student.getMajorNo());
					if(student.getSchoolNo() != null && !("").equals(student.getSchoolNo())){
						jo.addProperty("schoolNo", student.getSchoolNo());
						School school = itsSchoolService.findSchoolBySchoolNo(student.getSchoolNo());
						if(null != school)
							jo.addProperty("schoolName", school.getName());
						else
							jo.addProperty("schoolName", "-------");
					}
					jo.addProperty("gradeNo", student.getGradeNo());
					jo.addProperty("executiveClassName", student.getExecutiveClassName());
					if(student.getMajorNo() != null && !("").equals(student.getMajorNo())){
						jo.addProperty("majorNo", student.getMajorNo());
						Major major = itsMajorService.findMajorByMajorNo(student.getMajorNo());
						if(null != major)
							jo.addProperty("majorName", major.getName());
						else
							jo.addProperty("majorName", "-------");
					}
					//jo.addProperty("name", student.getCultivationType());
					//public static final int CULTIVATIONTYPE_ACADEMIC = 1;//培养类别：学术性研究生
					//public static final int CULTIVATIONTYPE_PROFESSIONAL = 2;//培养类别：专业性研究生
					
					
					jo.addProperty("departmentNo", student.getDepartmentNo());
					jo.addProperty("laboratoryNo", student.getLaboratoryNo());
					jo.addProperty("tutorName", student.getTutorName());
					//jo.addProperty("state", student.getState());
					jo.addProperty("isLaboratorLeader", student.getIsLaboratorLeader());
					jo.addProperty("iInstituteLeader", student.getIsDepartmentLeader());
					jo.addProperty("isMonitor", student.getIsMonitor());
					jo.addProperty("isSchoolLeader", student.getIsSchoolLeader());
					break;
				case "3":
					jo.addProperty("degreeCourseScore", student.getDegreeCourseScore());
					jo.addProperty("degreeCourseRank", student.getDegreeCourseRank());
					jo.addProperty("allCourseScore", student.getAllCourseScore());
					jo.addProperty("allCourseRank", student.getAllCourseRank());
					jo.addProperty("cet4Score", student.getCet4Score());
					jo.addProperty("cet6Score", student.getCet6Score());
					break;
				case "4":
					if(null != student.getApartmentNo())
						jo.addProperty("apartmentNo", student.getApartmentNo());
					else
						jo.addProperty("apartmentNo", "");
					if(null != student.getRoomNo())
						jo.addProperty("roomNo", student.getRoomNo());
					else
						jo.addProperty("roomNo", "");
					break;
				case "5":
					jo.addProperty("telephoneNoFrequse", student.getTelephoneNoFrequse());
					jo.addProperty("telephoneNoFrequseIsPublic", student.getTelephoneNoFrequseIsPublic());
					jo.addProperty("telephoneNoBackup", student.getTelephoneNoBackup());
					jo.addProperty("telephoneNoBackupIsPublic", student.getTelephoneNoBackupIsPublic());
					jo.addProperty("emaiNo", student.getEmaiNo());
					jo.addProperty("emaiNoIspublic", student.getEmaiNoIspublic());
					jo.addProperty("qqNo", student.getQqNo());
					jo.addProperty("qqNoIsPublic", student.getQqNoIsPublic());
					jo.addProperty("wechatNo", student.getWechatNo());
					jo.addProperty("wechatNoIsPublic", student.getWechatNoIsPublic());
					jo.addProperty("sinaweiboNo", student.getSinaweiboNo());
					jo.addProperty("sinaweiboNoIsPublic", student.getSinaweiboNoIsPublic());
					break;
				default:
					break;
			}
		}else{
			jo.addProperty("exist", false);
		}
		
		return jo.toString();
	}
	@RequestMapping(value = "/student/updateStudentInfo")
	@ResponseBody
	public String updateStudentInfo(HttpServletRequest request){	
		
		String studentNo = request.getParameter("studentNo");
		String infoType = request.getParameter("infoType");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String birthplace = request.getParameter("birthplace");
		String homeAddress = request.getParameter("homeAddress");
		String ethnic = request.getParameter("ethnic");
		String politicalLandscape = request.getParameter("politicalLandscape");
		String IdCardNo = request.getParameter("IdCardNo");
		String accessType = request.getParameter("accessType");
		String undergraduateCollege = request.getParameter("undergraduateCollege");
		String undergraduateEnddate = request.getParameter("undergraduateEnddate");
		String researchDirection = request.getParameter("researchDirection");
		String gradeNo = request.getParameter("gradeNo");
		String schoolNo = request.getParameter("schoolNo");
		String majorNo = request.getParameter("majorNo");
		String cultivationType = request.getParameter("cultivationType");
		String executiveClassName = request.getParameter("executiveClassName");
		String departmentNo = request.getParameter("departmentNo");
		String laboratoryNo = request.getParameter("laboratoryNo");
		String tutorName = request.getParameter("tutorName");
		String degreeCourseScore = request.getParameter("degreeCourseScore");
		String degreeCourseRank = request.getParameter("degreeCourseRank");
		String allCourseScore = request.getParameter("allCourseScore");
		String allCourseRank = request.getParameter("allCourseRank");
		String cet4Score = request.getParameter("cet4Score");
		String cet6Score = request.getParameter("cet6Score");
		
		/*String state = request.getParameter("state");
		String isLaboratorLeader = request.getParameter("isLaboratorLeader");
		String isDepartmentLeader = request.getParameter("isDepartmentLeader");
		String isMonitor = request.getParameter("isMonitor");
		String isSchoolLeader = request.getParameter("isSchoolLeader");*/
		String telephoneNoFrequse = request.getParameter("telephoneNoFrequse");
		String telephoneNoFrequseIsPublic = request.getParameter("telephoneNoFrequseIsPublic");
		String telephoneNoBackup = request.getParameter("telephoneNoBackup");
		String telephoneNoBackupIsPublic = request.getParameter("telephoneNoBackupIsPublic");
		String emaiNo = request.getParameter("emaiNo");
		String emaiNoIspublic = request.getParameter("emaiNoIspublic");
		String qqNo = request.getParameter("qqNo");
		String qqNoIsPublic = request.getParameter("qqNoIsPublic");
		String wechatNo = request.getParameter("wechatNo");
		String wechatNoIsPublic = request.getParameter("wechatNoIsPublic");
		String sinaweiboNo = request.getParameter("sinaweiboNo");
		String sinaweiboNoIsPublic = request.getParameter("sinaweiboNoIsPublic");
		String apartmentNo = request.getParameter("apartmentNo");
		String roomNo = request.getParameter("roomNo");
		
		//Long studentId = 0l;
		String onlineBehaviorName = "";
		
		JsonObject jo = new JsonObject();
		Student student = itsStudentService.findStudentByStudentNo(studentNo);
		if(null != student){
			//studentId = student.getStudentId();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			switch(infoType){
				case "1":
					student.setSex(Integer.valueOf(sex));
					try {
						student.setBirthday(sdf.parse(birthday));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					student.setBirthplace(birthplace);
					student.setHomeAddress(homeAddress);
					student.setEthnic(ethnic);
					student.setPoliticalLandscape(Integer.valueOf(politicalLandscape));
					student.setIdCardNo(IdCardNo);
					student.setUndergraduateCollege(undergraduateCollege);
					try {
						student.setUndergraduateEnddate(sdf.parse(undergraduateEnddate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					student.setCultivationType(Integer.valueOf(cultivationType));
					student.setAccessType(Integer.valueOf(accessType));
					//student.setResearchDirection(researchDirection);
					onlineBehaviorName = "修改基本信息";
				break;
				case "2":
					student.setSchoolNo(schoolNo);
					student.setGradeNo(gradeNo);
					student.setMajorNo(majorNo);
					student.setExecutiveClassName(executiveClassName);
					student.setDepartmentNo(departmentNo);
					student.setLaboratoryNo(laboratoryNo);
					student.setTutorName(tutorName);
					onlineBehaviorName = "修改学习信息";
					break;
				case "3":
					student.setDegreeCourseScore(Float.valueOf(degreeCourseScore));;
					student.setDegreeCourseRank(Integer.valueOf(degreeCourseRank));;
					student.setAllCourseScore(Float.valueOf(allCourseScore));;
					student.setAllCourseRank(Integer.valueOf(allCourseRank));;
					student.setCet4Score(Integer.valueOf(cet4Score));;
					student.setCet6Score(Integer.valueOf(cet6Score));;
					onlineBehaviorName = "修改成绩信息";
					break;
				case "4":
					student.setApartmentNo(apartmentNo);
					student.setRoomNo(roomNo);
					onlineBehaviorName = "修改生活信息";
					break;
				case "5":
					student.setTelephoneNoFrequse(telephoneNoFrequse);
					student.setTelephoneNoFrequseIsPublic(Integer.valueOf(telephoneNoFrequseIsPublic));
					student.setTelephoneNoBackup(telephoneNoBackup);
					student.setTelephoneNoBackupIsPublic(Integer.valueOf(telephoneNoBackupIsPublic));
					student.setEmaiNo(emaiNo);
					student.setEmaiNoIspublic(Integer.valueOf(emaiNoIspublic));
					student.setQqNo(qqNo);
					student.setQqNoIsPublic(Integer.valueOf(qqNoIsPublic));
					student.setWechatNo(wechatNo);
					student.setWechatNoIsPublic(Integer.valueOf(wechatNoIsPublic));
					student.setSinaweiboNo(sinaweiboNo);
					student.setSinaweiboNoIsPublic(Integer.valueOf(sinaweiboNoIsPublic));
					onlineBehaviorName = "修改社交信息";
					break;
				default:
					break;
			}
			boolean result = itsStudentService.updateStudent(student);
			jo.addProperty("msg", result);
			
			OnlineBehavior onlineBehavior = new OnlineBehavior();
			Long onlineBehaviorId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.OnlineBehavior_ObjectType);
			onlineBehavior.setOnlineBehaviorId(onlineBehaviorId);
			onlineBehavior.setUserNo(studentNo);
			onlineBehavior.setUserIp(GetIp.getRealIP(request));
			onlineBehavior.setOnlineBehaviorName(onlineBehaviorName);
			onlineBehavior.setType(OnlineBehavior.Type_Update);
			onlineBehavior.setState(OnlineBehavior.Delete_No);
			onlineBehavior.setRecordTime(new Date());
			itsOnlineBehaviorService.addOnlineBehavior(onlineBehavior);
		}else{
			jo.addProperty("msg", false);
		}
		
		return jo.toString();
	}
	
	@RequestMapping(value = "/student/getStudentSimpleInfoList.action")
	@ResponseBody
	public String getStudentSimpleInfoList(HttpServletRequest request) throws Exception {
		
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);

		String currentExecutiveClassToSearch = currentStudent.getExecutiveClassName();
		List<ExecutiveClass> sameGroupExecutiveClassList= itsExecutiveClassService.getAllSameGroupExecutiveClassListByOneExecutiveClassName(currentExecutiveClassToSearch);
		List<String> executiveClassNameList = new ArrayList<String>();
		for(ExecutiveClass executiveClass:sameGroupExecutiveClassList){
			executiveClassNameList.add(executiveClass.getName());
		}
		List<Student> studentList = itsStudentService.findStudentListByExecutiveClassNames(executiveClassNameList);
		
		JsonArray ja = new JsonArray();
		for(Student student:studentList){
			JsonObject jo = new JsonObject();
			jo.addProperty("studentNo", student.getStudentNo());
			jo.addProperty("name", student.getName());
			ja.add(jo);
		}
		return ja.toString();
	}
	@RequestMapping(value = "/student/updateStudentPassword.action")
	@ResponseBody
	public String updateStudentPassword(HttpServletRequest request) throws Exception {
		
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		String oldPassword = request.getParameter("oldPassword").toString();
		String newPassword = request.getParameter("newPassword").toString();
		
		JsonObject reuslt = new JsonObject();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		if(currentStudent.getPassword().equals(MD5Data.encryption(oldPassword))){
			//输入密码正确
			currentStudent.setPassword(MD5Data.encryption(newPassword));
			boolean updateResult = itsStudentService.updateStudent(currentStudent);
			if(updateResult){
				reuslt.addProperty("msg", "1");
				//Long studentId = (Long) request.getSession().getAttribute("studentId");
				OnlineBehavior onlineBehavior = new OnlineBehavior();
				Long onlineBehaviorId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.OnlineBehavior_ObjectType);
				onlineBehavior.setOnlineBehaviorId(onlineBehaviorId);
				onlineBehavior.setUserNo(currentStudentNo);
				onlineBehavior.setUserIp(GetIp.getRealIP(request));
				onlineBehavior.setOnlineBehaviorName("修改账户密码");
				onlineBehavior.setType(OnlineBehavior.Type_Update);
				onlineBehavior.setState(OnlineBehavior.Delete_No);
				onlineBehavior.setRecordTime(new Date());
				itsOnlineBehaviorService.addOnlineBehavior(onlineBehavior);
			}else{
				reuslt.addProperty("msg", "2");
			}
		}else{
			//输入密码错误
			reuslt.addProperty("msg", "3");
		}
		return reuslt.toString();
	}
	
	@RequestMapping(value = "/admin/resetStudentPassword.action")
	@ResponseBody
	public String resetStudentPassword(HttpServletRequest request) throws Exception {
		
		Long studentId = Long.valueOf(request.getParameter("studentId"));
		JsonObject reuslt = new JsonObject();
		Student currentStudent = itsStudentService.findStudentByStudentId(studentId);
		currentStudent.setPassword(MD5Data.encryption(currentStudent.getStudentNo()));
		boolean updateResult = itsStudentService.updateStudent(currentStudent);
		reuslt.addProperty("msg", updateResult);
		return reuslt.toString();
	}
	
	@RequestMapping(value = "/student/studentManageList.action")
	public ModelAndView studentManageList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");
		String sexToSearch = request.getParameter("sexToSearch");
		String birthplaceToSearch = request.getParameter("birthplaceToSearch");
		String politicalLandscapeToSearch = request.getParameter("politicalLandscapeToSearch");
		String accessTypeToSearch = request.getParameter("accessTypeToSearch");
		String majorNoToSearch = request.getParameter("majorNoToSearch");
		String cultivationTypeToSearch = request.getParameter("cultivationTypeToSearch");
		String executiveClassToSearch = null;
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
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
		if(sexToSearch == null || sexToSearch.equals("")){
			sexToSearch = "";
		}else{
			sexToSearch = sexToSearch.trim();
		}
		if(birthplaceToSearch == null || birthplaceToSearch.equals("")){
			birthplaceToSearch = "";
		}else{
			birthplaceToSearch = birthplaceToSearch.trim();
		}
		if(politicalLandscapeToSearch == null || politicalLandscapeToSearch.equals("")){
			politicalLandscapeToSearch = "";
		}else{
			politicalLandscapeToSearch = politicalLandscapeToSearch.trim();
		}
		if(accessTypeToSearch == null || accessTypeToSearch.equals("")){
			accessTypeToSearch = "";
		}else{
			accessTypeToSearch = accessTypeToSearch.trim();
		}
		if(majorNoToSearch == null || majorNoToSearch.equals("")){
			majorNoToSearch = "";
		}else{
			majorNoToSearch = majorNoToSearch.trim();
		}
		if(cultivationTypeToSearch == null || cultivationTypeToSearch.equals("")){
			cultivationTypeToSearch = "";
		}else{
			cultivationTypeToSearch = cultivationTypeToSearch.trim();
		}
		executiveClassToSearch = currentStudent.getExecutiveClassName();
		ModelMap model = new ModelMap();
		List<Map<String,Object>> studentList = new ArrayList<Map<String,Object>>();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		Page<Student> studentPage = itsStudentService.getStudentsByPageQueryFromStudent(studentNoToSearch, studentNameToSearch, sexToSearch, birthplaceToSearch,politicalLandscapeToSearch,  accessTypeToSearch, majorNoToSearch, cultivationTypeToSearch,executiveClassToSearch, page, String.valueOf(pageSize));
		//result.addProperty("total", studentPage.getTotalCount());
		if(studentPage != null){
			//List<Student> studentList = studentPage.getResult();
			for(Student student:studentPage){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("studentId", student.getStudentId());
				map.put("studentNo", student.getStudentNo());
				map.put("name", student.getName());
				
				String sex = "";
				//String schoolName = "";
				String majorName = "";
				//String classExecutiveName = "";
				if(student.getSex() != null){
					if(1 == student.getSex()){
						sex = "男";
					}else if(2 == student.getSex()){
						sex = "女";
					}
				}
				/*if(student.getSchoolNo() != null && !("").equals(student.getSchoolNo())){
					School school = itsSchoolService.findSchoolBySchoolNo(student.getSchoolNo());
					schoolName = school.getName();
				}*/
				if(student.getMajorNo() != null && !("").equals(student.getMajorNo())){
					Major major = itsMajorService.findMajorByMajorNo(student.getMajorNo());
					if(null != major)
						majorName = major.getName();
				}
				
				map.put("sex", sex);
				//map.put("schoolName", schoolName);
				map.put("majorName", majorName);
				map.put("executiveClassName", student.getExecutiveClassName());
				if(student.getState() == Student.STATE_NORMAL){
					map.put("stateText", "在学");
				}else if(student.getState() == Student.STATE_ABNORMAL){
					map.put("stateText", "不正常");
				}
				if(student.getTelephoneNoFrequse() != null && !("").equals(student.getTelephoneNoFrequse())){
					if(student.getTelephoneNoFrequseIsPublic() != null && student.getTelephoneNoFrequseIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						map.put("telephone", student.getTelephoneNoFrequse());
					}else{
						map.put("telephone", "保密");
					}
				}else{
					map.put("telephone", "暂未填写");
				}
				if(student.getQqNo() != null && !("").equals(student.getQqNo())){
					if(student.getQqNoIsPublic() != null && student.getQqNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						map.put("qq", student.getQqNo());
					}else{
						map.put("qq", "保密");
					}
				}else{
					map.put("qq", "暂未填写");
				}
				if(student.getWechatNo() != null && !("").equals(student.getWechatNo())){
					if(student.getWechatNoIsPublic() != null && student.getWechatNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						map.put("wechat", student.getWechatNo());
					}else{
						map.put("wechat", "保密");
					}
				}else{
					map.put("wechat", "暂未填写");
				}
				if(student.getSinaweiboNo() != null && !("").equals(student.getSinaweiboNo())){
					if(student.getSinaweiboNoIsPublic() != null && student.getSinaweiboNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						map.put("sinaweibo", student.getSinaweiboNo());
					}else{
						map.put("sinaweibo", "保密");
					}
				}else{
					map.put("sinaweibo", "暂未填写");
				}
				studentList.add(map);
			}
			
		}
		model.addAttribute("studentList", studentList);
		model.addAttribute("totalPage", studentPage.getTotalPages());
		model.addAttribute("totalCount", studentPage.getTotalCount());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/student/student_list_manage", "studentInfo", model);
	}
	
	@RequestMapping(value = "/student/searchStudentManageList.action")
	public @ResponseBody
	String searchStudentManageList(HttpServletRequest request) {
		String page = request.getParameter("page");
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");
		String sexToSearch = request.getParameter("sexToSearch");
		String birthplaceToSearch = request.getParameter("birthplaceToSearch");
		String politicalLandscapeToSearch = request.getParameter("politicalLandscapeToSearch");
		String accessTypeToSearch = request.getParameter("accessTypeToSearch");
		String majorNoToSearch = request.getParameter("majorNoToSearch");
		String cultivationTypeToSearch = request.getParameter("cultivationTypeToSearch");
		String executiveClassToSearch = null;
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
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
		if(sexToSearch == null || sexToSearch.equals("")){
			sexToSearch = "";
		}else{
			sexToSearch = sexToSearch.trim();
		}
		if(birthplaceToSearch == null || birthplaceToSearch.equals("")){
			birthplaceToSearch = "";
		}else{
			birthplaceToSearch = birthplaceToSearch.trim();
		}
		if(politicalLandscapeToSearch == null || politicalLandscapeToSearch.equals("")){
			politicalLandscapeToSearch = "";
		}else{
			politicalLandscapeToSearch = politicalLandscapeToSearch.trim();
		}
		if(accessTypeToSearch == null || accessTypeToSearch.equals("")){
			accessTypeToSearch = "";
		}else{
			accessTypeToSearch = accessTypeToSearch.trim();
		}
		if(majorNoToSearch == null || majorNoToSearch.equals("")){
			majorNoToSearch = "";
		}else{
			majorNoToSearch = majorNoToSearch.trim();
		}
		if(cultivationTypeToSearch == null || cultivationTypeToSearch.equals("")){
			cultivationTypeToSearch = "";
		}else{
			cultivationTypeToSearch = cultivationTypeToSearch.trim();
		}
		executiveClassToSearch = currentStudent.getExecutiveClassName();
		
		
		int pageSize = GetPageSize.PAGESIZE_LIST();
		Page<Student> studentPage = itsStudentService.getStudentsByPageQueryFromStudent(studentNoToSearch, studentNameToSearch, sexToSearch, birthplaceToSearch,politicalLandscapeToSearch, accessTypeToSearch, majorNoToSearch, cultivationTypeToSearch,executiveClassToSearch, page, String.valueOf(pageSize));
		JsonObject result = new JsonObject();
		int totalCount = studentPage.getTotalCount();
		int totalPage = ((totalCount % pageSize != 0) ? (totalCount / pageSize + 1) : (totalCount / pageSize));
		totalPage = totalCount == 0 ? 1 : totalPage;
		result.addProperty("totalPage", totalPage);
		result.addProperty("totalCount", totalCount);
		result.addProperty("currentPage", Integer.valueOf(page));
		result.addProperty("pageSize", pageSize);
		JsonArray ja = new JsonArray();
		if (studentPage != null) {
			//List<Student> studentList = studentPage.getResult();
			for(Student student:studentPage){
				JsonObject jo = new JsonObject();
				jo.addProperty("studentId", student.getStudentId());
				jo.addProperty("studentNo", student.getStudentNo());
				jo.addProperty("name", student.getName());
				
				String sex = "";
				//String schoolName = "";
				String majorName = "";
				//String classExecutiveName = "";
				if(student.getSex() != null){
					if(1 == student.getSex()){
						sex = "男";
					}else if(2 == student.getSex()){
						sex = "女";
					}
				}
				/*if(student.getSchoolNo() != null && !("").equals(student.getSchoolNo())){
					School school = itsSchoolService.findSchoolBySchoolNo(student.getSchoolNo());
					schoolName = school.getName();
				}*/
				if(student.getMajorNo() != null && !("").equals(student.getMajorNo())){
					Major major = itsMajorService.findMajorByMajorNo(student.getMajorNo());
					if(null != major)
						majorName = major.getName();
				}
				
				jo.addProperty("sex", sex);
				//jo.addProperty("schoolName", schoolName);
				jo.addProperty("majorName", majorName);
				jo.addProperty("executiveClassName", student.getExecutiveClassName());
				if(student.getState() == Student.STATE_NORMAL){
					jo.addProperty("stateText", "在学");
				}else if(student.getState() == Student.STATE_ABNORMAL){
					jo.addProperty("stateText", "不正常");
				}
				if(student.getTelephoneNoFrequse() != null && !("").equals(student.getTelephoneNoFrequse())){
					if(student.getTelephoneNoFrequseIsPublic() != null && student.getTelephoneNoFrequseIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						jo.addProperty("telephone", student.getTelephoneNoFrequse());
					}else{
						jo.addProperty("telephone", "保密");
					}
				}else{
					jo.addProperty("telephone", "暂未填写");
				}
				if(student.getQqNo() != null && !("").equals(student.getQqNo())){
					if(student.getQqNoIsPublic() != null && student.getQqNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						jo.addProperty("qq", student.getQqNo());
					}else{
						jo.addProperty("qq", "保密");
					}
				}else{
					jo.addProperty("qq", "暂未填写");
				}
				if(student.getWechatNo() != null && !("").equals(student.getWechatNo())){
					if(student.getWechatNoIsPublic() != null && student.getWechatNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						jo.addProperty("wechat", student.getWechatNo());
					}else{
						jo.addProperty("wechat", "保密");
					}
				}else{
					jo.addProperty("wechat", "暂未填写");
				}
				if(student.getSinaweiboNo() != null && !("").equals(student.getSinaweiboNo())){
					if(student.getSinaweiboNoIsPublic() != null && student.getSinaweiboNoIsPublic() != Student.INFORMATION_AUTHORITY_SECRECY){
						jo.addProperty("sinaweibo", student.getSinaweiboNo());
					}else{
						jo.addProperty("sinaweibo", "保密");
					}
				}else{
					jo.addProperty("sinaweibo", "暂未填写");
				}
				ja.add(jo);
			}
			
		}

		result.add("record", ja);
		return result.toString();

	}
	
	@RequestMapping(value = "/student/studentScoreList.action")
	public ModelAndView studentScoreList(HttpServletRequest request) throws Exception {
		String page = request.getParameter("page");
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");
		String sexToSearch = request.getParameter("sexToSearch");
		String birthplaceToSearch = request.getParameter("birthplaceToSearch");
		String politicalLandscapeToSearch = request.getParameter("politicalLandscapeToSearch");
		String accessTypeToSearch = request.getParameter("accessTypeToSearch");
		String majorNoToSearch = request.getParameter("majorNoToSearch");
		String cultivationTypeToSearch = request.getParameter("cultivationTypeToSearch");
		String executiveClassToSearch = null;
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
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
		if(sexToSearch == null || sexToSearch.equals("")){
			sexToSearch = "";
		}else{
			sexToSearch = sexToSearch.trim();
		}
		if(birthplaceToSearch == null || birthplaceToSearch.equals("")){
			birthplaceToSearch = "";
		}else{
			birthplaceToSearch = birthplaceToSearch.trim();
		}
		if(politicalLandscapeToSearch == null || politicalLandscapeToSearch.equals("")){
			politicalLandscapeToSearch = "";
		}else{
			politicalLandscapeToSearch = politicalLandscapeToSearch.trim();
		}
		if(accessTypeToSearch == null || accessTypeToSearch.equals("")){
			accessTypeToSearch = "";
		}else{
			accessTypeToSearch = accessTypeToSearch.trim();
		}
		if(majorNoToSearch == null || majorNoToSearch.equals("")){
			majorNoToSearch = "";
		}else{
			majorNoToSearch = majorNoToSearch.trim();
		}
		if(cultivationTypeToSearch == null || cultivationTypeToSearch.equals("")){
			cultivationTypeToSearch = "";
		}else{
			cultivationTypeToSearch = cultivationTypeToSearch.trim();
		}
		executiveClassToSearch = currentStudent.getExecutiveClassName();
		ModelMap model = new ModelMap();
		List<Map<String,Object>> studentList = new ArrayList<Map<String,Object>>();
		int pageSize = GetPageSize.PAGESIZE_LIST();
		Page<Student> studentPage = itsStudentService.getStudentsByPageQueryFromStudent(studentNoToSearch, studentNameToSearch, sexToSearch, birthplaceToSearch,politicalLandscapeToSearch,  accessTypeToSearch, majorNoToSearch, cultivationTypeToSearch,executiveClassToSearch, page, String.valueOf(pageSize));
		//result.addProperty("total", studentPage.getTotalCount());
		if(studentPage != null){
			//List<Student> studentList = studentPage.getResult();
			for(Student student:studentPage){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("studentId", student.getStudentId());
				map.put("studentNo", student.getStudentNo());
				map.put("name", student.getName());
				map.put("degreeCourseScore", student.getDegreeCourseScore());
				map.put("degreeCourseRank", student.getDegreeCourseRank());
				map.put("allCourseScore", student.getAllCourseScore());
				map.put("allCourseRank", student.getAllCourseRank());
				map.put("cet4Score", student.getCet4Score());
				map.put("cet6Score", student.getCet6Score());
				studentList.add(map);
			}
			
		}
		model.addAttribute("studentList", studentList);
		model.addAttribute("totalPage", studentPage.getTotalPages());
		model.addAttribute("totalCount", studentPage.getTotalCount());
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("currentPage", page);
		return new ModelAndView("/home/student/student_score_list", "studentInfo", model);
	}
	
	@RequestMapping(value = "/student/searchStudentScoreList.action")
	public @ResponseBody
	String searchStudentScoreList(HttpServletRequest request) {
		String page = request.getParameter("page");
		String studentNoToSearch = request.getParameter("studentNoToSearch");
		String studentNameToSearch = request.getParameter("studentNameToSearch");
		String sexToSearch = request.getParameter("sexToSearch");
		String birthplaceToSearch = request.getParameter("birthplaceToSearch");
		String politicalLandscapeToSearch = request.getParameter("politicalLandscapeToSearch");
		String accessTypeToSearch = request.getParameter("accessTypeToSearch");
		String majorNoToSearch = request.getParameter("majorNoToSearch");
		String cultivationTypeToSearch = request.getParameter("cultivationTypeToSearch");
		String executiveClassToSearch = null;
		String currentStudentNo = request.getSession().getAttribute("studentNo").toString();
		Student currentStudent = itsStudentService.findStudentByStudentNo(currentStudentNo);
		if(page == null || page.equals("")){
			page = "1";
		}else{
			page = page.trim();
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
		if(sexToSearch == null || sexToSearch.equals("")){
			sexToSearch = "";
		}else{
			sexToSearch = sexToSearch.trim();
		}
		if(birthplaceToSearch == null || birthplaceToSearch.equals("")){
			birthplaceToSearch = "";
		}else{
			birthplaceToSearch = birthplaceToSearch.trim();
		}
		if(politicalLandscapeToSearch == null || politicalLandscapeToSearch.equals("")){
			politicalLandscapeToSearch = "";
		}else{
			politicalLandscapeToSearch = politicalLandscapeToSearch.trim();
		}
		if(accessTypeToSearch == null || accessTypeToSearch.equals("")){
			accessTypeToSearch = "";
		}else{
			accessTypeToSearch = accessTypeToSearch.trim();
		}
		if(majorNoToSearch == null || majorNoToSearch.equals("")){
			majorNoToSearch = "";
		}else{
			majorNoToSearch = majorNoToSearch.trim();
		}
		if(cultivationTypeToSearch == null || cultivationTypeToSearch.equals("")){
			cultivationTypeToSearch = "";
		}else{
			cultivationTypeToSearch = cultivationTypeToSearch.trim();
		}
		executiveClassToSearch = currentStudent.getExecutiveClassName();
		
		
		int pageSize = GetPageSize.PAGESIZE_LIST();
		Page<Student> studentPage = itsStudentService.getStudentsByPageQueryFromStudent(studentNoToSearch, studentNameToSearch, sexToSearch, birthplaceToSearch,politicalLandscapeToSearch, accessTypeToSearch, majorNoToSearch, cultivationTypeToSearch,executiveClassToSearch, page, String.valueOf(pageSize));
		JsonObject result = new JsonObject();
		int totalCount = studentPage.getTotalCount();
		int totalPage = ((totalCount % pageSize != 0) ? (totalCount / pageSize + 1) : (totalCount / pageSize));
		totalPage = totalCount == 0 ? 1 : totalPage;
		result.addProperty("totalPage", totalPage);
		result.addProperty("totalCount", totalCount);
		result.addProperty("currentPage", Integer.valueOf(page));
		result.addProperty("pageSize", pageSize);
		JsonArray ja = new JsonArray();
		if (studentPage != null) {
			//List<Student> studentList = studentPage.getResult();
			for(Student student:studentPage){
				JsonObject jo = new JsonObject();
				jo.addProperty("studentId", student.getStudentId());
				if(currentStudentNo.equals( student.getStudentNo())){
					jo.addProperty("studentNo", student.getStudentNo());
					jo.addProperty("name", student.getName());
				}else{
					jo.addProperty("studentNo", "***");
					jo.addProperty("name", "***");
				}
				//jo.addProperty("studentNo", student.getStudentNo());
				//jo.addProperty("name", student.getName());
				jo.addProperty("degreeCourseScore", student.getDegreeCourseScore());
				jo.addProperty("degreeCourseRank", student.getDegreeCourseRank());
				jo.addProperty("allCourseScore", student.getAllCourseScore());
				jo.addProperty("allCourseRank", student.getAllCourseRank());
				jo.addProperty("cet4Score", student.getCet4Score());
				jo.addProperty("cet6Score", student.getCet6Score());
				ja.add(jo);
			}
			
		}

		result.add("record", ja);
		return result.toString();

	}
	
	/**
	 * 批量导入学生成绩
	 * @author Haozhifeng
	 * @param 
	 * @param studentScoreBatchAddFile
	 * */
	@RequestMapping(value = "/admin/batchUpdateStudentScore")
	@ResponseBody
	public String batchUpdateStudentScore(HttpServletRequest request) {
		JsonObject result = new JsonObject();
		String uploadedFile = request.getParameter("studentScoreBatchUpdateFile");
		List<List<String>> studentListsInfoOfBatchFile = new ArrayList<List<String>>();
		List<Student> studentList = new ArrayList<Student>();
		try {
			//String uploadedFileInServer = CommonPath.getWebappPath(uploadedFile);
			String uploadedFileInServer = CommonPath.getWebappPath("") + uploadedFile;
			studentListsInfoOfBatchFile =ExcelResolveUtil.readExcel(uploadedFileInServer);
			if(studentListsInfoOfBatchFile != null && studentListsInfoOfBatchFile.size() > 0){
				for(int i = 0;i < studentListsInfoOfBatchFile.size();i++){
					List<String> studentInfo = studentListsInfoOfBatchFile.get(i);
					//for(int j = 0;j < studentInfo.size();j++){
						//Student student = new Student();
						
						//String infoSwqNo = studentInfo.get(0).trim();
						String studentNo = studentInfo.get(0).trim();
						String studentName = studentInfo.get(1).trim();
						String schoolName = studentInfo.get(2).trim();
						String majorName = studentInfo.get(3).trim();
						String degreeCourseScore = studentInfo.get(4).trim();
						String degreeCourseRank = studentInfo.get(5).trim();
						String allCourseScore = studentInfo.get(6).trim();
						String allCourseRank = studentInfo.get(7).trim();
						String cet4Score = studentInfo.get(8).trim();
						String cet6Score = studentInfo.get(9).trim();
						
						
						//Long studentId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.Student_OBJECTTYPE);
						Student student = itsStudentService.findStudentByStudentNo(studentNo);
						if(!degreeCourseScore.equals("")){
							student.setDegreeCourseScore(Float.valueOf(degreeCourseScore));
						}
						if(!degreeCourseRank.equals("")){
							student.setDegreeCourseRank(Integer.valueOf(degreeCourseRank));						
						}
						if(!allCourseScore.equals("")){
							student.setAllCourseScore(Float.valueOf(allCourseScore));
						}
						if(!allCourseRank.equals("")){
							student.setAllCourseRank(Integer.valueOf(allCourseRank));
						}
						if(!cet4Score.equals("")){
							student.setCet4Score(Integer.valueOf(cet4Score));
						}
						if(!cet6Score.equals("")){
							student.setCet6Score(Integer.valueOf(cet6Score));
						}
						studentList.add(student);
					//}
				}
				boolean batchAddResult = itsStudentService.updateStudentsByBatch(studentList);
				result.addProperty("msg", batchAddResult);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result.toString();
	}
	
}

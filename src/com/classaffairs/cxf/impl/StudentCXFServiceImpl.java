/**
 * 
 */
package com.classaffairs.cxf.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.classaffairs.cxf.StudentCXFService;
import com.classaffairs.entity.Major;
import com.classaffairs.entity.Student;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.MajorService;
import com.classaffairs.service.StudentService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Service
public class StudentCXFServiceImpl implements StudentCXFService {
	
	@Autowired
	private StudentService itsStudentService;
	@Autowired
	private MajorService itsMajorService;

	/* (non-Javadoc)
	 * @see com.classaffairs.cxf.StudentCXFService#getStudentsByPageQueryFromAdmin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@GET
	@Path("/getStudentsByPageQueryFromStudent/{studentNo}/{studentName}/{sex}/{birthplace}/{politicalLandscape}/{accessType}/{majorNo}/{cultivationType}/{executiveClassNo}/{page}/{pageSize}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, "text/html; charset=UTF-8" })
	@Transactional(readOnly = false)
	public Page<Student> getStudentsByPageQueryFromStudent(@PathParam("studentNo")String studentNo, 
			@PathParam("studentName")String studentName, 
			@PathParam("sex")String sex,
			@PathParam("birthplace")String birthplace,
			@PathParam("politicalLandscape")String politicalLandscape, 
			@PathParam("accessType")String accessType, 
			@PathParam("majorNo")String majorNo, 
			@PathParam("cultivationType")String cultivationType, 
			@PathParam("executiveClassNo")String executiveClassNo, 
			@PathParam("page")String page, 
			@PathParam("pageSize")String pageSize){
		JsonArray ja = new JsonArray();
		Page<Student> studentPage = itsStudentService.getStudentsByPageQueryFromStudent(studentNo, studentName, sex, birthplace, politicalLandscape,accessType, majorNo, cultivationType,executiveClassNo, page, String.valueOf(pageSize));
		if(studentPage != null){
			//List<Student> studentList = studentPage.getResult();
			for(Student student:studentPage){
				JsonObject jo =new JsonObject();
				Map<String,Object> map = new HashMap<String,Object>();
				jo.addProperty("studentId", student.getStudentId());
				jo.addProperty("studentNo", student.getStudentNo());
				jo.addProperty("name", student.getName());
				
				String sexText = "";
				//String schoolName = "";
				String majorName = "";
				//String classExecutiveName = "";
				if(student.getSex() != null){
					if(1 == student.getSex()){
						sexText = "男";
					}else if(2 == student.getSex()){
						sexText = "女";
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
				
				jo.addProperty("sex", sexText);
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
		return null;
	}

}

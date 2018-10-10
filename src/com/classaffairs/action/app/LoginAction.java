/**
 * 
 */
package com.classaffairs.action.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.common.GetIp;
import com.classaffairs.entity.OnlineBehavior;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.entity.Student;
import com.classaffairs.service.OnlineBehaviorService;
import com.classaffairs.service.StudentService;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class LoginAction {
	@Autowired
	private StudentService itsStudentService;
	
	@Autowired 
	private OnlineBehaviorService itsOnlineBehaviorService;
	
	private List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();
	
	@RequestMapping(value = "/getStudentByStudentNo")
	public @ResponseBody
	String getStudentByStudentNo(HttpServletRequest reuqest){
		
		String studentNo = reuqest.getParameter("studentNo");
		
		JsonObject result = new JsonObject();
		
		Student student = itsStudentService.findStudentByStudentNo(studentNo);
		if(student != null){
			result.addProperty("exist", true);
			result.addProperty("studentId", student.getStudentId());
		}else{
			result.addProperty("exist", false);
		}
		
		return result.toString();
	}
	
	@RequestMapping(value = "/getStudentState")
	public @ResponseBody
	String getStudentState(HttpServletRequest reuqest){
		
		String studentId = reuqest.getParameter("studentId");
		
		JsonObject result = new JsonObject();
		
		Integer state = 1;
		Student student = itsStudentService.findStudentByStudentId(Long.valueOf(studentId));
		if(student != null){
			state = student.getState();
		}
		result.addProperty("state", state);
		
		return result.toString();
	}
	
	/**
	 * 判断是否登录
	 * @param request
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping(value = "/getIfLogin.action")
	public @ResponseBody
	String getIfLogin(HttpServletRequest request) throws Exception {
		String login = (String) request.getSession().getAttribute("login");
		JsonObject o = new JsonObject();
		if ("true".equals(login)) {
			o.addProperty("success", "1");
		} else {
			o.addProperty("success", "0");
		}
		return o.toString();
	}*/
	
	/**
	 * 
	 *消除登录信息
	 */
	@RequestMapping("/removeloadinfo.action")
	@ResponseBody
	public String removesessionattr(HttpServletRequest request) {
		String url = request.getParameter("url");
		int index = 0;
		if (url != null) {
			Iterator<Map<String, Object>> it = infoList.iterator();
			while (it.hasNext()) {
				Map<String, Object> map = it.next();
				String urlkey = (String) map.get("url");
				if (urlkey.equals(url)) {
					infoList.remove(index);
					break;
				}
				index++;
			}

		}
		JsonObject result = new JsonObject();
		result.addProperty("success", true);

		return result.toString();
	}
	
	/**
	 * 获取登录信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLoginMessage.action")
	public @ResponseBody
	String getLoginMessage(HttpServletRequest request) throws Exception {
		String login = (String) request.getSession().getAttribute("login");
		JsonObject jo = new JsonObject();
		if ("true".equals(login)) {
			jo.addProperty("hasLogined", "1");
			Long studentId = (Long) request.getSession().getAttribute("studentId");
			String studentNo = (String) request.getSession().getAttribute("studentNo");
			String studentName = (String) request.getSession().getAttribute("studentName");
			//jo.addProperty("studentId", studentId);
			jo.addProperty("studentNo", studentNo);
			jo.addProperty("studentName", studentName);

			/*OnlineBehavior onlineBehavior = new OnlineBehavior();
			Long onlineBehaviorId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.OnlineBehavior_ObjectType);
			onlineBehavior.setOnlineBehaviorId(onlineBehaviorId);
			onlineBehavior.setUserId(studentId);
			onlineBehavior.setUserIp(GetIp.getRealIP(request));
			onlineBehavior.setOnlineBehaviorName("登录");
			onlineBehavior.setType(OnlineBehavior.Type_Login);
			onlineBehavior.setState(OnlineBehavior.Delete_No);
			onlineBehavior.setRecordTime(new Date());
			itsOnlineBehaviorService.addOnlineBehavior(onlineBehavior);*/
			
		} else {
			jo.addProperty("hasLogined", "0");
		}
		/*if (accountId != null) {
			jsonObject.addProperty("accountId", accountId);
			int type = itsAccountService.getAccountByAccountId(accountId).getType();
			switch (type) {
			case 1:
				jsonObject.addProperty("friendOrCoach", "1");
				jsonObject.addProperty("userId", String.valueOf(request.getSession().getAttribute("userId")));
				jsonObject.addProperty("name",itsUsersService.getUser(accountId).getNickname());
				jsonObject.addProperty("clubId", "0");
				break;
			case 2:
				jsonObject.addProperty("friendOrCoach", "2");
				jsonObject.addProperty("userId", String.valueOf(request.getSession().getAttribute("userId")));
				jsonObject.addProperty("name",itsUsersService.getUser(accountId).getNickname());
				jsonObject.addProperty("clubId", "0");
				break;
			case 3:
				jsonObject.addProperty("clubId", String.valueOf(request.getSession().getAttribute("clubId")));
				jsonObject.addProperty("name",itsClubService.getClubByAccountId(accountId).getName());
				jsonObject.addProperty("userId", "0");
				break;
			default:
			}
		} else {
			jsonObject.addProperty("accountId", "0");
			jsonObject.addProperty("userId", "0");
			jsonObject.addProperty("clubId", "0");
		}*/
		return jo.toString();
	}
	
	/**
	 * 记录学生的登录记录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loginRecord.action")
	public @ResponseBody
	void loginRecord(HttpServletRequest request) throws Exception {
		String login = (String) request.getSession().getAttribute("login");
		JsonObject jo = new JsonObject();
		if ("true".equals(login)) {
			String studentId = (String) request.getSession().getAttribute("studentNo");
			OnlineBehavior onlineBehavior = new OnlineBehavior();
			Long onlineBehaviorId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.OnlineBehavior_ObjectType);
			onlineBehavior.setOnlineBehaviorId(onlineBehaviorId);
			onlineBehavior.setUserNo(studentId);
			onlineBehavior.setUserIp(GetIp.getRealIP(request));
			onlineBehavior.setOnlineBehaviorName("登录");
			onlineBehavior.setType(OnlineBehavior.Type_Login);
			onlineBehavior.setState(OnlineBehavior.Delete_No);
			onlineBehavior.setRecordTime(new Date());
			itsOnlineBehaviorService.addOnlineBehavior(onlineBehavior);
		}
	}
	
}

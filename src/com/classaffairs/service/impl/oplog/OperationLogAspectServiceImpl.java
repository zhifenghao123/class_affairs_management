/**
 * 
 */
package com.classaffairs.service.impl.oplog;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.common.DateConvert;
import com.classaffairs.common.GetIp;
import com.classaffairs.entity.OperationLog;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.service.OperationLogAspectService;
import com.classaffairs.service.OperationLogService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class OperationLogAspectServiceImpl implements OperationLogAspectService {
	@Autowired
	private OperationLogService itsOperationLogService;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogAspectService#addOplogAround(org.aspectj.lang.ProceedingJoinPoint)
	 */
	@SuppressWarnings("rawtypes")
	public boolean addOplogAround(ProceedingJoinPoint pjp){
		boolean addOplogAspectResult = false;
		HttpServletRequest request = SysContent.getRequest();
		String operatorId = String.valueOf(request.getSession().getAttribute("jobId")) ;//获取用户Id
		if(!"null".equals(operatorId)){
			String operatorIp = GetIp.getRealIP(request);//获取用户IP
			
			String useMethodName = pjp.getSignature().getName();//获取方法名
			String operateArguments = null;
			String operateResult = null;
			String currentApplicationJavaClass = null;
			String memo = null;
			Date operateTime = new Date();
			Object[] args = pjp.getArgs();//ProceedingJoinPoint获取的方法的原始参数
			operateArguments =Arrays.toString(args);
			if(null!=args&&args.length>0){
				for(Object obj:args){
					if(useMethodName.contains("add")||useMethodName.contains("update")){
						try {
							String[] objToStrArray = new String[5];
							String classpath = null;
							Object object = null;
							if(obj.toString().contains("EnhancerByCGLIB")){
								objToStrArray = obj.toString().split("EnhancerByCGLIB");
								classpath = objToStrArray[0].substring(0,(objToStrArray[0].length()-2));//截取类的绝对路径
								Class clazz = Class.forName(classpath);
								object  =clazz.newInstance();//通过反射生成此类型的实例
								ConvertUtils.register(new DateConvert(), Date.class);
								BeanUtils.copyProperties(object, obj);//在此对类转换，将CGLIB类型类转换为原始的类
							}else if(obj.toString().contains("@")){
								object = obj;
							}
							//获取实体的绝对路径
							classpath = object.getClass().getCanonicalName();
							switch(classpath){
							case "com.classaffairs.entity.Application":
								 currentApplicationJavaClass = "应用";
								break;
							case "com.classaffairs.entity.Authority":
								currentApplicationJavaClass = "权限";
								break;
							case "com.classaffairs.entity.Role":
								currentApplicationJavaClass = "角色";
								break;
							case "com.classaffairs.entity.Administrator":
								currentApplicationJavaClass = "管理员";
								break;
							case "com.classaffairs.entity.Student":
								currentApplicationJavaClass = "学生";
								break;
							case "com.classaffairs.entity.School":
								currentApplicationJavaClass = "学院";
								break;
							case "com.classaffairs.entity.Department":
								currentApplicationJavaClass = "应用";
								break;
							case "com.classaffairs.entity.Major":
								currentApplicationJavaClass = "专业";
								break;
							case "com.classaffairs.entity.Grade":
								currentApplicationJavaClass = "应用";
								break;
							case "com.classaffairs.entity.ExecutiveClass":
								currentApplicationJavaClass = "行政班级";
								break;
							case "com.classaffairs.entity.Laboratory":
								currentApplicationJavaClass = "教研室";
								break;
							case "com.classaffairs.entity.Apartment":
								currentApplicationJavaClass = "公寓";
								break;
							case "com.classaffairs.entity.Dormitory":
								currentApplicationJavaClass = "宿舍";
								break;
							case "com.classaffairs.entity.Notice":
								currentApplicationJavaClass = "通知";
								break;
							case "com.classaffairs.entity.Activity":
								currentApplicationJavaClass = "活动";
								break;
							case "com.classaffairs.entity.Blog":
								currentApplicationJavaClass = "博客";
								break;
							default:
								currentApplicationJavaClass = "";
								break;
						}
							//根据实体的绝对路径获取关注字段列表
							List<String> propertyList = EntityForOplogManager.getInstance().getPropertyList(classpath);
							//主键Id
							String objId = null;
							//关注字段
							String concernAttr =null;
							if(null!=propertyList&&propertyList.size()>0){
								objId = propertyList.get(0);
								concernAttr = propertyList.get(1);
							}
							//开始根据对象和关注字段反射出字段的值
							Field objIdField = object.getClass().getDeclaredField(objId);
							Field concernAttrField = object.getClass().getDeclaredField(concernAttr);
							objIdField.setAccessible(true);
							concernAttrField.setAccessible(true);
							operateArguments = objToString(concernAttrField.get(object));
							//memo用于记录对象的id和路径名，便于删除的时候直接获取关注字段的值
							if(useMethodName.contains("add")){
								memo = objIdField.get(object).toString()+"@"+classpath+"add";
							}else if(useMethodName.contains("update")){
								memo = objIdField.get(object).toString()+"@"+classpath+"update";
							}
						} catch (NoSuchFieldException nSFE) {
							Log.log.error("操作日志反射无此属性异常"+nSFE);
							nSFE.printStackTrace();
						} catch (SecurityException se) {
							Log.log.error("操作日志反射安全异常异常"+se);
							se.printStackTrace();
						} catch (Exception e) {
							Log.log.error("操作日志异常"+e);
							e.printStackTrace();
						}
					}
				}
			}
			//操作日志入库
			try {
				Object proceedR	= pjp.proceed();
				if(null==proceedR){
					operateResult = (String)proceedR;
				}else{
					operateResult = proceedR.toString();
				}
			} catch (Throwable e) {
				Log.log.error("操作日志运行结果处理异常"+e);
				e.printStackTrace();
			}
			OperationLog oplog = new OperationLog();
			oplog.setOperatorId(new Long(operatorId));
			oplog.setOperatorIp(operatorIp);
			oplog.setOperateTime(operateTime);
			//oplog.setUseMethodName(useMethodName);
			//oplog.setOperateArguments(operateArguments);
			String operationName = null;
			if(useMethodName.contains("add")){
				oplog.setType("add");
				operationName = "新增" + currentApplicationJavaClass;
			}else if(useMethodName.contains("update")){
				oplog.setType("update");
				operationName = "修改" + currentApplicationJavaClass;
			}
			oplog.setOperationName(operationName);
			oplog.setOperateArguments(operateArguments);
			oplog.setOperationResult(operateResult);
			//oplog.setMemo(memo);
			Log.log.info("operatorId="+operatorId+"--operatorIp="+operatorIp+"--operateTime="+operateTime
					+"--useMethodName="+useMethodName+"--operateArguments="+operateArguments+"--operateResult="+operateResult);
			addOplogAspectResult = itsOperationLogService.addOperationLog(oplog);
		}else{
			try {
				pjp.proceed();
				addOplogAspectResult = true;
			} catch (Throwable e) {
				Log.log.error("操作日志运行结果处理异常"+e);
				e.printStackTrace();
			}
		}
		return addOplogAspectResult;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogAspectService#addOplogBefore(org.aspectj.lang.JoinPoint)
	 */
	public boolean addOplogBefore(JoinPoint jp){
		boolean addOplogAspectResult = false;
		HttpServletRequest request = SysContent.getRequest();
		String operatorId = String.valueOf(request.getSession().getAttribute("jobId")) ;
		if(!"null".equals(operatorId)){
			String operatorIp = GetIp.getRealIP(request);
			
			String useMethodName = jp.getSignature().getName();
			String operateArguments = null;
			String operateResult = null;
			String implClasspath = jp.getTarget().getClass().getCanonicalName();
			String[] implClasspathArray = implClasspath.split("ServiceImpl");
			String entityClasspath = implClasspathArray[0].replace("service.impl", "entity");
			String currentApplicationJavaClass = null;
			switch(entityClasspath){
			case "com.classaffairs.entity.Application":
				 currentApplicationJavaClass = "应用";
				break;
			case "com.classaffairs.entity.Authority":
				currentApplicationJavaClass = "权限";
				break;
			case "com.classaffairs.entity.Role":
				currentApplicationJavaClass = "角色";
				break;
			case "com.classaffairs.entity.Administrator":
				currentApplicationJavaClass = "管理员";
				break;
			case "com.classaffairs.entity.Student":
				currentApplicationJavaClass = "学生";
				break;
			case "com.classaffairs.entity.School":
				currentApplicationJavaClass = "学院";
				break;
			case "com.classaffairs.entity.Department":
				currentApplicationJavaClass = "应用";
				break;
			case "com.classaffairs.entity.Major":
				currentApplicationJavaClass = "专业";
				break;
			case "com.classaffairs.entity.Grade":
				currentApplicationJavaClass = "应用";
				break;
			case "com.classaffairs.entity.ExecutiveClass":
				currentApplicationJavaClass = "行政班级";
				break;
			case "com.classaffairs.entity.Laboratory":
				currentApplicationJavaClass = "教研室";
				break;
			case "com.classaffairs.entity.Apartment":
				currentApplicationJavaClass = "公寓";
				break;
			case "com.classaffairs.entity.Dormitory":
				currentApplicationJavaClass = "宿舍";
				break;
			case "com.classaffairs.entity.Notice":
				currentApplicationJavaClass = "通知";
				break;
			case "com.classaffairs.entity.Activity":
				currentApplicationJavaClass = "活动";
				break;
			case "com.classaffairs.entity.Blog":
				currentApplicationJavaClass = "博客";
				break;
			default:
				currentApplicationJavaClass = "";
				break;
		}
			Date operateTime = new Date();
			Object[] args = jp.getArgs();
			operateArguments =Arrays.toString(args);
			if(null!=args&&args.length>0){
				for(Object obj:args){
					String id = obj.toString();
					String memo1 = id+"@"+entityClasspath+"update";
					String memo2 = id+"@"+entityClasspath+"Add";
					operateArguments = obj.toString();
				/*	String updateArguement = itsOperationLogService.getOplogArguement(memo1);//先查是否有更新记录,有更新记录以最新的更新参数为准，
					//没有更新记录，则以新增记录为准
					if(null!=updateArguement&&!updateArguement.equals("")){
						operateArguments =  updateArguement;
					}else{
						String addArgument = itsOperationLogService.getOplogArguement(memo2);
						operateArguments = addArgument;
					}*/
				}
			}
			//操作日志入库
			try {
				operateResult = "true";
				OperationLog oplog = new OperationLog();
				oplog.setOperatorId(new Long(operatorId));
				oplog.setOperatorIp(operatorIp);
				oplog.setOperateTime(operateTime);
				String operationName = null;
				if(useMethodName.contains("delete")){
					oplog.setType("delete");
					operationName = "删除" + currentApplicationJavaClass;
				}
				//oplog.setUseMethodName(useMethodName);
				oplog.setOperateArguments(operateArguments);
				oplog.setOperationName(operationName);
				oplog.setOperationResult(operateResult);
				//oplog.setMemo("");
				/*Log.log.info("OplogId:"+oplog.getOplogId()+"/OperatorIp:"+oplog.getOperatorIp()+"/OperatorId:"+oplog.getOperatorId());
				Log.log.info("/UseMethodName:"+oplog.getUseMethodName()+"/OperateArguments:"+oplog.getOperateArguments());
				Log.log.info("/OperateResult:"+oplog.getOperateResult());*/
				addOplogAspectResult = itsOperationLogService.addOperationLog(oplog);
			}catch(DataAccessException e){
				Log.log.error("操作日志数据库访问异常cn.togym.services.app.impl.oplog.OplogServiceImpl", e);
				e.printStackTrace();
			}catch (Throwable throwable) {
				Log.log.error("操作日志切面异常cn.togym.services.app.impl.oplog.OplogServiceImpl", throwable);
				throwable.printStackTrace();
			}
		}else{
			
		}
		return addOplogAspectResult;
	}
	
	public String objToString(Object obj){
		String str=null;
		if(null!=obj){
			str = obj.toString();
		}else{
			str = (String)obj;
		}
		return str;
	}
}

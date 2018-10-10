/**
 * 
 */
package com.classaffairs.service.impl.oplog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.classaffairs.service.OperationLogAspectService;

/**
 * @author Haozhifeng
 *
 */
@Component
@Aspect
public class OperationLogAspect {
	
	@Autowired
	private OperationLogAspectService itsOperationLogAspectService;
	//连接点，后台系统管理所有新增、删除和修改的方法都被控制
	@Pointcut("execution(public * com.classaffairs.service.impl.**.add*(..))")
	public void addLogging(){}
	
	@Pointcut("execution(public * com.classaffairs.service.impl.**.delete*(..))")
	public void deleteLogging(){}
	
	@Pointcut("execution(public * com.classaffairs.service.impl.**.update*(..))")
	public void updateLogging(){}
	
/*	@Pointcut("execution(public * com.classaffairs.service.impl.**.select*Loging(..))")
	public void selectLogging(){}*/
	
/*	@Pointcut("execution(public * cn.togym.services.app.impl.oplog.OplogDictionaryServiceImpl.addOplogDictionary(..))")
	public void addOplogDictionaryLogging(){}
	
	@Pointcut("execution(public * cn.togym.services.app.impl.oplog.OplogDictionaryServiceImpl.updateOplogDictionary(..))")
	public void updateOplogDictionaryLogging(){}*/
	
	//环绕通知，用于新增和修改时服务逻辑织入
	/*@Around("addLogging()||updateLogging()||addOplogDictionaryLogging()||updateOplogDictionaryLogging()")*/
	@Around("addLogging()||updateLogging()")
	public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
		boolean result = false;
		result = itsOperationLogAspectService.addOplogAround(pjp);
		return result;
	}
	//前置通知，用于删除时的服务逻辑织入
	@Before("deleteLogging()")
	public Object logBefore(JoinPoint jp) throws Throwable{
		boolean result = false;
		result = itsOperationLogAspectService.addOplogBefore(jp);
		return result;
	}
}

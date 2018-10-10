/**
 * 
 */
package com.classaffairs.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 *  用于操作日志的环绕通知和前置通知服务
 * @author Haozhifeng
 *
 */
public interface OperationLogAspectService {
	/**
	 * 添加带有切面环绕通知的操作日志（主要用于新增和修改）
	 * @param pjp 连接点对象
	 * @return true-成功    false-失败
	 */
	public boolean addOplogAround(ProceedingJoinPoint pjp);

	/**
	 * 添加带有切面前置通知的操作日志（主要用于删除）
	 * @param pjp 连接点对象
	 * @return true-成功    false-失败
	 */
	public boolean addOplogBefore(JoinPoint jp);
}

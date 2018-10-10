/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.entity.OperationLog;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface OperationLogService {

	/**
	 * 新增一条操作日志记录
	 * @param operationLog
	 * @return true-成功 false-失败
	 */
	public boolean addOperationLog(OperationLog operationLog);
	/**
	 * 修改操作日志
	 * @param operationLog
	 * @param operatorid
	 * @param operatorip
	 * @param operatetime
	 * @param appmodel
	 * @param usemodelclassname
	 * @param usemethodname
	 * @param operateresult
	 * @param memo
	 * @param operatearguments
	 * @return true-成功 false-失败
	 */
	public boolean updateOperationLog(OperationLog operationLog);

	/**
	 * 删除一条操作日志
	 * @param operationLogId
	 * @return true-成功 false-失败
	 */
	public boolean deleteOperationLog(Long operationLogId);
	/**
	 * 根据operationLogId获取操作日志
	 * @param operationLogId
	 * @return OperationLog
	 */
	public OperationLog getOperationLogById(Long operationLogId);

	/**
	 * 获取所有操作日志
	 * @param recordOffset
	 * @param pageSize
	 * @return Page<OperationLog>
	 */
	public Page<OperationLog> getALLOperationLogsPage(int recordOffset, int pageSize);

	/**
	 * 获取所有操作日志
	 * @return List<OperationLog>
	 */
	public List<OperationLog> getALLOperationLogsList();

	public String getOperationLogArguement(String memo);


	/**
	 * 删除一个操作日志列表
	 * @param operationLogs
	 * @return 删除成功的日志条数
	 */
	public int deleteOpLogList(List<OperationLog> operationLogs);

	/**
	 * 根据操作者和日期获取日志列表
	 * @param operatorName
	 * @param startDate
	 * @param endDate
	 * @param recordOffset
	 * @param pageSize
	 * @return Page<OperationLog>
	 */
	public Page<OperationLog> getOperationLogByOperatorNameAndDatePage(String operatorId, String startDate, String endDate, int recordOffset, int pageSize);

	/**
	 * 根据操作者和日期获取日志列表
	 * @param operatorName
	 * @param startDate
	 * @param endDate
	 * @return List<OperationLog>
	 */
	public List<OperationLog> getOperationLogByOperatorNameAndDateList(String operatorId, String startDate, String endDate);

}

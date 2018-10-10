/**
 * 
 */
package com.classaffairs.entity;

import java.util.Date;

/**
 * @author Haozhifeng
 *
 */
public class OperationLog {
	public Long operationLogId;
	public Long operatorId;
	public String operatorIp;
	public Date operateTime;
	public String type;
	public String operationName;
	public String operateArguments;
	public String operationResult;
	public Long getOperationLogId() {
		return operationLogId;
	}
	public void setOperationLogId(Long operationLogId) {
		this.operationLogId = operationLogId;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorIp() {
		return operatorIp;
	}
	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperateArguments() {
		return operateArguments;
	}
	public void setOperateArguments(String operateArguments) {
		this.operateArguments = operateArguments;
	}
	public String getOperationResult() {
		return operationResult;
	}
	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}
}

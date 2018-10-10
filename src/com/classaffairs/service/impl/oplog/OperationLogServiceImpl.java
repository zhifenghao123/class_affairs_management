/**
 * 
 */
package com.classaffairs.service.impl.oplog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.dao.OperationLogDao;
import com.classaffairs.entity.OperationLog;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.OperationLogService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

	@Autowired
	private OperationLogDao itsOperationLogDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#addOperationLog(com.classaffairs.entity.OperationLog)
	 */
	@Override
	public boolean addOperationLog(OperationLog operationLog) {
		boolean result = false;
		try {
			if (null == operationLog) {
				return result;
			}
			operationLog.setOperationLogId(CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.OperationLog_OBJECTTYPE));
			int addR = itsOperationLogDao.mSave(operationLog);
			result = (addR == 1);
		} catch (DataAccessException e) {
			Log.log.error("添加操作日志数据库访问异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", e);
			e.printStackTrace();
		} catch (Exception ee) {
			Log.log.error("添加操作日志异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", ee);
			ee.printStackTrace();
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#updateOperationLog(com.classaffairs.entity.OperationLog)
	 */
	@Override
	public boolean updateOperationLog(OperationLog operationLog) {
		boolean result = false;
		try {
			if (null == operationLog) {
				return result;
			}
			int updateR = itsOperationLogDao.mUpdate(operationLog);
			result = (updateR == 1);
		} catch (DataAccessException e) {
			Log.log.error("修改操作日志数据库访问异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", e);
			e.printStackTrace();
		} catch (Exception ee) {
			Log.log.error("修改操作日志异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", ee);
			ee.printStackTrace();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#deleteOperationLog(java.lang.Long)
	 */
	@Override
	public boolean deleteOperationLog(Long operationLogId) {
		boolean result = false;
		try {
			itsOperationLogDao.mDeleteById(operationLogId);
			result = true;
		} catch (DataAccessException e) {
			Log.log.error("删除操作日志数据库访问异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", e);
			e.printStackTrace();
		} catch (Exception ee) {
			Log.log.error("删除操作日志异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", ee);
			ee.printStackTrace();
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#getOperationLogById(java.lang.Long)
	 */
	@Override
	public OperationLog getOperationLogById(Long operationLogId) {
		OperationLog operationLog ;
		try {
			operationLog = (OperationLog) itsOperationLogDao.mFindById(operationLogId);
		} catch (DataAccessException e) {
			Log.log.error("获取操作日志数据库访问异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", e);
			e.printStackTrace();
			return null;
		} catch (Exception ee) {
			Log.log.error("获取操作日志异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", ee);
			ee.printStackTrace();
			return null;
		}
		return operationLog;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#getALLOperationLogsPage(int, int)
	 */
	@Override
	public Page<OperationLog> getALLOperationLogsPage(int recordOffset,
			int pageSize) {
		try {
			return (Page<OperationLog>) itsOperationLogDao.mPageQuery("getAll", "", recordOffset, pageSize);
		} catch (DataAccessException e) {
			Log.log.error("获取所有操作日志数据库访问异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", e);
			e.printStackTrace();
			return null;
		} catch (Exception ee) {
			Log.log.error("获取所有操作日志异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", ee);
			ee.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#getALLOperationLogsList()
	 */
	@Override
	public List<OperationLog> getALLOperationLogsList() {
		try {
			return (List<OperationLog>) itsOperationLogDao.mFind("getAll", "");
		} catch (DataAccessException e) {
			Log.log.error("获得所有操作日志数据库访问异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", e);
			e.printStackTrace();
			return null;
		} catch (Exception ee) {
			Log.log.error("获得所有操作日志异常com.classaffairs.service.impl.oplog.OperationLogServiceImpl", ee);
			ee.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#getOperationLogArguement(java.lang.String)
	 */
	@Override
	public String getOperationLogArguement(String memo) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#deleteOpLogList(java.util.List)
	 */
	@Override
	public int deleteOpLogList(List<OperationLog> operationLogs) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#getOperationLogByOperatorNameAndDatePage(java.lang.String, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public Page<OperationLog> getOperationLogByOperatorNameAndDatePage(
			String operatorId, String startDate, String endDate,
			int recordOffset, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.OperationLogService#getOperationLogByOperatorNameAndDateList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<OperationLog> getOperationLogByOperatorNameAndDateList(
			String operatorId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}

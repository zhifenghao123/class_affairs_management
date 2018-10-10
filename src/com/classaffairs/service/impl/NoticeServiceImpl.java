/**
 * 
 */
package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.classaffairs.dao.NoticeDao;
import com.classaffairs.entity.Notice;
import com.classaffairs.entity.Notice;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.NoticeService;

/**
 * @author Haozhifeng
 *
 */
@Repository
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeDao itsNoticeDao;
	@Override
	public boolean addNotice(Notice notice) {
		int result = 0;
		try{
			result = itsNoticeDao.mSave(notice);
		}catch(DataAccessException dae){
			Log.log.error("新增通知访问数据库异常，通知名称：" , dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增通知异常，通知名称："  , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean updateNotice(Notice notice) {
		int result = 0;
		try{
			result = itsNoticeDao.mUpdate(notice);
		}catch(DataAccessException dae){
			Log.log.error("修改通知访问数据库异常，通知名称：" , dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改通知异常，通知名称："  , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	@Override
	public boolean deleteNotice(Long noticeId) {
		try{
			itsNoticeDao.mDeleteById(noticeId);
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除通知访问数据库异常，通知名称Id：" + noticeId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除通知异常，通知名称Id：" + noticeId , e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Notice findNoticeByNoticeId(Long noticeId) {
		Notice Notice = null;
		try {
			Notice = (Notice) itsNoticeDao.mFindById(noticeId);
		} catch (DataAccessException dae) {
			Log.log.error("通过通知id获取通知访问数据库异常NoticeId=" + noticeId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过通知id获取通知异常NoticeId=" + noticeId, e);

			e.printStackTrace();
		}

		return Notice;
	}

	@Override
	public
	Page<Notice> getNoticesByPageQuery(String keyWordSearch,String publisherNo,
			String publisherName,String startTimeToSearchPublishTime,
			String endTimeToSearchPublishTime,String currentExecutiveClassName,int page,int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(keyWordSearch != null && !("").equals(keyWordSearch))
			param.put("keyWordSearch", "%" + keyWordSearch + "%");
		param.put("publisherNo", publisherNo);
		param.put("publisherName", publisherName);
		if(startTimeToSearchPublishTime != null && !("").equals(startTimeToSearchPublishTime))
			param.put("startTimeToSearchPublishTime", startTimeToSearchPublishTime);
		if(endTimeToSearchPublishTime != null && !("").equals(endTimeToSearchPublishTime))
			param.put("endTimeToSearchPublishTime", endTimeToSearchPublishTime);
		param.put("currentExecutiveClassName", currentExecutiveClassName);
		Page<Notice> noticePage = null;
		try {
			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(pageSize);

			noticePage = (Page<Notice>) itsNoticeDao.mPageQuery("findNoticeByFuzzyInformation", param, recordOffset, Integer.valueOf(pageSize));
			
		} catch (DataAccessException dae) {
			Log.log.error("通过参数分页获取通知操作数据库异常,通知名称：", dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过参数分页获取通知异常,通知名称：" , e);
			e.printStackTrace();
		}

		return noticePage;
	}

}

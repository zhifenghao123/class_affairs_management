/**
 * 
 */
package com.classaffairs.service;

import com.classaffairs.entity.Notice;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface NoticeService {
	boolean addNotice(Notice notice);
	
	boolean updateNotice(Notice notice);
	
	boolean deleteNotice(Long noticeId);
	
	Notice findNoticeByNoticeId(Long noticeId);
	
	Page<Notice> getNoticesByPageQuery(String keyWordSearch,String publisherNo,String publisherName,String startTimeToSearchPublishTime,String endTimeToSearchPublishTime,String currentExecutiveClassName,int page,int pageSize);
}

/**
 * 
 */
package com.classaffairs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.common.security.ClassAffairFilterInvocationSecurityMetadataSource;
import com.classaffairs.dao.ApplicationDao;
import com.classaffairs.entity.Application;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.ApplicationService;

/**
 * @author Haozhifeng
 *
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {
	@Autowired
	private ApplicationDao itsApplicationDao;
	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ApplicationService#addApplication(com.classaffairs.entity.Application)
	 */
	@Override
	public boolean addApplication(Application application) {
		int result = 0;
		try{
			result = itsApplicationDao.mSave(application);
			//重载加载权限
			ClassAffairFilterInvocationSecurityMetadataSource.loadResourceDefine();
		}catch(DataAccessException dae){
			Log.log.error("新增应用访问数据库异常，应用名称：" + application.getDisplayName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("新增应用异常，应用名称：" + application.getDisplayName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ApplicationService#updateApplication(com.classaffairs.entity.Application)
	 */
	@Override
	public boolean updateApplication(Application application) {
		int result = 0;
		try{
			result = itsApplicationDao.mUpdate(application);
			//重新加载权限
			ClassAffairFilterInvocationSecurityMetadataSource.loadResourceDefine();
		}catch(DataAccessException dae){
			Log.log.error("修改应用访问数据库异常，应用名称：" + application.getDisplayName(), dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("修改应用异常，应用名称：" + application.getDisplayName() , e);
			e.printStackTrace();
		}
		return result == 1;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ApplicationService#deleteApplication(java.lang.Long)
	 */
	@Override
	public boolean deleteApplication(Long applicationId) {
		try{
			itsApplicationDao.mDeleteById(applicationId);
			//重新加载权限
			ClassAffairFilterInvocationSecurityMetadataSource.loadResourceDefine();
			return true;
		}catch(DataAccessException dae){
			Log.log.error("删除应用访问数据库异常，应用名称Id：" + applicationId, dae);
			dae.printStackTrace();
		}catch(Exception e){
			Log.log.error("删除应用异常，应用名称Id：" + applicationId , e);
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ApplicationService#findApplicationByApplicationId(java.lang.Long)
	 */
	@Override
	public Application findApplicationByApplicationId(Long applicationId) {
		Application application = null;
		try {
			application = (Application) itsApplicationDao.mFindById(applicationId);
		} catch (DataAccessException dae) {
			Log.log.error("通过应用id获取应用访问数据库异常applicationId=" + applicationId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过应用id获取应用异常applicationId=" + applicationId, e);

			e.printStackTrace();
		}

		return application;
	}

	/* (non-Javadoc)
	 * @see com.classaffairs.service.system.ApplicationService#finAllApplication()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Application> finAllApplication() {
		return (List<Application>) itsApplicationDao.mFindAll();
	}

	@Override
	public Application getApplicationsByUrl(String url) {
		Application application = null;
		try {
			Object obj = itsApplicationDao.mSelectOne("getByUrl", url);
			if (obj != null) {
				application = (Application) obj;
			}
		} catch (DataAccessException dae) {
			Log.log.error("通过应用链接获取唯一应用访问数据库异常，应用链接:" + url, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过应用链接获取唯一应用异常，应用链接:" + url, e);

			e.printStackTrace();
		}

		return application;
	}

	@Override
	public List<Application> getApplicationByPareantAppId(Long pareantAppId) {
		List<Application> application = null;
		try {
			application = (List<Application>) itsApplicationDao.mFind("getByParentApplicationId", pareantAppId);
		} catch (DataAccessException dae) {
			Log.log.error("通过应用pid获取应用访问数据库异常,pid=" + pareantAppId, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过应用pid获取应用异常,pid=" + pareantAppId, e);

			e.printStackTrace();
		}

		return application;
	}

	@Override
	public Page<Application> getAllApplications(String page, String rows,
			String displayName) {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("displayName", "%" + displayName + "%");

		Page<Application> allApplications = null;
		try {

			int recordOffset = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);

			allApplications = (Page<Application>) itsApplicationDao.mPageQuery("getAll", param, recordOffset, Integer.valueOf(rows));
		} catch (NumberFormatException nfe) {

			Log.log.error("通过参数分页获取所有应用数据转换异常", nfe);

			nfe.printStackTrace();
		} catch (DataAccessException dae) {

			Log.log.error("通过参数分页获取所有应用访问数据库异常", dae);

			dae.printStackTrace();
		} catch (Exception e) {

			Log.log.error("通过参数分页获取所有应用异常", e);

			e.printStackTrace();
		}

		return allApplications;
	}

	@Override
	public Application getApplicationsByDisplayName(String displayname) {
		Application application = null;
		try {
			application = (Application) itsApplicationDao.mSelectOne("getByDisplayName", displayname);
		} catch (DataAccessException dae) {
			Log.log.error("通过应用名称获取唯一应用访问数据库异常，应用名称:" + displayname, dae);

			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过应用名称获取唯一应用异常，应用名称:" + displayname, e);

			e.printStackTrace();
		}

		return application;
	}

}

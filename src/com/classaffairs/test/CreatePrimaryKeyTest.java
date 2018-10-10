package com.classaffairs.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;

import com.classaffairs.common.CreatePrimaryKey;
import com.classaffairs.dao.PrimaryKeyDao;
import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.core.springtestcase.SpringTestCase;
import com.classaffairs.framework.core.utils.Log;

public class CreatePrimaryKeyTest extends SpringTestCase {
	
	@Autowired
	private CreatePrimaryKey cpk;
	
	@Test
	@Rollback(false)
	public void testGetObjectId() {
		//Long applicationId = CreatePrimaryKey.getInstnce().getObjectId(PrimaryKey.APPLICATION_OBJECTTYPE);
		//System.out.println(applicationId);
		//BaseDaoImpl baseDao = SpringContextHelper.getBaseDao();
		PrimaryKeyDao primaryKeyDao = new PrimaryKeyDao();
		PrimaryKey primaryKey,primaryKey1,primaryKey11,primaryKey2;
		try {//getByObjectId
			primaryKey = (PrimaryKey) primaryKeyDao.mSelectOne("getByObjectType", 29);
			primaryKey11 = (PrimaryKey) primaryKeyDao.mSelectOne("findById", 29);
			primaryKey2 = (PrimaryKey) primaryKeyDao.mFindById(29);
			//Application application = (Application) baseDao.mSelectOne(Application.class, "getByDisplayName", "系统管理");
			System.out.println(primaryKey.getPrimarykeyId());
		}catch (DataAccessException dae) {
				Log.log.error("通过主键生成对象类型获取主键生成对象访问数据库异常,对象类型" + PrimaryKey.APPLICATION_OBJECTTYPE, dae);
				dae.printStackTrace();
			} catch (Exception e) {
				Log.log.error("通过主键生成对象类型获取主键生成对象异常,对象类型" + PrimaryKey.APPLICATION_OBJECTTYPE, e);
				e.printStackTrace();
			}
		
	}
	
}

package com.classaffairs.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.entity.PrimaryKey;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.dao.PrimaryKeyDao;
/**
 * 获取对象主键公共类
 * @author Haozhifeng
 * @since 2013-11-29
 */
@Service
public class CreatePrimaryKey {
	/*@Autowired
	private PrimaryKeyDao itsPrimaryKeyDao;*/
	
	private Map<String, Object> map = new HashMap<String, Object>();

	private CreatePrimaryKey() {
	}

	private static CreatePrimaryKey _instance = new CreatePrimaryKey();

	public static CreatePrimaryKey getInstnce() {
		return _instance;
	}

	/**
	 * 通过对象类型获取对象最大值id（当前所需id）
	 * @param objectType 主键生成对象类型(CreatePrimary类中的静态常量)
	 * @return 主键生成对象最大值id
	 */
	public synchronized long getObjectId(Integer objectType) {
		PrimaryKeyDao itsPrimaryKeyDao = SpringContextHelper.getPrimaryKeyDao();
		PrimaryKey primaryKey;
		try {
			//primaryKey = (PrimaryKey) baseDao.mSelectOne(PrimaryKey.class, "getByObjectType", objectType);
			primaryKey = (PrimaryKey) itsPrimaryKeyDao.mSelectOne("getByObjectType", objectType);
			if (primaryKey != null) {
				try {
					map.put("objectType", objectType);
					map.put("objectId", primaryKey.getObjectId() + 1);
					//baseDao.mUpdateField(PrimaryKey.class, "updateByType", map);
					itsPrimaryKeyDao.mUpdateField("updateByType", map);
					return primaryKey.getObjectId();
				} catch (DataAccessException dae) {
					Log.log.error("通过主键生成对象类型修改主键生成对象最大值id访问数据库异常,对象类型" + objectType, dae);
					dae.printStackTrace();
				} catch (Exception e) {
					Log.log.error("通过主键生成对象类型修改主键生成对象最大值id异常,对象类型" + objectType, e);
					e.printStackTrace();
				}
			} else {
				PrimaryKey key = new PrimaryKey();
				key.setPrimarykeyId(objectType);
				key.setObjectId(1001L);
				key.setObjectType(objectType);
				try {
					//baseDao.mSave(PrimaryKey.class, key);
					itsPrimaryKeyDao.mSave( key);
					return 1000;
				} catch (DataAccessException dae) {
					Log.log.error("通过主键生成对象类型修改主键生成对象最大值id访问数据库异常,对象类型" + objectType, dae);
					dae.printStackTrace();
				} catch (Exception e) {
					Log.log.error("通过主键生成对象类型修改主键生成对象最大值id异常,对象类型" + objectType, e);
					e.printStackTrace();
				}
			}
		} catch (DataAccessException dae) {
			Log.log.error("通过主键生成对象类型获取主键生成对象访问数据库异常,对象类型" + objectType, dae);
			dae.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过主键生成对象类型获取主键生成对象异常,对象类型" + objectType, e);
			e.printStackTrace();
		}
		return 1000;//1000:主键的初始值
	}
}

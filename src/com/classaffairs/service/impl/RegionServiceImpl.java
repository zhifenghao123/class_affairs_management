package com.classaffairs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.classaffairs.common.region.RegionManager;
import com.classaffairs.common.region.RegionNode;
import com.classaffairs.dao.RegionDao;
import com.classaffairs.entity.Region;
import com.classaffairs.framework.core.cache.CacheHelper;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.RegionService;
@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionDao itsRegionDao;
	@Override
	public boolean addRegion(Region region) {
		try {
			int result = itsRegionDao.mSave(region);
			return result == 1;
		} catch (DataAccessException de) {
			Log.log.error("新增区域数据库访问异常", de);
			de.printStackTrace();
		} catch (Exception e) {
			Log.log.error("新增区域异常", e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateRegion(Region region) {
		try {
			int row = itsRegionDao.mUpdate(region);
			RegionManager.getInstnce().reload();
			return row == 1;
		} catch (DataAccessException de) {
			Log.log.error("更新区域数据库访问异常", de);
			de.printStackTrace();
		} catch (Exception e) {
			Log.log.error("更新区域异常", e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteRegion(Long region_id) {
		try {
			itsRegionDao.mDeleteById(region_id);
			Region region = (Region) CacheHelper.get(region_id + "Region");
			if (region != null) {
				CacheHelper.delete(region_id + "Region");
			}
			RegionManager.getInstnce().reload();
			return true;
		} catch (DataAccessException de) {
			Log.log.error("删除区域数据库访问异常", de);
			de.printStackTrace();
		} catch (Exception e) {
			Log.log.error("删除区域异常", e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Region getRegionById(Long region_id) {
		try {
			Region region = (Region) itsRegionDao.mFindById(region_id);
			return region;
		} catch (DataAccessException de) {
			Log.log.error("通过id获取区域数据库访问异常", de);
			de.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过id获取区域异常", e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Page<Region> getRegionPageByParentId(Long parentId, String pageNum,
			String pageSize) {
		int size = Integer.valueOf(pageSize);
		int offset = size * (Integer.valueOf(pageNum) - 1);
		Page<Region> regionPage = null;
		try {
			regionPage = (Page<Region>) itsRegionDao.mPageQuery("getByParentRegionId", parentId, offset, size);
		} catch (DataAccessException de) {
			Log.log.error("通过parentId获取子区域页数据库访问异常", de);
			de.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过parentId获取子区域页数据库访问异常", e);
			e.printStackTrace();
		}
		return regionPage;
	}

	@Override
	public RegionNode getNodeByRegionId(Long regionId) {
		Region r = null;
		RegionNode rn = null;
		r = getRegionById(regionId);
		rn = new RegionNode(r, this);
		return rn;
	}

	@Override
	public List<RegionNode> getChildrenNodeList(Long region_id) {
		List<RegionNode> lrn = new ArrayList<RegionNode>();
		List<Region> childList = getRegionListByParentIdAndState(region_id, 1);
		for (Region r : childList) {
			RegionNode rn = new RegionNode(r, this);
			rn.getType();
			lrn.add(rn);
		}
		return lrn;
	}

	@Override
	public List<Region> getRegionListByParentIdAndState(Long parentRegionId,
			Integer state) {
		List<Region> regionList = null;
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("region_id", parentRegionId);
		if (state == Region.OPENED || state == Region.CLOSED)
			para.put("state", state);
		try {
			if (parentRegionId != null) {
				regionList = (List<Region>) itsRegionDao.mFind("getByParentIdAndState", para);
				return regionList;
			}
		} catch (DataAccessException de) {
			Log.log.error("通过父区域区域id和state获取区域数据库访问异常", de);
			de.printStackTrace();
		} catch (Exception e) {
			Log.log.error("通过父区域区域id和state获取区域异常", e);
			e.printStackTrace();
		}
		return null;
	}


}

/**
 * 
 */
package com.classaffairs.common.region;

import java.util.ArrayList;
import java.util.List;

import com.classaffairs.entity.Region;
import com.classaffairs.framework.core.cache.CacheHelper;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.service.RegionService;
import com.classaffairs.service.impl.RegionServiceImpl;

/**
 * @author Haozhifeng
 *
 */
public class RegionManager extends AbstractManager {
	
	private static RegionManager _instance = new RegionManager();
	//memcache key
	private static String RegionCacheString = "RegionString";
	/**
	 * 区域的根列表
	 */
	private List<RegionNode> rootList;
	/**
	 * 区域使用的服务
	 */
	private static RegionServiceImpl regionServiceImpl = new RegionServiceImpl();
	
	public static RegionService getRegionService() {
		return regionServiceImpl;
	}

	public static RegionManager getInstnce() {
		return _instance;
	}
	
	@Override
	public void reload() {
		if (RegionManager.isCacheFlag()) {
			CacheHelper.delete(RegionCacheString);
		}
		init();
	}

	@Override
	public void init() {
		//regionServiceImpl.setBaseDao(SpringContextHelper.getBaseDao());
		if (RegionManager.isCacheFlag()) {
			List<RegionNode> rootListtemp = regionServiceImpl.getChildrenNodeList(0L);
			getAll(rootListtemp);
			CacheHelper.put(RegionCacheString, rootListtemp);
		} else {
			rootList = regionServiceImpl.getChildrenNodeList(0L);
			//初始化所有的孩子节点
			getAll(rootList);
		}
	}


	/**
	 * 获得指定id的区域对象
	 * @param id
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Region getNode(Long regionId) {

		for (RegionNode r : getDataFromCahce()) {
			if (r.getRegionId().equals(regionId))
				return r.getContent();
			else {
				return findFromTopToBottomById(r.getContent(), regionId);
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List<RegionNode> getDataFromCahce() {
		if (isCacheFlag())
			return (List<RegionNode>) CacheHelper.get(RegionCacheString);
		else
			return rootList;
	}
	/**
	 * 获得指定区域id的所有上级区域 的字符串（供简化地址输入补充完整地址使用）
	 * @param id
	 * @return
	 */
	public String getParentAreasString(Long id, String delimeter) {
		String retStr = "";
		List<Region> temp = getParentAreasList(id);
		for (Region r : temp) {
			retStr = retStr + r.toString() + delimeter;
		}
		return retStr;
	}
	
	/**
	 * 获得指定区域id的所有上级区域 （供简化地址输入补充完整地址使用）
	 * @param Id 当前区域id
	 * @return 上级区域的列表 如 高新区 的上级 ：中国、陕西省、西安市
	 */
	public List<Region> getParentAreasList(Long id) {
		if (id == null)
			return null; //throw illgalArgException;
		List<Region> retList = new ArrayList<Region>();
		Region p, r = getNode(id);
		while (r != null) {
			p = r.getParent();
			if (p == null)
				return retList;
			retList.add(0, p);
			r = p;
		}
		return retList;
	}

	/**
	 * 获得指定区域id的所有直接下级区域 （供选择输入地址使用的接口）
	 * @param regionid 区域标识 如“陕西省”
	 * @return 返回直接的下级区域 如：陕西省：西安市、铜川市、宝鸡市。。。
	 */
	public List<Region> getDirectChildren(Long regionid) {
		Region r = getNode(regionid);
		if (r != null) {
			return r.getChildren();
		}
		return null;
	}

	/**
	 * 获得指定区域树下的指定区域的所有区域(给定一棵树的节点，找出这颗树上（该节点之下的）所有指定类别的节点)
	 * 例如：获得“中国” 下个所有城市 北京市、上海 、西安、咸阳、宝鸡等
	 * @param treeId 支持多国地址树中的一棵树的id或任一节点 
	 * @param categoryId 区域类型：市or 县
	 * @return
	 */
	public List<Region> getChildrenByType(Long treeId, Integer type) {
		List<Region> result = new ArrayList<Region>();

		Region root = this.getNode(treeId);
		if (null == root) {
			return null;
		} else {
			if (root.getType().equals(type)) {
				result.add(root);
				return result;
			} else {
				List<Region> subList = root.getChildren();
				for (Region sub : subList) {
					if (sub.getType().equals(type)) {
						return subList;
					} else {
						List<Region> subResult = this.getChildrenByType(sub.getRegionId(), type);
						if (null != subResult) {
							for (Region s : subResult) {
								result.add(s);
							}
						}
					}
				}
				return result;
			}
		}
	}
	


	/**
	 * 通过下级区域，获取上级区域类型为type的区域
	 */
	@SuppressWarnings("unused")
	private Region findToTop(Region r, Integer type) {
		Region p = r;
		while (p != null) {
			if (p.getType().equals(type))
				return p;
			else {
				p = p.getParent();
			}
		}
		return null;
	}



	public Long getTreeId() {
		if (null != this.rootList) {
			return this.rootList.get(0).getRegionId();
		} else {
			return null;
		}
	}

	/**
	 * 获得指定name的区域对象
	 * @param id
	 * @return
	 */
	public Region getNodeByName(String name) {

		for (RegionNode r : getDataFromCahce()) {
			if (r.getName().equals(name))
				return r.getContent();
			else {
				return this.findFromTopToBottomByName(r.getContent(), name);
			}
		}
		return null;
	}

	protected Region findFromTopToBottomByName(Region t, String key) {
		List<Region> rcList = t.getChildren();
		if (rcList.size() == 0)
			return null;
		for (Region rtemp : rcList) {
			if (rtemp != null) {
				if (rtemp.getName().equals(key)) {
					return rtemp;
				}
				Region temp = findFromTopToBottomByName(rtemp, key);
				if (temp != null)//找到 return
					return temp;
			}
		}
		return null;
	}

	/**
	 * 通过regionId查询regionName
	 * @param regionId(包括cityId和urbanId)
	 * @return regionName(包括cityName和urbanName)
	 */
	public String getRegionName(Long regionId) {
		String regionName = "";
		Region itsRegion = new Region();
		if (null != regionId && !regionId.equals("")) {
			itsRegion = RegionManager.getInstnce().getNode(regionId);
			if (null != itsRegion) {
				regionName = itsRegion.getName();
			}
		}
		return regionName;
	}


}

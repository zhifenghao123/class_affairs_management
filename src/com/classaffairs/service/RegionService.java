/**
 * 
 */
package com.classaffairs.service;

import java.util.List;

import com.classaffairs.common.region.RegionNode;
import com.classaffairs.entity.Region;
import com.classaffairs.framework.sdp.orm.query.Page;

/**
 * @author Haozhifeng
 *
 */
public interface RegionService {
	/**
	 * 由region对象插入数据
	 * @param region 区域对象
	 * @return 添加区域对象成功返回true，否则返回false
	 */
	public boolean addRegion(Region region);

	/**
	 * 更新区域对象
	 * @param region 区域对象
	 * @return 修改区域对象成功返回true，否则返回false
	 */
	public boolean updateRegion(Region region);
	/**
	 * 根据区域Id删除该区域
	 * @param region_id 区域id
	 * @return 删除成功返回true，否则返回false
	 */
	public boolean deleteRegion(Long region_id);
	/**
	 * 根据区域id从数据库获取该区域对象
	 * @param regionId 区域对象id
	 * @return Region 区域对象
	 */
	public Region getRegionById(Long region_id);
	/**
	 * 根据父区域Id获取直接子区域页
	 * @param parentId 父区域id
	 * @param pageNum 第几页
	 * @param pageSize 页面大小
	 * @return Page 区域对象页
	 */
	public Page<Region> getRegionPageByParentId(Long parentId, String pageNum, String pageSize);
	/**
	 * 通过区域id获取该区域结点
	 * @param region_id 区域id
	 * @return RegionNode 区域节点
	 */
	public RegionNode getNodeByRegionId(Long regionId);
	/**
	 * region_id==null 获得区域的根 节点
	 * if not child
	 * @param region_id 区域id
	 * @return List区域节点列表
	 */
	public List<RegionNode> getChildrenNodeList(Long regionId);
	/**
	 * 根据父区域Id和启用状态获取直接子区域列表
	 * @param parentId 父区域id
	 * @param state 启用的状态
	 * @return List 区域对象列表
	 */
	public List<Region> getRegionListByParentIdAndState(Long parentRegionId, Integer state);
}

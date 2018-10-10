package com.classaffairs.common.region;

import java.util.List;

public interface Node<T> {
	/**
	 * 获取区域节点的id
	 * @return region_id
	 */
	public Long getRegionId();

	/**
	 * 获取直接上级区域节点
	 * @return
	 */
	public T getParent();

	/**
	 * 设置父节点
	 * @param parent
	 */
	public void setParent(T parent);

	/**
	 * 获取区域的直接下级区域节点列表
	 * @return 节点类表
	 */
	public List<T> getChildren();

	/**
	 * 添加子节点
	 * @param children 子节点列表
	 */
	public void setChildren(List<T> children);

	/**
	 * 为当前节点添加一个子节点
	 * @param child
	 */
	public void addChild(T child);

	/**
	 * 从当前节点中删除一个子节点
	 * @param child
	 * @return 移除成功返回true，否则返回false
	 */
	public boolean removeChild(T child);

	/**
	 *获取直接上级区域id
	 * @return
	 */
	/**
	 * @return
	 */
	public Long getParentid();
}

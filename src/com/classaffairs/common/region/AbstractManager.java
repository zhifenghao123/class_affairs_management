/**
 * 
 */
package com.classaffairs.common.region;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Haozhifeng
 *
 */
public abstract class AbstractManager {
	
	private static boolean cacheFlag = false;//判断是否采用缓存

	public static boolean isCacheFlag() {
		return cacheFlag;
	}

	public static void setCacheFlag(boolean cacheFlag) {
		AbstractManager.cacheFlag = cacheFlag;
	}
	
	/**
	 * 重新加载
	 */
	public abstract void reload();

	public abstract void init();
	
	/**
	 * 通过一个节点列表获取所有的子节点的列表
	 * @param arg 节点列表
	 * @return 
	 */
	protected <T extends Node<T>> void getAll(List<T> arg) {
		if (arg == null)
			return;
		for (T rn : arg) {
			if (rn == null)
				return;
			getAll(rn.getChildren());
		}
	}
	
	protected <T extends Node<T>, P> T findFromTopToBottomById(T t, P key) {
		List<T> rcList = t.getChildren();
		if (rcList.size() == 0)
			return null;
		for (T rtemp : rcList) {
			if (rtemp != null) {
				if (rtemp.getRegionId().equals(key)) {
					return rtemp;
				}
				T temp = findFromTopToBottomById(rtemp, key);
				if (temp != null)//找到 return
					return temp;
			}
		}
		return null;
	}
	
	/**
	 * 获得指定id的区域对象
	 * @param id
	 * @return 区域 节点
	 */
	public abstract <T> T getNode(Long id);

	/**
	 * 为父节点添加一个子节点
	 * @param child 节点
	 * @return
	 */

	public <T extends AbstractNode<T>> void addChild(T child) {
		if (child == null)
			return;
		T pnode = getNode(child.getParentid());
		if (pnode.getChildren() == null)
			pnode.children = new ArrayList<T>();
		child.setParent(pnode);
		pnode.addChild(child);
	}

	/**
	 * 从当前节点中删除一个子节点
	 * @param child 节点
	 * @return 删除成功返回true，否则返回false
	 */
	public <T extends AbstractNode<T>, R> boolean removeChild(T child) {
		if (child == null)
			return false;
		if (child.getParentid() == null) {//删除子节点即删除所有其的所有孩子
			T pnode = getNode(child.getParentid());
			if (pnode == null)//此节点不存在异常
				return false;
			return pnode.removeChild(child);
		} else {// 根节点
			return false;
		}
	}

	/**
	 * 从缓存或静态数据中取数据入口
	 * @return 节点列表
	 */
	protected abstract <T extends Node<T>> List<T> getDataFromCahce();
	
}

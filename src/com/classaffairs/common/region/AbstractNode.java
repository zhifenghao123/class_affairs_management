package com.classaffairs.common.region;

import java.util.ArrayList;
import java.util.List;

public class AbstractNode<T extends Node<T>> implements Node<T> {

	protected Long regionId;//区域编号
	protected String name;//区域名称
	protected Long parentid;//所属大区系统编号
	protected T itsParent;
	public List<T> children;
	public AbstractNode() {
		super();
	}

	@Override
	public Long getRegionId() {
		return regionId;
	}

	@Override
	public T getParent() {
		return itsParent;
	}

	@Override
	public void setParent(T parent) {
		this.itsParent = parent;
		this.regionId = parent.getRegionId();
	}

	@Override
	public List<T> getChildren() {
		return children;
	}

	@Override
	public void setChildren(List<T> children) {
		for (T r : children) {
			r.setParent((T) this);
		}
		this.children = children;
	}

	@Override
	public void addChild(T child) {
		if (children == null)
			children = new ArrayList<T>();
		child.setParent((T) this);
		children.add(child);
	}

	@Override
	public boolean removeChild(T child) {
		if (children == null)//此节点不存在异常
			return false;
		return children.remove(child);
	}

	@Override
	public Long getParentid() {
		return parentid;
	}
	
	public int getChildAreasCount() {
		return getChildren().size();
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

}

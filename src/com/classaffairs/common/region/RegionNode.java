package com.classaffairs.common.region;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.classaffairs.entity.Region;
import com.classaffairs.framework.core.utils.Log;
import com.classaffairs.service.RegionService;

public class RegionNode extends AbstractNode<RegionNode> {
	private Region content;//C	
	transient private RegionService itsService;
	//关系
	private RegionNode parent;
	
	public RegionNode(Region r, RegionService regionService) {
		content = r;
		itsService = regionService;

	}
	
	public Region getContent() {
		return content;
	}
	public void setContent(Region content) {
		this.content = content;
	}
	public RegionService getItsService() {
		return itsService;
	}
	public void setItsService(RegionService itsService) {
		this.itsService = itsService;
	}
	public RegionNode getParent() {
		if (content.getParent() == null && content.getParentid() != null) {
			try {
				setParent(itsService.getNodeByRegionId(content.getParentid()));
			} catch (Exception e) {
				e.printStackTrace();
				Log.log.error("获得区域的上级区域异常entity.region", e);
			}
		}
		return parent;
	}
	public void setParent(RegionNode parent) {
		this.content.setParent(parent.getContent());
		this.parent = parent;
	}
	
	
	public int getChildAreasCount() {
		return content.getChildren().size();
	}

	/**
	 * 获得该区域的直接下级区域
	 * @return
	 */
	public List<RegionNode> getChildren() {
		if (content.getChildren() == null) {
			try {
				setChildren(itsService.getChildrenNodeList(this.content.getRegionId()));
			} catch (DataAccessException de) {
				Log.log.error("区域数据库访问异常entity.region", de);
			} catch (Exception e) {
				Log.log.error("获取下级区域异常entity.region", e);
			}
		}
		return children;
	}

	public void setChildren(List<RegionNode> children) {
		if (children == null)
			return;
		List<Region> lr = new ArrayList<Region>();
		for (RegionNode rn : children) {
			rn.setParent(this);
			//rn.getCategory();
			lr.add(rn.getContent());
		}
		this.content.setChildren(lr);
		this.children = children;
	}

	public Long getRegionId() {
		return content.getRegionId();
	}

	public void setRegionId(Long regionId) {
		this.content.setRegionId(regionId);
	}

	public String getCode() {
		return content.getCode();
	}

	public void setCode(String code) {
		this.content.setCode(code);
	}

	public String getName() {
		return content.getName();
	}

	public void setName(String name) {
		this.content.setName(name);
	}

	public String getEnglishname() {
		return content.getEnglishName();
	}

	public void setEnglishname(String englishName) {
		this.content.setEnglishName(englishName);
	}

	public Integer getType() {
		return content.getType();
	}

	public void setType(Integer type) {
		this.content.setType(type);
	}

	public Long getParentid() {
		return content.getParentid();
	}

	public void setParentid(Long parentid) {
		this.content.setParentid(parentid);
	}
}

/**
 * 
 */
package com.classaffairs.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 后台管理页面导航树的tree模型
 * @author Haozhifeng
 *
 */
public class UITreeNode implements Serializable {
	
	private static final long serialVersionUID = -1017170409874432442L;

	private String id;//节点ID

	private String text;//显示节点文本

	private String state = "open";//节点状态,默认为'关闭'

	private List<UITreeNode> child;//子节点，一个节点数组声明了若干节点

	private Map<String, Object> attributes;//被添加到节点的自定义属性

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<UITreeNode> getChild() {
		return child;
	}

	public void setChild(List<UITreeNode> child) {
		this.child = child;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}

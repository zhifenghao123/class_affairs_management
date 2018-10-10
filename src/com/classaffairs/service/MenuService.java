package com.classaffairs.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.classaffairs.entity.UITreeNode;

/**
 * @author Haozhifeng
 *
 */
public interface MenuService {
	
	public List<UITreeNode> tree(String id, HttpServletRequest request);
	
}

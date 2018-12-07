package com.pkms.sys.tree.service;

import java.util.List;

import com.pkms.sys.tree.model.TreeModel;


public interface TreeServiceIf {

	public List<TreeModel> readList(TreeModel treeModel) throws Exception;

	public TreeModel read(TreeModel treeModel) throws Exception;

	public void create(TreeModel treeModel) throws Exception;

	public int update(TreeModel treeModel) throws Exception;

	public int delete(TreeModel treeModel) throws Exception;
	
}

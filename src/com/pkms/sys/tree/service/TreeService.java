package com.pkms.sys.tree.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.sys.tree.dao.TreeDao;
import com.pkms.sys.tree.model.TreeModel;


@Service("TreeService")
public class TreeService implements TreeServiceIf{
	
	@Resource(name = "TreeDao")
	private TreeDao treeDao;
	
	@Override
	public List<TreeModel> readList(TreeModel treeModel) throws Exception {
		return (List<TreeModel>) treeDao.readList(treeModel);
	}

	@Override
	public TreeModel read(TreeModel treeModel) throws Exception {
		return treeDao.read(treeModel);
	}
	
	@Override
	public void create(TreeModel treeModel) throws Exception {
//		return
				treeDao.create(treeModel);
	}

	@Override
	public int update(TreeModel treeModel) throws Exception {
		return treeDao.update(treeModel);
		
	}

	@Override
	public int delete(TreeModel treeModel) throws Exception {
		return treeDao.delete(treeModel);
	}	

	
	
	
	
}

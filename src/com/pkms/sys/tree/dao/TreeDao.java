package com.pkms.sys.tree.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sys.tree.model.TreeModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("TreeDao")
public class TreeDao extends IbatisAbstractDAO{

	
	public List<TreeModel> readList(TreeModel treeModel) throws Exception{
		return (List<TreeModel>) readList("Tree.list", treeModel);
	}

	public TreeModel read(TreeModel treeModel) {
		return (TreeModel) read("Tree.select", treeModel);
	}

	public void create(TreeModel treeModel) {
//		return (Integer) 
				create("Tree.insert", treeModel);
	}

	public int update(TreeModel treeModel) {
		return (Integer) update("Tree.update", treeModel);
	}

	public int delete(TreeModel treeModel) {
		return (Integer) delete("Tree.delete", treeModel);
	}

	
}

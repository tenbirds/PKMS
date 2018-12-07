package com.pkms.sys.tree.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.sys.tree.model.TreeModel;
import com.pkms.sys.tree.service.TreeServiceIf;

@Controller
public class TreeController {
	
	@Resource(name = "TreeService")
	private TreeServiceIf treeService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	
	@RequestMapping(value = "/sys/tree/Tree_ReadList.do")
	public String readList(TreeModel treeModel, Model model) throws Exception {
		return "/sys/tree/Tree_ReadList";
	}
	
	
	@RequestMapping(value="/sys/tree/Tree_Ajax_Read.do")
	@ResponseBody
	public List<TreeModel> Tree_Ajax_Read(TreeModel treeModel) throws Exception{
		return (List<TreeModel>) treeService.readList(treeModel);
	}

	@RequestMapping(value="/sys/tree/Tree_Create.do")
	@ResponseBody
	public void Tree_Create(TreeModel treeModel) throws Exception{
		treeService.create(treeModel);
	}
	
	@RequestMapping(value="/sys/tree/Tree_Update.do")
	@ResponseBody
	public int Tree_Update(TreeModel treeModel) throws Exception{
		return treeService.update(treeModel);
	}
	
	@RequestMapping(value="/sys/tree/Tree_Delete.do")
	@ResponseBody
	public int Tree_Delete(TreeModel treeModel) throws Exception{
		return treeService.delete(treeModel);
	}
	
	
	
}

package com.pkms.sys.group1.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group1.service.Group1ServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Controller
public class Group1AjaxController {

	@Resource(name = "Group1Service")
	private Group1ServiceIf group1Service;

	@RequestMapping(value = "/sys/group1/Group1_Ajax_Create.do")
	public String create(SysModel sysModel, Model model) throws Exception {
		SysModel sysModelData = group1Service.create(sysModel);
		return ResultUtil.handleSuccessResultParam(model, sysModelData.getGroup1_seq(), "");
	}

	@RequestMapping(value = "/sys/group1/Group1_Ajax_Read.do")
	public String read(SysModel sysModel, Model model) throws Exception {

		SysModel sysModelData = new SysModel();

		if (StringUtils.hasLength(sysModel.getGroup1_seq())) {
			sysModelData = group1Service.read(sysModel);
		}

		model.addAttribute("SysModel", sysModelData);

		return "/sys/group1/Group1_Ajax_Read";
	}

	@RequestMapping(value = "/sys/group1/Group1_Ajax_ReadList.do")
	public String readList(SysModel sysModel, Model model) throws Exception {

		List<SysModel> sysModelList = group1Service.readList(sysModel);
		sysModel.setTotalCount(sysModelList.size());

		model.addAttribute("SysModel", sysModel);
		model.addAttribute("SysModelList", sysModelList);

		return "/sys/group1/Group1_Ajax_ReadList";
	}

	@RequestMapping(value = "/sys/group1/Group1_Ajax_Update.do")
	public String update(SysModel sysModel, Model model) throws Exception {
		group1Service.update(sysModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/sys/group1/Group1_Ajax_Delete.do")
	public String delete(SysModel sysModel, Model model) throws Exception {
		group1Service.delete(sysModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/sys/group1/Group1_Ajax_systemSearch.do")
	public String searchList(SysModel sysModel, Model model) throws Exception {
		List<SysModel> searchList = group1Service.searchList(sysModel);
		model.addAttribute("SysModel", sysModel);
		model.addAttribute("SearchList", searchList);
		return "/sys/group1/Group1_Ajax_systemSearch";
	}

}

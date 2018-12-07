package com.pkms.common.login.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;

/**
 * 
 * @author : skywarker
 * @Date : 2012. 4. 23.
 * 
 */
@Controller
public class BpAddUserAjaxController {

	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;

	@RequestMapping(value = "/usermg/user/BpAddUser_Ajax_Read.do")
	public String read(BpModel bpModel, Model model) throws Exception {

		BpUserModel bpUserModel = bpModel.createBpUserModel();

		BpUserModel bpUserModelData = new BpUserModel();

		if (StringUtils.hasLength(bpUserModel.getBp_user_id())) {
			bpUserModelData = bpUserService.read(bpUserModel);
		}

		bpModel.updateBpUserModel(bpUserModelData);

		model.addAttribute("BpModel", bpModel);

		return "/usermg/user/BpAddUser_Ajax_Read";
	}
	
	
	@RequestMapping(value = "/usermg/user/BpAddUser_Ajax_UserAdd.do")
	public String UserAdd(BpModel bpModel, Model model) throws Exception {

		BpUserModel bpUserModel = bpModel.createBpUserModel();

		BpUserModel bpUserModelData = new BpUserModel();

		if (StringUtils.hasLength(bpUserModel.getBp_user_id())) {
			bpUserModelData = bpUserService.read(bpUserModel);
		}

		bpModel.updateBpUserModel(bpUserModelData);

		model.addAttribute("BpModel", bpModel);

		return "/usermg/user/BpAddUser_Ajax_UserAdd";
	}

}

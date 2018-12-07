package com.pkms.common.top.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.top.service.MyBpUserServiceIf;
import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.user.model.BpUserModel;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 31.
 * 
 */
@Controller
public class MyBpUserController {

	@Resource(name = "MyBpUserService")
	private MyBpUserServiceIf myBpUserService;

	@RequestMapping(value = "/common/top/MyBpAddUser_Ajax_Read.do")
	public String read(BpModel bpModel, Model model) throws Exception {

		BpUserModel bpUserModel = bpModel.createBpUserModel();

		BpUserModel bpUserModelData = new BpUserModel();

		if (StringUtils.hasLength(bpUserModel.getBp_user_id())) {
			bpUserModelData = myBpUserService.read(bpUserModel);
		}

		bpModel.updateBpUserModel(bpUserModelData);

		model.addAttribute("BpModel", bpModel);

		return "/usermg/user/BpAddUser_Ajax_Read";
	}

}

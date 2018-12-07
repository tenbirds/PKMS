package com.pkms.usermg.user.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
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
public class BpUserAjaxDuplicateController {

	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;

	@RequestMapping(value = "/usermg/user/BpUser_Ajax_Duplicate_Read.do")
	public String readDuplicate(BpModel bpModel, Model model) throws Exception {

		BpUserModel bpUserModel = bpModel.createBpUserModel();

		if (!StringUtils.hasLength(bpUserModel.getBp_user_id())) {
			throw new Exception("유효하지 않은 아이디 입니다.");
		}

		int count = bpUserService.readCount(bpUserModel);

		if (count > 0) {
			return ResultUtil.handleSuccessResultParam(model, "false", "");
		}
		return ResultUtil.handleSuccessResultParam(model, "true", "");

	}

}

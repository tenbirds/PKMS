package com.pkms.usermg.auth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.usermg.auth.model.AuthUserModel;
import com.pkms.usermg.auth.service.AuthUserServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 20.
 * 
 */
@Controller
public class AuthUserAjaxController {

	@Resource(name = "AuthUserService")
	private AuthUserServiceIf authUserService;

	@RequestMapping(value = "/usermg/auth/AuthUser_Ajax_ReadTree.do")
	public String readTree(AuthUserModel authUserModel, Model model) throws Exception {

		authUserService.read(authUserModel);

		return ResultUtil.handleSuccessResultParam(model, authUserModel.getTreeScript(), "");
	}

	@RequestMapping(value = "/usermg/auth/AuthUser_Ajax_ReadList.do")
	public String readList(AuthUserModel authUserModel, Model model) throws Exception {

		List<?> authUserList = authUserService.readList(authUserModel);

		authUserModel.setTotalCount(authUserList.size());
		model.addAttribute("AuthUserModel", authUserModel);
		
		model.addAttribute("authUserList", authUserList);

		return "/usermg/auth/AuthUser_Ajax_ReadList";
	}

	
	@RequestMapping(value = "/usermg/auth/AuthUser_Ajax_Update.do")
	public String update(AuthUserModel authUserModel, Model model) throws Exception {
		authUserService.update(authUserModel);
		return ResultUtil.handleSuccessResult();
	}

}

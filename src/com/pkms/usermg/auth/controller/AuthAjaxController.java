package com.pkms.usermg.auth.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.usermg.auth.model.AuthModel;
import com.pkms.usermg.auth.service.AuthServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 20.
 * 
 */
@Controller
public class AuthAjaxController {

	@Resource(name = "AuthService")
	private AuthServiceIf authService;

	@RequestMapping(value = "/usermg/auth/Auth_Ajax_ReadList.do")
	public String readList(AuthModel authModel, Model model) throws Exception {

		authService.read(authModel);

		return ResultUtil.handleSuccessResultParam(model, authModel.getTreeScript(), "");
	}

	@RequestMapping(value = "/usermg/auth/Auth_Ajax_Update.do")
	public String update(AuthModel authModel, Model model) throws Exception {
		authService.update(authModel);
		return ResultUtil.handleSuccessResult();
	}

}

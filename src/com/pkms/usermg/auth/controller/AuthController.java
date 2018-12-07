package com.pkms.usermg.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.org.model.OrgModel.ORG_OPTION;
import com.pkms.usermg.auth.model.AuthModel;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 20.
 * 
 */
@Controller
public class AuthController {

	@RequestMapping(value = "/usermg/auth/Auth_Read.do")
	public String readList(AuthModel authModel, Model model) throws Exception {

		authModel.setOption(ORG_OPTION.ALL);
		model.addAttribute("AuthModel", authModel);

		return "/usermg/auth/Auth_Read";
	}

}

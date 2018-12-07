package com.pkms.usermg.auth.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.usermg.auth.model.AuthModel;
import com.pkms.usermg.auth.model.AuthUserModel;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 20.
 * 
 */
@Controller
public class AuthUserController {

	@RequestMapping(value = "/usermg/auth/AuthUser_Read.do")
	public String readList(AuthUserModel authUserModel, Model model) throws Exception {
		
		model.addAttribute("AuthUserModel", authUserModel);
		model.addAttribute("AuthUserModelList", new ArrayList<AuthModel>());

		return "/usermg/auth/AuthUser_Read";
	}

}

package com.pkms.usermg.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.usermg.user.model.SktUserModel;

/**
 * 
 * @author : skywarker
 * @Date : 2012. 5. 11.
 * 
 */
@Controller
public class SktUserBpPopupController {

	@RequestMapping(value = "/usermg/user/SktUserBp_Popup_Read.do")
	public String read(SktUserModel sktUserModel, Model model) throws Exception {

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("SktUserModel", sktUserModel);

		return "/usermg/user/SktUserBp_Popup_Read";
	}

}

package com.pkms.sys.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.service.SystemPopupServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 11.
 * 
 */
@Controller
public class SystemAuthPopupController {

	@Resource(name = "SystemPopupService")
	private SystemPopupServiceIf systemPopupService;


	@RequestMapping(value = "/sys/system/SystemAuth_Popup_Read.do")
	public String read(SysModel sysModel, Model model) throws Exception {
		sysModel.setCheck_auth("Y");
		sysModel.setSystem_popup_gubun("");
		systemPopupService.read(sysModel);
		model.addAttribute("SysModel", sysModel);
		return "/sys/system/SystemAuth_Popup_Read";
	}


}

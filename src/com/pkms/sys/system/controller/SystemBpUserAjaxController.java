package com.pkms.sys.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.service.SystemBpUserServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Controller
public class SystemBpUserAjaxController {

	@Resource(name = "SystemBpUserService")
	private SystemBpUserServiceIf systemBpUserService;


	@RequestMapping(value = "/sys/system/SystemBpUser_Ajax_Read.do")
	public String read(SysModel sysModel, Model model) throws Exception {

		model.addAttribute("SysModel", sysModel);
		
		return "/sys/system/SystemBpUser_Ajax_Read";
	}

	@RequestMapping(value = "/sys/system/SystemBpUser_Ajax_ReadList.do")
	public String readList(SysModel sysModel, Model model) throws Exception {

		List<?> sysModelList = systemBpUserService.readList(sysModel);
		sysModel.setTotalCount(sysModelList.size());

		model.addAttribute("SysModel", sysModel);
		model.addAttribute("SysModelList", sysModelList);

		return "/sys/system/SystemBpUser_Ajax_ReadList";
	}

	@RequestMapping(value = "/sys/system/System_Popup_Bp.do")
	public String readUserPopup(SysModel sysModel, Model model) throws Exception {
		
		model.addAttribute("SysModel", sysModel);
		
		return "/sys/system/System_Popup_Bp";
	}

}

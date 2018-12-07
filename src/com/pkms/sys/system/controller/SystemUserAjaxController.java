package com.pkms.sys.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.service.SystemUserServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Controller
public class SystemUserAjaxController {

	@Resource(name = "SystemUserService")
	private SystemUserServiceIf systemUserService;

	@RequestMapping(value = "/sys/system/SystemUser_Ajax_Read.do")
	public String read(SysModel sysModel, Model model) throws Exception {

		SysModel sysModelData = systemUserService.read(sysModel);
		model.addAttribute("SysModel", sysModelData);
		
		return "/sys/system/SystemUser_Ajax_Read";
	}

	@RequestMapping(value = "/sys/system/SystemUser_Ajax_ReadList.do")
	public String readList(SysModel sysModel, Model model) throws Exception {

		List<?> sysModelList = systemUserService.readListSearchUser(sysModel);
		sysModel.setTotalCount(sysModelList.size());

		model.addAttribute("SysModel", sysModel);
		model.addAttribute("SysModelList", sysModelList);

		return "/sys/system/SystemUser_Ajax_ReadList";
	}

	@RequestMapping(value = "/sys/system/System_Popup_SKTUser.do")
	public String readUserPopup(SysModel sysModel, Model model) throws Exception {
		SysModel sysModelData = systemUserService.read(sysModel);
		if(null != sysModel.getSystem_seq()){			
			sysModelData.setSystem_seq(sysModel.getSystem_seq());
			sysModelData.setSel_id(sysModel.getSel_id());
			sysModelData.setSel_ids(sysModel.getSel_ids());
			sysModelData.setSel_names(sysModel.getSel_names());
			sysModelData.setSelect_system_user_id(sysModel.getSelect_system_user_id());
		}

		model.addAttribute("SysModel", sysModelData);
		
		return "/sys/system/System_Popup_User";
	}

}

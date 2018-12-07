package com.pkms.sys.group1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.sys.common.model.SysModel;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Controller
public class Group1Controller {

	@RequestMapping(value = "/sys/group1/Group1_ReadList.do")
	public String readList(SysModel sysModel, Model model) throws Exception {

		model.addAttribute("SysModel", sysModel);

		return "/sys/group1/Group1_ReadList";
	}

}

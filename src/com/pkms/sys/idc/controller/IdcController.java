package com.pkms.sys.idc.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.sys.idc.model.IdcModel;
import com.pkms.sys.idc.service.IdcServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 11.
 * 
 */
@Controller
public class IdcController {

	@Resource(name = "IdcService")
	private IdcServiceIf idcService;

	@RequestMapping(value = "/sys/idc/Idc_Popup_ReadList.do")
	public String readList(IdcModel idcModel, Model model) throws Exception {

		List<?> idcModelList = idcService.readList(idcModel);

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("IdcModel", idcModel);
		model.addAttribute("IdcModelList", idcModelList);

		return "/sys/idc/Idc_Popup_ReadList";
	}

}

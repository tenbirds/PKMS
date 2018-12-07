package com.pkms.usermg.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.org.model.OrgModel.ORG_OPTION;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.SktUserPopupServiceIf;

/**
 * 
 * @author : skywarker
 * @Date : 2012. 5. 11.
 * 
 */
@Controller
public class SktManagerUserPopupController {

	@Resource(name = "SktUserPopupService")
	private SktUserPopupServiceIf sktUserPopupService;

	@RequestMapping(value = "/usermg/user/SktManagerUser_Popup_Read.do")
	public String read(SktUserModel sktUserModel, Model model) throws Exception {

		sktUserModel.setOption(ORG_OPTION.MANAGER_GROUP_ONLY);
		SktUserModel sktUserModelData = sktUserPopupService.read(sktUserModel);
		model.addAttribute("SktUserModel", sktUserModelData);

		/*
		 * UI 모델 세팅
		 */
		return "/usermg/user/SktManagerUser_Popup_Read";
	}

	@RequestMapping(value = "/usermg/user/SktManagerUser_Ajax_ReadList.do")
	public String readList(SktUserModel sktUserModel, Model model) throws Exception {
		if(!"".equals(sktUserModel.getSearchKeyword()) && sktUserModel.getSearchKeyword() != null){			
			sktUserModel.setOption(ORG_OPTION.MANAGER_GROUP_ONLY);
//		List<?> sktUserModelList = sktUserPopupService.readListManager(sktUserModel);
			List<?> sktUserModelList = sktUserPopupService.readListAuth_boan(sktUserModel);
			sktUserModel.setTotalCount(sktUserModelList.size());
			
			model.addAttribute("SktUserModelList", sktUserModelList);
		}else{
			throw new Exception("error.biz.검색 할 단어를 입력하세요.");
		}
		
		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("SktModel", sktUserModel);

		return "/usermg/user/SktManagerUser_Ajax_ReadList";
	}

}

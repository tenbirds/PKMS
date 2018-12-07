package com.pkms.usermg.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pkms.common.util.ResultUtil;
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
public class SktUserPopupController {

	@Resource(name = "SktUserPopupService")
	private SktUserPopupServiceIf sktUserPopupService;

	@RequestMapping(value = "/usermg/user/SktUser_Popup_Read.do")
	public String read(SktUserModel sktUserModel, Model model, 
			@RequestParam(value="count", required = false, defaultValue="5") int count) throws Exception {

		sktUserModel.setOption(ORG_OPTION.AUTH_GROUP_ONLY);
		SktUserModel sktUserModelData = sktUserPopupService.read(sktUserModel);
		model.addAttribute("SktUserModel", sktUserModelData);

		/*
		 * UI 모델 세팅
		 */
		return "/usermg/user/SktUser_Popup_Read";
	}
	
	//read+부서 선택 가능 페이지
	@RequestMapping(value = "/usermg/user/SktUser_Popup_Read_Detail.do")
	public String read_detail(SktUserModel sktUserModel, Model model, 
			@RequestParam(value="count", required = false, defaultValue="5") int count) throws Exception {

		sktUserModel.setOption(ORG_OPTION.AUTH_GROUP_ONLY);
		SktUserModel sktUserModelData = sktUserPopupService.read(sktUserModel);
		model.addAttribute("SktUserModel", sktUserModelData);

		/*
		 * UI 모델 세팅
		 */
		return "/usermg/user/SktUser_Popup_Read_detail";
	}

	//read+부서 선택 가능 페이지
		@RequestMapping(value = "/usermg/user/SktUser_Chart_Popup_Read_Detail.do")
		public String read_detail2(SktUserModel sktUserModel, Model model, 
				@RequestParam(value="count", required = false, defaultValue="5") int count) throws Exception {

			sktUserModel.setOption(ORG_OPTION.AUTH_GROUP_ONLY);
			SktUserModel sktUserModelData = sktUserPopupService.read(sktUserModel);
			model.addAttribute("SktUserModel", sktUserModelData);

			/*
			 * UI 모델 세팅
			 */
			return "/usermg/user/SktUser_Popup_Chart_Read_detail";
		}
		
	@RequestMapping(value = "/usermg/user/SktUser_Ajax_ReadList.do")
	public String readList(SktUserModel sktUserModel, Model model) throws Exception {

		sktUserModel.setOption(ORG_OPTION.AUTH_GROUP_ONLY);
		List<?> sktUserModelList = sktUserPopupService.readListAuth_boan(sktUserModel);
		sktUserModel.setTotalCount(sktUserModelList.size());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("SktModel", sktUserModel);
		model.addAttribute("SktUserModelList", sktUserModelList);

		return "/usermg/user/SktUser_Ajax_ReadList";
	}
	
	@RequestMapping(value = "/usermg/user/SktUser_Ajax_ReadEmpno.do")
	public String readEmpno(SktUserModel sktUserModel, Model model) throws Exception {
		String empno ="";
		sktUserModel = sktUserPopupService.readEmpno(sktUserModel);
		empno = sktUserModel.getEmpno();
		return ResultUtil.handleSuccessResultParam(model,empno,empno);
	}

}

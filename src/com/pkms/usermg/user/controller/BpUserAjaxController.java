package com.pkms.usermg.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.bp.model.BpModel.USE_YN;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;

/**
 * 
 * @author : skywarker
 * @Date : 2012. 4. 23.
 * 
 */
@Controller
public class BpUserAjaxController {

	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;

	@RequestMapping(value = "/usermg/user/BpUser_Ajax_Create.do")
	public String create(BpModel bpModel, Model model) throws Exception {

		BpUserModel bpUserModel = bpModel.createBpUserModel();

		bpUserService.create(bpUserModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/usermg/user/BpUser_Ajax_Read.do")
	public String read(BpModel bpModel, Model model) throws Exception {

		BpUserModel bpUserModel = bpModel.createBpUserModel();

		BpUserModel bpUserModelData = new BpUserModel();

		if (StringUtils.hasLength(bpUserModel.getBp_user_id())) {
			bpUserModelData = bpUserService.read(bpUserModel);
		}

		bpModel.updateBpUserModel(bpUserModelData);

		model.addAttribute("BpModel", bpModel);

		return "/usermg/user/BpUser_Ajax_Read";
	}

	@RequestMapping(value = "/usermg/user/BpUser_Ajax_ReadList.do")
	public String readList(BpModel bpModel, Model model) throws Exception {

		/*
		 * 서비스로 부터 목록 정보 조회
		 */
		BpUserModel bpUserModel = bpModel.createBpUserModel();
		bpUserModel.setUse_yn(USE_YN.YES.getCode());
		List<?> bpUserModelList = bpUserService.readList(bpUserModel);

		/*
		 * UI 모델 세팅
		 */
		bpModel.setTotalCount(bpUserModelList.size());
		model.addAttribute("BpModel", bpModel);
		model.addAttribute("BpUserModelList", bpUserModelList);

		return "/usermg/user/BpUser_Ajax_ReadList";
	}

	@RequestMapping(value = "/usermg/user/BpUser_Ajax_Update.do")
	public String update(BpUserModel bpUserModel, Model model) throws Exception {

		bpUserService.update(bpUserModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/usermg/user/BpUser_Ajax_Delete.do")
	public String delete(BpUserModel bpUserModel, Model model) throws Exception {
		bpUserModel.setUse_yn(USE_YN.NO.getCode());
		bpUserService.updateYN(bpUserModel);
		
		//협력업체 담당자 삭제시 해당 시스템의 담당자 목록 동시 삭제 0826 - ksy
		bpUserService.deleteDamdang(bpUserModel);
		
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/usermg/bp/Bp_Popup_System_ReadList.do")
	public String bp_sys_readList(BpModel bpModel, Model model) throws Exception {
		
		BpUserModel bpUserModel = bpModel.createBpUserModel();

		List<?> bpUserModelList = bpUserService.bp_sys_readList(bpUserModel);
		
//		bpModel.updateBpUserModel(bpUserModelData);
		
		bpModel.setTotalCount(bpUserModelList.size());
		model.addAttribute("BpModel", bpModel);
		model.addAttribute("BpUserModelList", bpUserModelList);
		
		return "/usermg/bp/Bp_Popup_System_ReadList";
	}
	
}

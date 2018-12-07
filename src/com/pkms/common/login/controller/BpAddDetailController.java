package com.pkms.common.login.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.pkmscode.service.PkmsCodeServiceIf;
import com.pkms.common.util.CodeUtil;
import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.bp.service.BpServiceIf;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : 009
 * @Date : 2012. 5. 7.
 * 
 */
@Controller
public class BpAddDetailController {

	@Resource(name = "PkmsCodeService")
	private PkmsCodeServiceIf pkmsCodeService;

	@Resource(name = "BpService")
	private BpServiceIf bpService;

	@RequestMapping("/common/login/BpAddDetail_Popup_Read.do")
	public String read(BpModel bpModel, Model model) throws Exception {
		
		String bp_num_temp = bpModel.getBp_num_temp();
		
		BpModel bpModelData = bpService.read(bpModel);
		bpModelData.setBp_num_temp(bp_num_temp);
		
		// 사용여부 코드 값 세팅
		bpModelData.setUseYnItems(CodeUtil.convertCodeModel(pkmsCodeService.readList("USE")));

		model.addAttribute("BpModel", bpModelData);

		return "/usermg/bp/BpAddDetail_Popup_Read";
	}
	
	@RequestMapping("/common/login/BpAddDetail_Popup_UserAdd.do")
	public String userAdd(BpModel bpModel, Model model) throws Exception {
		
		String bp_num_temp = bpModel.getBp_num_temp();
		
		BpModel bpModelData = bpService.read(bpModel);
		bpModelData.setBp_num_temp(bp_num_temp);
		
		// 사용여부 코드 값 세팅
		bpModelData.setUseYnItems(CodeUtil.convertCodeModel(pkmsCodeService.readList("USE")));

		model.addAttribute("BpModel", bpModelData);

		return "/usermg/bp/BpAddDetail_Popup_UserAdd";
	}

}

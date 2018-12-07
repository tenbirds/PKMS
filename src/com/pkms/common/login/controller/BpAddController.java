package com.pkms.common.login.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.pkmscode.service.PkmsCodeServiceIf;
import com.pkms.common.util.CodeUtil;
import com.pkms.usermg.bp.model.BpModel;

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
public class BpAddController {

	@Resource(name = "PkmsCodeService")
	private PkmsCodeServiceIf pkmsCodeService;

	@RequestMapping("/common/login/BpAdd_Popup_Read.do")
	public String read(BpModel bpModel, Model model) throws Exception {
		BpModel bpModelData = new BpModel();

		// 사용여부 코드 값 세팅
		bpModelData.setUseYnItems(CodeUtil.convertCodeModel(pkmsCodeService.readList("USE")));

		model.addAttribute("BpModel", bpModelData);
		
		return "/usermg/bp/BpAdd_Popup_Read";
	}
	
}

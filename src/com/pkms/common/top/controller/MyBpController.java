package com.pkms.common.top.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.pkmscode.service.PkmsCodeServiceIf;
import com.pkms.common.top.service.MyBpServiceIf;
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
public class MyBpController {

	@Resource(name = "MyBpService")
	private MyBpServiceIf myBpService;

	@Resource(name = "PkmsCodeService")
	private PkmsCodeServiceIf pkmsCodeService;

	@RequestMapping("/common/top/MyBp_Read.do")
	public String read(BpModel bpModel, Model model) throws Exception {
		
		BpModel bpModelData = myBpService.read(bpModel);
		// 업체등록 요청 화면과 동일하게 사용하기 위하여 임시값 세팅
		bpModelData.setBp_num_temp(bpModel.getBp_num());
		
		
		// 사용여부 코드 값 세팅
		bpModelData.setUseYnItems(CodeUtil.convertCodeModel(pkmsCodeService.readList("USE")));

		model.addAttribute("BpModel", bpModelData);
		
		return "/usermg/bp/BpAddDetail_Popup_Read";
	}
	
}

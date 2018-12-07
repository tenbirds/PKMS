package com.pkms.common.login.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.bp.model.BpModel.BP_APPROVAL_STATE;
import com.pkms.usermg.bp.service.BpServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 25.
 * 
 */
@Controller
public class BpAddAjaxController {

	@Resource(name = "BpService")
	private BpServiceIf bpService;

	@RequestMapping(value = "/usermg/bp/BpAdd_Ajax_Duplicate_Read.do")
	public String read(BpModel bpModel, Model model) throws Exception {

		if (!StringUtils.hasLength(bpModel.getBp_num())) {
			throw new Exception("유효하지 않은 사업자등록번호 입니다.");
		}

		int count = bpService.readCount(bpModel);

		if (count == 0) {
			return ResultUtil.handleSuccessResultParam(model, "true", "");
		}
		
		bpModel = bpService.read(bpModel);
		if(BP_APPROVAL_STATE.TEMP.getCode().equals(bpModel.getApproval_state())){
			return ResultUtil.handleSuccessResultParam(model, "temp", "");
			
		}else if("N".equals(bpModel.getUse_yn())){
			return ResultUtil.handleSuccessResultParam(model, "disabled", "");
		}
		
		return ResultUtil.handleSuccessResultParam(model, "false", "");
	}

}

package com.pkms.usermg.bp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.bp.service.BpServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 20.
 * 
 */
@Controller
public class BpAjaxController {

	@Resource(name = "BpService")
	private BpServiceIf bpService;

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@RequestMapping(value = "/usermg/bp/Bp_Ajax_Create.do")
	public String create(BpModel BpModel, Model model) throws Exception {
		bpService.create(BpModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/usermg/bp/Bp_Ajax_Duplicate_Read.do")
	public String read(BpModel bpModel, Model model) throws Exception {

		if (!StringUtils.hasLength(bpModel.getBp_num())) {
			throw new Exception("유효하지 않은 사업자등록번호 입니다.");
		}

		int count = bpService.readCount(bpModel);

		if (count > 0) {
			return ResultUtil.handleSuccessResultParam(model, "false", "");
		}
		return ResultUtil.handleSuccessResultParam(model, "true", "");
	}

	@RequestMapping(value = "/usermg/bp/Bp_Popup_ReadList.do")
	public String readList(BpModel bpModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(bpModel);

		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> BpModelList = bpService.readListAll(bpModel);
		paginationInfo.setTotalRecordCount(bpModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("BpModel", bpModel);
		model.addAttribute("BpModelList", BpModelList);

		return "/usermg/bp/Bp_Popup_ReadList";
	}

	@RequestMapping(value = "/usermg/bp/Bp_Ajax_Update.do")
	public String update(BpModel bpModel, Model model) throws Exception {
		bpService.update(bpModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/usermg/bp/Id_Find_Ajax_Read.do")
	public String id_find(BpModel bpModel, Model model) throws Exception {
		List<BpModel> BpIdList = bpService.findIdList(bpModel);
		
		model.addAttribute("BpIdList", BpIdList);
		return "/common/Id_Find_Ajax_Read";
	}
	
	@RequestMapping(value = "/usermg/bp/Pass_Ajax_Update.do")
	public String pass_update(BpModel bpModel, Model model) throws Exception {
		bpService.pass_update(bpModel);
		return ResultUtil.handleSuccessResult();
	}
}

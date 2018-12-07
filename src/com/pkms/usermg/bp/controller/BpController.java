package com.pkms.usermg.bp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.pkmscode.service.PkmsCodeServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.CodeUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
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
public class BpController {

	@Resource(name = "BpService")
	private BpServiceIf bpService;

	@Resource(name = "PkmsCodeService")
	private PkmsCodeServiceIf pkmsCodeService;

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@RequestMapping(value = "/usermg/bp/Bp_Read.do")
	public String read(BpModel bpModel, Model model) throws Exception {
		BpModel bpModelData = bpService.read(bpModel);

		// 사용여부 코드 값 세팅
		bpModelData.setUseYnItems(CodeUtil.convertCodeModel(pkmsCodeService.readList("USE")));

		/*
		 * 검색 조건, 페이징 세팅
		 */
		bpModelData.setSearchFilter(bpModel.getSearchFilter());
		bpModelData.setSearchCondition(bpModel.getSearchCondition());
		bpModelData.setSearchKeyword(bpModel.getSearchKeyword());
		bpModelData.setPageIndex(bpModel.getPageIndex());

		bpModelData.setSession_user_id(bpModel.getSession_user_id());
		bpModelData.setSession_user_name(bpModel.getSession_user_name());
		
		model.addAttribute("BpModel", bpModelData);

		return "/usermg/bp/Bp_Read";
	}

	@RequestMapping(value = "/usermg/bp/Bp_ReadList.do")
	public String readList(BpModel bpModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(bpModel);

		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> BpModelList = bpService.readList(bpModel);
		paginationInfo.setTotalRecordCount(bpModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("BpModel", bpModel);
		model.addAttribute("BpModelList", BpModelList);

		return "/usermg/bp/Bp_ReadList";
	}
	
	@RequestMapping(value = "/usermg/bp/Password_Popup_Search.do")
	public String PassPopup(HttpServletRequest request, Model model) throws Exception {
		String url_gubun = "";
		url_gubun = request.getParameter("url_gubun");
		
		model.addAttribute("url_gubun", url_gubun);
		return "/common/Password_Popup_Search";
	}
	
}

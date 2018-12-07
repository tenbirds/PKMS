package com.pkms.newpncrmg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.newpncrmg.model.NewpncrModel;
import com.pkms.newpncrmg.service.NewpncrDuplicateCheckServiceIf;

@Controller
public class NewpncrDuplicateCheckController {
	
	@Resource(name = "NewpncrDuplicateCheckService")
	private NewpncrDuplicateCheckServiceIf newpncrDuplicateCheckservice;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	
	@RequestMapping(value = "/newpncrmg/Newpncr_Duplication_Check_Popup_ReadList.do")
	public String readList(NewpncrModel newpncrModel, Model model) throws Exception{
		
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(newpncrModel);
		
		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> newpncrDuplicateCheckModelList = null;
		if(newpncrModel.getTitle() != null){
			newpncrDuplicateCheckModelList = newpncrDuplicateCheckservice.readList(newpncrModel);
			paginationInfo.setTotalRecordCount(newpncrModel.getTotalCount());
		}
		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("newpncrDuplicateCheckModelList", newpncrDuplicateCheckModelList);
		model.addAttribute("NewpncrModel", newpncrModel);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "/newpncrmg/Newpncr_Duplication_Check_Popup_ReadList";
	}
	
	@RequestMapping(value = "/newpncrmg/Newpncr_Duplication_Check_Ajax_Read.do")
	public String read(NewpncrModel newpncrModel, Model model) throws Exception{
		
		String retUrl = newpncrModel.getRetUrl();
		if ("".equals(retUrl)) {
			throw new Exception("read에서는 반드시 retUrl이 정의 되어야 합니다.");
		}
		
		NewpncrModel newpncrModelData = new NewpncrModel();
		
		if(StringUtils.hasLength(newpncrModel.getNew_pn_cr_seq())){
			newpncrModelData = newpncrDuplicateCheckservice.read(newpncrModel);
		}
		
		model.addAttribute("NewpncrModel", newpncrModelData);
		
		return "/newpncrmg/Newpncr_Duplication_Check_Ajax_Read";
	}
	
	@RequestMapping(value = "/newpncrmg/Newpncr_Duplication_Create.do")
	public String create(NewpncrModel newpncrModel, Model model) throws Exception{
		newpncrDuplicateCheckservice.create(newpncrModel);
		return ResultUtil.handleSuccessResultParam(model, newpncrModel.getNew_pn_cr_seq(), "");
	}

}

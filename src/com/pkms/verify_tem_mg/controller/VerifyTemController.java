package com.pkms.verify_tem_mg.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.pkms.verify_tem_mg.service.VerifyTemServiceIf;

@Controller
public class VerifyTemController {
	@Resource(name = "VerifyTemService")
	private VerifyTemServiceIf verifyTemService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	
	@RequestMapping(value = "/verify_tem_mg/verifyTemp_ReadList.do")
	public String verifyTemp_ReadList(VerifyTemModel verifyTemModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(verifyTemModel);
		
		if(verifyTemModel.getSearchCondition().equals("") || verifyTemModel.getSearchCondition() == null){
			verifyTemModel.setSearchCondition("vol");
		}
		List<VerifyTemModel> readList_quest = verifyTemService.readList_quest(verifyTemModel);
		
		paginationInfo.setTotalRecordCount(verifyTemModel.getTotalCount());
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("verifyTemModel", verifyTemModel);
		model.addAttribute("readList_quest", readList_quest);
		
		return "/verifyTem_mg/verifyTemp_ReadList";
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTemp_Read.do")
	public String verifyTemp_Read(VerifyTemModel verifyTemModel, Model model) throws Exception {
		String searchCon = verifyTemModel.getSearchCondition();
		if(StringUtils.hasLength(verifyTemModel.getQuest_seq())){
			verifyTemModel = verifyTemService.read_quest(verifyTemModel);
			verifyTemModel.setSearchCondition(searchCon);
			
			// 해당 검증 항목 주제가 패키지검증완료된 것에 해당되는지
			String s_yn = verifyTemService.verifyinfo_pkgVerify_S(verifyTemModel);
			
			model.addAttribute("S_gubun", s_yn);
		}else{
		}
		model.addAttribute("verifyTemModel", verifyTemModel);
		return "/verifyTem_mg/verifyTemp_Read";
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTemp_Create.do")
	public String verifyTemp_Create(VerifyTemModel verifyTemModel, Model model, HttpServletRequest request ) throws Exception {
		verifyTemModel = verifyTemService.create_quest(verifyTemModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTemp_Delete.do")
	public String verifyTemp_Delete(VerifyTemModel verifyTemModel, Model model) throws Exception {
		verifyTemService.delete_verifyTem(verifyTemModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/verify_tem_mg/verifyTemp_Update.do")
	public String verifyTemp_Update(VerifyTemModel verifyTemModel, Model model, HttpServletRequest request ) throws Exception {
		// 패키지 완료된 것이 없을때 
		verifyTemService.update_quest(verifyTemModel);
		return ResultUtil.handleSuccessResult();
	}
	
	
}

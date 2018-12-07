package com.pkms.tempmg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.pkmscode.service.PkmsCodeServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.CodeUtil;
import com.pkms.common.util.ResultUtil;
import com.pkms.tempmg.model.TempmgModel;
import com.pkms.tempmg.service.TempmgServiceIf;

/**
 * 템플릿 Controller
 * @author : 009
 * @Date : 2012. 4. 20
 */
@Controller
public class TempmgController {

	@Resource(name = "TempmgService")
	private TempmgServiceIf tempmgService;

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@Resource(name = "PkmsCodeService")
	private PkmsCodeServiceIf pkmsCodeService;

	@RequestMapping(value = "/tempmg/Tempmg_Ajax_Create.do")
	public String create(TempmgModel tempmgModel, Model model, SessionStatus status) throws Exception {
		tempmgModel = tempmgService.create(tempmgModel);
		
        status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, tempmgModel.getTpl_seq(), null);
	}

	@RequestMapping(value = "/tempmg/Tempmg_Read.do")
	public String read(TempmgModel tempmgModel, Model model) throws Exception {
		TempmgModel tempmgModelData = tempmgService.read(tempmgModel);
		
		/*
		 * 검색 조건, 페이징 세팅
		 */
		tempmgModelData.setSearchCondition(tempmgModel.getSearchCondition());
		tempmgModelData.setSearchKeyword(tempmgModel.getSearchKeyword());
		tempmgModelData.setPageIndex(tempmgModel.getPageIndex());
		
		// 사용여부 코드 값 세팅
		tempmgModelData.setUseYnItems(CodeUtil.convertCodeModel(pkmsCodeService.readList("USE")));

		model.addAttribute("TempmgModel", tempmgModelData);

		return "/tempmg/Tempmg_Read";
	}

	@RequestMapping(value = "/tempmg/Tempmg_ReadList.do")
	public String readList(TempmgModel tempmgModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(tempmgModel);

		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> tempmgModelList = tempmgService.readList(tempmgModel);
		paginationInfo.setTotalRecordCount(tempmgModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("TempmgModel", tempmgModel);
		model.addAttribute("TempmgModelList", tempmgModelList);

		return "/tempmg/Tempmg_ReadList";
	}

	@RequestMapping(value = "/tempmg/Tempmg_Ajax_Update.do")
	public String update(TempmgModel tempmgModel, Model model, SessionStatus status) throws Exception {
		tempmgService.update(tempmgModel);
		
        status.setComplete();
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/tempmg/Tempmg_ExcelDownload.do")
	public String excelDownload(TempmgModel tempmgModel, Model model) throws Exception {
		return ResultUtil.handleSuccessResultParam(model, tempmgService.excelDownload(tempmgModel), "");
	}

	/**
	 * 20181203 eryoon
	 * Code List 추가
	 */
	@RequestMapping(value = "/tempmg/Codemg_ReadList.do")
	public String codemgReadList(TempmgModel tempmgModel, Model model) throws Exception {
		
		return "/tempmg/Codemg_ReadList";
	}
	
}

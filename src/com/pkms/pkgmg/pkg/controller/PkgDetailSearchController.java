package com.pkms.pkgmg.pkg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgDetailSearchServiceIf;

/**
 * PKG 보완적용내역 검색 관련 Controller<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 26.
 * 
 */
@Controller
public class PkgDetailSearchController {

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@Resource(name = "PkgDetailSearchService")
	private PkgDetailSearchServiceIf pkgDetailSearchService;
	
	/**
	 * 보완적용내역 목록 조회
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailSearch_Popup_ReadList.do")
	public String readList(PkgModel pkgModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(pkgModel);

		List<PkgDetailModel> pkgDetailModelList = pkgDetailSearchService.readList(pkgModel);
		paginationInfo.setTotalRecordCount(pkgModel.getTotalCount());
		
		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("PkgDetailModelList", pkgDetailModelList);

		return "/pkgmg/pkg/PkgDetailSearch_Popup_ReadList";
	}

	/**
	 * 보완적용내역 상세 조회
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgDetailSearch_Popup_Read.do")
	public String read(PkgModel pkgModel, Model model) throws Exception {
		PkgModel pkgModelData = pkgDetailSearchService.read(pkgModel);
		
		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("PkgModelData", pkgModelData);
		
		return "/pkgmg/pkg/PkgDetailSearch_Popup_View";
	}
	
}

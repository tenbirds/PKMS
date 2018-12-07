package com.pkms.pkgmg.pkg.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.pkms.pkgmg.pkg.service.PkgSupplementServiceIf;

/**
 * 보완적용내용 등록/수정 관련 Controller<br/>
 *  - 엑셀업로드 등
 * 
 * @author : 009
 * @Date : 2012. 4. 26.
 * 
 */
@Controller
public class PkgSupplementController {

	@Resource(name = "PkgSupplementService")
	private PkgSupplementServiceIf pkgSupplementService;
	
	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;
	/**
	 * 엑셀 검사 통과 후 저장
	 * @param pkgModel
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgSupplement_Popup_Create.do")
	public String create(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		pkgSupplementService.create(pkgModel);
		
		//긴급등록 업데이트
		pkgService.urgency_update(pkgModel);
		
		status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, pkgModel.getTpl_seq(), pkgModel.getTpl_ver());
	}
	
	/**
	 * 엑셀 유효성 검사 및 업로드
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgSupplement_Popup_ReadList.do")
	public String readList(PkgModel pkgModel, Model model) throws Exception {
		pkgSupplementService.excelUpload(pkgModel);

		model.addAttribute("PkgModel", pkgModel);

		return "/pkgmg/pkg/PkgSupplement_Popup_ReadList";
	}

	/**
	 * 템플릿 다운로드
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgSupplement_Template_ExcelDownload.do")
	public String templateDownload(PkgModel pkgModel, Model model) throws Exception {
		String str = pkgSupplementService.templateDownload(pkgModel);
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
	
	/**
	 * 등록된 데이터 다운로드
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/PkgSupplement_ExcelDownload.do")
	public String excelDownload(PkgModel pkgModel, Model model) throws Exception {
		String pkg_detail_seq_bak = pkgModel.getPkg_detail_seq(); 
		pkgModel.setPkg_detail_seq("");
		String str = pkgSupplementService.excelDownload(pkgModel);
		pkgModel.setPkg_detail_seq(pkg_detail_seq_bak);
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
}

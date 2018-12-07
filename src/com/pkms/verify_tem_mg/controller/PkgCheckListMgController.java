package com.pkms.verify_tem_mg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.wings.util.DateUtil;
import com.pkms.verify_tem_mg.model.PkgCheckListManagerModel;
import com.pkms.verify_tem_mg.service.PkgCheckListMgServiceIf;

@Controller
public class PkgCheckListMgController {
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	
	@Resource(name = "PkgCheckListMgService")
	private PkgCheckListMgServiceIf pkgCheckListMgService;	
	
	
	@RequestMapping(value = "/verifyTem_mg/pkg21_menuCheck_admin.do")
	public String pkg21_menuCheck_admin(PkgCheckListManagerModel pkgCheckListManagerModel, Model model) throws Exception{
		List<PkgCheckListManagerModel> code_list = null;
		pkgCheckListManagerModel.setGubun("CHK_L");
		code_list = (List<PkgCheckListManagerModel>) pkgCheckListMgService.codeList(pkgCheckListManagerModel);
		
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(pkgCheckListManagerModel);	
//		List<PkgCheckListManagerModel> pkgCheckListMg = null;
		List<?> pkgCheckListMg = null;
		
		try {
//			pkgCheckListMg  = (List<PkgCheckListManagerModel>) pkgCheckListMgService.readList(pkgCheckListManagerModel);
			pkgCheckListMg  = pkgCheckListMgService.readList(pkgCheckListManagerModel);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("-----error start------------------------------------------------------");
			System.out.println(e);
			System.out.println("-----error end------------------------------------------------------");
		}
		
		for (int j = 0; j < pkgCheckListMg.size(); j++) { // jsp 에서 처리할때 개행때문에 문제가 있어서 수정
				((PkgCheckListManagerModel) pkgCheckListMg.get(j)).setChk_content( (((PkgCheckListManagerModel) pkgCheckListMg.get(j)).getChk_content()).replaceAll("(\r\n|\r|\n|\n\r)", "<br> "));
		}
		
		paginationInfo.setTotalRecordCount(pkgCheckListManagerModel.getTotalCount());

		
		if (!StringUtils.hasLength(pkgCheckListManagerModel.getStdate()) || !StringUtils.hasLength(pkgCheckListManagerModel.getEddate())) {
			pkgCheckListManagerModel.setStdate(DateUtil.formatDateByMonth(DateUtil.format(), -3));
			pkgCheckListManagerModel.setEddate(DateUtil.dateFormat());
		}
		
		model.addAttribute("pkgCheckListManagerModel", pkgCheckListManagerModel);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("pkgCheckListMg", pkgCheckListMg);
		model.addAttribute("code_list", code_list);

		return "/verifyTem_mg/pkg21_menuCheck_admin";
	}
	
	
	
	
	@RequestMapping(value = "/verifyTem_mg/pkg21_menuCheck_admin_detail.do")
	public String pkg21_menuCheck_admin_detail(PkgCheckListManagerModel pkgCheckListManagerModel, Model model) throws Exception{

		if(pkgCheckListManagerModel.getChk_seq() != null && pkgCheckListManagerModel.getChk_seq() != "") {		
		
				PkgCheckListManagerModel pkgCheckListMg = null;
				try {
					pkgCheckListMg  = pkgCheckListMgService.readone(pkgCheckListManagerModel);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}
		
				if((pkgCheckListMg.getChk_content()) != null || (pkgCheckListMg.getChk_content()).length() != 0) {
					pkgCheckListMg.setChk_content( (pkgCheckListMg.getChk_content()).replaceAll("(\r\n|\r|\n|\n\r)", "<br> "));
				}
				
				model.addAttribute("pkgCheckListMg", pkgCheckListMg);
		}
		
		return "/verifyTem_mg/pkg21_menuCheck_admin_detail";
	}
	
	
	
	@RequestMapping(value = "/verifyTem_mg/pkg21_menuCheck_admin_view.do")
	public String readview(PkgCheckListManagerModel pkgCheckListManagerModel, Model model) throws Exception {
		List<PkgCheckListManagerModel> code_list = null;
		pkgCheckListManagerModel.setGubun("CHK_L");
		code_list = (List<PkgCheckListManagerModel>) pkgCheckListMgService.codeList(pkgCheckListManagerModel);
		
		PkgCheckListManagerModel pkgCheckListMg = null;
		if(pkgCheckListManagerModel.getChk_seq() != null && pkgCheckListManagerModel.getChk_seq().length() != 0) {
			try {
				pkgCheckListMg  = pkgCheckListMgService.readone(pkgCheckListManagerModel);
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
		}
		
		model.addAttribute("code_list", code_list);
		model.addAttribute("pkgCheckListMg", pkgCheckListMg);
		
		return "/verifyTem_mg/pkg21_menuCheck_admin_view";
	}
	
	
	
	@RequestMapping(value = "/verifyTem_mg/pkg21_menuCheck_admin_edit.do")
	public String readedit(PkgCheckListManagerModel pkgCheckListManagerModel, Model model) throws Exception {
//		pkgCheckListManagerModel.setAnswer_type();//결과
		pkgCheckListManagerModel.setPosition("S");
		if(pkgCheckListManagerModel.getChk_seq() != null && pkgCheckListManagerModel.getChk_seq().length() != 0) {//update
				pkgCheckListMgService.update(pkgCheckListManagerModel);
		}else {
				pkgCheckListMgService.insert(pkgCheckListManagerModel);
		}
			
		return ResultUtil.handleSuccessResult();
	}
		
	
	@RequestMapping(value = "/verifyTem_mg/pkg21_menuCheck_admin_Delete.do")
	public String readDelete(PkgCheckListManagerModel pkgCheckListManagerModel, Model model) throws Exception {
		if(pkgCheckListManagerModel.getChk_seq() != null && pkgCheckListManagerModel.getChk_seq().length() != 0) {
				pkgCheckListMgService.delete(pkgCheckListManagerModel);
		}
		return ResultUtil.handleSuccessResult();
	}
	
	
	/**
	 * 목록 엑셀다운로드
	 * @param newpncrModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verifyTem_mg/menuCheck_admin_ExcelDownload.do")
	public String excelDownload(PkgCheckListManagerModel pkgCheckListManagerModel, Model model) throws Exception {
		String str = pkgCheckListMgService.excelDownload(pkgCheckListManagerModel);
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
	
	
	
	
	
	

}

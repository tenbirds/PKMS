package com.pkms.newpncrmg.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.newpncrmg.model.NewpncrModel;
import com.pkms.newpncrmg.service.NewpncrServiceIf;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.wings.util.DateUtil;

@Controller
public class NewpncrController {
	
	@Resource(name = "NewpncrService")
	private NewpncrServiceIf newpncrServie;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@RequestMapping(value = "/newpncrmg/Newpncr_ReadList.do")
	public String readList(NewpncrModel newpncrModel, Model model) throws Exception{
		
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(newpncrModel);
		
		//기본 검색 기간 설정
		if(!StringUtils.hasLength(newpncrModel.getSearch_reg_date_start()) || !StringUtils.hasLength(newpncrModel.getSearch_reg_date_end())  ){
			newpncrModel.setSearch_reg_date_start(DateUtil.formatDateByMonth(DateUtil.format(), -1));
			newpncrModel.setSearch_reg_date_end(DateUtil.dateFormat());
		}
		
		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */ 
		List<?> newpncrModelList = newpncrServie.readList(newpncrModel);
		paginationInfo.setTotalRecordCount(newpncrModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("newpncrModelList", newpncrModelList);
		model.addAttribute("NewpncrModel", newpncrModel);
		model.addAttribute("paginationInfo", paginationInfo);
		
//		newpncrModel.setSearch_indept("");
//		newpncrModel.setSystem_search_gubun("");
		return "/newpncrmg/Newpncr_ReadList";
	}
	
	@RequestMapping(value = "/newpncrmg/Newpncr_Read.do")
	public String read(NewpncrModel newpncrModel, Model model) throws Exception{
		String retUrl = newpncrModel.getRetUrl();
		if ("".equals(retUrl)) {
			throw new Exception("read에서는 반드시 retUrl이 정의 되어야 합니다.");
		}
		NewpncrModel newpncrModelData = new NewpncrModel();
		
		if(StringUtils.hasLength(newpncrModel.getNew_pn_cr_seq())){
			newpncrModelData = newpncrServie.read(newpncrModel);
			List<SysRoadMapModel> readListChart = newpncrServie.readListChart(newpncrModelData);
			model.addAttribute("readListSubChart", readListChart);
		}
		
		
		
		//답글 리스트 가져오기
		List<NewpncrModel> readListRepl = newpncrServie.readListRepl(newpncrModel);
		
		/*
		 * 검색조건, 페이징 세팅
		 */
		
		newpncrModelData.setSearch_reg_date_start(newpncrModel.getSearch_reg_date_start());
		newpncrModelData.setSearch_reg_date_end(newpncrModel.getSearch_reg_date_end());
		newpncrModelData.setSearch_system_seq(newpncrModel.getSearch_system_seq());
		newpncrModelData.setSearch_system_name(newpncrModel.getSearch_system_name());
		newpncrModelData.setSearch_reg_user(newpncrModel.getSearch_reg_user());
		newpncrModelData.setSearch_reg_name(newpncrModel.getSearch_reg_name());
		newpncrModelData.setSearchCondition(newpncrModel.getSearchCondition());
		newpncrModelData.setSearchKeyword(newpncrModel.getSearchKeyword());
		newpncrModelData.setSearchRoleCondition(newpncrModel.getSearchRoleCondition());
		newpncrModelData.setSearchNew_pncr_gubun(newpncrModel.getSearchNew_pncr_gubun());
		newpncrModelData.setPageIndex(newpncrModel.getPageIndex());
		
		model.addAttribute("NewpncrModel", newpncrModelData);
		model.addAttribute("ReadListRepl",readListRepl);
		return retUrl;
	}
	
	@RequestMapping(value = "/newpncrmg/Newpncr_Create.do")
	public String create(NewpncrModel newpncrModel, SysRoadMapModel sysRoadMapModel,Model model) throws Exception{
		newpncrServie.create(newpncrModel);
		if(sysRoadMapModel.getRoad_map_seqs() != null){
			newpncrServie.createRoadMapMapping(newpncrModel, sysRoadMapModel);
			
			if(newpncrModel.getSystem_seq2() != null && !"".equals(newpncrModel.getSystem_seq2())){
				newpncrModel.setSystem_seq(newpncrModel.getSystem_seq2());
				newpncrServie.create(newpncrModel);
				newpncrServie.createRoadMapMapping(newpncrModel, sysRoadMapModel);
			}
		}
		
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/newpncrmg/Newpncr_Update.do")
	public String update(NewpncrModel newpncrModel,SysRoadMapModel sysRoadMapModel, Model model) throws Exception{
		newpncrServie.update(newpncrModel);
		
		if("refine".equals(newpncrModel.getStateInfo())){
			newpncrServie.deleteRoadMapMapping(newpncrModel); //Road_map_mapping 테이블에서 삭제
			if(sysRoadMapModel.getRoad_map_seqs() == null){
			} else {
				newpncrServie.createRoadMapMapping(newpncrModel, sysRoadMapModel); //Road_map_mapping 테이블에 추가				
			}
		}
		
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/newpncrmg/Newpncr_Delete.do")
	public String delete(NewpncrModel newpncrModel, Model model) throws Exception{
		newpncrServie.delete(newpncrModel);
		newpncrServie.deleteRoadMapMapping(newpncrModel); //Road_map_mapping 테이블에서 삭제
		return ResultUtil.handleSuccessResult();
	}
	
	/**
	 * 목록 엑셀다운로드
	 * @param newpncrModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/newpncrmg/Newpncr_ExcelDownload.do")
	public String excelDownload(NewpncrModel newpncrModel, Model model) throws Exception {
		String str = newpncrServie.excelDownload(newpncrModel);
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
	
	
	/**
	 * 답글 추가 0909 - ksy
	 */
	
	@RequestMapping(value = "/newpncrmg/Newpncr_CreateRepl.do")
	public String createRepl(NewpncrModel newpncrModel, Model model) throws Exception{
		newpncrServie.createRepl(newpncrModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/newpncrmg/Newpncr_UpdateRepl.do")
	public String updateRepl(NewpncrModel newpncrModel, Model model, HttpServletRequest request) throws Exception{
		
		//jsp에서 hidden name값을 모델에 저장해놓고 그 name에 해당하는 내용물을 request.getParameter로 불러와 다시 모델에있는 답글에 set
		newpncrModel.setManager_repl(request.getParameter(newpncrModel.getReplstatusM()));
		newpncrModel.setBp_repl(request.getParameter(newpncrModel.getReplstatusB()));
		newpncrServie.updateRepl(newpncrModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/newpncrmg/Newpncr_Ajax_Chart_Read.do")
	public String readGantChart(NewpncrModel newpncrModel, Model model) throws Exception{
		
		if(StringUtils.hasLength(newpncrModel.getNew_pn_cr_seq())){ //new_pn_cr_seq가 있을경우
			if("1".equals(newpncrModel.getRegisterFlag())){ //수정 에서 System 변경 했을 경우 기존 road_map 테이블에서 템플릿 가지고옴
				NewpncrModel pncrModel = new NewpncrModel();
				pncrModel.setSystem_seq(newpncrModel.getSystem_seq());
				List<SysRoadMapModel> readListChart = newpncrServie.readListChart(pncrModel);
				model.addAttribute("readListChart", readListChart);
			}else{
				List<SysRoadMapModel> readListChart = newpncrServie.readListChart(newpncrModel); //pncr 디테일 화면 road_map_mapping 테이블에서 매핑여부 확인
				model.addAttribute("readListChart", readListChart);
			}
			
		}else{
			List<SysRoadMapModel> readListChart = newpncrServie.readListChart(newpncrModel);
			List<List<SysRoadMapModel>> list = newpncrServie.readListSubChart(newpncrModel);
			model.addAttribute("readListChart", readListChart);
			model.addAttribute("readListSubChart", list);
			
		}
		model.addAttribute("newpncrModel", newpncrModel);
		
		return "/newpncrmg/Newpncr_ReadChart";
	}
}

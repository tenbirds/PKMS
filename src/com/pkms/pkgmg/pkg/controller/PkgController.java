package com.pkms.pkgmg.pkg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.dao.PkgStatusDAO;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.pkms.pkgmg.pkg.service.PkgStatusServiceIf;
import com.pkms.pkgmg.pkg.service.PkgStatusVerifyServiceIf;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group1.service.Group1ServiceIf;
import com.pkms.sys.group2.service.Group2ServiceIf;
import com.pkms.sys.system.service.SystemServiceIf;
import com.wings.model.CodeModel;
import com.wings.util.DateUtil;


/**
 * PKG Main Controller<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class PkgController {

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@Resource(name = "Group1Service")
	private Group1ServiceIf group1Service;

	@Resource(name = "Group2Service")
	private Group2ServiceIf group2Service;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "PkgStatusService")
	private PkgStatusServiceIf pkgStatusService;
	
	@Resource(name = "PkgStatusVerifyService")
	private PkgStatusVerifyServiceIf pkgStatusVerifyService;
	
	@Resource(name = "PkgStatusDAO")
	private PkgStatusDAO pkgStatusDAO;
	/**
	 * PKG 상세 조회
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/Pkg_Popup_Read.do")
	public String read(PkgModel pkgModel, Model model) throws Exception {
		PkgModel pkgModelData = new PkgModel();
		PkgModel pkgModelProgressData = new PkgModel();
		
		String retUrl = pkgModel.getRetUrl();
		if ("".equals(retUrl)) {
			throw new Exception("read에서는 반드시 retUrl이 정의 되어야 합니다.");
		}

		// pkg read
		pkgModelData = pkgService.read(pkgModel);

		// progress read
		pkgModelProgressData = pkgService.popupProgressRead(pkgModel);
		
		
		// print read
		List<PkgModel> printRead_Time = pkgService.printRead_Time(pkgModel);
		List<PkgModel> pkgEqList = (List<PkgModel>) pkgService.printRead_EQ(pkgModel);
//		List<PkgModel> pkgPnCrList = (List<PkgModel>) pkgService.printRead_PnCr(pkgModel);
		
		SysModel sysModelData = new SysModel();
		
		SysModel idcData = new SysModel();
		//담당자정보 - 시스템정보 추
		if (StringUtils.hasLength(pkgModelData.getPkg_seq())) {
//			System.out.println("☆☆☆☆☆☆흐음 값 어딨니☆☆☆☆☆");
			sysModelData.setSystem_seq(pkgModelData.getSystem_seq());
			sysModelData = systemService.read(sysModelData);
//			System.out.println("시스템 seq : "+sysModelData.getSystem_seq());
//			System.out.println("시스템 이름 : "+sysModelData.getName());
			
			idcData.setSystem_seq(pkgModelData.getSystem_seq());
			idcData = systemService.pkg_equipment_user(sysModelData);
			
		}
		
//		System.out.println("시스템 이름 : "+sysModelData.getName());
//		System.out.println("☆☆☆☆☆☆끝☆☆☆☆☆");
		model.addAttribute("PkgModel", pkgModelData);

		model.addAttribute("PkgModelProgress", pkgModelProgressData);
		
		model.addAttribute("PkgTimeList", printRead_Time);
		model.addAttribute("PkgEqList", pkgEqList);
//		model.addAttribute("PkgPnCrList", pkgPnCrList);
		model.addAttribute("SysModel", sysModelData);
		model.addAttribute("IdcData", idcData);
		return retUrl;
	}

	/**
	 * PKG 목록 조회
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/Pkg_ReadList.do")
	public String readList(PkgModel pkgModel, Model model) throws Exception {

		// 세션에 저장된 조회 조건 처리
		pkgModel = pkgService.setSearchCondition(pkgModel);

		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(pkgModel);

		// 기본 검색 기간 설정
		if (!StringUtils.hasLength(pkgModel.getDate_start()) || !StringUtils.hasLength(pkgModel.getDate_end())) {
			pkgModel.setDate_start(DateUtil.formatDateByMonth(DateUtil.format(), -3));
			pkgModel.setDate_end(DateUtil.dateFormat());
		}

		// PKG현황, 일정목록 List
		List<PkgModel> pkgModelList = (List<PkgModel>) pkgService.readList(pkgModel);
		paginationInfo.setTotalRecordCount(pkgModel.getTotalCount());

		// Group1 List
		List<SysModel> group1List = group1Service.readList(new SysModel());
		ArrayList<CodeModel> group1Items = new ArrayList<CodeModel>();
		for (SysModel group1 : group1List) {
			CodeModel codeModel = new CodeModel();
			codeModel.setCode(group1.getGroup1_seq());
			codeModel.setCodeName(group1.getName());
			group1Items.add(codeModel);
		}

		// Group2 List
		ArrayList<CodeModel> group2Items = new ArrayList<CodeModel>();
		if(pkgModel.getGroup1Condition() != null && !"".equals(pkgModel.getGroup1Condition())) {
			SysModel sysModel = new SysModel();
			sysModel.setGroup1_seq(pkgModel.getGroup1Condition());
			List<SysModel> group2List = group2Service.readList(sysModel);
			for (SysModel group2 : group2List) {
				CodeModel codeModel = new CodeModel();
				codeModel.setCode(group2.getGroup2_seq());
				codeModel.setCodeName(group2.getName());
				group2Items.add(codeModel);
			}
		}
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("PkgModelList", pkgModelList);
		model.addAttribute("Group1List", group1Items);
		model.addAttribute("Group2List", group2Items);

		return "/pkgmg/pkg/Pkg_ReadList";
	}

	/**
	 * 검증요청
	 * @param pkgModel
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/Pkg_Update.do")
	public String update(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		pkgService.update(pkgModel);
        status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, pkgModel.getStatus(), pkgModel.getStatus_operation());
	}

	/**
	 * PKG 삭제
	 * @param pkgModel
	 * @param model
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/Pkg_Delete.do")
	public String delete(PkgModel pkgModel, Model model, SessionStatus status) throws Exception {
		pkgService.delete(pkgModel);
        status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, pkgModel.getStatus(), pkgModel.getStatus_operation());
	}
	
	/**
	 * 목록 엑셀다운로드
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/Pkg_ExcelDownload.do")
	public String excelDownload(PkgModel pkgModel, Model model) throws Exception {
		String str = pkgService.excelDownload(pkgModel);
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
	
	/**
	 * 검증진도율
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg/Pkg_Popup_Progress_Read.do")
	public String popupProgress(PkgModel pkgModel, Model model) throws Exception {
		pkgModel = pkgService.popupProgressRead(pkgModel);
		
		model.addAttribute("PkgModel", pkgModel);
		
		return "/pkgmg/pkg/Pkg_Popup_Progress_Read";
	}
	
	@RequestMapping(value = "/pkgmg/pkg/Pkg_Popup_Print.do")
	public String printRead(PkgModel pkgModel, Model model) throws Exception {
		PkgModel pkgModelProgressData = new PkgModel();
		PkgModel pkgFileModel = new PkgModel();
		pkgModel.setContent(pkgModel.getContent().replaceAll("\n","<br/>"));
		pkgModel.setRm_issue_comment(pkgModel.getRm_issue_comment().replaceAll("\n","<br/>"));
		
		// progress read
		pkgModelProgressData = pkgService.popupProgressRead(pkgModel);

		// print read
		List<PkgModel> printRead_Time = pkgService.printRead_Time(pkgModel);
		List<PkgModel> pkgEqList = (List<PkgModel>) pkgService.printRead_EQ(pkgModel);
//		List<PkgModel> pkgPnCrList = (List<PkgModel>) pkgService.printRead_PnCr(pkgModel);
		
		
		pkgFileModel.setMaster_file_id(pkgModel.getMaster_file_id());
		if (pkgFileModel.getMaster_file_id() != null && !"".equals(pkgFileModel.getMaster_file_id())) {
			// 첨부 파일 정보 세팅
			fileManageService.read(pkgFileModel);
		}
		
		model.addAttribute("PkgFileModel", pkgFileModel);
		model.addAttribute("PkgModelProgress", pkgModelProgressData);
		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("PkgTimeList", printRead_Time);
		model.addAttribute("PkgEqList", pkgEqList);
//		model.addAttribute("PkgPnCrList", pkgPnCrList);
		
		int status = Integer.parseInt(pkgModel.getStatus()); 
		if("0".equals(pkgModel.getSelected_status())){
			PkgModel pkgModelData = new PkgModel();
			if((status > 1 && status < 10) || status == 26){
				pkgModel.setSelected_status("2");
				pkgModelData = pkgStatusService.read(pkgModel);
				
				if(status > 3 && status < 10){
					PkgModel pkgVerifyInfo = new PkgModel();
					pkgVerifyInfo.setPkg_seq(pkgModel.getPkg_seq());
					
					// 패키지의 검증정보 가져오기(검증버전,검증content)
					if(!"N".equals(pkgModel.getVol_yn())){					
						pkgVerifyInfo.setVerify_type("vol");
						PkgModel vol = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo); 
						vol.setVerify_content(vol.getVerify_content().replaceAll("\n","<br/>"));
						model.addAttribute("vol", vol);
					}
					if(!"N".equals(pkgModel.getCha_yn())){
						pkgVerifyInfo.setVerify_type("cha");
						PkgModel cha = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo);
						cha.setVerify_content(cha.getVerify_content().replaceAll("\n","<br/>"));					
						model.addAttribute("cha", cha);
					}
					if(!"N".equals(pkgModel.getNon_yn())){					
						pkgVerifyInfo.setVerify_type("non");
						PkgModel non = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo);
						non.setVerify_content(non.getVerify_content().replaceAll("\n","<br/>"));
						model.addAttribute("non", non);
					}
					PkgStatusModel pkgStatusModelData_search = new PkgStatusModel();
					
					pkgStatusModelData_search.setPkg_seq(pkgModel.getPkg_seq());
					pkgStatusModelData_search.setStatus("3");
					
					pkgStatusModelData_search = pkgStatusDAO.read(pkgStatusModelData_search);
					pkgModelData.getPkgStatusModel().setCol41(pkgStatusModelData_search.getCol41());
					
					if(status > 6 && status < 10){
						PkgStatusModel pkgStatusModelData = new PkgStatusModel();
						
						pkgStatusModelData.setPkg_seq(pkgModel.getPkg_seq());
						pkgStatusModelData.setStatus("7");
						
						pkgStatusModelData = pkgStatusDAO.read(pkgStatusModelData);
						
						model.addAttribute("PkgMdlData", pkgStatusModelData);
					}
				}
				
				model.addAttribute("PkgModelData", pkgModelData);
				pkgModel.setSelected_status("0");
			}			
		}else if(!"0".equals(pkgModel.getSelected_status()) && !"7".equals(pkgModel.getSelected_status())){			
			PkgModel pkgModelData = new PkgModel();
			
			String select_status="";
			
			if("4".equals(pkgModel.getSelected_status()) || "5".equals(pkgModel.getSelected_status()) || "6".equals(pkgModel.getSelected_status())){
				select_status = pkgModel.getSelected_status();
			}
			
			pkgModel.setSelected_status("2");
			pkgModelData = pkgStatusService.read(pkgModel);
			
			if("4".equals(select_status) || "5".equals(select_status) || "6".equals(select_status)){
				PkgModel pkgVerifyInfo = new PkgModel();
				pkgVerifyInfo.setPkg_seq(pkgModel.getPkg_seq());
				
				// 패키지의 검증정보 가져오기(검증버전,검증content)
				if(!"N".equals(pkgModel.getVol_yn())){					
					pkgVerifyInfo.setVerify_type("vol");
					PkgModel vol = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo); 
					vol.setVerify_content(vol.getVerify_content().replaceAll("\n","<br/>"));
					model.addAttribute("vol", vol);
				}
				if(!"N".equals(pkgModel.getCha_yn())){
					pkgVerifyInfo.setVerify_type("cha");
					PkgModel cha = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo);
					cha.setVerify_content(cha.getVerify_content().replaceAll("\n","<br/>"));					
					model.addAttribute("cha", cha);
				}
				if(!"N".equals(pkgModel.getNon_yn())){					
					pkgVerifyInfo.setVerify_type("non");
					PkgModel non = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo);
					non.setVerify_content(non.getVerify_content().replaceAll("\n","<br/>"));
					model.addAttribute("non", non);
				}
				
				PkgStatusModel pkgStatusModelData_search = new PkgStatusModel();
				
				pkgStatusModelData_search.setPkg_seq(pkgModel.getPkg_seq());
				pkgStatusModelData_search.setStatus("3");
				
				pkgStatusModelData_search = pkgStatusDAO.read(pkgStatusModelData_search);
				pkgModelData.getPkgStatusModel().setCol41(pkgStatusModelData_search.getCol41());
				
				pkgModel.setSelected_status(select_status);
			}
			
			model.addAttribute("PkgModelData", pkgModelData);
		}else if("7".equals(pkgModel.getSelected_status())){
			PkgModel pkgVerifyInfo = new PkgModel();
			pkgVerifyInfo.setPkg_seq(pkgModel.getPkg_seq());
			
			// 패키지의 검증정보 가져오기(검증버전,검증content)
			if(!"N".equals(pkgModel.getVol_yn())){					
				pkgVerifyInfo.setVerify_type("vol");
				PkgModel vol = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo); 
				vol.setVerify_content(vol.getVerify_content().replaceAll("\n","<br/>"));
				model.addAttribute("vol", vol);
			}
			if(!"N".equals(pkgModel.getCha_yn())){
				pkgVerifyInfo.setVerify_type("cha");
				PkgModel cha = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo);
				cha.setVerify_content(cha.getVerify_content().replaceAll("\n","<br/>"));					
				model.addAttribute("cha", cha);
			}
			if(!"N".equals(pkgModel.getNon_yn())){					
				pkgVerifyInfo.setVerify_type("non");
				PkgModel non = pkgStatusVerifyService.readPKGVerify_info(pkgVerifyInfo);
				non.setVerify_content(non.getVerify_content().replaceAll("\n","<br/>"));
				model.addAttribute("non", non);
			}
			
			PkgModel pkgModelData = new PkgModel();
			pkgModel.setSelected_status("7");
			pkgModelData = pkgStatusService.read(pkgModel);

			PkgStatusModel pkgStatusModelData_search = new PkgStatusModel();
			
			pkgStatusModelData_search.setPkg_seq(pkgModel.getPkg_seq());
			pkgStatusModelData_search.setStatus("3");
			
			pkgStatusModelData_search = pkgStatusDAO.read(pkgStatusModelData_search);
			pkgModelData.getPkgStatusModel().setCol41(pkgStatusModelData_search.getCol41());
			
			model.addAttribute("PkgModelData", pkgModelData);
		}
		
		return "/pkgmg/pkg/Pkg_Popup_Print_Read";
	}
	
	@RequestMapping(value = "/pkgmg/pkg/Pkg_Popup_Print_Dev.do")
	public String printReadDev(PkgModel pkgModel, Model model) throws Exception {
		PkgModel pkgModelProgressData = new PkgModel();

		pkgModel.setContent(pkgModel.getContent().replaceAll("\n","<br/>"));
		
		// progress read
		pkgModelProgressData = pkgService.popupProgressRead_Dev(pkgModel);

		model.addAttribute("PkgModelProgress", pkgModelProgressData);
		model.addAttribute("PkgModel", pkgModel);

		return "/pkgmg/pkg/Pkg_Popup_Print_Read_Dev";
	}
}

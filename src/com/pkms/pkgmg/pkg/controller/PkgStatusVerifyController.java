package com.pkms.pkgmg.pkg.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.dao.PkgStatusDAO;
import com.pkms.pkgmg.pkg.dao.PkgTab2DAO;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.pkms.pkgmg.pkg.service.PkgStatusServiceIf;
import com.pkms.pkgmg.pkg.service.PkgStatusVerifyServiceIf;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.service.SystemServiceIf;
import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.pkms.verify_tem_mg.service.VerifyTemMgServiceIf;
import com.pkms.verify_tem_mg.service.VerifyTemServiceIf;

@Controller
public class PkgStatusVerifyController {
	
	@Resource(name = "PkgStatusVerifyService")
	private PkgStatusVerifyServiceIf pkgStatusVerifyService;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;
	
	@Resource(name = "PkgStatusDAO")
	private PkgStatusDAO pkgStatusDAO;

	@Resource(name = "PkgTab2DAO")
	private PkgTab2DAO pkgTab2DAO;
	
	@Resource(name = "PkgStatusService")
	private PkgStatusServiceIf pkgStatusService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;
	
	@RequestMapping(value = "/pkgmg/pkg/PkgStatusVerify_Ajax_Read.do")
	public String PkgStatusVerify_Ajax_Read(VerifyTemModel verifyTemModel, PkgModel pkgModel, 
			SysModel sysModel, Model model) throws Exception {
		verifyTemModel.setVerify_type(pkgModel.getVerify_type());
		String vertype = "";
		if(verifyTemModel.getVerify_type().equals("vol")){ vertype = "31"; }
		else if(verifyTemModel.getVerify_type().equals("sec")){ vertype = "32"; }
		else if(verifyTemModel.getVerify_type().equals("cha")){ vertype = "33"; }
		else if(verifyTemModel.getVerify_type().equals("non")){ vertype = "34"; }
		String retUrl = "/pkgmg/pkg/Pkg_Status_"+vertype+"_Ajax_Read";
		
		int pkg_verify_gubun = pkgStatusVerifyService.getPKG_verify_gubun(pkgModel); // 등록/수정 구분값
		PkgModel pkgVerifyInfo = pkgStatusVerifyService.readPKGVerify_info(pkgModel); // 패키지의 검증정보 가져오기(검증버전,검증content)
		VerifyTemModel verifyYN = pkgStatusVerifyService.getVerifyYN_frompkg(pkgModel.getPkg_seq()); //검증4타입의 Y,N,S 구분값
		
		List<VerifyTemModel> List_verifyTem = pkgStatusVerifyService.getList_verifyTem(verifyTemModel);//탬플릿제목리스트
		
		if(pkg_verify_gubun == 0){ //등록
			if(List_verifyTem.size() != 0){
				verifyTemModel.setVerify_seq(List_verifyTem.get(0).getVerify_seq());
				verifyTemModel.setVerify_ver(List_verifyTem.get(0).getVerify_ver());
				verifyTemModel.setSystem_seq(List_verifyTem.get(0).getSystem_seq());
			}
		}else{ //수정
			verifyTemModel.setVerify_seq(pkgVerifyInfo.getVerify_seq());
			verifyTemModel.setVerify_ver(Integer.parseInt(pkgVerifyInfo.getVerify_ver()));
			List<VerifyTemModel> ResultList = pkgStatusVerifyService.readVerify_result(verifyTemModel);//결과저장리스트
			
			if (pkgModel.getMaster_file_id() != null && !"".equals(pkgModel.getMaster_file_id())) {
				// 첨부 파일 정보 
				fileManageService.read(pkgModel);
			}
			
			PkgStatusModel pkgStatusModel = new PkgStatusModel();
			pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
			pkgStatusModel.setStatus(vertype);
			pkgStatusModel = pkgStatusDAO.read(pkgStatusModel);
			model.addAttribute("ResultList", ResultList);
			model.addAttribute("pkg4verify_seq_ver", pkgVerifyInfo.getVerify_seq()+"_"+pkgVerifyInfo.getVerify_ver());
			model.addAttribute("pkgStatusModel", pkgStatusModel);
			
		}
		List<VerifyTemModel> List_verifyQuest = pkgStatusVerifyService.getList_verifyQuest(verifyTemModel);//검증질문리스트
		
		//검증타입별 각 담당자정보 가져오기
		SysModel sysModelData = systemService.read(sysModel);
		PkgModel pkgModelData = pkgService.read(pkgModel);
		
		model.addAttribute("verifyTemModel", verifyTemModel);
		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("pkgModelData", pkgModelData);
		model.addAttribute("verifyYN", verifyYN);
		model.addAttribute("pkgVerifyInfo", pkgVerifyInfo);
		model.addAttribute("pkg_verify_gubun", pkg_verify_gubun);
		model.addAttribute("SysModel", sysModelData);
		model.addAttribute("List_verifyQuest", List_verifyQuest);
		model.addAttribute("List_verifyTem", List_verifyTem);
		
		return retUrl;
	}
	
	@RequestMapping(value = "/pkgmg/pkg/PkgStatusVerify_Ajax_Create.do")
	public String PkgStatusVerify_Ajax_Create(VerifyTemModel verifyTemModel, PkgModel pkgModel, 
			Model model, HttpServletRequest request) throws Exception {
		String end = request.getParameter("end");
		String quest_type = request.getParameter("quest_type");
		String [] quest_input = pkgModel.getResult_quest_input().split(",");
		String [] item_input = pkgModel.getResult_item_input().split(",");
		String [] quest_radio = pkgModel.getResult_quest_radio().split(",");
		String [] item_radio = pkgModel.getResult_item_radio().split(",");
		String [] verify_seq_ver = pkgModel.getVerify_tem_info().split("_");
		
		verifyTemModel.setEnd(end);
		verifyTemModel.setVerify_seq(verify_seq_ver[0]);
		verifyTemModel.setVerify_ver(Integer.parseInt(verify_seq_ver[1]));
		verifyTemModel.setPkg_seq(pkgModel.getPkg_seq());
		verifyTemModel.setVerify_content(pkgModel.getVerify_content());
		pkgModel.setVerify_seq(verify_seq_ver[0]);
		pkgModel.setVerify_ver(verify_seq_ver[1]);
		
		pkgStatusVerifyService.delVerify_result(verifyTemModel);
		
		if(!quest_type.equals("radioType")){//단답형 or 복합형
			if(quest_input.length != 0){
				for(int i=0; i<quest_input.length; i++){//단답insert
					verifyTemModel.setQuest_seq(quest_input[i]);
					verifyTemModel.setResult_memo(item_input[i]);
					verifyTemModel.setVerify_type(pkgModel.getVerify_type());
					pkgStatusVerifyService.create_verifyResult(verifyTemModel);
				}
			}
		}
		if(!quest_type.equals("textType")){//선택형 or 복합형
			if(quest_radio.length != 0){
				for(int i=0; i<quest_radio.length; i++){//선택insert
					verifyTemModel.setQuest_seq(quest_radio[i]);
					verifyTemModel.setResult_memo(item_radio[i]);
					verifyTemModel.setVerify_type(pkgModel.getVerify_type());
					pkgStatusVerifyService.create_verifyResult(verifyTemModel);
				}
			}
		}
		//첨부파일 저장
		if ("".equals(pkgModel.getMaster_file_id())) {
			fileManageService.create(pkgModel, "PKG_");
		} else {
			fileManageService.update(pkgModel, "PKG_");
		}
		//마스터파일 없을시 생성
		pkgTab2DAO.update(pkgModel);
		
		// 현재 status 관련 create or update
		PkgStatusModel pkgStatusModel = new PkgStatusModel();
		pkgStatusModel.setCol43(pkgModel.getCol43());
		pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
		pkgStatusModel.setVerify_start_date(verifyTemModel.getVerify_start_date());
		pkgStatusModel.setVerify_end_date(verifyTemModel.getVerify_end_date());
		
		String vertype = "";
		if(pkgModel.getVerify_type().equals("vol")){ vertype = "31"; }
		else if(pkgModel.getVerify_type().equals("sec")){ vertype = "32"; }
		else if(pkgModel.getVerify_type().equals("cha")){ vertype = "33"; }
		else if(pkgModel.getVerify_type().equals("non")){ vertype = "34"; }
		pkgStatusModel.setStatus(vertype);
		
		if(pkgModel.getEnd().equals("등록")){
			pkgStatusService.createStatus4Verify(pkgStatusModel);
			pkgStatusVerifyService.createPKGVerify_Info(pkgModel);
		}else if(pkgModel.getEnd().equals("수정")){
			pkgStatusService.updateStatus4Verify(pkgStatusModel);
			pkgStatusVerifyService.updatePKGVerify_Info(pkgModel);
		}
		//검증완료시
		else{
			//등록된status가 없으면 create
			if(pkgStatusDAO.status_gubun_verify(pkgStatusModel) == 0){
				pkgStatusService.createStatus4Verify(pkgStatusModel);
			}else{//있으면 update
				pkgStatusService.updateStatus4Verify(pkgStatusModel);
			}
			
			//등록된verify가 없으면 create
			if(pkgStatusVerifyService.getPKG_verify_gubun(pkgModel) == 0){
				pkgStatusVerifyService.createPKGVerify_Info(pkgModel);
			}else{//있으면 update
				pkgStatusVerifyService.updatePKGVerify_Info(pkgModel);
			}
		}
		
		//검증완료시 pkgmaster태이블 - S 수정
		if(end.equals("complete")){
			pkgStatusVerifyService.to_pkgmaster_verifyinfo(verifyTemModel);
		}
		
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/pkgmg/pkg/selectVerifyTem_Ajax_read.do")
	public String selectVerifyTem_Ajax_read(VerifyTemModel verifyTemModel, PkgModel pkgModel, Model model) throws Exception {
		verifyTemModel.setVerify_type(pkgModel.getVerify_type());
		String [] verify_seq_ver = pkgModel.getVerify_tem_info().split("_");
	
		verifyTemModel.setVerify_seq(verify_seq_ver[0]);
		verifyTemModel.setVerify_ver(Integer.parseInt(verify_seq_ver[1]));
		List<VerifyTemModel> List_verifyQuest = pkgStatusVerifyService.getList_verifyQuest(verifyTemModel);
		
		model.addAttribute("List_verifyQuest", List_verifyQuest);
		model.addAttribute("verifyTemModel", verifyTemModel);
		model.addAttribute("PkgModel", pkgModel);
		
		return "/pkgmg/pkg/Pkg_Status_Verify_select_Ajax_Read";
	}
	
	@RequestMapping(value = "/pkgmg/pkg/PkgStatusSecurity_Ajax_Read.do")
	public String PkgStatusSecurity_Ajax_Read(VerifyTemModel verifyTemModel, PkgModel pkgModel, 
			SysModel sysModel, Model model) throws Exception {
		verifyTemModel.setVerify_type(pkgModel.getVerify_type());
		String vertype = "32";

		String retUrl = "/pkgmg/pkg/Pkg_Status_"+vertype+"_Ajax_Read";
		
		verifyTemModel.setSeq_num(pkgModel.getPkg_seq());
		int audit_rate = 0;
//		pkgStatusVerifyService.getAudit_Rate(verifyTemModel); //진행률
		
		//검증타입별 각 담당자정보 가져오기
		SysModel sysModelData = systemService.read(sysModel);
		PkgModel pkgModelData = pkgService.read(pkgModel);
		
		model.addAttribute("verifyTemModel", verifyTemModel);
		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("pkgModelData", pkgModelData);
		model.addAttribute("SysModel", sysModelData);
		model.addAttribute("audit_rate", audit_rate);

		return retUrl;
	}

}

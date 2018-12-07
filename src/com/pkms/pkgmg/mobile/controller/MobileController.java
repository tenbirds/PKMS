package com.pkms.pkgmg.mobile.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.pkgmg.mobile.service.MobileServiceIf;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.pkms.pkgmg.pkg.service.PkgEquipmentServiceIf;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.pkms.pkgmg.pkg.service.PkgStatusServiceIf;
import com.pkms.pkgmg.pkg.service.PkgUserServiceIf;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.pkg21.service.Pkg21ServiceIf;
import com.pkms.pkgmg.pkg21.service.Pkg21StatusServiceIf;



/**
 * 모바일 승인을 위한 상세 페이지 관련 클래스<br>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class MobileController {

	/**
	 * 모바일 서비스
	 */
	@Resource(name = "MobileService")
	private MobileServiceIf mobileService;

	/**
	 * PKG 상태별 서비스
	 */
	@Resource(name = "PkgStatusService")
	private PkgStatusServiceIf pkgStatusService;
	
	/**
	 * PKG 기본정보
	 */
	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;
	
	/**
	 * PKG 장비정보
	 */
	@Resource(name = "PkgEquipmentService")
	private PkgEquipmentServiceIf pkgEquipmentService;
	
	@Resource(name = "Pkg21Service")
	private Pkg21ServiceIf pkg21Service;
	
	@Resource(name = "PkgUserService")
	private PkgUserServiceIf pkgUserService;
	
	@Resource(name = "Pkg21StatusService")
	private Pkg21StatusServiceIf pkg21StatusService;
	
	/**
	 * 모바일용 상세 페이지 조회
	 * @param pkgModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/mobile/Mobile_Read.do")
	public String read(PkgModel pkgModel, Model model) throws Exception {
		String selected_status = pkgModel.getSelected_status();
		String startd="";
		String endd="";

		if(!StringUtils.hasLength(pkgModel.getEndD()) || !StringUtils.hasLength(pkgModel.getStartD())){
		}else{			
			startd=pkgModel.getStartD();
			endd=pkgModel.getEndD();
		}
		
		pkgModel = mobileService.read(pkgModel);
		pkgModel.setContent(pkgModel.getContent().replace("\n", "<br>").replace(" ", "&nbsp;"));
		pkgModel.setRm_issue_comment(pkgModel.getRm_issue_comment().replace("\n", "<br>").replace(" ", "&nbsp;"));
		//보완적용내역목록
		List<PkgModel> pkgModelList = mobileService.readList_bp_step3(pkgModel);
		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("PkgModelList", pkgModelList);
		
		//검증완료목록
		PkgModel pkgModelSktStep3 = mobileService.read_skt_step3(pkgModel);
//		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("PkgModelSktStep3", pkgModelSktStep3);
		
		//검증진도율
		PkgModel pkgModelProgress = mobileService.read_progress(pkgModel);
//		model.addAttribute("PkgModel", pkgModel); <--  ?? 
		model.addAttribute("PkgModelProgress", pkgModelProgress);
		
		pkgModel.setSelected_status(selected_status);
		PkgModel pkgModelStatus = pkgStatusService.read(pkgModel);

		PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
		pkgEModel.setPkg_seq(pkgModel.getPkg_seq());

		List<PkgEquipmentModel> eModelList = pkgEquipmentService.readList(pkgEModel);
		pkgModel.setPkgEquipmentModelList(eModelList);
		
		pkgModel.setSelected_status(selected_status);

		pkgModel.setStartD(startd);
		pkgModel.setEndD(endd);

		model.addAttribute("PkgModel", pkgModel);
		model.addAttribute("PkgModelStatus", pkgModelStatus);
		
		List<PkgModel> printRead_Time = pkgService.printRead_Time(pkgModel);
		List<PkgModel> pkgEqList = (List<PkgModel>) pkgService.printRead_EQ(pkgModel);
		
		
		model.addAttribute("PkgTimeList", printRead_Time);
		model.addAttribute("PkgEqList", pkgEqList);
//		model.addAttribute("PkgModelStatus2", pkgEquipmentModelList);

		return "/pkgmg/mobile/Mobile_Read";
	}
	
	private Pkg21Model valueSetting(Pkg21Model p21Model, Pkg21Model pkg21Model) throws Exception{
		p21Model.setSession_user_email(pkg21Model.getSession_user_email());
		p21Model.setSession_user_group_id(pkg21Model.getSession_user_group_id());
		p21Model.setSession_user_group_name(pkg21Model.getSession_user_group_name());
		p21Model.setSession_user_id(pkg21Model.getSession_user_id());
		p21Model.setSession_user_mobile_phone(pkg21Model.getSession_user_mobile_phone());
		p21Model.setSession_user_name(pkg21Model.getSession_user_name());
		p21Model.setSession_user_type(pkg21Model.getSession_user_type());
		
		p21Model.setPkg_seq(pkg21Model.getPkg_seq());
		p21Model.setStatus(pkg21Model.getStatus());
		p21Model.setSelect_status(pkg21Model.getSelect_status());
		p21Model.setDev_yn(pkg21Model.getDev_yn());
		p21Model.setStatus_dev(pkg21Model.getStatus_dev());
		p21Model.setSystem_seq(pkg21Model.getSystem_seq());
		p21Model.setMaster_file_id(pkg21Model.getMaster_file_id());
		
		p21Model.setContent(pkg21Model.getContent());
		p21Model.setContent_pn(pkg21Model.getContent_pn());
		p21Model.setContent_cr(pkg21Model.getContent_cr());
		p21Model.setContent_self(pkg21Model.getContent_self());
		p21Model.setContent_invest(pkg21Model.getContent_invest());
		p21Model.setTtm(pkg21Model.getTtm());
		p21Model.setSystem_name(pkg21Model.getSystem_name());
		
		p21Model.setCha_yn(pkg21Model.getCha_yn());
		p21Model.setVol_yn(pkg21Model.getVol_yn());
		p21Model.setSec_yn(pkg21Model.getSec_yn());
		
		p21Model.setSer_downtime(pkg21Model.getSer_downtime());
		p21Model.setImpact_systems(pkg21Model.getImpact_systems());
		p21Model.setCheck_seqs_e(pkg21Model.getCheck_seqs_e());
		
		p21Model.setTitle(pkg21Model.getTitle());
		return p21Model;
	}
	
	@RequestMapping(value = "/pkgmg/mobile/Mobile_21Read.do")
	public String mobile21_read(Pkg21Model pkg21Model, Model model) throws Exception {
		String status_chk = pkg21Model.getStatus_chk();
		String charge_gubun = "";
		String select_status = "";
		if("R".equals(status_chk)){
			charge_gubun = pkg21Model.getCharge_gubun();
		}
		pkg21Model = pkg21Service.read(pkg21Model);

		if("DA".equals(charge_gubun)){
			select_status = "111";
		}else if("DR".equals(charge_gubun)){
			select_status = "111";
		}else if("AU".equals(charge_gubun)){
			select_status = "121";
		}else if("AR".equals(charge_gubun)){
			select_status = "121";
		}else if("AS".equals(charge_gubun)){
			select_status = "131";
		}else if("CA".equals(charge_gubun)){
			select_status = "161";
		}else if("VA".equals(charge_gubun)){
			select_status = "151";
		}else{
			select_status = pkg21Model.getStatus();
		}

		Pkg21Model p21Model = new Pkg21Model();
		pkg21Model.setSelect_status(select_status);

		if(null != pkg21StatusService.read(pkg21Model)){
			if("R".equals(status_chk)){
				pkg21Model.setSelect_status(select_status);
				pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
				p21Model = pkg21StatusService.read(pkg21Model);
	
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
	
				pkgUserModel.setCharge_gubun(charge_gubun);
				p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
				p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
				
				if("LA".equals(charge_gubun)){
					if("141".equals(select_status) || "134".equals(select_status)){
						select_status = "141";
					}else{
						select_status = "143";
					}
				}
				
				
				if("151".equals(select_status)){
					p21Model.setPkg21ModelList(pkg21Service.getPkgVol(pkg21Model));
				}
			}
		}
		
		if("131".equals(pkg21Model.getStatus()) || "132".equals(pkg21Model.getStatus()) ||
				"133".equals(pkg21Model.getStatus()) || "134".equals(pkg21Model.getStatus())){
			p21Model.setPkgEquipmentModelList(pkg21Service.getPkgEquipment(pkg21Model, "S"));
		}
		
		if("141".equals(pkg21Model.getStatus()) || "142".equals(pkg21Model.getStatus()) ||
				"143".equals(pkg21Model.getStatus())){
			p21Model.setPkgEquipmentModelList(pkg21Service.getPkgEquipment(pkg21Model, "E"));
			p21Model.setPkgEquipmentModelList4E(pkg21Service.getPkgEquipment4E(pkg21Model, "S"));
		}
		
		pkg21Model.setSelect_status(select_status);

		p21Model = valueSetting(p21Model, pkg21Model);
		p21Model.setPkg21FileModelList(pkg21Service.pkg_result_list(pkg21Model));

		p21Model.setStatus_chk(status_chk);
		p21Model.setCharge_gubun(charge_gubun);
		
		
		model.addAttribute("Pkg21Model", p21Model);
		return "/pkgmg/mobile/Mobile21_Read";
	}

	
}

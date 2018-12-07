package com.pkms.pkgmg.pkg21.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.newmail.model.NewMailModel;
import com.pkms.common.newmail.service.NewMailServiceIf;
import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg21.service.Pkg21ServiceIf;
import com.pkms.pkgmg.pkg21.service.Pkg21StatusServiceIf;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.service.EquipmentServiceIf;
import com.pkms.sys.group1.service.Group1ServiceIf;
import com.pkms.sys.group2.service.Group2ServiceIf;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.sys.system.service.SystemServiceIf;
import com.wings.model.CodeModel;
import com.wings.util.DateUtil;
import com.wings.util.WingsStringUtil;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.pkms.pkgmg.pkg.service.PkgEquipmentServiceIf;
import com.pkms.pkgmg.pkg.service.PkgUserServiceIf;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

@Controller
public class Pkg21Controller {
	@Resource(name = "Pkg21Service")
	private Pkg21ServiceIf pkg21Service;
	
	@Resource(name = "Pkg21StatusService")
	private Pkg21StatusServiceIf pkg21StatusService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	@Resource(name = "Group1Service")
	private Group1ServiceIf group1Service;

	@Resource(name = "Group2Service")
	private Group2ServiceIf group2Service;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "PkgUserService")
	private PkgUserServiceIf pkgUserService;
	
	@Resource(name = "PkgEquipmentService")
	private PkgEquipmentServiceIf pkgEquipmentService;
	
	@Resource(name = "EquipmentService")
	private EquipmentServiceIf equipmentService;

	@Resource(name = "NewMailService")
	protected NewMailServiceIf newMailService;
	
	@Resource(name = "AttachFileService")
	protected AttachFileServiceIf attachFileService;
	
	static Logger logger = Logger.getLogger(Pkg21Controller.class);
	
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_ReadList.do")
	public String readList(Pkg21Model pkg21Model, Model model) throws Exception{
		/*
		 * 페이징 모델 생성
		 */
		// 기본 검색 기간 설정
		if (!StringUtils.hasLength(pkg21Model.getDate_start()) || !StringUtils.hasLength(pkg21Model.getDate_end())) {
			pkg21Model.setDate_start(DateUtil.formatDateByMonth(DateUtil.format(), -3));
			pkg21Model.setDate_end(DateUtil.dateFormat());
		}
		
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(pkg21Model);

		List<Pkg21Model> pkg21List = (List<Pkg21Model>) pkg21Service.readList(pkg21Model);
		paginationInfo.setTotalRecordCount(pkg21Model.getTotalCount());
		
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
		if(pkg21Model.getGroup1Condition() != null && !"".equals(pkg21Model.getGroup1Condition())) {
			SysModel sysModel = new SysModel();
			sysModel.setGroup1_seq(pkg21Model.getGroup1Condition());
			List<SysModel> group2List = group2Service.readList(sysModel);
			for (SysModel group2 : group2List) {
				CodeModel codeModel = new CodeModel();
				codeModel.setCode(group2.getGroup2_seq());
				codeModel.setCodeName(group2.getName());
				group2Items.add(codeModel);
			}
		}
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("Pkg21Model", pkg21Model);
		model.addAttribute("Pkg21ModelList", pkg21List);
		model.addAttribute("Group1List", group1Items);
		model.addAttribute("Group2List", group2Items);
		
		return "/pkgmg/pkg21/Pkg21_ReadList";
	}
	
	/**
	 * 신규/수정 페이지
	 * @param pkg21Model
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Read.do")
	public String read(Pkg21Model pkg21Model, Model model) throws Exception{
		//pkg_seq가 있는 경우 data read
		if("read".equals(pkg21Model.getRead_gubun())){
			pkg21Model = pkg21Service.read(pkg21Model);
		}
		
		/*
		 * 20181203 eryoon
		 * 초대적용 시 확대적용이 남은 갯수 체크
		 */
		pkg21Model.setEq_cnt(pkg21Service.getEqCount(pkg21Model));
		
		pkg21Model.setSelect_status("101");
		model.addAttribute("Pkg21Model", pkg21Model);
		return "/pkgmg/pkg21/Pkg21_Read";
	}
	
	/**
	 * 신규 저장
	 * @param pkg21Model
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Create.do")
	public String create(Pkg21Model pkg21Model, Model model, HttpServletRequest request) throws Exception {
		pkg21Service.create(pkg21Model, request);
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getPkg_seq(), "");
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Update.do")
	public String update(Pkg21Model pkg21Model, Model model) throws Exception {
		pkg21Service.update(pkg21Model);
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getPkg_seq(), "");
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Delete.do")
	public String delete(Pkg21Model pkg21Model, Model model) throws Exception {
		pkg21Service.pkg_delete(pkg21Model);
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getPkg_seq(), "");
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Status_Read.do")
	public String statusRead(Pkg21Model pkg21Model, Model model) throws Exception{
		//pkg_seq가 있는 경우 data read
		String retUrl = "";
		retUrl = "/pkgmg/pkg21/Pkg21_"+pkg21Model.getSelect_status()+"_Read";
		logger.debug("select_status: " + pkg21Model.getSelect_status());
		
		Pkg21Model p21Model = new Pkg21Model();
		p21Model = valueSetting(p21Model, pkg21Model);
		
		if("102".equals(pkg21Model.getSelect_status())){
			if("101".equals(pkg21Model.getStatus())){
			}else{
				List<Pkg21Model> readCellUserList = pkg21StatusService.readCellUserList(pkg21Model);
				p21Model = pkg21StatusService.read(pkg21Model);	
				
				/*
				 * 20181115 eryoon 추가 
				 * cell user
				 */
				p21Model.setReadCellUserList(readCellUserList);
				
				p21Model = valueSetting(p21Model, pkg21Model);
			}
		} else if("111".equals(pkg21Model.getSelect_status())){
			if(("102".equals(pkg21Model.getStatus())) || ("102".equals(pkg21Model.getStatus_dev())) ){
				//승인자 목록
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkg21Model.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.DA);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				p21Model.setSystemUserModelList(sysModel.getSystemUserList());
			} else {
				String select_status = pkg21Model.getSelect_status();
				pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
				p21Model = pkg21StatusService.read(pkg21Model);
				pkg21Model.setSelect_status(select_status);
				p21Model = valueSetting(p21Model, pkg21Model);
				p21Model = value111(p21Model, pkg21Model);
			}
			p21Model.setPkg21FileModelList(pkg21Service.pkg_result_list(pkg21Model));
		} else if("121".equals(pkg21Model.getSelect_status())){
			if("Y".equals(pkg21Model.getDev_yn())){
				if("114".equals(pkg21Model.getStatus())){
					//승인자 목록
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
				}else{
					String select_status = pkg21Model.getSelect_status();
					pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
					p21Model = pkg21StatusService.read(pkg21Model);
					pkg21Model.setSelect_status(select_status);
					p21Model = valueSetting(p21Model, pkg21Model);
					p21Model = value121(p21Model, pkg21Model);
				}
			} else {
				if("102".equals(pkg21Model.getStatus())){
					//승인자 목록
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
				}else{
					String select_status = pkg21Model.getSelect_status();
					pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
					p21Model = pkg21StatusService.read(pkg21Model);
					pkg21Model.setSelect_status(select_status);
					p21Model = valueSetting(p21Model, pkg21Model);
					p21Model = value121(p21Model, pkg21Model);
				}
			}
			p21Model.setPkg21FileModelList(pkg21Service.pkg_result_list(pkg21Model));
		} else if("161".equals(pkg21Model.getSelect_status())){
			String select_status = pkg21Model.getSelect_status();
			if(!"N".equals(pkg21Model.getCha_yn())){
				if(null != pkg21StatusService.real_status(pkg21Model)){
					pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));					
					p21Model = pkg21StatusService.read(pkg21Model);

					PkgUserModel pkgUserModel = new PkgUserModel();
					pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
					
					pkgUserModel.setCharge_gubun("CA");
					p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
					p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
					
					Pkg21Model p161Model = new Pkg21Model();
					p161Model.setPkg_seq(pkg21Model.getPkg_seq());
					p161Model.setSelect_status("161");
					
					p161Model = pkg21StatusService.read(p161Model);
					//p21Model.setEq_cnt(p161Model.getEq_cnt());
					p21Model.setReg_date_plan(p161Model.getReg_date());
					p21Model.setReg_plan_user(p161Model.getReg_user_name());
				}else{
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.CA);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
					
					pkg21Model.setSelect_status("121");
					Pkg21Model cvtModel = new Pkg21Model();
					pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
					cvtModel = pkg21StatusService.read(pkg21Model);
					p21Model.setCol3(cvtModel.getCol6());
					//pkg21Model.setEq_cnt(cvtModel.getEq_cnt());
				}
				
				PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
				pkgEModel.setPkg_seq(pkg21Model.getPkg_seq());
				pkgEModel.setWork_result("양호");
				if(null != pkgEquipmentService.read21List(pkgEModel)){					
					p21Model.setPkgEquipmentModelList(pkgEquipmentService.read21List(pkgEModel));
				}
			}
			pkg21Model.setSelect_status(select_status);
			p21Model = valueSetting(p21Model, pkg21Model);
		} else if("151".equals(pkg21Model.getSelect_status())){
			String select_status = pkg21Model.getSelect_status();
			if(!"N".equals(pkg21Model.getVol_yn())){
				
				if(null != pkg21StatusService.real_status(pkg21Model)){
					pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));					
					p21Model = pkg21StatusService.read(pkg21Model);

					PkgUserModel pkgUserModel = new PkgUserModel();
					pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
					
					pkgUserModel.setCharge_gubun("VA");
					p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
					p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
					
					Pkg21Model p161Model = new Pkg21Model();
					p161Model.setPkg_seq(pkg21Model.getPkg_seq());
					p161Model.setSelect_status("151");
					
					p161Model = pkg21StatusService.read(p161Model);
					p21Model.setReg_date_plan(p161Model.getReg_date());
					p21Model.setReg_plan_user(p161Model.getReg_user_name());
					
				}
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkg21Model.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.VA);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				p21Model.setSystemUserModelList(sysModel.getSystemUserList());
				
				p21Model.setPkg21ModelList(pkg21Service.getPkgVol(pkg21Model));
			}
			
			pkg21Model.setSelect_status(select_status);
			p21Model = valueSetting(p21Model, pkg21Model);
		}else if("171".equals(pkg21Model.getSelect_status())){
			if(null != pkg21StatusService.real_status(pkg21Model)){		
				p21Model = pkg21StatusService.read(pkg21Model);
			}else{
				p21Model = pkg21StatusService.readEqCnt(pkg21Model);
			}
			logger.debug("===============================================================");
			logger.debug("eq_cnt: "+p21Model.getEq_cnt());
			logger.debug("===============================================================");
			
			p21Model = valueSetting(p21Model, pkg21Model);
		}else if("131".equals(pkg21Model.getSelect_status())){
			pkg21Model.setPkgEquipmentModelList(pkg21Service.getPkgEquipment(pkg21Model, "S"));
			
			if("124".equals(pkg21Model.getStatus())){
				//승인자 목록
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkg21Model.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				p21Model.setSystemUserModelList(sysModel.getSystemUserList());
			}else{
				//pkg21StatusService.real_status 를 안찾는 이유는 어차피 pkg_status에 내용이 없다. reg 정보 밖에..
				p21Model = pkg21StatusService.read(pkg21Model);
				p21Model = valueSetting(p21Model, pkg21Model);
				
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());

				pkgUserModel.setCharge_gubun("AS");
				p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
				p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
				
				Pkg21Model p111Model = new Pkg21Model();
				p111Model.setPkg_seq(pkg21Model.getPkg_seq());
				p111Model.setSelect_status("131");
				
				p111Model = pkg21StatusService.read(p111Model);
				p21Model.setReg_date_plan(p111Model.getReg_date());
				p21Model.setReg_plan_user(p111Model.getReg_user_name());
			}
			p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
		}else if("133".equals(pkg21Model.getSelect_status())){
			if(!"132".equals(pkg21Model.getStatus())){				
				String select_status = pkg21Model.getSelect_status();
				pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));					
				p21Model = pkg21StatusService.read(pkg21Model);
				
				Pkg21Model p111Model = new Pkg21Model();
				p111Model.setPkg_seq(pkg21Model.getPkg_seq());
				p111Model.setSelect_status("133");
				
				if(null != pkg21StatusService.read(p111Model)){					
					p111Model = pkg21StatusService.read(p111Model);
					p21Model.setReg_date_plan(p111Model.getReg_date());
					p21Model.setReg_plan_user(p111Model.getReg_user_name());
				}
				
				if("133".equals(pkg21Model.getStatus())){
					p21Model.setEq_cnt_left(pkg21Service.readEQCntLeft(pkg21Model));
				}else{				
					p111Model.setPkg_seq(pkg21Model.getPkg_seq());
					if("139".equals(pkg21Model.getStatus())){
						p111Model.setSelect_status("139");
					}else{						
						p111Model.setSelect_status("134");
					}
					if(null != pkg21StatusService.read(p111Model)){						
						p111Model = pkg21StatusService.read(p111Model);
						p21Model.setReg_date_result(p111Model.getReg_date());
						p21Model.setReg_result_user(p111Model.getReg_user_name());
					}
				}
				
				pkg21Model.setSelect_status(select_status);
				p21Model = valueSetting(p21Model, pkg21Model);
			}
		}else if("141".equals(pkg21Model.getSelect_status())){
			pkg21Model.setPkgEquipmentModelList(pkg21Service.getPkgEquipment(pkg21Model, "E"));
			pkg21Model.setPkgEquipmentModelList4E(pkg21Service.getPkgEquipment4E(pkg21Model, "S"));
			if("134".equals(pkg21Model.getStatus())){
				//승인자 목록
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkg21Model.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				p21Model.setSystemUserModelList(sysModel.getSystemUserList());
			}else{
				String select_status = pkg21Model.getSelect_status();
				if(!"141".equals(pkg21Model.getStatus())){
					pkg21Model.setSelect_status("142");
				}
				if(null != pkg21StatusService.read(pkg21Model)){					
					p21Model = pkg21StatusService.read(pkg21Model);
				}
				pkg21Model.setSelect_status(select_status);
				p21Model = valueSetting(p21Model, pkg21Model);
				
				if("N".equals(p21Model.getUpdate_gubun())){
					PkgUserModel pkgUserModel = new PkgUserModel();
					pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
					
					pkgUserModel.setCharge_gubun("LA");
					if(null != pkgUserService.readList(pkgUserModel) && 0 < pkgUserService.readList(pkgUserModel).size()){						
						p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
						p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
					}else{
						//승인자 목록
						SysModel sysModel = new SysModel();
						sysModel.setSystem_seq(pkg21Model.getSystem_seq());
						sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
						sysModel = systemService.readUsersAppliedToSystem(sysModel);
						p21Model.setSystemUserModelList(sysModel.getSystemUserList());
					}
				}else{
					//승인자 목록
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
				}
				
				Pkg21Model p111Model = new Pkg21Model();
				p111Model.setPkg_seq(pkg21Model.getPkg_seq());
				p111Model.setSelect_status("141");
				
				if(null != pkg21StatusService.read(p111Model)){					
					p111Model = pkg21StatusService.read(p111Model);
					p21Model.setReg_date_plan(p111Model.getReg_date());
					p21Model.setReg_plan_user(p111Model.getReg_user_name());
				}
			}
			p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			p21Model.setPkgEquipmentModelList4E(pkg21Model.getPkgEquipmentModelList4E());
		}else if("143".equals(pkg21Model.getSelect_status())){
			pkg21Model.setPkgEquipmentModelList(pkg21Service.getPkgEquipment(pkg21Model, "E"));

			if(!"142".equals(pkg21Model.getStatus())){
				if(null != pkg21StatusService.read(pkg21Model)){					
					p21Model = pkg21StatusService.read(pkg21Model);
					
					p21Model.setReg_date_plan(p21Model.getReg_date());
					p21Model.setReg_plan_user(p21Model.getReg_user_name());
				}

				if(!"143".equals(pkg21Model.getStatus())){
					Pkg21Model p111Model = new Pkg21Model();
					p111Model.setPkg_seq(pkg21Model.getPkg_seq());
					if("149".equals(pkg21Model.getStatus())){
						p111Model.setSelect_status("149");
					}else{
						p111Model.setSelect_status("199");						
					}
					
					if(null != pkg21StatusService.read(pkg21Model)){						
						p111Model = pkg21StatusService.read(p111Model);
						p21Model.setReg_date_result(p111Model.getReg_date());
						p21Model.setReg_result_user(p111Model.getReg_user_name());
					}
				}
				
				p21Model = valueSetting(p21Model, pkg21Model);
			}
			p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			
			pkgUserModel.setCharge_gubun("LA");
			if(null != pkgUserService.readList(pkgUserModel) && 0 < pkgUserService.readList(pkgUserModel).size()){
				Pkg21Model ynModel = new Pkg21Model();
				
				ynModel = pkg21StatusService.pkg_user_all_yn(pkg21Model);
				p21Model.setPkg_user_all_yn(ynModel.getPkg_user_all_yn());
			}else{
				p21Model.setPkg_user_all_yn("N");
			}
		}

		/**
		 * 20181203 eryoon
		 * * 초대적용 시 확대적용이 남은 갯수 체크
		 */
		 
		p21Model.setEq_cnt(pkg21Service.getEqCount(pkg21Model));
		
		model.addAttribute("Pkg21Model", p21Model);
		return retUrl;
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Status_Create.do")
	public String status_create(Pkg21Model pkg21Model, Model model, HttpServletRequest request) throws Exception {
		
		ArrayList<String> gubun =  new ArrayList<String>() ; // mail send 위한 gubun 값 set
		NewMailModel inputModel = new NewMailModel();//메일 title , content set
		String mailType="SYSUSER";
		String user_ord=pkg21Model.getOrd();
		
		String _fin = "Y"; 
		
		if(user_ord.isEmpty()) {
			user_ord = "1";
		}else {
			int ord = Integer.parseInt(user_ord)+1;
			user_ord = String.valueOf(ord);
		}
		
		
		
		String[] chkSeqs = pkg21Model.getChk_seqs();
		String[] chk_results = pkg21Model.getChk_results();
		
		if("151".equals(pkg21Model.getSelect_status())){  // 용량검증
			if("151".equals(pkg21Model.getSave_status())){
				pkg21Service.pkg_vol_create(pkg21Model);
			}else{
				pkg21Service.pkg_vol_update(pkg21Model);
			}
		}
		
		if("131".equals(pkg21Model.getSave_status())){
			equipment_create(pkg21Model, "S");
		}
		if("140".equals(pkg21Model.getSave_status())){
			equipment_create(pkg21Model, "E");
			pkg21Service.tangoWork(pkg21Model, "E");
		}
		/* 일정 저장이 아닌 승인자에 대한 요청이기에 장비수립 할 필요 없음 (요청이 있을 때 열면 됨)
		 * if("141".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model);
			pkg21Service.tangoWork(pkg21Model, "E");
		}*/
		if("143".equals(pkg21Model.getSave_status()) || "144".equals(pkg21Model.getSave_status())){
			equipment_work_update(pkg21Model);
		}
		if("133".equals(pkg21Model.getSelect_status())){
			if("132".equals(pkg21Model.getStatus())){
				if("133".equals(pkg21Model.getSave_status())){					
					equipment_s_work_update(pkg21Model);
				}
			}
		}
		//개발검증 마지막 승인 확인 마지막 승인시 저장
		if("112".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "DA");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("111");
			}
		}else if("114".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "DR");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("113");
			}
		}else if("122".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "AU");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("121");
			}
		}else if("124".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "AR");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("123");
			}
		}else if("162".equals(pkg21Model.getSave_status())){//과금검증
			_fin = fin_active(pkg21Model, "CA");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("161");
			}
		}else if("152".equals(pkg21Model.getSave_status())){//용량검증
			_fin = fin_active(pkg21Model, "VA");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("151");
			}
		}else if("132".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "AS");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("131");
			}else {
				mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
				inputModel.setOrd("100");//승인담당자 ord 132 일때는 메일을 안보내야 하기때문에 없는 ord
				
				logger.debug("----------tangoWork START------");
				pkg21Service.tangoWork(pkg21Model, "S");
				logger.debug("----------tangoWork END------");
				
			}
			
		}else if("142".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "LA");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("141");
			}else {
				mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
				inputModel.setOrd("100");//승인담당자 ord 132 일때는 메일을 안보내야 하기때문에 없는 ord
			}
		}else if("144".equals(pkg21Model.getSave_status())){
			_fin = "N";
			pkg21Model.setSave_status("143");
		}
		
		if("Y".equals(_fin)){
			
			if("102".equals(pkg21Model.getSelect_status())){   // 검증결과 최초 저장
				pkg21Model.setVerify_date_start(pkg21Model.getCol5());
				pkg21Model.setVerify_date_end(pkg21Model.getCol6());
				
				if("D".equals(pkg21Model.getDev_yn())){
					pkg21Model.setStatus_dev("102");
				}
				gubun.add("VU");//상용 검증담당자
				gubun.add("LV");//현장적용(현장담당자)
				if("N".equals(pkg21Model.getDev_yn())){
					gubun.add("DV");//개발 검증담당자
				}
				if("Y".equals(pkg21Model.getVol_yn()) ){
					gubun.add("VO");//용량검증
				}
				
				//cell 참여 인력 생성
				if ("Y".equals(pkg21Model.getCol17())) {
					createCellUserList (pkg21Model, request);
				}
				
				inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"2"));
				inputModel.setMailTitle("SVT 계획결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목				
				String content ="";
				content = newMailService.genrerateString(null ,pkg21Model , "2",""); // SVT 계획결과 :2
				inputModel.setMailContent(content); // 내용
			}
			
			if("111".equals(pkg21Model.getSave_status()) || "113".equals(pkg21Model.getSave_status()) ||    //개발 승인
			   "121".equals(pkg21Model.getSave_status()) || "123".equals(pkg21Model.getSave_status()) ||	//cvt 상용 승인
			   "161".equals(pkg21Model.getSave_status()) || "151".equals(pkg21Model.getSave_status()) ||	
			   "131".equals(pkg21Model.getSave_status()) || "141".equals(pkg21Model.getSave_status())){
				//승인자 정보 저장
				this.pkg_user_create(pkg21Model);
				

				
				
				
				
				//mail 정보 set
				if("111".equals(pkg21Model.getSave_status()) || "113".equals(pkg21Model.getSave_status())) { // 개발검증계획 요청시 1차 승인 담당자에게만 메일발송
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					gubun.add("DA");//개발 승인담당자
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차
					
						if("111".equals(pkg21Model.getSave_status())) { // DVT 계획승인요청
							inputModel.setMailTitle("DVT 검증계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
							inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"3"));
							String content ="";
							content = newMailService.genrerateString(null ,pkg21Model , "3" ,user_ord); // DVT 계획 :3
							inputModel.setMailContent(content); // 내용
						}
										
										
						if("113".equals(pkg21Model.getSave_status())) { // DVT 계획결과 승인요청
							inputModel.setMailTitle("DVT 검증결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
							inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"4"));
							String content ="";
							content = newMailService.genrerateString(null ,pkg21Model , "4" ,user_ord); // DVT 결과 승인요청							
							inputModel.setMailContent(content); // 내용
						}				
					
					
				}//mail 정보 set end		
				
				//mail 정보 set
				if("123".equals(pkg21Model.getSave_status()) ) { // CVT 검증결과 1차 승인 담당자에게만 메일발송
						mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
						gubun.add("AR");//상용 결과승인담당자						
						inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
						inputModel.setMailTitle("CVT 검증결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"7"));
						String content ="";
						content = newMailService.genrerateString(null ,pkg21Model , "7" ,user_ord); // CVT 결과 승인요청						
						inputModel.setMailContent(content); // 내용
						
				}//mail 정보 set end	
				
				
	
			}
			
			if("141".equals(pkg21Model.getSave_status())){
				pkg21StatusService.update(pkg21Model);
			}else{
				if("140".equals(pkg21Model.getSave_status())){
					pkg21Model.setSave_status("141");
					
					//최초 일정저장할경우 메일보내지 않음
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					inputModel.setOrd("100");//승인담당자 ord 132 일때는 메일을 안보내야 하기때문에 없는 ord	
				}
				
				if("133".equals(pkg21Model.getSelect_status())){
					if("199".equals(pkg21Model.getSave_status())){
						pkg21Model.setSave_status("134");
						pkg21StatusService.create(pkg21Model);
						pkg21Model.setSave_status("199");
					}
				}
				pkg21StatusService.create(pkg21Model);
			}

			//개발검증 저장 시
			if("111".equals(pkg21Model.getSelect_status())){
				if("D".equals(pkg21Model.getDev_yn())){
					pkg21Service.pkg_dev_status_update(pkg21Model);	
				}else{
					//pkg_master status update
					pkg21Service.pkg_status_update(pkg21Model);	
					
					if("112".equals(pkg21Model.getSave_status()) ) { //계획 1차 승인 담당자가 승인 할때 2차 승인담당자에게 메일발송
						mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
						gubun.add("DA");//개발 승인담당자
						inputModel.setOrd( user_ord);//1차 담당자
						inputModel.setMailTitle("DVT 검증계획 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"3"));
						String content ="";
						content = newMailService.genrerateString(null ,pkg21Model , "3" ,user_ord); // DVT 검증계획 등록						
						inputModel.setMailContent(content); // 내용
						
					}//mail 정보 set end
					if("114".equals(pkg21Model.getSave_status()) ) { //결과 1차 승인 담당자가 승인 할때 2차 승인담당자에게 메일발송		

					String	mailcheck = fin_active2(pkg21Model, "DR");
					inputModel.setMailTitle(" DVT 검증결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					String content ="";
					
						if("N".equals(mailcheck)) { // 현재 모든 담당자가 승인했는지 다시한번체크  Y: 마지막 담당자, N : 마지막담당자 아님 
							mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
							gubun.add("DR");//개발 승인담당자
							inputModel.setOrd(user_ord);//다음 담당자에게 전달
							inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"4"));
							content = newMailService.genrerateString(null ,pkg21Model , "4" ,user_ord); // DVT 검증결과 등록 승인요청
						}else {
//							inputModel.setOrd(user_ord);//다음 담당자에게 전달
							mailType ="SYSUSER";//pkg_user에서 받는사람 id 검색
							gubun.add("DV");//개발 검증담당자
							gubun.add("VU");//상용 검증담당자
							gubun.add("LV");//현장적용(현장담당자)
							if("Y".equals(pkg21Model.getVol_yn()) ){
								gubun.add("VO");//용량검증
							}
							inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"5"));
							content = newMailService.genrerateString(null ,pkg21Model , "5" ,user_ord); // DVT 검증결과 등록 승인완료
						}

						inputModel.setMailContent(content); // 내용	
					}//mail 정보 set end
					
					
					
				}
			}else if("151".equals(pkg21Model.getSelect_status()) || "161".equals(pkg21Model.getSelect_status()) ||
					 "171".equals(pkg21Model.getSelect_status())){
				if("152".equals(pkg21Model.getSave_status()) || "162".equals(pkg21Model.getSave_status()) ||
				   "171".equals(pkg21Model.getSave_status())){					
					pkg21Service.pkg_verify_update(pkg21Model);
				}

				mailType="SYSUSER";
				gubun.add("VU");//상용 검증담당자
				gubun.add("LV");//현장적용(현장담당자)
				if("N".equals(pkg21Model.getDev_yn())){
					gubun.add("DV");//개발 검증담당자
				}						
				String content ="";
				if("151".equals(pkg21Model.getSelect_status()) || "152".equals(pkg21Model.getSave_status())) {//용량검증
					inputModel.setMailTitle(" 용량검증 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					
//					String[] chkSeqs = pkg21Model.getChk_seqs();
//					String[] chk_results = pkg21Model.getChk_results();
					
					pkg21Model.setChk_seqs(chkSeqs);
					pkg21Model.setChk_results(chk_results);
					pkg21Model.setPkg21ModelList(pkg21Service.getPkgVol(pkg21Model));//검증항목리스트
					
					String	mailcheck = fin_active2(pkg21Model, "VA");
					if("N".equals(mailcheck)) { // 현재 모든 담당자가 승인했는지 다시한번체크  Y: 마지막 담당자, N : 마지막담당자 아님 
						mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
						gubun.clear();
						gubun.add("VA");//개발 승인담당자
						inputModel.setOrd(user_ord);//다음 담당자에게 전달
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"14"));
						content = newMailService.genrerateString(null ,pkg21Model , "14" ,user_ord); // 승인요청
					}else {
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"15"));
						content = newMailService.genrerateString(null ,pkg21Model , "15" ,user_ord); // 승인완료
					}
				

				}
				if("161".equals(pkg21Model.getSelect_status()) || "162".equals(pkg21Model.getSave_status())) {//과금검증
					inputModel.setMailTitle(" 과금검증 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
//					content = newMailService.genrerateString(null ,pkg21Model , "16" ,user_ord); //
					
					String	mailcheck = fin_active2(pkg21Model, "CA");
					if("N".equals(mailcheck)) { // 현재 모든 담당자가 승인했는지 다시한번체크  Y: 마지막 담당자, N : 마지막담당자 아님 
						mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
						gubun.clear();
						gubun.add("CA");//과금 승인담당자
						inputModel.setOrd(user_ord);//다음 담당자에게 전달
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"16"));
						content = newMailService.genrerateString(null ,pkg21Model , "16" ,user_ord); // 승인요청
					}else {
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"17"));
						content = newMailService.genrerateString(null ,pkg21Model , "17" ,user_ord); // 승인완료
					}

				}
				if("171".equals(pkg21Model.getSelect_status()) ) {//보안검증
					inputModel.setMailTitle("보안검증 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"18"));
					content = newMailService.genrerateString(null ,pkg21Model , "18" ,user_ord); // 171만 있는듯..
				}
				
				inputModel.setMailContent(content); // 내용	
				
				
			}else{			
				//pkg_master status update
				pkg21Service.pkg_status_update(pkg21Model);
				

				if("121".equals(pkg21Model.getSave_status()) || "122".equals(pkg21Model.getSave_status())  || "124".equals(pkg21Model.getSave_status())  ) {//cvt 계획(121,122-2차담당)/ 결과(124)
//					121:승인요청 /122 : 1차담당자의 승인
					
					String content ="";

					//122 검증계획 승인자
					String mailcheck = fin_active2(pkg21Model, "AU");// 현재 모든 담당자가 승인했는지 다시한번체크  Y: 아직 모두 승인 안함, N : 모든 담당자 승인
					if("124".equals(pkg21Model.getSave_status()) ) { //검증결과 승인 일경우
						mailcheck = fin_active2(pkg21Model, "AR");
					}
					if("121".equals(pkg21Model.getSave_status()) && "1".equals(user_ord)){ // CVT검증계획 승인요청
						mailcheck = "N";
					}
										
					
					if("N".equals(mailcheck)) {  // 결과 승인 완료  내가 마지막 사용자가 아닐때 N 
						mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
						gubun.add("AU");//상용 승인담당자						
						inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차			

						if("124".equals(pkg21Model.getSave_status())){ // CVT검증계획 승인요청
							inputModel.setMailTitle(" CVT 결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
							inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"7"));
							content = newMailService.genrerateString(null ,pkg21Model , "7" ,user_ord); // CVT검증결과 승인요청 : 7
						}else {//121
							inputModel.setMailTitle(" CVT 계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
							inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"6"));
							content = newMailService.genrerateString(null ,pkg21Model , "6" ,user_ord); // CVT검증계획 승인요청 : 6
						}
						
						
					}else { // 현재승인 담당자가 마지막일 경우
						if(("124".equals(pkg21Model.getSave_status()))) {
							inputModel.setMailTitle(" CVT 결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
							mailType ="SYSUSER";//pkg_user에서 받는사람 id 검색
							gubun.add("DV");//개발 검증담당자
							gubun.add("VU");//상용 검증담당자
							gubun.add("LV");//현장적용(현장담당자)
							if("Y".equals(pkg21Model.getVol_yn()) ){
								gubun.add("VO");//용량검증
							}
							if("Y".equals(pkg21Model.getCha_yn()) ){
								gubun.add("CH");//용량검증
							}
							inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"8"));
							content = newMailService.genrerateString(null ,pkg21Model , "8" ,""); // CVT검증결과 승인완료 : 8
							
						}else {//메일 안보냄
							mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
							inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차	
						}
					}

					inputModel.setMailContent(content); // 내용	
					
				}//mail 정보 set end
				
				if("143".equals(pkg21Model.getSave_status()) || "144".equals(pkg21Model.getSave_status())  ) {//확대적용 결과
					mailType ="SYSUSER";//pkg_user에서 받는사람 id 검색
					gubun.add("MO");//상황관제 담당자
					gubun.add("VU");//상용 검증담당자
					gubun.add("LV");//한장적용
					if("Y".equals(pkg21Model.getVol_yn()) ){
						gubun.add("VO");//용량검증
					}
					if("Y".equals(pkg21Model.getCha_yn()) ){
						gubun.add("CH");//과금검증
					}
					
					List<PkgEquipmentModel> eqments = new ArrayList<PkgEquipmentModel>();  
					Pkg21Model pkmodel = new Pkg21Model();
					pkmodel.setPkg_seq(pkg21Model.getPkg_seq());
					pkmodel.setWork_gubun("E");//S: 초도 / E: 확대
					eqments = newMailService.eqmentList(pkmodel);
					String[] selectSeqs =  new String[eqments.size()];
					for (int i = 0; i < eqments.size(); i++) {
						selectSeqs[i] = eqments.get(i).getEquipment_seq();
					} 
					pkg21Model.setCheck_seqs_e(selectSeqs);	
					
					if(pkg21Model.getCheck_seqs_e() != null && 	(pkg21Model.getCheck_seqs_e()).length != 0) {				
						List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();  // 장비운영 담당자
						NewMailModel eqment_seq = new NewMailModel();
						//이미 결과가 수립된 equipment_seq 는 'N' 값임 쿼리에 문자열 들어가면 error 배열에 N 빼기
						String[] eqments_seq = null;
						int no_cnt = 0;
						for(String seq : pkg21Model.getCheck_seqs_e()){
							if("N".equals(seq)){					
								no_cnt++;
							}
						}
						eqments_seq = new String[(pkg21Model.getCheck_seqs_e().length - no_cnt)];
						int ok_cnt = 0;
						for(String seq : pkg21Model.getCheck_seqs_e()){
							if(!"N".equals(seq)){					
								eqments_seq[ok_cnt] = seq;
								ok_cnt++;
							}
						}
//						eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
						eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
						eqment_users = newMailService.eqmentUserList(eqment_seq);
						inputModel.setAddUsers(eqment_users);
					}
					inputModel.setMailTitle("확대적용 결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					
					String content ="";
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"13"));
					content = newMailService.genrerateString(pkg21Service.getPkgEquipment(pkg21Model, "E") ,pkg21Model , "13" ,""); // 확대적용결과완료 : 13

					inputModel.setMailContent(content); // 내용	
					

				}
				
				
				if("133".equals(pkg21Model.getSave_status()) || "134".equals(pkg21Model.getSave_status()) ) {//당일 초도적용결과
					mailType ="SYSUSER";//pkg_user에서 받는사람 id 검색
//					gubun.add("VU");//장비윤영담당자 ???????????????
					gubun.add("LV");//현장적용(현장담당자)
					gubun.add("MO");//상황관제 담당자
					gubun.add("VU");//상용 검증담당자
					if("Y".equals(pkg21Model.getVol_yn()) ){
						gubun.add("VO");//용량검증
					}
					if("Y".equals(pkg21Model.getCha_yn()) ){
						gubun.add("CH");//과금검증
					}
					
					
					List<PkgEquipmentModel> eqments = new ArrayList<PkgEquipmentModel>();  
					Pkg21Model pkmodel = new Pkg21Model();
					pkmodel.setPkg_seq(pkg21Model.getPkg_seq());
					pkmodel.setWork_gubun("S");//S: 초도 / E: 확대
					eqments = newMailService.eqmentList(pkmodel);
					String[] selectSeqs =  new String[eqments.size()];
					for (int i = 0; i < eqments.size(); i++) {
						selectSeqs[i] = eqments.get(i).getEquipment_seq();
					} 
					pkg21Model.setCheck_seqs_e(selectSeqs);					
					if(pkg21Model.getCheck_seqs_e() != null && 	(pkg21Model.getCheck_seqs_e()).length != 0) {				
						List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();  // 장비운영 담당자
						NewMailModel eqment_seq = new NewMailModel();
						//이미 결과가 수립된 equipment_seq 는 'N' 값임 쿼리에 문자열 들어가면 error 배열에 N 빼기
						String[] eqments_seq = null;
						int no_cnt = 0;
						for(String seq : pkg21Model.getCheck_seqs_e()){
							if("N".equals(seq)){					
								no_cnt++;
							}
						}
						eqments_seq = new String[(pkg21Model.getCheck_seqs_e().length - no_cnt)];
						int ok_cnt = 0;
						for(String seq : pkg21Model.getCheck_seqs_e()){
							if(!"N".equals(seq)){					
								eqments_seq[ok_cnt] = seq;
								ok_cnt++;
							}
						}
//						eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
						eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
						eqment_users = newMailService.eqmentUserList(eqment_seq);
						inputModel.setAddUsers(eqment_users);
					}
					
					
					
										
					if("133".equals(pkg21Model.getSave_status())) {
						inputModel.setMailTitle(" 초도적용 결과 [당일] (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"10"));
						String content ="";
						content = newMailService.genrerateString(pkg21Service.getPkgEquipment(pkg21Model, "S") ,pkg21Model , "10" ,""); // 초도적용 결과 [당일] : 10
//						content = newMailService.genrerateString(pkg21Service.getPkgEquipment4E(pkg21Model, "S") ,pkg21Model , "10" ,""); // 초도적용 결과 [당일] : 10

						inputModel.setMailContent(content); // 내용	
					}
					if("134".equals(pkg21Model.getSave_status())) {
						inputModel.setMailTitle(" 초도적용 결과 [최종] (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
						String content ="";
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"11"));
						content = newMailService.genrerateString(pkg21Service.getPkgEquipment(pkg21Model, "S") ,pkg21Model , "11" ,""); // 초도적용 결과 [당일] : 10
//						content = newMailService.genrerateString(pkg21Service.getPkgEquipment4E(pkg21Model, "S") ,pkg21Model , "11" ,""); // 초도적용 결과 [최종] : 11

						inputModel.setMailContent(content); // 내용	
					}
					
		
					
				}
				
				//PKG 완료 버튼 -> 메일 X
				if("199".equals(pkg21Model.getSave_status()) ) {//확대적용 결과 -> pkg  or 초도적용결과 ->PKG
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					inputModel.setOrd("100");//현재 승인 담당자 ord 123....차	
//					if(pkg21Model.getCheck_seqs_e() != null && 	(pkg21Model.getCheck_seqs_e()).length != 0) {				
//						List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();  // 장비운영 담당자
//						NewMailModel eqment_seq = new NewMailModel();
//						eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
//						eqment_users = newMailService.eqmentUserList(eqment_seq);
//						inputModel.setAddUsers(eqment_users);
//					}
//					
//					if("133".equals(pkg21Model.getSelect_status()) ) { // 초도 적용결과 에서 pkg 완료
//						mailType ="SYSUSER";//pkg_user에서 받는사람 id 검색
////						gubun.add("VU");//장비윤영담당자 ???????????????
//						gubun.add("LV");//현장적용(현장담당자)
//						gubun.add("MO");//상황관제 담당자
//						gubun.add("VU");//상용 검증담당자
//						if("Y".equals(pkg21Model.getVol_yn()) ){
//							gubun.add("VO");//용량검증
//						}
//						if("Y".equals(pkg21Model.getCha_yn()) ){
//							gubun.add("CH");//과금검증
//						}
//						
//						inputModel.setMailTitle(" 초도적용 결과 [최종] (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
//						
////						List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();
////						NewMailModel eqment_seq = new NewMailModel();
////						eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
////						eqment_users = newMailService.eqmentUserList(eqment_seq);
////						inputModel.setAddUsers(eqment_users);
////new ..						
////						List<PkgEquipmentModel> eqmentListSum = pkg21Service.getPkgEquipment(pkg21Model, "S");
////						String[] eq_seqs = null;
////						int i=0;
////						if(eqmentListSum != null && 	!(eqmentListSum).isEmpty()) {
////						for(PkgEquipmentModel list : eqmentListSum) {
////							eq_seqs[i] = list.getEquipment_seq();
////						}
////						
////						List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();
////						NewMailModel eqment_seq = new NewMailModel();
////						eqment_seq.setEqment_seqs(eq_seqs);// equment seq set
////						eqment_users = newMailService.eqmentUserList(eqment_seq);
////						inputModel.setAddUsers(eqment_users);
////						}
//						
//						
//						
//						
//						
//						String content ="";
//						content = newMailService.genrerateString(null ,pkg21Model , "11" ,""); // 초도적용 결과 [최종] : 11
//						inputModel.setMailContent(content); // 내용	
//						
//						
//						
//						
//					}else { // 확대적용 pkg완료
//						mailType ="SYSUSER";//pkg_user에서 받는사람 id 검색
//						gubun.add("MO");//상황관제 담당자
//						gubun.add("VU");//상용 검증담당자
//						if("Y".equals(pkg21Model.getVol_yn()) ){
//							gubun.add("VO");//용량검증
//						}
//						if("Y".equals(pkg21Model.getCha_yn()) ){
//							gubun.add("CH");//과금검증
//						}
//						inputModel.setMailTitle(" 확대적용 결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
//
//						String content ="";
//						content = newMailService.genrerateString(pkg21Service.getPkgEquipment(pkg21Model, "E") ,pkg21Model , "13" ,""); // 확대적용 완료 : 13
//
//						inputModel.setMailContent(content); // 내용	
//						
//						
//					}
					
					
					
					
					
					
				}
				
				
				
			}
		//다음 승인자에게 mail 및 sms
		}else{
			
//			마지막 승인 담당자 일경우 다음 결과 1차승인 담당자에게 메일 보낸다
			if("111".equals(pkg21Model.getSave_status()) ) {//DVT 계획 수립, DVT 결과 
				mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
				gubun.add("DA");//개발 승인담당자
				inputModel.setOrd(user_ord);//다음 담당자에게 전달
				
				inputModel.setMailTitle("DVT 검증계획 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				String content ="";
				inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"3"));
				content = newMailService.genrerateString(null ,pkg21Model , "3" ,user_ord); // DVT 검증계획 승인요청 : 3
				inputModel.setMailContent(content); // 내용
				
				
			}
			if("114".equals(pkg21Model.getSave_status()) ) { //결과 1차 승인 담당자가 승인 할때 2차 승인담당자에게 메일발송		

			String	mailcheck = fin_active2(pkg21Model, "DR");
			inputModel.setMailTitle("DVT 검증결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
			String content ="";

				if("N".equals(mailcheck)) { // 현재 모든 담당자가 승인했는지 다시한번체크  Y: 마지막 담당자, N : 마지막담당자 아님
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					gubun.add("DR");//개발 승인담당자
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"4"));
					content = newMailService.genrerateString(null ,pkg21Model , "4" ,user_ord); // DVT 검증결과 승인요청 : 4

				}else {
					inputModel.setOrd(user_ord);//다음 담당자에게 전달
					mailType ="SYSUSER";//pkg_user에서 받는사람 id 검색
					gubun.add("DV");//개발 검증담당자
					gubun.add("VU");//상용 검증담당자
					gubun.add("LV");//현장적용(현장담당자)
					if("Y".equals(pkg21Model.getVol_yn()) ){
						gubun.add("VO");//용량검증
					}
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"5"));
					content = newMailService.genrerateString(null ,pkg21Model , "5" ,user_ord); // DVT 검증결과 승인완료 : 5
				}
				
				inputModel.setMailContent(content); // 내용	
			}//mail 정보 set end
			
			
			if("121".equals(pkg21Model.getSave_status()) || "122".equals(pkg21Model.getSave_status())  || "124".equals(pkg21Model.getSave_status())  ) {
//				121:승인요청 /122 : 1차담당자의 승인
				
				String content ="";				

				//122 검증계획 승인자
				String mailcheck = fin_active2(pkg21Model, "AU");// 현재 모든 담당자가 승인했는지 다시한번체크  Y: 아직 모두 승인 안함, N : 모든 담당자 승인
				if("124".equals(pkg21Model.getSave_status()) ) { //검증결과 승인 일경우
					mailcheck = fin_active2(pkg21Model, "AR");
			
				}
				if("121".equals(pkg21Model.getSave_status()) && "1".equals(user_ord)){ // CVT검증계획 승인요청
					mailcheck = "N";
				}
									
				
				if("N".equals(mailcheck)) {  // 결과 승인 완료  내가 마지막 사용자가 아닐때 N
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					gubun.add("AU");//상용 승인담당자						
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차			

					if("124".equals(pkg21Model.getSave_status())){ // CVT검증계획 승인요청
						inputModel.setMailTitle("CVT 결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"7"));
						content = newMailService.genrerateString(null ,pkg21Model , "7" ,user_ord); // CVT검증결과 승인요청 : 7
					}else {
						inputModel.setMailTitle(" CVT 계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"6"));
						content = newMailService.genrerateString(null ,pkg21Model , "6" ,user_ord); // CVT검증계획 승인요청 : 6
					}
					
				}else { // n차  승인 담당자
					if(("124".equals(pkg21Model.getSave_status()))) {
						inputModel.setMailTitle("CVT 결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
						mailType ="SYSUSER";//pkg_user에서 받는사람 id 검색
						gubun.add("DV");//개발 검증담당자
						gubun.add("VU");//상용 검증담당자
						gubun.add("LV");//현장적용(현장담당자)
						if("Y".equals(pkg21Model.getVol_yn()) ){
							gubun.add("VO");//용량검증
						}
						if("Y".equals(pkg21Model.getCha_yn()) ){
							gubun.add("CH");//용량검증
						}	
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"8"));
						content = newMailService.genrerateString(null ,pkg21Model , "8" ,""); // CVT검증결과 승인완료 : 8
						
					}else {
						mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
						inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차	
					}
				}

				inputModel.setMailContent(content); // 내용	
				
			}//mail 정보 set end
			
			
			
			if("143".equals(pkg21Model.getSave_status()) ) {//확대적용 결과
				mailType ="SYSUSER";//pkg_user에서 받는사람 id 검색
				gubun.add("MO");//상황관제 담당자
				gubun.add("VU");//상용 검증담당자
				if("Y".equals(pkg21Model.getVol_yn()) ){
					gubun.add("VO");//용량검증
				}
				if("Y".equals(pkg21Model.getCha_yn()) ){
					gubun.add("CH");//과금검증
				}
				inputModel.setMailTitle("확대적용 결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				
							

				
				inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"13"));
				String content ="";
				content = newMailService.genrerateString(pkg21Service.getPkgEquipment(pkg21Model, "E") ,pkg21Model , "13" ,""); // 확대적용결과완료 : 13

				inputModel.setMailContent(content); // 내용	
				

			}
			

			
	
		}
		
		
		
		
		
		
		
		
//mail send start ------------------------------------------------------------------------------------------------

		if("141".equals(pkg21Model.getSave_status())){	// 확대 승인 요청일 경우 메일 내용
			for (int i = 0; i < pkg21Model.getCheck_seqs_e().length; i++) {
				if("".equals( pkg21Model.getCheck_seqs_e()[i])){ // 장비시퀀스에 N 으로 들어오는것이 있으면 메일을 보내지 않는다.
					mailType ="PKGUSER";//
					inputModel.setOrd("100");//유저 검색 되지 않도록 하기 위해서 set
				}
			}
		}
		
		
		
		if(("131".equals(pkg21Model.getSave_status()) || ( "141".equals(pkg21Model.getSave_status())  && mailType.equals("SYSUSER"))   )  ) {//초도적용 /확대적용 최초

		List<PkgEquipmentModel> eqmentList = null;	
		if("131".equals(pkg21Model.getSave_status()) ){	// 초도 승인 요청일 경우 메일 내용
			inputModel.setMailTitle("초도적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
			eqmentList =   pkg21Service.getPkgEquipment(pkg21Model, "S");
//			eqmentList =   pkg21Service.getPkgEquipment4E(pkg21Model, "S"); // 등록 데이터가 있는것만
			inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"9"));
			String content ="";
			content = newMailService.genrerateString(eqmentList ,pkg21Model , "9" ,""); // 초도적용계획수립 : 9

			inputModel.setMailContent(content); // 내용	
			
		}
			
			
			
		
	if("141".equals(pkg21Model.getSave_status())){	// 확대 승인 요청일 경우 메일 내용
		inputModel.setMailTitle("확대적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
		eqmentList =   pkg21Service.getPkgEquipment(pkg21Model, "E");
		inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"12"));
		String content ="";
		content = newMailService.genrerateString(eqmentList ,pkg21Model , "12" ,""); //  확대적용 계획수립 : 12

		inputModel.setMailContent(content); // 내용	
		
	}
		

				
//					String[] eq_seqs = new String[eqmentList.size()];
//					int i=0;
//					if(eqmentList != null && 	!(eqmentList).isEmpty()) {
//					for(PkgEquipmentModel list : eqmentList) {
//						eq_seqs[i] = list.getEquipment_seq();
//					}
					
//					List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();
//					NewMailModel eqment_seq = new NewMailModel();
//					eqment_seq.setEqment_seqs(eq_seqs);// equment seq set
//					eqment_users = newMailService.eqmentUserList(eqment_seq);
//					inputModel.setAddUsers(eqment_users);
//					}
	if(pkg21Model.getCheck_seqs_e() != null && 	(pkg21Model.getCheck_seqs_e()).length != 0) {				
		List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();  // 장비운영 담당자
		NewMailModel eqment_seq = new NewMailModel();
		//이미 결과가 수립된 equipment_seq 는 'N' 값임 쿼리에 문자열 들어가면 error 배열에 N 빼기
		String[] eqments_seq = null;
		int no_cnt = 0;
		for(String seq : pkg21Model.getCheck_seqs_e()){
			if("N".equals(seq)){					
				no_cnt++;
			}
		}
		eqments_seq = new String[(pkg21Model.getCheck_seqs_e().length - no_cnt)];
		int ok_cnt = 0;
		for(String seq : pkg21Model.getCheck_seqs_e()){
			if(!"N".equals(seq)){					
				eqments_seq[ok_cnt] = seq;
				ok_cnt++;
			}
		}
//		eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
		eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
		eqment_users = newMailService.eqmentUserList(eqment_seq);
		inputModel.setAddUsers(eqment_users);
	}
	
	
	
				gubun.add("LV");//현장적용(현장담당자)
				gubun.add("MO");//상황관제 담당자
				gubun.add("VU");//상용 검증담당자
				if("Y".equals(pkg21Model.getVol_yn()) ){
					gubun.add("VO");//용량검증
				}
				if("Y".equals(pkg21Model.getCha_yn()) ){
					gubun.add("CH");//과금검증
				}
				

			if( ("1".equals(user_ord)&& "131".equals(pkg21Model.getSave_status())) || ("1".equals(user_ord)&& "141".equals(pkg21Model.getSave_status()) )  ) { //141 확대적용은 update 인 경우가 있기때문에
				
				newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti
			 }
			
			
			
			
			gubun.clear(); // 2번째 메일을 위해 초기화

			//pkg 승인 담당자
			if("131".equals(pkg21Model.getSave_status())) { // 초도적용 에서만
				gubun.add("AS");//검증승인담당자	초도
			}else {
				gubun.add("LA");//현장승인담당자	확대
			}
			inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차				
			inputModel.setPkg_seq(pkg21Model.getPkg_seq());
			inputModel.setGubuns(gubun);
			
			List<String> pkg_userlist = new ArrayList<String>();
			pkg_userlist = newMailService.pkgUserIdList( inputModel);
			
			if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
				inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
				newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
			}
			
			
		}else {
			
			if("SYSUSER".equals(mailType)) {
				newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);			
			}else {
				inputModel.setPkg_seq(pkg21Model.getPkg_seq());
				inputModel.setGubuns(gubun);
				
				List<String> pkg_userlist = new ArrayList<String>();
				pkg_userlist = newMailService.pkgUserIdList( inputModel);
				
				
				if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
				inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
				newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
				}
				
			}
		}
//mail send end  -----------------------------------------------------------------------------------------------------------------------------------------------------		

		
		
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), pkg21Model.getSave_status());
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_After_Create.do")
	public String after_create(Pkg21Model pkg21Model, Model model) throws Exception {
		this.pkg_user_create(pkg21Model);

		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), "");
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_After_Update.do")
	public String after_update(Pkg21Model pkg21Model, Model model) throws Exception {
		String _fin = fin_active(pkg21Model, "LA");
		System.out.println("마지막 승인자? YN : "+_fin);
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), "");
	}
	
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Status_Reject.do")
	public String status_reject(Pkg21Model pkg21Model, Model model) throws Exception {

		//이전단계로 돌아가기 위한 status 삭제
		pkg21Service.pkg_status_delete(pkg21Model);
		if("111".equals(pkg21Model.getSelect_status()) || "121".equals(pkg21Model.getSelect_status())){			
			Pkg21Model delModel = new Pkg21Model();
			delModel.setPkg_seq(pkg21Model.getPkg_seq());
			
			if("D".equals(pkg21Model.getDev_yn())){				
				if("111".equals(pkg21Model.getSelect_status())){
					delModel.setSave_status("12");
				}else if("121".equals(pkg21Model.getSelect_status())){
					delModel.setSave_status("11");
				}
				pkg21Service.pkg_status_delete_like(delModel);
			}
			
			if("111".equals(pkg21Model.getSelect_status())){
				delModel.setSave_status("102");
				pkg21Service.pkg_status_delete_like(delModel);
				delModel.setSave_status("11");
				pkg21Service.pkg_status_delete_like(delModel);
				
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
				pkgUserService.delete(pkgUserModel);				
			}

			pkg21Model.setSave_status("101");

			if("121".equals(pkg21Model.getSelect_status())){					
				delModel.setSave_status("11");
				pkg21Service.pkg_status_delete_like(delModel);
				delModel.setSave_status("12");
				pkg21Service.pkg_status_delete_like(delModel);
				
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
				pkgUserService.delete(pkgUserModel);
				if(!"Y".equals(pkg21Model.getDev_yn())){
					delModel.setSave_status("102");
					pkg21Service.pkg_status_delete_like(delModel);
				}else{					
					pkg21Model.setSave_status("102");
				}
			}
			
			
//			111
//				D DVT, SVT_R, CVT
//				Y DVT, SVT_R
//			121
//				D CVT, DVT, SVT_R
//				Y CVT, DVT
//				N CVT
			SystemFileModel systemFileModel = new SystemFileModel();
			systemFileModel.setMaster_file_id(pkg21Model.getMaster_file_id());

			if("D".equals(pkg21Model.getDev_yn())){
				systemFileModel.setType("DVT");
				delete_file(systemFileModel);
				systemFileModel.setType("CVT");
				delete_file(systemFileModel);
				systemFileModel.setType("SVT_R");
				delete_file(systemFileModel);
			}else if("Y".equals(pkg21Model.getDev_yn())){
				if("111".equals(pkg21Model.getSelect_status())){
					systemFileModel.setType("DVT");
					delete_file(systemFileModel);
					systemFileModel.setType("SVT_R");
					delete_file(systemFileModel);
				}
				if("121".equals(pkg21Model.getSelect_status())){
					systemFileModel.setType("CVT");
					delete_file(systemFileModel);
					systemFileModel.setType("DVT");
					delete_file(systemFileModel);
				}
			}else if("N".equals(pkg21Model.getDev_yn())){
				systemFileModel.setType("CVT");
				delete_file(systemFileModel);
			}
		}
		
		if("131".equals(pkg21Model.getSelect_status())){
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("AS");
			pkgUserService.delete(pkgUserModel);
			
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentModel.setWork_gubun("S");
			pkgEquipmentService.delete(pkgEquipmentModel);
			
			pkg21Model.setSave_status("124");
		}
		
		if("141".equals(pkg21Model.getSelect_status())){
			Pkg21Model delModel = new Pkg21Model();
			delModel.setPkg_seq(pkg21Model.getPkg_seq());

			delModel.setSave_status("14");
			pkg21Service.pkg_status_delete_like(delModel);
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("LA");
			pkgUserService.delete(pkgUserModel);
			
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentModel.setWork_gubun("E");
			pkgEquipmentService.delete(pkgEquipmentModel);
			
			pkg21Model.setSave_status("134");
		}

		//이전단계로 돌아가기 위한 master 갱신
		pkg21Service.pkg_status_update(pkg21Model);
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), pkg21Model.getSave_status());
	}
	
	public void delete_file(SystemFileModel systemFileModel) throws Exception {
		if(null != pkg21Service.sysFileList(systemFileModel)){
			List<SystemFileModel> fileList = pkg21Service.sysFileList(systemFileModel);
			for(SystemFileModel sysFile : fileList){
				attachFileService.new_file_del(sysFile);
			}
		}
	}
	
	/**
	 * Cell 참여자 목록 생성
	 * @param pkg21Model
	 * @param request
	 * @throws Exception
	 */
	private void createCellUserList (Pkg21Model pkg21Model, HttpServletRequest request) throws Exception {
		String[] sel_devsysUserVerifyId = request.getParameterValues("sel_devsysUserVerifyId");
		
		deleteCellUserList(pkg21Model); //초기화
		
		if (sel_devsysUserVerifyId.length > 0 ) {
			for (int i=0; sel_devsysUserVerifyId.length > i; i++) {
				if (sel_devsysUserVerifyId[i].equals(pkg21Model.getDev_system_user_id())) {
					pkg21Model.setSelect_user("Y");
				}else{
					pkg21Model.setSelect_user("N");
				}
				pkg21Model.setCell_user_id(sel_devsysUserVerifyId[i]);
				
				pkg21Service.createCellUserList(pkg21Model);
			}
		}
	}
	/**
	 * Cell 참여자 목록 초기화
	 * @param pkg21Model
	 * @throws Exception
	 */
	private void deleteCellUserList(Pkg21Model pkg21Model)  throws Exception {
		pkg21Service.deleteCellUserList(pkg21Model); //초기화
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Status_Update.do")
	public String status_update(Pkg21Model pkg21Model, Model model, HttpServletRequest request) throws Exception {//svt 결과 업데이트(최초등록 이후 업데이트시)
		pkg21StatusService.update(pkg21Model);
		
		/*
		 * 20181115 eryoon 추가 
		 * Cell 조직 참여 인력
		 */
		if ("Y".equals(pkg21Model.getCol17())) {
			createCellUserList (pkg21Model, request);
		}else{
			deleteCellUserList(pkg21Model);
		}
		
//mail send start------------------------------------------------		
		ArrayList<String> gubun =  new ArrayList<String>() ;		
		if("D".equals(pkg21Model.getDev_yn())){
			pkg21Model.setStatus_dev("102");
			gubun.add("VU");//상용 검증담당자
		}
		gubun.add("DV");//개발 검증담당자
		gubun.add("LV");//현장적용(현장담당자)
		if("Y".equals(pkg21Model.getVol_yn()) ){
			gubun.add("VO");//용량검증
		}
		
		
		NewMailModel inputModel = new NewMailModel();
		inputModel.setMailTitle("SVT 계획결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
		inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"2"));
		String content ="";
		content = newMailService.genrerateString(null ,pkg21Model , "2",""); // SVT 계획결과 :2
		inputModel.setMailContent(content); // 내용
		
		newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);
//mail send end------------------------------------------------		
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), pkg21Model.getSave_status());
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_PeType_Ajax_Read.do")
	public String peTypeRead(Pkg21Model pkg21Model, Model model) throws Exception{
		Pkg21Model p21Model = new Pkg21Model();
		p21Model = pkg21Service.peTypeRead(pkg21Model);
		return ResultUtil.handleSuccessResultParam(model, p21Model.getPe_type(), "");
	}
	
	//PKG 기본정보에서 필요한 상태값 전달
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
		
		p21Model.setCha_yn(pkg21Model.getCha_yn());
		p21Model.setVol_yn(pkg21Model.getVol_yn());
		p21Model.setSec_yn(pkg21Model.getSec_yn());
		
		p21Model.setSer_downtime(pkg21Model.getSer_downtime());
		p21Model.setImpact_systems(pkg21Model.getImpact_systems());
		p21Model.setCheck_seqs_e(pkg21Model.getCheck_seqs_e());
		
		p21Model.setTitle(pkg21Model.getTitle());
		return p21Model;
	}
	
	private Pkg21Model value111(Pkg21Model p21Model, Pkg21Model pkg21Model) throws Exception{
		if(("111".equals(pkg21Model.getStatus())) || ("111".equals(pkg21Model.getStatus_dev()))){
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());

			pkgUserModel.setCharge_gubun("DA");
			p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
			p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			
			Pkg21Model p111Model = new Pkg21Model();
			p111Model.setPkg_seq(pkg21Model.getPkg_seq());
			p111Model.setSelect_status("111");
			
			p111Model = pkg21StatusService.read(p111Model);
			p21Model.setReg_date_plan(p111Model.getReg_date());
			p21Model.setReg_plan_user(p111Model.getReg_user_name());
			
		}else if(("112".equals(pkg21Model.getStatus())) || ("112".equals(pkg21Model.getStatus_dev()))){
			//승인자 목록
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(pkg21Model.getSystem_seq());
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.DA);
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			p21Model.setSystemUserModelList(sysModel.getSystemUserList());
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());

			pkgUserModel.setCharge_gubun("DA");
			p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
			p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			
			Pkg21Model p111Model = new Pkg21Model();
			p111Model.setPkg_seq(pkg21Model.getPkg_seq());
			p111Model.setSelect_status("111");
			
			p111Model = pkg21StatusService.read(p111Model);
			p21Model.setReg_date_plan(p111Model.getReg_date());
			p21Model.setReg_plan_user(p111Model.getReg_user_name());
		}else{
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());

			pkgUserModel.setCharge_gubun("DA");
			p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
			p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			
			PkgUserModel pkgUserModel2 = new PkgUserModel();
			pkgUserModel2.setPkg_seq(pkg21Model.getPkg_seq());

			pkgUserModel2.setCharge_gubun("DR");
			p21Model.setPkgUserModelList2(pkgUserService.readList(pkgUserModel2));//등록된 승인자 목록
			p21Model.setUser_active_status2(pkgUserService.readActive(pkgUserModel2).getOrd());//현재 승인자 ord
			
			Pkg21Model p111Model = new Pkg21Model();
			p111Model.setPkg_seq(pkg21Model.getPkg_seq());
			p111Model.setSelect_status("111");
			
			p111Model = pkg21StatusService.read(p111Model);
			p21Model.setReg_date_plan(p111Model.getReg_date());
			p21Model.setReg_plan_user(p111Model.getReg_user_name());
			
			p111Model.setPkg_seq(pkg21Model.getPkg_seq());
			p111Model.setSelect_status("113");
			
			p111Model = pkg21StatusService.read(p111Model);
			p21Model.setReg_date_result(p111Model.getReg_date());
			p21Model.setReg_result_user(p111Model.getReg_user_name());
		}
		return p21Model;
	}
	
	private Pkg21Model value121(Pkg21Model p21Model, Pkg21Model pkg21Model) throws Exception{
		if("121".equals(pkg21Model.getStatus())){
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());

			pkgUserModel.setCharge_gubun("AU");
			p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
			p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			
			Pkg21Model p111Model = new Pkg21Model();
			p111Model.setPkg_seq(pkg21Model.getPkg_seq());
			p111Model.setSelect_status("121");
			
			p111Model = pkg21StatusService.read(p111Model);
			p21Model.setReg_date_plan(p111Model.getReg_date());
			p21Model.setReg_plan_user(p111Model.getReg_user_name());
			
		}else if("122".equals(pkg21Model.getStatus())){
			//승인자 목록
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(pkg21Model.getSystem_seq());
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			p21Model.setSystemUserModelList(sysModel.getSystemUserList());
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());

			pkgUserModel.setCharge_gubun("AU");
			p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
			p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			
			Pkg21Model p111Model = new Pkg21Model();
			p111Model.setPkg_seq(pkg21Model.getPkg_seq());
			p111Model.setSelect_status("121");
			
			p111Model = pkg21StatusService.read(p111Model);
			p21Model.setReg_date_plan(p111Model.getReg_date());
			p21Model.setReg_plan_user(p111Model.getReg_user_name());
		}else{
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());

			pkgUserModel.setCharge_gubun("AU");
			p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
			p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			
			PkgUserModel pkgUserModel2 = new PkgUserModel();
			pkgUserModel2.setPkg_seq(pkg21Model.getPkg_seq());

			pkgUserModel2.setCharge_gubun("AR");
			p21Model.setPkgUserModelList2(pkgUserService.readList(pkgUserModel2));//등록된 승인자 목록
			p21Model.setUser_active_status2(pkgUserService.readActive(pkgUserModel2).getOrd());//현재 승인자 ord
			
			Pkg21Model p111Model = new Pkg21Model();
			p111Model.setPkg_seq(pkg21Model.getPkg_seq());
			p111Model.setSelect_status("121");
			
			p111Model = pkg21StatusService.read(p111Model);
			p21Model.setReg_date_plan(p111Model.getReg_date());
			p21Model.setReg_plan_user(p111Model.getReg_user_name());
			
			p111Model.setPkg_seq(pkg21Model.getPkg_seq());
			p111Model.setSelect_status("123");
			
			p111Model = pkg21StatusService.read(p111Model);
			p21Model.setReg_date_result(p111Model.getReg_date());
			p21Model.setReg_result_user(p111Model.getReg_user_name());
		}
		return p21Model;
	}
	
	private void pkg_user_create(Pkg21Model pkg21Model) throws Exception{
		PkgUserModel pkgUserModel = null;
		
		// 시스템 user read
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(pkg21Model.getSystem_seq());
		
		//DVT
		if("111".equals(pkg21Model.getSelect_status())){			
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.DA);
		//CVT
		}else if("121".equals(pkg21Model.getSelect_status()) || "131".equals(pkg21Model.getSelect_status())){
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
		}else if("161".equals(pkg21Model.getSelect_status())){
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.CA);
		}else if("151".equals(pkg21Model.getSelect_status())){
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.VA);
		}else if("141".equals(pkg21Model.getSelect_status())){
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
		}
		
		sysModel = systemService.readUsersAppliedToSystem(sysModel);
		
		// create: 사업/개발/검증/협력업체
		List<SystemUserModel> sList = sysModel.getSystemUserList();
		String[] checkSeqs = pkg21Model.getCheck_seqs();
		int ord = 1;
		for(int i = 0; i < checkSeqs.length; i++) {
			for(SystemUserModel model : sList) {
				if("111".equals(pkg21Model.getSelect_status())){
					if(SYSTEM_USER_CHARGE_GUBUN.DA.getCode().equals(model.getCharge_gubun().getCode())) {
						if(model.getUser_id().equals(checkSeqs[i])) {
							pkgUserModel = new PkgUserModel();
							pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgUserModel.setUser_id(model.getUser_id());
							if("111".equals(pkg21Model.getSave_status())){								
								pkgUserModel.setCharge_gubun(model.getCharge_gubun().getCode());
							}else{
								pkgUserModel.setCharge_gubun("DR");
							}
							pkgUserModel.setOrd(String.valueOf((ord++)));
							pkgUserModel.setUse_yn("Y");
							pkgUserService.create(pkgUserModel);
							break;
						}
					}
				}else if("121".equals(pkg21Model.getSelect_status())){
					if(SYSTEM_USER_CHARGE_GUBUN.AU.getCode().equals(model.getCharge_gubun().getCode())) {
						if(model.getUser_id().equals(checkSeqs[i])) {
							pkgUserModel = new PkgUserModel();
							pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgUserModel.setUser_id(model.getUser_id());
							if("121".equals(pkg21Model.getSave_status())){								
								pkgUserModel.setCharge_gubun(model.getCharge_gubun().getCode());
							}else{
								pkgUserModel.setCharge_gubun("AR");
							}
							pkgUserModel.setOrd(String.valueOf((ord++)));
							pkgUserModel.setUse_yn("Y");
							pkgUserService.create(pkgUserModel);
							break;
						}
					}
				}else if("161".equals(pkg21Model.getSelect_status())){
					if(SYSTEM_USER_CHARGE_GUBUN.CA.getCode().equals(model.getCharge_gubun().getCode())) {
						if(model.getUser_id().equals(checkSeqs[i])) {
							pkgUserModel = new PkgUserModel();
							pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgUserModel.setUser_id(model.getUser_id());
							pkgUserModel.setCharge_gubun(model.getCharge_gubun().getCode());
							pkgUserModel.setOrd(String.valueOf((ord++)));
							pkgUserModel.setUse_yn("Y");
							pkgUserService.create(pkgUserModel);
							break;
						}
					}
				}else if("151".equals(pkg21Model.getSelect_status())){
					if(SYSTEM_USER_CHARGE_GUBUN.VA.getCode().equals(model.getCharge_gubun().getCode())) {
						if(model.getUser_id().equals(checkSeqs[i])) {
							pkgUserModel = new PkgUserModel();
							pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgUserModel.setUser_id(model.getUser_id());
							pkgUserModel.setCharge_gubun(model.getCharge_gubun().getCode());
							pkgUserModel.setOrd(String.valueOf((ord++)));
							pkgUserModel.setUse_yn("Y");
							pkgUserService.create(pkgUserModel);
							break;
						}
					}
				}else if("131".equals(pkg21Model.getSelect_status())){
					if(SYSTEM_USER_CHARGE_GUBUN.AU.getCode().equals(model.getCharge_gubun().getCode())) {
						if(model.getUser_id().equals(checkSeqs[i])) {
							pkgUserModel = new PkgUserModel();
							pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgUserModel.setUser_id(model.getUser_id());
							pkgUserModel.setCharge_gubun("AS");
							pkgUserModel.setOrd(String.valueOf((ord++)));
							pkgUserModel.setUse_yn("Y");
							pkgUserService.create(pkgUserModel);
							break;
						}
					}
				}else if("141".equals(pkg21Model.getSelect_status())){
					if(SYSTEM_USER_CHARGE_GUBUN.LA.getCode().equals(model.getCharge_gubun().getCode())) {
						if(model.getUser_id().equals(checkSeqs[i])) {
							pkgUserModel = new PkgUserModel();
							pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgUserModel.setUser_id(model.getUser_id());
							pkgUserModel.setCharge_gubun(model.getCharge_gubun().getCode());
							pkgUserModel.setOrd(String.valueOf((ord++)));
							pkgUserModel.setUse_yn("Y");
							pkgUserService.create(pkgUserModel);
							break;
						}
					}
				}
			}
		}
	}
	
	public String fin_active(Pkg21Model pkg21Model, String charge_gubun) throws Exception{
		String _final = "";
		PkgUserModel pkgUserModel = new PkgUserModel();
		pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
		
		if("121".equals(pkg21Model.getSelect_status()) || "131".equals(pkg21Model.getSelect_status()) ||
		   "141".equals(pkg21Model.getSelect_status())){
			pkgUserModel.setAu_comment(pkg21Model.getAu_comment());
		}
		
		//승인
		pkgUserModel.setOrd(pkg21Model.getOrd());
		pkgUserModel.setCharge_gubun(charge_gubun);
		pkgUserService.update(pkgUserModel);
		
		//모든 승인자가 승인한 경우에만 상태 업데이트
		pkgUserModel = pkgUserService.readActive(pkgUserModel);
		_final = pkgUserModel.getFin();
		return _final;
	}
	
	public String fin_active2(Pkg21Model pkg21Model, String charge_gubun) throws Exception{
		String _final = "";
		PkgUserModel pkgUserModel = new PkgUserModel();
		pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
		
		if("121".equals(pkg21Model.getSelect_status()) || "131".equals(pkg21Model.getSelect_status()) ||
		   "141".equals(pkg21Model.getSelect_status())){
			pkgUserModel.setAu_comment(pkg21Model.getAu_comment());
		}
		
		//승인
		pkgUserModel.setOrd(pkg21Model.getOrd());
		pkgUserModel.setCharge_gubun(charge_gubun);
		
		//모든 승인자가 승인한 경우에만 상태 업데이트
		pkgUserModel = pkgUserService.readActive(pkgUserModel);
		_final = pkgUserModel.getFin();
		return _final;
	}
	
	public void equipment_create(Pkg21Model pkg21Model, String charge_gubun) throws Exception {
		//create
		PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
//		pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
//		pkgEquipmentModel.setWork_gubun(charge_gubun);
//		pkgEquipmentService.delete(pkgEquipmentModel);
		
		String[] checkSeqs = pkg21Model.getCheck_seqs_e();
		String[] start_dates = pkg21Model.getStart_dates();
		String[] end_dates = pkg21Model.getEnd_dates();
		String[] start_times1 = pkg21Model.getStart_times1();
		String[] start_times2 = pkg21Model.getStart_times2();
		String[] end_times1 = pkg21Model.getEnd_times1();
		String[] end_times2 = pkg21Model.getEnd_times2();
		String[] ampms = pkg21Model.getAmpms();
		
		String[] cstart_dates = WingsStringUtil.getNotNullStringArray(start_dates);
		String[] cend_dates = WingsStringUtil.getNotNullStringArray(end_dates);
		String[] cstart_times1 = WingsStringUtil.getNotNullStringArray(start_times1);
		String[] cstart_times2 = WingsStringUtil.getNotNullStringArray(start_times2);
		String[] cend_times1 = WingsStringUtil.getNotNullStringArray(end_times1);
		String[] cend_times2 = WingsStringUtil.getNotNullStringArray(end_times2);
		String[] campms = WingsStringUtil.getNotNullStringArray(ampms);
		
		for(int i = 0; i < checkSeqs.length; i++) {
			pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentModel.setWork_gubun(charge_gubun);
			pkgEquipmentModel.setUse_yn("Y");
			pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
			pkgEquipmentModel.setStart_date(cstart_dates[i]);
			pkgEquipmentModel.setEnd_date(cend_dates[i]);
			pkgEquipmentModel.setStart_time1(cstart_times1[i]);
			pkgEquipmentModel.setStart_time2(cstart_times2[i]);
			pkgEquipmentModel.setEnd_time1(cend_times1[i]);
			pkgEquipmentModel.setEnd_time2(cend_times2[i]);
			pkgEquipmentModel.setAmpm(campms[i]);
			pkgEquipmentService.create21(pkgEquipmentModel);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void equipment_update(Pkg21Model pkg21Model) throws Exception {
		String[] checkSeqs = pkg21Model.getCheck_seqs_e();
		String[] start_dates = pkg21Model.getStart_dates();
		String[] end_dates = pkg21Model.getEnd_dates();
		String[] start_times1 = pkg21Model.getStart_times1();
		String[] start_times2 = pkg21Model.getStart_times2();
		String[] end_times1 = pkg21Model.getEnd_times1();
		String[] end_times2 = pkg21Model.getEnd_times2();
		String[] ampms = pkg21Model.getAmpms();
		
		String[] cstart_dates = WingsStringUtil.getNotNullStringArray(start_dates);
		String[] cend_dates = WingsStringUtil.getNotNullStringArray(end_dates);
		String[] cstart_times1 = WingsStringUtil.getNotNullStringArray(start_times1);
		String[] cstart_times2 = WingsStringUtil.getNotNullStringArray(start_times2);
		String[] cend_times1 = WingsStringUtil.getNotNullStringArray(end_times1);
		String[] cend_times2 = WingsStringUtil.getNotNullStringArray(end_times2);
		String[] campms = WingsStringUtil.getNotNullStringArray(ampms);
		
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(pkg21Model.getSystem_seq());
		List<SysModel> fullEqList = (List<SysModel>) equipmentService.readList(sysModel);
		
		PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
		PkgEquipmentModel pkgEqModel = new PkgEquipmentModel();
		pkgEqModel.setPkg_seq(pkg21Model.getPkg_seq());
		pkgEqModel.setWork_gubun("E");
		List<PkgEquipmentModel> beforeEqList = pkgEquipmentService.read21List(pkgEqModel);
		
		for(SysModel fullEqMdl : fullEqList){
			int create_cnt=0;
			for(PkgEquipmentModel beforeEqMdl : beforeEqList){
				if(beforeEqMdl.getEquipment_seq().equals(fullEqMdl.getEquipment_seq())){
					create_cnt++;
					
					int del_gubun_cnt=0;
					int del_n=0;
					for(int i = 0; i < checkSeqs.length; i++) {
						if(!"N".equals(checkSeqs[i])){
							if(beforeEqMdl.getEquipment_seq().equals(checkSeqs[i])){
								del_gubun_cnt++;
								if(!beforeEqMdl.getStart_date().equals(cstart_dates[i]) ||
								   !beforeEqMdl.getEnd_date().equals(cend_dates[i]) ||
								   !beforeEqMdl.getStart_time1().equals(cstart_times1[i]) ||
								   !beforeEqMdl.getStart_time2().equals(cstart_times2[i]) ||
								   !beforeEqMdl.getEnd_time1().equals(cend_times1[i]) || 
								   !beforeEqMdl.getEnd_time2().equals(cend_times2[i]) ||
								   !beforeEqMdl.getAmpm().equals(campms[i])){
									
									pkgEquipmentModel = new PkgEquipmentModel();
									pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
									pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
									pkgEquipmentService.delete(pkgEquipmentModel);
									//해당하는 tango data 삭제 신청
									pkg21Service.tango_E_Delete(pkg21Model, checkSeqs[i]);
									
									pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
									pkgEquipmentModel.setWork_gubun("E");
									pkgEquipmentModel.setUse_yn("Y");
									pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
									pkgEquipmentModel.setStart_date(cstart_dates[i]);
									pkgEquipmentModel.setEnd_date(cend_dates[i]);
									pkgEquipmentModel.setStart_time1(cstart_times1[i]);
									pkgEquipmentModel.setStart_time2(cstart_times2[i]);
									pkgEquipmentModel.setEnd_time1(cend_times1[i]);
									pkgEquipmentModel.setEnd_time2(cend_times2[i]);
									pkgEquipmentModel.setAmpm(campms[i]);
									
									pkgEquipmentService.create21(pkgEquipmentModel);
								}
							}
						}else{
							del_n++;
						}
					}
					if(del_n == 0){
						if(del_gubun_cnt == 0){ //delete
							pkgEquipmentModel = new PkgEquipmentModel();
							pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgEquipmentModel.setEquipment_seq(beforeEqMdl.getEquipment_seq());
							pkgEquipmentService.delete(pkgEquipmentModel);
							//해당하는 tango data 삭제 신청
							pkg21Service.tango_E_Delete(pkg21Model, beforeEqMdl.getEquipment_seq());
						}
					}
				}
			}
			
			if(create_cnt == 0){
				for(int i = 0; i < checkSeqs.length; i++) {
//					System.out.println(fullEqMdl.getEquipment_seq());
//					System.out.println(checkSeqs[i]);
//					System.out.println(cstart_dates[i]);
					if(fullEqMdl.getEquipment_seq().equals(checkSeqs[i])){
						pkgEquipmentModel = new PkgEquipmentModel();
						
						pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
						pkgEquipmentModel.setWork_gubun("E");
						pkgEquipmentModel.setUse_yn("Y");
						pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
						pkgEquipmentModel.setStart_date(cstart_dates[i]);
						pkgEquipmentModel.setEnd_date(cend_dates[i]);
						pkgEquipmentModel.setStart_time1(cstart_times1[i]);
						pkgEquipmentModel.setStart_time2(cstart_times2[i]);
						pkgEquipmentModel.setEnd_time1(cend_times1[i]);
						pkgEquipmentModel.setEnd_time2(cend_times2[i]);
						pkgEquipmentModel.setAmpm(campms[i]);
						
						pkgEquipmentService.create21(pkgEquipmentModel);
					}
				}
			}
		}
		
		
		
	}
	
	public void equipment_work_update(Pkg21Model pkg21Model) throws Exception {
		String[] checkSeqs = pkg21Model.getCheck_seqs_e();
		String[] work_result = pkg21Model.getWork_result();
		
		for(int i = 0; i < checkSeqs.length; i++) {
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
			pkgEquipmentModel.setWork_result(work_result[i]);
			pkgEquipmentService.update(pkgEquipmentModel);
			
			if("S".equals(pkg21Model.getCha_yn())){
				pkg21Model.setCha_yn("Y");
				pkg21Service.pkg_cha_yn_update(pkg21Model);
			}
		}
		
	}
	
	public void equipment_s_work_update(Pkg21Model pkg21Model) throws Exception {
		PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
		pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
		pkgEquipmentModel.setWork_result(pkg21Model.getCol1());
		pkgEquipmentService.s_work_update(pkgEquipmentModel);
		
		if("S".equals(pkg21Model.getCha_yn())){
			pkg21Model.setCha_yn("Y");
			pkg21Service.pkg_cha_yn_update(pkg21Model);
		}
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Eq_E_Update.do")
	public String eq_e_update(Pkg21Model pkg21Model, Model model) throws Exception {
		equipment_update(pkg21Model);
		pkg21Service.tangoWork(pkg21Model, "E");
		
		//mail send start------------------------------------------------		
		NewMailModel inputModel = new NewMailModel();
		ArrayList<String> gubun =  new ArrayList<String>() ;		
		
				
		List<PkgEquipmentModel> eqmentList = null;	
		eqmentList =   pkg21Service.getPkgEquipment(pkg21Model, "E");
		inputModel.setMailTitle("확대적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
		inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"12"));
		String content ="";
		content = newMailService.genrerateString(eqmentList ,pkg21Model , "12" ,""); //  확대적용 계획수립 : 12
		inputModel.setMailContent(content); // 내용	
		
		if(pkg21Model.getCheck_seqs_e() != null && 	(pkg21Model.getCheck_seqs_e()).length != 0) {				
			List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();  // 장비운영 담당자
			NewMailModel eqment_seq = new NewMailModel();
			//이미 결과가 수립된 equipment_seq 는 'N' 값임 쿼리에 문자열 들어가면 error 배열에 N 빼기
			String[] eqments_seq = null;
			int no_cnt = 0;
			for(String seq : pkg21Model.getCheck_seqs_e()){
				if("N".equals(seq)){					
					no_cnt++;
				}
			}
			eqments_seq = new String[(pkg21Model.getCheck_seqs_e().length - no_cnt)];
			int ok_cnt = 0;
			for(String seq : pkg21Model.getCheck_seqs_e()){
				if(!"N".equals(seq)){					
					eqments_seq[ok_cnt] = seq;
					ok_cnt++;
				}
			}
			eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
//			eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
			eqment_users = newMailService.eqmentUserList(eqment_seq);
			inputModel.setAddUsers(eqment_users);
		}
		
		gubun.add("LV");//현장적용(현장담당자)
		gubun.add("MO");//상황관제 담당자
		gubun.add("VU");//상용 검증담당자
		if("Y".equals(pkg21Model.getVol_yn()) ){
		gubun.add("VO");//용량검증
		}
		if("Y".equals(pkg21Model.getCha_yn()) ){
		gubun.add("CH");//과금검증
		}

		newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti


//		gubun.clear(); // 2번째 메일을 위해 초기화
//		gubun.add("LA");//현장승인담당자	
//		inputModel.setOrd("1");//현재 승인 담당자 ord 123....차				
//		inputModel.setPkg_seq(pkg21Model.getPkg_seq());
//		inputModel.setGubuns(gubun);
//
//		List<String> pkg_userlist = new ArrayList<String>();
//		pkg_userlist = newMailService.pkgUserIdList( inputModel);
//
//		if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
//			inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
//			newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
//		}
		//mail send end------------------------------------------------		
		
		
		
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), "");
	}
	
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_After_E_Update.do")
	public String after_e_update(Pkg21Model pkg21Model, Model model) throws Exception {
		equipment_update(pkg21Model);
		pkg21Service.tangoWork(pkg21Model, "E");
		
		PkgUserModel pkgUserModel = new PkgUserModel();
		pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
		pkgUserModel.setCharge_gubun("LA");
		pkgUserService.delete(pkgUserModel);
		
		//mail send start------------------------------------------------		
		NewMailModel inputModel = new NewMailModel();
		ArrayList<String> gubun =  new ArrayList<String>() ;		
		
				
		List<PkgEquipmentModel> eqmentList = null;	
		eqmentList =   pkg21Service.getPkgEquipment(pkg21Model, "E");
		inputModel.setMailTitle("확대적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
		inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"12"));
		String content ="";
		content = newMailService.genrerateString(eqmentList ,pkg21Model , "12" ,""); //  확대적용 계획수립 : 12
		inputModel.setMailContent(content); // 내용	

		if(pkg21Model.getCheck_seqs_e() != null && 	(pkg21Model.getCheck_seqs_e()).length != 0) {				
			List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();  // 장비운영 담당자
			NewMailModel eqment_seq = new NewMailModel();
			
			//이미 결과가 수립된 equipment_seq 는 'N' 값임 쿼리에 문자열 들어가면 error 배열에 N 빼기
			String[] eqments_seq = null;
			int no_cnt = 0;
			for(String seq : pkg21Model.getCheck_seqs_e()){
				if("N".equals(seq)){					
					no_cnt++;
				}
			}
			eqments_seq = new String[(pkg21Model.getCheck_seqs_e().length - no_cnt)];
			int ok_cnt = 0;
			for(String seq : pkg21Model.getCheck_seqs_e()){
				if(!"N".equals(seq)){					
					eqments_seq[ok_cnt] = seq;
					ok_cnt++;
				}
			}
			
			eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
			eqment_users = newMailService.eqmentUserList(eqment_seq);
			inputModel.setAddUsers(eqment_users);
		}
		
		gubun.add("LV");//현장적용(현장담당자)
		gubun.add("MO");//상황관제 담당자
		gubun.add("VU");//상용 검증담당자
		if("Y".equals(pkg21Model.getVol_yn()) ){
		gubun.add("VO");//용량검증
		}
		if("Y".equals(pkg21Model.getCha_yn()) ){
		gubun.add("CH");//과금검증
		}

		newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti


//		gubun.clear(); // 2번째 메일을 위해 초기화
//		gubun.add("LA");//현장승인담당자	
//		inputModel.setOrd("1");//현재 승인 담당자 ord 123....차				
//		inputModel.setPkg_seq(pkg21Model.getPkg_seq());
//		inputModel.setGubuns(gubun);
//
//		List<String> pkg_userlist = new ArrayList<String>();
//		pkg_userlist = newMailService.pkgUserIdList( inputModel);
//
//		if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
//			inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
//			newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
//		}
		//mail send end------------------------------------------------		
		
		
		
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), "");
	}
	
	@RequestMapping(value = "/pkgmg/pkg21/Pkg21_Cha_Eq_Update.do")
	public String cha_eq_update(Pkg21Model pkg21Model, Model model) throws Exception { // 과금검증 결과저장
		equipment_charge_update(pkg21Model);

		//mail send start------------------------------------------------		
		NewMailModel inputModel = new NewMailModel();
		ArrayList<String> gubun =  new ArrayList<String>() ;		
		
		inputModel.setMailTitle("과금검증 결과등록 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
		String content ="";
		
		PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
		pkgEModel.setPkg_seq(pkg21Model.getPkg_seq());
		pkgEModel.setWork_result("양호");
		if(null != pkgEquipmentService.read21List(pkgEModel)){					
			pkg21Model.setPkgEquipmentModelList(pkgEquipmentService.read21List(pkgEModel));
		}
		inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"17"));
		content = newMailService.genrerateString(null ,pkg21Model , "17" ,""); //  확대적용 계획수립 : 12
		inputModel.setMailContent(content); // 내용	


		gubun.add("DV");//개발 검증담당자
		gubun.add("VU");//상용 검증담당자
		gubun.add("LV");//현장적용(현장담당자)
		
		newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti



		//mail send end------------------------------------------------		
		
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), "");
	}
	
	public void equipment_charge_update(Pkg21Model pkg21Model) throws Exception {
		String[] checkSeqs = pkg21Model.getCheck_seqs_e();
		String[] charge_result = pkg21Model.getCharge_result();
		
		for(int i = 0; i < checkSeqs.length; i++) {
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
			pkgEquipmentModel.setCharge_result(charge_result[i]);
			pkgEquipmentService.update(pkgEquipmentModel);
			
			if("Y".equals(pkg21Model.getCha_yn())){
				pkg21Model.setCha_yn("S");
				pkg21Service.pkg_cha_yn_update(pkg21Model);
			}
		}
		
	}
}
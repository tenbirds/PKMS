package com.pkms.pkgmg.wired.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.newmail.model.NewMailModel;
import com.pkms.common.newmail.service.NewMailServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.pkms.pkgmg.pkg.service.PkgEquipmentServiceIf;
import com.pkms.pkgmg.pkg.service.PkgUserServiceIf;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.pkg21.service.Pkg21ServiceIf;
import com.pkms.pkgmg.pkg21.service.Pkg21StatusServiceIf;
import com.pkms.pkgmg.wired.model.WiredModel;
import com.pkms.pkgmg.wired.service.WiredServiceIf;
import com.pkms.pkgmg.wired.service.WiredStatusServiceIf;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.service.EquipmentServiceIf;
import com.pkms.sys.group1.service.Group1ServiceIf;
import com.pkms.sys.group2.service.Group2ServiceIf;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.sys.system.service.SystemServiceIf;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;
import com.pkms.usermg.user.service.SktUserServiceIf;
import com.wings.model.CodeModel;
import com.wings.util.DateUtil;
import com.wings.util.WingsStringUtil;


/**
 * PKG Main Controller<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class WiredController {
	@Resource(name = "WiredService")
	private WiredServiceIf wiredService;
	
	@Resource(name = "WiredStatusService")
	private WiredStatusServiceIf wiredStatusService;
	
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
	
//	@Resource(name = "SktUserService")
//	private SktUserServiceIf sktUserService;
//	
//	@Resource(name = "BpUserService")
//	private BpUserServiceIf bpUserService;
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_ReadList.do")
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

		List<Pkg21Model> pkg21List = (List<Pkg21Model>) wiredService.readList(pkg21Model);
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
		
		return "/pkgmg/wired/Wired_Pkg21_ReadList";
	}
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_Read.do")
	public String read(Pkg21Model pkg21Model, Model model) throws Exception{
		//pkg_seq가 있는 경우 data read

		if("read".equals(pkg21Model.getRead_gubun())){
			pkg21Model = wiredService.read(pkg21Model);
						
			pkg21Model.setSelect_status("301");
			Pkg21Model p21Model = new Pkg21Model();
			p21Model = wiredStatusService.read(pkg21Model);
			
			if(p21Model != null) {

				pkg21Model.setCol1(p21Model.getCol1());			
				pkg21Model.setCol2(p21Model.getCol2());		
				pkg21Model.setCol3(p21Model.getCol3());	
				pkg21Model.setCol4(p21Model.getCol4());			
				pkg21Model.setCol5(p21Model.getCol5());		
				pkg21Model.setCol6(p21Model.getCol6());	
							
				
			}else {
				pkg21Model.setSelect_status("300");
				p21Model = wiredStatusService.read(pkg21Model);
				if(p21Model != null) {
					pkg21Model.setCol1(p21Model.getCol1());			
					pkg21Model.setCol2(p21Model.getCol2());		
					pkg21Model.setCol3(p21Model.getCol3());	
					pkg21Model.setCol4(p21Model.getCol4());			
					pkg21Model.setCol5(p21Model.getCol5());		
					pkg21Model.setCol6(p21Model.getCol6());	
								
					
				}else {
					pkg21Model.setSelect_status("300");
				}
			}
			
		}
		
		model.addAttribute("Pkg21Model", pkg21Model);
		return "/pkgmg/wired/Wired_Pkg21_Read";
	}
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_Create.do")
	public String create(Pkg21Model pkg21Model, Model model) throws Exception {
		String tmpStatus = pkg21Model.getStatus();
//		if(tmpStatus == "300") {
//			pkg21Model.setStatus("301");
//		}		
		wiredService.create(pkg21Model);
		
		if(tmpStatus.equals("300")) {
			pkg21Model.setSave_status("300");
		}else {
			pkg21Model.setSave_status("301");
		}
		wiredStatusService.create(pkg21Model);
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getPkg_seq(), "");
	}
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_Update.do")
	public String update(Pkg21Model pkg21Model, Model model) throws Exception {
//		pkg21Model.getStatus();// 301  status not update 
		String tmpStatus = pkg21Model.getStatus();
		
		wiredService.update(pkg21Model);
		
		Pkg21Model p21Model = new Pkg21Model();
		
		if(tmpStatus.equals( "301")) {
//			pkg21Model.setSelect_status(tmpStatus);
			pkg21Model.setSelect_status("301");
			pkg21Model.setSave_status("301");
			wiredService.pkg_status_update(pkg21Model);	//master status update
		}else {
			pkg21Model.setSelect_status("300");
		}
		
		
		p21Model = wiredStatusService.read(pkg21Model);
		if(p21Model != null) {
			wiredStatusService.update(pkg21Model);		
			
		}else {
			if(tmpStatus.equals( "301")) {
				pkg21Model.setSave_status("301");				
			}else {
				pkg21Model.setSave_status("300");
			}
			wiredStatusService.create(pkg21Model);
		}
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getPkg_seq(), "");
	}
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_Status_Read.do")
	public String statusRead(Pkg21Model pkg21Model, Model model) throws Exception{
		//pkg_seq가 있는 경우 data read
		
		String retUrl = "";
		retUrl = "/pkgmg/wired/Wired_Pkg21_"+pkg21Model.getSelect_status()+"_Read";
		
		System.out.println("===================================");
		System.out.println(retUrl);
		System.out.println("===================================");
		String title = pkg21Model.getTitle();
		
		Pkg21Model p21Model = new Pkg21Model();
		Pkg21Model p21Model2 = new Pkg21Model();
		
		
		Pkg21Model p21Model3 = new Pkg21Model();
		
		p21Model = valueSetting(p21Model, pkg21Model);
		
		if("301".equals(pkg21Model.getSelect_status())){//계획 검증
				
				Pkg21Model pkg21Model2 = new Pkg21Model();
				pkg21Model2 = wiredService.read(pkg21Model);
				model.addAttribute("Pkg21Model2", pkg21Model2);
				
				
				p21Model = value111(p21Model, pkg21Model);
				
				p21Model.setSystem_name(pkg21Model2.getSystem_name());
				p21Model.setImportant(pkg21Model2.getImportant());
				p21Model.setWork_level(pkg21Model2.getWork_level());
				p21Model.setVer(pkg21Model2.getVer());
				p21Model.setSelect_status(pkg21Model.getSelect_status());
				p21Model.setMaster_file_id(pkg21Model2.getMaster_file_id());
				
				List<SystemUserModel> tmpUserModelList = p21Model.getSystemUserModelList(); // tmp
	
				String tmpSstatus =pkg21Model.getSelect_status();
				pkg21Model.setSelect_status("305");
				p21Model2 = wiredStatusService.read(pkg21Model);	
				
				if(p21Model2 != null) {	//저장 후 승인요청 단계	
		
					p21Model=p21Model2;
					
					p21Model.setSystem_name(pkg21Model2.getSystem_name());
					p21Model.setImportant(pkg21Model2.getImportant());
					p21Model.setWork_level(pkg21Model2.getWork_level());
					p21Model.setVer(pkg21Model2.getVer());
					p21Model.setSelect_status(pkg21Model.getSelect_status());
					p21Model.setSystemUserModelList(tmpUserModelList);
					
					p21Model = valueSetting(p21Model, pkg21Model);
					p21Model.setSelect_status("305");
					p21Model = value111(p21Model, pkg21Model);
					
					int uscount =0;
					for (int i = 0; i < p21Model.getPkgUserModelList().size(); i++) {
						if ("R".equals(p21Model.getPkgUserModelList().get(i).getStatus())){//R 승인요청
							uscount++;
						}					
					}
					
					if(uscount == 0) {
						p21Model.setSelect_status("306");
						pkg21Model.setSelect_status("307");
						p21Model2 = wiredStatusService.read(pkg21Model);	
											
						if(p21Model2 != null) {
							p21Model.setSelect_status("307");
							
							p21Model.setCol4(p21Model2.getCol4());			
							p21Model.setCol5(p21Model2.getCol5());		
							p21Model.setCol6(p21Model2.getCol6());	
							p21Model.setCol11(p21Model2.getCol11());	
	
							p21Model = value111(p21Model, p21Model);
							
							int uscount2 =0;
							for (int i = 0; i < p21Model.getPkgUserModelList2().size(); i++) {
								if ("R".equals(p21Model.getPkgUserModelList2().get(i).getStatus())){//R 승인요청
									uscount2++;
								}					
							}
							
							if(uscount2 == 0) {
								p21Model.setSelect_status("308");
							}
						}else {
							
							
							pkg21Model.setSelect_status("306");
							p21Model2 = wiredStatusService.read(pkg21Model);	
												
							if(p21Model2 != null) {
								p21Model.setCol4(p21Model2.getCol4());			
								p21Model.setCol5(p21Model2.getCol5());		
								
								if(p21Model2.getCol15() != null) {  // 반려 사유 추가
									p21Model.setCol6(p21Model2.getCol6() + "    //////// 반려 사유 : "+p21Model2.getCol15());	
								}else {
									p21Model.setCol6(p21Model2.getCol6());										
								}
								
								p21Model.setCol11(p21Model2.getCol11());	
		
//								p21Model = value111(p21Model, p21Model);
							}
							
							
							
							pkg21Model.setSelect_status(tmpSstatus);
						}
					}
	
				}else{ // 최초
					pkg21Model.setSelect_status(tmpSstatus); // 301검색
					p21Model2 = wiredStatusService.read(pkg21Model);
					
					
					if(p21Model2 != null) {
						tmpSstatus = pkg21Model.getSelect_status();
						Pkg21Model pkg21tmpmodel = new Pkg21Model();
						pkg21Model.setSelect_status("302");//개발검증 1번째 저장
						pkg21tmpmodel = wiredStatusService.read(pkg21Model);
						
						if(pkg21tmpmodel != null) {
							p21Model2 = pkg21tmpmodel;
						}else {
							pkg21Model.setSelect_status(tmpSstatus);
						}
						
						p21Model2 = valueSetting(p21Model2, pkg21Model);
						
					}else {
						
						p21Model2 = valueSetting(p21Model, pkg21Model);
					}
					
					
					
					
					p21Model2.setSystemUserModelList(p21Model.getSystemUserModelList());
					p21Model=p21Model2;
					p21Model.setSystem_name(pkg21Model2.getSystem_name());
					p21Model.setImportant(pkg21Model2.getImportant());
					p21Model.setWork_level(pkg21Model2.getWork_level());
					p21Model.setVer(pkg21Model2.getVer());
					p21Model.setSelect_status(pkg21Model.getSelect_status());
					p21Model.setSystemUserModelList(tmpUserModelList);
				}
			
				
		}else if("331".equals(pkg21Model.getSelect_status())){ //초도계획
				
			pkg21Model.setPkgEquipmentModelList(wiredService.getPkgEquipment(pkg21Model, "S"));
			p21Model = valueSetting(p21Model, pkg21Model);
			p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			
			int check =0;
			if(pkg21Model.getPkgEquipmentModelList() !=null ) {
				for (int i = 0; i < pkg21Model.getPkgEquipmentModelList().size(); i++) {
					if(pkg21Model.getPkgEquipmentModelList().get(i).getStart_date() !=null) {
						check++;
					}
					
				}
			}
			
			 if(check > 0){ //등록한 장비 확인  장비등록 없을때 331
				 
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.VU);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
					
					
					pkg21Model.setSelect_status("332");//승인 요청
					p21Model2 = wiredStatusService.read(pkg21Model);
					if(p21Model2 != null) {
	
						p21Model2 = value111(p21Model2, pkg21Model);
						p21Model = valueSetting(p21Model2, pkg21Model);
						
						int uscount2 =0;
						for (int i = 0; i < p21Model.getPkgUserModelList2().size(); i++) {
							if ("R".equals(p21Model.getPkgUserModelList2().get(i).getStatus())){//R 승인요청
								uscount2++;
							}					
						}
						
						if(uscount2 == 0) {
							p21Model.setSelect_status("335");//승인 완료
						}else {
							p21Model.setSelect_status("333");//승인요청
						}
						
						
					}else {
						
						p21Model.setSelect_status("331");//
					}
					
					p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
					
					
			 }
			
		
			
	 }else if("336".equals(pkg21Model.getSelect_status())){ // 초도 결과
		 
		 
		 String tmpSstatus =pkg21Model.getSelect_status();
			pkg21Model.setSelect_status("337");//초도 결과 등록
			p21Model2 = wiredStatusService.read(pkg21Model);	
		 
			if(p21Model2 != null) {	
				p21Model = valueSetting(p21Model2, pkg21Model);	
			}else {
				pkg21Model.setSelect_status(tmpSstatus);//pkg21Model.setSelect_status("336");//초도결과 임시저장
				p21Model3 = wiredStatusService.read(pkg21Model);
				
				if(p21Model3 != null) {	
					p21Model = valueSetting(p21Model3, pkg21Model);	
				}else {
					pkg21Model.setSelect_status("335");//pkg21Model.setSelect_status("336");//초도결과 임시저장
					p21Model = wiredStatusService.read(pkg21Model);
					p21Model = valueSetting(p21Model, pkg21Model);
				}
				
				
				
			}
		 

	 }else if("341".equals(pkg21Model.getSelect_status())){//확대계획
		 
			pkg21Model.setPkgEquipmentModelList(wiredService.getPkgEquipment(pkg21Model, "E"));//확대
			pkg21Model.setPkgEquipmentModelList4E(wiredService.getPkgEquipment4E(pkg21Model, "S"));//초도

			pkg21Model.setSelect_status("342");
			p21Model2 = wiredStatusService.read(pkg21Model);	
		 

			if(p21Model2 != null) {	//확대적용계획수립단계 있는지 확인

				p21Model = valueSetting(p21Model2, pkg21Model);	
				
//				SysModel sysModel = new SysModel();
//				sysModel.setSystem_seq(pkg21Model.getSystem_seq());
//				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
//				sysModel = systemService.readUsersAppliedToSystem(sysModel);
//				p21Model.setSystemUserModelList(sysModel.getSystemUserList());
				
				p21Model.setSelect_status("342");
				pkg21Model.setSelect_status("342");
				p21Model2 = wiredStatusService.read(pkg21Model);	
				if(p21Model2 != null) {
					p21Model = valueSetting(p21Model2, pkg21Model);	
					
					PkgUserModel pkgUserModel = new PkgUserModel();
					pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
					pkgUserModel.setCharge_gubun("LA");
					
					if(null != pkgUserService.readList(pkgUserModel) && 0 < pkgUserService.readList(pkgUserModel).size()){						
						p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
						p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
											
						int uscount2 =0;
						for (int i = 0; i < p21Model.getPkgUserModelList().size(); i++) {
							if ("R".equals(p21Model.getPkgUserModelList().get(i).getStatus())){//R 승인요청
								uscount2++;
							}					
						}
						
						if(uscount2 == 0) {
							p21Model.setSelect_status("343");//승인 완료
						}else {
							p21Model.setSelect_status("342");//승인요청
						}
					
					
						
					}else{
						//승인자 목록
						p21Model = valueSetting(p21Model, pkg21Model);
						p21Model = value111(p21Model, pkg21Model); // setSystemUserModelList
				
							SysModel sysModel = new SysModel();
							sysModel.setSystem_seq(pkg21Model.getSystem_seq());
							sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
							sysModel = systemService.readUsersAppliedToSystem(sysModel);
							p21Model.setSystemUserModelList(sysModel.getSystemUserList());
							
							p21Model.setSelect_status("341");
						
						
					}
					
					
					
				}
				
				Pkg21Model p111Model = new Pkg21Model();
				p111Model.setPkg_seq(pkg21Model.getPkg_seq());
				p111Model.setSelect_status("341");
				
				if(null != wiredStatusService.read(p111Model)){					
					p111Model = wiredStatusService.read(p111Model);
					p21Model.setReg_date_plan(p111Model.getReg_date());
					p21Model.setReg_plan_user(p111Model.getReg_user_name());
				}
				
				
			}else {
				pkg21Model.setSelect_status("337");
				p21Model = wiredStatusService.read(pkg21Model);
				p21Model = valueSetting(p21Model, pkg21Model);
				p21Model = value111(p21Model, pkg21Model); // setSystemUserModelList
		
				
				pkg21Model.setSelect_status("341");
				p21Model3 = wiredStatusService.read(pkg21Model);
				if(p21Model3 != null){
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
					
					p21Model.setSelect_status("341");
				}
				
			}
			
			
			
			p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			p21Model.setPkgEquipmentModelList4E(pkg21Model.getPkgEquipmentModelList4E());
		 
		 
	 }else if("343".equals(pkg21Model.getSelect_status())){ 
			 
				pkg21Model.setPkgEquipmentModelList(wiredService.getPkgEquipment(pkg21Model, "E"));
				String temp_Selectstatus = pkg21Model.getSelect_status();
	//				
						pkg21Model.setSelect_status("350");  
						p21Model2 = wiredStatusService.read(pkg21Model);
	
						if(p21Model2 != null ){		//저장			
							p21Model = valueSetting(p21Model2, pkg21Model);
							p21Model.setReg_date_plan(p21Model.getReg_date());
							p21Model.setReg_plan_user(p21Model.getReg_user_name());
							
						}else {
							pkg21Model.setSelect_status("345");   // 확대 계획 승인 완료 345 == 343
						}
						
						
					
				p21Model = valueSetting(p21Model, pkg21Model);
				
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
	//			
				pkgUserModel.setCharge_gubun("LA");
				if(null != pkgUserService.readList(pkgUserModel) && 0 < pkgUserService.readList(pkgUserModel).size()){
					Pkg21Model ynModel = new Pkg21Model();
					
					ynModel = wiredStatusService.pkg_user_all_yn(pkg21Model);
					p21Model.setPkg_user_all_yn(ynModel.getPkg_user_all_yn());
				}else{
					p21Model.setPkg_user_all_yn("N");
				}
			 
				
				
				int check_eqment =0;
				for (int i = 0; i < pkg21Model.getPkgEquipmentModelList().size(); i++) {
				
					if(pkg21Model.getPkgEquipmentModelList().get(i).getWork_result() != null) {
						check_eqment++;
					}
					
				}
				
				if(pkg21Model.getPkgEquipmentModelList().size() == check_eqment) {
					p21Model.setCol43("Y");//확대적용 결과 모든 장비 결과 입력시 pkg 완료 버튼 출력
				}else {
					p21Model.setCol43("N");//확대적용 결과 모든 장비 결과 입력시 pkg 완료 버튼 출력
				}
				
				p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			 
		 }
		p21Model.setTitle(title);

		model.addAttribute("Pkg21Model", p21Model);
		return retUrl;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_Status_Create.do")
	public String status_create(Pkg21Model pkg21Model, Model model) throws Exception {
		
		ArrayList<String> gubun =  new ArrayList<String>() ; // mail send 위한 gubun 값 set
		NewMailModel inputModel = new NewMailModel();//메일 title , content set
		String mailType="SYSUSER";
		String user_ord=pkg21Model.getOrd();
		if(user_ord.isEmpty()) {
			user_ord = "1";
		}else {
			int ord = Integer.parseInt(user_ord)+1;
			user_ord = String.valueOf(ord);
		}
		
		
		if("331".equals(pkg21Model.getSave_status())  ){
			if( "320".equals(pkg21Model.getStatus())) {
				equipment_update(pkg21Model);
//				equipment_s_work_update(pkg21Model);
			}else {
				equipment_create(pkg21Model, "S");
			}
			
		}
		if( "341".equals(pkg21Model.getSave_status())           ){
			if( "330".equals(pkg21Model.getStatus())) {
				equipment_update(pkg21Model);
//				equipment_work_update(pkg21Model);
			}else {
				equipment_create(pkg21Model, "E");
			}
			
		}
		
		if( "350".equals(pkg21Model.getSave_status()) ||  "351".equals(pkg21Model.getSave_status())	){
			equipment_work_update(pkg21Model);//확대 업데이트
		}
		
		if( "337".equals(pkg21Model.getSave_status())	){
			equipment_s_work_update(pkg21Model);//초도 결과 장비에도 업데이트
		}	
		
		String tmp_go_page = "";//399 완료시 사용
		
		//개발검증 마지막 승인 확인 마지막 승인시 저장
		String _fin = "Y"; 
		
	if("306".equals(pkg21Model.getSave_status())){
		_fin = fin_active(pkg21Model, "AU");
		if("N".equals(_fin)){
			pkg21Model.setSave_status("305");//의미없음 ...
		}
	}else if("308".equals(pkg21Model.getSave_status())){

		pkg21Model.setAu_comment(pkg21Model.getCol15());
		pkg21Model.setCol15("");
		
		_fin = fin_active(pkg21Model, "AR"); // 검증결과에 대한 승인 담당자 AU와 같음
		if("N".equals(_fin)){
			pkg21Model.setSave_status("307");
		}
	}else if("335".equals(pkg21Model.getSave_status())){//초도 승인 
		_fin = fin_active(pkg21Model, "AS");//검증승인담당
		if("N".equals(_fin)){
			pkg21Model.setSave_status("332");//실제 status 승인 요청 상태 값 (화면상 상태값 333)
		}
	}else if("343".equals(pkg21Model.getSave_status())){
		_fin = fin_active(pkg21Model, "LA");
		if("N".equals(_fin)){
			pkg21Model.setSave_status("342");
		}
	}else if("399".equals(pkg21Model.getSave_status())){
		
		 if("350".equals(pkg21Model.getSelect_status())){
			 tmp_go_page = "343";
		 }else if("337".equals(pkg21Model.getSelect_status())){
			 tmp_go_page = "336";
		 }
		
		
	}else if("351".equals(pkg21Model.getSave_status())){
		_fin = "N";
		pkg21Model.setSave_status("350");
	}
	
	
	
	
	
	//불량 승인 요청 시 페키지 종료	
	 if("307".equals(pkg21Model.getSave_status()) ) { // 
		 if("불량".equals(pkg21Model.getCol11())){
			 
			 wiredStatusService.create(pkg21Model);//불량 상태 저장
			 
			pkg21Model.setSave_status("389");//
			 wiredService.pkg_status_update(pkg21Model);// pkg_master status update
			 _fin = "N"; 
		 }
	 }
	 
	 
	 if("329".equals(pkg21Model.getSave_status()) ) { // 
		 if("불량".equals(pkg21Model.getCol1())){
			 pkg21Model.setSave_status("332");//초도 결과 승인 요청 
			 wiredStatusService.create(pkg21Model);//불량 상태 저장
			 
			pkg21Model.setSave_status("389");//
			 wiredService.pkg_status_update(pkg21Model);// pkg_master status update
			 _fin = "N"; 
		 }

		 
	 }
//---------------------------------------------------------------	
	
	 
	 
	
	
		if("Y".equals(_fin)){//승인완료 여부
			wiredStatusService.create(pkg21Model); // status table 상태값 update
			
			//342 확대적용 승인자 요청
			if("305".equals(pkg21Model.getSave_status()) || "307".equals(pkg21Model.getSave_status()) ||
				"332".equals(pkg21Model.getSave_status()) || "342".equals(pkg21Model.getSave_status())
					){
				
				this.pkg_user_create(pkg21Model);   //요청 승인자 리스트 등록
			}
			
			
			
			//mail 및 연동 그리고 master table status update
				if("302".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("311");//계획 수립
					wiredService.update(pkg21Model);// 작업난이도 ,중요도, 로밍.... 
				}else if("305".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("312");//계획 수립 , 승인요청 311 ,312
					
					
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					
					gubun.add("AU");//상용 승인담당자						
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
					inputModel.setMailTitle("유선 PKG  검증 계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목 승인요청
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"2"));
					String content ="";
					content = newMailService.genrerateStringWired(null ,pkg21Model , "2" ,user_ord); // 검증계획 상용승인요청					
					inputModel.setMailContent(content); // 내용
								
					
					inputModel.setPkg_seq(pkg21Model.getPkg_seq());
					inputModel.setGubuns(gubun);
					List<String> pkg_userlist = new ArrayList<String>();
					pkg_userlist = newMailService.pkgUserIdList( inputModel);
					if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
						inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
						newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
					}
					
				}else if("306".equals(pkg21Model.getSave_status()) ){ //계획 승인완료
					pkg21Model.setSave_status("313");//
					//승인완료로 종료
					
				}else  if("307".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("316");//계획 결과저장 및 승인요청
					
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					gubun.add("AR");//상용 결과 승인담당자						
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
					inputModel.setMailTitle("유선 PKG  검증 결과수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"3"));
					String content ="";
					content = newMailService.genrerateStringWired(null ,pkg21Model , "3" ,user_ord); // 검증계획 상용승인요청					
					inputModel.setMailContent(content); // 내용
					
					
					inputModel.setPkg_seq(pkg21Model.getPkg_seq());
					inputModel.setGubuns(gubun);
					
					List<String> pkg_userlist = new ArrayList<String>();
					pkg_userlist = newMailService.pkgUserIdList( inputModel);
					
					if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
						inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
						newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
					}
				}else  if("308".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("317");//계획 결과 승인완료	
					
					
					
					//승인 완료 되었습니다.
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					gubun.add("VU");//상용 검증담당자						
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
					inputModel.setMailTitle("유선 PKG  검증 완료 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"4"));
					String content ="";
					content = newMailService.genrerateStringWired(null ,pkg21Model , "4" ,""); // 검증계획 상용승인요청					
					inputModel.setMailContent(content); // 내용
	
					newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti
					
					
					
					
				}else if("331".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("321");//초도 계획 수립
					
					//초도계획 수립이 등록되었습니다.   5
					
					
				}else if("332".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("322");//초도계획 승인요청
					
					
					//초도계획 승인요청 등록돼었습니다.   5
					
					
					List<PkgEquipmentModel> eqmentList = null;	
					// 초도 승인 요청일 경우 메일 내용
					
					// 초도 승인 요청일 경우 메일 내용
					inputModel.setMailTitle("초도적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					eqmentList =   wiredService.getPkgEquipment(pkg21Model, "S");
//					eqmentList =   pkg21Service.getPkgEquipment4E(pkg21Model, "S"); // 등록 데이터가 있는것만
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"5"));
					String content ="";
					content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "5" ,""); // 초도적용계획수립 : 9

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
//						eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
						eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
						eqment_users = newMailService.eqmentUserList(eqment_seq);
						inputModel.setAddUsers(eqment_users);
					}
					
					
					gubun.add("VU");//상용 검증담당자
					
					newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti mail			
					
//노티 담당자 end					
		
					
					
					
//승인 담당자	 start				
					gubun.clear(); // 2번째 메일을 위해 초기화
					
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
								
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
					inputModel.setMailTitle("초도적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"6"));
					content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "6" ,user_ord); // 				
					inputModel.setMailContent(content); // 내용
					
					
					//pkg 승인 담당자
					gubun.add("AS");//검증승인담당자	초도
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차				
					inputModel.setPkg_seq(pkg21Model.getPkg_seq());
					inputModel.setGubuns(gubun);
					
					List<String> pkg_userlist = new ArrayList<String>();
					pkg_userlist = newMailService.pkgUserIdList( inputModel);
					
					if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
						inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
						newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
					}
					
					
					
					
					
				}else if("335".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("323");//초도계획 승인완료
					
					//////////////tango 연동
					wiredService.tangoWork(pkg21Model, "S"); //초도 
					
				}else if("336".equals(pkg21Model.getSave_status()) || "337".equals(pkg21Model.getSave_status())){ // 초도결과 저장
					
					if( "337".equals(pkg21Model.getSave_status())){//초도 적용완료 상태 일때만 완료 메일 336은 임시저장
						//상용검증담당, 현장적용담당
						mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
						gubun.add("VU");//상용 검증담당자	
						gubun.add("LV");//현장적용담당
						inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
						inputModel.setMailTitle("유선 PKG  초도결과 완료 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
						inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"7"));
						String content ="";
						content = newMailService.genrerateStringWired(null ,pkg21Model , "7" ,""); // 				
						inputModel.setMailContent(content); // 내용
						
						newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);
						
//						inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차				
//						inputModel.setPkg_seq(pkg21Model.getPkg_seq());
//						inputModel.setGubuns(gubun);
						
//						List<String> pkg_userlist = new ArrayList<String>();
//						pkg_userlist = newMailService.pkgUserIdList( inputModel);
						
//						if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
//							inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
//							newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
//						}
						
						
					}
					
					
					pkg21Model.setSave_status("324");// 초도적용결과
					
					
					
				}else  if("342".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("332");// 확대적용 승인요청
					
					//수립단계가 없기때문에 같이보냄
					//상용검증담당, 현장적용담당--알림    /     현장 승인 담당자 승인요청
					
					/////////////////////
					List<PkgEquipmentModel> eqmentList = null;	
					
					inputModel.setMailTitle("확대적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					eqmentList =   wiredService.getPkgEquipment(pkg21Model, "E");
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"8"));
					String content ="";
					content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "8" ,""); //  확대적용 계획수립 : 

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
//						eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
						eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
						eqment_users = newMailService.eqmentUserList(eqment_seq);
						inputModel.setAddUsers(eqment_users);
					}
					
					
					
					gubun.add("LV");//현장적용(현장담당자)
					gubun.add("VU");//상용 검증담당자
						
					newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti
						
		///////////////////////////				
					gubun.clear(); // 2번째 메일을 위해 초기화
	
					//pkg 승인 담당자
					gubun.add("LA");//현장승인담당자	확대
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차				
					inputModel.setPkg_seq(pkg21Model.getPkg_seq());
					inputModel.setGubuns(gubun);
					
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"9"));
//					String content ="";
					content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "9" ,""); //  확대적용 계획수립 승인요청 : 
					
					
					List<String> pkg_userlist = new ArrayList<String>();
					pkg_userlist = newMailService.pkgUserIdList( inputModel);
					
					if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
						inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
						newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
					}	
					
					
					
					
					
					
					
				}else if("343".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("333");// 확대적용 계획 승인완료
					
		//////////////tango 연동
					wiredService.tangoWork(pkg21Model, "E"); //확대 
					
				}else if("350".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("334");// 확대적용 결과승인완료 -- 실질적인 완료
					
					List<PkgEquipmentModel> eqmentList = null;	
					eqmentList =   wiredService.getPkgEquipment(pkg21Model, "E");
					//상용검증담당, 현장적용담당--알림 
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					gubun.add("LV");//현장적용(현장담당자)
					gubun.add("VU");//상용 검증담당자				
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
					inputModel.setMailTitle("유선 PKG 확대적용 완료 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"10"));
					String content ="";
					content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "10" ,user_ord); // 				
					inputModel.setMailContent(content); // 내용
					
					newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti					
					
				}else if("399".equals(pkg21Model.getSave_status()) ){ // 
					pkg21Model.setSave_status("399");// PKG완료 
				}else{
				
				}

				if(!"341".equals(pkg21Model.getSave_status()) ){ // 확대 장비 저장 할경우 그냥 넘김
					
					wiredService.pkg_status_update(pkg21Model);// pkg_master status update
				 
				}
			
				
			
			
		//다음 승인자에게 sms , 2차 담당자 - 마지막 전 담당자
		}else{   
			
			
			if("305".equals(pkg21Model.getSave_status()) ){ // 계획 검증 승인요청
//				mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
				gubun.add("AU");//상용 승인담당자						
				inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
				inputModel.setMailTitle("유선 PKG 검증 계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"2"));
				String content ="";
				content = newMailService.genrerateStringWired(null ,pkg21Model , "2" ,user_ord); // 검증계획 상용승인요청					
				inputModel.setMailContent(content); // 내용
				
				
				inputModel.setPkg_seq(pkg21Model.getPkg_seq());
				inputModel.setGubuns(gubun);
				
				List<String> pkg_userlist = new ArrayList<String>();
				pkg_userlist = newMailService.pkgUserIdList( inputModel);
				
				if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
					inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
					newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
				}
				
				
			}
			
			
			
			if("307".equals(pkg21Model.getSave_status()) ){ // 계획 결과 승인요청
				
				 if("양호".equals(pkg21Model.getCol11())){
					mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
					gubun.add("AR");//상용 승인담당자	  결과 상용승인 담당					
					inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
					inputModel.setMailTitle("유선 PKG 검증 계획 결과 수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
					inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"3"));
					String content ="";
					content = newMailService.genrerateStringWired(null ,pkg21Model , "3" ,user_ord); // 검증계획 상용승인요청					
					inputModel.setMailContent(content); // 내용
					
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
			
			
			
			
			if("332".equals(pkg21Model.getSave_status()) ){ // 초도적용 계획 결과 승인요청 
				
				List<PkgEquipmentModel> eqmentList = null;	
				// 초도 승인 요청일 경우 메일 내용
				
				inputModel.setMailTitle("초도적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				eqmentList =   wiredService.getPkgEquipment(pkg21Model, "S");
				String content ="";
					
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
//					eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
					eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
					eqment_users = newMailService.eqmentUserList(eqment_seq);
					inputModel.setAddUsers(eqment_users);
				}

							
				inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
				inputModel.setMailTitle("유선 PKG 검증 계획 결과 수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"6"));
				content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "6" ,user_ord); // 				
				inputModel.setMailContent(content); // 내용
				
				
				//pkg 승인 담당자
				gubun.add("AS");//검증승인담당자	초도
				inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차				
				inputModel.setPkg_seq(pkg21Model.getPkg_seq());
				inputModel.setGubuns(gubun);
				
				List<String> pkg_userlist = new ArrayList<String>();
				pkg_userlist = newMailService.pkgUserIdList( inputModel);
				
				if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
					inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
					newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
				}
				
				
			}
			
			
			
			if("342".equals(pkg21Model.getSave_status()) ){ // 확대적용  계획 결과 승인요청 
				
				/////////////////////
				List<PkgEquipmentModel> eqmentList = null;	
				
				inputModel.setMailTitle("확대적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				eqmentList =   wiredService.getPkgEquipment(pkg21Model, "E");
				inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"8"));
				String content ="";
				content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "8" ,""); //  확대적용 계획수립 : 8

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
//					eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
					eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
					eqment_users = newMailService.eqmentUserList(eqment_seq);
					inputModel.setAddUsers(eqment_users);
				}
				
				
				
				//pkg 승인 담당자
				gubun.add("LA");//현장승인담당자	확대
				inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차				
				inputModel.setPkg_seq(pkg21Model.getPkg_seq());
				inputModel.setGubuns(gubun);
				
				List<String> pkg_userlist = new ArrayList<String>();
				pkg_userlist = newMailService.pkgUserIdList( inputModel);
				
				if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
					inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
					newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
				}	
				
				
			}
			
			
			if("350".equals(pkg21Model.getSave_status()) ){ // 
				pkg21Model.setSave_status("334");// 확대적용 결과승인완료 -- 실질적인 완료
				
				
				List<PkgEquipmentModel> eqmentList = null;	
				eqmentList =   wiredService.getPkgEquipment(pkg21Model, "E");
				//상용검증담당, 현장적용담당--알림 
				mailType ="PKGUSER";//pkg_user에서 받는사람 id 검색
				gubun.add("LV");//현장적용(현장담당자)
				gubun.add("VU");//상용 검증담당자				
				inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차						
				inputModel.setMailTitle("유선 PKG  검증 완료 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"9"));
				String content ="";
				content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "9" ,user_ord); // 				
				inputModel.setMailContent(content); // 내용
				
				newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti
				
				
			}

			
		}//end else
		
		
		
		
		
		
		
//after save change status for go to page		
		
		 if("302".equals(pkg21Model.getSelect_status()) ||"305".equals(pkg21Model.getSelect_status()) || 
				 "306".equals(pkg21Model.getSelect_status()) || "307".equals(pkg21Model.getSelect_status()) || 
				 "308".equals(pkg21Model.getSelect_status())   ) {
//			 pkg21Model.setSave_status("301");
			 pkg21Model.setSelect_status("301");
			
		 }else if("332".equals(pkg21Model.getSelect_status())  || "333".equals(pkg21Model.getSelect_status()) || "335".equals(pkg21Model.getSelect_status()) ) {
//			 pkg21Model.setSave_status("301");
			 pkg21Model.setSelect_status("331");
		
			 
		 }else if("336".equals(pkg21Model.getSelect_status())  || "337".equals(pkg21Model.getSelect_status()) ) {
//			 pkg21Model.setSave_status("301");
			 pkg21Model.setSelect_status("336");
			

		 }else if("341".equals(pkg21Model.getSelect_status())  || "342".equals(pkg21Model.getSelect_status())  || "343".equals(pkg21Model.getSelect_status()) ) {//승인
			 pkg21Model.setSelect_status("341");
				
		 }else if("350".equals(pkg21Model.getSelect_status())){
				pkg21Model.setSelect_status("343");
				
		 }else if("399".equals(pkg21Model.getSelect_status())){
				pkg21Model.setSelect_status(tmp_go_page);		
		}
		 
		 
		 
	
		 
		 
		 
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), pkg21Model.getSave_status());
	}
	
	
	
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_After_Create.do")
	public String after_create(Pkg21Model pkg21Model, Model model) throws Exception {
		this.pkg_user_create(pkg21Model);

		
		pkg21Model.setSelect_status("341");
		
		
		
		
//		확대적용 추가 등록 
//		메일??등록되면 첫번째 승인자에게 전달
		String user_ord=pkg21Model.getOrd();
		if(user_ord.isEmpty()) {
			user_ord = "1";
		}else {
			int ord = Integer.parseInt(user_ord)+1;
			user_ord = String.valueOf(ord);
		}
		
		
		NewMailModel inputModel = new NewMailModel();
		ArrayList<String> gubun =  new ArrayList<String>() ;
		
		List<PkgEquipmentModel> eqmentList = null;	
		
		inputModel.setMailTitle("확대적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
		eqmentList =   wiredService.getPkgEquipment(pkg21Model, "E");
		inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"8"));
		String content ="";
		content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "8" ,""); //  확대적용 계획수립 : 

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
//			eqment_seq.setEqment_seqs(pkg21Model.getCheck_seqs_e());// equment seq set
			eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
			eqment_users = newMailService.eqmentUserList(eqment_seq);
			inputModel.setAddUsers(eqment_users);
		}
		
		
		
		gubun.add("LV");//현장적용(현장담당자)
		gubun.add("VU");//상용 검증담당자
			
		newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti
			
///////////////////////////				
		gubun.clear(); // 2번째 메일을 위해 초기화

		//pkg 승인 담당자
		gubun.add("LA");//현장승인담당자	확대
		inputModel.setOrd(user_ord);//현재 승인 담당자 ord 123....차				
		inputModel.setPkg_seq(pkg21Model.getPkg_seq());
		inputModel.setGubuns(gubun);
		
		inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"9"));
//		String content ="";
		content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "9" ,""); //  확대적용 계획수립 승인요청 : 
		
		
		List<String> pkg_userlist = new ArrayList<String>();
		pkg_userlist = newMailService.pkgUserIdList( inputModel);
		
		if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
			inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
			newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리
		}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), "");
	}
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_After_Update.do")
	public String after_update(Pkg21Model pkg21Model, Model model) throws Exception {
		String _fin = fin_active(pkg21Model, "LA");
		System.out.println("마지막 승인자? YN : "+_fin);
		
		pkg21Model.setSelect_status("341");
		
		
		
		
//		메일? N이면 다음 차수 승인자에게 메일 전달
		
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), "");
	}		
	
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_Status_Update.do")
	public String status_update(Pkg21Model pkg21Model, Model model) throws Exception {
		wiredStatusService.update(pkg21Model);
		
		
		
		//mail send start------------------------------------------------		
				ArrayList<String> gubun =  new ArrayList<String>() ;		
				gubun.add("VU");//상용 검증담당자
								
				NewMailModel inputModel = new NewMailModel();
				inputModel.setMailTitle("SVT 계획결과 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
				inputModel.setSmsMsgvalue(newMailService.makeSmsMsg(pkg21Model,"1"));
				String content ="";
				content = newMailService.genrerateString(null ,pkg21Model , "1",""); // 
				inputModel.setMailContent(content); // 내용
				
				newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);
		//mail send end------------------------------------------------		
		
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), "");
	}
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_PeType_Ajax_Read.do")
	public String peTypeRead(Pkg21Model pkg21Model, Model model) throws Exception{
		Pkg21Model p21Model = new Pkg21Model();
		p21Model = wiredService.peTypeRead(pkg21Model);
		return ResultUtil.handleSuccessResultParam(model, p21Model.getPe_type(), "");
	}

	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_After_E_Update.do")
	public String after_e_update(Pkg21Model pkg21Model, Model model) throws Exception {
		equipment_update(pkg21Model);
		wiredService.tangoWork(pkg21Model, "E");
		
		PkgUserModel pkgUserModel = new PkgUserModel();
		pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
		pkgUserModel.setCharge_gubun("LA");
		pkgUserService.delete(pkgUserModel);
		
		//mail send start------------------------------------------------		
//		NewMailModel inputModel = new NewMailModel();
//		ArrayList<String> gubun =  new ArrayList<String>() ;		
//		
//				
//		List<PkgEquipmentModel> eqmentList = null;	
//		eqmentList =   wiredService.getPkgEquipment(pkg21Model, "E");
//		inputModel.setMailTitle("확대적용계획수립 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목
//		inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"8"));
//		String content ="";
//		content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "8" ,""); //  확대적용 계획수립 : 12
//		inputModel.setMailContent(content); // 내용	
//
//		if(pkg21Model.getCheck_seqs_e() != null && 	(pkg21Model.getCheck_seqs_e()).length != 0) {				
//			List<NewMailModel> eqment_users = new ArrayList<NewMailModel>();  // 장비운영 담당자
//			NewMailModel eqment_seq = new NewMailModel();
//			
//			//이미 결과가 수립된 equipment_seq 는 'N' 값임 쿼리에 문자열 들어가면 error 배열에 N 빼기
//			String[] eqments_seq = null;
//			int no_cnt = 0;
//			for(String seq : pkg21Model.getCheck_seqs_e()){
//				if("N".equals(seq)){					
//					no_cnt++;
//				}
//			}
//			eqments_seq = new String[(pkg21Model.getCheck_seqs_e().length - no_cnt)];
//			int ok_cnt = 0;
//			for(String seq : pkg21Model.getCheck_seqs_e()){
//				if(!"N".equals(seq)){					
//					eqments_seq[ok_cnt] = seq;
//					ok_cnt++;
//				}
//			}
//			
//			eqment_seq.setEqment_seqs(eqments_seq);// equment seq set
//			eqment_users = newMailService.eqmentUserList(eqment_seq);
//			inputModel.setAddUsers(eqment_users);
//		}
//		
//		gubun.add("LV");//현장적용(현장담당자)
//		gubun.add("MO");//상황관제 담당자
//		gubun.add("VU");//상용 검증담당자
//		if("Y".equals(pkg21Model.getVol_yn()) ){
//		gubun.add("VO");//용량검증
//		}
//		if("Y".equals(pkg21Model.getCha_yn()) ){
//		gubun.add("CH");//과금검증
//		}

//		newMailService.maileModule(pkg21Model.getPkg_seq(), gubun, "NON", inputModel);	//noti


		
		
		
		
		
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

		if(("301".equals(pkg21Model.getSelect_status()))){
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(pkg21Model.getSystem_seq());
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			p21Model.setSystemUserModelList(sysModel.getSystemUserList());
		}
			if(("305".equals(pkg21Model.getSelect_status()))){
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
				pkgUserModel.setCharge_gubun("AU");
				p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
				p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			}
			
			if(("307".equals(pkg21Model.getSelect_status()))){
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
				pkgUserModel.setCharge_gubun("AU");
				p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
				p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
				
				pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
				pkgUserModel.setCharge_gubun("AR");
				p21Model.setPkgUserModelList2(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
				p21Model.setUser_active_status2(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			}
			
			
			if(("332".equals(pkg21Model.getSelect_status()))){
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
				pkgUserModel.setCharge_gubun("AS");
				p21Model.setPkgUserModelList2(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
				p21Model.setUser_active_status2(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			}
			
			
			if(("337".equals(pkg21Model.getSelect_status())) ){ // 확대계획
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkg21Model.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				p21Model.setSystemUserModelList(sysModel.getSystemUserList());
			}
			
			
		return p21Model;
	}
	
	
	private void pkg_user_create(Pkg21Model pkg21Model) throws Exception{
		PkgUserModel pkgUserModel = null;
//		
//		// 시스템 user read
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(pkg21Model.getSystem_seq());
//		
		
		if("332".equals(pkg21Model.getSave_status())){
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
		}else if("342".equals(pkg21Model.getSave_status())) {
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
		}
		else {
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
			
		}
//		System.out.println(sysModel.getSystem_seq());
		sysModel = systemService.readUsersAppliedToSystem(sysModel);
//		
//		// create: 사업/개발/검증/협력업체
		List<SystemUserModel> sList = sysModel.getSystemUserList();
		String[] checkSeqs = pkg21Model.getCheck_seqs();
		int ord = 1;
		for(int i = 0; i < checkSeqs.length; i++) {
			for(SystemUserModel model : sList) {
				if("342".equals(pkg21Model.getSave_status())){
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
				}else if("332".equals(pkg21Model.getSave_status())){
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
				}else {
					if(SYSTEM_USER_CHARGE_GUBUN.AU.getCode().equals(model.getCharge_gubun().getCode())) {
				
						if(model.getUser_id().equals(checkSeqs[i])) {
							pkgUserModel = new PkgUserModel();
							pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgUserModel.setUser_id(model.getUser_id());
							
							if("307".equals(pkg21Model.getSave_status())) {
								pkgUserModel.setCharge_gubun("AR");
								
							}else {
								pkgUserModel.setCharge_gubun(model.getCharge_gubun().getCode());								
							}
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

		if(pkg21Model.getAu_comment() != null) {
			pkgUserModel.setAu_comment(pkg21Model.getAu_comment());
		}
		if(pkg21Model.getSave_status() == "308") {
			pkgUserModel.setAu_comment(pkg21Model.getCol15());
		}
		
		
//		//승인
		pkgUserModel.setOrd(pkg21Model.getOrd());
		pkgUserModel.setCharge_gubun(charge_gubun);
		pkgUserService.update(pkgUserModel);
//		
//		//모든 승인자가 승인한 경우에만 상태 업데이트
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
		List<SysModel> fullEqList = (List<SysModel>)equipmentService.readList(sysModel);
		
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
									wiredService.tango_E_Delete(pkg21Model, checkSeqs[i]);
									
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
							wiredService.tango_E_Delete(pkg21Model, beforeEqMdl.getEquipment_seq());
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
				wiredService.pkg_cha_yn_update(pkg21Model);
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
			wiredService.pkg_cha_yn_update(pkg21Model);
		}
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_Status_Reject.do")
	public String status_reject(Pkg21Model pkg21Model, Model model) throws Exception {
		
//		300 상태일때 comment 없음 
//		319 상태일때만 comment 가 col15
//		나머지 331 , 341 일때 comment 가 col14
		
		ArrayList<String> gubun =  new ArrayList<String>() ; // mail send 위한 gubun 값 set
		
		if("300".equals(pkg21Model.getSave_status())){	//개발검증 수립 단계에서 반려
			
			pkg21Model.setSave_status("300");// 300 제거 
			wiredService.pkg_status_delete(pkg21Model);
			
			Pkg21Model pkg21Model2 = new Pkg21Model();
			pkg21Model2.setSelect_status("301");//조회
			pkg21Model2.setSave_status("300");//변경
			pkg21Model2.setPkg_seq(pkg21Model.getPkg_seq());
			wiredStatusService.statusUpdate(pkg21Model2);//301-> 300 임시저장 상태로 변경
			
			pkg21Model.setSave_status("309");//master_status 반려상태
			
			NewMailModel inputModel = new NewMailModel();//메일 title , content set
			inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"101"));
			inputModel.setMailTitle("PKG개발 결과 반려 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목				
			String content ="";
			content = newMailService.genrerateStringWired(null ,pkg21Model , "101",""); // SVT 계획결과 :2
			inputModel.setMailContent(content); // 내용
			
			List<String> pkg_userlist = new ArrayList<String>();
			//등록자 조회
			
			if("M".equals(pkg21Model.getReg_user_gubun())){//manager ID  pkg 등록자에게 보낼경우만 필요한 로직
				inputModel.setUser_type("M");
				pkg_userlist.add(pkg21Model.getReg_user());
			}else {//BP ID
				inputModel.setUser_type("B");
				inputModel.setUser_id(pkg21Model.getReg_user());
			}
			//send mail

			
			if(!inputModel.getUser_type().isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
				newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리   -- 현재 manager 만 검색한다. 
			}
			
			
		}else if("301".equals(pkg21Model.getSave_status())){	//개발검증 계획 승인 단계에서 반려
//			status 305 단계 삭제     ->   302단계 삭제  / 305 -> 302 변경
//			메일 발송 - 요청자 이전 승인자
//			파일 삭제
//			systemFileModel.setType("CVT");
//			delete_file(systemFileModel);
			
//			반려 시 Comment 입력 필수. 요청자와 이전승인자에게 “승인 반려, 개발검증계획을 수정하세요.”
			
			pkg21Model.setSave_status("302");// 300 제거 
			wiredService.pkg_status_delete(pkg21Model);
			
			Pkg21Model pkg21Model2 = new Pkg21Model();
			pkg21Model2.setSelect_status("305");//조회
			pkg21Model2.setSave_status("302");//변경
			pkg21Model2.setPkg_seq(pkg21Model.getPkg_seq());
			wiredStatusService.statusUpdate(pkg21Model2);//305-> 302 계획 수립 승인 완료 단계
			
			
//			System.out.println("====301=============au_comment===========statr===============");
//			System.out.println(pkg21Model.getAu_comment());//반려 이유 -- 메일 추가
//			System.out.println("====301=============au_comment===========end===============");
			
			pkg21Model.setSave_status("302");//조회 
			pkg21Model.setCol14(pkg21Model.getAu_comment());
			wiredStatusService.update(pkg21Model);//반려사유 저장
//			wiredStatusDAO.update
			
			
//			pkg21Model.setSave_status("302");// 305 제거 
//			wiredService.pkg_status_delete(pkg21Model);
//			
//			pkg21Model.setSave_status("301");
//			wiredStatusService.update(pkg21Model);//삭제대신 수정가능한 status 상태 임시저장(300)으로 변경			
			pkg21Model.setSave_status("314");//master_status 반려상태
			
			NewMailModel inputModel = new NewMailModel();//메일 title , content set
			inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"102"));
			inputModel.setMailTitle("검증계획 반려 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목				
			String content ="";
			content = newMailService.genrerateStringWired(null ,pkg21Model , "102",""); //
			inputModel.setMailContent(content); // 내용
			
			
//			Pkg21Model pkg21Model2 = new Pkg21Model();
//			pkg21Model2.setPkg_seq(pkg21Model.getPkg_seq());
//			pkg21Model2 = wiredService.read(pkg21Model2);
			
			List<String> pkg_userlist = new ArrayList<String>();
			//등록자 조회
			
			
			if("M".equals(pkg21Model.getReg_user_gubun())){//manager ID pkg 등록자  300으로 반려하기때문에
				inputModel.setUser_type("M");
				pkg_userlist.add(pkg21Model.getReg_user());
			}else {//BP ID
				inputModel.setUser_type("B");
				inputModel.setUser_id(pkg21Model.getReg_user());
			}
			//send mail

//			pkg_userlist = newMailService.pkgUserIdList2( inputModel);
			
			if(!inputModel.getUser_type().isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
//				inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
				newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리   -- 현재 manager 만 검색한다. 
			}
			
		
			
			//등록자에게 전달 후 이전 승인자에게 전달
			inputModel = new NewMailModel();//메일 title , content set
			gubun.clear(); 
			inputModel.setUser_type("M");
			gubun.add("AU");//현장승인담당자	확대
			inputModel.setOrd( pkg21Model.getOrd() );//현재 승인 담당자 ord 123....차				
			inputModel.setPkg_seq(pkg21Model.getPkg_seq());
			inputModel.setGubuns(gubun);
			
//			List<String> pkg_userlist = new ArrayList<String>();
			pkg_userlist = newMailService.pkgUserIdList2( inputModel);  //현재 ord 보다 낮은 담당자 리스트 이전 승인자
			if(!pkg_userlist.isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
				inputModel.setUser_ids(pkg_userlist);
				newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리   -- 현재 manager 만 검색한다. 
			}
			
			
//			메일 보내고 승인자 list삭제
//			this.pkg_user_create(pkg21Model);
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("AU");
			pkgUserService.delete(pkgUserModel);
			
			
			
			
			
			
			
		}else if("319".equals(pkg21Model.getSave_status())){	//검증결과 승인반려
			
//			status 307 단계 삭제
//			메일 발송 - 요청자 이전 승인자
//			파일 삭제
//			systemFileModel.setType("CVT");
//			delete_file(systemFileModel);		
			
			pkg21Model.setSave_status("306");// 300 제거 
			wiredService.pkg_status_delete(pkg21Model);
			
			Pkg21Model pkg21Model2 = new Pkg21Model();
			pkg21Model2.setSelect_status("307");//조회
			pkg21Model2.setSave_status("306");//변경
			pkg21Model2.setPkg_seq(pkg21Model.getPkg_seq());
			wiredStatusService.statusUpdate(pkg21Model2);//305-> 302 계획 수립 승인 완료 단계
			
			
//			System.out.println("====319=============au_comment===========statr===============");
//			System.out.println(pkg21Model.getCol15());//반려 이유 -- 메일 추가
//			System.out.println("====319=============au_comment===========end===============");
			
			pkg21Model.setSave_status("306");//조회 
			pkg21Model.setCol15(pkg21Model.getCol15());
			wiredStatusService.update(pkg21Model);//반려사유 저장
			
			

			
//			pkg21Model.setSave_status("306");
//			wiredStatusService.update(pkg21Model);//삭제대신 수정가능한 status 상태 임시저장(300)으로 변경			
			pkg21Model.setSave_status("319");//master_status 반려상태
			
			NewMailModel inputModel = new NewMailModel();//메일 title , content set
			inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"103"));
			inputModel.setMailTitle("검증 결과 승인 반려 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목				
			String content ="";
			content = newMailService.genrerateStringWired(null ,pkg21Model , "103",""); // SVT 계획결과 :2
			inputModel.setMailContent(content); // 내용
			
			
//			Pkg21Model pkg21Model2 = new Pkg21Model();
//			pkg21Model2.setPkg_seq(pkg21Model.getPkg_seq());
//			pkg21Model2 = wiredService.read(pkg21Model2);
			
			List<String> pkg_userlist = new ArrayList<String>();
			//등록자 조회
			
//			inputModel = new NewMailModel();//메일 title , content set
			gubun.clear(); 
			inputModel.setUser_type("M");
			gubun.add("AU");//현장승인담당자	확대
			inputModel.setOrd( pkg21Model.getOrd() );//현재 승인 담당자 ord 123....차				
			inputModel.setPkg_seq(pkg21Model.getPkg_seq());
			inputModel.setGubuns(gubun);
			
//				List<String> pkg_userlist = new ArrayList<String>();
			pkg_userlist = newMailService.pkgUserIdList2( inputModel);  //현재 ord 보다 낮은 담당자 리스트
			
			if(!inputModel.getUser_type().isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
				inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
				newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리   -- 현재 manager 만 검색한다. 
			}
			
			
			
//			메일 보내고 승인자 list삭제
//			this.pkg_user_create(pkg21Model);
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("AR");
			pkgUserService.delete(pkgUserModel);
			
			
			
			
			
			
		}else if("332".equals(pkg21Model.getSave_status())){	//초도적용 계획 수립 승인 반려   --  초도 적용 계획 수립 단계부터 다시진행
			
//			status 332/ 331 단계 삭제
//			메일 발송 - 요청자 이전 승인자
			
			pkg21Model.setSave_status("332");// 332 계획 승인요청 제거 
			wiredService.pkg_status_delete(pkg21Model);
			pkg21Model.setSave_status("331");// 331 계획 수립 제거 
			wiredService.pkg_status_delete(pkg21Model);
			
			
//			pkg21Model.setSave_status("306");//조회 
//			pkg21Model.setCol15(pkg21Model.getCol15());
//			wiredStatusService.update(pkg21Model);//반려사유 저장
			
			pkg21Model.setSave_status("308");//조회 
			pkg21Model.setCol14(pkg21Model.getAu_comment());
			wiredStatusService.update(pkg21Model);//반려사유 저장
			
//			pkg21Model.setSave_status("308");
//			wiredStatusService.update(pkg21Model);//삭제대신 수정가능한 status 상태 임시저장(300)으로 변경			
			pkg21Model.setSave_status("320");//master_status 반려상태
			
			
			List<PkgEquipmentModel> eqmentList = null;	
			eqmentList =   wiredService.getPkgEquipment(pkg21Model, "S");
			
			
			NewMailModel inputModel = new NewMailModel();//메일 title , content set
			inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"104"));
			inputModel.setMailTitle("초도적용 계획 승인 반려 (PKG명 : "+pkg21Model.getTitle()+")"); // 제목				
			String content ="";
			content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "104",""); // SVT 계획결과 :2
			inputModel.setMailContent(content); // 내용
			
			
//			Pkg21Model pkg21Model2 = new Pkg21Model();
//			pkg21Model2.setPkg_seq(pkg21Model.getPkg_seq());
//			pkg21Model2 = wiredService.read(pkg21Model2);
			
			List<String> pkg_userlist = new ArrayList<String>();
			//등록자 조회
			
//			inputModel = new NewMailModel();//메일 title , content set
			gubun.clear(); 
			inputModel.setUser_type("M");
			gubun.add("AU");//현장승인담당자	확대
			inputModel.setOrd( pkg21Model.getOrd() );//현재 승인 담당자 ord 123....차				
			inputModel.setPkg_seq(pkg21Model.getPkg_seq());
			inputModel.setGubuns(gubun);
				
//				List<String> pkg_userlist = new ArrayList<String>();
			pkg_userlist = newMailService.pkgUserIdList2( inputModel);  //현재 ord 보다 낮은 담당자 리스트

			if(!inputModel.getUser_type().isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
				inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
				newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리   -- 현재 manager 만 검색한다. 
			}
			
			
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("AS");
			pkgUserService.delete(pkgUserModel);
			
			
			
		}else if("341".equals(pkg21Model.getSave_status())){	//확대적용 계획 수립 승인 반려   --  초도 적용 계획 수립 단계부터 다시진행
			pkg21Model.setSave_status("342");// 342 확대 계획 승인요청 제거 
			wiredService.pkg_status_delete(pkg21Model);
//			status 342 단계 삭제
//			메일 발송 - 요청자 이전 승인자
			
			
			pkg21Model.setSave_status("308");//조회 
			pkg21Model.setCol14(pkg21Model.getAu_comment());
			wiredStatusService.update(pkg21Model);//반려사유 저장
			
			
//			pkg21Model.setSave_status("337");
//			wiredStatusService.update(pkg21Model);//삭제대신 수정가능한 status 상태 임시저장(300)으로 변경			
			pkg21Model.setSave_status("330");//master_status 반려상태
			
			
			List<PkgEquipmentModel> eqmentList = null;	
			eqmentList =   wiredService.getPkgEquipment(pkg21Model, "E");
			
			NewMailModel inputModel = new NewMailModel();//메일 title , content set
			inputModel.setSmsMsgvalue(newMailService.makeSmsMsgWired(pkg21Model,"105"));
			inputModel.setMailTitle("확대적용 계획 승인 반려  (PKG명 : "+pkg21Model.getTitle()+")"); // 제목				
			String content ="";
			content = newMailService.genrerateStringWired(eqmentList ,pkg21Model , "105",""); // 
			inputModel.setMailContent(content); // 내용
			
			
//			Pkg21Model pkg21Model2 = new Pkg21Model();
//			pkg21Model2.setPkg_seq(pkg21Model.getPkg_seq());
//			pkg21Model2 = wiredService.read(pkg21Model2);
			
			List<String> pkg_userlist = new ArrayList<String>();
			//등록자 조회
			
//			inputModel = new NewMailModel();//메일 title , content set
			gubun.clear(); 
			inputModel.setUser_type("M");
			gubun.add("AU");//현장승인담당자	확대
			inputModel.setOrd( pkg21Model.getOrd() );//현재 승인 담당자 ord 123....차				
			inputModel.setPkg_seq(pkg21Model.getPkg_seq());
			inputModel.setGubuns(gubun);
			
//				List<String> pkg_userlist = new ArrayList<String>();
			pkg_userlist = newMailService.pkgUserIdList2( inputModel);  //현재 ord 보다 낮은 담당자 리스트

			if(!inputModel.getUser_type().isEmpty()) {//검색된 담당자 정보가 없을경우 메일을 안보낸다. ex 2차 담당자가 검색안되는 경우 안보낸다.
				inputModel.setUser_ids(pkg_userlist);//pkg_user에서 승인담당자 검색 후 set
				newMailService.maileModuleUserId(   "NON", inputModel);//user list 받아서 처리   -- 현재 manager 만 검색한다. 
			}
			
			
			
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("LA");
			pkgUserService.delete(pkgUserModel);
			
			
			
		}
		
		

		//이전단계로 돌아가기 위한 master 갱신
		wiredService.pkg_status_update(pkg21Model);
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), pkg21Model.getSave_status());
	}
	
	public void delete_file(SystemFileModel systemFileModel) throws Exception {
		if(null != wiredService.sysFileList(systemFileModel)){
			List<SystemFileModel> fileList = wiredService.sysFileList(systemFileModel);
			for(SystemFileModel sysFile : fileList){
				attachFileService.new_file_del(sysFile);
			}
		}
	}
	
	
	
	
	@RequestMapping(value = "/pkgmg/wired/Wired_Pkg21_NOW_Status_Navi.do")
	public String Status_Navi(Pkg21Model pkg21Model, Model model) throws Exception{

		Pkg21Model pkg21Model2 = new Pkg21Model();
		Pkg21Model p21Model = new Pkg21Model();
		Pkg21Model search = new Pkg21Model();
		String now_navi_status ="1";
		search.setPkg_seq(pkg21Model.getPkg_seq());
//		System.out.println("==============getPkg_seq=====================");
//		System.out.println("===================================");
//		System.out.println(pkg21Model.getPkg_seq());
//		System.out.println("===================================");
//		System.out.println("===================================");
		if(pkg21Model.getPkg_seq() != null) {
			pkg21Model2 = wiredService.read(search);				
		}
		
		if(pkg21Model2 != null) {
			if("301".equals(pkg21Model2.getStatus())  || "309".equals(pkg21Model2.getStatus())  ){
				now_navi_status = "1"; // PKG 개발결과
				
				search.setSelect_status("301");
				p21Model = wiredStatusService.read(search);	
				if(p21Model != null) {
					now_navi_status = "2";
				}
				
			}else if("311".equals(pkg21Model2.getStatus()) || "312".equals(pkg21Model2.getStatus()) ||"313".equals(pkg21Model2.getStatus()) ||"314".equals(pkg21Model2.getStatus()) ||
					"315".equals(pkg21Model2.getStatus()) ||"316".equals(pkg21Model2.getStatus()) ||"317".equals(pkg21Model2.getStatus()) ||"319".equals(pkg21Model2.getStatus()) ){
	
				now_navi_status = "2";// 개발 검증
							
				search.setSelect_status("308");
				p21Model = wiredStatusService.read(search);	
				if(p21Model != null) {
					now_navi_status = "3";
				}
				
			}else if("321".equals(pkg21Model2.getStatus()) || "322".equals(pkg21Model2.getStatus()) ||"323".equals(pkg21Model2.getStatus()) ||"320".equals(pkg21Model2.getStatus())  ){
									
				now_navi_status = "3";// 초도적용 계획 수립
							
				search.setSelect_status("335");
				p21Model = wiredStatusService.read(search);	
				if(p21Model != null) {
					now_navi_status = "4";
				}
				
			}else if("324".equals(pkg21Model2.getStatus()) || "329".equals(pkg21Model2.getStatus())  ){
				
				now_navi_status = "4";// 초도적용 결과
					
				search.setSelect_status("337");
				p21Model = wiredStatusService.read(search);	
				if(p21Model != null) {
					now_navi_status = "5";
				}
				
			}else if("332".equals(pkg21Model2.getStatus()) || "333".equals(pkg21Model2.getStatus())  || "330".equals(pkg21Model2.getStatus()) ){
				
				now_navi_status = "5";// 확대적용 계획 수립
					
				search.setSelect_status("343");
				p21Model = wiredStatusService.read(search);	
				if(p21Model != null) {
					now_navi_status = "6";
				}
				
			}else if("334".equals(pkg21Model2.getStatus())  ){
				
				now_navi_status = "6";// 확대적용 결과
					
				
			}else if("399".equals(pkg21Model2.getStatus())  ){
				
				now_navi_status = "7";// PKG완료
					
				
			}else if("389".equals(pkg21Model2.getStatus())  ){
				
				search.setSelect_status("307");
				p21Model = wiredStatusService.read(search);	
				if(p21Model != null) {
					now_navi_status = "2";
				}
					
				
			}
		}

		
		return ResultUtil.handleSuccessResultParam(model, now_navi_status, pkg21Model.getCol43());
//			return now_navi_status;
	}
	
	
	
	
	
	
	public String fin_active2(Pkg21Model pkg21Model, String charge_gubun) throws Exception{
		String _final = "";
		PkgUserModel pkgUserModel = new PkgUserModel();
		pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
		
		//승인
		pkgUserModel.setOrd(pkg21Model.getOrd());
		pkgUserModel.setCharge_gubun(charge_gubun);
		
		//모든 승인자가 승인한 경우에만 상태 업데이트
		pkgUserModel = pkgUserService.readActive(pkgUserModel);
		_final = pkgUserModel.getFin();
		return _final;
	}
	
	

	
	
	
	
	
}
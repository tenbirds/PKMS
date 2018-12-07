package com.pkms.pkgmg.access.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.client.Stub;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import skt.soa.notification.webservice.SMSSender;
import skt.soa.notification.webservice.SMSSenderServiceLocator;

import com.pkms.common.sms.model.SmsModel;
import com.pkms.common.sms.service.SmsServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.pkms.pkgmg.pkg.service.PkgEquipmentServiceIf;
import com.pkms.pkgmg.pkg.service.PkgUserServiceIf;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.pkg21.service.Pkg21StatusServiceIf;
import com.pkms.pkgmg.access.service.AccessServiceIf;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group1.service.Group1ServiceIf;
import com.pkms.sys.group2.service.Group2ServiceIf;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.sys.system.service.SystemServiceIf;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.SktUserServiceIf;
import com.wings.model.CodeModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.DateUtil;


/**
 * PKG Main Controller<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Controller
public class AccessController {
	static Logger logger = Logger.getLogger(AccessController.class);
	
	@Resource(name = "AccessService")
	private AccessServiceIf accessService;
	
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
	
	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;
	
	@Resource(name = "SmsService")
	private SmsServiceIf smsService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_ReadList.do")
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

		List<Pkg21Model> pkg21List = (List<Pkg21Model>) accessService.readList(pkg21Model);
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
		
		return "/pkgmg/access/Access_Pkg21_ReadList";
	}
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_Read.do")
	public String read(Pkg21Model pkg21Model, Model model) throws Exception{
		//pkg_seq가 있는 경우 data read

		if("read".equals(pkg21Model.getRead_gubun())){
			pkg21Model = accessService.read(pkg21Model);
			pkg21Model.setSelect_status("201");
			Pkg21Model p21Model = new Pkg21Model();
			p21Model.setPkg_seq(pkg21Model.getPkg_seq());
			p21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
			p21Model = pkg21StatusService.read(p21Model);

			if(StringUtils.hasLength(p21Model.getCol1())){				
				pkg21Model.setCol1(p21Model.getCol1());
			}
			if(StringUtils.hasLength(p21Model.getCol2())){				
				pkg21Model.setCol2(p21Model.getCol2());
			}
			if(StringUtils.hasLength(p21Model.getCol3())){
				pkg21Model.setCol3(p21Model.getCol3());
			}
			if(StringUtils.hasLength(p21Model.getCol4())){
				pkg21Model.setCol4(p21Model.getCol4());
			}
			if(StringUtils.hasLength(p21Model.getCol5())){
				pkg21Model.setCol5(p21Model.getCol5());
			}
			if(StringUtils.hasLength(p21Model.getCol6())){
				pkg21Model.setCol6(p21Model.getCol6());
			}
			if(StringUtils.hasLength(p21Model.getCol7())){
				pkg21Model.setCol7(p21Model.getCol7());
			}
			if(StringUtils.hasLength(p21Model.getCol8())){
				pkg21Model.setCol8(p21Model.getCol8());
			}
			
			Pkg21Model p241Model = new Pkg21Model();
			p241Model.setPkg_seq(pkg21Model.getPkg_seq());
			p241Model.setSelect_status("241");
			if(null != pkg21StatusService.real_status(p241Model)){
				p241Model.setSelect_status(pkg21StatusService.real_status(p241Model));
				if("243".equals(p241Model.getSelect_status())){
					pkg21Model.setCol24("end");
				}else{
					pkg21Model.setCol24("ing");
				}
			}else{
				pkg21Model.setCol24("start");
			}
			
			Pkg21Model p251Model = new Pkg21Model();
			p251Model.setPkg_seq(pkg21Model.getPkg_seq());
			p251Model.setSelect_status("251");
			if(null != pkg21StatusService.real_status(p251Model)){
				p251Model.setSelect_status(pkg21StatusService.real_status(p251Model));
				if("252".equals(p251Model.getSelect_status())){
					pkg21Model.setCol25("end");
				}else{
					pkg21Model.setCol25("ing");
				}
			}else{
				pkg21Model.setCol25("start");
			}
			
		}
		pkg21Model.setPkg21ModelList(accessService.getChkList(pkg21Model, "ACC_DVT"));
		
		pkg21Model.setSelect_status("201");
		model.addAttribute("Pkg21Model", pkg21Model);
		return "/pkgmg/access/Access_Pkg21_Read";
	}
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_Create.do")
	public String create(Pkg21Model pkg21Model, Model model) throws Exception {
		accessService.create(pkg21Model);
		pkg21Model.setSave_status("201");
		pkg21StatusService.create(pkg21Model);
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getPkg_seq(), "");
	}
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_Update.do")
	public String update(Pkg21Model pkg21Model, Model model) throws Exception {
		accessService.update(pkg21Model);
		pkg21Model.setSave_status(pkg21StatusService.real_status(pkg21Model));
		pkg21StatusService.update(pkg21Model);
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getPkg_seq(), "");
	}
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_Status_Read.do")
	public String statusRead(Pkg21Model pkg21Model, Model model) throws Exception{
		//pkg_seq가 있는 경우 data read
		String retUrl = "";
		retUrl = "/pkgmg/access/Access_Pkg21_"+pkg21Model.getSelect_status()+"_Read";
		
		Pkg21Model p21Model = new Pkg21Model();
		p21Model = valueSetting(p21Model, pkg21Model);
		
		if("211".equals(pkg21Model.getSelect_status())){
			if(!"203".equals(pkg21Model.getStatus())){
				pkg21Model.setPkgEquipmentModelList(accessService.getPkgEquipment(pkg21Model, "1S"));
				String select_status = pkg21Model.getSelect_status();
				
				pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
				p21Model = pkg21StatusService.read(pkg21Model);
				
				pkg21Model.setSelect_status(select_status);
				p21Model = valueSetting(p21Model, pkg21Model);
				p21Model = value211(p21Model, pkg21Model);
				
				p21Model.setPkg21ModelList(accessService.getChkList(pkg21Model, "ACC_DVT1"));
				p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			}
		}
		if("221".equals(pkg21Model.getSelect_status())){
			if(!"212".equals(pkg21Model.getStatus())){
				pkg21Model.setPkgEquipmentModelList(accessService.getPkgEquipment(pkg21Model, "2S"));
				String select_status = pkg21Model.getSelect_status();
				
				pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
				p21Model = pkg21StatusService.read(pkg21Model);
				
				pkg21Model.setSelect_status(select_status);
				p21Model = valueSetting(p21Model, pkg21Model);
				p21Model = value221(p21Model, pkg21Model);
				
				p21Model.setPkg21ModelList(accessService.getChkList(pkg21Model, "ACC_DVT2"));
				p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			}
		}
		
		if("231".equals(pkg21Model.getSelect_status())){
			if(!"222".equals(pkg21Model.getStatus())){
				pkg21Model.setPkgEquipmentModelList(accessService.getPkgEquipment(pkg21Model, "3S"));
				
				if(null != pkg21StatusService.read(pkg21Model)){
					Pkg21Model p211Model = new Pkg21Model();
					p211Model = pkg21StatusService.read(pkg21Model);
					p21Model.setReg_date_plan(p211Model.getReg_date());
					p21Model.setReg_plan_user(p211Model.getReg_user_name());
				}
				

				if(!"231".equals(pkg21Model.getStatus())){
					Pkg21Model p2Model = new Pkg21Model();
					p2Model.setPkg_seq(pkg21Model.getPkg_seq());
					String select_status = pkg21Model.getSelect_status();

					pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
					p2Model = pkg21StatusService.read(pkg21Model);
					
					pkg21Model.setSelect_status(select_status);
					p21Model.setReg_date_result(p2Model.getReg_date());
					p21Model.setReg_result_user(p2Model.getReg_user_name());
					
					if(!"232".equals(pkg21Model.getStatus())){
						PkgUserModel pkgUserModel = new PkgUserModel();
						pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
						
						pkgUserModel.setCharge_gubun("AU");
						if(null != pkgUserService.readList(pkgUserModel) && 0 < pkgUserService.readList(pkgUserModel).size()){
							p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
							p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
						}else{
							//승인자 목록
							SysModel sysModel = new SysModel();
							sysModel.setSystem_seq(pkg21Model.getSystem_seq());
							sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
							sysModel = systemService.readUsersAppliedToSystem(sysModel);
							p21Model.setSystemUserModelList(sysModel.getSystemUserList());
						}
					}else{
						PkgUserModel pkgUserModel = new PkgUserModel();
						pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
						
						pkgUserModel.setCharge_gubun("AU");
						p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
						p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
					}
				}else{
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
				}

				p21Model = valueSetting(p21Model, pkg21Model);
				
				p21Model.setPkg21ModelList(accessService.getChkList(pkg21Model, "ACC_DVT3"));
				p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			}
		}
		
		if("241".equals(pkg21Model.getSelect_status())){
			String select_status = pkg21Model.getSelect_status();
			if(null != pkg21StatusService.real_status(pkg21Model)){
				Pkg21Model p211Model = new Pkg21Model();
				p211Model = pkg21StatusService.read(pkg21Model);
				
				pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
				p21Model = pkg21StatusService.read(pkg21Model);
				p21Model.setReg_date_plan(p211Model.getReg_date());
				p21Model.setReg_plan_user(p211Model.getReg_user_name());
				
				p21Model.setReg_date_result(p21Model.getReg_date());
				p21Model.setReg_result_user(p21Model.getReg_user_name());

				if("243".equals(pkg21Model.getSelect_status())){
					pkg21Model.setCol24("end");
				}else{
					pkg21Model.setCol24("ing");
				}
				
				if("241".equals(pkg21Model.getSelect_status())){
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
				}else{
					
					PkgUserModel pkgUserModel = new PkgUserModel();
					pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
					
					pkgUserModel.setCharge_gubun("A0");
					p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
					p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
				}
				p21Model.setPkg21ModelList(accessService.getChkList(pkg21Model, "ACC_CVT"));
			}else{
				pkg21Model.setSelect_status("203");
				p21Model = pkg21StatusService.read(pkg21Model);
				p21Model.setCol43("Y");
				pkg21Model.setCol24("start");
			}
			pkg21Model.setSelect_status(select_status);
			p21Model = valueSetting(p21Model, pkg21Model);
		}
		
		if("251".equals(pkg21Model.getSelect_status())){
			String select_status = pkg21Model.getSelect_status();
			if(null != pkg21StatusService.real_status(pkg21Model)){
				pkg21Model.setPkgEquipmentModelList(accessService.getPkgEquipment(pkg21Model, "1C"));
				
				Pkg21Model p211Model = new Pkg21Model();
				p211Model = pkg21StatusService.read(pkg21Model);
				
				pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
				p21Model = pkg21StatusService.read(pkg21Model);
				
				p21Model.setReg_date_plan(p211Model.getReg_date());
				p21Model.setReg_plan_user(p211Model.getReg_user_name());
				
				if("252".equals(pkg21Model.getSelect_status())){
					pkg21Model.setCol25("end");
				}else{
					pkg21Model.setCol25("ing");
				}
				
				if("251".equals(pkg21Model.getSelect_status())){
					p21Model.setCol43("N");
				}else{
					p21Model.setReg_date_result(p21Model.getReg_date());
					p21Model.setReg_result_user(p21Model.getReg_user_name());
					
					p21Model.setCol43("S");
				}
				
				p21Model.setPkg21ModelList(accessService.getChkList(pkg21Model, "ACC_CVT1"));
				p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			}else{
				p21Model.setCol43("Y");
				pkg21Model.setCol25("start");
			}
			
			pkg21Model.setSelect_status(select_status);
			p21Model = valueSetting(p21Model, pkg21Model);
		}
		
		if("261".equals(pkg21Model.getSelect_status())){
			String select_status = pkg21Model.getSelect_status();
			if(null != pkg21StatusService.real_status(pkg21Model)){
				pkg21Model.setPkgEquipmentModelList(accessService.getPkgEquipment(pkg21Model, "2C"));
				
				Pkg21Model p211Model = new Pkg21Model();
				p211Model = pkg21StatusService.read(pkg21Model);
				
				pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
				p21Model = pkg21StatusService.read(pkg21Model);
				
				p21Model.setReg_date_plan(p211Model.getReg_date());
				p21Model.setReg_plan_user(p211Model.getReg_user_name());
				
				if("261".equals(pkg21Model.getSelect_status())){
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
				}else{
					p21Model.setReg_date_result(p21Model.getReg_date());
					p21Model.setReg_result_user(p21Model.getReg_user_name());
					
					PkgUserModel pkgUserModel = new PkgUserModel();
					pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
					
					pkgUserModel.setCharge_gubun("A2");
					p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
					p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
				}
				
				p21Model.setPkg21ModelList(accessService.getChkList(pkg21Model, "ACC_CVT2"));
				p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			}else{
				p21Model.setCol43("Y");
			}
			
			pkg21Model.setSelect_status(select_status);
			p21Model = valueSetting(p21Model, pkg21Model);
		}
		
		if("271".equals(pkg21Model.getSelect_status())){
			if(!"264".equals(pkg21Model.getStatus())){
				pkg21Model.setPkgEquipmentModelList(accessService.getPkgEquipment(pkg21Model, "1E"));
				String select_status = pkg21Model.getSelect_status();

				if(null != pkg21StatusService.read(pkg21Model)){
					Pkg21Model p211Model = new Pkg21Model();
					p211Model = pkg21StatusService.read(pkg21Model);
					p21Model.setReg_date_plan(p211Model.getReg_date());
					p21Model.setReg_plan_user(p211Model.getReg_user_name());
				}

				if(!"271".equals(pkg21Model.getStatus())){
					if(null != pkg21StatusService.real_status(pkg21Model)){
						Pkg21Model p2Model = new Pkg21Model();
						p2Model.setPkg_seq(pkg21Model.getPkg_seq());
						pkg21Model.setSelect_status(pkg21StatusService.real_status(pkg21Model));
						p2Model = pkg21StatusService.read(pkg21Model);
						pkg21Model.setSelect_status(select_status);
						p21Model.setReg_date_result(p2Model.getReg_date());
						p21Model.setReg_result_user(p2Model.getReg_user_name());
					}
					
					if(!"272".equals(pkg21Model.getStatus())){
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
						PkgUserModel pkgUserModel = new PkgUserModel();
						pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
						
						pkgUserModel.setCharge_gubun("LA");
						p21Model.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
						p21Model.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
					}
				}else{
					SysModel sysModel = new SysModel();
					sysModel.setSystem_seq(pkg21Model.getSystem_seq());
					sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
					sysModel = systemService.readUsersAppliedToSystem(sysModel);
					p21Model.setSystemUserModelList(sysModel.getSystemUserList());
				}
				
				p21Model.setPkgEquipmentModelList(pkg21Model.getPkgEquipmentModelList());
			}
			p21Model = valueSetting(p21Model, pkg21Model);
		}
		
		model.addAttribute("Pkg21Model", p21Model);
		return retUrl;
	}
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_Status_Create.do")
	public String status_create(Pkg21Model pkg21Model, Model model) throws Exception {
		
		String _fin = "Y"; 
		
		//1차 2차 3차 초도 chk list 저장
		if("203".equals(pkg21Model.getSave_status()) || "212".equals(pkg21Model.getSave_status())
			|| "222".equals(pkg21Model.getSave_status()) || "232".equals(pkg21Model.getSave_status())
			|| "242".equals(pkg21Model.getSave_status()) || "252".equals(pkg21Model.getSave_status())
			|| "262".equals(pkg21Model.getSave_status())){
			accessService.pkg_chk_create(pkg21Model);
		}
				
		if("273".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "LA");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("272");
			}
		}
		
		if("233".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "AU");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("232");
			}
		}
		
		if("243".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "A0");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("242");
			}
		}
		
		if("263".equals(pkg21Model.getSave_status())){
			_fin = fin_active(pkg21Model, "A2");
			if("N".equals(_fin)){
				pkg21Model.setSave_status("262");
			}
		}
		
		if("211".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "1S");
			equipment_copy_file(pkg21Model, "1SEquipmentAttach1", "1SRquipmentAttach");
			equipment_delete_file(pkg21Model, "1SEquipmentAttach1");
		}
		
		if("221".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "2S");
			equipment_copy_file(pkg21Model, "2SEquipmentAttach1", "2SRquipmentAttach");
			equipment_delete_file(pkg21Model, "2SEquipmentAttach1");
		}
		
		if("231".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "3S");
			equipment_copy_file(pkg21Model, "3SEquipmentAttach1", "3SRquipmentAttach");
			equipment_delete_file(pkg21Model, "3SEquipmentAttach1");
		}
		
		if("251".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "1C");
			equipment_copy_file(pkg21Model, "1CEquipmentAttach1", "1CRquipmentAttach");
			equipment_delete_file(pkg21Model, "1CEquipmentAttach1");
		}
		
		if("261".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "2C");
			equipment_copy_file(pkg21Model, "2CEquipmentAttach1", "2CRquipmentAttach");
			equipment_delete_file(pkg21Model, "2CEquipmentAttach1");
		}
		
		if("271".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "1E");
			equipment_copy_file(pkg21Model, "1EEquipmentAttach1", "1ERquipmentAttach");
			equipment_delete_file(pkg21Model, "1EEquipmentAttach1");
		}
		
		
		
		if("Y".equals(_fin)){
			pkg21StatusService.create(pkg21Model);
			if(!"241".equals(pkg21Model.getSelect_status()) && !"251".equals(pkg21Model.getSelect_status())){				
				accessService.pkg_status_update(pkg21Model);
			}
			
			if("259".equals(pkg21Model.getSave_status())){
				accessService.pkg_status_update(pkg21Model);
			}
			
			//승인요청시
			if("272".equals(pkg21Model.getSave_status()) || "232".equals(pkg21Model.getSave_status())
				|| "242".equals(pkg21Model.getSave_status()) || "262".equals(pkg21Model.getSave_status())){
				this.pkg_user_create(pkg21Model);
			}
			
			if("272".equals(pkg21Model.getSave_status()) || "212".equals(pkg21Model.getSave_status())
				|| "222".equals(pkg21Model.getSave_status()) || "232".equals(pkg21Model.getSave_status())
				|| "252".equals(pkg21Model.getSave_status()) || "262".equals(pkg21Model.getSave_status())){
				
				if("271".equals(pkg21Model.getSelect_status())){
					pkg21Model.setWork_gubun("1E");
				} else if("211".equals(pkg21Model.getSelect_status())){
					pkg21Model.setWork_gubun("1S");
				} else if("221".equals(pkg21Model.getSelect_status())){
					pkg21Model.setWork_gubun("2S");
				} else if("231".equals(pkg21Model.getSelect_status())){
					pkg21Model.setWork_gubun("3S");
				} else if("251".equals(pkg21Model.getSelect_status())){
					pkg21Model.setWork_gubun("1C");
				} else if("261".equals(pkg21Model.getSelect_status())){
					pkg21Model.setWork_gubun("2C");
				}
				
				this.equipment_result_update(pkg21Model);
			}
		}else{
			
		}
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), pkg21Model.getSave_status());
	}
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_Status_Update.do")
	public String status_update(Pkg21Model pkg21Model, Model model) throws Exception {
//		pkg21StatusService.update(pkg21Model);
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), "");
	}
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_PeType_Ajax_Read.do")
	public String peTypeRead(Pkg21Model pkg21Model, Model model) throws Exception{
		Pkg21Model p21Model = new Pkg21Model();
//		p21Model = pkg21Service.peTypeRead(pkg21Model);
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
		
		p21Model.setCol24(pkg21Model.getCol24());
		p21Model.setCol25(pkg21Model.getCol25());
		
		return p21Model;
	}
	
	private void pkg_user_create(Pkg21Model pkg21Model) throws Exception{
		PkgUserModel pkgUserModel = null;
		
		// 시스템 user read
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(pkg21Model.getSystem_seq());
		
		if("272".equals(pkg21Model.getSave_status())){
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.LA);
		}else if("232".equals(pkg21Model.getSave_status()) || "242".equals(pkg21Model.getSave_status())
				|| "262".equals(pkg21Model.getSave_status())){
			sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
		}
		
		sysModel = systemService.readUsersAppliedToSystem(sysModel);
		List<SystemUserModel> sList = sysModel.getSystemUserList();
		
		String[] checkSeqs = pkg21Model.getCheck_seqs();
		int ord = 1;
		for(int i = 0; i < checkSeqs.length; i++) {
			for(SystemUserModel model : sList) {
				if("272".equals(pkg21Model.getSave_status())){
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
				}else if("232".equals(pkg21Model.getSave_status())){
					if(SYSTEM_USER_CHARGE_GUBUN.AU.getCode().equals(model.getCharge_gubun().getCode())) {
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
				}else if("242".equals(pkg21Model.getSave_status())){
					if(SYSTEM_USER_CHARGE_GUBUN.AU.getCode().equals(model.getCharge_gubun().getCode())) {
						if(model.getUser_id().equals(checkSeqs[i])) {
							pkgUserModel = new PkgUserModel();
							pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgUserModel.setUser_id(model.getUser_id());
							pkgUserModel.setCharge_gubun("A0");
							pkgUserModel.setOrd(String.valueOf((ord++)));
							pkgUserModel.setUse_yn("Y");
							pkgUserService.create(pkgUserModel);
							break;
						}
					}
				}else if("262".equals(pkg21Model.getSave_status())){
					if(SYSTEM_USER_CHARGE_GUBUN.AU.getCode().equals(model.getCharge_gubun().getCode())) {
						if(model.getUser_id().equals(checkSeqs[i])) {
							pkgUserModel = new PkgUserModel();
							pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
							pkgUserModel.setUser_id(model.getUser_id());
							pkgUserModel.setCharge_gubun("A2");
							pkgUserModel.setOrd(String.valueOf((ord++)));
							pkgUserModel.setUse_yn("Y");
							pkgUserService.create(pkgUserModel);
							break;
						}
					}
				}
				
				
			}
		}
		
		//SMS
		pkgUserModel = new PkgUserModel();
		pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
		
		if("272".equals(pkg21Model.getSave_status())){
			pkgUserModel.setCharge_gubun("LA");
		}else if("232".equals(pkg21Model.getSave_status())){
			pkgUserModel.setCharge_gubun("AU");
		}else if("242".equals(pkg21Model.getSave_status())){
			pkgUserModel.setCharge_gubun("A0");
		}else if("262".equals(pkg21Model.getSave_status())){
			pkgUserModel.setCharge_gubun("A2");
		}
		
		pkgUserModel = pkgUserService.readActive(pkgUserModel);
		this.sendSms(pkg21Model, pkgUserModel);
	}
	
	public String fin_active(Pkg21Model pkg21Model, String charge_gubun) throws Exception{
		String _final = "";
		PkgUserModel pkgUserModel = new PkgUserModel();
		pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
		
		//승인
		pkgUserModel.setOrd(pkg21Model.getOrd());
		pkgUserModel.setCharge_gubun(charge_gubun);
		pkgUserService.update(pkgUserModel);
		
		//모든 승인자가 승인한 경우에만 상태 업데이트
		pkgUserModel = pkgUserService.readActive(pkgUserModel);
		_final = pkgUserModel.getFin();
		
		//SMS
		if("Y".equals(_final)) {//더이상 승인할 사람이 없다는 의미
		} else {
			//다음 승인자에게 SMS
			this.sendSms(pkg21Model, pkgUserModel);
		}
		return _final;
	}
	
	private Pkg21Model value211(Pkg21Model p21Model, Pkg21Model pkg21Model) throws Exception{
		Pkg21Model p211Model = new Pkg21Model();
		p211Model = pkg21StatusService.read(pkg21Model);
		p21Model.setReg_date_plan(p211Model.getReg_date());
		p21Model.setReg_plan_user(p211Model.getReg_user_name());

		if(!"211".equals(pkg21Model.getStatus())){
			p211Model.setPkg_seq(pkg21Model.getPkg_seq());
			p211Model.setSelect_status("212");
			p211Model = pkg21StatusService.read(pkg21Model);
			p21Model.setReg_date_result(p211Model.getReg_date());
			p21Model.setReg_result_user(p211Model.getReg_user_name());
		}

		return p21Model;
	}
	
	private Pkg21Model value221(Pkg21Model p21Model, Pkg21Model pkg21Model) throws Exception{
		Pkg21Model p211Model = new Pkg21Model();
		p211Model = pkg21StatusService.read(pkg21Model);
		p21Model.setReg_date_plan(p211Model.getReg_date());
		p21Model.setReg_plan_user(p211Model.getReg_user_name());

		if(!"221".equals(pkg21Model.getStatus())){
			p211Model.setPkg_seq(pkg21Model.getPkg_seq());
			p211Model.setSelect_status("222");
			p211Model = pkg21StatusService.read(pkg21Model);
			p21Model.setReg_date_result(p211Model.getReg_date());
			p21Model.setReg_result_user(p211Model.getReg_user_name());
		}

		return p21Model;
	}
		
	public void equipment_update(Pkg21Model pkg21Model, String work_gubun) throws Exception {
		PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
		
		pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
		pkgEquipmentModel.setWork_gubun(work_gubun);
		pkgEquipmentModel=pkgEquipmentService.maxOrd(pkgEquipmentModel);
		
		String max_ord = pkgEquipmentModel.getOrd();

		pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
		pkgEquipmentModel.setWork_gubun(work_gubun);
		pkgEquipmentModel.setOrd(max_ord);

		pkgEquipmentModel.setStart_date(pkg21Model.getStart_date());
		pkgEquipmentModel.setEnd_date(pkg21Model.getEnd_date());
		pkgEquipmentModel.setStart_time1(pkg21Model.getStart_time1());
		pkgEquipmentModel.setStart_time2(pkg21Model.getStart_time2());
		pkgEquipmentModel.setEnd_time1(pkg21Model.getEnd_time1());
		pkgEquipmentModel.setEnd_time2(pkg21Model.getEnd_time2());
		pkgEquipmentModel.setAmpm(pkg21Model.getAmpm());
		pkgEquipmentModel.setPatch_title(pkg21Model.getPatch_title());
		pkgEquipmentService.updateAccess(pkgEquipmentModel);
		
		accessService.tangoWork(pkg21Model, work_gubun);
	}
	
	public void equipment_result_update(Pkg21Model pkg21Model) throws Exception {
		String[] checkSeqs = pkg21Model.getCheck_seqs_e();
		String[] work_result = pkg21Model.getWork_result();
		String[] result_comment = pkg21Model.getResult_comment();
		
		for(int i = 0; i < checkSeqs.length; i++) {			
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentModel.setOrd(checkSeqs[i]);
			pkgEquipmentModel.setWork_result(work_result[i]);
			pkgEquipmentModel.setWork_gubun(pkg21Model.getWork_gubun());
			pkgEquipmentModel.setResult_comment(result_comment[i]);
			pkgEquipmentService.updateResultAccess(pkgEquipmentModel);
		}
		
	}
	
	public void equipment_delete_file(Pkg21Model pkg21Model, String delete_file_id) throws Exception {
		pkg21Model.setDel_file_id(delete_file_id);
		accessService.equipment_delete_file(pkg21Model);
	}
	
	public void not_like_delete_file(Pkg21Model pkg21Model, String not_delete_file_id) throws Exception {
		pkg21Model.setDel_file_id(not_delete_file_id);
		accessService.not_like_delete_file(pkg21Model);
	}
	
	public void equipment_copy_file(Pkg21Model pkg21Model, String delete_file_id, String copy_file_id) throws Exception {
		pkg21Model.setDel_file_id(copy_file_id);
		
		//1ER count = max+1 
		Pkg21Model p21Mdl = new Pkg21Model();
		p21Mdl= accessService.copy_cnt(pkg21Model);
		
		pkg21Model.setDel_file_id(delete_file_id);
		pkg21Model.setCopy_file_count(copy_file_id + p21Mdl.getCopy_file_count());
		
		accessService.copy_file(pkg21Model);
	}
	
	public void copy_file_delete(Pkg21Model pkg21Model, String copy_file_id) throws Exception {
		pkg21Model.setDel_file_id(copy_file_id);
		
		accessService.copy_file_delete(pkg21Model);
	}
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_After_Create.do")
	public String after_Create(Pkg21Model pkg21Model, Model model) throws Exception {
		if("211".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "1S");
			equipment_copy_file(pkg21Model, "1SEquipmentAttach1", "1SRquipmentAttach");
			equipment_delete_file(pkg21Model, "1SEquipmentAttach1");
			
			pkg21Model.setSave_status("211");
			accessService.pkg_status_update(pkg21Model);
			pkg21StatusService.update(pkg21Model);
		}
		
		if("221".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "2S");
			equipment_copy_file(pkg21Model, "2SEquipmentAttach1", "2SRquipmentAttach");
			equipment_delete_file(pkg21Model, "2SEquipmentAttach1");
			
			pkg21Model.setSave_status("221");
			accessService.pkg_status_update(pkg21Model);
			pkg21StatusService.update(pkg21Model);
		}
		
		if("231".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "3S");
			equipment_copy_file(pkg21Model, "3SEquipmentAttach1", "3SRquipmentAttach");
			equipment_delete_file(pkg21Model, "3SEquipmentAttach1");
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("AU");
			pkgUserService.delete(pkgUserModel);
			
			pkg21Model.setSave_status("232");
			accessService.pkg_status_delete(pkg21Model);
			
			pkg21Model.setSave_status("233");
			accessService.pkg_status_delete(pkg21Model);
			
			pkg21Model.setSave_status("231");
			accessService.pkg_status_update(pkg21Model);
		}
		
		if("251".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "1C");
			equipment_copy_file(pkg21Model, "1CEquipmentAttach1", "1CRquipmentAttach");
			equipment_delete_file(pkg21Model, "1CEquipmentAttach1");
			
			pkg21Model.setSave_status("251");
			pkg21StatusService.update(pkg21Model);
		}
		
		if("261".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "2C");
			equipment_copy_file(pkg21Model, "2CEquipmentAttach1", "2CRquipmentAttach");
			equipment_delete_file(pkg21Model, "2CEquipmentAttach1");
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("A2");
			pkgUserService.delete(pkgUserModel);
			
			pkg21Model.setSave_status("262");
			accessService.pkg_status_delete(pkg21Model);
			
			pkg21Model.setSave_status("263");
			accessService.pkg_status_delete(pkg21Model);
			
			pkg21Model.setSave_status("261");
			accessService.pkg_status_update(pkg21Model);
		}
		
		if("271".equals(pkg21Model.getSave_status())){
			equipment_update(pkg21Model, "1E");
			equipment_copy_file(pkg21Model, "1EEquipmentAttach1", "1ERquipmentAttach");
			equipment_delete_file(pkg21Model, "1EEquipmentAttach1");
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("LA");
			pkgUserService.delete(pkgUserModel);
			
			pkg21Model.setSave_status("272");
			accessService.pkg_status_delete(pkg21Model);
			
			pkg21Model.setSave_status("273");
			accessService.pkg_status_delete(pkg21Model);
			
			pkg21Model.setSave_status("271");
			accessService.pkg_status_update(pkg21Model);
		}
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), pkg21Model.getSave_status());
	}
	
	@RequestMapping(value = "/pkgmg/access/Access_Pkg21_Status_Reject.do")
	public String status_reject(Pkg21Model pkg21Model, Model model) throws Exception {
		if("233".equals(pkg21Model.getSave_status())){
			equipment_delete_file(pkg21Model, "3SEquipmentAttach1");
			copy_file_delete(pkg21Model, "3SRquipmentAttach");
			copy_file_delete(pkg21Model, "3hoTestResultAttach");
			copy_file_delete(pkg21Model, "3systemLevelAttach");
			
			Pkg21Model delModel = new Pkg21Model();
			delModel.setPkg_seq(pkg21Model.getPkg_seq());
			delModel.setSave_status("23");
			accessService.pkg_status_delete_like(delModel);
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("AU");
			pkgUserService.delete(pkgUserModel);
			
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentModel.setWork_gubun("3S");
			pkgEquipmentService.deleteAccess(pkgEquipmentModel);
			
			accessService.pkg_chk_delete(pkg21Model);
			
			pkg21Model.setSave_status("222");
		}
		
		if("241".equals(pkg21Model.getSave_status())){
			Pkg21Model delModel = new Pkg21Model();
			delModel.setPkg_seq(pkg21Model.getPkg_seq());
			delModel.setSave_status("23");
			accessService.pkg_status_delete_like(delModel);
			delModel.setSave_status("22");
			accessService.pkg_status_delete_like(delModel);
			delModel.setSave_status("21");
			accessService.pkg_status_delete_like(delModel);
			delModel.setSave_status("203");
			accessService.pkg_status_delete(delModel);
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("AU");
			pkgUserService.delete(pkgUserModel);
			
			accessService.pkg_chk_delete_all(pkg21Model);
			
			this.not_like_delete_file(pkg21Model, "ttmattach");
			
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentService.deleteAccess(pkgEquipmentModel);
			
			pkg21Model.setSave_status("202");
		}
		
		if("243".equals(pkg21Model.getSave_status())){
			Pkg21Model delModel = new Pkg21Model();
			delModel.setPkg_seq(pkg21Model.getPkg_seq());
			delModel.setSave_status("24");
			accessService.pkg_status_delete_like(delModel);
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("A0");
			pkgUserService.delete(pkgUserModel);
			
			equipment_delete_file(pkg21Model, "AccCvtPkg");
			equipment_delete_file(pkg21Model, "AccCvtTraffic");
			equipment_delete_file(pkg21Model, "AccSvtSwPld");
			equipment_delete_file(pkg21Model, "AccSvtKpi");
			equipment_delete_file(pkg21Model, "AccSvtPkgDes");
			equipment_delete_file(pkg21Model, "AccSvtOpt");

			accessService.pkg_chk_delete(pkg21Model);
		}
		
		if("263".equals(pkg21Model.getSave_status())){
			equipment_delete_file(pkg21Model, "2CEquipmentAttach1");
			copy_file_delete(pkg21Model, "2CRquipmentAttach");
			copy_file_delete(pkg21Model, "2CoTestResultAttach");
			copy_file_delete(pkg21Model, "2CystemLevelAttach");
			
			Pkg21Model delModel = new Pkg21Model();
			delModel.setPkg_seq(pkg21Model.getPkg_seq());
			delModel.setSave_status("26");
			accessService.pkg_status_delete_like(delModel);
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("A2");
			pkgUserService.delete(pkgUserModel);
			
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentModel.setWork_gubun("2C");
			pkgEquipmentService.deleteAccess(pkgEquipmentModel);
			
			accessService.pkg_chk_delete(pkg21Model);
			
			pkg21Model.setSave_status("233");
		}
		
		if("273".equals(pkg21Model.getSave_status())){
			equipment_delete_file(pkg21Model, "1EEquipmentAttach1");
			copy_file_delete(pkg21Model, "1ERquipmentAttach");
			
			Pkg21Model delModel = new Pkg21Model();
			delModel.setPkg_seq(pkg21Model.getPkg_seq());
			delModel.setSave_status("27");
			accessService.pkg_status_delete_like(delModel);
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgUserModel.setCharge_gubun("LA");
			pkgUserService.delete(pkgUserModel);
			
			PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
			pkgEquipmentModel.setPkg_seq(pkg21Model.getPkg_seq());
			pkgEquipmentModel.setWork_gubun("1E");
			pkgEquipmentService.deleteAccess(pkgEquipmentModel);
			
			pkg21Model.setSave_status("264");
		}
		
		if(!"243".equals(pkg21Model.getSave_status())){			
			accessService.pkg_status_update(pkg21Model);
		}
		
		return ResultUtil.handleSuccessResultParam(model, pkg21Model.getSelect_status(), pkg21Model.getSave_status());
	}
	
	private void sendSms(Pkg21Model pkg21Model,PkgUserModel pkgUserModel) throws Exception {
		String tel = "";
		String tel1 = "";
		String tel2 = "";
		
		SktUserModel sktUserModel = new SktUserModel();
		sktUserModel.setEmpno(pkgUserModel.getUser_id());
		sktUserModel = sktUserService.read(sktUserModel);
		
		tel = sktUserModel.getMovetelno();
		if(tel != null) {
			tel = tel.replaceAll("-", "");
			tel1 = tel.substring(0, 3);
			tel2 = tel.substring(3, tel.length());
			
			SmsModel smsModel = new SmsModel();
			smsModel.setLog_no("1");//의미없음
			smsModel.setMsg("[PKMS 승인요청] " + pkg21Model.getTitle());
			smsModel.setDestcid(tel1);//국번
			smsModel.setDestcallno(tel2);
			smsModel.setPortedflag("0");//의미없음
			smsModel.setTid("65491");//승인인 경우
			
			try{
				SMSSenderServiceLocator locator = new SMSSenderServiceLocator();
				locator.setEndpointAddress("SMSSenderSoapPort", propertyService.getString("SOA.Sms.ip"));
				SMSSender service = locator.getSMSSenderSoapPort();
				Stub stub = (Stub)service;
				stub._setProperty(Stub.USERNAME_PROPERTY, propertyService.getString("SOA.Username"));
				stub._setProperty(Stub.PASSWORD_PROPERTY, propertyService.getString("SOA.Password"));
				
				String CONSUMER_ID = null; //송신시스템 id(시스템 별 ID 부여)
				String RPLY_PHON_NUM = null; //송신자 전화번호
				String TITLE = ""; //전송메시지
				String PHONE = null; //수신자 전화번호
				
				String URL = ""; //수신 URL		 					
				String START_DT_HMS = ""; //예약발송 시작시간			
				String END_DT_HMS = "";	//예약발송 종료시간
				
				CONSUMER_ID = propertyService.getString("SOA.Username");
				RPLY_PHON_NUM = propertyService.getString("SOA.Sms.sendNum");
				
				TITLE = "[PKMS 승인요청] " + pkg21Model.getTitle();
				
				PHONE = tel1 + tel2;
				
				StringHolder _return = new StringHolder();
				StringHolder uuid = new StringHolder();
				
				logger.debug("============================ sms_send_input_CONSUMER_ID ============================" + CONSUMER_ID);
				logger.debug("============================ sms_send_input_RPLY_PHON_NUM ==========================" + RPLY_PHON_NUM);
				logger.debug("============================ sms_send_input_TITLE ==================================" + TITLE);
				logger.debug("============================ sms_send_input_PHONE ==================================" + PHONE);
				
				service.send(CONSUMER_ID, RPLY_PHON_NUM, TITLE, PHONE, URL, START_DT_HMS, END_DT_HMS, _return, uuid);
				
				logger.debug("============================ sms_send_return =======================================" + _return.value);
				logger.debug("============================ sms_send_return_uuid ==================================" + uuid.value);
			} catch (ServiceException e) {
				e.printStackTrace();
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
			
			smsService.create(smsModel);
		}
	}
}
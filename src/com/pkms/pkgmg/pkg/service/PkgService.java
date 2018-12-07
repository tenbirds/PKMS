package com.pkms.pkgmg.pkg.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.client.Stub;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import skt.soa.notification.webservice.SMSSender;
import skt.soa.notification.webservice.SMSSenderServiceLocator;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.mail.model.MailModel;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.common.owms.model.OwmsModel;
import com.pkms.common.owms.service.OwmsServiceIf;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.common.sms.model.SmsModel;
import com.pkms.common.util.PkgMailUtil;
import com.pkms.common.workSystem.model.WorkSystemModel;
import com.pkms.common.workSystem.service.WorkSystemServiceIf;
import com.pkms.pkgmg.pkg.dao.PkgDAO;
import com.pkms.pkgmg.pkg.dao.PkgStatusDAO;
import com.pkms.pkgmg.pkg.dao.PkgSnetDao;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.model.EquipmentUserModel;
import com.pkms.sys.system.dao.SystemDAO;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.sys.system.service.SystemServiceIf;
import com.wings.properties.service.PropertyServiceIf;
import com.pkms.common.sms.service.SmsServiceIf;
import com.wings.util.ExcelUtil;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.pkms.verify_tem_mg.model.VerifyTemModel;

/**
 * PKG Main Service<br/>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgService")
public class PkgService implements PkgServiceIf {

	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;

	@Resource(name = "PkgDAO")
	private PkgDAO pkgDAO;
	
	@Resource(name = "PkgStatusDAO")
	private PkgStatusDAO pkgStatusDAO;

	@Resource(name = "OwmsService")
	private OwmsServiceIf owmsService;
	
	@Resource(name = "MailService")
	private MailServiceIf mailService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "PkgDetailVariableService")
	private PkgDetailVariableServiceIf pkgDetailVariableService;
	
	@Resource(name = "PkgEquipmentService")
	private PkgEquipmentServiceIf pkgEquipmentService;
	
	@Resource(name = "WorkSystemService")
	private WorkSystemServiceIf workSystemService;
	
	@Resource(name = "SystemDAO")
	private SystemDAO systemDAO;
	
	@Resource(name = "PkgSnetDao")
	private PkgSnetDao pkgSnetDao;
	
	@Resource(name = "SmsService")
	private SmsServiceIf smsService;
	
	@Override
	public PkgModel read(PkgModel pkgModel) throws Exception {
		PkgModel pkgModelData = null;
		if (StringUtils.hasLength(pkgModel.getPkg_seq())) {
//			Snet 연동- 보안 진행율 100% 시 보안검증 update
			VerifyTemModel verifyTemModel = new VerifyTemModel();
			verifyTemModel.setSeq_num(pkgModel.getPkg_seq());
			
			int audit_rate = 0;
			if("Y".equals(pkgModel.getSec_yn())){
				try{					
//					audit_rate = pkgSnetDao.getAudit_Rate(verifyTemModel);
				}catch(Exception e){
					throw new Exception("error.biz.Smart guard 연동 중 에러가 발생하였습니다. 관리자에게 연락바랍니다.");
				}					
				if(audit_rate == 100){
					PkgModel pkgModel_security = new PkgModel();
					pkgModel_security.setPkg_seq(pkgModel.getPkg_seq());
					pkgDAO.security_update(pkgModel_security);
				}
			}
			pkgModelData = this.read4Master(pkgModel);

			// 버전구분 기본값 Set
			//pkgModelData.setVer_gubun(pkgModel.getVer_gubun());

			// 보완적용내역 조회
			pkgDetailVariableService.read(pkgModelData);

			// 담당자
			this.readUser(pkgModelData, true, false);
			
			// 권한(수정) 세팅
			pkgModelData.setSession_user_id(pkgModel.getSession_user_id());
			this.setGranted(pkgModelData);
			
			if (pkgModelData.getMaster_file_id() != null && !"".equals(pkgModelData.getMaster_file_id())) {
				// 첨부 파일 정보 세팅
				fileManageService.read(pkgModelData);
			}

		} else {
			pkgModelData = new PkgModel();
			pkgModelData.setSession_user_id(pkgModel.getSession_user_id());
			pkgModelData.setSession_user_type(pkgModel.getSession_user_type());
			
			// 담당자
			this.readUser(pkgModelData, true, true);
			
			// 권한(수정) 세팅
			this.setGranted(pkgModelData);
			
			// 등록 기본 정보 조회(tpl_seq)
//			pkgModelData = this.base_read(pkgModel);
//
//			if (pkgModelData == null || "".equals(pkgModelData.getTpl_seq())) {
//				throw new Exception("error.biz.사용가능한 템플릿이 없습니다. 관리자에게 문의하세요.");
//			}
		}

		return pkgModelData;
	}

	/**
	 * PKG 상세에서 필요한 권한 세팅
	 * @param pkgModel
	 * @throws Exception
	 */
	private void setGranted(PkgModel pkgModel) throws Exception {
		Map<String, String> grantedMap = new HashMap<String, String>();
		
		List<String> authList = sessionService.readAuth();
		
		//Spring Security 권한 체크 
		for(String auth : authList){
			
			// 관리자 권한인 경우
			if("ROLE_ADMIN".equals(auth)) {
				grantedMap.put("ADMIN", "ADMIN");
				pkgModel.setGranted("ADMIN");
				
			// BP 권한인 경우: 무조건 BP
			} else if("ROLE_BP".equals(auth)) {
				List<SystemUserModel> systemUserModelList = pkgModel.getSystemUserModelList();
				
				for(SystemUserModel systemUserModel : systemUserModelList) {
					if(SYSTEM_USER_CHARGE_GUBUN.BU.equals(systemUserModel.getCharge_gubun())) {
						if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
							pkgModel.setGranted("BP");
							
							sessionService.create("PKG_SYSTEM_BY_GRANTED", new SYSTEM_USER_CHARGE_GUBUN[]{SYSTEM_USER_CHARGE_GUBUN.BU});
							return;
						}
					}
				}
				
				//BP이면서 해당 담당이 아니면 조회 권한 자체가 없다.
				pkgModel.setGranted("NONE");
				return;
			}
		}
		
		List<SystemUserModel> systemUserModelList = pkgModel.getSystemUserModelList();
		
		for(SystemUserModel systemUserModel : systemUserModelList) {
			
			//승인자인 경우: 무조건 승인
			if(SYSTEM_USER_CHARGE_GUBUN.AU.equals(systemUserModel.getCharge_gubun())) {
				if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
					grantedMap.put("APPROVER", "APPROVER");
					pkgModel.setGranted("APPROVER");
					
					sessionService.create("PKG_SYSTEM_BY_GRANTED", new SYSTEM_USER_CHARGE_GUBUN[]{SYSTEM_USER_CHARGE_GUBUN.AU, SYSTEM_USER_CHARGE_GUBUN.VU});
					return;
				}
			//승인자/관리자가 아니면서 검증인 경우: 검증
			} else if(SYSTEM_USER_CHARGE_GUBUN.DA.equals(systemUserModel.getCharge_gubun())) {
				if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
					grantedMap.put("APPROVER", "APPROVER");
					pkgModel.setGranted("APPROVER");
					
					sessionService.create("PKG_SYSTEM_BY_GRANTED", new SYSTEM_USER_CHARGE_GUBUN[]{SYSTEM_USER_CHARGE_GUBUN.DA, SYSTEM_USER_CHARGE_GUBUN.DV});
					return;
				}
				
			//승인자/관리자가 아니면서 검증인 경우: 검증
			}else if(SYSTEM_USER_CHARGE_GUBUN.VU.equals(systemUserModel.getCharge_gubun())) {
				if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
					if(!grantedMap.containsKey("APPROVER") && !grantedMap.containsKey("ADMIN")) {
						grantedMap.put("MANAGER", "MANAGER");
						pkgModel.setGranted("MANAGER");
					}
				}
			
			//승인자/관리자/검증자가 아닌 경우: 운용
			}else if(SYSTEM_USER_CHARGE_GUBUN.DV.equals(systemUserModel.getCharge_gubun())) {
				if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
					if(!grantedMap.containsKey("APPROVER") && !grantedMap.containsKey("ADMIN")) {
						grantedMap.put("MANAGER", "MANAGER");
						pkgModel.setGranted("MANAGER");
					}
				}
			
			//승인자/관리자/검증자가 아닌 경우: 운용
			}else if(SYSTEM_USER_CHARGE_GUBUN.PU.equals(systemUserModel.getCharge_gubun())) {
				if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
					if(!grantedMap.containsKey("APPROVER") && !grantedMap.containsKey("ADMIN") && !grantedMap.containsKey("MANAGER")) {
						grantedMap.put("OPERATOR", "OPERATOR");
						pkgModel.setGranted("OPERATOR");
					}
				}
			}//승인자/관리자/검증자가 아닌 경우: 운용
			else if(SYSTEM_USER_CHARGE_GUBUN.VO.equals(systemUserModel.getCharge_gubun())) {
				if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
					if(!grantedMap.containsKey("APPROVER") && !grantedMap.containsKey("ADMIN") && !grantedMap.containsKey("MANAGER")) {
						grantedMap.put("OPERATOR", "OPERATOR");
						pkgModel.setGranted("OPERATOR");
					}
				}
			}//승인자/관리자/검증자가 아닌 경우: 운용
			else if(SYSTEM_USER_CHARGE_GUBUN.SE.equals(systemUserModel.getCharge_gubun())) {
				if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
					if(!grantedMap.containsKey("APPROVER") && !grantedMap.containsKey("ADMIN") && !grantedMap.containsKey("MANAGER")) {
						grantedMap.put("OPERATOR", "OPERATOR");
						pkgModel.setGranted("OPERATOR");
					}
				}
			}//승인자/관리자/검증자가 아닌 경우: 운용
			else if(SYSTEM_USER_CHARGE_GUBUN.CH.equals(systemUserModel.getCharge_gubun())) {
				if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
					if(!grantedMap.containsKey("APPROVER") && !grantedMap.containsKey("ADMIN") && !grantedMap.containsKey("MANAGER")) {
						grantedMap.put("OPERATOR", "OPERATOR");
						pkgModel.setGranted("OPERATOR");
					}
				}
			}//승인자/관리자/검증자가 아닌 경우: 운용
			else if(SYSTEM_USER_CHARGE_GUBUN.NO.equals(systemUserModel.getCharge_gubun())) {
				if(pkgModel.getSession_user_id().equals(systemUserModel.getUser_id())) {
					if(!grantedMap.containsKey("APPROVER") && !grantedMap.containsKey("ADMIN") && !grantedMap.containsKey("MANAGER")) {
						grantedMap.put("OPERATOR", "OPERATOR");
						pkgModel.setGranted("OPERATOR");
					}
				}
			}
		}
		
		//나머지는 무조건: 운용
		if(grantedMap.size() <= 0) {
			pkgModel.setGranted("OPERATOR");
		}
		
		if("ADMIN".equals(pkgModel.getGranted())) {
			sessionService.create("PKG_SYSTEM_BY_GRANTED", new SYSTEM_USER_CHARGE_GUBUN[]{SYSTEM_USER_CHARGE_GUBUN.AU, SYSTEM_USER_CHARGE_GUBUN.VU,SYSTEM_USER_CHARGE_GUBUN.DV,SYSTEM_USER_CHARGE_GUBUN.DA});
		} else if("MANAGER".equals(pkgModel.getGranted())) {
			sessionService.create("PKG_SYSTEM_BY_GRANTED", new SYSTEM_USER_CHARGE_GUBUN[]{SYSTEM_USER_CHARGE_GUBUN.VU,SYSTEM_USER_CHARGE_GUBUN.DV});
		} else if("OPERATOR".equals(pkgModel.getGranted())) {
			sessionService.create("PKG_SYSTEM_BY_GRANTED", new SYSTEM_USER_CHARGE_GUBUN[]{});
		}
	}
	
	@Override
	public PkgModel read4Master(PkgModel pkgModel) throws Exception {
		PkgStatusModel pkgStatusModel = new PkgStatusModel();
		pkgStatusModel.setSession_user_id(pkgModel.getSession_user_id());
		
		pkgModel = pkgDAO.read(pkgModel);
		
		if (pkgModel == null) {
			throw new Exception("error.biz.조회 내용이 없습니다. 관리자에게 문의 하세요.");
		}

		//과금 상태
		this.setPeStatus(pkgModel);
		
		//TAB2 입력화면 통합
		
		pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
		pkgStatusModel.setStatus("3");
		
		PkgStatusModel pkgStatusModelData = pkgStatusDAO.read(pkgStatusModel);
		
		if(pkgStatusModelData != null){
			pkgModel.setCol1(pkgStatusModelData.getCol1());
			pkgModel.setCol2(pkgStatusModelData.getCol2());
			pkgModel.setCol3(pkgStatusModelData.getCol3());
			pkgModel.setCol4(pkgStatusModelData.getCol4());
			pkgModel.setCol5(pkgStatusModelData.getCol5());
			pkgModel.setCol6(pkgStatusModelData.getCol6());
			pkgModel.setCol7(pkgStatusModelData.getCol7());
			pkgModel.setCol8(pkgStatusModelData.getCol8());
			pkgModel.setCol9(pkgStatusModelData.getCol9());
			pkgModel.setCol10(pkgStatusModelData.getCol10());
			pkgModel.setCol11(pkgStatusModelData.getCol11());
			pkgModel.setCol12(pkgStatusModelData.getCol12());
			pkgModel.setCol13(pkgStatusModelData.getCol13());
			pkgModel.setCol14(pkgStatusModelData.getCol14());
			pkgModel.setCol15(pkgStatusModelData.getCol15());
			pkgModel.setCol16(pkgStatusModelData.getCol16());
			pkgModel.setCol17(pkgStatusModelData.getCol17());
			pkgModel.setCol18(pkgStatusModelData.getCol18());
			pkgModel.setCol19(pkgStatusModelData.getCol19());
			pkgModel.setCol20(pkgStatusModelData.getCol20());
			pkgModel.setCol21(pkgStatusModelData.getCol21());
			pkgModel.setCol22(pkgStatusModelData.getCol22());
			pkgModel.setCol23(pkgStatusModelData.getCol23());
			pkgModel.setCol24(pkgStatusModelData.getCol24());
			pkgModel.setCol25(pkgStatusModelData.getCol25());
			pkgModel.setCol26(pkgStatusModelData.getCol26());
			pkgModel.setCol27(pkgStatusModelData.getCol27());
			pkgModel.setCol28(pkgStatusModelData.getCol28());
			pkgModel.setCol29(pkgStatusModelData.getCol29());
			pkgModel.setCol30(pkgStatusModelData.getCol30());
			pkgModel.setCol31(pkgStatusModelData.getCol31());
			pkgModel.setCol32(pkgStatusModelData.getCol32());
			pkgModel.setCol33(pkgStatusModelData.getCol33());
			pkgModel.setCol34(pkgStatusModelData.getCol34());
			pkgModel.setCol35(pkgStatusModelData.getCol35());
			pkgModel.setCol36(pkgStatusModelData.getCol36());
			pkgModel.setCol37(pkgStatusModelData.getCol37());
			pkgModel.setCol38(pkgStatusModelData.getCol38());
			pkgModel.setCol39(pkgStatusModelData.getCol39());
			pkgModel.setCol40(pkgStatusModelData.getCol40());
			pkgModel.setCol41(pkgStatusModelData.getCol41());
			pkgModel.setCol42(pkgStatusModelData.getCol42());
			pkgModel.setCol43(pkgStatusModelData.getCol43());
		} else {
			pkgStatusDAO.create(pkgStatusModel);
		}
		
		if(!"N".equals(pkgModel.getDev_yn())){
			pkgStatusModel.setStatus("22");
			
			pkgStatusModelData = pkgStatusDAO.read(pkgStatusModel);
			if(pkgStatusModelData == null){
				pkgStatusDAO.create(pkgStatusModel);
			}
		}
		//TAB2 end
		
		//RoadMap read
		SysRoadMapModel rmModel = new SysRoadMapModel();
		rmModel.setPkg_seq(pkgModel.getPkg_seq());
		List<SysRoadMapModel> prmList = systemDAO.pkgRoadMaps(rmModel);
		
		for(SysRoadMapModel prmModel : prmList){
			if("04".equals(prmModel.getCode())){
				pkgModel.setStart_date_04(prmModel.getStart_date());
				pkgModel.setEnd_date_04(prmModel.getEnd_date());
				pkgModel.setComment_04(prmModel.getContent());
				pkgModel.setPkg_road_map_seq_04(prmModel.getPkg_road_map_seq());
				pkgModel.setRoad_map_seq_04(prmModel.getRoad_map_seq());
			}else if("07".equals(prmModel.getCode())){
				pkgModel.setStart_date_07(prmModel.getStart_date());
				pkgModel.setEnd_date_07(prmModel.getEnd_date());
				pkgModel.setComment_07(prmModel.getContent());
				pkgModel.setPkg_road_map_seq_07(prmModel.getPkg_road_map_seq());
				pkgModel.setRoad_map_seq_07(prmModel.getRoad_map_seq());
			}else if("08".equals(prmModel.getCode())){
				pkgModel.setStart_date_08(prmModel.getStart_date());
				pkgModel.setEnd_date_08(prmModel.getEnd_date());
				pkgModel.setComment_08(prmModel.getContent());
				pkgModel.setPkg_road_map_seq_08(prmModel.getPkg_road_map_seq());
				pkgModel.setRoad_map_seq_08(prmModel.getRoad_map_seq());
			}
		}
		
		return pkgModel;
	}
	
	static Logger logger = Logger.getLogger(PkgService.class);
	
	@Override
	public void readUser(PkgModel pkgModel, boolean isWithEquipmentUser, boolean isAll) throws Exception {
		if(isAll) {
			List<SystemUserModel> allSystemUserModel = new ArrayList<SystemUserModel>();
			Map<SysModel, List<EquipmentUserModel>> allMap = new HashMap<SysModel, List<EquipmentUserModel>>();
			
			SystemUserModel systemUserModel = new SystemUserModel();
			systemUserModel.setUser_id(pkgModel.getSession_user_id());
			systemUserModel.setUser_gubun(pkgModel.getSession_user_type());

			@SuppressWarnings("unchecked")
			List<SysModel> systemList = (List<SysModel>) systemService.readListAppliedToUser(systemUserModel);
			
			for(SysModel sysModel : systemList) {
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				
				if(sysModel != null) {
					allSystemUserModel.addAll(sysModel.getSystemUserList());
					
					if(isWithEquipmentUser) {
						//운용(장비별) 담당자
						allMap.putAll(sysModel.getEquipmentUserMap());
					}
				}
			}
			
			//검증,승인,사업계획,개발,협력업체 담당자
			pkgModel.setSystemUserModelList(allSystemUserModel);
			
			if(isWithEquipmentUser) {
				//운용(장비별) 담당자
				pkgModel.setEquipmentUserModelMap(allMap);
			}
		} else {
			SysModel sysModel = new SysModel();
			sysModel.setSystem_seq(pkgModel.getSystem_seq());
			if("4".equals(pkgModel.getSelected_status()) || "5".equals(pkgModel.getSelected_status()) || "6".equals(pkgModel.getSelected_status())){
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.VU);
			}
			
			sysModel = systemService.readUsersAppliedToSystem(sysModel);
			
			if(sysModel != null) {
				//검증,승인,사업계획,개발,협력업체 담당자
				pkgModel.setSystemUserModelList(sysModel.getSystemUserList());
				
				if(isWithEquipmentUser) {
					//운용(장비별) 담당자
					pkgModel.setEquipmentUserModelMap(sysModel.getEquipmentUserMap());
				}
			}
		}
		
	}
	
	@Override
	public List<PkgModel> readList(PkgModel pkgModel) throws Exception {
		pkgModel.setTotalCount(pkgDAO.readTotalCount(pkgModel));
		
		List<PkgModel> resultList = (List<PkgModel>) pkgDAO.readList(pkgModel);
		
		for(PkgModel model : resultList) {
			this.setPeStatus(model);
		}

		return resultList;
	}

	/**
	 * 과금 상태 조회
	 * @param pkgModel
	 * @throws Exception
	 */
	private void setPeStatus(PkgModel pkgModel) throws Exception {
		//요청중 상태이고, 과금영향이 있는 경우
		if("Y".equals(pkgModel.getPe_yn())) {
			OwmsModel owmsModel = new OwmsModel();
			owmsModel.setSeq_no(pkgModel.getPkg_seq());
			owmsModel = owmsService.read(owmsModel);
			
			if(owmsModel != null) {
				pkgModel.setPe_status(owmsModel.getPe_status());
				if("1".equals(pkgModel.getStatus())) {
					pkgModel.setStatus_name(owmsModel.getPe_status_name());
				}
			}
		}
	}
	
	@Override
	public void readTotalCountTemplate(PkgModel pkgModel) throws Exception {
		pkgModel.setTotalCountTemplate(pkgDAO.readTotalCountTemplate(pkgModel));
	}

	@Override
	public List<PkgModel> readListHistory(PkgModel pkgModel) throws Exception {
		List<PkgModel> pkgList = pkgDAO.readListHistory(pkgModel);
		PkgEquipmentModel pkgEquipmentModel = null;
		for (PkgModel pkg : pkgList) {
			pkgEquipmentModel = new PkgEquipmentModel();
			if("Y".equals(pkgModel.getEq_history_yn())){
				pkgEquipmentModel.setEq_history_yn(pkgModel.getEq_history_yn());
			}
			pkgEquipmentModel.setPkg_seq(pkg.getPkg_seq());
			List<PkgEquipmentModel> pkgEquipmentList = pkgEquipmentService.readList(pkgEquipmentModel);
			pkg.setPkgEquipmentModelList(pkgEquipmentList);
		}
		return pkgList;
	}
	
	@Override
	public void update(PkgModel pkgModel) throws Exception {
		if("D".equals(pkgModel.getDev_yn_bak())){
			pkgModel.setDev_yn("D");
		}
		
		if("D".equals(pkgModel.getDev_yn()) && "0".equals(pkgModel.getStatus_dev())){
			pkgModel.setStatus_dev("1");
		}
		
		this.status_update(pkgModel);
		this.urgency_update(pkgModel);
		
		PkgModel rPkgModel = this.read(pkgModel);
		pkgModel.setStatus(rPkgModel.getStatus());
		
/*		
 * 		UI의 과금접수 클릭 시 OWMS 요청하는 페이지 링크로 변경	
		//요청중이고 과금영향이 있는 경우
		if("1".equals(rPkgModel.getStatus()) && "Y".equals(rPkgModel.getPe_yn())) {
			//과금 등록
			OwmsModel owmsModel = new OwmsModel();
			owmsModel.setReqno(rPkgModel.getPkg_seq());
			owmsModel.setApplicant_id(pkgModel.getSession_user_id());//검증접수 사번
			owmsModel.setReqtlt(rPkgModel.getTitle().length() > 100 ? rPkgModel.getTitle().substring(100) : rPkgModel.getTitle());
			owmsModel.setChange_sumary(rPkgModel.getPe_edit_title());
			owmsModel.setApplicant_sumary(rPkgModel.getPe_content());
			owmsModel.setEquipment_sumary(rPkgModel.getPe_equip_ip() + ":" + rPkgModel.getPe_equip_port());
			owmsModel.setTest_mobile_num(rPkgModel.getPe_no_1() + " | " + rPkgModel.getPe_no_2() + " | " + rPkgModel.getPe_no_3() + " | " + rPkgModel.getPe_no_4() + " | " + rPkgModel.getPe_no_5());
			owmsModel.setEmergencyflg(rPkgModel.getPe_gubun());
			if(rPkgModel.getPe_test_date() != null) {
				owmsModel.setTest_dt(rPkgModel.getPe_test_date().replaceAll("-", "."));
			}
			owmsService.create(owmsModel);
		}
*/
		
		this.readUser(pkgModel, true, false);
		
		pkgModel.setSelected_status("1");
		
		if(pkgModel.getSession_user_type().equals(USER_TYPE.B)){
			/**
			 * 메일 전송
			 */
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(PkgMailUtil.getTitle4Status(pkgModel));
			mailModel.setMsgText(PkgMailUtil.getContent4Status(pkgModel, null));
			mailModel.setTos(PkgMailUtil.getToAddress4Status(pkgModel));			
			mailModel.setFrom(pkgModel.getSession_user_email());
			/**
			 * 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy
			 */
			mailModel.setTosInfo(this.getTosInfo(pkgModel));
			
			mailService.create4Multi(mailModel);
			String[] phone=new String[pkgModel.getSystemUserModelList().size()];
			int phone_cnt=0;
			for(SystemUserModel userModel : pkgModel.getSystemUserModelList()){
				phone[phone_cnt] = userModel.getUser_phone();
				phone_cnt++;
			}
			
			String [] clean_phone = this.cleanStr(phone);

			for(String phone_number : clean_phone){
				this.sendSms(pkgModel, phone_number);
			}
		}
	}
	
	@Override
	public void status_update(PkgModel pkgModel) throws Exception {
		if("D".equals(pkgModel.getDev_yn())){
			if("21".equals(pkgModel.getSelected_status()) || "22".equals(pkgModel.getSelected_status()) || "23".equals(pkgModel.getSelected_status()) || "24".equals(pkgModel.getSelected_status())){
				pkgModel.setStatus_dev(pkgModel.getSelected_status());
				pkgModel.setSelected_status(pkgModel.getStatus());
			}
		}
		pkgDAO.status_update(pkgModel);
	}
	
	@Override
	public void urgency_update(PkgModel pkgModel) throws Exception {
		if("Y".equals(pkgModel.getUrgency_yn()) || "N".equals(pkgModel.getUrgency_yn())){			
			pkgDAO.urgency_update(pkgModel);
		}
	}
	
	@Override
	public void tpl_seq_update(PkgModel pkgModel) throws Exception {
		pkgDAO.tpl_seq_update(pkgModel);
	}
	
	@Override
	public void verify_update(PkgModel pkgModel) throws Exception {
		pkgDAO.verify_update(pkgModel);
	}

	@Override
	public void delete(PkgModel pkgModel) throws Exception {
		pkgDetailVariableService.delete(pkgModel);
		
		pkgDAO.delete(pkgModel);

		fileManageService.delete(pkgModel);
		
		WorkSystemModel WorkSysModel = new WorkSystemModel();
		
		WorkSysModel.setPkg_seq(pkgModel.getPkg_seq());
		List<WorkSystemModel> del_Seq_List = workSystemService.read_Seq_S(WorkSysModel);
		if(del_Seq_List != null && del_Seq_List.size() > 0){
			for(WorkSystemModel delModel : del_Seq_List){
				workSystemService.update_pkms_main_del(delModel);
				workSystemService.delete_Equipment(delModel);
			}
		}
		
		SysRoadMapModel srmModel = new SysRoadMapModel();
		srmModel.setSystem_seq(pkgModel.getSystem_seq());
		srmModel.setPkg_seq(pkgModel.getPkg_seq());
		List<SysRoadMapModel> seqList = systemDAO.readPkgRoadMapSeqList(srmModel);
		
		if((null != seqList) && (seqList.size() > 0)){
			for(SysRoadMapModel seqModel : seqList){
				systemDAO.deleteRoadMap(seqModel);
				systemDAO.pkgDeleteRoadMap(seqModel);
			}
		}
		
	}

	@Override
	public PkgModel setSearchCondition(PkgModel pkgModel) throws Exception {
		if(pkgModel.isSessionCondition()) {
			PkgModel sessionModel = (PkgModel) sessionService.read("PKG_READLIST_SESSION_KEY");
			if(sessionModel == null) {
				pkgModel = new PkgModel();
			} else {
				pkgModel = sessionModel;
			}
		} else {
			sessionService.create("PKG_READLIST_SESSION_KEY", pkgModel);
		}
		
		return pkgModel;
	}
 
	@Override
	public String excelDownload(PkgModel pkgModel) throws Exception {
		//데이터
		pkgModel.setPaging(false);
		List<?> readList =  this.readList(pkgModel);		
		 
		//Excel 헤더
		String[] headers = new String[]{"STATUS_NAME", "GROUP_DEPTH",  "VER", "TITLE",   "REG_USER_NAME","REG_DATE"};
		
		//Excel 데이터 추출
		@SuppressWarnings("unchecked")
		List<List<String>> excelDataList = ExcelUtil.extractExcelData((List<Object>) readList, headers);

		//파일 생성 후 다운로드할 파일명
		String downloadFileName = ExcelUtil.write(pkgModel.EXCEL_FILE_NAME, propertyService.getString("Globals.fileStorePath"), new String[]{ "상태","시스템","version","제목","작성자","요청일자"}, excelDataList);
		
		return downloadFileName;
	}
	
	@Override
	public PkgModel popupProgressRead(PkgModel pkgModel) throws Exception {
		pkgModel = pkgDAO.popupProgressRead(pkgModel);
		return pkgModel;
	}
	
	@Override
	public PkgModel popupProgressRead_Dev(PkgModel pkgModel) throws Exception {
		pkgModel = pkgDAO.popupProgressRead_Dev(pkgModel);
		return pkgModel;
	}
	
	
	/**메일발송시 받는사람의 여러가지 정보를 출력을 위한 세팅 추가 1017 -  ksy*/
	public String[] getTosInfo(PkgModel pkgModel) {
		List<Object> list = new ArrayList<Object>();
		
		//검증,승인,사업계획,개발,협력업체 담당자
		List<SystemUserModel> systemUserModelList = pkgModel.getSystemUserModelList();
		
		if(systemUserModelList != null) {
			list.addAll(systemUserModelList);
		}
		
		//운용(장비별) 담당자
		List<EquipmentUserModel> EquipmentUserModelList = null;
		Map<SysModel, List<EquipmentUserModel>> equipmentUserModelMap = pkgModel.getEquipmentUserModelMap();
		
		if(equipmentUserModelMap != null) {
			Collection<List<EquipmentUserModel>> collection = equipmentUserModelMap.values();
			Iterator<List<EquipmentUserModel>> it = collection.iterator();
			
			while(it.hasNext()) {
				EquipmentUserModelList = (List<EquipmentUserModel>) it.next();
				
				list.addAll(EquipmentUserModelList);
			}
		}
		
		//return
		String[] rets = new String[list.size()];
		int i = 0;
		
		for(Object object : list) {
			if(object instanceof SystemUserModel) {
				rets[i] = ((SystemUserModel)object).getUser_email()+"("+((SystemUserModel)object).getUser_name()+" "+((SystemUserModel)object).getSosok()+")";
			} else if(object instanceof EquipmentUserModel) {
				rets[i] = ((EquipmentUserModel)object).getUser_email()+"("+((EquipmentUserModel)object).getUser_name()+" "+((EquipmentUserModel)object).getSosok()+")";
			}
			i++;
		}
		return rets;
	} /***/
	
	@Override
	public List<PkgModel> printRead_Time(PkgModel pkgModel) throws Exception {
		return (List<PkgModel>) pkgDAO.printRead_Time(pkgModel);
	}
	
	@Override
	public List<PkgModel> printRead_EQ(PkgModel pkgModel) throws Exception {		
		return (List<PkgModel>) pkgDAO.printRead_EQ(pkgModel);
	}
	
	@Override
	public List<PkgModel> printRead_PnCr(PkgModel pkgModel) throws Exception {
		return (List<PkgModel>) pkgDAO.printRead_PnCr(pkgModel);
	}
	
	@Override
	public List<PkgModel> workCntList(PkgModel pkgModel) throws Exception {
		return (List<PkgModel>) pkgDAO.workCntList(pkgModel);
	}
	
	@Override
	public List<PkgModel> workPkgList(PkgModel pkgModel) throws Exception {
		return (List<PkgModel>) pkgDAO.workPkgList(pkgModel);
	}
	
	@Override
	public List<PkgModel> workLimitList(PkgModel pkgModel) throws Exception {
		return (List<PkgModel>) pkgDAO.workLimitList(pkgModel);
	}
	
	@Override
	public List<PkgModel> helloList(PkgModel pkgModel) throws Exception {
		return (List<PkgModel>) pkgDAO.helloList(pkgModel);
	}
	
	@Override
	public List<PkgModel> helloList2(PkgModel pkgModel) throws Exception {
		return (List<PkgModel>) pkgDAO.helloList2(pkgModel);
	}
	
	@Override
	public void workLimitSave(PkgModel pkgModel) throws Exception {
		pkgDAO.workLimitSave(pkgModel);
	}
	
	@Override
	public void sendSms(PkgModel pkgModel, String phone) throws Exception {
		String tel = phone;
		String tel1 = "";
		String tel2 = "";
		if(tel != null) {
			tel = tel.replaceAll("-", "");
			tel1 = tel.substring(0, 3);
			tel2 = tel.substring(3, tel.length());

			SmsModel smsModel = new SmsModel();
			smsModel.setLog_no("1");//의미없음
			smsModel.setMsg("[PKMS PKG 검증요청] " + pkgModel.getTitle());
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
				
				//Input 파라미터
				String CONSUMER_ID = propertyService.getString("SOA.Username"); //송신시스템 id(시스템 별 ID 부여)
				String RPLY_PHON_NUM = propertyService.getString("SOA.Sms.sendNum"); //송신자 전화번호
				String TITLE = "(PKMS PKG 검증요청) [" + pkgModel.getTitle() + "] 확인해 주시기 바랍니다."; //전송메시지
				String PHONE = tel1 + tel2; //수신자 전화번호
				
				String URL = ""; //수신 URL		 					
				String START_DT_HMS = ""; //예약발송 시작시간			
				String END_DT_HMS = "";	//예약발송 종료시간
				
				logger.debug("============================ sms_send_input_CONSUMER_ID ============================" + CONSUMER_ID);
				logger.debug("============================ sms_send_input_RPLY_PHON_NUM ==========================" + RPLY_PHON_NUM);
				logger.debug("============================ sms_send_input_TITLE ==================================" + TITLE);
				logger.debug("============================ sms_send_input_PHONE ==================================" + PHONE);
				
				//Output (uuid는 예약전송일 경우에만 들어온다.");
				StringHolder _return = new StringHolder();
				StringHolder uuid = new StringHolder();
				
				//웹서비스 호출. (send오퍼레이션)
				service.send(CONSUMER_ID, RPLY_PHON_NUM, TITLE, PHONE, URL, START_DT_HMS, END_DT_HMS, _return, uuid);
				
				logger.debug("============================ sms_send_return =======================================" + _return.value);
				logger.debug("============================ sms_send_return_uuid ==================================" + uuid.value);
			} catch (ServiceException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			smsService.create(smsModel);
		}

	}
	
	@Override
	public String [] cleanStr(String [] str) throws Exception {
		// 중복사용자 제거
		ArrayList<String> uniqEmailAl = new ArrayList<String>();
		for (String clean_str : str) { //받을 사람 수만큼
			if (uniqEmailAl.contains(clean_str)) continue; //리스트 안의 중복자 제거 contains
			uniqEmailAl.add(clean_str); //중보 제거된 값 리스트에 생성
		}
		str = (String[]) uniqEmailAl.toArray(new String[uniqEmailAl.size()]); //Array 값 -> String 으로 변환
		uniqEmailAl.clear();
		uniqEmailAl = null;
		
		return str;
	}

}

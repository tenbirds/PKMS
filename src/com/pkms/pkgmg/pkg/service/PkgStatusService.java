package com.pkms.pkgmg.pkg.service;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.client.Stub;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import skt.soa.notification.webservice.SMSSender;
import skt.soa.notification.webservice.SMSSenderServiceLocator;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.mail.model.MailModel;
import com.pkms.common.mail.service.MailServiceIf;
import com.pkms.common.sms.model.SmsModel;
import com.pkms.common.sms.service.SmsServiceIf;
import com.pkms.common.util.PkgMailUtil;
import com.pkms.common.util.PkgSmsUtil;
import com.pkms.common.workSystem.model.WorkSystemModel;
import com.pkms.common.workSystem.service.WorkSystemServiceIf;
import com.pkms.pkgmg.pkg.dao.PkgStatusDAO;
import com.pkms.pkgmg.pkg.dao.PkgTab2DAO;
import com.pkms.pkgmg.pkg.model.PkgDetailVariableModel;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.model.PkgStatusModel;
import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.model.EquipmentUserModel;
import com.pkms.sys.equipment.service.EquipmentServiceIf;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.sys.system.service.SystemServiceIf;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.SktUserServiceIf;
import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.WingsStringUtil;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.pkms.sys.system.dao.SystemDAO;
import com.pkms.sys.system.dao.SystemUserDAO;
import com.pkms.pkgmg.pkg.dao.PkgStatusVerifyDao;
import com.pkms.pkgmg.pkg.dao.PkgSnetDao;

/**
 * PKG 상태별 관련 Service<br/>
 * 
 * 
 * @author : 009
 * @Date : 2012. 5. 08.
 * 
 */
@Service("PkgStatusService")
public class PkgStatusService implements PkgStatusServiceIf {
	
	static Logger logger = Logger.getLogger(PkgStatusService.class);
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;

	@Resource(name = "PkgEquipmentService")
	private PkgEquipmentServiceIf pkgEquipmentService;
	
	@Resource(name = "EquipmentService")
	private EquipmentServiceIf equipmentService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "PkgUserService")
	private PkgUserServiceIf pkgUserService;
	
	@Resource(name = "PkgStatusDAO")
	private PkgStatusDAO pkgStatusDAO;

	@Resource(name = "MailService")
	private MailServiceIf mailService;
	
	@Resource(name = "SmsService")
	private SmsServiceIf smsService;
	
	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;
	
	@Resource(name = "WorkSystemService")
	private WorkSystemServiceIf workSystemService;
	
//	@Resource(name = "WorkSystemDAO")
//	private PkgStatusDAO workSystemDAO;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;
	
	@Resource(name = "PkgDetailVariableService")
	private PkgDetailVariableServiceIf pkgDetailVariableService;
	
	@Resource(name = "SystemDAO")
	private SystemDAO systemDAO;
	
	@Resource(name = "SystemUserDAO")
	private SystemUserDAO systemUserDAO;
	
	@Resource(name = "PkgTab2DAO")
	private PkgTab2DAO pkgTab2DAO;
	
	@Resource(name = "PkgStatusVerifyDao")
	private PkgStatusVerifyDao pkgStatusVerifyDao;
	
	@Resource(name = "PkgSnetDao")
	private PkgSnetDao pkgSnetDao;
	
	@Override
	public void create(PkgModel pkgModel) throws Exception {
		/*
		 *  PKG_STATUS에 CREATE 상태
		 *  검증접수 & 계획: 2
		 *  검증결과 : 26
		 *  검증완료: 3
		 *  초도일정수립: 4
		 *  초도적용완료: 5
		 *  확대일정수립: 6
		 *  확대승인요청: 7
		 *  확대승인완료: 8
		 *  확대적용완료: 9
		 *  결과등록: 10
		 */
		
		boolean isUpdateStatus = false;
		
		// PkgStatusModel Get
		PkgStatusModel pkgStatusModel = pkgModel.getPkgStatusModel();
		pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
		pkgStatusModel.setStatus(pkgModel.getSelected_status());
		pkgStatusModel.setSession_user_id(pkgModel.getSession_user_id());
		
		//반려
		if(pkgModel.isTurn_down()) {
			if("26".equals(pkgModel.getSelected_status())){
				PkgUserModel pkgUserModel = null;

				//기존 등록되어 있는 사용자 모두 제거
				pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("SU");
				pkgUserService.delete(pkgUserModel);
			}else{				
				if(!"2".equals(pkgModel.getSelected_status()) && !"3".equals(pkgModel.getSelected_status())
						&& !"6".equals(pkgModel.getSelected_status()) && !"7".equals(pkgModel.getSelected_status()) &&
						!"9".equals(pkgModel.getSelected_status()) && !"21".equals(pkgModel.getSelected_status()) &&
						!"24".equals(pkgModel.getSelected_status()) && !"25".equals(pkgModel.getSelected_status())) {
					throw new Exception("error.biz.반려/원복을 할 수 있는 상태가 아닙니다.\n이상이 있을 경우 관리자에게 문의하시기 바랍니다.");
				}
				
				//받는 사람: 운용자 미포함
				//순서 중요: setSelected_status 이전에 해야 함
				this.sendMailByStatus(pkgModel, pkgStatusModel, false);
				
				pkgModel.setSelected_status("99");
				isUpdateStatus = true;
				
				WorkSystemModel WorkSysModel = new WorkSystemModel();
				
				WorkSysModel.setPkg_seq(pkgModel.getPkg_seq());
				List<WorkSystemModel> del_Seq_List = workSystemService.read_Seq_S(WorkSysModel);
				if(del_Seq_List != null){
					for(WorkSystemModel delModel : del_Seq_List){
						workSystemService.update_pkms_main_del(delModel);
						workSystemService.delete_Equipment(delModel);
					}
				}
				
				//로드맵 연동
				this.pkgRoadMap(pkgModel);
			}
		//정상
		} else {
			if("21".equals(pkgModel.getSelected_status())) {//개발검증 접수
				isUpdateStatus = true;
				
				//받는 사람: 승인자 미포함
				this.sendMailByStatus_dev(pkgModel, pkgStatusModel, false);
			} else if("22".equals(pkgModel.getSelected_status())) {//개발검증 완료
				isUpdateStatus = true;
				
			}else if("23".equals(pkgModel.getSelected_status())) {//개발승인 요청
				PkgUserModel pkgUserModel = null;

				// 시스템 user read
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkgModel.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.DA);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				
				// create: 사업/개발/검증/협력업체
				List<SystemUserModel> sList = sysModel.getSystemUserList();
				String[] checkSeqs = pkgModel.getCheck_seqs();
				int ord = 1;
				for(int i = 0; i < checkSeqs.length; i++) {
					for(SystemUserModel model : sList) {
						if(SYSTEM_USER_CHARGE_GUBUN.DA.getCode().equals(model.getCharge_gubun().getCode())) {
							if(model.getUser_id().equals(checkSeqs[i])) {
								pkgUserModel = new PkgUserModel();
								pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
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
				isUpdateStatus = true;
				
				//1차 승인자에게 SMS
				pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("DA");
				pkgUserModel = pkgUserService.readActive(pkgUserModel);
				
				this.sendSms(pkgModel, pkgUserModel);

				//메일을 보내기 위해: 승인자 목록
				pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("DA");
				List<PkgUserModel> pkgUserModelList = pkgUserService.readList(pkgUserModel);
				pkgModel.setPkgUserModelList(pkgUserModelList);
				
				//받는 사람(개발 검증자 개발 승인자)
				this.sendMailByStatus_dev(pkgModel, pkgStatusModel, true);

			} else if("24".equals(pkgModel.getSelected_status())) {
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				
				//승인
				pkgUserModel.setOrd(pkgModel.getOrd());
				pkgUserModel.setCharge_gubun("DA");
				pkgUserModel.setAu_comment(pkgModel.getAu_comment());
				pkgUserService.update(pkgUserModel);
				
				//모든 승인자가 승인한 경우에만 상태 업데이트
				pkgUserModel = pkgUserService.readActive(pkgUserModel);
				String _final = pkgUserModel.getFin();
				String active_ord = pkgUserModel.getOrd();
				
				if("Y".equals(_final)) {//더이상 승인할 사람이 없다는 의미
					isUpdateStatus = true;
				} else {
					isUpdateStatus = false;
					
					//다음 승인자에게 SMS
					this.sendSms(pkgModel, pkgUserModel);
				}
				
				//메일을 보내기 위해: 승인자 목록
				pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("DA");
				pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
				pkgModel.setUser_active_status(active_ord);
				
				//받는 사람: 운용자 미포함
//				this.sendMailByStatus_dev(pkgModel, pkgStatusModel, true);

				//초도적용완료 시작 
			} else if("2".equals(pkgModel.getSelected_status())) { //상용 검증 접수
//				String[] col1 = pkgStatusModel.getCol1().split(",");
//				pkgStatusModel.setCol1(col1[0]);
//				pkgModel.setVerify_date_start(pkgStatusModel.getCol1());
//				pkgService.verify_update(pkgModel);
//				isUpdateStatus = true;
				if ("".equals(pkgModel.getMaster_file_id())) {
					fileManageService.create(pkgModel, "PKG_");
				} else {
					fileManageService.update(pkgModel, "PKG_");
				}

				pkgTab2DAO.update(pkgModel);
				
				PkgUserModel pkgUserModel = null;

				//기존 등록되어 있는 사용자 모두 제거
				pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("SU");
				pkgUserService.delete(pkgUserModel);
				
				// 시스템 user read
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkgModel.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				
				// create: 사업/개발/검증/협력업체
				List<SystemUserModel> sList = sysModel.getSystemUserList();
				String[] checkSeqs = pkgModel.getCheck_seqs();
				int ord = 1;
				for(int i = 0; i < checkSeqs.length; i++) {
					for(SystemUserModel model : sList) {
						if(SYSTEM_USER_CHARGE_GUBUN.AU.getCode().equals(model.getCharge_gubun().getCode())) {
							if(model.getUser_id().equals(checkSeqs[i])) {
								pkgUserModel = new PkgUserModel();
								pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
								pkgUserModel.setUser_id(model.getUser_id());
								pkgUserModel.setCharge_gubun("SU");
								pkgUserModel.setOrd(String.valueOf((ord++)));
								pkgUserModel.setUse_yn("Y");
								pkgUserService.create(pkgUserModel);
								break;
							}
						}
					}
				}
				isUpdateStatus = true;
				
				if("complete".equals(pkgStatusModel.getCol22())){					
					//1차 승인자에게 SMS
					pkgUserModel = new PkgUserModel();
					pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
					pkgUserModel.setCharge_gubun("SU");
					pkgUserModel = pkgUserService.readActive(pkgUserModel);
					
					this.sendSms(pkgModel, pkgUserModel);
				}
			}else if("25".equals(pkgModel.getSelected_status())) {//검증계획 요청
				
				
			}else if("26".equals(pkgModel.getSelected_status())) {//검증계획 승인
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				
				//승인
				pkgUserModel.setOrd(pkgModel.getOrd());
				pkgUserModel.setCharge_gubun("SU");
				pkgUserModel.setAu_comment(pkgModel.getAu_comment());
				pkgUserService.update(pkgUserModel);
				
				//모든 승인자가 승인한 경우에만 상태 업데이트
				pkgUserModel = pkgUserService.readActive(pkgUserModel);
				String _final = pkgUserModel.getFin();
				String active_ord = pkgUserModel.getOrd();
				
				if("Y".equals(_final)) {//더이상 승인할 사람이 없다는 의미
					isUpdateStatus = true;
					if("Z".equals(pkgModel.getOn_yn())){
						pkgModel.setOn_yn("Y");
					}
					
					if("Z".equals(pkgModel.getNon_yn())){
						pkgModel.setNon_yn("Y");
					}
					
					if("Z".equals(pkgModel.getCha_yn())){
						pkgModel.setCha_yn("Y");
					}
					
					if("Z".equals(pkgModel.getSec_yn())){
						pkgModel.setSec_yn("Y");
						
						VerifyTemModel verifyTemModel = new VerifyTemModel();
						verifyTemModel.setSeq_num(pkgModel.getPkg_seq());
						verifyTemModel.setPkg_nm(pkgModel.getTitle());
						verifyTemModel.setUser_id(pkgModel.getSession_user_id());
						verifyTemModel.setUser_nm(pkgModel.getSession_user_name());
						
//						pkgSnetDao.pkgStatusSecurity_create(verifyTemModel);
					}
					
					if("Z".equals(pkgModel.getVol_yn())){
						pkgModel.setVol_yn("Y");
					}

				} else {
					isUpdateStatus = false;
					
					//다음 승인자에게 SMS
					this.sendSms(pkgModel, pkgUserModel);
				}
				
				//메일을 보내기 위해: 승인자 목록
				pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("SU");
				pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
				pkgModel.setUser_active_status(active_ord);
				
				//받는 사람: 운용자 미포함
				this.sendMailByStatus(pkgModel, pkgStatusModel, false);
//				isUpdateStatus = true;
				
			}else if("3".equals(pkgModel.getSelected_status())) {//검증 완료
				//상용검증결과 결과 값 유효성 검사 시작	
				pkgModel.setPkg_detail_seq(""); //NEW/PN/CR이나 상세 클릭하면 pkg_detail_seq 값이 세팅되어 보완적용내역 조회시 pkg_detail_seq 값이 있으면 안되기 때문에 초기화
				List<List<PkgDetailVariableModel>> pkgDetailVariableModelList_check = new ArrayList<List<PkgDetailVariableModel>>(); //상세내역 리스트 생성자 선언
				pkgDetailVariableService.read(pkgModel); //엑셀 상세내역 불러오기
				pkgDetailVariableModelList_check = pkgModel.getPkgDetailVariableModelList(); //상세내역 가져와서 리스트에 담기
				
				for (int i = 0; i < pkgDetailVariableModelList_check.size(); i++) { //상용검증결과 유효성검사
//					System.out.println("☆★☆★☆★☆★☆★☆★"+pkgDetailVariableModelList_check.get(i).get(2).getContent()+"☆★☆★☆★☆★☆★☆★");
					//상용검증결과(OK/COK)가 아니면 중지
					if(!"OK".equals(pkgDetailVariableModelList_check.get(i).get(2).getContent()) && !"COK".equals(pkgDetailVariableModelList_check.get(i).get(2).getContent())){
						throw new Exception("error.biz.보완검증내역중 상세내역안의 상용검증결과에 OK/COK 중 하나를 입력해야 검증완료 진행이 가능합니다.");
					}
				}//상용검증결과 결과 값 유효성 검사 끝
				
				pkgModel.setVerify_date_start(pkgStatusModel.getCol1());
				pkgModel.setVerify_date_end(pkgStatusModel.getCol2());
				pkgService.verify_update(pkgModel);

//				//첨부파일
//				if ("".equals(pkgModel.getMaster_file_id())) {
//					fileManageService.create(pkgModel, "PKG_");
//				} else {
//					fileManageService.update(pkgModel, "PKG_");
//				}
				isUpdateStatus = true;
				
				//로드맵 연동
//				this.pkgRoadMap(pkgModel);
				
				//받는 사람: 운용자 포함
//				this.sendMailByStatus(pkgModel, pkgStatusModel, true);
				
			} else if("4".equals(pkgModel.getSelected_status())) {//초도일정수립
				//delete
				PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
				pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgEquipmentModel.setWork_gubun("S");
				pkgEquipmentService.delete(pkgEquipmentModel);
				
				/*
				 * 초도승인이 끝난 후 초도일정 수립 시
				 * 초도일정 수립과 초도승인 사이의 데이터값 삭제, 변경
				 * (PKG_MASTER table : status 값 변경 *현재 상태 값) - 변경 
				 * (PKG_STATUS table : 단계 진행 내용 추가 값(5,6단계의 로우 생성 지우기)) - 삭제
				 * ----PKG_USER table : 초도승인요청(status : 5) 시 자동삭제 후 생성으로 삭제, 변경 불필요)
				 */
				//System.out.println("☆★☆현재상태☆★☆ -> " + pkgModel.getStatus());
				if("6".equals(pkgModel.getStatus())){ 
					pkgService.status_update(pkgModel);
					
					pkgStatusModel.setStatus("5");
					pkgStatusDAO.delete(pkgStatusModel);
					
					pkgStatusModel.setStatus("6");
					pkgStatusDAO.delete(pkgStatusModel);
					
					pkgStatusModel.setStatus(pkgModel.getSelected_status());			
				}
				//create
				String[] checkSeqs = pkgModel.getCheck_seqs();
				String[] work_dates = pkgModel.getWork_dates();
				String[] start_times1 = pkgModel.getStart_times1();
				String[] start_times2 = pkgModel.getStart_times2();
				String[] end_times1 = pkgModel.getEnd_times1();
				String[] end_times2 = pkgModel.getEnd_times2();
				
				String[] cwork_dates = WingsStringUtil.getNotNullStringArray(work_dates);
				String[] cstart_times1 = WingsStringUtil.getNotNullStringArray(start_times1);
				String[] cstart_times2 = WingsStringUtil.getNotNullStringArray(start_times2);
				String[] cend_times1 = WingsStringUtil.getNotNullStringArray(end_times1);
				String[] cend_times2 = WingsStringUtil.getNotNullStringArray(end_times2);
				
				/*
				 * 초도수립 수정을 가능하게 수정할려면 eqWorkSeq 를 새로 만드는게 아니고
				 * 이전 등록건에 대해 조회해서 그걸 재사용하면 됨
				 */
				String eqWorkSeq = pkgEquipmentService.readEqWorkSeq(pkgEquipmentModel);
				
				for(int i = 0; i < checkSeqs.length; i++) {
					pkgEquipmentModel = new PkgEquipmentModel();
					pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
					pkgEquipmentModel.setWork_gubun("S");
					pkgEquipmentModel.setUse_yn("Y");
					pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
					pkgEquipmentModel.setEq_work_seq(eqWorkSeq);
					pkgEquipmentModel.setWork_date(cwork_dates[i]);
					pkgEquipmentModel.setStart_time1(cstart_times1[i]);
					pkgEquipmentModel.setStart_time2(cstart_times2[i]);
					pkgEquipmentModel.setEnd_time1(cend_times1[i]);
					pkgEquipmentModel.setEnd_time2(cend_times2[i]);
					pkgEquipmentModel.setAmpm("야간");
					pkgEquipmentService.create(pkgEquipmentModel);
				}
				isUpdateStatus = true;
				
				//메일을 보내기 위해: 일정수립한 장비목록
				this.setPkgSystem(pkgModel);
				
				PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
				pkgEModel.setPkg_seq(pkgModel.getPkg_seq());

				pkgEModel.setWork_gubun("S");
				List<PkgEquipmentModel> eModelList = pkgEquipmentService.readList(pkgEModel);
				pkgModel.setPkgEquipmentModelList(eModelList);
				
				//로드맵 연동
//				this.pkgRoadMap(pkgModel);
				
				//받는 사람: 운용자 포함
//				this.sendMailByStatus(pkgModel, pkgStatusModel, true);

			} else if("5".equals(pkgModel.getSelected_status())) {//초도승인요청
				PkgUserModel pkgUserModel = null;

				//기존 등록되어 있는 사용자 모두 제거
				pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("AU");
				pkgUserService.delete(pkgUserModel);
				
				// 시스템 user read
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkgModel.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				
				// create: 사업/개발/검증/협력업체
				List<SystemUserModel> sList = sysModel.getSystemUserList();
				String[] checkSeqs = pkgModel.getCheck_seqs();
				int ord = 1;
				for(int i = 0; i < checkSeqs.length; i++) {
					for(SystemUserModel model : sList) {
						if(SYSTEM_USER_CHARGE_GUBUN.AU.getCode().equals(model.getCharge_gubun().getCode())) {
							if(model.getUser_id().equals(checkSeqs[i])) {
								pkgUserModel = new PkgUserModel();
								pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
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
				isUpdateStatus = true;
				
				//1차 승인자에게 SMS
				pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("AU");
				pkgUserModel = pkgUserService.readActive(pkgUserModel);
				
				this.sendSms(pkgModel, pkgUserModel);

				//메일을 보내기 위해: 승인자 목록
//				pkgUserModel = new PkgUserModel();
//				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
//				pkgUserModel.setCharge_gubun("AU");
//				List<PkgUserModel> pkgUserModelList = pkgUserService.readList(pkgUserModel);
//				pkgModel.setPkgUserModelList(pkgUserModelList);
				
				//받는 사람: 운용자 미포함
//				this.sendMailByStatus(pkgModel, pkgStatusModel, false);

			} else if("6".equals(pkgModel.getSelected_status())) {//초도승인 경우
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				
				//승인
				pkgUserModel.setOrd(pkgModel.getOrd());
				pkgUserModel.setCharge_gubun("AU");
				pkgUserModel.setAu_comment(pkgModel.getAu_comment());
				pkgUserService.update(pkgUserModel);
				
				//모든 승인자가 승인한 경우에만 상태 업데이트
				pkgUserModel = pkgUserService.readActive(pkgUserModel);
				String _final = pkgUserModel.getFin();
				String active_ord = pkgUserModel.getOrd();
				
				if("Y".equals(_final)) {//더이상 승인할 사람이 없다는 의미
					isUpdateStatus = true;
					
					PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
					pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
					pkgEquipmentModel.setWork_gubun("S");
					List<PkgEquipmentModel> pkgEquipmentModelList = pkgEquipmentService.readList(pkgEquipmentModel);
					
					try{
						this.sendWork4Result(pkgModel, pkgStatusModel, pkgEquipmentModelList, "S");
					}catch(Exception e){
						throw new Exception("error.biz.iwcs연동중 에러가 발생하였습니다. 관리자에게 연락바랍니다.", e);
					}
				} else {
					isUpdateStatus = false;
					
					//다음 승인자에게 SMS
					this.sendSms(pkgModel, pkgUserModel);
				}
				
				//메일을 보내기 위해: 승인자 목록
				pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("AU");
				pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
				pkgModel.setUser_active_status(active_ord);
				
				//받는 사람: 운용자 미포함
				this.sendMailByStatus(pkgModel, pkgStatusModel, false);

				//초도적용완료 시작 
			} else if("7".equals(pkgModel.getSelected_status())) {//초도적용완료(결과등록)
				//첨부파일
				if ("".equals(pkgModel.getMaster_file_id())) {
					fileManageService.create(pkgModel, "PKG_");
				} else {
					fileManageService.update(pkgModel, "PKG_");
				}
				
				if("Y".equals(pkgStatusModel.getUpdate_gubun())){					
					isUpdateStatus = true;
				}
				
				//상용검증결과 결과 값 유효성 검사 시작 ----121121 jun----				
				pkgModel.setPkg_detail_seq(""); //NEW/PN/CR이나 상세 클릭하면 pkg_detail_seq 값이 세팅되어 보완적용내역 조회시 pkg_detail_seq 값이 있으면 안되기 때문에 초기화
				List<List<PkgDetailVariableModel>> pkgDetailVariableModelList_check = new ArrayList<List<PkgDetailVariableModel>>(); //상세내역 리스트 생성자 선언
				pkgDetailVariableService.read(pkgModel); //엑셀 상세내역 불러오기
				pkgDetailVariableModelList_check = pkgModel.getPkgDetailVariableModelList(); //상세내역 가져와서 리스트에 담기
				
				for (int i = 0; i < pkgDetailVariableModelList_check.size(); i++) { //상용검증결과 유효성검사
//					System.out.println("☆★☆★☆★☆★☆★☆★"+pkgDetailVariableModelList_check.get(i).get(2).getContent()+"☆★☆★☆★☆★☆★☆★");
					//상용검증결과(OK/COK)가 아니면 중지
					if(!"OK".equals(pkgDetailVariableModelList_check.get(i).get(2).getContent()) && !"COK".equals(pkgDetailVariableModelList_check.get(i).get(2).getContent())){
						throw new Exception("error.biz.보완적용내역중 상세내역안의 상용검증결과에 OK/COK 중 하나를 입력해야 검증완료 진행이 가능합니다.");
					}
				}//상용검증결과 결과 값 유효성 검사 끝

				
				//받는 사람: 운용자 포함
				this.sendMailByStatus(pkgModel, pkgStatusModel, true);

			} else if("8".equals(pkgModel.getSelected_status())) {//확대일정수립
//				관리권한자: ADMIN
//				승인자: APPROVER
//				검증자: MANAGER
//				운용/사업/개발: OPERATOR
				
				//delete
				PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();

				//선언
				String[] all_checkSeqs = pkgModel.getAll_check_seqs(); //모든 장비
				String[] checkSeqs = pkgModel.getCheck_seqs(); //선택
				String[] noncheckSeq = pkgModel.getNon_check_seqs(); //선택 안된 시퀀스
				String[] work_dates = pkgModel.getWork_dates();
				String[] start_times1 = pkgModel.getStart_times1();
				String[] start_times2 = pkgModel.getStart_times2();
				String[] end_times1 = pkgModel.getEnd_times1();
				String[] end_times2 = pkgModel.getEnd_times2();
				String[] ampmss = pkgModel.getAmpms();
//				String[] work_result = pkgModel.getWork_result();
				
				String[] cwork_dates = WingsStringUtil.getNotNullStringArray(work_dates);
				String[] cstart_times1 = WingsStringUtil.getNotNullStringArray(start_times1);
				String[] cstart_times2 = WingsStringUtil.getNotNullStringArray(start_times2);
				String[] cend_times1 = WingsStringUtil.getNotNullStringArray(end_times1);
				String[] cend_times2 = WingsStringUtil.getNotNullStringArray(end_times2);
				String[] ampms = WingsStringUtil.getNotNullStringArray(ampmss);
				
				if("C".equals(pkgModel.getStatus_operation())) { //최초 생성
					/*
					 * 확대수립 수정을 가능하게 수정할려면 eqWorkSeq 를 새로 만드는게 아니고
					 * 이전 등록건에 대해 조회해서 그걸 재사용하면 됨
					 */
					String eqWorkSeq = pkgEquipmentService.readEqWorkSeq(pkgEquipmentModel);

					for(int j = 0; j < all_checkSeqs.length; j++){
						for(int i = 0; i < checkSeqs.length; i++) {
		//					System.out.println("==========work_result[i]의 값 = "+work_result[i]+"==============");
//							System.out.println("☆☆checkseqs☆☆☆"+checkSeqs[i]);
							if(all_checkSeqs[j].equals(checkSeqs[i])){
								pkgEquipmentModel = new PkgEquipmentModel();
								pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
								pkgEquipmentModel.setWork_gubun("E");
								pkgEquipmentModel.setUse_yn("Y");
								pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
								pkgEquipmentModel.setEq_work_seq(eqWorkSeq);
								pkgEquipmentModel.setWork_date(cwork_dates[i]);
								pkgEquipmentModel.setStart_time1(cstart_times1[i]);
								pkgEquipmentModel.setStart_time2(cstart_times2[i]);
								pkgEquipmentModel.setEnd_time1(cend_times1[i]);
								pkgEquipmentModel.setEnd_time2(cend_times2[i]);
								pkgEquipmentModel.setWork_result(null);
								if(ampms[j] != null && !ampms[j].equals("")){
									pkgEquipmentModel.setAmpm(ampms[j]);
								}else{
									pkgEquipmentModel.setAmpm("야간");
								}
								
								pkgEquipmentService.create(pkgEquipmentModel);
							}
						}
					}
				}else{ //수정
					String eqWorkSeq = pkgEquipmentService.readEqWorkSeq(pkgEquipmentModel);
					
					PkgEquipmentModel pkgEqModel = new PkgEquipmentModel();
					pkgEqModel.setPkg_seq(pkgModel.getPkg_seq());
					
					pkgEqModel.setWork_gubun("E");
					List<PkgEquipmentModel> pkgEqModelList = pkgEquipmentService.readList(pkgEqModel);					
					int j = 0;
					for(String all_check_seq : all_checkSeqs){ // j    //모든 장비
						int i = 0;
						for(String check_seq : checkSeqs){ // i        //추가 혹은 수정되어야 할 장비
							if(all_check_seq.equals(check_seq)){ //체크된 장비
								int del_cnt = 0;
								int no_del_cnt = 0;
								for(PkgEquipmentModel peqModel : pkgEqModelList){
									if(peqModel.getEquipment_seq().equals(check_seq)){ //기존에 있는 장비(수정/미수정)
//										System.out.println("ㅁㅁ기존에 있는 장비 check_seq === "+check_seq);
										no_del_cnt++;
										if(!peqModel.getWork_date().equals(cwork_dates[i]) || !peqModel.getStart_time1().equals(cstart_times1[i]) ||
												!peqModel.getStart_time2().equals(cstart_times2[i]) || !peqModel.getEnd_time1().equals(cend_times1[i]) ||
												!peqModel.getEnd_time2().equals(cend_times2[i]) || !peqModel.getAmpm().equals(ampms[j])){ //날짜 시간 하나라도 틀리면 삭제
											del_cnt++;
//											System.out.println("ㅁㅁ기존에 있는 장비 data틀린 장비 check_seq === "+check_seq);
										}
									}
								}
//								System.out.println("del_cnt == " + del_cnt);
//								System.out.println("no_del_cnt == " + no_del_cnt);
								if(no_del_cnt > 0){ //기존에 장비가 있고
									if(del_cnt > 0){ //data가 변경이 되었다면 삭제 후 추가
//										System.out.println("ㅁㅁ기존에 장비가 있고 data가 변경되었다 삭제한다.");
//										System.out.println("삭제할 장비는 " + check_seq);
										pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
										pkgEquipmentModel.setEquipment_seq(check_seq);
										pkgEquipmentModel.setWork_gubun("E");
										pkgEquipmentModel.setWork_result(null);
										pkgEquipmentService.delete(pkgEquipmentModel);
										
										pkgEquipmentModel = new PkgEquipmentModel();
										pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
										pkgEquipmentModel.setWork_gubun("E");
										pkgEquipmentModel.setUse_yn("Y");
										pkgEquipmentModel.setEquipment_seq(check_seq);
										pkgEquipmentModel.setEq_work_seq(eqWorkSeq);
										pkgEquipmentModel.setWork_date(cwork_dates[i]);
										pkgEquipmentModel.setStart_time1(cstart_times1[i]);
										pkgEquipmentModel.setStart_time2(cstart_times2[i]);
										pkgEquipmentModel.setEnd_time1(cend_times1[i]);
										pkgEquipmentModel.setEnd_time2(cend_times2[i]);
										pkgEquipmentModel.setWork_result(null);
										if(ampms[j] != null && !ampms[j].equals("")){
											pkgEquipmentModel.setAmpm(ampms[j]);
										}else{
											pkgEquipmentModel.setAmpm("야간");
										}
										
										pkgEquipmentService.create(pkgEquipmentModel);
									}
								}else{ //기존에 장비가 없으면 추가
//									System.out.println("기존에 장비가 없어서 추가한다");
//									System.out.println("추가할 장비는 " + check_seq);
									pkgEquipmentModel = new PkgEquipmentModel();
									pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
									pkgEquipmentModel.setWork_gubun("E");
									pkgEquipmentModel.setUse_yn("Y");
									pkgEquipmentModel.setEquipment_seq(check_seq);
									pkgEquipmentModel.setEq_work_seq(eqWorkSeq);
									pkgEquipmentModel.setWork_date(cwork_dates[i]);
									pkgEquipmentModel.setStart_time1(cstart_times1[i]);
									pkgEquipmentModel.setStart_time2(cstart_times2[i]);
									pkgEquipmentModel.setEnd_time1(cend_times1[i]);
									pkgEquipmentModel.setEnd_time2(cend_times2[i]);
									pkgEquipmentModel.setWork_result(null);
									if(ampms[j] != null && !ampms[j].equals("")){
										pkgEquipmentModel.setAmpm(ampms[j]);
									}else{
										pkgEquipmentModel.setAmpm("야간");
									}
									
									pkgEquipmentService.create(pkgEquipmentModel);
								}
							}
							i++;
						}
						
						//기존에 있으나 선택 안된 장비 삭제
						for(String non_check_seq : noncheckSeq){
							for(PkgEquipmentModel peqModel : pkgEqModelList){
								if(peqModel.getEquipment_seq().equals(non_check_seq)){
									pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
									pkgEquipmentModel.setEquipment_seq(non_check_seq);
									pkgEquipmentModel.setWork_gubun("E");
									pkgEquipmentModel.setWork_result(null);
									pkgEquipmentService.delete(pkgEquipmentModel);
								}
							}
						}
						j++;
					}
				}

				isUpdateStatus = true;
				
				//메일을 보내기 위해: 일정수립한 장비목록
				this.setPkgSystem(pkgModel);
				
				PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
				pkgEModel.setPkg_seq(pkgModel.getPkg_seq());

				pkgEModel.setWork_gubun("E");
				List<PkgEquipmentModel> pkgEquipmentModelList = pkgEquipmentService.readList(pkgEModel);
				pkgModel.setPkgEquipmentModelList(pkgEquipmentModelList);
				
				//확대 수립 작업시스템 등록
//				this.sendWork4Apply(pkgModel, DateUtil.format(), pkgEquipmentModelList, "E");
				
				try{
					//확대-작업 시스템 연동
					this.sendWork4Result(pkgModel, pkgStatusModel, pkgEquipmentModelList, "E");
				}catch(Exception e){
					throw new Exception("error.biz.iwcs연동중 에러가 발생하였습니다. 관리자에게 연락바랍니다.");
				}
				
				//로드맵 연동
//				this.pkgRoadMap(pkgModel);
				
				//받는 사람: 운용자 포함
//				this.sendMailByStatus(pkgModel, pkgStatusModel, true);

			} else if("9".equals(pkgModel.getSelected_status())) {//확대적용완료(결과등록)
				
				
				//상용검증결과 결과 값 유효성 검사 시작 ----121121 jun----				
				pkgModel.setPkg_detail_seq(""); //NEW/PN/CR이나 상세 클릭하면 pkg_detail_seq 값이 세팅되어 보완적용내역 조회시 pkg_detail_seq 값이 있으면 안되기 때문에 초기화
				List<List<PkgDetailVariableModel>> pkgDetailVariableModelList_check = new ArrayList<List<PkgDetailVariableModel>>(); //상세내역 리스트 생성자 선언
				pkgDetailVariableService.read(pkgModel); //엑셀 상세내역 불러오기
				pkgDetailVariableModelList_check = pkgModel.getPkgDetailVariableModelList(); //상세내역 가져와서 리스트에 담기
				
				for (int i = 0; i < pkgDetailVariableModelList_check.size(); i++) { //상용검증결과 유효성검사
//					System.out.println("☆★☆★☆★☆★☆★☆★"+pkgDetailVariableModelList_check.get(i).get(2).getContent()+"☆★☆★☆★☆★☆★☆★");
					//상용검증결과(OK/COK)가 아니면 중지
					if(!"OK".equals(pkgDetailVariableModelList_check.get(i).get(2).getContent()) && !"COK".equals(pkgDetailVariableModelList_check.get(i).get(2).getContent())){
						throw new Exception("error.biz.보완적용내역중 상세내역안의 상용검증결과에 OK/COK 중 하나를 입력해야 검증완료 진행이 가능합니다.");
					}
				}//상용검증결과 결과 값 유효성 검사 끝
				
				//첨부파일
				if ("".equals(pkgModel.getMaster_file_id())) {
					fileManageService.create(pkgModel, "PKG_");
				} else {
					fileManageService.update(pkgModel, "PKG_");
				}
				
				
				PkgEquipmentModel pkgEqModel = new PkgEquipmentModel();
				pkgEqModel.setPkg_seq(pkgModel.getPkg_seq());
				
				pkgEqModel.setWork_gubun("E");
				//이전 data
				List<PkgEquipmentModel> pkgEqModelList = pkgEquipmentService.readList(pkgEqModel);
				
				//delete
				PkgEquipmentModel pkgEquipmentModel = new PkgEquipmentModel();
				pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgEquipmentModel.setWork_gubun("E");
				
				//create
				String[] checkSeqs = pkgModel.getCheck_seqs();
				String[] work_dates = pkgModel.getWork_dates();
				String[] start_times1 = pkgModel.getStart_times1();
				String[] start_times2 = pkgModel.getStart_times2();
				String[] end_times1 = pkgModel.getEnd_times1();
				String[] end_times2 = pkgModel.getEnd_times2();
				String[] work_result = pkgModel.getWork_result();
				String[] ampmss = pkgModel.getAmpms();
				String[] charge_result = pkgModel.getCharge_result();
				
				String[] cwork_dates = WingsStringUtil.getNotNullStringArray(work_dates);
				String[] cstart_times1 = WingsStringUtil.getNotNullStringArray(start_times1);
				String[] cstart_times2 = WingsStringUtil.getNotNullStringArray(start_times2);
				String[] cend_times1 = WingsStringUtil.getNotNullStringArray(end_times1);
				String[] cend_times2 = WingsStringUtil.getNotNullStringArray(end_times2);
				String[] ampms = WingsStringUtil.getNotNullStringArray(ampmss);
				/*
				 * 확대수립 수정을 가능하게 수정할려면 eqWorkSeq 를 새로 만드는게 아니고
				 * 이전 등록건에 대해 조회해서 그걸 재사용하면 됨
				 */
				String eqWorkSeq = pkgEquipmentService.readEqWorkSeq(pkgEquipmentModel);

				for(int i = 0; i < checkSeqs.length; i++) {
					for(PkgEquipmentModel peqModel : pkgEqModelList){
						if(peqModel.getEquipment_seq().equals(checkSeqs[i])){
							if(!work_result[i].equals(peqModel.getWork_result()) || !charge_result[i].equals(peqModel.getCharge_result()) ){
								pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
								pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
								pkgEquipmentModel.setWork_gubun("E");
								pkgEquipmentModel.setWork_result(null);
								pkgEquipmentService.delete(pkgEquipmentModel);
								
								pkgEquipmentModel = new PkgEquipmentModel();
								pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
								pkgEquipmentModel.setWork_gubun("E");
								pkgEquipmentModel.setUse_yn("Y");
								pkgEquipmentModel.setEquipment_seq(checkSeqs[i]);
								pkgEquipmentModel.setEq_work_seq(eqWorkSeq);
								pkgEquipmentModel.setWork_date(cwork_dates[i]);
								pkgEquipmentModel.setStart_time1(cstart_times1[i]);
								pkgEquipmentModel.setStart_time2(cstart_times2[i]);
								pkgEquipmentModel.setEnd_time1(cend_times1[i]);
								pkgEquipmentModel.setEnd_time2(cend_times2[i]);
								pkgEquipmentModel.setWork_result(work_result[i]);
								pkgEquipmentModel.setCharge_result(charge_result[i]);
								if(ampms[i] != null && !ampms[i].equals("")){
									pkgEquipmentModel.setAmpm(ampms[i]);
								}else{
									pkgEquipmentModel.setAmpm("야간");
								}
								
								pkgEquipmentService.create(pkgEquipmentModel);
							}
						}
					}
				}

				isUpdateStatus = true;

				this.setPkgSystem(pkgModel);
				
				//그냥 저장 부분저장시에는 메일을 안보낸다 
				if(!"save".equals(pkgStatusModel.getCol22())){					
					//받는 사람: 운용자 미포함
					this.sendMailByStatus(pkgModel, pkgStatusModel, false);
				}
			}
		}

		if(pkgModel.isTurn_down() && "26".equals(pkgModel.getSelected_status())) {
			if("Y".equals(pkgModel.getDev_yn())){
				pkgModel.setSelected_status("24");	
			}else{
				pkgModel.setSelected_status("1");
			}
			
			pkgService.status_update(pkgModel);
			
			pkgStatusModel.setStatus("2");
			pkgStatusDAO.delete(pkgStatusModel);
		//등록인 경우
		}else if("C".equals(pkgModel.getStatus_operation())) {
			if("2".equals(pkgModel.getSelected_status())){
				if("save".equals(pkgStatusModel.getCol22())){
					pkgModel.setSelected_status(pkgModel.getStatus());					
				}
				//create
				if("N".equals(pkgStatusModel.getUpdate_gubun())){
					pkgStatusModel.setUpdate_gubun("Y");
					pkgStatusDAO.create(pkgStatusModel);
				//update
				}else if("Y".equals(pkgStatusModel.getUpdate_gubun())){
					int updateCnt = pkgStatusDAO.update(pkgStatusModel);
					if(updateCnt != 1) {
						throw new Exception("error.biz.수정되지 않았습니다. 새로고침 후 다시 시도해보시기 바랍니다.\n지속적으로 동일한 에러가 발생하는 경우 관리자에게 문의하시기 바랍니다.");
					}
				}
			}else if("25".equals(pkgModel.getSelected_status())) {
			}else if("24".equals(pkgModel.getSelected_status())) {
				if(isUpdateStatus) {
					pkgStatusDAO.create(pkgStatusModel);
				}
			} else if("26".equals(pkgModel.getSelected_status())) {
				if(isUpdateStatus) {
					pkgStatusDAO.create(pkgStatusModel);
				}
				
				pkgStatusModel.setStatus("2");
				
				int updateCnt = pkgStatusDAO.update(pkgStatusModel);
				if(updateCnt != 1) {
					throw new Exception("error.biz.수정되지 않았습니다. 새로고침 후 다시 시도해보시기 바랍니다.\n지속적으로 동일한 에러가 발생하는 경우 관리자에게 문의하시기 바랍니다.");
				}
			} else if("6".equals(pkgModel.getSelected_status())) {
				if(isUpdateStatus) {
					pkgStatusDAO.create(pkgStatusModel);
				}
			
			} else if("7".equals(pkgModel.getSelected_status())){
				if("save".equals(pkgStatusModel.getCol22())){
					pkgModel.setSelected_status(pkgModel.getStatus());					
				}
				//create
				if("N".equals(pkgStatusModel.getUpdate_gubun())){
					pkgStatusModel.setUpdate_gubun("Y");
					pkgStatusDAO.create(pkgStatusModel);
				//update
				}else if("Y".equals(pkgStatusModel.getUpdate_gubun())){
					int updateCnt = pkgStatusDAO.update(pkgStatusModel);
					if(updateCnt != 1) {
						throw new Exception("error.biz.수정되지 않았습니다. 새로고침 후 다시 시도해보시기 바랍니다.\n지속적으로 동일한 에러가 발생하는 경우 관리자에게 문의하시기 바랍니다.");
					}
				}
				
				pkgModel.setSelected_status("7");
			} else if("9".equals(pkgModel.getSelected_status())){
				if("save".equals(pkgStatusModel.getCol22())){
					pkgModel.setSelected_status(pkgModel.getStatus());					
				}
				//create
				if("N".equals(pkgStatusModel.getUpdate_gubun())){
					pkgStatusModel.setUpdate_gubun("Y");
					pkgStatusDAO.create(pkgStatusModel);
				//update
				}else if("Y".equals(pkgStatusModel.getUpdate_gubun())){
					int updateCnt = pkgStatusDAO.update(pkgStatusModel);
					if(updateCnt != 1) {
						throw new Exception("error.biz.수정되지 않았습니다. 새로고침 후 다시 시도해보시기 바랍니다.\n지속적으로 동일한 에러가 발생하는 경우 관리자에게 문의하시기 바랍니다.");
					}
				}
			} else if(!"9".equals(pkgModel.getSelected_status())){
				if("3".equals(pkgModel.getSelected_status())){
					if(isUpdateStatus) {
						int updateCnt = pkgStatusDAO.update(pkgStatusModel);
						if(updateCnt != 1) {
							throw new Exception("error.biz.수정되지 않았습니다. 새로고침 후 다시 시도해보시기 바랍니다.\n지속적으로 동일한 에러가 발생하는 경우 관리자에게 문의하시기 바랍니다.");
						}
					}
				}else if("22".equals(pkgModel.getSelected_status())){
					if(isUpdateStatus) {
						int updateCnt = pkgStatusDAO.update(pkgStatusModel);
						if(updateCnt != 1) {
							throw new Exception("error.biz.수정되지 않았습니다. 새로고침 후 다시 시도해보시기 바랍니다.\n지속적으로 동일한 에러가 발생하는 경우 관리자에게 문의하시기 바랍니다.");
						}
					}
				}else{
					if("Y".equals(pkgStatusModel.getUpdate_gubun())){
						int updateCnt = pkgStatusDAO.update(pkgStatusModel);
						if(updateCnt != 1) {
							throw new Exception("error.biz.수정되지 않았습니다. 새로고침 후 다시 시도해보시기 바랍니다.\n지속적으로 동일한 에러가 발생하는 경우 관리자에게 문의하시기 바랍니다.");
						}
					}else{
						pkgStatusDAO.create(pkgStatusModel);											
					}
				}
			}
			if(isUpdateStatus) {
				//초도 적용 완료시 확대정용 장비가 없으면 완료 처리함 2012-09-06
				if("7".equals(pkgModel.getSelected_status())){
					
					List<PkgEquipmentModel> pkgEquipmentModelList = new ArrayList<PkgEquipmentModel>();
					pkgEquipmentModelList = this.getPkgEquipment(pkgModel, "E");
					
					if(pkgEquipmentModelList == null  || pkgEquipmentModelList.isEmpty() || pkgEquipmentModelList.size() < 0 ){
						pkgModel.setSelected_status("9");
					}
					
	//					System.out.println("=====================1======================" + pkgModel.getApply_end());
					//확대 적용 장비가 없어도 완료 처리 20121115
					if("E".equals(pkgModel.getApply_end())){
						pkgModel.setSelected_status("9");
					}
				}
				
				pkgService.status_update(pkgModel);
			}
		} else {//수정인 경우
			if(isUpdateStatus) {
				int updateCnt = pkgStatusDAO.update(pkgStatusModel);
				if("7".equals(pkgModel.getSelected_status())){
					if("E".equals(pkgModel.getApply_end())){
						pkgModel.setSelected_status("9");
						pkgService.status_update(pkgModel);
					}
				}
				if("2".equals(pkgModel.getSelected_status())){
					pkgModel.setSelected_status(pkgModel.getStatus());
					pkgService.status_update(pkgModel);
				}
				
				if(updateCnt != 1) {
					throw new Exception("error.biz.수정되지 않았습니다. 새로고침 후 다시 시도해보시기 바랍니다.\n지속적으로 동일한 에러가 발생하는 경우 관리자에게 문의하시기 바랍니다.");
				}
			}
		}
		
		//상태 조회
		PkgModel rPkgModel = pkgService.read(pkgModel);
		pkgModel.setStatus(rPkgModel.getStatus());
		pkgModel.setMax_status(rPkgModel.getMax_status());
	}

	@Override
	public PkgModel read(PkgModel pkgModel) throws Exception {
		
		PkgStatusModel pkgStatusModel = new PkgStatusModel();
		
		if("10".equals(pkgModel.getSelected_status())) {//완료 결과
			List<PkgStatusModel> returnList = new ArrayList<PkgStatusModel>();
			
			pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
			pkgStatusModel.setStatus("3");
			PkgStatusModel pkgStatusModelData0 = pkgStatusDAO.read(pkgStatusModel);
			returnList.add(pkgStatusModelData0);

			pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
			pkgStatusModel.setStatus("7");
			PkgStatusModel pkgStatusModelData1 = pkgStatusDAO.read(pkgStatusModel);
			returnList.add(pkgStatusModelData1);

			pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
			pkgStatusModel.setStatus("9");
			PkgStatusModel pkgStatusModelData2 = pkgStatusDAO.read(pkgStatusModel);
			returnList.add(pkgStatusModelData2);
			this.setPkgSystem(pkgModel);
			pkgModel.setPkgEquipmentModelList(this.getPkgEquipment(pkgModel, "E"));
			
			pkgModel.setPkgStatusModelList(returnList);
			
			//첨부파일
			if (pkgModel.getMaster_file_id() != null && !"".equals(pkgModel.getMaster_file_id())) {
				fileManageService.read(pkgModel);
			}
		} else {
			pkgStatusModel.setPkg_seq(pkgModel.getPkg_seq());
			pkgStatusModel.setStatus(pkgModel.getSelected_status());
			
			if("24".equals(pkgModel.getSelected_status())){
				PkgStatusModel devModel = pkgStatusModel;
				devModel = pkgStatusDAO.readComment(devModel);
									
				pkgModel.setDev_comment(devModel.getCol1());
			}
			PkgStatusModel pkgStatusModelData = pkgStatusDAO.read(pkgStatusModel);

			pkgModel.setPkgStatusModel(pkgStatusModelData);
		}
		if("2".equals(pkgModel.getSelected_status())) {
			//첨부파일
			if (pkgModel.getMaster_file_id() != null && !"".equals(pkgModel.getMaster_file_id())) {
				fileManageService.read(pkgModel);
			}
			
			SysModel sysModel_25 = new SysModel();
			sysModel_25.setSystem_seq(pkgModel.getSystem_seq());
			sysModel_25 = systemService.readUsersAppliedToSystem(sysModel_25);
			
			pkgModel.setSystemUserModelList_25(sysModel_25.getSystemUserList());

			//승인요청 전인 경우
			if("1".equals(pkgModel.getStatus()) || "24".equals(pkgModel.getStatus())) {
				//승인자 목록
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkgModel.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				
				//검증,승인,사업계획,개발,협력업체 담당자
				pkgModel.setSystemUserModelList(sysModel.getSystemUserList());
				
				if("save".equals(pkgModel.getPkgStatusModel().getCol22())){	//승인 요청전 가저장 상태
					PkgUserModel pkgUserModel = new PkgUserModel();
					pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
					pkgUserModel.setCharge_gubun("SU");
					pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));

					List<SystemUserModel> system_userModelList = new ArrayList<SystemUserModel>();
					
					for(SystemUserModel sysmdl : pkgModel.getSystemUserModelList()){
						int cnt = 0;
						for(PkgUserModel aa : pkgModel.getPkgUserModelList()){
							if(aa.getUser_id().equals(sysmdl.getUser_id())){
								sysmdl.setSystem_user_ok_gubun("Y");
								cnt++;
								system_userModelList.add(sysmdl);
							}
						}
						if(cnt == 0){
							sysmdl.setSystem_user_ok_gubun("N");
							system_userModelList.add(sysmdl);
						}
					}
					
					pkgModel.setSystemUserModelList(null);
					pkgModel.setSystemUserModelList(system_userModelList);
				}
			} else {//승인요청한 후
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("SU");
				pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));
			}
			
		}else if("25".equals(pkgModel.getSelected_status())) {
			
		//검증결과 시 첨부파일 정보 세팅
		}else if("26".equals(pkgModel.getSelected_status())) {
			//첨부파일
			if (pkgModel.getMaster_file_id() != null && !"".equals(pkgModel.getMaster_file_id())) {
				fileManageService.read(pkgModel);
			}
			
			PkgStatusModel pkgStatusModelData_search = new PkgStatusModel();
			
			pkgStatusModelData_search.setPkg_seq(pkgModel.getPkg_seq());
			pkgStatusModelData_search.setStatus("2");
			
			pkgStatusModelData_search = pkgStatusDAO.read(pkgStatusModelData_search);
			
			pkgModel.getPkgStatusModel().setCol1(pkgStatusModelData_search.getCol1());
			pkgModel.getPkgStatusModel().setCol2(pkgStatusModelData_search.getCol2());
			pkgModel.getPkgStatusModel().setCol3(pkgStatusModelData_search.getCol3());
			pkgModel.getPkgStatusModel().setCol4(pkgStatusModelData_search.getCol4());
			pkgModel.getPkgStatusModel().setCol5(pkgStatusModelData_search.getCol5());
			pkgModel.getPkgStatusModel().setCol6(pkgStatusModelData_search.getCol6());
			pkgModel.getPkgStatusModel().setCol7(pkgStatusModelData_search.getCol7());
			pkgModel.getPkgStatusModel().setCol8(pkgStatusModelData_search.getCol8());
			pkgModel.getPkgStatusModel().setCol9(pkgStatusModelData_search.getCol9());
			pkgModel.getPkgStatusModel().setCol10(pkgStatusModelData_search.getCol10());
			pkgModel.getPkgStatusModel().setCol11(pkgStatusModelData_search.getCol11());
			pkgModel.getPkgStatusModel().setCol12(pkgStatusModelData_search.getCol12());
			pkgModel.getPkgStatusModel().setCol13(pkgStatusModelData_search.getCol13());
			pkgModel.getPkgStatusModel().setCol14(pkgStatusModelData_search.getCol14());
			pkgModel.getPkgStatusModel().setCol15(pkgStatusModelData_search.getCol15());
			pkgModel.getPkgStatusModel().setCol16(pkgStatusModelData_search.getCol16());
			pkgModel.getPkgStatusModel().setCol17(pkgStatusModelData_search.getCol17());
			pkgModel.getPkgStatusModel().setCol18(pkgStatusModelData_search.getCol18());
			pkgModel.getPkgStatusModel().setCol19(pkgStatusModelData_search.getCol19());
			pkgModel.getPkgStatusModel().setCol21(pkgStatusModelData_search.getCol21());
			pkgModel.getPkgStatusModel().setCol22(pkgStatusModelData_search.getCol22());
			pkgModel.getPkgStatusModel().setCol23(pkgStatusModelData_search.getCol23());
			pkgModel.getPkgStatusModel().setCol24(pkgStatusModelData_search.getCol24());
			pkgModel.getPkgStatusModel().setCol25(pkgStatusModelData_search.getCol25());
			pkgModel.getPkgStatusModel().setCol26(pkgStatusModelData_search.getCol26());
			
			
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
			
			SysModel sysModel_25 = new SysModel();
			sysModel_25.setSystem_seq(pkgModel.getSystem_seq());
			sysModel_25 = systemService.readUsersAppliedToSystem(sysModel_25);
			
			pkgModel.setSystemUserModelList_25(sysModel_25.getSystemUserList());
			
			pkgUserModel.setCharge_gubun("SU");
			pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
			pkgModel.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			pkgModel.setUser_active_user_id(pkgUserService.readActive(pkgUserModel).getUser_id());//현재 승인자
		//검증결과 시 첨부파일 정보 세팅
		}else if("3".equals(pkgModel.getSelected_status()) || "22".equals(pkgModel.getSelected_status())) {
			if (pkgModel.getMaster_file_id() != null && !"".equals(pkgModel.getMaster_file_id())) {
				pkgModel.setPkg_detail_seq(""); //NEW/PN/CR이나 상세 클릭하면 pkg_detail_seq 값이 세팅되어 보완적용내역 조회시 pkg_detail_seq 값이 있으면 안되기 때문에 초기화
				pkgDetailVariableService.read(pkgModel); //엑셀 상세내역 불러오기
				int cnt=0;
				if(pkgModel.getPkgDetailVariableModelList().size() > 0){
					for(List<PkgDetailVariableModel> model : pkgModel.getPkgDetailVariableModelList()){
						if("3".equals(pkgModel.getSelected_status())){
							if(!"OK".equals(model.get(2).getContent()) && !"COK".equals(model.get(2).getContent())){
								cnt++;
							}
						}else{
							if(!"OK".equals(model.get(19).getContent()) && !"COK".equals(model.get(19).getContent()) && !"BYPASS".equals(model.get(19).getContent())){
								cnt++;
							}
						}
					}
					if(cnt > 0){
						pkgModel.setDetailvariable_check("N");
					}else{
						pkgModel.setDetailvariable_check("Y");
					}
				}
				
				fileManageService.read(pkgModel);
			}
		//초도일정수립인 경우 장비 목록을 가져와 설정된 것과 매핑한다.
		} else if("23".equals(pkgModel.getSelected_status())) {//개발승인요청인 경우
			//승인요청 전인 경우
			if("22".equals(pkgModel.getStatus())) {
				//승인자 목록
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkgModel.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.DA);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				
				//검증,승인,사업계획,개발,협력업체 담당자
				pkgModel.setSystemUserModelList(sysModel.getSystemUserList());
				
			}else if("22".equals(pkgModel.getStatus_dev()) && "D".equals(pkgModel.getDev_yn())){
				//승인자 목록
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkgModel.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.DA);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				
				//검증,승인,사업계획,개발,협력업체 담당자
				pkgModel.setSystemUserModelList(sysModel.getSystemUserList());
			} else {//승인요청한 후
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("DA");
				pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));
			}
		}else if("24".equals(pkgModel.getSelected_status())) {//개발승인 경우
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
			
			pkgUserModel.setCharge_gubun("DA");
			pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
			pkgModel.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			pkgModel.setUser_active_user_id(pkgUserService.readActive(pkgUserModel).getUser_id());//현재 승인자
		} else if("4".equals(pkgModel.getSelected_status())) {//초도승인요청인 경우
			//시스템 다운 시간 및 관련시스템 정보 로드
			this.setPkgSystem(pkgModel);
			
			pkgModel.setPkgEquipmentModelList(this.getPkgEquipment(pkgModel, "S"));
		} else if("5".equals(pkgModel.getSelected_status())) {//초도승인요청인 경우
			//승인요청 전인 경우
			if("4".equals(pkgModel.getStatus())) {
				//승인자 목록
				SysModel sysModel = new SysModel();
				sysModel.setSystem_seq(pkgModel.getSystem_seq());
				sysModel.setCharge_gubun(SYSTEM_USER_CHARGE_GUBUN.AU);
				sysModel = systemService.readUsersAppliedToSystem(sysModel);
				
				//검증,승인,사업계획,개발,협력업체 담당자
				pkgModel.setSystemUserModelList(sysModel.getSystemUserList());
			} else {//승인요청한 후
				PkgUserModel pkgUserModel = new PkgUserModel();
				pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
				pkgUserModel.setCharge_gubun("AU");
				pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));
			}
		} else if("6".equals(pkgModel.getSelected_status())) {//초도승인 경우
			PkgUserModel pkgUserModel = new PkgUserModel();
			pkgUserModel.setPkg_seq(pkgModel.getPkg_seq());
			
			pkgUserModel.setCharge_gubun("AU");
			pkgModel.setPkgUserModelList(pkgUserService.readList(pkgUserModel));//등록된 승인자 목록
			pkgModel.setUser_active_status(pkgUserService.readActive(pkgUserModel).getOrd());//현재 승인자 ord
			pkgModel.setUser_active_user_id(pkgUserService.readActive(pkgUserModel).getUser_id());//현재 승인자
		} else if("7".equals(pkgModel.getSelected_status())) {//초도결과 등록 경우
			if (pkgModel.getMaster_file_id() != null && !"".equals(pkgModel.getMaster_file_id())) {
				fileManageService.read(pkgModel);
			}
		} else if("8".equals(pkgModel.getSelected_status())) {//확대일정수립인 경우
			this.setPkgSystem(pkgModel);
			pkgModel.setPkgEquipmentModelList(this.getPkgEquipment(pkgModel, "E"));
			
			
			pkgModel.setPkgEquipmentModelList4E(this.getPkgEquipment4E(pkgModel));	////2012.09.04 추가 확대일정수립에서 초도적용장비를 보여주기 위한 리스트
		} else if("9".equals(pkgModel.getSelected_status())) {//확대결과 등록인 경우
			if (pkgModel.getMaster_file_id() != null && !"".equals(pkgModel.getMaster_file_id())) {
				fileManageService.read(pkgModel);
			}
			this.setPkgSystem(pkgModel);
			pkgModel.setPkgEquipmentModelList(this.getPkgEquipment(pkgModel, "E"));
		}
		
		return pkgModel;
	}


	/**
	 * 상태별 메일 발송
	 * @param pkgModel
	 * @param pkgStatusModel
	 * @param isWithEquipmentUser
	 * @throws Exception
	 */
	private void sendMailByStatus(PkgModel pkgModel, PkgStatusModel pkgStatusModel, boolean isWithEquipmentUser) throws Exception {
		if("C".equals(pkgModel.getStatus_operation())) {
			//받는 사람: 운용자 미포함
			pkgService.readUser(pkgModel, isWithEquipmentUser, false);
			
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(PkgMailUtil.getTitle4Status(pkgModel));
			mailModel.setMsgText(PkgMailUtil.getContent4Status(pkgModel, pkgStatusModel));
			mailModel.setTos(PkgMailUtil.getToAddress4Status(pkgModel));
			mailModel.setFrom(pkgModel.getSession_user_email());
			/** 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy */
			mailModel.setTosInfo(this.getTosInfo(pkgModel));
			
			mailService.create4Multi(mailModel);
		}
	}
	
	//개발검증 메일 발송
	private void sendMailByStatus_dev(PkgModel pkgModel, PkgStatusModel pkgStatusModel, boolean DA) throws Exception {
		if("C".equals(pkgModel.getStatus_operation())) {
			//받는 사람: 운용자 미포함
			
			this.dev_readUser(pkgModel, DA);
			
			MailModel mailModel = new MailModel();
			mailModel.setMsgSubj(PkgMailUtil.getTitle4Status(pkgModel));
			mailModel.setMsgText(PkgMailUtil.getContent4Status(pkgModel, pkgStatusModel));
			mailModel.setTos(PkgMailUtil.getToAddress4Status(pkgModel));
			mailModel.setFrom(pkgModel.getSession_user_email());
			/** 받는사람 정보에 email,이름,소속 같이 출력 추가 1016 - ksy */
			mailModel.setTosInfo(this.getTosInfo(pkgModel));
			
			mailService.create4Multi(mailModel);
		}
	}
	
	private void dev_readUser(PkgModel pkgModel, boolean DA) throws Exception {
		SystemUserModel systemUserModel = new SystemUserModel();
		systemUserModel.setSystem_seq(pkgModel.getSystem_seq());

		if(DA == true){
			systemUserModel.setCharge_gubun("DA");
		}else{
			systemUserModel.setCharge_gubun("DV");
		}
		List<SystemUserModel> userList  = systemUserDAO.dev_readUser(systemUserModel);
		for(SystemUserModel model : userList){
			pkgModel.getSystemUserModelList().add(model);
		}

	}
	
	/**
	 * PKG별 시스템 세팅
	 * @param pkgModel
	 * @throws Exception
	 */
	private void setPkgSystem(PkgModel pkgModel) throws Exception {
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(pkgModel.getSystem_seq());
		sysModel = systemService.read(sysModel);
		
		pkgModel.setDowntime(sysModel.getDowntime());
		pkgModel.setImpact_systems(sysModel.getImpact_systems());
	}
	
	private List<PkgEquipmentModel> getPkgEquipment4E(PkgModel pkgModel) throws Exception{
		List<PkgEquipmentModel> sEModelList = null;
		
		//일정수립한 장비목록
		PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
		pkgEModel.setPkg_seq(pkgModel.getPkg_seq());

		pkgEModel.setWork_gubun("S");
		sEModelList = pkgEquipmentService.readList(pkgEModel);
		
		return sEModelList;
	}
	
	/**
	 * PKG 관련 장비 조회
	 * @param pkgModel
	 * @param workGubun
	 * @return
	 * @throws Exception
	 */
	private List<PkgEquipmentModel> getPkgEquipment(PkgModel pkgModel, String workGubun) throws Exception {
		List<PkgEquipmentModel> pkgEquipmentModelList = new ArrayList<PkgEquipmentModel>();
		PkgEquipmentModel pkgEquipmentModel = null;
		List<PkgEquipmentModel> sEModelList = null;

		//시스템에 등록되어 있는 장비목록
		SysModel sysModel = new SysModel();
		sysModel.setSystem_seq(pkgModel.getSystem_seq());
		
		@SuppressWarnings("unchecked")
		List<SysModel> equiList = (List<SysModel>)equipmentService.readList(sysModel);

		//일정수립한 장비목록
		PkgEquipmentModel pkgEModel = new PkgEquipmentModel();
		pkgEModel.setPkg_seq(pkgModel.getPkg_seq());

		if("E".equals(workGubun)) {
			pkgEModel.setWork_gubun("S");
		} else if("S".equals(workGubun)) {
			pkgEModel.setWork_gubun("E");
		}
		sEModelList = pkgEquipmentService.readList(pkgEModel);

		pkgEModel.setWork_gubun(workGubun);
		List<PkgEquipmentModel> pkgEModelList = pkgEquipmentService.readList(pkgEModel);

		boolean isExist = false;
		
		MainLoop:
		for(SysModel equiModel : equiList) {
			
			//초도인 경우: 확대적용 장비 제외, 확대인 경우: 초도적용 장비는 제외
			for(PkgEquipmentModel sEModel : sEModelList) {
				if(equiModel.getEquipment_seq().equals(sEModel.getEquipment_seq())) {
					continue MainLoop;
				}
			}

			for(PkgEquipmentModel peModel : pkgEModelList) {
				if(equiModel.getEquipment_seq().equals(peModel.getEquipment_seq())) {
					pkgEquipmentModel = (PkgEquipmentModel) peModel.clone();
					isExist = true;
					break;
				}
			}
			
			if(!isExist) {
				pkgEquipmentModel = new PkgEquipmentModel();
				pkgEquipmentModel.setPkg_seq(pkgModel.getPkg_seq());
//				pkgEquipmentModel.setWork_gubun(workGubun);
				pkgEquipmentModel.setEquipment_seq(equiModel.getEquipment_seq());
			}
			
			pkgEquipmentModel.setIdc_name(equiModel.getIdc_name());
			pkgEquipmentModel.setEquipment_name(equiModel.getName());
			pkgEquipmentModel.setTeam_name(equiModel.getTeam_name());
			pkgEquipmentModel.setTeam_code(equiModel.getTeam_code());
			pkgEquipmentModel.setService_area(equiModel.getService_area());
			
			pkgEquipmentModelList.add(pkgEquipmentModel);
			
			isExist = false;
		}

		return pkgEquipmentModelList;
	}

	/**
	 * SMS 발송
	 * @param pkgModel
	 * @param pkgUserModel
	 * @throws Exception
	 */
	private void sendSms(PkgModel pkgModel, PkgUserModel pkgUserModel) throws Exception {
		String tel = "";
		String tel1 = "";
		String tel2 = "";
		
		String regno = "";
		String empno = "";
		String pkg_seq = "";
		
		SktUserModel sktUserModel = new SktUserModel();
		sktUserModel.setEmpno(pkgUserModel.getUser_id());
		sktUserModel = sktUserService.read(sktUserModel);
		regno = sktUserModel.getRegno();
		empno = sktUserModel.getEmpno();
		pkg_seq = pkgModel.getPkg_seq();
		tel = sktUserModel.getMovetelno();
		
		if(tel != null) {
			tel = tel.replaceAll("-", "");
			tel1 = tel.substring(0, 3);
			tel2 = tel.substring(3, tel.length());
			
			SmsModel smsModel = new SmsModel();
			smsModel.setLog_no("1");//의미없음
//			smsModel.setMsg(PkgSmsUtil.getMessage(pkgModel.getSelected_status(), pkgModel.getTitle()));
			smsModel.setMsg(PkgSmsUtil.getMessage(pkgModel.getSelected_status(), pkgModel.getTitle()));
//			smsModel.setMsg(smsModel.getMsg() + " (" + pkgModel.getTitle() + ")");
			smsModel.setDestcid(tel1);//국번
			smsModel.setDestcallno(tel2);
			smsModel.setPortedflag("0");//의미없음
			smsModel.setTid("65491");//승인인 경우
			
			//soa 연동
			try{
				//Sms서비스 생성
				SMSSenderServiceLocator locator = new SMSSenderServiceLocator();
				
				//개발기 (생략시 WSDL파일에 있는 포트사용)
				locator.setEndpointAddress("SMSSenderSoapPort", propertyService.getString("SOA.Sms.ip"));
				
				SMSSender service = locator.getSMSSenderSoapPort();
				
				//Authentication 설정
				Stub stub = (Stub)service;
				stub._setProperty(Stub.USERNAME_PROPERTY, propertyService.getString("SOA.Username"));
				stub._setProperty(Stub.PASSWORD_PROPERTY, propertyService.getString("SOA.Password"));				
				
				//Input 파라미터
				String CONSUMER_ID = null; //송신시스템 id(시스템 별 ID 부여)
				String RPLY_PHON_NUM = null; //송신자 전화번호
				String TITLE = ""; //전송메시지
				String TITLE2 = ""; //전송메시지2
				String PHONE = null; //수신자 전화번호
				
				String URL = ""; //수신 URL		 					
				String START_DT_HMS = ""; //예약발송 시작시간			
				String END_DT_HMS = "";	//예약발송 종료시간

				//테스트용
//				CONSUMER_ID = "test_consumer";
//				RPLY_PHON_NUM = "01091168781";
//				TITLE = "안녕하세요.PKMS입니다.";
//				PHONE = "01091168781";
				
//				String[] sp_system_name = null;	
//				sp_system_name = pkgModel.getSystem_name().split(">");
//				TITLE2 = " (" + sp_system_name[3] + "(" + pkgModel.getVer() + ")" + ")";
								
				CONSUMER_ID = propertyService.getString("SOA.Username");
				RPLY_PHON_NUM = propertyService.getString("SOA.Sms.sendNum");

				if("23".equals(pkgModel.getSelected_status()) || "24".equals(pkgModel.getSelected_status())){
					TITLE2 = "PKG제목:"+pkgModel.getTitle()+","+"대표담당자:"+pkgModel.getDev_system_user_name();
				}else if("5".equals(pkgModel.getSelected_status()) || "6".equals(pkgModel.getSelected_status())){
					TITLE2 = "PKG제목:"+pkgModel.getTitle()+","+"대표담당자:"+pkgModel.getSystem_user_name();
				}else if("2".equals(pkgModel.getSelected_status()) || "26".equals(pkgModel.getSelected_status())){
					TITLE2 = "PKG제목:"+pkgModel.getTitle()+","+"대표담당자:"+pkgModel.getSystem_user_name();
				}
				
//				TITLE = PkgSmsUtil.getMessage(pkgModel.getSelected_status(), pkgModel.getTitle());
				TITLE = PkgSmsUtil.getMessage(pkgModel.getSelected_status(), pkgModel.getTitle());
				PHONE = tel1 + tel2;
				
				//Output (uuid는 예약전송일 경우에만 들어온다.");
				StringHolder _return = new StringHolder();
				StringHolder uuid = new StringHolder();
				
				logger.debug("============================ sms_send_input_CONSUMER_ID ============================" + CONSUMER_ID);
				logger.debug("============================ sms_send_input_RPLY_PHON_NUM ==========================" + RPLY_PHON_NUM);
				logger.debug("============================ sms_send_input_TITLE ==================================" + TITLE);
				logger.debug("============================ sms_send_input_PHONE ==================================" + PHONE);
				
//				//웹서비스 호출. (send오퍼레이션)
				service.send(CONSUMER_ID, RPLY_PHON_NUM, TITLE, PHONE, URL, START_DT_HMS, END_DT_HMS, _return, uuid);
				service.send(CONSUMER_ID, RPLY_PHON_NUM, TITLE2, PHONE, URL, START_DT_HMS, END_DT_HMS, _return, uuid);
				
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
	
	/**
	 * 초도 /확대 결과 등록 시 작업시스템 등록
	 * 
	 * @param pkgModel
	 * @param pkgStatusModel
	 * @param pkgEquipmentModelList
	 * @param gubun
	 * @throws Exception
	 */
	// 초도적용완료 저장시 데이터 연동 
	private void sendWork4Result(PkgModel pkgModel, PkgStatusModel pkgStatusModel, List<PkgEquipmentModel> pkgEquipmentModelList, String gubun) throws Exception {
		WorkSystemModel workSystemModel = new WorkSystemModel();
		
		workSystemModel.setNo(pkgModel.getPkg_seq()); // no는 pkms랑 연동되는 FK(Foreign Key) : pkg_seq와 연동되요
		
		String total_title = "";
//		String title = ""; 작업관리 시스템에서 빼달라함
		String roaming = "";
		String area = "";
		String ver = "";
		String eq_name = "";
		String bypass_traffic = "";
		String full_system_name = "";

		ver = pkgModel.getVer();
		
		if(pkgModel.getRoaming_link().equals("상")){
			roaming = "[로밍연동-상]";
		}else if(pkgModel.getRoaming_link().equals("중")){
			roaming = "[로밍연동-중]";
		}else if(pkgModel.getRoaming_link().equals("하")){
			roaming = "[로밍연동-하]";
		}
		
		if("Y".equals(pkgModel.getBypass_traffic())){
			bypass_traffic = "[우회소통]";
		}
		
		workSystemModel.setMaster_file_id(pkgModel.getMaster_file_id()); //첨부파일 연동
		
		workSystemModel.setImpo("Y"); //pkms_main
		workSystemModel.setBackground_target(bypass_traffic + "PKMS의 작업절차서 파일 참조"); //pkms_main
		workSystemModel.setJob_bunya("3"); //pkms_main
		workSystemModel.setJob_gubun1("SW 작업"); //pkms_main

		if("F".equals(pkgModel.getVer_gubun())){ // 버전구분이 F(Full) 이면 FullPackage
			workSystemModel.setJob_gubun2("PKG적용-Full"); //pkms_main
		}else{ // 버전구분이 P(Partial) 이면 PartialPackage 불러오는
			workSystemModel.setJob_gubun2("PKG적용-Partial"); //pkms_main
		}
		
		//c_date  2013-03-25 15:50:47 ㅡ> 20130325 변환
		SimpleDateFormat DateFormat = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );
//		SimpleDateFormat DateCday = new SimpleDateFormat ( "yyyy-MM-dd HH:mm", Locale.KOREA );
		Date current = new Date();
		String c_date = DateFormat.format (current);
//		String c_day = DateCday.format (current);
		
		workSystemModel.setC_date(c_date);//pkms_sub
		workSystemModel.setIns_date(c_date);//pkms_main
		workSystemModel.setMaster_date_s(c_date);//pkms_sub
		
		workSystemModel.setGojang_step("원복");//pkms_main
		workSystemModel.setWork_effect("소통저조");//pkms_main
		workSystemModel.setWork_rank("L");//pkms_main
		
		workSystemModel.setWork_name(pkgModel.getSession_user_name());	//pkms_sub
		//iwcs-update -----
		workSystemModel.setIns_name(pkgModel.getSession_user_name());
		workSystemModel.setIns_id(pkgModel.getSession_user_id());
		workSystemModel.setIns_sosok(pkgModel.getSession_user_group_name());
		workSystemModel.setIns_sosok_code(pkgModel.getSession_user_group_id());		
		workSystemModel.setWork_phone(pkgModel.getSession_user_mobile_phone());
		workSystemModel.setWork_result_date_s(c_date);//pkms_sub
		workSystemModel.setSystem_seq(pkgModel.getSystem_seq());
		//iwcs-update ----
		
		workSystemModel.setWork_gubun(gubun);
		
		WorkSystemModel WorkSysMdl = new WorkSystemModel();
		WorkSysMdl.setSystem_seq(pkgModel.getSystem_seq());
		
		WorkSysMdl = workSystemService.read_Sys_User_Info(WorkSysMdl); //system 안의 대표담당자
		
		workSystemModel.setJob_man(WorkSysMdl.getJob_man()); // SYSTEM 대표담당자 
		workSystemModel.setJob_man_post(WorkSysMdl.getJob_man_post()); //SYSTEM 대표담당자의 부서		
		
		workSystemModel.setTarget_system(WorkSysMdl.getTarget_system()); //target_system
		workSystemModel.setWork_summary(WorkSysMdl.getTarget_system()); //work_summary
		
		if("S".equals(gubun)) {
			WorkSysMdl.setPkg_seq(pkgModel.getPkg_seq());
			WorkSysMdl = workSystemService.readIns_Data(WorkSysMdl); //승인요청자
			workSystemModel.setWork_name(WorkSysMdl.getIns_name());	//pkms_sub
			workSystemModel.setIns_name(WorkSysMdl.getIns_name());
			workSystemModel.setIns_id(WorkSysMdl.getIns_id());
			workSystemModel.setIns_sosok(WorkSysMdl.getIns_sosok());
			workSystemModel.setIns_sosok_code(WorkSysMdl.getIns_sosok_code());
		}
		
		//리스트에 있는 여러개의 장비 이름을 가져과 콤마로 구분해서 장비이름 넣기 SYSTEM_NAME
		String system_name = "";
		
		//pkgEquipmentModelList에 영향있는 변수들 if문 위에서 선언 1125
		String w_sTime ="";
		String w_eTime ="";
		
		WorkSystemModel WorkSysModel = new WorkSystemModel();
		String[] eq_seq = null;
		if("E".equals(gubun)) {
			if("U".equals(pkgModel.getStatus_operation())) {
				WorkSysModel.setPkg_seq(pkgModel.getPkg_seq());
				WorkSysModel.setWork_gubun(gubun);
				if("OPERATOR".equals(pkgModel.getGranted())){
					WorkSysModel.setMaster_team_code(pkgModel.getSession_user_group_id());
				}else{
					WorkSysModel.setMaster_team_code(null);
				}

				List<WorkSystemModel> eqList = workSystemService.read_Equipment(WorkSysModel);
				
				if(eqList != null){
					//after_장비 정보 선언
					//선언
					String[] all_checkSeqs= pkgModel.getAll_check_seqs(); //모든 장비
					String[] checkSeqs = pkgModel.getCheck_seqs(); //선택
					String[] noncheckSeq = pkgModel.getNon_check_seqs(); //선택 안된 시퀀스
					String[] work_dates = pkgModel.getWork_dates();
					String[] start_times1 = pkgModel.getStart_times1();
					String[] start_times2 = pkgModel.getStart_times2();
					String[] end_times1 = pkgModel.getEnd_times1();
					String[] end_times2 = pkgModel.getEnd_times2();
					String[] ampms = pkgModel.getAmpms();
					
					String[] cwork_dates = WingsStringUtil.getNotNullStringArray(work_dates);
					String[] cstart_times1 = WingsStringUtil.getNotNullStringArray(start_times1);
					String[] cstart_times2 = WingsStringUtil.getNotNullStringArray(start_times2);
					String[] cend_times1 = WingsStringUtil.getNotNullStringArray(end_times1);
					String[] cend_times2 = WingsStringUtil.getNotNullStringArray(end_times2);
					
					List<String> del_SeqList = new ArrayList<String>(); //삭제할 시퀀스 리스트
					List<String> del_Seq = new ArrayList<String>();	//시퀀스 중복제거 리스트
					
					List<String> no_del_SeqList = new ArrayList<String>();	//삭제안할 장비 시퀀스 리스트
					List<String> no_del_Seq = new ArrayList<String>();
					int j = 0;
					
					//삭제할 seq 걸러 내기
					for(String all_check_seq : all_checkSeqs){ // j    //모든 장비
						int i = 0;
						for(String check_seq : checkSeqs){ // i        //추가 혹은 수정되어야 할 장비
							if(all_check_seq.equals(check_seq)){ //체크된 장비
								int no_del_cnt = 0;
								for(WorkSystemModel eqModel : eqList){
									if(eqModel.getEquipment_seq().equals(check_seq)){ //기존에 있는 장비(수정/미수정)
										no_del_cnt++;
										if(!eqModel.getWork_date().equals(cwork_dates[i]) || !eqModel.getStart_time1().equals(cstart_times1[i]) ||
												!eqModel.getStart_time2().equals(cstart_times2[i]) || !eqModel.getEnd_time1().equals(cend_times1[i]) ||
												!eqModel.getEnd_time2().equals(cend_times2[i]) || !eqModel.getAmpm().equals(ampms[j])){ //날짜 시간 하나라도 틀리면 삭제
											del_SeqList.add(String.valueOf(eqModel.getSeq()));
										}
									}
								}
								if(no_del_cnt == 0){ //기존에 없는 장비
									//기존에 없는 추가 장비 중 다른 장비의 날짜와 겹친 데이터 추출
									WorkSysModel.setWork_date(cwork_dates[i]);
									WorkSysModel.setAmpm(ampms[j]);
									List<WorkSystemModel> del_eqList = workSystemService.read_Equipment(WorkSysModel);
									
									for(WorkSystemModel eqModel: del_eqList){
										del_SeqList.add(String.valueOf(eqModel.getSeq()));
									}
								}
							}
							i++;
						}
						
						//기존에 있으나 선택 안된 장비 추출
						for(String non_check_seq : noncheckSeq){
							for(WorkSystemModel eqModel : eqList){
								if(eqModel.getEquipment_seq().equals(non_check_seq)){
									del_SeqList.add(String.valueOf(eqModel.getSeq()));
								}
							}
						}
						j++;
					}//삭제할 seq 걸러 내기 끝
					
					//seq 중복제거
					if(del_SeqList != null){
						for(String i : del_SeqList){
							if(!del_Seq.contains(i)){
								del_Seq.add(i);
							}
						}
						//삭제할 seq 삭제
						for(String str : del_Seq){
							int i = Integer.parseInt(str);
							WorkSysModel.setSeq(i);
							workSystemService.update_pkms_main_del(WorkSysModel);
							workSystemService.delete_Equipment(WorkSysModel);
						}
					}
					
					for(WorkSystemModel eqModel : eqList){
						int cnt = 0; //제거대상 seq 카운트
						for(String str : del_Seq){
							int i = Integer.parseInt(str);
							if(eqModel.getSeq() == i){
								cnt++;
							}
						}
						if(cnt == 0){//제거대상이 아닌 seq
							no_del_SeqList.add(eqModel.getEquipment_seq());
						}
					}
					
					if(no_del_SeqList != null){ // ','구분자로 NOT IN 검색 설정 값(삭제하면 안될 장비들)
						for(String i : no_del_SeqList){
							if(!no_del_Seq.contains(i)){
								no_del_Seq.add(i);
							}
						}
						int seq_cnt=0;
						eq_seq = new String[no_del_Seq.size()];
						for(String str : no_del_Seq){
							eq_seq[seq_cnt] = str; 
							seq_cnt++;
						}
					}
				}//삭제 완료
			}
		}
		
		if("S".equals(gubun)) { //초도는 승인자가 데이터를 입력하기 때문에 데이터 있으면 모두 지우고 다시 다 입력
			WorkSysModel.setWork_gubun(gubun);
			WorkSysModel.setPkg_seq(pkgModel.getPkg_seq());
			List<WorkSystemModel> del_Seq_List = workSystemService.read_Seq_S(WorkSysModel);
			if(del_Seq_List != null){
				for(WorkSystemModel delModel : del_Seq_List){
					workSystemService.update_pkms_main_del(delModel);
					workSystemService.delete_Equipment(delModel);
				}
			}
		}
		
		int seq = 0;
		int am_seq = 0;
		int pe_cnt = 0;
		int am_cnt = 0;
		int pm_cnt = 0;
		//사전작업 시간
//		String before_s_date = "";
//		String before_e_date = "";
		
		WorkSysModel.setPkg_seq(pkgModel.getPkg_seq());
		WorkSysModel.setWork_gubun(gubun);
		
		if("OPERATOR".equals(pkgModel.getGranted())){
			WorkSysModel.setMaster_team_code(pkgModel.getSession_user_group_id());
		}else{
			WorkSysModel.setMaster_team_code(null);
		}
		
		WorkSysModel.setEquipment_seqs(eq_seq);

		List<WorkSystemModel> teamWorkDateList = workSystemService.readTeamWorkDate(WorkSysModel);
		if(teamWorkDateList != null){
			for(WorkSystemModel workDate : teamWorkDateList){
				WorkSysModel = workSystemService.read_SeqMax_Main(WorkSysModel);
				seq = WorkSysModel.getSeq(); //pkms_main
				String work_date= workDate.getWork_date();
				String team_code= "0000" + workDate.getMaster_team_code();				
				seq++;
				am_cnt=0;
				pe_cnt=0;
				pm_cnt=0;
				List<String> areaList = new ArrayList<String>();
				List<String> areaSetList = new ArrayList<String>(); 
				for(PkgEquipmentModel peModel : pkgEquipmentModelList){
					String peModel_work_date = peModel.getWork_date() + peModel.getStart_time1() + peModel.getEnd_time1();
					if(work_date.equals(peModel_work_date) && team_code.equals(peModel.getTeam_code())){
						if(workDate.getAmpm().equals(peModel.getAmpm())){							
							if("주간".equals(peModel.getAmpm())){ // 주간 - 사전작업 본작업 세팅
								am_cnt++;
								if(am_cnt == 1){
									am_seq = seq + 1;
									eq_name = peModel.getEquipment_name();
									full_system_name = peModel.getEquipment_name();
								}else if(am_cnt == 2){
									eq_name = eq_name + " 등";
								}
								if(am_cnt > 1){									
									full_system_name = full_system_name + ";" + peModel.getEquipment_name();
								}
							}else{
								pm_cnt++;
								if(pm_cnt == 1){
									eq_name = peModel.getEquipment_name();
									full_system_name = peModel.getEquipment_name();
								}else if(pm_cnt == 2){
									eq_name = eq_name + " 등";
								}
								if(pm_cnt > 1){
									full_system_name = full_system_name + ";" + peModel.getEquipment_name();									
								}
							}
							
//							SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
//							Date date = null;
//							date = dateFormat.parse(peModel.getWork_date());
//							Calendar cal = Calendar.getInstance();
//							cal.setTime(date);
//				            cal.add(Calendar.DATE, -1);
				            
//				            before_s_date = dateFormat.format(cal.getTime()) + " 21:00";
//							before_e_date = peModel.getWork_date() + " 06:00";
							w_sTime = peModel.getWork_date() + " " + peModel.getStart_time1() + ":" + peModel.getStart_time2();
							w_eTime = peModel.getWork_date() + " " + peModel.getEnd_time1() + ":" + peModel.getEnd_time2();
							
							//시스템 이름
							if(pe_cnt==0){
								system_name = "["+ peModel.getIdc_name() +"]"+ peModel.getEquipment_name();				
							}else{
								system_name = system_name +","+"["+ peModel.getIdc_name() +"]"+ peModel.getEquipment_name();
							}
							
							areaList.add(peModel.getIdc_name());
							
							pe_cnt++;
							
							//수정위한 보조 테이블 입력
							WorkSysModel.setSeq(seq);
							WorkSysModel.setEquipment_seq(peModel.getEquipment_seq());
							WorkSysModel.setWork_date(peModel.getWork_date());
							WorkSysModel.setStart_time1(peModel.getStart_time1());
							WorkSysModel.setStart_time2(peModel.getStart_time2());
							WorkSysModel.setEnd_time1(peModel.getEnd_time1());
							WorkSysModel.setEnd_time2(peModel.getEnd_time2());
							WorkSysModel.setAmpm(peModel.getAmpm());
							
							workSystemService.create_work_equipment(WorkSysModel);
							
							if(am_cnt > 0){
								WorkSysModel.setSeq(am_seq);
								WorkSysModel.setEquipment_seq(peModel.getEquipment_seq());
								WorkSysModel.setWork_date(peModel.getWork_date());
								WorkSysModel.setStart_time1(peModel.getStart_time1());
								WorkSysModel.setStart_time2(peModel.getStart_time2());
								WorkSysModel.setEnd_time1(peModel.getEnd_time1());
								WorkSysModel.setEnd_time2(peModel.getEnd_time2());
								WorkSysModel.setAmpm(peModel.getAmpm());
								
								workSystemService.create_work_equipment(WorkSysModel);
							}
						}
					}
				}
				WorkSysMdl.setMaster_team_code(workDate.getMaster_team_code());				
				
				workSystemModel.setSeq(seq);	//SEQ
				workSystemModel.setTeam_code(team_code);
				
				workSystemModel.setWork_plandate_s(w_sTime); // 작업시작시간 WORK_PLANDATE_S  2013-03-25 02:00
				workSystemModel.setWork_plandate_e(w_eTime); // 작업종료시간 WORK_PLANDATE_E  2013-03-25 07:00

				workSystemModel.setSystem_name(system_name); // 장비 이름 SYSTEM_NAME
				
				workSystemModel.setWork_sosok(workDate.getWork_sosok());
				for(String str : areaList){
					if(!areaSetList.contains(str)){
						if(!"".equals(str.replaceAll(" ", "")) && str != null){										
							areaSetList.add(str);
						}
					}
				}
				
				int areaSetList_cnt=0;
				for(String str : areaSetList){
					if(areaSetList_cnt==0){
						area = str + ";"; 
					}else{
						area = area + ";" + str;
					}
					areaSetList_cnt++;
				}
				
				workSystemModel.setService_effect_area(area); //pkms_main
				workSystemModel.setFull_system_name(full_system_name); //pkms_target_system
				if(!"".equals(roaming+bypass_traffic)){
					bypass_traffic = bypass_traffic + " ";
				}
				
				String content = "■ 시스템 : " + pkgModel.getSystem_name()+"<br/>" +
						 "■ 제목 : "+ pkgModel.getTitle() + "<br/>" +
						 "■ 대상시스템 : ";
				
				if("S".equals(gubun)) {//초도 - S
					total_title = roaming + bypass_traffic + area + " " + eq_name + " " + ver +" 초도";
					content = content + "초도 : " + area + eq_name + "<br/>";
				}else{ //확대 - E					
					total_title = roaming + bypass_traffic + area + " " + eq_name + " " + ver +" 확대";
					content = content + "확대 : " + area + eq_name + "<br/>";
				}
				content = content + "■ 상용대표담당자 : "+ pkgModel.getSystem_user_name()+"M <br/>"
						 + "■ 상용대표담당자 : "+ pkgModel.getDev_system_user_name()+"M <br/>"
						 + "■ PKG버젼 : " +ver + "<br/>"
						 + "■ 버전 구분 : ";
				if("F".equals(pkgModel.getVer_gubun())){
					content = content + "Full <br/>";
				}else{
					content = content + "Partial <br/>";
				}
				
				String pe_yn = "";
				if("Y".equals(pkgModel.getPe_yn())){
					pe_yn = "있음";
				}else{
					pe_yn = "없음";
				}
				
				content = content + "■ PKG 적용시 RM 검토 : " + pkgModel.getSer_content().replace("\n", "<br>").replace(" ", "&nbsp;") + "<br/>"
						 +"■ 서비스중단시간 : " + pkgModel.getSer_downtime() + "(분) <br/>"
						 +"■ 로밍 영향도 : " + roaming + "<br/>"
						 +"■ 우회소통 : " + bypass_traffic + "<br/>"
						 +"■ 과금영향도 : " + pe_yn + "<br/>"
						 +"■ 주요 보완내역 : " + pkgModel.getContent().replace("\n", "<br>").replace(" ", "&nbsp;") + "<br/>"
						 +"■ RM/CEM 관련 feature : " + pkgModel.getRm_issue_comment().replace("\n", "<br>").replace(" ", "&nbsp;");

				workSystemModel.setWork_content(content); //pkms_main
				
				workSystemModel.setTitle(total_title);	//제목 TITLE
				
				if(am_cnt > 0){
					workSystemModel.setTitle("[주간] "+ total_title);	//제목 TITLE
				}
				
				//iwcs-update
				WorkSystemModel model_bak = new WorkSystemModel();		
				model_bak = workSystemModel;
				//작업관리_업무PKMS DB연동 (insert) - PKGMGR_PKMS(본사 작업)
				
				workSystemService.create_pkms_main(workSystemModel);
				workSystemModel = model_bak;
				
//				if(am_cnt > 0){
//					model_bak.setSeq(am_seq);	//SEQ
//					
//					model_bak.setTitle(total_title + " 사전작업");	//제목 TITLE
//					model_bak.setWork_content(pkgStatusModel.getPrev_content()); // 작업내용 WORK_CONTENT
//					
//					model_bak.setWork_plandate_s(before_s_date); // 작업시작시간 WORK_PLANDATE_S  2013-03-25 02:00
//					model_bak.setWork_plandate_e(before_e_date); // 작업종료시간 WORK_PLANDATE_E  2013-03-25 07:00
//					
//					workSystemService.create_pkms_main(model_bak);
//				}
				
				//-------------------------(insert set) pkms_sub(본사)-------------------------
				//-------------------------------------공통부분 시작-------------------------------------
				String work_seq = String.valueOf(seq);
//				String am_work_seq = String.valueOf(am_seq);
				workSystemModel.setWork_seq(work_seq);//pkms_sub, pkms_target_system

				workSystemModel.setState("미승인");//승인 or 미승인
				
				workSystemModel.setWork_realdate_s(w_sTime);
				workSystemModel.setWork_realdate_e(w_eTime);
				
				workSystemModel.setWork_result("양호"); //pkms_sub
				workSystemModel.setWork_result_id(pkgModel.getSession_user_id()); //pkms_sub
				workSystemModel.setWork_result_name(pkgModel.getSession_user_name()); //pkms_sub
				
				workSystemModel.setOrg_gubun("SKT"); //pkms_sub

				//------------------------공통부분 끝--------------------
				
				List<WorkSystemModel> readTJList = workSystemService.readTJ(WorkSysMdl);
				
				if((readTJList != null) && (readTJList.size() > 0)){
					for(WorkSystemModel tjModel : readTJList){						
						workSystemModel.setMaster_id(tjModel.getMaster_id());
						workSystemModel.setMaster_name(tjModel.getConfirm_name());
						workSystemModel.setMaster_team_code("0000"+tjModel.getMaster_team_code());
						workSystemModel.setMaster_team_name(tjModel.getMaster_team_name());
						workSystemModel.setMaster_movetel(tjModel.getMaster_movetel());
					}
				}else{
					workSystemModel.setMaster_id(pkgModel.getSession_user_id());
					workSystemModel.setMaster_name(pkgModel.getSession_user_name());
					workSystemModel.setMaster_team_code(pkgModel.getSession_user_group_id());
					workSystemModel.setMaster_team_name(pkgModel.getSession_user_group_name());
					workSystemModel.setMaster_movetel(pkgModel.getSession_user_mobile_phone());
				}
				
				int sub_seq =0;
				WorkSystemModel workSeqModel = new WorkSystemModel();
				workSeqModel = workSystemService.read_SeqMax_Sub(workSeqModel);
				sub_seq = workSeqModel.getSeq() + 1;

				model_bak = workSystemModel;
				
				if("S".equals(gubun)) {//초도 - S
					workSystemModel.setWork_result("");
					workSystemModel.setWork_result_date_s(null);
					
					workSystemModel.setState("미승인");
					workSystemModel.setMaster_id("");
					workSystemModel.setMaster_name("");
					workSystemModel.setMaster_movetel("");
					
					WorkSystemModel work = new WorkSystemModel();
					work.setSystem_seq(pkgModel.getSystem_seq());
					work = workSystemService.read_Work_Info(work);
					
					workSystemModel.setWork_name(work.getWork_name());
					workSystemModel.setWork_phone(work.getWork_phone());
					workSystemModel.setWork_sosok(work.getWork_sosok());
					workSystemModel.setWork_confirm_name(null);
				}else{
					workSystemModel.setWork_name(pkgModel.getSession_user_name());
					workSystemModel.setWork_phone(pkgModel.getSession_user_mobile_phone());
					workSystemModel.setWork_sosok(pkgModel.getSession_user_group_name());
				}
				
				workSystemModel.setSeq(sub_seq);
				workSystemService.create_pkms_sub(workSystemModel);
				
				if("S".equals(gubun)) {
					workSystemModel.setSeq(sub_seq);
					
					workSystemModel.setState("승인");
					workSystemModel.setWork_confirm_name(pkgModel.getSession_user_name());
					workSystemModel.setWork_confirm_id(pkgModel.getSession_user_id());
					workSystemModel.setWork_confirm_date_s(workSystemModel.getMaster_date_s());
					
					WorkSystemModel TJMdl = new WorkSystemModel();
					TJMdl.setMaster_team_code(pkgModel.getSession_user_group_id().substring(4, 8));
					List<WorkSystemModel> readTJ = workSystemService.readTJ(TJMdl);
					if((readTJ != null) && (readTJ.size() > 0)){
						for(WorkSystemModel tjModel : readTJ){
							workSystemModel.setMaster_id(tjModel.getMaster_id());
							workSystemModel.setMaster_name(tjModel.getConfirm_name());
							workSystemModel.setMaster_team_code("0000"+tjModel.getMaster_team_code());
							workSystemModel.setMaster_team_name(tjModel.getMaster_team_name());
							workSystemModel.setMaster_movetel(tjModel.getMaster_movetel());
						}
					}else{
						workSystemModel.setMaster_id(pkgModel.getSession_user_id());
						workSystemModel.setMaster_name(pkgModel.getSession_user_name());
						workSystemModel.setMaster_team_code(pkgModel.getSession_user_group_id());
						workSystemModel.setMaster_team_name(pkgModel.getSession_user_group_name());
						workSystemModel.setMaster_movetel(pkgModel.getSession_user_mobile_phone());
					}
					
					sub_seq++;
					workSystemModel.setSeq(sub_seq);
					workSystemService.create_pkms_sub(workSystemModel);
				}
				
				workSystemModel = model_bak;
//				if(am_cnt > 0){
//					model_bak.setWork_seq(am_work_seq);//FK - PKGMGR_PKMS
//					
//					model_bak.setWork_realdate_s(before_s_date); //작업시작시간
//					model_bak.setWork_realdate_e(before_e_date); //작업종료시간
//					
//					sub_seq++;
//					workSystemModel.setSeq(sub_seq);
//					workSystemService.create_pkms_sub(model_bak);
//				}
				//-----------------------------(insert set) PKGMGR_PKMS_MASTER(본사) 끝-------------------------
				//등록 완료
			}
		}
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
	}
	
	/**
	 * PKG 일정 단계 진행 시 PKG로드맵 추가 및 수정
	 * */
	private void pkgRoadMap(PkgModel pkgModel) throws Exception{
		SysRoadMapModel model = new SysRoadMapModel();

		model.setSystem_seq(pkgModel.getSystem_seq());
		model.setPkg_seq(pkgModel.getPkg_seq());
		
		if(pkgModel.isTurn_down()){ //반려
			this.pkgRoadMap_delete(model);
		} 
		/*else { //정상
			if("3".equals(pkgModel.getSelected_status())) {//검증 완료 (코드 07)
				//시퀀스 찾아오기(값을 써야하기 때문에 insert시 nextval 을 안해준다
				model.setCode("07");
				this.pkgRoadMap_delete(model);
				
				String roadmapseq = systemDAO.roadMapSeqNext();
				
				model.setRoad_map_seq(roadmapseq);
				model.setReg_user(pkgModel.getSession_user_id());
				model.setContent(pkgModel.getTitle() + " 검증");
				model.setStart_date(pkgModel.getVerify_date_start());
				model.setEnd_date(pkgModel.getVerify_date_end());
				systemDAO.createRoadMapPkg(model);
				
				model.setTitle(pkgModel.getTitle() + " 검증");
				systemDAO.pkgCreateRoadMap(model);

			} else if("4".equals(pkgModel.getSelected_status()) || "8".equals(pkgModel.getSelected_status())) {//초도 일정 수립 (코드 08)
				model.setCode("08");
				this.pkgRoadMap_delete(model);
				
				String roadmapseq = systemDAO.roadMapSeqNext();
				model.setRoad_map_seq(roadmapseq);
				model.setReg_user(pkgModel.getSession_user_id());
				model.setContent(pkgModel.getTitle() + " 적용");
				
				if("4".equals(pkgModel.getSelected_status())){
					//날짜 정렬
					String[] work_dates = pkgModel.getWork_dates();
					String[] cwork_dates = WingsStringUtil.getNotNullStringArray(work_dates);
					
					Arrays.sort(cwork_dates);
					
					//제일 빠른 날짜
					model.setStart_date(cwork_dates[0]);
					//제일 느린 날짜
					model.setEnd_date(cwork_dates[cwork_dates.length-1]);
				}else{
					SysRoadMapModel searchModel = new SysRoadMapModel();
					searchModel = systemDAO.readPkgEquipmentDate(model);
					model.setStart_date(searchModel.getStart_date());
					model.setEnd_date(searchModel.getEnd_date());
				}
				systemDAO.createRoadMapPkg(model);
				
				model.setTitle(pkgModel.getTitle() + " 적용");
				systemDAO.pkgCreateRoadMap(model);
			}
		}*/
	}
	
	private void pkgRoadMap_delete(SysRoadMapModel model) throws Exception{
		List<SysRoadMapModel> seqList = systemDAO.readPkgRoadMapSeqList(model);
		
		if((null != seqList) && (seqList.size() > 0)){
			for(SysRoadMapModel seqModel : seqList){
				systemDAO.deleteRoadMap(seqModel);
				systemDAO.pkgDeleteRoadMap(seqModel);
			}
		}
	}
	
	/**pkg검증의 status*/
	public void createStatus4Verify(PkgStatusModel pkgStatusModel) throws Exception{
		pkgStatusDAO.create(pkgStatusModel);
	}
	public void updateStatus4Verify(PkgStatusModel pkgStatusModel) throws Exception{
		pkgStatusDAO.update(pkgStatusModel);
	}
	
}

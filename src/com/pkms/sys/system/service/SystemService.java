package com.pkms.sys.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.board.pkgVer.model.PkgVerModel;
import com.pkms.board.pkgVer.service.PkgVerService;
import com.pkms.board.pkgVer.service.PkgVerServiceIf;
import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.pkms.sys.common.model.SysRoadMapMappingModel;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.pkms.sys.common.model.SysRoadMapSubModel;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.dao.EquipmentDAO;
import com.pkms.sys.equipment.model.EquipmentUserModel;
import com.pkms.sys.equipment.service.EquipmentServiceIf;
import com.pkms.sys.equipment.service.EquipmentUserServiceIf;
import com.pkms.sys.idc.dao.IdcDAO;
import com.pkms.sys.idc.model.IdcModel;
import com.pkms.sys.system.dao.SystemDAO;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.sys.system.model.SystemUserModel.SYSTEM_USER_CHARGE_GUBUN;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;
import com.pkms.usermg.user.service.SktUserServiceIf;

/**
 * 
 * 
 * 시스템를 관리하는 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Service("SystemService")
public class SystemService implements SystemServiceIf {

	@Resource(name = "SystemDAO")
	private SystemDAO systemDAO;

	@Resource(name = "EquipmentService")
	private EquipmentServiceIf equipmentService;
	
	@Resource(name = "EquipmentUserService")
	private EquipmentUserServiceIf equipmentUserService;

	@Resource(name = "SystemUserService")
	private SystemUserServiceIf systemUserService;

	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;

	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;

	@Resource(name = "AttachFileService")
	private AttachFileServiceIf attachFileService;

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;
	
	@Resource(name = "PkgVerService")
	private PkgVerServiceIf pkgVerService;
	
	@Resource(name = "IdcDAO")
	private IdcDAO idcDAO;

	@Resource(name = "EquipmentDAO")
	private EquipmentDAO equipmentDAO;
	
	// @Resource(name = "PkgEquipmentService")
	// private PkgEquipmentServiceIf pkgEquipmentService;

	@Override
	public SysModel create(SysModel sysModel) throws Exception {

		/**
		 * 첨부 파일 처리
		 */
		attachFileService.create(sysModel, "SYSTEM_");

		String system_seq = systemDAO.readNextSeq();
		sysModel.setSystem_seq(system_seq);

		// 기본 정보 생성
		systemDAO.create(sysModel);
				
		// 시스템 담당자 생성
		createSystemUser(sysModel);

		return read(sysModel);
	}

	@Override
	public SysModel read(SysModel sysModel) throws Exception {

		SysModel sysModelData = new SysModel();
		if (StringUtils.hasLength(sysModel.getSystem_seq())) {
			

			// 기본 정보 조회
			sysModelData = systemDAO.read(sysModel);

			// 첨부 파일 정보 세팅
			attachFileService.read(sysModelData);

			// PKG 이력 조회
			PkgModel pkgModel = new PkgModel();
			pkgModel.setSystem_seq(sysModel.getSystem_seq());
			// PKG MASTER 목록 조회
			List<PkgModel> pkgList = pkgService.readListHistory(pkgModel);
			sysModelData.setPkgHistoryList(pkgList);

			SystemUserModel systemUserModel = new SystemUserModel();
			systemUserModel.setSystem_seq(sysModel.getSystem_seq());

			for (SYSTEM_USER_CHARGE_GUBUN charge_gubun : SYSTEM_USER_CHARGE_GUBUN.values()) {

				// 각 분야별 담당자 목록 정보 조회
				systemUserModel.setCharge_gubun(charge_gubun);
				List<?> systemUserList = systemUserService.readListSystemUser(systemUserModel);

				ArrayList<String> userIdList = new ArrayList<String>();
				ArrayList<String> userNameList = new ArrayList<String>();

				ArrayList<String> bpIdList = new ArrayList<String>();
				ArrayList<String> bpId1List = new ArrayList<String>();
				ArrayList<String> bpId2List = new ArrayList<String>();
				ArrayList<String> bpId3List = new ArrayList<String>();
				ArrayList<String> bpId4List = new ArrayList<String>();
				ArrayList<String> bpNameList = new ArrayList<String>();
				ArrayList<String> bpName1List = new ArrayList<String>();
				ArrayList<String> bpName2List = new ArrayList<String>();
				ArrayList<String> bpName3List = new ArrayList<String>();
				ArrayList<String> bpName4List = new ArrayList<String>();


				for (Object object : systemUserList) {
					SystemUserModel rUserModel = (SystemUserModel) object;

					// BP 담당자 상세 정보 조회
					if (SYSTEM_USER_CHARGE_GUBUN.BU.equals(charge_gubun)) {
						BpUserModel bpUserModel = new BpUserModel();
						bpUserModel.setBp_user_id(rUserModel.getUser_id());
						bpUserModel = bpUserService.read(bpUserModel);
						String buIdx  = rUserModel.getBu_idx();
						if ("1".equals(buIdx)) {
							bpId1List.add(rUserModel.getUser_id());
							bpName1List.add(bpUserModel.getBp_user_name());
						} else 
						if ("2".equals(buIdx)) {
							bpId2List.add(rUserModel.getUser_id());
							bpName2List.add(bpUserModel.getBp_user_name());
						} else 
						if ("3".equals(buIdx)) {
							bpId3List.add(rUserModel.getUser_id());
							bpName3List.add(bpUserModel.getBp_user_name());
						} else 
						if ("4".equals(buIdx)) {
							bpId4List.add(rUserModel.getUser_id());
							bpName4List.add(bpUserModel.getBp_user_name());
						} else {
							bpIdList.add(rUserModel.getUser_id());
							bpNameList.add(bpUserModel.getBp_user_name());
						}
						// SKT 담당자 상세 정보 조회
					} else {
						userIdList.add(rUserModel.getUser_id());
						SktUserModel sktUserModel = new SktUserModel();
						sktUserModel.setEmpno(rUserModel.getUser_id());
						sktUserModel = sktUserService.read(sktUserModel);
						userNameList.add(sktUserModel.getHname() + " [" + sktUserModel.getSosok() + "]");
					}
				}

				// 각 분야별 담당자 정보 세팅
				if (SYSTEM_USER_CHARGE_GUBUN.VU.equals(charge_gubun)) {
					sysModelData.setSel_sysUserVerifyId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserVerifyNames(userNameList.toArray(new String[0]));

				} else if (SYSTEM_USER_CHARGE_GUBUN.AU.equals(charge_gubun)) {
					sysModelData.setSel_sysUserApprovalId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserApprovalNames(userNameList.toArray(new String[0]));

				} else if (SYSTEM_USER_CHARGE_GUBUN.DV.equals(charge_gubun)) {
					sysModelData.setSel_devsysUserVerifyId(userIdList.toArray(new String[0]));
					sysModelData.setSel_devsysUserVerifyNames(userNameList.toArray(new String[0]));

				} else if (SYSTEM_USER_CHARGE_GUBUN.DA.equals(charge_gubun)) {
					sysModelData.setSel_devsysUserApprovalId(userIdList.toArray(new String[0]));
					sysModelData.setSel_devsysUserApprovalNames(userNameList.toArray(new String[0]));
					
				} else if (SYSTEM_USER_CHARGE_GUBUN.LV.equals(charge_gubun)) {
					sysModelData.setSel_locsysUserVerifyId(userIdList.toArray(new String[0]));
					sysModelData.setSel_locsysUserVerifyNames(userNameList.toArray(new String[0]));

				} else if (SYSTEM_USER_CHARGE_GUBUN.LA.equals(charge_gubun)) {
					sysModelData.setSel_locsysUserApprovalId(userIdList.toArray(new String[0]));
					sysModelData.setSel_locsysUserApprovalNames(userNameList.toArray(new String[0]));
					
				}else if (SYSTEM_USER_CHARGE_GUBUN.MO.equals(charge_gubun)) {
					sysModelData.setSel_sysUserMoId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserMoNames(userNameList.toArray(new String[0]));
				}else if (SYSTEM_USER_CHARGE_GUBUN.PU.equals(charge_gubun)) {
					sysModelData.setSel_sysUserBizId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserBizNames(userNameList.toArray(new String[0]));

				} else if (SYSTEM_USER_CHARGE_GUBUN.BU.equals(charge_gubun)) {
					if (bpIdList.size() > 0) {
						sysModelData.setSel_sysUserBpId(bpIdList.toArray(new String[0]));
						sysModelData.setSel_sysUserBpNames(bpNameList.toArray(new String[0]));
					}
					if (bpId1List.size() > 0) {
						sysModelData.setSel_sysUserBpId1(bpId1List.toArray(new String[0]));
						sysModelData.setSel_sysUserBpNames1(bpName1List.toArray(new String[0]));
					}
					if (bpId2List.size() > 0) {
						sysModelData.setSel_sysUserBpId2(bpId2List.toArray(new String[0]));
						sysModelData.setSel_sysUserBpNames2(bpName2List.toArray(new String[0]));
					}
					if (bpId3List.size() > 0) {
						sysModelData.setSel_sysUserBpId3(bpId3List.toArray(new String[0]));
						sysModelData.setSel_sysUserBpNames3(bpName3List.toArray(new String[0]));
					}
					if (bpId4List.size() > 0) {
						sysModelData.setSel_sysUserBpId4(bpId4List.toArray(new String[0]));
						sysModelData.setSel_sysUserBpNames4(bpName4List.toArray(new String[0]));
					}
				}else if (SYSTEM_USER_CHARGE_GUBUN.VO.equals(charge_gubun)) {
					sysModelData.setSel_sysUserVolId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserVolNames(userNameList.toArray(new String[0]));
				}else if (SYSTEM_USER_CHARGE_GUBUN.SE.equals(charge_gubun)) {
					sysModelData.setSel_sysUserSecId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserSecNames(userNameList.toArray(new String[0]));
				}else if (SYSTEM_USER_CHARGE_GUBUN.CH.equals(charge_gubun)) {
					sysModelData.setSel_sysUserChaId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserChaNames(userNameList.toArray(new String[0]));
				}else if (SYSTEM_USER_CHARGE_GUBUN.NO.equals(charge_gubun)) {
					sysModelData.setSel_sysUserNonId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserNonNames(userNameList.toArray(new String[0]));
				}else if (SYSTEM_USER_CHARGE_GUBUN.VA.equals(charge_gubun)) {
					sysModelData.setSel_sysUserVolApprovalId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserVolApprovalNames(userNameList.toArray(new String[0]));
				}else if (SYSTEM_USER_CHARGE_GUBUN.CA.equals(charge_gubun)) {
					sysModelData.setSel_sysUserChaApprovalId(userIdList.toArray(new String[0]));
					sysModelData.setSel_sysUserChaApprovalNames(userNameList.toArray(new String[0]));
				}
			}
		}
//		장비 국사 정보 받아오기
		
		IdcModel idcModel = new IdcModel();
		idcModel.setSystem_seq(sysModel.getSystem_seq());
		
		ArrayList<IdcModel> idcList = new ArrayList<IdcModel>();
		for (Object object : idcDAO.eq_readList(idcModel)) {
			if (object instanceof IdcModel) {
				idcList.add((IdcModel) object);
			}
		}
		sysModelData.setIdcList(idcList);
		return sysModelData;
	}

	@Override
	public List<?> readList(SysModel sysModel) throws Exception {
		return systemDAO.readList(sysModel);
	}

	@Override
	public List<SysModel> readList4User(SysModel sysModel) throws Exception {
		return systemDAO.readList4User(sysModel);
	}
	
	@Override
	public SysModel readUsersAppliedToSystem(SysModel sysModel) throws Exception {

		SysModel sysModelData = new SysModel();

		if (StringUtils.hasLength(sysModel.getSystem_seq())) {

			// 기본 정보 조회
			sysModelData = systemDAO.read(sysModel);
				
			SystemUserModel systemUserModel = new SystemUserModel();
			systemUserModel.setSystem_seq(sysModel.getSystem_seq());

			List<SYSTEM_USER_CHARGE_GUBUN> chargeGubunList = new ArrayList<SYSTEM_USER_CHARGE_GUBUN>();
			if (sysModel.getCharge_gubun() == null) {
				for (SYSTEM_USER_CHARGE_GUBUN charge_gubun : SYSTEM_USER_CHARGE_GUBUN.values()) {
					chargeGubunList.add(charge_gubun);
				}
			} else {
				chargeGubunList.add(sysModel.getCharge_gubun());
			}

			for (SYSTEM_USER_CHARGE_GUBUN charge_gubun : chargeGubunList) {

				// 각 분야별 담당자 목록 정보 조회
				systemUserModel.setCharge_gubun(charge_gubun);
				List<?> systemUserList = systemUserService.readListSystemUser(systemUserModel);
				
				for (Object object : systemUserList) {
					SystemUserModel readUserModel = (SystemUserModel) object;

					// 담당자 정보 세팅

					if (SYSTEM_USER_CHARGE_GUBUN.BU.equals(charge_gubun)) {
						// BP 사용자 상세 정보 조회
						BpUserModel bpUserModel = new BpUserModel();
						bpUserModel.setBp_user_id(readUserModel.getUser_id());
						bpUserModel = bpUserService.read(bpUserModel);
						// 담당자 상세 정보 세팅
						readUserModel.setUser_name(bpUserModel.getBp_user_name());
						readUserModel.setUser_email(bpUserModel.getBp_user_email());
						readUserModel.setUser_phone(bpUserModel.getBp_user_phone1() + "-" + bpUserModel.getBp_user_phone2() + "-" + bpUserModel.getBp_user_phone3());
						readUserModel.setSosok(bpUserModel.getBp_name());
						readUserModel.setBp_name(bpUserModel.getBp_name());
						
					} else {
						// SKT 사용자 상세 정보 조회
						SktUserModel sktUserModel = new SktUserModel();
						sktUserModel.setEmpno(readUserModel.getUser_id());
						sktUserModel = sktUserService.read(sktUserModel);
						// 담당자 상세 정보 세팅
						readUserModel.setUser_name(sktUserModel.getHname());
						readUserModel.setUser_email(sktUserModel.getEmail());
						readUserModel.setUser_phone(sktUserModel.getMovetelno());
						readUserModel.setSosok(sktUserModel.getSosok());
						readUserModel.setTsosok(sktUserModel.getTsosok());
						readUserModel.setIndept(sktUserModel.getIndept());
					}
					sysModelData.getSystemUserList().add(readUserModel);
				}
			}

			// 장비 목록 조회
			List<?> equipmentList = equipmentService.readList(sysModel);
			for (Object object : equipmentList) {
				SysModel equipment = (SysModel) object;

				// 장비 담당자 목록 정보 조회
				EquipmentUserModel equipmentUserModel = new EquipmentUserModel();
				equipmentUserModel.setEquipment_seq(equipment.getEquipment_seq());
				List<?> equipmentUserReadList = equipmentUserService.readList(equipmentUserModel);

				ArrayList<EquipmentUserModel> equipmentUserList = new ArrayList<EquipmentUserModel>();

				for (Object eObject : equipmentUserReadList) {
					EquipmentUserModel equipmentUser = (EquipmentUserModel) eObject;

					// SKT 담당자 상세 정보 조회
					SktUserModel sktUserModel = new SktUserModel();
					sktUserModel.setEmpno(equipmentUser.getUser_id());
					sktUserModel = sktUserService.read(sktUserModel);
					// 담당자 상세 정보 세팅
					equipmentUser.setUser_name(sktUserModel.getHname());
					equipmentUser.setUser_email(sktUserModel.getEmail());
					equipmentUser.setUser_phone(sktUserModel.getMovetelno());
					equipmentUser.setSosok((sktUserModel.getSosok()));
					equipmentUser.setTsosok(sktUserModel.getTsosok());
					equipmentUser.setIndept(sktUserModel.getIndept());
					
					equipmentUserList.add(equipmentUser);
				}
				sysModelData.getEquipmentUserMap().put(equipment, equipmentUserList);
			}
		}
		return sysModelData;
	}

	@Override
	public List<?> readListAppliedToUser(SystemUserModel systemUserModel) throws Exception {

		// 정렬 및 중복 제거을 위해 TreeMap 사용
		TreeMap<Integer, String> systemSeqMap = new TreeMap<Integer, String>();

		// 사용자에 해당하는 시스템 조회
		List<?> systemUserList = systemUserService.readListAppliedToUser(systemUserModel);
		for (Object object : systemUserList) {
			SystemUserModel userModel = (SystemUserModel) object;
			String systemSeq = userModel.getSystem_seq();
			// 시스템 키 기억
			systemSeqMap.put(Integer.parseInt(systemSeq), systemSeq);
		}

		// 사용자에 해당하는 장비 조회
		EquipmentUserModel equipmentUserModel = new EquipmentUserModel();
		equipmentUserModel.setUser_id(systemUserModel.getUser_id());
		equipmentUserModel.setUser_gubun(systemUserModel.getUser_gubun());
		List<?> equipmentUserList = equipmentUserService.readListAppliedToUser(equipmentUserModel);
		for (Object object : equipmentUserList) {
			EquipmentUserModel userModel = (EquipmentUserModel) object;
			SysModel equipment = new SysModel();
			equipment.setEquipment_seq(userModel.getEquipment_seq());
			equipment = equipmentService.readOnly(equipment);
			if (equipment != null) {
				String systemSeq = equipment.getSystem_seq();
				// 장비에 해당하는 시스템 키 기억
				try {
					systemSeqMap.put(Integer.parseInt(systemSeq), systemSeq);
				} catch (Exception e) {
				}
			}
		}

		ArrayList<SysModel> systemList = new ArrayList<SysModel>();

		// 시스템 상세 정보 조회
		for (String systemSeq : systemSeqMap.values()) {
			SysModel system = new SysModel();
			system.setSystem_seq(systemSeq);
			systemList.add(system);
			/*
			 * 상세정보는 이 메소드를 호출하는 다른 서비스들이 개별적으로 판단하여 본 서비스의 read 기능을 별도로
			 * 호출하여 사용하도록 변경.
			 */
			// systemList.add(read(system));
		}

		return systemList;
	}

	@Override
	public void update(SysModel sysModel) throws Exception {

		// 첨부 파일 정보 수정
		attachFileService.update(sysModel, "SYSTEM_");

		// 기본 정보 수정
		systemDAO.update(sysModel);

		// 시스템 담당자 삭제
		deleteSystemUser(sysModel);

		// 시스템 담당자 새로 생성
		createSystemUser(sysModel);
		
//		System.out.println("☆★☆★☆★length = "+sysModel.getSel_sysUserOperId().length);    장비 담당자 지정한 사람 잘 들어갔는지..
		if(sysModel.getSel_sysUserOperId().length > 0){
			List<?> equipmentList = equipmentDAO.eq_readList(sysModel);
			for (Object object : equipmentList) {
				SysModel equipment = (SysModel) object;
//				System.out.println("☆★☆★☆★Equipment_seq = "+equipment.getEquipment_seq());  장비시퀀스 받아왔는지 확인
				// 장비 담당자 삭제
				EquipmentUserModel equipmentUserModel = new EquipmentUserModel();
				equipmentUserModel.setEquipment_seq(equipment.getEquipment_seq());
				equipmentUserService.delete(equipmentUserModel);
				
				//장비 담당자 생성
				sysModel.setEquipment_seq(equipment.getEquipment_seq());
				equipmentService.createEquipmentUser(sysModel);
			}
			
		}
	}

	@Override
	public void delete(SysModel sysModel) throws Exception {

		/**
		 * 첨부 파일 삭제
		 */
//		attachFileService.delete(sysModel);

		// 기본 정보 삭제
		systemDAO.delete(sysModel);

		// 하위 정보 삭제
		List<?> subDeleteList = equipmentService.readList(sysModel);
		for (Object object : subDeleteList) {
			equipmentService.delete((SysModel) object);
		}

		// 시스템 담당자 삭제
		deleteSystemUser_u(sysModel);
	}

	private void deleteSystemUser(SysModel sysModel) throws Exception {
		SystemUserModel systemUserModel = new SystemUserModel();
		systemUserModel.setSystem_seq(sysModel.getSystem_seq());
		systemUserService.delete(systemUserModel);
	}
	
	private void deleteSystemUser_u(SysModel sysModel) throws Exception {
		SystemUserModel systemUserModel = new SystemUserModel();
		systemUserModel.setSystem_seq(sysModel.getSystem_seq());
		systemUserService.delete_u(systemUserModel);
	}

	private void createSystemUser(SysModel sysModel) throws Exception {

		ArrayList<SystemUserModel> systemUserList = new ArrayList<SystemUserModel>();

		// 각 분야별 담당자 정보 추출 및 세팅
		int order = 1;
		for (String userId : sysModel.getSel_devsysUserVerifyId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.DV, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_devsysUserApprovalId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.DA, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_sysUserVerifyId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.VU, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_sysUserApprovalId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.AU, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_locsysUserVerifyId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.LV, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_locsysUserApprovalId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.LA, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_sysUserMoId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.MO, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_sysUserBizId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.PU, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_sysUserBpId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.B, SYSTEM_USER_CHARGE_GUBUN.BU, "0");
		}
		//order = 1; // SYS_C005445 중복 발생 (CHARGE_GUBUN, ORD, SYSTEM_SEQ)
		for (String userId : sysModel.getSel_sysUserBpId1()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.B, SYSTEM_USER_CHARGE_GUBUN.BU, "1");
		}
		//order = 1;
		for (String userId : sysModel.getSel_sysUserBpId2()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.B, SYSTEM_USER_CHARGE_GUBUN.BU, "2");
		}
		//order = 1;
		for (String userId : sysModel.getSel_sysUserBpId3()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.B, SYSTEM_USER_CHARGE_GUBUN.BU, "3");
		}
		//order = 1;
		for (String userId : sysModel.getSel_sysUserBpId4()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.B, SYSTEM_USER_CHARGE_GUBUN.BU, "4");
		}
		
		order = 1;
		for (String userId : sysModel.getSel_sysUserVolId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.VO, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_sysUserSecId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.SE, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_sysUserChaId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.CH, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_sysUserNonId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.NO, "0");
		}
		
		order = 1;
		for (String userId : sysModel.getSel_sysUserVolApprovalId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.VA, "0");
		}
		order = 1;
		for (String userId : sysModel.getSel_sysUserChaApprovalId()) {
			setSystemUserModel(systemUserList, sysModel, order++, userId, USER_TYPE.M, SYSTEM_USER_CHARGE_GUBUN.CA, "0");
		}

		// 각 분야별 담당자 정보 생성
		if (systemUserList.size() > 0) {
			systemUserService.create(systemUserList);
		}

	}

	private void setSystemUserModel(ArrayList<SystemUserModel> systemUserList, SysModel sysModel, int order, String userId, USER_TYPE userType, SYSTEM_USER_CHARGE_GUBUN chargeGubun, String bu_idx) {
		SystemUserModel systemUserModel = new SystemUserModel();
		systemUserModel.setSystem_seq(sysModel.getSystem_seq());
		systemUserModel.setUser_gubun(userType);
		systemUserModel.setCharge_gubun(chargeGubun);
		systemUserModel.setOrd(String.valueOf(order));
		systemUserModel.setUser_id(userId);
		systemUserModel.setBu_idx(bu_idx);
		systemUserModel.setSession_user_name(sysModel.getSession_user_name());
		systemUserList.add(systemUserModel);
	}

	@Override
	public List<SysModel> readSystemSeq(SysModel sysModel) throws Exception {
		return systemDAO.readSystemSeq(sysModel);
	}
	
	@Override
	public void roadMapcreate(SysRoadMapModel sysRoadMapModel) throws Exception {
		System.out.println(sysRoadMapModel.getCodes().length);
		for (int i = 0; i < sysRoadMapModel.getCodes().length; i++) {
			SysRoadMapModel model = new SysRoadMapModel();
			model.setSystem_seq(sysRoadMapModel.getSystem_seq());
			model.setReg_user(sysRoadMapModel.getSession_user_id());
			model.setContent(sysRoadMapModel.getContents()[i]);
			model.setCode(sysRoadMapModel.getCodes()[i]);
			model.setStart_date(sysRoadMapModel.getStart_dates()[i]);
			model.setEnd_date(sysRoadMapModel.getEnd_dates()[i]);
			systemDAO.roadMapCreate(model);
		}
	}

	@Override
	public List<SysRoadMapModel> roadMapList(SysModel sysModel) {
		List<SysRoadMapModel> roadMapList = systemDAO.roadMapList(sysModel);
		List<SysRoadMapModel> list = new ArrayList<SysRoadMapModel>();
		if(roadMapList.size() != 0)
		list.add(roadMapList.get(0));
		
		for (int i = 1; i < roadMapList.size(); i++) {
			if(roadMapList.get(i).getCode().equals(roadMapList.get(i-1).getCode())){
				SysRoadMapSubModel model = new SysRoadMapSubModel();
								   model.setCode(roadMapList.get(i).getCode());
								   model.setRoad_map_seq(roadMapList.get(i).getRoad_map_seq());
								   model.setSystem_seq(roadMapList.get(i).getSystem_seq());
								   model.setNew_pn_cr_seq(roadMapList.get(i).getNew_pn_cr_seq());
								   model.setContent(roadMapList.get(i).getContent());
								   model.setStart_date(roadMapList.get(i).getStart_date());
								   model.setEnd_date(roadMapList.get(i).getEnd_date());
								   model.setReg_user(roadMapList.get(i).getReg_user());
								   model.setReg_date(roadMapList.get(i).getReg_date());
								   model.setUpdate_date(roadMapList.get(i).getUpdate_date());
								   model.setUpdate_user(roadMapList.get(i).getUpdate_user());
								   model.setMapping(roadMapList.get(i).getMapping());
								   model.setMap_code_seq(roadMapList.get(i).getMap_code_seq());
								   model.setCode_desc(roadMapList.get(i).getCode_desc());
								   model.setTooltip(roadMapList.get(i).getTooltip());
								   model.setName(roadMapList.get(i).getName());
								   model.setHname(roadMapList.get(i).getHname());
								   model.setMapping_list(roadMapList.get(i).getMapping_list());
								   
								   list.get(list.size()-1).getList().add(model);
			}else{
				list.add(roadMapList.get(i));
			}
			
//			roadMapList.get(i).setList(list);
		}
		
		return list;
	}

	@Override
	public SysRoadMapModel roadMapDetail(SysRoadMapModel sysRoadMapModel) {
		
		SysRoadMapModel sysRoadMapDetail = systemDAO.roadMapDetail(sysRoadMapModel);
		
		return sysRoadMapDetail;
	}
	
	@Override
	public void roadMapUpdate(SysRoadMapModel sysRoadMapModel) throws Exception {
		systemDAO.roadMapUpdate(sysRoadMapModel);
	}

	@Override
	public List<SysRoadMapMappingModel> roadMapMappingList(SysRoadMapModel sysRoadMapModel) {
		return systemDAO.roadMapMappingList(sysRoadMapModel);
	}

	@Override
	public void roadMapMappingDelete(SysRoadMapMappingModel sysRoadMapMappingModel) {
		systemDAO.roadMapMappingDelete(sysRoadMapMappingModel);
		
	}
	
	@Override
	public List<SysRoadMapModel> pkgRoadMapList(SysRoadMapModel sysRoadMapModel) {
		return systemDAO.pkgRoadMapList(sysRoadMapModel);
	}
	
	@Override
	public SysModel pkg_equipment_user(SysModel sysModel) throws Exception {
		SysModel sysModelData = new SysModel();
		
		List<?> equipmentList = equipmentService.idc_readList(sysModel);
		for (Object object : equipmentList) {
			SysModel equipment = (SysModel) object;

			// 장비 담당자 목록 정보 조회
			EquipmentUserModel equipmentUserModel = new EquipmentUserModel();
			equipmentUserModel.setIdc_seq(equipment.getIdc_seq());
			equipmentUserModel.setSystem_seq(equipment.getSystem_seq());
			List<?> equipmentUserReadList = equipmentUserService.idc_readList(equipmentUserModel);

			ArrayList<EquipmentUserModel> equipmentUserList = new ArrayList<EquipmentUserModel>();

			for (Object eObject : equipmentUserReadList) {
				EquipmentUserModel equipmentUser = (EquipmentUserModel) eObject;

				// SKT 담당자 상세 정보 조회
				SktUserModel sktUserModel = new SktUserModel();
				sktUserModel.setEmpno(equipmentUser.getUser_id());
				sktUserModel = sktUserService.read(sktUserModel);
				// 담당자 상세 정보 세팅
				equipmentUser.setUser_name(sktUserModel.getHname());
				equipmentUser.setUser_email(sktUserModel.getEmail());
				equipmentUser.setUser_phone(sktUserModel.getMovetelno());
				equipmentUser.setSosok((sktUserModel.getSosok()));
				equipmentUser.setTsosok(sktUserModel.getTsosok());
				equipmentUser.setIndept(sktUserModel.getIndept());
				
				equipmentUserList.add(equipmentUser);
			}
			sysModelData.getEquipmentUserMap().put(equipment, equipmentUserList);
		}
		
		return sysModelData;
	}

	@Override
	public List<SystemFileModel> systemFileReadList(SystemFileModel systemFileModel) throws Exception {
		
		return (List<SystemFileModel>)systemDAO.systemFileReadList(systemFileModel);
	}

	@Override
	public List<SystemFileModel> systemeListReadList(SystemFileModel systemFileModel) throws Exception {
		
		return (List<SystemFileModel>)systemDAO.systemeListReadList(systemFileModel);
	}
	
	
	@Override
	public boolean pkg_tree_list_add(SystemFileModel systemFileModel) throws Exception {
		
		try {
			systemDAO.pkg_tree_list_add(systemFileModel);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	@Override
	public boolean pkg_tree_list_update(SystemFileModel systemFileModel) throws Exception {
		try {
				systemDAO.pkg_tree_list_update(systemFileModel);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	
	@Override
	public boolean pkg_tree_list_delete(SystemFileModel systemFileModel) throws Exception {
		try {
				systemDAO.pkg_tree_list_delete(systemFileModel);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	
	@Override
	public int pkg_tree_list_idx(SystemFileModel systemFileModel) throws Exception {
		return systemDAO.pkg_tree_list_idx(systemFileModel);
	}

	
	@Override
	public void tree_file_add(SystemFileModel systemFileModel) throws Exception {
		attachFileService.tree_file_add(systemFileModel, "SYSTEM_");
	}
	
//	@Override
//	public void file_add(SystemFileModel systemFileModel) throws Exception {
//		attachFileService.file_add(systemFileModel, "PKG_");
//	}

	@Override
	public void tree_file_delete(SystemFileModel systemFileModel) throws Exception {
		attachFileService.tree_file_delete(systemFileModel);
	}

	@Override
	public List<SystemFileModel> tree_file_list(SystemFileModel systemFileModel) {
		return (List<SystemFileModel>)systemDAO.tree_file_list(systemFileModel);
	}

	@Override
	public int tree_file_move(SystemFileModel systemFileModel) {
		return attachFileService.tree_file_move(systemFileModel);
	}
	@Override
	public int tree_file_update(SystemFileModel systemFileModel) {
		return attachFileService.tree_file_update(systemFileModel);
	}
	
	
	
	
	
	@Override
	public String new_file_add(SystemFileModel systemFileModel,  String prefix) throws Exception {
		return attachFileService.new_file_add(systemFileModel, prefix);
	}
	
	@Override
	public String new_file_del(SystemFileModel systemFileModel) throws Exception {
		return attachFileService.new_file_del(systemFileModel);
	}	
	

//	@Override
//	public int engNameCheck(SystemFileModel systemFileModel) {
//		return  systemDAO.engNameCheck(systemFileModel);
//	}
	
	
	
	
	
	
}

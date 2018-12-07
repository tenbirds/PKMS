package com.pkms.sys.equipment.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.common.model.AbstractModel.USER_TYPE;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg.service.PkgServiceIf;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.dao.EquipmentDAO;
import com.pkms.sys.equipment.model.EquipmentHistoryModel;
import com.pkms.sys.equipment.model.EquipmentUserModel;
import com.pkms.sys.idc.model.IdcModel;
import com.pkms.sys.idc.service.IdcServiceIf;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.SktUserServiceIf;

/**
 * 
 * 
 * 시스템 중분류를 관리하는 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Service("EquipmentService")
public class EquipmentService implements EquipmentServiceIf {

	@Resource(name = "EquipmentDAO")
	private EquipmentDAO equipmentDAO;

	@Resource(name = "IdcService")
	private IdcServiceIf idcService;

	@Resource(name = "EquipmentUserService")
	private EquipmentUserServiceIf equipmentUserService;

	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;

	@Resource(name = "PkgService")
	private PkgServiceIf pkgService;

	@Override
	public SysModel create(SysModel sysModel) throws Exception {

		String equipment_seq = equipmentDAO.readNextSeq();
		sysModel.setEquipment_seq(equipment_seq);

		// 기본정보 생성
		equipmentDAO.create(sysModel);

		// 장비 담당자 생성
		createEquipmentUser(sysModel);

		return read(sysModel);
	}

	@Override
	public SysModel read(SysModel sysModel) throws Exception {

		SysModel sysModelData = new SysModel();

		if (StringUtils.hasLength(sysModel.getEquipment_seq())) {

			// 기본정보 조회
			sysModelData = equipmentDAO.read(sysModel);

			// 적용이력 조회
			List<PkgEquipmentModel> pkgEquipmentList = null;
			PkgModel pkgModel = new PkgModel();
			pkgModel.setSystem_seq(sysModel.getSystem_seq());
			pkgModel.setEq_history_yn("Y");
			// PKG MASTER 목록 조회
			List<PkgModel> pkgList = pkgService.readListHistory(pkgModel);
			
			for (PkgModel pkg : pkgList) {
				pkgEquipmentList = pkg.getPkgEquipmentModelList();
				for (PkgEquipmentModel pkgEquipment : pkgEquipmentList) {
					// 해당 장비와 일치하는 정보만 추출
					if (sysModel.getEquipment_seq().equals(pkgEquipment.getEquipment_seq())) {
						EquipmentHistoryModel equipmentHistoryModel = new EquipmentHistoryModel();
						equipmentHistoryModel.setPkg_ver(pkg.getVer());
						equipmentHistoryModel.setPkg_seq(pkg.getPkg_seq());
						equipmentHistoryModel.setPkg_title(pkg.getTitle());
						equipmentHistoryModel.setPkg_status_name(pkg.getStatus_name());
						equipmentHistoryModel.setVerify_date_start(pkg.getVerify_date_start());
						equipmentHistoryModel.setVerify_date_end(pkg.getVerify_date_end());
						equipmentHistoryModel.setWork_gubun(pkgEquipment.getWork_gubun());
						equipmentHistoryModel.setStart_time1(pkgEquipment.getStart_time1());
						equipmentHistoryModel.setStart_time2(pkgEquipment.getStart_time2());
						equipmentHistoryModel.setEnd_time1(pkgEquipment.getEnd_time1());
						equipmentHistoryModel.setEnd_time2(pkgEquipment.getEnd_time2());
						equipmentHistoryModel.setWork_date(pkgEquipment.getWork_date());
						sysModelData.getPkgEquipmentHistoryList().add(equipmentHistoryModel);
					}
				}
			}

			// 장비 담당자 목록 정보 조회
			EquipmentUserModel equipmentUserModel = new EquipmentUserModel();
			equipmentUserModel.setEquipment_seq(sysModel.getEquipment_seq());
			List<?> equipmentUserList = equipmentUserService.readList(equipmentUserModel);

			ArrayList<String> userIdList = new ArrayList<String>();
			ArrayList<String> userNameList = new ArrayList<String>();

			for (Object object : equipmentUserList) {
				EquipmentUserModel rUserModel = (EquipmentUserModel) object;
				userIdList.add(rUserModel.getUser_id());

				// 담당자 상세 정보 조회
				SktUserModel sktUserModel = new SktUserModel();
				sktUserModel.setEmpno(rUserModel.getUser_id());
				sktUserModel = sktUserService.read(sktUserModel);
				userNameList.add(sktUserModel.getHname() + " [" + sktUserModel.getSosok() + "]");
			}

			// 장비 담당자 정보 세팅
			sysModelData.setSel_sysUserOperId(userIdList.toArray(new String[0]));
			sysModelData.setSel_sysUserOperNames(userNameList.toArray(new String[0]));
		}

		ArrayList<IdcModel> idcList = new ArrayList<IdcModel>();
		for (Object object : idcService.readList(new IdcModel())) {
			if (object instanceof IdcModel) {
				idcList.add((IdcModel) object);
			}
		}
		sysModelData.setIdcList(idcList);

		return sysModelData;
	}

	public SysModel readOnly(SysModel sysModel) throws Exception {
		
		SysModel sysModelData = new SysModel();

		if (StringUtils.hasLength(sysModel.getEquipment_seq())) {
			// 기본정보 조회
			sysModelData = equipmentDAO.read(sysModel);
		}
		return sysModelData;
	}

	@Override
	public List<?> readList(SysModel sysModel) throws Exception {
		return equipmentDAO.readList(sysModel);
	}

	@Override
	public void update(SysModel sysModel) throws Exception {

		// 기본정보 수정
		equipmentDAO.update(sysModel);

		// 장비 담당자 삭제
		deleteEquipmentUser(sysModel);

		// 장비 담당자 새로 생성
		createEquipmentUser(sysModel);

	}

	@Override
	public void delete(SysModel sysModel) throws Exception {

		// 기본정보 삭제
		equipmentDAO.delete(sysModel);

		// 장비 담당자 삭제
		deleteEquipmentUser_u(sysModel);
	}
	
	@Override
	public void delete_real(SysModel sysModel) throws Exception {

		// 기본정보 삭제
		equipmentDAO.delete_real(sysModel);

		// 장비 담당자 삭제
		deleteEquipmentUser(sysModel);
	}

	public void createEquipmentUser(SysModel sysModel) throws Exception {

		ArrayList<EquipmentUserModel> equipmentUserList = new ArrayList<EquipmentUserModel>();

		// 각 분야별 담당자 정보 추출 및 세팅
		int order = 1;
		for (String userId : sysModel.getSel_sysUserOperId()) {
			setEquipmentUserModel(equipmentUserList, sysModel, order++, userId, USER_TYPE.M);
		}

		// 각 분야별 담당자 정보 생성
		if (equipmentUserList.size() > 0) {
			equipmentUserService.create(equipmentUserList);
		}
	}

	private void setEquipmentUserModel(ArrayList<EquipmentUserModel> equipmentUserList, SysModel sysModel, int order, String userId, USER_TYPE userType) {
		EquipmentUserModel equipmentUserModel = new EquipmentUserModel();
		equipmentUserModel.setEquipment_seq(sysModel.getEquipment_seq());
		equipmentUserModel.setUser_gubun(userType);
		equipmentUserModel.setOrd(String.valueOf(order));
		equipmentUserModel.setUser_id(userId);
		equipmentUserModel.setSession_user_name(sysModel.getSession_user_name());
		equipmentUserList.add(equipmentUserModel);
	}

	private void deleteEquipmentUser(SysModel sysModel) throws Exception {
		EquipmentUserModel equipmentUserModel = new EquipmentUserModel();
		equipmentUserModel.setEquipment_seq(sysModel.getEquipment_seq());
		equipmentUserService.delete(equipmentUserModel);
	}
	
	private void deleteEquipmentUser_u(SysModel sysModel) throws Exception {
		EquipmentUserModel equipmentUserModel = new EquipmentUserModel();
		equipmentUserModel.setEquipment_seq(sysModel.getEquipment_seq());
		equipmentUserService.delete_u(equipmentUserModel);
	}
	
	@Override
	public List<?> idc_readList(SysModel sysModel) throws Exception {
		return equipmentDAO.idc_readList(sysModel);
	}
}

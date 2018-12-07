package com.pkms.pkgmg.schedule.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.schedule.model.ScheduleModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * PKG검증/일정 > 일정목록<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
@Repository("ScheduleDAO")
public class ScheduleDAO extends IbatisAbstractDAO {

	/**
	 * Schedule 검증요청 목록 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ScheduleModel> readList(ScheduleModel model) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.readList", model);
	}

	/**
	 * Schedule 검증요청 총 개수 조회
	 * 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return int - 결과 개수
	 */
	public int readTotalCount(ScheduleModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("scheduleDAO.readTotalCount", model);
	}

	//excelDownList //scheduleDAO.excelDownList

	/**
	 * Schedule 리스트 엑셀 다운로드 
	 * @param UserModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<?> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> excelDownList(ScheduleModel model) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.excelDownList", model);
	}
	
	/**pkg검증일정-미완료건 7일마다 메일/sms 발송 추가  */
	/*@SuppressWarnings("unchecked")
	public  List<ScheduleModel> incompletePkgUserInfo(ScheduleModel scheduleModel) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.incompletePkgUserInfo", scheduleModel);
	}*/
	
	/*@SuppressWarnings("unchecked")
	public  List<ScheduleModel> readPkgVUAUList(ScheduleModel scheduleModel) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.readPkgVUAUList", scheduleModel);
	}
	
	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> readPkgSMinfo(ScheduleModel scheduleModel) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.readPkgSMinfo", scheduleModel);
	}*/
	
	/**pkg검증일정-미완료건 7일마다 메일/sms 발송 추가 1104 - ksy */
	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> incompletePkgList(String pkg_seq) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.incompletePkgList", pkg_seq);
	}
	
	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> incompletePkgSeq(ScheduleModel scheduleModel) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.incompletePkgSeq", scheduleModel);
	}
	
	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> emergencyPkgSeq(ScheduleModel scheduleModel) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.emergencyPkgSeq", scheduleModel);
	}
	
	/**이것만 사용 */
	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> incompletePkgUser(ScheduleModel scheduleModel) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.incompletePkgUser", scheduleModel);
	}

/*	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> incompletePkgList6(String pkg_seq) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.incompletePkgList6", pkg_seq);
	}
	
	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> incompletePkgSeq6(ScheduleModel scheduleModel) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.incompletePkgSeq6", scheduleModel);
	}

	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> incompletePkgSeq8(ScheduleModel scheduleModel) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.incompletePkgSeq8", scheduleModel);
	}
	
	@SuppressWarnings("unchecked")
	public  List<ScheduleModel> incompletePkgList8(String pkg_seq) throws Exception {
		return (List<ScheduleModel>) readList("scheduleDAO.incompletePkgList8", pkg_seq);
	}*/
	
	/**
	 * TEST BED 생성 및 삭제
	 * 
	 */
	public void create_testbed(List<ScheduleModel> list) throws Exception {
		create("scheduleDAO.create_testbed", list);
	}
	public void delete_testbed() throws Exception {
		delete("scheduleDAO.delete_testbed", null);
	}
	
	public void create_real(List<ScheduleModel> list) throws Exception {
		create("scheduleDAO.create_real", list);
	}
	public void delete_real() throws Exception {
		delete("scheduleDAO.delete_real", null);
	}
	
	/**
	 * group_depth 생성 및 삭제
	 * 
	 */
	public void create_group_depth(List<ScheduleModel> list) throws Exception {
		create("scheduleDAO.create_group_depth", list);
	}
	public void delete_group_depth() throws Exception {
		delete("scheduleDAO.delete_group_depth", null);
	}
	
	
}

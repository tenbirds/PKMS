package com.pkms.statsmg.stats.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.statsmg.stats.model.StatsModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * @author skywarker
 * 
 */
@Repository("StatsDAO")
public class StatsDAO extends IbatisAbstractDAO {

	/**
	 * 통계 조회
	 * 
	 * @param StatsModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<StatsModel> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StatsModel> readList(StatsModel model) throws Exception {
		return (List<StatsModel>) readList("StatsDAO.readList", model);
	}

	/**
	 * NEW/PN/CR 개수 조회
	 * 
	 * @param StatsModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<StatsModel> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StatsModel> readListPncr(StatsModel model) throws Exception {
		return (List<StatsModel>) readList("StatsDAO.readListPncr", model);
	}

	/**
	 * PKG 상세 검증 개수 조회
	 * 
	 * @param StatsModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<StatsModel> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StatsModel> readListPkgVerify(StatsModel model) throws Exception {
		return (List<StatsModel>) readList("StatsDAO.readListPkgVerify", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<StatsModel> equipment_readList(StatsModel model) throws Exception {
		return (List<StatsModel>) readList("StatsDAO.equipment_readList", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<StatsModel> equipment_idc_readList(StatsModel model) throws Exception {
		return (List<StatsModel>) readList("StatsDAO.equipment_idc_readList", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<StatsModel> equipment_cnt_readList(StatsModel model) throws Exception {
		return (List<StatsModel>) readList("StatsDAO.equipment_cnt_readList", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<StatsModel> equipment_group3_sum_readList(StatsModel model) throws Exception {
		return (List<StatsModel>) readList("StatsDAO.equipment_group3_sum_readList", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<StatsModel> equipment_group1_sum_readList(StatsModel model) throws Exception {
		return (List<StatsModel>) readList("StatsDAO.equipment_group1_sum_readList", model);
	}
	
	/**
	 * 통계 조회
	 * 
	 * @param StatsModel
	 *            - 초도, 확대 적용의 토탈 수
	 * @return List<StatsModel> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StatsModel> pkgEquipmentApply_total_count(StatsModel model) throws Exception {
		return (List<StatsModel>) readList("StatsDAO.pkgEquipmentApply_total_count", model);
	}
}

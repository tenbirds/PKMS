package com.pkms.statsmg.reports.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.statsmg.reports.model.WeeklyReportModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 
 * 
 * 분기별 보고서를 위한 DAO<br>
 * 
 * @author : skywarker
 * @Date : 2012. 6. 8.
 * 
 */
@Repository("WeeklyReportDAO")
public class WeeklyReportDAO extends IbatisAbstractDAO {

	/**
	 * PKG 주간 보고서 데이터 조회
	 * 
	 * @param WeeklyReportModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<WeeklyReportModel> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<WeeklyReportModel> readList(WeeklyReportModel model) throws Exception {
		return (List<WeeklyReportModel>) readList("WeeklyReportDAO.readList", model);
	}

	/**
	 * PKG 상세 검증 개수 조회
	 * 
	 * @param StatsModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<WeeklyReportDAO> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<WeeklyReportModel> readListPkgVerify(WeeklyReportModel model) throws Exception {
		return (List<WeeklyReportModel>) readList("WeeklyReportDAO.readListPkgVerify", model);
	}

}

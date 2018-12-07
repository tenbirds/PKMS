package com.pkms.statsmg.reports.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.statsmg.reports.model.QuarterlyReportModel;
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
@Repository("QuarterlyReportDAO")
public class QuarterlyReportDAO extends IbatisAbstractDAO {

	/**
	 * 분기별 보고서 데이터 조회
	 * 
	 * @param QuarterlyReportModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<QuarterlyReportModel> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<QuarterlyReportModel> readList(QuarterlyReportModel model) throws Exception {
		return (List<QuarterlyReportModel>) readList("QuarterlyReportDAO.readList", model);
	}

}

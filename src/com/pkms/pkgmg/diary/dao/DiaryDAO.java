package com.pkms.pkgmg.diary.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.diary.model.DiaryModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * PKG검증/일정 > 월별일정<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
@Repository("DiaryDAO")
public class DiaryDAO extends IbatisAbstractDAO {

	/**
	 * Diary 적용일정 목록 조회
	 * 
	 * @param DiaryModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<DiaryModel> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DiaryModel> readListApply(DiaryModel model) throws Exception {
		return (List<DiaryModel>) readList("DiaryDAO.readListApply", model);
	}

	/**
	 * 
	 * Diary 검증일정 목록 조회
	 * 
	 * @param DiaryModel
	 *            - 목록 조회 조건을 담은 모델
	 * @return List<DiaryModel> 결과 모델을 담은 list 객체
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DiaryModel> readListVerity(DiaryModel model) throws Exception {
		return (List<DiaryModel>) readList("DiaryDAO.readListVerity", model);
	}
	
	/**
	 * Excel 내려받기
	 * 
	 * @param DiaryModel
	 * @return List<DiaryModel>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<DiaryModel> readListApply_Excel(DiaryModel model) throws Exception {
		return (List<DiaryModel>) readList("DiaryDAO.readListApply_Excel", model);
	}
	@SuppressWarnings("unchecked")
	public List<DiaryModel> readListVerity_Excel(DiaryModel model) throws Exception {
		return (List<DiaryModel>) readList("DiaryDAO.readListVerity_Excel", model);
	}
	
}

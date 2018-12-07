package com.pkms.pkgmg.diary.service;

import com.pkms.pkgmg.diary.model.DiaryModel;

/**
 * PKG검증/일정 > 월별일정<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
public interface DiaryServiceIf {

	public void readList(DiaryModel diaryModel) throws Exception;

	public DiaryModel setSearchCondition(DiaryModel diaryModel) throws Exception;
	
	/**
	 * 목록 엑셀다운로드
	 * 
	 * @param diaryModel
	 * @return
	 * @throws Exception
	 */
	public String excelDownload(DiaryModel diaryModel) throws Exception;
}

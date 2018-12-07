package com.pkms.pkgmg.diary.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.pkgmg.diary.dao.DiaryDAO;
import com.pkms.pkgmg.diary.model.DiaryModel;
import com.pkms.pkgmg.diary.model.DiaryModel.DIARY_ITEM;

import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.ExcelUtil;

/**
 * PKG검증/일정 > 월별일정<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
@Service("DiaryService")
public class DiaryService implements DiaryServiceIf {

	@Resource(name = "DiaryDAO")
	private DiaryDAO diaryDAO;

	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;

	private final String DIARY_READLIST_SESSION_KEY = "DIARY_READLIST_SESSION_KEY";

	@Override
	public void readList(DiaryModel diaryModel) throws Exception {

		List<DiaryModel> resultList = new ArrayList<DiaryModel>();

		HashMap<String, List<DiaryModel>> diaryMap = new HashMap<String, List<DiaryModel>>();

		if (DIARY_ITEM.APPLY.equals(diaryModel.getDiaryItem())) {
			resultList = diaryDAO.readListApply(diaryModel);
			for (DiaryModel diary : resultList) {
				String pkg_seq = diary.getPkg_seq();
				String work_gubun = diary.getWork_gubun();
				String system_seq = diary.getSystem_seq();
				String work_date = diary.getWork_date();
				String key = pkg_seq + system_seq + work_gubun + work_date;
				List<DiaryModel> diaryList = diaryMap.get(key);
				if (diaryList == null) {
					diaryList = new ArrayList<DiaryModel>();
				}
				diaryList.add(diary);
				diaryMap.put(key, diaryList);
			}

		} else if (DIARY_ITEM.VERIFY.equals(diaryModel.getDiaryItem())) {
			resultList = diaryDAO.readListVerity(diaryModel);
			for (DiaryModel diary : resultList) {
				String pkg_seq = diary.getPkg_seq();
				String system_seq = diary.getSystem_seq();
				String verify_start_date = diary.getVerify_date_start();
				String verify_end_date = diary.getVerify_date_end();

				String key = pkg_seq + system_seq + verify_start_date + verify_end_date;
				List<DiaryModel> diaryList = diaryMap.get(key);
				if (diaryList == null) {
					diaryList = new ArrayList<DiaryModel>();
				}
				diaryList.add(diary);
				diaryMap.put(key, diaryList);
			}
		}
		// int index = 0;
		boolean finalFlag = false;
		
		String diaryScript = "[";
		for (List<DiaryModel> diaryList : diaryMap.values()) {
			if (diaryList.size() == 0) {
				continue;
			}
			for(DiaryModel fd : diaryList) {
				if(fd.getWork_date().equals(fd.getFinal_date())) {
					finalFlag = true;
				}
			}
			
			DiaryModel diary = diaryList.get(0);

			String pkg_seq = diary.getPkg_seq();
			String title = diary.getTitle();
			String start_date = "";
			String end_date = "";
			String description = "";
			
			String backgroundColor = diary.getGroup1_seq();

			if (DIARY_ITEM.APPLY.equals(diaryModel.getDiaryItem())) {
				description = "* 시스템: " + diary.getSystem_name() + "#";
				String work_gubun = diary.getWork_gubun();
				title = diary.getSystem_name() + ">" + title;
				if ("S".equals(work_gubun)) {
					title = "[초도] " + title;
					description += "* 적용: 초도#";
					
					/*
					 * 색깔 적용
					 * ICT = 9 = 초록색 = #beffc7
					 * ACCESS = 11 = 붉은색 = #fde9d9
					 * 통합전송 = 12 = 푸른색 = #beefff
					 */
					if("9".equals(diary.getGroup1_seq())){
						backgroundColor = "#b5fcb5";
					}else if("11".equals(diary.getGroup1_seq())){
						backgroundColor = "#fde9d9";
					}else if("12".equals(diary.getGroup1_seq())){
						backgroundColor = "#beefff";
					}else {
						backgroundColor = "#dcdcdc";
					}
				} else if ("E".equals(work_gubun)) {
					if(finalFlag) {
						title = "(최종)[확대] " + title;
						description += "* 적용: (최종)확대#";
					} else {
						title = "[확대] " + title;
						description += "* 적용: 확대#";
					}
					
					if("9".equals(diary.getGroup1_seq())){
						backgroundColor = "#b5fcb5";
					}else if("11".equals(diary.getGroup1_seq())){
						backgroundColor = "#fde9d9";
					}else if("12".equals(diary.getGroup1_seq())){
						backgroundColor = "#beefff";
					}else {
						backgroundColor = "#dcdcdc";
					}
				}
				start_date = end_date = diary.getWork_date();

			} else if (DIARY_ITEM.VERIFY.equals(diaryModel.getDiaryItem())) {
				description += "* 시스템: " + title + "#";
				start_date = diary.getVerify_date_start();
				end_date = diary.getVerify_date_end();
				if (!StringUtils.hasLength(end_date)) {
					end_date = start_date;
				}
				
				if("2".equals(diary.getStatus())){
					backgroundColor = "#ffdab9";
				}
			}
			description += "* 일정: " + start_date + " ~ " + end_date + "#";
			if (diaryList.size() > 1) {
				title = title + "외 " + diaryList.size();
			}
			if (DIARY_ITEM.APPLY.equals(diaryModel.getDiaryItem())) {
				description += "* 장비(팀) 목록 :#";
				for (DiaryModel dm : diaryList) {
					description += "&nbsp;&nbsp;- " + dm.getTitle() + " (" + dm.getTeam_name() + ")#";
				}
			}
			String url = "#";
			
			diaryScript += "{title: '" + title + "', start: '" + start_date + "', end: '" + end_date + "', " + //
					"id: '" + pkg_seq + "', " + //
					"url: '" + url + "', " + //
					"description: '" + description + "', " + //
					"backgroundColor: '" + backgroundColor + "', textColor:'#95361d', borderColor: '#f0d8c6'},";
		}
		if (diaryMap.size() > 0) {
			diaryScript = diaryScript.substring(0, (diaryScript.length() - 1));
		}
		diaryScript += "]";
		diaryModel.setDiaryScript(diaryScript);
	}

	@Override
	public DiaryModel setSearchCondition(DiaryModel diaryModel) throws Exception {
		if (diaryModel.isSessionCondition()) {
			DiaryModel sessionModel = (DiaryModel) sessionService.read(DIARY_READLIST_SESSION_KEY);
			if (sessionModel == null) {
				diaryModel = new DiaryModel();
			} else {
				diaryModel = sessionModel;
			}
		} else {
			sessionService.create(DIARY_READLIST_SESSION_KEY, diaryModel);
		}
		return diaryModel;
	}
	
	@Override
	public String excelDownload(DiaryModel diaryModel) throws Exception {
		//데이터
		diaryModel.setPaging(false);
		List<?> readList = new ArrayList<DiaryModel>();
		String downloadFileName="";
		if (DIARY_ITEM.APPLY.equals(diaryModel.getDiaryItem())) {
			readList = diaryDAO.readListApply_Excel(diaryModel);
			
			//Excel 헤더
			String[] headers = new String[]{"WORK_DATE", "PKG_TITLE", "SYSTEM_NAME", "TITLE", "TEAM_NAME", "WORK_GUBUN"};
			
			//Excel 데이터 추출
			@SuppressWarnings("unchecked")
			List<List<String>> excelDataList = ExcelUtil.extractExcelData((List<Object>) readList, headers);

			//파일 생성 후 다운로드할 파일명
			downloadFileName = ExcelUtil.write(diaryModel.EXCEL_FILE_NAME, propertyService.getString("Globals.fileStorePath"), new String[]{ "날짜","PKG_제목","시스템","장비","담당팀","일정구분"}, excelDataList);

		} else if (DIARY_ITEM.VERIFY.equals(diaryModel.getDiaryItem())) {
			readList = diaryDAO.readListVerity_Excel(diaryModel);

			//Excel 헤더
			String[] headers = new String[]{"VERIFY_DATE_START", "VERIFY_DATE_END", "PKG_TITLE", "TITLE"};
			
			//Excel 데이터 추출
			@SuppressWarnings("unchecked")
			List<List<String>> excelDataList = ExcelUtil.extractExcelData((List<Object>) readList, headers);
			
			//파일 생성 후 다운로드할 파일명
			downloadFileName = ExcelUtil.write(diaryModel.EXCEL_FILE_NAME, propertyService.getString("Globals.fileStorePath"), new String[]{ "검증시작일","검증종료일","PKG_제목","시스템"}, excelDataList);
		}
		 
		
		return downloadFileName;
	}
	
}

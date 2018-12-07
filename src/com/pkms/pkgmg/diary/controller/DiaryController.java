package com.pkms.pkgmg.diary.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.pkgmg.diary.model.DiaryModel;
import com.pkms.pkgmg.diary.service.DiaryServiceIf;

import com.pkms.common.util.ResultUtil;

/**
 * PKG검증/일정 > 월별일정<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
@Controller
public class DiaryController {

	@Resource(name = "DiaryService")
	private DiaryServiceIf diaryService;

	@RequestMapping(value = "/pkgmg/diary/Diary_ReadList.do")
	public String readList(DiaryModel diaryModel, Model model) throws Exception {

		// 세션에 저장된 조회 조건 처리
		diaryModel = diaryService.setSearchCondition(diaryModel);

		// diary script data Set
		diaryService.readList(diaryModel);

		model.addAttribute("DiaryModel", diaryModel);

		return "/pkgmg/diary/Diary_ReadList";
	}
	
	/**
	 * 목록 엑셀다운로드
	 * @param diaryModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pkgmg/diary/Diary_ExcelDownload.do")
	public String excelDownload(DiaryModel diaryModel, Model model) throws Exception {
		String str = diaryService.excelDownload(diaryModel);
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
}

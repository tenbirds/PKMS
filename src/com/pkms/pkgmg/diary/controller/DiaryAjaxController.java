package com.pkms.pkgmg.diary.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.diary.model.DiaryModel;
import com.pkms.pkgmg.diary.service.DiaryServiceIf;

/**
 * PKG검증/일정 > 월별일정<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
@Controller
public class DiaryAjaxController {

	@Resource(name = "DiaryService")
	private DiaryServiceIf diaryService;

	@RequestMapping(value = "/pkgmg/diary/Diary_Ajax_ReadList.do")
	public String readList(DiaryModel diaryModel, Model model) throws Exception {
		
		// 세션에 저장된 조회 조건 처리
		diaryModel = diaryService.setSearchCondition(diaryModel);
		
		// diary script data Set
		diaryService.readList(diaryModel);

		return ResultUtil.handleSuccessResultParam(model, diaryModel.getDiaryScript(), "");
	}
}

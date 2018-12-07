package com.pkms.statsmg.reports.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.schedule.model.ScheduleModel;
import com.pkms.statsmg.reports.model.QuarterlyReportModel;
import com.pkms.statsmg.reports.service.QuarterlyReportServiceIf;
import com.wings.util.DateUtil;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 비지니스 로직은 해당 클래서에서 구현되면 안되고 필요한 서비스를 호출하고 페이지 URL만 리턴 하도록 한다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 28.
 * 
 */
@Controller
public class QuarterlyReportController {

	@Resource(name = "QuarterlyReportService")
	private QuarterlyReportServiceIf quarterlyReportService;

	@RequestMapping(value = "/statsmg/reports/QuarterlyReport_ReadList.do")
	public String readList(QuarterlyReportModel quarterlyReportModel, Model model) throws Exception {

		// 기본 검색 기간 설정
		if (!StringUtils.hasLength(quarterlyReportModel.getStart_date()) || !StringUtils.hasLength(quarterlyReportModel.getEnd_date())) {
			quarterlyReportModel.setStart_date(DateUtil.formatDateByMonth(DateUtil.format(), -1));
			quarterlyReportModel.setEnd_date(DateUtil.dateFormat());
		}

		/*
		 * 통계 데이터 조회
		 */
		List<QuarterlyReportModel> quarterlyReportModelList = quarterlyReportService.readList(quarterlyReportModel);

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("QuarterlyReportModel", quarterlyReportModel);
		model.addAttribute("QuarterlyReportModelList", quarterlyReportModelList);

		return "/statsmg/reports/QuarterlyReport_ReadList";
	}
	
	@RequestMapping(value = "/statsmg/reports/QuarterlyReport_excelDown.do")
	public String excelDownload(QuarterlyReportModel quarterlyReportModel, Model model) throws Exception {
		//등록된 데이터 다운로드
		String str = quarterlyReportService.excelDownload(quarterlyReportModel);
		
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}

}

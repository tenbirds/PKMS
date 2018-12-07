package com.pkms.statsmg.reports.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.statsmg.reports.model.QuarterlyReportModel;
import com.pkms.statsmg.reports.model.WeeklyReportModel;
import com.pkms.statsmg.reports.service.WeeklyReportServiceIf;
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
public class WeeklyReportController {

	@Resource(name = "WeeklyReportService")
	private WeeklyReportServiceIf weeklyReportService;

	@RequestMapping(value = "/statsmg/reports/WeeklyReport_ReadList.do")
	public String readList(WeeklyReportModel weeklyReportModel, Model model) throws Exception {

		if (!StringUtils.hasLength(weeklyReportModel.getBase_date())) {
			weeklyReportModel.setBase_date(DateUtil.dateFormat());
		}
		
		// 현재기준 주의 끝 토요일 날짜
		String baseDate = DateUtil.getBaseDate(weeklyReportModel.getBase_date());

		 
		// baseDate 기준으로 실적 기준 날짜 세팅
		weeklyReportModel.setStart_date_before(DateUtil.formatDateByDayWeekly(baseDate, -6));
		weeklyReportModel.setEnd_date_before(baseDate);

		// 실적 조회
		weeklyReportModel.setStart_date(weeklyReportModel.getStart_date_before());
		weeklyReportModel.setEnd_date(weeklyReportModel.getEnd_date_before());
		List<WeeklyReportModel> weeklyReportModelListBefore = weeklyReportService.readList(weeklyReportModel);

		// baseDate 기준으로 계획 기준 날짜 세팅
		weeklyReportModel.setStart_date_after(DateUtil.formatDateByDay(baseDate, 1));
		weeklyReportModel.setEnd_date_after(DateUtil.formatDateByDay(baseDate, 7));

		// 계획 조회
		weeklyReportModel.setStart_date(weeklyReportModel.getStart_date_after());
		weeklyReportModel.setEnd_date(weeklyReportModel.getEnd_date_after());
		List<WeeklyReportModel> weeklyReportModelListAfter = weeklyReportService.readList(weeklyReportModel);

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("WeeklyReportModel", weeklyReportModel);
		model.addAttribute("WeeklyReportModelListBefore", weeklyReportModelListBefore);
		model.addAttribute("WeeklyReportModelListAfter", weeklyReportModelListAfter);

		return "/statsmg/reports/WeeklyReport_ReadList";
	}
	/**
	 * Excel Download
	 * @param weeklyReportModel
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/statsmg/reports/WeeklyReport_excelDown.do")
	public String excelDownload(WeeklyReportModel weeklyReportModel, Model model) throws Exception {
		//등록된 데이터 다운로드
		String str = weeklyReportService.excelDownload(weeklyReportModel);
		
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
/*
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private String getBaseDate(String datetime) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (datetime == null) {
			cal.setTime(new Date());
		} else {
			cal.setTime(DATE_FORMAT.parse(datetime));
		}
		cal.add(Calendar.DAY_OF_YEAR, +(7 - (getDayOfWeek(datetime))));
		return DATE_FORMAT.format(cal.getTime());
	}

	private int getDayOfWeek(String datetime) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (datetime == null) {
			cal.setTime(new Date());
		} else {
			cal.setTime(DATE_FORMAT.parse(datetime));
		}
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	private String formatDateByDay(String datetime, int day) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(DATE_FORMAT.parse(datetime));
		cal.add(Calendar.DAY_OF_YEAR, day);
		return DATE_FORMAT.format(cal.getTime());
	}*/

}

package com.pkms.statsmg.reports.service;

import java.util.List;

import com.pkms.statsmg.reports.model.QuarterlyReportModel;
import com.pkms.statsmg.reports.model.WeeklyReportModel;

public interface WeeklyReportServiceIf {

	public List<WeeklyReportModel> readList(WeeklyReportModel weeklyReportModel) throws Exception;
	public String excelDownload(WeeklyReportModel weeklyReportMode) throws Exception;

}

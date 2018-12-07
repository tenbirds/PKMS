package com.pkms.statsmg.reports.service;

import java.util.List;

import com.pkms.pkgmg.schedule.model.ScheduleModel;
import com.pkms.statsmg.reports.model.QuarterlyReportModel;

public interface QuarterlyReportServiceIf {

	public List<QuarterlyReportModel> readList(QuarterlyReportModel quarterlyReportModel) throws Exception;
	public String excelDownload(QuarterlyReportModel quarterlyReportModel) throws Exception;

}

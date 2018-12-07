package com.pkms.statsmg.stats.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pkms.statsmg.reports.model.QuarterlyReportModel;
import com.pkms.statsmg.stats.model.StatsModel;

public interface StatsServiceIf {

	public List<StatsModel> readList(StatsModel statsModel) throws Exception;
	public String excelDownload(StatsModel statsModel) throws Exception;

	public StatsModel setSearchCondition(StatsModel statsModel) throws Exception;
	
	public List<StatsModel> equipment_readList(StatsModel statsModel) throws Exception;
	public List<StatsModel> equipment_idc_readList(HttpServletRequest request, StatsModel statsModel) throws Exception;
	public List<StatsModel> equipment_cnt_readList(StatsModel statsModel) throws Exception;
	public List<StatsModel> equipment_group3_sum_readList(StatsModel statsModel) throws Exception;
	public List<StatsModel> equipment_group1_sum_readList(StatsModel statsModel) throws Exception;

	public String equipmentExcelDownload(StatsModel statsModel) throws Exception;
}

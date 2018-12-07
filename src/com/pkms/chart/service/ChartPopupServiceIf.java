package com.pkms.chart.service;

import java.util.List;

import com.pkms.statsmg.stats.model.StatsModel;

public interface ChartPopupServiceIf {
	
	public List<StatsModel> readList(StatsModel statsModel) throws Exception;

}

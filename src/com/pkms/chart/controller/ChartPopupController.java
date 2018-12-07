package com.pkms.chart.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.chart.service.ChartPopupServiceIf;
import com.pkms.statsmg.stats.model.StatsModel;

@Controller
public class ChartPopupController {
	
	@Resource(name = "ChartPopupService")
	private ChartPopupServiceIf chartPopupService;
	
	@RequestMapping(value = "/Chart_Popup_Read.do")
	public String read(StatsModel statsModel, Model model, HttpServletRequest request) throws Exception{

		List<StatsModel> statsModelList = chartPopupService.readList(statsModel);
		
		model.addAttribute("StatsModelList", statsModelList);
		model.addAttribute("StatsModel", statsModel);
		
		return "/chart/Chart_Popup_Read";
	}

}

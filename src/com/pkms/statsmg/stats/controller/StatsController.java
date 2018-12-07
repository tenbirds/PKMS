package com.pkms.statsmg.stats.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.statsmg.reports.model.WeeklyReportModel;
import com.pkms.statsmg.stats.model.StatsModel;
import com.pkms.statsmg.stats.service.StatsServiceIf;
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
public class StatsController {

	@Resource(name = "StatsService")
	private StatsServiceIf statsService;

	@RequestMapping(value = "/statsmg/stats/Stats_ReadList.do")
	public String readList(StatsModel statsModel, Model model) throws Exception {

		// 세션에 저장된 조회 조건 처리
		statsModel = statsService.setSearchCondition(statsModel);
		
		// 기본 검색 기간 설정
		if (!StringUtils.hasLength(statsModel.getStart_date()) || !StringUtils.hasLength(statsModel.getEnd_date())) {
			statsModel.setStart_date(DateUtil.formatDateByMonth(DateUtil.format(), -1));
			statsModel.setEnd_date(DateUtil.dateFormat());
		}

		/*
		 * 통계 데이터 조회
		 */
		List<StatsModel> statsModelList = statsService.readList(statsModel);

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("StatsModel", statsModel);
		model.addAttribute("StatsModelList", statsModelList);

		return "/statsmg/stats/Stats_ReadList";
	}
	/**
	 * Excel Download
	 * @param StatsController
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/statsmg/stats/Stats_excelDown.do")
	public String excelDownload(StatsModel statsModel, Model model) throws Exception {
		// 세션에 저장된 조회 조건 처리
		statsModel = statsService.setSearchCondition(statsModel);
		
		// 기본 검색 기간 설정
		if (!StringUtils.hasLength(statsModel.getStart_date()) || !StringUtils.hasLength(statsModel.getEnd_date())) {
			statsModel.setStart_date(DateUtil.formatDateByMonth(DateUtil.format(), -1));
			statsModel.setEnd_date(DateUtil.dateFormat());
		}
		//등록된 데이터 다운로드
		String str = statsService.excelDownload(statsModel);
		
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
	
	@RequestMapping(value = "/statsmg/stats/Equipment_current_stats_ReadList.do")
	public String equipment_readList(HttpServletRequest request, StatsModel statsModel, Model model) throws Exception {
		
		
		List<StatsModel> equipmentModelList = statsService.equipment_readList(statsModel);
//		
		model.addAttribute("StatsModel", statsModel);
		model.addAttribute("EquipmentModelList", equipmentModelList);
//		
//		//IDC
//		List<StatsModel> equipmentIdcModelList = statsService.equipment_idc_readList(request, statsModel);
//		
//		model.addAttribute("StatsModel", statsModel);
//		model.addAttribute("EquipmentIdcModelList", equipmentIdcModelList);
//		
//		//장비 개수
//		List<StatsModel> equipmentCntModelList = statsService.equipment_cnt_readList(statsModel);
//		
//		model.addAttribute("StatsModel", statsModel);
//		model.addAttribute("EquipmentCntModelList", equipmentCntModelList);
//		
//		//소계(소분류 기준)
//		List<StatsModel> equipmentGroup3SumModelList = statsService.equipment_group3_sum_readList(statsModel);
//		
//		model.addAttribute("StatsModel", statsModel);
//		model.addAttribute("EquipmentGroup3SumModelList", equipmentGroup3SumModelList);
//		
//		//소계(대분류 기준)
//		List<StatsModel> equipmentGroup1SumModelList = statsService.equipment_group1_sum_readList(statsModel);
//		
//		model.addAttribute("StatsModel", statsModel);
//		model.addAttribute("EquipmentGroup1SumModelList", equipmentGroup1SumModelList);
		
		return "/statsmg/stats/Equipment_current_stats_ReadList";
	}
	
	@RequestMapping(value = "/statsmg/stats/Equipment_excelDown.do")
	public String equipmentExcelDownload(StatsModel statsModel, Model model) throws Exception {

		String str = statsService.equipmentExcelDownload(statsModel);
		
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}

}

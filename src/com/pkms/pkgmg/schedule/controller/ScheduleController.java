package com.pkms.pkgmg.schedule.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.schedule.model.ScheduleModel;
import com.pkms.pkgmg.schedule.service.ScheduleServiceIf;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group1.service.Group1ServiceIf;
import com.pkms.sys.group2.service.Group2ServiceIf;
import com.wings.model.CodeModel;
import com.wings.util.DateUtil;

/**
 * PKG검증/일정 > 일정목록<br>
 * 
 * @author : scott
 * @Date : 2012. 4. 05.
 * 
 */
@Controller
public class ScheduleController {

	@Resource(name = "ScheduleService")
	private ScheduleServiceIf scheduleService;

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@Resource(name = "Group1Service")
	private Group1ServiceIf group1Service;

	@Resource(name = "Group2Service")
	private Group2ServiceIf group2Service;
	
	@RequestMapping(value = "/pkgmg/schedule/Schedule_ReadList.do")
	public String readList(ScheduleModel scheduleModel, Model model) throws Exception {

		// 세션에 저장된 조회 조건 처리
		scheduleModel = scheduleService.setSearchCondition(scheduleModel);

		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(scheduleModel);

		// 기본 검색 기간 설정
		if (!StringUtils.hasLength(scheduleModel.getDate_start()) || !StringUtils.hasLength(scheduleModel.getDate_end())) {
			scheduleModel.setDate_start(DateUtil.formatDateByMonth(DateUtil.format(), -1));
			scheduleModel.setDate_end(DateUtil.dateFormat());
		}

		List<ScheduleModel> scheduleModelList = scheduleService.readList(scheduleModel);
		paginationInfo.setTotalRecordCount(scheduleModel.getTotalCount());

		// Group1 List
		List<SysModel> group1List = group1Service.readList(new SysModel());
		ArrayList<CodeModel> group1Items = new ArrayList<CodeModel>();
		for (SysModel group1 : group1List) {
			CodeModel codeModel = new CodeModel();
			codeModel.setCode(group1.getGroup1_seq());
			codeModel.setCodeName(group1.getName());
			group1Items.add(codeModel);
		}

		// Group2 List
		ArrayList<CodeModel> group2Items = new ArrayList<CodeModel>();
		if(scheduleModel.getGroup1Condition() != null && !"".equals(scheduleModel.getGroup1Condition())) {
			SysModel sysModel = new SysModel();
			sysModel.setGroup1_seq(scheduleModel.getGroup1Condition());
			List<SysModel> group2List = group2Service.readList(sysModel);
			for (SysModel group2 : group2List) {
				CodeModel codeModel = new CodeModel();
				codeModel.setCode(group2.getGroup2_seq());
				codeModel.setCodeName(group2.getName());
				group2Items.add(codeModel);
			}
		}

		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("ScheduleModel", scheduleModel);
		model.addAttribute("ScheduleModelList", scheduleModelList);
		model.addAttribute("Group1List", group1Items);
		model.addAttribute("Group2List", group2Items);

		return "/pkgmg/schedule/Schedule_ReadList";
	}
	
	@RequestMapping(value = "/pkgmg/pkg/Schedule_ExcelDownload.do")
	public String excelDownload(ScheduleModel scheduleModel, Model model) throws Exception {
		//등록된 데이터 다운로드
		String str = scheduleService.excelDownload(scheduleModel);
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
}

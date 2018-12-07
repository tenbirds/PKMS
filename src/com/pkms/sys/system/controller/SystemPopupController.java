package com.pkms.sys.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.board.notice.model.NoticeModel;
import com.pkms.common.util.ResultUtil;
import com.pkms.sys.common.model.SysRoadMapMappingModel;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.service.SystemPopupServiceIf;
import com.pkms.sys.system.service.SystemServiceIf;
import com.sun.mail.imap.Utility.Condition;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 11.
 * 
 */
@Controller
public class SystemPopupController {

	@Resource(name = "SystemPopupService")
	private SystemPopupServiceIf systemPopupService;

	@Resource(name = "SystemService")
	private SystemServiceIf systemService;

	@RequestMapping(value = "/sys/system/System_Popup_Read.do")
	public String read(SysModel sysModel, Model model) throws Exception {
		sysModel.setSystem_popup_gubun("");
		systemPopupService.read(sysModel);
		model.addAttribute("SysModel", sysModel);

		return "/sys/system/System_Popup_Read";
	}
	
	//newpncr에서 등록시 시스템 두개 선택하기
	@RequestMapping(value = "/sys/system/System_Popup_Read2.do")
	public String read2(SysModel sysModel, Model model) throws Exception {
		sysModel.setSystem_popup_gubun("");
		systemPopupService.read(sysModel);
		model.addAttribute("SysModel", sysModel);

		return "/sys/system/System_Popup_Read2";
	}
	
	@RequestMapping(value = "/sys/system/System_Popup_Read_Detail.do")
	public String read_detail(SysModel sysModel, Model model) throws Exception {
		
		sysModel.setSystem_popup_gubun("Y");
		systemPopupService.read(sysModel);
		model.addAttribute("SysModel", sysModel);

		return "/sys/system/System_Popup_Read_detail";
	}

	@RequestMapping(value = "/sys/system/System_relation_Popup_Read_Detail.do")
	public String read_relation_detail(SysModel sysModel, Model model) throws Exception {
		
		sysModel.setSystem_popup_gubun("Y");
		systemPopupService.read(sysModel);
		model.addAttribute("SysModel", sysModel);

		return "/sys/system/System_Relation_Popup_Read";
	}	
	
	// 시스템 전체 선택용
	@RequestMapping(value = "/sys/system/System_Popup_Read_All.do")
	public String readAll(SysModel sysModel, Model model) throws Exception {
		sysModel.setSystem_popup_gubun("");
		sysModel.setUserCondition("1");
		systemPopupService.read(sysModel);
		
		model.addAttribute("SysModel", sysModel);
		return "/sys/system/System_Popup_Read_PkgVer";
	}
	
	@RequestMapping(value = "/sys/system/System_Popup_RoadMap.do")
	public String roadMap(SysModel sysModel, Model model) throws Exception {
		
		model.addAttribute("SysModel", sysModel);
		return "/sys/system/System_Popup_RoadMap";
	}
	
	@RequestMapping(value = "/sys/system/System_RoadMap_Ajax_Create.do")
	public String create(SysRoadMapModel sysRoadMapModel, Model model) throws Exception {
		
		systemService.roadMapcreate(sysRoadMapModel);
		
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/sys/system/System_Popup_RoadMapDetail.do")
	public String roadMapDetail(SysRoadMapModel sysRoadMapModel, Model model) throws Exception {
		
		
		model.addAttribute("sysRoadMapModel", systemService.roadMapDetail(sysRoadMapModel));
		model.addAttribute("sysRoadMapMappingList",systemService.roadMapMappingList(sysRoadMapModel));
		model.addAttribute("sysPkgRoadMapList",systemService.pkgRoadMapList(sysRoadMapModel));
		return "/sys/system/System_Popup_RoadMapDetail";
	}
	
	@RequestMapping(value = "/sys/system/System_RoadMap_Ajax_Update.do")
	public String update(SysRoadMapModel sysRoadMapModel, Model model) throws Exception {
		
		systemService.roadMapUpdate(sysRoadMapModel);
		
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/sys/system/System_RoadMap_Ajax_MappingDelete.do")
	public String update(SysRoadMapMappingModel sysRoadMapMappingModel, Model model) throws Exception {
		
		systemService.roadMapMappingDelete(sysRoadMapMappingModel);
		
		return ResultUtil.handleSuccessResult();
	}
	
}

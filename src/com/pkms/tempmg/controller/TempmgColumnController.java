package com.pkms.tempmg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.pkms.common.util.ResultUtil;
import com.pkms.tempmg.model.TempmgModel;
import com.pkms.tempmg.service.TempmgColumnServiceIf;

/**
 * 템플릿 항목별 Controller
 * @author : 009
 * @Date : 2012. 4. 20
 */
@Controller
public class TempmgColumnController {

	@Resource(name = "TempmgColumnService")
	private TempmgColumnServiceIf tempmgColumnService;

	@RequestMapping(value = "/tempmg/TempmgColumn_Ajax_Create.do")
	public String create(TempmgModel tempmgModel, Model model, SessionStatus status) throws Exception {
		tempmgModel = tempmgColumnService.create(tempmgModel);
		
        status.setComplete();
		return ResultUtil.handleSuccessResultParam(model, tempmgModel.getTpl_seq(), tempmgModel.getTpl_ver());
	}

	@RequestMapping(value = "/tempmg/TempmgColumn_Ajax_Read.do")
	public String read(TempmgModel tempmgModel, Model model) throws Exception {
		TempmgModel tempmgModelData = tempmgColumnService.read(tempmgModel);

		
		if (StringUtils.hasLength(tempmgModel.getTpl_seq()) && StringUtils.hasLength(tempmgModel.getOrd())) {
			tempmgModel = tempmgColumnService.read(tempmgModel);
		}
		
		model.addAttribute("TempmgModel", tempmgModelData);
		return "/tempmg/TempmgColumn_Ajax_Read";
	}

	@RequestMapping(value = "/tempmg/TempmgColumn_Ajax_ReadList.do")
	public String readList(TempmgModel tempmgModel, Model model) throws Exception {
		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> tempmgModelList = tempmgColumnService.readList(tempmgModel);

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("TempmgModel", tempmgModel);
		model.addAttribute("TempmgModelList", tempmgModelList);

		return "/tempmg/TempmgColumn_Ajax_ReadList";
	}

	@RequestMapping(value = "/tempmg/TempmgColumn_Ajax_Update.do")
	public String update(TempmgModel tempmgModel, Model model, SessionStatus status) throws Exception {
		tempmgColumnService.update(tempmgModel);
        status.setComplete();
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/tempmg/TempmgColumn_Ajax_Delete.do")
	public String delete(TempmgModel tempmgModel, Model model, SessionStatus status) throws Exception {
		tempmgColumnService.delete(tempmgModel);
        status.setComplete();
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/tempmg/TempmgColumn_ExcelDownload.do")
	public String excelDownload(TempmgModel tempmgModel, Model model) throws Exception {
		// 템플릿 다운로드
		String str = tempmgColumnService.excelDownload(tempmgModel);
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
}

package com.pkms.sys.group3.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group3.service.Group3ServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Controller
public class Group3AjaxController {

	@Resource(name = "Group3Service")
	private Group3ServiceIf group3Service;

	@RequestMapping(value = "/sys/group3/Group3_Ajax_Create.do")
	public String create(SysModel sysModel, Model model) throws Exception {
		SysModel sysModelData = group3Service.create(sysModel);
		return ResultUtil.handleSuccessResultParam(model, sysModelData.getGroup3_seq(), "");
	}

	@RequestMapping(value = "/sys/group3/Group3_Ajax_Read.do")
	public String read(SysModel sysModel, Model model) throws Exception {

		SysModel sysModelData = new SysModel();

		if (StringUtils.hasLength(sysModel.getGroup3_seq())) {
			sysModelData = group3Service.read(sysModel);
		}

		model.addAttribute("SysModel", sysModelData);

		return "/sys/group3/Group3_Ajax_Read";
	}

	@RequestMapping(value = "/sys/group3/Group3_Ajax_ReadList.do")
	public String readList(SysModel sysModel, Model model) throws Exception {

		List<?> sysModelList = group3Service.readList(sysModel);
		sysModel.setTotalCount(sysModelList.size());

		model.addAttribute("SysModel", sysModel);
		model.addAttribute("SysModelList", sysModelList);

		return "/sys/group3/Group3_Ajax_ReadList";
	}

	@RequestMapping(value = "/sys/group3/Group3_Ajax_Update.do")
	public String update(SysModel sysModel, Model model) throws Exception {
		group3Service.update(sysModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/sys/group3/Group3_Ajax_Delete.do")
	public String delete(SysModel sysModel, Model model) throws Exception {
		group3Service.delete(sysModel);
		return ResultUtil.handleSuccessResult();
	}

}

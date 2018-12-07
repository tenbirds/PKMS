package com.pkms.sys.group2.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group2.service.Group2ServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Controller
public class Group2AjaxController {

	@Resource(name = "Group2Service")
	private Group2ServiceIf group2Service;

	@RequestMapping(value = "/sys/group2/Group2_Ajax_Create.do")
	public String create(SysModel sysModel, Model model) throws Exception {
		SysModel sysModelData = group2Service.create(sysModel);
		return ResultUtil.handleSuccessResultParam(model, sysModelData.getGroup2_seq(), "");
	}

	@RequestMapping(value = "/sys/group2/Group2_Ajax_Read.do")
	public String read(SysModel sysModel, Model model) throws Exception {

		SysModel sysModelData = new SysModel();

		if (StringUtils.hasLength(sysModel.getGroup2_seq())) {
			sysModelData = group2Service.read(sysModel);
		}

		model.addAttribute("SysModel", sysModelData);

		return "/sys/group2/Group2_Ajax_Read";
	}

	@RequestMapping(value = "/sys/group2/Group2_Ajax_ReadList.do")
	public String readList(SysModel sysModel, Model model) throws Exception {

		List<?> sysModelList = group2Service.readList(sysModel);
		sysModel.setTotalCount(sysModelList.size());

		model.addAttribute("SysModel", sysModel);
		model.addAttribute("SysModelList", sysModelList);

		return "/sys/group2/Group2_Ajax_ReadList";
	}

	@RequestMapping(value = "/sys/group2/Group2_Ajax_Update.do")
	public String update(SysModel sysModel, Model model) throws Exception {
		group2Service.update(sysModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/sys/group2/Group2_Ajax_Delete.do")
	public String delete(SysModel sysModel, Model model) throws Exception {
		group2Service.delete(sysModel);
		return ResultUtil.handleSuccessResult();
	}

}

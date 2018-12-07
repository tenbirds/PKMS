package com.pkms.sys.equipment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.util.ResultUtil;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.equipment.service.EquipmentServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Controller
public class EquipmentAjaxController {

	@Resource(name = "EquipmentService")
	private EquipmentServiceIf equipmentService;

	@RequestMapping(value = "/sys/equipment/Equipment_Ajax_Create.do")
	public String create(SysModel sysModel, Model model) throws Exception {
		SysModel sysModelData = equipmentService.create(sysModel);
		return ResultUtil.handleSuccessResultParam(model, sysModelData.getEquipment_seq(), "");
	}

	@RequestMapping(value = "/sys/equipment/Equipment_Ajax_Read.do")
	public String read(SysModel sysModel, Model model) throws Exception {
		SysModel sysModelData = equipmentService.read(sysModel);
		model.addAttribute("SysModel", sysModelData);
		return "/sys/equipment/Equipment_Ajax_Read";
	}

	@RequestMapping(value = "/sys/equipment/Equipment_Ajax_ReadList.do")
	public String readList(SysModel sysModel, Model model) throws Exception {
		sysModel.setEq_history_yn("Y");
		List<?> sysModelList = equipmentService.readList(sysModel);
		sysModel.setTotalCount(sysModelList.size());

		model.addAttribute("SysModel", sysModel);
		model.addAttribute("SysModelList", sysModelList);

		return "/sys/equipment/Equipment_Ajax_ReadList";
	}

	@RequestMapping(value = "/sys/equipment/Equipment_Ajax_Update.do")
	public String update(SysModel sysModel, Model model) throws Exception {
		equipmentService.update(sysModel);
		return ResultUtil.handleSuccessResult();
	}
	
	/**
	 * 
	 * @param sysModel
	 * @param model
	 * @return
	 * @throws Exception
	 * 
	 * 철거 작업 - 기존 삭제 작업을 철거로 변경
	 */
	@RequestMapping(value = "/sys/equipment/Equipment_Ajax_Remove.do")
	public String remove(SysModel sysModel, Model model) throws Exception {
		equipmentService.delete(sysModel);
		return ResultUtil.handleSuccessResult();
	}
	
	/**
	 * 
	 * @param sysModel
	 * @param model
	 * @return
	 * @throws Exception
	 * 삭제는 완전삭제로 변경
	 * 기존 delete 는 flag 값 변경이므로 완전 삭제로 변경한다.
	 */
	@RequestMapping(value = "/sys/equipment/Equipment_Ajax_Delete.do")
	public String delete(SysModel sysModel, Model model) throws Exception {
		equipmentService.delete_real(sysModel);
		return ResultUtil.handleSuccessResult();
	}

}

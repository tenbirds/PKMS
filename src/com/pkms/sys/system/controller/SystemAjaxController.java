package com.pkms.sys.system.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.util.ResultUtil;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.common.model.SysRoadMapModel;
import com.pkms.sys.equipment.service.EquipmentServiceIf;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.sys.system.service.SystemServiceIf;

/**
 * Invoker 레이어에 해당하는 클래스로 Spring controller 이다.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Controller
public class SystemAjaxController {

	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "EquipmentService")
	private EquipmentServiceIf equipmentService;

	@RequestMapping(value = "/sys/system/System_Ajax_Create.do")
	public String create(SysModel sysModel, Model model) throws Exception {
		SysModel sysModelData = systemService.create(sysModel);
		return ResultUtil.handleSuccessResultParam(model, sysModelData.getSystem_seq(), "");
	}

	@RequestMapping(value = "/sys/system/System_Ajax_Read.do")
	public String read(SysModel sysModel, Model model) throws Exception {

		SysModel sysModelData = systemService.read(sysModel);
		
		List<?> equipmentList = equipmentService.readList(sysModelData);
		sysModelData.setTotalCount(equipmentList.size());
		List<SysRoadMapModel> roadMapList = systemService.roadMapList(sysModel);
		
		model.addAttribute("roadMapList", roadMapList);
		model.addAttribute("SysModel", sysModelData);
		
		return "/sys/system/System_Ajax_Read";
	}

	@RequestMapping(value = "/sys/system/System_Ajax_ReadList.do")
	public String readList(SysModel sysModel, Model model) throws Exception {

		List<?> sysModelList = systemService.readList(sysModel);
		sysModel.setTotalCount(sysModelList.size());

		model.addAttribute("SysModel", sysModel);
		model.addAttribute("SysModelList", sysModelList);

		return "/sys/system/System_Ajax_ReadList";
	}

	@RequestMapping(value = "/sys/system/System_Ajax_Update.do")
	public String update(SysModel sysModel, Model model) throws Exception {
		systemService.update(sysModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value = "/sys/system/System_Ajax_Delete.do")
	public String delete(SysModel sysModel, Model model) throws Exception {
		systemService.delete(sysModel);
		return ResultUtil.handleSuccessResult();
	}

	@RequestMapping(value="/sys/system/SystemFile_Ajax_Read.do")
	public String systemFileRead(SysModel sysModel, Model model) throws Exception{
		
		model.addAttribute("SysModel", sysModel);
		return "/sys/system/SystemFile_Ajax_Read";
	}
	
	@RequestMapping(value="/sys/system/SystemFileData_Ajax_Read.do")
	@ResponseBody
	public List<SystemFileModel> systemFileDataRead(SystemFileModel systemFileModel, Model model) throws Exception{
		return systemService.systemFileReadList(systemFileModel);
	}
	
	@RequestMapping(value="/sys/system/tree_file_add.do")
	@ResponseBody
	public void tree_file_add(SystemFileModel systemFileModel, Model model) throws Exception {
		systemService.tree_file_add(systemFileModel);
	}
	
	
	
	
	
	
	
	
//	new file add ing
	@RequestMapping(value="/common/attachfile/new_file_add.do")
	@ResponseBody
	public String new_file_add(SystemFileModel systemFileModel, Model model) throws Exception {

		String test ="";
		try {
			test = systemService.new_file_add(systemFileModel, systemFileModel.getPrefix());
		} catch (Exception e) {
			// TODO: handle exception
			test = "error";
		}
		
		return test;
	}
	
	
	
	@RequestMapping(value="/common/attachfile/new_file_del.do")
	@ResponseBody
	public String new_file_del(SystemFileModel systemFileModel, Model model) throws Exception {

		String test ="";
		try {
			test = systemService.new_file_del(systemFileModel);
		} catch (Exception e) {
			// TODO: handle exception
			test = "error";
		}
		
		return test;
	}
	
	
	
	
	
	@RequestMapping(value="/sys/system/SystemFileData_Ajax_Read2.do")
	@ResponseBody
	public List<SystemFileModel> SystemFileData_Ajax_Read2(SystemFileModel systemFileModel, Model model) throws Exception{
		// 파일 
		return systemService.systemeListReadList(systemFileModel);
	}

	@RequestMapping(value="/sys/system/pkg_tree_list_add.do")
	@ResponseBody
	public void pkg_tree_list_add(SystemFileModel systemFileModel, Model model) throws Exception {		
		systemFileModel.setOpen("true");
		systemFileModel.setIsParent("true");
		systemFileModel.setUse_yn("Y");
		
		if( "NEW".equals(systemFileModel.getId())) { //아닐경우 전달받아온다.
			try {// 현재 DB 상의 마지막 id 를 확인
				int idx = systemService.pkg_tree_list_idx(systemFileModel);		
				idx++;
				systemFileModel.setId(String.valueOf(idx));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ID serch ERROR");
				System.out.println(e);
			}//end try
			
			try {
				systemService.pkg_tree_list_add(systemFileModel);					
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("insert ERROR");
				System.out.println(e);
			}//end try
			
		}else {
			try {
				systemService.pkg_tree_list_update(systemFileModel);
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("update ERROR");
				System.out.println(e);

			}//end try
			
		}//end else

	}
	
	
	@RequestMapping(value="/sys/system/pkg_tree_list_delete.do")
	@ResponseBody
	public void pkg_tree_list_delete(SystemFileModel systemFileModel, Model model) throws Exception {		
		
		try {
				systemService.pkg_tree_list_delete(systemFileModel);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	
	
	
	
//	@RequestMapping(value="/sys/system/file_add.do")
//	@ResponseBody
//	public void file_add(SystemFileModel systemFileModel, Model model) throws Exception {
//		systemService.file_add(systemFileModel);
//	}
	
	@RequestMapping(value="/sys/system/tree_file_delete.do")
	@ResponseBody
	public void tree_file_delete(SystemFileModel systemFileModel, Model model) throws Exception {
		systemService.tree_file_delete(systemFileModel);
	}
	
	@RequestMapping(value="/sys/system/tree_file_list.do")
	@ResponseBody
	public List<SystemFileModel> tree_file_list(SystemFileModel systemFileModel, Model model) throws Exception {
		return systemService.tree_file_list(systemFileModel);
	}
	
	@RequestMapping(value="/sys/system/tree_file_move.do")
	@ResponseBody
	public int tree_file_move(SystemFileModel systemFileModel, Model model) throws Exception {
		return systemService.tree_file_move(systemFileModel);
	}
	
	@RequestMapping(value="/sys/system/tree_file_update.do")
	@ResponseBody
	public int tree_file_update(SystemFileModel systemFileModel, Model model) throws Exception {
		return systemService.tree_file_update(systemFileModel);
	}
	
	
}

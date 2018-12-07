package com.pkms.sys.file.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.file.model.FileModel;
import com.pkms.sys.file.service.FileServiceIf;
import com.pkms.sys.group1.service.Group1ServiceIf;
import com.pkms.sys.group2.service.Group2ServiceIf;
import com.pkms.sys.group3.service.Group3ServiceIf;
import com.pkms.sys.system.service.SystemServiceIf;
import com.pkms.sys.tree.model.TreeModel;
import com.pkms.sys.tree.service.TreeServiceIf;
import com.wings.model.CodeModel;

@Controller
public class FileController {
	
	@Resource(name = "FileService")
	private FileServiceIf fileService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	@Resource(name = "Group1Service")
	private Group1ServiceIf group1Service;

	@Resource(name = "Group2Service")
	private Group2ServiceIf group2Service;
	
	@Resource(name = "Group3Service")
	private Group3ServiceIf group3Service;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;
	
	@Resource(name = "TreeService")
	private TreeServiceIf treeService;
	
	@RequestMapping(value = "/sys/file/File_ReadList.do")
	public String readList(FileModel fileModel, Model model) throws Exception {
		
		List<?> fileModelList = fileService.readList(fileModel);
		
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
		if(fileModel.getGroup1Condition() != null && !"".equals(fileModel.getGroup1Condition())) {
			SysModel sysModel = new SysModel();
			sysModel.setGroup1_seq(fileModel.getGroup1Condition());
			List<SysModel> group2List = group2Service.readList(sysModel);
			for (SysModel group2 : group2List) {
				CodeModel codeModel = new CodeModel();
				codeModel.setCode(group2.getGroup2_seq());
				codeModel.setCodeName(group2.getName());
				group2Items.add(codeModel);
			}
		}
		
		// Group3 List
		ArrayList<CodeModel> group3Items = new ArrayList<CodeModel>();
		if(fileModel.getGroup2Condition() != null && !"".equals(fileModel.getGroup2Condition())) {
			SysModel sysModel = new SysModel();
			sysModel.setGroup2_seq(fileModel.getGroup2Condition());
			List<SysModel> group3List = group3Service.readList(sysModel);
			for (SysModel group3 : group3List) {
				CodeModel codeModel = new CodeModel();
				codeModel.setCode(group3.getGroup3_seq());
				codeModel.setCodeName(group3.getName());
				group3Items.add(codeModel);
			}
		}
		
		// System List
		ArrayList<CodeModel> systemItems = new ArrayList<CodeModel>();
		if(fileModel.getGroup3Condition() != null && !"".equals(fileModel.getGroup3Condition())) {
			SysModel sysModel = new SysModel();
			sysModel.setGroup3_seq(fileModel.getGroup3Condition());
			List<SysModel> sysModelList = (List<SysModel>) systemService.readList(sysModel);
			for (SysModel system : sysModelList) {
				CodeModel codeModel = new CodeModel();
				codeModel.setCode(system.getSystem_seq());
				codeModel.setCodeName(system.getName());
				systemItems.add(codeModel);
			}
		}
		
		// file gubun List
		TreeModel treeModel = new TreeModel();
		treeModel.setId(1);
		ArrayList<CodeModel> fileGubunItems = new ArrayList<CodeModel>();
		List<TreeModel> fileGubunList = (List<TreeModel>) treeService.readList(treeModel);
		for (TreeModel tree : fileGubunList) {
			CodeModel codeModel = new CodeModel();
			codeModel.setCode(String.valueOf(tree.getId()));
			codeModel.setCodeName(tree.getName());
			fileGubunItems.add(codeModel);
		}
		
		
		model.addAttribute("Group1List", group1Items);
		model.addAttribute("Group2List", group2Items);
		model.addAttribute("Group3List", group3Items);
		model.addAttribute("SystemList", systemItems);
		model.addAttribute("fileGubunList", fileGubunItems);
		
		model.addAttribute("fileModelList", fileModelList);
		
		model.addAttribute("FileModel", fileModel);
		
		return "/sys/file/File_ReadList";
	}

	
	@RequestMapping(value = "/sys/file/Group2_Select_Ajax_Read.do")
	public String group2_select(FileModel fileModel, Model model) throws Exception {
		SysModel sysModel = new SysModel();
		sysModel.setGroup1_seq(fileModel.getGroup1Condition());
		List<SysModel> group2List = group2Service.readList(sysModel);

		model.addAttribute("SysModelList", group2List);
		return "/pkgmg/common/Group2_Select_Ajax_ReadList";
	}
	
	@RequestMapping(value = "/sys/file/Group3_Select_Ajax_Read.do")
	public String group3_select(FileModel fileModel, Model model) throws Exception {
		SysModel sysModel = new SysModel();
		sysModel.setGroup2_seq(fileModel.getGroup2Condition());
		List<SysModel> group3List = group3Service.readList(sysModel);

		model.addAttribute("SysModelList", group3List);
		return "/pkgmg/common/Group3_Select_Ajax_ReadList";
	}
	
	@RequestMapping(value = "/sys/file/System_Select_Ajax_Read.do")
	public String system_select(FileModel fileModel, Model model) throws Exception {
		SysModel sysModel = new SysModel();
		sysModel.setGroup3_seq(fileModel.getGroup3Condition());
		List<SysModel> systemList = (List<SysModel>) systemService.readList(sysModel);

		model.addAttribute("SysModelList", systemList);
		return "/sys/system/common/System_Select_Ajax_ReadList";
	}
	
	@RequestMapping(value = "/sys/file/Mail_Popup_Send.do")
	public String mail_send(FileModel fileModel, Model model) throws Exception {
		List<FileModel> nameList = fileService.nameList(fileModel);
		List<FileModel> gubunList = fileService.gubunList(fileModel);
		
		model.addAttribute("nameList", nameList);
		model.addAttribute("gubunList", gubunList);
		model.addAttribute("FileModel", fileModel);
		return "/sys/file/File_Mail_Popup_Send";
	}
	
	@RequestMapping(value = "/sys/file/Mail_Ajax_Send.do")
	public String send(FileModel fileModel, Model model) throws Exception {
		fileService.mailSend(fileModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/sys/file/File_Confirm_YN.do")
	public String file_confirm(FileModel fileModel, Model model) throws Exception {
		fileService.confirmUpdate(fileModel);
		return ResultUtil.handleSuccessResult();
	}
	
	
	@RequestMapping(value = "/sys/file/file_ExcelDownload.do")
	public String fileExcelDownload(FileModel fileModel, Model model) throws Exception {
		String str = fileService.fileExcelDownload(fileModel);
		return ResultUtil.handleSuccessResultParam(model, str, "");
	}
	
	
	
	/*
	@RequestMapping(value = "/board/notice/Notice_ReadList.do")
	public String readList(NoticeModel noticeModel, Model model) throws Exception {
		
		
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(noticeModel);

		List<?> noticeModelList = noticeService.readList(noticeModel);
		paginationInfo.setTotalRecordCount(noticeModel.getTotalCount());

		model.addAttribute("noticeModelList", noticeModelList);
		model.addAttribute("noticeModel", noticeModel);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "/board/notice/Notice_ReadList";
	}
	
	@RequestMapping(value = "/board/notice/Notice_Read.do")
	public String read(NoticeModel noticeModel, Model model) throws Exception {
		
		String retUrl = noticeModel.getRetUrl();
		if ("".equals(retUrl)) {
			throw new Exception("error.biz.URL이 정의 되어야 합니다.");
		}else if("/board/notice/Notice_Read".equals(retUrl)){
			List<String> authList = sessionService.readAuth();
			int check_cnt=0; // 권한체크 count
			for(String key : authList){
				if(key.equals("ROLE_ADMIN") || key.equals("ROLE_MANAGER")){
					check_cnt ++;
				}
			}
			if(check_cnt == 0){
				throw new Exception("error.biz.공지사항을 작성할 권한이 없습니다.");
			}
		}

		NoticeModel noticeModelData = new NoticeModel();
		
		if (StringUtils.hasLength(noticeModel.getNotice_seq())) {
			noticeModelData = noticeService.read(noticeModel);
		}
		
		noticeModelData.setSearchCondition(noticeModel.getSearchCondition());
		noticeModelData.setSearchKeyword(noticeModel.getSearchKeyword());
		noticeModelData.setPageIndex(noticeModel.getPageIndex());
		
		
		model.addAttribute("NoticeModel", noticeModelData);

		return retUrl;
	}
	
	@RequestMapping(value = "/board/notice/Notice_Create.do")
	public String create(NoticeModel noticeModel, Model model) throws Exception {
		noticeService.create(noticeModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/notice/Notice_Ajax_Update.do")
	public String update(NoticeModel noticeModel, Model model) throws Exception {
		noticeService.update(noticeModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/notice/Notice_Ajax_Delete.do")
	public String delete(NoticeModel noticeModel, Model model) throws Exception {
		noticeService.delete(noticeModel);
		return ResultUtil.handleSuccessResult();
	}

	
	*/
	
}

package com.pkms.tempmg.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.sys.system.service.SystemServiceIf;
import com.pkms.tempmg.model.DocumentmgModel;
import com.pkms.tempmg.service.DocumentmgServiceIf;


@Controller
public class DocumentmgController {

	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@Resource(name = "DocumentmgService")
	private DocumentmgServiceIf documentmgService;
	
	@Resource(name = "SystemService")
	private SystemServiceIf systemService;

//	@RequestMapping(value = "/tempmg/Documentmg_ReadList.do")
	@RequestMapping(value = "/board/Documentmg_ReadList.do")
	public String documentmg_ReadList(DocumentmgModel documentmgModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(documentmgModel);
		List<DocumentmgModel> readList = (List<DocumentmgModel>) documentmgService.readList(documentmgModel);
		
		paginationInfo.setTotalRecordCount(documentmgModel.getTotalCount());
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("DocumentmgReadList", readList);
		
		return "/tempmg/Documentmg_ReadList";
	}
	
	@RequestMapping(value = "/board/Documentmg_Read.do")
	public String documentmg_Read(DocumentmgModel documentmgModel, Model model) throws Exception {
		
		if(StringUtils.hasLength(documentmgModel.getDoc_seq())  &&   (!"new".equals(documentmgModel.getDoc_seq()) ) ){
			documentmgModel = documentmgService.readone(documentmgModel);
			model.addAttribute("DocumentmgRead", documentmgModel);
		}else {
			model.addAttribute("DocumentmgRead", documentmgModel);
		}
		
		return "/tempmg/Documentmg_Read";
	}
	
	
	
	@RequestMapping(value = "/tempmg/documentmg_Create.do")
	public String documentmg_Create(DocumentmgModel documentmgModel, Model model, HttpServletRequest request ) throws Exception {

		//파일정보제외한 정보 
		if( StringUtils.hasLength(documentmgModel.getDoc_seq())  &&   (!"new".equals(documentmgModel.getDoc_seq())) ){
			documentmgService.update(documentmgModel);
		}else {
			String doc_seq = Integer.toString(documentmgService.docfileIdx(documentmgModel));
			documentmgModel.setDoc_seq(doc_seq);
			documentmgModel.setReg_user_id(documentmgModel.getSession_user_id());
			documentmgModel = documentmgService.insert(documentmgModel);
		}
		
		return ResultUtil.handleSuccessResult();
	}
	
	
	
//	new file add ing
	@RequestMapping(value="/tempmg/doc_file_add.do")
	@ResponseBody
	public String new_file_add(SystemFileModel systemFileModel, Model model) throws Exception {

		String test ="";
		try {
			test = documentmgService.doc_file_add(systemFileModel, systemFileModel.getPrefix());
		} catch (Exception e) {
			// TODO: handle exception
			test = "error";
		}
		return test;
	}
	
	
	
	@RequestMapping(value="/tempmg/doc_file_del.do")
	@ResponseBody
	public String new_file_del(DocumentmgModel documentmgModel, Model model) throws Exception {

		String test ="";
		try {
			test = documentmgService.doc_file_del(documentmgModel,documentmgModel.getPrefix());
		} catch (Exception e) {
			// TODO: handle exception
			test = "error";
		}
		
		return test;
	}
	
	
	
	
	
	@RequestMapping(value = "/board/Documentmg_Ajax_Read.do")
	@ResponseBody
	public DocumentmgModel documentmg__Ajax_Read(DocumentmgModel documentmgModel, Model model) throws Exception {
		return documentmgService.readone(documentmgModel);
	}
	
	
	
	@RequestMapping(value = "/tempmg/documentmg_Delete.do")
	public String verifyTemp_Delete(DocumentmgModel documentmgModel, Model model) throws Exception {
		String test ="";
		try {
			test = documentmgService.doc_file_del(documentmgModel,"List");
		} catch (Exception e) {
			// TODO: handle exception
			test = "error";
		}		
		return ResultUtil.handleSuccessResult();
//		return test;
	}

	
}

package com.pkms.common.attachfile.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.common.mail.service.MailService;
import com.wings.properties.service.PropertyServiceIf;
import com.wings.util.FileDownloadUtil;

@Controller
public class AttachFileController {
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "AttachFileService")
	protected AttachFileServiceIf attachFileService;
	
	private String propertyFilePathKey = "Globals.fileStorePath";
	
	static Logger logger = Logger.getLogger(AttachFileController.class);

	/**
	 * 첨부파일 다운로드.
	 * 
	 * @param attachFileModel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/attachfile/AttachFile_read.do")
	public void read(AttachFileModel attachFileModel, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String localFilePath = propertyService.getString(propertyFilePathKey);
		
		if(!"".equals(attachFileModel.getFile_path())){
			localFilePath = propertyService.getString(propertyFilePathKey) + File.separator + attachFileModel.getFile_path() + File.separator;
		}
		
		FileDownloadUtil.download(request, response, localFilePath + attachFileModel.getFile_name(), attachFileModel.getFile_org_name());
	}
	
	@RequestMapping(value = "/common/attachfile/AttachFile_DocRead.do")
	public void docRead(AttachFileModel attachFileModel, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String localFilePath = propertyService.getString(propertyFilePathKey);
		
		if(!"".equals(attachFileModel.getFile_path())){
			localFilePath = propertyService.getString(propertyFilePathKey) + File.separator + attachFileModel.getFile_path() + File.separator;
		}
		
		FileDownloadUtil.download(request, response, localFilePath + attachFileModel.getFile_name(), attachFileModel.getFile_org_name());
	}
	
	
	@RequestMapping(value = "/common/attachfile/Iwcs_down.do")
	public void idCheckFileDown(AttachFileModel attachFileModel, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String localFilePath = propertyService.getString(propertyFilePathKey);
		boolean idCheck = false;
		
		idCheck = attachFileService.readId(attachFileModel);
		System.out.println(attachFileModel.getEmpno());
		if(!idCheck){
			throw new Exception("error.biz.잘못된 방식의 접근입니다.");
		}
		
		if(!"".equals(attachFileModel.getFile_path())){
			localFilePath = propertyService.getString(propertyFilePathKey) + File.separator + attachFileModel.getFile_path() + File.separator;
		}

		FileDownloadUtil.download(request, response, localFilePath + attachFileModel.getFile_name(), attachFileModel.getFile_org_name());
	}
}

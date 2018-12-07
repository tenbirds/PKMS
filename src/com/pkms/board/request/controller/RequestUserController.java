package com.pkms.board.request.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.board.request.model.RequestModel;
import com.pkms.board.request.service.RequestUserServiceIf;
import com.pkms.common.util.ResultUtil;

@Controller
public class RequestUserController {

	@Resource(name = "RequestUserService")
	private RequestUserServiceIf requestUserService;
	
	@RequestMapping(value = "/board/request/RequestUser_Ajax_read.do")
	public String read(RequestModel requestModel, Model model) throws Exception{
		
		String retUrl = requestModel.getRetUrl();
		if("".equals(retUrl)){
			throw new Exception("read에서는 반드시 retUrl이 정의 되어야 합니다.");
		}
		
		RequestModel requestModelData = new RequestModel();
		
		if(StringUtils.hasLength(requestModel.getOrd())){
			requestModelData = requestUserService.read(requestModel);
		}
		model.addAttribute("requestModel", requestModelData);
		return retUrl;
	}
	
	@RequestMapping(value = "/board/request/RequestUser_Ajax_create.do")
	public String create(RequestModel requestModel, Model model) throws Exception{
		requestUserService.create(requestModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/request/RequestUser_Ajax_readList.do")
	public String readList(RequestModel requestModel, Model model) throws Exception{
		
		/*
		 * 목록 정보 조회
		 */
		List<?> requestUserModelList = requestUserService.readList(requestModel);
		
		
		model.addAttribute("requestUserModelList", requestUserModelList);
		
		return "/board/request/RequestUser_Ajax_ReadList";
	}
	
	@RequestMapping(value  = "/board/request/RequestUser_Ajax_update.do")
	public String update(RequestModel requestModel, Model model) throws Exception{
		requestUserService.update(requestModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value  = "/board/request/RequestUser_Ajax_delete.do")
	public String delete(RequestModel requestModel, Model model) throws Exception{
		requestUserService.delete(requestModel);
		return ResultUtil.handleSuccessResult();
	}
}

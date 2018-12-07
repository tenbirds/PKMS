package com.pkms.board.request.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.board.request.model.RequestModel;
import com.pkms.board.request.service.RequestServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.wings.util.DateUtil;

@Controller
public class RequestController {
	
	@Resource(name = "RequestService")
	private RequestServiceIf requestService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;

	@RequestMapping(value = "/board/request/Request_ReadList.do")
	public String readList(RequestModel requestModel, Model model) throws Exception{
		
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(requestModel);
		
		//기본 검색 기간 설정
		if(!StringUtils.hasLength(requestModel.getRequest_search_date_start()) || !StringUtils.hasLength(requestModel.getRequest_search_date_end())){
			requestModel.setRequest_search_date_start(DateUtil.formatDateByMonth(DateUtil.format(), -1));
			requestModel.setRequest_search_date_end(DateUtil.dateFormat());
		}

		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> requestModelList = requestService.readList(requestModel);
		paginationInfo.setTotalRecordCount(requestModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("RequestModel", requestModel);
		model.addAttribute("requestModelList", requestModelList);
		
		model.addAttribute("requestModel", requestModel);
		
		return "/board/request/Request_ReadList";
	}
	
	@RequestMapping(value = "/board/request/Request_Read.do")
	public String read(RequestModel requestModel, Model model) throws Exception{
		

		RequestModel requestModelData = new RequestModel();
		
		requestModelData = requestService.read(requestModel);
		
		/*
		 * 검색조건, 페이징 세팅
		 */
		requestModelData.setRequest_search_date_start(requestModel.getRequest_search_date_start());
		requestModelData.setRequest_search_date_end(requestModel.getRequest_search_date_end());
		requestModelData.setSearch_system_seq(requestModel.getSearch_system_seq());
		requestModelData.setSearch_system_name(requestModel.getSearch_system_name());
		requestModelData.setSearch_system_user_id(requestModel.getSearch_system_user_id());
		requestModelData.setSearch_system_user_name(requestModel.getSearch_system_user_name());
		requestModelData.setSearch_bp_name(requestModel.getSearch_bp_name());
		requestModelData.setSearch_request_state(requestModel.getSearch_request_state());
		requestModelData.setSearchAllManager(requestModel.isSearchAllManager());
		requestModelData.setSearchKeyword(requestModel.getSearchKeyword());
		requestModelData.setPageIndex(requestModel.getPageIndex());
		
		model.addAttribute("requestModel", requestModelData);
		
		return "/board/request/Request_Read";
	}
	
	@RequestMapping(value = "/board/request/Request_Create.do")
	public String create(RequestModel requestModel, Model model) throws Exception{
		requestService.create(requestModel);
		return ResultUtil.handleSuccessResultParam(model, requestModel.getRequest_seq(), "");
	}
	
	@RequestMapping(value = "/board/request/Request_Update.do")
	public String update(RequestModel requestModel, Model model) throws Exception{
		requestService.update(requestModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/request/Request_Delete.do")
	public String delete(RequestModel requestModel, Model model) throws Exception{
		requestService.delete(requestModel);
		return ResultUtil.handleSuccessResult();
	}
	
	
	
	
}

package com.pkms.board.notice.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.board.notice.model.NoticeModel;
import com.pkms.board.notice.service.NoticeServiceIf;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.wings.properties.service.PropertyServiceIf;

@Controller
public class NoticeController {
	
	@Resource(name = "NoticeService")
	private NoticeServiceIf noticeService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	@RequestMapping(value = "/board/notice/Notice_ReadList.do")
	public String readList(NoticeModel noticeModel, Model model) throws Exception {
		
		
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(noticeModel);

		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> noticeModelList = noticeService.readList(noticeModel);
		paginationInfo.setTotalRecordCount(noticeModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("noticeModelList", noticeModelList);
		model.addAttribute("noticeModel", noticeModel);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "/board/notice/Notice_ReadList";
	}
	
	@RequestMapping(value = "/board/notice/Notice_Read.do")
	public String read(NoticeModel noticeModel, Model model) throws Exception {
		
		String retUrl = noticeModel.getRetUrl();
//		System.out.println("☆★noticeModel.getRetUrl()☆★"+noticeModel.getRetUrl());
		if ("".equals(retUrl)) {
			throw new Exception("error.biz.URL이 정의 되어야 합니다.");
		}else if("/board/notice/Notice_Read".equals(retUrl)){
			/*
			 * 세션 정보에서 로그인한 사용자의 관리 권한을 가져옴
			 */
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
		
		/*
		 * 검색 조건, 페이징 세팅
		 */
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
	
}

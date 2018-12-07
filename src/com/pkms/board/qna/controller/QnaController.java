package com.pkms.board.qna.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pkms.board.qna.model.QnaModel;
import com.pkms.board.qna.service.QnaServiceIf;
import com.pkms.common.session.service.SessionServiceIf;
import com.pkms.common.tags.paging.pagination.PaginationInfo;
import com.pkms.common.tags.paging.service.PagingServiceIf;
import com.pkms.common.util.ResultUtil;
import com.wings.properties.service.PropertyServiceIf;

@Controller
public class QnaController {
	
	@Resource(name = "QnaService")
	private QnaServiceIf qnaService;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	@Resource(name = "SessionService")
	private SessionServiceIf sessionService;
	
	@Resource(name = "PagingService")
	private PagingServiceIf pagingService;
	
	@RequestMapping(value = "/board/qna/Qna_ReadList.do")
	public String readList(QnaModel qnaModel, Model model) throws Exception {
		/*
		 * 페이징 모델 생성
		 */
		PaginationInfo paginationInfo = pagingService.getPaginationInfo(qnaModel);

		/*
		 * 서비스로 부터 전체개수와 목록 정보 조회
		 */
		List<?> qnaModelList = qnaService.readList(qnaModel);
		paginationInfo.setTotalRecordCount(qnaModel.getTotalCount());

		/*
		 * UI 모델 세팅
		 */
		model.addAttribute("QnaModel", qnaModel);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("qnaModelList", qnaModelList);
		
		
		return "/board/qna/Qna_ReadList";
	}
	
	@RequestMapping(value = "/board/qna/Qna_Read.do")
	public String read(QnaModel qnaModel, Model model) throws Exception {

		String retUrl = qnaModel.getRetUrl();
		if ("".equals(retUrl)) {
			throw new Exception("read에서는 반드시 retUrl이 정의 되어야 합니다.");
		}
		
		Object[] resultObjects = null;
	
		if (StringUtils.hasLength(qnaModel.getQna_seq())) {
			resultObjects = qnaService.read(qnaModel);
		}
		
		if(retUrl.indexOf("View") > -1) {
			//QnA key 데이터
			model.addAttribute("QnaModel", qnaModel);
			
			//QnA 조회 데이터
			model.addAttribute("QnaModelData", resultObjects[0]);
			
			//QnA comment list
			model.addAttribute("CommentList", resultObjects[1]);
		} else if(retUrl.indexOf("Read") > -1) {
			//QnA key + 조회 데이터
			
			
			if(null!=resultObjects){
				//View Read
				model.addAttribute("QnaModel", resultObjects[0]);
			}else{
				//신규
				model.addAttribute("QnaModel", qnaModel);
			}
		}
		return retUrl;
	}
	
	
		
	@RequestMapping(value = "/board/qna/Qna_Ajax_Create.do")
	public String create(QnaModel qnaModel, Model model) throws Exception {
		qnaService.create(qnaModel);
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/qna/Qna_Ajax_Update.do")
	public String update(QnaModel qnaModel, Model model) throws Exception {
		/*
		 * 세션 정보에서 로그인한 사용자의 관리 권한을 가져옴
		 */
		List<String> authList = sessionService.readAuth();
		int check_cnt=0; // 권한체크 count
		for(String key : authList){
			if(key.equals("ROLE_ADMIN")){
				check_cnt ++;
			}
		}
		
		if(!qnaModel.getReg_user().equals(qnaModel.getSession_user_id()) && check_cnt == 0){
			throw new Exception("error.biz.수정할 권한이 없습니다.");			
		}else{
			qnaService.update(qnaModel);
		}
		
		return ResultUtil.handleSuccessResult();
	}
	
	@RequestMapping(value = "/board/qna/Qna_Ajax_Delete.do")
	public String delete(QnaModel qnaModel, Model model) throws Exception {
		/*
		 * 세션 정보에서 로그인한 사용자의 관리 권한을 가져옴
		 */
		List<String> authList = sessionService.readAuth();
		int check_cnt=0; // 권한체크 count
		for(String key : authList){
			if(key.equals("ROLE_ADMIN")){
				check_cnt ++;
			}
		}
		
		if(!qnaModel.getReg_user().equals(qnaModel.getSession_user_id()) && check_cnt == 0){
			throw new Exception("error.biz.삭제할 권한이 없습니다.");			
		}else{
			qnaService.delete(qnaModel);
		}
	
		return ResultUtil.handleSuccessResult();
	}
	
}

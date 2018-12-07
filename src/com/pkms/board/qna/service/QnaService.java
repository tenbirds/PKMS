package com.pkms.board.qna.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.board.qna.dao.QnaDao;
import com.pkms.board.qna.model.QnaModel;
import com.pkms.common.attachfile.service.AttachFileServiceIf;

@Service("QnaService")
public class QnaService implements QnaServiceIf{
	
	@Resource(name = "QnaDao")
	private QnaDao qnaDao;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;
	
	@Resource(name = "CommentService")
	private CommentServiceIf commentService;

	@Override
	public void create(QnaModel qnaModel) throws Exception {
		/**
		 * 첨부 파일 처리
		 */
		fileManageService.create(qnaModel, "QNA_");
		
		this.qnaDao.create(qnaModel);
	}

	@Override
	public Object[] read(QnaModel qnaModel) throws Exception {

		String retUrl = qnaModel.getRetUrl();
		
		/*
		 * read 모델
		 */
		qnaModel = this.qnaDao.read(qnaModel);
		
		//첨부 파일 정보 세팅
		fileManageService.read(qnaModel);
		
		/*
		 * 댓글
		 */
		List<?> resultList = null;
		if(retUrl.indexOf("View") > -1) {
			
			if(qnaModel.getContent() != null){
				qnaModel.setContent(qnaModel.getContent().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
			
			resultList = commentService.readList(qnaModel);
		}
		return new Object[] {qnaModel, resultList};
	}

	@Override
	public List<?> readList(QnaModel qnaModel) throws Exception {
		
		/*
		 * DAO에서 정보 목록 조회
		 */
		List<?> resultList = qnaDao.readList(qnaModel);
		/*
		 * 목록 전체 개수 조회
		 */
		int totalCount = qnaDao.readTotalCount(qnaModel);
		qnaModel.setTotalCount(totalCount);
		
		return resultList;
	}

	@Override
	public void update(QnaModel qnaModel) throws Exception {
		//첨부 파일 정보 수정
		fileManageService.update(qnaModel, "QNA_");
		
		this.qnaDao.update(qnaModel);
	}

	@Override
	public void delete(QnaModel qnaModel) throws Exception {
		/**
		 * 첨부 파일 삭제
		 */
		fileManageService.delete(qnaModel);
		this.qnaDao.delete(qnaModel);
	}
	
	

}

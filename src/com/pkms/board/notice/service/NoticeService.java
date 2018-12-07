package com.pkms.board.notice.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.board.notice.dao.NoticeDao;
import com.pkms.board.notice.model.NoticeModel;
import com.pkms.common.attachfile.service.AttachFileServiceIf;


@Service("NoticeService")
public class NoticeService implements NoticeServiceIf{
	
	@Resource(name = "NoticeDao")
	private NoticeDao noticeDao;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;	

	@Override
	public void create(NoticeModel noticeModel) throws Exception {
		
		/**
		 * 첨부 파일 처리
		 */
		fileManageService.create(noticeModel, "NOTICE_");
		
		
		this.noticeDao.create(noticeModel);
	}

	@Override
	public NoticeModel read(NoticeModel _noticeModel) throws Exception {
		
		String retUrl = _noticeModel.getRetUrl();
		
		NoticeModel noticeModel = this.noticeDao.read(_noticeModel);
		
		if(retUrl.indexOf("View") > -1) {
			if(noticeModel.getContent() != null){
				noticeModel.setContent(noticeModel.getContent().replace("\n", "<br>").replace(" ", "&nbsp;"));
			}
		}
		
		//첨부 파일 정보 세팅
		fileManageService.read(noticeModel);
		
		return noticeModel;
	}

	@Override
	public List<?> readList(NoticeModel noticeModel) throws Exception {
		
		/*
		 * DAO에서 정보 목록 조회
		 */
		List<?> resultList = noticeDao.readList(noticeModel);
		/*
		 * 목록 전체 개수 조회
		 */
		int totalCount = noticeDao.readTotalCount(noticeModel);
		noticeModel.setTotalCount(totalCount);
		
		return resultList;
	}

	@Override
	public void update(NoticeModel noticeModel) throws Exception {
		//첨부 파일 정보 수정
		fileManageService.update(noticeModel, "NOTICE_");
		
		this.noticeDao.update(noticeModel);
	}

	@Override
	public void delete(NoticeModel noticeModel) throws Exception {
		/**
		 * 첨부 파일 삭제
		 */
		fileManageService.delete(noticeModel);
		this.noticeDao.delete(noticeModel);
	}
	
	@Override
	public List<?> readList4Main(NoticeModel noticeModel) throws Exception {
		
		return noticeDao.readList4Main(noticeModel);
	}
		
}

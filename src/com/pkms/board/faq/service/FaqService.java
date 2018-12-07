package com.pkms.board.faq.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.board.faq.dao.FaqDao;
import com.pkms.board.faq.model.FaqModel;
import com.pkms.common.attachfile.service.AttachFileServiceIf;

@Service("FaqService")
public class FaqService implements FaqServiceIf{
	
	@Resource(name = "FaqDao")
	private FaqDao faqDao;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;

	@Override
	public void create(FaqModel faqModel) throws Exception {
		/**
		 * 첨부 파일 처리
		 */
		fileManageService.create(faqModel, "FAQ_");
		
		this.faqDao.create(faqModel);
	}
	
	@Override
	public FaqModel read(FaqModel faqModel) throws Exception {
		/*
		 * read 모델
		 */
		faqModel = this.faqDao.read(faqModel);
		
		//첨부 파일 정보 세팅
		fileManageService.read(faqModel);
		return faqModel;
	}
	
	@Override
	public List<?> readList(FaqModel faqModel) throws Exception {
		
		/*
		 * DAO에서 정보 목록 조회
		 */
		List<?> resultList = faqDao.readList(faqModel);
		/*
		 * 목록 전체 개수 조회
		 */
		int totalCount = faqDao.readTotalCount(faqModel);
		faqModel.setTotalCount(totalCount);
		
		return resultList;
	}

	@Override
	public void update(FaqModel faqModel) throws Exception {
		//첨부 파일 정보 수정
		fileManageService.update(faqModel, "Faq_");
		
		this.faqDao.update(faqModel);
	}

	@Override
	public void delete(FaqModel faqModel) throws Exception {
		/**
		 * 첨부 파일 삭제
		 */
		fileManageService.delete(faqModel);
		this.faqDao.delete(faqModel);
	}
}

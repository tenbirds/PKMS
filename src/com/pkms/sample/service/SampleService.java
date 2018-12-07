package com.pkms.sample.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.sample.dao.SampleDAO;
import com.pkms.sample.model.SampleModel;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 14.
 * 
 */
@Service("SampleService")
public class SampleService implements SampleServiceIf {

	@Resource(name = "SampleDAO")
	private SampleDAO sampleDAO;

	@Resource(name = "AttachFileService")
	private AttachFileServiceIf attachFileService;

	@Override
	public void create(SampleModel sampleModel) throws Exception {

		/**
		 * 첨부 파일 처리
		 */
		attachFileService.create(sampleModel, "SAMPLE_");

		/** UID 생성 */
		sampleModel.setId(String.valueOf(System.currentTimeMillis()));

		/** DAO에서 정보 생성 */
		sampleDAO.create(sampleModel);
	}

	@Override
	public SampleModel read(SampleModel sampleModel) throws Exception {

		sampleModel = sampleDAO.read(sampleModel);

		// 첨부 파일 정보 세팅
		attachFileService.read(sampleModel);

		return sampleModel;
	}

	@Override
	public List<?> readList(SampleModel sampleModel) throws Exception {

		/*
		 * DAO에서 정보 목록 조회
		 */
		List<?> resultList = sampleDAO.readList(sampleModel);

		/*
		 * 목록 전체 개수 조회
		 */
		int totalCount = sampleDAO.readTotalCount(sampleModel);
		sampleModel.setTotalCount(totalCount);

		return resultList;
	}

	@Override
	public void update(SampleModel sampleFileModel) throws Exception {

		// 첨부 파일 정보 수정
		attachFileService.update(sampleFileModel, "SAMPLE_");

		sampleDAO.update(sampleFileModel);

	}

	@Override
	public void delete(SampleModel sampleModel) throws Exception {
		/**
		 * 첨부 파일 삭제
		 */
		attachFileService.delete(sampleModel);

		sampleDAO.delete(sampleModel);
	}

}

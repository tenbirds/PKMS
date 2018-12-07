package com.pkms.newpncrmg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.attachfile.service.AttachFileServiceIf;
import com.pkms.newpncrmg.dao.NewpncrDuplicateCheckDao;
import com.pkms.newpncrmg.model.NewpncrModel;

@Service("NewpncrDuplicateCheckService")
public class NewpncrDuplicateCheckService implements NewpncrDuplicateCheckServiceIf {
	
	@Resource(name = "NewpncrDuplicateCheckDao")
	private NewpncrDuplicateCheckDao newpncrDuplicateCheckDao;
	
	@Resource(name = "NewpncrService")
	private NewpncrServiceIf newpncrServie;
	
	@Resource(name = "AttachFileService")
	private AttachFileServiceIf fileManageService;

	@Override
	public NewpncrModel read(NewpncrModel newpncrModel) throws Exception {
		
		newpncrModel = newpncrServie.read(newpncrModel);
		
		return newpncrModel;
	}

	@Override
	public List<?> readList(NewpncrModel newpncrModel) throws Exception {
		
		List<?> resultList = newpncrDuplicateCheckDao.readList(newpncrModel); 
		int totalCount = newpncrDuplicateCheckDao.readTotalCount(newpncrModel);
		
		newpncrModel.setTotalCount(totalCount);
		newpncrModel.setInit("true");
		return resultList;
	}

	@Override
	public void create(NewpncrModel newpncrModel) throws Exception {
		
		/**
		 * 첨부 파일 처리
		 */
		
		fileManageService.create(newpncrModel, "NEWPNCR_");
		
		//복사
		newpncrModel.setRetUrl("/newpncrmg/Newpncr_Read");
		NewpncrModel model = newpncrServie.read(newpncrModel);
		
		newpncrModel.setTitle(model.getTitle());
		newpncrModel.setProblem(model.getProblem());
		newpncrModel.setRequirement(model.getRequirement());
		newpncrModel.setNew_pncr_gubun(model.getNew_pncr_gubun());
		newpncrModel.setPriority(model.getPriority());
		newpncrModel.setProblem_gubun(model.getProblem_gubun());
		
		//state(상태)값 셋팅
		newpncrModel.setState("0");
		
		newpncrModel.setNew_pn_cr_seq(this.newpncrDuplicateCheckDao.create(newpncrModel));
		
	}

}

package com.pkms.verify_tem_mg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.verify_tem_mg.dao.VerifyTemDao;
import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.wings.properties.service.PropertyServiceIf;

@Service("VerifyTemService")
public class VerifyTemService implements VerifyTemServiceIf{

	@Resource(name = "VerifyTemDao")
	private VerifyTemDao verifyTemDao;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	public VerifyTemModel create_quest(VerifyTemModel verifyTemModel) throws Exception{
		String queSeq = verifyTemDao.create_quest(verifyTemModel);
		verifyTemModel.setQuest_seq(queSeq);
		return verifyTemModel;
	}
	
	public void create_item(VerifyTemModel verifyTemModel) throws Exception{
		verifyTemDao.create_item(verifyTemModel);
	}
	
	public List<VerifyTemModel> readList_quest(VerifyTemModel verifyTemModel) throws Exception{
		List<VerifyTemModel> readList = verifyTemDao.readList_quest(verifyTemModel);
		int totalCnt = verifyTemDao.readList_quest_totalCnt(verifyTemModel);
		verifyTemModel.setTotalCount(totalCnt);
		return readList;
	}
	
	public VerifyTemModel read_quest(VerifyTemModel verifyTemModel) throws Exception{
		verifyTemModel = verifyTemDao.read_quest(verifyTemModel);
		return verifyTemModel;
	}
	
	public List<VerifyTemModel> read_itemList(VerifyTemModel verifyTemModel) throws Exception{
		List<VerifyTemModel> readList = verifyTemDao.read_itemList(verifyTemModel);
		return readList;
	}
	
	public void delete_verifyTem(VerifyTemModel verifyTemModel) throws Exception{
		verifyTemDao.delete_result(verifyTemModel);
		verifyTemDao.delete_quest(verifyTemModel);
		verifyTemDao.delete_manage_tem(verifyTemModel);
	}
	
	public void update_quest(VerifyTemModel verifyTemModel) throws Exception{
		verifyTemDao.update_quest(verifyTemModel);
		verifyTemDao.delete_result(verifyTemModel);
	}
	
	public String verifyinfo_pkgVerify_S(VerifyTemModel verifyTemModel) throws Exception{
		String qu_seq = verifyTemModel.getQuest_seq();
		String S_gubun ="";
		
		List<VerifyTemModel> readList = verifyTemDao.verifyinfo_pkgVerify_S(verifyTemModel);
		
		VerifyTemModel verifyTemModel_S = new VerifyTemModel();
		for(int i=0; i<readList.size(); i++){
			verifyTemModel_S.setVerify_seq(readList.get(i).getVerify_seq());
			verifyTemModel_S.setVerify_ver(readList.get(i).getVerify_ver());
			List<VerifyTemModel> quest_pkgVerify_S = verifyTemDao.quest_pkgVerify_S(verifyTemModel_S);
			for(int j=0; j<quest_pkgVerify_S.size(); j++){
				if(qu_seq.equals(quest_pkgVerify_S.get(j).getQuest_seq())){
					S_gubun = "완료";
					break;
				}
			}
		}
		return S_gubun;
	}
	public List<VerifyTemModel> quest_pkgVerify_S(VerifyTemModel verifyTemModel) throws Exception{
		List<VerifyTemModel> readList = verifyTemDao.quest_pkgVerify_S(verifyTemModel);
		return readList;
	}
	
}

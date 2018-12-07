package com.pkms.verify_tem_mg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.verify_tem_mg.dao.VerifyTemMgDao;
import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.wings.properties.service.PropertyServiceIf;

@Service("VerifyTemMgService")
public class VerifyTemMgService implements VerifyTemMgServiceIf{
	
	@Resource(name = "VerifyTemMgDao")
	private VerifyTemMgDao verifyTemMgDao;
	
	@Resource(name = "PropertyService")
	protected PropertyServiceIf propertyService;
	
	public List<VerifyTemModel> readList_verifyTem(VerifyTemModel verifyTemModel) throws Exception{
		List<VerifyTemModel> readList = verifyTemMgDao.readList_verifyTem(verifyTemModel);
		int totalCnt = verifyTemMgDao.readList_verifyTem_totalCnt(verifyTemModel);
		verifyTemModel.setTotalCount(totalCnt);
		return readList;
	}
	
	public VerifyTemModel create_verifyManage(VerifyTemModel verifyTemModel) throws Exception{
		String verifySeq = verifyTemMgDao.create_verifyManage(verifyTemModel);
		verifyTemModel.setVerify_seq(verifySeq);
		return verifyTemModel;
	}
	
	public void create_verifyTem(VerifyTemModel verifyTemModel) throws Exception{
		verifyTemMgDao.create_verifyTem(verifyTemModel);
	}
	
	public List<VerifyTemModel> read_verifyTem(VerifyTemModel verifyTemModel) throws Exception{
		List<VerifyTemModel> readList = verifyTemMgDao.read_verifyTem(verifyTemModel);
		return readList;
	}
	
	public void delete_verifyMG(VerifyTemModel verifyTemModel) throws Exception{
		verifyTemMgDao.delete_verifyMG_Tem(verifyTemModel);
	}
	
	public int getNewVer(VerifyTemModel verifyTemModel) throws Exception{
		int newver  = verifyTemMgDao.getNewVer(verifyTemModel);
		return newver;
	}
	
	public List<String> check4PKGusing_temp(VerifyTemModel verifyTemModel) throws Exception{
		List<String> readList = verifyTemMgDao.check4PKGusing_temp(verifyTemModel);
		return readList;
	}
	
	public String read_sysName_verifyTem(String sysNM) throws Exception{
		return verifyTemMgDao.read_sysName_verifyTem(sysNM);
	}

}

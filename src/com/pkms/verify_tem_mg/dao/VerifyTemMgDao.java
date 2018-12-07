package com.pkms.verify_tem_mg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("VerifyTemMgDao")
public class VerifyTemMgDao extends IbatisAbstractDAO {
	
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> readList_verifyTem(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("VerifyTemMgDAO.readList_verifyTem", verifyTemModel);
	}
	
	public int readList_verifyTem_totalCnt(VerifyTemModel verifyTemModel) throws Exception {
		return (Integer) read("VerifyTemMgDAO.readList_verifyTem_totalCnt", verifyTemModel);
	}
	
	public String create_verifyManage(VerifyTemModel verifyTemModel) throws Exception {
		return (String) create("VerifyTemMgDAO.create_verifyManage", verifyTemModel);
	}
	
	public void create_verifyTem(VerifyTemModel verifyTemModel) throws Exception {
		create("VerifyTemMgDAO.create_verifyTem", verifyTemModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> read_verifyTem(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("VerifyTemMgDAO.read_verifyTem", verifyTemModel);
	}
	
	public void delete_verifyMG_Tem(VerifyTemModel verifyTemModel) throws Exception {
		delete("VerifyTemMgDAO.delete_verifyMG_Tem", verifyTemModel);
	}
	
	public void delete_verifyMG(VerifyTemModel verifyTemModel) throws Exception {
		delete("VerifyTemMgDAO.delete_verifyMG", verifyTemModel);
	}
	
	public int getNewVer(VerifyTemModel verifyTemModel) throws Exception {
		return (Integer) read("VerifyTemMgDAO.getNewVer", verifyTemModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> check4PKGusing_temp(VerifyTemModel verifyTemModel) throws Exception {
		return (List<String>) readList("VerifyTemMgDAO.check4PKGusing_temp", verifyTemModel);
	}
	
	public String read_sysName_verifyTem(String sysNM) throws Exception{
		return (String) read("VerifyTemMgDAO.read_sysName_verifyTem", sysNM);
	}
}

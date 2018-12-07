package com.pkms.verify_tem_mg.service;

import java.util.List;

import com.pkms.verify_tem_mg.model.VerifyTemModel;

public interface VerifyTemMgServiceIf {
	public List<VerifyTemModel> readList_verifyTem(VerifyTemModel verifyTemModel) throws Exception;
	public VerifyTemModel create_verifyManage(VerifyTemModel verifyTemModel) throws Exception;
	public void create_verifyTem(VerifyTemModel verifyTemModel) throws Exception;
	public List<VerifyTemModel> read_verifyTem(VerifyTemModel verifyTemModel) throws Exception;
	public void delete_verifyMG(VerifyTemModel verifyTemModel) throws Exception;
	public int getNewVer(VerifyTemModel verifyTemModel) throws Exception;
	public List<String> check4PKGusing_temp(VerifyTemModel verifyTemModel) throws Exception;
	public String read_sysName_verifyTem(String sysNM) throws Exception;
}

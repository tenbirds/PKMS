package com.pkms.verify_tem_mg.service;

import java.util.List;

import com.pkms.verify_tem_mg.model.VerifyTemModel;

public interface VerifyTemServiceIf {
	
	public VerifyTemModel create_quest(VerifyTemModel verifyTemModel) throws Exception;
	public void create_item(VerifyTemModel verifyTemModel) throws Exception;
	public List<VerifyTemModel> readList_quest(VerifyTemModel verifyTemModel) throws Exception;
	public VerifyTemModel read_quest(VerifyTemModel verifyTemModel) throws Exception;
	public List<VerifyTemModel> read_itemList(VerifyTemModel verifyTemModel) throws Exception;
	public void delete_verifyTem(VerifyTemModel verifyTemModel) throws Exception;
	public void update_quest(VerifyTemModel verifyTemModel) throws Exception;
	public String verifyinfo_pkgVerify_S(VerifyTemModel verifyTemModel) throws Exception;
	public List<VerifyTemModel> quest_pkgVerify_S(VerifyTemModel verifyTemModel) throws Exception;
	
}

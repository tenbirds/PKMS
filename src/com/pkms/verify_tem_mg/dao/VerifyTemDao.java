package com.pkms.verify_tem_mg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.verify_tem_mg.model.VerifyTemModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("VerifyTemDao")
public class VerifyTemDao extends IbatisAbstractDAO {
	
	public String create_quest(VerifyTemModel verifyTemModel) throws Exception {
		return (String) create("VerifyTemDAO.create_quest", verifyTemModel);
	}
	
	public void create_item(VerifyTemModel verifyTemModel) throws Exception {
		create("VerifyTemDAO.create_item", verifyTemModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> readList_quest(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("VerifyTemDAO.readList_quest", verifyTemModel);
	}
	
	public int readList_quest_totalCnt(VerifyTemModel verifyTemModel) throws Exception {
		return (Integer) read("VerifyTemDAO.readList_quest_totalCnt", verifyTemModel);
	}
	
	public VerifyTemModel read_quest(VerifyTemModel verifyTemModel) throws Exception {
		return (VerifyTemModel) read("VerifyTemDAO.read_quest", verifyTemModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> read_itemList(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("VerifyTemDAO.read_itemList", verifyTemModel);
	}
	
	public void delete_result(VerifyTemModel verifyTemModel) throws Exception {
		delete("VerifyTemDAO.delete_result", verifyTemModel);
	}
	
	public void delete_item(VerifyTemModel verifyTemModel) throws Exception {
		delete("VerifyTemDAO.delete_item", verifyTemModel);
	}
	
	public void delete_quest(VerifyTemModel verifyTemModel) throws Exception {
		delete("VerifyTemDAO.delete_quest", verifyTemModel);
	}
	
	public void update_quest(VerifyTemModel verifyTemModel) throws Exception {
		delete("VerifyTemDAO.update_quest", verifyTemModel);
	}
	
	public void delete_manage_tem(VerifyTemModel verifyTemModel) throws Exception {
		delete("VerifyTemDAO.delete_manage_tem", verifyTemModel);
	}
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> verifyinfo_pkgVerify_S(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("VerifyTemDAO.verifyinfo_pkgVerify_S", verifyTemModel);
	}
	@SuppressWarnings("unchecked")
	public List<VerifyTemModel> quest_pkgVerify_S(VerifyTemModel verifyTemModel) throws Exception {
		return (List<VerifyTemModel>) readList("VerifyTemDAO.quest_pkgVerify_S", verifyTemModel);
	}
	
}

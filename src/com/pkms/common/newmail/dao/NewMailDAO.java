package com.pkms.common.newmail.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.common.newmail.model.NewMailModel;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.wings.dao.IbatisAbstractDAO;

@Repository("NewMailDAO")
public class NewMailDAO extends IbatisAbstractDAO {

	/**
	 * new mail create utils
	 * 
	 * @param PkgModel
	 * @return pkgModel
	 * @throws Exception
	 */
	public List<NewMailModel> readuserList(NewMailModel model) throws Exception {
		return (List<NewMailModel>) readList("NewMailDAO.readList", model);
	}
	
	public List<NewMailModel> readInfo(NewMailModel model) throws Exception {
		return (List<NewMailModel>) readList("NewMailDAO.readInfoList", model);
	}

	public NewMailModel systemSeqSearch(NewMailModel model) throws Exception {
		return (NewMailModel) read("NewMailDAO.systemSeqSearch", model);
	}
	
	public List<NewMailModel> pkgUserInfoList(NewMailModel model) throws Exception {
		return (List<NewMailModel>) readList("NewMailDAO.pkgUserInfoList", model);
	}
	
	public List<String> pkgUserIdList(NewMailModel model) throws Exception {
		return (List<String>) readList("NewMailDAO.pkgUserIdList", model);
	}
	
	public List<String> pkgUserIdList2(NewMailModel model) throws Exception {
		return (List<String>) readList("NewMailDAO.pkgUserIdList2", model);
	}
	
	public List<NewMailModel> eqmentUserList( NewMailModel eqment_seq) throws Exception {
		return (List<NewMailModel>) readList("NewMailDAO.eqmentUserList", eqment_seq);
	}
	
	public List<PkgEquipmentModel> eqmentList(Pkg21Model pkg21Model) throws Exception {
		return (List<PkgEquipmentModel>) readList("NewMailDAO.eqmentList", pkg21Model);
	}
	
	
	
	
//	public List<NewMailModel> readUsersId(List<String> model) throws Exception {
//		return (List<NewMailModel>) read("NewMailDAO.readUsersId", model);
//	}

	


}

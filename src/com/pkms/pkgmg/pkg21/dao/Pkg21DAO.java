package com.pkms.pkgmg.pkg21.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.pkg21.model.Pkg21FileModel;
import com.pkms.sys.system.model.SystemFileModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("Pkg21DAO")
public class Pkg21DAO extends IbatisAbstractDAO {
	
	public String create(Pkg21Model model) throws Exception {
		return (String) create("pkg21DAO.create", model);
	}

	public int readTotalCount(Pkg21Model model) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("pkg21DAO.readTotalCount", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> readList(Pkg21Model model) throws Exception {
		List<Pkg21Model> readList = (List<Pkg21Model>) readList("pkg21DAO.readList", model);
		return readList;
	}
	
	public void update(Pkg21Model model) throws Exception {
		update("pkg21DAO.update", model);
	}
	
	public void pkg_delete(Pkg21Model model) throws Exception {
		update("pkg21DAO.pkg_delete", model);
	}
	
	public void pkg_status_update(Pkg21Model model) throws Exception {
		update("pkg21DAO.pkg_status_update", model);
	}	
	
	public void pkg_dev_status_update(Pkg21Model model) throws Exception {
		update("pkg21DAO.pkg_dev_status_update", model);
	}
	
	public void pkg_cha_yn_update(Pkg21Model model) throws Exception {
		update("pkg21DAO.pkg_cha_yn_update", model);
	}
	
	public void pkg_verify_update(Pkg21Model model) throws Exception {
		update("pkg21DAO.pkg_verify_update", model);
	}
	
	public Pkg21Model peTypeRead(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("pkg21DAO.peTypeRead",model);
	}
	
	public Pkg21Model read(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("pkg21DAO.read", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> questList(Pkg21Model model) throws Exception {
		return (List<Pkg21Model>) readList("pkg21DAO.questList",model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> valueList(Pkg21Model model) throws Exception {
		return (List<Pkg21Model>) readList("pkg21DAO.valueList",model);
	}
	
	public void pkg_vol_create(Pkg21Model model) throws Exception {
		update("pkg21DAO.pkg_vol_create", model);
	}
	
	public void pkg_vol_update(Pkg21Model model) throws Exception {
		create("pkg21DAO.pkg_vol_update", model);
	}
	
	public void pkg_result_delete(Pkg21FileModel model) throws Exception {
		delete("pkg21DAO.pkg_result_delete", model);
	}
	
	public void pkg_result_create(Pkg21FileModel model) throws Exception {
		create("pkg21DAO.pkg_result_create", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21FileModel> pkg_result_list(Pkg21Model model) throws Exception {
		return (List<Pkg21FileModel>) readList("pkg21DAO.pkg_result_list", model);
	}
	
	public void pkg_status_delete(Pkg21Model model) throws Exception {
		update("pkg21DAO.pkg_status_delete", model);
	}
	
	public void pkg_status_delete_like(Pkg21Model model) throws Exception {
		update("pkg21DAO.pkg_status_delete_like", model);
	}
	
	public int readEQCntLeft(Pkg21Model model) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("pkg21DAO.readEQCntLeft", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<SystemFileModel> sysFileList(SystemFileModel model) throws Exception {
		List<SystemFileModel> readList = (List<SystemFileModel>) readList("pkg21DAO.sysFileList", model);
		return readList;
	}

	public String getPkgSeq(Pkg21Model model) throws Exception {
		return (String) read("pkg21DAO.getPkgSeq", model);
	}
	
	/**
	 * cell user 등록
	 * @param model
	 * @throws Exception
	 */
	public void createCellUserList(Pkg21Model model) throws Exception {
		create("pkg21DAO.createCellUserList", model);
	}
	
	public void deleteCellUserList(Pkg21Model pkg21Model) throws Exception {
		delete("pkg21DAO.deleteCellUserList", pkg21Model);
	}

	/**
	 * 20181203 eryoon
	 * * 초대적용 시 확대적용이 남은 갯수 체크
	 */
	public int getEqCount(Pkg21Model pkg21Model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("pkg21DAO.eqCount", pkg21Model);
	}
}

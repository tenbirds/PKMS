package com.pkms.pkgmg.wired.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.pkms.pkgmg.pkg21.model.Pkg21FileModel;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.wired.model.WiredModel;
import com.pkms.sys.system.model.SystemFileModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * WiredDAO
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("WiredDAO")
public class WiredDAO extends IbatisAbstractDAO {


	
	public String create(Pkg21Model model) throws Exception {
		return (String) create("wiredDAO.create", model);
	}

	public int readTotalCount(Pkg21Model model) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("wiredDAO.readTotalCount", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> readList(Pkg21Model model) throws Exception {
		List<Pkg21Model> readList = (List<Pkg21Model>) readList("wiredDAO.readList", model);
		return readList;
	}
	
	public void update(Pkg21Model model) throws Exception {
		update("wiredDAO.update", model);
	}
	
	public void pkg_status_update(Pkg21Model model) throws Exception {
		update("wiredDAO.pkg_status_update", model);
	}
	
	public void pkg_dev_status_update(Pkg21Model model) throws Exception {
		update("wiredDAO.pkg_dev_status_update", model);
	}
	
	public void pkg_cha_yn_update(Pkg21Model model) throws Exception {
		update("wiredDAO.pkg_cha_yn_update", model);
	}
	
	public void pkg_verify_update(Pkg21Model model) throws Exception {
		update("wiredDAO.pkg_verify_update", model);
	}
	
	public Pkg21Model peTypeRead(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("wiredDAO.peTypeRead",model);
	}
	
	public Pkg21Model read(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("wiredDAO.read", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> questList(Pkg21Model model) throws Exception {
		return (List<Pkg21Model>) readList("wiredDAO.questList",model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> valueList(Pkg21Model model) throws Exception {
		return (List<Pkg21Model>) readList("wiredDAO.valueList",model);
	}
	
	public void pkg_vol_create(Pkg21Model model) throws Exception {
		update("wiredDAO.pkg_vol_create", model);
	}
	
	public void pkg_vol_update(Pkg21Model model) throws Exception {
		create("wiredDAO.pkg_vol_update", model);
	}
	
	public void pkg_result_delete(Pkg21FileModel model) throws Exception {
		delete("wiredDAO.pkg_result_delete", model);
	}
	
	public void pkg_result_create(Pkg21FileModel model) throws Exception {
		create("wiredDAO.pkg_result_create", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21FileModel> pkg_result_list(Pkg21Model model) throws Exception {
		return (List<Pkg21FileModel>) readList("wiredDAO.pkg_result_list", model);
	}
	
	public void pkg_status_delete(Pkg21Model model) throws Exception {
		update("wiredDAO.pkg_status_delete", model);
	}
	
	public void pkg_status_delete_like(Pkg21Model model) throws Exception {
		update("wiredDAO.pkg_status_delete_like", model);
	}
	
	public int readEQCntLeft(Pkg21Model model) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("wiredDAO.readEQCntLeft", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<SystemFileModel> sysFileList(SystemFileModel model) throws Exception {
		List<SystemFileModel> readList = (List<SystemFileModel>) readList("wiredDAO.sysFileList", model);
		return readList;
	}

}

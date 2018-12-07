package com.pkms.pkgmg.access.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.wings.dao.IbatisAbstractDAO;

/**
 * WiredDAO
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("AccessDAO")
public class AccessDAO extends IbatisAbstractDAO {
	public int readTotalCount(Pkg21Model model) throws Exception {
		return (Integer) getSqlMapClientTemplate().queryForObject("accessDAO.readTotalCount", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> readList(Pkg21Model model) throws Exception {
		List<Pkg21Model> readList = (List<Pkg21Model>) readList("accessDAO.readList", model);
		return readList;
	}
	
	public Pkg21Model read(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("accessDAO.read", model);
	}
	
	public String create(Pkg21Model model) throws Exception {
		return (String) create("accessDAO.create", model);
	}
	
	public void update(Pkg21Model model) throws Exception {
		update("accessDAO.update", model);
	}
	
	public void pkg_status_update(Pkg21Model model) throws Exception {
		update("accessDAO.pkg_status_update", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> questList(Pkg21Model model) throws Exception {
		return (List<Pkg21Model>) readList("accessDAO.questList",model);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> valueList(Pkg21Model model) throws Exception {
		return (List<Pkg21Model>) readList("accessDAO.valueList",model);
	}
	
	public void pkg_chk_create(Pkg21Model model) throws Exception {
		update("accessDAO.pkg_chk_create", model);
	}
	
	public void pkg_chk_delete(Pkg21Model model) throws Exception {
		update("accessDAO.pkg_chk_delete", model);
	}
	
	public void pkg_chk_delete_all(Pkg21Model model) throws Exception {
		update("accessDAO.pkg_chk_delete_all", model);
	}
	
	public void equipment_delete_file(Pkg21Model model) throws Exception {
		delete("accessDAO.equipment_delete_file", model);
	}
	
	public void not_like_delete_file(Pkg21Model model) throws Exception {
		delete("accessDAO.not_like_delete_file", model);
	}
	
	public void pkg_status_delete(Pkg21Model model) throws Exception {
		delete("accessDAO.pkg_status_delete", model);
	}
	
	public void pkg_status_delete_like(Pkg21Model model) throws Exception {
		delete("accessDAO.pkg_status_delete_like", model);
	}
	
	public Pkg21Model copy_cnt(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("accessDAO.copy_cnt", model);
	}
	
	public void copy_file(Pkg21Model model) throws Exception {
		update("accessDAO.copy_file", model);
	}
	
	public void copy_file_delete(Pkg21Model model) throws Exception {
		delete("accessDAO.copy_file_delete", model);
	}
}

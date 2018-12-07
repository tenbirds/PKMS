package com.pkms.pkgmg.wired.dao;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.wings.dao.IbatisAbstractDAO;

@Repository("WiredStatusDAO")
public class WiredStatusDAO extends IbatisAbstractDAO {
	
	public void create(Pkg21Model model) throws Exception {
		create("wiredStatusDAO.create", model);
	}
	
	public Pkg21Model read(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("wiredStatusDAO.read", model);
	}
	
	public void update(Pkg21Model model) throws Exception {
		update("wiredStatusDAO.update", model);
	}
	
	public void statusUpdate(Pkg21Model model) throws Exception {
		update("wiredStatusDAO.statusUpdate", model);
	}
	
	public String real_status(Pkg21Model model) throws Exception {
		return (String) read("wiredStatusDAO.real_status", model);
	}
	
	public Pkg21Model pkg_user_all_yn(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("wiredStatusDAO.pkg_user_all_yn", model);
	}
}

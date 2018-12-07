package com.pkms.pkgmg.pkg21.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.wings.dao.IbatisAbstractDAO;

@Repository("Pkg21StatusDAO")
public class Pkg21StatusDAO extends IbatisAbstractDAO {
	
	public void create(Pkg21Model model) throws Exception {
		create("pkg21StatusDAO.create", model);
	}
	
	public Pkg21Model read(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("pkg21StatusDAO.read", model);
	}
	
	public void update(Pkg21Model model) throws Exception {
		update("pkg21StatusDAO.update", model);
	}
	
	public String real_status(Pkg21Model model) throws Exception {
		return (String) read("pkg21StatusDAO.real_status", model);
	}
	
	public Pkg21Model pkg_user_all_yn(Pkg21Model model) throws Exception {
		return (Pkg21Model) read("pkg21StatusDAO.pkg_user_all_yn", model);
	}
	
	/**
	 * 20181115 eryoon 추가
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Pkg21Model> readCellUserList(Pkg21Model model) throws Exception {
		return (List<Pkg21Model>) readList("pkg21StatusDAO.getCellUserList", model);
	}

	public Pkg21Model readEqCnt(Pkg21Model pkg21Model) {
		return (Pkg21Model) read("pkg21StatusDAO.getEqCnt", pkg21Model);
	}
}

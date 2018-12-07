package com.pkms.pkgmg.pkg21.service;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.pkg21.dao.Pkg21StatusDAO;

@Service("Pkg21StatusService")
public class Pkg21StatusService implements Pkg21StatusServiceIf {
	@Resource(name="Pkg21StatusDAO")
	private Pkg21StatusDAO pkg21StatusDAO;
	
	@Override
	public void create(Pkg21Model pkg21Model) throws Exception {
		pkg21StatusDAO.create(pkg21Model);
	}
	
	@Override
	public void update(Pkg21Model pkg21Model) throws Exception {
		pkg21StatusDAO.update(pkg21Model);
	}
	
	@Override
	public Pkg21Model read(Pkg21Model pkg21Model) throws Exception {
		return pkg21StatusDAO.read(pkg21Model);
	}
	
	@Override
	public String real_status(Pkg21Model pkg21Model) throws Exception {
		return pkg21StatusDAO.real_status(pkg21Model);
	}
	
	@Override
	public Pkg21Model pkg_user_all_yn(Pkg21Model pkg21Model) throws Exception {
		return pkg21StatusDAO.pkg_user_all_yn(pkg21Model);
	}
	
	/**
	 * 20181115 eryoon 추가
	 */
	@Override
	public List<Pkg21Model> readCellUserList (Pkg21Model pkg21Model) throws Exception { 
		return pkg21StatusDAO.readCellUserList(pkg21Model);
	}

	@Override
	public Pkg21Model readEqCnt(Pkg21Model pkg21Model) throws Exception {
		return pkg21StatusDAO.readEqCnt(pkg21Model);
	}


}

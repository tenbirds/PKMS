package com.pkms.pkgmg.wired.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.pkgmg.wired.dao.WiredStatusDAO;
import com.pkms.pkgmg.pkg21.dao.Pkg21StatusDAO;

@Service("WiredStatusService")
public class WiredStatusService implements WiredStatusServiceIf {
	@Resource(name="WiredStatusDAO")
	private WiredStatusDAO wiredStatusDAO;
	
	@Override
	public void create(Pkg21Model pkg21Model) throws Exception {
		wiredStatusDAO.create(pkg21Model);
	}
	
	@Override
	public void update(Pkg21Model pkg21Model) throws Exception {
		wiredStatusDAO.update(pkg21Model);
	}
	
	@Override
	public void statusUpdate(Pkg21Model pkg21Model) throws Exception {
		wiredStatusDAO.statusUpdate(pkg21Model);
	}
	
	@Override
	public Pkg21Model read(Pkg21Model pkg21Model) throws Exception {
		return wiredStatusDAO.read(pkg21Model);
	}
	
	@Override
	public String real_status(Pkg21Model pkg21Model) throws Exception {
		return wiredStatusDAO.real_status(pkg21Model);
	}
	
	@Override
	public Pkg21Model pkg_user_all_yn(Pkg21Model pkg21Model) throws Exception {
		return wiredStatusDAO.pkg_user_all_yn(pkg21Model);
	}
}

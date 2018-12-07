package com.pkms.pkgmg.pkg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.pkgmg.pkg.dao.PkgUserDAO;
import com.pkms.pkgmg.pkg.model.PkgUserModel;

/**
 * PKG 승인자 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Service("PkgUserService")
public class PkgUserService implements PkgUserServiceIf {

	@Resource(name = "PkgUserDAO")
	private PkgUserDAO pkgUserDAO;

	@Override
	public void create(PkgUserModel pkgUserModel) throws Exception {
		pkgUserDAO.create(pkgUserModel);
	}

	@Override
	public List<PkgUserModel> readList(PkgUserModel pkgUserModel) throws Exception {
		return (List<PkgUserModel>) pkgUserDAO.readList(pkgUserModel);
	}

	@Override
	public PkgUserModel readActive(PkgUserModel pkgUserModel) throws Exception {
		return pkgUserDAO.readActive(pkgUserModel);
	}

	@Override
	public int update(PkgUserModel pkgUserModel) throws Exception {
		return pkgUserDAO.update(pkgUserModel);
	}

	@Override
	public void delete(PkgUserModel pkgUserModel) throws Exception {
		pkgUserDAO.delete(pkgUserModel);
	}

}

package com.pkms.pkgmg.pkg.dao;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 기본정보 관련 DAO
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("PkgTab1DAO")
public class PkgTab1DAO extends IbatisAbstractDAO {

	/**
	 * Pkg 기본정보 등록
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String create(PkgModel model) throws Exception {
		return (String) create("pkgTab1DAO.create", model);
	}

	/**
	 * Pkg 기본정보 수정
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void update(PkgModel model) throws Exception {
		update("pkgTab1DAO.update", model);
	}

}

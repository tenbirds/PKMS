package com.pkms.pkgmg.pkg.dao;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 보완적용내역 NewPnCr 관련 DAO
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("PkgDetailNewPnCrDAO")
public class PkgDetailNewPnCrDAO extends IbatisAbstractDAO {

	/**
	 * NewPnCr 매핑
	 * @param model
	 * @throws Exception
	 */
	public void update(PkgModel model) throws Exception {
		update("PkgDetailNewPnCrDAO.update", model);
	}
	

}

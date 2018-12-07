package com.pkms.pkgmg.pkg.dao;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 첨부파일 관련 DAO
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("PkgTab2DAO")
public class PkgTab2DAO extends IbatisAbstractDAO {

	/**
	 * 첨부파일 수정
	 * @param model
	 * @throws Exception
	 */
	public void update(PkgModel model) throws Exception {
		update("pkgTab2DAO.update", model);
	}
}

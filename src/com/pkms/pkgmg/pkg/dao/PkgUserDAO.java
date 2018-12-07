package com.pkms.pkgmg.pkg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgUserModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * PKG 승인자 관련 DAO
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("PkgUserDAO")
public class PkgUserDAO extends IbatisAbstractDAO {

	/**
	 * 승인자 설정
	 * @param pkgUserModel
	 * @throws Exception
	 */
	public void create(PkgUserModel pkgUserModel) throws Exception {
		create("PkgUserDAO.create", pkgUserModel);
	}

	/**
	 * 승인자 목록 조회
	 * @param pkgUserModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PkgUserModel> readList(PkgUserModel pkgUserModel) throws Exception {
		return (List<PkgUserModel>) readList("PkgUserDAO.readList", pkgUserModel);
	}

	/**
	 * 진행 중인 승인자 조회
	 * @param pkgUserModel
	 * @return
	 * @throws Exception
	 */
	public PkgUserModel readActive(PkgUserModel pkgUserModel) throws Exception {
		return (PkgUserModel) read("PkgUserDAO.readActive", pkgUserModel);
	}
	
	/**
	 * 승인 처리
	 * @param pkgUserModel
	 * @return
	 * @throws Exception
	 */
	public int update(PkgUserModel pkgUserModel) throws Exception {
		return (int) update("PkgUserDAO.update", pkgUserModel);
	}
	
	/**
	 * 승인자 삭제<br/>
	 * - 승인자 목록에서 수정 시 all delete-insert 때문에 필요
	 * @param pkgUserModel
	 * @throws Exception
	 */
	public void delete(PkgUserModel pkgUserModel) throws Exception {
		delete("PkgUserDAO.delete", pkgUserModel);
	}
}

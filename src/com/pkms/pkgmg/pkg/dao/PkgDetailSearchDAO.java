package com.pkms.pkgmg.pkg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 보완적용내역 검색 관련 DAO
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("PkgDetailSearchDAO")
public class PkgDetailSearchDAO extends IbatisAbstractDAO {

	/**
	 * 보완적용내역 목록 검색
	 * 
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgDetailModel> readList(PkgModel pkgModel) throws Exception {
		@SuppressWarnings("unchecked")
		List<PkgDetailModel> readList = (List<PkgDetailModel>) readList("PkgDetailSearchDAO.readList", pkgModel);
		return readList;
	}

	/**
	 * 보완적용내역 목록 전체 count
	 * 
	 * @param pkgModel
	 * @return
	 */
	public int readTotalCount(PkgModel pkgModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("PkgDetailSearchDAO.readTotalCount", pkgModel);
	}
}

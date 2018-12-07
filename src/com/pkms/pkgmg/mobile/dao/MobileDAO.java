package com.pkms.pkgmg.mobile.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 모바일용 승인 관련 DAO<br>
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("MobileDAO")
public class MobileDAO extends IbatisAbstractDAO {

	/**
	 * 상세 조회
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read(PkgModel pkgModel) throws Exception {
		return (PkgModel) read("MobileDAO.read", pkgModel);
	}

	/**
	 * 목록 조회
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgModel> readList(PkgModel pkgModel) throws Exception {
		@SuppressWarnings("unchecked")
		List<PkgModel> readList = (List<PkgModel>) readList("MobileDAO.readList", pkgModel);
		return readList;
	}
	
	/**
	 * 보완적용내역 목록
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public List<PkgModel> readList_bp_step3(PkgModel pkgModel) throws Exception {
		@SuppressWarnings("unchecked")
		List<PkgModel> readList_bp_step3 = (List<PkgModel>) readList("MobileDAO.readList_bp_step3", pkgModel);
		return readList_bp_step3;
	}
	
	/**
	 * 검증완료 목록
	 * @param pkgModel
	 * @return
	 * @throws Exception
	 */
	public PkgModel read_skt_step3(PkgModel pkgModel) throws Exception {
		return (PkgModel) read("MobileDAO.read_skt_step3", pkgModel);
	}
	
	public PkgModel read_progress(PkgModel pkgModel) throws Exception {
		return (PkgModel) read("MobileDAO.read_progress", pkgModel);
	}
}

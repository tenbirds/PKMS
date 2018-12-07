package com.pkms.pkgmg.pkg.dao;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgStatusModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 상태별 관련 DAO
 * 
 * @author : 009
 * @Date : 2012. 5. 08.
 * 
 */
@Repository("PkgStatusDAO")
public class PkgStatusDAO extends IbatisAbstractDAO {
	
	/**
	 * 상태별 등록
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String create(PkgStatusModel model) throws Exception {
		return (String) create("pkgStatusDAO.create", model);
	}

	/**
	 * 상태별 조회
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public PkgStatusModel read(PkgStatusModel model) throws Exception {
		return (PkgStatusModel) read("pkgStatusDAO.read", model);
	}

	
	/**
	 * 상태별 수정
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(PkgStatusModel model) throws Exception {
		return (int) update("pkgStatusDAO.update", model);
	}
	
	/**
	 * 초도승인 완료 후 초도일정 수립으로 가기 위한 데이터 삭제
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	public void delete(PkgStatusModel model) throws Exception {
		delete("pkgStatusDAO.delete", model);
	}
	
	public PkgStatusModel readComment(PkgStatusModel model) throws Exception {
		return (PkgStatusModel) read("pkgStatusDAO.readComment", model);
	}
	
	public int status_gubun_verify(PkgStatusModel model) throws Exception {
		return (Integer) read("pkgStatusDAO.status_gubun_verify", model);
	}
	
}

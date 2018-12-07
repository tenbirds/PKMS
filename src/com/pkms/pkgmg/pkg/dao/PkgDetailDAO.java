package com.pkms.pkgmg.pkg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgDetailModel;
import com.pkms.pkgmg.pkg.model.PkgDetailVariableModel;
import com.pkms.pkgmg.pkg.model.PkgModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 보완적용내역 관련 DAO
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("PkgDetailDAO")
public class PkgDetailDAO extends IbatisAbstractDAO {

	/**
	 * Pkg detail 등록<br/>
	 *  - 엑셀 업로드 시 호출
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String create(PkgDetailModel model) throws Exception {
		return (String) create("PkgDetailDAO.create", model);
	}

	/**
	 * Pkg detail 목록 조회<br/>
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PkgDetailModel> readList(PkgModel model) throws Exception {
		return (List<PkgDetailModel>) readList("PkgDetailDAO.readList", model);
	}

	/**
	 * Pkg detail 상세 조회<br/>
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PkgDetailVariableModel> read(PkgDetailModel model) throws Exception {
		return (List<PkgDetailVariableModel>) readList("PkgDetailDAO.read", model);
	}

	/**
	 * detail_delete 삭제
	 * 
	 * @param UserModel
	 * @exception Exception
	 */
	public void delete(PkgModel model) throws Exception {
		delete("PkgDetailDAO.delete", model);
	}

	
	//보완적용내역 신규/보완/개선 구분값 수정 추가 1002 - ksy
	public void updateNPC_gubun(PkgDetailModel model) throws Exception{
		update("PkgDetailDAO.updateNPC_gubun", model);
	}
}

package com.pkms.pkgmg.pkg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * PKG Main 관련 DAO
 * 
 * @author : 009
 * @Date : 2012. 4. 03.
 * 
 */
@Repository("PkgDAO")
public class PkgDAO extends IbatisAbstractDAO {

	/**
	 * Pkg 상세 조회
	 * 
	 * @param PkgModel
	 * @return pkgModel
	 * @throws Exception
	 */
	public PkgModel read(PkgModel model) throws Exception {
		return (PkgModel) read("pkgDAO.read", model);
	}

	/**
	 * Pkg 목록 조회
	 * 
	 * @param PkgModel
	 * @return List<PkgModel>
	 * @throws Exception
	 */
	public List<PkgModel> readList(PkgModel model) throws Exception {
		@SuppressWarnings("unchecked")
		List<PkgModel> readList = (List<PkgModel>) readList("pkgDAO.readList", model);
		return readList;
	}
	
	/**
	 * Pkg ExcelDownload
	 * 
	 * @param PkgModel
	 * @return List<PkgModel>
	 * @throws Exception
	 */
	public List<PkgModel> excelDownList(PkgModel model) throws Exception {
		@SuppressWarnings("unchecked")
		List<PkgModel> readList = (List<PkgModel>) readList("pkgDAO.excelDownList", model);
		return readList;
	}

	/**
	 * Pkg 목록 총 개수 조회
	 * 
	 * @param PkgModel
	 * @return int
	 */
	public int readTotalCount(PkgModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("pkgDAO.readTotalCount", model);
	}

	/**
	 * PKG 이력 목록
	 * @param PkgModel
	 * @return List<PkgModel>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PkgModel> readListHistory(PkgModel model) throws Exception {
		return (List<PkgModel>) readList("pkgDAO.readListHistory", model);
	}
	
	/**
	 * Pkg에 적용된 템플릿 개수 조회
	 * 
	 * @param PkgModel
	 * @return int
	 */
	public int readTotalCountTemplate(PkgModel model) {
		return (Integer) getSqlMapClientTemplate().queryForObject("pkgDAO.readTotalCountTemplate", model);
	}

	/**
	 * PKG현황 상태변경 Update
	 * 
	 * @param PkgModel
	 * @exception Exception
	 */
	public void status_update(PkgModel model) throws Exception {
		update("pkgDAO.status_update", model);
	}
	
	/**
	 * PKG 긴급등록 상태변경 Update
	 * 
	 * @param PkgModel
	 * @exception Exception
	 */
	public void urgency_update(PkgModel model) throws Exception {
		update("pkgDAO.urgency_update", model);
	}

	/**
	 * 템플릿 seq 수정
	 * @param model
	 * @throws Exception
	 */
	public void tpl_seq_update(PkgModel model) throws Exception {
		update("pkgDAO.tpl_seq_update", model);
	}
	
	/**
	 * 검증접수/완료 시 검증 정보 업데이트
	 * @param model
	 */
	public void verify_update(PkgModel model) {
		update("pkgDAO.verify_update", model);
	}

	/**
	 * PKG 삭제
	 * @param model
	 * @throws Exception
	 */
	public void delete(PkgModel model) throws Exception {
		delete("pkgDAO.delete", model);
	}
	
	public PkgModel popupProgressRead(PkgModel model) throws Exception {
		return (PkgModel) read("pkgDAO.popupProgressRead", model);
	}
	
	public PkgModel popupProgressRead_Dev(PkgModel model) throws Exception {
		return (PkgModel) read("pkgDAO.popupProgressRead_Dev", model);
	}
	
	/**
	 * PKG print
	 * @param PkgModel
	 * @return List<PkgModel>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PkgModel> printRead_Time(PkgModel model) throws Exception {
		return (List<PkgModel>) readList("pkgDAO.printRead_Time", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<PkgModel> printRead_EQ(PkgModel model) throws Exception {
		return (List<PkgModel>) readList("pkgDAO.printRead_EQ", model);
	}

	@SuppressWarnings("unchecked")
	public List<PkgModel> printRead_PnCr(PkgModel model) throws Exception {
		return (List<PkgModel>) readList("pkgDAO.printRead_PnCr", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<PkgModel> workCntList(PkgModel model) throws Exception {
		return (List<PkgModel>) readList("pkgDAO.workCntList", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<PkgModel> workPkgList(PkgModel model) throws Exception {
		return (List<PkgModel>) readList("pkgDAO.workPkgList", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<PkgModel> workLimitList(PkgModel model) throws Exception {
		return (List<PkgModel>) readList("pkgDAO.workLimitList", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<PkgModel> helloList(PkgModel model) throws Exception {
		return (List<PkgModel>) readList("pkgDAO.helloList", model);
	}

	@SuppressWarnings("unchecked")
	public List<PkgModel> helloList2(PkgModel model) throws Exception {
		return (List<PkgModel>) readList("pkgDAO.helloList2", model);
	}
	
	public void workLimitSave(PkgModel model) throws Exception {
		update("pkgDAO.workLimitSave", model);
	}
	
	public void security_update(PkgModel model) throws Exception {
		update("pkgDAO.security_update", model);
	}

}

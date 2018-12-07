package com.pkms.pkgmg.pkg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

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
@Repository("PkgDetailVariablDAO")
public class PkgDetailVariablDAO extends IbatisAbstractDAO {

	/**
	 * 보완적용내역 등록<br/>
	 *  - 엑셀 업로드 시 호출
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String create(PkgDetailVariableModel model) throws Exception {
		return (String) create("PkgDetailVariablDAO.create", model);
	}
	
	/**
	 * 보완적용내역 수정<br/>
	 *  - 화면에서 개별 수정 시 호출
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String update(List<PkgDetailVariableModel> list) throws Exception {
		return (String) create("PkgDetailVariablDAO.update", list);
	}
	
	public void add1(PkgDetailVariableModel pkgDetailVariableModel) throws Exception {
		create("PkgDetailVariablDAO.add1", pkgDetailVariableModel);
	}
	
	public void add2(PkgDetailVariableModel pkgDetailVariableModel) throws Exception {
		create("PkgDetailVariablDAO.add2", pkgDetailVariableModel);
	}
	/**
	 * detailSeq MAX값
	 * 
	 * 
	 * @throws Exception
	 */

	public int detailSeq() throws Exception {
		return (Integer) read("PkgDetailVariablDAO.detailSeq",null);
	}
	
	/**
	 * detailNO MAX값
	 * 
	 * 
	 * @throws Exception
	 */
	public int detailNO(String pkg_seq) throws Exception {
		return (Integer) read("PkgDetailVariablDAO.detailNO",pkg_seq);
	}
	
	/**
	 * 보완적용내역 전체 삭제<br/>
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void deleteAll(PkgModel model) throws Exception {
		delete("PkgDetailVariablDAO.deleteAll", model);
	}
	
	/**
	 * 보완적용내역 개별 삭제<br/>
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void delete(PkgModel model) throws Exception {
		delete("PkgDetailVariablDAO.delete", model);
	}
	
	public void okUpdate(List<String> okArray)throws Exception{
		update("PkgDetailVariablDAO.okUpdate", okArray);
	}
	public void nokUpdate(List<String> nokArray)throws Exception{
		update("PkgDetailVariablDAO.nokUpdate", nokArray);
	}

	public void cokUpdate(List<String> cokArray)throws Exception{
		update("PkgDetailVariablDAO.cokUpdate", cokArray);
	}
	
	/*
	 * 개발 ok nok cok 추가
	 */
	public void okUpdate_dev(List<String> okArray)throws Exception{
		update("PkgDetailVariablDAO.okUpdate_dev", okArray);
	}
	public void nokUpdate_dev(List<String> nokArray)throws Exception{
		update("PkgDetailVariablDAO.nokUpdate_dev", nokArray);
	}

	public void cokUpdate_dev(List<String> cokArray)throws Exception{
		update("PkgDetailVariablDAO.cokUpdate_dev", cokArray);
	}
	
	public void bypassUpdate_dev(List<String> bypassArray)throws Exception{
		update("PkgDetailVariablDAO.bypassUpdate_dev", bypassArray);
	}
}

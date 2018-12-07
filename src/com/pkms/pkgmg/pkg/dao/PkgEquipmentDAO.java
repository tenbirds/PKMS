package com.pkms.pkgmg.pkg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 초도/확대 장비 관련 DAO
 * 
 * @author : 009
 * @Date : 2012. 5. 08.
 * 
 */
@Repository("PkgEquipmentDAO")
public class PkgEquipmentDAO extends IbatisAbstractDAO {

	/**
	 * 새로운 EqWorkSeq(작업시스템연동 key) 생성(sequence)
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String readEqWorkSeq(PkgEquipmentModel model) throws Exception {
		return (String) read("pkgEquipmentDAO.readEqWorkSeq", model);
	}

	/**
	 * 초도/확대 적용 일정 등록
	 * @param model
	 * @throws Exception
	 */
	public void create(PkgEquipmentModel model) throws Exception {
		create("pkgEquipmentDAO.create", model);
	}
	
	public void create21(PkgEquipmentModel model) throws Exception {
		create("pkgEquipmentDAO.create21", model);
	}
	
	public void update(PkgEquipmentModel model) throws Exception {
		update("pkgEquipmentDAO.update", model);
	}
	
	public void s_work_update(PkgEquipmentModel model) throws Exception {
		update("pkgEquipmentDAO.s_work_update", model);
	}
	
	/**
	 * 초도/확대 적용 일정 조회
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PkgEquipmentModel> readList(PkgEquipmentModel model) throws Exception {
		return (List<PkgEquipmentModel>)readList("pkgEquipmentDAO.readList", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<PkgEquipmentModel> read21List(PkgEquipmentModel model) throws Exception {
		return (List<PkgEquipmentModel>)readList("pkgEquipmentDAO.read21List", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<PkgEquipmentModel> readOkList(PkgEquipmentModel model) throws Exception {
		return (List<PkgEquipmentModel>)readList("pkgEquipmentDAO.readOkList", model);
	}

	/**
	 * 초도/확대 적용 일정 삭제
	 * 
	 * @param model
	 * @throws Exception
	 */
	public void delete(PkgEquipmentModel model) throws Exception {
		delete("pkgEquipmentDAO.delete", model);
	}
	
	public PkgEquipmentModel readName(PkgEquipmentModel model) throws Exception {
		return (PkgEquipmentModel)read("pkgEquipmentDAO.readName", model);
	}
	
	public PkgEquipmentModel readOrd(PkgEquipmentModel model) throws Exception {
		return (PkgEquipmentModel)read("pkgEquipmentDAO.readOrd", model);
	}
	
	public PkgEquipmentModel maxOrd(PkgEquipmentModel model) throws Exception {
		return (PkgEquipmentModel)read("pkgEquipmentDAO.maxOrd", model);
	}
	
	public void createAccess(PkgEquipmentModel model) throws Exception {
		create("pkgEquipmentDAO.createAccess", model);
	}
	
	public void updateAccess(PkgEquipmentModel model) throws Exception {
		create("pkgEquipmentDAO.updateAccess", model);
	}
	
	@SuppressWarnings("unchecked")
	public List<PkgEquipmentModel> readAccess(PkgEquipmentModel model) throws Exception {
		return (List<PkgEquipmentModel>)readList("pkgEquipmentDAO.readAccess", model);
	}
	
	public void updateResultAccess(PkgEquipmentModel model) throws Exception {
		create("pkgEquipmentDAO.updateResultAccess", model);
	}
	
	public void deleteAccess(PkgEquipmentModel model) throws Exception {
		delete("pkgEquipmentDAO.deleteAccess", model);
	}
}

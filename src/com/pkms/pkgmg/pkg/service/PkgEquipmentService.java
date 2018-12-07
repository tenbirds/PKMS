package com.pkms.pkgmg.pkg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.pkgmg.pkg.dao.PkgEquipmentDAO;
import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;

/**
 * 초도/확대 장비 관련 Service
 * 
 * @author : 009
 * @Date : 2012. 5. 08.
 */
@Service("PkgEquipmentService")
public class PkgEquipmentService implements PkgEquipmentServiceIf {

	@Resource(name = "PkgEquipmentDAO")
	private PkgEquipmentDAO pkgEquipmentDAO;

	@Override
	public String readEqWorkSeq(PkgEquipmentModel model) throws Exception {
		return pkgEquipmentDAO.readEqWorkSeq(model);
	}
	
	@Override
	public void create(PkgEquipmentModel model) throws Exception {
		pkgEquipmentDAO.create(model);
	}
	
	@Override
	public void create21(PkgEquipmentModel model) throws Exception {
		pkgEquipmentDAO.create21(model);
	}
	
	@Override
	public void update(PkgEquipmentModel model) throws Exception {
		pkgEquipmentDAO.update(model);
	}
	
	@Override
	public void s_work_update(PkgEquipmentModel model) throws Exception {
		pkgEquipmentDAO.s_work_update(model);
	}
	
	@Override
	public List<PkgEquipmentModel> readList(PkgEquipmentModel model) throws Exception {
		return pkgEquipmentDAO.readList(model);
	}
	
	@Override
	public List<PkgEquipmentModel> read21List(PkgEquipmentModel model) throws Exception {
		return pkgEquipmentDAO.read21List(model);
	}
	
	@Override
	public void delete(PkgEquipmentModel model) throws Exception {
		pkgEquipmentDAO.delete(model);
	}
	
	@Override
	public PkgEquipmentModel readName(PkgEquipmentModel model) throws Exception {
		return pkgEquipmentDAO.readName(model);
	}
	
	@Override
	public PkgEquipmentModel readOrd(PkgEquipmentModel model) throws Exception {
		return pkgEquipmentDAO.readOrd(model);
	}
	
	@Override
	public PkgEquipmentModel maxOrd(PkgEquipmentModel model) throws Exception {
		return pkgEquipmentDAO.maxOrd(model);
	}
	
	@Override
	public void createAccess(PkgEquipmentModel model) throws Exception {
		pkgEquipmentDAO.createAccess(model);
	}
	
	@Override
	public void updateAccess(PkgEquipmentModel model) throws Exception {
		pkgEquipmentDAO.updateAccess(model);
	}
	
	@Override
	public List<PkgEquipmentModel> readAccess(PkgEquipmentModel model) throws Exception {
		return pkgEquipmentDAO.readAccess(model);
	}
	
	@Override
	public void updateResultAccess(PkgEquipmentModel model) throws Exception {
		pkgEquipmentDAO.updateResultAccess(model);
	}
	
	@Override
	public void deleteAccess(PkgEquipmentModel model) throws Exception {
		pkgEquipmentDAO.deleteAccess(model);
	}
}

package com.pkms.verify_tem_mg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.pkms.verify_tem_mg.model.PkgCheckListManagerModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("PkgCheckListMgDao")
public class PkgCheckListMgDao extends IbatisAbstractDAO {
	
	public List<?> readList(PkgCheckListManagerModel pkgCheckListManagerModel) {
		return readList("pkgCheckListMgDao.readList", pkgCheckListManagerModel);
	}

	public int readTotalCount(PkgCheckListManagerModel pkgCheckListManagerModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("pkgCheckListMgDao.readTotalCount", pkgCheckListManagerModel);
	}
	
	public List<?> codeList(PkgCheckListManagerModel pkgCheckListManagerModel) {
		return readList("pkgCheckListMgDao.codeList", pkgCheckListManagerModel);
	}
	
	public PkgCheckListManagerModel readone(PkgCheckListManagerModel pkgCheckListManagerModel) {
		return (PkgCheckListManagerModel) read("pkgCheckListMgDao.readone", pkgCheckListManagerModel);
	}
	public PkgCheckListManagerModel insert(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception {
		create("pkgCheckListMgDao.insert", pkgCheckListManagerModel);
		return pkgCheckListManagerModel;
	}
	
	public void delete(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception {
		delete("pkgCheckListMgDao.delete", pkgCheckListManagerModel);
	}
	
	public void update(PkgCheckListManagerModel pkgCheckListManagerModel) throws Exception {
		update("pkgCheckListMgDao.update", pkgCheckListManagerModel);
	}
	
	
	
	
	
}

package com.pkms.board.pkgVer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.board.pkgVer.model.PkgVerModel;

import com.wings.dao.IbatisAbstractDAO;

@Repository("PkgVerDao")
public class PkgVerDao extends IbatisAbstractDAO{
	@SuppressWarnings("unchecked")
	public List<PkgVerModel> readList(PkgVerModel pkgVerModel){
		return (List<PkgVerModel>)readList("pkgVerDao.readList", pkgVerModel);
	}
	
	public int readTotalCount(PkgVerModel pkgVerModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("pkgVerDao.readTotalCount", pkgVerModel);
	}
	
	public PkgVerModel read(PkgVerModel pkgVerModel){
		return (PkgVerModel)read("pkgVerDao.read", pkgVerModel);
	}
	
	public void create(PkgVerModel pkgVerModel){
		create("pkgVerDao.create", pkgVerModel);
	}
	
	public void update(PkgVerModel pkgVerModel){
		update("pkgVerDao.update", pkgVerModel);
	}
	
	public void delete(PkgVerModel pkgVerModel){
		delete("pkgVerDao.delete", pkgVerModel);
	}
	
}

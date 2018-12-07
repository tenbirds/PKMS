package com.pkms.pkgmg.access.service;

import java.util.List;

import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;

public interface AccessServiceIf {
	public List<Pkg21Model> readList(Pkg21Model pkg21Model) throws Exception;
	public Pkg21Model read(Pkg21Model pkg21Model) throws Exception;
	public void create(Pkg21Model pkg21Model) throws Exception;
	public void update(Pkg21Model pkg21Model) throws Exception;
	public void pkg_status_update(Pkg21Model pkg21Model) throws Exception;
	public List<Pkg21Model> getChkList(Pkg21Model pkg21Model,String chk_type) throws Exception;
	public void pkg_chk_create(Pkg21Model pkg21Model) throws Exception;
	public void pkg_chk_delete(Pkg21Model pkg21Model) throws Exception;
	public List<PkgEquipmentModel> getPkgEquipment(Pkg21Model pkg21Model, String work_gubun) throws Exception;
	public void equipment_delete_file(Pkg21Model pkg21Model) throws Exception;
	public void pkg_status_delete(Pkg21Model pkg21Model) throws Exception;
	public void pkg_status_delete_like(Pkg21Model pkg21Model) throws Exception;
	public Pkg21Model copy_cnt(Pkg21Model pkg21Model) throws Exception;
	public void copy_file(Pkg21Model pkg21Model) throws Exception;
	public void copy_file_delete(Pkg21Model pkg21Model) throws Exception;
	public void pkg_chk_delete_all(Pkg21Model pkg21Model) throws Exception;
	public void not_like_delete_file(Pkg21Model pkg21Model) throws Exception;
	public void tangoWork(Pkg21Model pkg21Model, String work_gubun) throws Exception;
//	public void tangoWork(Pkg21Model pkg21Model, String work_gubun, String max_ord) throws Exception;
}

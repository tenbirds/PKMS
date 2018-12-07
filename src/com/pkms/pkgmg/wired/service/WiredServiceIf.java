package com.pkms.pkgmg.wired.service;

import java.util.List;

import com.pkms.pkgmg.pkg.model.PkgEquipmentModel;
import com.pkms.pkgmg.pkg21.model.Pkg21FileModel;
import com.pkms.pkgmg.pkg21.model.Pkg21Model;
import com.pkms.sys.system.model.SystemFileModel;

public interface WiredServiceIf {
	public List<Pkg21Model> readList(Pkg21Model pkg21Model) throws Exception;
	public Pkg21Model read(Pkg21Model pkg21Model) throws Exception;
	public void create(Pkg21Model pkg21Model) throws Exception;
	public void update(Pkg21Model pkg21Model) throws Exception;
	public void pkg_status_update(Pkg21Model pkg21Model) throws Exception;
	public void pkg_dev_status_update(Pkg21Model pkg21Model) throws Exception;
	public void pkg_verify_update(Pkg21Model pkg21Model) throws Exception;
	public Pkg21Model peTypeRead(Pkg21Model pkg21Model) throws Exception;
	public List<PkgEquipmentModel> getPkgEquipment(Pkg21Model pkg21Model, String work_gubun) throws Exception;
	public List<PkgEquipmentModel> getPkgEquipment4E(Pkg21Model pkg21Model, String work_gubun) throws Exception;
	public List<Pkg21Model> getPkgVol(Pkg21Model pkg21Model) throws Exception;
	public void pkg_vol_create(Pkg21Model pkg21Model) throws Exception;
	public void pkg_vol_update(Pkg21Model pkg21Model) throws Exception;
	public List<Pkg21FileModel> pkg_result_list(Pkg21Model pkg21Model) throws Exception;
	public void pkg_status_delete(Pkg21Model pkg21Model) throws Exception;
	public void pkg_status_delete_like(Pkg21Model pkg21Model) throws Exception;
	public int readEQCntLeft(Pkg21Model pkg21Model) throws Exception;
	public void pkg_cha_yn_update(Pkg21Model pkg21Model) throws Exception;
	public List<SystemFileModel> sysFileList(SystemFileModel systemFileModel) throws Exception;
	public void tangoWork(Pkg21Model pkg21Model, String work_gubun) throws Exception;
	public void tango_E_Delete (Pkg21Model pkg21Model, String eq_seq) throws Exception;
}

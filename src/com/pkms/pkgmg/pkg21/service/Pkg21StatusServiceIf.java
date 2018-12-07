package com.pkms.pkgmg.pkg21.service;

import java.util.List;

import com.pkms.pkgmg.pkg21.model.Pkg21Model;

public interface Pkg21StatusServiceIf {
	public void create(Pkg21Model pkg21Model) throws Exception;
	public void update(Pkg21Model pkg21Model) throws Exception;
	public Pkg21Model read(Pkg21Model pkg21Model) throws Exception;
	public String real_status(Pkg21Model pkg21Model) throws Exception;
	public Pkg21Model pkg_user_all_yn(Pkg21Model pkg21Model) throws Exception;
	
	/**
	 * 20181115 eryoon 추가
	 * @param pkg21Model
	 * @return
	 * @throws Exception
	 */
	public List<Pkg21Model> readCellUserList (Pkg21Model pkg21Model) throws Exception;
	public Pkg21Model readEqCnt(Pkg21Model pkg21Model) throws Exception;
}

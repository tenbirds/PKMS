package com.pkms.pkgmg.wired.service;

import com.pkms.pkgmg.pkg21.model.Pkg21Model;

public interface WiredStatusServiceIf {
	public void create(Pkg21Model pkg21Model) throws Exception;
	public void update(Pkg21Model pkg21Model) throws Exception;
	public void statusUpdate(Pkg21Model pkg21Model) throws Exception;
	public Pkg21Model read(Pkg21Model pkg21Model) throws Exception;
	public String real_status(Pkg21Model pkg21Model) throws Exception;
	public Pkg21Model pkg_user_all_yn(Pkg21Model pkg21Model) throws Exception;
}

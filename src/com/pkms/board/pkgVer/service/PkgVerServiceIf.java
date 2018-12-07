package com.pkms.board.pkgVer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.pkms.board.pkgVer.model.PkgVerModel;

public interface PkgVerServiceIf {
	public TreeMap<String, ArrayList<PkgVerModel>> readList(PkgVerModel pkgVerModel) throws Exception;
	
	//public List<PkgVerModel> readList(PkgVerModel pkgVerModel) throws Exception;
	
	public PkgVerModel read(PkgVerModel pkgVerModel) throws Exception;
	
	public void create(PkgVerModel pkgVerModel) throws Exception;
	
	public void update(PkgVerModel pkgVerModel) throws Exception;
	
	public void delete(PkgVerModel pkgVerModel) throws Exception;
	
}

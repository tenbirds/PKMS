package com.pkms.newpncrmg.service;

import java.util.List;

import com.pkms.newpncrmg.model.NewpncrModel;

public interface NewpncrDuplicateCheckServiceIf {
	
	public void create(NewpncrModel newpncrModel) throws Exception;
	
	public NewpncrModel read(NewpncrModel newpncrModel) throws Exception;

	public List<?> readList(NewpncrModel newpncrModel) throws Exception;

}

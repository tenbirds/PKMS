package com.pkms.common.owms.service;

import com.pkms.common.owms.model.OwmsModel;

public interface OwmsServiceIf {

	public void create(OwmsModel owmsModel);
	
	public OwmsModel read(OwmsModel owmsModel) throws Exception;

}

package com.pkms.tempmg.service;

import java.util.List;

import com.pkms.tempmg.model.TempmgModel;

public interface TempmgServiceIf {

	public TempmgModel create(TempmgModel tempmgModel) throws Exception;

	public TempmgModel read(TempmgModel tempmgModel) throws Exception;
	
	public List<?> readList(TempmgModel tempmgModel) throws Exception;

	public void update(TempmgModel tempmgModel) throws Exception;

	public String excelDownload(TempmgModel tempmgModel) throws Exception;
}

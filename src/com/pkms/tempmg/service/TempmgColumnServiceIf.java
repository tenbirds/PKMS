package com.pkms.tempmg.service;

import java.util.List;

import com.pkms.tempmg.model.TempmgModel;

public interface TempmgColumnServiceIf {

	public TempmgModel create(TempmgModel tempmgModel) throws Exception;

	public TempmgModel read(TempmgModel tempmgModel) throws Exception;

	public List<TempmgModel> readList(TempmgModel tempmgModel) throws Exception;

	public void update(TempmgModel tempmgModel) throws Exception;

	public void delete(TempmgModel tempmgModel) throws Exception;

	public String excelDownload(TempmgModel tempmgModel) throws Exception;
	
	public String newExcelDownload(TempmgModel tempmgModel) throws Exception;
}

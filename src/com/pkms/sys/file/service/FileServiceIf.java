package com.pkms.sys.file.service;

import java.util.List;

import com.pkms.sys.file.model.FileModel;


public interface FileServiceIf {
	
	
	public List<?> readList(FileModel fileModel) throws Exception;
	public List<FileModel> nameList(FileModel fileModel) throws Exception;
	public List<FileModel> gubunList(FileModel fileModel) throws Exception;
	public void mailSend(FileModel fileModel) throws Exception;
	public void confirmUpdate(FileModel fileModel) throws Exception;
	public String fileExcelDownload(FileModel fileModel) throws Exception;
}

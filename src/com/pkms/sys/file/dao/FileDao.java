package com.pkms.sys.file.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.sys.file.model.FileModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("FileDao")
public class FileDao extends IbatisAbstractDAO{

	public List<?> readList(FileModel fileModel) throws Exception{
		return readList("File.list", fileModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<FileModel> nameList(FileModel fileModel) throws Exception{
		return (List<FileModel>) readList("File.nameList", fileModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<FileModel> gubunList(FileModel fileModel) throws Exception{
		return (List<FileModel>) readList("File.gubunList", fileModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<FileModel> mailInfo(FileModel fileModel) throws Exception{
		return (List<FileModel>) readList("File.mailInfo", fileModel);
	}
	
	@SuppressWarnings("unchecked")
	public List<FileModel> mailInfo_Op(FileModel fileModel) throws Exception{
		return (List<FileModel>) readList("File.mailInfo_Op", fileModel);
	}
	
	public void confirmUpdate(FileModel fileModel){
		update("File.confirmUpdate",fileModel);
	}
}

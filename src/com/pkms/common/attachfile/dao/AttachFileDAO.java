package com.pkms.common.attachfile.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.sys.system.model.SystemFileModel;
import com.wings.dao.IbatisAbstractDAO;

/**
 * 파일정보 관리를 위한 데이터 처리 클래스
 * 
 */
@Repository("AttachFileDAO")
public class AttachFileDAO extends IbatisAbstractDAO {

	Logger log = Logger.getLogger(this.getClass());

	public void createAll(List<AttachFileModel> fileList) {
		create("AttachFileDAO.createAll", fileList);
	}

	public void create(AttachFileModel attachFileModel) {
		create("AttachFileDAO.create", attachFileModel);
	}

	@SuppressWarnings("unchecked")
	public List<AttachFileModel> readList(AttachFileModel attachFileModel) {
		return (List<AttachFileModel>) readList("AttachFileDAO.readList", attachFileModel);
	}

	public void deleteAll(AttachFileModel attachFileModel) {
		delete("AttachFileDAO.deleteAll", attachFileModel);
	}

	public void delete(AttachFileModel attachFileModel) {
		delete("AttachFileDAO.delete", attachFileModel);
	}
	
	public List<?> deleteList(AttachFileModel attachFileModel) {
		return readList("AttachFileDAO.deleteList", attachFileModel);
	}

	public AttachFileModel read(AttachFileModel attachFileModel) {
		return (AttachFileModel)read("AttachFileDAO.read", attachFileModel);
	}
	
	public String readNextStringId() {
		return (String) getSqlMapClientTemplate().queryForObject("AttachFileDAO.NextStringId");
	}
	
	@SuppressWarnings("unchecked")
	public List<AttachFileModel> idreadList(AttachFileModel attachFileModel) {
		return (List<AttachFileModel>) readList("AttachFileDAO.idReadList", attachFileModel);
	}

	public int fileIdx(SystemFileModel systemFileModel) {
		return (Integer) read("AttachFileDAO.fileIdx", systemFileModel);
	}

	public int tree_file_move(SystemFileModel systemFileModel) {
		return (Integer) update("AttachFileDAO.tree_file_move", systemFileModel);
	}

	public int tree_file_update(SystemFileModel systemFileModel) {
		return (Integer) update("AttachFileDAO.tree_file_update", systemFileModel);
	}

	public int newfileIdx(SystemFileModel systemFileModel) {
		return (Integer) read("AttachFileDAO.newfileIdx", systemFileModel);
	}

	public void new_file_del(SystemFileModel systemFileModel) {
		delete("AttachFileDAO.new_file_del", systemFileModel);
	}
	
	
	public int docfileIdx(SystemFileModel systemFileModel) {
		return (Integer) read("AttachFileDAO.docfileIdx", systemFileModel);
	}
	
	
	
	
}

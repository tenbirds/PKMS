package com.pkms.tempmg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.common.attachfile.model.AttachFileModel;
import com.pkms.sys.system.model.SystemFileModel;
import com.pkms.tempmg.model.DocumentmgModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("DocumentmgDao")
public class DocumentmgDao extends IbatisAbstractDAO {
	
	public List<?> readList(DocumentmgModel documentmgmodel) {
		return readList("DocumentmgDao.readlist", documentmgmodel);
	}

	public int readTotalCount(DocumentmgModel documentmgmodel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("DocumentmgDao.readTotalCount", documentmgmodel);
	}
	
	public List<?> codeList(DocumentmgModel documentmgmodel) {
		return readList("DocumentmgDao.codeList", documentmgmodel);
	}
	
	public DocumentmgModel readone(DocumentmgModel documentmgmodel) {
		return (DocumentmgModel) read("DocumentmgDao.readone", documentmgmodel);
	}
	public DocumentmgModel insert(DocumentmgModel documentmgmodel) throws Exception {
		create("DocumentmgDao.insert", documentmgmodel);
//		create("DocumentmgDao.create", documentmgmodel);
		return documentmgmodel;
	}
	
	public void delete(DocumentmgModel documentmgmodel) throws Exception {
		delete("DocumentmgDao.delete", documentmgmodel);
	}
	
	public void filedelete(DocumentmgModel documentmgmodel) throws Exception {
		update("DocumentmgDao.filedelete", documentmgmodel);
	}
	
	public void update(DocumentmgModel documentmgmodel) throws Exception {
		update("DocumentmgDao.update", documentmgmodel);
	}
	
	public void create(DocumentmgModel documentmgmodel) {
		create("DocumentmgDao.create", documentmgmodel);
	}
	
	
	public void deleteList(DocumentmgModel documentmgmodel) throws Exception {
		delete("DocumentmgDao.deleteList", documentmgmodel);
	}	
	
	public int docfileIdx(DocumentmgModel documentmgmodel) {
		return (Integer) read("DocumentmgDao.docfileIdx", documentmgmodel);
	}
	
	
}

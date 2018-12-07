package com.pkms.newpncrmg.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.newpncrmg.model.NewpncrModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("NewpncrDuplicateCheckDao")
public class NewpncrDuplicateCheckDao extends IbatisAbstractDAO{

	public List<?> readList(NewpncrModel newpncrModel) {
		return readList("NewpncrDuplicateCheckDao.readList", newpncrModel);
	}

	public int readTotalCount(NewpncrModel newpncrModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("NewpncrDuplicateCheckDao.readTotalcount", newpncrModel);
	}

	public String create(NewpncrModel newpncrModel) {
		return (String) create("NewpncrDuplicateCheckDao.create", newpncrModel);
	}

}

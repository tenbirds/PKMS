package com.pkms.common.owms.dao;

import org.springframework.stereotype.Repository;

import com.pkms.common.owms.model.OwmsModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("OwmsDao")
public class OwmsDao extends IbatisAbstractDAO{

	public OwmsModel read(OwmsModel owmsModel) {
		return (OwmsModel) read("OwmsDao.read", owmsModel);
	}


}

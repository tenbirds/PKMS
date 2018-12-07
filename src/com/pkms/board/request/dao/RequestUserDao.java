package com.pkms.board.request.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.board.request.model.RequestModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("RequestUserDao")
public class RequestUserDao extends IbatisAbstractDAO{

	public RequestModel read(RequestModel requestModel) {
		return (RequestModel) read("RequestUserDao.read", requestModel);
	}

	public void create(RequestModel requestModel) {
		create("RequestUserDao.create", requestModel);
	}

	public List<?> readList(RequestModel requestModel) {
		return readList("RequestUserDao.readList", requestModel);
	}

	public void update(RequestModel requestModel) {
		update("RequestUserDao.update", requestModel);
	}

	public void delete(RequestModel requestModel) {
		delete("RequestUserDao.delete",requestModel);
	}
	
}

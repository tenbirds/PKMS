package com.pkms.board.request.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pkms.board.request.model.RequestModel;
import com.wings.dao.IbatisAbstractDAO;

@Repository("RequestDao")
public class RequestDao extends IbatisAbstractDAO{

	public String create(RequestModel requestModel) {
		return (String) create("RequestDao.create", requestModel);
	}

	public List<?> readList(RequestModel requestModel) {
		return readList("RequestDao.readList", requestModel);
	}

	public int readTotalCount(RequestModel requestModel) {
		return (Integer) getSqlMapClientTemplate().queryForObject("RequestDao.readTotalCount", requestModel);
	}

	public RequestModel read(RequestModel requestModel) {
		
		RequestModel rModel = (RequestModel) read("RequestDao.read", requestModel);
		
		if(rModel == null){
			rModel = requestModel;
		}
		
		return rModel;
		
	}

	public void update_Approve(RequestModel requestModel) {
		update("RequestDao.update_Approve", requestModel);
	}

	public void update_Reject(RequestModel requestModel) {
		update("RequestDao.update_Reject",requestModel);
	}
	
	public void update(RequestModel requestModel) {
		update("RequestDao.update", requestModel);
	}

	public void delete(RequestModel requestModel) {
		delete("RequestDao.delete", requestModel);
	}


}

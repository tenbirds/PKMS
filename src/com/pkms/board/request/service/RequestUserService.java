package com.pkms.board.request.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.board.request.dao.RequestUserDao;
import com.pkms.board.request.model.RequestModel;

@Service("RequestUserService")
public class RequestUserService implements RequestUserServiceIf {
	
	@Resource(name = "RequestUserDao")
	private RequestUserDao requestUserDao;

	@Override
	public void create(RequestModel requestModel) throws Exception {
		requestUserDao.create(requestModel);
	}
	
	@Override
	public RequestModel read(RequestModel requestModel) throws Exception {
		return requestUserDao.read(requestModel);
	}

	@Override
	public List<?> readList(RequestModel requestModel) throws Exception {
		return requestUserDao.readList(requestModel);
	}

	@Override
	public void update(RequestModel requestModel) throws Exception {
		requestUserDao.update(requestModel);
	}

	@Override
	public void delete(RequestModel requestModel) throws Exception {
		requestUserDao.delete(requestModel);
	}


}

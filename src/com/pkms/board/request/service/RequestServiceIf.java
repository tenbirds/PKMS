package com.pkms.board.request.service;

import java.util.List;

import com.pkms.board.request.model.RequestModel;

public interface RequestServiceIf {
	
	public void create(RequestModel requestModel) throws Exception;
	
	public RequestModel read(RequestModel requestModel) throws Exception;

	public List<?> readList(RequestModel requestModel) throws Exception;

	public void update(RequestModel requestModel) throws Exception;

	public void delete(RequestModel requestModel) throws Exception;

}

package com.pkms.usermg.bp.service;

import java.util.List;

import com.pkms.usermg.bp.model.BpModel;

public interface BpServiceIf {

	public void create(BpModel model) throws Exception;

	public BpModel read(BpModel model) throws Exception;

	public int readCount(BpModel model) throws Exception;

	public List<?> readList(BpModel model) throws Exception;

	public List<?> readListAll(BpModel model) throws Exception;

	public void update(BpModel model) throws Exception;
	
	public List<BpModel> readListSystem(BpModel model) throws Exception;
	
	public List<BpModel> findIdList(BpModel model) throws Exception;
	
	public void pass_update(BpModel model) throws Exception;

}

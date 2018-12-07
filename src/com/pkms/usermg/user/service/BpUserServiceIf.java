package com.pkms.usermg.user.service;

import java.util.List;

import com.pkms.usermg.user.model.BpUserModel;

public interface BpUserServiceIf {

	public void create(BpUserModel userModel) throws Exception;

	public BpUserModel read(BpUserModel userModel) throws Exception;

	public int readCount(BpUserModel userModel) throws Exception;

	public List<BpUserModel> readList(BpUserModel userModel) throws Exception;

	public void update(BpUserModel userModel) throws Exception;

	public void updateYN(BpUserModel userModel) throws Exception;

	public List<BpUserModel> bp_sys_readList(BpUserModel userModel) throws Exception;

	public void deleteDamdang(BpUserModel userModel) throws Exception;
}

package com.pkms.sys.idc.service;

import java.util.List;

import com.pkms.sys.idc.model.IdcModel;

public interface IdcServiceIf {

	public IdcModel read(IdcModel idcModel) throws Exception;

	public List<?> readList(IdcModel idcModel) throws Exception;

}

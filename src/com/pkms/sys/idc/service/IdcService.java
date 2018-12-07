package com.pkms.sys.idc.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.sys.idc.dao.IdcDAO;
import com.pkms.sys.idc.model.IdcModel;

/**
 * 
 * 
 * 국사 관련 기능 인터페이스 구현하고 있는 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 5. 11.
 * 
 */
@Service("IdcService")
public class IdcService implements IdcServiceIf {

	@Resource(name = "IdcDAO")
	private IdcDAO idcDAO;

	@Override
	public IdcModel read(IdcModel idcModel) throws Exception {

		idcModel = idcDAO.read(idcModel);

		return idcModel;
	}

	@Override
	public List<?> readList(IdcModel idcModel) throws Exception {

		/*
		 * DAO에서 정보 목록 조회
		 */
		List<?> resultList = idcDAO.readList(idcModel);

		idcModel.setTotalCount(resultList.size());

		return resultList;
	}
}

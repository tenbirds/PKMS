package com.pkms.sys.group1.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group1.dao.Group1DAO;
import com.pkms.sys.group2.service.Group2ServiceIf;

/**
 * 
 * 
 * 시스템 대분류를 관리하는 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 14.
 * 
 */
@Service("Group1Service")
public class Group1Service implements Group1ServiceIf {

	@Resource(name = "Group1DAO")
	private Group1DAO group1DAO;

	@Resource(name = "Group2Service")
	private Group2ServiceIf group2Service;

	@Override
	public SysModel create(SysModel sysModel) throws Exception {

		String group1_seq = group1DAO.readNextSeq();
		sysModel.setGroup1_seq(group1_seq);
		group1DAO.create(sysModel);

		return read(sysModel);
	}

	@Override
	public SysModel read(SysModel sysModel) throws Exception {
		return group1DAO.read(sysModel);
	}

	@Override
	public List<SysModel> readList(SysModel sysModel) throws Exception {
		return group1DAO.readList(sysModel);
	}

	@Override
	public void update(SysModel sysModel) throws Exception {
		group1DAO.update(sysModel);
	}

	@Override
	public void delete(SysModel sysModel) throws Exception {
		group1DAO.delete(sysModel);
		List<?> subDeleteList = group2Service.readList(sysModel);
		for (Object object : subDeleteList) {
			group2Service.delete((SysModel) object);
		}
	}
	
	@Override
	public List<SysModel> searchList(SysModel sysModel) throws Exception {
		if("0".equals(sysModel.getSearchGubun())){
			return group1DAO.searchList_0(sysModel);
		}else{
			
			return group1DAO.searchList_1(sysModel);
		}
	}
}

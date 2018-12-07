package com.pkms.sys.group3.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group3.dao.Group3DAO;
import com.pkms.sys.system.service.SystemServiceIf;

/**
 * 
 * 
 * 시스템 중분류를 관리하는 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Service("Group3Service")
public class Group3Service implements Group3ServiceIf {

	@Resource(name = "Group3DAO")
	private Group3DAO group3DAO;

	@Resource(name = "SystemService")
	private SystemServiceIf systemService;

	@Override
	public SysModel create(SysModel sysModel) throws Exception {

		String group3_seq = group3DAO.readNextSeq();
		sysModel.setGroup3_seq(group3_seq);
		group3DAO.create(sysModel);

		return read(sysModel);
	}

	@Override
	public SysModel read(SysModel sysModel) throws Exception {
		return group3DAO.read(sysModel);
	}

	@Override
	public List<SysModel> readList(SysModel sysModel) throws Exception {
		return group3DAO.readList(sysModel);
	}

	@Override
	public List<SysModel> readList4User(SysModel sysModel) throws Exception {
		return group3DAO.readList4User(sysModel);
	}
	
	@Override
	public void update(SysModel sysModel) throws Exception {
		group3DAO.update(sysModel);
	}

	@Override
	public void delete(SysModel sysModel) throws Exception {
		group3DAO.delete(sysModel);
		List<?> subDeleteList = systemService.readList(sysModel);
		for (Object object : subDeleteList) {
			systemService.delete((SysModel) object);
		}
	}

}

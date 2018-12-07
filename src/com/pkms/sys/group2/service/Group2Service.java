package com.pkms.sys.group2.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.group2.dao.Group2DAO;
import com.pkms.sys.group3.service.Group3ServiceIf;

/**
 * 
 * 
 * 시스템 중분류를 관리하는 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Service("Group2Service")
public class Group2Service implements Group2ServiceIf {

	@Resource(name = "Group2DAO")
	private Group2DAO group2DAO;

	@Resource(name = "Group3Service")
	private Group3ServiceIf group3Service;
	
	@Override
	public SysModel create(SysModel sysModel) throws Exception {
		
		String group2_seq = group2DAO.readNextSeq();
		sysModel.setGroup2_seq(group2_seq);
		group2DAO.create(sysModel);

		return read(sysModel);
	}

	@Override
	public SysModel read(SysModel sysModel) throws Exception {
		return group2DAO.read(sysModel);
	}

	@Override
	public List<SysModel> readList(SysModel sysModel) throws Exception {
		return group2DAO.readList(sysModel);
	}

	@Override
	public List<SysModel> readList4User(SysModel sysModel) throws Exception {
		return group2DAO.readList4User(sysModel);
	}
	
	@Override
	public void update(SysModel sysModel) throws Exception {
		group2DAO.update(sysModel);
	}

	@Override
	public void delete(SysModel sysModel) throws Exception {
		group2DAO.delete(sysModel);
		List<?> subDeleteList = group3Service.readList(sysModel);
		for (Object object : subDeleteList) {
			group3Service.delete((SysModel) object);
		}

	}

}

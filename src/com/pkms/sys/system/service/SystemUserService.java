package com.pkms.sys.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.org.model.OrgModel;
import com.pkms.org.model.OrgModel.ORG_OPTION;
import com.pkms.org.service.OrgServiceIf;
import com.pkms.sys.common.model.SysModel;
import com.pkms.sys.system.dao.SystemUserDAO;
import com.pkms.sys.system.model.SystemUserModel;
import com.pkms.usermg.user.model.SktUserModel;
import com.pkms.usermg.user.service.SktUserServiceIf;

/**
 * 
 * 
 * 시스템 담당자를 관리하는 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Service("SystemUserService")
public class SystemUserService implements SystemUserServiceIf {

	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;

	@Resource(name = "OrgService")
	private OrgServiceIf orgService;

	@Resource(name = "SystemUserDAO")
	private SystemUserDAO systemUserDAO;

	@Override
	public void create(List<SystemUserModel> systemUserModelList) throws Exception {
		systemUserDAO.create(systemUserModelList);
	}

	@Override
	public SysModel read(SysModel sysModel) throws Exception {

		// Organization 조회
		OrgModel orgModel = new OrgModel();
		orgModel.setOption(ORG_OPTION.AUTH_GROUP_ONLY);
		orgService.readList(orgModel);

		sysModel.setTreeScript(orgModel.getTreeScript());

		return sysModel;
	}

	@Override
	public List<?> readListSearchUser(SysModel sysModel) throws Exception {

		SktUserModel sktUserModel = new SktUserModel();
		sktUserModel.setIndept(sysModel.getGroup_id());
		sktUserModel.setSearchCondition(sysModel.getSearchCondition());
		sktUserModel.setSearchKeyword(sysModel.getSearchKeyword());

		return sktUserService.readListAuth(sktUserModel);
	}

	@Override
	public List<?> readListSystemUser(SystemUserModel systemUserModel) throws Exception {
		return systemUserDAO.readList(systemUserModel);
	}

	@Override
	public List<?> readListAppliedToUser(SystemUserModel systemUserModel) throws Exception {
		return systemUserDAO.readListAppliedToUser(systemUserModel);
	}
	

	@Override
	public void delete(SystemUserModel systemUserModel) throws Exception {
		systemUserDAO.delete(systemUserModel);
	}
	
	@Override
	public void delete_u(SystemUserModel systemUserModel) throws Exception {
		systemUserDAO.delete_u(systemUserModel);
	}


}

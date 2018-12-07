package com.pkms.usermg.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.org.model.OrgModel;
import com.pkms.org.service.OrgServiceIf;
import com.pkms.usermg.user.dao.SktUserDAO;
import com.pkms.usermg.user.model.SktUserModel;

/**
 * 
 * @author : skywarker
 * @Date : 2012. 5. 11.
 * 
 */
@Service("SktUserPopupService")
public class SktUserPopupService implements SktUserPopupServiceIf {

	@Resource(name = "SktUserService")
	private SktUserServiceIf sktUserService;

	@Resource(name = "OrgService")
	private OrgServiceIf orgService;

	@Resource(name = "SktUserDAO")
	private SktUserDAO sktUserDAO;
	
	@Override
	public SktUserModel read(SktUserModel userModel) throws Exception {

		// Organization 조회
		OrgModel orgModel = new OrgModel();
		orgModel.setOption(userModel.getOption());
		orgService.readList(orgModel);

		userModel.setTreeScript(orgModel.getTreeScript());

		return userModel;
	}


	@Override
	public List<?> readListAuth(SktUserModel userModel) throws Exception {
		return sktUserService.readListAuth(userModel);
	}
	
	@Override
	public List<?> readListAuth_boan(SktUserModel userModel) throws Exception {
		return sktUserService.readListAuth_boan(userModel);
	}

	@Override
	public List<?> readListManager(SktUserModel userModel) throws Exception {
		return sktUserService.readListManager(userModel);
	}
	
	@Override
	public SktUserModel readEmpno(SktUserModel userModel) throws Exception {
		return sktUserDAO.readEmpno(userModel);
	}

}

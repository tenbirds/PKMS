package com.pkms.usermg.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.usermg.user.dao.SktUserDAO;
import com.pkms.usermg.user.model.SktUserModel;

/**
 * 
 * 
 * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 12.
 * 
 */
@Service("SktUserService")
public class SktUserService implements SktUserServiceIf {

	@Resource(name = "SktUserDAO")
	private SktUserDAO sktUserDAO;

	@Override
	public SktUserModel read(SktUserModel userModel) throws Exception {

		if (userModel == null) {
			throw new Exception("User model is null.");
		}
		return sktUserDAO.read(userModel);
	}

	@Override
	public List<SktUserModel> readList(SktUserModel userModel) throws Exception {
		return sktUserDAO.readList(userModel);
	}

	@Override
	public List<SktUserModel> readListAuth(SktUserModel userModel) throws Exception {
		return sktUserDAO.readListAuth(userModel);
	}
	
	@Override
	public List<SktUserModel> readListAuth_boan(SktUserModel userModel) throws Exception {
		return sktUserDAO.readListAuth_boan(userModel);
	}

	@Override
	public List<SktUserModel> readListManager(SktUserModel userModel) throws Exception {
		return sktUserDAO.readListManager(userModel);
	}

}

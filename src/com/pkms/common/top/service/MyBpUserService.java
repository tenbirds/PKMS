package com.pkms.common.top.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;

/**
 * 
 * 
 * 협력업체 정보를 관리하는 서비스<br>
 * 
 * @author : 009
 * @Date : 2012. 5. 03.
 * 
 */
@Service("MyBpUserService")
public class MyBpUserService implements MyBpUserServiceIf {

	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;

	@Override
	public BpUserModel read(BpUserModel userModel) throws Exception {
		return bpUserService.read(userModel);
	}

}

package com.pkms.sys.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.sys.common.model.SysModel;
import com.pkms.usermg.bp.model.BpModel.USE_YN;
import com.pkms.usermg.user.model.BpUserModel;
import com.pkms.usermg.user.service.BpUserServiceIf;

/**
 * 
 * 
 * 시스템 관련 업체 담당자를 관리하는 서비스 구현 클래스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 * 
 */
@Service("SystemBpUserService")
public class SystemBpUserService implements SystemBpUserServiceIf {

	@Resource(name = "BpUserService")
	private BpUserServiceIf bpUserService;

	@Override
	public List<?> readList(SysModel sysModel) throws Exception {

		BpUserModel bpUserModel = new BpUserModel();
		bpUserModel.setBp_num(sysModel.getSel_bp_num());
		bpUserModel.setUse_yn(USE_YN.YES.toString());

		return bpUserService.readList(bpUserModel);
	}

}

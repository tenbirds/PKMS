package com.pkms.common.login.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.usermg.bp.model.BpModel;
import com.pkms.usermg.bp.service.BpServiceIf;

/**
 * 
 * 
 * 협력업체 정보를 관리하는 서비스<br>
 * 
 * @author : 009
 * @Date : 2012. 5. 03.
 * 
 */
@Service("BpAddService")
public class BpAddService implements BpAddServiceIf {

	@Resource(name = "BpService")
	private BpServiceIf bpService;

	@Override
	public BpModel read(BpModel bpModel) throws Exception {

		bpModel.setBp_num(bpModel.getSession_user_group_id());
		BpModel rModel = bpService.read(bpModel);
		
		return rModel;
	}

}

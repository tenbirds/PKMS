package com.pkms.common.top.service;

import org.springframework.stereotype.Service;

import com.pkms.common.top.model.TopModel;

/**
 * 
 * 
 * 협력업체 정보를 관리하는 서비스<br>
 * 
 * @author : 009
 * @Date : 2012. 5. 03.
 * 
 */
@Service("TopService")
public class TopService implements TopServiceIf {

	@Override
	public TopModel read(TopModel model) throws Exception {
		return model;
	}


}

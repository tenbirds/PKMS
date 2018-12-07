package com.pkms.org.service;

import com.pkms.org.model.OrgModel;

/**
 * SKT 조직도 관련 서비스 인터페이스<br>
 * 
 * @author : skywarker
 * @Date : 2012. 4. 25.
 * 
 */
public interface OrgServiceIf {
	public void readList(OrgModel orgModel) throws Exception;
	
	public void initOrg() throws Exception;
}

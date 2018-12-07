package com.pkms.common.pkmscode.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pkms.common.pkmscode.dao.PkmsCodeDAO;
import com.pkms.common.pkmscode.model.PkmsCodeModel;

/**
 * 
 * 
 * @author : skywarker
 * @Date : 2012. 4. 13.
 * 
 */
@Service("PkmsCodeService")
public class PkmsCodeService implements PkmsCodeServiceIf {

	@Resource(name = "PkmsCodeDAO")
	private PkmsCodeDAO pkmsCodeDAO;

	@Override
	public List<?> readList(String gubun) throws Exception {
		PkmsCodeModel pkmsCodeModel = new PkmsCodeModel();
		pkmsCodeModel.setGubun(gubun);
		return pkmsCodeDAO.readList(pkmsCodeModel);
	}

}

package com.pkms.common.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.pkms.usermg.bp.model.BpModel;

public interface LoginServiceIf {
	
	public void SsoLogin(HttpServletRequest request, HttpServletResponse response, Model model, String loginYype) throws Exception;

	public void update_loginCnt(BpModel bpModel);

	public BpModel read_loginCnt(BpModel bpModel);

}

package com.pkms.common.session.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface SessionServiceIf {

	public static final String SESSION_KEY_COMMON_USER = "SESSION_KEY_COMMON_USER";

	public void create(HttpServletRequest request) throws Exception;

	public Object read(String key) throws Exception;

	public void create(String key, Object object) throws Exception;

	public List<String> readAuth() throws Exception;
}

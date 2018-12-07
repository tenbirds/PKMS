package com.pkms.common.session.dao;

import java.util.HashMap;

public class SessionDAO {

	private HashMap<String, Object> objectMap = new HashMap<String, Object>();

	public void create(String key, Object object) {
		this.objectMap.put(key, object);
	}

	public Object read(String key) {
		return this.objectMap.get(key);
	}
}

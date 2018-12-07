package com.wings.model;

import java.io.Serializable;

/**
 * 
 * 
 * 
 * 
 * @author : skywarker
 * @Date : 2012. 3. 12.
 * 
 */
public class CodeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String codeName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	
}

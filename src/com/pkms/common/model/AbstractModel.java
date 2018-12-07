package com.pkms.common.model;

import java.io.Serializable;

/**
 * 
 * 
 * 정보 목록 표현시 필요한 검색 및 페이지 설정 정보 표현.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 12.
 * 
 */
public class AbstractModel implements Serializable {

	public enum USER_TYPE {

		M("M", "SKT 담당자"), B("B", "협력업체 담당자"), MM("MM", "SKT 담당자(MOBILE)");

		private String code;
		private String description;

		USER_TYPE(final String code, final String description) {
			this.code = code;
			this.description = description;
		}

		@Override
		public String toString() {
			return this.code;
		}

		public String getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Paging 여부 */
	private boolean isPaging = true;

	/** 총개수 */
	private int totalCount = 0;

	/** 현재페이지 */
	private int pageIndex = 1;

	/** 페이지갯수 */
	// private int pageUnit = 10;

	/** 페이지사이즈 */
	// private int pageSize = 10;

	/** firstIndex */
	private int firstIndex = 1;

	/** lastIndex */
	// private int lastIndex = 1;

	/** recordCountPerPage */
	private int recordCountPerPage = 10;

	/** read return url[등록/수정, view: 상세] */
	private String retUrl = "";

	/** 공통필드 */
	/**
	 * 로그인 사용자 아이디
	 */
	private String session_user_id = "";

	/**
	 * 로그인 사용자 이름
	 */
	private String session_user_name = "";

	/**
	 * 로그인 사용자 타입
	 */
	private USER_TYPE session_user_type;

	/**
	 * 로그인 사용자 휴대전화
	 */
	private String session_user_mobile_phone = "";

	/**
	 * 로그인 사용자 회사 또는 부서 아이디
	 */
	private String session_user_group_id = "";

	/**
	 * 로그인 사용자 회사 또는 부서 이름
	 */
	private String session_user_group_name = "";
	
	/**
	 * 로그인 사용자 이메일
	 */
	private String session_user_email = "";
	
	public String getSession_user_id() {
		return session_user_id;
	}

	public void setSession_user_id(String session_user_id) {
		this.session_user_id = session_user_id;
	}

	public String getSession_user_name() {
		return session_user_name;
	}

	public void setSession_user_name(String session_user_name) {
		this.session_user_name = session_user_name;
	}

	public USER_TYPE getSession_user_type() {
		return session_user_type;
	}

	public void setSession_user_type(USER_TYPE session_user_type) {
		this.session_user_type = session_user_type;
	}

	public String getSession_user_mobile_phone() {
		return session_user_mobile_phone;
	}

	public void setSession_user_mobile_phone(String session_user_mobile_phone) {
		this.session_user_mobile_phone = session_user_mobile_phone;
	}

	public String getSession_user_group_id() {
		return session_user_group_id;
	}

	public void setSession_user_group_id(String session_user_group_id) {
		this.session_user_group_id = session_user_group_id;
	}

	public String getSession_user_group_name() {
		return session_user_group_name;
	}

	public void setSession_user_group_name(String session_user_group_name) {
		this.session_user_group_name = session_user_group_name;
	}
	
	public String getSession_user_email() {
		return session_user_email;
	}

	public void setSession_user_email(String session_user_email) {
		this.session_user_email = session_user_email;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	// public int getLastIndex() {
	// return lastIndex;
	// }
	//
	// public void setLastIndex(int lastIndex) {
	// this.lastIndex = lastIndex;
	// }

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	// public int getPageUnit() {
	// return pageUnit;
	// }

	// public void setPageUnit(int pageUnit) {
	// this.pageUnit = pageUnit;
	// }

	// public int getPageSize() {
	// return pageSize;
	// }
	//
	// public void setPageSize(int pageSize) {
	// this.pageSize = pageSize;
	// }

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isPaging() {
		return isPaging;
	}

	public void setPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}
}

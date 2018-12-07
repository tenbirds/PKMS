package com.pkms.common.tags.paging.pagination;

import com.pkms.common.model.AbstractModel;


/**
 * 페이징 처리 관련 정보 모델 <br>
 * 
 * 페이징 처리에 필요한 데이터를 Required Fields, Not Required Fields 로 나누었다.<br>
 * 
 * Required Fields<br>
 * : 사용자가 입력해야 하는 필드값이다.<br>
 * currentPageNo : 현재 페이지 번호.<br>
 * recordCountPerPage : 한 페이지당 게시되는 게시물 건 수.<br>
 * pageSize : 페이지 리스트에 게시되는 페이지 건수.<br>
 * totalRecordCount : 전체 게시물 건 수.<br>
 * 
 * Not Required Fields<br>
 * : 사용자가 입력한 Required Fields 값을 바탕으로 계산하여 정해지는 값이다.<br>
 * totalPageCount: 페이지 개수.<br>
 * firstPageNoOnPageList : 페이지 리스트의 첫 페이지 번호.<br>
 * lastPageNoOnPageList : 페이지 리스트의 마지막 페이지 번호.<br>
 * firstRecordIndex : 페이징 SQL의 조건절에 사용되는 시작 rownum.<br>
 * lastRecordIndex : 페이징 SQL의 조건절에 사용되는 마지막 rownum.<br>
 * 
 * 페이징 Custom 태그인 &lt;ui:pagination&gt; 사용시에 paginationInfo 필드에
 * PaginationInfo 객체를 값으로 주어야 한다.<br>
 * * 설명을 작성 하세요<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 14.
 * 
 */
public class PaginationInfo {

	/**
	 * Required Fields - 이 필드들은 페이징 계산을 위해 반드시 입력되어야 하는 필드 값들이다.
	 * 
	 * currentPageNo : 현재 페이지 번호 recordCountPerPage : 한 페이지당 게시되는 게시물 건
	 * 수 pageSize : 페이지 리스트에 게시되는 페이지 건수, totalRecordCount : 전체 게시물 건 수.
	 */

	private int currentPageNo;
	private int recordCountPerPage;
	private int pageSize;
	private int totalRecordCount;

	public PaginationInfo() {
	}
	
	public PaginationInfo(AbstractModel abstractPagingModel) {
		setCurrentPageNo(abstractPagingModel.getPageIndex());
//		setRecordCountPerPage(propertyService.getInt("recordCountPerPage"));
//		setPageSize(propertyService.getInt("pageSize"));
		
		abstractPagingModel.setFirstIndex(getFirstRecordIndex());
//		abstractPagingModel.setLastIndex(getLastRecordIndex());
		abstractPagingModel.setRecordCountPerPage(getRecordCountPerPage());
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/**
	 * Not Required Fields - 이 필드들은 Required Fields 값을 바탕으로 계산해서 정해지는 필드
	 * 값이다.
	 * 
	 * totalPageCount: 페이지 개수 firstPageNoOnPageList : 페이지 리스트의 첫 페이지 번호
	 * lastPageNoOnPageList : 페이지 리스트의 마지막 페이지 번호 firstRecordIndex : 페이징
	 * SQL의 조건절에 사용되는 시작 rownum. lastRecordIndex : 페이징 SQL의 조건절에 사용되는
	 * 마지막 rownum.
	 */

	private int totalPageCount;
	private int firstPageNoOnPageList;
	private int lastPageNoOnPageList;
	private int firstRecordIndex;
	private int lastRecordIndex;

	public int getTotalPageCount() {
		totalPageCount = ((getTotalRecordCount() - 1) / getRecordCountPerPage()) + 1;
		return totalPageCount;
	}

	public int getFirstPageNo() {
		return 1;
	}

	public int getLastPageNo() {
		return getTotalPageCount();
	}

	public int getFirstPageNoOnPageList() {
		firstPageNoOnPageList = ((getCurrentPageNo() - 1) / getPageSize()) * getPageSize() + 1;
		return firstPageNoOnPageList;
	}

	public int getLastPageNoOnPageList() {
		lastPageNoOnPageList = getFirstPageNoOnPageList() + getPageSize() - 1;
		if (lastPageNoOnPageList > getTotalPageCount()) {
			lastPageNoOnPageList = getTotalPageCount();
		}
		return lastPageNoOnPageList;
	}

	public int getFirstRecordIndex() {
		firstRecordIndex = (getCurrentPageNo() - 1) * getRecordCountPerPage();
		return firstRecordIndex;
	}

	public int getLastRecordIndex() {
		lastRecordIndex = getCurrentPageNo() * getRecordCountPerPage();
		return lastRecordIndex;
	}
	
}

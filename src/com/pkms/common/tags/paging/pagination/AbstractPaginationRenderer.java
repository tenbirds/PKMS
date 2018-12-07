package com.pkms.common.tags.paging.pagination;

import java.text.MessageFormat;

/**
 * 
 * 인터페이스 PaginationRenderer의 구현 추상클래스.<br>
 * 기본적인 페이징 기능이 구현되어 있으며, 화면에서 아래와 같이 display 된다.<br>
 * 
 * [처음][이전] 1 2 3 4 5 6 7 8 [다음][마지막]<br>
 * 
 * 클래스 변수들이 각 element와 매핑이 되는데,<br>
 * firstPageLabel = [처음]<br>
 * previousPageLabel = [이전]<br>
 * currentPageLabel = 현재 페이지 번호<br>
 * otherPageLabel = 현재 페이지를 제외한 페이지 번호<br>
 * nextPageLabel = [다음]<br>
 * lastPageLabel = [마지막]<br>
 * 
 * 클래스 변수값을 AbstractPaginationRenderer를 상속받은 하위 클래스에서 주게 되면,<br>
 * 페이징 포맷만 프로젝트 UI에 맞춰 커스터마이징 할 수 있다.<br>
 * 자세한 사항은 개발자 메뉴얼의 개발프레임워크 실행환경/화면처리/MVC/View/Pagination Tag를 참고하라.<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 14.
 * 
 */
public abstract class AbstractPaginationRenderer implements PaginationRenderer {

	public String firstPageLabel;
	public String previousPageLabel;
	public String currentPageLabel;
	public String otherPageLabel;
	public String nextPageLabel;
	public String lastPageLabel;

	public String renderPagination(PaginationInfo paginationInfo, String jsFunction) {

		StringBuffer strBuff = new StringBuffer();

		int firstPageNo = paginationInfo.getFirstPageNo();
		int firstPageNoOnPageList = paginationInfo.getFirstPageNoOnPageList();
		int totalPageCount = paginationInfo.getTotalPageCount();
		int pageSize = paginationInfo.getPageSize();
		int lastPageNoOnPageList = paginationInfo.getLastPageNoOnPageList();
		int currentPageNo = paginationInfo.getCurrentPageNo();
		int lastPageNo = paginationInfo.getLastPageNo();

		strBuff.append("<div class=\"paging margin_none\">\n");

		if (totalPageCount > pageSize) {
			if (firstPageNoOnPageList > pageSize) {
				strBuff.append("<a href=\"#\" class=\"btn_move\"><img src=\"/images/paging_prevdb.png\" alt=\"\" onclick=\"javascript:fn_readList("+Integer.toString(firstPageNo)+"); return false;\" /> </a>");
				strBuff.append("<a href=\"#\" class=\"btn_move\"><img src=\"/images/paging_prev.png\" alt=\"\" onclick=\"javascript:fn_readList("+Integer.toString(firstPageNoOnPageList - 1)+"); return false;\" /> </a>");				
//				strBuff.append(MessageFormat.format(firstPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
//				strBuff.append(MessageFormat.format(previousPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList - 1) }));
			} else {
				
				strBuff.append("<a href=\"#\" class=\"btn_move\"><img src=\"/images/paging_prevdb.png\" alt=\"\" onclick=\"javascript:fn_readList("+Integer.toString(firstPageNo)+"); return false; \" /> </a>");
				strBuff.append("<a href=\"#\" class=\"btn_move\"><img src=\"/images/paging_prev.png\" alt=\"\" onclick=\"javascript:fn_readList("+Integer.toString(firstPageNo)+"); return false; \" /> </a>");				
				
//				strBuff.append(MessageFormat.format(firstPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
//				strBuff.append(MessageFormat.format(previousPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNo) }));
			}
		}

		for (int i = firstPageNoOnPageList; i <= lastPageNoOnPageList; i++) {
			if (i == currentPageNo) {
				strBuff.append("<a class=\"active\" />"+Integer.toString(i)+"</a>");				
//				strBuff.append(MessageFormat.format(currentPageLabel, new Object[] { Integer.toString(i) }));

			} else {
				strBuff.append(MessageFormat.format(otherPageLabel, new Object[] { "javascript:"+jsFunction, Integer.toString(i), Integer.toString(i) }));
			}
		}

		if (totalPageCount > pageSize) {
			if (lastPageNoOnPageList < totalPageCount) {
				strBuff.append("<a href=\"#\" class=\"btn_move\"><img src=\"/images/paging_next.png\" alt=\"\" onclick=\"javascript:fn_readList("+Integer.toString(firstPageNoOnPageList + pageSize)+"); return false;\" /> </a>");
				strBuff.append("<a href=\"#\" class=\"btn_move\"><img src=\"/images/paging_nextdb.png\" alt=\"\" onclick=\"javascript:fn_readList("+Integer.toString(lastPageNo)+"); return false;\" /> </a>");				
			
				
//				strBuff.append(MessageFormat.format(nextPageLabel, new Object[] { jsFunction, Integer.toString(firstPageNoOnPageList + pageSize) }));
//				strBuff.append(MessageFormat.format(lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
			} else {
				
				strBuff.append("<a href=\"#\" class=\"btn_move\"><img src=\"/images/paging_next.png\" alt=\"\" onclick=\"javascript:fn_readList("+Integer.toString(lastPageNo)+"); return false;\" /> </a>");
				strBuff.append("<a href=\"#\" class=\"btn_move\"><img src=\"/images/paging_nextdb.png\" alt=\"\" onclick=\"javascript:fn_readList("+Integer.toString(lastPageNo)+"); return false;\" /> </a>");				
			
				
//				strBuff.append(MessageFormat.format(nextPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
//				strBuff.append(MessageFormat.format(lastPageLabel, new Object[] { jsFunction, Integer.toString(lastPageNo) }));
			}
		}
		strBuff.append("</div>");
		
		
		
		
		
		
		return strBuff.toString();
	}
}

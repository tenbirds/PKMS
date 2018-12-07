package com.pkms.common.tags.paging.pagination;

/**
 * 페이징 리스트의 기본적인 포맷을 제공한다.<br>
 * 이를 위해 AbstractPaginationRenderer의 클래스 변수들의 값을 세팅한다. <br>
 * 화면에서 아래와 같이 display 된다. <br>
 * 
 * [처음][이전] 1 2 3 4 5 6 7 8 [다음][마지막]<br>
 * 
 * 클래스 변수들이 각 element와 다음과 같이 매핑이 된다.<br>
 * firstPageLabel = [처음]<br>
 * previousPageLabel = [이전]<br>
 * currentPageLabel = 현재 페이지 번호<br>
 * otherPageLabel = 현재 페이지를 제외한 페이지 번호<br>
 * nextPageLabel = [다음]<br>
 * lastPageLabel = [마지막] *<br>
 * 
 * @author : skywarker
 * @Date : 2012. 3. 14.
 * 
 */
public class DefaultPaginationRenderer extends AbstractPaginationRenderer {

	public DefaultPaginationRenderer() {
		firstPageLabel = 
				"	<a class=\"direction prev\" href=\"#\" onclick=\"{0}({1}); return false;\">\n"
				+ "	<span>				</span>\n"
				+ "	<span>				</span>처음</a> \n";
		previousPageLabel = 
				"	<a class=\"direction prev\" href=\"#\" onclick=\"{0}({1}); return false;\">\n"
				+ "	<span>				</span>이전</a> \n";
		currentPageLabel = "	<strong>{0}</strong> \n";
		otherPageLabel = "	<a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a>\n";
		nextPageLabel = 
				"	<a class=\"direction next\" href=\"#\" onclick=\"{0}({1}); return false;\">다음\n"
				+ "	<span>				</span>\n"
				+ "	</a> \n";
		lastPageLabel = 
				"	<a class=\"direction next\" href=\"#\" onclick=\"{0}({1}); return false;\">\n"
				+ "	마지막 \n"
				+ "	<span>				</span>\n"
				+ "	<span>				</span>\n"
				+ "	</a>\n";
	}

	@Override
	public String renderPagination(PaginationInfo paginationInfo, String jsFunction) {

		return super.renderPagination(paginationInfo, jsFunction);
	}

}
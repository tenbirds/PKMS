<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script> 
<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>

<script type="text/javaScript">
$(document).ready(function($) {
	init_system_popup();

	/* 테이블 스크롤 제어 */
	doTable("detail_search_table", "tbl_type", "1", "0", ["390", "50", "390", "150"], "N");
});

//시스템 선택 결과 처리
function fn_system_popup_callback(system_seq, system_name) {
	$("input[id=system_seq]").val(system_seq);
	$("input[id=system_name]").val(system_name);
}

function fn_readList(pageIndex) {
	if(document.getElementById("system_seq").value == '') {
		alert('시스템은 반드시 선택하셔야 합니다.');
		return;
	}
	
	if(document.getElementById("content").value == '') {
		alert('검색어를 입력하세요.');
		document.getElementById("content").focus();
		return;
	}
	
	// 검색어없이 검색 시 전체 검색 
	document.getElementById("pageIndex").value = pageIndex;

	var form = document.getElementById("PkgModel");
	form.target = "_self";
	form.action = "<c:url value='/pkgmg/pkg/PkgDetailSearch_Popup_ReadList.do'/>";
	form.submit();
}

function fn_read(pkg_seq, pkg_detail_seq) {
	document.getElementById("pkg_seq").value = pkg_seq;
	document.getElementById("pkg_detail_seq").value = pkg_detail_seq;

	var form = document.getElementById("PkgModel");
	form.target = "_self";
	form.action = "<c:url value='/pkgmg/pkg/PkgDetailSearch_Popup_Read.do'/>";
	form.submit();
}

</script>

</head>
<body>

<form:form commandName="PkgModel" method="post" enctype="multipart/form-data">
<form:hidden path="pageIndex" />
<form:hidden path="system_seq" />
<form:hidden path="retUrl" />
<form:hidden path="pkg_seq" />
<form:hidden path="pkg_detail_seq" />


	<!-- m_tab_search PKG현황, 일정목록 검색 -->
	<div style="width:1000px; top:0px; left:0px" id="pop_wrap">
	<!--팝업 content -->
				
		<div id="pop_content" style="border-width:0px;">
			
			<a class="close_layer" onClick="window.close();" href="#"><img alt=레이어닫기 src="/images/pop_btn_close2.gif" width="15" height="14"></a>

			<h4 class="ly_header">보완적용내역 검색</h4>
			
			<div class="search"  style="width:990px;vertical-align: top; margin: 5px;">
				 <table class="sear_table1">
					<colgroup>
						<col/>
						<col width="300px" />
						<col width="50px"/>
						<col width="100px" />
						<col/>
					</colgroup>
					<tr>
						<th style="width:70px">시스템<span class='necessariness'>*</span></th>
						<td>
							<form:input path="system_name" maxlength="50" class="inp" style="width:280px" readonly="true" /> 
						</td>
						<td valign="middle">
							<img src="/images/pop_btn_select.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" />
						</td>
						<!-- <th>보완적용내역 - 제목<span class='necessariness'>*</span></th> -->
						<th>보완적용내역<span class='necessariness'>*</span></th>
						<td>
							<form:select path="content_ord">
								<form:option value="1" label="제목" />
								<form:option value="6" label="S/W블럭" />
								<form:option value="9" label="보완내역" />
								<form:option value="10" label="문제점 및 배경설명" />
								<form:option value="12" label="검증분야 및 방법" />
								<form:option value="17" label="추가요청사항 및 SKT의견" />
							</form:select>
						</td>
						<td>
							<form:input path="content" maxlength="100" onkeypress="if(event.keyCode == 13) { fn_readList(1); }" style="width:200px;" />
						</td>
					</tr>
				</table>
	
				<!--조회버튼 -->
				<div class="btn_sear">
					<a href="javascript:fn_readList('1');"></a>
				</div>
			</div>
			
			
			<div class="fakeContainer" style="width: 990px; height: 500px">
				<table id="detail_search_table" class="tbl_type" border="1">
					<thead>
						<tr>
							<th scope="col">PKG검증요청 - 제목</th>
							<th scope="col">유형</th>
							<!-- <th scope="col">보완적용내역 - 제목</th> -->
							<th scope="col">보완적용내역</th>
							<th scope="col">등록일</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${paginationInfo.totalRecordCount == 0}">
							<tr>
								<td colspan="4" height="30">검색 결과가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach var="result" items="${PkgDetailModelList}" varStatus="status">
							<tr>
								<td align="left">
									<a href="javascript:fn_pkg_read('read', '${result.pkg_seq}')">
										<c:out value="${fn:substring(result.title, 0, 30)}" /> <c:out value="${fn:length(result.title) > 30 ? '...' : ''}" />
									</a>
								</td>
								<td>${result.new_pn_cr_gubun}</td>
								<td align="left">
									<a href="javascript:fn_read('${result.pkg_seq}', '${result.pkg_detail_seq}')">
										<c:out value="${fn:substring(result.content, 0, 30)}" /> <c:out value="${fn:length(result.content) > 30 ? '...' : ''}" />
									</a>
								</td>
								<td>${result.reg_date}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		
			<div id="m_tab_pagination">
				<!--페이지수 -->
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
			</div>
		 
		</div>	
			
		 
	</div> 
	</form:form>


</body>
</html>

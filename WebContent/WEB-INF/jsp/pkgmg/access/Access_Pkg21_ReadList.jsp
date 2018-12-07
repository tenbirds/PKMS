<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="/css/font.css"/>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/ui.core.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type='text/javascript' src='/js/pkgmg/pkgmg.access.pkg21.js'></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(
		function($) {
			// Calendar Init
			doCalendar("date_start");
			doCalendar("date_end");
		}
	);
	
	function fn_group2_readList() {
		doSubmit("Pkg21Model", "/pkgmg/common/Group2_Select_Ajax_Read.do", "fn_callback_group2_readList");
	}

	function fn_callback_group2_readList(data) {
		$("select[id=group2Condition]").html(data);
	}
</script>

</head>
<body>
<form:form commandName="Pkg21Model" method="post" onsubmit="return false;">
	<form:hidden path="pkg_seq" />
	<form:hidden path="read_gubun" />
	<form:hidden path="pageIndex" />

	<div class="new_con_Div32 new_pkms_sysSearchForm">
		
		<div class="new_search fl_wrap">
			<table class="new_sear_table1 fl_wrap" style="width: 100%">
				<colgroup>
					<col width="5%">
					<col width="20%">
					<col width="5%">
					<col width="20%">
					<col width="5%">
					<col width="20%">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th class="w2">요청 or 적용 기간</th>
						<td>
							<form:input path="date_start" class="inp_w30 fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span> 
							<form:input path="date_end" class="inp_w30 fl_left" readOnly="true" />
						</td>
						<th>상태</th>
						<td class="sysc_inp">
							<form:select path="statusCondition" items="${Pkg21Model.statusConditionsList}" itemLabel="codeName" itemValue="code" />
						</td>
						<th>담당</th>
						<td>
							<form:radiobuttons path="userCondition" items="${Pkg21Model.userConditionsList}" itemLabel="codeName" itemValue="code" />
						</td>
						<td rowspan="2">
							<div class="new_btn_sear05">
								<a href="javascript:fn_readList('1');">조 회</a><br>
								<a href="javascript:fn_clearList('1');">검색 초기화</a>
							</div>
						</td>
					</tr>
					<tr>
						<th>대분류</th>
						<td class="sysc_inp">
							<form:select path="group1Condition" style="width:80%;" onchange="fn_group2_readList();">
								<form:option value="" label="전 체" />
								<form:options items="${Group1List}" itemLabel="codeName" itemValue="code" />
							</form:select>
						</td>
						<th>중분류</th>
						<td class="sysc_inp">
							<form:select path="group2Condition" style="width:80%;">
								<form:option value="" label="전 체" />
								<form:options items="${Group2List}" itemLabel="codeName" itemValue="code" />
							</form:select>
						</td>
						<th>제목</th>
						<td>
							<form:input path="searchKeyword" class="new_inp new_inp_w4" onkeypress="if(event.keyCode == 13) { fn_readList(1); }" />
						</td>
					</tr>
				</tbody>
			</table>
<!-- 			<div class="new_btn_sear">
				<a href="javascript:fn_readList('1');">조 회</a><br>
				<a href="javascript:fn_clearList('1');">검색 초기화</a>
			</div> -->
		</div>
		<br />
		<div class="con_Div2 con_area">
			<div class="con_detail fl_wrap">
				<div class="con_top_btn fl_right">
					<span class="con_top_btn"><a href="javascript:fn_access_read('new','');">신규등록</a></span>
				</div>
				<p class="mt20 fl_left">
				!!상태가 <span class="txt_red">붉은색</span> - <b>진행중</b>
				, &nbsp;<span class="txt_blue">파란색</span> - <b>완료</b>
<!-- 				, &nbsp;바탕이  &nbsp;<span style="background:#fec5e0;">분홍색</span> - <b>‘긴급’</b> -->
<!-- 				, &nbsp;<span style="background:#cefbc2;">초록색</span> - <b>‘승인대기’</b> -->
				입니다.
				</p>
				<table class="tbl_type12" summary="Access PKG현황 목록">
					<caption>Access PKG현황 <span style="font-size: 14px;font-weight:lighter;">(전체: ${paginationInfo.totalRecordCount}건)</span></caption>
					<colgroup>
						<col width="10%" />
						<col width="*" /> 
						<col width="10%" />
						<col width="*" />
						<col width="10%" />
						<col width="10%" />
						<col width="10%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="row">상태</th>
							<th scope="row">시스템(대&gt;중&gt;소&gt;시스템)</th>
							<th scope="row">버전</th>
							<th scope="row">제목</th>
							<th scope="row" class="th_height">대표담당자</th>
							<th scope="row">작성자</th>
							<th scope="row" class="th_height">작성일</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${paginationInfo.totalRecordCount == 0}">
							<tr>
								<td colspan="7" height="30">검색 결과가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach var="result" items="${Pkg21ModelList}" varStatus="status">
							<tr>
								<td>
									<c:choose>
										<c:when test="${result.status eq 209 || result.status eq 219
										|| result.status eq 229 || result.status eq 239
										|| result.status eq 249 || result.status eq 259
										|| result.status eq 269 || result.status eq 279
										|| result.status eq 299}">
											<p class="txt_blue ">
												${result.status_name}
											</p>
										</c:when>
										<c:otherwise>
											<p class="txt_red">
												${result.status_name}
											</p>
										</c:otherwise>
									</c:choose>
								</td>
								<td class="td_left">
									${result.group_depth}
								</td>
								<td>
									<c:out value="${result.ver}" />
								</td>
								<td class="td_left">
									<div style="width:200px;overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
										<a href="javascript:fn_access_read('read','${result.pkg_seq}');">
											${result.title}
										</a>
									</div>
								</td>
								<td>${result.system_user_name}</td>
								<td>${result.reg_user_name}</td>
								<td>${result.reg_date}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="con_top_area fl_right mt10">
<!-- 					<span class="con_top_btn"> -->
<!-- 						<a href="#">엑셀 다운로드</a> -->
<!-- 					</span> -->
				</div>		
				<p class="mt10">
				<!-- paging -->
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />	
				</p>
			</div>
		</div>
	</div>
</form:form>
</body>
</html>

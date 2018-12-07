<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>

<!-- Css -->
<!-- <link rel="stylesheet" type="text/css" href="/css/style.css"/> -->
<link rel="stylesheet" type="text/css" href="/css/font.css"/>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
<!-- Js -->
<script type="text/javascript" src="/js/jquery/ui.core.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type='text/javascript' src='/js/pkgmg/pkgmg.wired.pkg21.js'></script>

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
	<!-- 페이징 -->
	<form:hidden path="pageIndex" />

<!-- container -->

	<!-- Main contents -->

	<!-- PKG현황 -->
	<div class="new_con_Div32 new_pkms_sysSearchForm">
		
		<div class="new_search fl_wrap">
			<table class="new_sear_table1 fl_wrap" style="width: 100%;">
				<colgroup>
					<col width="5%">
					<col width="30%">
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
<!-- 							<input type="radio" class="fl_left"> -->
<!-- 							<span class="mr15"><label>홍길동</label></span> -->
<!-- 							<input type="radio" class="fl_left"> -->
<!-- 							<span class="ml05"><label>전체</label></span> -->
						</td>
						<td rowspan="2">
							<div class="new_btn_sear05">
								<a href="javascript:fn_readList('1');">조 회</a><br>
								<a href="javascript:fn_clearList('3');">검색 초기화</a>
							</div>
						</td>
					</tr>
					<tr>
						<th>대분류</th>
						<td class="sysc_inp">
							<form:select path="group1Condition" onchange="fn_group2_readList();">
								<form:option value="" label="전 체" />
								<form:options items="${Group1List}" itemLabel="codeName" itemValue="code" />
							</form:select>
						</td>
						<th>중분류</th>
						<td class="sysc_inp">
							<form:select path="group2Condition">
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
			
		</div>
		<br />
		
		<div class="con_Div2 con_area">
			<div class="con_detail fl_wrap">
				<div class="con_top_btn fl_right">
					<span class="con_top_btn"><a href="javascript:fn_pkg21_read('new','');">신규등록</a></span>
				</div>
				<table class="tbl_type12">
					<colgroup>
						<col width="8%">
						<col width="5%">
						<col width="24%">
						<col width="5%">
						<col width="20%">
						<col width="8%">
						<col width="6%">
						<col width="10%">
						<col width="7%">
						<col width="7%">
					</colgroup>
					<thead>
						<tr>
							<th>상태</th>
							<th class="th_height">진도율</th>
							<th>시스템(대&gt;중&gt;소&gt;시스템)</th>
							<th>버전</th>
							<th>제목</th>
							<th class="th_height">대표담당자</th>
							<th>작성자</th>
							<th class="th_height">요청/<br>검증일자</th>
							<th class="th_height">검증내역<br>개수</th>
							<th class="th_height">개선내역<br>개수</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${paginationInfo.totalRecordCount == 0}">
							<tr>
								<td colspan="10" height="30">검색 결과가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach var="result" items="${Pkg21ModelList}" varStatus="status">
							<tr>
								<td>
									<p class="txt_red">
										${result.status_name}
									</p>
								</td>
								<td>0%</td>
								<td class="td_left">
									${result.group_depth}
								</td>
								<td>
									<c:out value="${result.ver}" />
								</td>
								<td class="td_left">
									<div style="width:200px;overflow:hidden; white-space:nowrap; text-overflow:ellipsis;">
										<a href="javascript:fn_pkg21_read('read','${result.pkg_seq}');">
											${result.title}
										</a>
									</div>
								</td>
								<td>${result.system_user_name}</td>
								<td>${result.reg_user_name}</td>
								<td>${result.reg_date}</td>
								<td>3</td>
								<td>3</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="con_top_area fl_right mt10"><span class="con_top_btn"><a href="#">엑셀 다운로드</a></span></div>				
			</div>
			
			<!-- paging -->
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
			<!-- <div class="paging margin_none">
				<a href="#" class="btn_move"><img src="/images/paging_prevdb.png" alt=""/></a>
				<a href="#" class="btn_move"><img src="/images/paging_prev.png" alt=""/></a>
				<a href="#" class="active">1</a>
				<a href="#">2</a>
				<a href="#">3</a>
				<a href="#">4</a>
				<a href="#">5</a>
				<a href="#">6</a>
				<a href="#">7</a>
				<a href="#">8</a>
				<a href="#">9</a>
				<a href="#">10</a>
				<a href="#" class="btn_move"><img src="/images/paging_next.png" alt=""/></a>
				<a href="#" class="btn_move"><img src="/images/paging_nextdb.png" alt=""/></a>   
			</div> -->
			<!-- // paging -->
		</div>
		<!-- // PKG현황 -->
	</div>
	<!-- // Main contents -->

<!-- //container -->
</form:form>
</body>
</html>

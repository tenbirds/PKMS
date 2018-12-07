<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>

<link rel="stylesheet" type="text/css" href="/css/dynatree/ui.dynatree.css">
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.dynatree.js"></script>

<script type="text/javaScript" defer="defer">
$(document).ready(function($) {
//   	$("input[id=title]").val(parent.fn_child_setTitle());
 	doTable("scrollTable", "tbl_type11", "1", "0", ["100", "100","300"]);
  	fn_init();
});

function fn_init() {
	
 	if($("input[id=init]").val() == 'false') {
//  		alert($("input[id=init]").val());
 		$("#title").val(parent.fn_child_setTitle());
 		$("#searchKeyword").val(parent.fn_child_setTitle());
	  	fn_readList(1);
 	}
}

function fn_readList(pageIndex) {
	
	document.getElementById('pageIndex').value = pageIndex;
	var title = $("#title").val();
	
	var form = document.getElementById('NewpncrModel');
	form.title.value = form.searchKeyword.value;
	form.action = "<c:url value='/newpncrmg/Newpncr_Duplication_Check_Popup_ReadList.do'/>";
	form.submit();
}

function fn_searchReadList(title) {
	fn_readList();
}
function fn_popup_close() {
	parent.$('img[id=overlap]').closeDOMWindow();
}
function fn_read(gubun, id){
	var form = document.getElementById('NewpncrModel');
	
	if (gubun == "read") {
		form.retUrl.value = "/newpncrmg/Newpncr_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/newpncrmg/Newpncr_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	
	form.new_pn_cr_seq.value = id;
	
	doSubmit4File("NewpncrModel", "/newpncrmg/Newpncr_Duplication_Check_Ajax_Read.do", "fn_callback_read");
}
function fn_callback_read(data){
// 	alert(data);
	$("#newpncrDetailView").html(data);
}
</script>
</head>
<body>
	
	<form:form commandName="NewpncrModel" method="post" enctype="multipart/form-data" onsubmit="return false;">
	
		<form:hidden path="new_pn_cr_seq"/>
		<form:hidden path="init"/>
		<form:hidden path="title"/>
		<form:hidden path="retUrl" />
		<!-- 페이징 -->
		<form:hidden path="pageIndex"/>
		
		<!--팝업 전체레이아웃 시작-->
		<div style="width: 1110px;" id="pop_wrap">
		
			<!--팝업 content -->
			<div id="pop_content">

				<!--타이틀 -->
				<h4 class="ly_header">중복 확인</h4>
				
				<fieldset>
				
					<div style="width: 530px; height: 587px; float: left; vertical-align: top; border: 1px solid #dcdcdc;margin-left: 12px;">
					
						<div class="search"  style="width: 500px; vertical-align: top; margin:10px;">
						<table class="sear_table1" border="1">
							<tr>
								<th>제목</th>
								<td>
									<form:input path="searchKeyword" cssClass="txt" cssStyle="width : 300px;" onkeypress="if(event.keyCode == 13) { fn_readList(1); return;}"/>
								</td>
								<td>
									<div class="btn_sear">
										<a href="javascript:fn_readList(1)"></a>
									</div>
								</td>
							</tr>
						</table>
					</div>
					
						<div class="fakeContainer" style="width:500px;height:480px">
							<table id="scrollTable" class="tbl_type12">
								<tr>
									<th scope="col">번호</th>
									<th scope="col">상태</th>
									<!-- <th scope="col">구분</th>
									<th scope="col">시스템</th> -->
									<th scope="col">제목</th>
								</tr>
								<c:choose>
									<c:when test="${not empty newpncrDuplicateCheckModelList}">
										<c:forEach var="newpncrDuplicateCheckModelList" items="${newpncrDuplicateCheckModelList}" varStatus="status">
											<tr>
												<td><c:out value="${newpncrDuplicateCheckModelList.new_pn_cr_no}" /></td>
												<td style="text-align: left;"><c:out value="${newpncrDuplicateCheckModelList.state}" /></td>
												<%-- <td><c:out value="${newpncrDuplicateCheckModelList.new_pncr_gubun}" /></td>
												<td><c:out value="${newpncrDuplicateCheckModelList.system_name}" /></td> --%>
												<td style="text-align: left;">
													<a href="javascript:fn_read('view', '<c:out value="${newpncrDuplicateCheckModelList.new_pn_cr_seq}"/>')">
														<c:out value="${fn:substring(newpncrDuplicateCheckModelList.title, 0, 30)}"/>
														<c:out value="${fn:length(newpncrDuplicateCheckModelList.title) > 30 ? '...' : ''}"/>
													</a>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr><td colspan="5">등록된 신규/PN/CR이 없습니다.</td></tr>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
						<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />
					</div>
					
					<div id="newpncrDetailView" style="margin-left: 5px; width: 530px; height: 600px; float: left; vertical-align: top;">
						<fieldset style="border:1px solid #ddd;">
							<legend>
								기본정보
							</legend>
						
							<table class="tbl_type11">
								<colgroup>
									<col width="120">
									<col width="388">
								</colgroup>
								<tr>
									<th>제목</th>
									<td colspan="7"></td>
								</tr>
								<tr>
									<th>신규/PN/CR구분</th>
									<td></td>
								</tr>
								<tr>
									<th>우선순위</th>
									<td></td>
								</tr>
								<tr>
									<th>시스템</th>
									<td></td>
								</tr>
							</table>
						</fieldset>
						
						<fieldset class="detail_fieldset" style="width:508px;height:355px;border:1px solid #ddd;overflow-y:scroll;overview:hidden;">
							<legend>상세정보</legend>
							<table class="tbl_type11">
								<colgroup>
									<col width="120">
									<col width="388">
								</colgroup>
								<tr>
									<th>문제구분</th>
									<td></td>
								</tr>
								<tr>
									<th>문제점</th>
									<td></td>
								</tr>
								<tr>
									<th>요구사항</th>
									<td></td>
								</tr>
								<tr>
								<tr>
									<th>첨부파일1</th>
									<td colspan="3">
										
									</td>
								</tr>
								<tr>
									<th>첨부파일2</th>
									<td colspan="3">
										
									</td>
								</tr>
								<tr>
									<th>첨부파일3</th>
									<td colspan="3">
										
									</td>
								</tr>
							</table>
						</fieldset>
					</div>
				
				</fieldset>
				
				<!--상단닫기버튼 -->
				<a class="close_layer" href="javascript:fn_popup_close();"> <img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14"></a>
<!-- 
				하단 닫기버튼
				<div id="pop_footer">
					<a href="javascript:fn_confirm();"> <img alt="확인" src="/images/btn_identify.gif"></a>
					&nbsp;
					<a href="javascript:fn_init();"> <img alt="확인" src="/images/btn_initialization.gif"></a>
				</div>
 -->		
			</div>
			
			<span class="shadow shadow2"> </span> <span class="shadow shadow3"> </span> <span class="shadow shadow4"> </span>
		</div>
		<!--팝업 전체레이아웃 "끝-->
	</form:form>

</body>
</html>
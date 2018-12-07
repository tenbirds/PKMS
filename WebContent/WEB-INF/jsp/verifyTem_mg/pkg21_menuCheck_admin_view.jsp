<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty pkgCheckListMg.chk_seq ? '등록' : '수정'}" />
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javaScript"  defer="defer">

$(document).ready(function($) {
	
	$('textarea[maxlength]').keydown(function(){
	    var max = parseInt($(this).attr('maxlength'));
	    var str = $(this).val();
		if(str.length > max) {
		    $(this).val(str.substr(0, max-1));
			alert("최대 [" + max + " 자]까지 입력 가능합니다.");
		}
	});
	
});


function fn_create() {
	
	if(!fn_Validation())
		return;
	
	doSubmit4File("PkgCheckListManagerModel", "/verifyTem_mg/pkg21_menuCheck_admin_edit.do", "fn_callback_create", "저장 되었습니다.");
}
function fn_update() {
	
	if(!fn_Validation())
		return;
	
	doSubmit4File("PkgCheckListManagerModel", "/verifyTem_mg/pkg21_menuCheck_admin_edit.do", "fn_callback_update", "수정 되었습니다.");
}
function fn_delete(){
	if (confirm("삭제하시겠습니까?")) {
		doSubmit4File("PkgCheckListManagerModel", "/verifyTem_mg/pkg21_menuCheck_admin_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
	}
}
function fn_callback_create(data) {
	fn_readList();
}
function fn_callback_update(data) {
	fn_readList();
}
function fn_callback_delete(data) {
	fn_readList();
}
function fn_readList() {
	var form  = document.getElementById('PkgCheckListManagerModel');
	form.chk_type.value = "";
	form.status.value = "";
	form.title.value = "";
	form.action = "/verifyTem_mg/pkg21_menuCheck_admin.do";
	form.submit();
}
function fn_Validation(){
	
	var title = document.getElementById("title");
	var content = document.getElementById("chk_content");
	
	if(!isNullAndTrim(title, "제목을 입력해주세요.")){
		$("#title").focus();
		return false;
	}
	if(!isNullAndTrim(content, "내용을 입력해주세요.")){
		$("#chk_content").focus();
		return false;
	}
	
	return true;
}
</script>
</head>
<body>
	<form:form commandName="PkgCheckListManagerModel" method="post" enctype="multipart/form-data" onsubmit="return false;">	
	<input type="hidden" id="chk_seq" name="chk_seq" value="${pkgCheckListMg.chk_seq}">
	 <input id="pageIndex" name="pageIndex" type="hidden" value="1">
<%-- 	<input type="hidden" id="reg_user" name="reg_user" value="${sessionId}"> --%>
		
		<!-- 검색조건 유지 -->
<%-- 		<form:hidden path="searchCondition"/> --%>
<%-- 		<form:hidden path="searchKeyword"/> --%>
		
		
		<sec:authentication property="principal.username" var="sessionId"/>
		
		
		<div class="con_Div32">
			<div class="con_area">
				<div class="con_detail_long">
					<div class="con_top_btn fl_wrap">
						<c:choose>
							<c:when test="${registerFlag == '등록'}">
							<span><a href="javascript:fn_create()">저장</a></span>
							</c:when>
							<c:otherwise>
								<span><a href="javascript:fn_delete()">삭제</a></span>
								<span><a href="javascript:fn_update()">수정</a></span>
							</c:otherwise>
						</c:choose>
						<span><a href="javascript:fn_readList()">목록</a></span>
					</div>
					<!--기본정보 -->
		
					<h3 class="stitle">기본정보</h3>
					<table class="tbl_type11"> 
						<colgroup>
							<col width="10%">
							<col width="40%">
							<col width="10%">
							<col width="40%">
						</colgroup>
						<tr>
							<th>제목 <span  class='necessariness'>*</span></th>
							<td colspan="3"> 
							<input type="text" id="title" name="title" value='${pkgCheckListMg.title }'class="new_inp inp_w95" maxlength="100" />
							</td>
						</tr>
						<tr>
							<th>사용분류</th>
							<td>
								<select id="chk_type" name="chk_type">
									<c:forEach var="result" items="${code_list}" varStatus="status">
										<c:choose>
											<c:when test="${result.common_code eq pkgCheckListMg.chk_type}">
												<option value="${result.common_code}" selected="selected" >${result.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${result.common_code}">${result.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>
							<th>상태</th>
							<td>
								<select id="status" name="status">
									<c:choose>
										<c:when test="${pkgCheckListMg.status eq 'Y'}">
											<option value="Y" selected="selected" >사용</option>
											<option value="N">미사용</option>
										</c:when>
										<c:otherwise>
											<option value="Y">사용</option>
											<option value="N" selected="selected" >미사용</option>
										</c:otherwise>
									</c:choose>
								</select>
							</td>
						</tr>
						<tr>
							<th>내용 <span  class='necessariness'>*</span></th>
							<td colspan="3">
							<textarea id="chk_content" name="chk_content" rows="10"  class="textarea_w95" maxlength="15000" >${pkgCheckListMg.chk_content }</textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>	
		</div>
	</form:form>
	
</body>
</html>
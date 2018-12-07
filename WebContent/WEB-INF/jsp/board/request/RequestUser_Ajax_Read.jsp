<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty requestModel.ord ? '등록' : '수정'}"/>

<script type="text/javaScript" defer="defer">
function validation(){
	
	var request_name = document.getElementById("request_name");
	var request_phone = document.getElementById("request_phone");
	
	if(!isNullAndTrim(request_name, "검증 요청자를 입력해주세요.")){
		$("#request_name").focus();
		return false;
	}
	if(!isNullAndTrim(request_phone, "전화번호를 입력해주세요.")){
		$("#request_phone").focus();
		return false;
	}
	
	return true;
}
function fn_create_reqUser(){
	
	if(!validation())
		return;
	
	doSubmit4File("requestModel", "/board/request/RequestUser_Ajax_create.do", "fn_callback_reqUser_create");
}
function fn_update_reqUser(){
	if(!validation())
		return;
	
	doSubmit4File("requestModel", "/board/request/RequestUser_Ajax_update.do", "fn_callback_reqUser_update");
}
function fn_delete_reqUser(){
	doSubmit4File("requestModel", "/board/request/RequestUser_Ajax_delete.do", "fn_callback_reqUser_delete");
}
function fn_callback_reqUser_create(){
	fn_reqUser_readList();
}
function fn_callback_reqUser_update(){
	fn_reqUser_readList();
}
function fn_callback_reqUser_delete(){
	fn_reqUser_readList();
}
</script>
<table class="tbl_type1">
	<tr>
		<th>검증요청자</th>
		<td>
			<input type="text" name="request_name" id="request_name" value="${requestModel.request_name}" class="inp inp_w3" maxlength="10"/>
			<input type="hidden" name="ord" id="ord" value="${requestModel.ord}"/>
		</td>
		<th>전화번호</th>
		<td>
			<input type="text" name="request_phone" id="request_phone" value="${requestModel.request_phone}" class="inp inp_w3" onkeyup="javascript:fn_number_validator(this)" maxlength="11"/>
		</td>
	</tr>
</table>
<div class="btn_location">
	<c:choose>
		<c:when test="${registerFlag == '수정'}">
			<sec:authorize ifAnyGranted = "ROLE_BP">
				<span><a href="javascript:fn_update_reqUser()"><img src="/images/two_btn_modify.gif" alt="수정" /></a></span>
				<span><a href="javascript:fn_delete_reqUser()"><img src="/images/two_btn_delete.gif" alt="삭제" /></a></span>
			</sec:authorize>
		</c:when>
		<c:otherwise>
			<span><a href="javascript:fn_create_reqUser()"><img src="/images/two_btn_save.gif" alt="저장" /></a></span>	
		</c:otherwise>	
	</c:choose>
</div>

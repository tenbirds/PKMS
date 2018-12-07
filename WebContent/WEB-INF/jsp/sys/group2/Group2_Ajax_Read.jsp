<%--
/**
 * Ajax를 이용하여 표시되는 시스템 중분류 상세
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:set var="registerFlag" value="${empty SysModel.group2_seq ? '등록' : '수정'}" />

<script type="text/javaScript" defer="defer">
	$(document).ready(function($) {
		$("input[id=name]").focus();
	});

	// 중분류 생성
	function fn_create() {

		if (!isNullAndTrim_J($("#name"), "중분류명은 필수 입력사항 입니다."))
			return;

		doSubmit4File("SysModel",
				"<c:url value='/sys/group2/Group2_Ajax_Create.do'/>",
				"fn_callback_create_group2");
	}

	// 중분류 생성 callback
	function fn_callback_create_group2(data) {
		var group2_seq = $("input[id=param1]").val();
		$("#group2_seq").val(group2_seq);
		fn_readListGroup2();
	}

	// 중분류 수정
	function fn_update() {

		if (!isNullAndTrim_J($("#name"), "중분류명은 필수 입력사항 입니다."))
			return;

		doSubmit4File("SysModel",
				"<c:url value='/sys/group2/Group2_Ajax_Update.do'/>",
				"fn_callback_update_group2");
	}

	// 중분류 수정 callback
	function fn_callback_update_group2(data) {
		fn_readListGroup2();
	}

	// 중분류 삭제
	function fn_delete() {
		if (confirm("정말로 삭제 하시겠습니까?")) {
			doSubmit4File("SysModel",
					"<c:url value='/sys/group2/Group2_Ajax_Delete.do'/>",
					"fn_callback_delete_group2");
		}
	}

	// 중분류 삭제 callback
	function fn_callback_delete_group2(data) {
		$("#group2_seq").val("");
		fn_readListGroup1();
	}
	
	function fn_copy(){
	}
	
</script>



<!--기본정보 테이블 -->
<h3 class="stitle">기본정보</h3>
<table class="tbl_type11 w_100">
	<colgroup>
		<col width="10%"/>
		<col width="*"/>
	<colgroup>
	<tr>
		<th>중분류명<span class='necessariness'>*</span></th>
		<td><input type="text" id="name" name="name" value="${SysModel.name}" maxlength="15" class="new_inp new_inp_w3" /></td>
	</tr>
</table>
<br /><br />
<div id="reg_info">
<c:choose>
	<c:when test="${registerFlag == '수정'}">
		<!--등록정보 테이블 -->
		<div class="write_info2 fl_wrap">
			등록자(등록일)
			<span class="name2">
				<c:out value="${SysModel.reg_user}" /> (<c:out value="${SysModel.reg_date}" />)
			</span>
			수정자(수정일)
			<span class="name2">
				<c:out value="${SysModel.update_user}" /> (<c:out value="${SysModel.update_date}" />)
			</span>
		</div>
	</c:when>
</c:choose>
</div>







<%--
/**
 * Ajax를 이용하여 표시되는 업체 담당자 등록/수정 폼
 * 
 * @author : skywarker
 * @Date : 2012. 4. 19.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty BpModel.bp_user_id ? '등록' : '수정'}" />

<script type="text/javaScript" defer="defer">

	$(function($){
		$("#approval_user_name2").val($("#approval_user_name").val());
		doModalPopup("#open_sktuser_bp_popup2", 1, "click", 520, 600, "/usermg/user/SktUserBp_Popup_Read.do");
		});
	/* 아이디 중복 체크 */
	function fn_check_duplicate_bpuser_id() {
		doSubmit("BpModel",
				"<c:url value='/usermg/user/BpUser_Ajax_Duplicate_Read.do'/>",
				"fn_callback_duplicate_bpuser_id");
	}

	/* 아이디 중복 체크 Callback */
	function fn_callback_duplicate_bpuser_id(data) {

		var result = $("input[id=param1]").val();
		var bp_user_id_value = document.getElementById("bp_user_id").value;
		tempBpUserId = bp_user_id_value;

		if (result == "true") {
			var msg = "[" + bp_user_id_value + "]는 사용 가능한 아이디 입니다.";
			$("#check_bpuser_id_msg").html(
					"<font color=green>" + msg + "</font>");
			alert(msg);
			isBpUserIdValid = true;
			isBpUserIdDuplicate = false;
		} else {
			bpUserIdValidMsg = "[" + bp_user_id_value + "]는 중복되는 아이디입니다."
			$("#check_bpuser_id_msg").html(
					"<font color=red>" + bpUserIdValidMsg + "</font>");
			isBpUserIdValid = false;
			isBpUserIdDuplicate = true;
		}
	}

	/* 아이디 유효성 체크 */
	function fn_check_bpuser_id() {

		isBpUserIdValid = false;

		var bp_user_id_value = document.getElementById("bp_user_id").value;

		if (bp_user_id_value == "") {
			bpUserIdValidMsg = "담당자 아이디를 입력하세요!";
			$("#check_bpuser_id_msg").html(
					"<font color=red>" + bpUserIdValidMsg + "</font>");
			return;
		}

		if (bp_user_id_value.length < 4) {
			bpUserIdValidMsg = "담당자 아이디는 4자리 이상 이여야 합니다!";
			$("#check_bpuser_id_msg").html(
					"<font color=red>" + bpUserIdValidMsg + "</font>");
			return;
		}
		
		if (bp_user_id_value.match(/[^a-zA-Z0-9-_@.]/) != null) {
			bpUserIdValidMsg = "담당자 아이디는 영문/숫자 이여야 합니다.\n 특수문자는 -, _, @, . 만 사용가능!";
			$("#check_bpuser_id_msg").html(
					"<font color=red>" + bpUserIdValidMsg + "</font>");
			return;
		}

		if (tempBpUserId == bp_user_id_value) {
			if (isBpUserIdDuplicate) {
				bpUserIdValidMsg = "[" + bp_user_id_value + "]는 중복되는 아이디입니다."
				$("#check_bpuser_id_msg").html(
						"<font color=red>" + bpUserIdValidMsg + "</font>");
			} else {
				var msg = "[" + bp_user_id_value + "]는 사용 가능한 아이디입니다.";
				$("#check_bpuser_id_msg").html(
						"<font color=green>" + msg + "</font>");
				isBpUserIdValid = true;
			}

		} else {
			bpUserIdValidMsg = "담당자 아이디[" + bp_user_id_value + "]의 중복을 확인 하세요.";
			$("#check_bpuser_id_msg")
					.html(
							"<a href='javascript:fn_check_duplicate_bpuser_id();'><img src='/images/two_btn_overlap.gif' alt='중복확인'  align='absmiddle' style='vertical-align: middle;' /></a> <font color=orange>"
									+ bpUserIdValidMsg + "</font>");
		}
		return;
	}

	/* BP 사용자 등록,수정 폼 유효성 체크 */
	function validationBpUserForm(create) {
		var bp_user_name = document.getElementById("bp_user_name");
		var bp_user_phone1 = document.getElementById("bp_user_phone1");
		var bp_user_phone2 = document.getElementById("bp_user_phone2");
		var bp_user_phone3 = document.getElementById("bp_user_phone3");
		var bp_user_email = document.getElementById("bp_user_email");
		var bp_user_passwd1 = document.getElementById("bp_user_passwd1");
		var bp_user_passwd2 = document.getElementById("bp_user_passwd2");
		

		if (!isNullAndTrim(bp_user_name, "담당자 이름은 필수 입력사항 입니다."))
			return false;


		if (!isNull(bp_user_passwd1, "비밀번호는 필수 입력사항 입니다."))
			return false;
		if (!isNull(bp_user_passwd2, "비밀번호 확인은 필수 입력사항 입니다."))
			return false;

		if (bp_user_passwd1.value != bp_user_passwd2.value) {
			alert("비밀번호 확인이 틀렸습니다.\n다시입력해주십시오.");
			bp_user_passwd2.focus();
			return false;
		}
		
		//--------------------------------비밀번호 TQAMS 동일 적용------------------------------
		/**
		* 비밀번호 필수 항목 체크
		**/
		var id = document.getElementById("bp_user_id").value;
		var pass =document.getElementById("bp_user_passwd1").value;
		
		if(id.toLowerCase() == pass.toLowerCase()) {
			alert("ID와 PASSWORD는 같을 수 없습니다.");
			bp_user_passwd1.focus();
			return false;
		}

		var temp = "";
		var intCnt =0;
		
		for(var i = 0; i< pass.length; i++)
		{
			temp = pass.charAt(i);
			if(temp== pass.charAt(i+1) && temp == pass.charAt(i+2) && temp == pass.charAt(i+3))
			{
				intCnt = intCnt + 1;
			}
		}
		
		if(intCnt > 0 )
		{
			alert("비밀번호는 동일문자 연속 4회 이상 올수 없습니다.");
			return false;
	    }
		
		if(pass.length < 9)
		{
			alert("비밀번호는 대/소문자, 숫자, 특수문자를 혼용하여 9자 이상으로 설정 하셔야 합니다.");
			return false;
		}

		if(!pass.match(/^.*(?=.*\d)(?=.*[a-zA-Z])(?=.*[~,!,@,#,$,*,(,),=,+,_,.,|]).*$/))
		{
			alert("비밀번호는 대/소문자, 숫자, 특수문자를 혼용하여 9자 이상으로 설정 하셔야 합니다.");
			return false;
		}
		if(pass.match(/(0123)|(1234)|(2345)|(3456)|(4567)|(5678)|(6789)|(7890)/) || pass.match(/(3210)|(4321)|(5432)|(6543)|(7654)|(8765)|(9870)|(0987)/))
		{
			alert("비밀번호는 연속적인 숫자가 올 수 없습니다.");
			return false;
		}
		if(pass.search(id)>-1)
		{
			alert("ID가 포함된 비밀번호는 사용하실 수 없습니다.");
			return false;
		}
		
		//--------------------------------end------------------------------
		if (create) {
			var bp_user_id = document.getElementById("bp_user_id");
			
			if (!isNullAndTrim(bp_user_id, "담당자 아이디는 필수 입력사항 입니다."))
				return false;
			
			if (!isBpUserIdValid) {
				alert(bpUserIdValidMsg);
				return false;
			}
		} else {
			if (bp_user_passwd1.value != "" || bp_user_passwd2.value != "") {
				if (bp_user_passwd1.value != bp_user_passwd2.value) {
					alert("비밀번호 확인이 틀렸습니다.\n다시입력해주십시오.");
					bp_user_passwd2.focus();
					return false;
				}
			}
		}

		if (!isNullAndTrim(bp_user_phone1, "이동전화는 필수 입력사항 입니다."))
			return false;
		if (!isNullAndTrim(bp_user_phone2, "이동전화는 필수 입력사항 입니다."))
			return false;
		if (!isNullAndTrim(bp_user_phone3, "이동전화는 필수 입력사항 입니다."))
			return false;
		if (!isNumber(bp_user_phone1, "이동전화번호는 숫자 이여야 합니다."))
			return false;
		if (!isNumber(bp_user_phone2, "이동전화번호는 숫자 이여야 합니다."))
			return false;
		if (!isNumber(bp_user_phone3, "이동전화번호는 숫자 이여야 합니다."))
			return false;
		if (!isNullAndTrim(bp_user_email, "이메일은 필수 입력사항 입니다."))
			return false;

		if (!/^(.+)@(.+)$/.test(bp_user_email.value)) {
			alert("이메일 형식이 올바르지 않습니다.");
			bp_user_email.focus();
			return false;
		}

		document.getElementById("bp_user_pw").value = bp_user_passwd1.value;

		return true;
	}

	/* 등록 */
	function fn_create_user() {
		if (!validationBpUserForm(true)) {
			return;
		}

		$("#approval_state").val($("#approval_state_def").val());
		doSubmit4File("BpModel",
				"<c:url value='/usermg/user/BpUser_Ajax_Create.do'/>",
				"fn_callback_create_user");
	}

	/* 등록 Callback */
	function fn_callback_create_user(data) {
	}

	/* 수정 */
	function fn_update_user() {
		if (!validationBpUserForm(false)) {
			return;
		}

		//if (confirm("정말로 수정 하시겠습니까?")) {
		$("#approval_state").val("");
		doSubmit4File("BpModel",
				"<c:url value='/usermg/user/BpUser_Ajax_Update.do'/>",
				"fn_callback_update_user");
		//}
	}

	/* 수정 Callback */
	function fn_callback_update_user(data) {
// 		fn_user_readList();
	}

	/* 삭제 */
	function fn_delete_user() {
// 		if (!validationBpUserForm(false)) {
// 			return;
// 		}

// 		if (confirm("정말로 삭제 하시겠습니까?")) {
// 			doSubmit4File("BpModel",
// 					"<c:url value='/usermg/user/BpUser_Ajax_Delete.do'/>",
// 					"fn_callback_delete_user");
// 		}
	}

	/* 삭제 Callback */
	function fn_callback_delete_user(data) {
// 		fn_user_readList();
	}
	
</script>

<input type="hidden" id="approval_state_def" name="approval_state_def" value="0" />
<input type="hidden" id="bp_user_pw" name="bp_user_pw" value="" />

<table class="popup_sear_table1">
	
	<tr>
		<th>담당자 이름<span class='necessariness'>*</span></th>
		<td><input type="text" id="bp_user_name" name="bp_user_name" value="${BpModel.bp_user_name}" maxlength="20" class="inp inp_bp_w2" /></td>
		<th>담당자 아이디<span class='necessariness'>*</span></th>
		<td>
			<c:choose>
				<c:when test="${registerFlag == '수정'}">
					<c:out value="${BpModel.bp_user_id}" />
					<input type="hidden" id="bp_user_id" name="bp_user_id" value="${BpModel.bp_user_id}" />
				</c:when>
				<c:otherwise>
					<input type="text" id="bp_user_id" name="bp_user_id" value="${BpModel.bp_user_id}" maxlength="20" class="inp inp_bp_w2" onchange="javascript:fn_check_bpuser_id()" onkeyup="javascript:fn_check_bpuser_id()"
						onmouseup="javascript:fn_check_bpuser_id()" style="IME-MODE: disabled;" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th>승인 담당자<span class='necessariness'>*</span></th>
		<td colspan="2">
			<span class="fl_left"><input type="text" class="inp inp_bp_w2" id="approval_user_name2" readonly/> </span>
<!-- 			<span class="btn_line_blue2 ml10 mt03 fl_left"><a href="#" id="open_sktuser_bp_popup2">검색</a></span>  -->
			<span class="ml03 mt00 fl_left"><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer; vertical-align: middle;" id="open_sktuser_bp_popup2" align="absmiddle" style="vertical-align: middle;" /></span>
		</td>
		<td>
			<div id="check_bpuser_id_msg"></div>
		</td>
	</tr>
	<tr>
		<th>비밀번호<span class='necessariness'>*</span></th>
		<td><input type="password" id="bp_user_passwd1" name="bp_user_passwd1" maxlength="20" class="inp inp_bp_w2" /></td>

		<th>비밀번호 확인<span class='necessariness'>*</span></th>
		<td><input type="password" id="bp_user_passwd2" name="bp_user_passwd2" maxlength="20" class="inp inp_bp_w2" /></td>
	</tr>
	<tr>
		
		<th>이동전화<span class='necessariness'>*</span></th>
		<td>
			<input type="text" id="bp_user_phone1" name="bp_user_phone1" value="${BpModel.bp_user_phone1}" maxlength="4" class="inp inp_bp_w4"  onkeydown="fn_numbersonly(event);" style="ime-mode:disabled"/> 
			<input type="text" id="bp_user_phone2" name="bp_user_phone2" value="${BpModel.bp_user_phone2}" maxlength="4" class="inp inp_bp_w4"  onkeydown="fn_numbersonly(event);" style="ime-mode:disabled" />
			<input type="text" id="bp_user_phone3" name="bp_user_phone3" value="${BpModel.bp_user_phone3}" maxlength="4" class="inp inp_bp_w4"  onkeydown="fn_numbersonly(event);" style="ime-mode:disabled" /></td>

		<th>이메일<span class='necessariness'>*</span></th>
		<td><input type="text" id="bp_user_email" name="bp_user_email" value="${BpModel.bp_user_email}" maxlength="100" class="inp inp_bp_w2" /></td>
	</tr>
</table>
<table style="width: 100%;">
	<tr>
		<td align="center" style="padding: 5px 0px 0px 0px;"><c:choose>
				<c:when test="${registerFlag == '수정'}">
<!-- 					<span><a href="javascript:fn_update_user();"><img src="/images/two_btn_modify.gif" alt="수정" /></a></span> -->
<!-- 					<span><a href="javascript:fn_delete_user();"><img src="/images/two_btn_delete.gif" alt="삭제" /></a></span> -->
					<span class="pop_line_blue3"><a href="javascript:fn_update_user();">수정</a></span>
					<span class="pop_line_blue3"><a href="javascript:fn_delete_user();">삭제</a></span>
				</c:when>
				<c:otherwise>
<!-- 					<span><a href="javascript:fn_create_user();"><img src="/images/two_btn_save.gif" alt="저장" /></a></span> -->
					<span class="pop_line_blue3"><a href="javascript:fn_create_user();">저장</a></span>
				</c:otherwise>
			</c:choose></td>
	</tr>
</table>



<%-- 
<table class="tbl_type1" style="width: 100%;">
	<tr>
		<th class="td_bp_w1">담당자 이름<span class='necessariness'>*</span></th>
		<td class="td_bp_w2"><input type="text" id="bp_user_name" name="bp_user_name" value="${BpModel.bp_user_name}" maxlength="20" class="inp inp_bp_w2" /></td>
		<th class="td_bp_w1">담당자 아이디<span class='necessariness'>*</span></th>
		<td class="td_bp_w2"><c:choose>
				<c:when test="${registerFlag == '수정'}">
					<c:out value="${BpModel.bp_user_id}" />
					<input type="hidden" id="bp_user_id" name="bp_user_id" value="${BpModel.bp_user_id}" />
				</c:when>
				<c:otherwise>
					<input type="text" id="bp_user_id" name="bp_user_id" value="${BpModel.bp_user_id}" maxlength="20" class="inp inp_bp_w2" onchange="javascript:fn_check_bpuser_id()" onkeyup="javascript:fn_check_bpuser_id()"
						onmouseup="javascript:fn_check_bpuser_id()" style="IME-MODE: disabled;" />
				</c:otherwise>
			</c:choose></td>
		<th class="td_bp_w1">승인 담당자<span class='necessariness'>*</span></th>
		<td><input type="text" class="inp inp_bp_w2" id="approval_user_name2" readonly/></td>
		<td>
			<img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer; vertical-align: middle;" id="open_sktuser_bp_popup2" align="absmiddle" style="vertical-align: middle;" />
		</td>
		<td colspan="5">
			<div id="check_bpuser_id_msg"></div>
		</td>
	</tr>
	<tr>
		<th class="td_bp_w1">비밀번호<span class='necessariness'>*</span></th>
		<td class="td_bp_w2"><input type="password" id="bp_user_passwd1" name="bp_user_passwd1" maxlength="20" class="inp inp_bp_w2" /></td>

		<th class="td_bp_w1">비밀번호 확인<span class='necessariness'>*</span></th>
		<td class="td_bp_w2"><input type="password" id="bp_user_passwd2" name="bp_user_passwd2" maxlength="20" class="inp inp_bp_w2" /></td>

		<th class="td_bp_w1">이동전화<span class='necessariness'>*</span></th>
		<td class="td_bp_w2">
			<input type="text" id="bp_user_phone1" name="bp_user_phone1" value="${BpModel.bp_user_phone1}" maxlength="4" class="inp inp_bp_w4"  onkeydown="fn_numbersonly(event);" style="ime-mode:disabled"/> 
			<input type="text" id="bp_user_phone2" name="bp_user_phone2" value="${BpModel.bp_user_phone2}" maxlength="4" class="inp inp_bp_w4"  onkeydown="fn_numbersonly(event);" style="ime-mode:disabled" />
			<input type="text" id="bp_user_phone3" name="bp_user_phone3" value="${BpModel.bp_user_phone3}" maxlength="4" class="inp inp_bp_w4"  onkeydown="fn_numbersonly(event);" style="ime-mode:disabled" /></td>

		<th class="td_bp_w1">이메일<span class='necessariness'>*</span></th>
		<td class="td_bp_w2"><input type="text" id="bp_user_email" name="bp_user_email" value="${BpModel.bp_user_email}" maxlength="100" class="inp inp_bp_w2" /></td>
	</tr>
</table>
<table style="width: 100%;">
	<tr>
		<td align="right" style="padding: 5px 0px 0px 0px;"><c:choose>
				<c:when test="${registerFlag == '수정'}">
					<span><a href="javascript:fn_update_user();"><img src="/images/two_btn_modify.gif" alt="수정" /></a></span>
					<span><a href="javascript:fn_delete_user();"><img src="/images/two_btn_delete.gif" alt="삭제" /></a></span>
				</c:when>
				<c:otherwise>
					<span><a href="javascript:fn_create_user();"><img src="/images/two_btn_save.gif" alt="저장" /></a></span>
				</c:otherwise>
			</c:choose></td>
	</tr>
</table>

 --%>


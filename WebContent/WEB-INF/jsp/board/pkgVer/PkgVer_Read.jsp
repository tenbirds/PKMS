<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="registerFlag" value="${empty PkgVerModel.system_seq ? '등록' : '수정'}" />
<html>
<head>

<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javaScript"  defer="defer">
 $(document).ready(function($) {
	 init_system_popup_pkgVer();
	
	 $('textarea[maxlength]').keydown(function(){
        var max = parseInt($(this).attr('maxlength'));
        var str = $(this).val();
		if(str.length > max) {
		    $(this).val(str.substr(0, max-1));
			alert("최대 [" + max + " 자]까지 입력 가능합니다.");
		}
	}); 
});
 
function fn_readList() {
	var form = document.getElementById('PkgVerModel');
	form.action = "<c:url value='/board/pkgVer/PkgVer_ReadList.do'/>";
	form.submit();
}

function fn_create(){
	
	if(!fn_validataion())
		return;
	
	doSubmit("PkgVerModel", "/board/pkgVer/PkgVer_Create.do", "fn_callback_create", "등록 되었습니다.");
}

function fn_validataion(){
	var pkg_ver = document.getElementById("pkg_ver");

	if(!isNullAndTrim(pkg_ver, "PKG 버전을 입력해주세요.")){
		$("#pkg_ver").focus();
		return false;
	}
			
	if($("#system_seq").val()==""){
		alert("시스템을 선택해주세요.");
		$("#system_seq").focus();	
		return false;
	}
		
	if($("#content").val()==""){
		alert("내용을 입력해주세요.");
		$("#content").focus();	
		return false;
	}
		 
	if($("#content").val().length > 1000){
		alert("주요내역은 1000자 까지 입력해주세요.");
		return false;
	}		
	return true;
}

function fn_callback_create(){
	fn_readList();
}

function fn_update(){
	if(!fn_validataion())
		return;
	var form = document.getElementById('PkgVerModel');

	if (confirm("저장 하시겠습니까?")) {
		doSubmit("PkgVerModel", "/board/pkgVer/PkgVer_Update.do", "fn_callback_update", "저장 되었습니다.");
	}
}

function fn_callback_update(){
	fn_readList();
}

function fn_read(){
	var form = document.getElementById('PkgVerModel');
	
	form.action = "<c:url value='/board/pkgVer/PkgVer_Read.do'/>";
	form.submit();
}

function fn_delete(){
	if (confirm("삭제하시겠습니까?")) {
		doSubmit("PkgVerModel", "/board/pkgVer/PkgVer_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
	}
}

function fn_callback_delete(){
	fn_readList();
}

//시스템 선택 결과 처리
function fn_system_popup_callback(system_key, system_name){
	if(system_key==""){
		return;
	}
	
	$("#system_seq").val(system_key);
// 	$("#system_name").val(system_name);
	
	var group_nm = system_name.split(">");
	var group_nm2 = system_name.split(">");
	var group_name = group_nm[0] + ">" + group_nm[1];
	
	$("#group_name").val(group_nm2[0]);
	$("#system_name").val(group_nm[2]);
	$("#bp_name").val("");
	$("#bp_phone").val("");
	$("#skt_phone").val("");
	
	var form = document.getElementById('PkgVerModel');
	doSubmit("PkgVerModel", "/board/pkgVer/PkgVer_Ajax_User.do", "fn_callback_users");
}

function fn_callback_users(){
	$("#sys_skt_user").val($("#param1").val());
	$("#sys_bp_user").val($("#param2").val());
	$("#bp_name").val($("#param3").val());
	$("#bp_phone").val($("#param4").val());
	$("#skt_phone").val($("#param5").val());
}

function user_search(){
	doSubmit("PkgVerModel", "/board/pkgVer/PkgVer_Ajax_User.do", "fn_callback_users");
}

function system_input_alert(){
	alert("시스템 입력시 자동으로 입력 됩니다.");
}

</script>
</head>
<body>
 <form:form commandName="PkgVerModel" method="post" onsubmit="return false;">
	<form:hidden path="system_seq"/> 
	
	<!-- 페이징 -->
	<form:hidden path="pageIndex"/>

	<!-- 검색조건 유지 -->
	<form:hidden path="search_reg_date_start"/>
	<form:hidden path="search_reg_date_end"/>
	<form:hidden path="search_system_seq"/>
	<form:hidden path="search_system_name"/>
	
	<div class="btn_location">
		 <c:choose>
			<c:when test="${registerFlag == '등록'}">
				<span><a href="javascript:fn_create()"><img src="/images/btn_save.gif" alt="저장" /></a></span>
			</c:when>
			<c:otherwise>
				<span><a href="javascript:fn_delete()"><img src="/images/btn_delete.gif" alt="삭제" /></a></span>
				<span><a href="javascript:fn_update()"><img src="/images/btn_save.gif" alt="저장" /></a></span>
			</c:otherwise>
		</c:choose> 
		<span><a href="javascript:fn_readList()"><img src="/images/btn_list.gif" alt="목록" /></a></span>
	</div>
	
	<div class="con_Div3">
		<!--기본정보 -->
		<fieldset>
			<legend>기본정보</legend>
			<table class="tbl_type0" style="width: 100%" border="0">
				<tr>
					<th>중분류 <span  class='necessariness'>*</span></th>
					<td>
						<c:choose>
							<c:when test="${registerFlag == '등록'}">
								<form:input path="group_name" readonly="true" cssStyle="width: 250px;" onclick="system_input_alert()" /> 
							</c:when>
							<c:otherwise>
								<form:input path="group_name" readonly="true" cssStyle="width: 250px;" /> 
							</c:otherwise>
						</c:choose>
					</td>
					
					<th>시스템 <span  class='necessariness'>*</span></th>
					<c:choose>
						<c:when test="${registerFlag == '등록'}">
							<td>
								<form:input path="system_name" readonly="true" cssStyle="width: 170px; " onclick="system_input_alert()"/>					
								<span>
									<img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" />
								</span>
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<form:input path="system_name" readonly="true" cssStyle="width: 250px; " />
							</td>
			    			<th>담당자 검색</th>
							<td>
								<span>
									<img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" onclick = "user_search()"/>
								</span>
							</td>
						</c:otherwise>
					</c:choose>	
				</tr>
				
				<tr>
					<th>SKT 담당자 <span  class='necessariness'>*</span></th>
					<td style="width: 220px;">
						<form:input path="sys_skt_user" cssStyle="width: 250px;" maxlength="200" />
					</td>
					
					<th>SKT 연락처 <span  class='necessariness'>*</span></th>
					<td>
						<form:input path="skt_phone"  cssStyle="width: 250px;" maxlength="200" /> 
					</td>
				</tr>
				
				<tr>
					<th>BP 사 <span  class='necessariness'>*</span></th>
					<td style="width: 280px;">
						<form:input path="bp_name"  cssStyle="width: 250px;" maxlength="200"/>
					</td>
					
					<th>BP 담당자 <span  class='necessariness'>*</span></th>
					<td>
						<form:input path="sys_bp_user"  cssStyle="width: 250px;" maxlength="200"/> 
					</td>
					
					<th>BP 연락처 <span  class='necessariness'>*</span></th>
					<td>
						<form:input path="bp_phone"  cssStyle="width: 250px;" maxlength="200"/> 
					</td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset class="detail_fieldset">
			<legend>상세정보</legend>
			<table class="tbl_type0" style="width: 100%" border="0">
				<tr>
					<th>PKG 버전 <span  class='necessariness'>*</span></th>
					<td colspan="3"> <form:input path="pkg_ver" size="60" cssStyle="width:1050px;" maxlength="100"/> </td>
				</tr>
				
				<tr>
					<th>주요내역 <span  class='necessariness'>*</span></th>
					<td colspan="3"> <form:textarea path="content" rows="5" cssStyle="width:1050px;"  maxlength="1000"/> </td>
				</tr>
				
				<tr>
					<th>비고</th>
					<td colspan="3">
						<form:input path="ver_note" cssStyle="width:1050px;"/>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
	</form:form>
</body>
</html>
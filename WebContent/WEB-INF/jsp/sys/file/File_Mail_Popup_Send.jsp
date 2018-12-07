<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="registerFlag" value="${empty SysModel.system_seq ? '등록' : '수정'}" />

<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/supertable/supertable.css" />
<link rel="stylesheet" type="text/css" href="/css/dynatree/ui.dynatree.css">
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link rel="stylesheet" type="text/css" href="/css/system.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery.scrollbar/jquery.mCustomScrollbar.css"/>

<link type="text/css" rel="stylesheet" href="/css/main.css" />

<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.dynatree.js"></script>

<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(function($) {
		
	});
	
	function fn_send(){
		var title = $("#title");
		var content = $("#content");
		
		if(title.val() == ""){
			alert("제목을 입력하세요.");
			title.focus();
			return;
		}
		if(content.val() == ""){
			alert("내용을 입력하세요.");
			content.focus();
			return;
		}
		
		doSubmit("FileModel",
				"/sys/file/Mail_Ajax_Send.do",
				"fn_callback_send", "메일이 전송되었습니다.");
		
// 		parent.fn_callback_select_user_popup(sel_ids, sel_names, select_user);
	}
	
	function fn_callback_send(){
		window.close();
	}
	
</script>
</head>
<body style="background-color: transparent">
<form:form commandName="FileModel" method="post" onsubmit="return false;">


<div style="width: 100%; height: 100%;" id="pop_wrap">
	<h4 class="ly_header">메일 보내기</h4>

	<div style="margin-top: 15px;">
		<!--버튼위치 -->
		<div class="btn_location" style="margin-right: 15px;">		
			<span>
				<a href="javascript:fn_send();">
					<img src="/images/btn_mail_send.gif" alt="신규" />
				</a>
			</span>
		</div>
		<table style="height: 430px; width: 98%; margin-left: 10px;" class="tbl_type1">
			<colgroup>
				<col width="10%;" >
				<col width="90%;" >
			</colgroup>
			<tr height="70px;">
				<th>
					시스템
				</th>
				<td>
					<div style="overflow-y: auto; width: 835px; height: 64px; overflow-x: hidden;">
						<c:forEach var="nameList" items="${nameList}" varStatus="status">
							<input type="hidden" name="system_seqs" value="${nameList.system_seq}">
							<c:choose>
								<c:when test="${status.first}">
									${nameList.system_name}
								</c:when>
								<c:otherwise>
									,${nameList.system_name}
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</div>
				</td>
			<tr height="30px;">
				<th>
					받는 대상
				</th>
				<td>
					<c:forEach var="gubunList" items="${gubunList}" varStatus="status">
						<input type="checkbox" name="charge_gubuns" value="${gubunList.charge_gubun}" checked="checked">
						${gubunList.charge_gubun_name}
					</c:forEach>
				</td>
			</tr>
			<tr height="30px;">
				<th>
					제목
				</th>
				<td>
					<input type="text" style="width: 95%;" id="title" name="title" maxlength="100">
				</td>
			</tr>
			<tr height="300px;">
				<th>
					내용
				</th>
				<td>
					<textarea style="width: 95%;" rows="18" id="content" name="content" maxlength="15000" ></textarea>
				</td>
			</tr>
		</table>
	</div>
</div>
</form:form>
</body>
</html>



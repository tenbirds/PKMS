<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty QnaModel.qna_seq ? '등록' : '수정'}" />
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javaScript"  defer="defer">

function fn_read(id) {//수정 페이지
	var form = document.getElementById('PkgCheckListManagerModel');
	form.chk_seq.value = id;
	form.pageIndex.value = 0;
	form.action = "<c:url value='/verifyTem_mg/pkg21_menuCheck_admin_view.do'/>";
	form.submit();
}

function fn_readList() {//목록
	var form  = document.getElementById('PkgCheckListManagerModel');
	form.pageIndex.value = 1;
	form.action = "/verifyTem_mg/pkg21_menuCheck_admin.do";
	form.submit();
}

</script>
</head>
<body>
	<form:form commandName="PkgCheckListManagerModel" method="post" enctype="multipart/form-data" onsubmit="return false;">
	
		<input type="hidden" id="chk_seq" name="chk_seq" value="">
		<input type="hidden" id="reg_user" name="reg_user" value="">
		<input id="pageIndex" name="pageIndex" type="hidden" value="">
		
		<sec:authentication property="principal.username" var="sessionId"/>
		
		<input type="hidden" id="Session_user_id" name="Session_user_id" value="${sessionId}">
		
		
			
		<div class="con_Div32">
			<div class="con_area">
				<div class="con_detail_long">
					<div class="con_top_btn fl_wrap">
						<c:choose>
							<c:when test="${sessionId eq pkgCheckListMg.reg_user}">
								<span><a href="javascript:fn_read('<c:out value="${pkgCheckListMg.chk_seq}"/>')">수정</a></span>
							</c:when>
							<c:otherwise>
								<sec:authorize ifAnyGranted = "ROLE_ADMIN">
									<span><a href="javascript:fn_read( '<c:out value="${pkgCheckListMg.chk_seq}"/>')">수정</a></span>
								</sec:authorize>
							</c:otherwise>
						</c:choose>
						<span><a href="javascript:fn_readList()">목록</a></span>
					</div>
					<table class="tbl_type11">
						<colgroup>
							<col width="10%">
							<col width="40%">
							<col width="10%">
							<col width="40%">
						</colgroup>
						<tr>
							<th>제목</th>
							<td colspan="3"> ${pkgCheckListMg.title }</td>
						</tr>
						<tr>
							<th>사용분류</th>
							<td class="inp_w3">${pkgCheckListMg.chk_type_name}</td>
							<th>상태</th>
							<td>
									<c:choose>
										<c:when test="${pkgCheckListMg.status eq 'Y'}">
											사용
										</c:when>
										<c:otherwise>
											미사용
										</c:otherwise>
									</c:choose>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="mg20 line_height25">
									${pkgCheckListMg.chk_content }
								</div>
							</td>
						</tr>
						<tr>
							<th>등록자</th>
							<td class="inp_w3">${pkgCheckListMg.reg_user_name}</td>
							<th>등록 시간</th>
							<td>${pkgCheckListMg.reg_date}</td>
						</tr>
						<tr>
							<th>수정자</th>
							<td class="inp_w3">${pkgCheckListMg.update_user_name}</td>
							<th>수정 시간</th>
							<td>${pkgCheckListMg.update_date}</td>
						</tr>
					</table>

					<!-- QNA폼 끝 -->
					

				</div>
			</div>
		</div>
	</form:form>
	
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<meta name="decorator" content="mobile" />
	<meta name="viewport" content="width=480, initial-scale=-1, user-scalable=yes,target-densitydpi=device-dpi" />
	<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
	<script type="text/javascript" src="/js/common.js"></script>
	<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
	<script type="text/javaScript" defer="defer"> 
	$(document).ready(
			function($) {
				// Calendar Init
				doCalendar("startD");
				doCalendar("endD");
			});
		/* 등록/상세/수정 화면 */
		function fn_read(pkg_seq, status, charge_gubun, dev_yn) {
			$("#pkg_seq").val(pkg_seq);
			if(charge_gubun == "DA" && dev_yn == "D"){
				$("#selected_status").val("24");
			}else if(status == "23"){
				$("#selected_status").val("24");
			} else if(status == "2") {
				$("#selected_status").val("26");
			} else if(status == "5") {
				$("#selected_status").val("6");				
			}
			
			var form = document.getElementById("PkgModel");
			form.action = "<c:url value='/pkgmg/mobile/Mobile_Read.do'/>";
			form.submit();
		}
		
		function fn_21read(pkg_seq, status_chk, charge_gubun) {
			$("#pkg_seq").val(pkg_seq);
			$("#status_chk").val(status_chk);
			$("#charge_gubun").val(charge_gubun);
			
			var form = document.getElementById("PkgModel");
			form.action = "/pkgmg/mobile/Mobile_21Read.do";
			form.submit();
		}
		
		function daysearch(){
			var startD = $("#startD").val();
			var endD = $("#endD").val();
			location.href = "/Main.do?startD="+startD+"&endD="+endD;
		}
	</script>
</head>
<body>
	<!--탑1 -->
	<div class="mob_top1 fl_wrap">
		<h1>
			PKMS <span>Package Management system</span>
		</h1>
		<!--로그아웃 -->
		<span class="mob_logout"><a href="/Login_Delete.do"><img src="/images/mob_btn_logout.png" alt="로그아웃" /></a></span>
	</div>

	<!--탑2 -->
	<div class="mob_top2">
		<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
		<div class="mob_title1 fl_wrap">
			<!--타이틀 -->
			<h2><strong>PKMS</strong> 승인요청</h2>
		</div>
		<!--텍스트 -->
		<div class="mob_top2_tex1"><strong>${mdl.session_user_name}</strong>님의 <u>승인관련 패키지 검증/과정</u> 목록입니다</div>
		

		</sec:authorize>
	</div>

	<form:form commandName="PkgModel" method="post" onsubmit="return false;" action="/Main.do">

	<!--컨텐츠 -->
	<div class="mob_contents">
		<form:hidden path="pkg_seq" />
		<form:hidden path="selected_status" />
		<form:hidden path="charge_gubun" />
		<form:hidden path="status_chk" />
		
		<div class="search">
			<span class="fl_left" style="display:block;width:40%;"><input type="text" id="startD" name="startD" class="new_inp fl_left" style="width:78%;" value="${mdl.startD}"/></span>
			<span class="fl_left" style="display:block;width:15px;line-height:33px;text-align:ceter;"> ~ </span>
			<span class="fl_left" style="display:block;width:40%;"><input type="text" id="endD" name="endD" class="new_inp fl_left" style="width:78%;" value="${mdl.endD}"/></span>
			<div class="btn_sear">
				<a href="javascript:daysearch()"></a>
			</div>
		</div>
		<table class="tbl_type12" cellspacing="0" style="width:100%">
			<!--셀넓이조절가능 -->
			<colgroup>
				<col width="20%;">
				<col width="*;">
				<col width="20%;">
				<col width="20%;">
			</colgroup>
			<thead>
			<tr>
				<th scope="col">상태</th>
				<th scope="col">제목</th>
				<th scope="col">요청일시</th>
				<th scope="col">시스템</th>
			</tr>
			</thead>
			<tbody>
			<c:if test="${fn:length(PkgModelList) == 0}">
				<tr>
					<td colspan="3" height="30" align="center">등록된 항목이 없습니다.</td>
				</tr>
			</c:if>

			<c:forEach var="result" items="${PkgModelList}" varStatus="status">
				<c:choose>
					<c:when test="${result.branch_type == 'PKMS' }">
						<c:choose>
							<c:when test="${result.status_chk == 'R' }">
								<tr style="background-color:#dcdcdc;"> 
							</c:when>
							<c:otherwise>
								<tr>
							</c:otherwise>
						</c:choose>
							<td style="text-align: center;">
								<font color='red'><b>${result.status_name}</b></font>
							</td>
							<td align="left" style="padding-left:10px;">
								<a href="javascript:fn_read('${result.pkg_seq}', '${result.status}', '${result.charge_gubun}', '${result.dev_yn}' )">
									<c:out value="${fn:substring(result.title, 0, 21)}" />
									<c:out value="${fn:length(result.title) > 21 ? '..' : ''}" />
								</a>
							</td>
							<td>${result.reg_date }</td>
							<td>${result.group_depth}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${result.status_chk == 'R' }">
								<tr style="background-color:#dcdcdc;"> 
							</c:when>
							<c:otherwise>
								<tr>
							</c:otherwise>
						</c:choose>
							<td style="text-align: center;">
								<font color='red'>
								<b>
									${result.status_name}
									<c:if test="${result.status_chk == 'R' }">
										<c:choose>
											<c:when test="${result.charge_gubun == 'DA'}">
												(DVT 계획)
											</c:when>
											<c:when test="${result.charge_gubun == 'DR'}">
												(DVT 결과)
											</c:when>
											<c:when test="${result.charge_gubun == 'AU'}">
												(CVT 계획)
											</c:when>
											<c:when test="${result.charge_gubun == 'AR'}">
												(CVT 결과)
											</c:when>
											<c:when test="${result.charge_gubun == 'AS'}">
												(초도)
											</c:when>
											<c:when test="${result.charge_gubun == 'CA'}">
												(과금검증)
											</c:when>
											<c:when test="${result.charge_gubun == 'VA'}">
												(용량검증)
											</c:when>
											<c:otherwise>
												(확대)
											</c:otherwise>
										</c:choose>
									</c:if>
								</b>
								</font>
							</td>
							<td align="left" style="padding-left:10px;">
								<a href="javascript:fn_21read('${result.pkg_seq}', '${result.status_chk}', '${result.charge_gubun}')">
									<c:out value="${fn:substring(result.title, 0, 21)}" />
									<c:out value="${fn:length(result.title) > 21 ? '..' : ''}" />
								</a>
							</td>
							<td>${result.reg_date }</td>
							<td>${result.group_depth}</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
					
			</tbody>
		</table>
	</div>

	<!--버튼위치 -->
	<div class="mob_top2">
	</div>
	
	</form:form>
</body>
</html>

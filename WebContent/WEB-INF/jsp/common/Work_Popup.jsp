<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PKMS (패키지 관리 시스템)</title>

<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>

<script type="text/javaScript">
	function work_pkg_read(gubun, seq){
		$("#work_limit").val(0);
		fn_pkg_read(gubun, seq, '');		
	}
	
	function checkNumber(before) {
		var objEv = event.srcElement;
		var numPattern = /([^0-9])/;
		var gubun = objEv.value.match(numPattern);

		if (gubun != null) {
			alert("숫자만 입력하세요");
			objEv.value = before;
			objEv.focus();
			return;
		}
		
		if(objEv.value == ""){
			alert("작업건수는 필수 입력입니다.");

			objEv.value = before;
		}
	}
	
	function limit_save(no, code) {
		var work_limit = $("#work_limit_"+no).val(); 
		
		$("#work_limit").val(work_limit);
		$("#team_code").val(code);

		doSubmit("PkgModel", "/WorkLimitSave.do",
		"fn_callback_limit_save");
	}
	
	function fn_callback_limit_save(data) {
		var result = $("#param1").val(); 
		alert(result);
	}
	
	// 윈도우 창을 닫을 때 로그아웃 처리
	function openPage(url_gubun){
		if(url_gubun != 'Main'){
			var up_count = 0;
			var count = $("#limit_count").val();
			var limit = $("#limit");
			for(var i = 0; i < count; i++) {
				if(limit.eq(i).val() > 0) {
					up_count = up_count + 1;
				}
			}
			if(up_count > 0){
				alert("초과된 작업건수가 " + up_count + "건 있습니다. 확인하시고 진행바랍니다.");
			}
		}
	}
</script>

</head>

<body onload="openPage('${url_gubun}')">
	<form:form commandName="PkgModel" method="post" enctype="multipart/form-data">
		<input type="hidden" name="retUrl" id="retUrl"/>
		<input type="hidden" name="pkg_seq" id="pkg_seq"/>
		<input type="hidden" name="work_limit" id="work_limit"/>
		<input type="hidden" name="team_code" id="team_code"/>
		<br/>
		<div align = "center">
			<table class="tbl_type" style="width: 80%;">
				<caption>금일 작업건수</caption>
				<thead>
					<tr>
						<th>
							팀명
						</th>
						<th>
							작업수
						</th>
						<th>
							PKG 제목
						</th>
						<th>
							담당자
						</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty workCntList}">
							<c:forEach var="workCntList" items="${workCntList}" varStatus="status">
								<tr>
									<td><c:out value="${workCntList.team_name}" /></td>
									<td><c:out value="${workCntList.team_count}" /></td>
									<td style="text-align: left;">
										<c:forEach var="workPkgList" items="${workPkgList}" varStatus="no">
											<c:if test="${workPkgList.team_code eq workCntList.team_code}">
												<a href="javascript:work_pkg_read('read', '<c:out value="${workPkgList.pkg_seq}"/>')">
													<p align="left">
														<c:out value="${fn:substring(workPkgList.title, 0, 30)}"/>
														<c:out value="${fn:length(workPkgList.title) > 30 ? '...' : ''}"/>
													</p>
												</a>
											</c:if>
										</c:forEach>
									</td>
									<td>
										<c:forEach var="workPkgList" items="${workPkgList}" varStatus="no">
											<c:if test="${workPkgList.team_code eq workCntList.team_code}">
												<p>
													<c:out value="${workPkgList.system_user_name}" />
												</p>
											</c:if>
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4">금일 등록된 일정이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<br/>
			<table class="tbl_type" style="width: 80%;">
				<caption>금일 작업건수 초과 정보</caption>
				<thead>
					<tr>
						<th>
							팀명
						</th>
						<th>
							작업건수
						</th>
						<th>
							작업제한 건수
						</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty workCntList}">
							<c:forEach var="workCntList" items="${workCntList}" varStatus="status">
								<c:forEach var="workLimitList" items="${workLimitList}" varStatus="no">
									<c:if test="${workCntList.team_code eq workLimitList.team_code}">
										<c:choose>
											<c:when test="${workCntList.team_count > workLimitList.work_limit}">
												<tr>
													<td>
														<span style="color: red;">
															<c:out value="${workCntList.team_name}" />
														</span>
													</td>
													<td>
														<span style="color: red;">
															<c:out value="${workCntList.team_count}" />
															<input type="hidden" id="limit" value="1"/>
														</span>
													</td>
													<td><c:out value="${workLimitList.work_limit}" /></td>
												</tr>
											</c:when>
											<c:otherwise>
												<tr>
													<td><c:out value="${workCntList.team_name}" /></td>
													<td colspan="2">
														<input type="hidden" id="limit" value="0"/>
														작업건수 제한이 초과 되지 않았습니다.
													</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${status.last}">
										<input type="hidden" value="${status.count}" id="limit_count"/>
									</c:if>
								</c:forEach>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="3">금일 등록된 일정이 없습니다.</td>
							</tr>
						</c:otherwise>
						</c:choose>
				</tbody>
			</table>
			<c:if test="${granted eq 'ADMIN'}">
				<br/>
				<table class="tbl_type" style="width: 80%;">
					<caption>1일 작업건수 제한관리</caption>
					<tr>
						<th>
							팀명
						</th>
						<th>
							작업건수(max)
						</th>
						<th>
						</th>
					</tr>
					<c:forEach var="workLimitList" items="${workLimitList}" varStatus="status">
						<tr>
							<td>
								<c:out value="${workLimitList.team_name}" />
							</td>
							<td>
								<input type="text" name="work_limit_${status.count}" id="work_limit_${status.count}" value="${workLimitList.work_limit}" onchange="javascript:checkNumber(${workLimitList.work_limit})" style="width: 40px; text-align: right;"/>
							</td>
							<td>
								<a href="javascript:limit_save(${status.count}, '${workLimitList.team_code}');">
									<img src="/images/btn_save.gif" alt="저장" style="cursor: pointer;" />
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
	</form:form>
</body>
</html>

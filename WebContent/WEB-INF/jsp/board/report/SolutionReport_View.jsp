<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javaScript"  defer="defer">

$(document).ready(function($) {
	doModalPopup("#mail_popup", 1, "click", 700, 400, "/board/report/Solution_Report_Popup_MailList.do?system_seq=${SolutionReportModel.system_seq}");
	$("#content").val("");
});

/* 수정 화면 */
function fn_read(gubun) {
	var form = document.getElementById('SolutionReportModel');

	if (gubun == "read") {
		form.retUrl.value = "/board/report/SolutionReport_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/board/report/SolutionReport_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}	
	form.action = "<c:url value='/board/report/Solution_Report_Read.do'/>";
	form.submit();
}
function fn_delete(id){
	var form = document.getElementById('SolutionReportModel');
	form.solution_report_comment_seq.value = id;
	
	if (confirm("삭제하시겠습니까?")) {
		doSubmit4File("SolutionReportModel", "/board/report/Solution_Report_Comment_Delete.do", "fn_callback_create", "삭제 되었습니다.");
	}
}
function fn_callback_create(data) {
	$("#content").val("");
	fn_read('view');
}
function fn_callback_delete(data) {
	fn_readList();
}
function fn_readList() {
	var form  = document.getElementById('SolutionReportModel');

	form.action = "/board/report/Solution_Report_ReadList.do";
	form.submit();
}
function fn_create() {
	
	var content = document.getElementById("content");
	
	if(!isNullAndTrim(content, "내용을 입력해주세요.")){
		$("#content").focus();
		return;
	}
	
	doSubmit4File("SolutionReportModel", "/board/report/Solution_Report_Comment_Create.do", "fn_callback_create");
}
function fn_yes(){
	var au_comment = document.getElementById("au_comment");
	
	if(!isNullAndTrim(au_comment, "승인 comment를 입력해주세요.")){
		$("#au_comment").focus();
		return;
	}
	
	doSubmit4File("SolutionReportModel", "/board/report/Solution_Report_User_Yes.do", "fn_callback_create", "승인 되었습니다.");
}

function fn_confirm(){
	var sosok_comment = document.getElementById("sosok_comment");
	
	if(!isNullAndTrim(sosok_comment, "확인 comment를 입력해주세요.")){
		$("#sosok_comment").focus();
		return;
	}
	
	doSubmit4File("SolutionReportModel", "/board/report/Solution_Report_Confirm_Yes.do", "fn_callback_create", "확인 되었습니다.");
}
function fn_complete(){
	if (confirm("완료하시겠습니까?")) {
		doSubmit4File("SolutionReportModel", "/board/qna/Solution_Report_Ajax_Complete.do", "fn_callback_complete", "완료 되었습니다.");
	}
}
function fn_callback_complete(data) {
	fn_readList();
}

function fn_callback_mail_popup(mailList){
	$("#check_mails").val(mailList);
	$("#mail_popup").closeDOMWindow();
}
function system_m(group1_seq, group2_seq, group3_seq, system_seq) {
	$("#group1_seq").val(group1_seq);
	$("#group2_seq").val(group2_seq);
	$("#group3_seq").val(group3_seq);
	$("#system_seq").val(system_seq);
	
	var form = document.getElementById("SolutionReportModel");
	form.action = "<c:url value='/sys/group1/Group1_ReadList.do'/>";
	form.submit();
}
</script>
</head>
<body>
	<!-- 폼 시작 -->
	<form:form commandName="SolutionReportModel" method="post"  enctype="multipart/form-data" onsubmit="return false;">
	
		<form:hidden path="solution_report_seq"/>
		<form:hidden path="solution_report_comment_seq"/>
		
		<!-- return URL -->
		<form:hidden path="retUrl" />
		
		<!-- 첨부파일 -->
		<form:hidden path="master_file_id" />
		
		<!-- 검색조건 유지 -->
		<form:hidden path="search_system_seq"/>
		<form:hidden path="search_system_name"/>
		<form:hidden path="search_date_start"/>
		<form:hidden path="search_date_end"/>
		<form:hidden path="search_reg_name"/>
		<form:hidden path="search_reg_sosok"/>
		<form:hidden path="search_title"/>
		
		<!-- 승인자 ID -->
		<form:hidden path="user_id"/>
		
		<!-- 시스템 링크 -->
		<form:hidden path="group1_seq" />
		<form:hidden path="group2_seq" />
		<form:hidden path="group3_seq" />
		<form:hidden path="system_seq" />
		
		<form:hidden path="deleteList"/>
		
		<sec:authentication property="principal.username" var="sessionId"/>
		
		
		<div class="con_Div2 con_area mt20">
			<div class="con_detail">
				<!--버튼위치 -->
				<div class="con_top_btn">
					<c:if test="${SolutionReportModel.result_yn == 'N'}">
						<c:if test="${SolutionReportModel.vu_yn == 'Y'}">
							<span>
								<a href="javascript:fn_complete()">완료</a> 
							</span>
						</c:if>
						<c:if test="${SolutionReportModel.reg_user == sessionId}">
							<span>
								<a href="javascript:fn_read('read', '<c:out value="${SolutionReportModel.solution_report_seq}"/>')">
									수정
								</a>
							</span>
						</c:if>
					</c:if>
					<span>
						<a href="javascript:fn_readList()">목록</a>
					</span>
				</div>
		
				<!--제목 -->
				<h3 class="stitle">제목</h3>
		
				<table class="tbl_type11">
					<colgroup>
						<col width="8%">
						<col width="15%">
						<col width="10%">
						<col width="17%">
						<col width="8%">
						<col width="15%">
						<col width="10%">
						<col width="17%">
					</colgroup>
					<tr>
						<th>제목</th>
						<td colspan="7">
							${SolutionReportModel.title}
							<form:hidden path="title"/>
						</td>
					</tr>
					<tr>
						<th>
							등록자
						</th>
						<td>
							${SolutionReportModel.reg_name}
						</td>
						<th>
							등록일시
						</th>
						<td>
							${SolutionReportModel.reg_date}
						</td>
						<th>
							수정자
						</th>
						<td>
							${SolutionReportModel.update_name}
						</td>
						<th>
							수정일시	
						</th>
						<td>
							${SolutionReportModel.update_date}
						</td>
					</tr>
				</table>
				
				<h3 class="stitle">구분</h3>
					
				<table class="tbl_type11">
					<colgroup>
						<col width="8%">
						<col width="30%">
						<col width="10%">
						<col width="25%">
						<col width="10%">
						<col width="17%">
					</colgroup>
					<tr>
						<th>
							시스템
						</th>
						<td>
							<a href="javascript:system_m(${SolutionReportModel.group1_seq }, ${SolutionReportModel.group2_seq }, ${SolutionReportModel.group3_seq }, ${SolutionReportModel.system_seq } );">
								${SolutionReportModel.system_name}
							</a>
							<form:hidden path="system_name"/>
						</td>
						<th>
							내용분류
						</th>
						<td>
							<c:choose>
								<c:when test="${SolutionReportModel.content_gubun == '이슈사항'}">
									이슈사항 : 운용팀 to Sol, Sol to 운용팀
								</c:when>
								<c:when test="${SolutionReportModel.content_gubun == '권고사항'}">
									권고사항 : Sol to 운용팀
								</c:when>
								<c:when test="${SolutionReportModel.content_gubun == '문의사항'}">
									문의사항 : 운용팀 to Sol
								</c:when>
								<c:otherwise>
									기타 : 운용팀 to Sol, Sol to 운용팀
								</c:otherwise>
							</c:choose>
						</td>
						<th>
							완료여부
						</th>
						<td>
							<c:choose>
								<c:when test="${SolutionReportModel.result_yn == 'Y'}">
									완료
								</c:when>
								<c:otherwise>
									미완료
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
				
				<h3 class="stitle">내용</h3>
				<table class="tbl_type11">
					<colgroup>
						<col width="8%">
						<col width="*">
						<col width="*">
					</colgroup>
					<tr>
						<th>
							공지사유
						</th>
						<td colspan="2">
							${SolutionReportModel.noti_why}
						</td>
					</tr>
					<tr>
						<th>
							내용
						</th>
						<td colspan="2">
							<p class="mt10" style="min-height:60px;">${SolutionReportModel.content}</p>
						</td>
					</tr>
					<tr>
						<th rowspan="4">
							첨부파일
						</th>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile1}" name="attachFile1" size="20" mode="view" />
						</td>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile2}" name="attachFile2" size="20" mode="view" />
						</td>
					</tr>
					<tr>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile3}" name="attachFile3" size="20" mode="view" />
						</td>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile4}" name="attachFile4" size="20" mode="view" />
						</td>
					</tr>
					<tr>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile5}" name="attachFile5" size="20" mode="view" />
						</td>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile6}" name="attachFile6" size="20" mode="view" />
						</td>
					</tr>
					<tr>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile7}" name="attachFile7" size="20" mode="view" />
						</td>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile8}" name="attachFile8" size="20" mode="view" />
						</td>
					</tr>
				</table>
				
				<h3 class="stitle">공지대상부서</h3>
				<table class="tbl_type12">
					<thead>
						<tr>
							<th style="width:20%;">공지대상부서</th>
							<th style="width:10%;">확인</th>
							<th style="width:10%;">확인자</th>
							<th style="width:25%;">확인일시</th>
							<th style="width:35%;">COMMENT</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${SolutionReportModel.sosok_cnt > 0}">
								<c:forEach var="result" items="${SolutionReportModel.solutionReportSosokList}" varStatus="status">
									<c:if test="${result.use_yn == 'Y'}">
										<tr>
											<td>
												${result.sosok}
											</td>
											<td>
												<c:choose>
													<c:when test="${result.confirm_yn == 'Y'}">
														확인완료
													</c:when>
													<c:when test="${SolutionReportModel.session_user_group_id eq result.indept}">													
														<img src="/images/btn_identify.gif" alt="확인" style="cursor:pointer;" onclick="javascript:fn_confirm()"/>
													</c:when>
													<c:otherwise>
														미완료
													</c:otherwise>
												</c:choose>
											</td>
											<td>${result.update_name}</td>
											<td>${result.update_date}</td>
											<c:choose>
												<c:when test="${SolutionReportModel.session_user_group_id eq result.indept}">
													<td>
														<c:choose>
															<c:when test="${result.confirm_yn == 'Y'}">
																${result.sosok_comment}
															</c:when>
															<c:otherwise>
																<form:input path="sosok_comment" class="inp" size="40" maxlength="50"/>														
															</c:otherwise>
														</c:choose>
													</td>
												</c:when>
												<c:otherwise>
													<td>${result.sosok_comment}</td>
												</c:otherwise>											
											</c:choose>
										</tr>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="4">등록된 승인자가 없습니다.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
				<h3 class="stitle">승인</h3>
				<table class="tbl_type12">
					<thead>
						<tr>
							<th style="width:7%;">승인</th>
							<th style="width:8%;">이름</th>
							<th style="width:15%;">부서</th>
							<th style="width:15%;">전화번호</th>
							<th style="width:15%;">이메일</th>
							<th style="width:15%;">승인일시</th>
							<th style="width:25%;">COMMENT</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${SolutionReportModel.user_cnt > 0}">
								<c:forEach var="result" items="${SolutionReportModel.solutionReportUserList}" varStatus="status">
									<c:if test="${result.use_yn == 'Y'}">
										<tr>
											<td>
												<c:choose>
													<c:when test="${result.status_yn == 'Y'}">
														승인완료
													</c:when>
													<c:when test="${sessionId eq result.user_id}">													
														<img src="/images/btn_approbation.gif" alt="승인" style="cursor:pointer;" onclick="javascript:fn_yes()"/>
													</c:when>
													<c:otherwise>
														미완료
													</c:otherwise>
												</c:choose>
											</td>
											<td>${result.user_name}</td>
											<td>${result.sosok}</td>
											<td>${result.user_phone}</td>
											<td>${result.user_email}</td>
											<td>${result.update_date}</td>
											<c:choose>
												<c:when test="${sessionId eq result.user_id}">
													<td>
														<c:choose>
															<c:when test="${result.status_yn == 'Y'}">
																${result.au_comment}
															</c:when>
															<c:otherwise>
																<form:input path="au_comment" class="inp_w95" size="40" maxlength="50"/>														
															</c:otherwise>
														</c:choose>
													</td>
												</c:when>
												<c:otherwise>
													<td>${result.au_comment}</td>
												</c:otherwise>											
											</c:choose>
										</tr>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="6">등록된 승인자가 없습니다.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
	<!-- 폼 끝 -->
	
				<!-- COMMENT 시작 -->
				<!--총 덧글 수 -->
				<div class="cb_info_area2">
					<strong>${SolutionReportModel.comment_cnt}개</strong>의 답글이 있습니다.
				</div>
				
				<!--답변 -->
				<div class="cb_lstcomment mb30">
					<ul>
						<c:forEach var="CommentList" items="${CommentList}">
						<li class="cb_thumb_on">
							<div class="cb_comment_area fl_wrap">
								<div class="cb_info_area fl_left">
									<p class="cb_nick_name">${CommentList.reg_name}</p>  
									<span class="cb_date">${CommentList.reg_date}</span>
								</div>
								<div class="cb_dsc_comment fl_left">
									<p class="cb_dsc con_width95">${CommentList.content}</p>
								</div>
								<div class="cb_section2 fl_right">
									<c:choose>
										<c:when test="${sessionId eq CommentList.reg_user}">
											<span class="cb_nobar"><a href="javascript:fn_delete('<c:out value="${CommentList.solution_report_comment_seq}"/>')"><img src="/images/comment_delete.png" alt="삭제"></a></span> 
										</c:when>
										<c:otherwise>
											<sec:authorize ifAnyGranted = "ROLE_ADMIN">
												<span class="cb_nobar"><a href="javascript:fn_delete('<c:out value="${CommentList.solution_report_comment_seq}"/>')"><img src="/images/comment_delete.png" alt="삭제"></a></span> 
											</sec:authorize>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</li>
						</c:forEach>
					</ul>
				</div>
				<!--답글 끝 -->
	
				
				<div class="cb_txt_area fl_wrap">
					<div id="mail_popup" class="btn_line_blue22 fl_wrap mb10 ml70">
						<span class="fl_left">답글 시 메일 공지 목록</span>
						<p class="fl_left txt_red" style="margin: 7px;">
							* 클릭 (선택하지 않을 경우 메일이 전송되지 않습니다.)
						</p>
					</div>
					<span class="fl_left ml10" style="line-height:32px;">메일 목록 :</span>  
					<input type="text" id="check_mails" name="check_mails" class="new_inp inp_w90 ml03 fl_left" readonly="readonly" />
				</div>
				<!--덧글입력 -->
				<div class="cb_txt_area fl_wrap">
					<!-- 유동형 덧글 입력상자 -->
					<form:textarea path="content" rows="8" class="textarea_w90"/>
					<div class="cb_btn_area fl_right">
						<span><a href="javascript:fn_create()">등록</a></span>
					</div>
					
					<!-- //유동형 덧글 입력상자 -->
				</div>
				<!--덧글 끝 -->
				
			</div>
		</div>
	
	<!-- COMMENT 끝-->
	</form:form>
	
</body>
</html>
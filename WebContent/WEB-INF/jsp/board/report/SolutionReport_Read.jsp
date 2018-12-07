<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty SolutionReportModel.solution_report_seq ? '등록' : '수정'}" />
<c:set var="fileFlag" value="${empty SolutionReportModel.solution_report_seq ? 'create' : 'update'}" />
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
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
	
	init_system_popup();
	doLoadingInitEnd();
});
/* 수정 화면 */
function fn_read() {
	var form = document.getElementById('SolutionReportModel');

	form.retUrl.value = "/board/report/SolutionReport_View";
	form.action = "<c:url value='/board/report/Solution_Report_Read.do'/>";
	form.submit();
}

function fn_create() {
	if(!fn_Validation())
	return;
	
	doSubmit4File("SolutionReportModel", "/board/report/Solution_Report_Ajax_Create.do", "fn_callback_create");
}
function fn_update() {
	
	if(!fn_Validation())
		return;
	
	doSubmit4File("SolutionReportModel", "/board/qna/Solution_Report_Ajax_Update.do", "fn_callback_update");
}
function fn_delete(){
	if (confirm("삭제하시겠습니까?")) {
		doSubmit4File("SolutionReportModel", "/board/qna/Solution_Report_Ajax_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
	}
}
function fn_callback_create(data) {
	fn_readList();
}
function fn_callback_update(data) {
	fn_read();
}
function fn_callback_delete(data) {
	fn_readList();
}
function fn_readList() {
	var form  = document.getElementById('SolutionReportModel');

	form.action = "/board/report/Solution_Report_ReadList.do";
	form.submit();
}
function fn_Validation(){
	
	var title = document.getElementById("title");
	var content = document.getElementById("content");
	var system_seq = document.getElementById("system_seq");
	var noti_why = document.getElementById("noti_why");
	
	if(!isNullAndTrim(title, "제목을 입력해주세요.")){
		$("#title").focus();
		return false;
	}
	if(!isNullAndTrim(content, "내용을 입력해주세요.")){
		$("#content").focus();
		return false;
	}
	if(!isNullAndTrim(system_seq, "시스템을 선택해주세요.")){
		$("#system_name").focus();
		return false;
	}
	if(!isNullAndTrim(noti_why, "공지사유를 입력해주세요.")){
		$("#noti_why").focus();
		return false;
	}
	
	//승인자 공지대상부서 확인 여부
	var check_indepts =$("input[name='check_indepts']");
	var check_seqs =$("input[name='check_seqs']");
	
	var indepts_count = $("#check_indepts_count").val();
	var seqs_count = $("#check_seqs_count").val();
	
	var i_cnt=0;
	var s_cnt=0;
	
	for(var i=0; i < indepts_count; i++){
		if(check_indepts.eq(i).is(":checked")){
			i_cnt++;
		}
	}
	for(var i=0; i < seqs_count; i++){
		if(check_seqs.eq(i).is(":checked")){
			s_cnt++;
		}
	}
	
	if(i_cnt == 0){
		alert("공지대상부서를 선택해 주세요.");
		return false;
	}
	
	if(s_cnt == 0){
		alert("승인자를 선택해 주세요.");
		return false;
	}
	return true;
}

//시스템 선택 결과 처리
function fn_system_popup_callback(system_key, system_name){
	$("#system_seq").val(system_key);
	$("#system_name").val(system_name);
	read_user();
}
function read_user(){
	doSubmit("SolutionReportModel", "/board/report/SolutionReportUser_Ajax_Read.do", "fn_callback_read_user");
}
function fn_callback_read_user(data){
	$("#solution_report_user_data").html(data);
}

</script>
</head>
<body>
	<form:form commandName="SolutionReportModel" method="post" enctype="multipart/form-data" onsubmit="return false;">
	
		<form:hidden path="solution_report_seq"/>
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
		
		<form:hidden path="deleteList"/>
		
		<div class="con_Div2 con_area mt20">
			<div class="con_detail">
				<!--버튼위치 -->
				<div class="con_top_btn">
					<c:choose>
						<c:when test="${registerFlag == '등록'}">
							<span><a href="javascript:fn_create()">저장</a></span>
						</c:when>
						<c:otherwise>
							<span>
								<a href="javascript:fn_update()">수정</a>
								<a href="javascript:fn_delete()">삭제</a>
							</span>					
						</c:otherwise>
					</c:choose>
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
						<th>
							제목 <span  class='necessariness'>*</span>
						</th>
						<td colspan="7">
							<form:input path="title" class="new_inp inp_w95" maxlength="50"/>
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
						<col width="8%">
						<col width="30%">
						<col width="8%">
						<col width="16%">
					</colgroup>
					<tr>
						<th>
							시스템 <span  class='necessariness'>*</span>
						</th>
						<td>
							<form:hidden path="system_seq"/>
							<input type="hidden" name="system_seq_bak" value="${SolutionReportModel.system_seq}" />
							<form:input path="system_name" class="new_inp inp_w70 fl_left" readonly="readonly"/>
							<img src="/images/pop_btn_search1.gif" alt="선택" class="fl_left mg03" style="cursor: pointer;" id="open_system_popup" align="absmiddle" />
						</td>
						<th>
							내용분류
						</th>
						<td>
							<form:select path="content_gubun"  items="${SolutionReportModel.content_gubunList}" itemLabel="codeName" itemValue="code"/>
						</td>
						<th>
							완료여부
						</th>
						<td>
							<form:hidden path="result_yn"/>
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
							공지사유 <span  class='necessariness'>*</span>
						</th>
						<td colspan="2">
							<form:input path="noti_why" class="new_inp inp_w95" maxlength="50"/>
						</td>
					</tr>
					<tr>
						<th>
							내용 <span  class='necessariness'>*</span>
						</th>
						<td colspan="2">
							<form:textarea path="content" rows="10" class="textarea_w95" maxlength="15000"/>
						</td>
					</tr>
					<tr>
						<th rowspan="4">
							첨부파일
						</th>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile1}" name="attachFile1" size="20" mode="${fileFlag}" />
						</td>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile2}" name="attachFile2" size="20" mode="${fileFlag}" />
						</td>
					</tr>
					<tr>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile3}" name="attachFile3" size="20" mode="${fileFlag}" />
						</td>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile4}" name="attachFile4" size="20" mode="${fileFlag}" />
						</td>
					</tr>
					<tr>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile5}" name="attachFile5" size="20" mode="${fileFlag}" />
						</td>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile6}" name="attachFile6" size="20" mode="${fileFlag}" />
						</td>
					</tr>
					<tr>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile7}" name="attachFile7" size="20" mode="${fileFlag}" />
						</td>
						<td>
							<ui:file attachFileModel="${SolutionReportModel.attachFile8}" name="attachFile8" size="20" mode="${fileFlag}" />
						</td>
					</tr>
				</table>
					
				<div id="solution_report_user_data">
					<h3 class="stitle">공지대상부서</h3>
					<table class="tbl_type12">
						<thead>
							<tr>
								<th style="width:10%">&nbsp;</th>
								<th style="width:30%;">
									공지대상부서
								</th>
								<th style="width:10%;">
									확인
								</th>
								<th style="width:10%;">
									확인자
								</th>
								<th style="width:40%;">
									확인일시
								</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${registerFlag == '수정'}">
									<c:forEach var="result" items="${SolutionReportModel.solutionReportSosokList}" varStatus="status">
										<tr>
											<td>
												<c:choose>
													<c:when test="${result.use_yn == 'N'}">
														<input type="checkbox" name="check_indepts" value="${result.indept}" />
													</c:when>
													<c:otherwise>
														<input type="checkbox" name="check_indepts" value="${result.indept}" checked/>
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												${result.sosok}
											</td>
											<td>
												<c:choose>
													<c:when test="${result.confirm_yn == 'Y'}">
														확인
													</c:when>
													<c:otherwise>
														미확인
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												${result.update_name}
											</td>
											<td>
												${result.update_date}
											</td>
										</tr>
										<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_indepts_count"/></c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="5">시스템을 선택해 주세요.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
						
					<h3 class="stitle">승인</h3>
					<table class="tbl_type12">
						<thead>
							<tr>
								<th style="width:5%">&nbsp;</th>
								<th style="width:10%;">승인</th>
								<th style="width:10%;">이름</th>
								<th style="width:25%;">부서</th>
								<th style="width:20%;">전화번호</th>
								<th style="width:30%;">이메일</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${registerFlag == '수정'}">
									<c:forEach var="result" items="${SolutionReportModel.solutionReportUserList}" varStatus="status">
										<tr>
											<td>
												<c:choose>
													<c:when test="${result.use_yn == 'N'}">
														<input type="checkbox" name="check_seqs" value="${result.user_id}" />
													</c:when>
													<c:otherwise>
														<input type="checkbox" name="check_seqs" value="${result.user_id}" checked/>
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												<c:choose>
													<c:when test="${result.status_yn == 'Y'}">
														승인
													</c:when>
													<c:otherwise>
														미승인
													</c:otherwise>
												</c:choose>
											</td>
											<td>${result.user_name}</td>
											<td>${result.sosok}</td>
											<td>${result.user_phone}</td>
											<td>${result.user_email}</td>
										</tr>
										<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_seqs_count"/></c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="6">시스템을 선택해 주세요.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty NoticeModel.notice_seq ? '등록' : '수정'}" />
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javaScript"  defer="defer">
	$(document).ready(function($) {
		doLoadingInitStart();
		
		$('textarea[maxlength]').keydown(function(){
	        var max = parseInt($(this).attr('maxlength'));
	        var str = $(this).val();
			if(str.length > max) {
			    $(this).val(str.substr(0, max-1));
				alert("최대 [" + max + " 자]까지 입력 가능합니다.");
			}
		});
		
		doCalendar("main_date_start");
		doCalendar("main_date_end");
		fn_Display();
		doLoadingInitEnd();
	});

/* 수정 화면 */
function fn_read(id) {
	var form = document.getElementById('NoticeModel');

	form.id.value = id;
	form.retUrl.value = "/board/notice/Notice_View";
	form.action = "<c:url value='/board/notice/Notice_Read.do'/>";
	form.submit();
}

function fn_Display(){
	var chk = $('#main_pop_yn').is(":checked");
	if(chk){
// 		alert(chk);
		doDivSH("hide", "noticePopN", 0);
		doDivSH("show", "noticePopY", 0);
	}else{
// 		alert(chk);
		doDivSH("show", "noticePopN", 0);
		doDivSH("hide", "noticePopY", 0);
	}
}
function fn_create() {
	
	if(!fn_Validation())
		return;
	
	doSubmit4File("NoticeModel", "/board/notice/Notice_Create.do", "fn_callback_create");
}
function fn_update() {
	
	if(!fn_Validation())
		return;
	
	doSubmit4File("NoticeModel", "/board/notice/Notice_Ajax_Update.do", "fn_callback_update");
}
function fn_delete(){
	if (confirm("삭제하시겠습니까?")) {
		doSubmit4File("NoticeModel", "/board/notice/Notice_Ajax_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
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
	var form  = document.getElementById('NoticeModel');

	form.action = "/board/notice/Notice_ReadList.do";
	form.submit();
}
function fn_Validation(){
	
	var title = document.getElementById("title");
	var content = document.getElementById("content");
	var main_date_start = document.getElementById("main_date_start");
	var main_date_end = document.getElementById("main_date_end");
	
	if(!isNullAndTrim(title, "제목을 입력해주세요.")){
		$("#title").focus();
		return false;
	}
	if(!isNullAndTrim(content, "내용을 입력해주세요.")){
		$("#content").focus();
		return false;
	}
	
	if($('#main_pop_yn').is(":checked")){
		
		if(!isNullAndTrim(main_date_start, "시작일을 지정해주세요.")){
			$("#main_date_start").focus();
			return false;
		}
		if(!isNullAndTrim(main_date_end, "종료일을 지정해주세요.")){
			$("#main_date_end").focus();
			return false;
		}
		
		if(($("#main_date_start").val().replace(/-/g,""))>$("#main_date_end").val().replace(/-/g,"")){
			alert("시작일자가 종료일자보다 나중입니다.");
			$("#main_date_end").focus();
			return false;
		}  
 
	}
	
	return true;
}
</script>
</head>
<body>

	<form:form commandName="NoticeModel" method="post"  enctype="multipart/form-data" onsubmit="return false;">
		
		<form:hidden path="notice_seq"/>
		<!-- 첨부파일 -->
		<form:hidden path="master_file_id" />
		<!-- 페이징 -->
		<form:hidden path="pageIndex"/>
		
		<!-- return URL -->
		<form:hidden path="retUrl" />
		
		<!-- 검색조건 유지 -->
		<form:hidden path="searchCondition"/>
		<form:hidden path="searchKeyword"/>
		
		<form:hidden path="deleteList"/>
		
		<sec:authentication property="principal.username" var="sessionId"/>
		
		<div class="con_Div32">
			<div class="con_area">
				<div class="con_detail">
					<div class="con_top_btn fl_wrap">
						<c:choose>
							<c:when test="${registerFlag == '등록'}">
								<span><a href="javascript:fn_create()">저장</a></span>
							</c:when>
							<c:otherwise>
								<span><a href="javascript:fn_delete()">삭제</a></span>
								<span><a href="javascript:fn_update()">수정</a></span>
							</c:otherwise>
						</c:choose>
						<span><a href="javascript:fn_readList()">목록</a></span>
					</div>
					<!--기본정보 -->
		
					<h3 class="stitle">기본정보</h3>
					<table class="tbl_type11"> 
						<colgroup>
							<col width="10%">
							<col width="40%">
							<col width="10%">
							<col width="40%">
						</colgroup>
						<tr>
							<th>제목 <span  class='necessariness'>*</span></th>
							<td colspan="3"><form:input path="title" class="new_inp" style="width:96%;" maxlength="60"/></td>
						</tr>
						<tr>
							<th>내용 <span  class='necessariness'>*</span></th>
							<td colspan="3"><form:textarea path="content" rows="10" class="textarea_w95" maxlength="15000"/></td>
						</tr>
						<tr>
							<th>메인팝업공지</th>
							<td colspan="3">
								<p class="fl_left" style="width: 30%;" >
									<span id="noticePopN" class="help_notice2 mt10"> 체크박스를 체크하시면 달력을 선택 하실수 있습니다.</span>
									<span id="noticePopY" class="mt05" style="width:90%; display: none;">
										<form:input path="main_date_start" class="new_inp inp_w30 fl_left" id="main_date_start" readonly="true" size="7"/>
										<span class="fl_left mg05"> ~ </span> 
										<form:input path="main_date_end" class="new_inp inp_w30 fl_left" id="main_date_end" readonly="true" size="7"/>
									</span>
								</p>
								<span class="fl_left mt10">
								<form:checkbox path="main_pop_yn" id="main_pop_yn" onclick="javascript:fn_Display()" label="등록여부"/>
								</span>
							</td>
						</tr>
						<tr>
							<th>첨부파일1</th>
							<td>
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										<ui:file attachFileModel="${NoticeModel.file1}" name="file1" mode="update" />
									</c:when>
									<c:otherwise>
										<ui:file attachFileModel="${NoticeModel.file1}" name="file1" mode="create" />
									</c:otherwise>
								</c:choose>
							</td>
							<th>첨부파일2</th>
							<td>
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										<ui:file attachFileModel="${NoticeModel.file2}" name="file2" mode="update" />
									</c:when>
									<c:otherwise>
										<ui:file attachFileModel="${NoticeModel.file2}" name="file2" mode="create" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>첨부파일3</th>
							<td>
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										<ui:file attachFileModel="${NoticeModel.file3}" name="file3" mode="update" />
									</c:when>
									<c:otherwise>
										<ui:file attachFileModel="${NoticeModel.file3}" name="file3" mode="create" />
									</c:otherwise>
								</c:choose>
							</td>
							<th>첨부파일4</th>
							<td>
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										<ui:file attachFileModel="${NoticeModel.file4}" name="file4" mode="update" />
									</c:when>
									<c:otherwise>
										<ui:file attachFileModel="${NoticeModel.file4}" name="file4" mode="create" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>첨부파일5</th>
							<td colspan="3">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										<ui:file attachFileModel="${NoticeModel.file5}" name="file5" mode="update" />
									</c:when>
									<c:otherwise>
										<ui:file attachFileModel="${NoticeModel.file5}" name="file5" mode="create" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form:form>
	
</body>
</html>
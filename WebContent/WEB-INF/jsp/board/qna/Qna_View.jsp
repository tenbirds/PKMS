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
<script type="text/javaScript"  defer="defer">
$(document).ready(function($) {
	$("#content").val("");
});

/* 수정 화면 */
function fn_read(gubun) {
	var form = document.getElementById('QnaModel');

	if (gubun == "read") {
		form.retUrl.value = "/board/qna/Qna_Read";
	} else if (gubun == "view") {
		form.retUrl.value = "/board/qna/Qna_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}
	form.action = "<c:url value='/board/qna/Qna_Read.do'/>";
	form.submit();
}
function fn_delete(gubun, id){
	var form = document.getElementById('QnaModel');
	form.qna_comment_seq.value = id;
	
	if (confirm("삭제하시겠습니까?")) {
		if(gubun=="qna"){
			doSubmit4File("QnaModel", "/board/qna/Qna_Ajax_Delete.do", "fn_callback_delete", "삭제 되었습니다.");	
		}else if(gubun=="comment"){
			doSubmit4File("QnaModel", "/board/comment/Comment_Delete.do", "fn_callback_create", "삭제 되었습니다.");
		}
		
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
	var form  = document.getElementById('QnaModel');

	form.action = "/board/qna/Qna_ReadList.do";
	form.submit();
}
function fn_create() {
	
	var content = document.getElementById("content");
	
	if(!isNullAndTrim(content, "내용을 입력해주세요.")){
		$("#content").focus();
		return;
	}
	
	
	doSubmit4File("QnaModel", "/board/comment/Comment_Create.do", "fn_callback_create");
}


</script>
</head>
<body>

	<!-- QNA폼 시작 -->
	<form:form commandName="QnaModel" name="inputFrom" method="post"  enctype="multipart/form-data" onsubmit="return false;">
	
		<form:hidden path="qna_seq"  />
		<form:hidden path="qna_comment_seq"  />
		<!-- return URL -->
		<form:hidden path="retUrl" />
		<!-- 첨부파일 -->
		<form:hidden path="master_file_id" />
		
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
							<c:when test="${sessionId eq QnaModelData.reg_user}">
								<span><a href="javascript:fn_read('read', '<c:out value="${QnaModelData.qna_seq}"/>')">수정</a></span>
							</c:when>
							<c:otherwise>
								<sec:authorize ifAnyGranted = "ROLE_ADMIN">
									<span><a href="javascript:fn_read('read', '<c:out value="${QnaModelData.qna_seq}"/>')">수정</a></span>
								</sec:authorize>
							</c:otherwise>
						</c:choose>
						<span><a href="javascript:fn_readList()">목록</a></span>
					</div>
					<h3 class="stitle">기본정보</h3>
					<table class="tbl_type11">
						<colgroup>
							<col width="10%">
							<col width="40%">
							<col width="10%">
							<col width="40%">
						</colgroup>
						<tr>
							<th>제목</th>
							<td colspan="3">${QnaModelData.title}</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="mg20 line_height25">
									${QnaModelData.content}
								</div>
							</td>
						</tr>
						<tr>
							<th>등록자</th>
							<td class="inp_w3">${QnaModelData.reg_name}</td>
							<th>등록 시간</th>
							<td>${QnaModelData.reg_date}</td>
						</tr>
						<tr>
							<th>수정자</th>
							<td class="inp_w3">${QnaModelData.update_name}</td>
							<th>수정 시간</th>
							<td>${QnaModelData.update_date}</td>
						</tr>
					</table>

					<!-- QNA폼 끝 -->
					
					<!-- QNA_COMMENT 시작 -->
					<!--총 덧글 수 -->
					<div class="cb_info_area2">
						<strong>${QnaModelData.cnt}개</strong>의 답글이 있습니다.
					</div>
					
					<!--답변 -->
					<div class="cb_lstcomment">
						<ul>
							<c:forEach var="CommentList" items="${CommentList}">
							<li class="cb_thumb_on">
								<div class="cb_comment_area fl_wrap">
									<div class="cb_info_area fl_left">
										<p class="cb_nick_name mr20">${CommentList.reg_name}</p>
										<span class="cb_date">${CommentList.reg_date}</span>
									</div>
									<div class="cb_dsc_comment fl_left">
										<p class="cb_dsc con_width95">${CommentList.content}</p>
									</div>
									<div class="cb_section2  fl_right"> 
										<c:choose>
											<c:when test="${sessionId eq CommentList.reg_user}">
												<span class="cb_nobar"><a href="javascript:fn_delete('comment', '<c:out value="${CommentList.qna_comment_seq}"/>')"><img src="/images/comment_delete.png" alt="삭제"></a></span> 
											</c:when>
											<c:otherwise>
												<sec:authorize ifAnyGranted = "ROLE_ADMIN">
													<span class="cb_nobar"><a href="javascript:fn_delete('comment', '<c:out value="${CommentList.qna_comment_seq}"/>')"><img src="/images/comment_delete.png" alt="삭제"></a></span> 
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
				
					<!--덧글입력 -->
					<div class="cb_txt_area fl_wrap">
						<!-- 유동형 덧글 입력상자 -->
						<form:textarea path="content" rows="8" cssClass="fl_left"/>
						<div class="cb_btn_area fl_right">
							<span><a href="javascript:fn_create()">등록</a></span>
						</div>
						<!-- //유동형 덧글 입력상자 -->
					</div>
					<!--덧글 끝 -->
				</div>
			</div>
		</div>
	
	<!-- QNA_COMMENT 끝-->
	</form:form>
	
</body>
</html>
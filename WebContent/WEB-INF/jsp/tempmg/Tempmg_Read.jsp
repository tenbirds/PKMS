<%--
/**
 * 템플릿 상세 화면
 * @author : 009
 * @Date : 2012. 4. 23.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<c:set var="registerFlag" value="${empty TempmgModel.tpl_seq ? '등록' : '수정'}" />

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script> 

<script type="text/javaScript" defer="defer">
	$(document).ready(function($) {
		/* 테이블 스크롤 제어 */
		//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
// 		doTable("scrollTable_Column", "tbl_type", "1", "0", ["100", "1025"], "N");

		fn_column_readList('');
		fn_init();
	});

	var registerFlag = '<c:out value="${registerFlag}"/>';
	
	function fn_init() {
		if(registerFlag == "수정") {
			$("#use_yn_title").show();
			$("#use_yn_select").show();
			$("#btn_modify").show();
			$("#btn_copy").show();
			$("div[id=template_download_button]").css("display", "block");
		} else {
			$("#use_yn_title").hide();
			$("#use_yn_select").hide();
			$("#btn_modify").hide();
			$("#btn_copy").hide();
		}
	}

	/* 목록 페이지 이동 */
	function fn_readList(pageIndex) {
		var form = document.getElementById("TempmgModel");
		form.action = "<c:url value='/tempmg/Tempmg_ReadList.do'/>";
		form.submit();
	}
	
	/* 수정 */
	function fn_update() {
		if($("#use_yn").val() == 'Y') {
			if (confirm("이 버전의 사용여부를 \"예\"로 수정하시면 이전의 \"사용\" 템플릿 버전은 자동으로 \"사용안함\"으로 변경됩니다. 수정하시겠습니까?")) {
				fn_update_doSubmit();
				return;
			}
		} else {
			fn_update_doSubmit();
		}
	}

	function fn_update_doSubmit() {
		doSubmit4File("TempmgModel", "<c:url value='/tempmg/Tempmg_Ajax_Update.do'/>", "fn_callback_update");
	}
	
	/* Callback Update */
	function fn_callback_update(data) {
	}
	
	function fn_column_readList(tpl_seq) {
		if(tpl_seq != '') {
			$("input[id=tpl_seq]").val(tpl_seq);
		}
		$("input[id=position]").val("V");
	
		doSubmit("TempmgModel", "<c:url value='/tempmg/TempmgColumn_Ajax_ReadList.do'/>", "fn_callback_column_readList");
	}
	
	/* BP User 목록 결과 처리 */
	function fn_callback_column_readList(data) {
		$("#column_list_area").html(data);
	}
	
	/* 선택 시  */
	function fn_column_read(ord) {
		if(ord == '') {
			$("#ord").val("");
		} else {
			$("#ord").val(ord);
		}
		doSubmit("TempmgModel", "<c:url value='/tempmg/TempmgColumn_Ajax_Read.do'/>", "fn_callback_column_read");
	}

	/* 정보 콜백 처리 */
	function fn_callback_column_read(data) {
		doDivSH("empty", "column_detail_area", 0);
		$("#column_detail_area").html(data);
		doDivSH("show", "column_detail_area", 0);
		
		if($("#ord").val() == null || $("#ord").val() == '') {
			$("#title").val("");
		}
		$("#title").focus();
	}

	function fn_copy() {
		if (confirm("해당 템플릿의 정보를 복사하여 새로운 템플릿을 생성합니다. 복사하시겠습니까?")) {
			doSubmit("TempmgModel", "<c:url value='/tempmg/Tempmg_Ajax_Create.do'/>", "fn_callback_column_copy", "복사되었습니다. 수정화면으로 이동됩니다.");
		}
	}

	function fn_callback_column_copy(data) {
		fn_read($("#param1").val());
	}

	/* 등록/상세/수정 화면 */
	function fn_read(tpl_seq) {
		$("#tpl_seq").val(tpl_seq);

		var form = document.getElementById("TempmgModel");
		form.action = "<c:url value='/tempmg/Tempmg_Read.do'/>";
		form.submit();
	}

	//템플릿 다운로드
	function fn_excel_download() {
		doSubmit("TempmgModel", "/tempmg/TempmgColumn_ExcelDownload.do", "fn_callback_excel_download");
	}

	// 템플릿 다운로드 callback
	function fn_callback_excel_download(data) {
		var excel_file_name = $("input[id=param1]").val();
		downloadFile(excel_file_name, excel_file_name, "");
	}
</script>

</head>
<body>
<form:form commandName="TempmgModel" method="post" nctype="multipart/form-data" onsubmit="return false;">

	<!-- 페이징 -->
	<form:hidden path="pageIndex"/>

	<!-- return URL -->
	<form:hidden path="retUrl" />

	<!-- 검색조건 유지 -->
	<form:hidden path="searchCondition"/>
	<form:hidden path="searchKeyword"/>
	
	<form:hidden path="tpl_seq" />
	<form:hidden path="ord" />
	<form:hidden path="position" />
	
	<form:hidden path="pkgUseCnt"/>


<!--기본정보, 상세정보 DIV 시작(스크롤지정)-->
<div class="con_Div32">
	<div class="con_area">
		<div class="con_detail_long">
			<div class="con_top_btn fl_wrap">
				<!--버튼위치영역 -->
				<span id="btn_modify"><a href="javascript:fn_update();">수정</a></span>
				<span id="btn_copy"><a href="javascript:fn_copy();">복사하기</a></span>
				<span><a href="javascript:fn_readList();">목록</a></span>
			</div>
					
			<!--기본정보 -->
			<h3 class="stitle">기본 정보</h3>
			<table class="tbl_type11">
				<tr>
					<th class="td_bp_w1">템플릿 버전</th>
					<td class="td_bp_w2" id="tpl_ver"><c:out value="${TempmgModel.tpl_ver}"/></td>
					<th id="use_yn_title">사용여부</th>
					<td id="use_yn_select">
						<form:select path="use_yn" items="${TempmgModel.useYnItems}" itemLabel="codeName" itemValue="code" />
					</td>
				</tr>
			</table>
			
			<div class="fl_wrap mt10">
				<c:if test="${TempmgModel.pkgUseCnt != 0}">
					<span class="txt_red">* 이 버전은 [PKG 검증 요청]에 <c:out value="${TempmgModel.pkgUseCnt}"/> 건이 사용되었습니다. 사용 중인 버전은 항목 수정이 불가합니다. (사용여부만 수정 가능합니다.)</span>
				</c:if>
				<c:if test="${TempmgModel.pkgUseCnt == 0}">
					<span class="btn_line_blue2 fl_right"><a href="javascript: fn_column_read('');">추가</a></span>
				</c:if>
			</div>
			<h3 class="stitle">템플릿 항목</h3>
<!-- 			<div class="fakeContainer"> -->
			<table id="scrollTable_Column" class="tbl_type12">
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">항목명</th>
					</tr>
				</thead>
				<tbody id="column_list_area">
					<tr>
						<td colspan="2">등록된 항목이 없습니다.</td>
					</tr>
				</tbody>
			</table>
<!-- 			</div> -->
			<div id="column_detail_area" class="mt10">
			
			</div>

			<div id="template_download_button" class="mt05" style="display:none"><img src="/images/pop_btn_templetdown.gif" alt="템플릿다운로드" style="cursor:pointer;" onClick="fn_excel_download();"></div>
		</div>
	</div>
</div>

</form:form>
</body>
</html>

<%@page import="com.pkms.pkgmg.pkg.model.PkgModel"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script> 

<script type="text/javaScript">
$(document).ready(function($) {
	// 초기 화면 Init
	fn_init();

	/* 테이블 스크롤 제어 */
	doTable("scrollTable", "tbl_type", "1", "0", ["60", "70", "70", "262"], "N");
});

// Modal layer close
function fn_close() {
	try{
		parent.$('img[id=open_3_1]').closeDOMWindow();
	} catch(e){
		alert("알수없는 오류가 발생했습니다. F5를 이용해서 새로고침을 하세요.");
	}
}

// 초기 화면 Init
function fn_init() {
	// 부모창 Key 조회
	var pkg_seq = parent.$("input[id=pkg_seq]").val();
	var tpl_seq = parent.$("input[id=tpl_seq]").val();
	var pkg_detail_count = parent.$("input[id=pkg_detail_count]").val();
		
	// Key Set
	$("input[id=pkg_seq]").val(pkg_seq);
	$("input[id=tpl_seq]").val(tpl_seq);
	$("input[id=pkg_detail_count]").val(pkg_detail_count);
	
	var isError = "${pkgModel.pkg_detail_validation_success_yn}";

	if (isError != "Y" && isError != "N") {
		alert(isError);
		fn_close();
		return;
	}
	
	var isCheck = "${empty pkgModel.pkgDetailModelList}";
	
	if (isCheck == "true") {
		var tpl_ver = parent.$("input[id=tpl_ver]").val();
		var useY_tpl_ver = parent.$("input[id=useY_tpl_ver]").val();
		
		if(tpl_ver == null || tpl_ver == '') {
			
		} else {
			var msg = "업로드하게되면 등록된 데이터와 매핑정보는 삭제됩니다."
				  + "\n다운로드한 파일을 수정하여 업로드하시면 데이터를 보존하실 수 있습니다."
				  ;
			
			if(tpl_ver != useY_tpl_ver) {
				msg = msg 
					  + "\n\n적용되어 있는 템플릿 버전은 [" 
				      + tpl_ver + "]이며,\n사용가능 템플릿 버전은 [" 
				      + useY_tpl_ver + "]입니다."
				      + "\n\n보완적용내역을 다시 업로드 할 경우 사용 가능 템플릿 버전을 다운받아서 사용하셔야 합니다."
				      ;
			}
			msg = msg + "\n\n업로드 하시겠습니까?";
			
			if(confirm(msg)) {
				
			} else {
				fn_close();
			}
		}
		
		$("span[id=button_comment]").css("display", "block");
		$("span[id=button_comment]").html("검사 후 저장 가능합니다.");
		$("div[id=button_save]").css("display", "none");
	} else {
		if(isError == "N") {
			$("span[id=button_comment]").css("display", "block");
		$("span[id=button_comment]").html("잘못된 데이터가 있습니다. [수정 > 검사] 후 저장하세요.");
			$("span[id=button_save]").css("display", "none");
		} else {
			$("span[id=button_comment]").css("display", "none");
			$("span[id=button_save]").css("display", "block");
		}
	}
}

// 엑셀 검사 통과 후 저장
function fn_save() {
	var tab2_yn = parent.$("#master_file_id").val(); 
	if (tab2_yn != "") {
		$("#urgency_yn").val("N");
	}
	doSubmit4File("PkgModel", "/pkgmg/pkg/PkgSupplement_Popup_Create.do", "fn_save_callback", "저장되었습니다.");
	//doSubmit4File2("PkgModel", "/pkgmg/pkg/PkgSupplement_Popup_Create.do", "fn_save_callback", "저장되었습니다.");
}


// 보완적용내역 Update callback
function fn_save_callback() {
	parent.$("input[id=tpl_seq]").val($("input[id=param1]").val());
	parent.$("input[id=tpl_ver]").val($("input[id=param2]").val());
	fn_close();
	
	// 부모창 Update Callback 호출
	parent.fn_callback_update();
}

// 엑셀 유효성 검사
function fn_excel_upload() {
	var fileObj = $("input[name=file_excel_upload]");
	if (fileObj.val() == "") {
		alert("파일을 선택하세요.");
		return;
	}

	if(!new RegExp(".xlsx").test(fileObj.val()) && !new RegExp(".xls").test(fileObj.val()) && !new RegExp(".xlss").test(fileObj.val())
			&& !new RegExp(".xlsxx").test(fileObj.val())){
		alert("엑셀 파일만 올릴 수 있습니다.");
		return;
	}
	
	// 유효성 검사 submit
	var form = $("form[id=PkgModel]");
	form.action = "/pkgmg/pkg/PkgSupplement_Popup_ReadList.do";
	form.submit();
}

</script>

</head>
<body style="background-color: transparent">

<form:form commandName="PkgModel" method="post" enctype="multipart/form-data">
<form:hidden path="pkg_seq" />
<form:hidden path="tpl_seq" />
<form:hidden path="pkg_detail_count" />
<form:hidden path="master_file_id" />

<form:hidden path="urgency_yn" />

<div style="width:500px;height:490px;">
	<div style="width: 520px;" id="pop_wrap">
		<!--팝업 content -->
		<div id="pop_content" style="ly_pop">
		
			<!--타이틀 -->
			<h4 class="ly_header">※ 유효성 검사 및 업로드</h4>
			<div class="pop_search" style="width:500px;">
				<table class="sear_table1">
					<tr>
						<td>
							<ui:file attachFileModel="${PkgModel.file_excel_upload}" name="file_excel_upload" size="40" mode="create" />
						</td>
					</tr>
				</table>
		
				<!--조회버튼 -->
				<div class="pop_btn_sear_loc">
					<a href="javascript:fn_excel_upload();"><img src="/images/btn_test.gif" alt="검사하기"></a>
				</div>
			</div>
			<a class="close_layer" href="javascript:fn_close();"> <img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14"></a>
			<br/><br/><br/>
			
			<!-- 이곳이 내용긑  -->
			<!--테이블 --> 
		<div class="fakeContainer" style="width:500px;height:315px;">
				<table id="scrollTable" class="tbl_type" border="1" cellspacing="0">
					<thead>
					<tr>
						<th scope="col">번호</th>
						<th scope="col">분류</th>
						<th scope="col">검사결과</th>
						<th scope="col">검사내용</th>
					</tr>
					</thead>
					<tbody>
						<c:if test="${empty pkgModel.pkgDetailModelList}">
							<tr>
								<td colspan="4" height="30">검색 결과가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach var="result" items="${pkgModel.pkgDetailModelList}" varStatus="status">
							<tr>
<%-- 								<td><fmt:formatNumber value="${result.no}" type="number" minFractionDigits="0" /></td> --%>
								 <td>${result.no}</td> 
								<td>${result.new_pn_cr_gubun}</td>
								<td>${result.validation_result}</td>
								<td align="left">${result.validation_result_content}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			
			<!--버튼위치 -->
			<div>
			</div>
			<div  class="btn_location"  style=" padding-right:20px" >
				<span id="button_comment" style="display:block;">검사 후 저장 가능합니다.</span>
				<span id="button_save" style="display:none;"><a href="javascript:fn_save();"><img src="/images/btn_save.gif" alt="저장" /></a></span>
			</div>
			 
			 <div id="pop_footer"  >
				<a href="javascript:fn_close();"><img alt=닫기 src="/images/pop_btn_close.gif" width="38" height="21"></a>
			 </div>
			
		</div>
	</div>		
</div>  
</form:form>
</body>
</html>

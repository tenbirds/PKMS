<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>

<!-- Css -->
<!-- <link rel="stylesheet" type="text/css" href="/css/style.css"/> -->
<!-- <link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" /> -->
<!-- <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css"> -->

<!-- Js -->
<!-- <script type="text/javascript" src="/js/jquery/jquery-1.12.0.min.js"></script> -->
<!-- <script type="text/javascript" src="/js/jquery/ui.js"></script> -->

<!-- <script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script> -->



<link rel="stylesheet" type="text/css" href="/css/font.css"/>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
<!-- Js -->
<script type="text/javascript" src="/js/jquery/ui.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.qtip-1.0.0-rc3.min.js"></script>

<script type="text/javaScript" defer="defer">

$(document).ready(function($) {
	//달력
	doCalendar("stdate");
	doCalendar("eddate");
	
'<c:forEach var="list3" items="${pkgCheckListMg}" varStatus="status3">'
		$("#valuelow${status3.count}").qtip({
			content : "<p style='line-height:1.6em'>'"	
			+ '${list3.chk_content}'	
			+ "'</p>",
			position : {
				target : 'mouse'
			},
			style : {
				name : 'cream',
				tip : true
			}
		});
'</c:forEach>';	
				
	
				
	});



/* 수정 화면 */
function newAddBtn() {
// alert("신규등록 ");
var form = document.getElementById('PkgCheckListManagerModel');
form.chk_seq.value = '';
form.pageIndex.value = 1;
form.action = "<c:url value='/verifyTem_mg/pkg21_menuCheck_admin_view.do'/>";
form.submit();
	
}

function detailBtn(id) {
// 	alert("상세화면"+id);
	var form = document.getElementById('PkgCheckListManagerModel');
		form.chk_seq.value = id;
		form.pageIndex.value = 1;
		form.action = "<c:url value='/verifyTem_mg/pkg21_menuCheck_admin_detail.do'/>";
		form.submit();
	}



function searchBtn(){
	
	if(!validate())
		return;
	
	fn_readList(1);
}
function validate(){
	
	if($("#stdate").val()!=""){
		if($("#eddate").val()==""){
			alert("검색 시작일을 선택해주세요.");
			$("#eddate").focus();
			return false;
		}
	}
	if($("#eddate").val()!=""){
		if($("#stdate").val()==""){
			alert("검색 종료일을 선택해주세요.");
			$("#stdate").focus();
			return false;
		}
	}
	
	//name 있는 tag만 전달함.
	if(($("#stdate").val().replace(/-/g,""))>$("#eddate").val().replace(/-/g,"")){
		alert("시작일이 종료일자보다 나중입니다.");
		$("#eddate").focus();
		return ;
	}  
	
	return true;
}





//엑셀다운로드
function fn_list_excelDownload() {
	 
	doSubmit("PkgCheckListManagerModel", "/verifyTem_mg/menuCheck_admin_ExcelDownload.do", "fn_callback_file_download_newpncr");
	 
}
function fn_callback_file_download_newpncr(data) {
	var file_name = $("input[id=param1]").val();
	downloadFile(file_name, file_name, "");
}







/* 목록 페이지 이동 */
function fn_readList(pageIndex) {
	
	
	var form = document.getElementById('PkgCheckListManagerModel');
	form.pageIndex.value = pageIndex;
// 	document.getElementById('pageIndex').value = pageIndex;
	form.action = "/verifyTem_mg/pkg21_menuCheck_admin.do";
	form.submit();
}




</script>

</head>
<body>
	<form:form modelAttribute="PkgCheckListManagerModel" id="PkgCheckListManagerModel" name="PkgCheckListManagerModel" method="post" onsubmit="return false;">
	 <input id="pageIndex" name="pageIndex" type="hidden" value="">
<!-- <input id="pageIndex" name="pageIndex" type="hidden" value="1"> -->
 <input id="chk_seq" name="chk_seq" type="hidden" value="">

<!-- container -->
	<div class="new_con_Div32 new_pkms_sysSearchForm">
		
		
		<!--상단 검색 조건 start -->
		<div class="new_search fl_wrap">
			<table class="new_sear_table1 fl_wrap">
					<colgroup>
						<col width="10%">
						<col width="15%">
						<col width="8%">
						<col width="15%">
						<col width="7%">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th>등록일</th>
							<td colspan="3">
								<input type="text" id="stdate" name="stdate"  class="inp_w20 fl_left" readonly="true" value=""/>
								<span class="fl_left mg05"> ~ </span> 
								<input type="text" id="eddate" name="eddate"   class="inp_w20 fl_left" readonly="true" value=""/>
							</td>
							<th>등록자</th>
							<td>
								<input type="text" id="reg_user" name="reg_user" class="inp_w20">
							</td>
						</tr>
						<tr>
							<th>상태</th>
							<td class="sysc_inp">
								<select id="status" name="status">
									<option value="">전체</option>
									<option value="Y">사용</option>
									<option value="N">미사용</option>
								</select>
							</td>
							<th>사용분류</th>
							<td class="sysc_inp">
								<select id="chk_type" name="chk_type" >
								<option value="">전체</option>
								
								<c:forEach var="result" items="${code_list}" varStatus="status">
									<option value="${result.common_code}">${result.name}</option>
								</c:forEach>	
								</select>
							</td>							
							<th>내용</th>
							<td>
								<input type="text" id="title" name="title" class="new_inp inp_w90">
							</td>
						</tr>
					</tbody>
				</table>
				<!--조회버튼 -->
				<div class="new_btn_sear"><a href="#" onclick="javascript:searchBtn()">조 회</a></div>
		</div>
		<br />
<!--상단 검색 조건 end -->





		<div class="con_Div2 con_area">
			<div class="con_detail_long fl_wrap">
				<div class="con_top_btn fl_right">
					<span class="con_top_btn"><a href="#"  onclick="javascript:newAddBtn()">신규</a></span>
				</div>
				
					<table id="listtable" class="tbl_type12">
						<colgroup>
							<col width="9%">
							<col width="7%">
							<col width="20%">
							<col width="5%">
							<col width="15%">
							<col width="7%">
							<col width="7%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
						</colgroup>
						<thead>
							<tr>

								<th>사용분류</th>
								<th>상태</th>
								<th>내용</th>
								<th>등록자</th>
								<th>등록일</th>
							</tr>
						</thead>
						<tbody>
						
						
						
		<c:forEach var="result" items="${pkgCheckListMg}" varStatus="status">

							<tr id = "valuelow${status.count}"   onclick="javascript:detailBtn('<c:out value="${result.chk_seq}"/>')">
								<td class="td_center txt_red">${result.chk_type_name}</td>
								<td class="td_center">
									<c:choose>
										<c:when test="${result.status eq 'Y'}">
											사용
										</c:when>
										<c:otherwise>
											미사용
										</c:otherwise>
									</c:choose>
								</td>
								<td class="td_left"><a href="#"><c:out value="${fn:substring(result.title,0,37)}" /> <c:out value="${fn:length(result.title) > 37 ? '...' : ''}" /></a></td>
								<td class="td_center">${result.reg_user_name}</td>
								<td class="td_center">${result.reg_date}</td>
							</tr>

		</c:forEach>				
						
							
							
						</tbody>
					</table>
					<div class="con_top_area fl_right mt10"><span class="con_top_btn"><a href="javascript:fn_list_excelDownload();">엑셀 다운로드</a></span></div>	
					<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" />			
				</div>
				
			</div>
<!-- 		</div> -->
		<!-- // PKG현황 -->
	</div>
	<!-- // Main contents -->
	
	<!-- footer -->
<!-- 	<footer> -->
<!-- 		<div class="footer_wrap">Copyright © 2012,2018  SKTelecom All Rights Reserved</div> -->
<!-- 	</footer> -->
	<!-- //footer -->
<!-- </div> -->
<!-- //container -->
	</form:form>
</body>
</html>

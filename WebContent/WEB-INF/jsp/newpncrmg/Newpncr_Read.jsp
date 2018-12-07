<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty NewpncrModel.new_pn_cr_seq ? '등록' : '수정'}" />
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javaScript"  defer="defer">
$(document).ready(function($) {
	init_system_popup();
	init_system_popup2();
	fn_duplication_check();
	
	$('textarea[maxlength]').keydown(function(){
        var max = parseInt($(this).attr('maxlength'));
        var str = $(this).val();
		if(str.length > max) {
		    $(this).val(str.substr(0, max-1));
			alert("최대 [" + max + " 자]까지 입력 가능합니다.");
		}
	});
	doModalPopup("img[name=open_relation_system_popup", 1, "click", 478, 680, "/sys/system/System_relation_Popup_Read_Detail.do");
	if('${registerFlag}' == "수정"){
		doSubmit2("NewpncrModel", "/newpncrmg/Newpncr_Ajax_Chart_Read.do", "fn_PncrChart_rollback");
	}
	
});
function fn_readList() {
	$("#reject_flag").val("");
	var form = document.getElementById('NewpncrModel');
	form.action = "<c:url value='/newpncrmg/Newpncr_ReadList.do'/>";
	form.submit();
}
function fn_create(){
	
	if(!fn_validataion())
		return;
	
	doSubmit4File("NewpncrModel", "/newpncrmg/Newpncr_Create.do", "fn_callback_create", "등록 되었습니다.");
}
function fn_validataion(){

	var title = document.getElementById("title");

	if(!isNullAndTrim(title, "제목을 입력해주세요.")){
		$("#title").focus();
		return false;
	}
	
	if($("#system_seq").val()==""){
		alert("시스템을 선택해주세요.");
		$("#system_seq").focus();	
		return false;
	}
	
	//ctrl + v 시 15000자 이상 입력가능 문제 해결
	var problem = $("#problem").val();
	var requirement = $("#requirement").val();
	var max = 15000;
	
	if(problem.length > max || requirement.length > max){
		alert("최대 [" + max + " 자]까지 입력 가능합니다.");
		return false;
	}
	
	return true;
}
function fn_callback_create(){
	fn_readList();
}
function fn_update(key){
	
	
	if(!fn_validataion())
		return;
	
	var message = "";

	var form = document.getElementById('NewpncrModel');
	form.stateInfo.value = key;	
	
	if(key=="update"){
		message = "수정 되었습니다.";
	}
	
	doSubmit4File("NewpncrModel", "/newpncrmg/Newpncr_Update.do", "fn_callback_update", message);
}
function fn_callback_update(){
	fn_read();
}
function fn_read(){
	var form = document.getElementById('NewpncrModel');
	form.retUrl.value = "/newpncrmg/Newpncr_View";
	form.action = "<c:url value='/newpncrmg/Newpncr_Read.do'/>";
	form.submit();
}
function fn_delete(){
	if (confirm("삭제하시겠습니까?")) {
		doSubmit4File("NewpncrModel", "/newpncrmg/Newpncr_Delete.do", "fn_callback_delete", "삭제 되었습니다.");
	}
}
function fn_callback_delete(){
	fn_readList();
}
//시스템 선택 결과 처리
function fn_system_popup_callback(system_key, system_name){
	$("#system_seq").val(system_key);
	$("#system_name").val(system_name);

	if('${registerFlag}' == "수정"){
		$("#registerFlag").val("1");
	}
	doSubmit2("NewpncrModel", "/newpncrmg/Newpncr_Ajax_Chart_Read.do", "fn_PncrChart_rollback");
}

//시스템 하나더 선택 결과 처리
function fn_system_popup_callback2(system_key, system_name){
	$("#problem").focus();
	$("#system_seq2").val(system_key);
	$("#system_name2").val(system_name);
	if('${registerFlag}' == "수정"){
		$("#registerFlag").val("1");
	}
	doSubmit2("NewpncrModel", "/newpncrmg/Newpncr_Ajax_Chart_Read.do", "fn_PncrChart_rollback");
}

function fn_PncrChart_rollback(data){
	$("#chartResult").html(data);
}
function fn_system_relation_popup_callback(system_key, system_name){
	var temp_relation_id = $("#temp_relation_id").val();
	$("#" + temp_relation_id).val(system_name);
	$("#relation_system_seq" + temp_relation_id.replace("relation_system","")).val(system_key);
	if('${registerFlag}' == "수정"){
		$("#registerFlag").val("1");
	}
	doSubmit2("NewpncrModel", "/newpncrmg/Newpncr_Ajax_Chart_Read.do", "fn_PncrChart_rollback");
}
function fn_duplication_check(){
// 	doModalPopup("img[id=open_system_popup]", 1, "click", 478, 660, "/sys/system/System_Popup_Read.do");
	doModalPopup("img[id=overlap]", 1, "click", 1113, 670, "/newpncrmg/Newpncr_Duplication_Check_Popup_ReadList.do");
}
function fn_child_setTitle(){
		
	var title = $("#title").val();
	return title;
}
function relation_system_add(ts){
	var index = $(ts).parent().parent().index();
	var html = "";
		html +=	"<tr>";
		html +=	"<th style='width: 180px; height: 40px;'></th>";
		html += "<th style='width: 20px;'><p onclick='relation_system_add(this)'  class='btn_plus_minus' >+</p><p class='btn_plus_minus' onclick='relation_system_remove(this)'>-</p></th>";
		html += "<td><input type='hidden' name='relation_system_seq' class='new_inp inp_w40 fl_left' /><input type='text' name='relation_system'  class='new_inp inp_w40 fl_left' /><span class='fl_left mg02'><img src='/images/pop_btn_search1.gif' alt='선택' style='cursor: pointer;' align='absmiddle' onclick='relation_system_popup(this)' name='open_relation_system_popup'/></span></td>";
		html += "</tr>";
	
	$("#addTable tr:eq("+index+")").after(html);
	$("img[name=open_relation_system_popup").unbind("click");
	doModalPopup("img[name=open_relation_system_popup", 1, "click", 478, 660, "/sys/system/System_relation_Popup_Read_Detail.do");
	
	$("input[name=relation_system]","#addTable").each(function(pi, po){
		if(pi!=0)
		$(this).attr("id", "relation_system"+pi);
	});

	$("input[name=relation_system_seq]","#addTable").each(function(pi, po){
		if(pi!=0)
		$(this).attr("id", "relation_system_seq"+pi);
	});
	
}
function relation_system_popup(ts){
	var relation_system_id = $(ts).parent().parent().find("input[name=relation_system]").attr("id");
	$("#temp_relation_id").val(relation_system_id);
}
function relation_system_remove(ts){
	var index = $(ts).parent().parent().index();
	$("#addTable  tr:eq("+index+")").remove();
	doSubmit2("NewpncrModel", "/newpncrmg/Newpncr_Ajax_Chart_Read.do", "fn_PncrChart_rollback");
	if('${registerFlag}' == "수정"){
		$("#registerFlag").val("1");
	}
}
function relation_system_First_remove(){
	$("#relation_system_seq").val("");
	$("#relation_system").val("");
	doSubmit2("NewpncrModel", "/newpncrmg/Newpncr_Ajax_Chart_Read.do", "fn_PncrChart_rollback");
}
</script>
</head>
<body>
	<form:form commandName="NewpncrModel" method="post" enctype="multipart/form-data" onsubmit="return false;">
	
	<form:hidden path="new_pn_cr_seq"/>
	<!-- 첨부파일 -->
	<form:hidden path="master_file_id" />
	<!-- 페이징 -->
	<form:hidden path="pageIndex"/>
	<!-- return URL -->
	<form:hidden path="retUrl" />
	<!-- 검색조건 유지 -->
	<form:hidden path="searchCondition"/>
	<form:hidden path="searchKeyword"/>
	<form:hidden path="search_reg_date_start"/>
	<form:hidden path="search_reg_date_end"/>
	<form:hidden path="search_system_seq"/>
	<form:hidden path="search_system_name"/>
	<form:hidden path="search_reg_user"/>
	<form:hidden path="search_reg_name"/>
	<form:hidden path="searchRoleCondition"/>
	<form:hidden path="searchNew_pncr_gubun"/>
	<form:hidden path="reject_flag"/>
	<!-- 업데이트 상태값 -->
	<form:hidden path="stateInfo"/>
	
	<form:hidden path="deleteList"/>
	
	<input type="hidden" id="temp_relation_id"/>
	<input type="hidden" id="registerFlag" name="registerFlag" value="0"/>
	
	
	<div class="new_con_Div32">
		<!--기본정보 -->
		
			
		<div class="con_area">				
			<div class="con_detail">
				<div class="con_top_btn fl_wrap">
					<span>
						<a href="javascript:fn_readList()">목록</a>
					</span>
					<c:choose>
						<c:when test="${registerFlag == '등록'}">
							<span>
								<a href="javascript:fn_create()">저장</a>
							</span>
						</c:when>
						<c:otherwise>
							<span>
								<a href="javascript:fn_delete()">삭제</a>
								<a href="javascript:fn_update('update')">저장</a>
							</span>
						</c:otherwise>
					</c:choose>
					
				</div>
				
				<h3 class="stitle">기본정보</h3>
				
				<table class="tbl_type11 w100">
					<colgroup>
						<col width="7%">
						<col width="12%">
						<col width="7%">
						<col width="13%">
						<col width="7%">
						<col width="24%">
						<col width="7%">
						<col width="*">
					</colgroup>
					<tr>
						<th>제목 <span  class='necessariness'>*</span></th>
						<td colspan="7">
							<form:input path="title" class="new_inp inp_w90" maxlength="60"/>
							<c:if test="${registerFlag == '등록'}">
								<a href="javascript:fn_duplication_check()"><img src="/images/two_btn_overlap.gif" alt="중복검사" id="overlap" align="absmiddle" style="vertical-align: middle;"/></a>
							</c:if>
						</td>
					</tr>
					<tr>
						<th>유형 <span  class='necessariness'>*</span></th>
						<td><form:select path="new_pncr_gubun" items="${NewpncrModel.new_pncr_gubunList}" itemLabel="codeName" itemValue="code"/></td>
						<th>우선순위</th>
						<td><form:select path="priority" items="${NewpncrModel.priorityList}" itemLabel="codeName" itemValue="code"/></td>
						<c:choose>
							<c:when test="${registerFlag == '등록'}">
								<th>시스템 <span  class='necessariness'>*</span></th>
								<td>
									<form:hidden path="system_seq" class="new_inp inp_w70"/>
									<form:input path="system_name" readonly="true" class="new_inp inp_w70 fl_left"/>
									<span class="fl_left mg02"><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" /></span>
								</td>
								<th>시스템2</th>
								<td>
									<form:hidden path="system_seq2" class="new_inp inp_w70"/>
									<form:input path="system_name2" readonly="true" class="new_inp inp_w70 fl_left"/>
									<span class="fl_left mg02"><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup2" align="absmiddle" /></span>
								</td>
							</c:when>
							<c:otherwise>
								<th>시스템 <span  class='necessariness'>*</span></th>
								<td colspan="3">
									<form:hidden path="system_seq" class="new_inp inp_w70"/>
									<form:input path="system_name" readonly="true" class="new_inp inp_w70 fl_left"/>
									<span class="fl_left mg02"><img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup" align="absmiddle" /></span>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table>
				
		<!-- </fieldset>
			
			<fieldset class="detail_fieldset"> -->
				<br>
				<h3 class="stitle">상세정보</h3>
			
				<table class="tbl_type11" style="width: 100%">
					<colgroup>
						<col width="7%">
						<col width="*">
						<col width="7%">
						<col width="*">
					</colgroup>
					<tr>
						<th>문제구분</th>
						<td colspan="3"><form:select path="problem_gubun" items="${NewpncrModel.problem_gubunList}" itemLabel="codeName" itemValue="code"/></td>
					</tr>
					<tr>
						<th>문제점</th>
						<td colspan="3"><form:textarea path="problem" rows="5" cssStyle="width:95%;"  maxlength="15000"/></td>
					</tr>
					<tr>
						<th>요구사항</th>
						<td colspan="3"><form:textarea path="requirement" rows="5" cssStyle="width:95%;"  maxlength="15000"/></td>
					</tr>
					<tr>
					<th>첨부파일1</th>
					<td>
						<c:choose>
							<c:when test="${registerFlag == '수정'}">
								<ui:file attachFileModel="${NewpncrModel.file1}" name="file1" mode="update" />
							</c:when>
							<c:otherwise>
								<ui:file attachFileModel="${NewpncrModel.file1}" name="file1" mode="create" />
							</c:otherwise>
						</c:choose>
					</td>
					<th>첨부파일2</th>
					<td>
						<c:choose>
							<c:when test="${registerFlag == '수정'}">
								<ui:file attachFileModel="${NewpncrModel.file2}" name="file2" mode="update" />
							</c:when>
							<c:otherwise>
								<ui:file attachFileModel="${NewpncrModel.file2}" name="file2" mode="create" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>첨부파일3</th>
					<td>
						<c:choose>
							<c:when test="${registerFlag == '수정'}">
								<ui:file attachFileModel="${NewpncrModel.file3}" name="file3" mode="update" />
							</c:when>
							<c:otherwise>
								<ui:file attachFileModel="${NewpncrModel.file3}" name="file3" mode="create" />
							</c:otherwise>
						</c:choose>
					</td>
					<th>번호</th>
					<td><c:out value="${NewpncrModel.new_pn_cr_no}" /></td>
				</tr>
				</table>
				
		<!-- </fieldset>
		<fieldset class="detail_fieldset"> -->
				<br>
				<h3 class="stitle">로드맵 정보</h3>
				
				<c:if test="${registerFlag ne '수정'}">
				<table class="tbl_type11" style="width: 100%" id="addTable">
					<colgroup>
						<col width="10%">
						<col width="4%">
						<col width="*">
					</colgroup>
					<tr>
						<th>연관 시스템</th>
						<th style="width: 20px; cursor: pointer;">
							<p class="btn_plus_minus" onclick="relation_system_add(this)">+</p><p class="btn_plus_minus" onclick='relation_system_First_remove()'>-</p>
						</th>
						<td>
						<c:choose>
							<c:when test="${not empty systemList}">
								<input type="hidden" name="relation_system_seq" id="relation_system_seq" value="${systemList[0].system_seq }" class="new_inp inp_w40 fl_left"/>
								<input type="text" name="relation_system" id="relation_system" style="width:250px;"value=" ${systemList[0].name }" class="new_inp inp_w40 fl_left"/>			
							</c:when>
							<c:otherwise>
								<input type="hidden" name="relation_system_seq" id="relation_system_seq" class="new_inp inp_w40 fl_left"/>
								<input type="text" name="relation_system" id="relation_system"  class="new_inp inp_w40 fl_left"/>
							</c:otherwise>
						</c:choose>
						<span class="fl_left mg02"><img src="/images/pop_btn_search1.gif" alt="선택" align='absmiddle' style="cursor: pointer;" name="open_relation_system_popup" onclick="relation_system_popup(this)"/></span></td>
					</tr>
				</table>
				</c:if>
					
				
				<div id="chartResult"></div>
			
			</div>
		</div>
	</div>
	
	</form:form>
</body>
</html>
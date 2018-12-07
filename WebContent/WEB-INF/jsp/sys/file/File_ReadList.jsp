<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<!-- <link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" /> -->
<%-- <script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script>  --%>
<script type="text/javaScript"  defer="defer">
	$(document).ready(function($) {
		/* 테이블 스크롤 제어 */
		//doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr);
// 		doTable("scrollTable", "tbl_type", "1", "0", ["150", "750", "150", "150"]);
		
		
	});
	
	function fn_readList(pageIndex) {
// 		document.getElementById('pageIndex').value = pageIndex;
		
		var form = document.getElementById('FileModel');
		form.action = "<c:url value='/sys/file/File_ReadList.do'/>";
		form.submit();
	}
	
	function fn_group2_readList() {
		doSubmit("FileModel", "/sys/file/Group2_Select_Ajax_Read.do", "fn_callback_group2_readList");
	}
	function fn_callback_group2_readList(data) {
		$("select[id=group2Condition]").html(data);
		fn_group3_readList();
	}
	
	function fn_group3_readList() {
		doSubmit("FileModel", "/sys/file/Group3_Select_Ajax_Read.do", "fn_callback_group3_readList");
	}
	function fn_callback_group3_readList(data) {
		$("select[id=group3Condition]").html(data);
		fn_system_readList();
	}
	
	function fn_system_readList() {
		doSubmit("FileModel", "/sys/file/System_Select_Ajax_Read.do", "fn_callback_system_readList");
	}
	function fn_callback_system_readList(data) {
		$("select[id=systemCondition]").html(data);
		$("select[id=gubunCondition]").val("");
	}
	//전체 체크
	function fn_allCheck() {
		var count = $("#system_seqs_count").val();

		var system_seqs = $("input[name='system_seqs']"); 
		
	    var chk = $("#allCheckbox").is(":checked");
	    
	    for(var i=0; i<count; i++) {
	    	system_seqs.eq(i).attr("checked", chk);
		}
	}
	
	function fn_mail_send(){
		var count = $("#system_seqs_count").val();
		var system_seqs = $("input[name='system_seqs']");
		var cnt = 0;
		for(var i=0; i<count; i++) {
	    	if(system_seqs.eq(i).is(":checked")){
	    		cnt++;
	    	}
		}
		if(cnt == 0){
			alert("1개 이상의 자료를 선택해 주세요");
			return;
		}
		
		var url = "/sys/file/Mail_Popup_Send.do";
		var option = "width=1000px, height=520px, scrollbars=no, resizable=no, statusbar=no";
		var form = document.getElementById("FileModel");
		var sWin = window.open("", "mail_send", option);
		
		form.target = "mail_send";
		form.action = url;
		form.submit();
		sWin.focus();
	}
	
	function system_m(group1_seq, group2_seq, group3_seq, system_seq) {
		$("#group1_seq").val(group1_seq);
		$("#group2_seq").val(group2_seq);
		$("#group3_seq").val(group3_seq);
		$("#system_seq").val(system_seq);
		
		var form = document.getElementById("FileModel");
		form.action = "<c:url value='/sys/group1/Group1_ReadList.do'/>";
		form.submit();
	}
	
	function fn_file_confirm_yn(master_file_id,attach_file_id){
		if(master_file_id == '' || attach_file_id == ''){
			alert("파일을 인식할 수 없습니다. 파일을 등록하여 주시기 바랍니다.");
			return;
		}
		$("#master_file_id").val(master_file_id);
		$("#attach_file_id").val(attach_file_id);
		doSubmit("FileModel", "/sys/file/File_Confirm_YN.do", "fn_callback_confirm", "완료 되었습니다.");
	}
	function fn_callback_confirm(){
		
		fn_readList(1);
	}
	function fn_file_excel_download() {
		doSubmit("FileModel", "/sys/file/file_ExcelDownload.do", "fn_callback_file_excel_download");
	}
	function fn_callback_file_excel_download(data) {
		var file_name = $("input[id=param1]").val();
		downloadFile(file_name, file_name, "");
	}
</script>
</head>

<body>
	<form:form commandName="FileModel" method="post" onsubmit="return false;">
<%-- 	<form:hidden path="pageIndex" /> --%>
<%-- 	<form:hidden path="notice_seq"/> --%>
<%-- 	<form:hidden path="retUrl" /> --%>
		
		<!-- 시스템 링크 -->
		<form:hidden path="group1_seq" />
		<form:hidden path="group2_seq" />
		<form:hidden path="group3_seq" />
		<form:hidden path="system_seq" />
		
		<form:hidden path="master_file_id" />
		<form:hidden path="attach_file_id" />
		
		<!--조회 -->
		<div class="search">
			<table class="sear_table1" >
				<tr>
					<th style="width:70px;">대분류</th>
					<td>
						<form:select path="group1Condition" style="width:220px;" onchange="fn_group2_readList();">
							<form:option value="" label="전 체" />
							<form:options items="${Group1List}" itemLabel="codeName" itemValue="code" />
						</form:select>
					</td>
					<th style="width:70px;">중분류</th>
					<td>
						<form:select path="group2Condition" style="width:220px;" onchange="fn_group3_readList();">
							<form:option value="" label="전 체" />
							<form:options items="${Group2List}" itemLabel="codeName" itemValue="code" />
						</form:select>
					</td>
					<th style="width:70px;">소분류</th>
					<td>
						<form:select path="group3Condition" style="width:220px;" onchange="fn_system_readList();">
							<form:option value="" label="전 체" />
							<form:options items="${Group3List}" itemLabel="codeName" itemValue="code" />
						</form:select>
					</td>
				</tr>
				<tr>
					<th style="width:70px;">시스템</th>
					<td>
						<form:select path="systemCondition" style="width:220px;">
							<form:option value="" label="전 체" />
							<form:options items="${SystemList}" itemLabel="codeName" itemValue="code" />
						</form:select>
					</td>
					<th style="width:70px;">자료구분</th>
					<td>
						<form:select path="gubunCondition" style="width:220px;">
							<form:option value="" label="전 체" />
							<form:options items="${fileGubunList}" itemLabel="codeName" itemValue="code" />
						</form:select>
					</td>
					<th style="width:70px;">파일명</th>
					<td>
						<form:input type="text" path="file_org_name"/>
					</td>
					<th style="width:70px;">파일미등록</th>
					<td>
						<form:checkbox path="no_file"/>
<%-- 						<form:checkbox path="no_file"/> --%>
					</td>
				</tr>
			</table>

			<!--조회버튼 -->
			<div class="btn_sear"><a href="javascript:fn_readList(1)"></a></div>
		</div>


		<!--버튼위치 -->
		<div class="btn_location">		
			<span>
				<span>
					<a href="javascript:fn_mail_send();">
						<img src="/images/btn_mail_send.gif" alt="신규" />
					</a>
				</span>
				<span>
					<a href="javascript:fn_file_excel_download();">
						<img src="/images/btn_excelldown.gif" alt="엑셀 다운로드" />
					</a>
				</span>
			</span>
		</div>
		<!--테이블, 페이지 DIV 시작 -->
		<div class="con_Div2" style="min-height: 650px; overflow: auto;">
			
			<!-- 스크롤 DIV -->
			<div class="fakeContainer" style="width:1200px;height:500px">
			<!--테이블 -->
			<table id="scrollTable" class="tbl_type" border="1">
				<thead>
				<tr>
					<th scope="col">
						<input type="checkbox" id="allCheckbox" onclick="fn_allCheck();" />
					</th>
					<th scope="col" style="width: 80px;">대분류</th>
					<th scope="col" style="width: 80px;">중분류</th>
					<th scope="col" style="width: 80px;">소분류</th>
					<th scope="col" style="width: 80px;">시스템명</th>
					<th scope="col" style="width: 120px;">자료구분</th>
					<th scope="col">파일명</th>
					<th scope="col" style="width: 120px;">등록일</th>
					<th scope="col" style="width: 80px;">등록자</th>
					<th scope="col" style="width: 80px;">대표자</th>
					<th scope="col" style="width: 80px;">검토</th>
					<th scope="col" style="width: 120px;">검토일</th>
					<th scope="col" style="width: 80px;">검토자</th>
				</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty fileModelList}">
							<c:forEach var="fileModelList" items="${fileModelList}" varStatus="status">
								<tr>
									<td>
										<input type="checkbox" name="system_seqs" value="${fileModelList.system_seq}" />
									</td>
									<td style="text-align: left;"><c:out value="${fileModelList.group1_name}" /></td>
									<td style="text-align: left;"><c:out value="${fileModelList.group2_name}" /></td>
									<td style="text-align: left;"><c:out value="${fileModelList.group3_name}" /></td>
									<td style="text-align: left;">
										<a href="javascript:system_m(${fileModelList.group1_seq }, ${fileModelList.group2_seq }, ${fileModelList.group3_seq }, ${fileModelList.system_seq } );">
											<c:out value="${fileModelList.system_name}" />
										</a>
									</td>
									<td style="text-align: left;"><c:out value="${fileModelList.tree_name}" /></td>
									<td style="text-align: left; text-decoration: underline; color:blue;">
									<a href="#" onclick="javascript:downloadFile('${fileModelList.file_name}','${fileModelList.file_org_name}','${fileModelList.file_path}'); return false;">${fileModelList.file_org_name}</a>
									</td>
									<td><c:out value="${fileModelList.reg_date}" /></td>
									<td><c:out value="${fileModelList.reg_user}" /></td>
									<td><c:out value="${fileModelList.system_user_name}" /></td>
									<td>
										<c:choose>
											<c:when test="${fileModelList.confirm_yn == 'Y'}">완료</c:when>
											<c:otherwise>
												<img src="/images/btn_identify.gif" alt="확인" style="cursor:pointer;" onclick="javascript:fn_file_confirm_yn('${fileModelList.master_file_id}','${fileModelList.attach_file_id}')"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td><c:out value="${fileModelList.confirm_date}" /></td>
									<td><c:out value="${fileModelList.confirm_user}" /></td>
								</tr>
								<c:if test="${status.last}">
									<input type="hidden" value="${status.count}" id="system_seqs_count"/>
								</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr><td colspan="9">등록된 자료가 없습니다.</td></tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			</div>
			
<%-- 			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_readList" /> --%>
			
		</div>
	</form:form>
	
</body>
</html>
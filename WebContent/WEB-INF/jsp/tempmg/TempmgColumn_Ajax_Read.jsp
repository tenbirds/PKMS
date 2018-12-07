<%--
/**
 * 템플릿 항목 등록 폼
 * 
 * @author : 009
 * @Date : 2012. 4. 20.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<c:set var="registerFlag" value="${empty TempmgModel.ord ? '등록' : '수정'}" />

<script type="text/javaScript" defer="defer">
	
	function validation(){
		var mMasterFileId = '${TempmgModel.master_file_id }';

		if ($("input[id=title]").val() == "") {
			alert("항목명은 필수 입력사항 입니다.");
			$("input[id=title]").focus();
			return false;
		}else if($("input[name=file1]").val() == "" && mMasterFileId == ""){
			alert("첨부파일은 필수 등록사항 입니다.");
			return false;
		}

		return true;
	}

	/* 등록 */
	function fn_column_create() {
		if(!validation()){
			return;
		}
		
		doSubmit4File("TempmgModel", "<c:url value='/tempmg/TempmgColumn_Ajax_Create.do'/>", "fn_callback_column_create", "신규 등록되었습니다.");
	}
	
	function fn_callback_column_create(data) {
//		alert(data);

//		if($("input[id=param1]").val() == '') {
//			alert('1');
//		} else if($("input[id=param1]").val() == null) {
//			alert('2');
//		}
//		return false;
		
		var tpl_seq = $("input[id=param1]").val();
		
		if(registerFlag == "등록" && ( ($("input[id=tpl_seq]").val() == '') || ($("input[id=tpl_seq]").val() == null) ) ) {
//			$("input[id=tpl_seq]").val(tpl_seq);
			$("td[id=tpl_ver]").html($("input[id=param2]").val());
			$("div[id=template_download_button]").css("display", "block");

			registerFlag = "수정";
			fn_init();
		}
		
		fn_column_readList(tpl_seq);
		
		$("input[id=ord]").val("");
		$("input[id=title]").val("");
		$("input[id=title]").focus();
	}

	/* 수정 */
	function fn_column_update() {
		if(!validation()){
			return;
		}

		doSubmit4File("TempmgModel", "<c:url value='/tempmg/TempmgColumn_Ajax_Update.do'/>", "fn_callback_column_update");
	}
	
	function fn_callback_column_update(data) {
		fn_column_readList($("#tpl_seq").val());
		
		$("input[id=ord]").val("");
		$("input[id=title]").val("");
		doDivSH("empty", "column_detail_area", 0);
	}
	
	/* 삭제 */
	function fn_column_delete() {
		if (confirm("정말로 삭제하시겠습니까?")) {
			doSubmit4File("TempmgModel", "<c:url value='/tempmg/TempmgColumn_Ajax_Delete.do'/>", "fn_callback_column_delete");
		}
	}
	
	function fn_callback_column_delete(data) {
		fn_column_readList($("#tpl_seq").val());
		
		$("input[id=ord]").val("");
		$("input[id=title]").val("");
		doDivSH("empty", "column_detail_area", 0);
	}
	
</script>

<!-- 
<fieldset class="detail_fieldset">
	<legend>
		<c:out value="${registerFlag }" />
	</legend>
 -->

	<table id="column_read_tb" class="tbl_type11">
		<colgroup>
			<col width="15%"/>
			<col width="*"/>
			<col width="7%"/>
		</colgroup>
		<tr>
			<th>항목명 <span class='necessariness'>*</span></th>
			<td>
				<input type="text" id="title" name="title" maxlength="50" class="new_inp inp_w100" value="<c:out value='${TempmgModel.title }'/>" />
			</td>
			<td align="right" rowspan="2" class="mr05">
				<c:choose>
					<c:when test="${registerFlag == '수정'}">
						<span class="btn_line_blue3 mb03 fl_right"><a href="javascript:fn_column_update();">수정</a></span>
						<span class="btn_line_blue3 fl_right"><a href="javascript:fn_column_delete();">삭제</a></span>
					</c:when>
					<c:otherwise>
						<span class="btn_line_blue3 fl_right"><a href="javascript: fn_column_create();">저장</a></span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th class="td_bp_w1">첨부파일 <span class='necessariness'>*</span></th>
			<td>
				<c:choose>
					<c:when test="${registerFlag == '수정'}">
						<ui:file attachFileModel="${TempmgModel.file1}" name="file1" mode="update" size="50" />
					</c:when>
					<c:otherwise>
						<ui:file attachFileModel="${TempmgModel.file1}" name="file1" mode="create" size="50" />
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
<!-- 
</fieldset>
 -->
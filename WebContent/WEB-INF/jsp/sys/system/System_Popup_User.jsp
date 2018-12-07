<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="registerFlag" value="${empty SysModel.system_seq ? '등록' : '수정'}" />

<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/supertable/supertable.css" />
<link rel="stylesheet" type="text/css" href="/css/dynatree/ui.dynatree.css">
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link rel="stylesheet" type="text/css" href="/css/system.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery.scrollbar/jquery.mCustomScrollbar.css"/>

<link type="text/css" rel="stylesheet" href="/css/main.css" />

<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.dynatree.js"></script>

<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(function($) {
		try {
			doAuthTree();
			var treeData = eval(document.getElementById("treeScript").value + ";");
			var rootNode = $("#tree").dynatree("getRoot");
			rootNode.removeChildren(true);
			rootNode.addChild(treeData);
		} catch (e) {
			alert("시스템 오류가 발생 하여 조직 목록 조회에 실패 하였습니다.");
		}
		
		var select_system_user_id = $("#select_system_user_id").val();
		if(select_system_user_id != '' && select_system_user_id != null){
			fn_selectUserHandle();
		}
	});
	
	function fn_selectUserHandle(){
		var map = new Map();
		var selectbox = $("#sel_ids").get(0);
		for ( var y = 0; y < selectbox.length; y++) {
			map.put(selectbox.options[y].value, selectbox.options[y].text);
		}

		var selectId = $("#select_system_user_id").val();
				
		$("#select_user").empty().data('options'); 
		var keys = map.keys();
		
		for(var i = 0; i<keys.length; i++){
			$("#select_user").append("<option value='"+keys[i]+"'>"+map.get(keys[i])+"</option>");
		}

		$("#select_user").val(selectId);
	}
	
	function onActivateTree(key) {
		document.getElementById("group_id").value = key;
		document.getElementById("searchKeyword").value = "";
		fn_systemUser_readList('select');
	}
	
	function fn_systemUser_readList(mode) {
		m_mode = mode;
		if (mode == "search") {
			
			var searchKeyword = document.getElementById("searchKeyword");
			searchKeyword.value = ltrim(searchKeyword.value);
			searchKeyword.value = rtrim(searchKeyword.value);

			if (searchKeyword.value == "") {
				alert("검색 할 단어를 입력 하세요.");
				searchKeyword.focus();
				return;
			}
			document.getElementById("group_id").value = "";
		}
		doSubmit("SysModel",
				"/sys/system/SystemUser_Ajax_ReadList.do",
				"fn_callback_systemUser_readList");

	}

	function fn_callback_systemUser_readList(data) {
		$("#system_user_list_area").html(data);
	}

	// 선택된 각 담당자 추가
	function fn_add_sysUser() {
		var system_user_ids = $("input[name=system_user_ids]");
		var system_user_names = $("input[name=system_user_names]");
		var selectbox = $("#sel_ids").get(0);

		for ( var x = 0; x < system_user_ids.length; x++) {
			if (system_user_ids[x].checked) {
				var duplicate = false;

				// 이미 존재 하는지 여부 체크
				for ( var y = 0; y < selectbox.length; y++) {
					if (selectbox.options[y].value == system_user_ids[x].value) {
						duplicate = true;
					}
				}

				// 기존에 없는 담당자만 추가
				if (!duplicate) {
					selectbox.options[selectbox.length] = new Option(
							system_user_names[x].value,
							system_user_ids[x].value);
				}
			}
		}
		fn_selectUserHandle();
	}
	
	function fn_remove_sysUser() {
		var selectbox = $("#sel_ids").get(0);
		var count = selectbox.length;
		for ( var x = count - 1; x >= 0; x--) {
			if (selectbox.options[x].selected) {
				selectbox.options[x] = null;
			}
		}

		fn_selectUserHandle();
	}
	
	function fn_confirm(){
		var selectbox = $("#sel_ids").get(0);
		var sel_ids="";
		var sel_names="";
		var select_user = $("#select_user").val();
		
		for ( var y = 0; y < selectbox.length; y++) {
			if(y==0){				
				sel_ids = selectbox.options[y].value;
				sel_names = selectbox.options[y].text;
			}else{
				sel_ids = sel_ids+","+selectbox.options[y].value;
				sel_names = sel_names+","+selectbox.options[y].text;
			}
		}
		if(select_user !='' && select_user != null){
			
		}else{
			select_user= '-'
		}
		window.opener.fn_callback_select_user_popup(sel_ids, sel_names, select_user);
// 		parent.fn_callback_select_user_popup(sel_ids, sel_names, select_user);
		window.close();
	}
	
	function fn_close(){
		var sel_id = $("#sel_id").val();
		parent.$("#"+sel_id).closeDOMWindow();
	};
</script>
</head>
<body style="background-color: transparent">
<form:form commandName="SysModel" method="post" onsubmit="return false;">

<input type="hidden" id="treeScript" name="treeScript" value="${SysModel.treeScript}">
<input type="hidden" id="group_id" name="group_id">
<input type="hidden" id="sel_id" name="sel_id" value="${SysModel.sel_id}">
<input type="hidden" id="select_system_user_id" name="select_system_user_id" value="${SysModel.select_system_user_id}">

<div style="width: 100%; height: 100%;background:#fff;" id="pop_wrap">
	<h4 class="ly_header">담당자 선택</h4>
<!-- 	<a class="close_layer" href="javascript:fn_close();" style="float: right; padding-right: 15px;"> -->
<!-- 		<img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14"> -->
<!-- 	</a> -->
	<div style="margin-top: 15px;">
		<table style="width:97%;height: 400px;margin:0 auto;" class="w_100">
			<colgroup>
				<col width="30%" >
				<col width="5%" >
				<col width="65%" >
			</colgroup>
			<tr>
				<td>
					<table id="user_table" class="tbl_type11 sysUsertable_pop">
						<colgroup>
							<col width="100px;">
							<col width="*">
						</colgroup>
						<c:if test="${SysModel.sel_id == 'devsysUserVerifyId' || SysModel.sel_id == 'sysUserVerifyId' || SysModel.sel_id == 'sysUserBpId'}">
							<tr>
							  <th class="th_center th_gray">대표담당자</th>
							  <td>
								  <select id="select_user" name="select_user" style="width: 195px; padding: 0px;">
								  </select>
							  </td>
							</tr>
						</c:if>
						<tr>
							<c:choose>
								<c:when test="${SysModel.sel_id == 'devsysUserVerifyId' || SysModel.sel_id == 'sysUserVerifyId' || SysModel.sel_id == 'sysUserBpId'}">
									<td colspan="2">
									<select id="sel_ids" name="sel_ids" multiple="multiple" size="40" class="select_w_100" style="height:340px;padding:5px;">
								</c:when>
								<c:otherwise>
									<td colspan="2">
									<select id="sel_ids" name="sel_ids" multiple="multiple" size="40" class="select_w_100" style="height:382px;padding:5px;">
								</c:otherwise>
							</c:choose>
									<c:if test="${registerFlag == '수정'}">
										<c:forEach var="result" items="${SysModel.sel_ids}" varStatus="loop">
											<option value="${SysModel.sel_ids[loop.count-1]}">
												${SysModel.sel_names[loop.count-1]}
											</option>
										</c:forEach>
									</c:if>
								</select>
							</td>
						</tr>
					</table>
					
					
				</td>
				<td>
						<!--추가,삭제버튼 -->
						<div class="sys_user_button" id="system_user_button" style="width:40px;">	
							<a href="javascript:fn_add_sysUser();"><img src="/images/info_arrow_left.png" alt="추가" /></a>
							<a href="javascript:fn_remove_sysUser();"><img src="/images/info_arrow_right.png" alt="삭제" /></a>
						</div>
				
				
				</td>
				<td valign="top">
					<div class="new_sysc_div2_pop" style="height:408px;">
	
	
						<div id="system_user_title" class="new_sysc_div2_pop_title" style="line-height:46px;">담당자</div>
					
					
						<!--tree -->
						<div id="tree" class="sysc_div4 fl_left" style="height: 340px; width:49%;"></div>
					
						<!--리스트 -->
						<div class="new_sysc_div5 fl_left" style="height: 340px; width:45%;">
					
							<!--검색조건 -->
							<div class="new_sysc_div5_search">
								<div class="sysc_inp">
									<select id="searchCondition" name="searchCondition" style="vertical-align: middle;" class="fl_left">
										<c:forEach var="result" items="${SysModel.searchConditionsList}" varStatus="status">
											<option value="${result.code}"><c:out value="${result.codeName}" /></option>
										</c:forEach>
									</select> 
									<input id="searchKeyword" name="searchKeyword" type="text" class="new_inp inp_w40 fl_left ml03" style="width:38%" size="5" onkeypress="if(event.keyCode == 13) { fn_systemUser_readList('search'); return;}" />
									<span class="fl_right btn_line_blue"><a href="javascript:fn_systemUser_readList('search');">검색</a></span>
								</div>
							</div>
					
							<!--해당리스트 -->
							<div id="system_user_list_area" style="padding:0px 0px 0px 0px; width:99%; height:290px; clear:both; overflow:auto;"></div>
						
						</div>
					</div>
				</td>
			</tr>
		</table>
		
		<div id="pop_footer" style="margin:0 auto 10px;width:100px;padding-top:0;border-top:none;">
			<span class="btn_line_blue3"><a href="javascript:fn_confirm();">선 택</a></span>
		</div>
	</div>
	
	
	
</div>
</form:form>
</body>
</html>



<%--
/**
 * Ajax를 이용하여 표시되는 시스템 상세
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<script type="text/javaScript" defer="defer">
	
	function fn_initSystemUser(title) {
		try {
		//	$("#system_user_title").text(title+" 담당자 선택");
			doAuthTree();
			var treeData = eval(document.getElementById("treeScript").value + ";");
			var rootNode = $("#tree").dynatree("getRoot");
			rootNode.removeChildren(true);
			rootNode.addChild(treeData);
		} catch (e) {
			alert("시스템 오류가 발생 하여 조직 목록 조회에 실패 하였습니다.");
		}
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

	
</script>

<input type="hidden" id="treeScript" name="treeScript" value="${SysModel.treeScript}">
<input type="hidden" id="group_id" name="group_id">

<!--관련정보 띄우기 -->
<div class="new_sysc_div2_pop">	
	<div id="system_user_title" class="new_sysc_div2_pop_title">담당자</div>


	<!--tree -->
	<div id="tree" class="new_sysc_div4"></div>

	<!--리스트 -->
	<div class="new_sysc_div5">
		<!--검색조건 -->
		<div class="new_sysc_div5_search">
			<div class="sysc_inp">
				
				<select id="searchCondition" name="searchCondition" class="fl_left">
					<c:forEach var="result" items="${SysModel.searchConditionsList}" varStatus="status">
						<option value="${result.code}"><c:out value="${result.codeName}" /></option>
					</c:forEach>
				</select> 
				<input id="searchKeyword" name="searchKeyword" type="text" class="new_inp new_inp_w7 fl_left ml03" size="5" onkeypress="if(event.keyCode == 13) { fn_systemUser_readList('search'); return;}" />
				<span class="fl_left btn_line_blue"><a href="javascript:fn_systemUser_readList('search');">검색</a></span>
			</div>
		</div>

		<!--해당리스트 -->
		<div id="system_user_list_area" style="padding:0px 0px 0px 0px; width:380px; height:190px; clear:both; overflow:auto;"></div>

	</div>

</div>





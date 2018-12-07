<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="/css/dynatree/ui.dynatree.css">
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<link type="text/css" rel="stylesheet" href="/css/main.css" />

<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.dynatree.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javaScript" defer="defer">
$(document).ready(function($) {
	doCalendarName("start_date");
	doCalendarName("end_date");
	var startDate = dateReturn($("input[name='start_date']").val());
	var endDate = dateReturn($("input[name='end_date']").val());
	$("input[name='end_date']").datepicker("option", "minDate", new Date(startDate.getFullYear(), startDate.getMonth()-1, startDate.getDate()));
	$("input[name='start_date']").datepicker("option", "maxDate", new Date(endDate.getFullYear(), endDate.getMonth()-1, endDate.getDate()));
});
function fn_close(){
	parent.$("#road_map_write").closeDOMWindow();
};
function fn_road_map_update(){
	var content = $("textarea[name='content']").val();

	if(content == null || content == ""){
		alert("내용을 입력하세요.");
		return;
	}
	doSubmit2("roadMapDetailForm", "/sys/system/System_RoadMap_Ajax_Update.do", "fn_roadMapDetail_rollback");
}
function fn_roadMapDetail_rollback(){
	alert("수정 되었습니다");
	parent.fn_readSystem();
	parent.$("#road_map_write").closeDOMWindow();
}
function fn_mappingDelete(road_map_mapping_seq){
	if(confirm("삭제하시겠습니까?")){
		$("#road_map_mapping_seq").val(road_map_mapping_seq);
		doSubmit2("roadMapDetailForm", "/sys/system/System_RoadMap_Ajax_MappingDelete.do", "fn_roadMapMappingDelete_rollback");
	}
}
function fn_pkgMapDelete(){
	alert("pkg 반려 처리 시 삭제 됩니다.\n현재 시스템 관리에서는 삭제하실 수 없습니다.");
}
function fn_roadMapMappingDelete_rollback(){
		alert("삭제 되었습니다.");
		this.location.href= "/sys/system/System_Popup_RoadMapDetail.do?road_map_seq=${sysRoadMapModel.road_map_seq}";
		parent.fn_readSystem();
}
function dateReturn(stringDate){
	var secDate = stringDate.replace("-","").replace("-","");
	var year = secDate.substr(0,4);
	var month = secDate.substr(4,2);
	var day = secDate.substr(6,2);
	var date = new Date(year, month, day);
	return date;
}
</script>
<body>
<form id="roadMapDetailForm">
<div id="pop_wrap" style="width: 100%; height: 100%;">
<input type="hidden" name="road_map_seq" value="${sysRoadMapModel.road_map_seq }"/>
<input type="hidden" name="system_seq" value="${sysRoadMapModel.system_seq }"/>
<input type="hidden" name="road_map_mapping_seq" id="road_map_mapping_seq"/>
<h4 class="ly_header">로드맵 정보</h4>
	<fieldset style="width: 90%; text-align: left; padding-bottom: 10px;">
			<legend>${sysRoadMapModel.code_desc }</legend>
			
			<table class="tbl_type">
					<tr>
						<th width="25%">날짜</th>
						<td><input type="text" name="start_date" readonly="readonly" value="${sysRoadMapModel.start_date }" style="width: 90px;"/> ~ <input type="text" name="end_date" readonly="readonly" value="${sysRoadMapModel.end_date }" style="width: 90px;"/></td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea cols="35" rows="10" name="content" style="width: 295px;">${sysRoadMapModel.content }</textarea></td>
					</tr>
			</table>
			<br/><br/>
				<h4>Mapping 목록</h4>
			<hr/>
			<div style="overflow-x: hidden; height: 120px; width: 100%;">
			<table class="tbl_type">
				<c:choose>
				<c:when test="${empty sysRoadMapMappingList && empty sysPkgRoadMapList}">
					<tr>
						<td style="text-align: center; width: 420px;"><h4>Mapping된 목록이 없습니다.</h4></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:if test = "${not empty sysRoadMapMappingList}">
						<tr>
							<th colspan="4">신규/보완/개선 Mapping</th>
						</tr>
						<tr>
							<th width="200px;">제목</th>
							<th>등록자</th>
							<th>등록일</th>
							<th>삭제</th>
						</tr>
						<c:forEach items="${sysRoadMapMappingList}" var="list" varStatus="status">
							<tr>
								<td style="text-align: left;"><h4>${list.title }</h4></td>
								<td style="text-align: center;"><h4>${list.hname }</h4></td>
								<td style="text-align: center;"><h4>${list.reg_date }</h4></td>
								<td style="text-align: center;"><img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14" onclick="fn_mappingDelete('${list.road_map_mapping_seq}')" style="cursor: pointer;"></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test = "${not empty sysPkgRoadMapList}">
						<tr>
							<th colspan="4">PKG 검증 Mapping</th>
						</tr>
						<tr>
							<th width="200px;">제목</th>
							<th>등록자</th>
							<th>등록일</th>
							<th>삭제</th>
						</tr>
						<c:forEach items="${sysPkgRoadMapList}" var="list2" varStatus="status">
							<tr>
								<td style="text-align: left;"><h4>${list2.title }</h4></td>
								<td style="text-align: center;"><h4>${list2.hname }</h4></td>
								<td style="text-align: center;"><h4>${list2.reg_date }</h4></td>
								<td style="text-align: center;"><img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14" onclick="fn_pkgMapDelete()" style="cursor: pointer;"></td>
							</tr>
						</c:forEach>
					</c:if>
				</c:otherwise>
				</c:choose>
			</table>
			</div>
		</fieldset>
		<a class="close_layer" href="javascript:fn_close();"> <img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14"></a>
		<br/><br/>
		<div align="center">
			<span><a href="javascript:fn_road_map_update();"><img src="/images/pop_btn_save.gif" alt="저장" /></a></span> <span><a href="javascript:fn_close();"><img src="/images/pop_btn_close.gif" alt="목록" /></a></span>
		</div>
</div>
</form>
</body>
</html>

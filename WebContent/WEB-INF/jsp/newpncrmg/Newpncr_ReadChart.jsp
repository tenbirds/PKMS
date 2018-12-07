<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="/js/pkgmg/jsgantt.js"></script>
<link rel="stylesheet" type="text/css" href="/css/jsgantt.css"/>
<script type="text/javascript" src="/js/jquery/jquery.qtip-1.0.0-rc3.min.js"></script>
<script type="text/javaScript">
	
	doModalPopup("img[name=relation_system_user_popup]", 1, "click", 783, 576, "/usermg/user/SktUser_Popup_Read.do");

	var g = new JSGantt.GanttChart('g',document.getElementById('GanttCharthIV'), 'day');
	if (g) {
		$("#chartTitle").html("<br/><span class='necessariness'>*</span>"+'${readListChart[0].name}' + " 일정");
		  '<c:forEach items="${readListChart}" var="list" varStatus="status">';
			  g.AddTaskItem(new JSGantt.TaskItem('${list.road_map_seq}', '${list.code_desc}','${list.start_date}','${list.end_date}', '','', 'detailShowHide(\'${list.road_map_seq}\')','','',0,0,'',0,'','${list.mapping}',

					  new Array(
					  			<c:forEach items="${list.list}" var="list2" varStatus="status2">
					  			new JSGantt.DelayItem('${list2.road_map_seq}','${list2.code_desc}','${list2.start_date}','${list2.end_date}','','detailShowHide(\'${list2.road_map_seq}\')','','${list2.mapping}')<c:if test="${!status2.last}">,</c:if>
					  			</c:forEach>	
					  	)));
					  
			  
			  g.Draw();	
			  g.DrawDependencies();
			  $("DIV.scroll2").css("width", "730px");
		  '</c:forEach>';
	}else{
		  alert("not defined");
	}
	//주 표시
	JSGantt.changeFormat("week", g);
	
	'<c:forEach items="${readListSubChart}" var="list" varStatus="status">';
		
		s = new JSGantt.GanttChart('g',document.getElementById('subList'+'${status.index}'), 'day');
		if (s) {
			  '<c:forEach items="${list}" var="list2" varStatus="status2">';
			  '<c:if test="${status2.first}">';
				  $("#subList${status.index}").before("<br/><legend><span class='necessariness'>*</span>"+' ${list2.name}'+" 일정</legend>");
			  '</c:if>'
				  s.AddTaskItem(new JSGantt.TaskItem('${list2.road_map_seq}', '${list2.code_desc}','${list2.start_date}','${list2.end_date}', '','', 'detailShowHide(\'${list2.road_map_seq}\')','','',0,0,'',0,'','${list2.mapping}',
						  new Array(
						  			<c:forEach items="${list2.list}" var="list3" varStatus="status3">
						  			new JSGantt.DelayItem('${list3.road_map_seq}','${list3.code_desc}','${list3.start_date}','${list3.end_date}','','detailShowHide(\'${list3.road_map_seq}\')','','${list3.mapping}')<c:if test="${!status3.last}">,</c:if>
						  			</c:forEach>	
						  	)));
				  s.Draw();	
				  s.DrawDependencies();
			  '</c:forEach>';
		}else{
			  alert("not defined");
		}
		$("DIV.scroll2").css("width", "730px");
		$("#subList${status.index}").after("<br/>");
	'</c:forEach>';
	
	function fn_index_select(i){
		$("#statusIndex").val(i);
	}
	function fn_sktuser_popup_callback(hname, empno){
		$("#hname"+$("#statusIndex").val()).val(empno);
		$("#empno"+$("#statusIndex").val()).val(hname);
	}
	
	$(document).ready(function($) {
		$(".g_task").each(function(i,v){
			var aa = $(this).attr("id").replace("taskbar_","");
		});
		
		
		'<c:forEach items="${readListChart}" var="list" varStatus="status">';
			$("#taskbar_${list.road_map_seq}").qtip({
				content : "<p style='line-height:1.6em'>"
						+ '${list.tooltip}'
						+ "</p>",
				position : {
					target : 'mouse'
				},
				style : {
					name : 'cream',
					tip : true
				}
			});
			'<c:forEach items="${list.list}" var="list2" varStatus="status2">';
				$("#taskbar_${list2.road_map_seq}").qtip({
					content : "<p style='line-height:1.6em'>"
							+ '${list2.tooltip}'
							+ "</p>",
					position : {
						target : 'mouse'
					},
					style : {
						name : 'cream',
						tip : true
					}
				});
			'</c:forEach>';
		'</c:forEach>';
		
		
		'<c:forEach items="${readListSubChart}" var="list" varStatus="status">';
			'<c:forEach items="${list}" var="list2" varStatus="status2">';
				$("#taskbar_${list2.road_map_seq}").qtip({
					content : "<p style='line-height:1.6em'>"
							+ '${list2.tooltip}'
							+ "</p>",
					position : {
						target : 'mouse'
					},
					style : {
						name : 'cream',
						tip : true
					}
				});
				'<c:forEach items="${list2.list}" var="list3" varStatus="status3">'
						$("#taskbar_${list3.road_map_seq}").qtip({
							content : "<p style='line-height:1.6em'>"
									+ '${list3.tooltip}'
									+ "</p>",
							position : {
								target : 'mouse'
							},
							style : {
								name : 'cream',
								tip : true
							}
						});
				'</c:forEach>';	
			'</c:forEach>';
		'</c:forEach>';
	});
	
	
	function detailShowHide(road_map_seq){
		if($("#subListDetail"+road_map_seq).css("display")=="none"){
			$("#subListDetail"+road_map_seq).show();	
		}else{
			$("#subListDetail"+road_map_seq).hide();
		}
	}
</script>
<input type="hidden" id="statusIndex"/>
<legend id="chartTitle"></legend>
<div style="position:relative; width:300px;" class="gantt" id="GanttCharthIV"></div>
<c:forEach items="${readListChart}" var="list" varStatus="status">
	<div id="subListDetail${list.road_map_seq }" style="display: none;">
	<br/>
	<legend><span class='necessariness'>*</span>로드맵 맵핑</legend>
	<input type="hidden" name="road_map_seqs" value="${list.road_map_seq }"/>
			<table class="tbl_type1">
				<tr>
					<th style="width: 100px;">날짜</th>
					<td style="text-align: left; padding-left: 5px; width: 470px;"><input type="text" value="${list.start_date }" readonly="readonly"/> ~ <input type="text" value="${list.end_date }" readonly="readonly"/></td>
<!-- 					<th>매핑여부</th> -->
<%-- 					<td style="text-align: left; padding-left: 5px;"><input type="checkbox" value="${list.road_map_seq }" name="check_seq" <c:if test="${list.mapping eq 'Y'}">checked="checked"</c:if>/>매핑 하겠습니다.</td> --%>
				</tr>
				<tr>
					<th>내용</th>
					<td style="text-align: left; padding-left: 5px;" colspan="3"><textarea rows="10" style="width: 100%;" readonly="readonly">${list.content }</textarea></td>
				</tr>
			</table>
	</div>
	<c:forEach items="${list.list}" var="list2" varStatus="status2">
		<div id="subListDetail${list2.road_map_seq }" style="display: none;">
		<br/>
		<legend><span class='necessariness'>*</span>로드맵 맵핑</legend>
			<input type="hidden" name="road_map_seqs" value="${list2.road_map_seq }"/>
			<table class="tbl_type1">
				<tr>
					<th style="width: 100px;">날짜</th>
					<td style="text-align: left; padding-left: 5px; width: 470px;""><input type="text" value="${list2.start_date }" readonly="readonly"/> ~ <input type="text" value="${list2.end_date }" readonly="readonly"/></td>
<!-- 					<th>매핑여부</th> -->
<%-- 					<td style="text-align: left; padding-left: 5px;"><input type="checkbox" value="${list2.road_map_seq }" name="check_seq" <c:if test="${list2.mapping eq 'Y'}">checked="checked"</c:if>/>매핑 하겠습니다.</td> --%>
				</tr>
				<tr>
					<th>내용</th>
					<td style="text-align: left; padding-left: 5px;" colspan="3"><textarea rows="10" style="width: 100%;" readonly="readonly">${list2.content }</textarea></td>
				</tr>
			</table>
		</div>
	</c:forEach>
</c:forEach>
<c:forEach items="${readListSubChart}" varStatus="status" var="list">
	<div id="subList${status.index }"></div>
	<c:forEach items="${list}" var="list2" varStatus="status2">
		<div id="subListDetail${list2.road_map_seq }" style="display: none;">
		<br/>
		<legend><span class='necessariness'>*</span>로드맵 맵핑</legend>
		<input type="hidden" name="road_map_seqs" value="${list2.road_map_seq }"/>
				<table class="tbl_type1">
					<tr>
						<th style="width: 100px;">날짜</th>
						<td style="text-align: left; padding-left: 5px; width: 470px;"><input type="text" value="${list2.start_date }" readonly="readonly"/> ~ <input type="text" value="${list2.end_date }" readonly="readonly"/></td>
<!-- 						<th>매핑여부</th> -->
<%-- 						<td style="text-align: left; padding-left: 5px;"><input type="checkbox" value="${list2.road_map_seq }" name="check_seq" <c:if test="${list2.mapping eq 'Y'}">checked="checked"</c:if>/>매핑 하겠습니다.</td> --%>
					</tr>
					<tr>
						<th>내용</th>
						<td style="text-align: left; padding-left: 5px;" colspan="3"><textarea rows="10" style="width: 100%;" readonly="readonly">${list2.content }</textarea></td>
					</tr>
				</table>
		</div>
		
		
		<c:forEach items="${list2.list}" var="list3" varStatus="status3">
			<div id="subListDetail${list3.road_map_seq }" style="display: none;">
			<br/>
			<legend><span class='necessariness'>*</span>로드맵 맵핑</legend>
			<input type="hidden" name="road_map_seqs" value="${list3.road_map_seq }"/>
					<table class="tbl_type1">
						<tr>
							<th style="width: 100px;">날짜</th>
							<td style="text-align: left; padding-left: 5px; width: 470px;"><input type="text" value="${list3.start_date }" readonly="readonly"/> ~ <input type="text" value="${list3.end_date }" readonly="readonly"/></td>
<!-- 							<th>매핑여부</th> -->
<%-- 							<td style="text-align: left; padding-left: 5px;"><input type="checkbox" value="${list3.road_map_seq }" name="check_seq" <c:if test="${list3.mapping eq 'Y'}">checked="checked"</c:if>/>매핑 하겠습니다.</td> --%>
						</tr>
						<tr>
							<th>내용</th>
							<td style="text-align: left; padding-left: 5px;" colspan="3"><textarea rows="10" style="width: 100%;" readonly="readonly">${list3.content }</textarea></td>
						</tr>
					</table>
			</div>
		</c:forEach>
	</c:forEach>
</c:forEach>
	
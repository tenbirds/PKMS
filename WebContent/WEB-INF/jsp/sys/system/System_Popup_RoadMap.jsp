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
	
	function rowadd(){
		var html = "";
		
		
		$("#addList").html("<caption>세부사항 일정</caption>");
		$("input[name='checkList']:checked").each(function(p, i){
			var text = "";
		
			if(i.value == "01"){
				text = "시스템 설치";
			}else if(i.value == "02"){
				text = "시스템인수시험";
			}else if(i.value == "03"){
				text = "망작업";
			}
// 			else if(i.value == "04"){
// 				text = "패키지 개발";
// 			}
			else if(i.value == "05"){
				text = "연동시험";
			}else if(i.value == "06"){
				text = "개발인수 시험";
			}
// 			else if(i.value == "07"){
// 				text = "패키지 검증";
// 			}else if(i.value == "08"){
// 				text = "패키지 적용";
// 			}
			else if(i.value == "09"){
				text = "성능시험";
			}else if(i.value == "10"){
				text = "서비스 오픈";
			}
			
			
			html += "<tr>";
			html += "<th rowspan='2' style='width:160px;'>"+text+"<input type='hidden' name='codes' value='"+i.value+"'/></th>";
			html += "<th style='cursor:pointer;' onclick='add(this,\""+i.value+"\", \""+text+"\");' rowspan='2'>+</th>";
			html += "<th style='width:100px;' >기간</th>";
			html += "<td colspan='4' style='text-align: left; padding-left: 15px;'><input type='text' name='start_dates'/> ~ <input type='text' name='end_dates'/></td>";
			html += "<td></td>";
			html += "<td></td>";
			html += "</tr>";
			html += "<tr>";
// 			html += "<th style='cursor:pointer;' onclick='remove();'>-</th>"
			html += "<th>내용</th>";
			html += "<td></td>";
			html += "<td colspan='3' style='text-align: left; padding-left: 15px;'><textarea style='width: 90%;' name='contents' rows='5'/></textarea></td>";
			html += "<td></td>";
			html += "</tr>";
		});

		$("#addList").append(html);

		$("input[name=start_dates]").each(function(p, i){
			setDatePicker($('input[name="start_dates"]:eq('+p+')'), '<=', $('input[name="end_dates"]:eq('+p+')'));
			setDatePicker($('input[name="end_dates"]:eq('+p+')'), '>=', $('input[name="start_dates"]:eq('+p+')'));
		});
	}
	function add(ts, val, text){
		var html = "";
		html += "<tr>";
		html += "<th rowspan='2'>"+text+"<input type='hidden' name='codes' value='"+val+"'/></th>";
		html += "<th style='cursor:pointer;' onclick='add(this,\""+val+"\",\""+text+"\");'>+</th>";
		html += "<th style='width:100px;' >기간</th>";
		html += "<td colspan='4' style='text-align: left; padding-left: 15px;'><input type='text' name='start_dates'/> ~ <input type='text' name='end_dates'/></td>";
		html += "<td></td>";
		html += "<td></td>";
		html += "</tr>";
		html += "<tr>";
		html += "<th style='cursor:pointer;' onclick='remove(this);'>-</th>";
		html += "<th>내용</th>";
		html += "<td></td>";
		html += "<td colspan='3' style='text-align: left; padding-left: 15px;'><textarea style='width: 90%;' name='contents' rows='5'/></textarea></td>";
		html += "<td></td>";
		html += "</tr>";
		
		var index = $(ts).parent().index();
		index = index + 1;
		$("#addList  tr:eq("+index+")").after(html);
// 		doCalendarName("start_dates");
// 		doCalendarName("end_dates");
		
		$("input[name=start_dates]").each(function(p, i){
			setDatePicker($('input[name="start_dates"]:eq('+p+')'), '<=', $('input[name="end_dates"]:eq('+p+')'));
			setDatePicker($('input[name="end_dates"]:eq('+p+')'), '>=', $('input[name="start_dates"]:eq('+p+')'));
		});
	}
	function remove(ts){
		var index = $(ts).parent().index();
		$("#addList  tr:eq("+index+")").remove();		
		index = index - 1;
		$("#addList  tr:eq("+index+")").remove();
		
	}
	function fn_roadMap_create(){
		if(!fn_Validation())
			return;
		
		doSubmit2("roadMapForm", "/sys/system/System_RoadMap_Ajax_Create.do", "fn_roadMap_rollback");
	}
	
	function fn_Validation(){
		var codes_cnt = 0;
		codes_cnt = $("input[name=codes]").length;
		
		var codes = $("input[name=codes]");
		var start_dates = $("input[name=start_dates]");
		var end_dates = $("input[name=end_dates]");
		var contents = $("textarea[name=contents]");
		var check_cnt = 0;
		
		for(var i = 0; i < codes_cnt; i++) {
			if(codes.eq(i).val() == "04" || codes.eq(i).val() == "07" || codes.eq(i).val() == "08"){
				check_cnt++;
			}
			 
			var str = contents.eq(i).val().replace(/(^\s*)|(\s*$)/, "");
			
			if(start_dates.eq(i).val() =="" || end_dates.eq(i).val() =="" || str ==""){
				alert("세부사항 중 입력하지 않은 항목이 존재합니다.");
				return false;
			}
		}
		
		if(check_cnt > 0){
			alert("패키지 '개발/검증/적용'은 PKG검증/일정에서 검증과정 시 '개발/검증/적용' 단계에서 자동적용됩니다.");
			return false;
		}
		
		return true;
	}
	
	function fn_roadMap_rollback(){
		alert("저장 되었습니다.");
		parent.fn_readSystem();
		parent.$("#road_map_write").closeDOMWindow();
	}
	function fn_popup_close(){
		parent.$("#road_map_write").closeDOMWindow();	
	}
</script>
</head>

<body style="background-color: transparent">
<form id="roadMapForm" name="roadMapForm">
<div style="width: 100%; height: 100%;" id="pop_wrap">
<input type="hidden" value="${SysModel.system_seq }" name="system_seq" id="system_seq"/>
	<h4 class="ly_header">로드맵 작성</h4>
	<a class="close_layer" href="javascript:fn_popup_close();" style="float: right; padding-right: 15px;"> <img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14"></a>
	<br/>
		<div style="width: 95%; text-align: right;">		
			<span><a href="javascript:fn_roadMap_create()"><img src="/images/btn_save.gif" alt="저장" /></a></span>
		</div>
	<div id="pop_content" style="vertical-align: top; border: 1px solid #dcdcdc; width: 880px; margin-left: 10px;">
	<div align = "center">
		<fieldset style="width: 95%; text-align: left; padding-bottom: 10px;">
			<legend>로드맵 작성 팝업 창</legend>
			
			<table class="tbl_type">
					<caption>기본정보</caption>
					<tr>
						<th rowspan="2" style="width: 100px;"> 항목&nbsp;<span class='necessariness'>*</span></th>
						<td align="left"><input type="checkbox" name="checkList" value="01" />시스템 설치</td>
						<td align="left"><input type="checkbox" name="checkList" value="02"/>시스템인수시험</td>
						<td align="left"><input type="checkbox" name="checkList" value="03"/>망작업</td>
<!-- 						<td align="left"><input type="checkbox" name="checkList" value="04"/>패키지 개발</td> -->
						<td align="left"><input type="checkbox" name="checkList" value="05"/>연동시험</td>
						<td></td>
						<td rowspan="2">
						
<!-- 						<input type="button" value="항목 추가" onclick="rowadd();"/> -->
							<span>
								<img src="/images/btn_plus.gif" alt="항목 추가" style="cursor: pointer;" onclick="rowadd();"/>
							</span>
<!-- 							<img alt="항목 추가" src="/images/btn_plus.gif" width="15" height="14" onclick="rowadd();"> -->
							
						</td>
					</tr>
					<tr>
						<td align="left"><input type="checkbox" name="checkList" value="06"/>개발인수시험</td>
<!-- 						<td align="left"><input type="checkbox" name="checkList" value="07"/>패키지 검증</td> -->
<!-- 						<td align="left"><input type="checkbox" name="checkList" value="08"/>패키지 적용</td> -->
						<td align="left"><input type="checkbox" name="checkList" value="09"/>성능시험</td>
						<td align="left"><input type="checkbox" name="checkList" value="10"/>서비스오픈</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
			</table>
			<br/>
			<div style="overflow-x:hidden; height: 480px;">
				<table class="tbl_type" id="addList">
						<caption>세부사항 일정</caption>
				</table>
			</div>
		</fieldset>
		
		</div>
	</div>
	</div>
</form>
</body>
</html>

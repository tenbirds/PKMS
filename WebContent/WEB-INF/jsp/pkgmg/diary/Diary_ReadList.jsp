<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<link type="text/css" rel="stylesheet" href="/css/fullcalendar/fullcalendar.css" />
<link type="text/css" rel="stylesheet" href="/css/main.css" />

<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script>
<script type='text/javascript' src='/js/jquery/fullcalendar/fullcalendar.min.js'></script>
<script type="text/javascript" src="/js/jquery/jquery.qtip-1.0.0-rc3.min.js"></script>
<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>

<script type="text/javaScript" defer="defer">

	var diaryId = "#diary";

	$(document).ready(function($) {
		// 월별일정(Diary) Init
		var diaryData = eval($("input[id=diaryScript]").val() + ";");
		doDiaryPage(diaryData);
		
	});

	// Diary Init
	function doDiaryPage(diaryData) {
		$(diaryId).css("font-size", "12px");
		$(diaryId).css("font-weight", "normal");
		$(diaryId).css("width", "100%", "min-width", "900px");

		$(diaryId).fullCalendar({
			header : {
				left : '',
				center : '',
				right : 'prev, title, next, today' // month,agendaWeek,agendaDay
			},
			weekMode : 'variable',
			editable : false,
			events : diaryData,
			eventRender : function(event, element) {
				element.qtip({
					content : "<p style='line-height:1.6em'>"+event.description.replace(/#/g, '<br />')+"</p>",
					position: { 
						target: 'mouse'
					},
					style: { name: 'cream', tip: true }
				});
			},
			eventClick : function(event) {
				if (event.id) {
					fn_pkg_read('read',event.id, "DiaryModel");
					return false;
				}
			}
		});
	}
	
	//Diary reload
	function fn_diary_reload() {
		$("#sessionCondition").val(false);
		doSubmit("DiaryModel", "/pkgmg/diary/Diary_Ajax_ReadList.do",
				"fn_callback_diary_reload");
	}

	//Diary reload callback
	function fn_callback_diary_reload(data) {
		try {
			var result = $("input[id=param1]").val();
			var diaryData = eval(result + ";");
			$(diaryId).fullCalendar('removeEvents');
			$(diaryId).fullCalendar('addEventSource', diaryData);
			$(diaryId).fullCalendar('rerenderEvents');
		} catch (e) {
			alert("시스템 오류가 발생 하여 일정 목록 조회에 실패 하였습니다.");
		}
	}
	
</script>

</head>
<body>
	<form:form commandName="DiaryModel" method="post" onsubmit="return false;">
		<form:hidden path="diaryScript" htmlEscape="false" />
		<form:hidden path="sessionCondition" />
		<form:hidden path="retUrl" />
		<form:hidden path="pkg_seq" />
		<form:hidden path="excel_date" />
		
		<!--탭, 테이블, 페이지 DIV 시작 -->
		<div class="con_Div2">
			<!--탭 div 시작 -->
			<div class="con_Div6">
				<ul class="m_tab">
					<li class="m_tab1"><a href="/pkgmg/pkg/Pkg_ReadList.do"></a></li>
					<li class="m_tab2"><a href="/pkgmg/schedule/Schedule_ReadList.do"></a></li>
					<li class="m_tab3_on"></li>
				</ul>
			</div>
			<!--조회, 테이블, 페이지수 div 시작 -->
			<div class="new_con_Div71">

				<!-- m_tab3 월별일정 -->
				<div id="m_tab3" class="mt30">
					
					<div style="position:absolute;top:37px;left:10px;">
						<ul class="main_select fl_left mt03" style="width: 310px;">
							<li class="select_tex1">담당</li>
							<li class="select_line"></li>
							<li>
								<form:radiobuttons onclick="javascript:fn_diary_reload();" path="userDiaryCondition" items="${DiaryModel.userDiaryConditionList}" itemLabel="codeName" itemValue="code" />
							</li>
						</ul>
						<ul class="main_select fl_left mt03" style="width:230px;">
							<li class="select_tex1">일정</li>
							<li class="select_line"></li>
							<li>
								<form:radiobuttons onclick="javascript:fn_diary_reload();" path="diaryItem" items="${DiaryModel.diaryItemList}" itemLabel="codeName" itemValue="code" style="line-height:26px;" />
							</li>
						</ul>
						<div class="fl_left">
							<span class="btn_line_blue2 mg00"><a href="javascript:fn_diary_excel_download();">엑셀 다운로드</a></span>
						</div>
					</div>
					
					<div id='diary'></div>
				</div>
			</div>
			<!--조회, 테이블, 페이지수 div 끝 -->
		</div>
		<!--탭, 테이블, 페이지 DIV 시작 -->
	</form:form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PKMS (패키지 관리 시스템)</title>

<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<link type="text/css" rel="stylesheet" href="/css/fullcalendar/fullcalendar.css" />
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/main.css" />

<script type='text/javascript' src='/js/jquery/fullcalendar/fullcalendar.min.js'></script>
<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.qtip-1.0.0-rc3.min.js"></script>
<script type='text/javascript' src='/js/pkgmg/pkgmg.common.js'></script>

<script type="text/javaScript">
	var diaryId = "#diary";

	var sessionUserId = '<c:out value="${MainModel.session_user_id}" />';

	$(document).ready(function($) {
		var diaryData = eval($("#diaryScript").val() + ";");
		doDiaryMain(diaryData);

		var mtop = 210;
		var mleft = 315;
		$('input:hidden[name=popup_seq]').map(function() {
			var id = "layerPop" + this.value;
			//setCookie(sessionUserId + id, "ok", 1);
			if (notice_getCookie(sessionUserId + id) != id + "ok") {
				$("#" + id).css("margin-top", mtop + "px");
				$("#" + id).css("margin-left", mleft + "px");
				doDivSH("show", id, 0);
				mtop += 10;
				mleft += 10;
			} else {
				$("#" + id).remove();
			}
			return this.value;
		}).get();
		
		$('a[title]').qtip();
		
// 		manual_popup();
// 		work_popup('Main');
	});

	// Diary Init
	function doDiaryMain(diaryData) {
		$(diaryId).css("font-size", "10px");
		$(diaryId).css("font-weight", "normal");
		$(diaryId).css("width", "893px");
		$(diaryId).css("height", "520px");

		$(diaryId).fullCalendar(
				{
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
							content : "<p style='line-height:1.6em'>"
									+ event.description.replace(/#/g, '<br />')
									+ "</p>",
							position : {
								target : 'mouse'
							},
							style : {
								name : 'cream',
								tip : true
							}
						});
					},
					eventClick : function(event) {
						if (event.id) {
							fn_pkg_read('read', event.id, 'MainModel');
							return false;
						}
					}
				});
	}

	//Diary reload
	function fn_diary_reload() {
		$("#sessionCondition").val(false);
		doSubmit("MainModel", "/pkgmg/diary/Diary_Ajax_ReadList.do",
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

	function fn_read_newpncr(id) {
		$("#retUrl").val("/newpncrmg/Newpncr_View");
		$("#new_pn_cr_seq").val(id);

		var form = document.getElementById('MainModel');
		form.target = "_self";
		form.action = "<c:url value='/newpncrmg/Newpncr_Read.do'/>";
		form.submit();
	}

	function fn_read_notice(id) {
		$("#retUrl").val("/board/notice/Notice_View");
		$("#notice_seq").val(id);

		var form = document.getElementById('MainModel');
		form.target = "_self";
		form.action = "<c:url value='/board/notice/Notice_Read.do'/>";
		form.submit();
	}

	function notice_getCookie(name) {
		var nameOfCookie = name + "=";
		var x = 0;
		while (x <= document.cookie.length) {
			var y = (x + nameOfCookie.length);
			if (document.cookie.substring(x, y) == nameOfCookie) {
				if ((endOfCookie = document.cookie.indexOf(";", y)) == -1)
					endOfCookie = document.cookie.length;
				return unescape(document.cookie.substring(y, endOfCookie));
			}
			x = document.cookie.indexOf(" ", x) + 1;
			if (x == 0)
				break;
		}
		return "";
	}

	function setCookie(name, value, expiredays) {
		var todayDate = new Date();
		todayDate.setDate(todayDate.getDate() + expiredays);
		document.cookie = name + "=" + escape(value) + "; path=/; expires="
				+ todayDate.toGMTString() + ";"
	}

	function closeLayer(id) {
		//doDivSH("hide", id, 0);
		$("#" + id).remove();
	}

	function closeLayerCookie(id) {
		//doDivSH("hide", id, 0);
		$("#" + id).remove();
		setCookie(sessionUserId + id, id + "ok", 1);
	}
	
	function getCookie(name) {
		 var Found = false; 
		 var start, end;
		 var i = 0;
		  
		 while(i <= document.cookie.length) { 
		 	start = i;
		 	end = start + name.length;
		  
			 if(document.cookie.substring(start, end) == name) { 
			 	Found = true;
			 	break;
			 } 
		 	i++;
		} 
		if(Found == true) { 
			start = end + 1; 
			end = document.cookie.indexOf(";", start);
			if(end < start){
				end = document.cookie.length;
			}
			return document.cookie.substring(start, end); 
		} 
		 return "" 
	}
	
	function system_m(group1_seq, group2_seq, group3_seq, system_seq) {
		$("#group1_seq").val(group1_seq);
		$("#group2_seq").val(group2_seq);
		$("#group3_seq").val(group3_seq);
		$("#system_seq").val(system_seq);
		
		var form = document.getElementById("MainModel");
		form.target = "_self";
		form.action = "<c:url value='/sys/group1/Group1_ReadList.do'/>";
		form.submit();
	}
</script>

</head>

<body>

	<c:forEach var="popup" items="${NoticePopupModelList}" varStatus="status">
		<div id="layerPop${popup.notice_seq}" class="notice_wrap">
			<h3 class="fl_left">공지사항</h3>
			<div class="fl_right btn_close">
				<a href="javascript:closeLayer('layerPop${popup.notice_seq}');"><img alt="닫기" src="/images/pop_btn_close2.gif" /></a>
			</div>
			<br /> <br />
			<div class="notice_tit">
				<c:out value="${popup.title}" />
			</div>
			<div class="notice_con">
				<pre><c:out value="${popup.content}" /></pre>
			</div>
			<label for="popup_check${popup.notice_seq}">
				<input type="checkbox" name="popup_check${popup.notice_seq}" id="popup_check${popup.notice_seq}" onclick="javascript:closeLayerCookie('layerPop${popup.notice_seq}');" />
				오늘은 이 창을 다시 열지않음
			</label>
		</div>
		<input type="hidden" name="popup_seq" value="${popup.notice_seq}" />
	</c:forEach>

	
		
		<!--PKG현황 -->
		<div class="inner">
			<div class="tit">PKG 현황</div>
			<div class="con_width35 btn_line_blue2 fl_right fl_wrap">
				<span class="fl_right"><a href="/manual.mht" target ="_blank">매뉴얼</a></span>
<!-- 				<span class="fl_right mr03"><a href="javascript:fn_pkg_read('read', '', 'MainModel');">신규</a></span> -->
			</div>

			<div class="pkg_tb2" style="height: 350px; clear: both; overflow-y: auto; overflow-x: hidden;">
				<!-- 
			<div class="pkg_tb2_sc">
			 -->
				<table cellspacing="0" cellpadding="0" class="con_width100">
					
					<c:choose>
						<c:when test="${fn:length(PkgModelList) == 0}">
							<tr>
								<th colspan="2" style="width: 100%; text-align: center; top-margin: 5px; height: 40px">진행중인 PKG 검증 현황이 없습니다</th>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="result" items="${PkgModelList}" varStatus="status">
								<!-- 과금처리 반려는 제외 -->
								<c:if test="${result.pe_status != '9'}">
								<tr>
									<c:choose>
										<c:when test="${result.pkg_user_status == 'R'}">
											<th>
												<p>
													<a href="javascript:fn_pkg_read('read', '${result.pkg_seq}', 'MainModel')" title="시스템:${result.system_name }<br/>제목:${result.title }<br/>개발검증대표:${result.dev_system_user_name }<br/>상용검증대표:${result.system_user_name }">
			<%-- 											<c:out value="${fn:substring(result.title,0,25)}" /> --%>
														<c:out value="${result.title}" /> 
			<%-- 											<c:out value="${fn:length(result.title) > 25 ? '..' : ''}" /> --%>
													</a>
												</p>
											</th>
										</c:when>
										<c:otherwise>
											<th class="${status.count % 2 == 0 ? 'even_number' : '' }">
												<p>
													<a href="javascript:fn_pkg_read('read', '${result.pkg_seq}', 'MainModel')" title="시스템:${result.system_name }<br/>제목:${result.title }<br/>개발검증대표:${result.dev_system_user_name }<br/>상용검증대표:${result.system_user_name }">
		<%-- 											<c:out value="${fn:substring(result.title,0,25)}" /> --%>
													<c:out value="${result.title}" /> 
		<%-- 											<c:out value="${fn:length(result.title) > 25 ? '..' : ''}" /> --%>
													</a>
												</p>
											</th>
										</c:otherwise>
									</c:choose>
									<td class="${status.count % 2 == 0 ? 'even_number' : '' }">
										<ul class="pkg_flow">
											<c:choose>
												<c:when test="${result.dev_yn == 'D'}">
													<c:forEach begin="2" end="6" var="i">
														<c:choose>
															<c:when test="${result.status_dev == 1}">
																<c:choose>
																	<c:when test="${(result.status_dev + 2) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${(result.status_dev + 2) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${result.status_dev > 20 && result.status_dev < 24}">
																<c:choose>
																	<c:when test="${(result.status_dev - 17) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${ (result.status_dev - 17) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">과금처리중</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">과금접수</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:forEach>
													<c:forEach begin="7" end="15" var="i">
														<c:choose>
															<c:when test="${result.status < 3}">
																<c:choose>
																	<c:when test="${(result.status + 6) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${(result.status + 6) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${result.status == 26}">
																<c:choose>
																	<c:when test="${(result.status - 17) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${(result.status - 17) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${(result.status + 7) == i}">
																		<c:choose>
																			<c:when test="${i == 15}">
																				<li>
																					<div class="main_flow_on_last">
																						<p>
																							<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																						</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on">
																						<p>
																							<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																						</p>
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:when test="${(result.status + 7) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:when test="${result.dev_yn == 'Y'}">
													<c:forEach begin="2" end="15" var="i">
														<c:choose>
															<c:when test="${result.status > 20 && result.status < 25}">
																<c:choose>
																	<c:when test="${(result.status - 17) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${ (result.status - 17) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						<p>
																							과금처리중
																						</p>
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${result.status == 1}">
																<c:choose>
																	<c:when test="${(result.status + 2) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${(result.status + 2) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${result.status == 2}">
																<c:choose>
																	<c:when test="${(result.status + 6) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${(result.status + 6) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${result.status == 26}">
																<c:choose>
																	<c:when test="${(result.status - 18) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${(result.status - 18) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${(result.status + 7) == i}">
																		<c:choose>
																			<c:when test="${i == 15}">
																				<li>
																					<div class="main_flow_on_last">
																						<p>
																							<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																						</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on">
																						<p>
																							<c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out>
																						</p>
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:when test="${(result.status + 7) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList_dev[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<c:forEach begin="2" end="11" var="i">
														<c:choose>
															<c:when test="${result.status < 3}">
																<c:choose>
																	<c:when test="${(result.status + 2) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${(result.status + 2) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:when test="${result.status == 26}">
																<c:choose>
																	<c:when test="${(result.status - 19) == i}">
																		<li>
																			<div class="main_flow_on">
																				<p>
																					<c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out>
																				</p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${(result.status -19) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${(result.status + 3) == i}">
																		<c:choose>
																			<c:when test="${i == 11}">
																				<li>
																					<div class="main_flow_on_last">
																						<p>
																							<c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out>
																						</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on">
																						<p>
																							<c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out>
																						</p>
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:when test="${(result.status + 3) < i}">
																		<li>
																			<div class="main_flow_off">
																				<p><c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:when>
																	<c:when test="${result.pe_yn == 'Y' and i == 2}">
																		<c:choose>
																			<c:when test="${result.pe_status == '2'}">
																				<li>
																					<div class="main_flow_on_first">
																						과금처리중
																					</div>
																				</li>
																			</c:when>
																			<c:when test="${result.pe_status == '3'}">
																				<li>
																					<div class="main_flow_ing">
																						<p>과금처리완료</p>
																					</div>
																				</li>
																			</c:when>
																			<c:otherwise>
																				<li>
																					<div class="main_flow_on_first">
																						과금접수
																					</div>
																				</li>
																			</c:otherwise>
																		</c:choose>
																	</c:when>
																	<c:otherwise>
																		<li>
																			<div class="main_flow_ing">
																				<p><c:out value="${MainModel.statusConditionsList[i].codeName }"></c:out></p>
																			</div>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</ul>
									</td>
								</tr>
								</c:if>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</table>
			</div>

			<!--pkg현황 끝 -->

			<!-- PKG현황 -->
			<div class="main_contents mt50">	
				<div class="fl_wrap">
					<div class="main_cont50 fl_left mr15">
						<h1 class="tit2">공지사항</h1>
						<div class="con_area">
							<table cellspacing="0" cellpadding="0" class="pkg_tb3">	
							<c:choose>
								<c:when test="${fn:length(NoticeModelList) == 0}">
									<tr>
										<td class="pkg_tb3_list" colspan="2">등록된 내용이 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="result" items="${NoticeModelList}" varStatus="status">
										<c:if test="${status.count <= 8 }">
											<colgroup>
												<col width="80%"/>
												<col width="*"/>
											</colgroup>
											<tr>
												<td class="pkg_tb3_list">
													<a href="javascript:fn_read_notice('${result.notice_seq}')"> 
														<c:out value="${fn:substring(result.title,0,55)}" /> 
														<c:out value="${fn:length(result.title) > 55 ? '...' : ''}" />
													</a>
												</td>
												<td class="pkg_tb3_date">
													<c:out value="${result.reg_date}" />
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
	
							<c:if test="${fn:length(NoticeModelList) < 10}">
								<c:forEach var="index" begin="1" end="${10 - fn:length(NoticeModelList)}" step="1">
									<tr>
										<td class="pkg_tb3_list_space" colspan="2"></td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
						</div>
					</div>
					<div class="main_cont50 fl_right">
						<h1 class="tit2">NEW/PN/CR</h1>
						<div class="con_area">
							<table cellspacing="0" cellpadding="0" class="pkg_tb3">
							<c:choose>
								<c:when test="${fn:length(NewpncrModelList) == 0}">
									<tr>
										<td class="pkg_tb3_list" colspan="2">등록된 내용이 없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="result" items="${NewpncrModelList}" varStatus="status">
										<c:if test="${status.count <= 8 }">
											<tr>
												<td class="pkg_tb3_state"><c:out value="${result.state}" /></td>
												<td class="pkg_tb3_state_list"><a href="javascript:fn_read_newpncr('${result.new_pn_cr_seq}')"> <c:out value="${fn:substring(result.title,0,40)}" /> <c:out value="${fn:length(result.title) > 40 ? '...' : ''}" />
												</a></td>
											</tr>
										</c:if>
									</c:forEach>
								</c:otherwise>
							</c:choose>
	
							<c:if test="${fn:length(NewpncrModelList) < 10}">
								<c:forEach var="index" begin="1" end="${10 - fn:length(NewpncrModelList)}" step="1">
									<tr>
										<td class="pkg_tb3_list_space" colspan="2"></td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
						</div>
					</div>
				</div>
			</div>

		</div>
		<!--공지와 달력 구분 -->
		<div class="main_c2 fl_wrap">
		<form:form commandName="MainModel" method="post" onsubmit="return false;">
		<form:hidden path="retUrl" />
		<form:hidden path="pkg_seq" />
		<form:hidden path="new_pn_cr_seq" />
		<form:hidden path="notice_seq" />
		<form:hidden path="sessionCondition" />
		<form:hidden path="group1_seq" />
		<form:hidden path="group2_seq" />
		<form:hidden path="group3_seq" />
		<form:hidden path="system_seq" />
			<!--공지사항 -->
			<%-- <div class="main_div2">

				<!--신규/pn/cr -->
				<div class="main_div2_c1">
					<table cellspacing="0" cellpadding="0" class="pkg_tb3">
						<caption>&nbsp;</caption>

						<c:choose>
							<c:when test="${fn:length(NewpncrModelList) == 0}">
								<tr>
									<td class="pkg_tb3_list" colspan="2">등록된 내용이 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="result" items="${NewpncrModelList}" varStatus="status">
									<c:if test="${status.count <= 10 }">
										<tr>
											<td class="pkg_tb3_state"><c:out value="${result.state}" /></td>
											<td class="pkg_tb3_state_list"><a href="javascript:fn_read_newpncr('${result.new_pn_cr_seq}')"> <c:out value="${fn:substring(result.title,0,15)}" /> <c:out value="${fn:length(result.title) > 15 ? '...' : ''}" />
											</a></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>

						<c:if test="${fn:length(NewpncrModelList) < 10}">
							<c:forEach var="index" begin="1" end="${10 - fn:length(NewpncrModelList)}" step="1">
								<tr>
									<td class="pkg_tb3_list_space" colspan="2"></td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>

				<!--공지사항 -->
				<div class="main_div2_c2">
					<table cellspacing="0" cellpadding="0" class="pkg_tb4">
						<caption>&nbsp;</caption>

						<c:choose>
							<c:when test="${fn:length(NoticeModelList) == 0}">
								<tr>
									<td class="pkg_tb3_list" colspan="2">등록된 내용이 없습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="result" items="${NoticeModelList}" varStatus="status">
									<c:if test="${status.count <= 10 }">
										<tr>
											<td class="pkg_tb3_list"><a href="javascript:fn_read_notice('${result.notice_seq}')"> <c:out value="${fn:substring(result.title,0,15)}" /> <c:out value="${fn:length(result.title) > 15 ? '...' : ''}" />
											</a></td>
											<td class="pkg_tb3_date"><c:out value="${result.reg_date}" /></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:otherwise>
						</c:choose>

						<c:if test="${fn:length(NoticeModelList) < 10}">
							<c:forEach var="index" begin="1" end="${10 - fn:length(NoticeModelList)}" step="1">
								<tr>
									<td class="pkg_tb3_list_space" colspan="2"></td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>

				<!--문의 -->
				<div class="pkg_inquiry"></div>

			</div> --%>

			<!--달력 영역 -->

			<%-- <div class="pkg_tb5_caption fl_wrap">
				<div class="pkg_tb5_caption_image"></div>
				<!--달력 -->
				<div id='diary' style="margin: 2px 0px 0px 0px; position: absolute;"></div>
				<div style="margin: 0px 0px 0px 200px; position: absolute;">
					<ul class="main_select" style="width: 250px;">
						<li class="select_tex1">담당</li>
						<li class="select_line"></li>
						<li>
							<form:radiobuttons onclick="javascript:fn_diary_reload();" style='overflow:hidden; white-space:nowrap; text-overflow:ellipsis;'
							path="userDiaryCondition" items="${MainModel.userDiaryConditionList}" itemLabel="codeName" itemValue="code" />
						</li>
					</ul>
				</div>
				<div style="margin: 0px 0px 0px 460px; position: absolute;">
					<ul class="main_select" style="width: 200px;">
						<li class="select_tex1">일정</li>
						<li class="select_line"></li>
						<li><form:radiobuttons onclick="javascript:fn_diary_reload();" style='overflow:hidden; white-space:nowrap; text-overflow:ellipsis;' path="diaryItem" items="${MainModel.diaryItemList}" itemLabel="codeName" itemValue="code" /></li>
					</ul>
				</div>
			</div> --%>
	</form:form>
<%-- 			<input type="hidden" id="diaryScript" name="diaryScript" value="${MainModel.diaryScript }" /> --%>


		</div>
</body>
</html>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/ui.js"></script>
<script type='text/javascript' src="/js/pkgmg/pkgmg.wired.pkg21.js"></script>

<script type="text/javaScript" defer="defer">
$(document).ready(
		function($) {
			status_navi('5');//navi
			var check_seqs_e = $("input[name='check_seqs_e']");
			doCalendar("PkgModel_pkgEquipmentModel_start_date_All");
			doCalendar("PkgModel_pkgEquipmentModel_end_date_All");

			for(var i = 0; i < check_seqs_e.size(); i++) {
				doCalendar("PkgModel_pkgEquipmentModel_start_date_" + (i+1));
				doCalendar("PkgModel_pkgEquipmentModel_end_date_" + (i+1));

				if(check_seqs_e.eq(i).is(":checked")) {
					doDivSH("show", "equipment_se_date_" + (i + 1), 0);
				}
			}
		}
	);
</script>

</head>
<body>
<form:form commandName="Pkg21Model" method="post" onsubmit="return false;">
	<form:hidden path="pkg_seq" />
	<form:hidden path="select_status" />
	<form:hidden path="status" />
	

	<form:hidden path="status_dev" />
	<form:hidden path="read_gubun" />
	<form:hidden path="dev_yn" />
	<form:hidden path="system_seq" />
	<form:hidden path="content" />
	<form:hidden path="content_pn" />
	<form:hidden path="content_cr" />
	<form:hidden path="content_self" />
	<form:hidden path="content_invest" />
	<form:hidden path="save_status" />
	<form:hidden path="cha_yn" />
	<form:hidden path="vol_yn" />
	<form:hidden path="sec_yn" />
	<form:hidden path="ser_downtime" />
	<form:hidden path="impact_systems" />
	<form:hidden path="update_gubun" />
	
	<form:hidden path="master_file_id" />
	<form:hidden path="title" />
	
	<form:hidden path="ord" />
	
	<!-- 	navi -->
	<form:hidden path="col43" />
	
	<!-- 현황 예제1 -->
	<div class="sub_contents01 fl_wrap">
		<div class="sub_flow_wrap_100">
				<ul class="sub_flow_line01 fl_wrap">
				<li id="status_navi1" class="sub_flow">
					<p id="status_navi_fn1" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
						PKG개발 결과
					</p>
					<p id="status_navi_ing1" class="ing" style="cursor: pointer; display:none; " onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">	PKG개발 결과</span>
					</p>
					<p id="status_navi_now1" class="on_blue" >
						PKG개발 결과
					</p>
					<p id="status_navi_non1" class="w_long"  style=" display:none; " >
						<img src="/images/ic_flow.png">
						<span class="over_text">PKG개발 결과</span>
					</p>
				</li>
				
				<li id="status_navi2" class="sub_flow">
					<p id="status_navi_fn2" class="on" style="cursor: pointer; display:none; " onclick="javascript:fn_pkg21_status_read('301');">
						개발검증
					</p>
					<p id="status_navi_ing2" class="ing" style="cursor: pointer; display:none; " onclick="javascript:fn_pkg21_status_read('301');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">개발검증</span>
					</p>
					<p id="status_navi_now2" class="on_blue" style="display:none;" >
						개발검증
					</p>
					<p id="status_navi_non2" class="w_long" style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">개발검증</span>
					</p>
				</li>
				
				<li id="status_navi3" class="sub_flow">
					<p id="status_navi_fn3"  class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('331');">
						초도적용계획수립
					</p>
					<p id="status_navi_ing3"  class="ing" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('331');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">초도적용계획수립</span>
					</p>
					<p id="status_navi_now3" class="on_blue" style="display:none;" >
						초도적용계획수립
					</p>
					<p id="status_navi_non3" class="w_long" style=" " >
						<img src="/images/ic_flow.png">
						<span class="over_text">초도적용계획수립</span>
					</p>
				</li>
				<li id="status_navi4" class="sub_flow">
					<p id="status_navi_fn4" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('336');">
						초도적용 결과
					</p>
					<p id="status_navi_ing4" class="ing"  style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('336');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">초도적용 결과</span>
					</p>
					<p id="status_navi_now4" class="on_blue" style="display:none;" >
						초도적용 결과
					</p>
					<p id="status_navi_non4" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">초도적용 결과</span>
					</p>
				</li>
				<li id="status_navi5" class="sub_flow">
					<p id="status_navi_fn5" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('341');">
						확대적용계획수립
					</p>
					<p id="status_navi_ing5" class="ing" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('341');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">확대적용계획수립</span>
					</p>
					<p id="status_navi_now5" class="on_blue" style="display:none;" >
						확대적용계획수립
					</p>
					<p id="status_navi_non5" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">확대적용계획수립</span>
					</p>
				</li>
				<li id="status_navi6" class="sub_flow">
					<p id="status_navi_fn6" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('343');">
						확대적용결과
					</p>
					<p id="status_navi_ing6" class="ing" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('343');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">확대적용결과</span>
					</p>
					<p id="status_navi_now6" class="on_blue" style="display:none;" >
						확대적용결과
					</p>	
					<p id="status_navi_non6" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">확대적용결과</span>
					</p>
				</li>
				
				<li id="status_navi7" class="sub_flow">
					<p id="status_navi_fn7" class="on" style="cursor: pointer;display:none;">
						완료
					</p>
					<p id="status_navi_non7" style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">완료</span>
					</p>
				</li>
			</ul>
		</div>		
	</div>
	
	
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">확대적용 계획수립</h2>
		<div class="sub_con_close2">
			<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list" style="display: block">
<%-- 		status:	${Pkg21Model.status}   a:	${Pkg21Model.select_status} --%>
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="15%">
					<col width="*">
					<col width="15%">
					<col width="*">
				</colgroup>
				<tbody>
					<tr>
						<th>서비스중단시간</th>
						<td>${Pkg21Model.ser_downtime}</td>
						<th>영향시스템</th>
						<td>${Pkg21Model.impact_systems}</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<h3>초도적용장비</h3>
		<div class="table_style03 mt10">
			<table class="con_width100">
				<colgroup>
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="55%">
					<col width="15%">
				</colgroup>
				<thead>
					<tr>
						<th>국사</th>
						<th>장비명</th>
						<th>서비스지역</th>
						<th>적용일시</th>
						<th>담당</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${Pkg21Model.pkgEquipmentModelList4E}" varStatus="status">
						<tr>
							<td class="td_center">
								${result.team_name}
							</td>
							<td class="td_center">
								${result.equipment_name}
							</td>
							<td class="td_center">
								${result.service_area}
							</td>
							<td class="td_center">
								${result.start_date} ${result.start_time1}:${result.start_time2}
								 ~ 
								${result.end_date} ${result.end_time1}:${result.end_time2}
							</td>
							<td class="td_center">
								${result.reg_user_name} [${result.reg_user_sosok}]
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<h3>확대적용장비</h3>
		<div class="table_style03">
			<table class="con_width100">
				<colgroup>
					<col width="3%">
					<col width="5%">
					<col width="7%">
					<col width="10%">
					<col width="75%">
				</colgroup>
				<thead>
					<tr>
						<th>
							<input type="checkbox" name="allCheckbox" onclick="fn_allCheck();" />
						</th>
						<th>국사</th>
						<th>장비명</th>
						<th>서비스지역</th>
						<th>적용일시</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result2" items="${Pkg21Model.pkgEquipmentModelList}" varStatus="status">
						<tr>
							<td class="td_center">
								<c:choose>
									<c:when test="${empty result2.work_result}">
										<c:choose>
											<c:when test="${empty result2.start_date}">
												<input type="checkbox" name="check_seqs_e" onchange="javascript:fn_checkboxEquipment_click(this, ${status.count});" value="${result2.equipment_seq}" />
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="check_seqs_e" onchange="javascript:fn_checkboxEquipment_click(this, ${status.count});" value="${result2.equipment_seq}" checked/>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<input type="hidden" name="check_seqs_e" value="N" />
									</c:otherwise>
								</c:choose>
							</td>
							<td class="td_center">
								${result2.team_name}
							</td>
							<td class="td_center">
								${result2.equipment_name}
							</td>
							<td class="td_center">
								${result2.service_area}
							</td>
							<td class="td_center sysc_inp mg02">
<%-- 								<div id="equipment_se_date_${status.count}" style="display:none;"> --%>
								<div id="equipment_se_date_${status.count}" >
									<c:choose>
										<c:when test="${empty result2.work_result}">
											<div class="date_time">
												<select name="ampms" id="PkgModel_pkgEquipmentModel_ampm_${status.count}" onchange="javascript:fn_SelectAmPm(this, ${status.count})" class="fl_left mr05">
													<c:choose>
														<c:when test="${result2.ampm eq '주간'}">
															<option value = "야간">야간</option>
															<option value = "주간" selected="selected">주간</option>
														</c:when>
														<c:otherwise>
															<option value = "야간" selected="selected">야간</option>
															<option value = "주간">주간</option>
														</c:otherwise>
													</c:choose>
												</select>
												<input id="PkgModel_pkgEquipmentModel_start_date_${status.count}" name="start_dates" class="new_inp fl_left inp_w100px ml03" type="text" value="${result2.start_date}" readonly="readonly" />
												
												<input id="PkgModel_pkgEquipmentModel_start_time1_${status.count}" name="start_times1" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="${result2.start_time1}"/>
												<span class="fl_left line_height35"> : </span>
												<input type="text" id="PkgModel_pkgEquipmentModel_start_time2_${status.count}" name="start_times2" maxlength="2" class="new_inp inp_w20px fl_left"  value="${result2.start_time2}"/>
												
												<span class="fl_left mg05"> ~ </span>
												
												<input id="PkgModel_pkgEquipmentModel_end_date_${status.count}" name="end_dates" class="new_inp fl_left inp_w100px ml03" type="text" value="${result2.end_date}" readonly="readonly" /> 
												
												<input id="PkgModel_pkgEquipmentModel_end_time1_${status.count}" name="end_times1" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="${result2.end_time1}"/>
												<span class="fl_left line_height35"> : </span>
												<input id="PkgModel_pkgEquipmentModel_end_time2_${status.count}" name="end_times2" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="${result2.end_time2}"/>
											</div>
										</c:when>
										<c:otherwise>
											${result2.start_date} ${result2.start_time1}:${result2.start_time2}
											 ~ 
											${result2.end_date} ${result2.end_time1}:${result2.end_time2}
											<select style="display: none;" name="ampms" id="PkgModel_pkgEquipmentModel_ampm_${status.count}">
												<option value="${result2.ampm}">${result2.ampm}</option>
											</select>
											<input name="start_dates" value = "${result2.start_date}" type=hidden>
											<input name="start_times1" value = "${result2.start_time1}" type="hidden">
											<input name="start_times2" value = "${result2.start_time2}" type="hidden">
											<input name="end_dates" value = "${result2.end_date}" type="hidden">
											<input name="end_times1" value = "${result2.end_time1}" type="hidden">
											<input name="end_times2" value = "${result2.end_time2}" type="hidden">
										</c:otherwise>
									</c:choose>
								</div>
							</td>
						</tr>
						<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_seqs_count"/></c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<h3>선택 장비 일괄 등록</h3>
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="15%">
					<col width="75%">
					<col width="10%">
				</colgroup>
				<thead></thead>
				<tbody>
					<tr>
						<th>적용 일시</th>
						<td class="td_center sysc_inp">
							<select id="PkgModel_pkgEquipmentModel_ampm_All" onchange="fn_SelectAmPm_All(this)" class="fl_left mr05" >
								<option value = "야간" selected="selected">야간</option>
								<option value = "주간">주간</option>
							</select>

							<input id="PkgModel_pkgEquipmentModel_start_date_All" class="new_inp fl_left inp_w100px ml03" type="text" readonly/>
							<input id="PkgModel_pkgEquipmentModel_start_time1_All" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="02"/>
							<span class="fl_left line_height35"> : </span> 
							<input id="PkgModel_pkgEquipmentModel_start_time2_All" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="00"/> 
							<span class="fl_left line_height35 ml03"> ~ </span>
							<input id="PkgModel_pkgEquipmentModel_end_date_All" class="new_inp fl_left inp_w100px ml03" type="text" readonly/>
							<input id="PkgModel_pkgEquipmentModel_end_time1_All" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="07"/>
							<span class="fl_left line_height35"> : </span>
							<input id="PkgModel_pkgEquipmentModel_end_time2_All" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="00" />
						</td>
						<td>
							<span class="btn_wrap2">
								<a href="javascript:fn_SelectAllCheck();" class="btn btn_blue">적용</a>
							</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
		
	<c:if test="${Pkg21Model.select_status < 341}">
		<div class="bt_btn"><span class="btn_org2">
			<a href="javascript:fn_save_341()">내용저장</a></span>
		</div>
	</c:if>
	
	<c:if test="${Pkg21Model.select_status > 341}">
		<div class="bt_btn"><span class="btn_org2">
			<a href="javascript:fn_after_eq_e();">일정 저장</a></span>
		</div>
	</c:if>
	
	
	
	<!---- /확대적용 계획수립 ---->
	
	
	
	<c:if test="${Pkg21Model.select_status > 337 }">
	
	
	<!---- 확대적용 승인요청 ---->
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">확대적용 승인요청 </h2>
		<div class="sub_con_close2">
			<span id="con_open1" style="cursor: pointer;" onclick="javascript:fn_open_and_close('1');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list1" style="display: block">
		
		<div class="table_style03 fl_wrap">
			<table class="con_width100">
				<colgroup>
					<col width="5%">
					<col width="5%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="15%">
					<col width="35%">
				</colgroup>
				<thead>
					<tr>
						<th>선택</th>
						<th>승인</th>
						<th>이름</th>
						<th>부서</th>
						<th>전화번호</th>
						<th>이메일</th>
						<th>결과</th>
						<th>Comment</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${Pkg21Model.status > 333}">
							<c:choose>
								<c:when test="${empty Pkg21Model.pkgUserModelList}">
									<c:forEach var="result" items="${Pkg21Model.systemUserModelList}" varStatus="status">
										<tr>
											<td class="td_center">
												<c:choose>
													<c:when test="${empty result.user_id}">
														<input type="checkbox" name="check_seqs" value="${result.user_id}" />
													</c:when>
													<c:otherwise>
														<input type="checkbox" name="check_seqs" value="${result.user_id}" checked/>
													</c:otherwise>
												</c:choose>
											</td>
											<td class="td_center">${status.count} 차</td>
											<td class="td_center">${result.user_name}</td>
											<td class="td_center">${result.sosok}</td>
											<td class="td_center">${result.user_phone}</td>
											<td class="td_center">${result.user_email}</td>
											<td class="td_center">미요청</td>
											<td class="td_center"></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach var="result" items="${Pkg21Model.pkgUserModelList}" varStatus="status">
										<tr>
											<td class="td_center">
												<c:if test="${result.status == 'R'}">
													<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
														<span class="btn_wrap">
															<a href="javascript:fn_after_343('${result.ord}');" class="btn btn_red">승인</a>
															<a href="javascript:fn_reject_343();" class="btn btn_blue">반려</a>
														</span>
													</c:if>
												</c:if>
											</td>
											<td class="td_center">${status.count} 차</td>
											<td class="td_center">${result.user_name}</td>
											<td class="td_center">${result.sosok}</td>
											<td class="td_center">${result.user_phone}</td>
											<td class="td_center">${result.user_email}</td>
											<td class="td_center">
												<c:choose>
													<c:when test="${result.status == 'F'}">
														<font color="blue">승인</font>
														<c:if test="${not empty result.update_date}">
															(${result.update_date})
														</c:if>
													</c:when>
													<c:otherwise>
														미승인
													</c:otherwise>
												</c:choose>
											</td>
											<td class="td_center">
												<c:choose>
													<c:when test="${result.status == 'F'}">
														${result.au_comment}
													</c:when>
													<c:otherwise>
														<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
															<form:input path="au_comment" maxlength="100" class="new_inp inp_w90" />
														</c:if>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:when>
						
						<c:otherwise>
							<c:if test="${Pkg21Model.status == 324 }">
								<c:forEach var="result" items="${Pkg21Model.systemUserModelList}" varStatus="status">
									<tr>
										<td class="td_center">
											<c:choose>
												<c:when test="${empty result.user_id}">
													<input type="checkbox" name="check_seqs" value="${result.user_id}" />
												</c:when>
												<c:otherwise>
													<input type="checkbox" name="check_seqs" value="${result.user_id}" checked/>
												</c:otherwise>
											</c:choose>
										</td>
										<td class="td_center">${status.count} 차</td>
										<td class="td_center">${result.user_name}</td>
										<td class="td_center">${result.sosok}</td>
										<td class="td_center">${result.user_phone}</td>
										<td class="td_center">${result.user_email}</td>
										<td class="td_center">미요청</td>
										<td class="td_center"></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${Pkg21Model.select_status > 341 && Pkg21Model.update_gubun == 'N'}">
								<c:forEach var="result" items="${Pkg21Model.pkgUserModelList}" varStatus="status">
									<tr>
										<td class="td_center">
											<c:if test="${result.status == 'R'}">
												<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
													<span class="btn_wrap">
														<a href="javascript:fn_save_343('${result.ord}');" class="btn btn_red">승인</a>
														<a href="javascript:fn_reject_343();" class="btn btn_blue">반려</a>
													</span>
												</c:if>
											</c:if>
										</td>
										<td class="td_center">${status.count} 차</td>
										<td class="td_center">${result.user_name}</td>
										<td class="td_center">${result.sosok}</td>
										<td class="td_center">${result.user_phone}</td>
										<td class="td_center">${result.user_email}</td>
										<td class="td_center">
											<c:choose>
												<c:when test="${result.status == 'F'}">
													<font color="blue">승인</font>
													<c:if test="${not empty result.update_date}">
														(${result.update_date})
													</c:if>
												</c:when>
												<c:otherwise>
													미승인
												</c:otherwise>
											</c:choose>
										</td>
										<td class="td_center">
											<c:choose>
												<c:when test="${result.status == 'F'}">
													${result.au_comment}
												</c:when>
												<c:otherwise>
													<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
														<form:input path="au_comment" maxlength="100" class="new_inp inp_w90" />
													</c:if>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</c:otherwise>
					</c:choose>

					
					
					
					
					
				</tbody>
			</table>
			<div class="mt10 mb10 txt_888">
				<p>* 시스템에 등록된 현장승인 담당자 정보를 현행화하세요.</p>
			</div>
		</div>
		<div class="write_info2 fl_wrap">
			<c:if test="${Pkg21Model.select_status > 337}">
				확대등록
				<span class="name2">
					${Pkg21Model.reg_plan_user} (${Pkg21Model.reg_date_plan})
				</span>
			</c:if>
		</div>
		<c:if test="${Pkg21Model.status ne 334 && Pkg21Model.select_status == 341 }">
			<div class="bt_btn">
				<span class="btn_org2">
					<a href="javascript:fn_save_342();">확대승인요청</a>
				</span>
			</div>
		</c:if>
		<c:if test="${Pkg21Model.status eq 334 && Pkg21Model.select_status == 341 }">
<!-- 		확대등록 이후 첫번째 승인 요청 -->
			<div class="bt_btn">
				<span class="btn_org2">
					<a href="javascript:fn_after_342();">확대승인요청</a>
				</span>
			</div>
		</c:if>
	</div>
	
	</c:if>
</form:form>
</body>
</html>

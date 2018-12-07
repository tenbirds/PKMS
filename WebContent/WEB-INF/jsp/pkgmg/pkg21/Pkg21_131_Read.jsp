<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/ui.core.js"></script>
<script type='text/javascript' src='/js/pkgmg/pkgmg.pkg21.js'></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(
		function($) {
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
	
	<form:hidden path="master_file_id" />
	<form:hidden path="title" />
	
	<form:hidden path="ord" />
<div class="tit">
${Pkg21Model.title}
</div>
<div class="new_con_Div32">
	<div class="sub_contents01 fl_wrap">
		<sec:authorize ifNotGranted="ROLE_ADMIN" >
			<c:set var="s_granted" value="N" />
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_ADMIN" >
			<c:set var="s_granted" value="Y" />
		</sec:authorize>
		<div class="sub_flow_wrap fl_left">
			<ul class="sub_flow_line01 fl_wrap">
				<li class="sub_flow">
					<c:choose>
						<c:when test="${registerFlag == '등록'}">
							<p class="on">
								SVT 계획수립
							</p>
						</c:when>
						<c:when test="${Pkg21Model.select_status == 101}">
							<p class="on_blue">
								SVT 계획수립
							</p>
						</c:when>
						<c:otherwise>
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">SVT 계획수립</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status == 101 && Pkg21Model.select_status == 102}">
							<p class="on">
								SVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status == 101}">
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('102');">
								SVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101 && Pkg21Model.select_status == 102}">
							<p class="on_blue">
								SVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('102');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">SVT 결과</span>
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">SVT 결과</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.dev_yn == 'N'}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">DVT 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status < 102}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">DVT 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status == 102 && Pkg21Model.select_status == 111}">
							<p class="on">
								DVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status == 102}">
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
								DVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.dev_yn == 'D'}">
							<c:choose>
								<c:when test="${Pkg21Model.status_dev < 114 && Pkg21Model.select_status == 111}">
									<p class="on">
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status_dev < 114}">
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status_dev == 114 && Pkg21Model.select_status == 111}">
									<p class="on_blue">
										DVT 결과
									</p>	
								</c:when>
								<c:otherwise>
									<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										<img src="/images/ic_flow_blue.png">
										<span class="over_text_ing text_1">DVT 결과</span>
									</p>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${Pkg21Model.status < 114 && Pkg21Model.select_status == 111}">
									<p class="on">
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status < 114}">
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status > 113 && Pkg21Model.select_status == 111}">
									<p class="on_blue" >
										DVT 결과
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status > 113}">
									<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										<img src="/images/ic_flow_blue.png">
										<span class="over_text_ing text_1">DVT 결과</span>
									</p>
								</c:when>
								<c:otherwise>								
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('111');">
										DVT 결과
									</p>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 102}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">CVT 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 123 && Pkg21Model.select_status == 121}">
							<p class="on_blue">
								CVT 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 123}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('121');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">CVT 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.dev_yn == 'Y'}">
							<c:choose>
								<c:when test="${Pkg21Model.status < 114}">
									<p>
										<img src="/images/ic_flow.png">
										<span class="over_text">CVT 결과</span>
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status > 113 && Pkg21Model.select_status == 121}">
									<p class="on">
										CVT 결과
									</p>
								</c:when>
								<c:otherwise>
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('121');">
										CVT 결과
									</p>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${Pkg21Model.status > 101 && Pkg21Model.select_status == 121}">
							<p class="on">
								CVT 결과
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('121');">
								CVT 결과
							</p>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>	
		<div class="sub_flow_wrap fl_left">	
			<ul class="sub_flow_line02 fl_wrap">
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.cha_yn == 'N'}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">과금검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.cha_yn == 'S' && Pkg21Model.select_status == 161}">
							<p class="on_blue">
								과금검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.cha_yn == 'S'}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('161');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">과금검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.dev_yn == 'D'}">
							<c:choose>
								<c:when test="${Pkg21Model.status_dev < 114 || Pkg21Model.status < 124}">
									<p>
										<img src="/images/ic_flow.png">
										<span class="over_text">과금검증</span>
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status > 123 && Pkg21Model.select_status == 161}">
									<p class="on">
										과금검증
									</p>
								</c:when>
								<c:otherwise>
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('161');">
										과금검증
									</p>
								</c:otherwise>
							</c:choose>	
						</c:when>
						<c:when test="${Pkg21Model.status > 123 && Pkg21Model.select_status == 161}">
							<p class="on">
								과금검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 123}">
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('161');">
								과금검증
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">과금검증</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.vol_yn == 'N'}">
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">용량검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.vol_yn == 'S' && Pkg21Model.select_status == 151}">
							<p class="on_blue">
								용량검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.vol_yn == 'S'}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('151');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">용량검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101 && Pkg21Model.select_status == 151}">
							<p class="on">
								용량검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101}">
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('151');">
								용량검증
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">용량검증</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow" >
					<c:choose>
						<c:when test="${Pkg21Model.sec_yn == 'S' && Pkg21Model.select_status == '171'}">
							<p class="on_blue">
								보안검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.sec_yn == 'S'}">
							<p class="ing" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('171');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing text_1">보안검증</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.select_status == 171}">
							<p class="on">
								보안검증
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 101}">
							<p style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('171');">
								<img src="/images/ic_flow.png">
								<span class="over_text">보안검증</span>
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">보안검증</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
			<ul class="sub_flow_line03 fl_wrap">
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 124}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">초도적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 131 && Pkg21Model.select_status == 131}">
							<p class="on_blue">
								초도적용 계획수립
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 131}">
							<p class="ing w_long" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('131');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">초도적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${(Pkg21Model.cha_yn == 'Y' || Pkg21Model.vol_yn == 'Y') && s_granted == 'N'}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">초도적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.dev_yn == 'D'}">
							<c:choose>
								<c:when test="${Pkg21Model.status_dev < 114}">
									<p class="w_long">
										<img src="/images/ic_flow.png">
										<span class="over_text">초도적용 계획수립</span>
									</p>
								</c:when>
								<c:when test="${Pkg21Model.status_dev > 113 && Pkg21Model.select_status == 131}">
									<p class="on">
										초도적용 계획수립
									</p>
								</c:when>
								<c:otherwise>
									<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('131');">
										초도적용 계획수립
									</p>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${Pkg21Model.status > 123 && Pkg21Model.select_status == 131}">
							<p class="on">
								초도적용 계획수립
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('131');">
								초도적용 계획수립
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 132}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">초도적용 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 133 && Pkg21Model.select_status == 133}">
							<p class="on_blue">
								초도적용 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 133}">
							<p class="ing w_long" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('133');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">초도적용 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 131 && Pkg21Model.select_status == 133}">
							<p class="on">
								초도적용 결과
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('133');">
								초도적용 결과
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 134 || Pkg21Model.status == 139  || Pkg21Model.eq_cnt == 0}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">확대적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 141 && Pkg21Model.select_status == 141}">
							<p class="on_blue">
								확대적용 계획수립
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 141}">
							<p class="ing w_long" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('141');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">확대적용 계획수립</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 133 && Pkg21Model.select_status == 141}">
							<p class="on">
								확대적용 계획수립
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('141');">
								확대적용 계획수립
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status < 142 || Pkg21Model.eq_cnt == 0}">
							<p class="w_long">
								<img src="/images/ic_flow.png">
								<span class="over_text">확대적용 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 143 && Pkg21Model.select_status == 143}">
							<p class="on_blue">
								확대적용 결과
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 143}">
							<p class="ing w_long" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('143');">
								<img src="/images/ic_flow_blue.png">
								<span class="over_text_ing">확대적용 결과</span>
							</p>
						</c:when>
						<c:when test="${Pkg21Model.status > 141 && Pkg21Model.select_status == 143}">
							<p class="on">
								확대적용 결과
							</p>
						</c:when>
						<c:otherwise>
							<p class="on" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('143');">
								확대적용 결과
							</p>
						</c:otherwise>
					</c:choose>
				</li>
				<li class="sub_flow">
					<c:choose>
						<c:when test="${Pkg21Model.status == 199}">
							<p class="on_blue">
								완료
							</p>
						</c:when>
						<c:otherwise>
							<p>
								<img src="/images/ic_flow.png">
								<span class="over_text">완료</span>
							</p>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</div>
	</div>
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">초도적용 계획수립</h2>
		<div class="sub_con_close2">
			<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list" style="display: block">
		
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
					<c:forEach var="result" items="${Pkg21Model.pkgEquipmentModelList}" varStatus="status">
						<tr>
							<td class="td_center">
								<input type="checkbox" id="check_seqs_e${result.equipment_seq}" name="check_seqs_e" onchange="javascript:fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" <c:if test="${!empty result.start_date}">checked='checked'</c:if> />
							</td>
							<td class="td_center">
								${result.team_name}
							</td>
							<td class="td_center">
								${result.equipment_name}
							</td>
							<td class="td_center">
								${result.service_area}
							</td>
							<td class="td_center sysc_inp mg02">
								<div id="equipment_se_date_${status.count}" style="display:none;">
									<div class="date_time2">
										<select name="ampms" id="PkgModel_pkgEquipmentModel_ampm_${status.count}" onchange="javascript:fn_SelectAmPm(this, ${status.count})" class="fl_left mr05">
											<c:choose>
												<c:when test="${result.ampm eq '주간'}">
													<option value = "야간">야간</option>
													<option value = "주간" selected="selected">주간</option>
												</c:when>
												<c:otherwise>
													<option value = "야간" selected="selected">야간</option>
													<option value = "주간">주간</option>
												</c:otherwise>
											</c:choose>
										</select>
										<input id="PkgModel_pkgEquipmentModel_start_date_${status.count}" name="start_dates" class="new_inp fl_left inp_w100px ml03" type="text" value="${result.start_date}" readonly="readonly" />
										
										<input id="PkgModel_pkgEquipmentModel_start_time1_${status.count}" name="start_times1" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="${result.start_time1}"/>
										<span class="fl_left line_height35"> : </span>
										<input type="text" id="PkgModel_pkgEquipmentModel_start_time2_${status.count}" name="start_times2" maxlength="2" class="new_inp inp_w20px fl_left"  value="${result.start_time2}"/>
										
										<span class="fl_left mg05"> ~ </span>
										
										<input id="PkgModel_pkgEquipmentModel_end_date_${status.count}" name="end_dates" class="new_inp fl_left inp_w100px ml03" type="text" value="${result.end_date}" readonly="readonly" /> 
										
										<input id="PkgModel_pkgEquipmentModel_end_time1_${status.count}" name="end_times1" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="${result.end_time1}"/>
										<span class="fl_left line_height35"> : </span>
										<input id="PkgModel_pkgEquipmentModel_end_time2_${status.count}" name="end_times2" maxlength="2" class="new_inp inp_w20px fl_left" type="text" value="${result.end_time2}"/>
									</div>
								</div>
							</td>
						</tr>
						<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_seqs_count"/></c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<h3>선택 장비 적용 일시 일괄 등록</h3>
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
	<!---- /초도적용 계획수립 ---->
	
	<!---- 초도적용 승인요청 ---->
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">초도적용 승인요청</h2>
		<div class="sub_con_close2">
			<span id="con_open1" style="cursor: pointer;" onclick="javascript:fn_open_and_close('1');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list1" style="display: block">
		
		<div class="table_style03">
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
						<th>Comment <span class="txt_red">*</span></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${Pkg21Model.status == 124}">
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
					<c:if test="${Pkg21Model.status > 124}">
						<c:forEach var="result" items="${Pkg21Model.pkgUserModelList}" varStatus="status">
							<tr>
								<td class="td_center">
									<c:if test="${result.status == 'R'}">
										<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
											<span class="btn_wrap">
												<a href="javascript:fn_save_132('${result.ord}');" class="btn btn_red">승인</a>
												<a href="javascript:fn_reject_132();" class="btn btn_blue">반려</a>
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
				</tbody>
			</table>
		</div>
		<div class="write_info2 fl_wrap">
			<c:if test="${Pkg21Model.status > 124}">
				초도등록
				<span class="name2">
					${Pkg21Model.reg_plan_user} (${Pkg21Model.reg_date_plan})
				</span>
			</c:if>
		</div>
		<c:if test="${Pkg21Model.status == 124}">
			<div class="bt_btn">
				<span class="btn_org2">
					<a href="javascript:fn_save_131()">초도승인요청</a>
				</span>
			</div>
		</c:if>
	</div>
</div>
</form:form>
</body>
</html>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<body>
		
		<c:if test="${pkgModel.status >= 6}">
			<input type="hidden" name="work_gubun" value="S" />
			<sec:authentication property="principal.username" var="sessionId"/>
			<input type="hidden" id="Session_user_id" name="Session_user_id" value="${sessionId}">
			<input type="hidden" name="work_date"  id="work_date" />
			<input type="hidden" name="team_code" id="team_code" />
		</c:if>
		
		<h4 class="balloon_header">초도일정수립</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
			<img alt=닫기 src="/images/btn_close_layer.png">
		</a>
		<div class="ly_sub">
					
			<!--라디오 버튼 선택 -->

			<!--버튼위치 -->
			<div class="pop_btn_location3">
				<span>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_4()" />
					</sec:authorize>
				</span>
			</div>
					
			<div id="flow_tb_1" class="Npaper_type" style="height:600px;">
			
				<table class="tbl_type_ly tbl_td_w" summary="">
					<colgroup>
						<col width="140px">
						<col width="*">
						<col width="140px">
						<col width="*">
					</colgroup>
					<tr>
						<th scope="col">서비스 중단 시간</th>
						<td scope="col">
							${PkgModel.ser_downtime} 분
						</td>
						<th scope="col">영향 시스템</th>
						<td scope="col" >
							${PkgModel.impact_systems}
						</td>
					</tr>
				</table>
						
				<table class="tbl_type_ly tbl_td_wc mt_20">
					<caption>초도적용장비</caption>
					<colgroup>
						<col width="50px;">
						<col width="120px;">
						<col width="120px;">
						<col width="90px;">
						<col width="">
					</colgroup>
					<thead>
						<tr>
							<th>
								<input type="checkbox" name="allCheckbox" onclick="fn_allCheck();" />
							</th>
	<!-- 						<th style="width:50px;">&nbsp;</th> -->
							<th>국사</th>
							<th>장비</th>
							<th>서비스지역</th>
							<th>적용일시</th>
	
						</tr>
					</thead>
					
					<tbody>
					<c:forEach var="result" items="${PkgModel.pkgEquipmentModelList}" varStatus="status">
						<tr>
							<td>
								<c:choose>
									<c:when test="${empty result.work_date}">
								<input type="checkbox" name="check_seqs" onclick="fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" />
									</c:when>
									<c:otherwise>
								<input type="checkbox" name="check_seqs" onclick="fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" checked/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								${result.team_name}&nbsp;
							</td>
							<td>
								${result.equipment_name}&nbsp;
							</td>
							<td>
								${result.service_area}&nbsp;
							</td>
							<td>
								
								<div id="equipment_se_none_${status.count}">&nbsp;</div>
								<div id="equipment_se_date_${status.count}" style="display:none">
									<input id="PkgModel_pkgEquipmentModel_work_date_${status.count}" name="work_dates" class="new_inp inp_w100px fl_left mr02" type="text" value="${result.work_date}" readonly/>
									<input id="PkgModel_pkgEquipmentModel_start_time1_${status.count}" name="start_times1" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="${result.start_time1}"/>
									<span class="fl_left mg03">:</span> 
									<input id="PkgModel_pkgEquipmentModel_start_time2_${status.count}" name="start_times2"style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="${result.start_time2}"/> 
									<span class="fl_left mg03">~</span>
									<input id="PkgModel_pkgEquipmentModel_end_time1_${status.count}" name="end_times1" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="${result.end_time1}"/>
									<span class="fl_left mg03">:</span>
									<input id="PkgModel_pkgEquipmentModel_end_time2_${status.count}" name="end_times2" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="${result.end_time2}"/>
									
									<c:if test="${pkgModel.status >= 6}">
<%-- 										<img src="/images/btn_identify.gif" alt="확인" style="cursor:pointer; vertical-align: bottom;" onclick="fn_iwcs_view('${result.work_date}','${result.team_code}')" /> --%>
										<div class="btn_line_blue22 fl_left"><span onclick="fn_iwcs_view('${result.work_date}','${result.team_code}')">확인</span></div>
									</c:if>
								</div>
							</td>
						</tr>
						<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_seqs_count"/></c:if>
					</c:forEach>
					</tbody>
				</table>
				
				
				<table class="tbl_type_ly tbl_td_w mt_20">
					<caption>선택 장비 일괄 등록</caption>
					<colgroup>
						<col width="140px">
						<col width="*">
					</colgroup>
						<tr>
							<th>
								적용 일시
							</th>
							<td>
								<input id="PkgModel_pkgEquipmentModel_work_date_All" class="new_inp inp_w100px fl_left" type="text" readonly/>
								<input id="PkgModel_pkgEquipmentModel_start_time1_All" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="02"/>
								<span class="fl_left mg03">:</span>
								<input id="PkgModel_pkgEquipmentModel_start_time2_All" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="00"/> 
								<span class="fl_left mg03">~</span>
								<input id="PkgModel_pkgEquipmentModel_end_time1_All" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="07"/>
								<span class="fl_left mg03">:</span>
								<input id="PkgModel_pkgEquipmentModel_end_time2_All" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="00" />
								<span class="btn_line_blue2 fl_left mg03"><a href="javascript:fn_SelectAllCheck();">적용</a></span>
							</td>
							
					</tr>
				</table>
			</div>

			<div class="pop_status_box">
				<c:if test="${not empty PkgModel.pkgStatusModel.reg_date}">
					<div class="pop_status_reg">
						등록: ${PkgModel.pkgStatusModel.reg_user_name} (${PkgModel.pkgStatusModel.reg_date})
					</div>
				</c:if>
	
				<c:if test="${not empty PkgModel.pkgStatusModel.update_date}">
					<div class="pop_status_update">
						수정: ${PkgModel.pkgStatusModel.update_user_name} (${PkgModel.pkgStatusModel.update_date})
					</div>
				</c:if>
				<div class="pop_status_update btn_line_blue3">
					<a href="javascript:fn_print();">[PKG 요약 보고]</a>
				</div>
			</div>
						
		</div>
 
		<div class="edge_cen" style="top:100px;"></div>
 

</body>

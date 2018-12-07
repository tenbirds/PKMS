<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<body>
		
		<c:if test="${pkgModel.status >= 8}">
			<input type="hidden" name="work_gubun" value="E" />
			<sec:authentication property="principal.username" var="sessionId"/>
			<input type="hidden" id="Session_user_id" name="Session_user_id" value="${sessionId}">
			<input type="hidden" name="work_date"  id="work_date" />
			<input type="hidden" name="team_code" id="team_code" />
		</c:if>
		
		<h4 class="balloon_header">확대일정수립</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
		<img alt=닫기 src="/images/btn_close_layer.png">						</a>

		<div class="ly_sub">
			<!--라디오 버튼 선택 -->
			<!--버튼위치 -->
			<div class="pop_btn_location3">
				<span>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_8()" />
					</sec:authorize>
				</span>
			</div>
					
			<div id="flow_tb_1"  class="Npaper_type" style="height:625px;">
			
				<table class="tbl_type_ly tbl_td_w" summary="">
					<colgroup>
						<col width="140px" />
						<col width="" />
						<col width="140px" />
						<col width="" />
					</colgroup>
					<tr>
						<th scope="col">서비스 중단 시간</th>
						<td scope="col">
							${PkgModel.ser_downtime} 분
						</td>
						<th scope="col">영향 시스템</th>
						<td scope="col">
							${PkgModel.impact_systems}
						</td>
					</tr>
				</table>
				
				<table class="tbl_type_ly tbl_td_wc mt_20">
					<caption>초도적용장비</caption>
					<colgroup>
						<col width="130px" />
						<col width="130px" />
						<col width="100px" />
						<col width="" />
					</colgroup>
					<thead>
						<tr>
							<th>국사</th>
							<th>장비</th>
							<th>서비스지역</th>
							<th>적용일시</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${PkgModel.pkgEquipmentModelList4E}" varStatus="status">
							<tr>
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
									<div>${result.work_date} ${result.start_time1}:${result.start_time2} ~ ${result.end_time1}:${result.end_time2}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				
				<table class="tbl_type_ly tbl_td_wc mt_20">
					<caption>확대적용장비</caption>
					<colgroup>
						<col width="25px" />
						<col width="25px" />
						<col width="80px" />
						<col width="80px" />
						<col width="80px" />
						<col width="40px" />
						<col width="" />
						<col width="40px" />
					</colgroup>
					<thead>
						<tr>
							<th>
								<input type="checkbox" name="allCheckbox" onclick="fn_allCheck_E('${PkgModel.granted}','${PkgModel.session_user_group_id}');" />
							</th>
							<th>
								<input type="checkbox" name="allCheckbox_date" onclick="fn_allCheck_date('${PkgModel.granted}','${PkgModel.session_user_group_id}');"/>
							</th>
							<th>국사</th>
							<th>장비</th>
							<th>서비스지역</th>
							<th>주야</th>
							<th>적용일시</th>
							<th>수정</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
						<c:when test="${not empty PkgModel.pkgEquipmentModelList}">
							<c:forEach var="result" items="${PkgModel.pkgEquipmentModelList}" varStatus="status">						
								<input type="hidden" id= "non_check_seqs_${status.count}" name="non_check_seqs" />
								<input type="hidden" id= "all_check_seqs_${status.count}" name="all_check_seqs" />
								<input type="hidden" name="team_codes" value="${result.team_code}"/>							
					
								<tr id="color_line">
									<td>
										<c:choose>
											<c:when test="${PkgModel.granted eq 'OPERATOR' && result.team_code ne PkgModel.session_user_group_id}">
												<c:choose>
													<c:when test="${empty result.work_date}">
														<input type="checkbox" id= "non_check_seqs_${status.count}" name="check_seqs" onclick="fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" style="display: none;"/>
													</c:when>
													<c:otherwise>
														<input type="checkbox" id= "non_check_seqs_${status.count}" name="check_seqs" onclick="fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" checked style="display: none;"/>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${empty result.work_date}">
														<input type="checkbox" id= "non_check_seqs_${status.count}" name="check_seqs" onclick="fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" />
													</c:when>
													<c:otherwise>
														<input type="checkbox" id= "non_check_seqs_${status.count}" name="check_seqs" onclick="fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" checked/>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<div id="check_ok_${status.count}" style="display: none;">
											<c:choose>
												<c:when test="${PkgModel.granted eq 'OPERATOR' && result.team_code ne PkgModel.session_user_group_id}">
													<input type="checkbox" name="check_seqs_date" style="display: none;"/>
												</c:when>
												<c:otherwise>									
													<input type="checkbox" name="check_seqs_date"/>
												</c:otherwise>
											</c:choose>
										</div>
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
										<c:choose>
											<c:when test="${PkgModel.granted eq 'OPERATOR' && result.team_code ne PkgModel.session_user_group_id}">
												${result.ampm}
												<select name="ampms" style="display: none">
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
											</c:when>
											<c:otherwise>
												<select name="ampms" id="PkgModel_pkgEquipmentModel_ampm_${status.count}" style="display: none" onchange="fn_SelectAmPm(this, ${status.count})">
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
											</c:otherwise>
										</c:choose>									
									</td>
									<td>
										<div id="equipment_se_none_${status.count}">
											<c:if test="${PkgModel.granted eq 'OPERATOR' && result.team_code ne PkgModel.session_user_group_id}">
												<c:choose>
													<c:when test="${empty result.work_date}">
													</c:when>
													<c:otherwise>
														${result.work_date} ${result.start_time1}:${result.start_time2}~${result.end_time1}:${result.end_time2}
													</c:otherwise>
												</c:choose>
											</c:if>
										</div>
										<div id="equipment_se_date_${status.count}" style="display:none">
											<c:choose>
												<c:when test="${PkgModel.granted eq 'OPERATOR' && result.team_code ne PkgModel.session_user_group_id}">
													${result.work_date} ${result.start_time1}:${result.start_time2}~${result.end_time1}:${result.end_time2}
													<input name="work_dates" type="hidden" value="${result.work_date}" />
													<input name="start_times1" type="hidden" value="${result.start_time1}"/>
													<input name="start_times2" type="hidden" value="${result.start_time2}"/> 
													<input name="end_times1" type="hidden" value="${result.end_time1}"/>
													<input name="end_times2" type="hidden" value="${result.end_time2}"/>
												</c:when>
												<c:otherwise>
													<input id="PkgModel_pkgEquipmentModel_work_date_${status.count}" class="new_inp inp_w100px fl_left" type="text" value="${result.work_date}" name="work_dates" readonly="readonly"/>
													<input id="PkgModel_pkgEquipmentModel_start_time1_${status.count}" name="start_times1" class="inp" style="width:15px" maxlength="2" class="inp" type="text" value="${result.start_time1}"/>: 
													<input id="PkgModel_pkgEquipmentModel_start_time2_${status.count}" name="start_times2" class="inp" style="width:15px" maxlength="2" class="inp" type="text" value="${result.start_time2}"/> 
													~ <input id="PkgModel_pkgEquipmentModel_end_time1_${status.count}" name="end_times1" class="inp" style="width:15px" maxlength="2" class="inp" type="text" value="${result.end_time1}"/>:
													<input id="PkgModel_pkgEquipmentModel_end_time2_${status.count}" name="end_times2" class="inp" style="width:15px" maxlength="2" class="inp" type="text" value="${result.end_time2}"/>
													<c:if test="${pkgModel.status >= 8}">
														<img src="/images/btn_identify.gif" alt="확인" style="cursor:pointer; vertical-align: bottom;" onclick="fn_iwcs_view('${result.work_date}','${result.team_code}')" />
													</c:if>
												</c:otherwise>
											</c:choose>
										</div>
									</td>
									<td>${result.reg_user_name}</td>
								</tr>
								<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_seqs_count"/></c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="8">확대 적용할 장비가 없습니다.</td>
							</tr>
						</c:otherwise>
						</c:choose>
							
					</tbody>
				</table>
				
				<table class="tbl_type_ly tbl_td_w mt_20">
					<caption>선택 장비 일괄 등록</caption>
					<colgroup>
						<col width="25px" />
						<col width="25px" />
						<col width="" />
					</colgroup>
					<tr>
						<th>
							<input type="checkbox" checked="checked" disabled="disabled">
						</th>
						<th>
							<input type="checkbox" checked="checked" disabled="disabled">
						</th>
						<th>
							적용 일시
						</th>
						<td> 
							<select id="PkgModel_pkgEquipmentModel_ampm_All" onchange="fn_SelectAmPm_All(this)" class="new_inp fl_left mr03">
								<option value = "야간" selected="selected">야간</option>
								<option value = "주간">주간</option>
							</select>
							<input id="PkgModel_pkgEquipmentModel_work_date_All" class="new_inp inp_w100px fl_left" type="text" readonly/>
							<input id="PkgModel_pkgEquipmentModel_start_time1_All" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="02"/>
							<span class="fl_left mg03">:</span>
							<input id="PkgModel_pkgEquipmentModel_start_time2_All" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="00"/> 
							<span class="fl_left mg03">~</span>
							<input id="PkgModel_pkgEquipmentModel_end_time1_All" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="07"/>
							<span class="fl_left mg03">:</span>
							<input id="PkgModel_pkgEquipmentModel_end_time2_All" style="width:15px" maxlength="2" class="new_inp fl_left" type="text" value="00" />
<!-- 							<a href="javascript:fn_SelectAllCheck_E();"><img alt="적용" src="/images/btn_application.gif" align="middle"></a> -->
							<span class="btn_line_blue2 fl_left mg03"><a href="javascript:fn_SelectAllCheck_E();">적용</a></span>
						</td>
					</tr>
				</table>
				
				<div id="am_content">
					<table class="tbl_type_ly tbl_td_w mt_20">
						<caption>주간 사전작업 내용</caption>
						<tr>
							<th style="width:100%;">
								작업내용
							</th>
						</tr>
						<tr>
							<td style="width:100%;">
								<c:choose>
									<c:when test="${PkgModel.pkgStatusModel.prev_content eq '' || PkgModel.pkgStatusModel.prev_content eq null}">
<textarea name="pkgStatusModel.prev_content" rows="7" class="inp_w1" style="width:98.5%;" maxlength="600">
21:00 우회소통
03:30 세션삭제
05:50 세션삭제 완료
</textarea>								
									</c:when>
									<c:otherwise>
<textarea name="pkgStatusModel.prev_content" rows="7" class="inp_w1" style="width:95%;" maxlength="600">
${PkgModel.pkgStatusModel.prev_content}
</textarea>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<div class="pop_status_box" style="display:none;">		
			
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
			</div>
						
		</div>
 
		<div class="edge_cen" style="top:505px;"></div>
 
</body>

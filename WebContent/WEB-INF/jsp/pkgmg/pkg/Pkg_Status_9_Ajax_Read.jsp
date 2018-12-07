<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:parseNumber value = "${PkgModel.status}" var = "intStatus"/>
<c:set var="registerFlag" value="${intStatus < 7 ? '등록' : '수정'}" />

<body>
<!-- 부분저장 구분 값 -->
<input type="hidden" name="pkgStatusModel.update_gubun" value="${PkgModel.pkgStatusModel.update_gubun}"/>
<input type="hidden" name="pkgStatusModel.col22" id="pkgStatusModel_col22" value="${PkgModel.pkgStatusModel.col22}"/>

		<h4 class="balloon_header">확대결과 등록</h4>
		<div style="position: absolute; top: 5px; right:35px; z-index:10;">
			<img src="/images/btn_owms_link.gif" alt="owms 링크" style="cursor:pointer;" onclick="javascript:owms();" />
		</div>
		<a class="ly_close" href="javascript:fn_flow_init();">
			<img alt=닫기 src="/images/btn_close_layer.png">
		</a>

		<div class="ly_sub">
					
			<!--라디오 버튼 선택 -->
			
			<!--버튼위치 -->
			<div class="pop_btn_location3">
				<span>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
						<!-- 정상종료(부분저장시 or 전체 저장) -->
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_9('','save')" />
						
					</sec:authorize>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER">
						<img src="/images/btn_complete.gif" alt="완료" style="cursor:pointer;" onclick="fn_pkg_9('all_ok', 'complete')"/>
						
						<!-- 확대적용장비가 남아있어도 저장하고 종료 -->
						<img src="/images/btn_apply_end.gif" alt="PKG완료" style="cursor:pointer;" onclick="fn_pkg_9('', 'complete')" />						
					</sec:authorize>
				</span>
			</div>
			<p style="float: right; padding-top: 5px; padding-right: 10px; font-size: 14px; color: red;">
				※과금확인방법 FAQ 참고
			</p>
			<%-- <div id="flow_tb_1" style="display:block; margin-top:25px">
			
				<table class="tbl_type_ly" border="1" summary="">
					<colgroup>
						<col width="140px">
						<col width="*">
					</colgroup>
					<tr>
						<td>모니터링 기간</td>
						<td style="text-align:left; padding-left:10px;">
							<input id="pkgStatusModel_col1" name="pkgStatusModel.col1" onkeydown="fn_numbersonly(event);" style="ime-mode:disabled; width:30px; text-align:right;" class="inp" type="text" value="${PkgModel.pkgStatusModel.col1}"/> 일
						</td>
					</tr>
				</table>

				<table class="tbl_type_ly" border="1" summary="">
					<colgroup>
						<col width="170px">
						<col width="70px">
						<col width="*">
					</colgroup>
					<tr>
						<td scope="col" rowspan="2">현장 호시험 결과</td>
						<td scope="col" rowspan="2">
							<select id="pkgStatusModel_col2" name="pkgStatusModel.col2">
								<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
									<c:choose>
										<c:when test="${result.code == PkgModel.pkgStatusModel.col2}">
										<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
										</c:when>
										<c:otherwise>
										<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
						<td scope="col" align="left">
							 <input type="text" id="pkgStatusModel_col3" name="pkgStatusModel.col3" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col3}" />
						</td>
					</tr>
					<tr>
						<td scope="col" align="left">
							<div class="pop_system">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file22}" name="file22" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file22}" name="file22" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
				</table>
				
				<table class="tbl_type_ly" border="1" summary="">
					<colgroup>
						<col width="170px">
						<col width="70px">
						<col width="*">
					</colgroup>
					<tr>
						<td scope="col" rowspan="2">시스템 감시 사항<br/>: 부하/메모리/ALM/FLT/STS 등</td>
						<td scope="col" rowspan="2">
							<select id="pkgStatusModel_col4" name="pkgStatusModel.col4">
								<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
									<c:choose>
										<c:when test="${result.code == PkgModel.pkgStatusModel.col4}">
										<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
										</c:when>
										<c:otherwise>
										<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
						<td scope="col" align="left">
							 <input type="text" id="pkgStatusModel_col5" name="pkgStatusModel.col5" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col5}" />
						</td>
					</tr>
					<tr>
						<td scope="col" align="left">
							<div class="pop_system">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file23}" name="file23" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file23}" name="file23" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
				</table>
				
				<table class="tbl_type_ly" border="1" summary="">
					<colgroup>
						<col width="170px">
						<col width="70px">
						<col width="*">
					</colgroup>
					<tr>
						<td scope="col" rowspan="2">품질 감시 사항<br/>: 서비스 품질 등</td>
						<td scope="col" rowspan="2">
							<select id="pkgStatusModel_col6" name="pkgStatusModel.col6">
								<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
									<c:choose>
										<c:when test="${result.code == PkgModel.pkgStatusModel.col6}">
										<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
										</c:when>
										<c:otherwise>
										<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
						<td scope="col" align="left">
							 <input type="text" id="pkgStatusModel_col7" name="pkgStatusModel.col7" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col7}" />
						</td>
					</tr>
					<tr>
						<td scope="col" align="left">
							<div class="pop_system">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file24}" name="file24" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file24}" name="file24" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
				</table>
				
				<table class="tbl_type_ly" border="1" summary="">
					<colgroup>
						<col width="170px">
						<col width="70px">
						<col width="*">
					</colgroup>
					<tr>
						<td scope="col" rowspan="2">상용 과금 검증 결과</td>
						<td scope="col" rowspan="2">
							<select id="pkgStatusModel_col8" name="pkgStatusModel.col8">
								<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
									<c:choose>
										<c:when test="${result.code == PkgModel.pkgStatusModel.col8}">
										<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
										</c:when>
										<c:otherwise>
										<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
						<td scope="col" align="left">
							 <input type="text" id="pkgStatusModel_col9" name="pkgStatusModel.col9" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col9}" />
						</td>
					</tr>
					<tr>
						<td scope="col" align="left">
							<div class="pop_system">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file25}" name="file25" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file25}" name="file25" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
				</table>
				
				<table class="tbl_type_ly" border="1" summary="">
					<colgroup>
						<col width="170px">
						<col width="70px">
						<col width="*">
					</colgroup>
					<tr>
						<td scope="col" rowspan="2">기타 문제사항 및 F/U 내역</td>
						<td scope="col" rowspan="2">
							<select id="pkgStatusModel_col10" name="pkgStatusModel.col10">
								<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
									<c:choose>
										<c:when test="${result.code == PkgModel.pkgStatusModel.col10}">
										<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
										</c:when>
										<c:otherwise>
										<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
						<td scope="col" align="left">
							 <input type="text" id="pkgStatusModel_col11" name="pkgStatusModel.col11" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col11}" />
						</td>
					</tr>
					<tr>
						<td scope="col" align="left">
							<div class="pop_system">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file26}" name="file26" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file26}" name="file26" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
				</table>

			</div> --%>
			
			<!-- 본문 -->
			<div id="flow_tb_1" class="Npaper_type" style="height:570px;">
			
				<table class="tbl_type_ly tbl_td_wc">
					<caption>확대적용장비</caption>
					<colgroup>
						<!--<col width="50px" />-->
						<col width="80px" />
						<col width="80px" />
						<col width="100px" />
						<col width="" />
						<col width="95px" />
						<col width="95px" />
						<col width="40px" />
					</colgroup>
					<thead>
						<tr>	
							<th style="display: none;">&nbsp;</th>
							<th>국사</th>
							<th>장비</th>
							<th>서비스지역</th>
							<th>적용일시</th>
							<th>적용결과</th>
							<th>과금결과</th>
							<th>확인</th>
						</tr>
					</thead>

					<tbody>
						<c:choose>
						<c:when test="${not empty PkgModel.pkgEquipmentModelList}">
							<c:forEach var="result" items="${PkgModel.pkgEquipmentModelList}" varStatus="status">
							<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="work_result_count"/></c:if>
								<input type="hidden" name="ampms" value="${result.ampm}"/>
								<div id="equipment_se_date_${status.count}" style="display:none">
									<input type= "hidden" name= "work_dates" value="${result.work_date}"/>
									<input type= "hidden" id="PkgModel_pkgEquipmentModel_start_time1_${status.count}" name= "start_times1" value="${result.start_time1}"/>
									<input type= "hidden" id="PkgModel_pkgEquipmentModel_start_time2_${status.count}" name= "start_times2" value="${result.start_time2}"/>
									<input type= "hidden" id="PkgModel_pkgEquipmentModel_end_time1_${status.count}" name= "end_times1" value="${result.end_time1}"/>
									<input type= "hidden" id="PkgModel_pkgEquipmentModel_end_time2_${status.count}" name= "end_times2" value="${result.end_time2}"/>
								</div>
								<tr>
									<td style="display: none;">
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
										<c:choose>
											<c:when test="${empty result.work_date}">
												확대일정 수립 필요
											</c:when>
											<c:otherwise>
												${result.work_date}&nbsp;&nbsp;
												${result.start_time1} : ${result.start_time2} ~ ${result.end_time1} : ${result.end_time2}	
											</c:otherwise>
										</c:choose>				
									</td>
									<td>
										<c:choose>
											<c:when test="${empty result.work_date}">
												-
											</c:when>
											<c:otherwise>
												<select id="PkgModel_pkgEquipmentModel_work_result_${status.count}" name="work_result">
													<c:forEach var="result2" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
														<c:choose>
															<c:when test="${result2.code == result.work_result}">
															<option value="<c:out value='${result2.code}' />" selected><c:out value="${result2.codeName}" /></option>
															</c:when>
															<c:otherwise>
															<option value="<c:out value='${result2.code}' />"><c:out value="${result2.codeName}" /></option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>	
											</c:otherwise>
										</c:choose>		
									</td>
									<td>
										<c:choose>
											<c:when test="${empty result.work_date}">
												-
											</c:when>
											<c:otherwise>
												<select id="PkgModel_pkgEquipmentModel_charge_result_${status.count}" name="charge_result">
													<c:forEach var="result2" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
														<c:choose>
															<c:when test="${result2.code == result.charge_result}">
															<option value="<c:out value='${result2.code}' />" selected><c:out value="${result2.codeName}" /></option>
															</c:when>
															<c:otherwise>
															<option value="<c:out value='${result2.code}' />"><c:out value="${result2.codeName}" /></option>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>	
											</c:otherwise>
										</c:choose>		
									</td>
									<td>${result.reg_user_name}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="7">확대 적용할 장비가 없습니다.</td>
							</tr>
						</c:otherwise>
						</c:choose>
					</tbody>
				</table>

			</div>
			<!-- 본문 끝 -->
			
			<!-- 등록자 수정자 -->			

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
			</div>
						
		</div>
 
		<div class="edge_cen" style="top:575px;"></div>
 
</body>

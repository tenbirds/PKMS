<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:parseNumber value = "${PkgModel.status}" var = "intStatus"/>
<c:set var="registerFlag" value="${intStatus < 7 ? '등록' : '수정'}" />

<body>
	
<!-- 적용완료 구분 값 -->
<input type="hidden" id="apply_end" name="apply_end" value=""/>

<!-- 업데이트 구분 값 22번은 select 시 update_gubun은 현재 값 -->
<input type="hidden" name="pkgStatusModel.update_gubun" value="${PkgModel.pkgStatusModel.update_gubun}"/>
<input type="hidden" name="pkgStatusModel.col22" id="pkgStatusModel_col22" value="${PkgModel.pkgStatusModel.col22}"/>

		<h4 class="balloon_header">초도결과 등록</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
			<img alt=닫기 src="/images/btn_close_layer.png">
		</a>

		<div class="ly_sub">
		
			<!-- 말풍선 내용-->
			<div id="tip_message_1" class="tip_message" style="visibility:hidden;">
				<table style="border: 3px solid #F9E98E; color: #A27D35; background-color: #FBF7AA;">
					<tr>
						<td width="200px" height="100px">
							&nbsp;확대적용장비가 없을 경우 이 단계에서 패키지 작업이 완료됩니다.<br/> 만약, 확대적용장비가 있어 작업을 계속 진행할
							경우는 저장 버튼을 눌러 다음단계로 진행 하시기 바랍니다.
						</td>
					</tr>
				</table>
			</div>
			
			<!--라디오 버튼 선택 -->
			
			<!--버튼위치 -->
			<!-- div 버튼 숨기기
			<div class="pop_btn_location3">
			</div> -->
			
			<div class="pop_btn_location3 mb_10">
				<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
					<c:if test="${PkgModel.pkgStatusModel.update_gubun == 'N'}">
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_7('save')"/>
					</c:if>
					<c:if test="${PkgModel.pkgStatusModel.update_gubun == 'Y' && PkgModel.pkgStatusModel.col22 == 'save'}">
						<img src="/images/btn_complete.gif" alt="완료" style="cursor:pointer;" onclick="fn_pkg_7('complete')"/>&nbsp;
						<img src="/images/btn_apply_end.gif" alt="PKG완료" style="cursor:pointer;" onclick="fn_pkg_7_end()"
							onMouseOver="tip_message_over('tip_message_1')" onMouseMove="tip_message_over('tip_message_1')"
							onMouseOut="tip_message_out('tip_message_1')" />
					</c:if>
				</sec:authorize>
			</div>
<%-- 			<div class="pop_btn_location3">
				<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
					<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_7()" align="right"/>
				</sec:authorize>
			</div> --%>
			
<!-- 			1차 저장인지.. 완료인지..구분 보고서 형식 다르게.. -->
			<p class="btn_line_blue3" style="float: right; padding-top: 5px; padding-right: 10px; font-size: 14px;">
				<a href="javascript:fn_print();">
					[PKG 적용 결과 보고]
				</a>
			</p>
			
			<div id="flow_tb_1"  class="Npaper_type" style="height: 480px;">
				<table class="tbl_type_ly tbl_td_w sc_td_g" summary="">
					<caption>당일결과 등록</caption>
					<colgroup>
						<col width="170px">
						<col width="70px">
						<col width="*">
					</colgroup>
					<tr>
						<th scope="col">현장 호시험 결과</th>
						<td scope="col">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'N'}">
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
								</c:when>
								<c:otherwise>
									<input type="text" style="width: 45px;" id="pkgStatusModel_col2" name="pkgStatusModel.col2" value="${PkgModel.pkgStatusModel.col2}" readonly="readonly"/> 
								</c:otherwise>
							</c:choose>
						</td>
						<td scope="col">
							 <input type="text" id="pkgStatusModel_col3" name="pkgStatusModel.col3" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col3}"  maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						
							<div class="pop_system mt_5">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file17}" name="file17" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file17}" name="file17" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col">시스템 감시 사항<br/>: 부하/메모리/ALM/FLT/STS 등</th>
						<td scope="col">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'N'}">
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
								</c:when>
								<c:otherwise>
									<input type="text" style="width: 45px;" id="pkgStatusModel_col4" name="pkgStatusModel.col4" value="${PkgModel.pkgStatusModel.col4}" readonly="readonly"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td scope="col">
							 <input type="text" id="pkgStatusModel_col5" name="pkgStatusModel.col5" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col5}"  maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						
							<div class="pop_system mt_5">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file18}" name="file18" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file18}" name="file18" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col">품질 감시 사항<br/>: 서비스 품질 등</th>
						<td scope="col">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'N'}">
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
								</c:when>
								<c:otherwise>
									<input type="text" style="width: 45px;" id="pkgStatusModel_col6" name="pkgStatusModel.col6" value="${PkgModel.pkgStatusModel.col6}" readonly="readonly"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							 <input type="text" id="pkgStatusModel_col7" name="pkgStatusModel.col7" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col7}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						
							<div class="pop_system mt_5">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file19}" name="file19" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file19}" name="file19" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col">
							<em class="em_blue">
								상용 과금 검증 결과
							</em>
							<div class="help_notice"  style="text-align:left;">
								OWMS 과금검증결과를 <br/>확인하세요!
							</div>
							<img src="/images/btn_owms_link.gif" alt="owms 링크" style="cursor:pointer; margin-top: 12px;" onclick="javascript:owms();" />
						</th>
						<td scope="col">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'N'}">
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
								</c:when>
								<c:otherwise>
									<input type="text" style="width: 45px;" id="pkgStatusModel_col8" name="pkgStatusModel.col8" value="${PkgModel.pkgStatusModel.col8}" readonly="readonly"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							 <input type="text" id="pkgStatusModel_col9" name="pkgStatusModel.col9" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col9}"  maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						
							<div class="pop_system mt_5">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file20}" name="file20" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file20}" name="file20" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col">기타 문제사항 및 F/U 내역</th>
						<td scope="col">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'N'}">									
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
								</c:when>
								<c:otherwise>
									<input type="text" style="width: 45px;" id="pkgStatusModel_col10" name="pkgStatusModel.col10" value="${PkgModel.pkgStatusModel.col10}" readonly="readonly"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							 <input type="text" id="pkgStatusModel_col11" name="pkgStatusModel.col11" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col11}"  maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						
							<div class="pop_system mt_5">
								<c:choose>
									<c:when test="${registerFlag == '수정'}">
										 <ui:file attachFileModel="${PkgModel.file21}" name="file21" size="50" mode="update" />
									</c:when>
									<c:otherwise>
										 <ui:file attachFileModel="${PkgModel.file21}" name="file21" size="50" mode="create" />
									</c:otherwise>
								</c:choose>
							</div>
						</td>
					</tr>
				</table>
				
				<table class="tbl_type_ly tbl_td_w sc_td_g" summary="">
					<caption>최종결과 등록</caption>
					<colgroup>
						<col width="170px">
						<col width="70px">
						<col width="*">
					</colgroup>
					<tr>
						<th>모니터링 기간</th>
						<td colspan="2" style="text-align:left;">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'Y'}">
									<input id="pkgStatusModel_col1" name="pkgStatusModel.col1" onkeydown="fn_numbersonly(event);" style="ime-mode:disabled; width:30px; text-align:right;" maxlength=2 class="inp" type="text" value="${PkgModel.pkgStatusModel.col1}"/> 일
								</c:when>
								<c:otherwise>
									<input id="pkgStatusModel_col1" name="pkgStatusModel.col1" style="ime-mode:disabled; width:30px; text-align:right;" maxlength=2 class="inp" type="text" value="${PkgModel.pkgStatusModel.col1}" readonly="readonly"/> 일
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="col">시스템 감시 사항<br/>: 부하/메모리/ALM/FLT/STS 등</th>
						<td scope="col">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'Y'}">
									<select id="pkgStatusModel_col12" name="pkgStatusModel.col12">
										<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
											<c:choose>
												<c:when test="${result.code == PkgModel.pkgStatusModel.col12}">
												<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
												</c:when>
												<c:otherwise>
												<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</c:when>
								<c:otherwise>
									<input type="hidden" style="width: 45px;" id="pkgStatusModel_col12" name="pkgStatusModel.col12" value="${PkgModel.pkgStatusModel.col12}" readonly="readonly"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td scope="col">
							 <input type="text" id="pkgStatusModel_col13" name="pkgStatusModel.col13" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col13}"  maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						</td>
					</tr>
					<tr>
						<th scope="col">품질 감시 사항<br/>: 서비스 품질 등</th>
						<td scope="col">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'Y'}">
									<select id="pkgStatusModel_col14" name="pkgStatusModel.col14">
										<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
											<c:choose>
												<c:when test="${result.code == PkgModel.pkgStatusModel.col14}">
												<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
												</c:when>
												<c:otherwise>
												<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</c:when>
								<c:otherwise>
									<input type="hidden" style="width: 45px;" id="pkgStatusModel_col14" name="pkgStatusModel.col14" value="${PkgModel.pkgStatusModel.col14}" readonly="readonly"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							 <input type="text" id="pkgStatusModel_col15" name="pkgStatusModel.col15" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col15}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						</td>
					</tr>
					<tr>
						<th scope="col">
							<em class="em_blue">
								상용 과금 검증 결과
							</em>
							<div class="help_notice"  style="text-align:left;">
								OWMS 과금검증결과를 <br/>확인하세요!
							</div>
							<img src="/images/btn_owms_link.gif" alt="owms 링크" style="cursor:pointer; margin-top: 12px;" onclick="javascript:owms();" />
						</th>
						<td scope="col">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'Y'}">
									<select id="pkgStatusModel_col16" name="pkgStatusModel.col16">
										<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
											<c:choose>
												<c:when test="${result.code == PkgModel.pkgStatusModel.col16}">
												<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
												</c:when>
												<c:otherwise>
												<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</c:when>
								<c:otherwise>
									<input type="hidden" style="width: 45px;" id="pkgStatusModel_col16" name="pkgStatusModel.col16" value="${PkgModel.pkgStatusModel.col16}" readonly="readonly"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							 <input type="text" id="pkgStatusModel_col17" name="pkgStatusModel.col17" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col17}"  maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						</td>
					</tr>
					<tr>
						<th scope="col">기타 문제사항 및 F/U 내역</th>
						<td scope="col">
							<c:choose>
								<c:when test="${PkgModel.pkgStatusModel.update_gubun == 'Y' }">
									<select id="pkgStatusModel_col18" name="pkgStatusModel.col18">
										<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
											<c:choose>
												<c:when test="${result.code == PkgModel.pkgStatusModel.col18}">
												<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
												</c:when>
												<c:otherwise>
												<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</c:when>
								<c:otherwise>
									<input type="hidden" style="width: 45px;" id="pkgStatusModel_col18" name="pkgStatusModel.col18" value="${PkgModel.pkgStatusModel.col18}" readonly="readonly"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							 <input type="text" id="pkgStatusModel_col19" name="pkgStatusModel.col19" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col19}"  maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						</td>
					</tr>
				</table>
			</div>

			<div class="pop_status_box">
				<c:if test="${not empty PkgModel.pkgStatusModel.reg_date}">
					<div class="pop_status_reg">
						당일등록: ${PkgModel.pkgStatusModel.reg_user_name} (${PkgModel.pkgStatusModel.reg_date})
					</div>
				</c:if>
	
				<c:if test="${not empty PkgModel.pkgStatusModel.update_date}">
					<div class="pop_status_update">
						최종등록: ${PkgModel.pkgStatusModel.update_user_name} (${PkgModel.pkgStatusModel.update_date})
					</div>
				</c:if>
			</div>
						
		</div>
 
		<div class="edge_cen" style="top:260px;"></div>
 
</body>

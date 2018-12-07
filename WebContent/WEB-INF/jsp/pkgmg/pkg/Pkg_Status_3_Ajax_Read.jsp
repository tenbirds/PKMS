<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:parseNumber value = "${PkgModel.status}" var = "intStatus"/>
<c:set var="registerFlag" value="${intStatus < 3 ? '등록' : '수정'}" />

<body>
	<input type = "hidden" id="detailvariable_check" name="detailvariable_check" value ="${PkgModel.detailvariable_check}"/>
	
	<h4 class="balloon_header">상용검증완료</h4>
	
	<a class="ly_close" href="javascript:fn_flow_init();">
	<img alt=닫기 src="/images/btn_close_layer.png"> </a>
	
	<div class="ly_sub">
		<!--라디오 버튼 선택 -->
		
		<!--버튼위치 -->
		<div class="pop_btn_location3">
			<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
				<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_3()" />
				<img src="/images/btn_urgency.gif" alt="긴급검증" style="cursor:pointer;" onclick="fn_pkg_3('urgency')" />
			</sec:authorize>
		</div>
	
		<div class="help_notice mb_10">
			상용화검증내역(왼쪽) 메뉴의 상용검증결과를 'OK'또는 'COK'로 입력 후 검증완료를 진행해 주시기 바랍니다.
		</div>
	
		<div class="Npaper_type" style="height: 590px; width:100%;" id ="fn_pkg_3_tab">
			
			<table class="tbl_type_ly tbl_td_w">
				<caption>PKG 요약</caption>
				<colgroup>
					<col width="140px;">
					<col width="*">
				</colgroup>
				<tbody>
				<tr>
					<th>주요 보완내역</th>
					<td>
						<table width="98.5%" class="tbl_inner_table txt-right">
							<thead>
								<tr>	
									<th></th>
									<th>항목수</th>
									<th>검증내역개수</th>
									<th>개선내역개수</th>
									<th>검증진도율</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>신규</th>
									<td>${PkgModelProgress.new_col1 }</td>
									<td>${PkgModelProgress.new_col2 }</td>
									<td>${PkgModelProgress.new_col3 }</td>
									<td>
										<c:choose>
											<c:when test="${PkgModelProgress.new_col4 > 0 }">
												<fmt:formatNumber value="${PkgModelProgress.new_col4 / PkgModelProgress.new_col1 * 100 }" pattern="0" />
											</c:when>
											<c:otherwise>
												0
											</c:otherwise>
										</c:choose>
										%
									</td>
								</tr>
								<tr>
									<th>보완</th>
									<td>${PkgModelProgress.pn_col1 }</td>
									<td>${PkgModelProgress.pn_col2 }</td>
									<td>${PkgModelProgress.pn_col3 }</td>
									<td>
										<c:choose>
											<c:when test="${PkgModelProgress.pn_col4 > 0 }">
												<fmt:formatNumber value="${PkgModelProgress.pn_col4 / PkgModelProgress.pn_col1 * 100 }" pattern="0" />
											</c:when>
											<c:otherwise>
												0
											</c:otherwise>
										</c:choose>
										%
									</td>
								</tr>
								<tr>
									<th>개선</th>
									<td>${PkgModelProgress.cr_col1 }</td>
									<td>${PkgModelProgress.cr_col2 }</td>
									<td>${PkgModelProgress.cr_col3 }</td>
									<td>
										<c:choose>
											<c:when test="${PkgModelProgress.cr_col4 > 0 }">
												<fmt:formatNumber value="${PkgModelProgress.cr_col4 / PkgModelProgress.cr_col1 * 100 }" pattern="0" />
											</c:when>
											<c:otherwise>
												0
											</c:otherwise>
										</c:choose>
										%
									</td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<th>합계</th>
									<td>${PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1 }</td>
									<td>${PkgModelProgress.new_col2 + PkgModelProgress.pn_col2 + PkgModelProgress.cr_col2 }</td>
									<td>${PkgModelProgress.new_col3 + PkgModelProgress.pn_col3 + PkgModelProgress.cr_col3 }</td>
									<td>
										<c:choose>
											<c:when test="${(PkgModelProgress.new_col4 + PkgModelProgress.pn_col4 + PkgModelProgress.cr_col4) / (PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1) * 100 != 'NaN'}">
												<fmt:formatNumber value="${(PkgModelProgress.new_col4 + PkgModelProgress.pn_col4 + PkgModelProgress.cr_col4) / (PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1) * 100 }" pattern="0" />
											</c:when>
											<c:otherwise>
												0
											</c:otherwise>
										</c:choose>
										%
									</td>
								</tr>
							</tfoot>
						</table>
						<p class="tbl_inner_txtbox">
							${PkgModel.content }
						</p>
					</td>
				</tr>
				</tbody>
			</table>
			<table class="tbl_type_ly tbl_td_w">
				<caption>상용검증 계획/결과</caption>
				<tbody>
				<tr>
					<th>기능검증</th>
					<td>
						<h3>기능 검증계획</h3>
						<textarea style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" readonly="readonly">${PkgModelData.col21}</textarea>								
						<ui:file attachFileModel="${PkgModel.file94}" name="file94" size="50" mode="view" />
						<br/>
						<h3>기능 검증결과</h3>
						<textarea id="pkgStatusModel_col41" name="pkgStatusModel.col41"  style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);">${PkgModel.pkgStatusModel.col41}</textarea>
					</td>
				</tr>
				<c:if test="${PkgModel.non_yn != 'N'}">
					<tr>
						<th>비기능검증</th>
						<td>
							<h3>비기능 검증계획</h3>
							<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModelData.col4}</textarea>
							<ui:file attachFileModel="${PkgModel.file93}" name="file93" size="50" mode="view" />
							<%-- <br/>
							<h3>비기능 검증결과</h3>
							<textarea style="width: 625px; height: 50px;;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${PkgModelData.col8}</textarea> --%>
							<br/>
							<h3>비기능 검증결과</h3>
							<textarea style="width: 625px; height: 50px;;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${non.verify_content}</textarea>
						</td>
					</tr>
				</c:if>
				<c:if test="${PkgModel.vol_yn != 'N'}">
					<tr>
						<th>용량검증</th>
						<td>
							<h3>용량 검증계획</h3>
							<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModelData.col1}</textarea>
							<ui:file attachFileModel="${PkgModel.file90}" name="file90" size="50" mode="view" />
							<%-- <br/>
							<h3>용량 검증결과</h3>
							<textarea style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${PkgModelData.col5}</textarea> --%>
							<br/>
							<h3>용량 검증결과</h3>
							<textarea style="width: 625px; height: 50px;;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${vol.verify_content}</textarea>
						</td>
					</tr>
				</c:if>
				<c:if test="${PkgModel.cha_yn != 'N'}">
					<tr>
						<th>과금검증</th>
						<td>
							<h3>과금 검증계획</h3>
							<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly">${PkgModelData.col3}</textarea>
							<ui:file attachFileModel="${PkgModel.file92}" name="file92" size="50" mode="view" />
							<%-- <br/>
							<h3>과금 검증결과</h3>
							<textarea style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${PkgModelData.col7}</textarea> --%>
							<br/>
							<h3>과금 검증결과</h3>
							<textarea style="width: 625px; height: 50px;;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${cha.verify_content}</textarea>
						</td>
					</tr>
				</c:if>
				<c:if test="${PkgModel.sec_yn != 'N'}">
					<tr>
						<th>보안검증</th>
						<td>
							<h3>보안 검증계획</h3>
							<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModelData.col2}</textarea>
							<ui:file attachFileModel="${PkgModel.file91}" name="file91" size="50" mode="view" />
						</td>
					</tr>
				</c:if>
				</tbody>
			</table>
			
			<table class="tbl_type_ly tbl_td_w" summary="">
				<colgroup>
					<col width="170px">
					<col width="*">
				</colgroup>
				<tr>
					<th scope="col">검증일자 <span  class='necessariness'>*</span></th>
					<td scope="col" style="text-align:left; padding-left:10px;">
						<input id="pkgStatusModel_col1" name="pkgStatusModel.col1" class="new_inp inp_w100px fl_left" type="text" value="${PkgModel.pkgStatusModel.col1}" readonly/>
						<span class="fl_left mg03">~</span>
						<input id="pkgStatusModel_col2" name="pkgStatusModel.col2" class="new_inp inp_w100px fl_left" type="text" value="${PkgModel.pkgStatusModel.col2}" readonly/>
					</td>
				</tr>
			</table>
			
			<table class="tbl_type_ly  tbl_td_w sc_td_g" summary="">
			<caption>공급사 자체 검증</caption>
				<colgroup>
					<col width="170px">
					<col width="70px">
					<col width="*">
				</colgroup>
				<tr>
					<th scope="col" >보완내역별 시험 결과 <span  class='necessariness'>*</span></th>
					<td scope="col">
						<select id="pkgStatusModel_col3" name="pkgStatusModel.col3">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col3}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>
						 <input type="text" id="pkgStatusModel_col4" name="pkgStatusModel.col4" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col4}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						<div class="pop_system mt_5">
					 		<ui:file attachFileModel="${PkgModel.file37}" name="file37" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">Regression Test 및<br/>기본 검증 결과 <span  class='necessariness'>*</span></th>
					<td scope="col">
						<select id="pkgStatusModel_col5" name="pkgStatusModel.col5">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col5}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>
						 <input type="text" id="pkgStatusModel_col6" name="pkgStatusModel.col6" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col6}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
					
						<div class="pop_system mt_5">
							<%-- <c:choose>
								<c:when test="${registerFlag == '수정'}">
									 <ui:file attachFileModel="${PkgModel.file10}" name="file10" size="50" mode="update" />
								</c:when>
								<c:otherwise>
									 <ui:file attachFileModel="${PkgModel.file10}" name="file10" size="50" mode="create" />
								</c:otherwise>
							</c:choose> --%>
							
							<ui:file attachFileModel="${PkgModel.file5}" name="file5" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">
						성능 용량 시험 결과
					</th>
					<td scope="col">
						<select id="pkgStatusModel_col7" name="pkgStatusModel.col7">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col7}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td scope="col">
						 <input type="text" id="pkgStatusModel_col8" name="pkgStatusModel.col8" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col8}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
						
						<div class="pop_system mt_5">
				 			<ui:file attachFileModel="${PkgModel.file6}" name="file6" size="50" mode="view" />
						</div>
						
					</td>
				</tr>
			</table>

			<table class="tbl_type_ly  tbl_td_w sc_td_g" summary="">
				<caption>상용화 검증</caption>
				<colgroup>
					<col width="170px">
					<col width="70px">
					<col width="*">
				</colgroup>
				<tr>
					<th scope="col">보완내역서, <br/>기능 변경 요청서 <span  class='necessariness'>*</span></th>
					<td scope="col">
						<select id="pkgStatusModel_col13" name="pkgStatusModel.col13">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col13}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td scope="col">
						 <input type="text" id="pkgStatusModel_col14" name="pkgStatusModel.col14" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col14}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
					
						<div class="pop_system mt_5">
							<ui:file attachFileModel="${PkgModel.file3}" name="file3" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">보완내역별 검증 결과 <span  class='necessariness'>*</span></th>
					<td scope="col">
						<select id="pkgStatusModel_col15" name="pkgStatusModel.col15">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col15}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td scope="col">
						 <input type="text" id="pkgStatusModel_col16" name="pkgStatusModel.col16" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col16}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);" />
					
						<div class="pop_system mt_5">
							<ui:file attachFileModel="${PkgModel.file38}" name="file38" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">서비스 영향도 (로밍 포함)</th>
					<td scope="col">
						<select id="pkgStatusModel_col17" name="pkgStatusModel.col17">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col17}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td scope="col">
						 <input type="text" id="pkgStatusModel_col18" name="pkgStatusModel.col18" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col18}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);" />
					
						<div class="pop_system mt_5">
							<ui:file attachFileModel="${PkgModel.file12}" name="file12" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">과금 영향도 <span  class='necessariness'>*</span>	</th>
					<td scope="col">
						<select id="pkgStatusModel_col19" name="pkgStatusModel.col19">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col19}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td scope="col">
						 <input type="text" id="pkgStatusModel_col20" name="pkgStatusModel.col20" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col20}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);" />
					
						<div class="pop_system mt_5">
							<ui:file attachFileModel="${PkgModel.file39}" name="file39" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">작업절차서, S/W 블록 내역<br/>(list 및 size) <span  class='necessariness'>*</span></th>
					<td scope="col">
						<select id="pkgStatusModel_col21" name="pkgStatusModel.col21">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col21}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td scope="col">
						 <input type="text" id="pkgStatusModel_col22" name="pkgStatusModel.col22" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col22}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);" />
					
						<div class="pop_system mt_5">
							<ui:file attachFileModel="${PkgModel.file1}" name="file1" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">PKG 적용 후 check list <span  class='necessariness'>*</span></th>
					<td scope="col">
						<select id="pkgStatusModel_col23" name="pkgStatusModel.col23">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col23}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td>
						 <input type="text" id="pkgStatusModel_col24" name="pkgStatusModel.col24" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col24}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);" />
					
						<div class="pop_system mt_5">
							<ui:file attachFileModel="${PkgModel.file14}" name="file14" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">CoD/PoD 변경 사항,<br/>운용팀 공지사항 <span  class='necessariness'>*</span></th>
					<td scope="col">
						<select id="pkgStatusModel_col25" name="pkgStatusModel.col25">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col25}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td scope="col">
						 <input type="text" id="pkgStatusModel_col26" name="pkgStatusModel.col26" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col26}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);" />
					
						<div class="pop_system mt_5">
							<ui:file attachFileModel="${PkgModel.file15}" name="file15" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col">보안Guide 적용확인서 <span  class='necessariness'>*</span></th>
					<td scope="col">
						<select id="pkgStatusModel_col40" name="pkgStatusModel.col40">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col40}">
									<option value="<c:out value='${result.code}' />" selected><c:out value="${result.codeName}" /></option>
									</c:when>
									<c:otherwise>
									<option value="<c:out value='${result.code}' />"><c:out value="${result.codeName}" /></option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</td>
					<td scope="col">
						 <input type="text" id="pkgStatusModel_col39" name="pkgStatusModel.col39" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col39}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);" />
					
						<div class="pop_system mt_5">
							<ui:file attachFileModel="${PkgModel.file68}" name="file68" size="50" mode="view" />
						</div>
					</td>
				</tr>
				
				<c:if test="${not empty PkgModel.pkgStatusModel.reg_date}">
					<tr>
						<th scope="col" rowspan="2">검증완료 OK 이력</th>
						<td class="bg_gr">검증 승인자</td>
						<td><c:out value ="${PkgModel.pkgStatusModel.reg_user_name}"/></td>
					</tr>
					<tr>
						<td class="bg_gr">날짜 / 시간</td>
						<td>${PkgModel.pkgStatusModel.reg_date}</td>
					</tr>
				</c:if>
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
		</div>
					
	</div>
	
	<div class="edge_cen" style="top:130px;"></div>

</body>

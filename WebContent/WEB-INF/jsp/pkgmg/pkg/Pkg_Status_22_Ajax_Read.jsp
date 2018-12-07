<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<body>
	<input type = "hidden" id="detailvariable_check" name="detailvariable_check" value ="${PkgModel.detailvariable_check}"/>
	
	<h4 class="balloon_header">개발검증완료</h4>
	
	<a class="ly_close" href="javascript:fn_flow_init();">
	<img alt=닫기 src="/images/btn_close_layer.png"> </a>
	
	<div class="ly_sub">
	
		<!--버튼위치 -->
		<div class="pop_btn_location3 mb_10">
			<span>
				<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
					<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_22()" />
				</sec:authorize>
			</span>
		</div>
	
		<div class="help_notice">
			보완적용내역(왼쪽) 메뉴의 상용검증결과를 'OK'또는 'COK'로 입력 후 검증완료를 진행해 주시기 바랍니다.
		</div>

		<div id="flow_tb_1"  class="Npaper_type">
	
			<table class="tbl_type_ly tbl_td_w" summary="">
				<colgroup>
					<col width="170px">
					<col width="*">
				</colgroup>
				<tr>
					<th scope="col">개발 검증 일자</th>
					<td scope="col" style="text-align:left;">
						<input id="pkgStatusModel_col27" name="pkgStatusModel.col27" style="width:100px" class="new_inp fl_left" type="text" value="${PkgModel.pkgStatusModel.col27}" readonly/>
						<span class="fl_left mg03">~</span>
						<input id="pkgStatusModel_col28" name="pkgStatusModel.col28" style="width:100px" class="new_inp fl_left" type="text" value="${PkgModel.pkgStatusModel.col28}" readonly/>
					</td>
				</tr>
			</table>
			<br/>
			<table class="tbl_type_ly tbl_td_w sc_td_g" summary="">
			<caption>개발 검증</caption>
				<colgroup>
					<col width="170px">
					<col width="70px">
					<col width="*">
				</colgroup>
				<tr>
					<th scope="col" rowspan="2">개발 근거 문서</th>
					<td scope="col" rowspan="2">
						<select id="pkgStatusModel_col33" name="pkgStatusModel.col33">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col33}">
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
						 <input type="text" id="pkgStatusModel_col29" name="pkgStatusModel.col29" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col29}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
					</td>
				</tr>
				<tr>
					<td scope="col" align="left">
						<div class="pop_system">
					 		<ui:file attachFileModel="${PkgModel.file40}" name="file40" size="50" mode="view" /> &nbsp;
					 		<ui:file attachFileModel="${PkgModel.file41}" name="file41" size="50" mode="view" /> &nbsp;
					 		<ui:file attachFileModel="${PkgModel.file42}" name="file42" size="50" mode="view" /> &nbsp;
					 		<ui:file attachFileModel="${PkgModel.file43}" name="file43" size="50" mode="view" /> &nbsp;
					 		<ui:file attachFileModel="${PkgModel.file44}" name="file44" size="50" mode="view" /> &nbsp;
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col" rowspan="2">신규 기능 규격서</th>
					<td scope="col" rowspan="2">
						<select id="pkgStatusModel_col34" name="pkgStatusModel.col34">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col34}">
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
						 <input type="text" id="pkgStatusModel_col10" name="pkgStatusModel.col10" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col10}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
					</td>
				</tr>
				<tr>
					<td scope="col" align="left">
						<div class="pop_system">
							<ui:file attachFileModel="${PkgModel.file7}" name="file7" size="50" mode="view" /> &nbsp;
							<ui:file attachFileModel="${PkgModel.file45}" name="file45" size="50" mode="view" /> &nbsp;
							<ui:file attachFileModel="${PkgModel.file46}" name="file46" size="50" mode="view" /> &nbsp;
							<ui:file attachFileModel="${PkgModel.file47}" name="file47" size="50" mode="view" /> &nbsp;
							<ui:file attachFileModel="${PkgModel.file48}" name="file48" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col" rowspan="2">보완 내역서 <span  class='necessariness'>*</span></th>
					<td scope="col" rowspan="2">
						<select id="pkgStatusModel_col35" name="pkgStatusModel.col35">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col35}">
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
						 <input type="text" id="pkgStatusModel_col30" name="pkgStatusModel.col30" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col30}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
					</td>
				</tr>
				<tr>
					<td scope="col" align="left">
						<div class="pop_system">
				 			<ui:file attachFileModel="${PkgModel.file49}" name="file49" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file50}" name="file50" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file51}" name="file51" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file52}" name="file52" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file53}" name="file53" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col" rowspan="2">시험 절차서</th>
					<td scope="col" rowspan="2">
						<select id="pkgStatusModel_col36" name="pkgStatusModel.col36">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col36}">
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
						 <input type="text" id="pkgStatusModel_col31" name="pkgStatusModel.col31" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col31}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
					</td>
				</tr>
				<tr>
					<td scope="col" align="left">
						<div class="pop_system">
				 			<ui:file attachFileModel="${PkgModel.file54}" name="file54" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file55}" name="file55" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file56}" name="file56" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file57}" name="file57" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file58}" name="file58" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col" rowspan="2">코드 리뷰 및 SW<br/>아키텍처 리뷰</th>
					<td scope="col" rowspan="2">
						<select id="pkgStatusModel_col37" name="pkgStatusModel.col37">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col37}">
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
						 <input type="text" id="pkgStatusModel_col32" name="pkgStatusModel.col32" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col32}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
					</td>
				</tr>
				<tr>
					<td scope="col" align="left">
						<div class="pop_system">
				 			<ui:file attachFileModel="${PkgModel.file59}" name="file59" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file60}" name="file60" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file61}" name="file61" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file62}" name="file62" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file63}" name="file63" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col" rowspan="2">기능 검증 결과 <span class='necessariness'>*</span></th>
					<td scope="col" rowspan="2">
						<select id="pkgStatusModel_col38" name="pkgStatusModel.col38">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col38}">
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
						 <input type="text" id="pkgStatusModel_col12" name="pkgStatusModel.col12" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col12}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
					</td>
				</tr>
				<tr>
					<td scope="col" align="left">
						<div class="pop_system">
				 			<ui:file attachFileModel="${PkgModel.file8}" name="file8" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file64}" name="file64" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file65}" name="file65" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file66}" name="file66" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file67}" name="file67" size="50" mode="view" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="col" rowspan="2">성능용량 시험결과 </th>
					<td scope="col" rowspan="2">
						<select id="pkgStatusModel_col42" name="pkgStatusModel.col42">
							<c:forEach var="result" items="${PkgModel.pkgStatusModel.verify_result_3List}" varStatus="status">
								<c:choose>
									<c:when test="${result.code == PkgModel.pkgStatusModel.col42}">
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
						 <input type="text" id="pkgStatusModel_col41" name="pkgStatusModel.col41" style="width:430px" class="inp" value="${PkgModel.pkgStatusModel.col41}" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
					</td>
				</tr>
				<tr>
					<td scope="col" align="left">
						<div class="pop_system">
				 			<ui:file attachFileModel="${PkgModel.file69}" name="file69" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file70}" name="file70" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file71}" name="file71" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file72}" name="file72" size="50" mode="view" /> &nbsp;
				 			<ui:file attachFileModel="${PkgModel.file73}" name="file73" size="50" mode="view" />
						</div>
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
		</div>
					
	</div>
	
	<div class="edge_cen" style="top:140px;"></div>

</body>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<body>
		<input type="hidden" name="pkgStatusModel.update_gubun" value="${PkgModel.pkgStatusModel.update_gubun}"/>
		<input type="hidden" name="pkgStatusModel.col22" id="pkgStatusModel_col22" value="${PkgModel.pkgStatusModel.col22}"/>
		<h4 class="balloon_header">검증계획요청</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
			<img alt=닫기 src="/images/btn_close_layer.png">
		</a>

		<div class="ly_sub">
					
			<!--라디오 버튼 선택 -->
			
			<!--버튼위치 -->
			<div class="pop_btn_location3">
				<span>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_25('save')" />
						<img src="/images/btn_complete.gif" alt="완료" style="cursor:pointer;" onclick="fn_pkg_25('complete')" />
					</sec:authorize>
				</span>
			</div>
					
			<div id="flow_tb_1" class="Npaper_type" style="height:645px;">
				
				<h2>PKG 검증 계획</h2>
			
				<table class="tbl_type_ly tbl_td_w">
					<colgroup>
						<col width="140px;">
						<col width="*">
						<col width="140px;">
						<col width="20%">
					</colgroup>
					<tr>
						<th>PKG 명</th>
						<td>${PkgModel.title }</td>
						<th>대표 담당자</th>
						<td>${PkgModel.system_user_name}</td>
					</tr>
					<tr>
						<th>대상 시스템</th>
						<td>${PkgModel.system_name_org}</td>
						<th>PKG 버전</th>
						<td>${PkgModel.ver}</td>
					</tr>
				</table>
				
				<table class="tbl_type_ly tbl_td_w">
				
					<colgroup>
						<col width="140px;">
						<col width="*">
					</colgroup>
					
					<thead>
						<tr>
							<th colspan="2" class="tt_head">PKG 주요내역</th>
						</tr>
					<thead>
					<tbody>
						<tr>
							<th>주요 보완내역</th>
							<td>
									<table width="98.5%" class="tbl_inner_table txt-right">
										<colgroup>
											<col width="15%">
											<col width="15%">
											<col width="20%;">
											<col width="20%;">
											<col width="20%;">
										</colgroup>
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
						<tr>
							<th>기능검증</th>
							<td>
									<h3>Smoke Test 내역</h3>
									<textarea id="pkgStatusModel_col19" name="pkgStatusModel.col19"  style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${PkgModel.pkgStatusModel.col19}</textarea>
									
									<c:choose>
										<c:when test="${PkgModel.on_yn == 'N'}">
											<div id="on" style="display: none; margin-right: 5px;"  >
										</c:when>
										<c:otherwise>
											<div id="on" style="display: block; margin-right: 5px;">
										</c:otherwise>
									</c:choose>
									
										<h3>일정 :</h3>
										<input id="pkgStatusModel_col17" name="pkgStatusModel.col17" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col17}" readonly="readonly"/>
										~
										<input id="pkgStatusModel_col18" name="pkgStatusModel.col18" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col18}" readonly="readonly"/>
									</div>
									<div style="position:relative;">
										<c:if test="${PkgModel.on_yn == 'Z'}">
											<input type="checkbox" style="position:relative; top:3px;" id="on_check" onclick="fn_checkbox_yn_click(this, 'on');" checked="checked" />
											<label for="on_check" style=""> 
												검증필요
											</label>
										</c:if>
										<c:if test="${PkgModel.on_yn == 'N'}">
											<label for="on_check" >
												<input type="checkbox" style="position:relative; top:3px;" id="on_check" onclick="fn_checkbox_yn_click(this, 'on');" /> 검증필요
											</label>
										</c:if>
										<c:if test="${PkgModel.on_yn == 'S' || PkgModel.on_yn == 'Y'}">
											<input type="checkbox" style="position:relative; top:3px;" id="on_check" checked="checked" readonly="readonly" /> 검증필요
										</c:if>
									</div>
									<br/>
									<h3>담당자 :</h3>
									<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'VU'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
									<br/>
									<h3>기능 검증계획</h3>
									<textarea id="pkgStatusModel_col21" name="pkgStatusModel.col21"  style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);">${PkgModel.pkgStatusModel.col21}</textarea>
									<c:choose>
										<c:when test="${PkgModel.file94 != null }">
											<ui:file attachFileModel="${PkgModel.file94}" name="file94" size="50" mode="update" />
										</c:when>
										<c:otherwise>
											<ui:file attachFileModel="${PkgModel.file94}" name="file94" size="50" mode="create" />
										</c:otherwise>
									</c:choose>
							</td>
						</tr>
						<tr>
							<th>비기능검증</th>
							<td>
									<h3>Smoke Test 내역</h3>
									<textarea id="pkgStatusModel_col8" name="pkgStatusModel.col8"  style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${PkgModel.pkgStatusModel.col8}</textarea>
									<br/>
									
									<c:choose>
										<c:when test="${PkgModel.non_yn == 'N'}">
											<div id="non" style="display: none; margin-right: 5px;"  >
										</c:when>
										<c:otherwise>
											<div id="non" style="display: block; margin-right: 5px;">
										</c:otherwise>
									</c:choose>
									
										<h3>일정 :</h3>
										<input id="pkgStatusModel_col15" name="pkgStatusModel.col15" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col15}" readonly="readonly"/>
										~
										<input id="pkgStatusModel_col16" name="pkgStatusModel.col16" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col16}" readonly="readonly"/>
									</div>
									<div style="position:relative;">
										<c:if test="${PkgModel.non_yn == 'Z'}">
											<label for="non_check" >
												<input type="checkbox" style="position:relative; top:3px;" id="non_check" onclick="fn_checkbox_yn_click(this, 'non');" checked="checked" /> 검증필요
											</label>
										</c:if>
										<c:if test="${PkgModel.non_yn == 'N'}">
											<label for="non_check" >
												<input type="checkbox" style="position:relative; top:3px;" id="non_check" onclick="fn_checkbox_yn_click(this, 'non');" /> 검증필요
											</label>
										</c:if>
										<c:if test="${PkgModel.non_yn == 'S' || PkgModel.non_yn == 'Y'}">
											<input type="checkbox" style="position:relative; top:3px;" id="non_check" checked="checked" readonly="readonly" /> 검증필요
										</c:if>
									</div>
									<br/>
									<h3>담당자 :</h3>
									<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'NO'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
									<br/>
									<h3>비기능 검증계획</h3>
									<textarea id="pkgStatusModel_col4" name="pkgStatusModel.col4"  style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);">${PkgModel.pkgStatusModel.col4}</textarea>
									
									<c:choose>
										<c:when test="${PkgModel.file93 != null }">
											<ui:file attachFileModel="${PkgModel.file93}" name="file93" size="50" mode="update" />
										</c:when>
										<c:otherwise>
											<ui:file attachFileModel="${PkgModel.file93}" name="file93" size="50" mode="create" />
										</c:otherwise>
									</c:choose>
							</td>
						</tr>
						<tr>
							<th>용량검증</th>
							<td>
									<h3>Smoke Test 내역</h3>
									<textarea id="pkgStatusModel_col5" name="pkgStatusModel.col5"  style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${PkgModel.pkgStatusModel.col5}</textarea>
									
									<c:choose>
										<c:when test="${PkgModel.vol_yn == 'N'}">
											<div id="vol" style="display: none; margin-right: 5px;"  >
										</c:when>
										<c:otherwise>
											<div id="vol" style="display: block; margin-right: 5px;">
										</c:otherwise>
									</c:choose>
									
										<h3>일정 : </h3>
										<input id="pkgStatusModel_col9" name="pkgStatusModel.col9" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col9}" readonly="readonly"/>
										~
										<input id="pkgStatusModel_col10" name="pkgStatusModel.col10" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col10}" readonly="readonly"/>
									</div>
									
									<div style="position:relative;">
										<c:if test="${PkgModel.vol_yn == 'Z'}">
											<label for="vol_check" >
												<input type="checkbox" style="position:relative; top:3px;" id="vol_check" onclick="fn_checkbox_yn_click(this, 'vol');" checked="checked"/> 검증필요 &nbsp;&nbsp;
											</label>
										</c:if>
										<c:if test="${PkgModel.vol_yn == 'N'}">
											<label for="vol_check" >
												<input type="checkbox" style="position:relative; top:3px;" id="vol_check" onclick="fn_checkbox_yn_click(this, 'vol');" /> 검증필요 &nbsp;&nbsp;
											</label>
										</c:if>
										<c:if test="${PkgModel.vol_yn == 'S' || PkgModel.vol_yn == 'Y'}">
											<input type="checkbox" style="position:relative; top:3px;" id="vol_check" checked="checked" readonly="readonly"  /> 검증필요 &nbsp;&nbsp;
										</c:if>
									</div>
									<br/>
									<h3>담당자 :</h3>
									<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'VO'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
									<br/>
									<h3>용량 검증계획</h3>
									<br/>
									<textarea id="pkgStatusModel_col1" name="pkgStatusModel.col1"  style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);">${PkgModel.pkgStatusModel.col1}</textarea>
									
									<c:choose>
										<c:when test="${PkgModel.file90 == null }">
											<ui:file attachFileModel="${PkgModel.file90}" name="file90" size="50" mode="create" />
										</c:when>
										<c:otherwise>
											<ui:file attachFileModel="${PkgModel.file90}" name="file90" size="50" mode="update" />
										</c:otherwise>
									</c:choose>
							</td>
						</tr>
						<tr>
							<th>과금검증</th>
							<td>
									<h3>Smoke Test 내역</h3>
									<textarea id="pkgStatusModel_col7" name="pkgStatusModel.col7"  style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${PkgModel.pkgStatusModel.col7}</textarea>
									
									<c:choose>
										<c:when test="${PkgModel.cha_yn == 'N'}">
											<div id="cha" style="display: none; margin-right: 5px;"  >
										</c:when>
										<c:otherwise>
											<div id="cha" style="display: block; margin-right: 5px;">
										</c:otherwise>
									</c:choose>
										<h3>일정 : </h3>
										<input id="pkgStatusModel_col13" name="pkgStatusModel.col13" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col13}" readonly="readonly"/>
										~
										<input id="pkgStatusModel_col14" name="pkgStatusModel.col14" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col14}" readonly="readonly"/>
									</div>
									
									<div style="position:relative;">
										<c:if test="${PkgModel.cha_yn == 'Z'}">
											<label for="cha_check" >
												<input type="checkbox" style="position:relative; top:3px;" id="cha_check" onclick="fn_checkbox_yn_click(this, 'cha');" checked="checked" /> 검증필요 &nbsp;&nbsp;
											</label>
										</c:if>
										<c:if test="${PkgModel.cha_yn == 'N'}">
											<label for="cha_check" >
												<input type="checkbox" style="position:relative; top:3px;" id="cha_check" onclick="fn_checkbox_yn_click(this, 'cha');" /> 검증필요 &nbsp;&nbsp;
											</label>
										</c:if>
										<c:if test="${PkgModel.cha_yn == 'S' || PkgModel.cha_yn == 'Y'}">
											<input type="checkbox" style="position:relative; top:3px;" id="cha_check" checked="checked" readonly="readonly" /> 검증필요 &nbsp;&nbsp;
										</c:if>
									</div>									
									<br/>
									<h3>담당자 :</h3>
									<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'CH'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
									<br/>
									
									<h3>과금 검증계획</h3>
									<textarea id="pkgStatusModel_col3" name="pkgStatusModel.col3"  style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);">${PkgModel.pkgStatusModel.col3}</textarea>
									
									<c:choose>
										<c:when test="${PkgModel.file92 != null }">
											<ui:file attachFileModel="${PkgModel.file92}" name="file92" size="50" mode="update" />
										</c:when>
										<c:otherwise>
											<ui:file attachFileModel="${PkgModel.file92}" name="file92" size="50" mode="create" />
										</c:otherwise>
									</c:choose>
							</td>
						</tr>
						<tr>
							<th>보안검증
							<h4 title="
PKMS에서 검증 계획이 승인 되면 SmartGuard 사이트로 이동하여, 
진단대상 장비에 대해서 
 1> 진단관리 > 장비/에이전트 등록
 2> 진단관리 > 진단실행
을 해주시면 됩니다.
							" style="margin-top: 5px;"><img src="/images/btn_question.png"></h4></th>
							<td>
								
									<h3>Smoke Test 내역</h3>
									<textarea id="pkgStatusModel_col6" name="pkgStatusModel.col6"  style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${PkgModel.pkgStatusModel.col6}</textarea>
									
									<c:choose>
										<c:when test="${PkgModel.sec_yn == 'N'}">
											<div id="sec" style="display: none; margin-right: 5px;"  >
										</c:when>
										<c:otherwise>
											<div id="sec" style="display: block; margin-right: 5px;">
										</c:otherwise>
									</c:choose>
									
										<h3>일정 : </h3>
										<input id="pkgStatusModel_col11" name="pkgStatusModel.col11" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col11}" readonly="readonly"/>
										~
										<input id="pkgStatusModel_col12" name="pkgStatusModel.col12" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col12}" readonly="readonly"/>
									</div>
									<div style="position:relative;">
										<c:if test="${PkgModel.sec_yn == 'Z'}">
											<label for="sec_check" >
												<input type="checkbox" style="position:relative; top:3px;" id="sec_check" onclick="fn_checkbox_yn_click(this, 'sec');" checked="checked"/> 검증필요 &nbsp;&nbsp;
											</label>
										</c:if>
										<c:if test="${PkgModel.sec_yn == 'N'}">
											<label for="sec_check" >
												<input type="checkbox" style="position:relative; top:3px;" id="sec_check" onclick="fn_checkbox_yn_click(this, 'sec');" /> 검증필요 &nbsp;&nbsp;
											</label>
										</c:if>
										<c:if test="${PkgModel.sec_yn == 'S' || PkgModel.sec_yn == 'Y'}">
											<input type="checkbox" style="position:relative; top:3px;" id="sec_check" checked="checked" readonly="readonly"  /> 검증필요 &nbsp;&nbsp;
										</c:if>
									</div>
									<br/>
									<h3>담당자 :</h3>
									<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'SE'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
									<br/>
									
									<h3>보안 검증계획</h3>
									<textarea id="pkgStatusModel_col2" name="pkgStatusModel.col2"  style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);">${PkgModel.pkgStatusModel.col2}</textarea>
									

									<c:choose>
										<c:when test="${PkgModel.file91 != null }">
											<ui:file attachFileModel="${PkgModel.file91}" name="file91" size="50" mode="update" />
										</c:when>
										<c:otherwise>
											<ui:file attachFileModel="${PkgModel.file91}" name="file91" size="50" mode="create" />
										</c:otherwise>
									</c:choose>

							</td>
						</tr>
					</tbody>
				</table>
				
				<table class="tbl_type_ly tbl_td_w text-center mt_20" summary="">
					<thead>
					<tr>
						<th style="width:50px;">&nbsp;</th>
						<th style="width:100px;">승인</th>
						<th style="width:110px;">이름</th>
						<th style="width:110px;">부서</th>
						<th style="width:110px;">전화번호</th>
						<th style="width:110px;">이메일</th>
					</tr>
					</thead>
					<tbody>
							
				<c:choose>
					<c:when test="${pkgModel.status == '2'}">
						<c:forEach var="result" items="${PkgModel.systemUserModelList}" varStatus="status">
							<tr>
								<td>
									<c:choose>
										<c:when test="${empty result.user_id}">
											<input type="checkbox" name="check_seqs" value="${result.user_id}" />
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="check_seqs" value="${result.user_id}" checked/>
										</c:otherwise>
									</c:choose>
								</td>
								<td>${status.count} 차</td>
								<td>${result.user_name}</td>
								<td>${result.sosok}</td>
								<td>${result.user_phone}</td>
								<td>${result.user_email}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${PkgModel.pkgUserModelList}" varStatus="status">
							<tr>
								<td>
									<c:choose>
										<c:when test="${result.use_yn == 'Y'}">
									<input type="checkbox" name="check_seqs" value="${result.user_id}" checked />
										</c:when>
										<c:otherwise>
									<input type="checkbox" name="check_seqs" value="${result.user_id}"/>
										</c:otherwise>
									</c:choose>
								</td>
								<td>${status.count} 차</td>
								<td>${result.user_name}</td>
								<td>${result.sosok}</td>
								<td>${result.user_phone}</td>
								<td>${result.user_email}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
										
					</tbody>
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
 
		<div class="edge_cen"></div>

</body>

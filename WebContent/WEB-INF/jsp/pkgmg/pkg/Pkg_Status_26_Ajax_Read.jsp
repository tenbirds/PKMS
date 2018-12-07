<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<body>
	<input type="hidden" name="pkgStatusModel.update_gubun" value="${PkgModel.pkgStatusModel.update_gubun}"/>
	<input type="hidden" name="pkgStatusModel.col23" id="pkgStatusModel_col23" value="${PkgModel.pkgStatusModel.col23}"/>
	<input type="hidden" name="pkgStatusModel.col24" id="pkgStatusModel_col24" value="${PkgModel.pkgStatusModel.col24}"/>
	<input type="hidden" name="pkgStatusModel.col25" id="pkgStatusModel_col25" value="${PkgModel.pkgStatusModel.col25}"/>
	<input type="hidden" name="pkgStatusModel.col26" id="pkgStatusModel_col26" value="${PkgModel.pkgStatusModel.col26}"/>
	<c:choose>
		<c:when test="${pkgModel.user_active_user_id == PkgModel.session_user_id}">
			<input type="hidden" id="active_enabled" name="active_enabled" value="Y"/>
		</c:when>
		<c:otherwise>
			<input type="hidden" id="active_enabled" name="active_enabled" value="N"/>
		</c:otherwise>
	</c:choose>

	<script>
	</script>

		<h4 class="balloon_header">검증계획승인</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
			<img alt=닫기 src="/images/btn_close_layer.png">
		</a>

		<div class="ly_sub">
		
			<div class="Npaper_type" style="height: 645px;">
			
				<h2>PKG 상용검증 승인</h2>
				
				<table class="tbl_type_ly tbl_td_w first-tbl">
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
							<th colspan="2" class="tt_head">PKG 상용검증 승인</th>
						</tr>
					</thead>
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
						<tr>
							<th>기능검증</th>
							<td>
								<h3>일정 : </h3>
								<input id="pkgStatusModel_col17" name="pkgStatusModel.col17" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col17}" readonly="readonly"/>
								~
								<input id="pkgStatusModel_col18" name="pkgStatusModel.col18" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col18}" readonly="readonly"/>
								<c:if test="${PkgModel.on_yn == 'Y' || PkgModel.on_yn == 'S' || PkgModel.on_yn == 'Z'}">
									<input type="checkbox" checked="checked" disabled="disabled" /> 검증필요
								</c:if>
								<c:if test="${PkgModel.on_yn == 'N'}">
									<input type="checkbox" disabled="disabled"/> 검증필요
								</c:if>
								<br/>									
								
								<h3>담당자 :</h3>
								<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
									<c:if test="${result.charge_gubun == 'VU'}">
										${result.sosok}-${result.user_name}M&nbsp;
									</c:if>
								</c:forEach>
								<br/>									
								
								<h3>기능 검증계획</h3>
								<textarea id="pkgStatusModel_col21" name="pkgStatusModel.col21"  style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" readonly="readonly">${PkgModel.pkgStatusModel.col21}</textarea>								
								<ui:file attachFileModel="${PkgModel.file94}" name="file94" size="50" mode="view" />
							</td>
						</tr>
						<tr>
							<th>비기능검증</th>
							<td>
								<h3>일정 : </h3>
								<input id="pkgStatusModel_col15" name="pkgStatusModel.col15" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col15}" readonly="readonly"/>
								~
								<input id="pkgStatusModel_col16" name="pkgStatusModel.col16" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col16}" readonly="readonly"/>
								<c:if test="${PkgModel.non_yn == 'Y' || PkgModel.non_yn == 'S' || PkgModel.non_yn == 'Z'}">
									<input type="checkbox" checked="checked" disabled="disabled" /> 검증필요
								</c:if>
								<c:if test="${PkgModel.non_yn == 'N'}">
									<input type="checkbox" disabled="disabled" /> 검증필요
								</c:if>
								<br/>
								
								<h3>담당자 :</h3>
								<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
									<c:if test="${result.charge_gubun == 'NO'}">
										${result.sosok}-${result.user_name}M&nbsp;
									</c:if>
								</c:forEach>
								<br/>
								<c:choose>
									<c:when test="${PkgModel.non_yn == 'N'}">
										<h3>비기능 미검증 사유</h3>
										<textarea id="pkgStatusModel_col8" name="pkgStatusModel.col8"  style="width: 625px; height: 50px;;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly" >${PkgModel.pkgStatusModel.col8}</textarea>
									</c:when>
									<c:otherwise>
										<h3>비기능 검증계획</h3>
										<textarea id="pkgStatusModel_col4" name="pkgStatusModel.col4"  style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col4}</textarea>
										<ui:file attachFileModel="${PkgModel.file93}" name="file93" size="50" mode="view" />
									</c:otherwise>
								</c:choose>
								
							</td>
						</tr>
						<tr>
							<th>용량검증</th>
							<td>
								<h3>일정 : </h3>
								<input id="pkgStatusModel_col9" name="pkgStatusModel.col9" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col9}" readonly="readonly"/>
								~
								<input id="pkgStatusModel_col10" name="pkgStatusModel.col10" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col10}" readonly="readonly"/>
								<c:if test="${PkgModel.vol_yn == 'Y' || PkgModel.vol_yn == 'S' || PkgModel.vol_yn == 'Z'}">
									<input type="checkbox" checked="checked" disabled="disabled" /> 검증필요
								</c:if>
								<c:if test="${PkgModel.vol_yn == 'N'}">
									<input type="checkbox" disabled="disabled" /> 검증필요
								</c:if>
								<br/>
								
								<h3>담당자 :</h3>
								<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
									<c:if test="${result.charge_gubun == 'VO'}">
										${result.sosok}-${result.user_name}M&nbsp;
									</c:if>
								</c:forEach>
								<br/>
								<c:choose>
									<c:when test="${PkgModel.vol_yn == 'N'}">
										<h3>용량 미검증 사유</h3>
										<br/>
										<textarea id="pkgStatusModel_col5" name="pkgStatusModel.col5"  style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly" >${PkgModel.pkgStatusModel.col5}</textarea>
									</c:when>
									<c:otherwise>
										<h3>용량 검증계획</h3>
										<textarea id="pkgStatusModel_col1" name="pkgStatusModel.col1"  style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col1}</textarea>
										<ui:file attachFileModel="${PkgModel.file90}" name="file90" size="50" mode="view" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>과금검증</th>
							<td>
								<h3>일정 : </h3>
								<input id="pkgStatusModel_col13" name="pkgStatusModel.col13" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col13}" readonly="readonly"/>
								~
								<input id="pkgStatusModel_col14" name="pkgStatusModel.col14" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col14}" readonly="readonly"/>
								<c:if test="${PkgModel.cha_yn == 'Y' || PkgModel.cha_yn == 'S' || PkgModel.cha_yn == 'Z'}">
									<input type="checkbox" checked="checked" disabled="disabled" /> 검증필요
								</c:if>
								<c:if test="${PkgModel.cha_yn == 'N'}">
									<input type="checkbox" disabled="disabled" /> 검증필요
								</c:if>
								<br/>
								
								<h3>담당자 :</h3>
								<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
									<c:if test="${result.charge_gubun == 'CH'}">
										${result.sosok}-${result.user_name}M&nbsp;
									</c:if>
								</c:forEach>
								<br/>
								<c:choose>
									<c:when test="${PkgModel.cha_yn == 'N'}">
										<h3>과금 미검증 사유</h3>
										<textarea id="pkgStatusModel_col7" name="pkgStatusModel.col7"  style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly" >${PkgModel.pkgStatusModel.col7}</textarea>
									</c:when>
									<c:otherwise>
										<h3>과금 검증계획</h3>
										<textarea id="pkgStatusModel_col3" name="pkgStatusModel.col3"  style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly">${PkgModel.pkgStatusModel.col3}</textarea>
										<ui:file attachFileModel="${PkgModel.file92}" name="file92" size="50" mode="view" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>보안검증</th>
							<td>
								<h3>일정 : </h3>
								<input id="pkgStatusModel_col11" name="pkgStatusModel.col11" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col11}" readonly="readonly"/>
								~
								<input id="pkgStatusModel_col12" name="pkgStatusModel.col12" style="width:70px" class="inp" type="text" value="${PkgModel.pkgStatusModel.col12}" readonly="readonly"/>
								<c:if test="${PkgModel.sec_yn == 'Y' || PkgModel.sec_yn == 'S' || PkgModel.sec_yn == 'Z'}">
									<input type="checkbox" checked="checked" disabled="disabled" /> 검증필요
								</c:if>
								<c:if test="${PkgModel.sec_yn == 'N'}">
									<input type="checkbox" disabled="disabled" /> 검증필요
								</c:if>
								<br/>
								
								<h3>담당자 :</h3>
								<c:forEach var="result" items="${PkgModel.systemUserModelList_25}" varStatus="status">
									<c:if test="${result.charge_gubun == 'SE'}">
										${result.sosok}-${result.user_name}M&nbsp;
									</c:if>
								</c:forEach>
								<br/>
								<c:choose>
									<c:when test="${PkgModel.sec_yn == 'N'}">
										<h3>보안 미검증 사유</h3>
										<textarea id="pkgStatusModel_col6" name="pkgStatusModel.col6"  style="width: 625px; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly" >${PkgModel.pkgStatusModel.col6}</textarea>
									</c:when>
									<c:otherwise>
										<h3>보안 검증계획</h3>
										<textarea id="pkgStatusModel_col2" name="pkgStatusModel.col2"  style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col2}</textarea>
										<ui:file attachFileModel="${PkgModel.file91}" name="file91" size="50" mode="view" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
				
				<table class="tbl_type_ly tbl_td_w text-center mt_20" summary="">
					<thead>
					<tr>
						<th style="width:160px;">상태</th>
						<th style="width:40px;">승인</th>
						<th style="width:70px;">이름</th>
						<th style="width:100px;">승인일시</th>
						<th>comment</th>
					</tr>
					</thead>
					
					<tbody>
							
					<c:forEach var="result" items="${PkgModel.pkgUserModelList}" varStatus="status">
						<tr>
							<td>
								<c:choose>
									<c:when test="${result.status == 'F'}">
										<font color="blue">승인</font>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${result.ord == PkgModel.user_active_status and result.user_id == PkgModel.session_user_id}">
												<img src="/images/btn_approbation.gif" alt="승인" style="cursor:pointer;" onclick="fn_pkg_26('${result.ord}')"/>
												<img src="/images/btn_return.gif" alt="반려" style="cursor:pointer;" onclick="fn_pkg_26('return')" />
											</c:when>
											<c:otherwise>
												<font color="gray">요청</font>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
							<td>${status.count} 차</td>
							<td>${result.user_name}</td>
							<td>
								<c:choose>
									<c:when test="${not empty result.update_date}">
										${result.update_date}
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							<td style="text-align:left; padding-left:10px;word-break:break-all">
								<c:choose>
									<c:when test="${result.status == 'F'}">
										${result.au_comment}&nbsp;
									</c:when>
									<c:otherwise>
										<c:if test="${result.ord == PkgModel.user_active_status and result.user_id == PkgModel.session_user_id}">
											<input id="au_comment" name="au_comment" style="width:380px" class="inp" type="text" value="${result.au_comment}"  maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
										</c:if>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							
						</tr>
					</c:forEach>
									
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

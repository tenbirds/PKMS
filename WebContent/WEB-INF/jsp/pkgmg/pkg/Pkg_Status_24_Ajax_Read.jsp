<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<body>
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

		<h4 class="balloon_header">개발완료 승인</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
		<img alt=닫기 src="/images/btn_close_layer.png">						</a>

		<div class="ly_sub">
					
			<!--라디오 버튼 선택 -->
			<div class="caption_select">
				<input name="check_flow_tb" type="radio" value="1" onclick="fn_flow_tb();" checked="checked" /> 개발승인
				<input name="check_flow_tb" type="radio" value="2" onclick="fn_flow_tb();" /> 반려
			</div>
			
			<!--버튼위치 -->
			<div class="pop_btn_location3" style="display:none">
				<span>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_24()" />
					</sec:authorize>
				</span>
			</div>
					
			<div id="flow_tb_1" class="Npaper_type" style="padding-top:30px; height: 420px;">
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
						<td>${PkgModel.dev_system_user_name}</td>
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
					</tbody>
				</table>
				<table class="tbl_type_ly tbl_td_w" summary="">
					<colgroup>
						<col width="140px">
						<col width="*">
					</colgroup>
					<tr>
						<th>
							승인요청 Comment
						</th>
						<td>
							<textarea id="pkgStatusModel_col1" name="pkgStatusModel.col1"  style="width: 98.5%; height: 50px;" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);" readonly="readonly">${PkgModel.dev_comment}</textarea>
						</td>
					</tr>
				</table>
				
				<table class="tbl_type_ly tbl_td_wc" summary="">
					<thead>
					<tr>
						<th style="width:60px;">상태</th>
						<th style="width:40px;">승인</th>
						<th style="width:70px;">이름</th>
						<th style="width:110px;">승인일시</th>
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
												<img src="/images/btn_approbation.gif" alt="승인" style="cursor:pointer;" onclick="fn_pkg_24('${result.ord}')"/>
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
											<input id="au_comment" name="au_comment" style="width:300px" class="inp" type="text" value="${result.au_comment}"  maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
										</c:if>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							
						</tr>
					</c:forEach>
									
					</tbody>
				</table>
				<br/>
			</div>

			<div id="flow_tb_2" style="padding-top:10px; display:none;">
			
				<table class="tbl_type_ly tbl_td_w mt_20">
					<colgroup>
						<col width="140px">
						<col width="*">
					</colgroup>
					<tr>
						<td scope="col">반려 사유 <span  class='necessariness'>*</span></td>
						<td scope="col">
							<textarea id="pkgStatusModel_col20" name="pkgStatusModel.col20" style="width:520px" class="inp_w1" rows="14"  maxlength="1000" onkeydown="fn_detail_variable_textarea_check(this);">${PkgModel.pkgStatusModel.col20}</textarea>
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
 
		<div class="edge_cen" ></div>
 
</body>

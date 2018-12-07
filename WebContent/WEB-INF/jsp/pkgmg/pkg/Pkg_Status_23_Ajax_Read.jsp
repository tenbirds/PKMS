<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<body>
		<h4 class="balloon_header">개발완료 보고</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
			<img alt=닫기 src="/images/btn_close_layer.png">	
		</a>

		<div class="ly_sub">
			
			<!--버튼위치 -->
			<div class="pop_btn_location3 mb_10">
				<span>
					<c:if test="${pkgModel.status == '22' || pkgModel.status_dev == '22'}">
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_23()"/>						
					</c:if>
				</span>
			</div>
			<div class="help_notice">
				승인자 선택 후 저장 시 1차 승인자에게 SMS/E-Mail이 전송됩니다.
			</div>
			<!--라디오 버튼 선택 -->
					
			<div id="flow_tb_1"  class="Npaper_type"  style="height:425px;">
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
							<textarea id="pkgStatusModel_col1" name="pkgStatusModel.col1"  style="width: 98.5%; height: 50px;" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);">${PkgModel.pkgStatusModel.col1}</textarea>
						</td>
					</tr>
				</table>
				
				<table class="tbl_type_ly tbl_td_wc" summary="">
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
					<c:when test="${pkgModel.status == '22' || pkgModel.status_dev == '22'}">
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
				<br/>
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

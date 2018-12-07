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

		<h4 class="balloon_header">초도승인</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
		<img alt=닫기 src="/images/btn_close_layer.png">						</a>

		<div class="ly_sub">
					
			<!--라디오 버튼 선택 -->
			<div class="mb_10">
				<input name="check_flow_tb" type="radio" value="1" onclick="fn_flow_tb();" checked="checked" /> 초도승인
				<input name="check_flow_tb" type="radio" value="2" onclick="fn_flow_tb();" /> 반려
			</div>
			
			<!--버튼위치 -->
			<div class="pop_btn_location3" id="flow_6_save_button" style="display:none">
				<span>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_6()" />
					</sec:authorize>
				</span>
			</div>
					
			<div id="flow_tb_1"  class="Npaper_type">
			
				<table class="tbl_type_ly tbl_td_wc" summary="">
					<colgroup>
						<col width="60px;" />
						<col width="40px;" />
						<col width="70px;" />
						<col width="110px;" />
						<col width="" />
					</colgroup>
					<thead>
					<tr>
						<th>상태</th>
						<th>승인</th>
						<th>이름</th>
						<th>승인일시</th>
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
<%-- 												${pkgModel.status == '5'} --%>
<%-- 												<img src="/images/btn_approbation.gif" alt="승인" style="cursor:pointer;" onclick="fn_pkg_6('${result.ord}')" onload="work_popup(6)"/> --%>
												<img src="/images/btn_approbation.gif" alt="승인" style="cursor:pointer;" onclick="fn_pkg_6('${result.ord}')"/>
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
				
			</div>

			<div id="flow_tb_2" style="display:none;">
				<table class="tbl_type_ly">
					<colgroup>
						<col width="140px">
						<col width="*">
					</colgroup>
					<tr>
						<td scope="col">반려 사유 <span  class='necessariness'>*</span></td>
						<td scope="col" style="text-align:left; padding-left:10px;">
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
				<div class="pop_status_update btn_line_blue3">
					<a href="javascript:fn_print();">[PKG 요약 보고]</a>
				</div>
			</div>
		</div>
 
		<div class="edge_cen"></div>
 
</body>

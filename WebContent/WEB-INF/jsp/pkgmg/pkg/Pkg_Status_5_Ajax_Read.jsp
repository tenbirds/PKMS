<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<body>
		<h4 class="balloon_header">초도적용 승인요청</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
		<img alt=닫기 src="/images/btn_close_layer.png">						</a>

		<div class="ly_sub">
					
			<!--라디오 버튼 선택 -->
			
			<!--버튼위치 -->
			<div class="pop_btn_location3">
				<span>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
<%-- 						${pkgModel.status == '4'} --%>
<!-- 						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_5()" onload="work_popup(5)"/>						 -->
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_5()" />
					</sec:authorize>
				</span>
			</div>
					
			<div id="flow_tb_1" class="Npaper_type" style="height:235px;">
			
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
					<c:when test="${pkgModel.status == '4'}">
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
				<div class="pop_status_update btn_line_blue3">
					<a href="javascript:fn_print();">[PKG 요약 보고]</a>
				</div>
			</div>
						
		</div>
 
		<div class="edge_cen"></div>

</body>

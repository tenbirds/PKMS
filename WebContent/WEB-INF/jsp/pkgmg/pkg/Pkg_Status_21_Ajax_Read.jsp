<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<body>

		<h4 class="balloon_header">개발검증접수</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
			<img alt=닫기 src="/images/btn_close_layer.png">	
		</a>

		<div class="ly_sub">
			
			<!--버튼위치 -->
			<div class="pop_btn_location3">
				<span>
					<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
						<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="fn_pkg_21()" />
					</sec:authorize>
				</span>
			</div>
					
			<!--라디오 버튼 선택 -->
			<div class="mb_10">
				<input name="check_flow_tb" type="radio" value="1" onclick="fn_flow_tb();" checked="checked" /> 검증접수
				<input name="check_flow_tb" type="radio" value="2" onclick="fn_flow_tb();" /> 반려
			</div>
					
			<div id="flow_tb_1" class="Npaper_type" style="height:230px;">
			
				<table class="tbl_type_ly tbl_td_w" summary="">
					<colgroup>
						<col width="140px">
						<col width="*">
					</colgroup>
					<tbody>
					<tr>
						<th scope="col">검증 승인일 <span class='necessariness'>*</span></th>
						<td scope="col" style="text-align:left; padding-left:10px;">
							<input id="pkgStatusModel_col1" name="pkgStatusModel.col1" style="width:100px" class="new_inp fl_left mg03" type="text" value="${PkgModel.pkgStatusModel.col1}" readonly/>
						</td>
					</tr>
					<tr>
						<th>검증 Comment<br/>PKG검증장소<br/>요청사항 <span class='necessariness'>*</span></th>
						<td>
							<textarea id="pkgStatusModel_col2" name="pkgStatusModel.col2" style="width: 520px; height: 130px;" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);">${PkgModel.pkgStatusModel.col2}</textarea>
						</td>
					</tr>
					</tbody>
				</table>
				
			</div>

			<div id="flow_tb_2" style="display:none;">
				<table class="tbl_type_ly tbl_td_w">
					<colgroup>
						<col width="140px">
						<col width="*">
					</colgroup>
					<tr>
						<th scope="col">반려 사유 <span  class='necessariness'>*</span></th>
						<td scope="col" style="text-align:left; padding-left:10px;">
							<textarea id="pkgStatusModel_col20" name="pkgStatusModel.col20" maxlength="1000" onkeydown="fn_detail_variable_textarea_check(this);"  style="width:520px" class="inp_w1" rows="14">${PkgModel.pkgStatusModel.col20}</textarea>
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
 
		<div class="edge_cen"></div>
 
 
</body>

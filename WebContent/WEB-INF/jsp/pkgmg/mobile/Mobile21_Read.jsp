<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
	pageContext.setAttribute("cr", "\r");
%>

<html>
<head>
	<meta name="decorator" content="mobile" />
	<meta name="viewport" content="width=480, initial-scale=-1, user-scalable=yes,target-densitydpi=device-dpi" />
	<script type="text/javaScript" defer="defer">
		function fn_readList() {
			var form = document.getElementById("Pkg21Model");
			form.action = "/Main.do";
			form.submit();
		}
		
		function fn_save_162(ord){
			$("#save_status").val("162");
			$("#ord").val(ord);
			create_status();
		}
		
		function fn_save_152(ord){
			$("#save_status").val("152");
			$("#ord").val(ord);
			create_status();
		}
		
		function fn_save_112(ord){
			$("#save_status").val("112");
			$("#ord").val(ord);
			create_status();
		}
		
		function fn_save_114(ord){
			$("#save_status").val("114");
			$("#ord").val(ord);
			create_status();
		}
		
		function fn_save_122(ord){
			if($.trim($("#au_comment").val()) == ""){
				alert("승인 시 comment를 입력하세요.");
				$("#au_comment").focus();
				return;
			}
			$("#save_status").val("122");
			$("#ord").val(ord);
			create_status();
		}
		
		function fn_save_124(ord){
			$("#save_status").val("124");
			$("#ord").val(ord);
			create_status();
		}
		
		function fn_save_132(ord){
			if($.trim($("#au_comment").val()) == ""){
				alert("승인 시 comment를 입력하세요.");
				$("#au_comment").focus();
				return;
			}
			$("#save_status").val("132");
			$("#ord").val(ord);
			create_status();
		}
		
		function fn_after_142(ord){
			if($.trim($("#au_comment").val()) == ""){
				alert("승인 시 comment를 입력하세요.");
				$("#au_comment").focus();
				return;
			}
			
			$("#ord").val(ord);
			
			if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
				doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_After_Update.do", "fn_callback_status");
			}else{
				return;
			}
		}
		
		function fn_save_142(ord){
			if($.trim($("#au_comment").val()) == ""){
				alert("승인 시 comment를 입력하세요.");
				$("#au_comment").focus();
				return;
			}
			$("#save_status").val("142");
			$("#ord").val(ord);
			create_status();
		}
		
		function fn_reject_132(){
			$("#save_status").val("131");
			fn_reject_rollback();
		}
		
		function fn_reject_142(){
			$("#save_status").val("141");
			alert("반려 시 모든 확대일정 및 결과가 삭제됩니다.");
			fn_reject_rollback();
		}

		function create_status(){
			if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
				doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Status_Create.do", "fn_callback_status");
			}else{
				return;
			}
		}
		
		function fn_callback_status(){
			$("#status_chk").val("F");
			
			var form = document.getElementById("Pkg21Model");
			form.action = "/pkgmg/mobile/Mobile_21Read.do";
			form.submit();
		}
		
		function fn_reject_rollback(){
			if(confirm("이전 단계로 돌아갑니다.\n반려하시겠습니까?")) {
				doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Status_Reject.do", "fn_callback_status");
			}else{
				return;
			}
		}
	</script>
</head>

<body>
	<form:form commandName="Pkg21Model" method="post" onsubmit="return false;">

	<!--탑1 -->
	<div class="mob_top1 fl_wrap">
		<h1>
			PKMS <span>Package Management system</span>
		</h1>
		<!--로그아웃 -->
		<div class="mob_logout">
			<a href="/Login_Delete.do">
				<img src="/images/mob_btn_logout.png" alt="로그아웃" />
			</a>
		</div>
	</div>

	<!--탑2 -->
	<div class="mob_top2">
		<div class="mob_title1 fl_wrap">
			<!--타이틀 -->
			<h2><strong>PKMS</strong> 승인요청</h2>
		</div>
		<!--텍스트 -->
		<div class="mob_top2_tex1">
			<strong>${Pkg21Model.session_user_name}</strong>
			님의 <u>승인관련 패키지 검증/과정</u> 목록입니다
		</div>
	</div>
	
	<!--컨텐츠 -->
	<div class="mob_contents">
	
		<form:hidden path="pkg_seq" />
		<form:hidden path="select_status" />
		<form:hidden path="status" />
		<form:hidden path="status_dev" />
		<form:hidden path="read_gubun" />
		<form:hidden path="dev_yn" />
		<form:hidden path="system_seq" />
		<form:hidden path="content" />
		<form:hidden path="content_pn" />
		<form:hidden path="content_cr" />
		<form:hidden path="content_self" />
		<form:hidden path="content_invest" />
		<form:hidden path="save_status" />
		<form:hidden path="cha_yn" />
		<form:hidden path="vol_yn" />
		<form:hidden path="sec_yn" />
		<form:hidden path="ser_downtime" />
		<form:hidden path="impact_systems" />
		
		<form:hidden path="master_file_id" />
		<form:hidden path="title" />
		
		<form:hidden path="ord" />
		
		<form:hidden path="status_chk" />
		<form:hidden path="charge_gubun" />
		
		<form:hidden path="col1" />
		<form:hidden path="col2" />
		<form:hidden path="col3" />
		<form:hidden path="col4" />
		<form:hidden path="col5" />
		<form:hidden path="col6" />
		<form:hidden path="col7" />
		<form:hidden path="col8" />
		<form:hidden path="col9" />
		<form:hidden path="col10" />
		<form:hidden path="col11" />
		<!--버튼위치 -->
		<div class="mob_top2">
			<!--목록 -->
			<div class="mob_btn2 btn_line_blue3">
				<span><a href="javascript:fn_readList();">목록</a></span>
			</div>
		</div>

		<div class="caption">기본정보</div>
		<table class="tbl_type11" cellspacing="0" style="width:100%; table-layout: fixed;">
			<colgroup>
				<col width="20%" />
				<col width="80%" />
			</colgroup>
			<tbody>
				<tr>
					<th>시스템</th>
					<td>
						${Pkg21Model.system_name}
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td>${Pkg21Model.title}</td>
				</tr>
				<tr>
					<th>신규</th>
					<td>
						<pre>${Pkg21Model.content}</pre>
					</td>
				</tr>
				<tr>
					<th>운용투자</th>
					<td>
						<pre>${Pkg21Model.content_invest}</pre>
					</td>
				</tr>
				<tr>
					<th>PN</th>
					<td>
						<pre>${Pkg21Model.content_pn}</pre>
					</td>
				</tr>
				<tr>
					<th>CR</th>
					<td>
						<pre>${Pkg21Model.content_cr}</pre>
					</td>
				</tr>
				<tr>
					<th>자체보완</th>
					<td>
						<pre>${Pkg21Model.content_self}</pre>
					</td>
				</tr>
				<tr>
					<th>TTM</th>
					<td>
						<pre>${Pkg21Model.ttm}</pre>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="caption">시험결과</div>
		<table class="tbl_type11" cellspacing="0" style="width:100%; table-layout: fixed;">
			<colgroup>
				<col width="19%">
				
				<col width="27%">
				<col width="27%">
				<col width="27%">
			</colgroup>
			<thead>
				<tr>
					<th>시험항목</th>
					<th>SVT결과<br/>(OK/NOK/COK/POK)</th>
					<th>DVT결과<br/>(OK/NOK/COK/POK)</th>
					<th>CVT결과<br/>(OK/NOK/COK/POK)</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty Pkg21Model.pkg21FileModelList}">
					<tr>
						<td colspan="4">
							시험결과가 존재하지 않습니다.
						</td>
					</tr>
				</c:if>
				<c:forEach var="result" items="${Pkg21Model.pkg21FileModelList}" varStatus="status">
					<tr>
						<td>
							<c:choose>
								<c:when test="${result.test_item == 'NEW'}">
									신규
								</c:when>
								<c:when test="${result.test_item == 'ABN'}">
									Abnoramal 항목
								</c:when>
								<c:when test="${result.test_item == 'OP'}">
									운용투자
								</c:when>
								<c:when test="${result.test_item == 'REG'}">
									Regression 항목
								</c:when>
								<c:when test="${result.test_item == 'RM'}">
									RM 항목
								</c:when>
								<c:when test="${result.test_item == 'SELF'}">
									자체보완
								</c:when>
								<c:otherwise>
									${result.test_item}
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							${result.svt_ok} / ${result.svt_nok} / ${result.svt_cok} / ${result.svt_pok}
						</td>
						<td>
							${result.dvt_ok} / ${result.dvt_nok} / ${result.dvt_cok} / ${result.dvt_pok}
						</td>
						<td>
							${result.cvt_ok} / ${result.cvt_nok} / ${result.cvt_cok} / ${result.cvt_pok}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<c:if test="${Pkg21Model.status_chk == 'R'}">
			<div class="caption">승인 목록</div>
			<table class="tbl_type12" cellspacing="0">
				<c:choose>
					<c:when test="${Pkg21Model.charge_gubun == 'AU' || Pkg21Model.charge_gubun == 'AS' || Pkg21Model.charge_gubun == 'LA'}">
						<colgroup>
							<col width="20%">
							<col width="15%">
							<col width="20%">
							<col width="20%">
							<col width="20%">
						</colgroup>
					</c:when>
					<c:otherwise>
						<colgroup>
							<col width="20%">
							<col width="15%">
							<col width="30%">
							<col width="30%">
						</colgroup>
					</c:otherwise>
				</c:choose>
				
				<thead>
					<tr>
						<th>승인</th>
						<th>이름</th>
						<th>부서</th>
						<c:if test="${Pkg21Model.charge_gubun == 'AU' || Pkg21Model.charge_gubun == 'AS' || Pkg21Model.charge_gubun == 'LA'}">
							<th>Comment</th>
						</c:if>
						<th>승인일시</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty Pkg21Model.pkgUserModelList}">
						<tr>
							<td colspan="4">
								현재 본인의 승인단계가 아닙니다.
							</td>
						</tr> 
					</c:if>
					<c:forEach var="result" items="${Pkg21Model.pkgUserModelList}" varStatus="status">
						<tr>
							<td class="td_center">
								<c:choose>
									<c:when test="${result.status == 'F'}">
										<font color="blue">승인</font>
									</c:when>
									<c:otherwise>
										<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
											<span class="btn_wrap">
												<c:choose>
													<c:when test="${Pkg21Model.charge_gubun == 'CA'}">
														<a href="javascript:fn_save_162('${result.ord}');" class="btn btn_red">승인</a>
													</c:when>
													<c:when test="${Pkg21Model.charge_gubun == 'VA'}">
														<a href="javascript:fn_save_152('${result.ord}');" class="btn btn_red">승인</a>
													</c:when>
													<c:when test="${Pkg21Model.charge_gubun == 'DA'}">
														<a href="javascript:fn_save_112('${result.ord}');" class="btn btn_red">승인</a>
													</c:when>
													<c:when test="${Pkg21Model.charge_gubun == 'DR'}">
														<a href="javascript:fn_save_114('${result.ord}');" class="btn btn_red">승인</a>
													</c:when>
													<c:when test="${Pkg21Model.charge_gubun == 'AU'}">
														<a href="javascript:fn_save_122('${result.ord}');" class="btn btn_red">승인</a>
													</c:when>
													<c:when test="${Pkg21Model.charge_gubun == 'AR'}">
														<a href="javascript:fn_save_124('${result.ord}');" class="btn btn_red">승인</a>
													</c:when>
													<c:when test="${Pkg21Model.charge_gubun == 'AS'}">
														<a href="javascript:fn_save_132('${result.ord}');" class="btn btn_red">승인</a>
														<a href="javascript:fn_reject_132();" class="btn btn_blue">반려</a>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${Pkg21Model.status > 141}">
																<a href="javascript:fn_after_142('${result.ord}');" class="btn btn_red">승인</a>
																<a href="javascript:fn_reject_142();" class="btn btn_blue">반려</a>
															</c:when>
															<c:otherwise>
																<a href="javascript:fn_save_142('${result.ord}');" class="btn btn_red">승인</a>
																<a href="javascript:fn_reject_142();" class="btn btn_blue">반려</a>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
											</span>
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
							<td>${result.user_name}</td>
							<td>${result.sosok}</td>
							<c:if test="${Pkg21Model.charge_gubun == 'AU' || Pkg21Model.charge_gubun == 'AS' || Pkg21Model.charge_gubun == 'LA'}">
								<td>
									<c:choose>
										<c:when test="${result.status == 'F'}">
											${result.au_comment}
										</c:when>
										<c:otherwise>
											<c:if test="${result.ord == Pkg21Model.user_active_status and result.user_id == Pkg21Model.session_user_id}">
												<form:input path="au_comment" maxlength="100" class="new_inp inp_w90" />
											</c:if>
										</c:otherwise>
									</c:choose>
								</td>
							</c:if>
							<td>
								<c:choose>
									<c:when test="${result.status == 'F'}">
										<font color="blue">승인</font>
										<c:if test="${not empty result.update_date}">
											(${result.update_date})
										</c:if>
									</c:when>
									<c:otherwise>
										미승인
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		
		<c:choose>
			<c:when test="${Pkg21Model.status > 140}">
				<div class="caption">초도적용 장비</div>
				<table class="tbl_type12" cellspacing="0">
					<colgroup>
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="55%">
					<col width="15%">
				</colgroup>
				<thead>
					<tr>
						<th>국사</th>
						<th>장비명</th>
						<th>서비스지역</th>
						<th>적용일시</th>
						<th>담당</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${Pkg21Model.pkgEquipmentModelList4E}" varStatus="status">
						<tr>
							<td class="td_center">
								${result.team_name}
							</td>
							<td class="td_center">
								${result.equipment_name}
							</td>
							<td class="td_center">
								${result.service_area}
							</td>
							<td class="td_center">
								${result.start_date} ${result.start_time1}:${result.start_time2}
								 ~ 
								${result.end_date} ${result.end_time1}:${result.end_time2}
							</td>
							<td class="td_center">
								${result.reg_user_name} [${result.reg_user_sosok}]
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
				<div class="caption">확대적용 장비</div>
				<table class="tbl_type12" cellspacing="0">
					<colgroup>
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="55%">
					</colgroup>
					<thead>
						<tr>
							<th style="display: none;"></th>
							<th>국사</th>
							<th>장비명</th>
							<th>서비스지역</th>
							<th>적용일시</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${Pkg21Model.pkgEquipmentModelList}" varStatus="status">
							<tr>
								<td style="display: none;">
									<c:choose>
										<c:when test="${empty result.work_result}">
											<c:choose>
												<c:when test="${empty result.start_date}">
													<input type="checkbox" name="check_seqs_e" onchange="javascript:fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" />
												</c:when>
												<c:otherwise>
													<input type="checkbox" name="check_seqs_e" onchange="javascript:fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" checked/>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<input type="hidden" name="check_seqs_e" value="N" />
										</c:otherwise>
									</c:choose>
								</td>
								<td class="td_center">
									${result.team_name}
								</td>
								<td class="td_center">
									${result.equipment_name}
								</td>
								<td class="td_center">
									${result.service_area}
								</td>
								<td class="td_center sysc_inp mg02">
									<c:choose>
										<c:when test="${empty result.start_date}">
											일정 미적용 장비
										</c:when>
										<c:otherwise>
											${result.ampm} ${result.start_date} ${result.start_time1}:${result.start_time2}
											 ~ 
											${result.end_date} ${result.end_time1}:${result.end_time2}
										</c:otherwise>
									</c:choose>

									<select style="display: none;" name="ampms" id="PkgModel_pkgEquipmentModel_ampm_${status.count}">
										<option value="${result.ampm}">${result.ampm}</option>
									</select>
									<input name="start_dates" value = "${result.start_date}" type=hidden>
									<input name="start_times1" value = "${result.start_time1}" type="hidden">
									<input name="start_times2" value = "${result.start_time2}" type="hidden">
									<input name="end_dates" value = "${result.end_date}" type="hidden">
									<input name="end_times1" value = "${result.end_time1}" type="hidden">
									<input name="end_times2" value = "${result.end_time2}" type="hidden">
								</td>
							</tr>
							<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_seqs_count"/></c:if>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:when test="${Pkg21Model.status > 130}">
				<table class="tbl_type12" cellspacing="0">
					<colgroup>
						<col width="15%">
						<col width="15%">
						<col width="15%">
						<col width="55%">
					</colgroup>
					<thead>
						<tr>
							<th style="display: none;"></th>
							<th>국사</th>
							<th>장비명</th>
							<th>서비스지역</th>
							<th>적용일시</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${Pkg21Model.pkgEquipmentModelList}" varStatus="status">
							<tr>
								<td style="display: none;">
									<c:choose>
										<c:when test="${empty result.start_date}">
											<input type="checkbox" name="check_seqs_e" onchange="javascript:fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" />
										</c:when>
										<c:otherwise>
											<input type="checkbox" name="check_seqs_e" onchange="javascript:fn_checkboxEquipment_click(this, ${status.count});" value="${result.equipment_seq}" checked/>
										</c:otherwise>
									</c:choose>
								</td>
								<td class="td_center">
									${result.team_name}
								</td>
								<td class="td_center">
									${result.equipment_name}
								</td>
								<td class="td_center">
									${result.service_area}
								</td>
								<td class="td_center sysc_inp mg02">
									<c:choose>
										<c:when test="${empty result.start_date}">
											일정 미적용 장비
										</c:when>
										<c:otherwise>
											${result.ampm} ${result.start_date} ${result.start_time1}:${result.start_time2}
											 ~ 
											${result.end_date} ${result.end_time1}:${result.end_time2}
										</c:otherwise>
									</c:choose>

									<select style="display: none;" name="ampms" id="PkgModel_pkgEquipmentModel_ampm_${status.count}">
										<option value="${result.ampm}">${result.ampm}</option>
									</select>
									<input name="start_dates" value = "${result.start_date}" type=hidden>
									<input name="start_times1" value = "${result.start_time1}" type="hidden">
									<input name="start_times2" value = "${result.start_time2}" type="hidden">
									<input name="end_dates" value = "${result.end_date}" type="hidden">
									<input name="end_times1" value = "${result.end_time1}" type="hidden">
									<input name="end_times2" value = "${result.end_time2}" type="hidden">
								</td>
							</tr>
							<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_seqs_count"/></c:if>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				
			</c:otherwise>
		</c:choose>
	</div>
	
	<div style="display: none;">
		<c:if test="${Pkg21Model.charge_gubun == 'VA'}">
		<input type="hidden" name="chk_results" id="chk_results"  />
		<input type="hidden" name="chk_seqs" id="chk_seqs"  />
			<table>
				<c:forEach var="result" items="${Pkg21Model.pkg21ModelList}" varStatus="status1">
					<tr>
						<td style="display: none;">
							<input type="checkbox" name="chk_seqs_val" value="${result.chk_seq}" checked/>
						</td>
						<td class="td_center">
							${result.vol_no}
						</td>
						<td>${result.title}</td>
						<td class="td_center">
							<c:forEach var="result2" items="${Pkg21Model.chk_result_list}" varStatus="status">
								<c:choose>
									<c:when test="${result2.code == result.chk_result}">
										<input type="radio" id="${result.chk_seq}" name="chk_results${status1.index}" value ="<c:out value='${result2.code}' />" checked="checked"  onclick="javascript:chk_value();" >
										<span class="mg03 ml05 mr15 fl_left" >${result2.codeName}</span>
									</c:when>
									<c:otherwise>
										<input type="radio" id="${result.chk_seq}" name="chk_results${status1.index}" value ="<c:out value='${result2.code}' />"   onclick="javascript:chk_value();"  >
										<span class="mg03 ml05 mr15 fl_left" >${result2.codeName}</span>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	
	</form:form>
</body>

</html>

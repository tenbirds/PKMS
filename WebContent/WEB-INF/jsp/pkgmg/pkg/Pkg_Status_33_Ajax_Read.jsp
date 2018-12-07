<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="registerFlag" value="${pkg_verify_gubun == 0 ? '등록' : '수정'}" />
<c:set var="QuestFlag" value="${empty List_verifyQuest ? 'N' : 'Y'}" />
<script type="text/javaScript">
$(document).ready(function($) {
	doCalendar("verify_start_date");
	doCalendar("verify_end_date");
	
	var elements = $('select option').filter('.pkg_veri_selec');
	elements.each(function(){
		if('${pkg4verify_seq_ver}' == $(this).val()){
 			$(this).attr("selected",":selected");
		}
	});
	
	var list_result1 = new Array();
	<c:forEach items="${ResultList}" var="list">
		list_result1.push("${list.result_memo}");
	</c:forEach>
	var list_result2 = new Array();
	<c:forEach items="${ResultList}" var="list2">
		list_result2.push("${list2.quest_seq}");
	</c:forEach>
	
	for (var i = 0; i < list_result1.length; i++) {
		$(".result_radio").each(function (index) {  
	    	if(list_result2[i] == $(this).attr('name')){
	    		if(list_result1[i] == $(this).val()){
	    			$(this).attr("checked",":checked");
	    		}
	    	}
	    });
	}
	for (var j = 0; j < list_result2.length; j++) {
		$(".result_input").each(function (index) {  
	    	if(list_result2[j] == $(this).attr('name')){
	    		$(this).val(list_result1[j]);
	    	}
	    });
	}
});

function check_bypass(e){
	var index = $("input:checkbox").index(e);
	if($(".chkbypass:eq("+index+")").is(":checked") == true ){
		$(".result_input:eq("+index+")").val("BYPASS");
	}else{
		$(".result_input:eq("+index+")").val("");
	}
}
</script>
	<h4 class="balloon_header">
		과금 검증
	</h4>
<input type="hidden" id="selectAjx_YN" name="selectAjx_YN" value="${PkgModel.selectAjx_YN}"/>
	<a class="ly_close" href="javascript:fn_flow_init_verify('33');">
	<img alt=닫기 src="/images/btn_close_layer.png">						</a>
	<div class="ly_sub">
				
		<!--버튼위치 -->
		<div class="pop_btn_location3">
			<span>
				<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
				<c:if test="${verifyYN.cha_yn != 'S' && (PkgModel.status == '26' || (PkgModel.status > 2 && PkgModel.status < 10))}">
					<c:forEach var="result" items="${SysModel.sel_sysUserChaId}" varStatus="loop">
						<c:if test="${(SysModel.sel_sysUserChaId[loop.count-1]) eq PkgModel.session_user_id}">
							<img src="/images/btn_cdrs_link.gif" alt="cdrs링크" style="cursor:pointer;" onclick="javascript:cdrs();"/>
							<img src="/images/btn_save.gif" alt="저장" style="cursor:pointer;" onclick="javascript:fn_create_result('${registerFlag}','33','${pkg_verify_gubun}','${QuestFlag}')" />
							<img src="/images/btn_verify_complete.gif" alt="검증완료" style="cursor:pointer;" onclick="javascript:fn_create_result('complete','33','${pkg_verify_gubun}','${QuestFlag}')"/>
						</c:if>
					</c:forEach>
				</c:if>
				</sec:authorize>
			</span>
		</div>
				
		<div id="flow_tb_1" style="display:block; height:525px; margin-top:25px; overflow:auto; overflow-x:hidden">
			<table class="tbl_type_ly" border="1" summary="">
				<colgroup>
					<col>
				</colgroup>
				<tr>
					<td scope="col" width="80px;">템플릿 명</td>
					<td scope="col" style="text-align:left; padding-left:10px;">
						<c:if test="${empty List_verifyQuest}">
							<font color="red">이 시스템으로 등록된 검증 템플릿이 없습니다. 템플릿관리 메뉴로 이동하여 해당 템플릿을 생성해주세요.</font>
						</c:if>
						<c:if test="${not empty List_verifyQuest}">
							<select id="verify_tem_info" name="verify_tem_info" onchange="javascript:fn_selectVerifyTem()">
								<c:forEach var="List" items="${List_verifyTem}" varStatus="">
								<option value="${List.verify_seq}_${List.verify_ver}" class="pkg_veri_selec">${List.verify_title}</option>
								</c:forEach>
							</select>
						</c:if>
					</td>
				</tr>
			</table>
		
			<br/>
			<!-- select한 템플릿리스트 - ajax -->
			<div id="ajax_select_verifyTem"></div>
			<c:if test="${not empty List_verifyQuest}">
				<div id="default_select_verifyTem">
					<table class="tbl_type_ly">
						<caption>검증 항목</caption>
						<tr>
							<th style="width:20px;"></th>
							<th style="">검증 항목 주제</th>
							<th style="width:220px;">검증 결과</th>
						</tr>
						<c:forEach var="List_Quest" items="${List_verifyQuest}" varStatus="status">
							<tr height="32px;">
								<td>${status.index+1}</td>
								<td style="text-align: left;">${List_Quest.quest_title}</td>
								<td>
									<c:if test="${List_Quest.quest_type eq '0'}"><!-- 선택형일때 -->
										<div>
											<input type="radio" id="" name="${List_Quest.quest_seq}" value="OK" class="result_radio"/> OK
											<input type="radio" id="" name="${List_Quest.quest_seq}" value="NOK" class="result_radio"/> NOK
											<input type="radio" id="" name="${List_Quest.quest_seq}" value="COK" class="result_radio"/> COK
											<input type="radio" id="" name="${List_Quest.quest_seq}" value="BYPASS" class="result_radio"/> BYPASS
										</div>
									</c:if>
									<c:if test="${List_Quest.quest_type eq '1'}"><!-- 단답형일때 -->
										<input type="text" id="" name="${List_Quest.quest_seq}" value="" class="result_input" style="width: 140px;" maxlength="200"/>
										<input type="checkbox" class="chkbypass" onclick="javascript:check_bypass(this);"/> BYPASS
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>	
			</c:if>
			
			<div style="padding-top: 10px;">
			<table class="tbl_type_ly" >
			<caption>일정 등록</caption>
				<colgroup>
					<col width="200" />
					<col width="*"/>
				</colgroup>
				<tr>
					<td scope="col">과금검증 일정 등록 <span class='necessariness'>*</span></td>
					<td scope="col" style="text-align:left; padding-left:10px;">
						<input id="verify_start_date" name="verify_start_date" style="width:90px" class="inp" type="text" value="${pkgStatusModel.verify_start_date}" readonly="readonly"/>
						&nbsp;~&nbsp;
						<input id="verify_end_date" name="verify_end_date" style="width:90px" class="inp" type="text" value="${pkgStatusModel.verify_end_date}" readonly="readonly"/>
					</td>
				</tr>
			</table>
			
			<div style="padding-top: 10px;">
			<table class="tbl_type_ly" style="">
				<caption>검증 결과 파일 등록</caption>
				<colgroup>
					<col width="200" />
					<col width="*"/>
				</colgroup>
				<tr>
					<th scope="col" height="30" rowspan="2">결과 파일 등록</th>
					<td scope="col" style="padding:6px 10px 4px; border-top:1px solid #e5e5e5; color:#4c4c4c;">
						<input type="text" id="col43" name="col43" value="${pkgStatusModel.col43}" style="width:90%;" class="inp" maxlength="100" onkeydown="fn_detail_variable_textarea_check(this);"/>
							<select id="selID43" onchange="fnSelBox43();">
								<c:forEach var="selectItem" begin="1" end="4" step="1">
									<c:choose>
										<c:when test="${PkgModel.file82 == null }">
											<option><c:out value="${selectItem}" /></option>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${PkgModel.file83 == null }">
													<option><c:out value="${selectItem}" /></option>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${PkgModel.file84 == null }">
															<c:if test="${status.index != 2}">
																<option><c:out value="${selectItem}" /></option>
															</c:if>
															<c:if test="${status.index == 2}">
																<option selected><c:out value="2" /></option>
															</c:if>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${PkgModel.file85 == null }">
																	<c:if test="${status.index != 3}">
																		<option><c:out value="${selectItem}" /></option>
																	</c:if>
																	<c:if test="${status.index == 3}">
																		<option selected><c:out value="3" /></option>
																	</c:if>
																</c:when>
															</c:choose>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>		
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td scope="col" align="left">
							<c:choose>
								<c:when test="${registerFlag == '수정'}">
									<div id="display_43_1"><ui:file attachFileModel="${PkgModel.file82}" name="file82" size="50" mode="update" /></div>
								</c:when>
								<c:otherwise>
									<div id="display_43_1"><ui:file attachFileModel="${PkgModel.file82}" name="file82" size="50" mode="create" /></div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${registerFlag == '수정'}">
									<c:choose>
										<c:when test="${PkgModel.file83 == null }">
											<div id="display_43_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file83}" name="file83" size="50" mode="update" /></div>
										</c:when>
										<c:otherwise>
											<div id="display_43_2"><br/><ui:file attachFileModel="${PkgModel.file83}" name="file83" size="50" mode="update" /></div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<div id="display_43_2" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file83}" name="file83" size="50" mode="create" /></div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${registerFlag == '수정'}">
									<c:choose>
										<c:when test="${PkgModel.file84 == null }">
											<div id="display_43_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file84}" name="file84" size="50" mode="update" /></div>
										</c:when>
										<c:otherwise>
											<div id="display_43_3"><br/><ui:file attachFileModel="${PkgModel.file84}" name="file84" size="50" mode="update" /></div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<div id="display_43_3" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file84}" name="file84" size="50" mode="create" /></div>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${registerFlag == '수정'}">
									<c:choose>
										<c:when test="${PkgModel.file85 == null }">
											<div id="display_43_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file85}" name="file85" size="50" mode="update" /></div>
										</c:when>
										<c:otherwise>
											<div id="display_43_4"><br/><ui:file attachFileModel="${PkgModel.file85}" name="file85" size="50" mode="update" /></div>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<div id="display_43_4" style="display:none;"><br/><ui:file attachFileModel="${PkgModel.file85}" name="file85" size="50" mode="create" /></div>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
			</table>
			</div>
			<div style="padding-top: 10px;">
			<table class="tbl_type_ly">
			<caption>검증 Comment</caption>
				<tr>
					<td align="left">
					<textarea id="verify_content" name="verify_content" maxlength="1000" style="width: 700px; height: 70px; margin-left: 8px;">${pkgVerifyInfo.verify_content}</textarea>
					</td>
				</tr>
			</table>
			</div>
			
			<div style="padding-top: 10px;">
			<table class="tbl_type_ly">
				<caption>검증 담당자</caption>
				<tr>
					<th scope="col">이름</th>
					<th scope="col">부서</th>
					<th scope="col">전화번호</th>
					<th scope="col">이메일</th>
				</tr>
				<c:forEach var="result" items="${pkgModelData.systemUserModelList}" varStatus="status">
					<c:if test="${result.charge_gubun == 'CH'}">
						<tr>
							<td align="center">${result.user_name}&nbsp;</td>
							<td align="center">${result.sosok}&nbsp;</td>
							<td align="center">${result.user_phone}&nbsp;</td>
							<td align="center">${result.user_email}&nbsp;</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
			</div>
		</div>

		<c:if test="${not empty pkgStatusModel.reg_date}">
			<div class="pop_status_reg">
				등록: ${pkgStatusModel.reg_user_name} (${pkgStatusModel.reg_date})
			</div>
		</c:if>

		<c:if test="${not empty pkgStatusModel.update_date}">
			<div class="pop_status_update">
				수정: ${pkgStatusModel.update_user_name} (${pkgStatusModel.update_date})
			</div>
		</c:if>
					
	</div>

	<div class="edge_cen" style="top:415px;"></div>

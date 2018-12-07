<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<body> 
	<div class="caption">보완적용내역 목록</div>
	<input type="hidden" id="pkg_detail_count_hidden" name="pkg_detail_count_hidden" value="${PkgModel.pkg_detail_count}" />
	<input type="hidden" id="tempmgModelListSize_hidden" name="tempmgModelListSize_hidden" value="${PkgModel.tempmgModelListSize}" />
	
	<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
		<div style="width:825px; text-align: right;"><span><a href="javascript:fn_radio_update();"><img src="/images/btn_save.gif" alt="저장" /></a></span></div>
	</sec:authorize>
	
	<div class="fakeContainer" style="width:825px;height:240px;border:1px solid #ddd">
		<table id="pkg_tab_3_2_table" class="pop_tbl_type3" style="width: 100%">
			<thead> 
			<tr>
				<th scope="col">PN/CR 매핑</th>
				<c:forEach var="result" items="${PkgModel.tempmgModelList}" varStatus="status">
					<c:if test="${status.index < 5}">
						<th scope="col">${result.title}</th>
					</c:if>
				</c:forEach>
				<c:forEach var="result" items="${PkgModel.tempmgModelList}" varStatus="status">
					<c:if test="${status.index == 21}">
						<th scope="col">${result.title}</th>
					</c:if>
				</c:forEach>
				<c:forEach var="result" items="${PkgModel.tempmgModelList}" varStatus="status">
					<c:if test="${18 < status.index and status.index < 21}">
						<th scope="col">${result.title}</th>
					</c:if>
				</c:forEach>
			</tr>
			</thead>
			<tbody>
		<c:forEach var="result" items="${PkgModel.pkgDetailVariableModelList}" varStatus="st">
			<c:set var="rt" value="${PkgModel.pkgDetailModelList[st.index]}" />
			<tr>
			<c:choose>
			    <c:when test="${rt.new_pn_cr_seq == 0}">
					<td id="td_detail_no_${rt.pkg_detail_seq}" class="td_detail_no_${rt.no} td_no2" style=" background-color:#FFDEA9; cursor:pointer;" onclick="fn_newpncr_click('${rt.pkg_detail_seq}', '${rt.no}', '${rt.new_pn_cr_gubun}', '');" onMouseOver="tip_message_over('tip_message_mapp')" onMouseMove="tip_message_over('tip_message_mapp')" onMouseOut="tip_message_out('tip_message_mapp')">
						<img id="img_detail_no_${rt.no}" src="/images/ico_maping_no.png" />
					</td>
			    </c:when>
			    <c:otherwise>
					<td id="td_detail_no_${rt.pkg_detail_seq}" class="td_detail_no_${rt.no} td_no3" style=" background-color:white; cursor:pointer;" onclick="fn_newpncr_click('${rt.pkg_detail_seq}', '${rt.no}', '${rt.new_pn_cr_gubun}', '${rt.new_pn_cr_seq}');" onMouseOver="tip_message_over('tip_message_mapp')" onMouseMove="tip_message_over('tip_message_mapp')" onMouseOut="tip_message_out('tip_message_mapp')">
						${rt.new_pn_cr_seq}
					</td>
			    </c:otherwise>
			</c:choose>
				<td>${rt.no}</td>
				<td>${rt.new_pn_cr_gubun}</td>
			<c:forEach var="rt_sub" items="${PkgModel.pkgDetailVariableModelList[st.index]}" varStatus="st_sub">
				<c:if test="${st_sub.index < 3 }">
					<c:choose>
						<c:when test="${st_sub.index == 2}">
							<td>
								<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'OK'}">checked</c:if> onclick="fn_detail_radio('${rt_sub.pkg_detail_seq}','ok')" id="${rt_sub.pkg_detail_seq}_ok" value="${rt_sub.pkg_detail_seq}" class="radio_OK"/>OK
								<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'NOK'}">checked</c:if> onclick="fn_detail_radio('${rt_sub.pkg_detail_seq}','nok')" id="${rt_sub.pkg_detail_seq}_nok" value="${rt_sub.pkg_detail_seq}" class="radio_NOK"/>NOK
								<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'COK'}">checked</c:if> onclick="fn_detail_radio('${rt_sub.pkg_detail_seq}','cok')" id="${rt_sub.pkg_detail_seq}_cok" value="${rt_sub.pkg_detail_seq}" class="radio_COK"/>COK
							</td>
						</c:when>
						<c:otherwise>
							<td id="td_detail_variable_${rt_sub.pkg_detail_seq}_${st_sub.index}" class="td_detail_variable_${rt_sub.pkg_detail_seq}_${st_sub.index}" style="cursor:pointer; ${(st_sub.index == 0) ? 'text-align:center' : 'text-align:left; padding:0 0 0 5px;' }" onclick="fn_detail_variable_click('${rt_sub.pkg_detail_seq}');">
									<c:out value="${fn:substring(rt_sub.content, 0 , 35)}" />
									<c:out value="${fn:length(rt_sub.content) > 35 ? '...' : ''}" />
							</td>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${st_sub.index == 19}">
					<td style="text-align: center;">
						<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'OK'}">checked</c:if> onclick="fn_detail_radio_dev('${rt_sub.pkg_detail_seq}','ok')" id="${rt_sub.pkg_detail_seq}_ok_dev" value="${rt_sub.pkg_detail_seq}" class="radio_OK_dev"/>OK
						<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'NOK'}">checked</c:if> onclick="fn_detail_radio_dev('${rt_sub.pkg_detail_seq}','nok')" id="${rt_sub.pkg_detail_seq}_nok_dev" value="${rt_sub.pkg_detail_seq}" class="radio_NOK_dev"/>NOK
						<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'COK'}">checked</c:if> onclick="fn_detail_radio_dev('${rt_sub.pkg_detail_seq}','cok')" id="${rt_sub.pkg_detail_seq}_cok_dev" value="${rt_sub.pkg_detail_seq}" class="radio_COK_dev"/>COK
						<input  type="radio" <c:if test="${fn:substring(rt_sub.content, 0 , 35) eq 'BYPASS'}">checked</c:if> onclick="fn_detail_radio_dev('${rt_sub.pkg_detail_seq}','bypass')" id="${rt_sub.pkg_detail_seq}_bypass_dev" value="${rt_sub.pkg_detail_seq}" class="radio_BYPASS_dev"/>BYPASS
					</td>
				</c:if>
			</c:forEach>
			<c:forEach var="rt_sub" items="${PkgModel.pkgDetailVariableModelList[st.index]}" varStatus="st_sub">									
				<c:if test="${16 < st_sub.index and 19 > st_sub.index}">
					<td id="td_detail_variable_${rt_sub.pkg_detail_seq}_${st_sub.index}" class="td_detail_variable_${rt_sub.pkg_detail_seq}_${st_sub.index}" style="cursor:pointer; ${(st_sub.index == 0) ? 'text-align:center' : 'text-align:left; padding:0 0 0 5px;' }" onclick="fn_detail_variable_click('${rt_sub.pkg_detail_seq}');">
							<c:out value="${fn:substring(rt_sub.content, 0 , 35)}" />
							<c:out value="${fn:length(rt_sub.content) > 35 ? '...' : ''}" />
					</td>
				</c:if>
			</c:forEach>
			</tr>
		</c:forEach>
			</tbody>
		</table>
	</div>
</body>



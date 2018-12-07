<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<body>				
	<table class="pop_tbl_type3" id="detail_variable_ajax_write">
		<thead>
		<tr>
			<c:forEach var="result" items="${PkgModel.tempmgModelList}" varStatus="status">
				<c:if test="${status.index > 0}">
					<th scope="col">${result.title}</th>
				</c:if>
			</c:forEach>
		</tr>
		</thead>
		<tbody>
	<c:forEach var="result" items="${PkgModel.pkgDetailVariableModelList}" varStatus="st">
		<c:set var="rt" value="${PkgModel.pkgDetailModelList[st.index]}" />
		<tr>
			<td>
				<textarea name="detail_variable_content2" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" style="width:${(empty PkgModel.templateWidths[st_sub.index]) ? 50 : PkgModel.templateWidths[st_sub.index]}px; height:190px; overflow:auto;"></textarea>
			</td>
		<c:forEach var="rt_sub" items="${PkgModel.pkgDetailVariableModelList[st.index]}" varStatus="st_sub">
			<td>
				<sec:authorize ifAnyGranted = "ROLE_BP">
					<c:if test="${st_sub.index eq 2 or st_sub.index eq 19}"> <!-- 상용 검증 결과 -->
						<textarea name="detail_variable_content2" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" style="width:${(empty PkgModel.templateWidths[st_sub.index]) ? 50 : PkgModel.templateWidths[st_sub.index]}px; height:190px; overflow:auto;" readonly></textarea>
					</c:if>
					<c:if test="${st_sub.index ne 2 and st_sub.index ne 19}">
						<textarea name="detail_variable_content2" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" style="width:${(empty PkgModel.templateWidths[st_sub.index]) ? 50 : PkgModel.templateWidths[st_sub.index]}px; height:190px; overflow:auto;"></textarea>
					</c:if>
				</sec:authorize>
				<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR" >
					<textarea name="detail_variable_content2" maxlength="1200" onkeydown="fn_detail_variable_textarea_check(this);" style="width:${(empty PkgModel.templateWidths[st_sub.index]) ? 50 : PkgModel.templateWidths[st_sub.index]}px; height:190px; overflow:auto;"></textarea>
				</sec:authorize>
			</td>	
		</c:forEach>
		</tr>
	</c:forEach>
	</tbody>
	</table>
</body>



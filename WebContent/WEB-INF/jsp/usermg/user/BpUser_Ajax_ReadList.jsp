<%--
/**
 * Ajax를 이용하여 표시되는 업체 담당자 목록
 * 
 * @author : skywarker
 * @Date : 2012. 4. 19.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${BpUserModel.totalCount == 0}">
	<tr>
		<td colspan="5">등록된 담당자가 없습니다.</td>
	</tr>
</c:if>
<c:forEach var="result" items="${BpUserModelList}" varStatus="status">
	<tr>
		<td><c:choose>
				<c:when test="${result.approval_state == '1'}">
					<font color="green">승인완료</font>
				</c:when>
				<c:when test="${result.approval_state == '0'}">
					<font color="orange">승인요청</font>
				</c:when>
				<c:otherwise>
					<font color="gray">임시저장</font>
				</c:otherwise>
			</c:choose></td>
		<td style='overflow:hidden; white-space:nowrap; text-overflow:ellipsis;width:70px;'><a href="javascript:fn_read_user('<c:out value="${result.bp_user_id}"/>')"><c:out value="${result.bp_user_name}" /></a></td>
		<td><c:out value="${result.bp_user_id}" />&nbsp;</td>
		<td><c:out value="${result.bp_user_phone1}" />&nbsp; <c:out value="${result.bp_user_phone2}" />&nbsp; <c:out value="${result.bp_user_phone3}" /></td>
		<td style='overflow:hidden; white-space:nowrap; text-overflow:ellipsis;width:70px;' title="<c:out value="${result.bp_user_email}" />"><c:out value="${result.bp_user_email}" />&nbsp;</td>
<%-- 		<c:if test = "${result.bp_user_id != '' and result.approval_state != '3'}"> --%>
<%-- 		<td style='overflow:hidden; white-space:nowrap; text-overflow:ellipsis;width:70px;'><a href="javascript:fn_sys_list('<c:out value="${result.bp_user_id}"/>')"> --%>
<!-- 		<img src="/images/btn_identify.gif" alt="확인" style="cursor: pointer; vertical-align: middle;" id="open_bp_popup" align="absmiddle" style="vertical-align: middle;" /></a> -->
<!-- 		</td> -->
<%-- 		</c:if> --%>
	</tr>
</c:forEach>




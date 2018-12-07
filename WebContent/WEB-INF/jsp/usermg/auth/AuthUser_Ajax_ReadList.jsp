<%--
/**
 * Ajax를 이용하여 표시되는 권한 설정 사용자 목록
 * 
 * @author : skywarker
 * @Date : 2012. 4. 19.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${AuthUserModel.totalCount == 0}">
	<tr>
		<td colspan="5">결과 목록이 없습니다.</td>
	</tr>
</c:if>
<c:forEach var="result" items="${authUserList}" varStatus="status">
	<tr>
		<td>
			<c:out value="${result.auth_user_name}" />&nbsp;
			<input type="hidden" name="auth_update_users" value="${result.auth_user_id}">
		</td>
		<td><c:out value="${result.auth_user_group_name}" />&nbsp;</td>
		<td><c:choose>
				<c:when test="${result.auth_role_admin}">
					<input type="checkbox" name="auth_role_admins" value="${result.auth_user_id}" checked="checked">
				</c:when>
				<c:otherwise>
					<input type="checkbox" name="auth_role_admins" value="${result.auth_user_id}">
				</c:otherwise>
			</c:choose></td>
		<td><c:choose>
				<c:when test="${result.auth_role_manager}">
					<input type="checkbox" name="auth_role_managers" value="${result.auth_user_id}" checked="checked">
				</c:when>
				<c:otherwise>
					<input type="checkbox" name="auth_role_managers" value="${result.auth_user_id}">
				</c:otherwise>
			</c:choose></td>
		<td><c:choose>
				<c:when test="${result.auth_role_operator}">
					<input type="checkbox" name="auth_role_operators" value="${result.auth_user_id}" checked="checked">
				</c:when>
				<c:otherwise>
					<input type="checkbox" name="auth_role_operators" value="${result.auth_user_id}">
				</c:otherwise>
			</c:choose></td>
	</tr>
</c:forEach>




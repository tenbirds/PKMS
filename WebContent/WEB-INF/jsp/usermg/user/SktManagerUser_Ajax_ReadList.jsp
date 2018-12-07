<%--
/**
 * Ajax를 이용하여 표시되는 SKT 사용자 목록
 * 
 * @author : skywarker
 * @Date : 2012. 4. 19.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${SktUserModel.totalCount == 0}">
	<tr>
		<td colspan="3">결과 목록이 없습니다.</td>
	</tr>
</c:if>
<c:forEach var="result" items="${SktUserModelList}" varStatus="status">
	<tr>
		<td>
			<input type="radio" id="radio_empno<c:out value="${result.empno}" />" name="radio_empno" value="${result.empno}">
			
			<input type="hidden" id="hidden_hname<c:out value="${result.empno}" />"
			name="hidden_hname<c:out value="${result.empno}" />" value="${result.hname}">
			
			<input type="hidden" id="hidden_sosok<c:out value="${result.empno}" />"
			name="hidden_sosok<c:out value="${result.empno}" />" value="${result.sosok}">
		</td>
		<td><c:out value="${result.hname}" /></td>
		<td><c:out value="${result.sosok}" /></td>
	</tr>
</c:forEach>




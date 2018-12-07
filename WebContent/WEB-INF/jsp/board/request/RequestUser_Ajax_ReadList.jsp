<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:choose>
	<c:when test="${not empty requestUserModelList}">
		<c:forEach var="requestUserModelList" items="${requestUserModelList}">
			<tr>
				<td>
					<sec:authorize ifAnyGranted = "ROLE_BP">
						<a href="javascript:fn_read_reqUser('<c:out value="${requestUserModelList.ord}"/>')"><c:out value="${requestUserModelList.request_name}"/></a>
					</sec:authorize>
					<sec:authorize ifNotGranted = "ROLE_BP">
						<c:out value="${requestUserModelList.request_name}"/>
					</sec:authorize>
				</td>
				<td><c:out value="${requestUserModelList.request_phone}"/></td>
			</tr>
		</c:forEach>		
	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="2">등록된 요청자가 없습니다.</td>
		</tr>
	</c:otherwise>
</c:choose>
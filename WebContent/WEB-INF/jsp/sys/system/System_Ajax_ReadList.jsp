<%--
/**
 * Ajax를 이용하여 표시되는 시스템 목록
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="result" items="${SysModelList}" varStatus="status">
	<c:choose>
		<c:when test="${result.use_yn == 'Y'}">
			<li id="system_<c:out value="${result.system_seq}"/>" onMouseOver="this.style.cursor='pointer'" onClick="javascript:fn_selectSystem('<c:out value="${result.system_seq}"/>')">
				<c:out value="${result.name}" />
			</li>
		</c:when>
		<c:otherwise>
			<li style="color: #9f9f9f;" id="system_<c:out value="${result.system_seq}"/>" onMouseOver="this.style.cursor='pointer'" onClick="javascript:fn_selectSystem('<c:out value="${result.system_seq}"/>')">
				<c:out value="${result.name}(삭제됨)" />
			</li>
		</c:otherwise>
	</c:choose>
</c:forEach>

<c:if test="${SysModel.totalCount < 8}">
	<c:forEach var="index" begin="1" end="${8 - SysModel.totalCount}" step="1">
		<li></li>
	</c:forEach>
</c:if>




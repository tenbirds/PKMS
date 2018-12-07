<%--
/**
 * Ajax를 이용하여 표시되는 시스템 중분류 목록
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="result" items="${SysModelList}" varStatus="status">
	<li id="group2_<c:out value="${result.group2_seq}"/>" onMouseOver="this.style.cursor='pointer'" onClick="javascript:fn_selectGroup2('<c:out value="${result.group2_seq}"/>')"><c:out value="${result.name}" /></li>
</c:forEach>

<c:if test="${SysModel.totalCount < 8}">
	<c:forEach var="index" begin="1" end="${8 - SysModel.totalCount}" step="1">
		<li></li>
	</c:forEach>
</c:if>




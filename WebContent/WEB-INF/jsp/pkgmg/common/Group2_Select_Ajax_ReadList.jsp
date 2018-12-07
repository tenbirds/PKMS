<%--
/**
 * Ajax를 이용하여 표시되는 시스템 중분류 목록(select 용)
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<option value="">전 체</option>
<c:forEach var="result" items="${SysModelList}" varStatus="status">
	<option value='<c:out value="${result.group2_seq}"/>'><c:out value="${result.name}" /></option>
</c:forEach>


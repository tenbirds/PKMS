<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javaScript" defer="defer">

</script>

	<c:choose>
		<c:when test="${not empty readList_quest}">
			<c:forEach var="result" items="${readList_quest}" varStatus="">
			<div style="height: 20px;">
			<input type="checkbox" id="quest_seq" name="quest_seq" value="${result.quest_seq}"/>
			<c:out value="${result.quest_title}" /><br/>
			</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<div style="height: 20px;">
				등록된 검증 항목이 없습니다. <br/>
				검증 항목 등록 페이지로 이동하여 관련 항목을 추가해 주세요.
			</div>
		</c:otherwise>
	</c:choose>
		

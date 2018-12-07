<%--
/**
 * Ajax를 이용하여 표시되는 시스템 상세
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<script type="text/javaScript" defer="defer">
	
</script>



<!--해당리스트 -->
<table cellspacing="0" class="sysc_tbl1" style="width: 365px;">
<c:if test="${SysModel.totalCount == 0}">
	<tr>
		<td>결과 목록이 없습니다.</td>
	</tr>
</c:if>
<c:forEach var="result" items="${SysModelList}" varStatus="status">
	<tr>
		<td width="20">
			<input type="checkbox" name="system_user_ids" value="${result.empno}" />
			<input type="hidden" name="system_user_names" value="${result.hname} [${result.sosok}]" />
		</td>
		<td width="100">
			<c:out value="${result.hname}" />
		</td>
		<td>
			<c:out value="${result.sosok}" />
		</td>
	</tr>
</c:forEach>
</table>






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
<table cellspacing="0" class="sysc_tbl1" style="width: 98.5%;">
<c:if test="${SysModel.totalCount == 0}">
	<tr>
		<td>결과 목록이 없습니다.</td>
	</tr>
</c:if>
<c:forEach var="result" items="${SysModelList}" varStatus="status">
	<tr>
		<td width="5%">
			<input type="checkbox" name="system_user_ids" value="${result.bp_user_id}" />
			<input type="hidden" name="system_user_names" value="${result.bp_user_name}" />
		</td>
		<td width="25%">
			<c:out value="${result.bp_user_name}" />
			(${result.bp_user_id})
		</td>
		<td width="25%">
			${result.bp_user_phone1}-${result.bp_user_phone2}-${result.bp_user_phone3}
		</td>
		<td width="45%">
			<c:out value="${result.bp_user_email}" />
		</td>
	</tr>
</c:forEach>
</table>






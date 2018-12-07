<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<body>
	<fieldset class="detail_fieldset">
		<legend>찾기 결과</legend>
		<table class="tbl_type1" style="width: 100%;">
				<tr>
					<th>
						아이디
					</th>
					<td>
						<c:choose>
							<c:when test="${not empty BpIdList}">
								<c:forEach var="BpIdList" items="${BpIdList}" varStatus="status">
									<p>${BpIdList.bp_user_id}</p>
								</c:forEach>
							</c:when>
							<c:otherwise>
								조회된 아이디가 없습니다.
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
		</table>
	</fieldset>
</body>

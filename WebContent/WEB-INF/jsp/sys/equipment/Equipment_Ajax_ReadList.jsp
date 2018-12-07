<%--
/**
 * Ajax를 이용하여 표시되는 시스템 장비 목록
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
			<li id="equipment_<c:out value="${result.equipment_seq}"/>" onMouseOver="this.style.cursor='pointer'" onClick="javascript:fn_selectEquipment('<c:out value="${result.equipment_seq}"/>')">
				<c:out value="${result.name}" />
				<c:if test="${result.cha_yn == 'Y'}">
					<span style="font-size: 11px; color: red; font-family: 돋움;">
						[과금]
					</span>
				</c:if>
			</li>
		</c:when>
		<c:otherwise>
			<li style="color: #9f9f9f;" id="equipment_<c:out value="${result.equipment_seq}"/>" onMouseOver="this.style.cursor='pointer'" onClick="javascript:fn_selectEquipment('<c:out value="${result.equipment_seq}"/>')">
				<c:out value="${result.name}(철거)" />
				<c:if test="${result.cha_yn == 'Y'}">
					<span style="font-size: 11px; color: red; font-family: 돋움;">
						[과금]
					</span>
				</c:if>
			</li>
		</c:otherwise>
	</c:choose>
</c:forEach>

<c:if test="${SysModel.totalCount < 8}">
	<c:forEach var="index" begin="1" end="${8 - SysModel.totalCount}" step="1">
		<li></li>
	</c:forEach>
</c:if>




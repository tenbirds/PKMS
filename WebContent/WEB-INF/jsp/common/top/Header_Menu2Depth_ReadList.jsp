<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>

<!-- Js -->
<script type="text/javascript" src="/css/js/ui.js"></script>
<body>
	<c:set var="subMenuMap" scope="page" value="<%=new java.util.TreeMap<Integer, java.util.TreeMap<Integer, com.pkms.common.menu.model.MenuModel>>()%>" />
	<c:forEach var="TopMenuModel" items="${topMenuList}">
		<c:set var="tempMap" scope="page" value="<%=new java.util.TreeMap<Integer, com.pkms.common.menu.model.MenuModel>()%>" />
		<c:forEach var="SubMenuModel" items="${subMenuList}">
			<c:if test="${TopMenuModel.menu_seq == SubMenuModel.parent_seq}">
				<c:set target="${tempMap}" property="${SubMenuModel.ord}" value="${SubMenuModel}" />
			</c:if>
		</c:forEach>
		<c:set target="${subMenuMap}" property="${TopMenuModel.ord}" value="${tempMap}" />
	</c:forEach>
	<c:forEach var="entry" items="${subMenuMap}" varStatus="status">
		<div class="sub_menu" id="menu_${entry.key}" >
			<c:forEach var="menuMap" items="${entry.value}" varStatus="mapStatus">
				<c:if test="${mapStatus.first}">
					<!--li class="sm_bg1"></li-->
					<div>
						</c:if>
						<c:choose>
							<c:when test="${mapStatus.last}">
								<!-- <span class="bg_none"> -->
									<a href="${menuMap.value.url}" class="depth3">${menuMap.value.name}</a>
								<!-- </span> -->
							</c:when>
							<c:otherwise>
								<!-- span--><a href="${menuMap.value.url}" class="depth3">${menuMap.value.name}</a><!-- </span> -->
							</c:otherwise>
						</c:choose>
						<c:if test="${mapStatus.last}">
					</div>
					<!-- li class="sm_bg3"></li-->
				</c:if>
			</c:forEach>
		</div>
	</c:forEach>
</body>
</html>

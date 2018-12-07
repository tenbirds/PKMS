<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>

<!-- Js -->
<script type="text/javascript" src="/css/js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="/css/js/ui.js"></script>
<body>

<!-- <header>
	<div class="header_wrap inner">
		<div class="logo"><a href="#"><img src="/images/common/main_logo.png" alt="PKMS 2.1"></a></div>
		<ul class="header_gnb">
			<li class="active"><a href="#">PKG검증</a></li>
			<li><a href="#">PKG등록</a></li>
			<li><a href="#">시스템관리</a></li>
		</ul>
	</div>
</header> -->
<!-- header -->
<!-- nav -->

	<div class="left_wrap inner">
		<!-- Utill -->
		<ul class="utill">
			<li>
				<span class="txt_name"> 
				<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
					${topModel.session_user_name }
				</sec:authorize>
				</span> 님 환영합니다. 
			</li>
			<li>
				<span class="txt_job">
					<sec:authorize ifAnyGranted="ROLE_ADMIN">
						관리 /
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_MANAGER">
						검증 /
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_OPERATOR">
						운용
					</sec:authorize>
					<sec:authorize ifAnyGranted="ROLE_BP">
						협력업체
					</sec:authorize>
				</span>
			</li>
			<li>
				<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
					<span class="mr05"><a href="/Login_Delete.do">로그아웃</a></span>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_BP">
					 l <span class="ml05"><a href="javascript:fn_update_bp()">정보변경</a></span>
				</sec:authorize>
			</li>
		</ul>
		<!-- Utill -->
		<!-- Gnb -->
		<ul class="gnb">
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
		<c:forEach var="MenuModel" items="${topMenuList}" varStatus="status">
			<c:choose>
				<c:when test="${ParentSeq == MenuModel.parent_seq}">
					<li id="firstDept${status.count}" class="active">
				</c:when>
				<c:otherwise>
					<li id="firstDept${status.count}" >
				</c:otherwise>			
			</c:choose>
					<c:choose>
						<c:when test="${MenuModel.url == '#'}">
							<a href="${MenuModel.url}" style="cursor: default;">${MenuModel.name}</a>
							<c:forEach var="entry" items="${subMenuMap}" varStatus="status">
								<div class="sub_menu" id="menu_${entry.key}" >
									<c:forEach var="menuMap" items="${entry.value}" varStatus="mapStatus">
										<c:if test="${menuMap.value.parent_seq == MenuModel.menu_seq}">
										<c:if test="${mapStatus.first}">
											<div>
										</c:if>
										<c:choose>
											<c:when test="${mapStatus.last}">
												<a href="${menuMap.value.url}" class="depth3">${menuMap.value.name}</a>
											</c:when>
											<c:otherwise>
												<a href="${menuMap.value.url}" class="depth3">${menuMap.value.name}</a>
											</c:otherwise>
										</c:choose>
										<c:if test="${mapStatus.last}">
											</div>
										</c:if>
										</c:if>
									</c:forEach>
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<a href="${MenuModel.url}" style="cursor: pointer;">${MenuModel.name}</a>
						</c:otherwise>
					</c:choose>
					<input type="hidden" name="menu_id_array" value="${MenuModel.menu_seq}" />
				</li>
				
			</c:forEach>
		</ul>
		
		<!-- Gnb -->
		<div class="callcenter_info fl_wrap">
			<dl>
				<dt>PKMS 문의</dt>
				<dd>유형만M<br><a href="mailto:nabiiyhm@sk.com">nabiiyhm@sk.com</a></dd>
				<dd>윤강근M<br><a href="mailto:kkyoon@sk.com">kkyoon@sk.com</a></dd>
				<dd>김준희대리<br><a href="mailto:jhkim@in-soft.co.kr">jhkim@in-soft.co.kr</a></dd>
			</dl>
		</div>
	</div>

<!-- nav -->

	<%-- <div class="t1">
		<div class="name">
			<span> 
			<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
				${topModel.session_user_name }
			</sec:authorize>
			</span> 님 환영합니다. 
			<sec:authorize ifAnyGranted="ROLE_ADMIN">
				[관리]
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_MANAGER">
				[검증]
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_OPERATOR">
				[운용]
			</sec:authorize>
		</div>

		<div>
			<ul class="logout">
				<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR, ROLE_BP">
					<li><a href="/Login_Delete.do">로그아웃</a></li>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_BP">
					<li class="t_div"></li>
					<li><a href="javascript:fn_update_bp()">정보변경</a></li>
				</sec:authorize>
			</ul>
		</div>
	</div>

	<div class="t2">
		<ul id="menu" class="menu">
			<c:forEach var="MenuModel" items="${topMenuList}" varStatus="status">
				<li class="m${MenuModel.menu_seq}">
					<c:choose>
						<c:when test="${MenuModel.url == '#'}">
							<a href="${MenuModel.url}" style="cursor: default;"></a>
						</c:when>
						<c:otherwise>
							<a href="${MenuModel.url}" style="cursor: pointer;"></a>
						</c:otherwise>
					</c:choose>
				</li>
				<input type="hidden" name="menu_id_array" value="${MenuModel.menu_seq}" />
			</c:forEach>
		</ul>
	</div> --%>
	<!--메뉴영역 끝 -->
</body>
</html>

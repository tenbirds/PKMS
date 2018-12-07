<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="/js/pkgmg/pkgmg.pkg.js"></script>
<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script> 
<script type="text/javaScript">
</script>
</head>
<body>
<div class="pop_progress_Div1">
	<div class="pop_tbl_tit">검증진도율</div>
	<!-- 검증진도율 = 상용검증결과입력수(xxx_col4) / 항목수(xxx_col1) * 100 -->
	<!-- 상용검증결과는 OK/NOK/COK -->
	<table cellspacing="0" class="pop_tbl_progress_type1" width="100%">
	<tr>	
		<th></th>
		<th>항목수</th>
		<th>검증내역개수</th>
		<th>개선내역개수</th>
		<th>검증진도율</th>
	</tr>
	<tr>
		<th>신규</th>
		<td>${PkgModel.new_col1 }</td>
		<td>${PkgModel.new_col2 }</td>
		<td>${PkgModel.new_col3 }</td>
		<td>
			<c:choose>
				<c:when test="${PkgModel.new_col4 > 0 }">
					<fmt:formatNumber value="${PkgModel.new_col4 / PkgModel.new_col1 * 100 }" pattern="0" />
				</c:when>
				<c:otherwise>
					0
				</c:otherwise>
			</c:choose>
			%
		</td>
	</tr>
	<tr>
		<th>보완</th>
		<td>${PkgModel.pn_col1 }</td>
		<td>${PkgModel.pn_col2 }</td>
		<td>${PkgModel.pn_col3 }</td>
		<td>
			<c:choose>
				<c:when test="${PkgModel.pn_col4 > 0 }">
					<fmt:formatNumber value="${PkgModel.pn_col4 / PkgModel.pn_col1 * 100 }" pattern="0" />
				</c:when>
				<c:otherwise>
					0
				</c:otherwise>
			</c:choose>
			%
		</td>
	</tr>
	<tr>
		<th>개선</th>
		<td>${PkgModel.cr_col1 }</td>
		<td>${PkgModel.cr_col2 }</td>
		<td>${PkgModel.cr_col3 }</td>
		<td>
			<c:choose>
				<c:when test="${PkgModel.cr_col4 > 0 }">
					<fmt:formatNumber value="${PkgModel.cr_col4 / PkgModel.cr_col1 * 100 }" pattern="0" />
				</c:when>
				<c:otherwise>
					0
				</c:otherwise>
			</c:choose>
			%
		</td>
	</tr>
	<tr>
		<th>합계</th>
		<td>${PkgModel.new_col1 + PkgModel.pn_col1 + PkgModel.cr_col1 }</td>
		<td>${PkgModel.new_col2 + PkgModel.pn_col2 + PkgModel.cr_col2 }</td>
		<td>${PkgModel.new_col3 + PkgModel.pn_col3 + PkgModel.cr_col3 }</td>
		<td>
			<c:choose>
				<c:when test="${(PkgModel.new_col4 + PkgModel.pn_col4 + PkgModel.cr_col4) / (PkgModel.new_col1 + PkgModel.pn_col1 + PkgModel.cr_col1) * 100 != 'NaN'}">
					<fmt:formatNumber value="${(PkgModel.new_col4 + PkgModel.pn_col4 + PkgModel.cr_col4) / (PkgModel.new_col1 + PkgModel.pn_col1 + PkgModel.cr_col1) * 100 }" pattern="0" />
				</c:when>
				<c:otherwise>
					0
				</c:otherwise>
			</c:choose>
			%
		</td>
	</tr>
	</table>
</div>

</body>
</html>
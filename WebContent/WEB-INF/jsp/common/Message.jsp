<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>PKMS (패키지 관리 시스템)</title>
	<link rel="stylesheet" type="text/css" href="/css/base.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.7.min.js"></script>
	<script type="text/javascript" src="/js/common.js"></script>
<script>
	function fn_onload_error() {
		//비즈니스 로직에서 발생한 메세지: link 인 경우에만 발생
		alert(document.getElementById("error_message").innerHTML);
		history.go(-1);
	}
</script>

</head>

<c:choose>
	<c:when test="${exception != null}">
		<c:choose>
			<c:when test="${fn:startsWith(exception.message, 'error.biz')}">
				<body onload="javascript:fn_onload_error();" oncontextmenu="return false">
					<div>.</div>
					<input type="hidden" id="resultType" name="resultType" value="error.biz" />
					<div style="display: none">
						<pre id="error_message">
							<c:out value="${fn:substring(exception.message, 10, fn:length(exception.message))}"/>
						</pre>
					</div>
				</body>
			</c:when>
			<c:when test="${fn:startsWith(exception.message, 'error.ext')}">
				<body onload="javascript:fn_onload_error();">
					<!--에러 -->
					<div class="error">
				
						<!--에러텍스트 -->
						<div class="error_tex">
							시스템 오류가 발생 하였습니다.<br/>오류가 계속되는 경우 관리자에게 문의하시기 바랍니다.
						</div>
			
					</div>
					<div style="display: none">
						<pre id="error_message">
							<c:out value="${exception.message}"/>
						</pre>
					</div>
				</body>
			</c:when>
			<c:otherwise>
				<!-- 시스템 에러 출력 화면 디자인 필요 -->
				<body>
					<div>.</div>
					<input type="hidden" id="resultType" name="resultType" value="error.sys" />

					<!--에러 -->
					<div class="error">
				
						<!--에러텍스트 -->
						<div class="error_tex">
							시스템 오류가 발생 하였습니다.<br/>오류가 계속되는 경우 관리자에게 문의하시기 바랍니다.
						</div>
			
					</div>
					<div style="display: none">
<!-- 					모의해킹 주석처리 -->
						<%-- <pre id="error_message">
							<c:out value="${exception.message}"/>
						</pre> --%>
					</div>
				</body>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<body>
				<div>.</div>
				<input type="hidden" id="resultType" name="resultType" value="<c:out value="success"/>">
		</body>
	</c:otherwise>
</c:choose>

</html>
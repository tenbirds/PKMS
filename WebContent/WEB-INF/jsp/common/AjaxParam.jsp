<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>PKMS (패키지 관리 시스템)</title>
</head>

<body>
	<dir>.</dir>
	<input type="hidden" id="resultType" name="resultType" value="<c:out value="success"/>">
	<input type="hidden" id="param1" name="param1" value="<c:out value="${param1}"/>">
	<input type="hidden" id="param2" name="param2" value="<c:out value="${param2}"/>">
	<input type="hidden" id="param3" name="param3" value="<c:out value="${param3}"/>">
	<input type="hidden" id="param4" name="param4" value="<c:out value="${param4}"/>">
	<input type="hidden" id="param5" name="param5" value="<c:out value="${param5}"/>">
</body>

</html>
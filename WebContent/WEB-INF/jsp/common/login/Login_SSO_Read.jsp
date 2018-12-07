<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>PKMS (패키지 관리 시스템)</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />                                              
<script>                                                                                                               
function fn_Sso_submit(){                                                                                              
	var form = document.getElementById("mForm");                                                                     
	form.target = "_self";                                                                                             
	form.action = "http://pkms.sktelecom.com/j_spring_security_check.do";                                                                      
	form.submit();                                                                                                       
}                                                                                                                 
</script>                                                                                                             
</head>                                                                                                                
<body>
	<form id='mForm' action='/j_spring_security_check.do' method='post' >
		<input type="hidden" id="check_user_type" name="check_user_type" value="<c:out value='${check_user_type}'/>" />
		<input type="hidden" id="j_username" name="j_username" value="<c:out value='${j_username}'/>" />
		<input type="hidden" id="loginType" name="loginType" value="<c:out value='${loginType}'/>" />
	</form>
	<script>                                                                                                               
		fn_Sso_submit();                                                                                              
	</script>                                                                                                             
</body>
</html>
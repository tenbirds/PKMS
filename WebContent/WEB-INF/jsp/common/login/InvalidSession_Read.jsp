<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PKMS (패키지 관리 시스템)</title>

<script type="text/javaScript">
//	fn_invaildSession(true); // frameset 용
//	fn_invaildSession();
//	function fn_invaildSession() {
		alert("안전한 PKMS 사용을 위해 자동 로그아웃 되었습니다.");
		
		if(opener != null) {
//			alert('opener is not null');
			try {
				opener.location.href = "/Login_Read.do";
				window.close();
			} catch (e) {
				if(top != null) {
//					alert('exception top is not null');
					top.location.href = "/Login_Read.do";
				} else {
//					alert('exception top is null');
					location.href = "/Login_Read.do";
				}
			}
		} else {
			if(top != null) {
//				alert('opener is null and top is not null');
				if(top.opener != null) {
//					alert('opener is null and top is not null and top.opener is not null');
					top.opener.location.href = "/Login_Read.do";
					top.close();
				} else {
//					alert('opener is null and top is not null and top.opener is null');
					top.location.href = "/Login_Read.do";
				}
			} else {
//				alert('opener is null and top is null');
				location.href = "/Login_Read.do";
			}
		}
//	}

</script>
</head>
<body>
	<div>
		<input type="hidden" id="resultType" name="resultType" value="invalid.session" />
	</div>
</body>
</html>


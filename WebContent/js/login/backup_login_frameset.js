function fn_invaildSession(sessionFlag) {
	if(sessionFlag) {
		alert("안전한 PKMS 사용을 위해 자동 로그아웃 되었습니다.");
	}	
	
	if(opener != null) {
		if (opener.top.frames["PKMS_MAIN"] != null) {
			opener.top.frames["PKMS_MAIN"].location.href = "/common/login/Login_Read.do";
		} else {
			opener.location.href = "/";
		}
		window.close();
	} else {
		if(top != null) {
			if(top.opener != null) {
				if (top.opener.top.frames["PKMS_MAIN"] != null) {
					top.opener.top.frames["PKMS_MAIN"].location.href = "/common/login/Login_Read.do";
				} else {
					top.opener.location.href = "/";
				}
				top.close();
			} else {
				if(sessionFlag) {
					if (top.frames["PKMS_MAIN"] != null) {
						top.frames["PKMS_MAIN"].location.href = "/common/login/Login_Read.do";
					} else {
						location.href = "/";
					}
				}
			}
		} else {
			if(sessionFlag) {
				if (top.frames["PKMS_MAIN"] != null) {
					top.frames["PKMS_MAIN"].location.href = "/common/login/Login_Read.do";
				} else {
					location.href = "/";
				}
			}
		}
	}
}


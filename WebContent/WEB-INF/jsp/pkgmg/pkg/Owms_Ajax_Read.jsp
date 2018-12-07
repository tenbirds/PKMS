<%--
/**
 * OWMS 팝업 띄우기
 * 도메인이 다르기 때문에 그냥 띄울수가 없고, 
 * Pkg_Popup_Read.jsp의 폼을 사용할려면 
 * enctype="multipart/form-data" 속성 때문에 문제가 발생 할 수 있음
 * 
 * @author : 009
 * @Date : 2012. 4. 17.
 */
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<script type="text/javaScript">

function onload() {
	
	document.charset="euc-kr";

	document.getElementById("REQ_NO").value = document.getElementById("pkg_seq").value;
	document.getElementById("REQ_TITLE").value = document.getElementById("title").value;
	document.getElementById("CHANGE_SUMARY").value = document.getElementById("pe_edit_title").value;
	document.getElementById("APPLICANT_SUMARY").value = document.getElementById("pe_content").value;
	document.getElementById("EQUIPMENT_SUMARY").value = document.getElementById("pe_equip_ip").value + ":" + document.getElementById("pe_equip_port").value;
	document.getElementById("TEST_MOBILE_NUM").value = 
		document.getElementById("pe_no_1").value + " | " +
		document.getElementById("pe_no_2").value + " | " +
		document.getElementById("pe_no_3").value + " | " +
		document.getElementById("pe_no_4").value + " | " +
		document.getElementById("pe_no_5").value
		;
	document.getElementById("EMERGENCY_FLAG").value = document.getElementById("pe_gubun").value;
	document.getElementById("TEST_DATE").value = (document.getElementById("pe_test_date").value).replace(/\-/gi, ".");

	var target = "PKG_OWMS_LINK_NEW";
// 	var url = "http://www.nate.com";
	var url = "http://owms.sktelecom.com/pkmsLink.do";  //owms 운영기 연동  URL
// 	var url = "http://owms.sktelecom.com:8088/pkmsLink.do";   //owms 스트용

	var ssWin = window.open("", target);

	var owms_form = document.getElementById("PkgModel");
	owms_form.target = target;
	owms_form.action = url;
	owms_form.submit();
	ssWin.focus();
}
</script>
</head>

<body onload="onload();">
	<form:form commandName="PkgModel" method="post">
		<form:hidden path="pkg_seq" />
		<form:hidden path="title" />
		<form:hidden path="pe_yn" />
		<form:hidden path="pe_edit_title" />
		<form:hidden path="pe_content" />
		<form:hidden path="pe_equip_ip" />
		<form:hidden path="pe_equip_port" />
		<form:hidden path="pe_no_1" />
		<form:hidden path="pe_no_2" />
		<form:hidden path="pe_no_3" />
		<form:hidden path="pe_no_4" />
		<form:hidden path="pe_no_5" />
		<form:hidden path="pe_gubun" />
		<form:hidden path="pe_test_date" />
	
		<input type="hidden" id="REQ_NO" name="REQ_NO" value="" />
		<input type="hidden" id="APPLICANT_ID" name="APPLICANT_ID" value="<sec:authentication property="principal.username"/>" />
		<input type="hidden" id="REQ_TITLE" name="REQ_TITLE" value="" />
		<input type="hidden" id="CHANGE_SUMARY" name="CHANGE_SUMARY" value="" />
		<input type="hidden" id="APPLICANT_SUMARY" name="APPLICANT_SUMARY" value="" />
		<input type="hidden" id="EQUIPMENT_SUMARY" name="EQUIPMENT_SUMARY" value="" />
		<input type="hidden" id="TEST_MOBILE_NUM" name="TEST_MOBILE_NUM" value="" />
		<input type="hidden" id="EMERGENCY_FLAG" name="EMERGENCY_FLAG" value="" />
		<input type="hidden" id="TEST_DATE" name="TEST_DATE" value="" />
	</form:form>
</body>
</html>

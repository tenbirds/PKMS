<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> -->
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=3.0, minimum-scale=0.5, user-scalable=yes" />

<title><decorator:title default="PKMS (패키지 관리 시스템)" /></title>
<link rel="stylesheet" type="text/css" href="/css/base.css" />
<link rel="stylesheet" type="text/css" href="/css/base1.css" />

<script type="text/javascript" src="/js/jquery/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/js/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/file.js"></script>

<!-- Js -->
<script type="text/javascript" src="/css/js/ui.js"></script>

<script type="text/javaScript">
	
	// 포커스가 select, text readonly 속성의 input에 있을 경우 백스페이스 안되게...
	document.onkeydown = function () {
	    var backspace = 8;
	    var t = document.activeElement;
	    if (event.keyCode == backspace) {
	        if (t.tagName == "SELECT"){
	            return false;
	        }
	        if (t.tagName == "INPUT" && t.getAttribute("readonly") == "readonly"){
	            return false;
	        }
	    }
	};
	
	
	$(document).ready(function($) {
		doLoadingInitStart();
	
// 		var index = 1;
// 		$("input:[name^=menu_id_array]").each(function() {
// 			doSubMenu($(this).val(), index++);
// 	  		});
					
		$('#main_top_logo').click(doMainUrl);
	
		doLoadingInitEnd();
		
//main 접속 후 공지사항		
// 		window.open("/Notice_Popup.do","bp_notice_popup","width=510, height=730, scrollbars=no, resizable=no, statusbar=no");
	});
		
	/* Home으로 */
	function doMainUrl() {
		top.location.href = "<c:url value='/'/>";
	}

	/* BP인 경우 정보변경 페이지 이동 */
	function fn_update_bp() {
		top.location.href = "<c:url value='/common/top/MyBp_Read.do'/>";
	}
		
</script>

<decorator:head />
</head>

<body onload="<decorator:getProperty property='body.onload' />" onunload="<decorator:getProperty property='body.onunload' />">

	<!--전체 레이아웃 시작-->
	<div id="common_wrapper">
		<div id="wrapper">
			<!--top 레이아웃 시작-->
			<div id="header" class="fl_wrap">
				<div id="top_navi1" class="header_wrap">
					<!--로고영역-->
					<div id="main_top_logo" class="header_logo"><a href="/">PKMS</a></div>
					<!-- header_nb -->
					<ul class="header_gnb">
						<li><a href="/pkgmg/pkg21/Pkg21_ReadList.do">PKG현황</a></li>
<!-- 						<li><a href="/pkgmg/pkg21/Pkg21_Read.do">PKG등록</a></li> -->
						<li><a href="/sys/group1/Group1_ReadList.do">시스템관리</a></li>
					</ul>
					<!-- header_nb -->
				</div>

			</div>
			<!--top 레이아웃 끝-->

			
			<div id="top_navi2" class="w3-container w3-cell">
				<!-- Header Menu1Depth -->
				<page:apply-decorator name="panel" page="/common/top/Header_Menu1Depth_ReadList.do" />
			</div>
			
			<!--서브메뉴 레이아웃 시작-->
			<%-- <div id="sm_Div1">
				<page:apply-decorator name="panel" page="/common/top/Header_Menu2Depth_ReadList.do" />
			</div> --%>
			<!--서브메뉴 레이아웃 끝-->

			<!--contents 레이아웃 시작-->
			<div id="centercolumn" class="w3-container w3-cell">
				<!-- 위치 타이틀 -->
				<page:apply-decorator name="panel" page="/common/top/Body_TitlePath_Read.do" />
				<decorator:body />
				
				<!--하단 레이아웃 -->
				<div id="footer">
					<span>Copyright © 2012,2018 SKTelecom All Rights Reserved</span>
				</div>
			</div>
			<!--contents 레이아웃 끝-->
			
		</div>
	</div>
	<!--전체 레이아웃 끝-->

</body>
</html>

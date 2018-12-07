<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=3.0, minimum-scale=0.5, user-scalable=yes" />
<!--
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, width=device-width" />
-->

<title><decorator:title default="PKMS (패키지 관리 시스템)" /></title>
<link rel="stylesheet" type="text/css" href="/css/base.css" />
<link rel="stylesheet" type="text/css" href="/css/base1.css" />

<script type="text/javascript" src="/js/jquery/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/js/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="/js/common.js"></script>

	<decorator:head/>
</head>

<body onload="<decorator:getProperty property='body.onload' />" onunload="<decorator:getProperty property='body.onunload' />">

<div class="mob_body">

	<decorator:body />

	<!--contents 레이아웃 끝-->

</div>
<!--전체 레이아웃 끝-->

</body>
</html>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Error1</title>
<link type="text/css" rel="stylesheet" href="/css/sample/sample.css" />
<style type="text/css">
* {
	margin: 0px;
	padding: 0px;
	font-size: 1em;
	font-family: Tahoma;
}

body {
	background: #eee;
}

span {
	margin-right: 15px;
	color: #46a;
	font-size: 120%;
}

A {
	text-decoration: none;
	color: #000000;
}

#content {
	position: absolute;
	width: 500px;
	height: 340px;
	margin: -170px 0px 0px -250px;
	padding: 20px;
	border: 1px solid #ccc;
	border-top: 5px solid #46a;
	background: #fff;
	top: 45%;
	left: 50%;
}

#title {
	margin-bottom: 30px;
	border-bottom: 1px dotted #ccc;
	font-size: 150%;
	font-weight: bold;
}

#desc {
	margin-bottom: 5px;
}
</style>
</head>
<body>
	<div id="content">
		<p id="title">
			<span>예외 발생</span>
		</p>
		<p id="desc">
			<a href="/" >[메인 페이지]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href=# onclick=history.back(-1);>[이전 페이지]</a>
		</p>
		<p>&nbsp;</p>
		<p id="desc"><c:out value="${sampleException.message}"/></p>
	</div>
</body>
</html>
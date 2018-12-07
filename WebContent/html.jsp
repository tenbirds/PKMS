<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="/css/sample/sample.css" />
<link type="text/css" rel="stylesheet" href="/css/basic.css" />

<!-- SiteMesh Include Start -->
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/jquery-1.7.min.js"></script>
<script type="text/javascript" src="/js/jquery/ui.core.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<!-- SiteMesh Include End -->
 
<script type="text/javascript">
	$(document).ready(function($) {
		doLoadingInitStart();

		doCalendar("datepicker");

		doTable("demoTable", "sDefault", "3", "2", 
				["120", "120", "200", "200", "200", "200", "200", "200"]);

		doLoadingInitEnd();
	});
	
function fn_layer_3_1_close() {
	parent.$('img[id=open_3_1]').closeDOMWindow();
}
</script>
</head>
<body topmargin="0" leftmargin="0">
<div id="wrapper">
<form:form commandName="OrganizationModel" name="detailForm">
	<a href="/organization/Organization_Auth_ReadList.do">조직도[권한] + 날짜선택(mvc적용)</a><br/>
	<a href="/organization/Organization_Charge_ReadList.do">조직도[담당]</a><br/>
	<a href="/pkg/diary/Pkg_Diary_ReadList.do">PKG 일정</a><br/>
	<a href="javascript:fn_layer_3_1_close();">fn_layer_3_1_close</a><br/>
	
	<br/>날짜선택(html)
	<p>Date: <input type="text" id="datepicker" readonly></p>

	
	<br/><br/>* 테이블 스크롤
	<div class="fakeContainer" style="width:1000px;">
	<table id="demoTable">
	 <tr>
	 <th rowspan="3">Account</th>
	 <th rowspan="3">First Name</th>
	 <th>Last Name</th>
	 <th colspan="3">Age</th>
	 <th>Favorite Color</th>
	 <th rowspan="3">Favorite Season</th>
	 </tr>
	 <tr>
	 <th>Last Name</th>
	 <th>Age</th>
	 <th colspan="2">State</th>
	 <th>Email Address</th>
	 </tr>
	 <tr>
	 <th>Last Name</th>
	 <th>Age</th>
	 <th>State</th>
	 <th>Favorite Color</th>
	 <th>Favorite Season</th>
	 </tr>
	 <tr>
	 <td>account0001</td>
	 <td>Jim</td>
	 <td>Bo</td>
	 <td>25</td>
	 <td>Delaware</td>
	 <td>Jim.Bo@gmail.com</td>
	 <td>Blue</td>
	 <td>Winter</td>
	 </tr>
	 <tr>
	 <td>account0002</td>
	 <td>Alley</td>
	 <td>Bo</td>
	 <td>28</td>
	 <td>Delaware</td>
	 <td>Alley.Bo@gmail.com</td>
	 <td>Red</td>
	 <td>Summer</td>
	 </tr>
	 <tr>
	 <td>account0003</td>
	 <td>Pablo</td>
	 <td>Picasso</td>
	 <td>65</td>
	 <td>N/A</td>
	 <td>pablo.picasso@universe.com</td>
	 <td>All</td>
	 <td>Spring</td>
	 </tr>
	 <tr>
	 <td>account0004</td>
	 <td>Jen</td>
	 <td>Dotsen</td>
	 <td>36</td>
	 <td>Maryland</td>
	 <td>jdotsen@yahoo.com</td>
	 <td>Pink</td>
	 <td>Winter</td>
	 </tr>
	 <tr>
	 <td>account0005</td>
	 <td>Bill</td>
	 <td>Tucker</td>
	 <td>12</td>
	 <td>Wyoming</td>
	 <td>superman@gmail.com</td>
	 <td>Green</td>
	 <td>Fall</td>
	 </tr>
	 <tr>
	 <td>account0006</td>
	 <td>Mary</td>
	 <td>Swanson</td>
	 <td>35</td>
	 <td>Colorado</td>
	 <td>mary.swanson@samsonite.com</td>
	 <td>Green</td>
	 <td>Spring</td>
	 </tr>
	 <tr>
	 <td>account0007</td>
	 <td>Chris</td>
	 <td>Tucker</td>
	 <td>38</td>
	 <td>California</td>
	 <td>ctucker@rushhour.com</td>
	 <td>Gold</td>
	 <td>Summer</td>
	 </tr>
	 <tr>
	 <td>account0008</td>
	 <td>Bat</td>
	 <td>Man</td>
	 <td></td>
	 <td>New York</td>
	 <td>batman@gotham.net</td>
	 <td>Black</td>
	 <td></td>
	 </tr>
	 <tr>
	 <td>account0009</td>
	 <td>Jimmy</td>
	 <td>Johnson</td>
	 <td>31</td>
	 <td>Nascar</td>
	 <td>jj@nascar.us</td>
	 <td>White</td>
	 <td>Summer</td>
	 </tr>
	 <tr>
	 <td>account0010</td>
	 <td>Britney</td>
	 <td>Spears</td>
	 <td>11</td>
	 <td>Tennessee</td>
	 <td>bspears@asylum.com</td>
	 <td>Red</td>
	 <td>Spring</td>
	 </tr>
	 <tr>
	 <td>account0011</td>
	 <td>M</td>
	 <td>Jordan</td>
	 <td>23</td>
	 <td>Illinois</td>
	 <td>mjordan@dabulls.com</td>
	 <td>Red</td>
	 <td>Winter</td>
	 </tr>
	 <tr>
	 <td>account0012</td>
	 <td>50</td>
	 <td>Cent</td>
	 <td>34</td>
	 <td>Alabama</td>
	 <td>fiddy@bama.com</td>
	 <td>Platinum</td>
	 <td>Fall</td>
	 </tr>
	 <tr>
	 <td>account0013</td>
	 <td>Jim</td>
	 <td>Bo</td>
	 <td>25</td>
	 <td>Delaware</td>
	 <td>Jim.Bo@gmail.com</td>
	 <td>Blue</td>
	 <td>Winter</td>
	 </tr>
	 <tr>
	 <td>account0014</td>
	 <td>Alley</td>
	 <td>Bo</td>
	 <td>28</td>
	 <td>Delaware</td>
	 <td>Alley.Bo@gmail.com</td>
	 <td>Red</td>
	 <td>Summer</td>
	 </tr>
	 <tr>
	 <td>account0015</td>
	 <td>Pablo</td>
	 <td>Picasso</td>
	 <td>65</td>
	 <td>N/A</td>
	 <td>pablo.picasso@universe.com</td>
	 <td>All</td>
	 <td>Spring</td>
	 </tr>
	 <tr>
	 <td>account0016</td>
	 <td>Jen</td>
	 <td>Dotsen</td>
	 <td>36</td>
	 <td>Maryland</td>
	 <td>jdotsen@yahoo.com</td>
	 <td>Pink</td>
	 <td>Winter</td>
	 </tr>
	 <tr>
	 <td>account0017</td>
	 <td>Bill</td>
	 <td>Tucker</td>
	 <td>12</td>
	 <td>Wyoming</td>
	 <td>superman@gmail.com</td>
	 <td>Green</td>
	 <td>Fall</td>
	 </tr>
	 <tr>
	 <td>account0018</td>
	 <td>Mary</td>
	 <td>Swanson</td>
	 <td>35</td>
	 <td>Colorado</td>
	 <td>mary.swanson@samsonite.com</td>
	 <td>Green</td>
	 <td>Spring</td>
	 </tr>
	 <tr>
	 <td>account0019</td>
	 <td>Chris</td>
	 <td>Tucker</td>
	 <td>38</td>
	 <td>California</td>
	 <td>ctucker@rushhour.com</td>
	 <td>Gold</td>
	 <td>Summer</td>
	 </tr>
	</table>
	</div>
	<br/><br/>

	* 영역 스크롤<br/>
	<!-- 영역 스크롤 class="pscroll" style="width:500px;height:100px;" -->
	<div class="pscroll" style="width:500px;height:100px;">
	<table  width="1200" border="1">
	    <colgroup>
	        <col width="60" />
	        <col width="*" />
	        <col width="100" />
	        <col width="100" />
	        <col width="50" />
	    </colgroup>
	    <thead>
	        <tr>
	            <th scope="col">No.</th>
	            <th scope="col">제목</th>
	            <th scope="col">작성자</th>
	            <th scope="col">작성일</th>
	            <th scope="col">조회</th>
	        </tr>
	    </thead>
	    <tfoot>
	        <tr>
	            <td colspan="5">
	                <a href="#">1</a>
	                <a href="#">2</a>
	                <a href="#">3</a>
	                <a href="#">4</a>
	                <a href="#">5</a>
	            </td>
	        </tr>
	    </tfoot>
	    <tbody>
	        <tr>
	            <td>71</td>
	            <td><a href="#">게시판 제목이 나타나는 부분</a></td>
	            <td>홍길동</td>
	            <td>2011-03-10</td>
	            <td>444</td>
	        </tr>
	        <tr>     
	            <td>70</td>
	            <td><a href="#">게시판 제목이 나타나는 부분</a></td>
	            <td>홍길동</td>
	            <td>2011-02-21</td>
	            <td>1026</td>
	        </tr>
	    </tbody>
	</table>
	</div>
	<br/><br/>



	</form:form>
</div>
</body>
</html>
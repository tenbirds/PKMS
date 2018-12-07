<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />

<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>
<script type="text/javascript" src="/js/pkgmg/pkgmg.pkg.js"></script>
<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javaScript" defer="defer">
function fn_confirm(){
	var check_mails =$("input[name='check_mails']"); 
	var count = $("#check_mails_count").val();
	var mails="";
	var cnt=0;
	for(var i=0; i < count; i++){
		if(check_mails.eq(i).is(":checked")){
			if(cnt > 0){
				mails = mails+","+check_mails.eq(i).val();
			}else{				
				mails = check_mails.eq(i).val();
			}
			cnt++;
		}
	}
	
	parent.fn_callback_mail_popup(mails);
}
function fn_close(){
	parent.$("#road_map_write").closeDOMWindow();
};
</script>
</head>

<body>
<form:form commandName="SolutionReportModel" method="post" enctype="multipart/form-data">
		<div class="fl_right mr10">
			<a href="javascript:fn_close();">
				<img alt="닫기" src="/images/pop_btn_close2.gif" width="15" height="14">
			</a>
		</div>
		<div class="popup_contents" style="width:95%;padding-top:10px;">
			
			<div class="fl_wrap">
				<h1 class="tit">메일 목록</h1>
			</div>
			
			<div class="con_area">
				<!--기본정보 -->
				<div class="con_width95 mt20 mg_c" style="border-bottom:1px solid #ddd;">
					<table class="tbl_type12">
						<colgroup>
							<col width="5%">
							<col width="25%">
							<col width="30%">
							<col width="40%">
						</colgroup>
						<thead>
							<tr>
								<th >&nbsp;</th>
								<th >이름</th>
								<th >부서</th>
								<th >이메일</th>
							</tr>
						</thead>
					</table>
					<div style="height:185px;overflow-y:auto;overflow-x: hidden;">
					<table class="tbl_type22">
						<colgroup>
							<col width="5%">
							<col width="25%">
							<col width="30%">
							<col width="40%">
						</colgroup>
						<tbody>
							<c:forEach var="result" items="${SolutionReportModel.solutionReportMailList}" varStatus="status">
								<tr>
									<td class="td_center">
										<input type="checkbox" id="check_mails_${status.index}" name="check_mails" value="${result.user_email}"/>
									</td>
									<td class="td_center">${result.user_name}</td>
									<td class="td_center">${result.sosok}</td>
									<td class="td_center">${result.user_email}</td>
								</tr>
								<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_mails_count"/></c:if>
							</c:forEach>
						</tbody>
					</table>
					</div>
				</div>
				<!--버튼위치영역 -->
				<div class="mg_c" style="width:10%;">
					<span class="btn_line_blue3"><a href="javascript:fn_confirm()">확인</a></span>	
				</div>
			</div>			
		</div>
</form:form>
</body>
</html>


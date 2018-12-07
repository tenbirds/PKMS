<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/supertable/supertable.css" />
<script type="text/javascript" src="<c:url value='/js/jquery/supertable/supertable.js'/>"></script> 
<script type='text/javascript' src='/js/pkgms/pkgms.common.js'></script>

<script type="text/javaScript">
function fn_readList() {
	var form = document.getElementById("PkgModel");
	form.target = "_self";
	form.action = "<c:url value='/pkgmg/pkg/PkgDetailSearch_Popup_ReadList.do'/>";
	form.submit();
}

</script>

</head>
<body>

<form:form commandName="PkgModel" method="post" enctype="multipart/form-data">
<form:hidden path="pageIndex" />
<form:hidden path="system_seq" />
<form:hidden path="system_name" />
<form:hidden path="retUrl" />
<form:hidden path="pkg_seq" />
<form:hidden path="pkg_detail_seq" />
<form:hidden path="content" />

	<!-- m_tab_search PKG현황, 일정목록 검색 -->
	<div style="width:1000px; top:0px; left:0px" id="pop_wrap">
	<!--팝업 content -->
				
		<div id="pop_content" style="border-width:0px;">  
			<a class="close_layer" onClick="window.close();" href="#"><img alt=레이어닫기 src="/images/pop_btn_close2.gif" width="15" height="14"></a>

			<h4 class="ly_header">보완적용내역 검색 상세</h4>
			<div  style=" overflow-x:scroll; overflow-y:hidden">
				<table class=pop_tbl_type3  style=" width:1000px;">
					<thead>
						<tr>
							<c:forEach var="result" items="${PkgModelData.tempmgModelList}" varStatus="status">
								<th>${result.title}</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${PkgModelData.pkgDetailVariableModelList}" varStatus="st">
							<c:set var="rt" value="${PkgModelData.pkgDetailModelList[st.index]}" />
							<tr>
								<td><textarea readonly style="width:50px; height:500px; overflow:hidden;">${rt.no}</textarea></td>
								<td><textarea readonly style="width:50px; height:500px; overflow:hidden;">${rt.new_pn_cr_gubun}</textarea></td>
								<c:forEach var="rt_sub" items="${PkgModelData.pkgDetailVariableModelList[st.index]}" varStatus="st_sub">
									<td>
										<textarea readonly style="width:${(empty PkgModelData.templateWidths[st_sub.index]) ? 50 : PkgModelData.templateWidths[st_sub.index]}px; height:500px; overflow:auto;">${rt_sub.content}</textarea>
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table> 
			</div>  
		</div>
		
		<div class="btn_location">
			<span><a href="javascript:fn_readList();"><img src="/images/btn_list.gif" alt="목록" /></a></span>
		</div>	 
	</div> 
	
		<!--조회, 테이블, 페이지수 div 끝 -->
	</form:form>

</body>
</html>

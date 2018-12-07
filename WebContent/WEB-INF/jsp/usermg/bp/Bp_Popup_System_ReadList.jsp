<%--
/**
 * 협력업체 담당자의 시스템 등록 현황
 * 
 * @author : skywarker
 * @Date : 2012. 09. 04.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<link rel="stylesheet" type="text/css" href="/css/supertable/supertable.css" />
<link rel="stylesheet" type="text/css" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/supertable/supertable.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui.custom.js"></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(
		function($) {
			doTable("scrollTable", "tbl_type", "1", "0", [ "700" ]);
		});
	
	/* 팝업 닫기 */
	function fn_popup_close() {
		parent.$('img[id=open_bp_popup]').closeDOMWindow();
	}

</script>
</head>
<body>

		<!--팝업 전체레이아웃 시작-->
		<div style="width: 780px;" id="pop_wrap">

			<!--팝업 content -->
			<div id="pop_content">

				<!--타이틀 -->
				<h4 class="ly_header">담당 시스템 목록</h4>

				<fieldset>
					<div style="height: 450px; padding: 5px; float: left; vertical-align: top; border: 1px solid #dcdcdc;">

						<!--조회영역 -->
						<div class="pop_search" style="width: 730px; border : 0px; height: 2px;">
							
						</div>
					
						<!--테이블, 페이지 DIV 시작 -->
						<div class="con_Div2" style="margin-top: 45px; width:740px; position: absolute;">
							<div class="fakeContainer" style="width: 725px; height: 380px;">
								<table id="scrollTable" class="tbl_type" border="1">
									<thead>
										<tr>
											<th scope="col" style="paddingleft : 10px;">시스템 경로</th>
										</tr>
									</thead>
									<tbody>										
										<c:if test="${BpModel.totalCount == 0}">
											<tr>
												<td colspan="5">등록된 시스템이 없습니다.</td>
											</tr>
										</c:if>
										<c:forEach var="result" items="${BpUserModelList}" varStatus="status">
											<tr>
												<td style="letter-spacing: 1px;"><c:out value="${result.group_depth}" /></td>
											</tr>
										</c:forEach>										
									</tbody>
								</table>
							</div>
						</div>
						
					</div>
				</fieldset>
				
			</div>
			<span class="shadow shadow2"> </span> <span class="shadow shadow3"> </span> <span class="shadow shadow4"> </span>		
		</div>
		<!-- 팝업 전체레이아웃 끝 -->
		
</body>
</html>
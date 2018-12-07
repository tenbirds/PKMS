<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
	pageContext.setAttribute("cr", "\r");
%>

<html>
<head>
	<meta name="decorator" content="mobile" />
	<meta name="viewport" content="width=480, initial-scale=-1, user-scalable=yes,target-densitydpi=device-dpi" />
	<script type="text/javaScript" defer="defer">
		var apply_cnt = 0;
		function fn_pkg_6(turn_down, ord){
			if(apply_cnt > 0){
				alert("이미 버튼을 누르셨습니다. 화면이 바뀔 때까지 조금만 기다려 주세요.");
				return;
			}else {
				apply_cnt++;
			}
			
			if(turn_down) {
				if(confirm("반려 하시겠습니까?")) {
					var au_comment = $("input[id=au_comment]");
					if (au_comment.val() == "") {
						alert("반려 사유는 필수항목입니다.");
						return;
					}
					$("input[id=pkgStatusModel_col20]").val(au_comment.val());
				} else {
					return;
				}
			}else{
				if(confirm("승인 하시겠습니까?")) {
					
				}else{
					return;
				}
			}
			
			var status = $("input[id=status]");
			var operation = $("input[id=status_operation]");
			$("input[id=ord]").val(ord);
			operation.val("C");
			fn_pkgstatus_create(turn_down);
			/* if(status.val() == "5") {
				$("input[id=ord]").val(ord);
				operation.val("C");
				$("input[id=selected_status]").val("6");
				fn_pkgstatus_create(turn_down);
			}
			
			if(status.val() == "23") {
				$("input[id=ord]").val(ord);
				operation.val("C");
				$("input[id=selected_status]").val("24");
				fn_pkgstatus_create(turn_down);
			}
			
			if(status.val() == "25") {
				$("input[id=ord]").val(ord);
				operation.val("C");
				$("input[id=selected_status]").val("26");
				fn_pkgstatus_create(turn_down);
			} */
			
			
		}
		
		//Pkg 상태값 create
		function fn_pkgstatus_create(turn_down) {
			var status = $("input[id=status]");
			var msg = "";
			if(turn_down) {
				$("input[id=turn_down]").val("true");
				msg = "반려 되었습니다.";
			} else {
				$("input[id=turn_down]").val("false");
				msg = "승인 되었습니다.";
			}
			
			doSubmit4File("PkgModel", "/pkgmg/pkg/PkgStatus_Create.do", "fn_callback_pkgstatus_create", msg);
		}
		
		//Pkg 상태값 callback
		function fn_callback_pkgstatus_create() {
			var sel_status = $("input[id=selected_status]").val();
			if(sel_status = "26"){
				$("input[id=selected_status]").val('25');
			}else{
				$("input[id=selected_status]").val('99');
			}
			var form = document.getElementById("PkgModel");
			form.action = "<c:url value='/pkgmg/mobile/Mobile_Read.do'/>";
			form.submit();
		}

		function fn_readList() {
			var form = document.getElementById("PkgModel");
			form.action = "/Main.do";
			form.submit();
		}
		
		function downloadFile(file_name, file_org_name, file_path){
			file_org_name = encodeURIComponent(file_org_name);
			file_org_name = encodeURIComponent(file_org_name);
			
			$("#resultHTML").html("<iframe name='filedownload' style='width:0px;height0px;display:none'></iframe>");
			var formData = "<form name='fileForm' method='post' target='filedownload' action='/common/attachfile/AttachFile_read.do?file_name=" + file_name + "&file_org_name=" + file_org_name + "&file_path=" + file_path + "'></form>";
			$(formData).appendTo('body').submit().remove();
		}
		
	</script>
</head>

<body>
	<!--탑1 -->
	<div class="mob_top1 fl_wrap">
		<h1>
			PKMS <span>Package Management system</span>
		</h1>
		<!--로그아웃 -->
		<div class="mob_logout"><a href="/Login_Delete.do"><img src="/images/mob_btn_logout.png" alt="로그아웃" /></a></div>
	</div>

	<!--탑2 -->
	<div class="mob_top2">
		<sec:authorize ifAnyGranted = "ROLE_ADMIN, ROLE_MANAGER, ROLE_OPERATOR">
		<div class="mob_title1 fl_wrap">
			<!--타이틀 -->
			<h2><strong>PKMS</strong> 승인요청</h2>
		</div>
		<!--텍스트 -->
		<div class="mob_top2_tex1"><strong>${PkgModel.session_user_name}</strong>님의 <u>승인관련 패키지 검증/과정</u> 목록입니다</div>
		

		</sec:authorize>
	</div>

	<form:form commandName="PkgModel" method="post" onsubmit="return false;">
	
<%-- 121109 	
<form:hidden path="pkg_seq" /> 
--%>

	<!--컨텐츠 -->
	<div class="mob_contents">

		<form:hidden path="pkg_seq" />
		<form:hidden path="ord" />
		<form:hidden path="status" />
		<form:hidden path="selected_status" />
		<form:hidden path="status_operation" />
		<form:hidden path="pkgStatusModel.col20" />
		<form:hidden path="turn_down" />
		<form:hidden path="ver" />
		<form:hidden path="system_name" value="${PkgModel.group_depth}"/>
		<form:hidden path="title" />
		<form:hidden path="content" />
		<form:hidden path="roaming_link"/>
		<form:hidden path="system_seq"/>
		<form:hidden path="dev_yn_bak" value="${PkgModel.dev_yn}"/>
		<form:hidden path="vol_yn"/>
		<form:hidden path="sec_yn"/>
		<form:hidden path="cha_yn"/>
		<form:hidden path="non_yn"/>
		<form:hidden path="on_yn"/>
		<form:hidden path="status_dev"/>
		<form:hidden path="rm_issue_comment"/>

		<form:hidden path="startD" value="${PkgModel.startD}" />
		<form:hidden path="endD" value="${PkgModel.endD}"/>
		<div class="caption">승인 목록</div>
		<table class="tbl_type12" cellspacing="0">
			<thead>
			<tr>
				<th style="width:40px;">상태</th>
				<th style="width:40px;">승인</th>
				<th style="width:70px;">이름</th>
				<th style="width:110px;">승인일시</th>
				<th>comment</th>
			</tr>
			</thead>
			<tbody>
					
			<c:forEach var="result" items="${PkgModelStatus.pkgUserModelList}" varStatus="status">
				<tr>
					<td>
						<c:choose>
							<c:when test="${result.status == 'F'}">
								<font color="blue">승인</font>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${result.ord == PkgModelStatus.user_active_status and result.user_id == PkgModel.session_user_id}">
										<c:set var="v_ord" value="${result.ord}" />
										<font color="red"><b>요청</b></font>
									</c:when>
									<c:otherwise>
										<font color="gray">요청</font>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
					<td>${status.count} 차</td>
					<td>${result.user_name}</td>
					<td>
						<c:choose>
							<c:when test="${not empty result.update_date}">
								${result.update_date}
							</c:when>
							<c:otherwise>
								&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
					<td style="text-align:left; padding-left:10px;">
						<c:choose>
							<c:when test="${result.status == 'F'}">
								${result.au_comment}&nbsp;
							</c:when>
							<c:otherwise>
								<c:if test="${result.ord == PkgModelStatus.user_active_status and result.user_id == PkgModel.session_user_id}">
									<input id="au_comment" name="au_comment" style="width:180px" class="inp" type="text" value="${result.au_comment}"/>
								</c:if>
								&nbsp;
							</c:otherwise>
						</c:choose>
					</td>
					
				</tr>
			</c:forEach>
			
			</tbody>
		</table>
	
	</div>
		<!--버튼위치 -->
		<div class="mob_top2">
			<!--목록 -->
			<div class="mob_btn2 btn_line_blue3">
				<span><a href="javascript:fn_readList();">목록</a></span>
			</div>
			<!--승인,반려 -->
			<div class="mob_btn1">
				<c:if test="${not empty v_ord}">
					<span><a href="javascript:fn_pkg_6(false, '${v_ord}')"><img src="/images/mob_btn_approbation.gif" alt="승인" /></a></span> <span><a href="javascript:fn_pkg_6(true, '${v_ord}')"><img src="/images/mob_btn_return.gif" alt="반려" width="71" height="30" /></a> </span>
				</c:if>
			</div>
		</div>

	<div class="mob_contents">
		<div class="caption">기본정보</div>
		<table class="tbl_type11" cellspacing="0" style="width:100%; table-layout: fixed;">
			<colgroup>
				<col width="20%" />
				<col width="45%" />
				<col width="15%" />
				<col width="20%" />
			</colgroup>
			<tbody>
				<tr>
					<th style="width:80px;">시스템</th>
					<td colspan="3">
						<div class="pop_system">
							${PkgModel.group_depth}
						</div>
					</td>
				</tr>
				<tr>
					<th style="width:80px;">제목</th>
					<td>${PkgModel.title}</td>
					<th style="width:80px;">담당자</th>
					<td>${PkgModel.system_user_name}</td>
				</tr>
				<tr>
					<th>대상 시스템</th>
					<td>
						<c:forEach var="result" items="${PkgEqList}" varStatus="status">
							<c:if test = "${result.work_gubun eq 'S'}">
							<p align="left">초도- ${result.idc_name} ${result.eq_name} 등 ${result.eq_cnt}식</p>
							</c:if>
							<c:if test = "${result.work_gubun eq 'E'}">
							<p align="left">확대- ${result.idc_name} ${result.eq_name} 등 ${result.eq_cnt}식</p>
							</c:if>
						</c:forEach>
					</td>
					<th>초도작업</th>
					<td>
						<c:forEach var="result" items="${PkgTimeList}" varStatus="status">
							<c:if test = "${result.work_gubun eq 'S'}">
							<p align="left">초도- ${result.min_date} ~ ${result.max_date}</p>
							</c:if>
							<c:if test = "${result.work_gubun eq 'E'}">
							<p align="left">확대- ${result.min_date} ~ ${result.max_date}</p>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>PKG 버전</th>
					<td>${PkgModel.ver}</td>
					<th>버전구분</th>
					<td>
						<c:choose>
							<c:when test="${PkgModel.ver_gubun == 'F'}">
								Full
							</c:when>
							<c:otherwise>
								Partial
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>주요 보완내역</th>
					<td colspan="3" style="word-break:break-all;">${fn:replace(PkgModel.content, cr, "<br/>")}</td>
				</tr>
				<tr>
					<th class="th_height">RM/CEM<br/>관련 feature</th>
					<td colspan="3" style="word-break:break-all;">${fn:replace(PkgModel.rm_issue_comment, cr, "<br/>")}</td>
				</tr>
				<tr>
					<th class="th_height">PKG 적용시<br/>RM 검토</th>
					<td colspan="3">
						<c:choose>
							<c:when test="${PkgModel.ser_yn == 'Y'}">
								영향도있음 ${PkgModel.ser_content}
							</c:when>
							<c:otherwise>
								영향도없음
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th class="th_height">서비스 중단시간</th>
					<td colspan="3">${PkgModel.ser_downtime} (분)</td>
				</tr>
				<tr>
					<th>로밍 영향도</th>
					<td colspan="3">
						<c:choose>
							<c:when test="${PkgModel.roaming_link ne 'N'}">
								<p style="text-align: left;">[로밍연동-${PkgModel.roaming_link}] ${PkgModel.roaming_link_comment}</p>
							</c:when>
							<c:otherwise>
								<p style="text-align: left;">[로밍영향도 없음]</p>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>과금 영향도</th>
					<td colspan="3">
						<c:choose>
							<c:when test="${PkgModel.pe_yn ne 'N'}">
								<p style="text-align: left;">[과금영향도 있음] ${PkgModel.pe_yn_comment}</p>
							</c:when>
							<c:otherwise>
								<p style="text-align: left;">[과금영향도 없음]</p>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</tbody>
		</table>
		
		<br/>
		<div class="caption">초도적용장비</div>
		<table class="tbl_type12">
				<tr>
					<th style="width:110px;">국사</th>
					<th style="width:110px;">장비</th>
					<th style="width:40px;">구분</th>
					<th>적용일시</th>
				</tr>
			<c:forEach var="result" items="${PkgModel.pkgEquipmentModelList}" varStatus="status">
				<tr>
					<td>
						${result.team_name}
					</td>
					<td>
						${result.equipment_name}
					</td>
					<td>
						<c:if test="${result.work_gubun eq 'S'}">
							초도
						</c:if>
						<c:if test="${result.work_gubun eq 'E'}">
							확대
						</c:if>
					</td>
					<td>
						<div>${result.work_date} ${result.start_time1}:${result.start_time2} ~ ${result.end_time1}:${result.end_time2}</div>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
		<div class="caption">검증진도율</div>
		<table class="tbl_type12" cellspacing="0" style="border-collapse: collapse;">
			<colgroup>
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
			</colgroup>
			<thead>
			<tr>
				<th></th>
				<th>항목수</th>
				<th>검증내역개수</th>
				<th>개선내역개수</th>
				<th>검증진도율</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<th>신규</th>
				<td>${PkgModelProgress.new_col1 }</td>
				<td>${PkgModelProgress.new_col2 }</td>
				<td>${PkgModelProgress.new_col3 }</td>
				<td>
					<c:choose>
						<c:when test="${PkgModelProgress.new_col4 > 0 }">
							<fmt:formatNumber value="${PkgModelProgress.new_col4 / PkgModelProgress.new_col1 * 100 }" pattern="0" />
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
					%
				</td>
			</tr>
			<tr>
				<th>보완</th>
				<td>${PkgModelProgress.pn_col1 }</td>
				<td>${PkgModelProgress.pn_col2 }</td>
				<td>${PkgModelProgress.pn_col3 }</td>
				<td>
					<c:choose>
						<c:when test="${PkgModelProgress.pn_col4 > 0 }">
							<fmt:formatNumber value="${PkgModelProgress.pn_col4 / PkgModelProgress.pn_col1 * 100 }" pattern="0" />
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
					%
				</td>
			</tr>
			<tr>
				<th>개선</th>
				<td>${PkgModelProgress.cr_col1 }</td>
				<td>${PkgModelProgress.cr_col2 }</td>
				<td>${PkgModelProgress.cr_col3 }</td>
				<td>
					<c:choose>
						<c:when test="${PkgModelProgress.cr_col4 > 0 }">
							<fmt:formatNumber value="${PkgModelProgress.cr_col4 / PkgModelProgress.cr_col1 * 100 }" pattern="0" />
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
					%
				</td>
			</tr>
			<tr>
				<th>합계</th>
				<td>${PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1 }</td>
				<td>${PkgModelProgress.new_col2 + PkgModelProgress.pn_col2 + PkgModelProgress.cr_col2 }</td>
				<td>${PkgModelProgress.new_col3 + PkgModelProgress.pn_col3 + PkgModelProgress.cr_col3 }</td>
				<td>
					<c:choose>
						<c:when test="${(PkgModelProgress.new_col4 + PkgModelProgress.pn_col4 + PkgModelProgress.cr_col4) / (PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1) * 100 != 'NaN'}">
							<fmt:formatNumber value="${(PkgModelProgress.new_col4 + PkgModelProgress.pn_col4 + PkgModelProgress.cr_col4) / (PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1) * 100 }" pattern="0" />
						</c:when>
						<c:otherwise>
							0
						</c:otherwise>
					</c:choose>
					%
				</td>
			</tr>	
			</tbody>
		</table>
		
		<br/>
		
		<div class="caption">보완적용내역 목록</div>
		<table class="tbl_type12" cellspacing="0">
			<thead>
			<tr>
				<th style="width:40px;">No</th>
				<th style="width:40px;">분류</th>
				<th style="width:40px;">중요도</th>
				<th>제목</th>
				<th style="width:50px;" class="th_height">상용검증결과</th>
				<th style="width:50px;" class="th_height">검증내역개수</th>
				<th style="width:50px;" class="th_height">개선내역개수</th>
			</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${fn:length(PkgModelList) > 0}">
					<c:forEach var="result" items="${PkgModelList}" varStatus="status">
						<tr>
							<td>${result.bp_step3_no }</td>
							<td>${result.bp_step3_new_pn_cr_gubun }</td>
							<td>${result.bp_step3_content_ord_0 }</td>
							<td style="text-align:left;">${result.bp_step3_content_ord_1 }</td>
							<td>${result.bp_step3_content_ord_2 }</td>
							<td>${result.bp_step3_content_ord_18 }</td>
							<td>${result.bp_step3_content_ord_19 }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="7">보완적용내역 목록이 존재하지 않습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
		
		<br/>
		
		<div class="caption">공급사 자체 검증</div>
		<table class="tbl_type11" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="30%" />
				<col width="*" />
			</colgroup>
			<tr>
				<th>검증일자<span class='necessariness'>*</span></th>
				<td>${PkgModelSktStep3.col1 } ~ ${PkgModelSktStep3.col2 }</td>
			</tr>
			<tr>
				<th rowspan="2">보완내역별 시험 결과 <span class='necessariness'>*</span></th>
				<td rowspan="2">
					${PkgModelSktStep3.col3 }
				</td>
				<td>
					${PkgModelSktStep3.col4 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file37 != null}">
						<ui:file attachFileModel="${PkgModel.file37}" name="file37" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>

			<tr>
				<th rowspan="2" class="th_height">Regression Test 및<br/>기본 검증 결과 <span class='necessariness'>*</span></th>
				<td rowspan="2">
					${PkgModelSktStep3.col5 }
				</td>
				<td>
					${PkgModelSktStep3.col6 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file5 != null}">
						<ui:file attachFileModel="${PkgModel.file5}" name="file5" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<th rowspan="2">성능 용량 시험 결과</th>
				<td rowspan="2">
					${PkgModelSktStep3.col7 }
				</td>
				<td>
					${PkgModelSktStep3.col8 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<%-- <c:if test="${PkgModel.file11 != null}">
						<ui:file attachFileModel="${PkgModel.file11}" name="file11" size="50" mode="mobile" />
					</c:if> --%>
					<c:if test="${PkgModel.file6 != null}">
						<ui:file attachFileModel="${PkgModel.file6}" name="file6" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
		</table>
		
		<br/>
		
		<div class="caption">개발 검증</div>
		<table class="tbl_type11" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
			<colgroup>
				<col width="30%" />
				<col width="*" />
			</colgroup>
			<tr>
				<th rowspan="2">신규 기능 규격</th>
				<td rowspan="2">
					${PkgModelSktStep3.col9 }
				</td>
				<td>
					${PkgModelSktStep3.col10 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file7 != null}">
						<ui:file attachFileModel="${PkgModel.file7}" name="file7" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<th rowspan="2">기능 및 과금 검증 결과 <span class='necessariness'>*</span></th>
				<td rowspan="2">
					${PkgModelSktStep3.col11 }
				</td>
				<td>
					${PkgModelSktStep3.col12 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file8 != null}">
						<ui:file attachFileModel="${PkgModel.file8}" name="file8" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
		</table>
		
		<br/>
		
		<div class="caption">상용화 검증</div>
		<table class="tbl_type11" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
			<colgroup>
				<col width="40%" />
				<col width="*" />
			</colgroup>
			<tr>
				<th rowspan="2" class="th_height">보완내역서,<br/>기능 변경 요청서 <span class='necessariness'>*</span></th>
				<td rowspan="2">
					${PkgModelSktStep3.col13 }
				</td>
				<td>
					${PkgModelSktStep3.col14 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file3 != null}">
						<ui:file attachFileModel="${PkgModel.file3}" name="file3" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<th rowspan="2">보완내역별 검증 결과 <span class='necessariness'>*</span></th>
				<td rowspan="2">
					${PkgModelSktStep3.col15 }
				</td>
				<td>
					${PkgModelSktStep3.col16 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file38 != null}">
						<ui:file attachFileModel="${PkgModel.file38}" name="file38" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<th rowspan="2" class="th_height">서비스 영향도 (로밍 포함)</th>
				<td rowspan="2">
					${PkgModelSktStep3.col17 }
				</td>
				<td>
					${PkgModelSktStep3.col18 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file12 != null}">
						<ui:file attachFileModel="${PkgModel.file12}" name="file12" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<th rowspan="2">과금 영향도 <span class='necessariness'>*</span></th>
				<td rowspan="2">
					${PkgModelSktStep3.col19 }
				</td>
				<td>
					${PkgModelSktStep3.col20 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file39 != null}">
						<ui:file attachFileModel="${PkgModel.file39}" name="file39" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<th rowspan="2" class="th_height">작업절차서, S/W 블록 내역<br/>(list 및 size) <span class='necessariness'>*</span></th>
				<td rowspan="2">
					${PkgModelSktStep3.col21 }
				</td>
				<td>
					${PkgModelSktStep3.col22 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file1 != null}">
						<ui:file attachFileModel="${PkgModel.file1}" name="file1" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
			
			<tr>
				<th rowspan="2">PKG 적용 후 check list <span class='necessariness'>*</span></th>
				<td rowspan="2">
					${PkgModelSktStep3.col23 }
				</td>
				<td>
					${PkgModelSktStep3.col24 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file14 != null}">
						<ui:file attachFileModel="${PkgModel.file14}" name="file14" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
			<tr>
				<th rowspan="2" class="th_height">CoD/PoD 변경 사항,<br/> 운용팀 공지사항 <span class='necessariness'>*</span></th>
				<td rowspan="2">
					${PkgModelSktStep3.col25 }
				</td>
				<td>
					${PkgModelSktStep3.col26 }
				</td>
			</tr>
			<tr>
				<td style="text-align:left;">
					<c:if test="${PkgModel.file15 != null}">
						<ui:file attachFileModel="${PkgModel.file15}" name="file15" size="50" mode="mobile" />
					</c:if>
				</td>
			</tr>
		</table>
		
		<br/>
		
	</div>


	</form:form>

</body>

</html>

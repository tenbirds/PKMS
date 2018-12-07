<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<body>

		<h4 class="balloon_header">완료 결과</h4>

		<a class="ly_close" href="javascript:fn_flow_init();">
		<img alt=닫기 src="/images/btn_close_layer.png">						</a>

		<div class="ly_sub">
					
			<div id="flow_tb_1"  class="Npaper_type"  style="height:620px;">

				<!-- 검증결과 -->
				<table class="tbl_type_ly tbl_td_w sc_td_g" summary="">
					<caption>검증결과</caption>
					<colgroup>
						<col width="170px">
						<col width="70px">
						<col width="*">
					</colgroup>
					<tr>
						<th scope="col">검증일자</th>
						<td scope="col" colspan="2" style="text-align:left">
							${PkgStatusModel_0.col1} ~ ${PkgStatusModel_0.col2}
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">주요 개선 기능 변경 요청서</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_0.col3}
						</td>
						<td scope="col">
							 ${PkgStatusModel_0.col4}
						</td>
					</tr>
					<tr>
						<td>	
							<div class="pop_system autoview">
								<c:if test="${not empty PkgModel.file9}">
									 <ui:file attachFileModel="${PkgModel.file9}" name="file9" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">신규/보완/개선 항목<br/>검증결과 (예외처리 포함)</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_0.col5}
						</td>
						<td scope="col">
							 ${PkgStatusModel_0.col6}
						</td>
					</tr>
					<tr>
						<td>						
							<div class="pop_system autoview">
								<c:if test="${not empty PkgModel.file10}">
									 <ui:file attachFileModel="${PkgModel.file10}" name="file10" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">과금 검증 결과</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_0.col7}
						</td>
						<td scope="col">
							 ${PkgStatusModel_0.col8}
						</td>
					</tr>
					<tr>
						<td>	
							<div class="pop_system autoview">
								<c:if test="${not empty PkgModel.file11}">
									 <ui:file attachFileModel="${PkgModel.file11}" name="file11" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">기존 서비스 영향도 검토</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_0.col9}
						</td>
						<td scope="col">
							 ${PkgStatusModel_0.col10}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file12}">
									 <ui:file attachFileModel="${PkgModel.file12}" name="file12" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">PKG 적용 절차서</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_0.col11}
						</td>
						<td scope="col">
							 ${PkgStatusModel_0.col12}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file13}">
									 <ui:file attachFileModel="${PkgModel.file13}" name="file13" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">PKG 적용 전후 CheckList</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_0.col13}
						</td>
						<td scope="col">
							 ${PkgStatusModel_0.col14}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file14}">
									 <ui:file attachFileModel="${PkgModel.file14}" name="file14" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">CoD/PoD 변경사항</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_0.col15}
						</td>
						<td scope="col">
							 ${PkgStatusModel_0.col16}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file15}">
									 <ui:file attachFileModel="${PkgModel.file15}" name="file15" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">운용팀 공지사항<br/>: 추가된 FLT, 변경된 기능 등</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_0.col17}
						</td>
						<td scope="col">
							 ${PkgStatusModel_0.col18}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file16}">
									 <ui:file attachFileModel="${PkgModel.file16}" name="file16" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
				</table>
		

		<!-- 초도결과 -->
				<table class="tbl_type_ly tbl_td_w sc_td_g text-center mt_20" summary="" >
					<caption>초도결과</caption>
					<colgroup>
						<col width="170px">
						<col width="70px">
						<col width="*">
					</colgroup>
					<tr>
						<th>모니터링 기간</th>
						<td colspan="2" style="text-align:left;">
							${PkgStatusModel_1.col1} 일
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">현장 호시험 결과</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_1.col2}
						</td>
						<td scope="col">
							${PkgStatusModel_1.col3}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file17}">
									 <ui:file attachFileModel="${PkgModel.file17}" name="file17" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">시스템 감시 사항<br/>: 부하/메모리/ALM/FLT/STS 등</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_1.col4}
						</td>
						<td scope="col">
							${PkgStatusModel_1.col5}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file18}">
									 <ui:file attachFileModel="${PkgModel.file18}" name="file18" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">품질 감시 사항<br/>: 서비스 품질 등</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_1.col6}
						</td>
						<td scope="col">
							${PkgStatusModel_1.col7}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file19}">
									 <ui:file attachFileModel="${PkgModel.file19}" name="file19" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">상용 과금 검증 결과</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_1.col8}
						</td>
						<td scope="col">
							${PkgStatusModel_1.col9}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file20}">
									 <ui:file attachFileModel="${PkgModel.file20}" name="file20" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" rowspan="2">기타 문제사항 및 F/U 내역</th>
						<td scope="col" rowspan="2">
							${PkgStatusModel_1.col10}
						</td>
						<td scope="col">
							${PkgStatusModel_1.col11}
						</td>
					</tr>
					<tr>
						<td scope="col">
							<div class="pop_system">
								<c:if test="${not empty PkgModel.file21}">
									 <ui:file attachFileModel="${PkgModel.file21}" name="file21" size="50" mode="update" />
								</c:if>
							</div>
						</td>
					</tr>
				</table>

				<!-- 확대결과 -->
				<%-- <table class="tbl_type_ly mag_top1" style="width:670px;">
				<caption>확대결과</caption>
				<tr>
					<td>
						<table class="tbl_type_ly" border="1" summary="" style="width:670px;">
							<colgroup>
								<col width="140px">
								<col width="*">
							</colgroup>
							<tr>
								<td>모니터링 기간</td>
								<td style="text-align:left; padding-left:10px;">
									${PkgStatusModel_2.col1} 일
								</td>
							</tr>
						</table>
		
						<table class="tbl_type_ly" border="1" summary="" style="width:670px;">
							<colgroup>
								<col width="170px">
								<col width="70px">
								<col width="*">
							</colgroup>
							<tr>
								<td scope="col" rowspan="2">현장 호시험 결과</td>
								<td scope="col" rowspan="2">
									${PkgStatusModel_2.col2}
								</td>
								<td scope="col">
									${PkgStatusModel_2.col3}
								</td>
							</tr>
							<tr>
								<td scope="col">
									<div class="pop_system">
										<c:if test="${not empty PkgModel.file22}">
											 <ui:file attachFileModel="${PkgModel.file22}" name="file22" size="50" mode="update" />
										</c:if>
									</div>
								</td>
							</tr>
						</table>
						
						<table class="tbl_type_ly" border="1" summary="" style="width:670px;">
							<colgroup>
								<col width="170px">
								<col width="70px">
								<col width="*">
							</colgroup>
							<tr>
								<td scope="col" rowspan="2">시스템 감시 사항<br/>: 부하/메모리/ALM/FLT/STS 등</td>
								<td scope="col" rowspan="2">
									${PkgStatusModel_2.col4}
								</td>
								<td scope="col">
									${PkgStatusModel_2.col5}
								</td>
							</tr>
							<tr>
								<td scope="col">
									<div class="pop_system">
										<c:if test="${not empty PkgModel.file23}">
											 <ui:file attachFileModel="${PkgModel.file23}" name="file23" size="50" mode="update" />
										</c:if>
									</div>
								</td>
							</tr>
						</table>
						
						<table class="tbl_type_ly" border="1" summary="" style="width:670px;">
							<colgroup>
								<col width="170px">
								<col width="70px">
								<col width="*">
							</colgroup>
							<tr>
								<td scope="col" rowspan="2">품질 감시 사항<br/>: 서비스 품질 등</td>
								<td scope="col" rowspan="2">
									${PkgStatusModel_2.col6}
								</td>
								<td scope="col">
									${PkgStatusModel_2.col7}
								</td>
							</tr>
							<tr>
								<td scope="col">
									<div class="pop_system">
										<c:if test="${not empty PkgModel.file24}">
											 <ui:file attachFileModel="${PkgModel.file24}" name="file24" size="50" mode="update" />
										</c:if>
									</div>
								</td>
							</tr>
						</table>
						
						<table class="tbl_type_ly" border="1" summary="" style="width:670px;">
							<colgroup>
								<col width="170px">
								<col width="70px">
								<col width="*">
							</colgroup>
							<tr>
								<td scope="col" rowspan="2">상용 과금 검증 결과</td>
								<td scope="col" rowspan="2">
									${PkgStatusModel_2.col8}
								</td>
								<td scope="col">
									${PkgStatusModel_2.col9}
								</td>
							</tr>
							<tr>
								<td scope="col">
									<div class="pop_system">
										<c:if test="${not empty PkgModel.file25}">
											 <ui:file attachFileModel="${PkgModel.file25}" name="file25" size="50" mode="update" />
										</c:if>
									</div>
								</td>
							</tr>
						</table>
						
						<table class="tbl_type_ly" border="1" summary="" style="width:670px;">
							<colgroup>
								<col width="170px">
								<col width="70px">
								<col width="*">
							</colgroup>
							<tr>
								<td scope="col" rowspan="2">기타 문제사항 및 F/U 내역</td>
								<td scope="col" rowspan="2">
									${PkgStatusModel_2.col10}
								</td>
								<td scope="col">
									${PkgStatusModel_2.col11}
								</td>
							</tr>
							<tr>
								<td scope="col">
									<div class="pop_system">
										<c:if test="${not empty PkgModel.file26}">
											 <ui:file attachFileModel="${PkgModel.file26}" name="file26" size="50" mode="update" />
										</c:if>
									</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</table> --%>
				<table class="tbl_type_ly tbl_td_wc mt_20">
					<caption>확대적용결과</caption>
					<colgroup>
						<col width="120px" />
						<col width="120px" />
						<col width="100px" />
						<col width="" />
						<col width="95px" />
						<col width="95px" />
					</colgroup>
					<thead>
						<tr>
							<th>국사</th>
							<th>장비</th>
							<th>서비스지역</th>
							<th>적용일시</th>
							<th>적용결과</th>
							<th>과금결과</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
						<c:when test="${not empty PkgModel.pkgEquipmentModelList}">
							<c:forEach var="result" items="${PkgModel.pkgEquipmentModelList}" varStatus="status">
							<input type="hidden" name="check_seqs" value="${result.equipment_seq}" />
								<tr>
									<td align="center">
										${result.team_name}&nbsp;
									</td>
									<td align="center">
										${result.equipment_name}&nbsp;
									</td>
									<td align="center">
										${result.service_area}&nbsp;
									</td>
									<td align="center">
										<c:choose>
											<c:when test="${empty result.work_date}">
												확대일정 수립 필요
											</c:when>
											<c:otherwise>
												${result.work_date}&nbsp;&nbsp;
												${result.start_time1} : ${result.start_time2} ~ ${result.end_time1} : ${result.end_time2}	
											</c:otherwise>
										</c:choose>										
									</td>
									<td>
									<c:if test = "${result.work_result == null || result.work_result == ''}">
										미적용
									</c:if>
									<c:if test = "${result.work_result != null && result.work_result != ''}">
										${result.work_result}
									</c:if>
									</td>
									<td>
									<c:if test = "${result.charge_result == null || result.charge_result == ''}">
										미적용
									</c:if>
									<c:if test = "${result.charge_result != null && result.charge_result != ''}">
										${result.work_result}
									</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6">확대적용 장비가 없습니다.</td>
							</tr>
						</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
			</div>

		</div>
 
		<div class="edge_cen" style="top:625px;"></div>
 
</body>

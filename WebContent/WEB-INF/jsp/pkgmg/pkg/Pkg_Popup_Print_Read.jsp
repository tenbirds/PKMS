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
</head>

<body>
<form:form commandName="PkgModel" method="post" enctype="multipart/form-data">
	<div align="center">
		
		<table style="border:currentColor; width: 700pt; border-collapse: collapse;">
			<tbody>
				<tr>
					<td colspan="6">
						<p align="right">
							<a href = "javascript:window.print()"><img src="/images/btn_print.gif"></a>
						</p>
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<p align="center" style="text-decoration: underline;font-size: 21pt; font-weight:bold;margin-top:10px;">
									PKG 적용 계획
						</p>
						<br/>
					</td>
				</tr>
				<tr>
					<td style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
									PKG 명
						</p>
					</td>
					<td colspan="2" style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 226.05px; background-color: transparent;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
									${PkgModel.title }
						</p>
					</td>
					<td style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 129.78px; height: 26.25pt;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
									담당자
						</p>
					</td>
					<td colspan="2" style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 208.21px; background-color: transparent;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
									${PkgModel.system_user_name }
						</p>
					</td>
				</tr>
				<tr>
					<td style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt; height: 26.25pt;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
									대상 시스템
						</p>
					</td>
					<td colspan="2" style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 370px; background-color: transparent;">
							<b>
								<span style=" font-size: 13px;">
									<c:forEach var="result" items="${PkgEqList}" varStatus="status">
										<c:if test = "${result.work_gubun eq 'S'}">
										<p align="left">초도:${result.idc_name} ${result.eq_name} 등 ${result.eq_cnt}식</p>
										</c:if>
										<c:if test = "${result.work_gubun eq 'E'}">
										<p align="left">확대:${result.idc_name} ${result.eq_name} 등 ${result.eq_cnt}식</p>
										</c:if>
									</c:forEach>
								</span>
							</b>
					</td>
					<td style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 129.78px;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
									초도작업
						</p>
					</td>
					<td colspan="2" style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 300px; background-color: transparent;">
						<b>
							<span style=" font-size: 13px;">
								<c:forEach var="result" items="${PkgTimeList}" varStatus="status">
									<c:if test = "${result.work_gubun eq 'S'}">
									<p align="left">초도:${result.min_date} ~ ${result.max_date}</p>
									</c:if>
									<c:if test = "${result.work_gubun eq 'E'}">
									<p align="left">확대:${result.min_date} ~ ${result.max_date}</p>
									</c:if>
								</c:forEach>
							</span>
						</b>	
					</td>
				</tr>
				<tr>
					<td colspan="6" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 699.21px;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
									PKG 주요 내역
						</p>
					</td>
				</tr>
				<tr>
					<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt; background-color: transparent; color:#e60b00;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
									주요 보완내역
						</p>
					</td>
					<td colspan="5" style="border: 1px solid #aaa; padding: 8px 10px; width: 204.21px; background-color: transparent; word-break:break-all;">
							<span style=" font-size: 13px;">
								<table style="width: 100%; margin-left: 0px; margin-right: 0px;" border="0">
									<tr>	
										<th style="background: rgb(217, 217, 217); padding: 8px 10px; border: 1px solid #aaa;"></th>
										<th style="background: rgb(217, 217, 217); padding: 8px 10px; border: 1px solid #aaa;">항목수</th>
										<th style="background: rgb(217, 217, 217); padding: 8px 10px; border: 1px solid #aaa;">검증내역개수</th>
										<th style="background: rgb(217, 217, 217); padding: 8px 10px; border: 1px solid #aaa;">개선내역개수</th>
										<th style="background: rgb(217, 217, 217); padding: 8px 10px; border: 1px solid #aaa;">검증진도율</th>
									</tr>
									<tr>
										<th style="padding: 8px 10px; border: 1px solid #aaa;">신규</th>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">${PkgModelProgress.new_col1 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">${PkgModelProgress.new_col2 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">${PkgModelProgress.new_col3 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">
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
										<th style="padding: 8px 10px; border: 1px solid #aaa;">보완</th>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">${PkgModelProgress.pn_col1 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">${PkgModelProgress.pn_col2 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">${PkgModelProgress.pn_col3 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">
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
										<th style="padding: 8px 10px; border: 1px solid #aaa;">개선</th>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">${PkgModelProgress.cr_col1 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">${PkgModelProgress.cr_col2 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">${PkgModelProgress.cr_col3 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa;">
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
										<th style="border: 1px solid #aaa; padding: 8px 10px; background:#f9f6eb;">합계</th>
										<td style="padding: 8px 10px; border: 1px solid #aaa; background:#f9f6eb;">${PkgModelProgress.new_col1 + PkgModelProgress.pn_col1 + PkgModelProgress.cr_col1 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa; background:#f9f6eb;">${PkgModelProgress.new_col2 + PkgModelProgress.pn_col2 + PkgModelProgress.cr_col2 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa; background:#f9f6eb;">${PkgModelProgress.new_col3 + PkgModelProgress.pn_col3 + PkgModelProgress.cr_col3 }</td>
										<td style="padding: 8px 10px; border: 1px solid #aaa; background:#f9f6eb;">
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
								</table>
								<p align="left" style="padding: 15px 0px;">
									${PkgModel.content }
								</p>
							</span>
					</td>
				</tr>
				<tr>
					<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt; background-color: transparent; color:#e60b00;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
								RM/CEM<br/>관련 feature
						</p>
					</td>
					<td colspan="5" style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 562.21px; background-color: transparent; word-break:break-all; ">
						<p style=" font-size: 13px; text-align: left;">
								${PkgModel.rm_issue_comment }
						</p>
					</td>
				</tr>
				<tr>
					<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt; background-color: transparent; color:#e60b00;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
								PKG 적용시<br/>RM 검토
						</p>
					</td>
					<td colspan="5" style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 562.21px; background-color: transparent; word-break:break-all; ">
						<span style=" font-size: 13px;">
							<c:choose>
								<c:when test="${PkgModel.ser_yn ne 'N'}">
									<p style="text-align: left;">[영향도 있음] ${PkgModel.ser_content }</p>
								</c:when>
								<c:otherwise>
									<p style="text-align: left;">[영향도 없음]</p>
								</c:otherwise>
							</c:choose>
						</span>
					</td>
				</tr>
				<tr>
					<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt; background-color: transparent; color:#e60b00;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
								과금 & 로밍<br/>특이 사항
						</p>
					</td>
					<td colspan="5" style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 562.21px; background-color: transparent; word-break:break-all; ">
						
						<span style=" font-size: 13px;">
							<c:choose>
								<c:when test="${PkgModel.pe_yn ne 'N'}">
									<p style="text-align: left;">[과금영향도 있음] ${PkgModel.pe_yn_comment}</p>
								</c:when>
								<c:otherwise>
									<p style="text-align: left;">[과금영향도 없음]</p>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${PkgModel.roaming_link ne 'N'}">
									<p style="text-align: left;">[로밍연동-${PkgModel.roaming_link}] ${PkgModel.roaming_link_comment}</p>
								</c:when>
								<c:otherwise>
									<p style="text-align: left;">[로밍영향도 없음]</p>
								</c:otherwise>
							</c:choose>
						</span>
						
					</td>
				</tr>
				<tr>
					<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt; height: background-color: transparent; color:#e60b00;">
						<p style=" font-size: 13px; font-weight:bold; text-align:center;">
									작업절차서<br/>(원복절차 포함)
						</p>
					</td>
					<td colspan="5"	style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 562.21px; background-color: transparent;">
						<p align="left"	style="text-align: left;">
							<span style=" font-size: 13px;">
								 ${PkgModel.col22} <br/>
							</span>
						</p>
							<div class="pop_system">
								<ui:file attachFileModel="${PkgFileModel.file1}" name="file1" size="50" mode="view" />
							</div>
					</td>
				</tr>
				<!--[if !supportMisalignedColumns]-->
				<tr height="0">
					<td width="130"
						style="border: 0px rgb(0, 0, 0); background-color: transparent;"></td>
					<td width="176"
						style="border: 0px rgb(0, 0, 0); width: 183.05px; background-color: transparent;"></td>
					<td width="52"
						style="border: 0px rgb(0, 0, 0); background-color: transparent;"></td>
					<td width="129"
						style="border: 0px rgb(0, 0, 0); width: 130.78px; background-color: transparent;"></td>
					<td width="25"
						style="border: 0px rgb(0, 0, 0); width: 26.21px; background-color: transparent;"></td>
					<td width="184"
						style="border: 0px rgb(0, 0, 0); background-color: transparent;"></td>
				</tr>
				<!--[endif]-->
			</tbody>
		</table>
		<div>
			<c:if test="${PkgModel.selected_status == '0' && ((PkgModel.status > 1 && PkgModel.status < 10) || PkgModel.status == 26)}">
				<table style="border:currentColor; width: 700pt; border-collapse: collapse;" >
					<thead>
						<tr>
							<th colspan="2">
								<p align="center" style="text-decoration: underline;font-size: 21pt; font-weight:bold;">
											PKG 상용검증 계획
								</p>
								<br/>
							</th>
						</tr>
					<thead>
					<tbody>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">기능검증</th>
					</tr>
					<tr>
						<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
						<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
							${PkgModelData.pkgStatusModel.col17}
							~
							${PkgModelData.pkgStatusModel.col18}
						</td>
					</tr>
					<tr>
						<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
						<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
							<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
								<c:if test="${result.charge_gubun == 'VU'}">
									${result.sosok}-${result.user_name}M&nbsp;
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">기능 검증계획</th>
						<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
							<textarea style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" readonly="readonly">${PkgModelData.pkgStatusModel.col21}</textarea>								
							<ui:file attachFileModel="${PkgModel.file94}" name="file94" size="50" mode="view" />
						</td>
					</tr>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능검증</th>
					</tr>
					<c:choose>
						<c:when test="${PkgModel.non_yn != 'N'}">
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${PkgModelData.pkgStatusModel.col15}
									~
									${PkgModelData.pkgStatusModel.col16}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'NO'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능 검증계획</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModelData.pkgStatusModel.col4}</textarea>
									<ui:file attachFileModel="${PkgModel.file93}" name="file93" size="50" mode="view" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능 미검증 사유</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col8}</textarea>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량검증</th>
					</tr>
					<c:choose>
						<c:when test="${PkgModel.vol_yn != 'N'}">
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${PkgModelData.pkgStatusModel.col9}
									~
									${PkgModelData.pkgStatusModel.col10}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'VO'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량 검증계획</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModelData.pkgStatusModel.col1}</textarea>
									<ui:file attachFileModel="${PkgModel.file90}" name="file90" size="50" mode="view" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량 미검증 사유</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col5}</textarea>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금검증</th>
					</tr>
					<c:choose>
						<c:when test="${PkgModel.cha_yn != 'N'}">
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${PkgModelData.pkgStatusModel.col13}
									~
									${PkgModelData.pkgStatusModel.col14}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'CH'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금 검증계획</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly">${PkgModelData.pkgStatusModel.col3}</textarea>
									<ui:file attachFileModel="${PkgModel.file92}" name="file92" size="50" mode="view" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금 미검증 사유</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col7}</textarea>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">보안검증</th>
					</tr>
					<c:choose>
						<c:when test="${PkgModel.sec_yn != 'N'}">
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${PkgModelData.pkgStatusModel.col11}
									~
									${PkgModelData.pkgStatusModel.col12}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'SE'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">보안 검증계획</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModelData.pkgStatusModel.col2}</textarea>
									<ui:file attachFileModel="${PkgModel.file91}" name="file91" size="50" mode="view" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">보안 미검증 사유</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col6}</textarea>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					</tbody>
				</table>
				<br/>
				<c:if test="${PkgModel.status > 3 && PkgModel.status < 10}">
					<table style="border:currentColor; width: 700pt; border-collapse: collapse;" >
						<thead>
							<tr>
								<th colspan="2">
									<p align="center" style="text-decoration: underline;font-size: 21pt; font-weight:bold;margin-top:10px;">
										PKG 검증 결과
									</p>
									<br/>
								</th>
							</tr>
						<thead>
						<tbody>
							<tr>
								<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">기능검증</th>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">기능 검증결과</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col41}</textarea>
								</td>
							</tr>
							<c:if test="${PkgModel.non_yn != 'N'}">
								<tr>
									<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능검증</th>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능 검증결과</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										${non.verify_content}
									</td>
								</tr>
							</c:if>
							<c:if test="${PkgModel.vol_yn != 'N'}">
								<tr>
									<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량검증</th>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량 검증결과</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										${vol.verify_content}
									</td>
								</tr>
							</c:if>
							<c:if test="${PkgModel.cha_yn != 'N'}">
								<tr>
									<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금검증</th>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금 검증결과</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										${cha.verify_content}
									</td>
								</tr>
							</c:if>
						</tbody>
					</table>
					<br/>
					<c:if test="${PkgModel.status > 7 && PkgModel.status < 10}">
						<table style="border:currentColor; width: 700pt; border-collapse: collapse;" >
							<thead>
								<tr>
									<th colspan="2">
										<p align="center" style="text-decoration: underline;font-size: 21pt; font-weight:bold;">
											PKG 적용결과
										</p>
										<br/>
									</th>
								</tr>
							<thead>
							<tbody>
								<tr>
									<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">당일결과</th>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										현장 호시험 결과
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										 결과: ${PkgMdlData.col2}, comment: ${PkgMdlData.col3}
									</td>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										시스템 감시 사항<br/>: 부하/메모리<br/>/ALM/FLT/STS 등
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										결과: ${PkgMdlData.col4}, comment: ${PkgMdlData.col5}
									</td>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										품질 감시 사항<br/>: 서비스 품질 등
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										결과: ${PkgMdlData.col6}, comment: ${PkgMdlData.col7}
									</td>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										상용 과금 검증 결과								
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										결과: ${PkgMdlData.col8}, comment: ${PkgMdlData.col9}
									</td>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										기타 문제사항 및<br/>F/U 내역
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										결과: ${PkgMdlData.col10}, comment: ${PkgMdlData.col11}
									</td>
								</tr>
								<tr>
									<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">최종결과</th>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										모니터링 기간
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										<c:if test="${PkgMdlData.col1 != ''}">
											${PkgMdlData.col1} 일
										</c:if>
									</td>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										시스템 감시 사항<br/>: 부하/메모리<br/>/ALM/FLT/STS 등
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										결과: ${PkgMdlData.col12}, comment: ${PkgMdlData.col13}
									</td>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										품질 감시 사항<br/>: 서비스 품질 등
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										결과: ${PkgMdlData.col14}, comment: ${PkgMdlData.col15}
									</td>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										상용 과금 검증 결과								
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										결과: ${PkgMdlData.col16}, comment: ${PkgMdlData.col17}
									</td>
								</tr>
								<tr>
									<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
										기타 문제사항 및<br/>F/U 내역
									</th>
									<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
										결과: ${PkgMdlData.col18}, comment: ${PkgMdlData.col19}
									</td>
								</tr>
							</tbody>
						</table>
					</c:if>
				</c:if>
			</c:if>
			<c:if test="${PkgModel.selected_status != '0' && PkgModel.selected_status != '7'}">
				<br/>
				<table style="border:currentColor; width: 700pt; border-collapse: collapse;" >
					<thead>
						<tr>
							<th colspan="2">
								<p align="center" style="text-decoration: underline;font-size: 21pt; font-weight:bold;">
											PKG 상용검증 계획
								</p>
								<br/>
							</th>
						</tr>
					<thead>
					<tbody>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">기능검증</th>
					</tr>
					<tr>
						<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
						<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
							${PkgModelData.pkgStatusModel.col17}
							~
							${PkgModelData.pkgStatusModel.col18}
						</td>
					</tr>
					<tr>
					
						<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
						<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
							<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
								<c:if test="${result.charge_gubun == 'VU'}">
									${result.sosok}-${result.user_name}M&nbsp;
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">기능 검증계획</th>
						<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
							<textarea style="width: 625px; height: 50px; margin-top: 5px;" maxlength="1200" readonly="readonly">${PkgModelData.pkgStatusModel.col21}</textarea>								
							<ui:file attachFileModel="${PkgModel.file94}" name="file94" size="50" mode="view" />
						</td>
					</tr>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능검증</th>
					</tr>
					<c:choose>
						<c:when test="${PkgModel.non_yn != 'N'}">
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${PkgModelData.pkgStatusModel.col15}
									~
									${PkgModelData.pkgStatusModel.col16}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'NO'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능 검증계획</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModelData.pkgStatusModel.col4}</textarea>
									<ui:file attachFileModel="${PkgModel.file93}" name="file93" size="50" mode="view" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능 미검증 사유</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col8}</textarea>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량검증</th>
					</tr>
					<c:choose>
						<c:when test="${PkgModel.vol_yn != 'N'}">
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${PkgModelData.pkgStatusModel.col9}
									~
									${PkgModelData.pkgStatusModel.col10}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'VO'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량 검증계획</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModelData.pkgStatusModel.col1}</textarea>
									<ui:file attachFileModel="${PkgModel.file90}" name="file90" size="50" mode="view" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량 미검증 사유</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col5}</textarea>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금검증</th>
					</tr>
					<c:choose>
						<c:when test="${PkgModel.cha_yn != 'N'}">
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${PkgModelData.pkgStatusModel.col13}
									~
									${PkgModelData.pkgStatusModel.col14}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'CH'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금 검증계획</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly">${PkgModelData.pkgStatusModel.col3}</textarea>
									<ui:file attachFileModel="${PkgModel.file92}" name="file92" size="50" mode="view" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금 미검증 사유</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col7}</textarea>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					<tr>
						<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">보안검증</th>
					</tr>
					<c:choose>
						<c:when test="${PkgModel.sec_yn != 'N'}">
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">일정</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${PkgModelData.pkgStatusModel.col11}
									~
									${PkgModelData.pkgStatusModel.col12}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">담당자</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<c:forEach var="result" items="${PkgModelData.systemUserModelList_25}" varStatus="status">
										<c:if test="${result.charge_gubun == 'SE'}">
											${result.sosok}-${result.user_name}M&nbsp;
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">보안 검증계획</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModelData.pkgStatusModel.col2}</textarea>
									<ui:file attachFileModel="${PkgModel.file91}" name="file91" size="50" mode="view" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">보안 미검증 사유</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col6}</textarea>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					</tbody>
				</table>
			</c:if>
			<c:if test="${PkgModel.selected_status == '4' || PkgModel.selected_status == '5' || PkgModel.selected_status == '6' || PkgModel.selected_status == '7'}">
				<br/>
				<table style="border:currentColor; width: 700pt; border-collapse: collapse;" >
					<thead>
						<tr>
							<th colspan="2">
								<p align="center" style="text-decoration: underline;font-size: 21pt; font-weight:bold;">
									PKG 검증 결과
								</p>
								<br/>
							</th>
						</tr>
					<thead>
					<tbody>
						<tr>
							<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">기능검증</th>
						</tr>
						<tr>
							<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">기능 검증결과</th>
							<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
								<textarea style="width: 625px; height: 50px;" maxlength="1200" readonly="readonly" >${PkgModel.pkgStatusModel.col41}</textarea>
							</td>
						</tr>
						<c:if test="${PkgModel.non_yn != 'N'}">
							<tr>
								<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능검증</th>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">비기능 검증결과</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${non.verify_content}
								</td>
							</tr>
						</c:if>
						<c:if test="${PkgModel.vol_yn != 'N'}">
							<tr>
								<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량검증</th>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">용량 검증결과</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${vol.verify_content}
								</td>
							</tr>
						</c:if>
						<c:if test="${PkgModel.cha_yn != 'N'}">
							<tr>
								<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금검증</th>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">과금 검증결과</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									${cha.verify_content}
								</td>
							</tr>
						</c:if>
					</tbody>
				</table>
				<c:if test="${PkgModel.selected_status == '7'}">
					<br/>
					<table style="border:currentColor; width: 700pt; border-collapse: collapse;" >
						<thead>
							<tr>
								<th colspan="2">
									<p align="center" style="text-decoration: underline;font-size: 21pt; font-weight:bold;">
										PKG 적용결과
									</p>
									<br/>
								</th>
							</tr>
						<thead>
						<tbody>
							<tr>
								<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">당일결과</th>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									현장 호시험 결과
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									 결과: ${PkgModelData.pkgStatusModel.col2}, comment: ${PkgModelData.pkgStatusModel.col3}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									시스템 감시 사항<br/>: 부하/메모리<br/>/ALM/FLT/STS 등
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									결과: ${PkgModelData.pkgStatusModel.col4}, comment: ${PkgModelData.pkgStatusModel.col5}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									품질 감시 사항<br/>: 서비스 품질 등
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									결과: ${PkgModelData.pkgStatusModel.col6}, comment: ${PkgModelData.pkgStatusModel.col7}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									상용 과금 검증 결과								
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									결과: ${PkgModelData.pkgStatusModel.col8}, comment: ${PkgModelData.pkgStatusModel.col9}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									기타 문제사항 및<br/>F/U 내역
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									결과: ${PkgModelData.pkgStatusModel.col10}, comment: ${PkgModelData.pkgStatusModel.col11}
								</td>
							</tr>
							<tr>
								<th colspan="2" style="background: rgb(130, 130, 130); color:#fff; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">최종결과</th>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									모니터링 기간
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									<c:if test="${PkgModelData.pkgStatusModel.col1 != ''}">
										${PkgModelData.pkgStatusModel.col1} 일
									</c:if>
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									시스템 감시 사항<br/>: 부하/메모리<br/>/ALM/FLT/STS 등
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									결과: ${PkgModelData.pkgStatusModel.col12}, comment: ${PkgModelData.pkgStatusModel.col13}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									품질 감시 사항<br/>: 서비스 품질 등
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									결과: ${PkgModelData.pkgStatusModel.col14}, comment: ${PkgModelData.pkgStatusModel.col15}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									상용 과금 검증 결과								
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									결과: ${PkgModelData.pkgStatusModel.col16}, comment: ${PkgModelData.pkgStatusModel.col17}
								</td>
							</tr>
							<tr>
								<th style="background-color: #f6f6f6; color:#e60b00; border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; width: 97.6pt;font-size: 13px; font-weight:bold; text-align:center;">
									기타 문제사항 및<br/>F/U 내역
								</th>
								<td style="border: 1px solid #aaa; padding: 8px 10px; min-height: 1em; background-color: transparent;font-size: 13px; font-weight:bold; text-align: left;">
									결과: ${PkgModelData.pkgStatusModel.col18}, comment: ${PkgModelData.pkgStatusModel.col19}
								</td>
							</tr>
						</tbody>
					</table>
				</c:if>
			</c:if>
		</div>
		<br/><br/>
	</div>
</form:form>
</body>
</html>


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
		
		<table style="border: currentColor; width: 700pt; border-collapse: collapse;">
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
						<p align="center" style="text-decoration: underline;">
							<b>
								<span style="font-family: 맑은 고딕; font-size: 24pt;">
									PKG 개발 검증
								</span>
							</b>
						</p>
						<br/>
					</td>
				</tr>
				<tr>
					<td style="background: rgb(217, 217, 217); border-width: 1.5pt 1pt 1pt 1.5pt; border-style: solid solid solid solid; border-color: windowtext; padding: 0cm 5.4pt; width: 97.6pt; height: 26.25pt;">
						<p align="center">
							<b>
								<span style="font-family: 맑은 고딕; font-size: 12pt;">
									PKG 명
								</span>
							</b>
						</p>
					</td>
					<td colspan="2" style="border-width: 1.5pt 1.5pt 1pt 0px; border-style: solid solid solid solid; border-color: windowtext windowtext windowtext rgb(0, 0, 0); padding: 0cm 5.4pt; width: 226.05px; height: 26.25pt; background-color: transparent;">
						<p align="center">
							<b>
								<span style="font-family: 맑은 고딕; font-size: 12pt;">
									${PkgModel.title }
								</span>
							</b>
						</p>
					</td>
					<td style="background: rgb(217, 217, 217); border-width: 1.5pt 1.5pt 1pt 0px; border-style: solid solid solid none; border-color: windowtext; padding: 0cm 5.4pt; width: 129.78px; height: 26.25pt;">
						<p align="center" style="text-align: center;">
							<b>
								<span style="font-family: 맑은 고딕; font-size: 12pt;">
									대표 개발담당자
								</span>
							</b>
						</p>
					</td>
					<td colspan="2" style="border-width: 1.5pt 1.5pt 1pt 0px; border-style: solid solid solid none; border-color: windowtext windowtext windowtext rgb(0, 0, 0); padding: 0cm 5.4pt; width: 208.21px; height: 26.25pt; background-color: transparent;">
						<p align="center" style="text-align: center;">
							<b>
								<span style="font-family: 맑은 고딕; font-size: 12pt;">
									${PkgModel.dev_system_user_name}
								</span>
							</b>
						</p>
					</td>
				</tr>
				<tr>
					<td style="background: rgb(217, 217, 217); border-width: 0px 1pt 1pt 1.5pt; border-style: solid solid solid solid; border-color: rgb(0, 0, 0) windowtext windowtext; padding: 0cm 5.4pt; width: 97.6pt; height: 26.25pt;">
						<p align="center" style="text-align: center;">
							<b>
								<span style="font-family : 맑은 고딕; font-size: 12pt;">
									대상 시스템
								</span>
							</b>
						</p>
					</td>
					<td colspan="2" style="border-width: 0px 1.5pt 1pt 0px; border-style: none solid solid none; border-color: rgb(0, 0, 0) windowtext windowtext rgb(0, 0, 0); padding: 0cm 5.4pt; width: 370px; height: 26.25pt; background-color: transparent;">
							<b>
								<span style="font-family: 맑은 고딕; font-size: 12pt;">
									${PkgModel.system_name_org}
								</span>
							</b>
					</td>
					<td style="background: rgb(217, 217, 217); border-width: 0px 1.5pt 1pt 0px; border-style: none solid solid none; border-color: rgb(0, 0, 0) windowtext windowtext rgb(0, 0, 0); padding: 0cm 5.4pt; width: 129.78px; height: 26.25pt;">
						<p align="center" style="text-align: center;">
							<b>
								<span style="font-family: 맑은 고딕; font-size: 12pt;">
									패키지개발 기간
								</span>
							</b>
						</p>
					</td>
					<td colspan="2" style="border-width: 0px 1.5pt 1pt 0px; border-style: none solid solid none; border-color: rgb(0, 0, 0) windowtext windowtext rgb(0, 0, 0); padding: 0cm 5.4pt; width: 300px; height: 26.25pt; background-color: transparent;">
						<b>
							<span style="font-family: 맑은 고딕; font-size: 12pt;">
								${PkgModel.start_date_04} ~ ${PkgModel.end_date_04}
							</span>
						</b>	
					</td>
				</tr>
				<tr>
					<td colspan="6" style="background: rgb(217, 217, 217); border-width: 0px 1.5pt 1pt; border-style: none solid solid; border-color: rgb(0, 0, 0) windowtext windowtext; padding: 0cm 5.4pt; width: 699.21px; height: 22pt;">
						<p align="center" style="text-align: center;">
							<b>
								<span style="font-family: 맑은 고딕; font-size: 12pt;">
									개발 주요 내역
								</span>
							</b>
						</p>
					</td>
				</tr>
				<tr>
					<td style="border-width: 0px 1pt 1pt 1.5pt; border-style: none solid solid; border-color: rgb(0, 0, 0) windowtext windowtext; padding: 0cm 5.4pt; width: 97.6pt; height: 32.1pt; background-color: transparent;">
						<p align="center" style="text-align: center;">
							<b>
								<span style="font-family: 맑은 고딕; font-size: 12pt;">
									개발 검증 요약
								</span>
							</b>
						</p>
					</td>
					<td colspan="5" style="border-width: 0px 1.5pt 1.5pt 0px; border-style: none solid solid none; border-color: rgb(0, 0, 0) windowtext windowtext rgb(0, 0, 0); width: 204.21px; height: 17.05pt; background-color: transparent; word-break:break-all;">
							<span style="font-family: 맑은 고딕; font-size: 12pt; ">
								<table style="width: 100%; margin-left: 0px; margin-right: 0px;" border="1">
									<tr>	
										<th style="background: rgb(217, 217, 217);"></th>
										<th style="background: rgb(217, 217, 217);">항목수</th>
										<th style="background: rgb(217, 217, 217);">검증내역개수</th>
										<th style="background: rgb(217, 217, 217);">개선내역개수</th>
										<th style="background: rgb(217, 217, 217);">검증진도율</th>
									</tr>
									<tr>
										<th style="background: rgb(217, 217, 217);">신규</th>
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
										<th style="background: rgb(217, 217, 217);">보완</th>
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
										<th style="background: rgb(217, 217, 217);">개선</th>
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
										<th style="background: rgb(217, 217, 217);">합계</th>
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
								</table>
								<p align="left">
									${PkgModel.content }
								</p>
							</span>
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
	</div>
</form:form>
</body>
</html>


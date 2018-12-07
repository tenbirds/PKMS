<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<body>
						<c:if test="${fn:length(PkgModel.newpncrModelList) == 0}">
							<tr>
								<td colspan="4" align="center">등록된 항목이 없습니다.</td>
							</tr>
						</c:if>
	
						<c:forEach var="result" items="${PkgModel.newpncrModelList}" varStatus="status">
									<tr>
										<td style="width:50px;" align="center">
							<c:choose>
								<c:when test="${PkgModel.new_pn_cr_seq == result.new_pn_cr_seq}">
											<input type="radio" name="newpncr_radio" value="${result.new_pn_cr_seq}_${result.new_pn_cr_no}" checked/>
								</c:when>
								<c:otherwise>
											<input type="radio" name="newpncr_radio" value="${result.new_pn_cr_seq}_${result.new_pn_cr_no}" />
								</c:otherwise>
							</c:choose>
										</td>
										<td style="width:70px;" align="center">${result.new_pn_cr_no}</td>
										<td style="width:50px;" align="center">${result.new_pncr_gubun}</td>
										<td align="left" style="padding:0 0 0 5px">${result.title}</td>
									</tr>
						</c:forEach>
</body>

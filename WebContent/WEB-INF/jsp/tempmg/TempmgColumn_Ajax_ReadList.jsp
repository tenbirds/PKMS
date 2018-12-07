<%--
/**
 * 등록된 템플릿 항목 목록
 * 
 * @author : 009
 * @Date : 2012. 4. 20.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

						<c:if test="${fn:length(TempmgModelList) == 0}">
							<tr>
								<td colspan="2">등록된 항목이 없습니다.</td>
							</tr>
						</c:if>
						
						<c:forEach var="result" items="${TempmgModelList}" varStatus="status">
							<tr>
								<td><c:out value="${status.count}" /></td>
								<td align="left">
									<c:choose>
										<c:when test="${TempmgModel.pkgUseCnt == 0}">
											<a href="javascript: fn_column_read('<c:out value="${result.ord}"/>')"><c:out value="${result.title}" /></a>
										</c:when>
										<c:otherwise>
											<c:out value="${result.title}" />
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>

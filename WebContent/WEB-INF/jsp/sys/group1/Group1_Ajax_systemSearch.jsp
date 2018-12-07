<%--
/**
 * Ajax를 이용하여 표시되는 시스템 대분류 상세
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javaScript" defer="defer">
	
</script>
<div class="new_pkms_sysSearchResult new_pkms_sysSearchResultHeader">
	<table>
		<colgroup>
			<c:if test= "${SysModel.searchGubun == '0'}">
				<col style="width: 100%" />
			</c:if>
			<c:if test= "${SysModel.searchGubun == '1'}">
				<col style="width: 50%" />
				<col style="width: 20%" />
				<col style="width: 30%" />
			</c:if>
		</colgroup>
		<thead>
			<tr>
				<c:if test= "${SysModel.searchGubun == '0'}">
					<th>해당 경로</th>
				</c:if>
				<c:if test= "${SysModel.searchGubun == '1'}">
					<th>해당 경로</th>
					<th>담당자</th>
					<th>소속명</th>
				</c:if>
			</tr>
		</thead>
	</table>
</div><!-- .pkms_sysSearchResultHeader -->

<div class="new_pkms_sysSearchResult new_pkms_sysSearchResultBody">
	<table>
		<colgroup>
			<c:if test= "${SysModel.searchGubun == '0'}">
				<col style="width: 100%" />
			</c:if>
			<c:if test= "${SysModel.searchGubun == '1'}">
				<col style="width: 50%" />
				<col style="width: 20%" />
				<col style="width: 30%" />
			</c:if>
		</colgroup>
		<tbody>
			<c:forEach var="result" items="${SearchList}" varStatus="status">
				<tr>
					<c:if test= "${SysModel.searchGubun == '0'}">
						<td>
							<a href="javascript:system_setting(${result.group1_seq }, ${result.group2_seq }, ${result.group3_seq }, ${result.system_seq });">
								<c:out value ="${result.group_full_name }"/>
							</a>
						</td>
					</c:if>
					<c:if test= "${SysModel.searchGubun == '1'}">
						<td>
							<a href="javascript:system_setting(${result.group1_seq }, ${result.group2_seq }, ${result.group3_seq }, ${result.system_seq });">
								<c:out value ="${result.group_full_name }"/>
							</a>
						</td>
						<td><c:out value ="${result.system_user_name }"/></td>
						<td><c:out value ="${result.user_sosok_name }"/></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div><!-- .pkms_sysSearchResultBody -->
<div class="new_pkms_sysSearchResultClose">
	<a href="javascript:close_sysSearch();">닫기</a>
</div>










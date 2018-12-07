<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<body>
	<h3 class="stitle">공지대상부서</h3>
	<table class="tbl_type12">
		<thead>
			<tr>
				<th style="width:10%">&nbsp;</th>
				<th style="width:30%;">
					공지대상부서
				</th>
				<th style="width:10%;">
					확인
				</th>
				<th style="width:10%;">
					확인자
				</th>
				<th style="width:40%;">
					확인일시
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${SolutionReportModel.solutionReportSosokList}" varStatus="status">
				<tr>
					<td>
						<input type="checkbox" name="check_indepts" value="${result.indept}" checked="checked"/>
					</td>
					<td>
						${result.sosok}
					</td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
				<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_indepts_count"/></c:if>
			</c:forEach>
		</tbody>
	</table>
			
	<h3 class="stitle">승인</h3>
	<table class="tbl_type12">
		<thead>
			<tr>
				<th style="width:5%">&nbsp;</th>
				<th style="width:10%;">승인</th>
				<th style="width:10%;">이름</th>
				<th style="width:25%;">부서</th>
				<th style="width:20%;">전화번호</th>
				<th style="width:30%;">이메일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${SolutionReportModel.systemUserModelList}" varStatus="status">
				<tr>
					<td>
						<input type="checkbox" name="check_seqs" value="${result.user_id}" checked="checked"/>
					</td>
					<td></td>
					<td>${result.user_name}</td>
					<td>${result.sosok}</td>
					<td>${result.user_phone}</td>
					<td>${result.user_email}</td>
				</tr>
				<c:if test="${status.last}"><input type="hidden" value="${status.count}" id="check_seqs_count"/></c:if>
			</c:forEach>
		</tbody>
	</table>
</body>

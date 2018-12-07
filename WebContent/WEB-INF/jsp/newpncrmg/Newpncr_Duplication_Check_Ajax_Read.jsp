<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
	<fieldset style="border:1px solid #ddd;">
		<legend>
			기본정보
		</legend>
	
		<table class="tbl_type11" >
			<colgroup>
				<col width="120">
				<col width="388">
			</colgroup>
			<tr>
				<th>제목</th>
				<td colspan="7"><c:out value="${NewpncrModel.title}"/></td>
			</tr>
			<tr>
				<th class="th_height">신규/보안/개선구분</th>
				<td>${NewpncrModel.new_pncr_gubun}</td>
			</tr>
			<tr>
				<th>우선순위</th>
				<td>${NewpncrModel.priority_name}</td>
			</tr>
			<tr>
				<th>시스템</th>
				<td>${NewpncrModel.system_name}</td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset class="detail_fieldset" style="width:508px;height:362px;border:1px solid #ddd;overflow-y:scroll;overview:hidden;">
		<legend>상세정보</legend>
		<table class="tbl_type11" >
			<colgroup>
				<col width="120">
				<col width="388">
			</colgroup>
			<tr>
				<th>문제구분</th>
				<td>${NewpncrModel.problem_gubun_name}</td>
			</tr>
			<tr>
				<th>문제점</th>
				<td>${NewpncrModel.problem}</td>
			</tr>
			<tr>
				<th>요구사항</th>
				<td>${NewpncrModel.requirement}</td>
			</tr>
			<tr>
			<tr>
				<th>첨부파일1</th>
				<td colspan="3">
					<ui:file attachFileModel="${NewpncrModel.file1}" name="file1" mode="view" />
				</td>
			</tr>
			<tr>
				<th>첨부파일2</th>
				<td colspan="3">
					<ui:file attachFileModel="${NewpncrModel.file2}" name="file2" mode="view" />
				</td>
			</tr>
			<tr>
				<th>첨부파일3</th>
				<td colspan="3">
					<ui:file attachFileModel="${NewpncrModel.file3}" name="file3" mode="view" />
				</td>
			</tr>
		</table>
	</fieldset>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javaScript">
</script>
				
	<div>
		<table class="tbl_type_ly">
			<caption>검증 항목</caption>
			<tr>
				<th style="width:20px;"></th>
				<th style="">검증 항목 주제</th>
				<th style="width:220px;">검증 결과</th>
			</tr>
			<c:forEach var="List_Quest" items="${List_verifyQuest}" varStatus="status">
				<tr height="32px;">
					<td>${status.index+1}</td>
					<td style="text-align: left;">${List_Quest.quest_title}</td>
					<td>
						<c:if test="${List_Quest.quest_type eq '0'}"><!-- 선택형일때 -->
							<div>
								<input type="radio" id="" name="${List_Quest.quest_seq}" value="OK" class="result_radio"/> OK
								<input type="radio" id="" name="${List_Quest.quest_seq}" value="NOK" class="result_radio"/> NOK
								<input type="radio" id="" name="${List_Quest.quest_seq}" value="COK" class="result_radio"/> COK
								<input type="radio" id="" name="${List_Quest.quest_seq}" value="BYPASS" class="result_radio"/> BYPASS
							</div>
						</c:if>
						<c:if test="${List_Quest.quest_type eq '1'}"><!-- 단답형일때 -->
							<input type="text" id="" name="${List_Quest.quest_seq}" value="" class="result_input" style="width: 140px;" maxlength="200"/>
							<input type="checkbox" class="chkbypass" onclick="javascript:check_bypass(this);"/> BYPASS
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>				


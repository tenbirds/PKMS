<%--
/**
 * Ajax를 이용하여 표시되는 시스템 상세
 * 
 * @author : skywarker
 * @Date : 2012. 4. 30.
 */
--%>

<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>

<script type="text/javaScript" defer="defer">
	function fn_initSystemUser(title) {
		$("#system_user_title").text(title+ " 담당자 선택");
		fn_bpUser_readList();
	}

	function fn_bpUser_readList() {
		doSubmit("SysModel", "/sys/system/SystemBpUser_Ajax_ReadList.do",
				"fn_callback_bpUser_readList");
	}

	function fn_callback_bpUser_readList(data) {
		$("#system_user_list_area").html(data);
	}
</script>

<!--관련정보 띄우기 -->
<div class="sysc_div2_pop">

	<div id="system_user_title" class="sysc_div2_pop_title">담당자</div>

	<!--리스트 -->
	<div style="float: left; width: 752px; height: 328px; border: 1px solid #cbcbcb; padding: 0px; margin-left: 5px;">

		<!--해당리스트 -->
		<div id="system_user_list_area" style="padding: 0px 0px 0px 0px; height: 327px; clear: both; overflow: auto;"></div>

	</div>

</div>





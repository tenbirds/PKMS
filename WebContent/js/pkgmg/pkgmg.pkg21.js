function fn_readList(pageIndex) {
	var form = document.getElementById("Pkg21Model");

	if(($("#date_start").val().replace(/-/g,""))>$("#date_end").val().replace(/-/g,"")){
		alert("시작일자가 종료일자보다 나중입니다.");
		$("#date_end").focus();
		return;
	}
	
	// 검색어없이 검색 시 전체 검색 
	document.getElementById("pageIndex").value = pageIndex;

	form.action = "/pkgmg/pkg21/Pkg21_ReadList.do";
	form.submit();
}

function fn_clearList (flag) {
	location.href="/pkgmg/pkg21/Pkg21_ReadList.do";
}

function fn_pkg21_read(gubun, pkg_seq) {
	var form  = document.getElementById('Pkg21Model');
	var read_gubun = document.getElementById("read_gubun");
	if(pkg_seq != null && pkg_seq != ""){		
		document.getElementById("pkg_seq").value = pkg_seq;
	}
	
	if (gubun == "new") {
		read_gubun.value = "new";
	}else if(gubun == "read"){
		read_gubun.value = "read";
	}else{
		alert("you must have param selected read or view");
		return;
	}
	form.action = "/pkgmg/pkg21/Pkg21_Read.do";
	form.submit();
}

//doSubmit(formId, url, callback, message)
function fn_pkg21_status_read(status) {
	$("#select_status").val(status);

	var form  = document.getElementById('Pkg21Model');
	form.action = "/pkgmg/pkg21/Pkg21_Status_Read.do";
	form.submit();
}

function fn_open_and_close(gubun){
	
	var list_id = "";
	var open_id = "";
	if(gubun == '0'){
		list_id = "con_list";
		open_id = "con_open";
	}else{
		list_id = "con_list"+gubun;
		open_id = "con_open"+gubun;
	}
	
	var con_list = document.getElementById(list_id);
	var con_open = document.getElementById(open_id);
	if(con_list.style.display=='block'){
		con_list.style.display="none";
		con_open.innerText="열기";
	} else {
		con_list.style.display="block";
		con_open.innerText="접기";
	}
}

function fn_ver_gubun_change(obj){
	if("F" == $(obj).val()){
		$("input[name=vol_yn]").attr("onclick", "return false;");
		$("input[name=vol_yn]").attr("checked", true);
		$("input[name=vol_yn]").val("Y");
		
		if("Y" == $("#pe_type").val()){
			$("input[name=cha_yn]").attr("onclick", "return false;");
			$("input[name=cha_yn]").attr("checked", true);
			$("input[name=cha_yn]").val("Y")
		}else{
			$("input[name=cha_yn]").attr("onclick", "return false;");
			$("input[name=cha_yn]").attr("checked", false);
			$("input[name=cha_yn]").val("N")
		}
		
		$("input[name=patch_yn]").val("N");
	}else if("C" == $(obj).val()){
		
		$("input[name=patch_yn]").val("Y");
		
		$("input[name=cha_yn]").attr("onclick", "javascript:fn_checkbox_yn_change(this, 'cha');");
		$("input[name=cha_yn]").attr("checked", false);
		$("input[name=cha_yn]").val("N")
		
		$("input[name=vol_yn]").attr("onclick", "javascript:fn_checkbox_yn_change(this, 'vol');");
		$("input[name=vol_yn]").attr("checked", false);
		$("input[name=vol_yn]").val("N")
			
			
			
	}else{
		$("input[name=vol_yn]").attr("onclick", "javascript:fn_checkbox_yn_change(this, 'vol');");
		$("input[name=vol_yn]").attr("checked", false);
		$("input[name=vol_yn]").val("N");
		
		if("Y" == $("#pe_type").val()){
			$("input[name=cha_yn]").attr("onclick", "return false;");
			$("input[name=cha_yn]").attr("checked", true);
			$("input[name=cha_yn]").val("Y")
		}else{
			$("input[name=cha_yn]").attr("onclick", "return false;");
			$("input[name=cha_yn]").attr("checked", false);
			$("input[name=cha_yn]").val("N")
		}
		$("input[name=patch_yn]").val("N");
	}
	
//	$("input[name=patch_yn]").val("N");
//	$("input[name=patch_yn]").attr("checked", false);
//	$("input[name=patch_yn]").attr("readonly", false);
}

function fn_patch_yn_change(obj, str) {
	//obj = this(해당 check box), str = name구분
	if($(obj).is(":checked")) {
		$("input[name="+str+"_yn]").val("Y");
		
		$("input[name=cha_yn]").attr("onclick", "");
		$("input[name=cha_yn]").attr("checked", false);
		$("input[name=cha_yn]").val("N");
		
		$("input[name=vol_yn]").attr("onclick", "");
		$("input[name=vol_yn]").attr("checked", false);
		$("input[name=vol_yn]").val("N");
		
	} else {
		$("input[name="+str+"_yn]").val("N");

		//전체
		if("F" == $("input[name=ver_gubun]:checked").val()){
			$("input[name=vol_yn]").attr("onclick", "return false;");
			$("input[name=vol_yn]").attr("checked", true);
			$("input[name=vol_yn]").val("Y");
			
			if("Y" == $("#pe_type").val()){
				$("input[name=cha_yn]").attr("onclick", "return false;");
				$("input[name=cha_yn]").attr("checked", true);
				$("input[name=cha_yn]").val("Y")
			}else{
				$("input[name=cha_yn]").attr("onclick", "return false;");
				$("input[name=cha_yn]").attr("checked", false);
				$("input[name=cha_yn]").val("N")
			}
			
			$("input[name=patch_yn]").val("N");
		}else if("C"== $("input[name=ver_gubun]:checked").val()){
			
			$("input[name=patch_yn]").val("Y");
			
			$("input[name=cha_yn]").attr("onclick", "javascript:fn_checkbox_yn_change(this, 'cha');");
			$("input[name=cha_yn]").attr("checked", false);
			$("input[name=cha_yn]").val("N")
			
			$("input[name=vol_yn]").attr("onclick", "javascript:fn_checkbox_yn_change(this, 'vol');");
			$("input[name=vol_yn]").attr("checked", false);
			$("input[name=vol_yn]").val("N")
				
						
		//부분
		}else{
			$("input[name=vol_yn]").attr("onclick", "return false;");
			$("input[name=vol_yn]").attr("checked", false);
			$("input[name=vol_yn]").val("N");
			
			if("Y" == $("#pe_type").val()){
				$("input[name=cha_yn]").attr("onclick", "return false;");
				$("input[name=cha_yn]").attr("checked", true);
				$("input[name=cha_yn]").val("Y")
			}else{
				$("input[name=cha_yn]").attr("onclick", "return false;");
				$("input[name=cha_yn]").attr("checked", false);
				$("input[name=cha_yn]").val("N")
			}
			$("input[name=patch_yn]").val("N");
		}
	}
	
//	$("#id").attr("readonly", true);
//	$("#id").attr("readonly", false); 
//	$("#"+name+"_ok").attr("checked", true);
}


//checkbox YN on/off
function fn_checkbox_yn_change(obj, str) {
	//obj = this(해당 check box), str = name구분
	if($(obj).is(":checked")) {
		$("input[name="+str+"_yn]").val("Y");
	} else {
		$("input[name="+str+"_yn]").val("N");
	}
}

//시스템 선택 결과 처리
function fn_system_popup_callback(system_seq, system_name) {
	$("input[id=system_seq]").val(system_seq);
	$("input[id=system_name]").val(system_name);
	
	doSubmit("Pkg21Model", "/pkgmg/pkg21/Pkg21_PeType_Ajax_Read.do", "fn_callback_pkg21_PeType_read");
}

function fn_callback_pkg21_PeType_read(data) {
	//과금영향도 타입 Y / N
	var pe_type = $("input[id=param1]").val();
	$("#pe_type").val(pe_type);
	if("Y" == pe_type){
		$("input[name=cha_yn]").attr("onclick", "return false;");
		$("input[name=cha_yn]").attr("checked", true);
		$("input[name=cha_yn]").val("Y")
	}else{
		$("input[name=cha_yn]").attr("onclick", "return false;");
		$("input[name=cha_yn]").attr("checked", false);
		$("input[name=cha_yn]").val("N")
	}
	var patch_yn = $("input[name=patch_yn]");
	fn_patch_yn_change(patch_yn, "patch");
}

function comment_yn(name){
	var id = $("input[name="+name+"]:checked").val();
	
	if(id == "Y"){
		doDivSH("show", name+"_comment", 0);
	}else{
		doDivSH("hide", name+"_comment", 0);
	}
}

function fn_save_101(){
	if ($.trim($("input[id=system_seq]").val()) == "") {
		alert("시스템을 선택하세요.");
		return;
	}else{
		$("input[id=system_seq]").val($.trim($("input[id=system_seq]").val()));
	} 
	
	var work_level = $("select[name=work_level] option:selected").val();
	if(work_level == ""){
		alert("작업난이도를 선택하세요.");
		return;
	}
	var important = $("select[name=important] option:selected").val();
	if(important == ""){
		alert("중요도를 선택하세요.");
		return;
	}
	
	if ($.trim($("input[id=ver]").val()) == "") {
		alert("PKG 버전을 입력하세요.");
		$("input[id=ver]").focus();
		return;
	}else{
		$("input[id=ver]").val($.trim($("input[id=ver]").val()));
	}

	if($("input[id=pkg_seq]").val() == "") {
		$("#status").val("101");
		create_101();
	} else {
		update_101();
	}
}

function create_101(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		$(window).unbind('beforeunload');
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Create.do", "fn_callback_101");
	}else{
		return;
	}
}

function update_101(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		$(window).unbind('beforeunload');
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Update.do", "fn_callback_101");
	}else{
		return;
	}
}

function fn_callback_101(data){
	fn_pkg21_read("read", $("input[id=param1]").val())
}



function fn_save_102(){
	var col3 = $("#col3");
	var col4 = $("#col4");
	
	var col5 = $("#col5");
	var col6 = $("#col6");
	
	var col11 = $("#col11");
	var col12 = $("#col12");
	var date_result = true; 
	date_result = date_validation_check(col3, col4);
	if(date_result == false){
		return;
	}
	date_result = date_validation_check(col5, col6);
	if(date_result == false){
		return;
	}
	date_result = date_validation_check(col11, col12);
	if(date_result == false){
		return;
	}
	

	<!--
	/*
	20181115 eryoon 추가
	Cell 조직 참여 체크시 참여인력 선택하기

		var cellFlag = $('input:checkbox[id="col17"]').is(":checked");
		var cellUser = $('[name="sel_devsysUserVerifyId"]');
		var cellUserLen = 0;
		
		if (cellFlag) {
			cellUserLen = cellUser.length;
			
			if (cellUserLen == 0) {
				alert("Cell 조직 참여에 체크 하셨습니다. \n적어도 한명의 Cell 참여 인력을 선택 하셔야 합니다.");
				return;
			}
		}
	*/	
	//-->

	if($("#status").val() == "101") {
		$("#save_status").val("102");
		create_status();
	} else if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	} else{
		$("#save_status").val("102");
		update_status();	
	}
}

function fn_save_111(){
	var col1 = $("#col1");
	var col2 = $("#col2");
	var date_result = true; 

	date_result = date_validation_check(col1, col2);
	if(date_result == false){
		return;
	}

	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	$("#save_status").val("111");
	create_status();
}

function fn_save_112(ord){
	$("#save_status").val("112");
	$("#ord").val(ord);
	create_status();
}

function fn_save_113(){
	var col3 = $("select[name=col3]").val();
	if(col3 == "불량"){
		if(confirm("검증결과를 '불량' 입력하셨습니다.\n 확실합니까?")) {
			fn_reject_113();
		}else{
			return;
		}
	}else if(col3 == "양호"){		
		$("#save_status").val("113");
		create_status();
	}else if(col3 == ""){
		alert("검증결과를 입력하세요.");
		$("select[name=col3]").focus();
		return;
	}
}

function fn_save_114(ord){
	$("#save_status").val("114");
	$("#ord").val(ord);
	create_status();
}

function fn_save_121(){
	var col1 = $("#col1");
	var col2 = $("#col2");
	
	var date_result = true; 

	date_result = date_validation_check(col1, col2);
	if(date_result == false){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	$("#save_status").val("121");
	create_status();
}
function fn_save_122(ord){
	if($.trim($("#au_comment").val()) == ""){
		alert("승인 시 comment를 입력하세요.");
		$("#au_comment").focus();
		return;
	}
	$("#save_status").val("122");
	$("#ord").val(ord);
	create_status();
}
function fn_save_123(){
	var col6 = $("#col6");
	var col7 = $("#col7");
	var date_result = true;
	
	if("N" != $("#cha_yn").val()){
		if($.trim(col6.val()) == ""){
			alert("과금검증 내용을 확인하세요.");
			col6.focus();
			return;
		}
	}
	if("N" != $("#vol_yn").val()){
		if($.trim(col7.val()) == ""){
			alert("용량검증 내용을 확인하세요.");
			col7.focus();
			return;
		}
	}
	
	var col11 = $("select[name=col11]").val();
	
	if(col11 == "불량"){
		if(confirm("검증결과를 '불량' 입력하셨습니다.\n 확실합니까?")) {
			fn_reject_123();
		}else{
			return;
		}
	}else if(col11 == "양호"){		
		$("#save_status").val("123");
		create_status();
	}else if(col11 == ""){
		alert("검증결과를 입력하세요.");
		$("select[name=col11]").focus();
		return;
	}
}
function fn_save_124(ord){
	$("#save_status").val("124");
	$("#ord").val(ord);
	create_status();
}

function fn_save_161(){
	var col1 = $("#col1");
	var col2 = $("#col2");
	var date_result = true; 

	date_result = date_validation_check(col1, col2);
	if(date_result == false){
		return;
	}

	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	var col4 = $(':radio[name=col4]:checked').val();
	if("반려" == col4){
		if($.trim($("#col5").val()) == ""){
			alert("반려 시 과금검증 Comment를 입력해야 합니다.");
			$("#col5").focus();
			return;
		}
	}
	
	$("#save_status").val("161");
	create_status();
}

function fn_save_162(ord){
	$("#save_status").val("162");
	$("#ord").val(ord);
	create_status();
}

function fn_save_151(){
	var col1 = $("#col1");
	var col2 = $("#col2");
	var date_result = true; 

	date_result = date_validation_check(col1, col2);
	if(date_result == false){
		return;
	}
	
	var result = true;
	result = fn_vol_check();
	if(!result){
		return;
	}

	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	$("#save_status").val("151");
	create_status();
}

function fn_save_152(ord){
	$("#save_status").val("152");
	$("#ord").val(ord);
	create_status();
}

function fn_save_171(){
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}

	if($.trim($("#col1").val()) == ""){
		alert("보안검증 Comment를 입력해야 합니다.");
		$("#col1").focus();
		return;
	}

	$("#save_status").val("171");
	create_status();
}

function fn_save_131(){
	var result = true;
	result = fn_equipment_check();
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	$("#save_status").val("131");
	create_status();
}
function fn_save_132(ord){
	if($.trim($("#au_comment").val()) == ""){
		alert("승인 시 comment를 입력하세요.");
		$("#au_comment").focus();
		return;
	}
	$("#save_status").val("132");
	$("#ord").val(ord);
	create_status();
}

function fn_save_133(){
	if($.trim($("#col2").val()) == ""){
		alert("Comment 를 입력하세요.");
		return;
	}
	
	if ($.trim($("#col3").val()) == "") {
		alert("필수입력 날짜를 확인하세요.");
		$("#col3").focus();
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}

	var col1 = $("select[name=col1] option:selected").val();
	if(col1 == ""){
		alert("당일결과를 입력하세요.");
		return;
	}else if(col1 == "불량"){
		if(confirm("결과가 불량이면 PKG가 반려로 종료됩니다.")) {
			$("#save_status").val("139");
		}else{
			return;
		}
	}else{	
		$("#save_status").val("133");
	}
	create_status();
}
function fn_save_134(){
	var col4 = $("select[name=col4] option:selected").val();
	if(col4 == ""){
		alert("최종결과를 입력하세요.");
		return;
	}else if(col4 == "불량"){
		if(confirm("결과가 불량이면 PKG가 반려로 종료됩니다.")) {			
			$("#save_status").val("139");
		}else{
			return;
		}
	}else{		
		$("#save_status").val("134");
	}
	
	create_status();
}

function fn_save_140(){
	var result = true;
	result = fn_equipment_check();
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	$("#update_gubun").val("Y");
	$("#save_status").val("140");
	create_status();
}

function fn_save_eq_e(){
	var result = true;
	result = fn_equipment_check();
	if(!result){
		return;
	}
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Eq_E_Update.do", "fn_eq_e_callback");
	}else{
		return;
	}
}

function fn_eq_e_callback(data){
	var select_status = $("input[id=param1]").val();
	
	$("#save_status").val("");
	fn_pkg21_status_read(select_status);
}

function fn_after_eq_e(){
	var result = true;
	result = fn_equipment_check();
	if(!result){
		return;
	}
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_After_E_Update.do", "fn_after_e_callback");
	}else{
		return;
	}
}

function fn_after_e_callback(data){
	var select_status = $("input[id=param1]").val();
	
	$("#save_status").val("");
	fn_pkg21_status_read(select_status);
}

function fn_save_141(){
	var result = true;
	result = fn_equipment_check();
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	$("#update_gubun").val("N");
	$("#save_status").val("141");
	create_status();
}

function fn_after_141(){
	var result = true;
	result = fn_equipment_check();
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_After_Create.do", "fn_callback_after");
	}else{
		return;
	}
}

function fn_save_142(ord){
	if($.trim($("#au_comment").val()) == ""){
		alert("승인 시 comment를 입력하세요.");
		$("#au_comment").focus();
		return;
	}
	$("#save_status").val("142");
	$("#ord").val(ord);
	create_status();
}

function fn_after_142(ord){
	if($.trim($("#au_comment").val()) == ""){
		alert("승인 시 comment를 입력하세요.");
		$("#au_comment").focus();
		return;
	}
	
	$("#ord").val(ord);
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_After_Update.do", "fn_callback_after");
	}else{
		return;
	}
}

function fn_callback_after(data){
	var select_status = $("input[id=param1]").val();

	$("#save_status").val("");
	fn_pkg21_status_read(select_status);
}

function fn_save_143(){
	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	$("#save_status").val("143");
	create_status();
}
function fn_save_144(){
	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	$("#save_status").val("144");
	create_status();
}
function fn_save_199(){
	if(133==$("#select_status").val()){		
		var col4 = $("select[name=col4] option:selected").val();
		if(col4 == ""){
			alert("최종결과를 입력하세요.");
			return;
		}else if(col4 == "불량"){
			if(confirm("결과가 불량이면 PKG가 반려로 종료됩니다.")) {			
				$("#save_status").val("139");
			}else{
				return;
			}
		}else{
			$("#save_status").val("199");
		}
	}else{		
		var result = true;
		result = fn_199_final_check();
		if(!result){
			return;
		}
		$("#save_status").val("199");
	}
	create_status();
}

function fn_199_final_check(){
	var check_seqs_e = $("input[name=check_seqs_e]");
	var eq_cnt = 0;
	for(var i = 0; i < check_seqs_e.size(); i++) {
		if(check_seqs_e.eq(i).is(":checked")) {
			eq_cnt++;
		}
	}
	if(eq_cnt > 0){
		alert("적용결과를 저장해 주시기 바랍니다.");
		return false;
	}
	return true;
}

function create_status(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Status_Create.do", "fn_callback_status");
	}else{
		return;
	}
}

function update_status(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Status_Update.do", "fn_callback_status");
	}else{
		return;
	}
}
	
function fn_callback_status(data){
	var select_status = $("input[id=param1]").val();
	var save_status = $("input[id=param2]").val();
	if(save_status == '152'){
		$("#vol_yn").val("S");
	}else if(save_status == '162'){
		$("#cha_yn").val("S");
	}else if(save_status == '171'){
		$("#sec_yn").val("S");
	}else if(save_status == '151' || save_status == '161'){
	}else{		
		$("#status").val(save_status);
	}

	if("133" == select_status){
		if("133" == $("#status").val()){
			if("133" == save_status){
				if("S" == $("#cha_yn").val()){					
					$("#cha_yn").val("Y");
				}
			}
		}
	}

	if("143" == select_status){
		if("143" == $("#status").val()){
			if("143" == save_status){				
				if("S" == $("#cha_yn").val()){					
					$("#cha_yn").val("Y");
				}
			}
		}
	}
	
	$("#save_status").val("");
	fn_pkg21_status_read(select_status);
	
}

function date_validation_check(start_date, end_date){
	if ($.trim(start_date.val()) == "" || $.trim(end_date.val()) == "") {
		alert("필수입력 날짜를 확인하세요.");
		start_date.focus();
		return false;
	}else{
		start_date.val($.trim(start_date.val()));
		end_date.val($.trim(end_date.val()));
	}
	if((start_date.val().replace(/-/g,""))>end_date.val().replace(/-/g,"")){
		alert("시작일자가 종료일자보다 나중입니다.");
		end_date.focus();
		return false;
	}
	return true;
}

function fn_SelectAllCheck(){
	var count = $("#check_seqs_count").val();
	
	var sday = $("#PkgModel_pkgEquipmentModel_start_date_All").val();
	var eday = $("#PkgModel_pkgEquipmentModel_end_date_All").val();
	
	if(sday == "" || eday == ""){
		alert("적용 일시를 먼저 선택 해 주세요.");
		return;
	}

	for(var i=0; i<count; i++){
		if($("input[name='check_seqs_e']").get(i).checked){
			$("#PkgModel_pkgEquipmentModel_start_date_"+(i+1)).val(sday);
			$("#PkgModel_pkgEquipmentModel_end_date_"+(i+1)).val(eday);
			$("#PkgModel_pkgEquipmentModel_start_time1_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_start_time1_All").val());
			$("#PkgModel_pkgEquipmentModel_start_time2_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_start_time2_All").val());
			$("#PkgModel_pkgEquipmentModel_end_time1_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_end_time1_All").val());
			$("#PkgModel_pkgEquipmentModel_end_time2_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_end_time2_All").val());
			$("#PkgModel_pkgEquipmentModel_ampm_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_ampm_All").val());
		}
	}	
}

function fn_SelectAmPm_All(obj){
//	alert(obj.value);
	
	var start_times1 = $("#PkgModel_pkgEquipmentModel_start_time1_All");
	var start_times2 = $("#PkgModel_pkgEquipmentModel_start_time2_All");
	var end_times1 = $("#PkgModel_pkgEquipmentModel_end_time1_All");
	var end_times2 = $("#PkgModel_pkgEquipmentModel_end_time2_All");
	if(obj.value == "주간"){
		start_times1.val("09");
		start_times2.val("00");
		end_times1.val("18");
		end_times2.val("00");
	}else{		
		start_times1.val("02");
		start_times2.val("00");
		end_times1.val("07");
		end_times2.val("00");
	}
}

function fn_SelectAmPm(obj, index){
//	alert(obj.value);
	var i = index - 1;
	
	var start_times1 = $("input[name='start_times1']");
	var start_times2 = $("input[name='start_times2']");
	var end_times1 = $("input[name='end_times1']");
	var end_times2 = $("input[name='end_times2']");
	if(obj.value == "주간"){
		start_times1.eq(i).val("09");
		start_times2.eq(i).val("00");
		end_times1.eq(i).val("18");
		end_times2.eq(i).val("00");
	}else{		
		start_times1.eq(i).val("02");
		start_times2.eq(i).val("00");
		end_times1.eq(i).val("07");
		end_times2.eq(i).val("00");
	}
}

function fn_allCheck() {
	var count = $("#check_seqs_count").val();
	var check_seqs_e = $("input[name='check_seqs_e']");
	
    var chk = $("input[name='allCheckbox']").is(":checked");
    
    for(var i=0; i<count; i++) {
    	if("N" != check_seqs_e.eq(i).val()){
    		check_seqs_e.eq(i).attr("checked", chk);
    		fn_checkboxEquipment_click(check_seqs_e.eq(i), i+1);	    		
    	}
	}
}

function fn_checkboxEquipment_click(obj, index) {
//	alert(obj.value);
	var i = index - 1;
	var start_dates = $("input[name='start_dates']");
	var end_dates = $("input[name='end_dates']");
	var start_times1 = $("input[name='start_times1']");
	var start_times2 = $("input[name='start_times2']");
	var end_times1 = $("input[name='end_times1']");
	var end_times2 = $("input[name='end_times2']");
	var ampms = $("select[name='ampms']");
	
	if($(obj).is(":checked")) {
		//모델에서 초기값을 안 정하고 여기서 하는 이유는 저장 시 체크된 것들만 받을 수 없기 때문
		ampms.eq(i).val("야간");
		start_times1.eq(i).val("02");
		start_times2.eq(i).val("00");
		end_times1.eq(i).val("07");
		end_times2.eq(i).val("00");

		doDivSH("show", "equipment_se_date_" + index, 0);		
	} else {
		$("input[name='allCheckbox']").attr("checked", false);
		start_dates.eq(i).val("");
		end_dates.eq(i).val("");

		start_times1.eq(i).val("");
		start_times2.eq(i).val("");
		end_times1.eq(i).val("");
		end_times2.eq(i).val("");

		doDivSH("hide", "equipment_se_date_" + index, 0);
	}
}

function fn_equipment_check(){
	var check_seqs_e = $("input[name=check_seqs_e]");
	   
	var start_dates = $("input[name='start_dates']");
	var end_dates = $("input[name='end_dates']");
	var non_check =0;

	var start_time1 = $("input[name='start_times1']");
	var end_time1 = $("input[name='end_times1']");
   
	var result = true;
	for(var i = 0; i < check_seqs_e.size(); i++) {
		if(check_seqs_e.eq(i).is(":checked")) {
			result = date_validation_check(start_dates.eq(i), end_dates.eq(i));
			if(!result){
				return false;
			}
			
			if(start_time1.eq(i).val() > 23 || end_time1.eq(i).val() > 23) {
				alert("시간은 0~23중의 숫자를 입력해 주시기 바랍니다.");
				return false;
			}
			non_check++;
		} 
	}
	
   if(non_check==0){
	   alert("적용장비를 선택하십시요");
	   return false;
   }
   return true;
}

function fn_equipment_final_check(){
	var check_seqs_e = $("input[name=check_seqs_e]");
	var work_result = $("select[name=work_result]");
	var eq_cnt = 0;
	for(var i = 0; i < check_seqs_e.size(); i++) {
		if(check_seqs_e.eq(i).is(":checked")) {
			eq_cnt++;
			if(work_result.eq(i).val() == ""){
				alert("적용결과를 모두 입력하셔야 합니다.");
				return false;
			}else if(work_result.eq(i).val() == "불량"){
				if(confirm("결과가 불량이면 PKG가 반려로 종료됩니다.")) {
					$("#save_status").val("149");
					create_status();
					return false;
				}else{
					return false;
				}
			}
		}
	}
	if(eq_cnt == 0){
		alert("결과를 입력할 장비가 존재하지 않습니다.");
		return false;
	}
	return true;
}

function fn_cha_eq(){
	var result = true;
	result = fn_equipment_charge_check();
	if(!result){
		return;
	}
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Cha_Eq_Update.do", "fn_cha_eq_callback");
	}else{
		return;
	}
}

function fn_cha_eq_callback(data){
	var select_status = $("input[id=param1]").val();
	
	$("#save_status").val("");
	$("#cha_yn").val("S");
	fn_pkg21_status_read(select_status);
}

function fn_equipment_charge_check(){
	var check_seqs_e = $("input[name=check_seqs_e]");
	var charge_result = $("select[name=charge_result]");
	
	for(var i = 0; i < check_seqs_e.size(); i++) {
		if(check_seqs_e.eq(i).is(":checked")) {
			if(charge_result.eq(i).val() == ""){
				alert("과금결과를 모두 입력하셔야 합니다.");
				return false;
			}else if(charge_result.eq(i).val() == "불량"){
				if(confirm("과금결과에 불량을 입력하셨습니다.\n계속하시겠습니까?")) {
				}else{
					return false;
				}
			}
		}
	}
	return true;
}

function fn_vol_check(){
	var rowCount =  $("#radioBoxcount > tbody").children().length;
	
	for(var i=0;i<rowCount;i++){
		var i_cnt = 0;
		if($(":input:radio[name='chk_results"+i+"']").is(":checked")){
			i_cnt++;
		}
		if(i_cnt == 0){
			alert("결과를 모두 입력하셔야 합니다.");
			return false;
		}
	}
	
	return true;
}

function fn_reject_142(){
	$("#save_status").val("141");
	alert("반려 시 모든 확대일정 및 결과가 삭제됩니다.");
	fn_reject_rollback();
}

function fn_reject_132(){
	$("#save_status").val("131");
	fn_reject_rollback();
}

function fn_reject_123(){
	$("#save_status").val("122");
	fn_reject_rollback();
}

function fn_reject_121(){
	if($("#dev_yn").val() == "Y"){		
		$("#save_status").val("114");
	}else{
		$("#save_status").val("102");
	}
	fn_reject_rollback();
}

function fn_reject_113(){
	$("#save_status").val("112");
	fn_reject_rollback();
}

function fn_reject_111(){
	$("#save_status").val("102");
	fn_reject_rollback();
}

function fn_reject_rollback(){
	if(confirm("이전 단계로 돌아갑니다.\n반려하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Status_Reject.do", "fn_reject_callback");
	}else{
		return;
	}
}
	
function fn_reject_callback(data){
	var save_status = $("input[id=param2]").val();
	$("#status").val(save_status);
	$("#save_status").val("");
	fn_pkg21_read("read", $("#pkg_seq").val());
	
}

function fn_pkg21_delete(){
	if(confirm("삭제 시 복구가 불가능 합니다.\n삭제하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/pkg21/Pkg21_Delete.do", "fn_pkg21_delete_callback");
	}else{
		return;
	}
}

function fn_pkg21_delete_callback(){
	var form = document.getElementById("Pkg21Model");
	form.action = "/pkgmg/pkg21/Pkg21_ReadList.do";
	form.submit();
}

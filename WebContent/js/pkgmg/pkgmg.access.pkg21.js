function fn_readList(pageIndex) {
	var form = document.getElementById("Pkg21Model");

	if(($("#date_start").val().replace(/-/g,""))>$("#date_end").val().replace(/-/g,"")){
		alert("시작일자가 종료일자보다 나중입니다.");
		$("#date_end").focus();
		return;
	}
	
	// 검색어없이 검색 시 전체 검색 
	document.getElementById("pageIndex").value = pageIndex;

	form.action = "/pkgmg/access/Access_Pkg21_ReadList.do";
	form.submit();
}

function fn_clearList (flag) {
	location.href="/pkgmg/access/Access_Pkg21_ReadList.do";
}

function fn_access_read(gubun, pkg_seq) {
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
	form.action = "/pkgmg/access/Access_Pkg21_Read.do";
	form.submit();
}

//doSubmit(formId, url, callback, message)
function fn_access_status_read(status) {
	$("#select_status").val(status);
	
	var form  = document.getElementById('Pkg21Model');
	if(status == "201"){
		fn_access_read("read", $("#pkg_seq").val());
	}else{		
		form.action = "/pkgmg/access/Access_Pkg21_Status_Read.do";
		form.submit();
	}
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
	
//	doSubmit("Pkg21Model", "/pkgmg/access/Access_Pkg21_PeType_Ajax_Read.do", "fn_callback_pkg21_PeType_read");
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

function fn_save_201(){
	if ($.trim($("input[id=title]").val()) == "") {
		alert("제목을 입력하세요.");
		return;
	}else{
		$("input[id=title]").val($.trim($("input[id=title]").val()));
	} 
	
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
	
	var col7 = $("#col7");
	var col8 = $("#col8");
	var date_result = true; 
	date_result = date_validation_check(col7, col8);
	if(date_result == false){
		return;
	}

	if($("input[id=pkg_seq]").val() == "") {
		$("#status").val("201");
		create_201();
	} else {
		update_201();	
	}
}

function create_201(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_Create.do", "fn_callback_201");
	}else{
		return;
	}
}

function update_201(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_Update.do", "fn_callback_201");
	}else{
		return;
	}
}

function fn_callback_201(data){
	fn_access_read("read", $("input[id=param1]").val())
}

function fn_save_202(){
	if ($.trim($("input[id=title]").val()) == "") {
		alert("제목을 입력하세요.");
		return;
	}else{
		$("input[id=title]").val($.trim($("input[id=title]").val()));
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
	
	var col7 = $("#col7");
	var col8 = $("#col8");
	var date_result = true; 
	date_result = date_validation_check(col7, col8);
	
	if(date_result == false){
		return;
	}
	
	if($("#status").val() == "201") {
		$("#save_status").val("202");
		create_status();
	} else if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.");
		return;
	} else{
		alert("PKG검증 상태가 이상합니다.");
		return;
	}
}

function fn_save_203(){
	if ($.trim($("input[id=title]").val()) == "") {
		alert("제목을 입력하세요.");
		return;
	}else{
		$("input[id=title]").val($.trim($("input[id=title]").val()));
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
	
	var col7 = $("#col7");
	var col8 = $("#col8");
	var date_result = true; 
	date_result = date_validation_check(col7, col8);
	if(date_result == false){
		return;
	}
	
	date_result = fn_result_check();
	
	if(!date_result){
		return;
	}
	
	if($("#status").val() == "202") {
		$("#save_status").val("203");
		create_status();
	} else if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.");
		return;
	} else{
		alert("PKG검증 상태가 이상합니다.");
		return;
	}
}

function fn_save_211(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}
	
	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}
			
	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("211");
	create_status();
}

function fn_after_211(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}
	
	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}
	
	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("211");
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_After_Create.do", "fn_after_e_callback");
	}else{
		return;
	}
}

function fn_save_212(){
	$("#save_status").val("212");

	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	
	result = fn_result_check();
	
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	create_status();
}

function fn_save_221(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}
	
	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}
	
	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("221");
	create_status();
}

function fn_after_221(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}

	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}
	
	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("221");
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_After_Create.do", "fn_after_e_callback");
	}else{
		return;
	}
}

function fn_save_222(){
	$("#save_status").val("222");

	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	
	result = fn_result_check();
	
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	create_status();
}

function fn_save_231(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}
	
	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}
	
	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("231");

	create_status();
}

function fn_after_231(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}
	
	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}

	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("231");
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_After_Create.do", "fn_after_e_callback");
	}else{
		return;
	}
}

function fn_save_232(){
	$("#save_status").val("232");

	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	
	result = fn_result_check();
	
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.");
		return;
	}
	
	create_status();
}

function fn_save_233(ord){
	$("#save_status").val("233");
	$("#ord").val(ord);
	create_status();
}

function fn_reject_233(){
	$("#save_status").val("233");
	alert("반려 시 초도일정 및 결과가 삭제됩니다.");
	fn_reject_rollback();
}

function fn_save_241(){
	var start_date = $("#col5");
	var end_date = $("#col6");
	
	var date_result = true; 
		
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}

	$("#save_status").val("241");

	create_status();
}

function fn_save_242(){
	var result = true; 
	
	result = fn_result_check();
	
	if(!result){
		return;
	}

	$("#save_status").val("242");

	create_status();
}

function fn_save_243(ord){
	$("#save_status").val("243");
	$("#ord").val(ord);
	create_status();
}

function fn_reject_241(){
	$("#save_status").val("241");
	alert("DVT결과 단계로 돌아갑니다.DVT결과 단계 포함 이후 단계 정보들은 모두 삭제 됩니다.");
	fn_reject_rollback();
}

function fn_reject_243(){
	$("#save_status").val("243");
	alert("CVT 단계로 돌아갑니다.CVT결과 및 승인 정보는 삭제됩니다.");
	fn_reject_rollback();
}

function fn_save_251(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}

	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}
	
	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("251");
	create_status();
}

function fn_after_251(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}
	
	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}

	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("251");
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_After_Create.do", "fn_after_e_callback");
	}else{
		return;
	}
}

function fn_save_252(){
	$("#save_status").val("252");

	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	
	result = fn_result_check();
	
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	create_status();
}

function fn_save_261(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}

	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}
	
	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("261");

	create_status();
}

function fn_after_261(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}

	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}
	
	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("261");
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_After_Create.do", "fn_after_e_callback");
	}else{
		return;
	}
}

function fn_save_262(){
	$("#save_status").val("262");

	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	
	result = fn_result_check();
	
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.");
		return;
	}
	
	create_status();
}

function fn_save_263(ord){
	$("#save_status").val("263");
	$("#ord").val(ord);
	create_status();
}

function fn_reject_263(){
	$("#save_status").val("263");
	alert("반려 시 초도일정 및 결과가 삭제됩니다.");
	fn_reject_rollback();
}

function fn_save_271(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}

	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}
	
	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("271");

	create_status();
}

function fn_after_271(){
	var start_date = $("#start_date");
	var end_date = $("#end_date");
	
	var date_result = true; 
	
	date_result = date_validation_check(start_date, end_date);
	if(!date_result){
		return;
	}
	
	if($.trim($("input[id=patch_title]").val()) == ""){
		alert("Patch명을 입력하세요.");
		return;
	}

	if ($.trim($("input[id=start_time1]").val()) == ""
		|| $.trim($("input[id=start_time2]").val()) == ""
		|| $.trim($("input[id=end_time1]").val()) == ""
		|| $.trim($("input[id=end_time2]").val()) == ""
		) {
		alert("일정 시간을 입력하세요.");
		return;
	}else{
		$("input[id=start_time1]").val($.trim($("input[id=start_time1]").val()));
		$("input[id=start_time2]").val($.trim($("input[id=start_time2]").val()));
		$("input[id=end_time1]").val($.trim($("input[id=end_time1]").val()));
		$("input[id=end_time2]").val($.trim($("input[id=end_time2]").val()));
	}

	if($("#del_file_id").val() == "0"){
		alert("장비 파일을 등록하세요");
		return;
	}
	
	$("#save_status").val("271");
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_After_Create.do", "fn_after_e_callback");
	}else{
		return;
	}
}

function fn_save_272(){
	$("#save_status").val("272");

	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.");
		return;
	}
	
	create_status();
}

function fn_save_273(ord){
	$("#save_status").val("273");
	$("#ord").val(ord);
	create_status();
}

function fn_reject_273(){
	$("#save_status").val("273");
	alert("반려 시 모든 확대일정 및 결과가 삭제됩니다.");
	fn_reject_rollback();
}

function fn_save_299(){
	$("#save_status").val("299");
	alert("PKG완료 입니다.");
	create_status();
}

function fn_after_e_callback(data){
	var select_status = $("input[id=param1]").val();
	var save_status = $("input[id=param2]").val();
	
	$("#save_status").val("");
	$("#status").val(save_status);
	
	fn_access_status_read(select_status);
}

function create_status(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_Status_Create.do", "fn_callback_status");
	}else{
		return;
	}
}

function update_status(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_Status_Update.do", "fn_callback_status");
	}else{
		return;
	}
}
	
function fn_callback_status(data){
	var select_status = $("input[id=param1]").val();
	var save_status = $("input[id=param2]").val();
	$("#status").val(save_status);
	$("#save_status").val("");
	fn_access_status_read(select_status);
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

function fn_result_check(){
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

function fn_equipment_final_check(){
	var check_seqs_e = $("input[name=check_seqs_e]");
	var work_result = $("select[name=work_result]");
	var result_comment = $("input[name=result_comment]");
	var eq_cnt = 0;
	for(var i = 0; i < check_seqs_e.size(); i++) {
		if(check_seqs_e.eq(i).is(":checked")) {
			eq_cnt++;
			if(work_result.eq(i).val() == ""){
				alert("적용결과를 입력해 주세요.");
				return false;
			}else if(work_result.eq(i).val() == "불량"){
				if(confirm("결과가 불량이면 PKG가 반려로 종료됩니다.")) {
					if($("#save_status").val() == "272"){						
						$("#save_status").val("279");
					}else if($("#save_status").val() == "212"){
						$("#save_status").val("219");
					}else if($("#save_status").val() == "222"){
						$("#save_status").val("229");
					}else if($("#save_status").val() == "232"){
						$("#save_status").val("239");
					}else if($("#save_status").val() == "252"){
						$("#save_status").val("259");
					}
					return true;
				}else{
					return false;
				}
			}
			
			if(result_comment.eq(i).val() == ""){
				alert("작업결과 및 comment를 입력해 주세요.");
				return false;
			}
		}
	}
	if(eq_cnt == 0){
		alert("결과를 입력할 장비가 존재하지 않습니다.");
		return false;
	}
	return true;
}

function fn_reject_rollback(){
	if(confirm("이전 단계로 돌아갑니다.\n반려하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/access/Access_Pkg21_Status_Reject.do", "fn_reject_callback");
	}else{
		return;
	}
}

function fn_reject_callback(data){
	var save_status = $("input[id=param2]").val();
	$("#status").val(save_status);
	$("#save_status").val("");
	fn_access_read("read", $("#pkg_seq").val());
	
}

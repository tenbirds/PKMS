function fn_readList(pageIndex) {
	var form = document.getElementById("Pkg21Model");

	if(($("#date_start").val().replace(/-/g,""))>$("#date_end").val().replace(/-/g,"")){
		alert("시작일자가 종료일자보다 나중입니다.");
		$("#date_end").focus();
		return;
	}
	
	// 검색어없이 검색 시 전체 검색 
	document.getElementById("pageIndex").value = pageIndex;

	form.action = "/pkgmg/wired/Wired_Pkg21_ReadList.do";
	form.submit();
}

function fn_clearList (flag) {
	location.href="/pkgmg/wired/Wired_Pkg21_ReadList.do";
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
	form.action = "/pkgmg/wired/Wired_Pkg21_Read.do";
	form.submit();
}






//doSubmit(formId, url, callback, message)
function fn_pkg21_status_read(status) {
	$("#select_status").val(status);

	var form  = document.getElementById('Pkg21Model');
	form.action = "/pkgmg/wired/Wired_Pkg21_Status_Read.do";
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
	
	doSubmit("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_PeType_Ajax_Read.do", "fn_callback_pkg21_PeType_read");
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


function fn_save_300(){
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
	
	
	
	var col3 = $("#col3");
	var col4 = $("#col4");
	var date_result = true; 

	date_result = date_validation_check(col3, col4);
	if(date_result == false){
		return;
	}
	
	
	

	if($("input[id=pkg_seq]").val() == "") {
		$("#status").val("300");
		create_301();
	} else {
		$("#status").val("300");
		update_301();	
	}
}



function fn_save_301(){
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
	
	
	
	var col3 = $("#col3");
	var col4 = $("#col4");
	var date_result = true; 

	date_result = date_validation_check(col3, col4);
	if(date_result == false){
		return;
	}
	
	
	

	if($("input[id=pkg_seq]").val() == "") {
		$("#status").val("301");
		create_301();
	} else {
		$("#status").val("301");
		update_301();	
	}
}

function create_301(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_Create.do", "fn_callback_301");
	}else{
		return;
	}
}

function update_301(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_Update.do", "fn_callback_301");
	}else{
		return;
	}
}

function fn_callback_301(data){
	fn_pkg21_read("read", $("input[id=param1]").val())
}





function fn_save_302(){
	
	
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
	
	
	
	$("#save_status").val("302");
	create_status();
	
}




function fn_reject_302(){ //개발검증 계획 승인 반려
//	if($.trim($("#au_comment").val()) == ""){
//		alert("반려 시 comment를 입력하세요.");
//		$("#au_comment").focus();
//		return;
//	}
	if(confirm("개발 결과를 반려합니다. PKG개발결과 단계로 돌아갑니다.")) {
		$("#save_status").val("300");
		fn_reject_rollback();
	}else{
		return;
	}
	
}







function fn_save_305(){
	var col1 = $("#col1");
	var col2 = $("#col2");
	
//	var col5 = $("#col5");
//	var col6 = $("#col6");
//	
//	var col11 = $("#col11");
//	var col12 = $("#col12");
	var date_result = true; 
//	date_result = date_validation_check(col3, col4);
//	if(date_result == false){
//		return;
//	}
//	date_result = date_validation_check(col5, col6);
//	if(date_result == false){
//		return;
//	}
	date_result = date_validation_check(col1, col2);
	if(date_result == false){
		return;
	}
	
	 if($("#status").val() == ""){
			alert("PKG검증 상태가 이상합니다.\n PKG개발 검증 먼저 진행해 주십시요.");
			return;
	} else if($("#status").val() == "311") {
		$("#save_status").val("305");
		create_status();
	} else if($("#status").val() == "314"){
		$("#save_status").val("305");
		create_status();	
	}else{
//		$("#save_status").val("305");
//		update_status();	
	}
}






function fn_save_307(){
//	var col1 = $("#col1");
//	var col2 = $("#col2");
	
	var col4 = $("#col4");
	var col5 = $("#col5");
	var col6 = $("#col6");
//	
//	var col11 = $("#col11");
//	var col12 = $("#col12");
	var date_result = true; 

	date_result = date_validation_check(col4, col5);
	if(date_result == false){
		return;
	}
	
	var col11 = $("select[name=col11]").val();
	if(col11 == "불량"){
		if(confirm("검증결과를 '불량' 입력하셨습니다.\n PKG 불량으로 종료 됩니다. \n 다시 한번 확인 하십시오.")) {
//			fn_reject_113();
		}else{
			return;
		}
	}else if(col11 == ""){
		alert("검증결과를 입력하세요.");
		$("select[name=col11]").focus();
		return;
	}
//	}else if(col11 == "양호"){		
//		$("#save_status").val("113");
//		create_status();

	
	
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\n PKG개발 검증 먼저 진행해 주십시요.");
		return;
	} else{
		$("#save_status").val("307");
//		update_status();	
		create_status();
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

function fn_save_306(ord){ //승인버튼
	if($.trim($("#au_comment").val()) == ""){
		alert("승인 시 comment를 입력하세요.");
		$("#au_comment").focus();
		return;
	}
	$("#save_status").val("306");
	$("#ord").val(ord);
	create_status();
}




function fn_reject_306(){ //개발검증 계획 승인 반려
	if($.trim($("#au_comment").val()) == ""){
		alert("반려 시 comment를 입력하세요.");
		$("#au_comment").focus();
		return;
	}
	$("#save_status").val("301");
	fn_reject_rollback();
}





function fn_save_308(ord){ //승인버튼
//	if($.trim($("#au_comment").val()) == ""){
//		alert("승인 시 comment를 입력하세요.");
//		$("#au_comment").focus();
//		return;
//	}
	
	if($.trim($("#col15").val()) == ""){
		alert("반려 시 comment를 입력하세요.");
		$("#col15").focus();
		return;
	}
	
	
	$("#save_status").val("308");
	$("#ord").val(ord);
	create_status();
}

function fn_reject_308(){ //개발검증 결과 승인 반려
//	if($.trim($("#au_comment").val()) == ""){
//		alert("반려 시 comment를 입력하세요.");
//		$("#au_comment").focus();
//		return;
//	}
	if($.trim($("#col15").val()) == ""){
		alert("반려 시 comment를 입력하세요.");
		$("#col15").focus();
		return;
	}
	
	
	
	
	$("#save_status").val("319");
	fn_reject_rollback();
}

function fn_save_331(){
	$("#save_status").val("331");
	create_status();
}


function fn_save_332(){//승인자 저장
	$("#save_status").val("332");
//	$("#ord").val(ord);
	create_status();
//	update_status();
}

function fn_save_333(ord){//승인
	if($.trim($("#au_comment").val()) == ""){
		alert("승인 시 comment를 입력하세요.");
		$("#au_comment").focus();
		return;
	}
	$("#save_status").val("335");
	$("#ord").val(ord);
	create_status();
}

//function fn_save_333(ord){
//	$("#save_status").val("335");
//	$("#ord").val(ord);
//	create_status();
//}


function fn_reject_332(){ //반려
	$("#save_status").val("332");
	fn_reject_rollback();
}






//function fn_save_333(){
//	if($.trim($("#col2").val()) == ""){
//		alert("Comment 를 입력하세요.");
//		return;
//	}
//	
//	if ($.trim($("#col3").val()) == "") {
//		alert("필수입력 날짜를 확인하세요.");
//		$("#col3").focus();
//		return;
//	}
//	
//	if($("#status").val() == ""){
//		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
//		return;
//	}
//
//	var col1 = $("select[name=col1] option:selected").val();
//	if(col1 == ""){
//		alert("당일결과를 입력하세요.");
//		return;
//	}else if(col1 == "불량"){
//		if(confirm("결과가 불량이면 PKG가 반려로 종료됩니다.")) {
//			$("#save_status").val("339");
//		}else{
//			return;
//		}
//	}else{	
//		$("#save_status").val("336");
//	}
//	create_status();
//}





function fn_save_336(){
	if($.trim($("#col2").val()) == ""){
		alert("Comment 를 입력하세요.");
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
	}else{	
//		$("#save_status").val("336");//임시저장
		if($("#select_status").val()  != "" && $("#select_status").val()  == "336"){
			$("#save_status").val("336");
			update_status();
		}else{
			$("#save_status").val("336");
			$("#select_status").val("336");
			create_status();
		}
	}
//	if($("#status").val() == "323"){
//		update_status();
//	}else{
//		
//	}
//	create_status();
//	update_status();
}


function fn_save_337(){//초도 결과 
	if($.trim($("#col2").val()) == ""){
		alert("Comment 를 입력하세요.");
		return;
	}
	
//	if ($.trim($("#col3").val()) == "") {
//		alert("필수입력 날짜를 확인하세요.");
//		$("#col3").focus();
//		return;
//	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}

	var col1 = $("select[name=col1] option:selected").val();
	if(col1 == ""){
		alert("결과를 입력하세요.");
		return;
	}else if(col1 == "불량"){
		if(confirm("결과가 불량이면 PKG가 반려로 종료됩니다.")) {
			$("#save_status").val("329");
		}else{
			return;
		}
	}else{	
		$("#save_status").val("337");
	}
	create_status();
}



function fn_save_339(){//초도 결과 
	if($.trim($("#col2").val()) == ""){
		alert("Comment 를 입력하세요.");
		return;
	}
	
//	if ($.trim($("#col3").val()) == "") {
//		alert("필수입력 날짜를 확인하세요.");
//		$("#col3").focus();
//		return;
//	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}

	var col1 = $("select[name=col1] option:selected").val();
	if(col1 == ""){
		alert("당일결과를 입력하세요.");
		return;
	}else{	
		$("#save_status").val("339");
	}
	create_status();
}









function fn_save_341(){//저장
	$("#save_status").val("341");
	create_status();
}






function fn_save_342(){//승인자 저장
//	$("#save_status").val("342");
//	$("#ord").val(ord);
//	create_status();
//	update_status();
	
	
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
	$("#save_status").val("342");
	create_status();
	
	
	
	
}






function fn_after_342(){
//	var result = true;
//	result = fn_equipment_check();
//	if(!result){
//		return;
//	}
//	
//	if($("#status").val() == ""){
//		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
//		return;
//	}
//	
		$("#select_status").val("342");
		$("#save_status").val("342");
//		create_status();
	
	
	
	var result = true;
	result = fn_equipment_check();
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.");
		return;
	}
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_After_Create.do", "fn_callback_after");
	}else{
		return;
	}
	
	
	
}

function fn_callback_after(data){
	var select_status = $("input[id=param1]").val();

	$("#save_status").val("");
	fn_pkg21_status_read(select_status);
}



//
function fn_save_343(ord){
	if($.trim($("#au_comment").val()) == ""){
		alert("승인 시 comment를 입력하세요.");
		$("#au_comment").focus();
		return;
	}
	$("#save_status").val("343");
	$("#select_status").val("343");
	$("#ord").val(ord);
	create_status();
}


function fn_after_343(ord){
	if($.trim($("#au_comment").val()) == ""){
		alert("승인 시 comment를 입력하세요.");
		$("#au_comment").focus();
		return;
	}
	
	$("#ord").val(ord);
	
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		$("#save_status").val("343");
		$("#select_status").val("343");
//		create_status();
		doSubmit4File("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_After_Update.do", "fn_callback_after");
	}else{
		return;
	}
}




function fn_reject_343(){
	$("#save_status").val("341");
	alert("반려 시 모든 확대일정 및 결과가 삭제됩니다.");
	fn_reject_rollback();
}






function fn_after_eq_e(){
	var result = true;
	result = fn_equipment_check();
	if(!result){
		return;
	}
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.");
		return;
	}
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_After_E_Update.do", "fn_after_e_callback");
	}else{
		return;
	}
}

function fn_after_e_callback(data){
	var select_status = $("input[id=param1]").val();
	
	$("#save_status").val("");
	fn_pkg21_status_read(select_status);
}






function fn_save_350(){
	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	$("#select_status").val("350");
	$("#save_status").val("350");
	create_status();
}



function fn_save_351(){
	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	
	if($("#status").val() == ""){
		alert("PKG검증 상태가 이상합니다.\nSVT계획수립 먼저 진행해 주십시요.");
		return;
	}
	
	$("#select_status").val("351");
	$("#save_status").val("351");
	create_status();
}









function fn_save_351(){
	var result = true;
	result = fn_equipment_final_check();
	if(!result){
		return;
	}
	$("#save_status").val("351");
	create_status();
}







function fn_save_399(){
	var check = confirm("PKG 적용을 완료합니다?");
	if(check){
		$("#save_status").val("399");
//		$("#select_status").val("399");
		create_status();
	}else {
		return;
	}

	
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


















function fn_reject_rollback(){
	if(confirm("이전 단계로 돌아갑니다.\n반려하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_Status_Reject.do", "fn_reject_callback");
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




function create_status(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_Status_Create.do", "fn_callback_status");
	}else{
		return;
	}
}

function update_status(){
	if(confirm("입력한 정보가 저장됩니다.\n저장하시겠습니까?")) {
		doSubmit4File("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_Status_Update.do", "fn_callback_status");
	}else{
		return;
	}
}
	
function fn_callback_status(data){
	var select_status = $("input[id=param1]").val();
	var save_status = $("input[id=param2]").val();
	$("#status").val(save_status);
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
					$("#save_status").val("349");
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






function status_navi(data){
//	console.log($('#pkg_seq').val());
//	console.log(data);
	$('#col43').val(data);//현재 페이지
	var pkg = $('#pkg_seq').val();
	if(pkg != null && pkg !="" && $('#select_status').val() !="300" ){
		doSubmit4File("Pkg21Model", "/pkgmg/wired/Wired_Pkg21_NOW_Status_Navi.do", "fn_status_navi");		
	}else{
		$('#status_navi_now1').css({
			'width': '70%',
			'height':'auto',
			'font-size':'13px',
			'font-weight':'600',
			'color':'#fff',
			'line-height':'20px',
			'text-align':'center',
			'border-radius':'15px',
			'padding':'5px 5px 3px',
			'margin':'16px 0 0 10%',
			'background':'#58a5d6'
		});
		$('#status_navi_now1').css("display", "");
		$('#status_navi_fn1').css("display","none");
		$('#status_navi_non1').css("display","none");
		$('#status_navi_ing1').css("display","none");
	}
}


function fn_status_navi(data){
	
	var now_naviStatus = $("input[id=param1]").val();
	var now_page = $("input[id=param2]").val();
	
//	console.log("====ssss=============");
//	console.log(now_naviStatus);
//	console.log(now_page);
//	console.log("=========eeeee========");
	if( $("#select_status").val() != 300){
		for (var i = 1; i < 8; i++) { // 네비 1-7까지
			//class 초기화 
	//		$('#status_navi_ing'+i).removeClass('');//현재 내 위치 
			if(now_naviStatus == i){// 현재상태 class = on
				$('#status_navi_fn'+i).css("display","");
				$('#status_navi_non'+i).css("display","none");
				$('#status_navi_ing'+i).css("display","none");
				$('#status_navi_now'+i).css("display","none");
			}else if(now_naviStatus < i){// 현재 상태 이후  class = 없음
				$('#status_navi_fn'+i).css("display","none");
				$('#status_navi_non'+i).css("display","");
				$('#status_navi_ing'+i).css("display","none");
				$('#status_navi_now'+i).css("display","none");
			}else if(now_naviStatus > i){ //현재 상태 이전  class = ing
				$('#status_navi_fn'+i).css("display","none");
				$('#status_navi_non'+i).css("display","none");
				$('#status_navi_ing'+i).css("display","");
				$('#status_navi_now'+i).css("display","none");
				
			}
			
			
			
			if(now_naviStatus != i && now_page == i){ // 현재 내가 있는 페이지
	//				$('#status_navi_ing'+i).addClass('on_blue');		
	//				$('#status_navi_ing'+i).removeClass('cursor');
				$('#status_navi_now'+i).css({
					'width': '70%',
					'height':'auto',
					'font-size':'13px',
					'font-weight':'600',
					'color':'#fff',
					'line-height':'20px',
					'text-align':'center',
					'border-radius':'15px',
					'padding':'5px 5px 3px',
					'margin':'16px 0 0 10%',
					'background':'#58a5d6'
				});
				$('#status_navi_now'+i).css("display", "");
				$('#status_navi_fn'+i).css("display","none");
				$('#status_navi_non'+i).css("display","none");
				$('#status_navi_ing'+i).css("display","none");
			}
			
			
		}
	}
	
	
	
}












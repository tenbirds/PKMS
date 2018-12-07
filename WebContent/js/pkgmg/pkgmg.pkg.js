// 초기 화면 Init
function fn_init() {
	// Tab1 저장 여부(필수값 추출)
	var pkg_seq = $("input[id=pkg_seq]").val();
	var tab1 = $("input[id=title]").val();
	var tab2 = $("input[id=master_file_id]").val();
	var tab3 = $("input[id=pkg_detail_count]").val();
	var system_seq = $("input[id=system_seq]").val();
	var status = $("input[id=status]").val();
	
	var prms_04 = $("input[id=pkg_road_map_seq_04]").val();
	var prms_07 = $("input[id=pkg_road_map_seq_07]").val();
	var prms_08 = $("input[id=pkg_road_map_seq_08]").val();
	
	var file37 = $("input[name=file37_button]").val();
	
	var dev_yn = $(':radio[name="dev_yn"]:checked').val();
	$("input[id=dev_yn_bak]").val(dev_yn);
	
	// 입력완료 Tab Class 제어 [상태 _on]
	if (prms_04 != "" || prms_07 != "" || prms_08 != "") { //로드맵
		fn_tab_endding_init(1);
	}
	
	if (tab1 != "") { //기본정보
		fn_tab_endding_init(2);
	}
	if (tab2 != "" && file37 != undefined) { //공급사검증내역 -파일첨부
		fn_tab_endding_init(3);
	}
	
	if (tab3 > 0) { //상용화 검증내역 -상용 엑셀검증
		fn_tab3();
	}
	if (system_seq != "") { //담당자
		fn_tab_endding_init(5);
	}

	//create 와 update를 구분하기 위함
	$("input[id=old_system_seq]").val($("input[id=system_seq]").val());
	
	 /*
		관리자: ADMIN :승인쪽 권환을 가져야함 (승인자+검증자기능 )
		협력업체: BP
		승인자: APPROVER
		검증자: MANAGER
		운용/사업/개발: OPERATOR 
		
		----- 담당직위별 순위도 -------
		승인자 > 관리 = 검증 > BP > 운용
		
	  */
	//alert("GRANTED--->"+GRANTED);
	//tab1, tab2, tab3 버튼 초기화
	if(pkg_seq == "") {
		$("div[id=tab1_comment]").css("display", "block");
		$("div[id=tab2_save]").css("display", "none");
		$("div[id=tab2_comment]").css("display", "block");
		$("div[id=tab3_upload_init]").css("display", "block");
		$("div[id=tab3_upload_update]").css("display", "none");
	} else {
		if(GRANTED == 'MANAGER' || GRANTED == 'BP' || GRANTED == 'ADMIN' || GRANTED == 'APPROVER') {//수정 작성 권한은 BP,검증,관리
			$("div[id=tab1_comment]").css("display", "none");
			$("div[id=tab2_save]").css("display", "block");
			$("div[id=tab2_comment]").css("display", "none");
			$("div[id=tab3_upload_init]").css("display", "none");
			$("div[id=tab3_upload_update]").css("display", "block");
			$("div[id=tab3_3_save]").css("display", "block");
			$("div[id=tab3_4_save]").css("display", "block");

			if( GRANTED == 'BP' ){
				$("span[id=excel_button_none_oknet]").css("display", "none");
				$("span[id=tab3_upload_update_oknet]").css("display", "none"); 
			}
		} else {
			//alert(2);
			$("div[id=tab1_save]").css("display", "none"); 
			$("#open_system_popup").css("display", "none");//입력화면에서 시스템 검색 버튼 안보이게 
		 
			$("div[id=tab1_comment]").css("display", "none");
			$("div[id=tab2_save]").css("display", "none");
			$("div[id=tab2_comment]").css("display", "none");
			
			$("div[id=tab3_upload_init]").css("display", "none");
			$("div[id=tab3_upload_update]").css("display", "block");
			$("span[id=tab3_upload_update_excel]").css("display", "none");
			
			$("span[id=tab3_upload_update_oknet]").css("display", "none");
			$("span[id=excel_button_none_oknet]").css("display", "none");
			
			$("div[id=tab3_3_save]").css("display", "none");
			$("div[id=tab3_4_save]").css("display", "none");
		}
	}

	//tab4 담당자 초기화
	if($("input[id=system_seq]").val() == '') {
		$("div[id=pkg_user_comment]").css("display", "block");
		$("div[id=pkg_user_table]").css("display", "none");
	} else {
		$("div[id=pkg_user_comment]").css("display", "none");
		$("div[id=pkg_user_table]").css("display", "block");
	}

	//tab1의 영향도 관련 레이어 초기화
	fn_ser_yn();
	fn_pe_yn();
	fn_roaming_link();
	
	// pkg_tab_3 레이어 hide
	doDivSH("hide", "pkg_tab_3", 0);

	// 로딩시 기본 On
	fn_tab_first_init();

	// 검증요청버튼 노출 여부
	fn_pkg_1_button();

	
	 
	// 삭제버튼 노출여부
	// 삭제버튼  상태값이 저장중이고 BP,관리,검증자,승인자만 복사버튼 선택 가능 
	// 승인운용은 보이면 안됨
	//협력업체는 상태가 0일때만 삭제가 가능 하고  관리자와 검증자,승인자는 언제나 삭제가 가능하다 
	//alert( "GRANTED---"+GRANTED);
	if ((status == 0 && GRANTED == 'BP') || (GRANTED == 'ADMIN' || GRANTED == 'MANAGER' || GRANTED == 'APPROVER')) {
		 
		if(system_seq != '') {
			doDivSH("show", "pkg_delete_button", 0);
		}
	}
	
	 
	// 복사버튼 노출여부
	// 복사버튼은 상태값이 저장중이고 BP,관리,검증자,승인자만 복사버튼 선택 가능 
	//승인운용은 보이면 안됨
	//복사는 임시저장이 아닌 경우 만 가능하며 
	//'BP'일 경우 반려나 완료일 경우만 복사가 가능하다 
	//운영자를 제외한 나머지 경우는 모두 가능 
	if (status != 0 &&( ((GRANTED == 'BP')&&((status=='99')||(status=='9')) )|| GRANTED == 'ADMIN' || GRANTED == 'MANAGER'|| GRANTED == 'APPROVER')) {
		doDivSH("show", "pkg_copy_button", 0);
	}
	
	// 플로우 status init
	fn_flow_status_init();
	
	fn_pkg_readonly();
}

/*
 * 권한에 따른 제어: 기본정보, 첨부파일
 */
function fn_pkg_readonly() {
	if(GRANTED != 'MANAGER' && GRANTED != 'BP' && GRANTED != 'ADMIN' && GRANTED != 'APPROVER') {//수정 작성 권한은 BP,검증,관리
		_fn_flow_input_readonly($("div[id=pkg_tab_1] :input"));
		_fn_flow_input_readonly($("div[id=pkg_tab_2] :input"));
	}
}
	
// 검증요청버튼 노출 여부
function fn_pkg_1_button() {
	var pkg_seq = $("input[id=pkg_seq]").val();
	var tab1 = $("input[id=title]").val();
	var tab2 = $("input[id=master_file_id]").val();
	var tab3 = $("input[id=pkg_detail_count]").val();
	var tab4 = $("input[id=system_seq]").val();
	var status = $("input[id=status]").val();

	//현제 상태가 '0'이여야 하고필수 값들을 다 입력한 상태인데 검증 ,운영권한 자는 검증 요청 버튼이 나오면 안됨
	//	alert(status + ' - ' + pkg_seq + ' - ' + tab1 + ' - ' + tab2 + ' - ' + tab3 + ' - ' + tab4 + ' - ' + GRANTED);
	if ((status == "0" && pkg_seq != "" && tab1 != "" && tab2 != "" && tab3 > 0 && tab4 != "") && (GRANTED == 'BP' || GRANTED == 'ADMIN' || GRANTED == 'MANAGER'|| GRANTED == 'APPROVER')) {
		doDivSH("show", "pkg_1_button", 0);
	}
}

// 로딩시 기본 On
function fn_tab_first_init() {
	
	var pop_tab1 = $("#pop_tab li:eq(1)");
//	var pop_tab8 = $("#pop_tab li:first");
	if (pop_tab1.hasClass("pop_tab1")) {
		pop_tab1.removeClass("pop_tab1").addClass("pop_tab1_on");
	} else if (pop_tab1.hasClass("pop_tab1_text")) {
		pop_tab1.removeClass("pop_tab1_text").addClass("pop_tab1_text_on");
	}
	$("input[id=pkg_tab_num]").val("tab1");
}

// 입력완료 Tab Class 제어
function fn_tab_endding_init(num) {
	$("#pop_tab li").each(function(i) {
		i++;
		if (num == i) {
			switch (i){ //추가-ksy
				case 1 :
					if ($(this).hasClass("pop_tab8_text")) {
						$(this).attr("class", "pop_tab8");
					}
					else if ($(this).hasClass("pop_tab8_text_on")) {
						$(this).attr("class", "pop_tab8_on");
					}
				case 2 :
					if ($(this).hasClass("pop_tab1_text")) {
						$(this).attr("class", "pop_tab1");
					}
					else if ($(this).hasClass("pop_tab1_text_on")) {
						$(this).attr("class", "pop_tab1_on");
					}
				case 3 :
					if ($(this).hasClass("pop_tab2_text")) {
						$(this).attr("class", "pop_tab2");
					}
					else if ($(this).hasClass("pop_tab2_text_on")) {
						$(this).attr("class", "pop_tab2_on");
					}
				case 4 :
					if ($(this).hasClass("pop_tab3_text")) {
						$(this).attr("class", "pop_tab3");
					}
					else if ($(this).hasClass("pop_tab3_text_on")) {
						$(this).attr("class", "pop_tab3_on");
					}
				case 5 :
					if ($(this).hasClass("pop_tab4_text")) {
						$(this).attr("class", "pop_tab4");
					}
					else if ($(this).hasClass("pop_tab4_text_on")) {
						$(this).attr("class", "pop_tab4_on");
					}
			
			}
				
			/*if ($(this).hasClass("pop_tab" + i + "_text")) {
				$(this).attr("class", "pop_tab" + i);
			}
			if ($(this).hasClass("pop_tab" + i + "_text_on")) {
				$(this).attr("class", "pop_tab" + i + "_on");
			}*/
		}
	});
}

// Tab Click 이벤트 제어
function fn_tab_click() {
	// 선택된 tab 번호조회
	var selected_num = $(this).attr("class").substring(7, 8); 

	if (selected_num == 5) {
		// 임시작성 --> 검증요청
		fn_pkg_1();
	} else if (selected_num == 6) {
		// 삭제
		fn_delete();
	} else if (selected_num == 7) {
		// 복사
		fn_copy();
	} else {
		// Class _on 초기화
		fn_tab_init();
		// Class _on 삽입
		$(this).attr("class", $(this).attr("class") + "_on");
		// 선택된 탭에 대한 레이어 제어
		fn_layer(selected_num);
	}
}

// Class _on 초기화
function fn_tab_init() {
	$("#pop_tab li").each(function(i) {
		i++;
		if (i < $("#pop_tab li").size()) {
			var thisClass = $(this).attr("class");
			var isOn = thisClass.substring(thisClass.length - 2, thisClass.length);

			if (isOn == "on") {
				$(this).attr("class", thisClass.substring(0, thisClass.length - 3));
			}
		}
	});
}

// 선택된 탭에 대한 레이어 분기
function fn_layer(selected_num) {
	$("#pop_tab li").each(function(i) {
		i++;

		if (i < $("#pop_tab li").size()) {
			// 레이어 감추기
			fn_layer_controll("hide", i);
			fn_layer_controll("hide", 8); //추가-ksy
		}
	});
	 
	// 선택된 탭에대한 레이어영역 노출
	fn_layer_controll("show", selected_num);
	
	$("input[id=pkg_tab_num]").val("tab" + selected_num);
}

// 탭별 레이어 제어
function fn_layer_controll(op, selected_num) {
	// Flow layer init
	fn_flow_init(); 
	
	if (op == "show") {
		doDivSH("show", "pkg_tab_" + selected_num, 500);
	} else if (op == "hide") {
		doDivSH("hide", "pkg_tab_" + selected_num, 0);
	}
}

// 서비스영향도 레이어 제어
function fn_ser_yn() {
	var ser_yn = $("select[name=ser_yn] option:selected");
	if (ser_yn.val() == "Y") {
		$("input[id=ser_content]").css("display", "block");
	} else {
		$("input[id=ser_content]").css("display", "none");
	}
}

// 과금영향도 상세내역 레이어 제어
function fn_pe_yn() {
	var pe_yn = $("select[id=pe_yn] option:selected");
	
	if (pe_yn.val() == "Y") {
//		doDivSH("show", "pkg_tab_1_1", 200);
		doDivSH("hide", "pe_yn_tip");
		doDivSH("hide", "pe_yn_mark");
		$("input[id=pe_yn_comment]").css("display", "block");
		
	} else {
//		doDivSH("hide", "pkg_tab_1_1", 0);
		doDivSH("show", "pe_yn_tip");
		doDivSH("show", "pe_yn_mark");
		$("input[id=pe_yn_comment]").css("display", "none");
	}

	var status = $("input[id=status]").val();
	if(status != "0") {
		$("select[id=pe_yn]").find('option').each(function() {
			if($("select[id=pe_yn]").val() != $(this).val()){
				$(this).remove();				
			}   
		  });
//		_fn_flow_input_readonly($("div[id=pkg_tab_1_1] :input"));
	} else {
		if(GRANTED == 'MANAGER' || GRANTED == 'BP' || GRANTED == 'ADMIN' || GRANTED == 'APPROVER') {//수정 작성 권한은 BP,검증,관리
//			doCalendar("pe_test_date");
		}
	}
}

//로밍영향도 레이어 제어
function fn_roaming_link() {
	var roaming_link = $("select[name=roaming_link] option:selected");
	if (roaming_link.val() != "N") {
		$("input[id=roaming_link_comment]").css("display", "block");
	} else {
		$("input[id=roaming_link_comment]").css("display", "none");
	}
}

// 등록/상세/수정 화면
function fn_read(gubun, pkg_seq) {
	var retUrl = $("input[id=retUrl]");

	if (gubun == "read") {
		retUrl.val("/pkgmg/pkg/Pkg_Popup_Read");
	} else if (gubun == "view") {
		retUrl.val("/pkgmg/pkg/Pkg_Popup_View");
	} else {
		alert("you must have param selected read or view");
		return;
	}

	$("input[id=pkg_seq]").val(pkg_seq);

	var form = $("form[id=PkgModel]");
	form.action = "/pkgmg/pkg/Pkg_Popup_Read.do";
	form.submit();
}

// 부모창 reload
function fn_opener_reload() {
	// opener가 페이지를 유지 할 경우에만 실행
	try {
		if (opener != null) {
			if (opener.top != null) {
				opener.top.fn_reload();
			} else {
				opener.fn_reload();
			}
		}
	} catch(e) {
//d		alert(e);
	}
}

// PKG Msg
function fn_msg() {
	var status = $("input[id=status]").val();
	var selected_status = $("input[id=selected_status]").val();
	//alert(selected_status);
	var status_operation = $("input[id=status_operation]").val();

	//alert(status_operation);
	if (status_operation == "U") {
		msg = "저장 되었습니다.";
	} else {
		if (status == "0") {
			msg = "임시저장 되었습니다.\n모든 탭 저장 후 검증요청이 가능합니다.";
		} else if (status == "1") {
			msg = "검증요청 되었습니다.\n관련 담당자들에게 메일이 발송되었습니다.\n검증센터 이용 시 '게시판>검증센터이용'에서 신청하세요.";
		} else if (status == "2") {
			var pe_yn = $("select[id=pe_yn]").val();
			/*if(pe_yn == 'Y') {
				msg = "검증접수 되었습니다.\n다음단계는 \"검증계획요청\"입니다.\n[단, 과금접수(처리 중)인 경우 과금 접수가 완료되어야만 검증완료를 진행하실 수 있습니다.]";
			} else {*/
				msg = "검증접수 되었습니다.\n다음단계는 \"검증계획요청\"입니다.";
//			}
		} else if (status == "3") {
			msg = "검증완료 되었습니다.\n다음단계는 \"초도일정수립\"입니다.";
		} else if (status == "4") {
			msg = "초도일정이 수립 되었습니다.\n다음단계는 \"초도승인요청\"입니다.";
		} else if (status == "5") {
			if (selected_status == "5") {
				msg = "초도적용 승인요청 되었습니다.\n다음단계는 \"초도적용승인\"입니다.";
			} else if (selected_status == "6") {
				msg = "승인 되었습니다.";
			}
		} else if (status == "6") {
			msg = "초도적용승인 되었습니다.";
		} else if (status == "7") {
			msg = "초도적용이 완료 되었습니다.\n다음단계는 \"확대일정수립\"입니다.";
		} else if (status == "8") {
//			if($("#part_save").val() == "E"){
//				msg = "저장 되었습니다.";
//			} else {
			msg = "확대일정이 수립 되었습니다.\n다음단계는 \"확대적용완료\"입니다.";
//			}
		} else if (status == "9") {
			msg = "확대적용 결과가 등록 되었습니다.\nPKG검증이 \"완료\"되었습니다.";
		} else if (status == "99") {
			
			if (selected_status=="7"){
				msg = "원복 요청하였습니다";
			} else {
				msg = "반려되었습니다.";
			}
			
		} else {
			msg = "저장 되었습니다.";
		}
	}

	return msg;
}

// Tab1 PKG 검증요청 기본정보 저장
function fn_tab1() {
	var dev_yn = $(':radio[name="dev_yn"]:checked').val();
	$("input[id=dev_yn_bak]").val(dev_yn);
	
	if ($.trim($("input[id=system_seq]").val()) == "") {
		alert("시스템을 선택하세요.");
		return;
	}else{
		$("input[id=system_seq]").val($.trim($("input[id=system_seq]").val()));
	} 
	
	if ($.trim($("input[id=title]").val()) == "") {
		alert("제목을 입력하세요.");
		$("input[id=title]").focus();
		return;
	}else{
		$("input[id=title]").val($.trim($("input[id=title]").val()));
	}
	
	if ($.trim($("input[id=ver]").val()) == "") {
		alert("PKG 버전을 입력하세요.");
		$("input[id=ver]").focus();
		return;
	}else{
		$("input[id=ver]").val($.trim($("input[id=ver]").val()));
	}

	if ($("textarea[name=content]").val().length > 550) {
		alert("주요 보완내역은\n최대 [550 자]까지 입력 가능합니다.");
		return;
	}
	
	if ($.trim($("textarea[name=content]").val()) == "") {
		alert("주요 보완내역을 입력하세요.");
		return;
	}
	
	if(txtflg){
		alert("주요 보완내역을 입력하세요.");
		return;
	}
	
	// Save
	if($("input[id=pkg_seq]").val() == "") {
		fn_tab1_create();
	} else {
		$("input[id=status_operation]").val("U");
		fn_tab1_update();	
	}
}

//Tab1 기본정보 Create
function fn_tab1_create() {
	doSubmit4File("PkgModel", "/pkgmg/pkg/PkgTab1_Create.do", "fn_callback_create", fn_msg());
}

//Tab1 기본정보 Update
function fn_tab1_update() {
	doSubmit4File("PkgModel", "/pkgmg/pkg/PkgTab1_Update.do", "fn_callback_update", fn_msg());
}

//Tab8 PKG 검증요청 기본정보 저장
function fn_tab8() {
	var s_day_04 = $("input[id=start_date_04]").val();
	var s_day_07 = $("input[id=start_date_07]").val();
	var s_day_08 = $("input[id=start_date_08]").val();
	
	var e_day_04 = $("input[id=end_date_04]").val();
	var e_day_07 = $("input[id=end_date_07]").val();
	var e_day_08 = $("input[id=end_date_08]").val();
	
	var comment_04 = $("input[id=comment_04]").val();
	var comment_07 = $("input[id=comment_07]").val();
	var comment_08 = $("input[id=comment_08]").val();

	var comment_cnt = 0;
	var se_day_cnt = 0;
	
	//날짜 하나만 입력 검증
	if ($("input[id=pkg_road_map_seq_04]").val() == "") {
		if (s_day_04 != "" && e_day_04 == "") {			
			se_day_cnt++;
		}
		if (e_day_04 != "" && s_day_04 == "") {			
			se_day_cnt++;
		}
	}
	if ($("input[id=pkg_road_map_seq_07]").val() == "") {
		if (s_day_07 != "" && e_day_07 == "") {			
			se_day_cnt++;
		}
		if (e_day_07 != "" && s_day_07 == "") {			
			se_day_cnt++;
		}
	}
	if ($("input[id=pkg_road_map_seq_08]").val() == "") {
		if (s_day_08 != "" && e_day_08 == "") {			
			se_day_cnt++;
		}
		if (e_day_08 != "" && s_day_08 == "") {			
			se_day_cnt++;
		}
	}

	//내용 입력 검증
	if(s_day_04 != "" && comment_04 == ""){
		comment_cnt++;
	}
	if(s_day_07 != "" && comment_07 == ""){
		comment_cnt++;
	}
	if(s_day_08 != "" && comment_08 == ""){
		comment_cnt++;
	}
	
	if(se_day_cnt > 0){
		alert("날짜를 선택완료하세요.");
		return;
	}
	
	if(comment_cnt > 0){
		alert("내용을 입력하세요.");
		return;
	}
	// Save
	$("input[id=status_operation]").val("U");
	doSubmit4File("PkgModel", "/pkgmg/pkg/PkgRoadMap_Update.do", "fn_callback_update", fn_msg());	
	
}

//Tab2 첨부파일 검증요청 기본정보 저장
function fn_tab2() {
	var file37 = $("input[name=file37]").val();
	var file37_delete = $("input[id=file37_delete]").val();

	var file5 = $("input[name=file5]").val();
	var file5_delete = $("input[id=file5_delete]").val();
	
	var file8 = $("input[name=file8]").val();
	var file8_delete = $("input[id=file8_delete]").val();
	
	var file68 = $("input[name=file68]").val();
	var file68_delete = $("input[id=file68_delete]").val();
	if (file37 == "" && (file37_delete == "Y" || file37_delete == undefined)) {
		alert("보완내역별 시험 결과는 필수입니다.");
		return;
	}
	if (file5 == "" && (file5_delete == "Y" || file5_delete == undefined)) {
		alert("Regression Test 및 기본 검증 결과는 필수입니다.");
		return;
	}
	
	if (file68 == "" && (file68_delete == "Y" || file68_delete == undefined)) {
		alert("보안Guide 적용확인서 결과는 필수입니다.");
		return;
	}
	
	if ($("#pkg_detail_count").val() > 0) {
		$("#urgency_yn").val("N");
	}
	
	// Save
//	$("input[id=pkg_seq]").val() == "" ? fn_tab2_create() : fn_tab2_update();
	fn_tab2_update();
}

//Tab3 보완적용내역 - 상용
function fn_tab3() {
	fn_tab_endding_init(4);
	fn_layer_controll("show", "3_2");
	
	// 보완적용내역 개수 Set
	var pkg_detail_count = $("input[id=pkg_detail_count]").val();
	$("span[id=pkg_detail_count_view]").html(pkg_detail_count);
	if(pkg_detail_count > 0) {
		$("div[id=excel_button_exist]").css("display", "block");
		$("div[id=excel_button_none]").css("display", "none");
		
		$("div[id=pop_result_none]").css("display", "none");
		$("div[id=pop_result]").css("display", "block");
	} else {
		$("div[id=excel_button_exist]").css("display", "none");
		$("div[id=excel_button_none]").css("display", "block");
		
		$("div[id=pop_result_none]").css("display", "block");
		$("div[id=pop_result]").css("display", "none");
	}
	
	// 보완적용내역 목록 Table
	doTable("pkg_tab_3_2_table", "tbl_type4", "1", "0", ['50', '40', '50', '50', '314', '170', '250', '60', '60'], "N");
}

//?????뭐지 아무데도 없어
function fn_callback_detail_variable_update_after2(data) {
	$("div[id=pkg_tab_3_2]").html(data);
	
}

//Tab2 첨부파일 Update
function fn_tab2_update() {
	$("input[id=status_operation]").val("U");

	doSubmit4File("PkgModel", "/pkgmg/pkg/PkgTab2_Update.do", "fn_callback_update", fn_msg());

}
//Create Callback 
function fn_callback_create() {
	// 등록/상세/수정 화면
//	alert($("input[id=param1]").val());
	fn_read("read", $("input[id=param1]").val());

	// 부모창 reload
	fn_opener_reload();
}

//Update Callback 
function fn_callback_update() {
	if($("input[id=system_seq]").val() == $("input[id=old_system_seq]").val()) {
		// 선택된 tab endding 처리
//		var selected_number = $("input[id=pkg_tab_num]").val().substring(3, 4);

		if ($("input[id=pkg_tab_num]").val() == "tab2") {
			doSubmit("PkgModel", "/pkgmg/pkg/PkgTab2_Ajax_Read.do", "fn_callback_tab2_update");
			fn_tab_endding_init(3);
			
		//PkgSupplement_Popup_ReadList 파일에서 save 후 호출되는 부분
		} else if ($("input[id=pkg_tab_num]").val() == "tab3") {
//			fn_layer_controll("show", "3_2");
//			fn_layer_controll("hide", "3_3");
//			fn_layer_controll("hide", "3_4");
//			doSubmit4File("PkgModel", "/pkgmg/pkg/PkgDetailVariable_Ajax_Read.do", "fn_callback_tab3_update");
			fn_read("read", $("input[id=pkg_seq]").val());
		}else if($("input[id=pkg_tab_num]").val() == "tab8"){
			$("input[id=pkg_road_map_seq_04]").val($("input[id=param1]").val());
			$("input[id=pkg_road_map_seq_07]").val($("input[id=param2]").val());
			$("input[id=pkg_road_map_seq_08]").val($("input[id=param3]").val());
			$("input[id=road_map_seq_04]").val($("input[id=param4]").val());
			$("input[id=road_map_seq_07]").val($("input[id=param5]").val());
			$("input[id=road_map_seq_08]").val($("input[id=param6]").val());
			fn_tab_endding_init(1);
		}else if($("input[id=pkg_tab_num]").val() == "tab1"){
			fn_read("read", $("input[id=pkg_seq]").val());
		}
		
		
		// 검증요청버튼 노출 여부
		fn_pkg_1_button();
		
//		fn_read("read", $("input[id=pkg_seq]").val());
	} else {
		fn_read("read", $("input[id=pkg_seq]").val());
	}
	
	// 부모창 reload
	fn_opener_reload();
}

// 첨부파일 화면 재조회
function fn_callback_tab2_update(data) {
	$("#pkg_tab_2_data").html(data);
	doCalendarName("col27");
	doCalendarName("col28");
	// 검증요청버튼 노출 여부
	fn_pkg_1_button();
}

// 보완적용내역 화면 재조회
function fn_callback_tab3_update(data) {
	// 등록/상세/수정 화면
	$("input[id=pkg_detail_count]").val($("input[id=pkg_detail_count_hidden]").val());
	$("input[id=tempmgModelListSize]").val($("input[id=tempmgModelListSize_hidden]").val());
	$("font[id=apply_tpl_ver]").html("[적용된 템플릿 버전: " + $("input[id=tpl_ver]").val() + "]");

	$("div[id=pkg_tab_3_2]").html(data);

	fn_tab3();
	
	doDivSH("show", "pkg_tab_3", 0);

	// 검증요청버튼 노출 여부
	fn_pkg_1_button();
}

// 템플릿 다운로드(템플릿 작성)
function fn_template_download() {
	
	//alert($("input[id=useY_tpl_ver]").val());
	var tempVer = $("input[id=useY_tpl_ver]").val();
	if(ltrim(tempVer)==""){
		alert("사용가능한 템플릿이 등록되어 있지 않습니다.\n 기본정보 입력 후 다시 다운로드 해보시기 바랍니다.");
		return;
	}
	  
	if(confirm("현재 사용 가능 템플릿 버전은 [" + $("input[id=useY_tpl_ver]").val() + "] 입니다.\n다운로드 하시겠습니까?")) {
		doSubmit("PkgModel", "/pkgmg/pkg/PkgSupplement_Template_ExcelDownload.do", "fn_callback_file_download_new");
	}
}

// 보완적용내역 다운로드
function fn_excel_download() {
	if(confirm("적용 템플릿 버전은 [" + $("input[id=tpl_ver]").val() + "] 입니다.\n다운로드 하시겠습니까?")) {
		doSubmit("PkgModel", "/pkgmg/pkg/PkgSupplement_ExcelDownload.do", "fn_callback_file_download");
	}
}

//템플릿 다운로드(템플릿 다운로드)
function fn_callback_file_download(data) {
	var file_name = $("input[id=param1]").val();
	downloadFile(file_name, file_name, "");
}

// 템플릿 다운로드(템플릿 다운로드)
function fn_callback_file_download_new(data) {
	var file_name = $("input[id=param1]").val();
	var sp_file_name = file_name.split("|");
	var sp_val_file_name = sp_file_name[0];
	var sp_val_file_org_name = sp_file_name[1];
	var sp_val_file_path = sp_file_name[2];

	downloadFile(sp_val_file_name, sp_val_file_org_name, sp_val_file_path);
}

// 보완적용내역 new/pn/cr 매핑 이미지 초기화
function fn_detail_init(new_pn_cr_seq, no) {
//	if(new_pn_cr_seq==""){
//		$("td[class*=td_detail_no_"+no+"]").css("background-color", "#FFDEA9");
//	}else{
//		$("td[class*=td_detail_no_"+no+"]").css("background-color", "#ffffff");
//	}
	$(".td_no2").css("background-color", "#FFDEA9");
	$(".td_no3").css("background-color", "#ffffff");
}

// 보완적용내역 가변데이터 이미지 초기화
function fn_detail_variable_init() {
	$("td[class*=td_detail_variable_]").css("background-color", "#ffffff");
}

// 보완적용내역 new/pn/cr 매핑 데이터 조회
function fn_newpncr_click(pkg_detail_seq, no, new_pn_cr_gubun, new_pn_cr_seq) {
	// 보완적용내역 new/pn/cr 매핑 이미지 초기화
	fn_detail_init(new_pn_cr_seq, no);

	// 보완적용내역 가변데이터 td 초기화
	fn_detail_variable_init();

	// 선택 td 변경
	$(".td_detail_no_" + no).css("background-color", "orange");
	
	// 선택된 new/pn/cr set
	if (no == "") {
		
	} else {
		$("input[id=pkg_detail_seq]").val(pkg_detail_seq);
		$("input[id=new_pn_cr_seq]").val(new_pn_cr_seq);
		$("select[id=newPnCr]").val(new_pn_cr_gubun);
		fn_newpncr_readList(no);
	}
	
	$("span[id=newpncr_detail_no]").html("<span style='text-color:blue'>보완적용내역 No:</span> " + no);
	$("span[id=newpncr_system]").html($("input[id=system_name]").val());
	
	doDivSH("show", "pkg_tab_3_3", 500);
	doDivSH("hide", "pkg_tab_3_4", 500);
}

//NewPnCr 목록 조회
function fn_newpncr_readList(no) {
	$("input[id=td_no]").val(no);
	doSubmit("PkgModel", "/pkgmg/pkg/PkgDetailNewPnCr_Ajax_ReadList.do", "fn_callback_PkgDetailNewPnCr_Ajax_ReadList");
}

//NewPnCr 목록 조회 callback
function fn_callback_PkgDetailNewPnCr_Ajax_ReadList(data) {
	$("#newpncr_list_area").html(data);
}

//NewPnCr 매핑
function fn_newpncr_update() {
	$("input[id=td_no]").val();
	var newPnCr = $(':radio[name="newpncr_radio"]:checked').val();
	
	if(newPnCr == undefined){
		alert("보안적용 내용을 선택 하십시요");
		return;
	}
	
	var arr = newPnCr.split("_");
	var seq = arr[0];
	var no = arr[1];
	
	$("input[id=new_pn_cr_seq]").val(seq);
	$("input[id=new_pn_cr_no]").val(no);
//	$("input[id=new_pn_cr_seq]").val($(':radio[name="newpncr_radio"]:checked').val());
	doSubmit("PkgModel", "/pkgmg/pkg/PkgDetailNewpncr_Update.do", "fn_callback_Pkg_Detail_Newpncr_Update", "적용되었습니다.");
}

//NewPnCr 매핑 callback
function fn_callback_Pkg_Detail_Newpncr_Update() {
	var td_no = $("input[id=td_no]").val();
	$("td[class*=td_detail_no_"+td_no+"]").css("background-color", "#ffffff");
	$("td[class*=td_detail_no_"+td_no+"]").removeClass("td_no2");
	$("td[class*=td_detail_no_"+td_no+"]").addClass("td_no3");
	
	$("td[id=td_detail_no_" + $("input[id=param1]").val() + "]").html($("input[id=new_pn_cr_seq]").val());
	$("input[id=new_pn_cr_seq]").val("");
	$("input[id=new_pn_cr_no]").val("");
}
	
// 보완적용내역 가변데이터 조회
function fn_detail_variable_click(pkg_detail_seq) {

	$("#detail_variable_add").val('');
	// 보완적용내역 new/pn/cr 매핑 td 초기화
	fn_detail_init();

	// 보완적용내역 가변데이터 td 초기화
	fn_detail_variable_init();
	
	// 상세정보 수정을 위한 key set
	$("input[id=pkg_detail_seq]").val(pkg_detail_seq);

	// 선택 td 변경
	$(".td_detail_variable_" + pkg_detail_seq + "_1").css("background-color", "orange");
	
	fn_detail_variable_read();
}

//보완적용내역 가변데이터 조회
function fn_detail_variable_click_dev(pkg_detail_seq) {
	$("#detail_variable_add").val('');

	// 보완적용내역 가변데이터 td 초기화
	fn_detail_variable_init();
	
	// 상세정보 수정을 위한 key set
	$("input[id=pkg_detail_seq]").val(pkg_detail_seq);

	// 선택 td 변경
	$(".td_detail_variable_" + pkg_detail_seq + "_1"+"_dev").css("background-color", "orange");
	
	fn_detail_variable_read_dev();
	
}

function fn_detail_add_variable_click() {
	$("#detail_variable_add").val('add');
	doSubmit("PkgModel", "/pkgmg/pkg/PkgDetailVariable_Ajax_WriteList.do", "fn_callback_detail_variable_read");
}

// 상용 검증 결과 라디오버튼 체크
function fn_detail_radio(name, truefalse){
	$("#ok").val("");
	$("#nok").val("");
	$("#cok").val("");
	$("#resultHTML").html('');
//	if(gubun ==1){
		if(truefalse == "ok"){
			$("#"+name+"_ok").attr("checked", true);
			$("#"+name+"_nok").attr("checked",false);
			$("#"+name+"_cok").attr("checked",false);
		}
		else if(truefalse == "nok"){
			$("#"+name+"_ok").attr("checked", false);
			$("#"+name+"_nok").attr("checked",true);
			$("#"+name+"_cok").attr("checked",false);
		}
		else{
			$("#"+name+"_ok").attr("checked", false);
			$("#"+name+"_nok").attr("checked",false);
			$("#"+name+"_cok").attr("checked",true);	
		}
//	}else{
//		if(truefalse == "ok"){
//			$("#"+name+"_ok2").attr("checked", true);
//			$("#"+name+"_nok2").attr("checked",false);
//		}else{
//			$("#"+name+"_ok2").attr("checked", false);
//			$("#"+name+"_nok2").attr("checked", true);	
//		}
	}
//}

//개발 검증 결과 라디오버튼 체크
function fn_detail_radio_dev(name, truefalse){
	$("#ok_dev").val("");
	$("#nok_dev").val("");
	$("#cok_dev").val("");
	$("#resultHTML").html('');
	
	if(truefalse == "ok"){
		$("#"+name+"_ok_dev").attr("checked", true);
		$("#"+name+"_nok_dev").attr("checked",false);
		$("#"+name+"_cok_dev").attr("checked",false);
		$("#"+name+"_bypass_dev").attr("checked",false);
	}else if(truefalse == "nok"){
		$("#"+name+"_ok_dev").attr("checked", false);
		$("#"+name+"_nok_dev").attr("checked",true);
		$("#"+name+"_cok_dev").attr("checked",false);
		$("#"+name+"_bypass_dev").attr("checked",false);
	}else if(truefalse == "cok"){
		$("#"+name+"_ok_dev").attr("checked", false);
		$("#"+name+"_nok_dev").attr("checked",false);
		$("#"+name+"_cok_dev").attr("checked",true);
		$("#"+name+"_bypass_dev").attr("checked",false);
	}else{
		$("#"+name+"_ok_dev").attr("checked", false);
		$("#"+name+"_nok_dev").attr("checked",false);
		$("#"+name+"_cok_dev").attr("checked",false);
		$("#"+name+"_bypass_dev").attr("checked",true);
	}
}

// 상용 검증 결과 라디오버튼 저장
function fn_radio_update(){
	$("#ok").val("");
	$("#nok").val("");
	$("#cok").val("");
	var	ok = "";
	var	nok ="";
	var cok = "";
	  $(".radio_OK:checked").each(function(pi,po){
	    ok += ","+po.value;
	  });
	  $(".radio_NOK:checked").each(function(pi2,po2){
		    nok += ","+po2.value;
		  });  
	  $(".radio_COK:checked").each(function(pi3,po3){
		    cok += ","+po3.value;
		  });  
	 if(ok!="")ok = ok.substring(1);
	 if(nok!="")nok = nok.substring(1);
	 if(cok!="")cok = cok.substring(1);
	 
	 $("#ok").val(ok);
	 $("#nok").val(nok);
	 $("#cok").val(cok);

	 //개발
	$("#ok_dev").val("");
	$("#nok_dev").val("");
	$("#cok_dev").val("");
	$("#bypass_dev").val("");
	var	ok_dev = "";
	var	nok_dev ="";
	var cok_dev = "";
	var bypass_dev = "";
	  $(".radio_OK_dev:checked").each(function(pi,po){
	    ok_dev += ","+po.value;
	  });
	  $(".radio_NOK_dev:checked").each(function(pi2,po2){
		    nok_dev += ","+po2.value;
		  });  
	  $(".radio_COK_dev:checked").each(function(pi3,po3){
		    cok_dev += ","+po3.value;
		  });
	  $(".radio_BYPASS_dev:checked").each(function(pi4,po4){
		    bypass_dev += ","+po4.value;
		  });
	 if(ok_dev!="")ok_dev = ok_dev.substring(1);
	 if(nok_dev!="")nok_dev = nok_dev.substring(1);
	 if(cok_dev!="")cok_dev = cok_dev.substring(1);
	 if(bypass_dev!="")bypass_dev = bypass_dev.substring(1);
	 
	 $("#ok_dev").val(ok_dev);
	 $("#nok_dev").val(nok_dev);
	 $("#cok_dev").val(cok_dev);
	 $("#bypass_dev").val(bypass_dev);
	 doSubmit("PkgModel", "/pkgmg/pkg/PkgDetailVariable_OkNok_Update.do", "fn_callback_detail_variable_update", "저장되었습니다.");
}

//보완적용내역 가변데이터 조회
function fn_detail_variable_read() {
	doSubmit("PkgModel", "/pkgmg/pkg/PkgDetailVariable_Ajax_ReadList.do", "fn_callback_detail_variable_read");
}

//보완적용내역 가변데이터 조회 callback
function fn_callback_detail_variable_read(data) {
	$("div[id=pkg_tab_3_2_div]").html(data);
	
	if(GRANTED != 'MANAGER' && GRANTED != 'BP' && GRANTED != 'ADMIN' && GRANTED != 'APPROVER') {//수정 작성 권한은 BP,검증,관리
		_fn_flow_input_readonly($("table[id=detail_variable_ajax_read] :input"));
	}

	doDivSH("hide", "pkg_tab_3_3", 500);
	doDivSH("show", "pkg_tab_3_4", 500);
}

//보완적용내역 가변데이터 조회
function fn_detail_variable_read_dev() {
	doSubmit("PkgModel", "/pkgmg/pkg/PkgDetailVariable_Ajax_ReadList_Dev.do", "fn_callback_detail_variable_read_dev");
}

//보완적용내역 가변데이터 수정
function fn_detail_variable_update() {
	if($("#detail_variable_add").val() != 'add'){
	doSubmit("PkgModel", "/pkgmg/pkg/PkgDetailVariable_Update.do", "fn_callback_detail_variable_update", "저장되었습니다.");
	}else{
	doSubmit("PkgModel", "/pkgmg/pkg/PkgDetailVariable_Insert.do", "fn_callback_detail_variable_update", "추가되었습니다.");
	
	}
}

//보완적용내역 가변데이터 수정 Callback
function fn_callback_detail_variable_update() {
//	fn_read("read", $("input[id=pkg_seq]").val());
	doSubmit2("PkgModel", "/pkgmg/pkg/PkgDetailVariable_Ajax_Read.do", "fn_callback_detail_variable_update_after");
}

//보완적용내역 가변데이터 수정 Callback 후 처리 
function fn_callback_detail_variable_update_after(data) {
	$("div[id=pkg_tab_3_2]").html(data);
	fn_tab3();
}

// Flow Click 이벤트 제어
function fn_flow_click(e) {
	if($(this).css("cursor") == 'pointer') {
		// 선택된 flow 번호 read
		var selected_id = $(this).attr("id");
		var selected_num = selected_id.substring(8, selected_id.length);
//			alert(selected_num);
		var selstatus = parseInt(selected_num);
		
		if(selected_num > 30 && selected_num < 35){
			fn_flow_init(selected_num);
			if(selected_num == 31){
				fn_readVerifyTem('vol')
			}else if(selected_num == 32){
//				fn_readVerifyTem('sec')
				fn_readSecurity();
			}else if(selected_num == 33){
				fn_readVerifyTem('cha')
			}else if(selected_num == 34){
				fn_readVerifyTem('non')
			}
		}else{
			if(selected_num == "1") {
				/*var pe_yn = $("select[id=pe_yn]").val();
				if(pe_yn == "Y") {
					fn_owm_link();
					return;
				}*/
				
			} else if(selected_num == "10") {
				var status = $("input[id=status]").val();
				
				if(status == "9") {
					// 완료보고
				} else {
					return;
				}
			}
			
			// 선택된 flow 번호의 status Set
			$("input[id=selected_status]").val(selected_num);
			
			// Flow layer init
			fn_flow_init(selected_num);
			
			var display = $("div[id=pkg_tab_flow_" + selected_num + "]").css("display");
	
			if (display != "block") {
				// 선택된 status 정보 read를 위한 ajax 호출
				doSubmit("PkgModel", "/pkgmg/pkg/PkgStatus_Ajax_Read.do", "fn_callback_status_read");
			} else {
				doDivSH("slideToggle", "pkg_tab_flow_" + selected_num, 100);
			}
		}
	}
}

function fn_owm_link() {
	
	/*var html = "";
	html += "<form name='PkgModel' target='PKG_OWMS_IFRAME'>";
	html += "<input type='hidden' name='pk_seq' value='121'";
	html += "</form>";*/
	
	var form = document.getElementById('PkgModel');
	form.target="PKG_OWMS_IFRAME";
	form.action="/pkgmg/pkg/Owms_Ajax_Read.do";
	form.method="POST";
	form.submit();
	
//	$("#PKG_OWMS_IFRAME").attr("src", "/pkgmg/pkg/Owms_Ajax_Read.do");
//	alert($("#PKG_OWMS_IFRAME").contents().find("#owms_form").html());
	
//	doSubmit("PkgModel", "/pkgmg/pkg/Owms_Ajax_Read.do", "fn_callback_owms_read");
}

function fn_callback_owms_read(data) {
//	$("iframe[id=PKG_OWMS_IFRAME]").html(data);
}

//선택된 status 정보 read Callback
function fn_callback_status_read(data) {
	
	// 선택된 flow 번호 Set
	var selected_status = $("input[id=selected_status]").val();

	//name 중복되면 array로 되기 때문에 전부 초기화 후 해당 div만 세팅하기 위함 
	for (var i = 2; i < 10; i++) {
		$("div[id=pkg_tab_flow_" + i + "]").html("");
	}

	// Data Set
	$("div[id=pkg_tab_flow_" + selected_status + "]").html(data);

	// 선택된 flow toggle
	doDivSH("slideToggle", "pkg_tab_flow_" + selected_status, 500);
	
	fn_flow_tb();
	
	var detailvariable_check = $("#detailvariable_check").val();
	if(selected_status == "3" || selected_status == "22"){
		if(detailvariable_check == "N"){
			alert("보완적용내역중 상세내역안의 상용검증결과에 OK/COK 중 하나를 입력해야 검증완료 진행이 가능합니다.");
			var pop_tab3 = $("#pop_tab li:eq(3)");
			// Class _on 초기화
			fn_tab_init();
			// Class _on 삽입
			pop_tab3.attr("class", pop_tab3.attr("class") + "_on");
			// 선택된 탭에 대한 레이어 제어
			fn_layer(3);
		}
	}
}

// Flow layer init
function fn_flow_init(selected_num) {

/*	$("#pkg_tab_flow div").each(function(i) {
		++i;
		if(i== 11){
			i = 21;
		}
		if (i != selected_num) {
			doDivSH("slideUp", "pkg_tab_flow_" + i, 100);
		}
	});*/
		
	for(var i = 1; i < 35; i++){
		if (i != selected_num) {
			doDivSH("slideUp", "pkg_tab_flow_" + i, 100);
		}
	}
	
//	var display = $("div[id=pkg_tab_flow_" + selected_num + "]").css("display");
//
//	if (display == "block") {
//		fn_read("read", $("input[id=pkg_seq]").val());
//	}	
}

// Flow 상태값에 따른 버튼 제어 init
function fn_flow_status_init() {
//	var status = $("input[id=selected_status]").val() == "" ? parseInt($("input[id=status]").val(), 10) : parseInt($("input[id=selected_status]").val(), 10);

	var selected_id;
	var cur_num;
	var int_cur_num;
	var suffix;
	var cursor;
	var turn_down = false;
	var org_status = $("input[id=status]").val();
	var status = parseInt(org_status);
	var max_status = parseInt($("input[id=max_status]").val());
	var pe_yn = $("select[id=pe_yn]").val();
	var pe_status = $("input[id=pe_status]").val();
	
	var dev_yn = $(':radio[name="dev_yn"]:checked').val();
	
	var org_status_dev = $("input[id=status_dev]").val();
	var status_dev = parseInt(org_status_dev);
	
	var vol_yn = $("input[id=vol_yn]").val();
	var sec_yn = $("input[id=sec_yn]").val();
	var cha_yn = $("input[id=cha_yn]").val();
	var non_yn = $("input[id=non_yn]").val();
	
	/*
	 * 	 pkg에서사용되는 코드값
	 *
	 	0	임시작성
		1	요청중
		2	검증접수
		3	검증완료
		4	초도일정수립
		5	초도승인요청
		6	초도승인완료
		7	초도적용완료
		8	확대일정수립
		9	확대적용완료
		99	반려 
	 
 */
//	alert(org_status+"-----"+status+"-----"+max_status+"-----"+pe_yn+"-----"+pe_status+"-----"+GRANTED );
	//반려
	if(status == 99) {
		turn_down = true;
		status = max_status;
		
	//정상
	} else {
//		alert(status + " ... " + pe_yn);
//		alert("status = "+status);
		if(status == 0) {
			;
		}else if(status == 2){
			status = 26;
		}else if(status == 26){
			status = 3;
		}else if(dev_yn == "N" || dev_yn == "D"){
			status = status + 1;
//		}else if(status == 1 && pe_yn == "Y") {
		}else if(status == 1) {
			//추가 및 변경-ksy
			if(status==1){
				status = 21; 
			}else if(status==21){
				status = 22;
			}else if(status==22){
				status = 23;
			}else if(status==23){
				status = 24;
			}else if(status==24){
				status = 2;
			}else{
				status = status + 1;
			}
		}else {
			//현재상태 + 1 = 처리해야 하는 상태
			//검증접수부터 시작하기 때문에(요청중은 이미 처리 된 것으로 판단)
			//추가 및 변경-ksy
			if(status==1){
				status = 21; 
			}else if(status==21){
				status = 22;
			}else if(status==22){
				status = 23;
			}else if(status==23){
				status = 24;
			}else if(status==24){
				status = 2;
			}else{
				status = status + 1;
			}
		}
//		alert("status_dev = "+status_dev);
		//개발상용 동시 진행
		if(dev_yn == "D"){
			if(status_dev==1){
				status_dev = 21; 
			}else if(status_dev==21){
				status_dev = 22;
			}else if(status_dev==22){
				status_dev = 23;
			}else if(status_dev==23){
				status_dev = 24;
			}else if(status_dev==24){
				status_dev = 2;
			}else{
				status_dev = status_dev + 1;
			}
		}
	}//end if 반려
	
//	status = 23;
	$("#flow_ul li").each(function(i, obj) {
		++i;
		
		selected_id = $(this).attr("id");
		cur_num = selected_id.substring(8, selected_id.length);
		int_cur_num = parseInt(cur_num);
		suffix = "_off";
		cursor = "default";
		
		//추가된 21~24 flow
		if(dev_yn == "Y" && int_cur_num > 20 && int_cur_num < 25){ /** 과금관련은 추후 추가해줘야함*/ 
			
			if(status == 26){ //무조건 지났을 상태
				cursor = "pointer";
				suffix = "_on";
			}else if(status > 1 && status < 11){ //무조건 지났을 상태
				cursor = "pointer";
				suffix = "_on";
			}else{
				if(status > int_cur_num){//지난상태
					cursor = "pointer";
					suffix = "_on";
				}else if(status == int_cur_num){//현상태
					suffix = "_now";
					if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER' || GRANTED == 'OPERATOR') {
						cursor = "pointer";
					} else {
						cursor = "default";
					}
				}else{//현상태 이후
					suffix = "_off";
					cursor = "default";
				}
			}
		}
		 //기존 2~10 flow
		else {
			if(status > 20 && status < 25){ /** 과금관련은 추후 추가해줘야함*/ 
				if(int_cur_num ==1){
					/*if(pe_yn == "Y") {
						cursor = "pointer";
						suffix = "_" + pe_status + "_now";
					}else{*/
						cursor = "default";
						suffix = "_on";
//					}
				}else{
					suffix = "_off";
					cursor = "default";
				}
			}else{ //기존조건들
				//지난 상태들
				if (status > int_cur_num) {
					if(status == 26){
						if(int_cur_num == 1){
							/*if(pe_yn == "Y") {
								//과금 완료/반려
								//if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
								cursor = "pointer";
								//} else {
								//	cursor = "pointer";
								if(pe_status == "3" || pe_status == "9") {
									suffix = "_" + pe_status + "_on";
								} else {
									if(status == 3) {
										//과금완료/반려가 아닌 경우 검증 접수까지만 진행 가능하도록 하고 과금 완료까지 대기 상태가 되어야 하기 때문
										if(pe_status != "3" && pe_status != "9") {
											suffix = "_" + pe_status + "_now";
										}
									} else {
										suffix = "_" + pe_status + "_now";
									}
								}
							} else {*/
								cursor = "default";
								suffix = "_on";
//							}
						}else if(int_cur_num == 2){
							cursor = "pointer";
							suffix = "_on";
						}else{
							suffix = "_off";
							cursor = "default";
						}
					} else if(int_cur_num == 1) {
						//요청중이 과금 영향이 있는 경우
						/*if(pe_yn == "Y") {
							//과금 완료/반려
							//if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
							cursor = "pointer";
							//} else {
							//	cursor = "pointer";
							if(pe_status == "3" || pe_status == "9") {
								suffix = "_" + pe_status + "_on";
							} else {
								if(status == 3) {
									//과금완료/반려가 아닌 경우 검증 접수까지만 진행 가능하도록 하고 과금 완료까지 대기 상태가 되어야 하기 때문
									if(pe_status != "3" && pe_status != "9") {
										suffix = "_" + pe_status + "_now";
									}
								} else {
									suffix = "_" + pe_status + "_now";
								}
							}
						} else {*/
							cursor = "default";
							suffix = "_on";
//						}				
					} else {
						cursor = "pointer";
						suffix = "_on";
					}
					//현 상태
				} else if (status == int_cur_num) {
					//반려
					if(turn_down) {
						cur_num = 99;
						suffix = "_on";
						cursor = "pointer";
					} else {
						// 임시작성
						if (status == 0) {
							suffix = "_off";
							cursor = "default";
						}else if(status == 26){ 
							/*if(pe_yn == "Y") {
								suffix = "_now";
								if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
									cursor = "pointer";
								} else {
									cursor = "default";
								}
								//과금완료/반려가 아닌 경우 검증 접수까지만 진행 가능하도록 하고 과금 완료까지 대기 상태가 되어야 하기 때문
								if(pe_status != "3" && pe_status != "9") {
									
								} else {
									suffix = "_now";
									//alert(pe_status+"---------"+pe_status);
									if(pe_status=='9'){
										suffix = "_off";
									}
								}
							} else {*/
								suffix = "_now";
								if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
									cursor = "pointer";
								} else {
									cursor = "default";
								}
//							}
						//검증완료
						}else if(status == 3) {
							/*if(pe_yn == "Y") {
								if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
									cursor = "pointer";
								} else {
									cursor = "default";
								}
								//과금완료/반려가 아닌 경우 검증 접수까지만 진행 가능하도록 하고 과금 완료까지 대기 상태가 되어야 하기 때문
								if(pe_status != "3" && pe_status != "9") {
							
								} else {
									suffix = "_now";
									//alert(pe_status+"---------"+pe_status);
									if(pe_status=='9'){
										suffix = "_off";
									}
								}
							} else {*/
								suffix = "_now";
								if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
									cursor = "pointer";
								} else {
									cursor = "default";
								}
//							}
							//초도승인
						} else if(status == 6) { 
							suffix = "_now";
							cursor = "pointer";
							
							//완료
						} else if(status == 10) {
							if(org_status == "9") {
								suffix = "_on";
								cursor = "pointer";
							} else {
								suffix = "_on";
								cursor = "default";
							}
						} else {
							// 요청중
							if(status == 1) {
								/*if(pe_yn == "Y") {
									if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
										cursor = "pointer";
									} else {
										cursor = "default";
									}
									//과금 완료/반려
									if(pe_status == "3" || pe_status == "9") {
										suffix = "_" + pe_status + "_on";
									} else {//과금접수/처리중
										suffix = "_" + pe_status + "_now";
									}
								} else {*/
									suffix = "_off";
									cursor = "default";
//								}
							} else if(status == 2) {
								/*if(pe_yn == "Y") {
									if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
										cursor = "pointer";
									} else {
										cursor = "default";
									}
									//과금 반려
									if(pe_status == "9") {
										suffix = "_off";
										cursor = "default";
									} else {//과금접수/처리중
										suffix = "_now";
									}
								} else {*/
									suffix = "_now";
									if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
										cursor = "pointer";
									} else {
										cursor = "default";
									}
//								}
							} else {
								suffix = "_now";
								if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER' || GRANTED == 'OPERATOR') {
									cursor = "pointer";
								} else {
									cursor = "default";
								}
							}
						}
					}
				} else { //status < int_cur_num
					if(int_cur_num == 2) {
//						if(status != 0 && pe_yn == "Y") {
						if(status != 0) {
							if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
								cursor = "pointer";
							} else {
								cursor = "default";
							}
							//과금 완료/반려
							if(pe_status == "3") {
								//검증접수 단계는 
								suffix = "_now";
							} else {
								//반려 이외에는 검증접수까지는 진행이 가능하도록
								if(pe_status != "9") {
									suffix = "_now";
								}
							}
						} else {
							suffix = "_off";
							cursor = "default";
						}
					}else if(int_cur_num == 26){
						if(status > 2 && status < 11){
							suffix = "_on";
							if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
								cursor = "pointer";
							} else {
								cursor = "default";
							}
						} else {
							suffix = "_off";
							cursor = "default";
						}
					} else {
						suffix = "_off";
						cursor = "default";
					}
				}
			}
		}
		
		//개발상용 동시 진행
		//int_cur_num = 해당 버튼 번호
		//status = 상태값
		//status_dev = 동시진행 상태값(동시진행 할 때, 개발의 상태
		if(dev_yn == "D" && int_cur_num > 20 && int_cur_num < 25){ /** 과금관련은 추후 추가해줘야함*/ 
			if(status_dev > 1 && status_dev < 11){ //무조건 지났을 상태
				cursor = "pointer";
				suffix = "_on";
			}else{
				if(status_dev > int_cur_num){//지난상태
					cursor = "pointer";
					suffix = "_on";
				}else if(status_dev == int_cur_num){//현상태
					suffix = "_now";
					if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER' || GRANTED == 'OPERATOR') {
						cursor = "pointer";
					} else {
						cursor = "default";
					}
				}else{//현상태 이후
					suffix = "_off";
					cursor = "default";
				}
			}
		}
		
		//30이상은 용량,보안,과금,비기능 검증
		if(int_cur_num > 30){
			if(int_cur_num == 31){			
				if(vol_yn == "Y"){
					suffix = "_now";
					if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER' || GRANTED == 'OPERATOR') {
						cursor = "pointer";
					} else {
						cursor = "default";
					}
				}else if(vol_yn == "S"){
					suffix = "_on";
					cursor = "pointer";
				}else{
					suffix = "_off";
					cursor = "default";
				}
			}else if(int_cur_num == 32){				
				if(sec_yn == "Y"){
					suffix = "_now";
					if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER' || GRANTED == 'OPERATOR') {
						cursor = "pointer";
					} else {
						cursor = "default";
					}
				}else if(sec_yn == "S"){
					suffix = "_on";
					cursor = "pointer";
				}else{
					suffix = "_off";
					cursor = "default";
				}
			}else if(int_cur_num == 33){				
				if(cha_yn == "Y"){
					suffix = "_now";
					if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER' || GRANTED == 'OPERATOR') {
						cursor = "pointer";
					} else {
						cursor = "default";
					}
				}else if(cha_yn == "S"){
					suffix = "_on";
					cursor = "pointer";
				}else{
					suffix = "_off";
					cursor = "default";
				}
			}else { //34				
				if(non_yn == "Y"){
					suffix = "_now";
					if(GRANTED == 'MANAGER'||GRANTED == 'ADMIN'|| GRANTED == 'APPROVER' || GRANTED == 'OPERATOR') {
						cursor = "pointer";
					} else {
						cursor = "default";
					}
				}else if(non_yn == "S"){
					suffix = "_on";
					cursor = "pointer";
				}else{
					suffix = "_off";
					cursor = "default";
				}
			}
		}
				
//		alert("pop_flow" + cur_num + suffix);
		$(this).attr("class", "pop_flow" + cur_num + suffix);
		$(this).attr("style", "cursor:" + cursor);
		
	});
}

//상태별 클릭 시
function fn_flow_tb() {
	
	var no;
	var otherNo;
	var status = $("input[id=status]").val();
	var intStatus = parseInt(status);
	var selected_status = $("input[id=selected_status]").val();
	var intSelected_status = parseInt(selected_status);
	var max_status = $("input[id=max_status]").val();

	//검증접수, 초도승인
	if(selected_status == "6" || selected_status == "21" || selected_status == "24") {
		//반려
		if(status == "99"){
			if(selected_status == max_status) {
				no = 2;
			} else {
				no = 1;
			}
		} else {
			no = $(':radio[name="check_flow_tb"]:checked').val();
		}
		
		if(no == 1) {
			otherNo = 2;
		} else {
			otherNo = 1;
		}
		
//	alert("no: " + no + " -- otherNo: " + otherNo);
		
		if(status == "99"){
			//반려를 했던 상태인 경우(예: 검증접수에서 반려를 한 경우 - 검증접수이면)
			if(selected_status == max_status) {
				$("input:radio[name=check_flow_tb]:input[value=" + no + "]").attr("checked", true);
				$("input:radio[name=check_flow_tb]:input[value=" + otherNo + "]").attr("disabled", "disabled");
			} else {
				$("input:radio[name=check_flow_tb]:input[value=" + no + "]").attr("checked", true);
				$("input:radio[name=check_flow_tb]:input[value=" + otherNo + "]").attr("disabled", "disabled");
			}
			
		} else if(status == "9"){//완료 
			$("input:radio[name=check_flow_tb]:input[value=" + no + "]").attr("checked", true);
			$("input:radio[name=check_flow_tb]:input[value=" + otherNo + "]").attr("disabled", "disabled");
		}
		
		if (no == 2 && selected_status == "6") {
			_fn_flow_save_display("block");
//			doDivSH("show", "flow_6_save_button", 0);
		}
		if (no == 1 && selected_status == "6") {
			_fn_flow_save_display("none");
//			doDivSH("hide", "flow_6_save_button", 0);
		}
		
		if (no == 2 && selected_status == "24") {
			_fn_flow_save_display("block");
//			doDivSH("show", "flow_6_save_button", 0);
		}
		if (no == 1 && selected_status == "24") {
			_fn_flow_save_display("none");
//			doDivSH("hide", "flow_6_save_button", 0);
		}
		
		doDivSH("hide", "flow_tb_" + otherNo, 0);
		doDivSH("show", "flow_tb_" + no, 0);
	}
	
	//달력관련
	if(status == "99") {
		;
	} else {
		//검증권한이 있는 사용자에게만 수정이 가능해야 함
		if(GRANTED == 'MANAGER' || GRANTED == 'ADMIN'|| GRANTED == 'APPROVER') {
			// 각 상태별 제어
			if (selected_status == "21") {
				doCalendar("pkgStatusModel_col1");
			}else if(selected_status == "22"){
				doCalendar("pkgStatusModel_col27");
				doCalendar("pkgStatusModel_col28");
			}else if (selected_status == "2") {
				doCalendar("pkgStatusModel_col9");
				doCalendar("pkgStatusModel_col10");
				
				doCalendar("pkgStatusModel_col11");
				doCalendar("pkgStatusModel_col12");
				
				doCalendar("pkgStatusModel_col13");
				doCalendar("pkgStatusModel_col14");
				
				doCalendar("pkgStatusModel_col15");
				doCalendar("pkgStatusModel_col16");
				
				doCalendar("pkgStatusModel_col17");
				doCalendar("pkgStatusModel_col18");
			} else if (selected_status == "3") {
				// col1,2 달력 Init
				doCalendarName("pkgStatusModel.col1");
				doCalendarName("pkgStatusModel.col2");
			}
		}
	}

	//alert("selected_status---->"+selected_status);
	//일정수립관련
	if (selected_status == "4" || selected_status == "8" || selected_status == "9" ) {
		var check_seqs = $("input[name='check_seqs']");
		//alert(GRANTED+"<----selected_status--->"+selected_status);
		if(selected_status == "4" || selected_status == "8"){
			doCalendar("PkgModel_pkgEquipmentModel_work_date_All");	
		}
		for(var i = 0; i < check_seqs.size(); i++) {
			if(GRANTED == 'MANAGER' || GRANTED == 'ADMIN'|| GRANTED == 'APPROVER' || GRANTED == 'OPERATOR') {
				doCalendar("PkgModel_pkgEquipmentModel_work_date_" + i);
			}
			
			if(check_seqs.eq(i).is(":checked")) { 
				doDivSH("hide", "equipment_se_none_" + (i + 1), 0);
				
				doDivSH("show", "equipment_se_date_" + (i + 1), 0);
				doDivSH("show", "check_ok_" + (i + 1), 0);
				doDivSH("show", "PkgModel_pkgEquipmentModel_ampm_" + (i + 1), 0);
			}
		}
		
	} else if ((selected_status == "3" || selected_status == "7" || selected_status == "9")) {
		if(selected_status == "3" && intStatus >= intSelected_status && intStatus != 26){
			_fn_flow_save_display("none");
		}
		/*
		 * 검증결과, 초도결과, 확대결과는 각 항목 중 하나라도 불량이면 반려로 처리하기 때문에
		 * 지나면 양호/불량은 수정 불가로 해야 함
		 * --06-22 
		 * 기존 상태변경을 막기 위해 상태 선택 을 disabled 시켰는데 
		 * 해당 select box 에서 불량을 배고 양호 만 나오게 하기로 함
		 */
		
		/*
		if(intStatus >= intSelected_status){ 
			_fn_flow_select_readonly($("div[id=flow_tb_1] :input"));
		}
		*/
		/*if(intStatus >= intSelected_status){ 
			var obj = $("div[id=flow_tb_1] :input");
			 
			for(var i = 0; i < obj.size(); i++) {
				
				if(obj[i].type == 'select-one') { 
					
					var temp = $("#"+obj[i].id+" option:last").val();
					
					if( temp.indexOf("불량")>=0 ){
//						$("#"+obj[i].id+" option:last").remove();
					}
					
				}
			}
		}*/
		
		
	}
	

	/*
	 * 관리자: ADMIN :승인쪽 권환을 가져야함 (승인자+검증자기능 )
		협력업체: BP
		승인자: APPROVER
		검증자: MANAGER
		운용/사업/개발: OPERATOR 
		완료나 반려 이거나 상관없이 무조건 수정이 가능 상태를 제외한 모든것들
	 */
	 
    //검증자와 관리자는 오른쪽 플로워에 대한 수정을 할수 있으나 스텝6일 경우는 승인자만 수정이 가능 합니다
//	alert(GRANTED);
	var readOnlyFlag = true;

	if(selected_status == "6"){
		//승인자만 승인이 가능 해야 합니다 
		if(GRANTED == 'APPROVER') {
			//현재 승인자인 경우에만 라디오버튼(반려) 활성
			if($("input[id=active_enabled]").val() == 'Y') {
				readOnlyFlag = false;
			} else {
				readOnlyFlag = true;
			}
		} else {
			readOnlyFlag = true;
		}
	} else {
		if((GRANTED == 'MANAGER') || (GRANTED == 'ADMIN') || (GRANTED == 'APPROVER') || (GRANTED == 'OPERATOR')) {
			readOnlyFlag = false;
		} else {
			readOnlyFlag = true;
		}
		//운용자에게 확대일정 수립 수정 가능
		if(selected_status == 8 && GRANTED == 'OPERATOR'){
			readOnlyFlag = false;
		}
		
	}
	/*
	 * 
	 */
	
//    alert(intStatus+"-------------"+intSelected_status);
	if((selected_status=="5")&&(intStatus >= intSelected_status)){
		readOnlyFlag = true;
	}
	
	//alert(readOnlyFlag+"---GRANTED--->"+GRANTED+"-----"+selected_status+"-----"+intStatus+"----"+max_status+"----------"+((intStatus>5)&&(selected_status==4)));
 /*
  * 초도장비 와 확대장비 등록 관련 입니다 
  * 초도장비는 초도장비 승인이 완료되면 수정이 불가능 해야하고 (선택한 탭이 4이고 현재 상태가 5보다 클경우)
  * 확대 적용은 확대적용요청 계획이 수립되면 수정이 불가능 합니다 (선택 탭이 확대적용계획 수립이고 현재 상태가 결과 등록일 경우 )
  */
//	 alert(intStatus+"-------------"+selected_status);
//	if(((intStatus>5)&&(selected_status==4))||((intStatus>8)&&(selected_status==8))){
//20130409 5에서 6으로 바꿈
	if(((intStatus>6)&&(selected_status==4))||((intStatus>8)&&(selected_status==8))||((intStatus>6)&&(selected_status==7))
			||((intStatus>8)&&(selected_status==9))){	 
		var check_seqs = $("input[name='check_seqs']");
		//alert(GRANTED+"<----selected_status--->"+selected_status);
		for(var i = 0; i < check_seqs.size(); i++) {
			  
			if(check_seqs.eq(i).is(":checked")) { 
				//달력 이미지 버튼 숨기기 
				$("div[id="+"equipment_se_date_" + (i + 1)+"]").find("img:first").hide();
				
				
				//레이어않에 있는 input  항목들을 가져와 해당 항목들을 disabled 합니다 
				//readonly 할 경우  날짜 항목을 클릭시 달력이 생성됩니다 
				var obj = $("div[id="+"equipment_se_date_" + (i + 1)+"] :input"); 
				
				if(obj.length>0){ 
					for(var j=0; j<obj.length; j++){
						obj[j].disabled = true;
						
					} 
				}
			}
		}
		_fn_flow_input_readonly($("input[name=allCheckbox]")); // 체크박스를 수정 못하게 합니다 
		_fn_flow_input_readonly($("input[name=check_seqs]")); // 체크박스를 수정 못하게 합니다 
		
		_fn_flow_save_display("none");//저장 버튼을 숨깁니다
	
	}
	
	if(selected_status=="26" && intStatus != 25 && intStatus != 26){
		readOnlyFlag = true;
	}

	if(selected_status=="2"&& intStatus >= 2 && intStatus != 24){
		readOnlyFlag = true;
	}
	
	
	if(readOnlyFlag) {//readonly 합니다

		_fn_flow_save_display("none");
		
		if($("input[name=check_flow_tb]") != undefined) {
			_fn_flow_input_readonly($("input[name=check_flow_tb]"));
		}
		
		if($("div[id=flow_tb_1]") != undefined) {
			var flow_tb_1_display = $("div[id=flow_tb_1]").css("display"); 
			
			if(flow_tb_1_display == 'block') {
				_fn_flow_input_readonly($("div[id=flow_tb_1] :input"));
			}
		} 
		
		if($("div[id=flow_tb_2]") != undefined) {
			var flow_tb_2_display = $("div[id=flow_tb_2]").css("display");
			
			if(flow_tb_2_display == 'block') {
				_fn_flow_input_readonly($("div[id=flow_tb_2] :input"));
			}
		}
	} else { // 수정 가능 합니다 
		//지난 상태에서 반려는 불가능함.
		var intStatus = parseInt(status);
		var intSelected_status = parseInt(selected_status);
		//alert(intStatus+"------"+intSelected_status);
		
		if(intStatus == 24){
			if(intSelected_status == 2){
				intStatus=0;
			}
		}
		
		if(intStatus >= intSelected_status) {
			_fn_flow_input_readonly($("input[name=check_flow_tb]"));
		}
	}
	
}//end function 

/*
 * 읽기 전용으로 바뀌면 해당 화면애 저장 버튼을 숨깁
 */
function _fn_flow_save_display(display) {
	var divs = $("div");
	for(var i=0; i < divs.size(); i++){  
		if(divs.eq(i).attr("class") == "pop_btn_location3"){
			divs.eq(i).css("display", display);
		} 
	}
}
	
/*
 * 읽기 전용
 */
function _fn_flow_input_readonly(obj) {
//	alert(obj.size());
	for(var i = 0; i < obj.size(); i++) {

		if(obj[i].type == 'checkbox' || obj[i].type == 'radio' || obj[i].type == 'select-one' || obj[i].type == 'button' || obj[i].type == 'file') {
			obj[i].disabled = true;
		} else {
			obj[i].readOnly = true;
		}
//		alert(obj[i].type + " : " + obj[i].id + " : " + obj[i].name + " : " + (obj[i]).readOnly + " - " + (obj[i]).disabled);
	}
}

/*
 * 읽기 전용
 */
function _fn_flow_select_readonly(obj) {
//	alert(obj.size());
	for(var i = 0; i < obj.size(); i++) {
		
		if(obj[i].type == 'select-one') {
			obj[i].disabled = true;
		}
	}
}

//Pkg Delete
function fn_delete() {
	if (confirm("삭제 후 데이터 복구는 불가능합니다. 삭제하시겠습니까?")) {
		doSubmit4File("PkgModel", "/pkgmg/pkg/Pkg_Delete.do", "fn_callback_delete");
	}
}

//Pkg copy
function fn_copy() {
	if (confirm("정보를 복사하여 새로운 검증 요청 건을 생성합니다. 복사하시겠습니까?")) {
		doSubmit4File("PkgModel", "/pkgmg/pkg/PkgCopy_Create.do", "fn_callback_create", "복사되었습니다.\n복사한 화면으로 이동합니다. 내용을 변경하여 진행하십시요.");
	}
}

//Pkg Delete callback
function fn_callback_delete() {
	// 부모창 reload
	fn_opener_reload();
	
	self.close();
}

//Pkg 검증요청
function fn_requst_update(selected_status) {
	$("input[id=selected_status]").val(selected_status);

	doSubmit4File("PkgModel", "/pkgmg/pkg/Pkg_Update.do", "fn_callback_request_update", "");
}

//Pkg 상태값 create
function fn_pkgstatus_create(selected_status) {
	
	$("input[id=selected_status]").val(selected_status);
	doSubmit4File("PkgModel", "/pkgmg/pkg/PkgStatus_Create.do", "fn_callback_pkgstatus_create", "");

}

//Pkg 상태값 callback
function fn_callback_request_update() {
	 
	$("input[id=status_operation]").val("");
	// 검증요청버튼 노출 여부
	fn_pkg_1_button();

	$("input[id=status]").val($("input[id=param1]").val());
//	$("input[id=status_crud_callback]").val($("input[id=param1]").val());

	// 부모창 reload
	fn_opener_reload();
	
	alert(fn_msg());

	var selected_status = $("input[id=selected_status]").val();

	// 임시저장 -> 요청중 상태 변환 시 close
	if (selected_status == "1") {
		//alert("selected_status: " + selected_status);
		// 창닫기
		self.close();
	}
}

//Pkg 상태값 callback
function fn_callback_pkgstatus_create() {
	var selected_status = $("input[id=selected_status]").val();
	
	doDivSH("slideToggle", "pkg_tab_flow_" + selected_status, 100);
	
	// 검증요청버튼 노출 여부
	fn_pkg_1_button();
	
	var isReload = false;
	if($("input[id=status]").val() != $("input[id=param1]").val()) {
		isReload = true;
	}
	
	$("input[id=status]").val($("input[id=param1]").val());
	$("input[id=max_status]").val($("input[id=param2]").val());
	
	if(selected_status != '2' && selected_status != '26' && $("input[id=turn_down]").val() != 'true' && selected_status != '7'){		
		alert(fn_msg());
	}
	
//	alert($("input[id=status]").val());
	// Flow 상태값 변경에 따른 버튼 제어 init
	fn_flow_status_init();
	
	if (isReload) {
		// 부모창 reload
		fn_opener_reload();
	}
	if(selected_status == "3" || selected_status == "22"){
		$("#col1").val($("#pkgStatusModel_col1").val());
		$("#col2").val($("#pkgStatusModel_col2").val());
		$("#col3").val($("#pkgStatusModel_col3").val());
		$("#col4").val($("#pkgStatusModel_col4").val());
		$("#col5").val($("#pkgStatusModel_col5").val());
		$("#col6").val($("#pkgStatusModel_col6").val());
		$("#col7").val($("#pkgStatusModel_col7").val());
		$("#col8").val($("#pkgStatusModel_col8").val());
		$("#col9").val($("#pkgStatusModel_col9").val());
		$("#col10").val($("#pkgStatusModel_col10").val());
		$("#col11").val($("#pkgStatusModel_col11").val());
		$("#col12").val($("#pkgStatusModel_col12").val());
		$("#col13").val($("#pkgStatusModel_col13").val());
		$("#col14").val($("#pkgStatusModel_col14").val());
		$("#col15").val($("#pkgStatusModel_col15").val());
		$("#col16").val($("#pkgStatusModel_col16").val());
		$("#col17").val($("#pkgStatusModel_col17").val());
		$("#col18").val($("#pkgStatusModel_col18").val());
		$("#col19").val($("#pkgStatusModel_col19").val());
		$("#col20").val($("#pkgStatusModel_col20").val());
		$("#col21").val($("#pkgStatusModel_col21").val());
		$("#col22").val($("#pkgStatusModel_col22").val());
		$("#col23").val($("#pkgStatusModel_col23").val());
		$("#col24").val($("#pkgStatusModel_col24").val());
		$("#col25").val($("#pkgStatusModel_col25").val());
		$("#col26").val($("#pkgStatusModel_col26").val());
		
		$("#col27").val($("#pkgStatusModel_col27").val());
		$("#col28").val($("#pkgStatusModel_col28").val());
		$("#col29").val($("#pkgStatusModel_col29").val());
		$("#col30").val($("#pkgStatusModel_col30").val());
		$("#col31").val($("#pkgStatusModel_col31").val());
		$("#col32").val($("#pkgStatusModel_col32").val());
		
		$("#col33").val($("#pkgStatusModel_col33").val());
		$("#col34").val($("#pkgStatusModel_col34").val());
		$("#col35").val($("#pkgStatusModel_col35").val());
		$("#col36").val($("#pkgStatusModel_col36").val());
		$("#col37").val($("#pkgStatusModel_col37").val());
		$("#col38").val($("#pkgStatusModel_col38").val());
		
		$("#col39").val($("#pkgStatusModel_col39").val());
		$("#col40").val($("#pkgStatusModel_col40").val());
		$("#col41").val($("#pkgStatusModel_col41").val());
		$("#col42").val($("#pkgStatusModel_col42").val());
	}
	fn_read("read", $("input[id=pkg_seq]").val());
}

//Pkg 상태값 반려
function fn_pkgstatus_update(selected_status) {
	$("input[id=selected_status]").val(selected_status);
	
	doSubmit4File("PkgModel", "/pkgmg/pkg/PkgStatus_Create.do", "fn_callback_pkgstatus_create", "");
}

//검증접수
function fn_pkg_21(){
	var turn_down = fn_check_turn_down();
	
	var status = $("input[id=status]");
	var status_dev = $("input[id=status_dev]");
	var operation = $("input[id=status_operation]");
	 
	var col1 = $("input[id=pkgStatusModel_col1]");
	var col2 = $("textarea[id=pkgStatusModel_col2]");
	var col20 = $("textarea[id=pkgStatusModel_col20]");

	if(turn_down) {
		if (col20.val() == "") {
			alert("반려 사유는 필수항목입니다.");
			return;
		}
	} else {  
		if (col1.val() == "") {
			alert("검증일자를 선택하세요.");
			return;
		}
		if (col2.val() == "") {
			alert("요청사항을 입력하세요.");
			col2.focus();
			return;
		} 
	}
	
	var dev_yn = $(':radio[name="dev_yn"]:checked').val();
	
	if(status.val() > "1"){
		if(dev_yn == 'D'){
			if(status_dev.val() == "1"){
				operation.val("C");
			}else {
				operation.val("U");
			}
		}
	}else if(status.val() == "1") {
		operation.val("C");
	} else {
		operation.val("U");
	}
	
	fn_set_turn_down(turn_down);
	if(operation.val()=="C"){		
		if(confirm("개발/상용, 상용검증 항목을 확인하세요.\n검증요청 하시겠습니까?")) {
			fn_pkgstatus_create("21");
		}
	}else{		
		fn_pkgstatus_create("21");
	}
}

//검증요청
function fn_pkg_1(){
	var pkg_seq = $("input[id=pkg_seq]").val();
	var tab1 = $("input[id=title]").val();
	var tab2 = $("input[id=master_file_id]").val();
	var tab3 = $("input[id=pkg_detail_count]").val();
	var tab4 = $("input[id=system_seq]").val();
	var status = $("input[id=status]").val();

	if (status != "0") {
		alert("임시작성 상태가 아닙니다. 관리자에게 문의 하세요.");
		return;
	}

	if (pkg_seq != "" && tab1 != "" && tab2 != "" && tab3 > 0 && tab4 != "") {
		// Pkg 상태값: 임시작성 -> 요청중 Set
		if(confirm("개발/상용, 상용검증 항목을 확인하세요.\n검증요청 하시겠습니까?")) {
			fn_requst_update("1");
		}
	} else {
		alert("입력되지 않은 정보가 있습니다.");
		return;
	}
}

function fn_pkg_1_urgency(){
	var dev_yn = $(':radio[name="dev_yn"]:checked').val();
	var msg = "긴급검증요청 하시겠습니까?";
	if(dev_yn == "D"){
		msg = "긴급 검증 요청 시 개발검증과 상용검증 접수가 동시에 진행 됩니다. 계속 하시겠습니까?";
		$("#dev_yn_bak").val("D");
	}else{
		msg = "긴급검증요청 하시겠습니까?";
	}
	if(confirm(msg)) {
		$("#urgency_yn").val("Y");
		fn_requst_update("1");
	}
}

function textClick_pkg2(obj){
	obj.value = "";
}

//검증접수
function fn_pkg_2(str){
	//기능 검증 내용
	$("input[id=on_yn]").val("Z");
	var on_yn = $("input[id=on_yn]").val();
	var on_start = $("input[id=pkgStatusModel_col17]").val();
	var on_end = $("input[id=pkgStatusModel_col18]").val();
	var on_text = $("textarea[id=pkgStatusModel_col21]").val();
	
	//비기능검증 & 내용
	var non_yn = $("input[id=non_yn]").val();
	var non_start = $("input[id=pkgStatusModel_col15]").val();
	var non_end = $("input[id=pkgStatusModel_col16]").val();
	var non_text = $("textarea[id=pkgStatusModel_col4]").val();
	var non_mi = $("textarea[id=pkgStatusModel_col8]").val();
	
	//용량검증 & 내용
	var vol_yn = $("input[id=vol_yn]").val();
	var vol_start = $("input[id=pkgStatusModel_col9]").val();
	var vol_end = $("input[id=pkgStatusModel_col10]").val();
	var vol_text = $("textarea[id=pkgStatusModel_col1]").val();
	var vol_mi = $("textarea[id=pkgStatusModel_col5]").val();
	
	//과금검증 & 내용
	var cha_yn = $("input[id=cha_yn]").val();
	var cha_start = $("input[id=pkgStatusModel_col13]").val();
	var cha_end = $("input[id=pkgStatusModel_col14]").val();
	var cha_text = $("textarea[id=pkgStatusModel_col3]").val();
	var cha_mi = $("textarea[id=pkgStatusModel_col7]").val();
	
	//보안검증 & 내용
	var sec_yn = $("input[id=sec_yn]").val();
	var sec_start = $("input[id=pkgStatusModel_col11]").val();
	var sec_end = $("input[id=pkgStatusModel_col12]").val();
	var sec_text = $("textarea[id=pkgStatusModel_col2]").val();
	var sec_mi = $("textarea[id=pkgStatusModel_col6]").val();
	
	if(str == 'save'){
		$("input[id=pkgStatusModel_col22]").val("save");
		
	}else if(str == 'complete'){
		$("input[id=pkgStatusModel_col22]").val("complete");
		
		if(on_yn == 'Z'){
			if(on_start =='' || on_end == ''){
				alert("기능검증 일정입력은 필수입니다.");
				return;
			}
			if(on_text == ''){
				alert("기능검증의 계획은 필수입니다.");
				return;
			}
		}
		
		if(non_yn == 'Z'){
			if(non_start =='' || non_end == ''){
				alert("검증필요 시 비기능검증 일정입력은 필수입니다.");
				return;
			}
			if(non_text == ''){
				alert("검증필요 시 비기능검증 계획은 필수입니다.");
				return;
			}
		}else if(non_yn == 'N'){
			if(non_mi == ''){
				alert("검증 불필요 시 비기능 미검증 사유는 필수입니다.");
				return;
			}
		}
		
		if(vol_yn == 'Z'){
			if(vol_start =='' || vol_end == ''){
				alert("검증필요 시 용량검증 일정입력은 필수입니다.");
				return;
			}
			if(vol_text == ''){
				alert("검증필요 시 용량검증 계획은 필수입니다.");
				return;
			}
		}else if(vol_yn == 'N'){
			if(vol_mi == ''){
				alert("검증 불필요 시 용량 미검증 사유는 필수입니다.");
				return;
			}
		}
		
		if(cha_yn == 'Z'){
			if(cha_start =='' || cha_end == ''){
				alert("검증필요 시 과금검증 일정입력은 필수입니다.");
				return;
			}
			if(cha_text == ''){
				alert("검증필요 시 과금검증 계획은 필수입니다.");
				return;
			}
		}else if(cha_yn == 'N'){
			if(cha_mi == ''){
				alert("검증 불필요 시 과금 미검증 사유는 필수입니다.");
				return;
			}
		}
		
		if(sec_yn == 'Z'){
			if(sec_start =='' || sec_end == ''){
				alert("검증필요 시 보안검증 일정입력은 필수입니다.");
				return;
			}
			if(sec_text == ''){
				alert("검증필요 시 보안검증 계획은 필수입니다.");
				return;
			}
		}else if(sec_yn == 'N'){
			if(sec_mi == ''){
				alert("검증 불필요 시 보안 미검증 사유는 필수입니다.");
				return;
			}
		}
	}
	
	var status = $("input[id=status]");
	var operation = $("input[id=status_operation]");
	if(status.val() == "1" || status.val() == "24") {
		operation.val("C");
	} else {
		operation.val("U");
	}
	
	var dev_yn = $(':radio[name="dev_yn"]:checked').val();
	if(dev_yn == 'N'){
		dev_yn = "상용 검증요청 (개발검증 생략)";
	}else if(dev_yn == 'Y'){
		dev_yn = "개발/상용 검증요청";
	}else{ //동시 검증
		dev_yn = "개발/상용 동시검증요청";
	}
	
	if(status.val() == "1" || status.val() == "24"){
		if(str == 'save'){
			if(confirm("임시 저장 되었습니다\n모든 항목이 입력되면 완료버튼을 눌러\n다음단계로 진행하시기 바랍니다.")) {
				fn_pkgstatus_create("2");
			}else{
				return;
			}
		}else if(str == 'complete'){
			if(confirm(dev_yn+"을 선택하셨습니다.\n검증계획 결과단계로 진행하시겠습니까?")) {
				fn_pkgstatus_create("2");
			}else{
				return;
			}
		}
	}else{
		fn_pkgstatus_create("2");
	}
	
}

//상용 검증
function fn_checkbox_yn_click(obj, str) {
	//obj = this(해당 check box), str = vol, sec, cha, non 구분
	if($(obj).is(":checked")) {
		$("input[name="+str+"_yn]").val("Z");
		$("div[id="+str+"]").css("display", "block");
	} else {
		$("input[name="+str+"_yn]").val("N");
		$("div[id="+str+"]").css("display", "none");
	}

}

function owms(){
	window.opener.owms();
}

function cdrs(){
	window.opener.cdrs();
}

function tbpotal(){
	window.opener.tbpotal();
}

function snet(){
	window.opener.snet();
}

//검증결과
function fn_pkg_3(str){
	
	if(str == 'urgency'){
		$("input[id=urgency_verifi]").val("Y");
	}else{
		var vol_yn = $("input[id=vol_yn]").val();
		var sec_yn = $("input[id=sec_yn]").val();
		var cha_yn = $("input[id=cha_yn]").val();
		var non_yn = $("input[id=non_yn]").val();
		
		if(vol_yn=='Y'){
			if(vol_yn!='S'){
				alert("용량검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
				return;
			}
		}
		if(sec_yn=='Y'){
			if(sec_yn!='S'){
				alert("보안검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
				return;
			}
		}
		if(cha_yn=='Y'){
			if(cha_yn!='S'){
				alert("과금검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
				return;
			}
		}
		if(non_yn=='Y'){
			if(non_yn!='S'){
				alert("비기능검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
				return;
			}
		}
	}
	
	//일자
	var col1 = $("input[id=pkgStatusModel_col1]");
	if(rtrim(col1.val()) == ""){ 
		alert("시작일을 입력하세요.");
		col1.focus();
		return;
	}
	
	var col2 = $("input[id=pkgStatusModel_col2]");
	if(rtrim(col2.val()) == ""){ 
		alert("종료일을 입력하세요.");
		col2.focus();
		return;
	}
	
	if((col1.val().replace(/-/g,"")) > col2.val().replace(/-/g,"")){
		alert("시작일자가 종료일자보다 나중입니다.");
		col2.focus();
		return false;
	}  
	
	//파일
	var col3 = $("select[id=pkgStatusModel_col3]");
	if(col3.val() == ""){ 
		alert("보완내역별 시험결과항목은 필수 항목입니다.");
		col3.focus();
		return;
	}
	
	var col5 = $("select[id=pkgStatusModel_col5]");
	if(col5.val() == ""){ 
		alert("Regression Test 및 기본 검증 결과는 필수 항목입니다.");
		col5.focus();
		return;
	}

	var col11 = $("select[id=pkgStatusModel_col11]");
	if(col11.val() == ""){ 
		alert("기능 및 과금 검증 결과는 필수 항목입니다.");
		col11.focus();
		return;
	}

	var col13 = $("select[id=pkgStatusModel_col13]");
	if(col13.val() == ""){ 
		alert("보완내역서, 기능 변경 요청서의 결과는 필수 항목입니다.");
		col13.focus();
		return;
	}
	
	var col15 = $("select[id=pkgStatusModel_col15]");
	if(col15.val() == ""){ 
		alert("보완내역별 검증 결과는 필수 항목입니다.");
		col15.focus();
		return;
	}
	
	var col19 = $("select[id=pkgStatusModel_col19]");
	if(col19.val() == ""){ 
		alert("과금 영향도의 결과는 필수 항목입니다.");
		col19.focus();
		return;
	}
	
	var col21 = $("select[id=pkgStatusModel_col21]");
	if(col21.val() == ""){ 
		alert("작업절차서, S/W 블록 내역의 결과는 필수 항목입니다.");
		col21.focus();
		return;
	}
	
	var col23 = $("select[id=pkgStatusModel_col23]");
	if(col23.val() == ""){ 
		alert("PKG 적용 후 check list의 결과는 필수 항목입니다.");
		col23.focus();
		return;
	}
	
	var col25 = $("select[id=pkgStatusModel_col25]");
	if(col25.val() == ""){ 
		alert("CoD/PoD 변경 사항, 운용팀 공지사항의 결과는 필수 항목입니다.");
		col25.focus();
		return;
	}
	
	var col40 = $("select[id=pkgStatusModel_col40]");
	if(col40.val() == ""){ 
		alert("보안Guide 적용확인서의 결과는 필수 항목입니다.");
		col40.focus();
		return;
	}

	//반려 확인
	var turn_down = fn_select_turn_down_pkg3();
	
	if(turn_down) {
		if(confirm("항목 중 하나라도 불량이면 반려 처리합니다. 반려하시겠습니까?")) {
		} else {
			return;
		}
	}
	
	var status = $("input[id=status]");
	var operation = $("input[id=status_operation]");
	
	if(status.val() == "26") {
		operation.val("C");
	} else {
		operation.val("U");
	}
	
	fn_set_turn_down(turn_down);
	
	// Pkg 상태값: 검증접수 -> 검증완료 Set
	fn_pkgstatus_create("3");
}
function fn_select_turn_down_pkg3(){
	var obj = $("div[id=fn_pkg_3_tab] :input");

	for(var i = 0; i < obj.size(); i++) {
		if(obj[i].type == 'select-one') {
			if(obj[i].value == '불량') {
				return true;
			}
		}
	}
	return false;
}
//검증결과
function fn_pkg_22(){

	var col35 = $("select[id=pkgStatusModel_col35]");
	if(col35.val() == ""){ 
		alert("보완 내역서의 결과는 필수 항목입니다.");
		col35.focus();
		return;
	}
	
	var col38 = $("select[id=pkgStatusModel_col38]");
	if(col38.val() == ""){ 
		alert("Regression Test 및 기본 검증 결과는 필수 항목입니다.");
		col38.focus();
		return;
	}

	//반려 확인
	var turn_down = fn_select_turn_down();
	
	if(turn_down) {
		if(confirm("항목 중 하나라도 불량이면 반려 처리합니다. 반려하시겠습니까?")) {
		} else {
			return;
		}
	}
	
	var status = $("input[id=status]");
	var operation = $("input[id=status_operation]");
	
	var status_dev = $("input[id=status_dev]").val();
	var dev_yn = $(':radio[name="dev_yn"]:checked').val();

	if(dev_yn == 'D'){
		$("input[id=dev_yn_bak]").val("D");
		
		if(status_dev == "21") {	
			operation.val("C");
		}else{
			operation.val("U");
		}
	}else{		
		if(status.val() == "21") {
			operation.val("C");
		} else {
			operation.val("U");
		}
	}
	
	
	fn_set_turn_down(turn_down);
	
	// Pkg 상태값: 검증접수 -> 검증완료 Set
	fn_pkgstatus_create("22");
}

//초도일정수립
function fn_pkg_4(){
	var turn_down = fn_check_turn_down();
	
	if(turn_down) {
		var col20 = $("input[id=pkgStatusModel_col20]");
		if (col20.val() == "") {
			alert("반려 사유는 필수항목입니다.");
			return;
		}
	} else {
/*		
		var t1 = document.getElementsByName("check_seqs");
		var t2 = document.getElementsByName("work_dates");
		var tt = '';
		for (var ii=0; ii<t1.length; ii++)
			tt = tt+'['+(ii+1)+']='+t1[ii].value+'/'+t1[ii].checked+'/'+t2[ii].value+', ';
		alert('저장 '+tt);
*/		 
		var check_seqs = $("input[name=check_seqs]");
	   
		var work_dates = $("input[name='work_dates']");
		var non_check =0;

		var start_time1 = $("input[name='start_times1']");
		var end_time1 = $("input[name='end_times1']");
	   
		for(var i = 0; i < check_seqs.size(); i++) {
			//alert("포문 " + i +  " : " + check_seqs.eq(i).val());
			
			if(check_seqs.eq(i).is(":checked")) {
				if(work_dates.eq(i).val() == "") {
					alert("적용일은 필수항목입니다.");
					return;
				}
				
				if(start_time1.eq(i).val() > 23 || end_time1.eq(i).val() > 23) {
					alert("시간은 0~23중의 숫자를 입력해 주시기 바랍니다.");
					return;
				}
				
				non_check++;
			} 
		}//end for
		
	   if(non_check==0){
		   alert("적용장비를 선택하십시요");
		   return; 
	   } 
	}//end if 
	
	 
	
	var status = $("input[id=status]");
	var operation = $("input[id=status_operation]");
	
	if(status.val() == "6"){
		if(confirm("초도승인이 완료된 상태입니다.\n저장 시 다음단계는 \"초도승인요청\"입니다.\n저장하시겠습니까?")) {
		} else {
			return;
		}
	}
	
	if(status.val() == "3") {
		operation.val("C");
	} else {
		operation.val("U");
	}
	
	fn_set_turn_down(turn_down);

	// Pkg 상태값: 검증완료 -> 초도일정수립 Set
	fn_pkgstatus_create("4");
}

//개발승인요청
function fn_pkg_23(){
	var turn_down = fn_check_turn_down();
	
	var status = $("input[id=status]");
	var operation = $("input[id=status_operation]");
	
	var status_dev = $("input[id=status_dev]").val();
	var dev_yn = $(':radio[name="dev_yn"]:checked').val();

	if(dev_yn == 'D'){
		$("input[id=dev_yn_bak]").val("D");

		if(status_dev == "22") {	
			operation.val("C");
		}else{
			operation.val("U");
		}
	}else{		
		if(status.val() == "22") {
			operation.val("C");
		} else {
			//반려인 경우
			operation.val("U");
		}
	}
	
	fn_set_turn_down(turn_down);
	fn_pkgstatus_create("23");
	
}

//초도승인요청
function fn_pkg_5(){
	var turn_down = fn_check_turn_down();
	
	if(turn_down) {
		var col20 = $("input[id=pkgStatusModel_col20]");
		if (col20.val() == "") {
			alert("반려 사유는 필수항목입니다.");
			return;
		}
	} else {
	}
	
	var status = $("input[id=status]");
	var operation = $("input[id=status_operation]");
	
	if(status.val() == "4") {
		operation.val("C");
	} else {
		//반려인 경우
		operation.val("U");
	}
	
	fn_set_turn_down(turn_down);
	fn_pkgstatus_create("5");
	
}

//검증계획 승인
function fn_pkg_26(ord){
	var turn_down = false;
	if(ord == 'return'){
		if(confirm("검증계획요청 단계로 변경됩니다. 반려하시겠습니까?")) {
			turn_down = true;
		} else {
			return;
		}
	}else{
		if(confirm("검증계획 승인을 하시겠습니까?")) {
		}else{
			return;
		}
	}
	
	var status = $("input[id=status]");
	
	var operation = $("input[id=status_operation]");
	
	if(status.val() == "2") {
		$("input[id=ord]").val(ord);
		
		operation.val("C");

		fn_set_turn_down(turn_down);

		fn_pkgstatus_create("26");
	}
}

//초도승인
function fn_pkg_24(ord){
	
	var turn_down = fn_check_turn_down();
	
	if(turn_down) {
		var col20 = $("input[id=pkgStatusModel_col20]");
		if (col20.val() == "") {
			alert("반려 사유는 필수항목입니다.");
			return;
		}
	} else {
	}
	
	var status = $("input[id=status]");
	var operation = $("input[id=status_operation]");

	var status_dev = $("input[id=status_dev]").val();
	var dev_yn = $(':radio[name="dev_yn"]:checked').val();

	if(dev_yn == 'D'){
		$("input[id=dev_yn_bak]").val("D");
		if(status_dev == "23") {
			$("input[id=ord]").val(ord);
			
			operation.val("C");

			fn_set_turn_down(turn_down);
			fn_pkgstatus_create("24");
		}
	}else{		
		if(status.val() == "23") {
			$("input[id=ord]").val(ord);
			
			operation.val("C");

			fn_set_turn_down(turn_down);

			fn_pkgstatus_create("24");
		}
	}
}

//초도승인
function fn_pkg_6(ord){
	
	var turn_down = fn_check_turn_down();
	
	if(turn_down) {
		var col20 = $("input[id=pkgStatusModel_col20]");
		if (col20.val() == "") {
			alert("반려 사유는 필수항목입니다.");
			return;
		}
	} else {
	}
	
	var status = $("input[id=status]");
	
	var operation = $("input[id=status_operation]");

	if(status.val() == "5") {
		$("input[id=ord]").val(ord);
		
		operation.val("C");

		fn_set_turn_down(turn_down);

		fn_pkgstatus_create("6");
	}
}

//초도 결과 등록
function fn_pkg_7(str){

	var turn_down = fn_select_turn_down();
	
	if(turn_down) {
		if(confirm("항목 중 하나라도 불량이면 반려 처리합니다. 반려하시겠습니까?")) {
		} else {
			return;
		}
	}

	if(str == 'save'){
		$("input[id=pkgStatusModel_col22]").val("save");
		
	}else if(str == 'complete'){
		$("input[id=pkgStatusModel_col22]").val("complete");
	}
	
	var status = $("input[id=status]");
	var operation = $("input[id=status_operation]");

	if(status.val() == "6") {
		operation.val("C");
	} else {
		operation.val("U");
	}
	
	fn_set_turn_down(turn_down);

	fn_pkgstatus_create("7");
}

function fn_pkg_7_end(){
//alert($("#apply_end").val());
	
	var vol_yn = $("input[id=vol_yn]").val();
	var sec_yn = $("input[id=sec_yn]").val();
	var cha_yn = $("input[id=cha_yn]").val();
	var non_yn = $("input[id=non_yn]").val();
	
	if(vol_yn=='Y'){
		if(vol_yn!='S'){
			alert("용량검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
			return;
		}
	}
	if(sec_yn=='Y'){
		if(sec_yn!='S'){
			alert("보안검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
			return;
		}
	}
	if(cha_yn=='Y'){
		if(cha_yn!='S'){
			alert("과금검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
			return;
		}
	}
	if(non_yn=='Y'){
		if(non_yn!='S'){
			alert("비기능검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
			return;
		}
	}
	
	var urgency_yn = $("#urgency_yn").val();
	if(confirm("확대일정을 수립 하지 않고 패키지 검증을 완료합니다.\n 적용완료하시겠습니까?")){
		if(urgency_yn == 'Y'){
			alert("긴급등록 상태입니다.\n 입력안된 정보를 입력해 주세요.");
			return;
		}else{			
			$("#apply_end").val("E");
			var str = "complete";
			fn_pkg_7(str);
		}
	} else {
		return;
	}
}

//확대일정수립
function fn_pkg_8(){
	var turn_down = fn_check_turn_down();
	var check_seqs = $("input[name='check_seqs']");
	var work_dates = $("input[name='work_dates']");
	var non_check =0;
	
	var count = $("#check_seqs_count").val();
	var start_time1 = $("input[name='start_times1']");
	var end_time1 = $("input[name='end_times1']");
	
	for(var i = 0; i < count; i++) {
		if(check_seqs.eq(i).is(":checked")) {
			$("#non_check_seqs_"+(i+1)).val("");
			$("#all_check_seqs_"+(i+1)).val(check_seqs.eq(i).val());
			if(work_dates.eq(i).val() == '') {
				alert("적용일은 필수항목입니다.");
				return;
			}
			
			if(start_time1.eq(i).val() > 23 || end_time1.eq(i).val() > 23) {
				alert("시간은 0~23사이의 숫자를 입력해 주시기 바랍니다.");
				return;
			}
			
			non_check++;
		} else {
			$("#non_check_seqs_"+(i+1)).val(check_seqs.eq(i).val());
			$("#all_check_seqs_"+(i+1)).val(check_seqs.eq(i).val());
		}
	
	}//end for
		
   if(non_check==0){
	   alert("적용장비를 선택하십시요");
	   return; 
   } 
   
   var status = $("input[id=status]");
   var operation = $("input[id=status_operation]");
	
   if(status.val() == "7") {
	   operation.val("C");
   } else {
	   operation.val("U");
   }
	
   fn_set_turn_down(turn_down);
   
   fn_pkgstatus_create("8");
}

//확대결과 등록
function fn_pkg_9(all_equipment, str){
	var turn_down = fn_select_turn_down();
	var urgency_yn = $("#urgency_yn").val();
	
	if(str == 'save'){
		$("input[id=pkgStatusModel_col22]").val("save");
		
	}else if(str == 'complete'){
		$("input[id=pkgStatusModel_col22]").val("complete");
	}
	
	if(urgency_yn == 'Y'){
		alert("긴급등록 상태입니다.\n 입력안된 정보를 입력해 주세요.");
		return;
	}
	
	if(turn_down) {
		if(confirm("항목 중 하나라도 불량이면 반려 처리합니다. 반려하시겠습니까?")) {
		} else {
			return;
		}
	}
	
	//121114 junhee
	var work_result_count = $("#work_result_count").val();
	var chk_null = 0;
	var chk_null_cha = 0;
//	alert("count = " + work_result_count);
	for(var i=0; i < work_result_count; i++){
//		alert("========for문=======" + i + " 번째===============");
//		alert("PkgModel_pkgEquipmentModel_work_result_" + ($("#PkgModel_pkgEquipmentModel_work_result_"+(i+1)).val()));
//		alert("======all_eq====="+ all_equipment +"=====");
		if(str == 'complete'){			
			//일정수립된 장비의 양호처리 체크
			if($("#PkgModel_pkgEquipmentModel_work_result_"+(i+1)).val() == ""){
				alert("확대적용장비의 적용결과가 모두 양호로 처리 되어야 \n 저장 및 완료가 가능합니다.");
				return;
			}
		}
		//일정수립이 안된 장비 갯수 체크
		if($("#PkgModel_pkgEquipmentModel_work_result_"+(i+1)).val() == null){
			chk_null += 1;
//			alert("======chk_null =="+i+"번째 장비="+ chk_null +" 개=====");
		}
	}

// 검증완료	
	var vol_yn = $("input[id=vol_yn]").val();
	var sec_yn = $("input[id=sec_yn]").val();
	var cha_yn = $("input[id=cha_yn]").val();
	var non_yn = $("input[id=non_yn]").val();
	
	if(vol_yn=='Y'){
		if(vol_yn!='S'){
			alert("용량검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
			return;
		}
	}
	if(sec_yn=='Y'){
		if(sec_yn!='S'){
			alert("보안검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
			return;
		}
	}
	if(cha_yn=='Y'){
		if(cha_yn!='S'){
			alert("과금검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
			return;
		}
	}
	if(non_yn=='Y'){
		if(non_yn!='S'){
			alert("비기능검증이 완료되지 않았습니다.\n검증완료 후 결과등록 바랍니다");
			return;
		}
	}

	
//	alert("======chk_null ==" + chk_null + " 개=====");
	if(str == 'complete'){
		if(all_equipment == "all_ok"){ //그냥 완료 시			
			if(chk_null > 0){
				alert("일정수립이 안된 장비가 존재합니다.\n 이상태에서 PKG완료를 원하시면 PKG완료 버튼을 눌러주세요.");
				return;
			}else{// 일정수립이 다 완료
				
			}
		}else{ // PKG 완료시 
			if(chk_null > 0){
				if(confirm("일정수립이 안된 장비가 존재합니다.\n 계속 하시겠습니까??")) {
				} else {
					return;
				}
			}
		}
	}
	
	var status = $("input[id=status]");
	var operation = $("input[id=status_operation]");
	
	$("input[id=urgency_verifi]").val("N");
	
	if(status.val() == "8") {
		operation.val("C");
	} else {
		operation.val("U");
	}

	fn_set_turn_down(turn_down);

	fn_pkgstatus_create("9");
}
//전체 체크
function fn_allCheck() {
	var count = $("#check_seqs_count").val();
	var check_seqs = $("input[name='check_seqs']");
	
    var chk = $("input[name='allCheckbox']").is(":checked");
    
    for(var i=0; i<count; i++) {
		check_seqs.eq(i).attr("checked", chk);
		fn_checkboxEquipment_click(check_seqs.eq(i), i+1);	
	}
}
//확대 전용
function fn_allCheck_E(granted, team_code) {
	var count = $("#check_seqs_count").val();
	var check_seqs = $("input[name='check_seqs']");
	var team_codes = $("input[name='team_codes']");

    var chk = $("input[name='allCheckbox']").is(":checked");
    for(var i=0; i<count; i++) {
    	if(granted == "OPERATOR" && team_code != team_codes.eq(i).val()){
    		
    	}else{    		
    		check_seqs.eq(i).attr("checked", chk);
    		fn_checkboxEquipment_click(check_seqs.eq(i), i+1);
    	}
	}

}

//전체 체크_date
function fn_allCheck_date(granted, team_code) {
	var count = $("#check_seqs_count").val();
	var check_seqs_date = $("input[name='check_seqs_date']");
	var team_codes = $("input[name='team_codes']");
	
    var chk = $("input[name='allCheckbox_date']").is(":checked");
    for(var i=0; i<count; i++) {
    	if(granted == "OPERATOR" && team_code != team_codes.eq(i).val()){
    		
    	}else{    		
    		check_seqs_date.eq(i).attr("checked", chk);
    	}
	}
    
}

function fn_SelectAllCheck(){
	var count = $("#check_seqs_count").val();
	
	var day = $("#PkgModel_pkgEquipmentModel_work_date_All").val();
	if(day == ""){
			alert("적용 일시를 먼저 선택 해 주세요.");
			return;
		}

	for(var i=0; i<count; i++){
		if($("input[name='check_seqs']").get(i).checked){
			$("#PkgModel_pkgEquipmentModel_work_date_"+(i+1)).val(day);
			$("#PkgModel_pkgEquipmentModel_start_time1_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_start_time1_All").val());
			$("#PkgModel_pkgEquipmentModel_start_time2_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_start_time2_All").val());
			$("#PkgModel_pkgEquipmentModel_end_time1_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_end_time1_All").val());
			$("#PkgModel_pkgEquipmentModel_end_time2_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_end_time2_All").val());
		}
	}	
	
}

function fn_SelectAllCheck_E(){
	var count = $("#check_seqs_count").val();
	
	var day = $("#PkgModel_pkgEquipmentModel_work_date_All").val();
	if(day == ""){
			alert("적용 일시를 먼저 선택 해 주세요.");
			return;
		}
	var cnt = 0;
	for(var i=0; i<count; i++){
		if($("input[name='check_seqs']").get(i).checked){
			if($("input[name='check_seqs_date']").get(i).checked){
				$("#PkgModel_pkgEquipmentModel_work_date_"+(i+1)).val(day);
				$("#PkgModel_pkgEquipmentModel_start_time1_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_start_time1_All").val());
				$("#PkgModel_pkgEquipmentModel_start_time2_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_start_time2_All").val());
				$("#PkgModel_pkgEquipmentModel_end_time1_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_end_time1_All").val());
				$("#PkgModel_pkgEquipmentModel_end_time2_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_end_time2_All").val());
				$("#PkgModel_pkgEquipmentModel_ampm_"+(i+1)).val($("#PkgModel_pkgEquipmentModel_ampm_All").val());
				cnt++;
			}
		}
	}
	if(cnt == 0){
		alert("두번째 박스에 체크된 일정이 존재하지 않습니다.");
		return;
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


//초도,확대 적용 장비 관련
function fn_checkboxEquipment_click(obj, index) {
//	alert(obj.value);
	var i = index - 1;
	var work_dates = $("input[name='work_dates']");
	var start_times1 = $("input[name='start_times1']");
	var start_times2 = $("input[name='start_times2']");
	var end_times1 = $("input[name='end_times1']");
	var end_times2 = $("input[name='end_times2']");
	var ampms = $("select[name='ampms']");
	
	if($(obj).is(":checked")) {
//		alert("checked");
		work_dates.eq(i).attr("readonly", true);
		
		//모델에서 초기값을 안 정하고 여기서 하는 이유는 저장 시 체크된 것들만 받을 수 없기 때문
		ampms.eq(i).val("야간");
		start_times1.eq(i).val("02");
		start_times2.eq(i).val("00");
		end_times1.eq(i).val("07");
		end_times2.eq(i).val("00");

		doDivSH("hide", "equipment_se_none_" + index, 0);
		
		doDivSH("show", "equipment_se_date_" + index, 0);
		doDivSH("show", "check_ok_" + index, 0);
		doDivSH("show", "PkgModel_pkgEquipmentModel_ampm_" + index, 0);
		
	} else {
		$("input[name='allCheckbox']").attr("checked", false);
//		alert("unchecked");
		work_dates.eq(i).val("");
		work_dates.eq(i).attr("readonly", true);
		//work_dates.eq(i).attr("disabled", true);

		start_times1.eq(i).val("");
		start_times2.eq(i).val("");
		end_times1.eq(i).val("");
		end_times2.eq(i).val("");

		doDivSH("hide", "equipment_se_date_" + index, 0);
		doDivSH("hide", "PkgModel_pkgEquipmentModel_ampm_" + index, 0);
		doDivSH("hide", "check_ok_" + index, 0);
		
		doDivSH("show", "equipment_se_none_" + index, 0);
	}
	/*
	var t1 = document.getElementsByName("check_seqs");
	var t2 = document.getElementsByName("work_dates");
	var tt = '';
	for (var ii=0; ii<t1.length; ii++)
		tt = tt+'['+(ii+1)+']='+t1[ii].value+'/'+t2[ii].value+', ';
	alert('AFTER '+tt);
	*/
}

//반려 사유 체크
function fn_is_null_turn_down() {
	var col20 = $("input[id=pkgStatusModel_col20]");
	//alert(col20.val());
	return true;
	if(col20.val() == "") {
		alert("반려 사유는 필수항목입니다.");
		return true;
	}
	return false;
}

//반려 여부 판단: 검증결과, 초도결과, 확대결과 이외
function fn_check_turn_down() {
	var fb1 = $("div[id=flow_tb_1]").css("display");

	if (fb1 == "" || fb1 == undefined) {
		return true;
	} else {
		if($("div[id=flow_tb_1]").css("display") == "block") {
			return false;
		} else {
			return true;
		}
	}
}

//반려 여부 판단: 검증결과, 초도결과, 확대결과
function fn_select_turn_down() {
	var obj = $("div[id=flow_tb_1] :input");

	for(var i = 0; i < obj.size(); i++) {
		if(obj[i].type == 'select-one') {
			if(obj[i].value == '불량') {
				return true;
			}
		}
	}
	return false;
}

//반려인 경우 세팅
function fn_set_turn_down(turn_down){
	if(turn_down) {
		// Pkg 상태값: 반려
		$("input[id=turn_down]").val("true");
	} else {
		// Pkg 상태값: 요청중 -> 검증접수 Set
		$("input[id=turn_down]").val("false");
	}
}

//시스템 선택 결과 처리
function fn_system_popup_callback(system_seq, system_name) {
	$("input[id=system_seq]").val(system_seq);
	$("input[id=system_name]").val(system_name);
}

//textarea max-length 체크
function fn_detail_variable_textarea_check(obj) {
    var max = parseInt($(obj).attr('maxlength'));
    var str = $(obj).val();
    
    if(str.length > max){
        $(obj).val(str.substr(0, max));
    	alert("최대 [" + max + " 자]까지 입력 가능합니다.");
    }
}

function fn_print(gubun) {
	if(gubun == "main"){
		$("input[id=selected_status]").val("0");
	}
	
	var target = "printRead";
	var option = "width=1016px, height=895px, scrollbars=yes, resizable=yes, statusbar=no";
	var sWin = window.open("", target, option);
			
	var form = document.getElementById("PkgModel");
	
	form.target = target;
	form.action = "/pkgmg/pkg/Pkg_Popup_Print.do";
	
	form.submit();
	sWin.focus();
}

function fn_print_dev() {
	var target = "printRead";
	var option = "width=1016px, height=695px, scrollbars=yes, resizable=yes, statusbar=no";
	var sWin = window.open("", target, option);
			
	var form = document.getElementById("PkgModel");
	
	form.target = target;
	form.action = "/pkgmg/pkg/Pkg_Popup_Print_Dev.do";
	
	form.submit();
	sWin.focus();
}

function fn_iwcs_view(date, code) {
	$("#work_date").val(date);
	$("#team_code").val(code);
	
	doSubmit("PkgModel", "/common/workSystem/Iwcs_view.do", "fn_callback_fn_iwcs_view");
}

function fn_callback_fn_iwcs_view(data) {
	var seq = $("#param1").val();
	var id = $("#param2").val();

	var target = "_blank";
	
//	alert("http://150.24.192.73/iwcs/doc/pkms_login.asp?seq="+seq+"&id="+id);
//	return;
	window.open("http://150.24.192.73/iwcs/doc/pkms_login.asp?seq="+seq+"&id="+id,target);
}


/**패키지검증 - 용량검증|보안검증|과금검증|비기능검증 */
function fn_readVerifyTem(vertype){
	$("#verify_type").val(vertype);
	doSubmit("PkgModel", "/pkgmg/pkg/PkgStatusVerify_Ajax_Read.do", "fn_callback_statusVerify_read");
}
function fn_readSecurity(){
	$("#verify_type").val("sec");
	doSubmit("PkgModel", "/pkgmg/pkg/PkgStatusSecurity_Ajax_Read.do", "fn_callback_statusVerify_read");
}
function fn_callback_statusVerify_read(data){
	var verify_type_url = "";
	if($("#verify_type").val() == "vol"){
		$("div[id=pkg_tab_flow_31]").html(data);
		verify_type_url = "31";
	}else if($("#verify_type").val() == "sec"){
		$("div[id=pkg_tab_flow_32]").html(data);
		verify_type_url = "32";
	}else if($("#verify_type").val() == "cha"){
		$("div[id=pkg_tab_flow_33]").html(data);
		verify_type_url = "33";
	}else if($("#verify_type").val() == "non"){
		$("div[id=pkg_tab_flow_34]").html(data);
		verify_type_url = "34";
	}
	doDivSH("slideToggle", "pkg_tab_flow_"+verify_type_url, 500);
}
function fn_create_result(end,selected_num,end_gubun,QuestFlag){
	var result_quest_input = "";
	var result_item_input = "";
	var result_quest_radio = "";
	var result_item_radio = "";
	var noneInput = "";
	var inputValcnt ;
	var radioValcnt ;
	var quest_type = "";
	var chkd_cnt ;
	var due_cnt ;
	var nokgubun = "";
	if($(".result_input").length == 0){	quest_type = "radioType"; }//선택형
	if($(".result_radio").length == 0){	quest_type = "textType"; }//단답형
	if($(".result_radio").length != 0 && $(".result_input").length != 0){ quest_type = "allType"; }//복합형
	
	if(QuestFlag == 'N'){
		alert("검증 템플릿을 등록해 주세요.");
		return;
	}
	
	/**검증완료시 nok 체크*/
	if(end == "complete"){
		if(end_gubun != 0){//수정
			$(".result_radio:radio:checked").each(function (index) {
				if($("#selectAjx_YN").val() == 'Y'){
					if($(this).val() == 'NOK'){
						nokgubun = "nok";
					}
				}else{
					if(index < ($(".result_radio:radio:checked").length/2)){
						if($(this).val() == 'NOK'){
							nokgubun = "nok";
						}
					}
				}
			});
		}else{//등록
			$(".result_radio:radio:checked").each(function (index) {
				if($(this).val() == 'NOK'){
					nokgubun = "nok";
				}
			});
		}
		if(nokgubun == "nok"){
			alert("검증결과중 NOK가 있습니다. 확인 바랍니다.");
			return;
		}
	}/***/
	
	//등록,수정 공통사용
	if(quest_type == "textType" || quest_type == "allType"){//단답형 or 복합형
		inputValcnt = $(".result_input").length/2;
		$(".result_input").each(function (index){
			if(index < inputValcnt){
				if($(this).val() != ''){
					result_quest_input += "," + $(this).attr('name');
					result_item_input += "," + $(this).val();
				}else{ noneInput = "none"; }
			}else{ }
		});
		if(noneInput=='none'){
			if(end == "complete"){
				alert("검증결과 등록은 필수 입니다."); return;
			}
		}
	}//
		
	if(quest_type == "radioType" || quest_type == "allType"){//선택형 or 복합형
		if(end_gubun != 0){//수정시에만
			radioValcnt = $(".result_radio:radio:checked").length/2;
			chkd_cnt = $(".result_radio:radio:checked").length;
			due_cnt = ($(".result_radio").length/2)/4;
			$(".result_radio:radio:checked").each(function (index) {
				if($("#selectAjx_YN").val() == 'Y'){
					result_quest_radio += "," + $(this).attr('name');
					result_item_radio += "," + $(this).val();
				}else{
					if(index < radioValcnt){
						result_quest_radio += "," + $(this).attr('name');
						result_item_radio += "," + $(this).val();
					}else{ }
				}
			});
			if($("#selectAjx_YN").val() == 'Y'){
				if(chkd_cnt != due_cnt){
					if(end == "complete"){
						alert("검증결과 등록은 필수 입니다."); return;
					}
				}
			}else{
				if(radioValcnt != due_cnt){
					if(end == "complete"){
						alert("검증결과 등록은 필수 입니다."); return;
					}
				}
			}
		}
		else if(end_gubun == 0){//등록시에만
			chkd_cnt = $(".result_radio:radio:checked").length;
			due_cnt = ($(".result_radio").length/2)/4;
			$(".result_radio:radio:checked").each(function (index) {
				result_quest_radio += "," + $(this).attr('name');
				result_item_radio += "," + $(this).val();
			});
			if(chkd_cnt != due_cnt){
				if(end == "complete"){
					alert("검증결과 등록은 필수 입니다."); return;
				}
			}
		}
	}
	if($("#verify_start_date").val()==''||$("#verify_end_date").val()==''){
		if(end == "complete"){
			alert("일정 등록은 필수 입니다."); return;
		}
	}
	
	$("#result_quest_input").val(result_quest_input.substring(1,result_quest_input.length));
	$("#result_item_input").val(result_item_input.substring(1,result_item_input.length));
	$("#result_quest_radio").val(result_quest_radio.substring(1,result_quest_radio.length));
	$("#result_item_radio").val(result_item_radio.substring(1,result_item_radio.length));
	$("#end").val(end);
	/*alert($("#result_quest_input").val() + "  = result_quest_input");
	alert($("#result_item_input").val() + "  = result_item_input");
	alert($("#result_quest_radio").val() + "  = result_quest_radio");
	alert($("#result_item_radio").val() + "  = result_item_radio");*/
	$("input[id=status_operation]").val("U");
	
	doSubmit4File("PkgModel", "/pkgmg/pkg/PkgStatusVerify_Ajax_Create.do?end="+end+"&quest_type="+quest_type, "fn_callback_create_result", fn_msg());
}
function fn_callback_create_result(){
	fn_read("read", $("input[id=pkg_seq]").val());
}

function fn_flow_init_verify(selected_num){
	if($("#verify_type").val() == "vol"){
		doDivSH("slideUp", "pkg_tab_flow_31", 100);
	}else if($("#verify_type").val() == "sec"){
		doDivSH("slideUp", "pkg_tab_flow_32", 100);
	}else if($("#verify_type").val() == "cha"){
		doDivSH("slideUp", "pkg_tab_flow_33", 100);
	}else if($("#verify_type").val() == "non"){
		doDivSH("slideUp", "pkg_tab_flow_34", 100);
	}
	//검증4타입만 reload.

	if(selected_num != '32'){
		var display = $("div[id=pkg_tab_flow_" + selected_num + "]").css("display");
		if (display == "block") {
			fn_read("read", $("input[id=pkg_seq]").val());
		}
	}
}
function fn_selectVerifyTem(){
	$("#selectAjx_YN").val("Y");
	doSubmit("PkgModel", "/pkgmg/pkg/selectVerifyTem_Ajax_read.do", "fn_callback_selectVerifyTem_Ajax_read");
}

function fn_callback_selectVerifyTem_Ajax_read(data){
	$("div[id=default_select_verifyTem]").empty();
	$("div[id=ajax_select_verifyTem]").html(data);
}
function fnSelBox43(){ //검증4타입
	var selIdx = document.forms[0].selID43.selectedIndex;
	for(var i=1; i<selIdx+2; i++){
		document.getElementById("display_43_"+i).style.display = "";
	}
	for(var j=selIdx+2; j<=4; j++){
		document.getElementById("display_43_"+j).style.display = "none";
	}		
}
function div_open_close(num){
	acc_status = $("div[id=acc_"+num+"]").css("display"); 
	if(acc_status == 'block'){
		doDivSH("hide", "acc_"+num+"", 0);
		$("a[id=oc_"+num+"]").html("열기");
	}else{
		doDivSH("show", "acc_"+num+"", 0);
		$("a[id=oc_"+num+"]").html("닫기");
	}
}

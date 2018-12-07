function fn_pkg_read(gubun, pkg_seq, formName) {
	var retUrl = document.getElementById("retUrl");

	if (gubun == "read") {
		retUrl.value = "/pkgmg/pkg/Pkg_Popup_Read";
	} else if (gubun == "view") {
		retUrl.value = "/pkgmg/pkg/Pkg_Popup_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}

	document.getElementById("pkg_seq").value = pkg_seq;

	var target = "pkgRead";
	var option = "width=1175px, height=895px, scrollbars=yes, resizable=yes, statusbar=no";
	var sWin = window.open("", target, option);
	
	if(formName == "" || typeof formName == "undefined"){
		formName = "PkgModel";
	}
			
	var form = document.getElementById(formName);
	form.target = target;
	form.action = "/pkgmg/pkg/Pkg_Popup_Read.do";
	form.submit();
	sWin.focus();
}
function fn_pkg_read2(gubun, pkg_seq, formName) {
	var retUrl = "";

	if (gubun == "read") {
		retUrl = "/pkgmg/pkg/Pkg_Popup_Read";
	} else if (gubun == "view") {
		retUrl = "/pkgmg/pkg/Pkg_Popup_View";
	} else {
		alert("you must have param selected read or view");
		return;
	}

	var target = "pkgRead";
	var option = "width=1175px, height=895px, scrollbars=yes, resizable=yes, statusbar=no";
	var sWin = "/pkgmg/pkg/Pkg_Popup_Read.do?pkg_seq="+pkg_seq+"&retUrl="+retUrl;
	
	window.open(sWin, target, option);
	
}

function fn_readDetailSearch() {
	var option = "width=1000px, height=700px, scrollbars=no, resizable=yes, statusbar=no";
	var sWin = window.open("", "PKG_DETAIL_SEARCH", option);

	var form = document.getElementById("PkgModel");
	form.target = "PKG_DETAIL_SEARCH";
	form.action = "/pkgmg/pkg/PkgDetailSearch_Popup_ReadList.do";
	form.submit();
	sWin.focus();
}

//보완적용내역 다운로드
function fn_list_excel_download() {
	 
	doSubmit("PkgModel", "/pkgmg/pkg/Pkg_ExcelDownload.do", "fn_callback_file_download");
	 
}
//템플릿 다운로드(템플릿 다운로드)
function fn_callback_file_download(data) {
	var file_name = $("input[id=param1]").val();
	downloadFile(file_name, file_name, "");
}
//보완적용내역 다운로드
function fn_schedule_list_excel_download() {
	 
	doSubmit("ScheduleModel", "/pkgmg/pkg/Schedule_ExcelDownload.do", "fn_callback_file_download");
	 
}

//보완적용내역 다운로드
function fn_diary_excel_download() {
	var date = $("h2",".fc-header-title").html();
	var year = date.substring(0,4);
	var month = date.substring(6,8);
	
	var excel_d = year+"-"+month;
	$("#excel_date").val(excel_d);
	doSubmit("DiaryModel", "/pkgmg/diary/Diary_ExcelDownload.do", "fn_callback_file_download");	 
}
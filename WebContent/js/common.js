// Body 화면 로딩 시 구현되는 공통 정의(Init Start)
function doLoadingInitStart() {
	doLoadingBarAppend();
//	doLoading("show");

	$("#loading").ajaxStart(function() {
		doLoading("show");
	});

	$("#loading").ajaxStop(function() {
		setTimeout(function() {
			doLoading("hidden");
		}, 10);
	});
}

// Body 화면 로딩 시 구현되는 공통 정의(Init Stop)
function doLoadingInitEnd() {
//	doLoading("hidden");
}

// Body Loading Bar Append
function doLoadingBarAppend() {
		
	$("div[id=common_wrapper]").append('<div id="loading" style="display:none;">'
			+'<div id="loadingContent" style="padding: 50px 0px 0px 160px;">'
			+'<div style="width:150px; height:150px; background: url(/images/common/loading_bg.png) no-repeat;padding: 10px 0px 0px 0px;">'
			+'<img src="/images/common/loading_new_f.gif"></div>'
			+'</div><div id="resultHTML" style="display:none;"></div></div>');
}

// 화면로딩 제어
function doLoading(op) {
	if (op == "show") {
		doFadeTo();
		$("#loading").fadeTo("fast", 0.5);
	} else if (op == "hidden") {
		$("#loading").fadeOut(500);
	} else {
		alert("option is [show or hidden]");
	}
}

var userAgent = navigator.userAgent.toLowerCase();

var browser = {
    msie    : /msie/.test( userAgent ) && !/opera/.test( userAgent ),
    safari  : /webkit/.test( userAgent ),
    firefox : /mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent ),
    opera   : /opera/.test( userAgent )
};   


// 로딩바 css 적용
function doFadeTo() {

	var totalHeight = 0;
	if (browser.msie) { // IE
		var scrollHeight = document.documentElement.scrollHeight;
		var browserHeight = document.documentElement.clientHeight;
		totalHeight = scrollHeight < browserHeight ? browserHeight
				: scrollHeight;
	} else if (browser.safari) { // Chrome || Safari
		totalHeight = document.body.scrollHeight;
	} else if (browser.firefox) { // Firefox || NS
		var bodyHeight = document.body.clientHeight;
		totalHeight = window.innerHeight < bodyHeight ? bodyHeight
				: window.innerHeight;
	} else if (browser.opera) { // Opera
		var bodyHeight = document.body.clientHeight;
		totalHeight = window.innerHeight < bodyHeight ? bodyHeight
				: window.innerHeight;
	} else {
		// alert("지원하지 않는 브라우져!!");
	}
	
//	alert(document.documentElement.scrollHeight + " >>" + document.body.scrollHeight + " ==> " + scrollHeight + " -- " + msgHeight + " == " + document.body.scrollTop);
//	var sh = (document.body.scrollHeight < 200) ? document.documentElement.scrollHeight : document.body.scrollHeight;
	
	var scrollHeight = parseInt(totalHeight, 10);
	var msgHeight = parseInt((scrollHeight / 2), 10)
			+ document.body.scrollTop;

	var loading = $("div[id=loading]");
	var loadingContent = $("div[id=loadingContent]");
	
	loading.css("position", "absolute");
	loading.css("top", "0");
	loading.css("left", "0");
	loading.css("width", "100%");
	
	//150922 수정
	loading.css("height", "1000%");
	//	loading.css("height", "100%");
	// loading.css("height", scrollHeight);
	loading.css("background", "#e9e9e9");
	loading.css("z-index", "1000");
	loading.css("text-align", "center");
	loading.css("vertical-align", "middle");
	
	loadingContent.css("position", "absolute");
	loadingContent.css("top", msgHeight);
	loadingContent.css("left", "50%");
	loadingContent.css("width", "500px");
	loadingContent.css("height", "200px");
	loadingContent.css("background", "#e9e9e9");
	loadingContent.css("z-index", "900");
	loadingContent.css("margin", "-200px 0 0 -250px");
}

// menu2Depth 기본선택
/*function doSubMenuSelect(i) {
	i++;
	$("#menu li.m" + i + " a").css('background-position', 'left bottom');
}*/

// menu2Depth 제어
//function doSubMenu(id, i) {
//	$('#menu li.m' + id).hover(
//			function() {
//				var left = 150 + ((i - 1) * 136);
//				$('#sm_Div1').css("left", left + "px");
//				$('#menu_' + id).css('display', 'block'); 
//			},
//			function() {
//				$('#menu_' + id).css('display', 'none');
//				$('#menu_' + id).hover(
//						function() { $('#menu_' + id).css('display', 'block'); },
//						function() { $('#menu_' + id).css('display', 'none'); }
//				);
//			}
//	);
//}
/*
function doSubMenu(i) {
	i++;
	$('#menu li.m' + i).hover(
			function() {
				var left = 150 + ((i - 1) * 155);
				$('#sm_Div1').css("left", left + "px");
				$('#menu_' + i).css('display', 'block'); 
			},
			function() {
				$('#menu_' + i).css('display', 'none');
				$('#menu_' + i).hover(
						function() { $('#menu_' + i).css('display', 'block'); },
						function() { $('#menu_' + i).css('display', 'none'); }
				);
			}
	);
}
*/

/*
 * 서버통신 공통모듈(Post)
 * 서버통신을 위한 파라미터는 form 변수에서 모두 정의한다.
 * formId: form ID
 * url: 서버통신 url
 * callback: 서버통신 종료후 구현될 callback명
 */
function doSubmit(formId, url, callback, message) {
	$.post(url
		 , $("form[id=" + formId + "]").serializeArray()
		 , function(data) {
			$("#resultHTML").html(data);

			var resultType = $("input[id=resultType]").val();

			if (resultType == 'invalid.session') {
//				alert('invalid.session');
			} else {
				if (resultType == 'error.sys') {
					alert('시스템 오류가 발생 하였습니다.\n오류가 계속되는 경우 관리자에게 문의하시기 바랍니다.');
				} else if (resultType == 'error.biz') {
					//비즈니스 로직에서 발생한 메세지
					alert(document.getElementById("error_message").innerHTML);
				} else {
					if (message != undefined && message != "") {
						alert(message);
					}
					
					// User collback
					eval(callback+'(data)');
				}
			}
	});
}
// submit시 resultHTML(data) 2번뿌리기때문..
function doSubmit2(formId, url, callback, message) {
	$.post(url
		 , $("form[id=" + formId + "]").serializeArray()
		 , function(data) {
//			$("#resultHTML").html(data);

			var resultType = $("input[id=resultType]").val();

			if (resultType == 'invalid.session') {
//				alert('invalid.session');
			} else {
				if (resultType == 'error.sys') {
					alert('시스템 오류가 발생 하였습니다.\n오류가 계속되는 경우 관리자에게 문의하시기 바랍니다.');
				} else if (resultType == 'error.biz') {
					//비즈니스 로직에서 발생한 메세지
					alert(document.getElementById("error_message").innerHTML);
				} else {
					if (message != undefined && message != "") {
						alert(message);
					}
					
					// User collback
					eval(callback+'(data)');
				}
			}
	});
}

/*
 * 서버통신 공통모듈(첨부파일 - submit)
 * 서버통신을 위한 파라미터는 form 변수에서 모두 정의한다.
 * formId: form ID
 * url: 서버통신 url
 * callback: 서버통신 종료후 구현될 callback명
 */
function doSubmit4File(formId, url, callback, message) {
	var arrLength = arguments.length;

	$("form[id=" + formId + "]").ajaxSubmit({
		url : url,
		type : "post",
		success : function(data){

			$("#resultHTML").html(data);

			var resultType = $("input[id=resultType]").val();
    			
			if (resultType == 'invalid.session') {
//				alert('invalid.session');
			} else {
				if (resultType == 'error.sys') {
					var error_message = document.getElementById("error_message").innerHTML;
					if(error_message.indexOf("Maximum upload size of") == 0){
						alert('최대 파일 업로드 크기를 초과 하였습니다.\n최대 업로드 크기: 4 GB');
					}else{
						alert('시스템 오류가 발생 하였습니다.\n오류가 계속되는 경우 관리자에게 문의하시기 바랍니다.');
					}
				} else if (resultType == 'error.biz') {
					//비즈니스 로직에서 발생한 메세지
					alert(document.getElementById("error_message").innerHTML);
				} else {
					if(arrLength == 3) {
						var operationName = "";
						if(new RegExp("Create.do","i").test(url)){
							operationName = "저장";
						}else if(new RegExp("Update.do","i").test(url)){
							operationName = "수정";
						}else if(new RegExp("Delete.do","i").test(url)){
							operationName = "삭제";
						}
						
						if (operationName != "") {
							message = operationName + " 되었습니다.";
						}
					}
	
					// CUD 성공 Or Error
					if (message != undefined && message != "") {
						alert(message);
					}
					// User collback
					eval(callback+'(data)');
				}
			}
		}
	});
}

//var _activeKey = null;

//AuthTree Init(정련 필요)
function doAuthTree(treeData, check) {
	$("#tree").dynatree({
		checkbox: check,
		selectMode: 3,
		autoCollapse: false, 
		clickFolderMode: 1,
		//persist: true,
		children: treeData,
		onSelect: function(select, node) {
			// Get a list of all selected nodes, and convert to a key array:
			//var selKeys = $.map(node.tree.getSelectedNodes(), function(node){
			//	return node.data.key;
			//});
			//$("#echoSelection3").text(selKeys.join(", "));

			// Get a list of all selected TOP nodes
			//var selRootNodes = node.tree.getSelectedNodes(true);
			// ... and convert to a key array:
			//var selRootKeys = $.map(selRootNodes, function(node){
			//	return node.data.key;
			//});
			//$("#echoSelectionRootKeys3").text(selRootKeys.join(", "));
			//$("#echoSelectionRoots3").text(selRootNodes.join(", "));
			onSelectTree(node.data.key, node.data.title);
		},
		onDblClick: function(node, event) {
			//node.toggleSelect();
			onDblClickTree(node.data.key, node.data.title, node);
		},
		onKeydown: function(node, event) {
			if( event.which == 32 ) {
				node.toggleSelect();
				return false;
			}
		},
		onClick: function(node, event) {
			onClickTree(node.data.key, node.data.title, node);
		},
		//onPostInit: function(isReloading, isError) {
     	// alert("reloading: "+isReloading+", error:"+isError);
     	// logMsg("onPostInit(%o, %o) - %o", isReloading, isError, this);
     	// Re-fire onActivate, so the text is updated
    	//	this.reactivate();
		//},
		onActivate: function(node) {
			onActivateTree(node.data.key, node.data.title);
		},        
		// The following options are only required, if we have more than one tree on one page:
		// initId: "treeData",
		// cookieId: "dynatree-Cb3",
		idPrefix: "dynatree-Cb3-"
	});
}

//Tree onClick에 대한 사용자 override 용 listener
function onSelectTree(key, name){
}
//Tree onClick에 대한 사용자 override 용 listener
function onClickTree(key, name){
}
//Tree onDblClick에 대한 사용자 override 용 listener
function onDblClickTree(key, name){
}
//Tree onClick에 대한 사용자 override 용 listener
function onActivateTree(key, name){
}


// ChargeTree Init(정련 필요)
function doChargeTree(treeData) {
	$("#tree").dynatree({
		children: treeData,

		onActivate: function(node) {
			$("#echoActive").text(node.data.key).append("(" + node.data.title + ")");
		},
		onDeactivate: function(node) {
			$("#echoActive").text("-");
		}
	});
}

// Calendar Init
function doCalendar(pickerId) {
	$("#" + pickerId).datepicker({
		changeYear: true,
		changeMonth: true,
		showOn: 'both', 
		buttonImage: '/images/common/btn_cale2.gif', 
		buttonText: '',
		buttonImageOnly: false
	});
	$.datepicker.regional['ko'] = {
			closeText: '닫기',
			prevText: '이전달',
			nextText: '다음달',
			currentText: '오늘',
			monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)','7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			monthNamesShort: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)','7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			dayNames: ['일','월','화','수','목','금','토'],
			dayNamesShort: ['일','월','화','수','목','금','토'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			dateFormat: 'yy-mm-dd', firstDay: 0,
			isRTL: false
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
}

function doCalendarName(pickerName) {
	$('[name$="'+ pickerName + '"]').datepicker({
		changeYear: true,
		changeMonth: true,
		showOn: 'both', 
		buttonImage: '/images/common/btn_cale2.gif', 
		buttonText: '',
		buttonImageOnly: false
	});
	$.datepicker.regional['ko'] = {
			closeText: '닫기',
			prevText: '이전달',
			nextText: '다음달',
			currentText: '오늘',
			monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)','7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			monthNamesShort: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)','7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			dayNames: ['일','월','화','수','목','금','토'],
			dayNamesShort: ['일','월','화','수','목','금','토'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			dateFormat: 'yy-mm-dd', firstDay: 0,
			isRTL: false
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
}

// Calendar Icon 호출
function doCalendarIcon(pickerId) {
	$("#" + pickerId + "").datepicker("show");
}

// Diary Init
function doDiary(diaryId, diaryData) {
	$('#' + diaryId).css("font-size", "11px");
//	$('#' + diaryId).css("text-align", "center");
	$('#' + diaryId).css("margin", "50 auto");
	$('#' + diaryId).css("width", "1100px");

	$('#' + diaryId).fullCalendar({
		header: {
			left: '',
			center: 'prevYear, prev, title, next, nextYear',
			right: 'today' // month,agendaWeek,agendaDay
		},
		editable: false,
		events: diaryData
	});
}

// tableScroll Init
function doTable(tableId, className, fixHeaderRow, fixLeftCol, widthArr, isResize) {
	if (arguments.length > 5 && isResize == "N") {
		new superTable(tableId, {
			cssSkin : className,
			colWidths: widthArr,
			headerRows: fixHeaderRow,
			fixedCols : fixLeftCol
		});
	} else {
		new superTable(tableId, {
			cssSkin : className,
			colWidths: widthArr,
			headerRows: fixHeaderRow,
			fixedCols : fixLeftCol,
			onFinish : function (headerCount, rowCount) {
				var fHeight = $(".fakeContainer").css("height");
//				alert(fHeight);
				var fakeHeight = fHeight.substring(0, fHeight.length - 2);
//				alert(fakeHeight);
				var realHeight = (34 * headerCount) + (rowCount * 31) + 2;
//d				
//				alert(headerCount);
//				alert(rowCount);
//				alert(realHeight + "<" + fakeHeight);
				
//				alert($(".fakeContainer").css("height"));
				
//				if (realHeight < fakeHeight) {
//					$(".fakeContainer").css("height", realHeight + "px");
//				}
			}
		});
	}
}

// Div 객체 제어
function doDivSH(op, divName, time) {
	if (op == "show") {
		$("#" + divName).show(time);
	} else if (op == "hide") {
		$("#" + divName).hide(time);
	} else if (op == "toggle") {
		$("#" + divName).toggle();
	} else if (op == "empty") {
		$("#" + divName).empty();
	} else if (op == "slideUp") {
		$("#" + divName).slideUp(time);
	} else if (op == "slideDown") {
		$("#" + divName).slideDown(time);
	} else if (op == "slideToggle") {
		$("#" + divName).slideToggle(time);
	}
}

// Modal Init
function doModal(mObjName, mIsModal, mEvent, mWidth, mHeight, mUrl) {
	$(mObjName).openDOMWindow({ 
		modal:mIsModal,	// 0: layer, 1: modal
		eventType:mEvent, // click, hover
		width:mWidth, 
		height:mHeight,
		windowSource:'iframe', 
		windowPadding:0,
		borderSize:'0', 
		windowSourceURL:mUrl
	});
}

//문자열 좌측의 공백 제거 처리 함수
function ltrim(para) {
	while (para.substring(0, 1) == ' ')
		para = para.substring(1, para.length);
	return para;
}

//문자열 중간의 공백 제거 처리 함수
function mtrim(para) {
	for ( var i = 0; i < para.length; i++) {
		if (para.substring(i, i + 1) == ' ')
			para = para.substring(0, i) + para.substring(i + 1, para.length);
	}
	return para;
}

//문자열 우측의 공백 제거 처리 함수
function rtrim(para) {
	while (para.substring(para.length - 1, para.length) == ' ')
		para = para.substring(0, para.length - 1);
	return para;
}

function onSubmitReturnFalse() {
	return false;
}	

/* Null 체크 */
function isNull(checkValue, strMessage) {
	if (checkValue.value == "") {
		alert(strMessage);
		checkValue.focus();
		return false;
	}
	return true;
}

/* Null 체크 및 공백제거 */
function isNullAndTrim(checkValue, strMessage) {
	checkValue.value = ltrim(checkValue.value);
	checkValue.value = rtrim(checkValue.value);
	return isNull(checkValue, strMessage);
}


/* 숫자 */
function isNumber(checkValue, strMessage) {
	var chk = checkValue.value;
	chk += ''; // 문자열로 변환
	chk = chk.replace(/^\s*|\s*$/g, ''); // 좌우 공백 제거
	if (chk == '' || isNaN(chk)) {
		if (strMessage != "") {
			alert(strMessage);
		}
		checkValue.focus();
		return false;
	}
	return true;
}

/* jquery Null 체크 */
function isNull_J(checkItem, strMessage) {
	if (checkItem.val() == "") {
		alert(strMessage);
		checkItem.focus();
		return false;
	}
	return true;
}

/* jquery Null 체크 및 공백제거 */
function isNullAndTrim_J(checkItem, strMessage) {
	checkItem.val(ltrim(checkItem.val()));
	checkItem.val(rtrim(checkItem.val()));
	return isNull_J(checkItem, strMessage);
}

/* jquery 숫자 체크 */
function isNumber_J(checkItem, strMessage) {
	var chk = checkItem.val();
	chk += ''; // 문자열로 변환
	chk = chk.replace(/^\s*|\s*$/g, ''); // 좌우 공백 제거
	if (chk == '' || isNaN(chk)) {
		if (strMessage != "") {
			alert(strMessage);
		}
		checkItem.focus();
		return false;
	}
	return true;
}



/* 함수 위치 추후 이동 고려 */
// 업체 선택 팝업 유틸
function init_bp_popup(){
	doModalPopup("img[id=open_bp_popup]", 1, "click", 783, 576, "/usermg/bp/Bp_Popup_ReadList.do");
}

//업체 선택 정보 콜백 Override 용
function fn_bp_popup_callback(bp_num, bp_name){
}

//SKT 사용자 선택 팝업 유틸
function init_sktuser_popup(){
	doModalPopup("img[id=open_sktuser_popup]", 1, "click", 783, 600, "/usermg/user/SktUser_Popup_Read.do");
}
function init_sktmanageruser_popup(){
	doModalPopup("img[id=open_sktuser_popup]", 1, "click", 783, 600, "/usermg/user/SktManagerUser_Popup_Read.do");
}

//SKT 사용자 선택시 부서선택가능
function init_sktuser_popup_detail(){
	doModalPopup("img[id=open_sktuser_popup]", 1, "click", 783, 600, "/usermg/user/SktUser_Popup_Read_Detail.do");
}
//SKT 사용자 선택 정보 콜백 Override 용
function fn_sktuser_popup_callback(user_id, user_name){
}

//시스템 선택 팝업 유틸
function init_system_popup(){
	doModalPopup("img[id=open_system_popup]", 1, "click", 478, 680, "/sys/system/System_Popup_Read.do");
}

//newpncr에서 등록시 시스템 두개 선택하기
function init_system_popup2(){
	doModalPopup("img[id=open_system_popup2]", 1, "click", 478, 680, "/sys/system/System_Popup_Read2.do");
}

function init_system_relation_popup_detail(){
	doModalPopup("img[name=open_relation_system_popup", 1, "click", 478, 680, "/sys/system/System_relation_Popup_Read_Detail.do");
}

function init_system_popup_pkgVer(){
	doModalPopup("img[id=open_system_popup]", 1, "click", 478, 660, "/sys/system/System_Popup_Read_All.do");
	
}

function init_system_auth_popup(){
	doModalPopup("img[id=open_system_popup]", 1, "click", 478, 660, "/sys/system/SystemAuth_Popup_Read.do");
}

//대분류 중분류 소분류 선택가능
function init_system_popup_detail(){
	doModalPopup("img[id=open_system_popup]", 1, "click", 478, 660, "/sys/system/System_Popup_Read_Detail.do");
}

//시스템 선택 정보 콜백 Override 용
function fn_system_popup_callback(system_key, system_name){
}

function doModalPopup(mObjName, mIsModal, mEvent, mWidth, mHeight, mUrl) {
	$(mObjName).openDOMWindow({ 
		modal:mIsModal,	// 0: layer, 1: modal
		eventType:mEvent, // click, hover
		width:mWidth, 
		height:mHeight,
		windowSource:'iframe', 
		windowPadding:0,
		borderSize:'0', 
		windowSourceURL:mUrl,
		bodyScroll:'N'
	});
}
function fn_number_validator(e){
 	 
	if(isNaN(e.value)){
		alert("정수만 입력 가능합니다.");
		e.value = "";
		e.focus();
	}
}



/************************************************************************
함수명 : fn_GetEvent
설 명  : 키코드 정보 획득
사용법 : fn_GetEvent(event) 
************************************************************************/
function fn_GetEvent(e)
{
    if(navigator.appName == 'Netscape')
    {
        keyVal = e.which;
    }
    else
    {
        keyVal = event.keyCode ;
    }
    return keyVal;
}
/************************************************************************
함수명 : fn_numbersonly
설 명  : 숫자만 입력되게 한다.
사용법 : onkeydown="fn_numbersonly(event);" style="ime-mode:disabled" 
************************************************************************/
function fn_numbersonly(evt)
{
    var myEvent = window.event ? window.event : evt;
    var isWindowEvent = window.event ? true : false;
    var keyVal = fn_GetEvent(evt);
    var result = false;
    if(myEvent.shiftKey)
    {
        result = false;
    }
    else
    {
    	                                                                                                         																	// '.'
        if((keyVal >= 48 && keyVal <=57) || (keyVal >= 96 && keyVal <=105) || (keyVal == 8) || (keyVal == 9)|| (keyVal == 46)|| (keyVal == 37)|| (keyVal == 39)|| (keyVal == 17)|| (keyVal == 190))
        {
            result = true;
        }
        else
        {
//        	alert("정수만 입력 가능합니다.");
            result = false;
        }
    }
    if(!result)
    {
        if(!isWindowEvent)
        {
            myEvent.preventDefault();
        }
        else
        {
            myEvent.returnValue=false;
        }
    }
}

/************************************************************************
javascript Map HashMap
************************************************************************/
Map = function() {
	this.map = new Object();
};
Map.prototype = {
	put : function(key, value) {
		this.map[key] = value;
	},
	get : function(key) {
		return this.map[key];
	},
	containsKey : function(key) {
		return key in this.map;
	},
	containsValue : function(value) {
		for ( var prop in this.map) {
			if (this.map[prop] == value)
				return true;
		}
		return false;
	},
	isEmpty : function(key) {
		return (this.size() == 0);
	},
	clear : function() {
		for ( var prop in this.map) {
			delete this.map[prop];
		}
	},
	remove : function(key) {
		delete this.map[key];
	},
	keys : function() {
		var keys = new Array();
		for ( var prop in this.map) {
			keys.push(prop);
		}
		return keys;
	},
	values : function() {
		var values = new Array();
		for ( var prop in this.map) {
			values.push(this.map[prop]);
		}
		return values;
	},
	size : function() {
		var count = 0;
		for ( var prop in this.map) {
			count++;
		}
		return count;
	}
};


/*Excel down*/ 
 
//템플릿 다운로드(템플릿 다운로드)
function fn_callback_file_download(data) {
	var file_name = $("input[id=param1]").val();
	downloadFile(file_name, file_name, "");
}

//파일 업로드 유효성 검사
function fn_upload(file, type){
/*
 if(chk != "xlsx" && chk != "jpg" && chk != "gif"
	 && chk != "bmp"
	 && chk != "pdf" && chk != "txt" && chk != "xls"
	 && chk != "doc" && chk != "ppt" && chk != "hwp"
	 && chk != "zip" && chk != "png") {
  alert(chk+"의 형식은 업로드 금지 파일입니다.");
 }*/
// {"xlsx","xlsm","xlsb","xls","mht","mhtml","xltx","xltm","xlt",
//		  "csv","prn","dif","slk","xlam","xla","xps","docx","docm","doc",
//		  "dotx","dotm","dot","rtf","wps","ppt","pptx","pptm","png",
//		  "potx","potm","pot","thmx","ppsx","ppsm","pps","ppam","ppa","tif",
//		  "wmf","emf","jpg","gif","bmp","pdf","txt","hwp","zip"}
	var value;
	if(type == "tree"){
		value = file.name;
	}else{
		value = file.value;
	}
	var Extension = value.substring(value.lastIndexOf(".") + 1);
	var chk = Extension.toLowerCase(Extension);
	var filechk = ["xlsx","xlsm","xlsb","xls","mht","mhtml","xltx","xltm","xlt",
	         	  "csv","prn","dif","slk","xlam","xla","xps","docx","docm","doc",
	         	  "dotx","dotm","dot","rtf","wps","ppt","pptx","pptm","png",
	         	  "potx","potm","pot","thmx","ppsx","ppsm","pps","ppam","ppa","tif",
	         	  "wmf","emf","jpg","gif","bmp","pdf","txt","hwp","zip",
	         	  "xlss","xlsxx","docc","docxx","pptt","pptxx"];
	var j = 0;
	for(var i = 0; i < filechk.length; i++){
		if(chk == filechk[i]){
			j++;
		}
	}
	if(j==0) {
		if(type == "tree"){
			return false;
		}else{
			alert(chk+"의 형식은 업로드 금지 파일입니다.");
			return false;
		}
	}else{
		return true;
	}
	
}

//말풍선 121116
function tip_message_over(id) {
	var obj = document.getElementById(id);
	if (obj != null) {
		obj.style.pixelLeft = window.event.clientX+2;
//		 ie8
//		alert(window.pageYOffset);
		if (window.pageYOffset == undefined){
			obj.style.pixelTop = window.event.clientY+document.body.scrollTop+2;
		}
		else {
			
			obj.style.pixelTop = window.event.clientY+window.pageYOffset+2;
		}
		obj.style.visibility = 'visible';
	}
}
function tip_message_out(id) {
	var obj = document.getElementById(id);
	if (obj != null) {
		obj.style.visibility = 'hidden';
	}
}

//작업건수 관리
function work_popup(gubun){
//	window.open("/Work_Popup_List.do?url_gubun="+gubun,"work_popup","width=600, height=750, scrollbars=yes, resizable=yes, statusbar=no, location=no");
}

//매뉴얼 팝업
function manual_popup(){ 
	var manualCookie = getCookie("manual_popup");
	if(manualCookie != "no"){
		window.open("/Manual_Popup_Notice.do","manual_popup","width=1000, height=845, scrollbars=no, resizable=no, statusbar=no, location=no");
	}
}

function owms() {
	window.open("http://owms.sktelecom.com", "_blank"); 
}

function cdrs() {
	window.open("http://cdrs.sktelecom.com", "_blank"); 
}

function tbpotal() {
	window.open("http://itcportal.sktelecom.com", "_blank"); 
}

function snet() {
	window.open("https://snet.sktelecom.com:10443", "_blank"); 
}

function setDatePicker(object, type, targetObject, eraserHidden, callback){
		$(object).datepicker({
			changeYear: true,
			changeMonth: true,
			showOn: 'both', 
			buttonImage: '/images/common/btn_cale2.gif', 
			buttonText: '',
			buttonImageOnly: false,
		    onSelect: function(dateText, o) {
		    	if(targetObject)
	    		{
		    		var date = $(this).datepicker('getDate');
		    		if(type == '<=')
		    		{
				    	$(targetObject).datepicker("option", "minDate", dateText ? new Date(date.getFullYear(), date.getMonth(), date.getDate()) : null);
		    		}
			    	else if(type == '>=')
		    		{
				    	$(targetObject).datepicker("option", "maxDate", dateText ? new Date(date.getFullYear(), date.getMonth(), date.getDate()) : null);
		    		}
			    	else if(type == '<')
		    		{
				    	$(targetObject).datepicker("option", "minDate", dateText ? new Date(date.getFullYear(), date.getMonth(), date.getDate()*1+1) : null);
		    		}
			    	else if(type == '>')
		    		{
				    	$(targetObject).datepicker("option", "maxDate", dateText ? new Date(date.getFullYear(), date.getMonth(), date.getDate()-1) : null);
		    		}

		    		if(typeof(callback) == 'function')
		    		{
		    			callback(object, type, targetObject, eraserHidden, callback, dateText, date);
		    		}
	    		}
		    }
		});
		$.datepicker.regional['ko'] = {
				closeText: '닫기',
				prevText: '이전달',
				nextText: '다음달',
				currentText: '오늘',
				monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)','7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
				monthNamesShort: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)','7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
				dayNames: ['일','월','화','수','목','금','토'],
				dayNamesShort: ['일','월','화','수','목','금','토'],
				dayNamesMin: ['일','월','화','수','목','금','토'],
				dateFormat: 'yy-mm-dd', firstDay: 0,
				isRTL: false
		};
		$.datepicker.setDefaults($.datepicker.regional['ko']);
}
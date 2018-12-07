<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="registerFlag" value="${empty Pkg21Model.pkg_seq ? '등록' : '수정'}" />

<html>
<head>

<!-- Css
<link rel="stylesheet" type="text/css" href="/css/style.css"/>
<link rel="stylesheet" type="text/css" href="/css/font.css"/>
 -->
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />

<!-- Js -->
<script type="text/javascript" src="/js/jquery/ui.core.js"></script>
<script type="text/javascript"  src="/js/pkgmg/pkgmg.wired.pkg21.js"></script>
<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/domwindow/jquery.DOMWindow.js"></script>

<script type="text/javaScript" defer="defer">
	$(document).ready(function($) {
		status_navi('1');//navi
		
		//시스템 선택
		init_system_popup();
		comment_yn("pe_yn");
		comment_yn("bypass_traffic");
		
		doCalendar("col1");
		doCalendar("col2");
		
		doCalendar("col3");
		doCalendar("col4");
		
		doCalendar("col5");
		doCalendar("col6");
		
				
			$('textarea[maxlength]').keydown(function(){
		        var max = parseInt($(this).attr('maxlength'));
		        var str = $(this).val();
				if(str.length > max) {
				    $(this).val(str.substr(0, max));
					alert("최대 [" + max + " 자]까지 입력 가능합니다.");
				}
			});
			
			 attachList();
			 
// 	 		 var pkg_seq = $("#pkg_seq").val();
// 	 		 if(pkg_seq.length != 0){
// 	 		 	$(window).unbind('beforeunload');
// 	 		 }
			 
		});

		
			
		function drag(obj) {
		    obj.on('dragenter', function (e) {
		        e.stopPropagation();
		        e.preventDefault();
		        $(this).css('border', '2px solid #5272A0');
		   });

		   obj.on('dragleave', function (e) {
		        e.stopPropagation();
		        e.preventDefault();
		        $(this).css('border', '2px dotted #8296C2');
		   });

		   obj.on('dragover', function (e) {
		        e.stopPropagation();
		        e.preventDefault();
		   });

		   obj.on('drop', function (e) {
		        e.preventDefault();
		        $(this).css('border', '2px dotted #8296C2');
		        
		        var files = e.originalEvent.dataTransfer.files;
		        if(files.length < 1){
		             return;
		        }

		        F_FileMultiUpload(files, obj);
		        e.preventDefault();
		   });
			}
		


	//파일 멀티 업로드
	function F_FileMultiUpload(files, obj) {
		var fileCount = files.length;//업로드 하는 파일개수
		var lastfilenum = $("#"+ obj.attr("name")+"_count").val();	//마지막 count
		var maxsize = $("#"+ obj.attr("name")+"_count").attr("name");	//maxsize;
		
		if((Number(fileCount)+Number(lastfilenum)) > maxsize){ // 최대 업로드 개수 > 업로드 파일 개수
			alert(maxsize+"개 이상의 파일을 올릴수 없습니다.");
			return;
		}
		
		
		if(confirm(fileCount + "개의 파일을 업로드 하시겠습니까?") ) {
	//파일 유효성 체크 start

	   	var boo = true; 
	  		for (var i = 0; i < fileCount; i++) {
	  			if(!fn_upload(files[i], "tree")){
	  				boo = false;         		
	  			}
	  		}

	  	    if(!boo){
	  			if ($.browser.msie) { // ie 일때 input[type=file] init.
//	   				$("#btn_upload").replaceWith( $("#btn_upload").clone(true) ); 
	  			} else { // other browser 일때 input[type=file] init.
//	   				$("#btn_upload").val(""); 
	  			}
	  	     	alert("파일형식이 금지된 파일이 포함되어 있습니다.");
	  	     	return;
	  	    }
	//파일 유효성 체크 end
	       	    
	        var data = new FormData();
	        for (var i = 0; i < files.length; i++) {
	           data.append('files', files[i]);
	        }
	        
	        var paidcall= obj.attr("name");
	        data.append("prefix", obj.attr("name") );
	        data.append('master_file_id', $("#master_file_id").val());
	       if( paidcall != "ttmattach"){
	        data.append('parent_tree_id', $("#"+ obj.attr("name")).val());
	       }else{
	    	data.append('parent_tree_id', '');
	       }
	        
	 		 $.ajax({
	       	 url:"/common/attachfile/new_file_add.do",
	           data: data,
	           type:'POST',
				data: data,
				async:false,
				cache:false,
				contentType:false,
				processData:false,
	           success: function(res) {
	       	   if(res == "error"){
		       		alert("파일을 확인해 주십시요.");
		       		return;
	           }
	           	$("#master_file_id").val(res);
	           	attachList();

	           },   
		        error:function(e){  
		            console.log(e.responseText);  
		        }
	        });
	    }
	}


	function delNewFile(attach_file_id, master_file_id, file_path, file_name) {
//	 	console.log(file_name);
		$.ajax({
		       type:"POST",  
		       url:"/common/attachfile/new_file_del.do",  
			   data:{
				   "master_file_id" : master_file_id
				   ,"attach_file_id" : attach_file_id
				   ,"file_path": file_path
				   ,"file_name" : file_name
				   },
		       success:function(data){
			        	$("#master_file_id").val(master_file_id);
			        	 attachList();
		       },   
		       error:function(e){  
		           console.log(e.responseText);  
		       }
		   });

	}




	function pageOutdelFiles( master_file_id) {
		$.ajax({
		       type:"POST",  
		       url:"/common/attachfile/new_file_del.do",  
			   data:{   "master_file_id" : master_file_id   },
		       success:function(data){
		    	   
		       },   
		       error:function(e){  
		           console.log(e.responseText);  
		       }
		   });

	}



	function attachfiles(menuid,list,type){ // 파일 내리기
		var html='';
		var html1='';
		var html2='';
		var count=0;
		
		if(type == "listfile"){
			for(var i=0; i < list.length; i++){
				if( menuid == list[i].pId  && list[i].pId != null){	
					++count;
					html1 +='<div id=\"'+list[i].attach_file_id+'\">';
					html1 +='<a href=\"#\" onclick=\"javascript:downloadFile(\''+list[i].file_name+'\',\''+list[i].name+'\',\''+list[i].file_path+'\'); return false;\">'+list[i].name+'</a>';
					html1 +='<input type=\"button\" onClick=\"javascript:delNewFile(\'' + list[i].attach_file_id +'\',\''+ $("#master_file_id").val() +'\',\''+ list[i].file_path+'\',\''+list[i].file_name+'\');\" class=\"btn_del\" title=\"삭제\">';
					html1 +='</div>';
				}
			}
			html = html1;
		}else{
			for(var i=0; i < list.length; i++){
				if(list[i].attach_file_id  != null && "ttmattach" == list[i].attach_file_id.substring(0,9)){//				ttm 첨부
						++count;
						html2 +='<div id=\"'+list[i].attach_file_id+'\">';
						html2 +='<a href=\"#\" onclick=\"javascript:downloadFile(\''+list[i].file_name+'\',\''+list[i].name+'\',\''+list[i].file_path+'\'); return false;\">'+list[i].name+'</a>';
						html2 +='<input type=\"button\" onClick=\"javascript:delNewFile(\'' + list[i].attach_file_id +'\',\''+ $("#master_file_id").val() +'\',\''+ list[i].file_path+'\',\''+list[i].file_name+'\');\" class=\"btn_del\" title=\"삭제\">';
						html2 +='</div>';
				}
			}
			html = html2
		}
			
		return [html, count];
	}


	function attachList(){ //첨부 리스트
		$.ajax({
	       type:"POST",  
	       url:"/sys/system/SystemFileData_Ajax_Read2.do",  
		   data:{"master_file_id" : $("#master_file_id").val(), "type" : "유선PKG개발결과","use_yn" : "Y"},
	       success:function(data){
		        var html='';	
		        var html2='';
		        var flag = true;
		        
		        for(var i=0; i < data.length; i++){
//	 	        	console.log(data);
					if(data[i].pId != 1000 && data[i].attach_file_id  == null){
						html += '<tr><th>'+data[i].name+'';
						if(data[i].required == "Y"){
							html += '<span class=\"txt_red\">*</span>';
						}
						html += '</th>';
						html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'\" name=\"id\" value=\"'+data[i].id+'\">';
						html +=	'<td>';
						var prantid = data[i].id ;
						var html3 =	attachfiles(data[i].id,data,"listfile");
						html +=	''+html3[0] ;
						if(html3[1] < data[i].maxlistsize ){
							html +=	'<input type=\"text\" name=\"'+data[i].eng_name+'\" id =\"dropzone'+(i+1)+'\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
							html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'_count\"'+' name='+data[i].maxlistsize+' value=\"'+html3[1]+'\" >';
							html += '<span class=\"mt02\">';
							html += '<input type=\"file\"  size=\"50\"  onchange=\"javascript:docfile_upload(this , \''+data[i].eng_name+'\');\"  >';
							html += '</span>';
						}
						html +=	'</td></tr>';	
			
					}else{
						if(data[i].attach_file_id  != null){
							if("ttmattach" == data[i].attach_file_id.substring(0,9) && flag ){
								var htmlttm =	attachfiles(data[i].id,data,"ttm"); //	ttm 첨부
								
								html2 +=	''+htmlttm[0] ;
								if(htmlttm[1] < 5 ){
									html2 += '<input name=\"ttmattach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
									html2 += '<input type=\"hidden\" id=\"ttmattach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
									html2 += '<span class=\"mt02\">';
									html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'ttmattach\' );\">';
									html2 += '</span>';
								}
								flag = false;
							}
						}
					}
				}
		 		
		 		if(html2.length == 0){
					html2 += '<input name=\"ttmattach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
					html2 += '<span class=\"mt02\">';
					html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'ttmattach\' );\">';
					html2 += '</span>';
				}
		 			 		
		 		$('#ttmdrop').empty();
		 		$('#ttmdrop').append(html2);
		 		
				$('#attachlist > tbody').empty();
				$('#attachlist > tbody:last').append(html);

				var dopzoncount=(data.length+1);
				for(k=0;k<dopzoncount;k++){
					obj = $("#dropzone"+k);
						drag(obj);
				}  
	       },   
	       error:function(e){  
	           console.log(e.responseText);  
	       }
	   });
	}



	// bt_btn  // 버튼 클릭시 예외
	// fn_save_101()
	// 페이지 나갈때 저장한하면 업로드 파일 전부 del
	$(window).on("beforeunload", function() {
		var pkg_seq = $("#pkg_seq").val();
		if(pkg_seq != null && pkg_seq.length != 0){
			
		}else{
		 pageOutdelFiles( $("#master_file_id").val()) ;		
		}
		
		 return;
	});

	// $('form').submit(function(){
	//   $(window).unbind('beforeunload');
	// });



	function docfile_upload(file , prefix){
//	 			alert(prefix);
			var value = file.value;
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
				alert(chk+"의 형식은 업로드 금지 파일입니다.");
				return
			}

			var data = new FormData();
			file = file.files[0]; //파일로 변환입력
			data.append('files',file);
			var paidcall= prefix;
			data.append("prefix", paidcall );
			data.append('master_file_id', $("#master_file_id").val());
			
	       if( paidcall != "ttmattach"){
	        data.append('parent_tree_id', $("#"+paidcall).val());
	       }else{
	    	data.append('parent_tree_id', '');
	       }
	        
	 		 $.ajax({
	       	 url:"/common/attachfile/new_file_add.do",
	           data: data,
	           type:'POST',
				data: data,
				async:false,
				cache:false,
				contentType:false,
				processData:false,
	           success: function(res) {
	        	if(res == "error"){
		       		alert("파일을 확인해 주십시요.");
		       		return;
	           	}
	           	$("#master_file_id").val(res);
	           	attachList();

	           },   
		        error:function(e){  
		            console.log(e.responseText);  
		        }
	        });

	}

	var fnLoadList = function () {
		$("#Pkg21Model").attr("action","/pkgmg/wired/Wired_Pkg21_ReadList.do"); 
		$("#Pkg21Model").submit();
	}

</script>

</head>
<body>
<form:form commandName="Pkg21Model" method="post">
	<form:hidden path="pkg_seq" />
	<form:hidden path="select_status" />
	<form:hidden path="status" />
	<form:hidden path="status_dev" />
	<form:hidden path="read_gubun" />
	<form:hidden path="pe_type" />
	
	<form:hidden path="col43" />
	
	<form:hidden path="master_file_id" />

	<form:hidden path="pageIndex"		value="${param.pageIndex }"/>
	<form:hidden path="date_start"		value="${param.date_start }"/>
	<form:hidden path="date_end"   		value="${param.date_end }"/>
	<form:hidden path="statusCondition"	value="${param.statusCondition }"/>
	<form:hidden path="userCondition"	value="${param.userCondition }"/>
	<form:hidden path="group1Condition"	value="${param.group1Condition }"/>
	<form:hidden path="group2Condition"	value="${param.group2Condition }"/>
	<form:hidden path="searchKeyword"	value="${param.searchKeyword }"/>

<!-- SVT 계획수립 -->
<div class="new_con_Div32">
	<!-- 현황 예제1 -->
	<div class="sub_contents01 fl_wrap">
		<div class="sub_flow_wrap_100">
			<ul class="sub_flow_line01 fl_wrap">
				<li id="status_navi1" class="sub_flow">
					<p id="status_navi_fn1" class="on" style="cursor:pointer; display:none;" onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
						PKG개발 결과
					</p>
					<p id="status_navi_ing1" class="ing" style="cursor:pointer; display:none; " onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">	PKG개발 결과</span>
					</p>
					<p id="status_navi_now1" class="on_blue">
						PKG개발 결과
					</p>
					<p id="status_navi_non1" class="w_long"  style="display:none; " >
						<img src="/images/ic_flow.png">
						<span class="over_text">PKG개발 결과</span>
					</p>
				</li>
				
				<li id="status_navi2" class="sub_flow">
					<p id="status_navi_fn2" class="on" style="cursor: pointer; display:none; " onclick="javascript:fn_pkg21_status_read('301');">
						개발검증
					</p>
					<p id="status_navi_ing2" class="ing" style="cursor: pointer; display:none; " onclick="javascript:fn_pkg21_status_read('301');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">개발검증</span>
					</p>
					<p id="status_navi_now2" class="on_blue" style="display:none;" >
						개발검증
					</p>
					<p id="status_navi_non2" class="w_long" style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">개발검증</span>
					</p>
				</li>
				<li id="status_navi3" class="sub_flow">
					<p id="status_navi_fn3"  class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('331');">
						초도적용계획수립
					</p>
					<p id="status_navi_ing3"  class="ing" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('331');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">초도적용계획수립</span>
					</p>
					<p id="status_navi_now3" class="on_blue" style="display:none;" >
						초도적용계획수립
					</p>
					<p id="status_navi_non3" class="w_long" style=" " >
						<img src="/images/ic_flow.png">
						<span class="over_text">초도적용계획수립</span>
					</p>
				</li>
				<li id="status_navi4" class="sub_flow">
					<p id="status_navi_fn4" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('336');">
						초도적용 결과
					</p>
					<p id="status_navi_ing4" class="ing"  style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('336');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">초도적용 결과</span>
					</p>
					<p id="status_navi_now4" class="on_blue" style="display:none;" >
						초도적용 결과
					</p>
					<p id="status_navi_non4" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">초도적용 결과</span>
					</p>
				</li>
				<li id="status_navi5" class="sub_flow">
					<p id="status_navi_fn5" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('341');">
						확대적용계획수립
					</p>
					<p id="status_navi_ing5" class="ing" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('341');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">확대적용계획수립</span>
					</p>
					<p id="status_navi_now5" class="on_blue" style="display:none;" >
						확대적용계획수립
					</p>
					<p id="status_navi_non5" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">확대적용계획수립</span>
					</p>
				</li>
				<li id="status_navi6" class="sub_flow">
					<p id="status_navi_fn6" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('343');">
						확대적용결과
					</p>
					<p id="status_navi_ing6" class="ing" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_status_read('343');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">확대적용결과</span>
					</p>
					<p id="status_navi_now6" class="on_blue" style="display:none;" >
						확대적용결과
					</p>	
					<p id="status_navi_non6" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">확대적용결과</span>
					</p>
				</li>
				
				<li id="status_navi7" class="sub_flow">
					<p id="status_navi_fn7" class="on" style="cursor: pointer;display:none;  ">
						완료
					</p>
					<p id="status_navi_non7" class="w_long"  style="  " >
						<img src="/images/ic_flow.png">
						<span class="over_text">완료</span>
					</p>
				</li>
			</ul>
		</div>	
		<!-- <div class="sub_flow_wrap fl_left">	
			<ul class="sub_flow_line02 fl_wrap">
				<li class="sub_flow">
					<p style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('161');">
						<img src="/images/ic_flow.png">
						<span class="over_text">과금검증</span>
					</p>
				</li>
				<li class="sub_flow">
					<p style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('151');">
						<img src="/images/ic_flow.png">
						<span class="over_text">용량검증</span>
					</p>
				</li>
				<li class="sub_flow" >
					<p style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('171');">
						<img src="/images/ic_flow.png">
						<span class="over_text">보안검증</span>
					</p>
				</li>
			</ul>
			<ul class="sub_flow_line03 fl_wrap">
				<li class="sub_flow">
					<p class="w_long" style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('131');">
						<img src="/images/ic_flow.png">
						<span class="over_text">초도적용 계획수립</span>
					</p>
				</li>
				<li class="sub_flow">
					<p style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('133');">
						<img src="/images/ic_flow.png">
						<span class="over_text">초도적용 결과</span>
					</p>
				</li>
				<li class="sub_flow">
					<p class="w_long"style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('141');">
						<img src="/images/ic_flow.png">
						<span class="over_text">확대적용 계획수립</span>
					</p>
				</li>
				<li class="sub_flow">
					<p style="cursor: pointer;" onclick="javascript:fn_pkg21_status_read('143');">
						<img src="/images/ic_flow.png">
						<span class="over_text">확대적용 결과</span>
					</p>
				</li>
				<li class="sub_flow">
					<p>
						<img src="/images/ic_flow.png">
						<span class="over_text">완료</span>
					</p>
				</li>
			</ul>
		</div> -->
	</div>
	

<!-- 			본문 내용 시작-->
	<div id="pkg21_status_content">
		<div class="sub_title fl_wrap">
			<h2 class="fl_left">기본정보</h2>
			<div class="sub_con_close2">
				<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
			</div>
		</div>
		<div class="con_Div2 con_area fl_wrap"  id="con_list" style="display: block">
<%-- 			status:	${Pkg21Model.status}   a:	${Pkg21Model.select_status} --%>
			<div class="table_style01">
				<table>
					<colgroup>
						<col width="12%">
						<col width="30%">
						<col width="10%">
						<col width="48%">
					</colgroup>
					<tbody>
						<tr>
							<th>시스템</th>
							<td colspan="3">
								<form:hidden path="system_seq" />
									<form:input path="system_name" maxlength="50" class="fl_left" style="width:500px" readonly="true" />
								<c:if test="${registerFlag == '등록' }">
									<span class="btn_line_blue2 ml05">
										<img src="/images/pop_btn_search1.gif" alt="선택" style="cursor: pointer;" id="open_system_popup"/>
									</span>
								</c:if>
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<th>개발검증요청</th> -->
<!-- 							<td colspan="3"> -->
<%-- 								<c:choose> --%>
<%-- 									<c:when test="${registerFlag == '등록'}"> --%>
<%-- 										<form:radiobuttons path="dev_yn" items="${Pkg21Model.dev_yn_list}" itemLabel="codeName" itemValue="code" />													 --%>
<%-- 									</c:when> --%>
<%-- 									<c:otherwise> --%>
<%-- 										<form:hidden path="dev_yn" /> --%>
<%-- 										<c:choose> --%>
<%-- 											<c:when test="${Pkg21Model.dev_yn == 'Y'}"> --%>
<!-- 												<span class="mg03 ml05 mr25">DVT/CVT 검증요청</span> -->
<%-- 											</c:when> --%>
<%-- 											<c:when test="${Pkg21Model.dev_yn == 'N'}"> --%>
<!-- 												<span class="mg03 ml05 mr25">DVT 검증요청(개발검증 생략)</span> -->
<%-- 											</c:when> --%>
<%-- 											<c:otherwise> --%>
<!-- 												<span class="mg03 ml05 mr25">DVT/CVT 동시검증요청</span> -->
<%-- 											</c:otherwise> --%>
<%-- 										</c:choose> --%>
<%-- 									</c:otherwise> --%>
<%-- 								</c:choose> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
						<tr>
							<th>제목</th>
							<td colspan="3">
								<c:choose>
									<c:when test="${registerFlag == '등록'}">
										시스템 선택 후 버전 및 버전구분 등록 시 자동생성
									</c:when>
									<c:otherwise>
										<span class="mg03 ml05 mr25">${Pkg21Model.title}</span>
										<form:hidden path="title" />									
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>작업난이도 <span class="txt_red">*</span></th>
							<td class="sysc_inp">
								<form:select path="work_level" items="${Pkg21Model.work_level_list}" itemLabel="codeName" itemValue="code" />
							</td>

							<th>중요도 <span class="txt_red">*</span></th>
							<td class="sysc_inp">
								<form:select path="important" items="${Pkg21Model.important_list}" itemLabel="codeName" itemValue="code" />
							</td>
						</tr>
						<tr>	
							<th>작업단위 </th>
							<td class="sysc_inp">
								추후결정
							</td>
							<th>PKG버전 <span class="txt_red">*</span></th>
							<td>
								<form:input path="ver" class="new_inp" maxlength="10" />
							</td>
						</tr>
						<tr>	
							<th>버전구분 <span class="txt_red">*</span></th>
							<td>
								<input type="hidden"  id="patch_yn" name="patch_yn" value="${Pkg21Model.patch_yn}" />
								<c:choose>
									<c:when test="${registerFlag == '등록'}">
										<form:radiobuttons path="ver_gubun" items="${Pkg21Model.ver_gubun_list}" itemLabel="codeName" itemValue="code" onclick="javascript:fn_ver_gubun_change(this);" />
										
														
<!-- 										<input type="checkbox" class="fl_left" name="patch_yn" onchange="javascript:fn_patch_yn_change(this, 'patch');" /> -->
<!-- 										<span class="mg03 ml05">Patch (</span> -->
										
<%-- 										<input type="checkbox" class="fl_left" name="cha_yn" onclick="return false;" value="${Pkg21Model.cha_yn}" onchange="fn_checkbox_yn_change(this, 'cha')"/> --%>
<!-- 										<span class="mg03 ml05">과금검증</span> -->
<%-- 										<input type="checkbox" class="fl_left" name="vol_yn" onclick="return false;" value="${Pkg21Model.vol_yn}" checked="checked" onchange="fn_checkbox_yn_change(this, 'vol')" /> --%>
<!-- 										<span class="mg03 ml05">용량시험 &nbsp;)</span> -->

									</c:when>
									<c:otherwise>
<%-- 										<form:hidden path="ver_gubun" /> --%>
										<c:choose>
											<c:when test="${Pkg21Model.patch_yn == 'Y'}">
												<span>
													<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun1" disabled="yes" value="F"/> 
													<label for="ver_gubun1">Full</label>
												</span>
												<span>	
													<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun2" disabled="yes"  value="P"/> 
													<label for="ver_gubun2">Partial</label>
												</span>
												<span>	
													<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun3" checked="checked" value="C"/> 
													<label for="ver_gubun3">Patch</label>
												</span>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${Pkg21Model.ver_gubun == 'F'}">
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun1" checked="checked" value="F"/> 
															<label for="ver_gubun1">Full</label>
														</span>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun2" disabled="yes" value="P"/> 
															<label for="ver_gubun2">Partial</label>
														</span>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun3" disabled="yes" value="C"/> 
															<label for="ver_gubun3">Patch</label>
														</span>
													</c:when>
													<c:otherwise>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun1" disabled="yes" value="F"/> 
															<label for="ver_gubun1">Full</label>
														</span>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun2" checked="checked" value="P"/> 
															<label for="ver_gubun2">Partial</label>
														</span>
														<span>
															<input type="radio" name="ver_gubun" class="fl_left" id="ver_gubun3" disabled="yes" value="C" /> 
															<label for="ver_gubun3">Patch</label>
														</span>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
								<!-- <input type="checkbox" class="fl_left">
								<span class="mg03 ml05">Patch (</span>
								<input type="checkbox" class="fl_left">
								<span class="mg03 ml05">과금검증</span>
								<input type="checkbox" class="fl_left">
								<span class="mg03 ml05">용량시험 )</span> -->
							</td>
							
							
							
							<th>검증유형 <span class="txt_red">*</span></th>
							<td>
							<c:choose>
									<c:when test="${registerFlag == '등록'}">
										<span>
											<input type="checkbox" class="fl_left" name="cha_yn" id="cha_yn"   onclick="return false;" value="${Pkg21Model.cha_yn}" />
											<label for="cha_yn">과금검증</label>
										</span>
										<span>
											<input type="checkbox" class="fl_left" name="vol_yn" id="vol_yn" checked="checked"  value="${Pkg21Model.vol_yn}"/>
											<label for="vol_yn">용량검증</label>
										</span>
										
									</c:when>
									<c:otherwise>
									
										<span>
											<c:if test="${Pkg21Model.cha_yn eq 'N'}">
	<%-- 											<input type="checkbox" class="fl_left" name="cha_yn"  value="${Pkg21Model.cha_yn}"  onclick="return false;" /> --%>
												<input type="checkbox" class="fl_left" name="cha_yn"  value="N"  onclick="return false;" />
											</c:if>
											<c:if test="${Pkg21Model.cha_yn eq 'Y' || Pkg21Model.cha_yn eq 'S'}">
												<c:if test="${Pkg21Model.cha_yn eq 'Y'}">
													<input type="checkbox" class="fl_left" name="cha_yn"  value="Y"  onclick="return false;" checked="checked"/>
												</c:if>
												<c:if test="${Pkg21Model.cha_yn eq 'S'}">
													<input type="checkbox" class="fl_left" name="cha_yn"  value="S"  onclick="return false;" checked="checked"/>
												</c:if>
											</c:if>
											<label for="cha_yn">과금검증</label>
										</span>
										
										<span>
											<c:if test="${Pkg21Model.vol_yn eq 'Y' || Pkg21Model.vol_yn eq 'S'}">
												<c:if test="${Pkg21Model.vol_yn eq 'Y'}">
													<input type="checkbox" class="fl_left" name="vol_yn"  value="Y"  onclick="return false;" checked="checked"/>
												</c:if>
												<c:if test="${Pkg21Model.vol_yn eq 'S'}">
													<input type="checkbox" class="fl_left" name="vol_yn"  value="S"  onclick="return false;" checked="checked"/>
												</c:if>
											</c:if>
											<c:if test="${Pkg21Model.vol_yn eq 'N'}">
												<input type="checkbox" class="fl_left" name="vol_yn" onclick="return false;"  value="N"/>
											</c:if>
											<label for="vol_yn">용량검증 &nbsp;</label>
										</span>
									</c:otherwise>
							</c:choose>
<%-- 								<span class="mg03 ml05">용량검증&nbsp;<input type="checkbox" class="fl_left" name="vol_yn" onclick="return false;" value="${Pkg21Model.vol_yn}"/></span> --%>
<%-- 								<span class="mg03 ml05">과금검증&nbsp;<input type="checkbox" class="fl_left" name="vol_yn" onclick="return false;" value="${Pkg21Model.vol_yn}"/></span> --%>
							</td>
						</tr>
						<tr>
							<th>신규내역</th>
							<td colspan="3">
								<form:textarea path="content" rows="5" class="textarea_w95" maxlength="550" />
							</td>
						</tr>
						<tr>
							<th>주요보완내역 (PN)</th>
							<td colspan="3">
								<form:textarea path="content_pn" rows="5" class="textarea_w95" maxlength="550" />
							</td>
						</tr>
						<tr>
							<th>주요개선내역 (CR)</th>
							<td colspan="3">
								<form:textarea path="content_cr" rows="5" class="textarea_w95" maxlength="550" />
							</td>
						</tr>
						<tr>
							<th>자체개선내역</th>
							<td colspan="3">
								<form:textarea path="content_self" rows="5" class="textarea_w95" maxlength="550" />
							</td>
						</tr>
						<tr>
							<th>TTM</th>
							<td colspan="3">
								<form:textarea path="ttm" rows="5" class="textarea_w95 mb05" maxlength="550" />
								
								<div id="ttmdrop" name="ttmdrop">
									페이지 로딩시에 스크립트에서 다시 내려받는다.
									<input name="ttmattach" id="dropzone0" type="text" class="new_inp new_inp_w3 fl_left mb05">
									<span class="btn_line_blue2 ml10 mt02">
										<a href="#">첨부파일</a>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<th>서비스 중단시간</th>
							<td colspan="3">
								<form:input path="ser_downtime" class="new_inp inp_w100px" maxlength="8" onkeydown="javascript:fn_numbersonly(event);" />
								분
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<th>로밍영향도</th> -->
<!-- 							<td colspan="3"> -->
<%-- 								<form:radiobuttons path="roaming_link" items="${Pkg21Model.roaming_link_list}" itemLabel="codeName" itemValue="code" class="fl_left" /> --%>
<!-- 								<input type="radio" class="fl_left">
<!-- 								<span class="mg03 ml05 mr25">로밍연동 있음 </span> -->
<!-- 								<input type="radio" class="fl_left"> -->
<!-- 								<span class="mg03 ml05 mr25">로밍연동 없음</span> --> 
<!-- 							</td> -->
<!-- 						</tr> -->
						<tr>
							<th>우회소통</th>
							<td colspan="3">
								<form:radiobuttons path="bypass_traffic" items="${Pkg21Model.bypass_traffic_list}" itemLabel="codeName" itemValue="code" class="fl_left" onChange="javascript:comment_yn('bypass_traffic');" />
								<form:input path="bypass_traffic_comment" maxlength="100" class="new_inp inp_w50" />
								<!-- <input type="radio" class="fl_left">
								<span class="mg03 ml05 mr25">있음 </span>
								<input type="radio" class="fl_left">
								<span class="mg03 ml05 mr25">없음</span>
								<input type="text" class="new_inp inp_w50"> -->
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<th>과금영향도</th> -->
<!-- 							<td colspan="3"> -->
<%-- 								<form:radiobuttons path="pe_yn" items="${Pkg21Model.pe_yn_list}" itemLabel="codeName" itemValue="code" onChange="javascript:comment_yn('pe_yn');" class="fl_left" /> --%>
<%-- 								<form:input path="pe_yn_comment" maxlength="100" class="new_inp inp_w50" /> --%>
<!-- 								<input type="radio" class="fl_left">
<!-- 								<span class="mg03 ml05 mr25">영향도 있음 </span> -->
<!-- 								<input type="radio" class="fl_left"> -->
<!-- 								<span class="mg03 ml05 mr25">영향도 없음</span> -->
<!-- 								<input type="text" class="new_inp inp_w50"> --> 
<!-- 							</td> -->
<!-- 						</tr> -->
					</tbody>
				</table>
			</div>	
		</div>
		
		
	<div class="sub_contents02"  id="con_list" style="display: block">
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tbody>
					<tr>
						<th>
						개발기간</th>
						<td >
							<form:input path="col1" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col2" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th>검증기간<span class="txt_red">*</span></th>
						<td >
							<form:input path="col3" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col4" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>
					<tr>
						<th>적용예정기간 <span class="txt_red">*</span></th>
						<td >
							<form:input path="col5" class="new_inp fl_left" readOnly="true" />
							<span class="fl_left mg05"> ~ </span>
							<form:input path="col6" class="new_inp fl_left" readOnly="true" />
						</td>
					</tr>

				</tbody>
			</table>
		</div>	
	</div>		
		
		<div class="sub_title fl_wrap">
			<h2 class="fl_left">첨부</h2>
			<div class="sub_con_close2">
				<span id="con_open1" style="cursor: pointer;" onclick="javascript:fn_open_and_close('1');">접기</span>
			</div>
		</div>
		<div class="sub_contents02"  id="con_list1" style="display: block">
			
			<div class="table_style01">
				<table id ="attachlist" class="con_width100">
					<colgroup>
						<col width="22%">
						<col width="78%">
					</colgroup>
					<tbody>
						<tr>
							<th>요구사항 명세서(요건서 or 명세서)</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05">
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>설계 Review 결과서</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
						<tr>
							<th>규격서</th>
							<td>
								<input type="text" class="new_inp new_inp_w3 fl_left mb05"> 
								<span class="btn_line_blue2 ml10 mt02"><a href="#">첨부파일</a></span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="sub_contents01">
			<div class="write_info fl_wrap">
				<c:if test="${registerFlag == '수정'}">
					등 록
					<span class="name2">${Pkg21Model.reg_user_name} (${Pkg21Model.reg_date})</span>
					수 정
					<span class="name2">
						${Pkg21Model.update_user_name} (${Pkg21Model.update_date})
					</span>
				</c:if>
			</div>
		</div>
		<div class="bt_btn_wrap fl_wrap">
		<a href="javascript:fnLoadList();" class="bt_btn1 btn_blue">목록</a>
		<c:if test="${Pkg21Model.select_status < '301'}">
			<span class="btn_org2">
				<a href="javascript:fn_save_300();">임 시 저 장</a>
			</span>
<%-- 		</c:if>	 --%>
<%-- 			<c:if test="${Pkg21Model.select_status == '300'}"> --%>
				<span class="btn_org2">
					<a href="javascript:fn_save_301();">완 료</a>
				</span>
		</c:if>
		</div>
	</div>
</div>
</form:form>
</body>
</html>

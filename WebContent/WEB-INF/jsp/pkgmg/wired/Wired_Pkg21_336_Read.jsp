<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://com.wings/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<link type="text/css" rel="stylesheet" href="/css/jquery_ui/ui.all.css" />

<script type="text/javascript" src="/js/jquery/datepicker/ui.datepicker.js"></script>
<script type="text/javascript" src="/js/jquery/ui.js"></script>
<script type='text/javascript' src="/js/pkgmg/pkgmg.wired.pkg21.js"></script>

<script type="text/javaScript" defer="defer">

$(document).ready(
		function($) {
			status_navi('4');//navi
			attachList();//첨부
						
		}
	);






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
			

			
//			if(obj.attr("name") == "cvtResult"){
//	  			var chvalue = files[i].name;
//				var Extension = chvalue.substring(chvalue.lastIndexOf(".") + 1);
//				var chk = Extension.toLowerCase(Extension);
			
//				var filechk =["xlsx"];
			
//				var j = 0;
//				for(var i = 0; i < filechk.length; i++){
//					if(chk == filechk[i]){
//						j++;
//					}
//				}
//				if(j==0) {
//					boo = false;
//				}
//			}
			
			
		}

	    if(!boo){
			if ($.browser.msie) { // ie 일때 input[type=file] init.
//				$("#btn_upload").replaceWith( $("#btn_upload").clone(true) ); 
			} else { // other browser 일때 input[type=file] init.
//				$("#btn_upload").val(""); 
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
   if( paidcall != "wiredFirstCheckListAttach"   ){
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
//	console.log(file_name);
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
		if(list[i].attach_file_id  != null && "wiredFirstCheckListAttach" == list[i].attach_file_id.substring(0,25)  && type == "wiredFirstCheckList"){//				ttm 첨부
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
   data:{"master_file_id" : $("#master_file_id").val(), "type" : "유선PKG검증결과","use_yn" : "Y"},
   success:function(data){
        var html='';	
        var html2='';
        
        var htmlcom1='';
        var htmlcom2='';
        var htmlcom3='';
        
        var flag1 = true;
        var flag2 = true;
        var flag3 = true;
        var flag4 = true;

        for(var i=0; i < data.length; i++){
////	        	console.log(data);
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
					html +=	'<input type=\"text\" name=\"'+data[i].eng_name+'\" id =\"dropzone'+(i+4)+'\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
					html += '<input type=\"hidden\" id=\"'+data[i].eng_name+'_count\"'+' name='+data[i].maxlistsize+' value=\"'+html3[1]+'\" >';
//						html += '<span class=\"btn_line_blue2 ml10 mt02\">';
					html += '<input type=\"file\" size=\"50\"  onchange=\"javascript:docfile_upload(this , \''+data[i].eng_name+'\');\"  >';
//						html += '</span>';
				}
				html +=	'</td></tr>';	
				
	
			}else{
				if(data[i].attach_file_id  != null){
//						if("cvtPComAttach1" == data[i].attach_file_id ){wiredResultdrop
					if("wiredFirstCheckListAttach" == data[i].attach_file_id.substring(0,25) &&  flag1 ){
						var htmlttm =	attachfiles(data[i].id,data,"wiredFirstCheckList"); //	cvtcomment
						
						html2 +=	''+htmlttm[0] ;
						if(htmlttm[1] < 5 ){
							html2 += '<input name=\"wiredFirstCheckListAttach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
							html2 += '<input type=\"hidden\" id=\"wiredFirstCheckListAttach_count\"'+' name="5" value=\"'+htmlttm[1]+'\" >';
//								html2 += '<span class=\"btn_line_blue2 ml10 mt02\">';
							html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'wiredFirstCheckListAttach\' );\">';
//								html2 += '</span>';
						}
						flag1 = false;
					}
					
					
					
				}
			}
		}
 		
 		if(html2.length == 0){
			html2 += '<input name=\"wiredFirstCheckListAttach\" id=\"dropzone0\" type=\"text\" class=\"new_inp new_inp_w3 fl_left mb05 mr05\" placeholder=\"여기에 파일을 드래그해주세요\" >';
//				html2 += '<span class=\"btn_line_blue2 ml10 mt02\">';
			html2 += '<input type=\"file\"  size=\"50\" onchange=\"javascript:docfile_upload(this , \'wiredFirstCheckListAttach\' );\">';
//				html2 += '</span>';
 		}

 		
 		$('#wiredFirstCheckListdrop').empty();
 		$('#wiredFirstCheckListdrop').append(html2);

 		
//			$('#attachlist > tbody:').empty();
//			$('#attachlist > tbody:last').append(html);
 		
		$('#attachlist').empty();
		$('#attachlist').append(html);
		

		var dopzoncount=(data.length+4); // 리스트 제외하고 comment에 있는 첨부가 4개 이기때문에 +4
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



//bt_btn  // 버튼 클릭시 예외
//fn_save_101()
//페이지 나갈때 저장한하면 업로드 파일 전부 del
$(window).on("beforeunload", function() {
var pkg_seq = $("#pkg_seq").val();
if(pkg_seq != null && pkg_seq.length != 0){
	
}else{
 pageOutdelFiles( $("#master_file_id").val()) ;		
}

 return;
});

//$('form').submit(function(){
//$(window).unbind('beforeunload');
//});















function docfile_upload(file , prefix){
//			alert(prefix);
	var value = file.value;
	var Extension = value.substring(value.lastIndexOf(".") + 1);
	var chk = Extension.toLowerCase(Extension);
	
	var filechk = ["xlsx","xlsm","xlsb","xls","mht","mhtml","xltx","xltm","xlt",
	         	  "csv","prn","dif","slk","xlam","xla","xps","docx","docm","doc",
	         	  "dotx","dotm","dot","rtf","wps","ppt","pptx","pptm","png",
	         	  "potx","potm","pot","thmx","ppsx","ppsm","pps","ppam","ppa","tif",
	         	  "wmf","emf","jpg","gif","bmp","pdf","txt","hwp","zip",
	         	  "xlss","xlsxx","docc","docxx","pptt","pptxx"];
	
	if(prefix == "cvtResult"){
		filechk =["xlsx"];
	}
	
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

	
	file = file.files[0]; //파일로 변환입력

    var data = new FormData();
//     for (var i = 0; i < files.length; i++) {
//        data.append('files', files[i]);
//     }
     data.append('files',file);
    var paidcall= prefix;
    data.append("prefix", paidcall );
    data.append('master_file_id', $("#master_file_id").val());
    if( paidcall != "wiredFirstCheckListAttach" ){
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




	



</script>

</head>
<body>
<form:form commandName="Pkg21Model" method="post" onsubmit="return false;">
	<form:hidden path="pkg_seq" />
	<form:hidden path="select_status" />
	<form:hidden path="status" />
	<form:hidden path="status_dev" />
	<form:hidden path="read_gubun" />
	<form:hidden path="dev_yn" />
	<form:hidden path="system_seq" />
	<form:hidden path="content" />
	<form:hidden path="content_pn" />
	<form:hidden path="content_cr" />
	<form:hidden path="content_self" />
	<form:hidden path="content_invest" />
	<form:hidden path="save_status" />
	<form:hidden path="cha_yn" />
	<form:hidden path="vol_yn" />
	<form:hidden path="sec_yn" />
<%-- 	<form:hidden path="ser_downtime" /> --%>
<%-- 	<form:hidden path="impact_systems" /> --%>
	
	<form:hidden path="master_file_id" />
	<form:hidden path="title" />
	
	<!-- 	navi -->
	<form:hidden path="col43" />
	
<div class="tit">
${Pkg21Model.title}
</div>
<div class="new_con_Div32">
		<sec:authorize ifNotGranted="ROLE_ADMIN" >
			<c:set var="s_granted" value="N" />
		</sec:authorize>
		<sec:authorize ifAnyGranted="ROLE_ADMIN" >
			<c:set var="s_granted" value="Y" />
		</sec:authorize>
	<div class="sub_contents01 fl_wrap">
		<div class="sub_flow_wrap_100">
					<ul class="sub_flow_line01 fl_wrap">
				<li id="status_navi1" class="sub_flow">
					<p id="status_navi_fn1" class="on" style="cursor: pointer; display:none;" onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
						PKG개발 결과
					</p>
					<p id="status_navi_ing1" class="ing" style="cursor: pointer; display:none; " onclick="javascript:fn_pkg21_read('read', '${Pkg21Model.pkg_seq}');">
						<img src="/images/ic_flow_blue.png">
						<span class="over_text_ing text_1">	PKG개발 결과</span>
					</p>
					<p id="status_navi_now1" class="on_blue" >
						PKG개발 결과
					</p>
					<p id="status_navi_non1" class="w_long"  style=" display:none; " >
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
	</div>
	
	
	
	
	<div class="sub_title fl_wrap">
		<h2 class="fl_left">초도적용 결과 </h2>
		<div class="sub_con_close2">
			<span id="con_open" style="cursor: pointer;" onclick="javascript:fn_open_and_close('0');">접기</span>
		</div>
	</div>
	<div class="sub_contents02"  id="con_list" style="display: block">
<%-- 		status:	${Pkg21Model.status}   a:	${Pkg21Model.select_status} --%>
		<div class="table_style01">
			<table class="con_width100">
				<colgroup>
					<col width="15%">
					<col width="85%">
				</colgroup>
				<tbody>
					<tr>
						<th>결과 <span class="txt_red">*</span></th>
						<td class="sysc_inp">
<%-- 							<form:select path="col1" items="${Pkg21Model.verify_result_3List}" itemLabel="codeName" itemValue="code" /> --%>
							<form:select path="col1" items="${Pkg21Model.s_result_3List}" itemLabel="codeName" itemValue="code" />
						</td>
					</tr>
<!-- 					<tr id="fileAttach_first" > -->
					<tr  >
						<th>Check List</th>
						<td>
							<div id = "wiredFirstCheckListdrop"  name = "wiredFirstCheckListdrop"  class="mt10">
								<input type="text" class="new_inp new_inp_w3 fl_left"> 
								<span class="btn_line_blue2 mg02"><a href="#">첨부파일</a></span>
							</div>
						</td>
			
<!-- 						<td><span class="btn_line_blue2"><a href="#">파일선택</a></span></td> -->
					</tr>
					<tr>
						<th>Comment <span class="txt_red">*</span></th>
						<td>
							<form:textarea path="col2" rows="5" class="new_inp textarea_w95" maxlength="550" />
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<c:if test="${Pkg21Model.select_status > 334 and Pkg21Model.select_status < 337}">
			<div class="bt_btn">
				<span class="btn_org3">
				<a href="javascript:fn_save_336();">임시저장</a>  <a href="javascript:fn_save_337();">초도적용결과 등록</a>
				
				 
				 
				</span>
			</div>
		</c:if>
		
		<c:if test="${Pkg21Model.select_status > 336 and Pkg21Model.status != 399 }">
			<div class="bt_btn">
				<span class="btn_org3">
				
				 <a href="javascript:fn_save_339();">PKG완료</a>
				 
				</span>
			</div>
		</c:if>


	</div>
</div>
</form:form>
</body>
</html>

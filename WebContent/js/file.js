
var deleteList = [];

function deleteFile(fileField) {
	
	if (confirm("저장 버튼을 클릭해야 삭제 내용이 반영됩니다.\n삭제 하시겠습니까?")){
		doDivSH("hide", fileField+"_div_delete", 0);
		doDivSH("show", fileField+"_div_file", 300);
		document.getElementById(fileField + '_delete').value = "Y";
		deleteList.push(fileField);
		if(document.getElementById('deleteList')) {
			document.getElementById('deleteList').value = deleteList;
		}
	}
}

function downloadFile(file_name, file_org_name, file_path){
	$("#resultHTML").html("<iframe name='filedownload' style='width:0px;height0px;display:none'></iframe>");
	file_org_name = file_org_name.replace('&', ' ');
	var formData = "<form name='fileForm' method='post' target='filedownload' action='/common/attachfile/AttachFile_read.do?file_name=" + file_name + "&file_org_name=" + file_org_name + "&file_path=" + file_path + "'></form>";
	$(formData).appendTo('body').submit().remove();
}

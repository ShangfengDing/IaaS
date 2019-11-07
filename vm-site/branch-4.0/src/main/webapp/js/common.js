//关闭facebox浮层
function closeFacebox(){
	$(document).trigger('close.facebox');
}

function checkEmpty(name, len, error, call){
	var value = $("#"+name).val().trim();
	if(value.length == 0){
		$("#"+error).html(call + "不得为空");
		return;
	}else if(value.length > len){
		$("#"+error).html(call + "不得超过"+len+"字");
		return;
	}else{
		$("#"+error).html("");
		return;
	}
}

function checkName(name, len, error, call){
	var value = $("#"+name).val();
	if(value.indexOf(" ") > -1){
		$("#"+error).html(call + "不能含空格");
		return;
	}
	if(value.indexOf("'") > -1 || value.indexOf("\"") > -1){
		$("#"+error).html(call + "不能含引号");
		return;
	}
	if(value.length == 0){
		$("#"+error).html(call + "不得为空");
		return;
	}else if(value.length > len){
		$("#"+error).html(call + "不得超过"+len+"字");
		return;
	}else{
		$("#"+error).html("");
		return;
	}
}

function clearForm(){
    	$(":input").not(':button, :submit, :reset, :hidden').val(" ");
}
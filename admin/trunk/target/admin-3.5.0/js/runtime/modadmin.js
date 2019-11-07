function cancel() {
	facebox_close();
	return false;
}

function isMobile(str) {
	return (/^(?:13\d|14|15|18)-?\d{5}(\d{3}|\*{3})$/.test(str));
} 

function isDigit(str){
    var reg = /^[0-9]+$/;
    return reg.test(str);
}

function isTel(str) {
	if (str.length==11 && isDigit(str))
		return true;
	return false;
}

function isEmail(str){
    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    return reg.test(str);
}

function submit() {
	var id = $("#adminid").val().trim();
	var email = $("#newemail").val().trim();
	var mobile = $("#newmobile").val().trim();
	var roleid = $("#roleid").val().trim();

	if (email == "") {
		$("#error_email").html("不得为空");
		return false;
	} else if (!isEmail(email)) {
		$("#error_email").html("邮件格式不正确");
		return false;
	} else {
		$("#error_email").html("");
	}
	
	if (mobile == "") {
		$("#error_mobile").html("不得为空");
		return false;
	} else if (!isMobile(mobile) && !isTel(mobile)) {
		$("#error_mobile").html("电话格式不正确");
		return false;
	} else {
		$("#error_mobile").html("");
	}
	
	$.ajax({  
        url:"runtime/modAdmin",  
        type:"post",
        data:{id:id, email:email, mobile:mobile, roleid:roleid},  
        success:function(data) {
        	if(data.result=="success"){
        		facebox_close();
        		fillTipBox("success","修改管理员成功！");
        		Location.reload();
            }else{
        		facebox_close();
            	hideLoading();
            	fillTipBox("error","修改管理员失败！");
            }   
        },
        error:function(data){
        	facebox_close();
        	hideLoading();
        	fillTipBox("error","修改管理员失败！");
        }
    });
}
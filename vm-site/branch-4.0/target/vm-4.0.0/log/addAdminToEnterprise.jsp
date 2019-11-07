<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
</head>
<body>
<table>
	<tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">邮箱</td>
		<td>
			<input type="text" class="editline leftmargin_10" name="email" id="email"/>
			<span class="redletter leftmargin_5" id="error_email"></span>
		</td>
    </tr>
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">邀请信息</td>
		<td>
		    &nbsp;&nbsp;&nbsp;<textarea cols="30" rows="5" class="editbox" placeholder="请输入1-50个字" name="description" id="description"></textarea>
			<span class="redletter leftmargin_5" id="error_description"></span>
		</td>
    </tr>
	<tr>
		<td></td>
		<td>
			<button class="button rightmargin_10" onclick="submitCheck()">确定</button>
			<button class="greybutton" onclick="cancel()">取消</button>
		</td>
		<td></td>
	</tr>
</table>
</body>
<script>
function cancel() {
	facebox_close();
	return false;
}

function isEmail(str) {
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    return reg.test(str);
}

function submitCheck() {
	var email = $("#email").val().trim();
	var des = $("#description").val().trim();
	
	if (email == "") {
		$("#error_email").html("不得为空");
		return false;
	} else if (!isEmail(email)) {
		$("#error_email").html("邮件格式不正确");
		return false;
	} else {
		$("#error_email").html("");
	}
	
	if (des == "") {
		$("#error_description").html("不得为空");
		return false;
	} else if (des.length > 50) {
		$("#error_description").html("不得超过50个字");
		return false;
	} else {
		$("#error_description").html("");
	}
	
	$.ajax({  
        url:"log/addUserToEnterprise",  
        type:"post",
        data:{
        	email:email,
        	des:des
        	},  
        success:function(data) {
        	if(data.result=="success"){
        		facebox_close();
        		hideLoading();
        		fillTipBox("success","邀请成功，等待对方同意！");
            }else if (data.result=="no_user"){
        		facebox_close();
        		hideLoading();
        		fillTipBox("error","此用户不存在，请检查邮箱拼写！");
            } else if (data.result=="already"){
            	facebox_close();
        		hideLoading();
        		fillTipBox("error","此用户已为本企业管理员！");
            } else if (data.result=="repeat"){
            	facebox_close();
        		hideLoading();
        		fillTipBox("error","您已对该用户发出邀请！");
            } else {
        		facebox_close();
            	hideLoading();
            	fillTipBox("error","邀请失败！");
            }   
        },
        error:function(data){
        	facebox_close();
        	hideLoading();
        	fillTipBox("error","添加管理员失败！");
        }
    });
}
</script>
</html>
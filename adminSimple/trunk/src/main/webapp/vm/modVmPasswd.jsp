<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<input type="hidden" id="userid" value="${param.userid}" />
<input type="hidden" id="serverid" value="${param.serverid}" />
<table class="formtable">
	<tr>
		<ol>
		   <li>为linux时，必须输入账户</li>
		   <li>为windows时，不输入账户，则清空账户名为Administrator之外的所有账户！</li>
		</ol>
	</tr>
    <tr>
	    <td class="blueletter rightalign rightpadding_10 middleveralign">用户的账户</td>
		<td>
			<input type="text" class="squareinput middleveralign" name="name" id="name" placeholder="请输入1-20个字">
			<span class="redletter leftmargin_5" id="error_name"></span>
		</td>
    </tr>
    <tr>
    	<td>
    	</td>
     	<td>
     		<a class="button topmargin_20" href="javascript:void(0)" onclick="submit();">确定</a>
     		<a class="greybutton topmargin_20" href="javascript:void(0)" onclick="facebox_close()">取消</a>
     	</td>
     </tr>
</table>
</body>
<script>
function submit() {
	var name = $("#name").val().trim();
	var userid = $("#userid").val().trim();
	var serverid = $("#serverid").val().trim();

	if(name.length > 20) {
		$("#error_name").html("1~20个字");
		return false;
	} else {
		$("#error_name").html("");
	}
	
	var result = "";
	
	$.ajax({  
        url:"vm/modvmpasswd",  
        type:"post",
        data:{userid:userid, serverid:serverid, name:name, value:result},  
        success:function(data) {
        	facebox_close();
            hideLoading();
            fillTipBox("success","修改密码是否成功，请查看日志内容！提示：当系统为windows时，若多次输入用户名修改不成功，可尝试不输入账户名称");
        },
        error:function(data){
        	facebox_close();
        	hideLoading();
        	fillTipBox("error","修改密码失败！");
        }
    });
}
</script>
</html>
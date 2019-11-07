<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<table>
    <tr>
     	<td width="80px;" class="blueletter">数据中心名称</td>
     	<td><input type="text" id="zonename1" class="editline bottommargin_5" value="" onblur="checkname()"/>
     		<span class="redletter" id="nametip1"></span></td>
     	<td></td>
    </tr>
    <tr>
     	<td></td>
     	<td><a class="button topmargin_20" href="javascript:void(0)" onclick="submit();">确定</a>
     		<a class="greybutton topmargin_20" href="javascript:void(0)" onclick="facebox_close()">取消</a>
     	</td>
    </tr>
</table>

<script>
getStyle();
function submit() {
	var name = $("#zonename1").val().trim();
	
	if(name=="" || name.length>20){
		$("#nametip1").html("名称需1~20个字");
		return;
	} else {
		$.post("system/newzone",{name:name}, function(data){
			if(data.result != "success"){
				fillTipBox("error","新建数据中心失败");
			}else{
				location.reload();
			}
		});
	}
}
function checkname(){
	var name = $("#zonename1").val().trim();
    
    if(name=="" || name.length>20){
        $("#nametip1").html("名称需1~20个字");
    }else{
        $("#nametip1").html("");
    }
}
</script>
</body>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<input type="hidden" id="zoneId" value="${param.zid}"/>
<table>
    <tr>
     	<td width="80px;" class="blueletter">数据中心名称</td>
     	<td><input type="text" id="zonename" class="editline bottommargin_5" value="${param.name }" onblur="checkname()"/>
     		<span class="redletter" id="nametip"></span></td>
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
	var name = $("#zonename").val().trim();
	var zoneId = $("#zoneId").val();
	
	if(name=="" || name.length>20){
		$("#nametip").html("名称需1~20个字");
		return;
	} else {
		showLoading();
		$.post("system/editzone", 
			{name:name, zoneId:zoneId}, function(data){
			hideLoading();
			if(data.result == "success"){
				$("#tr_"+data.zoneId).children().eq(1).html(data.name);
				facebox_close();
				fillTipBox("success","修改数据中心成功");
			}else{
				facebox_close();
				fillTipBox("error","修改数据中心失败");
			}
		});
	}
}
function checkname(){
	var name = $("#zonename").val().trim();
    
    if(name=="" || name.length>20){
        $("#nametip").html("名称需1~20个字");
    }else{
        $("#nametip").html("");
    }
}
</script>
</body>
</html>
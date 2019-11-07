<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<input type="hidden" id="zoneId" value='<s:property value="zoneId"/>'/>
<table>
    <tr>
     	<td width="65px;" class="blueletter">集群名称</td>
     	<td><input type="text" id="name" class="editline bottommargin_5" onblur="checkname()"/>
     		<span class="redletter" id="nametip"></span></td>
     	<td></td>
    </tr>
    <%-- <tr>
     	<td>数据中心</td>
     	<td><select id="zoneId">
		    <s:iterator id="zone" value="zones">
		    	<option value="<s:property value="#zone.id"/>"><s:property value="#zone.name"/></option>
		    </s:iterator>
	    	</select>
	    </td>
    </tr> --%>
<%--     <tr>
     	<td>外网lan</td>
     	<td><select class="selectboxsmall" id="publicVlanId">
		    <s:iterator id="vlan" value="publicVlans">
		    <option value="<s:property value="#vlan.id"/>"><s:property value="#vlan.id"/>:<s:property value="#vlan.name"/></option>
		    </s:iterator>
	    	</select>
	    </td>
    </tr>
    <tr>
     	<td>内网lan</td>
     	<td><select class="selectboxsmall" id="privateVlanId">
		    <s:iterator id="vlan" value="privateVlans">
		    <option value="<s:property value="#vlan.id"/>"><s:property value="#vlan.id"/>:<s:property value="#vlan.name"/></option>
		    </s:iterator>
	    	</select>
	    </td>
    </tr> --%>
    <tr>
     	<td></td>
     	<td><a class="button topmargin_20" href="javascript:void(0)" onclick="submit();">确定</a>
     		<a class="greybutton topmargin_20" href="javascript:void(0)" onclick="facebox_close()">取消</a>
     	</td>
    </tr>
</table>

<script>
function submit() {
	var name = $("#name").val().trim();
	var zoneId = $("#zoneId").val();
//	var publicVlanId = $("#publicVlanId").val();
//	var privateVlanId = $("#privateVlanId").val();
	var publicVlanId = 1;
	var privateVlanId = 2;
	
	if(name=="" || name.length>20){
		$("#nametip").html("名称需1~20个字");
		return false;
	} else {
		$.post("system/newcluster", 
			{name:name, zoneId:zoneId, publicVlanId:publicVlanId, privateVlanId:privateVlanId}, function(){
			location.reload();
		});
	}
}

function checkname(){
    var name = $("#name").val().trim();
    
    if(name=="" || name.length>20){
        $("#nametip").html("名称需1~20个字");
    }else{
        $("#nametip").html("");
    }
}
</script>
</body>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<form action="net/newnet" method="post" onsubmit="return checkAll();">
<table><tr>
	<td width="70px" class="blueletter rightalign rightpadding_10">选择集群</td>
	<td><select id="aggregateId" name="aggregateId" class="selectboxsmall" style="width:220px;">
		<s:iterator id="aggregate" value="aggregates">
			<option value="<s:property value="#aggregate.id"/>"><s:property value="#aggregate.name"/></option>
		</s:iterator>
		</select>
	<span class="redletter leftmargin_5" id="error1"></span></td>
</tr><tr>
	<td class="blueletter rightalign rightpadding_10">IP池类型</td>
	<td><select id="addressType" name="addressType" class="selectboxsmall">
			<option value="private">内网</option>
			<option value="public">外网</option>
		</select>
	<span class="redletter leftmargin_5" id="error2"></span></td>
</tr><tr>
	<td class="blueletter rightalign rightpadding_10">IP段</td>
	<td><input type="text" class="bottommargin_5" id="ipStart" name="ipStart" style="width:100px;" />
	&nbsp;-&nbsp;<input type="text" class="bottommargin_5" id="ipEnd" name="ipEnd" style="width:100px;" />
	<span class="redletter leftmargin_5" id="error3"></span></td>
</tr><tr>
	<td class="blueletter rightalign rightpadding_10">子网掩码</td>
	<td><input type="text" class="bottommargin_5" id="netmask" name="netmask" style="width:100px;" />
	<span class="redletter leftmargin_5" id="error4"></span></td>
</tr><tr>
	<td class="blueletter rightalign rightpadding_10">网关</td>
	<td><input type="text" class="bottommargin_5" id="gateway" name="gateway" style="width:100px;" />
	<span class="redletter leftmargin_5" id="error5"></span></td>
</tr><tr>
	<td class="blueletter rightalign rightpadding_10">DNS</td>
	<td><input type="text" class="bottommargin_5" id="dns" name="dns" style="width:100px;" />
    <span class="redletter leftmargin_5" id="error6"></span></td>
</tr><tr>
	<td></td>
	<td>
		<input class="button" type="submit" value="确定"/>
		<button class="greybutton" onclick="facebox_close();return false;">取消</button>
	</td><td></td>
</tr></table>
<input type="hidden" value="<s:property value="zoneId"/>" name="zoneId" id="zoneId" />
</form>
<script>
$(function(){
	getStyle();
});

function checkAll(){
	var aggregateId = $("#aggregateId").val();
	var addressType = $("#addressType").val();
	var ipStart = $("#ipStart").val();
	var ipEnd = $("#ipEnd").val();
	var netmask = $("#netmask").val();
	var gateway = $("#gateway").val();
	var dns = $("#dns").val();
	var zoneId = $("#zoneId").val();
	var ipStartPrefix;
	var ipStartNum;
	var ipEndPrefix;
	var ipEndNum;
	
	if(aggregateId == ""){
		$("#error1").html("请选择集群");
        return false;
	}else{
		$("#error1").html("");
	}
	
	if(addressType == ""){
        $("#error2").html("请选择IP池类型");
        return false;
    }else{
        $("#error2").html("");
    }
	
	if(ipStart == ""){
		$("#error3").html("IP段不得为空");
		return false;
	}else if(!isIP(ipStart)){
		$("#error3").html("IP段格式不正确");
		return false;
	}else{
		ipStartPrefix = ipStart.slice(0, ipStart.lastIndexOf('.'));
		ipStartNum = ipStart.slice(ipStart.lastIndexOf('.')+1);
		$("#error3").html("");
	}
	
	if(ipEnd == ""){
        $("#error3").html("IP段不得为空");
        return false;
    }else if(!isIP(ipEnd)){
        $("#error3").html("IP段格式不正确");
        return false;
    }else{
    	ipEndPrefix = ipEnd.slice(0, ipEnd.lastIndexOf('.'));
    	ipEndNum = ipEnd.slice(ipEnd.lastIndexOf('.')+1);
		
    	if(parseInt(ipStartNum) > parseInt(ipEndNum)) {
    		$("#error3").html("起始段不得大于结束段");
    		return false;
    	} else if(!(ipStartPrefix == ipEndPrefix)) {
    		$("#error3").html("IP段区间前三段不一致");
    		return false;
    	} else {
    		$("#error3").html("");
    	}
    }
	
	if(netmask == ""){
        $("#error4").html("子网掩码不得为空");
        return false;
    }else if(!isIP(netmask)){
        $("#error4").html("子网掩码格式不正确");
        return false;
    }else{
        $("#error4").html("");
    }
	
	
	/* if(gateway == ""){
        $("#error5").html("网关不得为空");
        return false;
    }else  */if(gateway != "" && !isIP(gateway)){
        $("#error5").html("网关格式不正确");
        return false;
    }else{
        $("#error5").html("");
    }
	
	/* if(dns == ""){
        $("#error6").html("DNS不得为空");
        return false;
    }else  */if(dns != "" && !isIP(dns)){
        $("#error6").html("DNS格式不正确");
        return false;
    }else{
        $("#error6").html("");
    }
	
	
	
	showLoading();
	return true;
}

function checkIPStart() {
	var ipStart = $("#ipStart").val();
	
	if(ipStart == ""){
        $("#error3").html("IP开始段不得为空");
        return;
	}else if(!isIP(ipStart)){
        $("#error3").html("IP开始段格式不正确");
        return;
	}else{
        $("#error3").html("");
	}
}

function checkIPEnd() {
	var ipEnd = $("#ipEnd").val();
	
	if(ipEnd == ""){
        $("#error3").html("IP结束段不得为空");
        return;
	}else if(!isIP(ipEnd)){
        $("#error3").html("IP结束段格式不正确");
        return;
	}else{
        $("#error3").html("");
	}
}


function checkIP(){
	
	var ipEnd = $("#ipEnd").val();
	var ipStartPrefix;
	var ipEndPrefix;
	//var ipStartPrefix = ipStart.slice(0, ipStart.lastIndexOf('.'));
	
    if(ipStart == ""){
        $("#error3").html("IP开始段不得为空");
    }else if(!isIP(ipStart)){
        $("#error3").html("IP开始段格式不正确");
    }else{
    	ipStart
        $("#error3").html("");
    }
    
    if(ipEnd == ""){
        $("#error3").html("IP结束段不得为空");
    }else if(!isIP(ipEnd)){
        $("#error3").html("IP段格式不正确");
    }else{
        $("#error3").html("");
    }
}

function checkNetmask(){
	var netmask = $("#netmask").val();
    if(netmask == ""){
        $("#error4").html("子网掩码不得为空");
    }else if(!isIP(netmask)){
        $("#error4").html("子网掩码格式不正确");
    }else{
        $("#error4").html("");
    }
}

function checkGateway(){
    var gateway = $("#gateway").val();
    /* if(gateway == ""){
        $("#error5").html("网关不得为空");
    }else  */if(gateway != "" && !isIP(gateway)){
        $("#error5").html("网关格式不正确");
    }else{
        $("#error5").html("");
    }
}

function checkDNS(){
	var dns = $("#dns").val();
   /*  if(dns == ""){
        $("#error6").html("DNS不得为空");
    }else  */if(dns != "" && !isIP(dns)){
        $("#error6").html("DNS格式不正确");
    }else{
        $("#error6").html("");
    }
}

function isIP(ipAddr){
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
    if(ipAddr.match(reg)){
        return true;
    } else {
        return false;
    }
}
</script>
</body>
</html>
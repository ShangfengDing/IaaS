<!DOCTYPE html>
<%@page import="com.opensymphony.xwork2.*"%>
<%@page import="appcloud.api.enums.AcServiceTypeEnum"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div class="bottommargin_10 midsize">所有服务列表：</div>
<div id="services">
<input type="hidden" id="hostId" value="<s:property value="hostId"/>" />
<!--<s:property value="serviceStatusMap"/>  -->
<table>
<s:iterator id="serviceStatus" value="serviceStatusMap">
	<s:if test="#serviceStatus.key!='UNKNOWN'">
		<tr>
			<td width="45px">
            <td width="230px">
            	<input type="checkbox" name="service" id="<s:property value="#serviceStatus.key"/>" value="<s:property value="#serviceStatus.key"/>"/>
            	<label for="<s:property value="#serviceStatus.key"/>">
					<s:property value="#serviceStatus.key"/>
				</label>
            </td>
            <td>
            	<s:if test="#serviceStatus.value=='RUNNING'">
            		<label class="greenletter" for="<s:property value="#serviceStatus.key"/>">
					<s:property value="#serviceStatus.value"/>
					</label>
				</s:if>
				<s:if test="#serviceStatus.value=='STOPED'">
            		<label class="redletter" for="<s:property value="#serviceStatus.key"/>">
					<s:property value="#serviceStatus.value"/>
					</label>
				</s:if>
				<s:if test="#serviceStatus.value=='NOT USED'">
					<label class="blueletter" for="<s:property value="#serviceStatus.key"/>">
					<s:property value="#serviceStatus.value"/>
					</label>
				</s:if>
            </td>
        </tr>
		
	</s:if>
</s:iterator>
</table>
</div>
<div class="centeralign topmargin_20">
	<button class="button" onclick="submit('start')">启动</button>
	<button class="button" onclick="submit('stop')">停止</button>
  	<button class="greybutton" onclick="facebox_close()">取消</button>
</div>
<script src='js/system/searchhost.js'></script>
<script>
function submit(operate) {
	var serviceStr = "";  
	$('input[name="service"]:checked').each(function(){  
		serviceStr += $(this).val()+",";  
	});
	if(serviceStr == ""){
		return false;
	}
	var hostId =document.getElementById("hostId").value;
	var url = (operate=="start") ? "system/startservice" : "system/stopservice";
	
	showLoading();
	$.post(url,{hostId:hostId,serviceStr:serviceStr},function(){
		hideLoading();
		facebox_close();
		submitAll(1);
	});
}
</script>
</body>
</html>
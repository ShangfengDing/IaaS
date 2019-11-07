<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<span class="redletter centeralign" id="error"></span>
<form action="system/changers" method="post" onsubmit="return checkAll();">
	<input name="clusterId" type="text" value="<s:property value='aggregate.id'/>" class="hidden"/>
	<table>
	
	<tr style="margin:4px 0;">
		<td class="blueletter leftalign rightpadding_10">集群名称</td>
		<td class="centeralign" ><span><s:property value="aggregate.name"/></span></td>
	</tr>
	<tr>
		<td class="blueletter leftalign rightpadding_10">策略名称</td>
		<td>
			<select id="name" name="rsUuid" style="height:24px;width:330px;margin:8px 0 4px 0;border:1px solid #adb9c2;line-height:24px;vertical-align:middle" onchange="nameChange()">
						<s:iterator value="rsNameMap">
							<option id="<s:property value='key'/>" value="<s:property value='key'/>"><s:property value="value"/></option>
						</s:iterator>
			</select>
		</td>
	</tr>
	<tr>
		<td class="blueletter leftalign rightpadding_10">策略描述</td>
		<td>
			<select id="description" style="height:24px;width:330px;margin:4px 0;border:1px solid #adb9c2;line-height:24px;vertical-align:middle" onchange="descriptionChange()">
					<s:iterator value="rsDescriptionMap">
						<option id="<s:property value='key'/>" value="<s:property value='key'/>"><s:property value="value"/></option>
					</s:iterator>
			</select>
		</td>
	</tr>
<%-- 		<tr>
			<td width="70px" class="blueletter rightalign rightpadding_10">集群名称</td>
			<td class="centeralign"><s:property value="aggregate.name"/></td>
		</tr> 
		<tr>
			<td width="70px" class="blueletter rightalign rightpadding_10">策略名称</td>
			<td>
				<select id="name" name="rsUuid" class="selectsmallbox" style="width:320px height:50px margin-bottom: 4px" onchange="nameChange()">
					<s:iterator value="rsNameMap">
						<option id="<s:property value='key'/>" value="<s:property value='key'/>"><s:property value="value"/></option>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td width="70px" class="blueletter rightalign rightpadding_10">策略描述</td>
			<td>
				<select id="description" class="selectsmallbox" style="width:320px height:50px margin-bottom: 4px" onchange="descriptionChange()">
					<s:iterator value="rsDescriptionMap">
						<option id="<s:property value='key'/>" value="<s:property value='key'/>"><s:property value="value"/></option>
					</s:iterator>
				</select>
			</td>
		</tr>--%>
		<tr>
			<td>
			</td>
			<td class="centeralign">
			<input class="button" type="submit" value="确定">
			<button class="greybutton" onclick="facebox_close();return false;">取消</button>
			</td>
		</tr>
	</table>
</form>
</body>
<script>
$(function(){
	getStyle();
});

function nameChange() {
	$("#description").val($("#name").val());
}

function descriptionChange() {
	$("#name").val($("#description").val());
}
function checkAll() {
	if($("#name").val() != $("#description").val()){
		$("#error").html("名称和描述不符，重新选择！");
		return false;
	} else{
		return true;
	}
}
</script>
</html>
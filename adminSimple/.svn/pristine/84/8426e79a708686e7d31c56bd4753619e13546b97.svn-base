<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<s:if test="aggregates.size()==0">
	还没有和该主机所属router信息一致的集群，请先创建一个！
</s:if>
<s:else>
	集群名称：
	<select id="aggregate">
	<s:iterator id="aggregate" value="aggregates">
		<option value="<s:property value="#aggregate.id"/>"><s:property value="#aggregate.name"/></option>
	</s:iterator>
	</select>
	<div class="centeralign topmargin_20">
		<button class="button" onclick="submit()">确定</button>
	  	<button class="greybutton" onclick="facebox_close()">取消</button>
  	</div>
</s:else>
<script>
function submit() {
	var hostId = '${param.hostId}';
	var aggregateId = $("#aggregate").val();
	$.post("system/hosttocluster",{hostId:hostId,aggregateId:aggregateId},function(){
		location.reload();
	});
}
</script>
</body>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>云主机概览 - 云海IaaS</title>
  	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">	
<s:include value="/template/_banner.jsp?menu=sys" />
<div id="inner">
	<s:include value="/vm/_left.jsp?menu=overview" />
    <div class="right"> 
		<div class="divline topmargin_20">云主机概览</div>
		<div class="contentline">
		<h3>现有云主机：<s:property value="servers.size()" />台</h3>
			<%-- <table class="datatable"><tr>
				<td>用户</td><td>名称</td><td>状态</td><td>数据中心</td><td>公网IP</td><td>创建时间</td><td width="160px">操作</td>
			</tr>
			<s:iterator id="server" value="servers">
			<tr>
				<td><s:property value="#server.tenantId"/></td>
				<td><s:property value="#server.name"/></td>
				<td><s:property value="#server.status"/></td>
				<td><s:property value="zoneIdNameMap[#server.availabilityZone]"/></td>
				<td><s:property value="#server.accessIPv4"/></td>
				<td><s:date name="#server.created"/>
				</td><td>
					<a class="blueletter" href="vm/vmdetail?userId=<s:property value="#server.tenantId"/>&serverId=<s:property value="#server.id"/>" title="查看详情">详情</a>
				</td>
			</tr>
			</s:iterator>
			</table> --%>
		</div>
	</div>
</div>
<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_common_js.jsp"></s:include>
</body>
</html>
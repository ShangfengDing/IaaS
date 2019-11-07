<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>告警管理 - 云海IaaS</title>
<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
<s:include value="/template/_banner.jsp?menu=runtime" />
<div id="inner">
	<s:include value="/runtime/_left.jsp?menu=alert" />
    <div class="right"> 
		<div class="divline topmargin_20">告警管理</div>
		<div class="contentline">
			<table class="datatable">
			<tr>
				<td>时间</td><td>来源</td><td>内容</td><td>详情</td><td>级别</td></tr>
			<s:iterator id="alertMsg" value="alertMsgs" status="st">
			<tr>
				<td><s:property value="#alertMsg.time"/></td>
				<td><s:property value="#alertMsg.module"/></td>
				<td><s:property value="#alertMsg.msg"/></td>
				<td><s:property value="#alertMsg.detail"/></td>
				<td><s:property value="#alertMsg.status"/></td>
			</tr>
			</s:iterator>
			</table>
		</div><!--contentline-->
	</div><!--right-->
	</div><!--#inner-->
<s:include value="/template/_footer.jsp" />
</div><!--#container-->
<s:include value="/template/_common_js.jsp" />
</body>
</html>
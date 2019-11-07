<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户概览 - 云海IaaS</title>
<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
<s:include value="/template/_banner.jsp?menu=user" />
<div id="inner">
	<s:include value="/user/_left.jsp?menu=index" />
    <div class="right"> 
		<div class="divline topmargin_10">用户概览</div>
		<div class="contentline"><h3>现有用户量为：<s:property value="userNum" />人</h3>
			<%-- <table class="datatable">
				<tr>
					<td>id</td><td>email</td><td>虚拟机数</td>
				</tr>
				<s:iterator id="user" value="users" status="st">
					<tr>
						<td><s:property value="#user.id"/></td>
						<td><s:property value="#user.email"/></td>
						<td></td>
					</tr>
				</s:iterator>      
			</table> --%>
		</div><!--contentline-->
	</div><!--right-->
</div>
<s:include value="/template/_footer.jsp" />
</div><!--#container-->
<s:include value="/template/_common_js.jsp" />
</body>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>错误提示页</title>
	<s:include value="/template/_head.jsp" />
</head>

<body>
<div id="container">
<s:include value="/template/_banner.jsp" />
<div id="inner">
	<div class="content">
		<h1>对不起，服务器开小差了 o(╯□╰)o</h1>
		<a href="javascript:void(0)" onclick="showes()">查看错误</a>
		<div id="es" class="hidden">${exceptionStack}</div>
	</div>
</div>

<s:include value="/template/_footer.jsp" />
</div>
<s:include value="/template/_common_js.jsp" />
<script type="text/javascript">
$(function() {
	$("#es").html($("#es").html()
		.replace(/Caused by:/ig, "<br/><br/><span style='color:red;font-weight:bold;'>Caused By:</span>")
		.replace(/at /g, "<br/>&nbsp; &nbsp; &nbsp; &nbsp;at "));
});
function showes(){
	$("#es").toggleClass("hidden");
}
</script>
</body>
</html>
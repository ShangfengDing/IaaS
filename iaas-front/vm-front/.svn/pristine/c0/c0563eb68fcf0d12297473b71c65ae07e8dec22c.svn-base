<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, com.appcloud.vm.common.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
  <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>用户冻结提示页</title>
  <s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
<s:include value="/template/_pub_banner.jsp?index=v" />
<div id="inner">

<s:if test="#session.userId == #session.loginUserId">
	<center><h1>用户已被冻结，请联系管理员！</h1></center>
</s:if>
<s:else>
	<table class="centeralign"><tr>
		<td class="centeralign bottomveralign">
			<span class="strong" style="font-size:2.0em">企业已被冻结，请联系管理员！</span>
			<span>或<a href="javascript:void(0)" onclick="quitenterprise(<s:property value='#session.loginUserId'/>,<s:property value='#session.userId'/>)">退出企业</a></span>
		</td>
	</tr></table>
</s:else>	

</div>

<s:include value="/template/_footer.jsp" />
</div>
<s:include value="/template/_commonjs.jsp"></s:include>
</body>
<script>
function quitenterprise(userId, ownerId) {
	if(userId == ownerId) {
		 fillTipBox("error", "企业所有者不能退出企业");
	} else if(confirm("确认退出企业吗？")) {
		showLoading();
		$.post("enterprise/quitenterprise",
			{userId:userId, ownerId:ownerId},
			function(data){
				if(data.result == "success"){
					fillTipBox("success", "退出企业成功，请重新登录");
				}else if(data.result == "fail"){
					fillTipBox("error", "退出企业失败");
				}
				hideLoading();
				facebox_close();
		});
	}
}
</script>
</html>


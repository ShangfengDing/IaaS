<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>数据中心 - 云海IaaS</title>
 	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
<s:include value="/template/_banner.jsp?menu=sys" />
<div id="inner">
	<s:include value="/system/_left.jsp?menu=zone" />
	<div class="right"> 
		<div class="divline bottommargin_20">数据中心列表
		<a class="button rightfloat" rel="facebox" href="system/newzone.jsp" title="新建数据中心" size="s">新建数据中心</a>
		</div>
		<div class="contentline">
			<table class="datatable">
			<tr>
				<td width="60px">序号</td><td>名称</td><td width="200px">创建时间</td><td width="120px">操作</td>
			</tr>
			<s:iterator id="zone" value="zones" status="st">
			<tr id="tr_<s:property value="#zone.id"/>">
				<td><s:property value="#st.index+1"/></td>
				<td><s:property value="#zone.name"/></td>
				<td><s:date name="#zone.createdAt" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a class="blueletter" href="system/cluster?zid=<s:property value="#zone.id"/>">管理</a>
				<a class="blueletter leftmargin_10" href="system/editzone.jsp?zid=<s:property value="#zone.id"/>&name=<s:property value="#zone.name"/>" rel="facebox" title="修改数据中心名称" size="s">修改</a></td>
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
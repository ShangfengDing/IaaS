<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理组设置 - 云海IaaS</title>
<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
<s:include value="/template/_banner.jsp?menu=operation" />
<div id="inner">
	<s:include value="/runtime/_left_admin.jsp?menu=role_manage" />
    <div class="right"> 
		<div class="divline bottommargin_20">管理组设置</div>
		<div class="contentline">
			<table>
				<td width="60px" class="rightalign">管理组名称</td>
				<td width="200px"><input type="text" style="width:150px;height:24px" class="squareinput leftmargin_10" id="rolename"/></td>
				<td>
					<button class="button leftmargin_10" onclick="submitSearch(1)">查询</button>
					<a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="cancelSearch()">取消</a>
				</td>
				<td>
					<a class="button rightfloat" href="runtime/preAddRole" rel="facebox" title="添加管理组">添加管理组</a>
				</td>
			</table>
		</div>
		<div class="dottedline"></div>
		<div id="query"></div>
	</div><!--right-->
</div>
<s:include value="/template/_footer.jsp" />
</div><!--#container-->
<s:include value="/template/_common_js.jsp" />
<script src="js/runtime/queryRole.js"></script>
</body>
</html>
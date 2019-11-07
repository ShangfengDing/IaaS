<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>用户管理 - 云海IaaS</title>
	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
	<s:include value="/template/_banner.jsp?menu=user" />
	<div id="inner">
		<s:include value="/user/_left.jsp?menu=query" />
		<div class="right">
			<div class="divline bottommargin_20">用户管理</div>
			<div class="contentline">
				<table>
					<tr height="45px">
						<td width="60px" class="rightalign">所属企业</td>
						<td width="180px"><input type="text" style="width:150px;height:24px" class="squareinput leftmargin_10" id="enterpriseName"/></td>
						<td width="60px" class="rightalign">所属邮箱</td>
						<td width="180px"><input type="text" style="width:150px;height:24px" class="squareinput leftmargin_10" id="email"/></td>
						<td width="60px" class="rightalign">所在群组</td>
						<td width="180px">
							<select class="leftmargin_10 selectbox" style="width:160px;height:24px;" id="group">
								<option value="">--全部--</option>
							</select>
						</td>
					</tr>
					<tr height="45px">
						<td></td>
						<td colspan="5" class="leftalign">
							<button class="button leftmargin_10" onclick="submitEmail(1)">查询</button>
							<button class="button leftmargin_10" onclick="searchAll(1)">查询全部</button>
							<a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="cancelSearch()">取消</a>
						</td>
					</tr>
				</table>
			</div>
			<div class="dottedline"></div>
			<div id="query"></div>
		</div><!--right-->
	</div>
	<s:include value="/template/_footer.jsp" />
</div><!--#container-->
<s:include value="/template/_common_js.jsp" />

</body>
<script src="js/user/usermanage.js"></script>
</html>
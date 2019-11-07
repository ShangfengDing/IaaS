<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>群组管理 - 云海IaaS</title>
<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
	<s:include value="/template/_banner.jsp?menu=user" />
	<div id="inner">
		<s:include value="/user/_left.jsp?menu=manage" />
    	<div class="right"> 
			<div class="divline bottommargin_20">
				<s:property value="zname"/>群组管理
				<a class="button rightfloat" href="group/preNewAcGroup" rel="facebox" title="新建群组" size="s">新建群组</a>
			</div>
		
			<s:if test='acGroups.size() == 0'>
            	<h3 class="centeralign">还没有群组</h3>
        	</s:if>
        	<s:else>
				<table class="datatable">
					<tr>
						<td>群组</td>
						<td>描述</td>
						<td width="120px">操作</td>
					</tr>
					<s:iterator id="acGroup" value="acGroups">
						<tr>
							<td><s:property value="#acGroup.name"/></td>
							<td><s:property value="#acGroup.description"/></td>
							<td>
								<a class="blueletter" href="group/showAcGroup?acGroupId=<s:property value="#acGroup.id"/>" rel="facebox" title="群组详情" size="s">详情</a>&nbsp;  
								<a class="blueletter leftmargin_10"  href="javascript:void(0)" onclick="delAcGroup(this,'<s:property value="#acGroup.id"/>')">删除</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</s:else>
		</div>
	</div>
	<s:include value="/template/_footer.jsp" />
</div>
<s:include value="/template/_common_js.jsp" />
</body>
<script src="js/group/acgroupmanage.js"></script>
</html>
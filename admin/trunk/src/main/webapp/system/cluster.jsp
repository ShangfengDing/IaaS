<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>集群管理 - 云海IaaS</title>
 	<s:include value="/template/_head.jsp" />
 	<style type="text/css">
 		.datatable td{
 			text-align : center;
 		}
 	</style>
</head>
<body>
<div id="container">
<s:include value="/template/_banner.jsp?menu=sys" />
<div id="inner">
    <input type="hidden" id="zid" value='<s:property value="zid"/>'/>
	<s:include value="/system/_left.jsp?menu=cluster" />
	
	<div class="right"> 
		<div class="divline bottommargin_20">集群管理
			<a class="button rightfloat" rel="facebox" href="system/prenewcluster?zoneId=<s:property value="zid"/>" title="新建集群" size="s">新建集群</a>
		</div>
		<div class="contentline">
		    <s:if test='aggregates.size() == 0'>
		      <h3 class="centeralign">还没有集群,请新建集群</h3>
		    </s:if>
		    <s:else>
				<table class="datatable">
				<tr>
<!-- 				名称	创建时间	节点数	云主机数	云硬盘数	操作
 -->				
					<td>名称</td>
					<td>创建时间</td>
					<td>节点数</td>
					<td>云主机数</td>
					<td>云硬盘数</td>
					<td>操作</td>
				</tr>
				<s:iterator id="list" value="clusterList">
					<s:iterator value="#list">
					<tr>
						<td><s:property value="key.name"/></td>
						<td><s:date name="key.createdAt" format="yyyy-MM-dd HH:mm:ss"/></td>
						
							<td><a class="blueletter" href="system/host?clusterId=<s:property value='key.id'/>" ><s:property value="value[0]"/></a></td>
							<td><a class="blueletter" href="vm/presearchvm?cluster=<s:property value='key.id'/>"><s:property value="value[1]"/></a></td>
							<td><a class="blueletter" href="hd/hdmanage?zone=1"><s:property value="value[2]"/></a></td>
						
						<td>
							<a class="blueletter" href="system/clusterdetail?id=<s:property value='key.id'/>">详情</a>
						</td>
					</tr>
					</s:iterator>
				</s:iterator>
				</table>
			</s:else>
		</div><!--contentline-->
	</div><!--right-->
	</div><!--#inner-->
<s:include value="/template/_footer.jsp" />
</div><!--#container-->
<s:include value="/template/_common_js.jsp" />
</body>
</html>
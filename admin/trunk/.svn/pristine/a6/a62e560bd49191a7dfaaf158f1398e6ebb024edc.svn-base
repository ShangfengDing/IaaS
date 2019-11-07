<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 


此方法已废

 -->
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>节点管理 - 云海</title>
 	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
<s:include value="/template/_banner.jsp?menu=sys" />
<div id="inner">
    <input type="hidden" id="zid" value='<s:property value="zid"/>'/>
	<s:include value="/system/_left_zone.jsp?menu=host" />
	<div class="right"> 
		<div id="highcharts"></div>
		<div class="divline"><s:property value="zname"/>&nbsp;&gt;&gt;&nbsp;节点管理</div>
		<div class="contentline">
		    <s:if test='hosts.size() == 0'>
              <h3 class="centeralign">还没有节点</h3>
            </s:if>
            <s:else>
			<table class="datatable">
			<tr>
				<td width="93px">节点IP</td><td width="100px">类型</td><td width="100px">状态</td>
				<td>集群</td><!-- <td>数据中心</td> --><td>所在位置</td>
				<td width="200px">操作</td>
			</tr>
			<s:iterator id="host" value="hosts" status="st">
			<tr class="strong">
				<td><s:property value="#host.ip"/></td>
				<td>
					<s:if test="#host.hostType.toString()=='COMPUTE_NODE'">计算节点</s:if>
					<s:else>功能节点</s:else>
				</td>
				<td>
					<s:if test="#host.hostStatus.toString()=='NORMAL_LOAD'"><span class="greenletter">正常负载</span></s:if>
					<s:elseif test="#host.hostStatus.toString()=='HIGH_LOAD'"><span class="orangeletter">高负载</span></s:elseif>
					<s:elseif test="#host.hostStatus.toString()=='LOW_LOAD'">低负载</s:elseif>
					<s:elseif test="#host.hostStatus.toString()=='CRASH'"><span class="redletter">宕机</span></s:elseif>
				</td>
				<td><s:if test="#host.aggregateId == null">
						无&nbsp;<a class="blueletter" rel="facebox" size="s" title="加入集群" 
						href="system/prehosttocluster?hostId=<s:property value="#host.id"/>&zoneId=<s:property value="zid"/>">加入集群</a>
					</s:if><s:else>
						<s:property value="#host.aggregateName"/>
					</s:else>
				</td>
				<%-- <td><s:property value="#host.availabilityZoneName"/></td> --%>
				<td><s:property value="#host.location"/></td>
				<td style="font-weight:normal">
					<%-- <a class="rightmargin_5 blueletter" href="javascript:void(0)" 
						onclick="showDetail(this,'<s:property value="hostIdResourceMap[#host.id].cpu"/>','<s:property value="hostIdResourceMap[#host.id].memoryMb"/>','<s:property value="hostIdResourceMap[#host.id].diskGb"/>')">查看详情</a>--%>				
					<a class="rightmargin_5 blueletter" rel="facebox" title="性能" size="s" href="system/hostperformance.jsp?hostName=<s:property value="#host.id"/>&hostCPU=<s:property value="hostIdResourceMap[#host.id].cpu"/>&hostMem=<s:property value="hostIdResourceMap[#host.id].memoryMb"/>&hostDisk=<s:property value="hostIdResourceMap[#host.id].diskGb"/>">性能</a>
					<a class="rightmargin_5 blueletter" rel="facebox" title="监控" size="l" href="system/hostmonitor.jsp?hostName=<s:property value="#host.id"/>">监控</a>
					<s:if test="#host.aggregateId != null">
					   <a class="leftmargin_5 blueletter" href="vm/presearchvm?clusterId=<s:property value="#host.aggregateId"/>&zoneId=<s:property value="zid"/>&hostId=<s:property value="#host.id"/>">托管虚拟机</a>
					</s:if>
					<s:else>
					<a class="leftmargin_5 blueletter" href="vm/presearchvm?zoneId=<s:property value="zid"/>&hostId=<s:property value="#host.id"/>">托管虚拟机</a>
                    </s:else>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="3">
					节点配置详情：<br/>
					CPU:<s:property value="hostIdResourceMap[#host.id].cpu"/>个,
					内存:<s:property value="hostIdResourceMap[#host.id].memoryMb"/>Mb,
					硬盘:<s:property value="hostIdResourceMap[#host.id].diskGb"/>Gb
				</td>
				<td colspan="2">
					已有服务：
					<a class="leftmargin_10 blueletter" rel="facebox" size="s" title="节点服务管理" 
						href="system/hostservice.jsp?hostId=<s:property value="#host.id"/>">启动停止服务</a><br/>
					<s:iterator id="service" value="hostIdServiceMap[#host.id]">
						<s:property value="#service.type"/>：
						<span id="<s:property value="#host.id"/><s:property value="#service.type"/>"><s:property value="#service.serviceStatus"/></span><br/>
					</s:iterator>
				</td>
			</tr>
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
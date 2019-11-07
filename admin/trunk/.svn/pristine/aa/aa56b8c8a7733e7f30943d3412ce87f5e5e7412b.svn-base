<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>集群详情 - 云海IaaS</title>
  	<s:include value="/template/_head.jsp" />
	<link rel="stylesheet" href="<%=Constants.FRONT_URL%>css/ui.daterangepicker.css" type="text/css" />
    <link rel="stylesheet" href="<%=Constants.FRONT_URL%>css/jquery-ui-1.7.1.custom.css" type="text/css" title="ui-theme" />
	
</head>
<body>
<div id="container">	
<s:include value="/template/_banner.jsp?menu=sys" />
<div id="inner">
	<s:include value="/system/_left.jsp?menu=cluster" />
    <div class="right"> 
		<div class="divline topmargin_10 bottommargin_20" >
    			  <span class="greyletter midsize strong">
    			  <a class="greyletter" href="system/cluster?zid=1"">集群管理</a>
    			  &nbsp;&gt;&gt;&nbsp;
    			  <a class="greyletter">集群详情&nbsp;(&nbsp;<s:property value="aggregate.name"/>&nbsp;)</a></span>
   		</div>
		<div class="contentline">
		<h3>基本信息</h3>
		<table class="datatable" border="1">
			<tr>
				<td width="298px">集群名称</td>
				<td><s:property value="aggregate.name"/></td>
			</tr>
			<tr>
				<td width="328px">集群描述</td>
				<td><s:property value="aggregate.name"/></td>
			</tr>
		</table>
		<h3>资源调度&nbsp;&nbsp;&nbsp;<a class="blueletter" href="system/prechangers?id=<s:property value='aggregate.id'/>" title="设置" rel="facebox" size="s">设置</a></h3>
 		<table class="datatable" border="1">
			<tr>
				<td>调度类型</td>
				<td>算法名称</td>
				<td>算法描述</td>
				<td>算法参数</td>
			</tr>
			<s:iterator id="list" value="curRSList">
				<tr>
					<td><s:property value="#list.type"/></td>
					<td><s:property value="#list.name"/></td>
					<td><s:property value="#list.description"/></td>
					<td><s:property value="#list.params"/></td>
				</tr>
			</s:iterator>
		</table>
		
		<h3>超售比例&nbsp;&nbsp;&nbsp;<a class="blueletter" href="system/setoversell.jsp?id=<s:property value='aggregate.id'/>" title="设置" rel="facebox" size="s">设置</a></h3>
		<table class="datatable">
			<tr>
				<td></td>
				<td>CPU</td>
				<td>内存/MB</td>
				<td>硬盘/GB</td>
			</tr>
			<tr>
				<td>硬件配置</td>
				<td><s:property value="cpu_conf"/></td>
				<td><s:property value="memory_conf"/></td>
				<td><s:property value="disk_conf"/></td>
			</tr>
			<tr>
				<td>超售比例(%)</td>
				<td><s:property value='aggregate.cpu_oversell'/>%</td>
				<td><s:property value='aggregate.memory_oversell'/>%</td>
				<td><s:property value='aggregate.disk_oversell'/>%</td>
			</tr>
			<tr>
				<td>总量</td>
				<td><s:property value="cpu_all"/></td>
				<td><s:property value="memory_all"/></td>
				<td><s:property value="disk_all"/></td>
			</tr>
			<tr>
				<td>已用</td>
				<td><s:property value="cpu_sell"/></td>
				<td><s:property value="memory_sell"/></td>
				<td><s:property value="disk_sell"/></td>
			</tr>
			<tr>
				<td>销售比例(%)</td>
				<td><s:property value="cpu_sell_rate"/>%</td>
				<td><s:property value="memory_sell_rate"/>%</td>
				<td><s:property value="disk_sell_rate"/>%</td>
			</tr>
			
		</table>
		<h3>网络资源&nbsp;&nbsp;&nbsp;<a class="blueletter" href="system/clusternewnet.jsp?id=<s:property value='aggregate.id'/>" rel="facebox" title="新建" size="s">新建</a></h3>

		<s:iterator value="addrPoolByCluster" id = "addrPoolList">
           <h3 class="centeralign">
          	 外网IP总量：<s:property value="ipNumMap[key]['totalPublic']"/>
                                         &nbsp;&nbsp; 剩余量：<s:property value="ipNumMap[key]['public']"/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                                                                                                                               内网IP总量：<s:property value="ipNumMap[key]['totalPrivate']"/>                 
                                         &nbsp;&nbsp;剩余量：<s:property value="ipNumMap[key]['private']"/>                 
           </h3>
			<table class="datatable"><tr>
				<!-- <td width="140px">集群</td><td width="60px">数据中心</td> --><td width="30px">类型</td>
				<td width="200px">IP段</td>
				<td>子网掩码</td><td>网关</td><td>DNS</td><td width="80px">操作</td>
			</tr>
			<s:iterator id="addrpool" value="value">
			<s:if test="#addrpool.aggregateId >=0">
			<tr>
				<%-- <td><s:property value="aggregateMap[#addrpool.aggregateId].name"/></td>
				<td><s:property value="aggregateMap[#addrpool.aggregateId].availabilityZoneName"/></td> --%>
				<td><s:property value="addrTypeMap[#addrpool.type]"/></td>
				<td><s:property value="#addrpool.ipStart"/>&nbsp;-&nbsp;<s:property value="#addrpool.ipEnd"/></td>
				<td><s:property value="#addrpool.netmask"/></td>
				<td><s:property value="#addrpool.gateway"/></td>
				<td><s:property value="#addrpool.dns"/></td>
				<td>
					<a class="blueletter" href="javascript:void(0)" onclick="showDetail(this,'<s:property value="#addrpool.id"/>')">详情</a>
					<a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="delNet(this,'<s:property value="#addrpool.id"/>')">删除</a>
				</td>
			</tr>
			</s:if>
			</s:iterator>
			</table>
		</s:iterator>
		<h3>节点列表</h3>
		<table class="datatable" border="1">
			<tr>
				<td class="centeralign">节点</td>
				<td class="centeralign">操作</td>
			</tr>
			<s:iterator value="hostList" id="hostList">
				<tr>
					<td class="centeralign" width="354px"><s:property value="#hostList.ip"/></td>
					<td class="centeralign"><a href="system/hostservice?hostId=<s:property value="#hostList.id"/>">详情</a></td>
				</tr>
			</s:iterator>
		</table>
		<h3>用户群组列表</h3>
		<table class="datatable" border="1">
			<tr>
				<td class="centeralign">群组名称</td>
				<td class="centeralign">集群名称</td>
			</tr>
			<s:iterator value="groupClusterMap" id="map">
				<tr>
					<td class="centeralign"><s:property value="key.name"/></td>
					<td class="centeralign">
						<s:iterator value="value" id="cluster">
							<a href="system/clusterdetail?id=<s:property value='#cluster.id'/>"><s:property value="#cluster.name"/></a>&nbsp;
						</s:iterator>
					</td>
				</tr>
			</s:iterator>
		</table>
		</div>
	</div>
</div>
<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_common_js.jsp"></s:include> 
 
 </body>
 <script>
 $(function(){
	 
	}); 
 function showDetail(obj,poolId){
	 $.post("net/netdetail",{poolId:poolId},function(data){
	 	$(obj).html("收起");
	 	$(obj).attr("onclick", "hideDetail(this,'"+poolId+"')");
	 	var detailTable = 
	 		"<tr class='yellowbox padding10'><td></td>" +
	 			"<td colspan='7'>";
	 	if(data.addressPool.ips == null || data.addressPool.ips.length == 0){
	 		detailTable += "该网段的ip地址还没有被使用过</td></tr>";
	 	} else {
	 		var ipStart = data.addressPool.ipStart;
	 		var ipEnd = data.addressPool.ipEnd;
	 		var allCount = ipEnd.slice(ipEnd.lastIndexOf('.')+1) - 
	 					   ipStart.slice(ipStart.lastIndexOf('.')+1) + 1;
	 		var usedCount = 0;
	 		$.each(data.addressPool.ips,function(i,ip) {
	 			usedCount = usedCount + 1;
	 		});
	 		
	 		detailTable += 
	 			"<h3>IP总量：" +  allCount + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "剩余IP数量：" + (allCount-usedCount) + "</h3>" +
	 			"<table border='1'>";
	 		
	 		detailTable	+= "<tr><td>IP地址</td><td>状态</td><td>MAC地址</td><td>虚拟机</td></tr>";
	 		$.each(data.addressPool.ips,function(i,ip) {
	 			detailTable += 
	 				"<tr>" +
	 					"<td>"+ip.ipAddress+"</td>" +
	 					"<td>"+ip.status+"</td>" +
	 					"<td>"+ip.macAddress+"</td>" +
	 					"<td>"+ip.serverName+"</td>" +
	 				"</tr>";
	 		});
	 			
	 		detailTable += "</table></td></tr>";
	 	}
	 	$(obj).parent().parent().after(detailTable);
	 });
	 }

	 //隐藏集群IP详情
	 function hideDetail(obj,poolId){
	 	$(obj).html("详情");
	 	$(obj).attr("onclick", "showDetail(this,'"+poolId+"')");
	 	var nextTr = $(obj).parent().parent().next();
	 	if(nextTr.attr("class").indexOf("yellowbox")>=0){
	 		nextTr.remove();
	 	}
	 }

	 //删除ip段
	 function delNet(obj,poolId){
	 	if(confirm("确认删除该IP段吗？")){
	 		$.post("net/delnet",{poolId:poolId},function(data){
	 			if(data.result=="success"){
	 				fillTipBox("success","删除成功");
	 				location.reload();
	 			} else {
	 				fillTipBox("error","该IP段已被使用，无法删除！");
	 			}
	 		});
	 	}
	 }
 </script>
</html>
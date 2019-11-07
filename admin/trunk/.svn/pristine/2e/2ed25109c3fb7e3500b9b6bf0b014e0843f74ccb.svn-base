<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>网络管理 - 云海IaaS</title>
  	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
	<s:include value="/template/_banner.jsp?menu=sys" />
	<div id="inner">
	    <input type="hidden" id="zid" value='<s:property value="zid"/>'/>
	    <input type="hidden" id="zname" value='<s:property value="zname"/>'/>
		<s:include value="/system/_left.jsp?menu=netlist" />
		<div class="right">
			<div class="divline bottommargin_20">
				网络管理
				<a class="button rightfloat" href="net/prenewnet?zoneId=<s:property value="zid"/>" rel="facebox" title="新建IP池" size="s">新建IP池</a>
			</div>
			<s:if test='addressPools.size() == 0'>
              <h3 class="centeralign">还没有网络管理</h3>
            </s:if>
            <s:else>
        <s:iterator value="addrPoolByCluster" id = "addrPoolList">
           <h3><s:property value="aggregateMap[key].name"/> 
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 外网IP总量：<s:property value="ipNumMap[key]['totalPublic']"/>
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
			</s:else>
		</div>
	</div>
	<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_common_js.jsp"></s:include> 
</body>
<script>
//显示集群IP详情
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
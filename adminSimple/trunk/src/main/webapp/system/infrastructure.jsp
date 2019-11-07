<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>综述首页 - 云海IaaS</title>
 	<%--<s:include value="/template/_head.jsp" />--%>
 	<style>
 		#square {
			float: left;
			width: 15.73%;
			height: 130px;
			border: 1px solid #CCC;
			margin-top: 1%;
			overflow: hidden;
		}
		.itemtable td{
			padding: 7px 0px 7px 0px;
		}
 	</style>
</head>
<body>
<div id="container">
<s:include value="/template/_banner.jsp?menu=shouye" />
<div id="inner">
	<s:include value="/system/_left.jsp?menu=infrastructure" />
	<div class="right"> 
		<div class="divline topmargin_10">综述首页</div>
		<div class="contentline">
			
			<table class="itemtable topmargin_10 bottommargin_10" >
				<tr class="tabletitle leftmargin_15">
					<td colspan="6">
						&nbsp;&nbsp;<img src="http://iaas.free4lab.com/images/my_vm_little.png">&nbsp;&nbsp;基础服务状态
					</td>
				</tr>
				<tr>
					<s:iterator id="service" value="acServices" status="st"> 
					<s:if test="#st.count < 5">
					<td class="grey1border">
					
					<table>
						<tr><td>
							<div class="centeralign">
								<s:if test="#service.serviceStatus.toString() == 'RUNNING'"><img src="images/inf_normal.png"></s:if>
								<s:elseif test="#service.serviceStatus.toString() == 'STOPED'"><img src="images/inf_error.png"></s:elseif>
								<s:elseif test="#service.serviceStatus.toString() == 'INIT'"><img src="images/inf_alert.png"></s:elseif>			
							</div>
						</td></tr>
						<tr><td>
							<div class="centeralign">
								<s:property value="#service.type"/>
								<br/>
								<s:property value="#service.hostIp"/>
							</div>
						</td>
						</tr>
					</table>
					</td>
					</s:if>
					</s:iterator>
				</tr>
				<tr>
					<s:iterator id="service" value="acServices" status="st"> 
					<s:if test="#st.count >= 5">
					<td class="grey1border" width="191px" height="60px">
					
					<table>
						<tr><td>
							<div class="centeralign topmargin_10">
								<img src="images/inf_normal.png">
							</div>
						</td></tr>
						<tr><td>
							<div class="centeralign">
								<s:property value="#service.type"/>
								<br/>
								<s:property value="#service.hostIp"/>
							</div>
						</td>
						</tr>
					</table>
					</td>
					</s:if>
					</s:iterator>
				</tr>
			</table>
			<table class="itemtable bottommargin_10" >
				<tr class="tabletitle leftmargin_15">
					<td colspan="6">
						&nbsp;&nbsp;<img src="images/user_sum.png">&nbsp;&nbsp;云平台概览
					</td>
				</tr>
				<tr>
						
						<td class="grey1border" width="191px" height="60px">
							<div class="centeralign">
							<span class="greyletter" style="line-height:120%; font-family: Arial, Helvetica, sans-serif; font-size: 50px;">
											<s:property value="hostNum"/></span>
							<br/><span class="bigsize">&nbsp;&nbsp;<a class="blueletter" href="/admin/system/host?zid=1">服务器</a></span>
							</div>
						</td>

						<td class="grey1border" width="191px" height="60px">
							 <div class="centeralign">
							<span class="greyletter" style="line-height:120%; font-family: Arial, Helvetica, sans-serif; font-size: 50px;"><s:property value="ipNumList[1]"/>/<s:property value="ipNumList[0]"/></span>
							<br/><span class="bigsize"><a class="blueletter" href="/admin/net/netlist?zid=1">外网IP</a></span>
							</div> 
						</td>
						<td class="grey1border" width="191px" height="60px">
							<div class="centeralign">
							<span class="greyletter" style="line-height:120%; font-family: Arial, Helvetica, sans-serif; font-size: 50px;"><s:property value="ipNumList[3]"/>/<s:property value="ipNumList[2]"/></span>
							<br/><span class="bigsize"><a class="blueletter" href="/admin/net/netlist?zid=1">内网IP</a></span>
							</div>
						</td>
						<td class="grey1border"  width="191px" height="60px">
							<div class="centeralign">
							<span class="greyletter" style="line-height:120%; font-family: Arial, Helvetica, sans-serif; font-size: 50px;"><s:property value="aggNum"/></span>
							<br/><span class="bigsize">&nbsp;&nbsp;<a class="blueletter" href="/admin/system/cluster?zid=1">集群</a></span>
							</div>
						</td>
						
				</tr>
				<tr>
						<td class="grey1border" width="191px" height="60px">
							<div class="centeralign">
							<span class="greyletter" style="line-height:120%; font-family: Arial, Helvetica, sans-serif; font-size: 50px;"><s:property value="enNum"/>/<s:property value="userNum"/></span>
							<br/><span class="bigsize">&nbsp;&nbsp;<a class="blueletter" href="/admin/user/usermanage.jsp">企业用户/用户</a></span>
							</div>
						</td>
						<td class="grey1border" width="191px" height="60px">
							<div class="centeralign">
							<span class="greyletter" style="line-height:120%; font-family: Arial, Helvetica, sans-serif; font-size: 50px;"><s:property value="serverNum"/></span>
							<br/><span class="bigsize">&nbsp;&nbsp;<a class="blueletter" href="/admin/vm/presearchvm">云主机</a></span>
							</div>
						</td>
						<td class="grey1border" width="191px" height="60px">
							<div class="centeralign">
							<span class="greyletter" style="line-height:120%; font-family: Arial, Helvetica, sans-serif; font-size: 50px;"><s:property value="volNum"/></span>
							<br/><span class="bigsize">&nbsp;&nbsp;<a class="blueletter" href="/admin/hd/hdmanage">云硬盘</a></span>
							</div>
						</td>
						<td class="grey1border" width="191px" height="60px">
							<div class="centeralign">
							<span class="greyletter" style="line-height:120%; font-family: Arial, Helvetica, sans-serif; font-size: 50px;"><s:property value="imgNum"/></span>
							<br/><span class="bigsize">&nbsp;&nbsp;<a class="blueletter" href="/admin/img/imglist?type=IMAGE">模板</a></span>
							</div>
						</td>
						
				</tr>
			</table>
			<br/>
		 	<!-- <div>
					<table>
						<tbody><tr>
							<td width="70px" class="midsize strong">操作日志</td>
							<td><a class="blueletter" href="runtime/admin_log.jsp?type=all" target="_blank">历史操作</a></td>
						</tr>
					</tbody></table>
			</div>
			<div id="query"></div> -->
		</div><!--contentline-->
	</div><!--right-->
	</div><!--#inner-->
<s:include value="/template/_footer.jsp" />
</div><!--#container-->
</body>
</html>
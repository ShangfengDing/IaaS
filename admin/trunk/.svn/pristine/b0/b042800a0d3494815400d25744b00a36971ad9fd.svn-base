<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>节点管理 - 云海IaaS</title>
  	<s:include value="/template/_head.jsp" />
	<link rel="stylesheet" href="<%=Constants.FRONT_URL%>css/ui.daterangepicker.css" type="text/css" />
    <link rel="stylesheet" href="<%=Constants.FRONT_URL%>css/jquery-ui-1.7.1.custom.css" type="text/css" title="ui-theme" />
</head>
<body>
<div id="container">	
<s:include value="/template/_banner.jsp?menu=sys" />
<div id="inner">
	<s:include value="/system/_left.jsp?menu=host" />
    <div class="right"> 
		<div class="divline topmargin_10">节点管理</div>
		<div class="contentline">
			<div class="topmargin_10 bottommargin_10 middleveralign">
	            <table>
                <tr>
                    <td class="rightalign" width="70px">主机IP</td>
                    <td colspan="2" width="200px">
                    	<input type="text" class="leftmargin_10 squareinputlt" style="width:150px;" name="ip" id="ip" />
                    </td>
                    <td class="rightalign" width="70px">主机类型</td>
                    <td colspan="2" width="200px">
                    	<select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="type" >
	                        <option value="">--全部--</option>
	                        <option value="COMPUTE_NODE">COMPUTE_NODE</option>
	                        <option value="FUNCTION_NODE">FUNCTION_NODE</option>
                    	</select>
                    </td>
                    <th class="rightalign" width="70px">主机状态</th>
                    <td colspan="2" width="200px">
	                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="status" >
	                        <option value="">--全部--</option>
	                        <option value="CRASH">CRASH</option>
	                        <option value="HIGH_LOAD">HIGH_LOAD</option>
	                        <option value="LOW_LOAD">LOW_LOAD</option>
	                        <option value="NORMAL_LOAD">NORMAL_LOAD</option>
	                    </select>
                    </td>
                 </tr>
                 <tr height="45px">
                   <%--  <td class="rightalign" width="70px">数据中心</td>
                    <td colspan="2" width="200px">
                    	<select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="zoneId" onchange="getAggregate()">
		                    <option value="">--全部--</option>
	                        <s:iterator id="zone" value="zones">
		                    <option value="<s:property value="#zone.id"/>"><s:property value="#zone.name"/></option>
		                    </s:iterator>
	                    </select>
                    </td> --%>
                    <td class="rightalign" width="70px">集群</td>
                    <td colspan="2" width="200px">
	                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="aggregateId">
	                        <option value="">--全部--</option>
	                    </select>
                    </td>
                    <td class="rightalign" width="70px">运行服务</td>
                    <td colspan="2" width="200px">
	                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="service" >
	                        <option value="">--全部--</option>
	                        <option value="DHCP_CONTROLLER">DHCP_CONTROLLER</option>
	                        <option value="IMAGE_SERVER">IMAGE_SERVER</option>
	                        <option value="LOL_SERVER">LOL_SERVER</option>
	                    	<option value="NETWORK_PROVIDER">NETWORK_PROVIDER</option>
	                    	<option value="NODE_MONITOR">NODE_MONITOR</option>
	                    	<option value="RESOURCE_SCHEDULER">RESOURCE_SCHEDULER</option>
	                    	<option value="VM_CONTROLLER">VM_CONTROLLER</option>
	                    	<option value="VM_SCHEDULER">VM_SCHEDULER</option>
	                    	<option value="VOLUME_PROVIDER">VOLUME_PROVIDER</option>
	                    	<option value="VOLUME_SCHEDULER">VOLUME_SCHEDULER</option>
	                    	<option value="UNKNOWN">UNKNOWN</option>
	                    </select>
                    </td>
                    <td class="rightalign" width="70px">CPU核数</td>
                    <td width="50px">
                    	<select class="leftmargin_10 selectbox" style="width:50px;height:24px;" id="cpuOperator">
		                    <option value="0">&lt;</option>
	                        <option value="1">&lt;=</option>
	                        <option value="2">=</option>
	                        <option value="3">&gt;=</option>
	                        <option value="4">&gt;</option>
	                    </select>
	                </td>
	                <td>
                    	<input type="text" class="squareinputlt" style="width:98px;" name="cpuCount" id="cpuCount"/>
                    </td>
                 </tr>
                 <tr>
                    
                    <td class="rightalign" width="70px">内存大小</td>
                    <td width="50px">
                    	<select class="leftmargin_10 selectbox" style="width:50px;height:24px;" id="memoryOperator">
		                    <option value="0">&lt;</option>
	                        <option value="1">&lt;=</option>
	                        <option value="2">=</option>
	                        <option value="3">&gt;=</option>
	                        <option value="4">&gt;</option>
	                    </select>
	                </td>
	                <td>
                    	<input type="text" class="leftmargin_0 squareinputlt" style="width:98px;" name="memoryCount" id="memoryCount"/>
                    </td>
                    <td class="rightalign" width="70px">硬盘大小</td>
                    <td width="50px">
                    	<select class="leftmargin_10 selectbox" style="width:50px;height:24px;" id="diskOperator">
		                    <option value="0">&lt;</option>
	                        <option value="1">&lt;=</option>
	                        <option value="2">=</option>
	                        <option value="3">&gt;=</option>
	                        <option value="4">&gt;</option>
	                    </select>
	                </td>
	                <td>
                    	<input type="text" class="squareinputlt" style="width:98px;" name="diskCount" id="diskCount"/>
                    </td>
                 </tr>
                 
                 <tr height="50px">
                 	<td class="rightalign" width="70px"></td>
                    <td colspan="6" width="470px">
                    	<span id="error" class="leftmargin_10 redletter"></span>
                    </td>
                    <td colspan="2">
                          <input type="button" value="确定" class="button leftmargin_10" onclick="submitAll(1)" />
                          <a href="javascript:void(0)" onclick="cancelSearch()" class="blueletter leftmargin_10">取消</a>
                    </td>
                 </tr>
                 
              </table>
	        <div class="dottedline topmargin_20"></div>
	        <div id="highcharts" class="hidden"></div>
	        <div id="query"></div>
		</div>
	</div>
</div>
</div>
<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_common_js.jsp"></s:include> 


<script src="js/system/searchhost.js"></script>
<script src="<%=Constants.FRONT_URL%>js/plugin/highcharts.js"></script>
</body>
</html>
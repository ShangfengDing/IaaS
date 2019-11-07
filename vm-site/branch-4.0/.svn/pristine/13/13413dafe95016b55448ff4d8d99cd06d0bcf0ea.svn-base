<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<%-- <div class="rightalign bottommargin_5 "> 
 	<button class="graybutton leftbutton selected" id="day" onclick="updateMonitor('day','${param.uuid}')">今天</button> 
	<button class="graybutton middlebutton" id="month" onclick="updateMonitor('month','${param.uuid}')">本月</button>
	<button class="graybutton rightbutton" id="year"onclick="updateMonitor('year','${param.uuid}')">本年</button> 
</div> --%>
<table border="1"><tr>
	<td class="centeralign" width="50%">CPU使用率(%)<br/><br/><div id="cpuhighcharts" class="current"></div></td>
	<td class="centeralign">磁盘读写(B/s)<br/><br/><div id="iohighcharts" class="current"></div></td>	
</tr><tr>
	<td class="centeralign" width="50%">网络收发(B/S)<br/><br/><div id="nethighcharts" class="current"></div></td>
	<td><input type=hidden id="vmMonitorId" value="${param.uuid}"/></td>
</tr>
</table>
<script src="js/vm/vmmonitor.js"></script>
</body>
</html>
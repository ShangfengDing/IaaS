<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<div class="rightalign bottommargin_5 ">
<input type="hidden" id="hostId" name="hostId" value="${param.hostName}" />
 	<%-- <button class="graybutton leftbutton selected" id="day" onclick="updateMonitor('day','${param.hostName}')">今天</button> 
	<button class="graybutton middlebutton" id="month" onclick="updateMonitor('month','${param.hostName}')">本月</button>
	<button class="graybutton rightbutton" id="year"onclick="updateMonitor('year','${param.hostName}')">本年</button>  --%>
</div>
<table border="1"><tr>
	<td class="centeralign" width="33%"><div>CPU使用率(%)<br/><div id="cpuhighcharts"></div></div></td>
	<td class="centeralign" width="33%"><div>内存占用率(%)<br/><div id="memhighcharts"></div></div></td>
	<td class="centeralign" width="34%"><div>硬盘占用率(%)<br/><div id="diskhighcharts"></div></div></td>	
</tr><tr>
    <td class="centeralign" width="33%"><div>平均负载<br/><div id="loadhighcharts"></div></div></td>
	<td class="centeralign" width="33%"><div>磁盘读写(MB/s)<br/><div id="iohighcharts"></div></div></td>	
	<td class="centeralign" width="34%"><div>网络收发(MB/S)<br/><div id="nethighcharts"></div></div></td>
</tr>
</table>
<script src="js/system/hostmonitor.js"></script>
</body>
</html>
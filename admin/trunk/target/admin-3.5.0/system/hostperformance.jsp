<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<input type="hidden" id="hostId" name="hostId" value="${param.hostName}" />
<input type="hidden" id="hostMem" name="hostMem" value="${param.hostMem}" />
<input type="hidden" id="hostDisk" name="hostDisk" value="${param.hostDisk}" />
<table><tr>
    <td class="blueletter">节点硬件配置</td>    
    <td>CPU：${param.hostCPU}个；内存：${param.hostMem}MB；硬盘：${param.hostDisk}GB</td>
</tr><tr>
	<td class="blueletter" width="100px">CPU使用率(%)</td>
	<td id="performance1"></td>
</tr><tr>
	<td class="blueletter">内存占用率(%)</td>	
	<td id="performance2"></td>
</tr><tr>
    <td class="blueletter">硬盘占用率(%)</td>    
    <td id="performance3"></td>
</tr><tr>
    <td class="blueletter">平均负载</td>    
    <td id="performance4"></td>
</tr><tr>
    <td class="blueletter">磁盘读出(MB/s)</td>    
    <td id="performance5"></td>
</tr><tr>
    <td class="blueletter">磁盘写入(MB/s)</td>    
    <td id="performance6"></td>
</tr><tr>
    <td class="blueletter">网络接收(MB/S)</td>    
    <td id="performance7"></td>
</tr><tr>
    <td class="blueletter">网络发出(MB/S)</td>    
    <td id="performance8"></td>
</tr>
</table>
<script>
$(function(){
	var hostId = $("#hostId").val();
	showLoading();
	updatePerformance('second',hostId);
	hideLoading();
	var wait = setInterval(function(){
		if($("#facebox").css("display") == "none"){
            clearInterval(wait);
        }else{
    		updatePerformance('second',hostId);
        }
	}, 3000);
}); 

function updatePerformance(type, hostName) {
	var url="system/hostmonitor";    
    $.post(url, {type:type,hostName:hostName}, function(data){
        //console.log(data.cpuPercent);
        var hostMem = $("#hostMem").val();
        var hostDisk = $("#hostDisk").val();
        var mem = accMul(hostMem, data.memPercent[0]);
        var disk = accMul(hostDisk, data.diskPercent[0]);
        mem = accMul(mem, 0.01);
        disk = accMul(disk, 0.01);
        $("#performance1").html(data.cpuPercent[0].toFixed(2)+"%");
        $("#performance2").html(data.memPercent[0].toFixed(2)+
        		"% ("+mem.toFixed(0)+"MB/"+hostMem+"MB)");
        $("#performance3").html(data.diskPercent[0].toFixed(2)+
        		"% ("+disk.toFixed(2)+"GB/"+hostDisk+"GB)");
        $("#performance4").html(data.loadaverage[0].toFixed(2));
        $("#performance5").html(data.diskReadRate[0].toFixed(2));
        $("#performance6").html(data.diskWriteRate[0].toFixed(2));
        $("#performance7").html(data.netInPercent[0].toFixed(2));
        $("#performance8").html(data.netOutPercent[0].toFixed(2));
    });
}

/**
 * 以下是本文件中用到的内部函数
 */
//浮点数相乘精度问题
function accMul(arg1,arg2) 
{ 
    var m=0,s1=String(arg1),s2=String(arg2);
    try{m+=s1.split(".")[1].length}catch(e){};
    try{m+=s2.split(".")[1].length}catch(e){};
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}
</script>
</body>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<input type="hidden" id="hostsId" value="<s:property value="hostsId"/>"/>
<input type="hidden" id="serverId" value="<s:property value="serverId"/>"/>
<input type="hidden" id="migrateType" value="<s:property value="type"/>"/>
<div class="bigsize strong bottommargin_5">集群<s:property value="server.aggregate"/>中共有<s:property value="hosts_num"/>个节点</div>
	<div>
		<table class="datatable">
			<tr><td class="padding5"></td><td>节点信息</td><td>CPU</td><td>内存</td><td>硬盘</td><td>操作</td>         
			</tr>
			<s:iterator value="hostlist" id="hosts" status="st">
			<tr>
				<td class="padding5">
				<s:if test='#st.index!=hostFlag'><input type="radio" value="<s:property value="#hosts.id"/>" name="radio"/>
				</s:if>
				<td><a class="greyletter"><s:property value="#hosts.ip" /></a><br />
				    <a class="greyletter"><s:property value="#hosts.id" /></a>
				</td>
				<td><a class="greyletter" id="<s:property value="#hosts.id"/>"><s:property value="hostIdResourceMap[#hosts.id].cpu" />核</a><br />
					<a class="greyletter" id="0-<s:property value="#hosts.id"/>"></a>
				</td>
				<td><a class="greyletter" ><s:property value="hostIdResourceMap[#hosts.id].memoryMb" />Mb</a><br />
					<a class="greyletter" id="1-<s:property value="#hosts.id"/>"></a>
					<input type="hidden" id="M-<s:property value="#hosts.id"/>" value="<s:property value="hostIdResourceMap[#hosts.id].memoryMb"/>"/>
				</td>
				<td><a class="greyletter" ><s:property value="hostIdResourceMap[#hosts.id].diskGb" />Gb</a><br />
					<a class="greyletter" id="2-<s:property value="#hosts.id"/>"></a>
					<input type="hidden" id="D-<s:property value="#hosts.id"/>" value="<s:property value="hostIdResourceMap[#hosts.id].diskGb"/>"/>
				</td>
				<td><a class="blueletter">实时状态</a></td>  
			</tr>
			</s:iterator>
		</table>
		<table class="formtable topmargin_10 ">
		<tr>
			<td><input type="button" value="确定" class="button" onclick="submitMigrate()" />
			    <a href="javascript:void(0)" class="blueletter leftmargin_10 " onclick="$(document).trigger('close.facebox');">取消</a>
			</td>         
		</tr>
		</table>
	</div>

<script>
getStyle();
</script>
<script>
$(function(){
	var hostsId = $("#hostsId").val();
	var host_strs = new Array();
	host_strs = hostsId.split(";");
	for(var i=0; i<host_strs.length; i++){
		updatePerformance('second',host_strs[i]);
	}
	/* showLoading();
	hideLoading();
	var wait = setInterval(function(){
		if($("#facebox").css("display") == "none"){
            clearInterval(wait);
        }else{
        	for(var i=0; i<host_strs.length; i++){
        		updatePerformance('second',host_strs[i]);
        	}
        }
	}, 3000); */
}); 

function updatePerformance(type, hostId) {
	var url="vm/hostsload";
    $.post(url, {type:type,hostId:hostId}, function(data){
        //console.log(data.cpuPercent);
        var hostMem = $("#M-"+data.hostId).val();
        var hostDisk = $("#D-"+data.hostId).val();
        var mem = accMul(hostMem, data.memPercent[0]);
        var disk = accMul(hostDisk, data.diskPercent[0]);
        mem = accMul(mem, 0.01);
        disk = accMul(disk, 0.01);
        $("#0-"+data.hostId).html(data.cpuPercent[0].toFixed(2)+"%");
        $("#1-"+data.hostId).html(data.memPercent[0].toFixed(2)+
        		"% ("+mem.toFixed(0)+"MB/"+hostMem+"MB)");
        $("#2-"+data.hostId).html(data.diskPercent[0].toFixed(2)+
        		"% ("+disk.toFixed(2)+"GB/"+hostDisk+"GB)");
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
function submitMigrate(){
	var radio_value = "";
	$("input[name='radio']:radio").each(function(){ 
        if($(this).attr("checked")){
            radio_value = $(this).val();
        }
    });
	var type = $("#migrateType").val();
	if(type == "online"){
		var url="vm/onlineMigrateserver";
		console.log("online");
	}else{
		var url="vm/migrateserver";
		console.log("offline");
	}
	
	var serverId = $("#serverId").val();
	showLoading();
	$.post(url,{hostsUuid:radio_value, serverId:serverId}, function(data){
		if(data.status == true){
			hideLoading();
			$(document).trigger('close.facebox');
			fillTipBox('success','虚拟机迁移成功。');
		}
		else{
			hideLoading();
			$(document).trigger('close.facebox');
			fillTipBox('error','虚拟机迁移失败。');
		}
	});
}
</script>
</body>
</html>
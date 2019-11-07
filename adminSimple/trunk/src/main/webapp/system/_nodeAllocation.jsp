<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants,appcloud.admin.action.system.VmSummaryAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<div id="allocationTable" class="panel panel-default front-panel" style="margin-top:10px;" >
    <table class="table table-striped multi-table" style="margin-bottom: 0px;">
        <thread>
            <tr>
                <th></th>
                <th>CPU</th>
                <th>内存</th>
                <th>硬盘</th>
            </tr>
        </thread>
        <tr>
            <td>硬件配置</td>
            <td id="confCPU"><s:property value="hostCPU"/>个</td>
            <td id="confMem"><s:property value="hostMem"/>MB</td>
            <td id="confDisk"><s:property value="hostDisk"/>GB</td>
        </tr>
        <tr>
            <td>超售比例(%)</td>
            <td id="cpu_oversell"><s:property value="cpu_oversell"/>%</td>
            <td id="memory_oversell"><s:property value="memory_oversell"/>%</td>
            <td id="disk_oversell"><s:property value="disk_oversell"/>%</td>
        </tr>
        <tr>
            <td>可供销售</td>
            <td id="cpu_all"></td>
            <td id="memory_all"></td>
            <td id="disk_all"></td>
        </tr>
        <tr>
            <td>实际销售</td>
            <td id="cpu_sell"><s:property value="cpu_sell"/>个</td>
            <td id="memory_sell"><s:property value="memory_sell"/>MB</td>
            <td id="disk_sell"><s:property value="disk_sell"/>GB</td>
        </tr>
        <tr>
            <td>剩余资源</td>
            <td id="cpu_remain"></td>
            <td id="memory_remain"></td>
            <td id="disk_remain"></td>
        </tr>
        <tr>
            <td>销售比例(%)</td>
            <td id="cpu_rate"></td>
            <td id="memory_rate"></td>
            <td id="disk_rate"></td>
        </tr>
    </table>
</div>
<script>
    var cpu_oversell=<s:property value="cpu_oversell"/>;
    var hostCPU=<s:property value="hostCPU"/>;
    var cpu_all=parseInt(cpu_oversell)*parseInt(hostCPU)/100;
    $('#cpu_all').html(cpu_all+"个");
    var memory_oversell=<s:property value="memory_oversell"/>
    var hostMem=<s:property value="hostMem"/>;
    var memory_all=parseFloat(memory_oversell) *(parseFloat(hostMem)-4*1024)/100;
    $('#memory_all').html(memory_all+"MB");
    var disk_oversell=<s:property value="disk_oversell"/>;
    var hostDisk=<s:property value="hostDisk"/>;
    var disk_all=parseFloat(disk_oversell)*parseFloat(hostDisk)/100;
    $('#disk_all').html(disk_all+"GB");
    $("#cpu_remain").html(parseInt($("#cpu_all").html()) - parseInt(<s:property value="cpu_sell"/>) + "个");
    $("#memory_remain").html(parseInt($("#memory_all").html()) - parseInt(<s:property value="memory_sell"/>) + "MB");
    $("#disk_remain").html(parseInt($("#disk_all").html()) - parseInt(<s:property value="disk_sell"/>) + "GB");

    $("#cpu_rate").html((parseInt($("#cpu_sell").html()) * 100 / parseInt($("#confCPU").html())).toFixed(2) + "%");
    $("#memory_rate").html((parseInt($("#memory_sell").html()) * 100 / parseInt($("#confMem").html())).toFixed(2) + "%");
    $("#disk_rate").html((parseInt($("#disk_sell").html()) * 100 / parseInt($("#confDisk").html())).toFixed(2) + "%");


</script>
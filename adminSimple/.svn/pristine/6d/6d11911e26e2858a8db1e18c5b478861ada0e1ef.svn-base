<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants,appcloud.admin.action.system.VmSummaryAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%--<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>--%>
<input type="hidden" id="uuid" value="${param.id}"/>
<div id="htloadTable">
    <div class="panel panel-default front-panel" style="height: 250px;">
        <%--<div class="panel-heading">--%>
        <%--<strong>基础设施详情</strong>--%>
        <%--</div>--%>
        <div class="panel-body">
            <%--<s:iterator value="cpuUsed" id="cpuUsed">--%>
            <%--<s:property value="#cpuUsed"/>--%>
            <%--</s:iterator>--%>
            <div id="cpu" style="height:220px"></div>
        </div>
    </div>

    <div class="panel panel-default front-panel" style="height: 250px;margin-top: 30px">
        <%--<div class="panel-heading">--%>
        <%--<strong>基础设施详情</strong>--%>
        <%--</div>--%>
        <div class="panel-body">
            <%--<s:iterator value="cpuUsed" id="cpuUsed">--%>
            <%--<s:property value="#cpuUsed"/>--%>
            <%--</s:iterator>--%>
            <div id="memUsed" style="height:220px"></div>
        </div>
    </div>

    <div class="panel panel-default front-panel" style="height: 250px;margin-top: 30px">
        <%--<div class="panel-heading">--%>
        <%--<strong>基础设施详情</strong>--%>
        <%--</div>--%>
        <div class="panel-body">
            <%--<s:iterator value="cpuUsed" id="cpuUsed">--%>
            <%--<s:property value="#cpuUsed"/>--%>
            <%--</s:iterator>--%>
            <div id="memSwap" style="height:220px"></div>
        </div>
    </div>


    <div class="panel panel-default front-panel" style="height: 250px;margin-top: 30px">
        <%--<div class="panel-heading">--%>
        <%--<strong>基础设施详情</strong>--%>
        <%--</div>--%>
        <div class="panel-body">
            <%--<s:iterator value="cpuUsed" id="cpuUsed">--%>
            <%--<s:property value="#cpuUsed"/>--%>
            <%--</s:iterator>--%>
            <div id="diskUsed" style="height:220px"></div>
        </div>
    </div>

    <div class="panel panel-default front-panel" style="height: 250px;margin-top: 30px">
        <%--<div class="panel-heading">--%>
        <%--<strong>基础设施详情</strong>--%>
        <%--</div>--%>
        <div class="panel-body">
            <%--<s:iterator value="cpuUsed" id="cpuUsed">--%>
            <%--<s:property value="#cpuUsed"/>--%>
            <%--</s:iterator>--%>
            <div id="diskErrorCount" style="height:220px"></div>
        </div>
    </div>

    <div class="panel panel-default front-panel" style="height: 250px;margin-top: 30px">
        <%--<div class="panel-heading">--%>
        <%--<strong>基础设施详情</strong>--%>
        <%--</div>--%>
        <div class="panel-body">
            <%--<s:iterator value="cpuUsed" id="cpuUsed">--%>
            <%--<s:property value="#cpuUsed"/>--%>
            <%--</s:iterator>--%>
            <div id="netDownload" style="height:220px"></div>
        </div>
    </div>

    <div class="panel panel-default front-panel" style="height: 250px;margin-top: 30px">
        <%--<div class="panel-heading">--%>
        <%--<strong>基础设施详情</strong>--%>
        <%--</div>--%>
        <div class="panel-body">
            <%--<s:iterator value="cpuUsed" id="cpuUsed">--%>
            <%--<s:property value="#cpuUsed"/>--%>
            <%--</s:iterator>--%>
            <div id="netUpload" style="height:220px"></div>
        </div>
    </div>






</div>
<script src="statics/js/jquery/jquery.min.js"></script>
<script src="statics/echart/echarts.js"></script>
<script src="statics/echart/echarts-liquidfill.js"></script>
<script>
    $('#servicecount').html("("+'<s:property value="count"/>'+")");
    var cpu1 = document.getElementById("cpu");
    var mycpu = echarts.init(cpu1);

    var memSwap = document.getElementById("memSwap");
    var mycpuMemS = echarts.init(memSwap);

    var netDownload = document.getElementById("netDownload");
    var mycpuNetDownload = echarts.init(netDownload);

    var netUpload = document.getElementById("netUpload");
    var mycpuNetUpload= echarts.init(netUpload);

    var diskUsed = document.getElementById("diskUsed");
    var mycpuDiskUsed = echarts.init(diskUsed);

    var diskErrorCount = document.getElementById("diskErrorCount");
    var mycpuDiskErrorCount= echarts.init(diskErrorCount);

    var memUsed = document.getElementById("memUsed");
    var mycpuMemUsed = echarts.init(memUsed);

    var timeLine=<s:property value="cpustr"/>;
    var cpuUsed=<s:property value="cpuUsed"/>;
    var memSwapdata=<s:property value="memSwap"/>
    var netDownloaddata=<s:property value="netDownload"/>
    var netUploaddata=<s:property value="netUpload"/>
    var diskUseddata=<s:property value="diskUsed"/>
    var diskErrorCountdata=<s:property value="diskErrorCount"/>
    var memUseddata=<s:property value="memUsed"/>;
    // console.log("cpu:"+cpuUsed)

//    var timeLine=[];
//    var cpuUsed=[];
//    var memSwapdata=[]
//    var netDownloaddata=[]
//    var netUploaddata=[]
//    var diskUseddata=[]
//    var diskErrorCountdata=[]
//    var memUseddata=[]

    // $("#cpu").html("df0");

    optionCPU = {
        color: '#1b9b0c',
        title:{
            // left:'10%',
            text:'CPU使用率'
        },
        tooltip : {
            trigger: 'axis',
            formatter : function (params) {
                return'<div><p>'+params[0].name+'</p><div><div>' +
                    params[0].marker+' CPU使用率: '+params[0].data+' %</div>';
            },
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '80px',
            right: '110px',
            bottom: '15%',
            containLabel: true
        },
        legend: {//
            data:['CPU Used'],
            x:'right',
            top:'15'
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,   //
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series : [
            {
                name:'  Health',
                type:'line',
                data:cpuUsed
            }
        ]
    };
    optionMemU = {
        color: '#1b9b0c',
        title:{
            // left:'10%',
            text:'内存使用率'
        },
        tooltip : {
            trigger: 'axis',
            formatter : function (params) {
                return'<div><p>'+params[0].name+'</p><div><div>' +
                    params[0].marker+' 内存使用率: '+params[0].data+' %</div>';
            },
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '80px',
            right: '110px',
            bottom: '15%',
            containLabel: true
        },
        legend: {//
            data:['CPU Used'],
            x:'right',
            top:'15'
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,   //
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series : [
            {
                name:'  Health',
                type:'line',
                data:memUseddata
            }
        ]
    };
    optionMemS = {
        color: '#1b9b0c',
        title:{
            // left:'10%',
            text:'内存交换率'
        },
        tooltip : {
            trigger: 'axis',
            formatter : function (params) {
                return'<div><p>'+params[0].name+'</p><div><div>' +
                    params[0].marker+' 内存交换率: '+params[0].data+' %</div>';
            },
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '80px',
            right: '110px',
            bottom: '15%',
            containLabel: true
        },
        legend: {//
            data:['CPU Used'],
            x:'right',
            top:'15'
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,   //
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series : [
            {
                name:'  Health',
                type:'line',
                data:memSwapdata
            }
        ]
    };
    optionNetDownload = {
        color: '#1b9b0c',
        title:{
            // left:'10%',
            text:'网络下载'
        },
        tooltip : {
            trigger: 'axis',
            formatter : function (params) {
                return'<div><p>'+params[0].name+'</p><div><div>' +
                    params[0].marker+' 网络下载量: '+params[0].data+' B/s</div>';
            },
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '80px',
            right: '110px',
            bottom: '15%',
            containLabel: true
        },
        legend: {//
            data:['CPU Used'],
            x:'right',
            top:'15'
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,   //
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series : [
            {
                name:'  Health',
                type:'line',
                data:netDownloaddata
            }
        ]
    };
    optionNetUpload = {
        color: '#1b9b0c',
        title:{
            // left:'10%',
            text:'网络上传'
        },
        tooltip : {
            trigger: 'axis',
            formatter : function (params) {
                return'<div><p>'+params[0].name+'</p><div><div>' +
                    params[0].marker+' 速率: '+params[0].data+' B/s</div>';
            },
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '80px',
            right: '110px',
            bottom: '15%',
            containLabel: true
        },
        legend: {//
            data:['CPU Used'],
            x:'right',
            top:'15'
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,   //
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series : [
            {
                name:'  Health',
                type:'line',
                data:netUploaddata
            }
        ]
    };
    optionDiskUsed = {
        color: '#1b9b0c',
        title:{
            // left:'10%',
            text:'硬盘使用率'
        },
        tooltip : {
            trigger: 'axis',
            formatter : function (params) {
                return'<div><p>'+params[0].name+'</p><div><div>' +
                    params[0].marker+' 硬盘使用率: '+params[0].data+' %</div>';
            },
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '80px',
            right: '110px',
            bottom: '15%',
            containLabel: true
        },
        legend: {//
            data:['CPU Used'],
            x:'right',
            top:'15'
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,   //
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series : [
            {
                name:'  Health',
                type:'line',
                data:diskUseddata
            }
        ]
    };
    optionDiskErrorCount = {
        color: '#1b9b0c',
        title:{
            // left:'10%',
            text:'硬盘坏道数'
        },
        tooltip : {
            trigger: 'axis',
            formatter : function (params) {
                return'<div><p>'+params[0].name+'</p><div><div>' +
                    params[0].marker+' 数量: '+params[0].data+' 个</div>';
            },
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '80px',
            right: '110px',
            bottom: '15%',
            containLabel: true
        },
        legend: {//
            data:['CPU Used'],
            x:'right',
            top:'15'
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,   //
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series : [
            {
                name:'  Health',
                type:'line',
                data:diskErrorCountdata
            }
        ]
    };
    mycpu.setOption(optionCPU);
    mycpuMemS.setOption(optionMemS);
    mycpuMemUsed.setOption(optionMemU);
    mycpuNetDownload.setOption(optionNetDownload);
    mycpuNetUpload.setOption(optionNetUpload);
    mycpuDiskUsed.setOption(optionDiskUsed);
    mycpuDiskErrorCount.setOption(optionDiskErrorCount);

//    $.get('system/loadHistoryData',{
//        uuid:hostId
//    },function(data){
//         timeLine=data.cpustr;
//         cpuUsed=data.cpuUsed
//         memSwapdata=data.memSwap;
//         netDownloaddata=data.netDownload;
//         netUploaddata=data.netUpload;
//         diskUseddata=data.diskUsed;
//         diskErrorCountdata=data.diskErrorCount;
//         memUseddata=data.memUsed;
//        mycpu.setOption(optionCPU,true);
//        mycpu2.setOption(optionMemU,true);
//        mycpu3.setOption(optionMemS,true);
//        mycpu4.setOption(optionNetDownload,true);
//        mycpu5.setOption(optionNetUpload,true);
//        mycpu6.setOption(optionDiskUsed,true);
//        mycpu7.setOption(optionDiskErrorCount,true);
//    })
</script>
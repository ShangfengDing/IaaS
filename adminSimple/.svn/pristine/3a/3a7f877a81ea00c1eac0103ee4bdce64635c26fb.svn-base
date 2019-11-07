<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>基础设施详情</title>
    <s:include value="/monitor/_head.jsp"/>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=system" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div>
                <ol class="breadcrumb">
                    <li>系统管理</li>
                    <li><a href="system/hostmanage?page=1">基础设施</a></li>
                    <li class="active">
                        <s:if test="vmSummary!=null">
                            <s:property value='vmSummary.name'/>
                        </s:if>
                        <s:else>
                            <s:property value='name'/>
                        </s:else>
                    </li>
                </ol>
            </div>
            <div class="inner">
                <div style="height: 150px;opacity:1;">
                    <%--<div class="panel-heading">--%>
                    <%--<strong>基础设施详情</strong>--%>
                    <%--</div>--%>
                    <div class="panel-body">
                        <div>

                            <div class="nav-brand">
                                    <img id="avator" alt="icon" class="img-responsive"/>
                            </div>

                        </div>
                        <div>
                            <div class="nav-brand">
                                <h4 class="midia-heading" style="font-size: 22px">
                                    <s:if test="vmSummary!=null">
                                        <span id="myGroupName"><s:property value='vmSummary.name'/></span>&nbsp;&nbsp;
                                    </s:if>
                                    <s:else>
                                        <span id="myGroupName"><s:property value='name'/></span>&nbsp;&nbsp;
                                    </s:else>
                                <span id="permission" class="badge"><s:property value='type'/></span>
                                </h4>
                                <div id="groupdesc" style="word-wrap:break-word;word-break:break-all;">
                                    UUID:&nbsp;&nbsp;<s:property value='uuid'/><br>
                                    <s:if test="vmSummary!=null">
                                    配置:&nbsp;&nbsp;<s:property value='vmSummary.model'/><br>
                                    备注:&nbsp;&nbsp;<s:property value='vmSummary.description'/><br>
                                    </s:if>
                                    <s:else>
                                        配置:&nbsp;&nbsp;<s:property value='model'/><br>
                                        备注:&nbsp;&nbsp;<s:property value='description'/><br>
                                    </s:else>
                                </div>
                            </div>
                        </div>
                        <%--<s:iterator value="cpuUsed" id="cpuUsed">--%>
                        <%--<s:property value="#cpuUsed"/>--%>
                        <%--</s:iterator>--%>
                        <div id="top1" style="height:150px"></div>
                    </div>
                </div>
                <div class="front-toolbar other">
                    <div id="freeshare" class="front-btn-group collapse" data-toggle="buttons">
                        <a  class="btn btn-default front-no-box-shadow"onclick="show1()"><input type="radio" name="options" autocomplete="off">详情1<span id="servicecount"></span></a>
                        <a class="btn btn-default front-no-box-shadow"onclick="showChart()"><input type="radio" name="options" autocomplete="off">实时图表详情<span id="uncontrolcount"></span></a>
                        <a class="btn btn-default front-no-box-shadow"onclick="show3()"><input type="radio" name="options" autocomplete="off">详情3<span id="diskcount"></span></a>
                    </div>
                </div>
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
                        <div id="memUsed" style="height:220px"></div>
                    </div>
                </div>


            </div>
        </div>
    </div>
    <%--<s:include value="/template/_footer.jsp"></s:include>--%>
</div>
</body>
<script src="statics/js/jquery/jquery.min.js"></script>
<script src="statics/echart/echarts.js"></script>
<script src="statics/echart/echarts-liquidfill.js"></script>
<%--<s:include value="/template/_common_js.jsp" />--%>
<script type="text/javascript">
    <%--var a =<s:property value="cpuUsed"/>;--%>

    var top1 = document.getElementById("top1");
    var mytop = echarts.init(top1);

    var cpu1 = document.getElementById("cpu");
    var mycpu = echarts.init(cpu1);

    var memSwap = document.getElementById("memSwap");
    var mycpu2 = echarts.init(memSwap);

    var netDownload = document.getElementById("netDownload");
    var mycpu3 = echarts.init(netDownload);

    var netUpload = document.getElementById("netUpload");
    var mycpu4 = echarts.init(netUpload);

    var diskUsed = document.getElementById("diskUsed");
    var mycpu5 = echarts.init(diskUsed);

    var diskErrorCount = document.getElementById("diskErrorCount");
    var mycpu6= echarts.init(diskErrorCount);

    var memUsed = document.getElementById("memUsed");
    var mycpu7 = echarts.init(memUsed);

    var timeLine=<s:property value="cpustr"/>;
    var cpuUsed=<s:property value="cpuUsed"/>;
    var memSwapdata=<s:property value="memSwap"/>
    var netDownloaddata=<s:property value="netDownload"/>
    var netUploaddata=<s:property value="netUpload"/>
    var diskUseddata=<s:property value="diskUsed"/>
    var diskErrorCountdata=<s:property value="diskErrorCount"/>
    var memUseddata=<s:property value="memUsed"/>;

    // alert(cpuUsed);



    function getUrlParms(type){
        var reg = new RegExp("(^|&)"+ type +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)
            return unescape(r[2]);
        return null;
    }
    var type1 = getUrlParms("type1");
    var src = '';

    switch(type1) {
        case '服务器':
            src = 'images/service_logo.png';
            break;
        case '非受控虚拟机':
            src = 'images/vm_logo.png';
            break;
        case '网络设备':
            src = 'images/monitor_logo.png';
            break;
        case '存储':
            src = 'images/store_logo.png';
            break;
        default:
            src = 'images/other_logo.png';
        }


    var dom = document.getElementById('avator');
        dom.src = src;



    // $("#cpu").html("df0");

    optionCPU = {
        color: '#1b9b0c',
        title:{
            // left:'10%',
            text:'CPU使用率',
        },
        tooltip : {
            trigger: 'axis',
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
    mycpu2.setOption(optionMemU);
    mycpu3.setOption(optionMemS);
    mycpu4.setOption(optionNetDownload);
    mycpu5.setOption(optionNetUpload);
    mycpu6.setOption(optionDiskUsed);
    mycpu7.setOption(optionDiskErrorCount);


</script>
</html>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<html>
<head>
    <s:include value="/monitor/_head.jsp"/>
    <title>首页-智能云监控</title>
    <meta charset="utf-8">
</head>
<body class="front-body">

<div class="front-inner front-inner-media">
    <jsp:include page="/template/_banner.jsp"/>

    <div id="container" class="container">
        <div class="media">
            <div class="media-left text-center">
                <p>
                    <img class="img-circle img-lg-avatar" src="statics/images/server.png">
                </p>
            </div>
            <div class="media-body media-middle">
                <div class="text-center">
                    <table style="margin-left: 50px">
                        <tr>
                            <td>UUID: <s:property value="hostDetail.uuid"/></td>
                            <td>IP地址: <s:property value="hostDetail.ipaddress"/></td>
                        </tr>
                        <tr>
                            <td>OS: <s:property value="hostDetail.os"/></td>
                            <td>CPU核心: <s:property value="hostDetail.cpuCore"/></td>
                        </tr>
                        <tr>
                            <td>CPU频率: <s:property value="hostDetail.cpu"/>MHz</td>
                            <td>内存大小: <s:property value="hostDetail.memory"/>MB</td>
                        </tr>
                        <tr>
                            <td>磁盘容量: <s:property value="hostDetail.disk"/>GB</td>
                            <td>网络带宽: <s:property value="hostDetail.network"/>Mb</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class=" col-md-12 placeholder" style="padding-left: 1px; padding-right: 5px">
            <div class="panel panel-default front-panel" style="margin-bottom:10px;">
                <div class="panel-heading">CPU占用率</div>
                <div id="cpu" class="panel-body" style="height: 400px;"></div>
            </div>
        </div>
        <div class=" col-md-12 placeholder" style="padding-left: 1px; padding-right: 5px">
            <div class="panel panel-default front-panel" style="margin-bottom:10px;">
                <div class="panel-heading">内存交换页</div>
                <div id="mem" class="panel-body" style="height: 400px;"></div>
            </div>
        </div>
        <div class=" col-md-12 placeholder" style="padding-left: 1px; padding-right: 5px">
            <div class="panel panel-default front-panel" style="margin-bottom:10px;">
                <div class="panel-heading">磁盘IO等待</div>
                <div id="io_wait" class="panel-body" style="height: 400px;"></div>
            </div>
        </div>
        <div class=" col-md-12 placeholder" style="padding-left: 1px; padding-right: 5px">
            <div class="panel panel-default front-panel" style="margin-bottom:10px;">
                <div class="panel-heading">网络流量</div>
                <div id="net" class="panel-body" style="height: 400px;"></div>
            </div>
        </div>
        <div class=" col-md-12 placeholder" style="padding-left: 1px; padding-right: 5px">
            <div class="panel panel-default front-panel" style="margin-bottom:10px;">
                <div class="panel-heading">磁盘占用率</div>
                <div id="disk" class="panel-body" style="height: 400px;"></div>
            </div>
        </div>
    </div>

    <jsp:include page="/template/_footer.jsp"/>
</div>
<script src="statics/js/jquery/jquery.min.js"></script>
<script src="statics/echart/echarts.js"></script>
<script src="statics/echart/echarts-liquidfill.js"></script>
<script type="text/javascript">
    var health = document.getElementById('health');

    //基于准备好的dom，初始化echarts实例
    <%--var healthChart = echarts.init(health);--%>

    var timeLine = '<s:property value="timeList"/>';
    timeLine = timeLine.substring(1, timeLine.length-1).split(',');
    <%--var healthList = <s:property value="healthList"/>;--%>

    var colors =[
        '#4f50f1'
    ];
    <%--optionHealth = {--%>
        <%--color: colors,--%>
        <%--tooltip : {--%>
            <%--trigger: 'axis',--%>
            <%--axisPointer : {            // 坐标轴指示器，坐标轴触发有效--%>
                <%--type : 'line'        // 默认为直线，可选为：'line' | 'shadow'--%>
            <%--}--%>
        <%--},--%>
        <%--grid: {--%>
            <%--left: '80px',--%>
            <%--right: '110px',--%>
            <%--bottom: '15%',--%>
            <%--containLabel: true--%>
        <%--},--%>
        <%--legend: {--%>
            <%--data:['Health']--%>
        <%--},--%>
        <%--xAxis : [--%>
            <%--{--%>
                <%--type : 'category',--%>
                <%--data : timeLine,--%>
                <%--axisTick: {--%>
                    <%--alignWithLabel: true--%>
                <%--}--%>
            <%--}--%>
        <%--],--%>
        <%--yAxis : [--%>
            <%--{--%>
                <%--type : 'value'--%>
            <%--}--%>
        <%--],--%>
        <%--series : [--%>
            <%--{--%>
                <%--name:'Health',--%>
                <%--type:'line',--%>
                <%--data: healthList,--%>
                <%--smooth: true--%>
            <%--}--%>
        <%--]--%>
    <%--};--%>
    <%--// 使用刚指定的配置项和数据显示图表。--%>
    <%--healthChart.setOption(optionHealth);--%>
    <%--$(window).resize(function() {--%>
        <%--healthChart.resize();--%>
    <%--});--%>

    var cpu = document.getElementById('cpu');

    //基于准备好的dom，初始化echarts实例
    var cpuChart = echarts.init(cpu);

    var cpuList = <s:property value="cpuList"/>;

    optionCPU = {
        color: colors,
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
        legend: {
            data:['CPU']
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,
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
        series : [
            {
                name:'CPU',
                type:'line',
                data: cpuList,
                smooth: true
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    cpuChart.setOption(optionCPU);
    $(window).resize(function() {
        cpuChart.resize();
    });

    var mem = document.getElementById('mem');

    //基于准备好的dom，初始化echarts实例
    var memChart = echarts.init(mem);

    var memList = <s:property value="memList"/>;

    optionMEM = {
        color: colors,
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
        legend: {
            data:['内存']
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,
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
        series : [
            {
                name:'内存',
                type:'line',
                data: memList,
                smooth: true
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    memChart.setOption(optionMEM);
    $(window).resize(function() {
        memChart.resize();
    });

    var iowait = document.getElementById('io_wait');

    //基于准备好的dom，初始化echarts实例
    var iowaitChart = echarts.init(iowait);

    var iowaitList = <s:property value="iowaitList"/>;

    optionIO = {
        color: colors,
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
        legend: {
            data:['磁盘IO等待时间']
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,
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
        series : [
            {
                name:'磁盘IO等待时间',
                type:'line',
                data: iowaitList,
                smooth: true
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    iowaitChart.setOption(optionIO);
    $(window).resize(function() {
        iowaitChart.resize();
    });


    var net = document.getElementById('net');

    //基于准备好的dom，初始化echarts实例
    var netChart = echarts.init(net);

    var netList = <s:property value="netList"/>;

    optionNET = {
        color: colors,
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
        legend: {
            data:['网络速率']
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,
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
        series : [
            {
                name:'网络速率',
                type:'line',
                data: netList,
                smooth: true
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    netChart.setOption(optionNET);
    $(window).resize(function() {
        netChart.resize();
    });


    var disk = document.getElementById('disk');

    //基于准备好的dom，初始化echarts实例
    var diskChart = echarts.init(disk);

    var diskList = <s:property value="diskList"/>;

    optionDISK = {
        color: colors,
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
        legend: {
            data:['磁盘占用率']
        },
        xAxis : [
            {
                type : 'category',
                data : timeLine,
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
        series : [
            {
                name:'磁盘占用率',
                type:'line',
                data: diskList,
                smooth: true
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    diskChart.setOption(optionDISK);
    $(window).resize(function() {
        diskChart.resize();
    });
</script>
</body>
</html>
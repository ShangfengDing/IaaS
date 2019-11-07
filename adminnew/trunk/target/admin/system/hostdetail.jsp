<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.*, appcloud.admin.common.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>节点详情 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=system"/>
<div class="front-inner">
    <div class="container">
        <input type="hidden" id="hostId" name="hostId" value='${param.hostId}'/>
        <input type="hidden" id="hostMem"/>
        <input type="hidden" id="hostDisk"/>
        <%--<s:include value="/system/_left.jsp?menu=host"/>--%>

        <div class="row" style="margin-top: 2%;margin-left:1px;font-size:15px">
            <div>
                <ol class="breadcrumb">
                    <li>
                        <a href="system/host?zid=1"><label class="control-label front-label">服务器管理</label></a>
                    </li>
                    <li class="active">服务器详情(192.168.1.24)</li>
                </ol>
            </div>
        </div>
        <div class="row" style="margin-top: 2%">
            <label class="col-md-1 control-label front-label">基本信息</label>
        </div>
        <%--以下表格都是静态的，没有后台数据--%>
        <div class="panel panel-default front-panel" style="margin-top:10px">
            <table class="table table-striped multi-table">
                <tr>
                    <th>服务器类型</th>
                </tr>
                <tr>
                    <th>服务器类别</th>
                    <td>结算服务器</td>
                </tr>
                <tr>
                    <th>服务器配置</th>
                    <td>CPU:16个；内存:96730MB；硬盘:2105GB；</td>
                </tr>
                <tr>
                    <th>服务器配置</th>
                    <td>集群1</td>
                </tr>
            </table>
        </div>
        <div class="row" style="margin-top: 2%;margin-bottom: 13px">
            <label class="col-md-1 control-label front-label">实时负载</label>
        </div>
        <div class="panel panel-default front-panel" style="margin-top:10px">
            <table class="table table-striped multi-table">
                <tr>
                    <th class="blueletter  centeralign" width="190px">服务器硬件配置</th>
                    <td id="hostConf2" class="centeralign" style="font-size:12px"></td>
                </tr>
                <tr>
                    <th class="blueletter centeralign" width="190px">CPU使用率(%)</th>
                    <td id="performance1" class="centeralign"></td>
                </tr>
                <tr>
                    <th class="blueletter centeralign" width="190px">内存占用率(%)</th>
                    <td id="performance2" class="centeralign"></td>
                </tr>
                <tr>
                    <th class="blueletter centeralign" width="190px">硬盘占用率(%)</th>
                    <td id="performance3" class="centeralign"></td>
                </tr>
                <tr>
                    <th class="blueletter centeralign">平均负载</th>
                    <td id="performance4" class="centeralign"></td>
                </tr>
                <tr>
                    <th class="blueletter centeralign">磁盘读出(MB/s)</th>
                    <td id="performance5" class="centeralign"></td>
                </tr>
                <tr>
                    <th class="blueletter centeralign">磁盘写入(MB/s)</th>
                    <td id="performance6" class="centeralign"></td>
                </tr>
                <tr>
                    <th class="blueletter centeralign">网络接收(MB/S)</th>
                    <td id="performance7" class="centeralign"></td>
                </tr>
                <tr>
                    <th class="blueletter centeralign">网络发出(MB/S)</th>
                    <td id="performance8" class="centeralign"></td>
                </tr>
            </table>
        </div>
        <div class="row" style="margin-top: 2%;margin-bottom: 13px">
            <label class="col-md-1 control-label front-label">历史负载</label>
        </div>
        <s:include value="/system/hostmonitor.jsp"/>
        <div class="row" style="margin-top: 2%;margin-bottom: 13px">
            <label class="col-md-1 control-label front-label">销售负载</label>
        </div>
        <div class="panel panel-default front-panel" style="margin-top:10px">
            <table class="table table-striped multi-table">
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
                    <td id="confCPU"></td>
                    <td id="confMem"></td>
                    <td id="confDisk"></td>
                </tr>

                <tr>
                    <td>超售比例(%)</td>
                    <td id="cpu_oversell"></td>
                    <td id="memory_oversell"></td>
                    <td id="disk_oversell"></td>
                </tr>
                <tr>
                    <td>可供销售</td>
                    <td id="cpu_all"></td>
                    <td id="memory_all"></td>
                    <td id="disk_all"></td>
                </tr>
                <tr>
                    <td>实际销售</td>
                    <td id="cpu_sell"></td>
                    <td id="memory_sell"></td>
                    <td id="disk_sell"></td>
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
        <div class="row" style="margin-top: 2%;margin-bottom: 13px">
            <label class="col-md-1 control-label front-label">服务列表</label>
        </div>
        <div class="panel panel-default front-panel" style="margin-top:10px">
            <table class="table table-striped multi-table">
                <tr>
                    <td width="320px">名称</td>
                    <td width="230px">状态</td>
                    <td>操作</td>
                </tr>
                <s:iterator id="serviceStatus" value="serviceStatusMap">
                    <s:if test="#serviceStatus.key!='UNKNOWN'">
                        <tr>
                            <td>
                                <s:property value="#serviceStatus.key"/>
                            </td>
                            <td>
                                <s:if test="#serviceStatus.value=='RUNNING'">
                                    <img src="images/inf_normal.png"/>
                                </s:if>
                                <s:if test="#serviceStatus.value=='STOPED'">
                                    <img src="images/inf_error.png"/>
                                </s:if>
                                <s:if test="#serviceStatus.value=='NOT USED'">
                                    <img src="images/inf_alert.png"/>
                                </s:if>
                            </td>
                            <td>
                                <s:if test="#serviceStatus.value=='RUNNING'">
                                    <a href="javascript:void(0)"
                                       onclick="stop('<s:property value="#serviceStatus.key"/>')">停止</a>
                                </s:if>
                                <s:if test="#serviceStatus.value=='STOPED'">
                                    <a href="javascript:void(0)"
                                       onclick="start('<s:property value="#serviceStatus.key"/>')">启动</a>
                                </s:if>
                                <s:if test="#serviceStatus.value=='NOT USED'">
                                    <a href="javascript:void(0)"
                                       onclick="start('<s:property value="#serviceStatus.key"/>')">启动</a>
                                </s:if>
                            </td>
                        </tr>
                    </s:if>
                </s:iterator>
            </table>
        </div>

        <div id="highcharts" class="hidden"></div>
        <div id="query"></div>

    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>

</div>
<%-- <s:include value="/template/_common_js.jsp"></s:include> --%>
<%--<script src="js/system/hostdetail.js"></script>--%>
<script src="<%=Constants.FRONT_URL%>js/plugin/highcharts.js"></script>

<script>

    var hostId = $("#hostId").val();
  //     showLoading();
       showHostDetailInfo(hostId);
       updatePerformance('second', hostId);
//        hideLoading();
        var wait = setInterval(function () {
            if ($("#facebox").css("display") == "none") {
                clearInterval(wait);
            } else {

                updatePerformance('second', hostId);

            }
        }, 3000);


    function showHostDetailInfo(hostId) {
        var url = "system/hostdetail";
        $.post(url, {hostName: hostId}, function (data) {
            //hostDetailIp
            $("#hostDetailIp").html("节点详情 (" + data.hostIp + ")");
            //hostConf
            $("#hostConf1").html("CPU:" + data.hostCPU + "个；" + "内存:" + data.hostMem + "MB；" + "硬盘:" + data.hostDisk + "GB；");
            $("#hostConf2").html("CPU:" + data.hostCPU + "个；" + "内存:" + data.hostMem + "MB；" + "硬盘:" + data.hostDisk + "GB；");
            $("#confCPU").html(data.hostCPU + "个");
            $("#confMem").html(data.hostMem + "MB");
            $("#confDisk").html(data.hostDisk + "GB");
            //基本信息
            $("#hostLocation").html(data.hostLocation);
            $("#hostType").html(data.hostType);
            $("#hostAggregate").html(data.hostAggregate);
            if ($("#hostType").html() == "功能节点") {
                //功能节点不显示销售负载
                $("#sell_title").addClass("hidden");
                $("#sell_table").addClass("hidden");
            }
            //显示给hostMonitor用
            $("#hostMem").val(data.hostMem);
            $("#hostDisk").val(data.hostDisk);
            //销售负载

            $("#cpu_oversell").html(data.cpu_oversell + "%");
            $("#memory_oversell").html(data.memory_oversell + "%");
            $("#disk_oversell").html(data.disk_oversell + "%");
            $("#cpu_all").html(data.cpu_oversell * data.hostCPU / 100 + "个");
            $("#memory_all").html(data.memory_oversell * (data.hostMem - 4 * 1024) / 100 + "MB");
            $("#disk_all").html(data.disk_oversell * data.hostDisk / 100 + "GB");
            $("#cpu_sell").html(data.cpu_sell + "个");
            $("#memory_sell").html(data.memory_sell + "MB");
            $("#disk_sell").html(data.disk_sell + "GB");
            //
            //$("#cpu_sell").html((parseInt(data.cpu_oversell) - parseInt(data.cpusell)");
            $("#cpu_remain").html(parseInt($("#cpu_all").html()) - parseInt(data.cpu_sell) + "个");
            $("#memory_remain").html(parseInt($("#memory_all").html()) - parseInt(data.memory_sell) + "MB");
            $("#disk_remain").html(parseInt($("#disk_all").html()) - parseInt(data.disk_sell) + "GB");

            $("#cpu_rate").html((parseInt($("#cpu_sell").html()) * 100 / parseInt($("#confCPU").html())).toFixed(2) + "%");
            $("#memory_rate").html((parseInt($("#memory_sell").html()) * 100 / parseInt($("#confMem").html())).toFixed(2) + "%");
            $("#disk_rate").html((parseInt($("#disk_sell").html()) * 100 / parseInt($("#confDisk").html())).toFixed(2) + "%");
        });
    }

    function updatePerformance(type, hostName) {
        var url = "system/hostmonitor";
        $.post(url, {type: type, hostName: hostName}, function (data) {
            //console.log(data.cpuPercent);
            var hostMem = $("#hostMem").val();
            var hostDisk = $("#hostDisk").val();

            var mem = accMul(hostMem, data.memPercent[0]);
            var disk = accMul(hostDisk, data.diskPercent[0]);
            mem = accMul(mem, 0.01);
            disk = accMul(disk, 0.01);


            $("#performance1").html(data.cpuPercent[0].toFixed(2) + "%");
            $("#performance2").html(data.memPercent[0].toFixed(2) +
                "% (" + mem.toFixed(0) + "MB/" + hostMem + "MB)");
            if(isNaN(data.diskPercent[0])){
                $("#performance3").html("0%");
            }else {
                $("#performance3").html(data.diskPercent[0].toFixed(2) +
                    "% (" + disk.toFixed(2) + "GB/" + hostDisk + "GB)");
            }
            $("#performance4").html(data.loadaverage[0].toFixed(2));
            $("#performance5").html(data.diskReadRate[0].toFixed(2));
            $("#performance6").html(data.diskWriteRate[0].toFixed(2));
            $("#performance7").html(data.netInPercent[0].toFixed(2));
            $("#performance8").html(data.netOutPercent[0].toFixed(2));
            console.log()
        });
    }

    /**
     * 以下是本文件中用到的内部函数
     */
//浮点数相乘精度问题
    function accMul(arg1, arg2) {
        var m = 0, s1 = String(arg1), s2 = String(arg2);
        try {
            m += s1.split(".")[1].length
        } catch (e) {
        }
        ;
        try {
            m += s2.split(".")[1].length
        } catch (e) {
        }
        ;
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    }

    function start(service) {
        if (window.confirm('确定启动' + service + "?")) {
            //alert(service);
            url = 'system/startservice';
            //$.post(url,{hostId:$('#hostId').val(),serviceStr:service},function(){
            //hideLoading();
            //facebox_close();
            //submitAll(1);
            //});
        }
    }

    function stop(service) {
        if (window.confirm('确定停止' + service + "?")) {
            //alert(service);
            url = 'system/stopservice';
            //$.post(url,{hostId:$('#hostId').val(),serviceStr:service},function(){
            //hideLoading();
            //facebox_close();
            //submitAll(1);
            //});
        }
    }
</script>
</body>
</html>
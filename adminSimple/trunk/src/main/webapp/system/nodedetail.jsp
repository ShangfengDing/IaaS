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
        <input type="hidden" id="hostId" name="hostId" value='${param.uuid}'/>
        <input type="hidden" id="buttonIndex" value="${param.button}"/>
        <input type="hidden" id="historyType" value="${param.type}"/>
        <input type="hidden" id="hostMem"/>
        <input type="hidden" id="hostDisk"/>
        <div class="col-md-12 form-group">
            <div>
                <ol class="breadcrumb">
                    <li>系统管理</li>
                    <li><a href="system/hostmanage?page=1">基础设施</a></li>
                    <li class="active">
                        <s:if test="host!=null">
                            <s:property value='host.name'/>
                        </s:if>
                        <s:else>
                            <s:property value='name'/>
                        </s:else>
                    </li>
                </ol>
            </div>
            <div class="inner">
                <div style="height: 150px;opacity:1;">
                    <div class="panel-body">
                        <div>
                            <div class="nav-brand">
                                <s:if test='host.type.toString()=="计算节点"'>
                                    <img id="avator" alt="icon" src="images/service_logo.png" class="img-responsive"/>
                                </s:if>
                                <s:else>
                                    <img id="avator" alt="icon" src="images/vm_logo.png" class="img-responsive"/>
                                </s:else>
                            </div>
                        </div>
                        <div>
                            <div class="nav-brand">
                                <h4 class="midia-heading">
                                    <s:if test="host!=null">
                                        <span id="myGroupName"><s:property value='host.name'/></span>&nbsp;&nbsp;
                                    </s:if>
                                    <s:else>
                                        <span id="myGroupName"><s:property value='name'/></span>&nbsp;&nbsp;
                                    </s:else>
                                    <s:if test='host.type.toString()=="计算节点"'>
                                        <span id="permission" class="badge">服务器</span>
                                    </s:if>
                                    <s:elseif test='host.type.toString()=="存储结点"'>
                                        <span id="permission" class="badge">存储结点</span>
                                    </s:elseif>
                                    <s:else>
                                        <span id="permission" class="badge">非受控虚拟机</span>
                                    </s:else>
                                </h4>
                                <div id="groupdesc" style="word-wrap:break-word;word-break:break-all;">
                                    UUID:&nbsp;&nbsp;<s:property value='uuid'/><br>
                                    <s:if test="host!=null">
                                        配置:&nbsp;&nbsp;<s:property value='host.cpuCore'/>CPU
                                             &nbsp;&nbsp;<s:property value="host.memoryMb"/>MB
                                             &nbsp;&nbsp;<s:property value="host.diskMb"/>MB<br>
                                        备注:&nbsp;&nbsp;<s:property value='host.extend'/><br>
                                    </s:if>
                                    <s:else>
                                        配置:&nbsp;&nbsp;<s:property value='model'/><br>
                                        备注:&nbsp;&nbsp;<s:property value='description'/><br>
                                    </s:else>
                                </div>

                            </div>
                        </div>
                        <div id="top1" style="height:150px"></div>
                    </div>
                </div>
                <%--<div class="front-toolbar other">
                    <div id="freeshare" class="front-btn-group collapse" data-toggle="buttons">
                        <a class="btn btn-default front-no-box-shadow" onclick="showHostDetailInfo()" id="allocation"><input type="radio" name="options" autocomplete="off">配置</a>
                        <a class="btn btn-default front-no-box-shadow" onclick="showService()" id="service"><input type="radio" name="options" autocomplete="off">服务</a>
                        <a class="btn btn-default front-no-box-shadow" onclick="showLoadHistory()" id="loadHistory"><input type="radio" name="options" autocomplete="off">性能</a>
                    </div>
                </div>--%>
                <%--<div id="allocationTable" class="panel panel-default front-panel" style="margin-top:10px;" >--%>
                    <%--<table class="table table-striped multi-table" style="margin-bottom: 0px;">--%>
                        <%--<thread>--%>
                            <%--<tr>--%>
                                <%--<th></th>--%>
                                <%--<th>CPU</th>--%>
                                <%--<th>内存</th>--%>
                                <%--<th>硬盘</th>--%>
                            <%--</tr>--%>
                        <%--</thread>--%>
                        <%--<tr>--%>
                            <%--<td>硬件配置</td>--%>
                            <%--<td id="confCPU"></td>--%>
                            <%--<td id="confMem"></td>--%>
                            <%--<td id="confDisk"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td>超售比例(%)</td>--%>
                            <%--<td id="cpu_oversell"></td>--%>
                            <%--<td id="memory_oversell"></td>--%>
                            <%--<td id="disk_oversell"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td>可供销售</td>--%>
                            <%--<td id="cpu_all"></td>--%>
                            <%--<td id="memory_all"></td>--%>
                            <%--<td id="disk_all"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td>实际销售</td>--%>
                            <%--<td id="cpu_sell"></td>--%>
                            <%--<td id="memory_sell"></td>--%>
                            <%--<td id="disk_sell"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td>剩余资源</td>--%>
                            <%--<td id="cpu_remain"></td>--%>
                            <%--<td id="memory_remain"></td>--%>
                            <%--<td id="disk_remain"></td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td>销售比例(%)</td>--%>
                            <%--<td id="cpu_rate"></td>--%>
                            <%--<td id="memory_rate"></td>--%>
                            <%--<td id="disk_rate"></td>--%>
                        <%--</tr>--%>
                    <%--</table>--%>
                <%--</div>--%>
                <%--<div id="serviceTable" class="panel panel-default front-panel" style="margin-top:10px">--%>
                    <%--<table class="table table-striped multi-table" style="margin-bottom: 0px">--%>
                        <%--<tr>--%>
                            <%--<th width="320px">名称</th>--%>
                            <%--<th width="230px">状态</th>--%>
                            <%--<th>操作</th>--%>
                        <%--</tr>--%>
                        <%--<s:iterator id="serviceStatus" value="serviceStatusMap">--%>
                            <%--<s:if test="#serviceStatus.key!='UNKNOWN'">--%>
                                <%--<tr>--%>
                                    <%--<td>--%>
                                        <%--<s:property value="#serviceStatus.key"/>--%>
                                    <%--</td>--%>
                                    <%--<td>--%>
                                        <%--<s:if test="#serviceStatus.value=='RUNNING'">--%>
                                            <%--<img src="images/inf_normal.png"/>--%>
                                        <%--</s:if>--%>
                                        <%--<s:if test="#serviceStatus.value=='STOPED'">--%>
                                            <%--<img src="images/inf_error.png"/>--%>
                                        <%--</s:if>--%>
                                        <%--<s:if test="#serviceStatus.value=='NOT USED'">--%>
                                            <%--<img src="images/inf_alert.png"/>--%>
                                        <%--</s:if>--%>
                                    <%--</td>--%>
                                    <%--<td>--%>
                                        <%--<s:if test="#serviceStatus.value=='RUNNING'">--%>
                                            <%--<a href="javascript:void(0)"--%>
                                               <%--onclick="stop('<s:property value="#serviceStatus.key"/>')">停止</a>--%>
                                        <%--</s:if>--%>
                                        <%--<s:if test="#serviceStatus.value=='STOPED'">--%>
                                            <%--<a href="javascript:void(0)"--%>
                                               <%--onclick="start('<s:property value="#serviceStatus.key"/>')">启动</a>--%>
                                        <%--</s:if>--%>
                                        <%--<s:if test="#serviceStatus.value=='NOT USED'">--%>
                                            <%--<a href="javascript:void(0)"--%>
                                               <%--onclick="start('<s:property value="#serviceStatus.key"/>')">启动</a>--%>
                                        <%--</s:if>--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                            <%--</s:if>--%>
                        <%--</s:iterator>--%>
                    <%--</table>--%>
                <%--</div>--%>
                <%--<s:include value="_nodedetailA.jsp"/>--%>
                <div id="varyTable"></div>
            </div>
        </div>
        <div id="highcharts" class="hidden"></div>
        <div id="query"></div>
        <div id="loading" class="hidden">
            <div class="front-loading">
                <div class="front-loading-block"></div>
                <div class="front-loading-block"></div>
                <div class="front-loading-block"></div>
            </div>
            <div class="panel-body text-center">正在加载请稍候</div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"/>
</div>
</body>
<%--<script src="statics/js/jquery/jquery.min.js"></script>--%>
<script src="statics/echart/echarts.js"></script>
<script src="statics/echart/echarts-liquidfill.js"></script>
<script src="<%=Constants.FRONT_URL%>js/plugin/highcharts.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        //do something
        showLoadHistory();
    })
    //基本信息
//    function getUrlParms(type){
//        var reg = new RegExp("(^|&)"+ type +"=([^&]*)(&|$)");
//        var r = window.location.search.substr(1).match(reg);
//        if(r!=null)
//            return unescape(r[2]);
//        return null;
//    }
//    var type1 = getUrlParms("type1");
//    var src = '';
//
//    switch(type1) {
//        case '服务器':
//            src = 'images/service_logo.png';
//            break;
//        case '非受控虚拟机':
//            src = 'images/vm_logo.png';
//            break;
//        case '网络设备':
//            src = 'images/monitor_logo.png';
//            break;
//        case '存储':
//            src = 'images/store_logo.png';
//            break;
//        default:
//            src = 'images/other_logo.png';
//    }
//    var dom = document.getElementById('avator');
//    dom.src = src;


    //按钮组
//    function showAllocation() {
//
//        $('#allocationTable').removeClass('hide');
//        $('#serviceTable').addClass('hide');
//        $('#htloadTable').addClass('hide');
//
//    }
//    function showService() {
//
//        $('#allocationTable').addClass("hide");
//        $('#serviceTable').removeClass('hide');
//        $('#htloadTable').addClass('hide');
//    }
//    function showHTload() {
//
//        $('#allocationTable').addClass("hide");
//        $('#serviceTable').addClass('hide');
//        $('#htloadTable').removeClass('hide');
//    }

    var hostId = $("#hostId").val();
    var button=$('#buttonIndex').val();
    var type=$('#historyType').val();
    if(button==2){
        $('#service').click();
    }else if(button==3){
        $('#loadHistory').click();
    }else{
       $('#allocation').click();
    }

    function showHostDetailInfo() {
        var loading = $('#loading').clone();
        loading.removeClass('hidden');
        $('#varyTable').html(loading);

        var url = "system/hostdetail";
        $.post(url, {hostName: hostId}, function (data) {
            $('#varyTable').html(data);
//            //hostDetailIp
////            $("#hostDetailIp").html("节点详情 (" + data.hostIp + ")");
//            //hostConf
////            $("#hostConf1").html("CPU:" + data.hostCPU + "个；" + "内存:" + data.hostMem + "MB；" + "硬盘:" + data.hostDisk + "GB；");
////            $("#hostConf2").html("CPU:" + data.hostCPU + "个；" + "内存:" + data.hostMem + "MB；" + "硬盘:" + data.hostDisk + "GB；");
//            $("#confCPU").html(data.hostCPU + "个");
//            $("#confMem").html(data.hostMem + "MB");
//            $("#confDisk").html(data.hostDisk + "GB");
//            //基本信息
//            $("#hostLocation").html(data.hostLocation);
//            $("#hostType").html(data.hostType);
//            $("#hostAggregate").html(data.hostAggregate);
//            if ($("#hostType").html() == "功能节点") {
//                //功能节点不显示销售负载
//                $("#sell_title").addClass("hidden");
//                $("#sell_table").addClass("hidden");
//            }
//            //显示给hostMonitor用
//            $("#hostMem").val(data.hostMem);
//            $("#hostDisk").val(data.hostDisk);
//            //销售负载
//
//            $("#cpu_oversell").html(data.cpu_oversell + "%");
//            $("#memory_oversell").html(data.memory_oversell + "%");
//            $("#disk_oversell").html(data.disk_oversell + "%");
//            $("#cpu_all").html(data.cpu_oversell * data.hostCPU / 100 + "个");
//            $("#memory_all").html(data.memory_oversell * (data.hostMem - 4 * 1024) / 100 + "MB");
//            $("#disk_all").html(data.disk_oversell * data.hostDisk / 100 + "GB");
//            $("#cpu_sell").html(data.cpu_sell + "个");
//            $("#memory_sell").html(data.memory_sell + "MB");
//            $("#disk_sell").html(data.disk_sell + "GB");
//            //
//            //$("#cpu_sell").html((parseInt(data.cpu_oversell) - parseInt(data.cpusell)");
//            $("#cpu_remain").html(parseInt($("#cpu_all").html()) - parseInt(data.cpu_sell) + "个");
//            $("#memory_remain").html(parseInt($("#memory_all").html()) - parseInt(data.memory_sell) + "MB");
//            $("#disk_remain").html(parseInt($("#disk_all").html()) - parseInt(data.disk_sell) + "GB");
//
//            $("#cpu_rate").html((parseInt($("#cpu_sell").html()) * 100 / parseInt($("#confCPU").html())).toFixed(2) + "%");
//            $("#memory_rate").html((parseInt($("#memory_sell").html()) * 100 / parseInt($("#confMem").html())).toFixed(2) + "%");
//            $("#disk_rate").html((parseInt($("#disk_sell").html()) * 100 / parseInt($("#confDisk").html())).toFixed(2) + "%");
        });
    }

    function showService(){
        var loading = $('#loading').clone();
        loading.removeClass('hidden');
        $('#varyTable').html(loading);
        var url = "system/findServiceStatus";
        $.post(url, {hostName: hostId}, function (data) {
            $('#varyTable').html(data);
        });
    }

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
        $.tipModal('confirm', 'info', '确定启动'+service+'?', function (result) {
          if(result==true){
              alert(service);
              url = 'system/startservice';
              $.post(url,{hostId:$('#hostId').val(),serviceStr:service},function(){
                  showService();
              });
          }
        });
//        if (window.confirm('确定启动' + service + "?")) {
//
//
//            //$.post(url,{hostId:$('#hostId').val(),serviceStr:service},function(){
//            //hideLoading();
//            //facebox_close();
//            //submitAll(1);
//            //});
//        }
    }

    function stop(service) {
        $.tipModal('confirm', 'info', '确定停止'+service+'?', function (result) {
            if(result==true){
                alert(service);
                url = 'system/stopservice';
                $.post(url,{hostId:$('#hostId').val(),serviceStr:service},function(){
                    showService();
                });

            }
        });
//        if (window.confirm('确定停止' + service + "?")) {
//            //alert(service);
//            url = 'system/stopservice';
//            //$.post(url,{hostId:$('#hostId').val(),serviceStr:service},function(){
//            //hideLoading();
//            //facebox_close();
//            //submitAll(1);
//            //});
//        }
    }

    function showLoadHistory(){
        var loading = $('#loading').clone();
        loading.removeClass('hidden');
        $('#varyTable').html(loading);
        $.get('system/findLoadHistory',{
            uuid:hostId
        },function(data){
            $('#varyTable').html(data);
            if(type!=null||type!=""){
//                document.querySelector("#"+type).scrollIntoView();
//                $("html,body").animate({scrollTop: $("#"+type).offset().center}, 500);
            }
        });
    }

</script>
</html>
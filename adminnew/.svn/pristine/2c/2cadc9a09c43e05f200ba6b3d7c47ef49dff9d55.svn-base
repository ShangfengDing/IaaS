<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.*, appcloud.admin.common.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>云主机管理 - 云海IaaS</title>
    <link rel="stylesheet" href="statics/plugin/bootstrap-datetimepicker/ver2/bootstrap-datetimepicker.min.css"/>
    <%--<link rel="stylesheet" href="http://newfront.free4lab.com/bootstrap/css/bootstrap.min.css">--%>
    <%--<link rel="stylesheet" href="http://newfront.free4lab.com/css/front.css">--%>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=cloud"/>
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div class="inner">
                <div>
                    <ol class="breadcrumb">
                        <li>资源管理</li>
                        <li class="active">云主机管理</li>
                    </ol>
                </div>
                <div class="panel panel-default front-panel">
                    <%--<s:include value="/system/_left.jsp?menu=vm" />--%>
                    <%--<div class="panel-heading">--%>
                        <%--<strong>云主机搜索</strong>--%>
                    <%--</div>--%>
                    <div class="panel-body" style="margin-top:3px;padding-bottom:0px">
                        <div id="yunhai-content" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">用户邮箱</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" name="email" id="email"/>
                                    <!-- <input type="text" class="datepicker" name="time" id="time" /> -->
                                </div>
                                <label class="col-md-1 control-label front-label">名称</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" name="name" id="name"/>
                                    <!-- <input type="text" class="datepicker" name="time" id="time" /> -->
                                </div>
                            </div>
                            <span id="error1" class="redletter leftmargin_10"></span>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">数据中心</label>
                                <div class="col-md-3">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="zone"
                                            onchange="checkZone()">
                                        <option value="">--全部--</option>
                                        <s:iterator id="zone" value="zones">
                                            <s:if test="#zone.name=='lab'">
                                            </s:if>
                                            <s:else>
                                            <option value="<s:property value="#zone.id"/>">
                                                    <s:property value="#zone.name"/>
                                            </option>
                                            </s:else>
                                        </s:iterator>
                                    </select>
                                </div>
                                <label class="col-md-1 control-label front-label">集群</label>
                                <div class="col-md-3">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="cluster"
                                            onchange="checkCluster()">
                                        <option value="">--全部--</option>
                                        <s:iterator id="aggregate" value="aggregates">
                                            <option value="<s:property value="#aggregate.id"/>"><s:property
                                                    value="#aggregate.name"/></option>
                                        </s:iterator>
                                    </select>
                                </div>
                                <label class="col-md-1 control-label front-label">服务器</label>
                                <div class="col-md-3">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="host">
                                        <option value="">--全部--</option>
                                        <s:iterator id="host" value="hosts">
                                            <option value="<s:property value="#host.id"/>">
                                                <s:iterator id="list"
                                                            value="hostList">
                                                    <s:if
                                                        test="#list.ip.equals(#host.ip)">
                                                        <s:property value="#list.name"/>
                                                    </s:if>
                                                </s:iterator>(<s:property value="#host.ip"/>)</option>
                                        </s:iterator>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">私网IP</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control front-no-box-shadow" name="ip" id="ip"
                                           onblur="checkIP()"/>
                                </div>
                                <label class="col-md-1 control-label front-label">UUID</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control front-no-box-shadow" name="uuid" id="uuid"
                                           onblur="checkUUID()"/>
                                </div>
                                <label class="col-md-1 control-label front-label">状态</label>
                                <div class="col-md-3" >
                                    <select class="form-control front-no-radius front-no-box-shadow" id="status">
                                        <option value="">--全部--</option>
                                        <option value="active">运行中</option>
                                        <option value="stopped">已关机</option>
                                        <option value="building">创建中</option>
                                        <option value="deleted">已删除</option>
                                        <option value="error">故障</option>
                                        <option value="rebuilding">ISO装机中</option>
                                        <option value="suspended">已挂起</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">创建时间</label>
                                <%--<div class="col-md-3">--%>
                                    <%--<input type="text" class="form-control front-no-box-shadow datepicker" name="time"--%>
                                           <%--id="time"/>--%>
                                <%--</div>--%>
                                <div class="col-md-5">
                                    <input type="text" class="form-control" id="begin-date" placeholder="起始时间">
                                </div>
                                <div class="col-md-1"></div>
                                <div class="col-md-5">
                                    <input type="text" class="form-control" id="end-date" placeholder="终止时间">
                                </div>
                            </div>
                            <span id="error2" class="redletter leftmargin_10"></span>
                            <%--<div class="form-group">--%>
                                <%--<label class="col-md-1 control-label front-label">可用区</label>--%>
                                <%--<div class="col-md-3">--%>
                                    <%--<select class="form-control front-no-radius front-no-box-shadow" id="status">--%>
                                        <%--<option value="">--全部--</option>--%>
                                        <%--<option value="active">运行中</option>--%>
                                        <%--<option value="stopped">已关机</option>--%>
                                        <%--<option value="building">创建中</option>--%>
                                        <%--<option value="deleted">已删除</option>--%>
                                        <%--<option value="error">故障</option>--%>
                                        <%--<option value="rebuilding">ISO装机中</option>--%>
                                        <%--<option value="suspended">已挂起</option>--%>
                                    <%--</select>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <div class="form-group">
                                <div class="col-lg-offset-1 col-lg-11 text-right" style="padding-top:5px;">
                                    <a href="javascript:void(0)" onclick="cancelSearch()" class="btn btn-default">取消</a>
                                    <button type="button" class="btn btn-primary" onclick="submitAll(1)">确定</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="dottedline topmargin_20"></div>
                    <input type="hidden" id="oldclusterId" value="<s:property value="clusterId"/>"/>
                    <input type="hidden" id="oldzoneId" value="<s:property value="zoneId"/>"/>
                    <input type="hidden" id="oldhostId" value="<s:property value="hostId"/>"/>
                </div>


                <div id="query">

                </div>
                <div id="loading" class="hidden">
                    <div class="front-loading">
                        <div class="front-loading-block"></div>
                        <div class="front-loading-block"></div>
                        <div class="front-loading-block"></div>
                    </div>
                    <div class="panel-body text-center">正在加载请稍候</div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
    <%-- <s:include value="/template/_common_js.jsp"></s:include> --%>
</div>

<script>
    $(document).ready(function () {
        $(".datepicker").each(function (i) {
            $(this).daterangepicker({
                arrows: true,
                dateFormat: 'yy-mm-dd'
            });
        });

        var oldhostId = $("#oldhostId").val();
        if (oldhostId != "") {
            $("#host").val(oldhostId);
        }
        var oldzoneId = $("#oldzoneId").val();
        if (oldzoneId > 0) {
            $("#zone").val(oldzoneId);
        }
        var oldclusterId = $("#oldclusterId").val();
        if (oldclusterId > 0) {
            $("#cluster").val(oldclusterId);
        }

        if (oldhostId != "" && oldzoneId > 0 && oldclusterId) {
            submitAll(1);
        }

        $("#email").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll(1);//回车键，用.ajax提交表单
            }
        });
        $("#name").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll(1);//回车键，用.ajax提交表单
            }
        });
        $("#ip").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll(1);//回车键，用.ajax提交表单
            }
        });
        $("#uuid").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll(1);//回车键，用.ajax提交表单
            }
        });
        $("#time").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll(1);//回车键，用.ajax提交表单
            }
        });

        //当从用户管理界面根据虚拟机数跳至本页面时使用
        var query = window.location.href;
        query = query.substring(query.indexOf("?") + 1);
        var pairs = query.split("&");
        for (var i = 0; i < pairs.length; i++) {
            var paramName = pairs[i].substring(0, pairs[i].indexOf("="));
            var paramValue = pairs[i].substring(pairs[i].indexOf("=") + 1);
            if (paramName == "email") {
                $("#email").val(paramValue);
                searchVm(1, paramValue, "", "", "", "", "", "", "", "", "");
            }
            if (paramName == "cluster") {
                $("#cluster").val(paramValue);
                $("#zone").val(1);
                searchVm(1, "", "", "", 1, paramValue, "", "", "", "", "");
            }
        }


    });

    function cancelSearch() {
        $("#query").html("");
        $("#email").val("");
        $("#name").val("");
        $("#status").val("");
        $("#zone").val("");
        $("#cluster").val("");
        $("#host").val("");
        $("#ip").val("");
        $("#time").val("");
        $("#error1").html("");
        $("#error2").html("");
        $("#uuid").val("");
        $("#begin-date").val("");
        $("#end-date").val("");
    }

    function checkZone() {
        var zoneId = $("#zone").val();
        $.ajax({
            type: "post",
            url: "system/getclusterandhostbyzid",
            data: {
                zoneId: zoneId
            },
            success: function (data) {
                $("#host").empty();
                $("#cluster").empty();
                var clusterList = data.aggregates;
                var hostList = data.hosts;
                if (clusterList != null && clusterList != "" && clusterList.length > 0) {
                    $("#cluster").removeAttr("disabled");
                    $("#cluster").append("<option value=\"\">--全部--</option>");
                    for (var i = 0; i < clusterList.length; i++) {
                        $("#cluster").append("<option value='" + clusterList[i].id + "'>" + clusterList[i].name + "</option>");
                    }
                } else {
                    $("#cluster").attr("disabled", "disabled");
                }
                if (hostList != null && hostList != "" && hostList.length > 0) {
                    $("#host").removeAttr("disabled");
                    $("#host").append("<option value=\"\">--全部--</option>");
                    for (var i = 0; i < hostList.length; i++) {
                        $("#host").append("<option value='" + hostList[i].id + "'>" + hostList[i].ip + "</option>");
                    }
                } else {
                    $("#host").attr("disabled", "disabled");
                }
            }
        });
    }

    function checkCluster() {
        var clusterId = $("#cluster").val();
        $.ajax({
            type: "post",
            url: "system/gethostbycid",
            data: {
                clusterId: clusterId
            },
            success: function (data) {
                $("#host").empty();
                var hostList = data.hosts;
                if (hostList != null && hostList != "" && hostList.length > 0) {
                    $("#host").removeAttr("disabled");
                    $("#host").append("<option value=\"\">--全部--</option>");
                    for (var i = 0; i < hostList.length; i++) {
                        $("#host").append("<option value='" + hostList[i].id + "'>" + hostList[i].ip + "</option>");
                    }
                } else {
                    $("#host").attr("disabled", "disabled");
                }
            }
        });
    }

    function submitAll(page) {
        var email = $("#email").val();
        var name = $("#name").val();
        var status = $("#status").val();
        var zone = $("#zone").val();
        var cluster = $("#cluster").val();
        var host = $("#host").val();
        var ip = $("#ip").val();
        var time = $("#time").val();
//        var starttime = "";
        var starttime = $("#begin-date").val();
//        var endtime = "";
        var endtime = $("#end-date").val();
        var uuid = $("#uuid").val();

        if (email == "" && name == "" && status == "" && zone == "" && cluster == ""
            && host == "" && ip == "" && uuid == "" && starttime == "" && endtime == "") {
            if (page == 0 || page == '0') {
                if (!confirm("导出全部主机？")) {
                    return;
                }
            } else {
                if (!confirm("查询全部主机？")) {
                    return;
                }
            }
        } else {
// 		if(email != "" && !isEmail(email)){
// 	        $("#error1").html("用户邮箱输入的格式不正确");
// 	        return;
// 	    }else{
            $("#error1").html("");
// 	    }

            if (ip != "" && !isIP(ip)) {
                $("#error2").html("IP地址输入的格式不正确");
                return;
            } else {
                $("#error2").html("");
            }

            if (uuid != "" && !isString(uuid)) {
                $("#error2").html("主机标识输入的格式不正确");
            } else {
                $("#error2").html("");
            }

//            if (time != "") {
//                var date = new Array();
//                date = time.split(" - ");
//                if (date.length == 1) {
//                    starttime = date[0] + " 00:00:00";
//                    endtime = date[0] + " 23:59:59";
//                } else if (date.length == 2) {
//                    if (date[0] > date[1]) {
//                        alert("输入的日期有误！");
//                        return;
//                    }
//                    starttime = date[0] + " 00:00:00";
//                    endtime = date[1] + " 23:59:59";
//                } else {
//                    alert("输入的日期有误！");
//                    return;
//                }
//            }

        }

        if (page == 0 || page == '0') {//以提交表单的形式导出云主机excel表
            var temp = document.createElement("form");
            temp.action = "vm/vmexcel";
            temp.method = "post";
            temp.style.display = "none";
            var PARAMS = {
                page: '1', email: email, name: name, status: status, zoneId: zone, clusterId: cluster,
                hostId: host, ip: ip, starttime: starttime, endtime: endtime, uuid: uuid
            };
            for (var par in PARAMS) {
                var opt = document.createElement("textarea");
                opt.name = par;
                opt.value = PARAMS[par];
                temp.appendChild(opt);
            }
            document.body.appendChild(temp);
            temp.submit();
            return temp;
        } else {
            //查询云主机
            $("#query").html("");
            var loading = $('#loading').clone();
            loading.removeClass('hidden');
            $('#query').append(loading);
            searchVm(page, email, name, status, zone, cluster, host, ip, starttime, endtime, uuid);
        }
    }

    //
    var e;
    var n;
    var s;
    var z;
    var c;
    var h;
    var i;
    var st;
    var et;
    var uid;

    function searchVm(page, email, name, status, zone, cluster, host, ip, starttime, endtime, uuid) {
        e = email;
        n = name;
        s = status;
        z = zone;
        c = cluster;
        h = host;
        i = ip;
        st = starttime;
        et = endtime;
        uid = uuid;
        getPage(page);

        function getPage(page) {
            $.post('vm/searchvm', {
                email: e,
                name: n,
                status: s,
                zoneId: z,
                clusterId: c,
                hostId: h,
                ip: i,
                starttime: st,
                endtime: et,
                uuid: uid,
                page: page
            }, function (data) {
                $('#query').html(data);
                console.log(data);
                $('#pageColumn').html($.getDivPageHtml(page, $('#pageColumn').data('endpage'), '(' + getPage + ')'));
            });
        }
    }

    function checkEmail() {
        var email = $("#email").val();
        if (email != "" && !isEmail(email)) {
            $("#error1").html("用户邮箱输入的格式不正确");
        } else {
            $("#error1").html("");
        }
    }

    function checkIP() {
        var ip = $("#ip").val();
        if (ip != "" && !isIP(ip)) {
            $("#error2").html("IP地址输入的格式不正确");
        } else {
            $("#error2").html("");
        }
    }

    function checkUUID() {
        var uuid = $("#uuid").val();
        if (uuid != "" && !isString(uuid)) {
            $("#error2").html("主机标识输入的格式不正确");
        } else {
            $("#error2").html("");
        }
    }

    function isIP(ipAddr) {
        var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
        if (ipAddr.match(reg)) {
            return true;
        } else {
            return false;
        }
    }

    function isString(str) {
        var reg = /^[A-Za-z0-9]+$/;
        if (str.match(reg)) {
            return true;
        } else {
            return false;
        }
    }

    //判断是否是email
    function isEmail(str) {
        var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        return reg.test(str);
    }


    //显示云主机详情
    function showDetail(obj, userId, vmId) {
        $.post("vm/vmbrief", {
                'userId': userId,
                'serverId': vmId
            },
            function (data) {
                console.log("userId=" + userId + ";serverid=" + vmId);
                console.log(data);
                $(obj).html("收起");
                $(obj).attr("onclick", "hideDetail(this,'" + userId + "','" + vmId + "')");

                var detailTable = "<tr class='yellowbox padding10'>" +
                    "<td colspan='9'><table class='formtable'>";

                var flavor =//硬件配置
                    data.flavor.vcpus + "个CPU," +
                    data.flavor.ram + "G内存," +
                    data.flavor.disk + "G硬盘," +
                    data.server.metadata.maxBandwidth + "M带宽";
                var publicIps = "";//公网ip
                for (var i = 0; i < data.publicIps.length; ++i) {
                    publicIps = publicIps + data.publicIps[i].addr + ',';
                }
                var privateIps = "";//私网ip
                for (var i = 0; i < data.privateIps.length; ++i) {
                    privateIps = privateIps + data.privateIps[i].addr + ',';
                }
                var securityGroup = data.server.securityGroup.name;//防火墙规则
                var createdTime = new Date(parseInt(data.server.created)).toLocaleString();//创建时间
                if (data.vmEndtime == null) {
                    var stopTime = "无";
                    var payType = "无";
                } else {
                    var stopTime = new Date(parseInt(data.vmEndtime.endTime)).toLocaleString();//停止时间
                    var payType = data.vmEndtime.payType;//计费方式
                }

                detailTable +=
                    '<tr>' +
                    '<td class="padding5 formlabel">硬件配置：</td><td class="padding5 formcontent">' + flavor + '</td>' +
                    '</tr><tr>' +
                    '<td class="padding5 formlabel">公网IP：</td><td class="padding5 formcontent">' + publicIps + '</td>' +
                    '</tr><tr>' +
                    '<td class="padding5 formlabel">内网IP：</td><td class="padding5 formcontent"> ' + privateIps + '</td>' +
                    '</tr><tr>' +
                    '<td class="padding5 formlabel">防火墙规则：</td><td class="padding5 formcontent">' + securityGroup + '</td>' +
                    '</tr><tr>' +
                    '<td class="padding5 formlabel">创建时间：</td><td class="padding5 formcontent">' + createdTime + '</td>' +
                    '</tr><tr>' +
                    '<td class="padding5 formlabel">停止时间：</td><td class="padding5 formcontent">' + stopTime + '</td>' +
                    '</tr><tr>' +
                    '<td class="padding5 formlabel">计费方式：</td><td class="padding5 formcontent">' + payType + '</td>' +
                    '</tr>';

                detailTable += "</table></td></tr>";

                $(obj).parent().parent().after(detailTable);
            });
    }

    //隐藏集群IP详情
    function hideDetail(obj, userId, vmId) {
        $(obj).html("查看详情");
        $(obj).attr("onclick", "showDetail(this,'" + userId + "','" + vmId + "')");
        var nextTr = $(obj).parent().parent().next();
        if (nextTr.attr("class").indexOf("yellowbox") >= 0) {
            nextTr.remove();
        }
    }


    function vmoperate(userId, operation, uuid, endTimeId) {
        if (confirm("该操作会对云主机产生重大影响，您确认进行吗？")) {

            $.post("vm/vmoperate", {
                userId: userId,
                operation: operation,
                uuid: uuid,
                endTimeId: endTimeId
            }, function () {
                if (operation == "delete" || operation == "forceDelete") {
                    gotonext("vm/presearchvm");
                } else {
                    alert("success,操作成功！请刷新页面");
                    fillTipBox("success", "操作成功返回！");
                }
                location.reload();
            });
        }
    }

    $(function() {
        $('#begin-date').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            autoclose:true
        });
        $('#end-date').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            autoclose:true
        });
    });
    getAlarmHistory(null,null)
    $("#search-log").click(function () {
        var btime = $("#begin-date").val();
        var etime = $("#end-date").val();
        var start = new Date().setHours(0, 0, 0, 0);

        if (btime != "") {
            btime = new Date($("#begin-date").val().substring(0, 19).replace(/-/g, '/')).getTime();
        } else {
            btime = start;
            $("#begin-date").attr("placeholder",formatDateTime(start));
        }
        if(etime != "") {
            etime = new Date($("#end-date").val().substring(0, 19).replace(/-/g, '/')).getTime();
        }else {
            etime = new Date().getTime();
        }
        getAlarmHistory(btime,etime);
    });

    function formatDateTime(inputTime) {
        var date = new Date(inputTime);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    };
    var btime,etime;
    function getAlarmHistory(begin, end){
        btime=begin;
        etime=end;
        getPage(1);
    }


</script>
<script src="statics/plugin//bootstrap-datetimepicker/ver2/bootstrap-datetimepicker.min.js"></script>
<%--<script src="http://newfront.free4lab.com/js/jquery/jquery.min.js"></script>--%>
<%--<script src="http://newfront.free4lab.com/bootstrap/js/bootstrap.min.js"></script>--%>
<%--<script src="http://newfront.free4lab.com/js/plugin/front.js"></script>--%>
</body>
</html>
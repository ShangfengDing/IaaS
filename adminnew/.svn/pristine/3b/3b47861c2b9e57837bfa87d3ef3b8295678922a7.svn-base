<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.*, appcloud.admin.common.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>运行日志 - 云海IaaS</title>
    <link rel="stylesheet" media="all" type="text/css" href="css/log.css" />
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=runtime"/>
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div class="inner">
                <div >
                    <ol class="breadcrumb">
                        <li>日志管理</li>
                        <li class="active">运行日志</li>
                    </ol>
                </div>
                <div class="panel panel-default front-panel">
                    <div class="panel-body" style="margin-top:3px;padding-bottom:0px">
                        <div id="yunhai-content" class="form-horizontal">
                            <div class="form-group" style="margin-bottom: 8px">
								<span><label class="col-md-1 control-label front-label">起始时间</label>
								<div class="col-md-5">
									<input type="text" class="form-control front-no-box-shadow"
                                           name="rest_example_4_start" id="rest_example_4_start" value=""/>
                                    <!-- <input type="text" class="datepicker" name="time" id="time" /> -->
								</div></span>
                                <span><label class="col-md-1 control-label front-label">终止时间</label>
								<div class="col-md-5">
									<input type="text" class="form-control front-no-box-shadow"
                                           name="rest_example_4_end" id="rest_example_4_end" value=""/>
                                    <!-- <input type="text" class="datepicker" name="time" id="time" /> -->
								</div></span>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">查找顺序</label>
                                <div >
                                    <label class="control-label front-label" style="margin-left: 14px"><input
                                            type="radio" name="timeasc"
                                                                                             value="yes"/>&nbsp;顺序</label>
                                    <label class="control-label front-label" style="margin-left: 40px"><input
                                            type="radio" name="timeasc"
                                                                                             checked="true" value="no"
                                                                                             value="yes"/>&nbsp;倒序</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">功能实体</label>
                                <div class="col-md-5">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="module">
                                        <option value="">--全部--</option>
                                        <option value="RESOURCE_SCHEDULER">RESOURCE_SCHEDULER</option>
                                        <option value="VOLUME_SCHEDULER">VOLUME_SCHEDULER</option>
                                        <option value="VOLUME_PROVIDER">VOLUME_PROVIDER</option>
                                        <option value="VM_SCHDULER">VM_SCHDULER</option>
                                        <option value="VM_CONTROLLER">VM_CONTROLLER</option>
                                        <option value="IMAGE_SERVER">IMAGE_SERVER</option>
                                        <option value="NODE_AGENT">NODE_AGENT</option>
                                        <option value="NODE_MONITOR">NODE_MONITOR</option>
                                        <option value="NETWORK_PROVIDER">NETWORK_PROVIDER</option>
                                        <option value="DHCP_CONTROLLER">DHCP_CONTROLLER</option>
                                        <option value="LOL_SERVER">LOL_SERVER</option>
                                        <option value="API_SERVER">API_SERVER</option>
                                        <option value="VM_FRONT">VM_FRONT</option>
                                        <option value="IAAS_CHECK">IAAS_CHECK</option>
                                        <!--
                                        <option value="VM_ADMIN">VM_ADMIN</option>
                                         -->
                                        <option value="UN_KNOWN">UN_KNOWN</option>
                                    </select>
                                </div>
                                <label class="col-md-1 control-label front-label">日志级别</label>
                                <div class="col-md-5">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="level">
                                        <option value="">--全部--</option>
                                        <option value="INFO">INFO</option>
                                        <option value="DEBUG">DEBUG</option>
                                        <option value="WARN">WARN</option>
                                        <option value="ERROR">ERROR</option>
                                    </select>
                                </div>
                            </div>
                            <div class="hidden" id="more">
                                <div class="form-group">
                                    <span><label class="col-md-1 control-label front-label">用户邮箱</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="email" value=""
                                               onblur="checkEmail()"/>
                                    </div>
                                    <span><label class="col-md-2 control-label front-label">虚拟机/硬盘标识</label></span>
                                    <div class="col-md-4">
                                        <input type="text" class="form-control front-no-box-shadow" id="vmuuid" value=""
                                               onblur="checkVmuuid()"/>
                                    </div>
                                </div>
                                <span id="error" class="redletter"></span>
                                <div class="form-group">
                                    <span><label class="col-md-1 control-label front-label">用户ID</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="user" value=""
                                               onblur="checkUser()"/>
                                    </div>
                                    <span><label class="col-md-1 control-label front-label">事务ID</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="transaction"
                                               value="" onblur="checkTransaction()"/>
                                    </div>
                                </div>
                                <span id="error1" class="redletter"></span>
                                <div class="form-group">
                                    <span><label class="col-md-1 control-label front-label">IP地址</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="ip" value=""
                                               onblur="checkIP()"/>
                                    </div>
                                    <span><label class="col-md-1 control-label front-label">操作</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="operation"
                                               value="" onblur="checkOperation()"/>
                                    </div>
                                </div>
                                <span id="error2" class="redletter"></span>
                                <div class="form-group">
                                    <span><label class="col-md-1 control-label front-label">操作源</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="source" value=""
                                               onblur="checkSource()"/>
                                    </div>
                                    <span><label class="col-md-1 control-label front-label">日志内容</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="log" value=""
                                               onblur="checkLog()"/>
                                    </div>
                                </div>
                            </div>
                            <span id="error3" class="redletter"></span>
                            <div class="form-group">
                                <div class="col-lg-12 text-right" style="padding-top: 5px">
                                    <%--<a href="javascript:void(0)" class="dropdown-toggle " data-toggle="dropdown" onclick="showMore(this)">高级查询<span class="caret"></span></a>--%>
                                    <span id="searchbutton" class="text-right">
                                            <a href="javascript:void(0)" onclick="cancelSearch()" class="btn btn-default">取消</a>
                                        <!--	<a href="javascript:void(0)" class="btn btn-primary"
                                                   onclick="showMore(this)">高级</a>
                                        -->
                                            <button type="button" class="btn btn-primary" onclick="submitAll()" >查询</button>
                                    </span>
                                        <%--<label class="col-md-1 text-left" style="padding-left: 7px">--%>
                                        <%--<div align="left" style="margin-top: 10px;font-weight: normal"><a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"onclick="showMore(this)">高级查询<span class="caret"></span></a></div>--%>
                                        <label class="col-md-1 text-left" style="padding-left: 7px">
                                        <div align="left" style="margin-top: 10px;font-weight: normal"><a
                                                href="javascript:void(0)"
                                                                                   class="dropdown-toggle"
                                                              data-toggle="dropdown"onclick="showMore(this)">高级查询<span class="caret"></span></a></div>
                                        </label>

                                </div>
                            </div>

                        </div>

                    </div>
                    <div class="dottedline topmargin_20"></div>
                    <input type="hidden" id="oldclusterId" value="<s:property value="clusterId"/>"/>
                    <input type="hidden" id="oldzoneId" value="<s:property value="zoneId"/>"/>
                    <input type="hidden" id="oldhostId" value="<s:property value="hostId"/>"/>
                </div>

                <div class="dottedline"></div>
                <div id="query2">

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
<script type="text/javascript" src="js/runtime/log.js"></script>
<script>

    $(document).ready(function () {
        var startDateTextBox = $('#rest_example_4_start');
        var endDateTextBox = $('#rest_example_4_end');

        startDateTextBox.datetimepicker({
            timeFormat: 'HH:mm:ss',
            stepHour: 1,
            stepMinute: 1,
            stepSecond: 5,
            onClose: function (dateText, inst) {
                if (endDateTextBox.val() != '') {
                    var testStartDate = startDateTextBox.datetimepicker('getDate');
                    var testEndDate = endDateTextBox.datetimepicker('getDate');
                    if (testStartDate > testEndDate)
                        endDateTextBox.datetimepicker('setDate', testStartDate);
                }
                /* else {
                    endDateTextBox.val(dateText);
                } */
            },
            onSelect: function (selectedDateTime) {
                endDateTextBox.datetimepicker('option', 'minDate', startDateTextBox.datetimepicker('getDate'));
            }
        });
        endDateTextBox.datetimepicker({
            timeFormat: 'HH:mm:ss',
            stepHour: 1,
            stepMinute: 1,
            stepSecond: 5,
            onClose: function (dateText, inst) {
                if (startDateTextBox.val() != '') {
                    var testStartDate = startDateTextBox.datetimepicker('getDate');
                    var testEndDate = endDateTextBox.datetimepicker('getDate');
                    if (testStartDate > testEndDate)
                        startDateTextBox.datetimepicker('setDate', testEndDate);
                }
                /* else {
                    startDateTextBox.val(dateText);
                } */
            },
            onSelect: function (selectedDateTime) {
                startDateTextBox.datetimepicker('option', 'maxDate', endDateTextBox.datetimepicker('getDate'));
            }
        });
        $("#rest_example_4_end").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("#rest_example_4_start").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("#email").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("#vmuuid").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("#user").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("#transaction").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("#ip").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("#operation").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("#source").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("#log").keydown(function (event) {
            if (event.which == "13" || event.keyCode == "13" ||
                window.event.which == "13" || window.event.keyCode == "13") {
                submitAll();//回车键，用.ajax提交表单
            }
        });
        $("input[name='timeasc']").click(function (event) {
            that = $(this);
            $("input[name='timeasc']").prop("checked", false);
            that.prop("checked", true);
        });
    });

    function showMore(obj) {
        $("#more").toggleClass("hidden");
        if ($("#more").hasClass("hidden")) {
            obj.innerHTML = "高级查询<span class=\"caret\"></span>";
            $("#searchbutton").css("margin-left", "0px");
        } else {
            $("#searchbutton").css("margin-left", "0px");
            obj.innerHTML = "收起";
        }
    }

    function cancelSearch() {
        $("#query").html("");
        $("#rest_example_4_start").val("");
        $("#rest_example_4_end").val("");
        $("#module").val("");
        $("#level").val("");
        $("#user").val("");
        $("#transaction").val("");
        $("#ip").val("");
        $("#operation").val("");
        $("#source").val("");
        $("#log").val("");
        $("#email").val("");
        $("#vmuuid").val("");
        $("#error").html("");
        $("#error1").html("");
        $("#error2").html("");
        $("#error3").html("");
        $("#query2").html("");
    }

    function submitAll() {
        var starttime = $("#rest_example_4_start").val();
        var endtime = $("#rest_example_4_end").val();
        var module = $("#module").val();
        var level = $("#level").val();
        var userId = $("#user").val();
        var transactionId = $("#transaction").val();
        var ip = $("#ip").val();
        var operation = $("#operation").val();
        var source = $("#source").val();
        var logContent = $("#log").val();
        var vmuuid = $("#vmuuid").val();
        var email = $("#email").val();
        var timeasc = $($("input[name='timeasc']:checked")[0]).val();
        if (starttime == "" && endtime == "" && module == "" && level == "" &&
            userId == "" && transactionId == "" && ip == "" && operation == "" &&
            source == "" && logContent == "" && vmuuid == "" && email == "") {
            if (!confirm("查询全部日志？")) {
                return;
            }
        } else {
            if (email != "" && !isEmail(email)) {
                $("#error").html("<div class=\"form-group\"><label class=\"col-md-12\">用户邮箱输入的格式不正确</label></div>");
            } else {
                $("#error").html("");
            }
            if (vmuuid != "" && !isString(vmuuid)) {
                $("#error").html("<div class=\"form-group\"><label class=\"col-md-12\">虚拟机/硬盘标识输入的格式不正确</label></div>");
            } else {
                $("#error").html("");
            }
            if (userId != "" && !isDigit(userId)) {
                $("#error1").html("<div class=\"form-group\"><label class=\"col-md-12\">用户ID输入的格式不正确</label></div>");
                return;
            } else {
                $("#error1").html("");
            }
            if (transactionId != "" && !isString(transactionId)) {
                $("#error1").html("<div class=\"form-group\"><label class=\"col-md-12\">事务ID输入的格式不正确</label></div>");
                return;
            } else {
                $("#error1").html("");
            }
            if (ip != "" && !isIP(ip)) {
                $("#error2").html("<div class=\"form-group\"><label class=\"col-md-12\">IP地址输入的格式不正确</label></div>");
                return;
            } else {
                $("#error2").html("");
            }
            if (operation != "" && !isLineString(operation)) {
                $("#error2").html("<div class=\"form-group\"><label class=\"col-md-12\">操作输入的格式不正确</label></div>");
                return;
            } else {
                $("#error2").html("");
            }
            if (source != "" && !isDotString(source)) {
                $("#error3").html("<div class=\"form-group\"><label class=\"col-md-12\">操作源输入的格式不正确</label></div>");
                return;
            } else {
                $("#error3").html("");
            }

            if (starttime != "") {
                starttime += " 000";
            }
            if (endtime != "") {
                endtime += " 000";
            }
        }

        //alert(starttime);
        //alert(endtime);

        $("#query").html("");
        searchLog(starttime, endtime, module, level, userId, transactionId, ip,
            operation, source, logContent, email, vmuuid, timeasc);
    }

    function searchLog(starttime, endtime, module, level, userId, transactionId, ip,
                       operation, source, logContent, email, vmuuid, timeasc) {
        loadScrollToTop();
        loadScrollToBottom();
        $.ajax({
            type: "post",
            url: "runtime/searchlog",
            data: {
                startTime: starttime, endTime: endtime, module: module,
                level: level, userId: userId, transactionId: transactionId, ip: ip,
                operation: operation, source: source, logContent: logContent, email: email,
                vmUuid: vmuuid, timeasc: timeasc
            },
            success: function (data) {
                var acMessageLogList = data.messageLogs;
                if (data.isEmail == 1) {
                    if ($("#query").html() == "") {
                        $("#query").html("<div class=\"divpage\" align=\"center\">无更多结果</div>");
                    } else {
                        $("#query #searchmore").html("<div class='divpage'>无更多结果</div>");
                    }
                    fillTipBox("error", "用户邮箱输入有误！");
                } else if (acMessageLogList == null || acMessageLogList == "" ||
                    acMessageLogList.length == 0) {
                    if ($("#query").html() == "") {
                        $("#query").html("<div class=\"divpage\" align=\"center\">无更多结果</div>");
                    } else {
                        $("#query #searchmore").html("<div class='divpage'>无更多结果</div>");
                    }
                }
                else {
                    //var st = (data.count - 1) * 20;
                    var timeMaps = JSON.parse(JSON.stringify(data.timeMap));
                    var present = "";
                    var pageHtml = "";
                    var flag = $("#query").html();
                    if (flag == "") {
                        present
                            +="<div><label class='control-label front-label text-left'style='font-weight:normal'>共"+acMessageLogList.length
                             +"条</label>"+
                            "<div class=\"panel panel-default front-panel\" style=\"margin-top: 10px\">\n" +
                            "<table class=\"table table-striped front-table\" id=\"querytable\"" +
                            "style=\"margin-bottom: 0px\"><tr>" +
                            //"<td width=\"60px\"></td>"+
                            "<th class=\" col-md-2\">时间</th>" +
                            "<th class=\"\">ID  /  IP地址</th>" +
                            "<th class=\"col-md-1\" style='text-align: center'>日志级别</th>" +
                            "<th class=\" col-md-1\" style='text-align: center;'>功能实体</th>" +

                            "<th class=\"col-md-2\" style='text-align: center;'>事务ID</th>" +
//                            "<th class=\"col-md-1\">IP地址</th>" +
                            "<th class=\"col-md-2\" style='text-align: center;'>UUID</th>" +
                            "<th class=\"col-md-1\" style='text-align: center;'>详情</th>" +
                            //"<td width=\"90px\" class=\"rightpadding_5 leftpadding_5\">操作</td>"+
                            //"<td width=\"100px\" class=\"rightpadding_5 leftpadding_5\">操作源</td>"+
                            //"<td class=\"leftpadding_5\">日志内容</td>"+
                            "</tr>";
                    }
                    for (var i = 0; i < acMessageLogList.length; i++) {
                        present += "<tr>"/* +"<td width=\"60px\">"+(st+i+1)+"</td>" */ +
                            "<td class=\"col-md-1\" style='vertical-align: middle'>" +
                            toDate(acMessageLogList[i].logTime)
                        ;
                        present += "<td class=\"\" style='vertical-align: middle'>" +
                            acMessageLogList[i].userId ;
                        if (acMessageLogList[i].ipAddress != null) present += "  /  "+acMessageLogList[i].ipAddress;
                        else present += "  /  无";
                        present += "</td><td class=\"col-md-1\" style='vertical-align: middle;text-align: center'>";
                        if (acMessageLogList[i].logLevel == "ERROR") {
                            present += "<span class=\"redletter\">" + acMessageLogList[i].logLevel + "</span></td>";
                        } else {
                            present += acMessageLogList[i].logLevel + "</td>";
                        }
                        present += "</td><td class=\"col-md-1\" style='vertical-align: middle;text-align: center;'>" +
                            acMessageLogList[i].module + "</td>";
//                        if (acMessageLogList[i].logLevel == "ERROR") {
//                            present += "<span class=\"redletter\">" + acMessageLogList[i].logLevel + "</span></td>";
//                        } else {
//                            present += acMessageLogList[i].logLevel + "</td>";
//                        }
//                        present += "<td class=\"\" style='vertical-align: middle'>" +
//                            acMessageLogList[i].userId ;
                        present+="</td><td class=\"col-md-2\" style='vertical-align: middle;text-align: center' title="+
                            acMessageLogList[i].transactionId+">";
                            if (acMessageLogList[i].transactionId.length>16) {
                                present += acMessageLogList[i].transactionId.substring(0,16)+"...";
                            }else {present += acMessageLogList[i].transactionId}
//                           present += "</td><td class=\"col-md-1\" style='vertical-align: middle'>" ;

//                            if (acMessageLogList[i].ipAddress != null) present += acMessageLogList[i].ipAddress;
//                            else present += "无";
                            present +=
                                "</td><td class=\"col-md-2\" style='vertical-align: middle;text-align: center;' title="+acMessageLogList[i].vmHdUuid+">";
                            if (acMessageLogList[i].vmHdUuid != null)
                                present+=acMessageLogList[i].vmHdUuid.substring(0,16)+"...";
                            else present += "无";
                            present+=
                                "</td><td class=\"col-md-1\" style='vertical-align: middle;text-align: center;'>" +
                            "<div class=\"dropdown\"><a class=\"blueletter\" data-toggle=\"dropdown\" href=\"javascript:void(0)\">查看</a>" +
                            "<ul class=\"dropdown-menu pull-right \" role=\"menu\" aria-labelledby=\"dLabel\"> <li class=\"padding715\">操作:" + acMessageLogList[i].operateDrpt +
                            "</li><li style='white-space:nowrap;' class=\"padding715\">操作源:" + acMessageLogList[i].sourceClass +
                            "</li><li class=\"padding715\">内容:" + acMessageLogList[i].logContent +
                            "</li></ul></div></td>" +

                            //acMessageLogList[i].operateDrpt + "</td><td class=\"rightpadding_5 leftpadding_5\"><span title='"+
                            //acMessageLogList[i].sourceClass + "'>" + acMessageLogList[i].sourceClass.substring(0,20)+"..."+
                            //"</span></td><td class=\"leftpadding_5\"><span title='"+acMessageLogList[i].logContent+"'>" +
                            //acMessageLogList[i].logContent.substring(0,50)+"..." + "</span></td>"+
                            "</tr>";
//                        present += "<td class=\"\">" +
//                            acMessageLogList[i].userId + "</td><td class=\"col-md-2\">" +
//                            acMessageLogList[i].transactionId + "</td><td class=\"col-md-1\">" +
//                            acMessageLogList[i].ipAddress + "</td><td class=\"col-md-1\">" +
//                            acMessageLogList[i].vmHdUuid + "</td><td class=\"col-md-1\">" +
//                            "<div class=\"dropdown\"><a class=\"blueletter\" data-toggle=\"dropdown\" href=\"javascript:void(0)\">查看</a>" +
//                            "<ul class=\"dropdown-menu pull-right \" role=\"menu\" aria-labelledby=\"dLabel\"> <li class=\"padding715\">操作:" + acMessageLogList[i].operateDrpt +
//                            "</li><li style='white-space:nowrap;' class=\"padding715\">操作源:" + acMessageLogList[i].sourceClass +
//                            "</li><li class=\"padding715\">内容:" + acMessageLogList[i].logContent +
//                            "</li></ul></div></td>" +
//
//                            //acMessageLogList[i].operateDrpt + "</td><td class=\"rightpadding_5 leftpadding_5\"><span title='"+
//                            //acMessageLogList[i].sourceClass + "'>" + acMessageLogList[i].sourceClass.substring(0,20)+"..."+
//                            //"</span></td><td class=\"leftpadding_5\"><span title='"+acMessageLogList[i].logContent+"'>" +
//                            //acMessageLogList[i].logContent.substring(0,50)+"..." + "</span></td>"+
//                            "</tr>";
                    }

                    if (flag == "") {
                        present += "</table></div><div class='divpage' id='searchmore'>";
                    }

                    var newtime = timeMaps[acMessageLogList[i - 1].logTime + 1];
                    if (timeasc == "yes") {
                        if (newtime < endtime || endtime == "") {
                            pageHtml += "<a class='pagenum' href='javascript:void(0)' " +
                                "onclick=\"searchLog('" + newtime + "','" + endtime + "','" + module + "','" +
                                level + "','" + userId + "','" + transactionId + "','" + ip + "','" + operation +
                                "','" + source + "','" + logContent + "','" + email + "','" + vmuuid + "')\">更多</a>";
                        } else {
                            pageHtml += "无更多结果";
                        }
                    } else {
                        if (newtime > starttime || starttime == "") {
                            pageHtml += "<a class='pagenum' href='javascript:void(0)' " +
                                "onclick=\"searchLog('" + starttime + "','" + newtime + "','" + module + "','" +
                                level + "','" + userId + "','" + transactionId + "','" + ip + "','" + operation +
                                "','" + source + "','" + logContent + "','" + email + "','" + vmuuid + "')\">更多</a>";
                        } else {
                            pageHtml += "无更多结果";
                        }
                    }


                    if (flag == "") {
                        present += pageHtml;
                        present += "</div>";
                        $("#query").html(present);
                    } else {
                        $("#querytable").append(present);
                        $("#searchmore").html(pageHtml);
                    }
                    $("#querytable tr:first").removeClass("tabletitle");
                    $(".webwidget_vertical_menu").webwidget_vertical_menu();
                    getStyle();
                }
            }
        });
    }

    function checkEmail() {
        var email = $("#email").val();
        if (email != "" && !isEmail(email)) {
            $("#error").html("<div class=\"form-group\"><label class=\"col-md-12\">用户邮箱输入的格式不正确</label></div>");
        } else {
            $("#error").html("");
        }
    }

    function checkVmuuid() {
        var vmuuid = $("#vmuuid").val();
        if (vmuuid != "" && !isString(vmuuid)) {
            $("#error").html("<div class=\"form-group\"><label class=\"col-md-12\">虚拟机/硬盘标识输入的格式不正确</label></div>");
        } else {
            $("#error").html("");
        }
    }

    function checkUser() {
        var userId = $("#user").val();
        if (userId != "" && !isDigit(userId)) {
            $("#error1").html("<div class=\"form-group\"><label class=\"col-md-12\">用户ID输入的格式不正确</label></div>");
        } else {
            $("#error1").html("");
        }
    }

    function checkTransaction() {
        var transactionId = $("#transaction").val();
        if (transactionId != "" && !isString(transactionId)) {
            $("#error1").html("<div class=\"form-group\"><label class=\"col-md-12\">事务ID输入的格式不正确</label></div>");
        } else {
            $("#error1").html("");
        }
    }

    function checkIP() {
        var ip = $("#ip").val();
        if (ip != "" && !isIP(ip)) {
            $("#error2").html("<div class=\"form-group\"><label class=\"col-md-12\">IP地址输入的格式不正确</label></div>");
        } else {
            $("#error2").html("");
        }
    }

    function checkOperation() {
        var operation = $("#operation").val();
        if (operation != "" && !isLineString(operation)) {
            $("#error2").html("<div class=\"form-group\"><label class=\"col-md-12\">操作输入的格式不正确</label></div>");
        } else {
            $("#error2").html("");
        }
    }

    function checkSource() {
        var source = $("#source").val();
        if (source != "" && !isDotString(source)) {
            $("#error3").html("<div class=\"form-group\"><label class=\"col-md-12\">操作源输入的格式不正确</label></div>");
        } else {
            $("#error3").html("");
        }
    }

    function checkLog() {
        //var logContent = $("#log").val();
    }

    function isIP(ipAddr) {
        var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
        if (ipAddr.match(reg) || isIPv6(ipAddr)) {
            return true;
        } else {
            return false;
        }
    }

    function isIPv6(str) {
        var idx = str.indexOf("::");
        // there is no "::" in the ip address
        if (idx == -1) {
            var items = str.split(":");
            if (items.length != 8) {
                return false;
            } else {
                for (i in items) {
                    if (!isHex(items[i])) {
                        return false;
                    }
                }
                return true;
            }
        } else {
            // at least, there are two "::" in the ip address
            if (idx != str.lastIndexOf("::")) {
                return false;
            } else {
                var items = str.split("::");
                var items0 = items[0].split(":");
                var items1 = items[1].split(":");
                if ((items0.length + items1.length) > 7) {
                    return false;
                } else {
                    for (i in items0) {
                        if (!isHex(items0[i])) {
                            return false;
                        }
                    }
                    for (i in items1) {
                        if (!isHex(items1[i])) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
    }

    // check whether every char of the str is a Hex char(0~9,a~f,A~F)
    function isHex(str) {
        if (str.length == 0 || str.length > 4) {
            return false;
        }
        str = str.toLowerCase();
        var ch;
        for (var i = 0; i < str.length; i++) {
            ch = str.charAt(i);
            if (!(ch >= '0' && ch <= '9') && !(ch >= 'a' && ch <= 'f')) {
                return false;
            }
        }
        return true;
    }

    function isDigit(str) {
        var reg = /^[0-9]+$/;
        if (str.match(reg)) {
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

    function isDotString(str) {
        var reg = /^[A-Za-z0-9\.]+$/;
        if (str.match(reg)) {
            return true;
        } else {
            return false;
        }
    }

    function isLineString(str) {
        var reg = /^[_A-Za-z0-9]+$/;
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
    function toDate(unixTimeStr) {
        var unixTimestamp = new Date(unixTimeStr);
        return unixTimestamp.toLocaleString();
    }
</script>
</body>
</html>
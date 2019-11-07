<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.*, appcloud.admin.common.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>操作日志 - 云海IaaS</title>
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
                        <li class="active">操作日志</li>
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
                                <div>
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

                                <span><label class="col-md-1 control-label front-label">用户邮箱</label></span>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" id="email" value=""
                                           onblur="checkEmail()"/>
                                </div>

                            </div>
                            <div class="hidden" id="more">
                                <div class="form-group">
                                    <span><label class="col-md-1 control-label front-label">用户ID</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="user" value=""
                                               onblur="checkUser()"/>
                                    </div>
                                    <span><label class="col-md-1 control-label front-label">设备</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="vm" value=""/>
                                    </div>
                                </div>
                                <span id="error" class="redletter"></span>
                                <div class="form-group">
                                    <span><label class="col-md-1 control-label front-label">设备ID</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="vmuuid"
                                               value="" onblur="checkVmuuid()"/>
                                    </div>
                                    <span><label class="col-md-1 control-label front-label">操作类型</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="operateType"
                                               value=""/>
                                    </div>
                                </div>
                                <span id="error1" class="redletter leftmargin_10"></span>
                                <div class="form-group">
                                    <span><label class="col-md-1 control-label front-label">操作结果</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="result" value=""/>
                                    </div>
                                    <span><label class="col-md-1 control-label front-label">服务提供商</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="provider"
                                               value="" onblur="checkSource()"/>
                                    </div>
                                </div>
                                <%--<div class="form-group">--%>
                                    <%--<span><label class="col-md-1 control-label front-label">服务提供商</label></span>--%>
                                    <%--<div class="col-md-5">--%>
                                        <%--<input type="text" class="form-control front-no-box-shadow" id="provider"--%>
                                               <%--value="" onblur="checkSource()"/>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </div>
                            <span id="error2" class="redletter"></span>
                            <div class="form-group">
                                <div class="col-lg-12 text-right" style="padding-top: 5px">
                                    <%--<a href="javascript:void(0)" class="dropdown-toggle " data-toggle="dropdown" onclick="showMore(this)">高级查询<span class="caret"></span></a>--%>
                                    <span id="searchbutton" class="text-right">
                                            <a href="javascript:void(0)" onclick="cancelSearch()" class="btn btn-default">取消</a>
                                        <!--	<a href="javascript:void(0)" class="btn btn-primary"
                                                   onclick="showMore(this)">高级</a>
                                        -->
                                            <button type="button" class="btn btn-primary" onclick="submitAll()">查询</button>
                                    </span>
                                    <label class="col-md-1 text-left">
                                        <div align="left"
                                             style="margin-top: 10px;font-weight: normal;margin-left: 7px;"><a
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

        //当从用户管理界面跳至本页面时使用
        var query = window.location.href;
        query = query.substring(query.indexOf("?") + 1);
        var pairs = query.split("&");
        for (var i = 0; i < pairs.length; i++) {
            var paramName = pairs[i].substring(0, pairs[i].indexOf("="));
            var paramValue = pairs[i].substring(pairs[i].indexOf("=") + 1);
            if (paramName == "type" && paramValue == "all") {
                searchLog("", "", "", "", "", "", "", "", "");
            }
        }
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
        $("#level").val("");
        $("#user").val("");
        $("#transaction").val("");
        $("#operation").val("");
        $("#source").val("");
        $("#log").val("");
        $("#email").val("");
        $("#vmuuid").val("");
        $("#error").html("");
        $("#error1").html("");
        $("#error2").html("");
        $("#query2").html("");
    }

    function submitAll() {
        var starttime = $("#rest_example_4_start").val();
        var endtime = $("#rest_example_4_end").val();
        var level = $("#level").val();
        var userId = $("#user").val();
        var result = $("#result").val();
        var vm = $("#vm").val();
        var provider = $("#provider").val();
        var vmuuid = $("#vmuuid").val();
        var email = $("#email").val();
        var operateType = $("#operateType").val();
        var timeasc = $($("input[name='timeasc']:checked")[0]).val();

        if (starttime == "" && endtime == "" && level == "" &&
            userId == "" && operateType == "" &&
            provider == "" && email == "" && vm == "" && email == ""
            && result == "") {
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
                $("#error1").html("<div class=\"form-group\"><label class=\"col-md-12\">设备ID输入的格式不正确</label></div>");
            } else {
                $("#error1").html("");
            }
            if (userId != "" && !isDigit(userId)) {
                $("#error").html("<div class=\"form-group\"><label class=\"col-md-12\">用户ID输入的格式不正确</label></div>");
                return;
            } else {
                $("#error").html("");
            }
            if (source != "" && !isDotString(provider)) {
                $("#error2").html("<div class=\"form-group\"><label class=\"col-md-12\">服务提供商的格式不正确</label></div>");
            } else {
                $("#error2").html("");
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
        searchLog(starttime, endtime, level, userId, vm, email, vmuuid, result, provider, operateType, timeasc);
    }

    function searchLog(starttime, endtime, level, userId, vm, email, vmuuid, result, provider, operateType, timeasc) {
        loadScrollToTop();
        loadScrollToBottom();
        $.ajax({
            type: "post",
            url: "runtime/searchOperateLog",
            data: {
                startTime: starttime, endTime: endtime,
                level: level, userId: userId, vm: vm, email: email,
                vmUuid: vmuuid, result: result, provider: provider,
                operateType: operateType, timeasc: timeasc
            },
            success: function (data) {
                var acOperateLogList = data.acOperateLogList;
                if (data.isEmail == 1) {
                    if ($("#query").html() == "") {
                        $("#query").html("<div class=\"divpage\" align=\"center\">无更多结果</div>");
                    } else {
                        $("#query #searchmore").html("<div class='divpage'>无更多结果</div>");
                    }
                    fillTipBox("error", "用户邮箱输入有误！");
                } else if (acOperateLogList == null || acOperateLogList == "" ||
                    acOperateLogList.length == 0) {
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
                            +="<div><label class='control-label front-label text-left'style='font-weight:normal'>共"+acOperateLogList.length
                                +"条</label>";
                        present += "<div class=\"panel panel-default front-panel\" style=\"margin-top: 10px\">\n" +
                            "<table class=\"table table-striped front-table\"\n" +
                            "style=\"margin-bottom: 0px\" id=\"querytable\"><tr>" +
                            //"<td width=\"60px\"></td>"+
                            "<th class=\"col-md-2\">时间</th>" +
                            "<th class=\"\">ID</th>" +
                            "<th class=\"col-md-1\" style='text-align: center;'>日志级别</th>" +
                            "<th class=\"col-md-1\" style='text-align: center;'>服务提供商</th>" +
                            "<th class=\"col-md-1\" style='text-align: center;'>设备</th>" +
                            "<th class=\"col-md-2\" style='text-align: center;'>设备ID</th>" +
                            "<th class=\"col-md-2\" style='text-align: center;'>操作类型</th>" +
                            "<th class=\"col-md-2\" style=\"text-align: center\">操作结果</th>" +
                            "</tr>";
                    }
                    for (var i = 0; i < acOperateLogList.length; i++) {
                        present += "<tr>"/* +"<td width=\"60px\">"+(st+i+1)+"</td>" */ + "<td class=\"col-md-1\" style='vertical-align: middle'>" +
                            toDate(acOperateLogList[i].createdTime)
                            + "</td>";
                        present += "<td class=\"\" style='vertical-align: middle'>" +
                            acOperateLogList[i].userId + "</td>";
                        present += "<td class=\"col-md-1\" style='vertical-align: middle;text-align: center'>";
                        if (acOperateLogList[i].infoType == "ERROR") {
                            present += "<span class=\"redletter\">" + acOperateLogList[i].infoType + "</span></td>";
                        } else {
                            present += acOperateLogList[i].infoType + "</td>";
                        }
                        present += "<td class=\"col-md-1\" style='vertical-align: middle;text-align: center;'>" +
                            acOperateLogList[i].provider +
                            "</td><td class=\"col-md-1\" style='vertical-align: middle;text-align: center;'>" +
                            acOperateLogList[i].device ;
                        present+=
                        "<td class=\"col-md-2\" title="+acOperateLogList[i].deviceId+" style='vertical-align: middle;text-align: center;'>";
                        if (acOperateLogList[i].deviceId != null)
                            present += acOperateLogList[i].deviceId.substring(0,16)+"...";
                        else present += "无";
                        present += "<td class=\"col-md-2\" style='vertical-align: middle;text-align: center;'>" +
                            acOperateLogList[i].operateType +
                            "<td class=\"col-md-2\" style='vertical-align: middle;text-align: center'>" +
                            acOperateLogList[i].result +
                            "</td></tr>";
                    }

                    if (flag == "") {
                        present += "</table></div>                <div class='divpage' id='searchmore'>";
                    }

                    var newtime = timeMaps[acOperateLogList[i - 1].createdTime + 1];
                    if (timeasc == "yes") {
                        if (newtime < endtime || endtime == "") {
                            pageHtml += "<a class='pagenum' href='javascript:void(0)' " +
                                "onclick=\"searchLog('" + newtime + "','" + endtime + "','" +
                                level + "','" + userId + "','" + vm + "','" + email + "','" + vmuuid +
                                "','" + result + "','" + provider + "')\">更多</a>";
                        } else {
                            pageHtml += "无更多结果";
                        }
                    } else {
                        if (newtime < starttime || starttime == "") {
                            pageHtml += "<a class='pagenum' href='javascript:void(0)' " +
                                "onclick=\"searchLog('" + starttime + "','" + newtime + "','" +
                                level + "','" + userId + "','" + vm + "','" + email + "','" + vmuuid +
                                "','" + result + "','" + provider + "')\">更多</a>";
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
            $("#error1").html("<div class=\"form-group\"><label class=\"col-md-12\">设备ID输入的格式不正确</label></div>");
        } else {
            $("#error1").html("");
        }
    }

    function checkUser() {
        var userId = $("#user").val();
        if (userId != "" && !isDigit(userId)) {
            $("#error").html("<div class=\"form-group\"><label class=\"col-md-12\">用户ID输入的格式不正确</label></div>");
        } else {
            $("#error").html("");
        }
    }

    function checkSource() {
        var provider = $("#provider").val();
        if (provider != "" && !isDotString(provider)) {
            $("#error2").html("<div class=\"form-group\"><label class=\"col-md-12\">服务提供商输入的格式不正确</label></div>");
        } else {
            $("#error2").html("");
        }
    }

    function checkLog() {
        //var logContent = $("#log").val();
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
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.*, appcloud.admin.common.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>结算日志 - 云海IaaS</title>
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
                        <li class="active">结算日志</li>
                    </ol>
                </div>
                <div class="panel panel-default front-panel">
                    <%--<s:include value="/system/_left.jsp?menu=vm" />--%>

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
                                <label class="control-label front-label" style="margin-left: 14px"><input
                                        type="radio" name="timeasc"
                                        value="yes"/>&nbsp;顺序</label>
                                <label class="control-label front-label" style="margin-left: 40px"><input
                                        type="radio" name="timeasc"
                                        checked="true" value="no"
                                        value="yes"/>&nbsp;倒序</label>
                            </div>
                            <div class="hidden" id="more">
                                <div class="form-group">
                                    <label class="col-md-1 control-label front-label">充值类型</label>
                                    <div class="col-md-5">
                                        <select class="form-control front-no-radius front-no-box-shadow" id="type">
                                            <option value="">--全部--</option>
                                            <option value="HD">HD</option>
                                            <option value="INSTANCETYPE">INSTANCETYPE</option>
                                            <option value="VM">VM</option>
                                            <option value="VMPACKAGE">VMPACKAGE</option>
                                        </select>
                                    </div>
                                    <label class="col-md-1 control-label front-label">计费周期</label>
                                    <div class="col-md-5">
                                        <select class="form-control front-no-radius front-no-box-shadow" id="pay_type">
                                            <option value="">--全部--</option>
                                            <option value="按需">按需</option>
                                            <option value="包年">包年</option>
                                            <option value="包日">包日</option>
                                            <option value="包月">包月</option>
                                        </select>
                                    </div>
                                </div>
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
                                    <span> <label class="col-md-1 control-label front-label">ID
                                </label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="user" value=""
                                               onblur="checkUser()"/>
                                    </div>
                                    <span><label class="col-md-1 control-label front-label">GroupID</label></span>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" id="group_id"
                                               value="" onblur="checkGroupId()"/>
                                    </div>
                                </div>
                            </div>
                            <span id="error1" class="redletter leftmargin_10"></span>
                            <div class="form-group">
                                <div class="col-lg-12 text-right" style="padding-top: 5px;">
                                    <%--<a href="javascript:void(0)" class="dropdown-toggle " data-toggle="dropdown" onclick="showMore(this)">高级查询<span class="caret"></span></a>--%>
                                    <span id="searchbutton" class="text-right">
                                            <a href="javascript:void(0)" onclick="cancelSearch()" class="btn btn-default">取消</a>
                                        <!--	<a href="javascript:void(0)" class="btn btn-primary"
                                                   onclick="showMore(this)">高级</a>
                                        -->
                                            <button type="button" class="btn btn-primary" onclick="submitAll()">查询</button>
                                    </span>
                                    <label class="col-md-1 text-left">
                                        <div align="left" style="margin-top: 10px;font-weight: normal;margin-left: 7px"><a
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
        $("#query2").html("");
    }

    function submitAll() {
        var starttime = $("#rest_example_4_start").val();
        var endtime = $("#rest_example_4_end").val();
        var userId = $("#user").val();
        var vmuuid = $("#vmuuid").val();
        var email = $("#email").val();
        var type = $("#type").val();
        var payType = $("#pay_type").val();
        var groupId = $("#group_id").val();
        var timeasc = $($("input[name='timeasc']:checked")[0]).val();
        if (starttime == "" && endtime == "" && userId == "" && vmuuid == "" && email == "") {
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
            if (groupId != "" && !isDigit(groupId)) {
                $("#error1").html("<div class=\"form-group\"><label class=\"col-md-12\">Group ID输入的格式不正确</label></div>");
                return;
            } else {
                $("#error1").html("");
            }
            if (starttime != "") {
                starttime += " 000";
            }
            if (endtime != "") {
                endtime += " 000";
            }
        }

        $("#query").html("");
        searchLog(starttime, endtime, userId, email, vmuuid, type, payType, groupId, timeasc);
    }

    function searchLog(starttime, endtime, userId, email, vmuuid, type, payType, groupId, timeasc) {
        loadScrollToTop();
        loadScrollToBottom();
        $.ajax({
            type: "post",
            url: "runtime/searchInstanceLog",
            data: {
                startTime: starttime, endTime: endtime, userId: userId, email: email, vmUuid: vmuuid,
                payType: payType, groupId: groupId, type: type, timeasc: timeasc

            },
            success: function (data) {
                var instanceLogList = data.instanceLogList;
                if (data.isEmail == 1) {
                    if ($("#query").html() == "") {
                        $("#query").html("<div class=\"divpage\" align=\"center\">无更多结果</div>");
                    } else {
                        $("#query #searchmore").html("<div class='divpage'>无更多结果</div>");
                    }
                    fillTipBox("error", "用户邮箱输入有误！");
                } else if (instanceLogList == null || instanceLogList == "" ||
                    instanceLogList.length == 0) {
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
                            +="<div><label class='control-label front-label text-left'style='font-weight:normal'>共"+instanceLogList.length
                            +"条</label>";
                        present += "<div class=\"panel panel-default front-panel\" style=\"margin-top: 10px\">\n" +
                            "                    <table class=\"table table-striped front-table\"\n" +
                            "                               style=\"margin-bottom: 0px\" id=\"querytable\"><tr>" +
                            //"<td width=\"60px\"></td>"+
                            "<th class=\"col-md-2 \">充值时间</th>" +
                            "<th class=\"\">ID</th>" +
                            "<th class=\"col-md-2\" style='text-align: center'>充值类型</th>" +
                            "<th class=\"col-md-2\" style='text-align: center'>截止时间</th>" +
                            "<th class=\"col-md-1\" style='text-align: center'>计费周期</th>" +
                            "<th class=\"col-md-1\" style='text-align: center'>群组Id</th>" +
                            "</tr>";
                    }
                    for (var i = 0; i < instanceLogList.length; i++) {
                        present += "<tr>"/* +"<td width=\"60px\">"+(st+i+1)+"</td>" */ + "<td class=\"col-md-2\">" +
                            toDate(instanceLogList[i].createdTime) + "</td>";
                        present += "<td class=\"\">" +
                            instanceLogList[i].userId + "</td><td class=\"col-md-2\" style='text-align: center'>" +
                            instanceLogList[i].type + "</td><td class=\"col-md-2\" style='text-align: center'>" +
                            toDate(instanceLogList[i].endTime) + "</td><td class=\"col-md-1\" style='text-align: center'>" +
                            instanceLogList[i].payType + "</td><td class=\"col-md-1\" style='text-align: center'>" +
                            instanceLogList[i].groupId + "</td></tr>";
                    }

                    if (flag == "") {
                        present += "</table></div><div class='divpage' id='searchmore'>";
                    }

                    var newtime = timeMaps[instanceLogList[i - 1].createdTime + 1];
                    if (timeasc == "yes") {
                        if (newtime < endtime || endtime == "") {
                            pageHtml += "<a class='pagenum' href='javascript:void(0)' " +
                                "onclick=\"searchLog('" + newtime + "','" + endtime + "','" + userId + "','" + email + "','" + vmuuid + "','" + type + "','" + payType + "','" + groupId + "','" + timeasc + "')\">更多</a>";
                        } else {
                            pageHtml += "无更多结果";
                        }
                    } else {
                        if (starttime < newtime || starttime == "") {
                            pageHtml += "<a class='pagenum' href='javascript:void(0)' " +
                                "onclick=\"searchLog('" + starttime + "','" + newtime + "','" + userId + "','" + email + "','" + vmuuid + "','" + type + "','" + payType + "','" + groupId + "','" + timeasc + "')\">更多</a>";
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

    function checkGroupId() {
        var groupId = $("#group_id").val();
        if (groupId != "" && !isDigit(groupId)) {
            $("#error1").html("<div class=\"form-group\"><label class=\"col-md-12\">Group ID输入的格式不正确</label></div>");
        } else {
            $("#error1").html("");
        }
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

    //判断是否是email
    function isEmail(str) {
        var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        return reg.test(str);
    }

    //unix转普通时间
    function toDate(unixTimeStr) {
        var unixTimestamp = new Date(unixTimeStr);
        return unixTimestamp.toLocaleString();
    }

</script>
</body>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>运行日志</title>
<s:include value="/template/_head.jsp" />
<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>/css/jquery-ui.css" />
<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>/css/jquery-ui-timepicker-addon.css" />
</head>
<body>
<div id="container">
<s:include value="/template/_pub_banner.jsp?index=v" />
<div id="inner">
	<s:include value="/template/_left.jsp?menu=log" />
    <div class="right"> 
		<div class="divline bottommargin_20">日志查询</div>
		<table class="border:0">
			<tr height="38px"><td width="60px">起始时间</td><td width="350px"><input type="text" name="rest_example_4_start" id="rest_example_4_start" value="" class="squareinput leftmargin_10 rightmargin_20" style="width:175px"/>
	        </td><td width="60px">终止时间</td><td><input type="text" name="rest_example_4_end" id="rest_example_4_end" value="" class="squareinput leftmargin_10 rightmargin_20" style="width:175px" />
	        </td></tr><%-- <tr height="38px"><td><span>日志级别</span></td><td>
            <select class="selectboxsmall leftmargin_10" id="level">
	            <option value="">--全部--</option>
	            <option value="INFO">INFO</option>
	            <option value="WARN">WARN</option>
	            <option value="ERROR">ERROR</option>
            </select></td>
            <td>虚拟机/硬盘标识</td><td><input type="text" class="squareinput leftmargin_10 rightmargin_20" id="vmuuid" onblur="checkVmuuid()"/>
            <span id="error" class="redletter"></span></td></tr> --%>
            <tr height="38px"><%-- <td>操作</td><td><!-- <input type="text" class="squareinputlt leftmargin_10 rightmargin_20" id="operation" onblur="checkOperation()"/> -->
             <select class="selectboxsmall leftmargin_10" id="operation">
                <option value="">--全部--</option>
                <option value="新建">新建</option>
                <option value="修改">修改</option>
                <option value="删除">删除</option>
                <option value="续费">续费</option>
                <option value="挂载">挂载</option>
                <option value="卸载">卸载</option>
            </select> --%>
            <%-- <span>事务ID</span><input type="text" class="squareinputlt leftmargin_10" id="transaction" onblur="checkTransaction()"/> --%>
            <!-- </td> --><td>日志内容</td><td><input type="text" class="squareinput leftmargin_10 rightmargin_20" id="log" onblur="checkLog()"/>
            <%-- <span id="error2" class="redletter"></span> --%></td><td></td>
            <td><button class="button leftmargin_10" onclick="submitAll()">查询</button>
            <a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="cancelSearch()">取消</a></td></tr>
        </table>
		<div class="dottedline"></div>
		<div id="query"></div>
	</div><!--right-->
</div>
<s:include value="/template/_footer.jsp" />
</div><!--#container-->
<s:include value="/template/_common_js.jsp" />
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/public.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/plugin/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/plugin/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>/js/plugin/jquery-ui-sliderAccess.js"></script>
<script>
$(document).ready(function(){
	var startDateTextBox = $('#rest_example_4_start');
    var endDateTextBox = $('#rest_example_4_end');
    
    startDateTextBox.datetimepicker({ 
        timeFormat: 'HH:mm:ss',
        stepHour: 1,
        stepMinute: 1,
        stepSecond: 5,
        onClose: function(dateText, inst) {
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
        onSelect: function (selectedDateTime){
            endDateTextBox.datetimepicker('option', 'minDate', startDateTextBox.datetimepicker('getDate') );
        }
    });
    endDateTextBox.datetimepicker({
    	timeFormat: 'HH:mm:ss',
        stepHour: 1,
        stepMinute: 1,
        stepSecond: 5,
        onClose: function(dateText, inst) {
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
        onSelect: function (selectedDateTime){
            startDateTextBox.datetimepicker('option', 'maxDate', endDateTextBox.datetimepicker('getDate') );
        }
    });
    
    //从sum页面跳转来
    var query = window.location.href;
    query = query.substring(query.indexOf("?")+1);
    var pairs = query.split("&");
    for(var i=0; i<pairs.length; i++) {
    	var paramName = pairs[i].substring(0,pairs[i].indexOf("="));
    	var paramValue = pairs[i].substring(pairs[i].indexOf("=")+1);
    	if(paramName == "type") {
    		searchLog("", "", "", "1");
    	}
    }
});

function cancelSearch(){
	$("#query").html("");
	$("#rest_example_4_start").val("");
	$("#rest_example_4_end").val("");
	$("#log").val("");
}

function submitAll(){
	var starttime = $("#rest_example_4_start").val();
    var endtime = $("#rest_example_4_end").val();
    var level = $("#level").val();
    var operation = $("#operation").val();
    var logContent = $("#log").val();
    var vmuuid = $("#vmuuid").val();
    var email = $("#email").val();
	
    if(starttime == "" && endtime == "" && logContent == ""){
        if(!confirm("查询全部日志？")){
            return;
        }
    }else{
        if(starttime != ""){
        	starttime += " 000";
        }
        if(endtime != ""){
        	endtime += " 000";
        }
    }
    
	$("#query").html("");
	searchLog(starttime, endtime, logContent, "1");
}

function searchLog(starttime, endtime, logContent, order){
	showLoading();
    $.ajax({
        type:"post", 
        url:"log/searchlog", 
        data:{
        	startTime: starttime, endTime: endtime, logContent:logContent, order:order
        },
        success:function(data){
        	var acMessageLogList = data.messageLogs;
        	if(acMessageLogList == null || acMessageLogList == "" ||
            		acMessageLogList.length == 0){
            	if($("#query").html() == ""){
            		$("#query").html("<h3 class='centeralign'>无更多结果</h3>");
            	}else{
            		$("#query #searchmore").html("<div class='divpage'>无更多结果</div>");
            	}
            }
            else{
                //var st = (data.count - 1) * 20;
                var timeMaps = JSON.parse(JSON.stringify(data.timeMap));
                var present = "";
                var pageHtml = "";
                var flag = $("#query").html();
                if(flag == ""){
                	 present += "<table class='datatable topmargin_20' id=\"querytable\"><tr>"+
                     "<td width=\"180px\" class=\"rightpadding_5\">时间</td>"+
                     "<td width=\"200px\" class=\"rightpadding_5 leftpadding_5\">操作</td>"+
                     "<td class=\"leftpadding_5\">日志内容</td>"+
                     "</tr>";
                }
                for(var i = 0;i < acMessageLogList.length;i++){
                    present += "<tr>"+"<td class=\"rightpadding_5\">"+
                    timeMaps[acMessageLogList[i].logTime].substring(0, timeMaps[acMessageLogList[i].logTime].length - 3) + 
                    "</td>";
                    present += "</td><td class=\"rightpadding_5 leftpadding_5\">"+
                    acMessageLogList[i].operateDrpt + "</td>"+"<td class=\"leftpadding_5\"><span title='"+acMessageLogList[i].logContent+"'>"+
                    acMessageLogList[i].logContent+"</span></td></tr>";
                }
                
                if(flag == ""){
                    present += "</table><div class='divpage' id='searchmore'>";
                }
                
                var newtime;
                if(order == "0") {
                	newtime = timeMaps[acMessageLogList[i-1].logTime + 1]
                	if(newtime < endtime || endtime == ""){
                    	pageHtml += "<a class='pagenum' href='javascript:void(0)' "+
                        "onclick=\"searchLog('"+newtime+"','"+endtime+"','"+logContent+"','0')\">更多</a>";
                    }else{
                    	pageHtml += "无更多结果";
                    }
                } else {
                	newtime = timeMaps[acMessageLogList[i-1].logTime - 1]
                	if(newtime > starttime || starttime == "") {
                		pageHtml += "<a class='pagenum' href='javascript:void(0)' "+
                        "onclick=\"searchLog('"+starttime+"','"+newtime+"','"+logContent+"','1')\">更多</a>";
                	} else {
                		pageHtml += "无更多结果";
                	}
                }
                
                    
                if(flag == ""){
                	present += pageHtml;
                    present += "</div>";
                    $("#query").html(present);
                }else{
                	$("#querytable").append(present);
                    $("#searchmore").html(pageHtml);
                }
                $("#querytable tr:first").removeClass("tabletitle");
                getStyle();
            };
            hideLoading();
        }
    });
};

function checkEmail(){
    var email = $("#email").val();
    if(email != "" && !isEmail(email)){
        $("#error").html("用户邮箱输入的格式不正确");
    }else{
        $("#error").html("");
    }
}

function checkVmuuid(){
    var vmuuid = $("#vmuuid").val();
    if(vmuuid != "" && !isString(vmuuid)){
        $("#error").html("虚拟机/硬盘标识输入的格式不正确");
    }else{
        $("#error").html("");
    }
}

function checkUser(){
	var userId = $("#user").val();
	if(userId != "" && !isDigit(userId)){
		$("#error1").html("用户ID输入的格式不正确");
	}else{
		$("#error1").html("");
	}
}

function checkTransaction(){
	var transactionId = $("#transaction").val();
	if(transactionId != "" && !isString(transactionId)){
		$("#error1").html("事务ID输入的格式不正确");
    }else{
        $("#error1").html("");
    }
}

function checkIP(){
	var ip = $("#ip").val();
	if(ip != "" && !isIP(ip)){
        $("#error1").html("IP地址输入的格式不正确");
    }else{
        $("#error1").html("");
    }
}

function checkOperation(){
	
}

function checkSource(){
	var source = $("#source").val();
	if(source != "" && !isDotString(source)){
        $("#error2").html("操作源输入的格式不正确");
    }else{
        $("#error2").html("");
    }
}

function checkLog(){
}

function isIP(ipAddr){
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
    if(ipAddr.match(reg) || isIPv6(ipAddr)){
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
    if(str.length == 0 || str.length > 4) {  
        return false;  
    }  
    str = str.toLowerCase();  
    var ch;  
    for(var i=0; i< str.length; i++) {  
        ch = str.charAt(i);  
        if(!(ch >= '0' && ch <= '9') && !(ch >= 'a' && ch <= 'f')) {  
            return false;  
        }  
    }  
    return true;  
}

function isDigit(str){
	var reg = /^[0-9]+$/;
    if(str.match(reg)){
        return true;
    } else {
        return false;
    }
}

function isString(str){
    var reg = /^[A-Za-z0-9]+$/;
    if(str.match(reg)){
        return true;
    } else {
        return false;
    }
}

function isDotString(str){
    var reg = /^[A-Za-z0-9\.]+$/;
    if(str.match(reg)){
        return true;
    } else {
        return false;
    }
}

function isLineString(str){
    var reg = /^[_A-Za-z0-9]+$/;
    if(str.match(reg)){
        return true;
    } else {
        return false;
    }
}

//判断是否是email
function isEmail(str){
    var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    return reg.test(str);
}

</script>
</body>
</html>
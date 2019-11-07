<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作日志 - 云海IaaS</title>
<s:include value="/template/_head.jsp" />
<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>css/jquery-ui.css" />
<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>css/jquery-ui-timepicker-addon.css" />
</head>
<body>
<div id="container">
<s:include value="/template/_banner.jsp?menu=operation" />
<div id="inner">
	<s:include value="/runtime/_left_admin.jsp?menu=adin_log" />
    <div class="right"> 
		<div class="divline bottommargin_20">操作日志</div>
		<div class="middleveralign contentline">
			<span>起始时间<input type="text" name="rest_example_4_start" id="rest_example_4_start" value="" class="squareinput leftmargin_10 rightmargin_20" style="width:175px"/></span>
            <span class="leftmargin_10">终止时间<input type="text" name="rest_example_4_end" id="rest_example_4_end" value="" class="squareinput leftmargin_10 rightmargin_20" style="width:175px" /></span>
		</div>
		<div class="form-group">
			<label class="col-md-2 control-label">查找顺序</label>
			<div class="col-md-10">
				<label class="radio-inline">
					<input type="radio" name="timeasc" value="yes"/>顺序
				</label>
				<label class="radio-inline">
					<input type="radio" name="timeasc" checked="true" value="no">倒序
				</label>
			</div>
		</div>
			<div class="topmargin_10 bottommargin_10">
			日志级别<select class="selectbox leftmargin_10 rightmargin_20" id="level" style="height:25px">
	        <option value="">--全部--</option>
	        <option value="info">INFO</option>
	        <option value="debug">DEBUG</option>
	        <option value="warn">WARN</option>
	        <option value="error">ERROR</option>
	        </select>
	        <div class="hidden" id="more">
	            <table class="border:0">
					<tr height="38px">
						<td colspan="6">
							<span>用户邮箱<input type="text" class="squareinput leftmargin_5 rightmargin_20" style="width:244px;" id="email" onblur="checkEmail()"/></span>
							<span id="error" class="redletter"></span>
						</td>
						<td colspan="6">
							<span>用户ID<input type="text" class="squareinput leftmargin_5 rightmargin_20" id="user" onblur="checkUser()"/></span>
						</td>
					</tr>
					<tr height="38px">
						<td colspan="6">
							<span>设&nbsp;&nbsp;&nbsp;&nbsp;备<input type="text" class="squareinput leftmargin_5 rightmargin_20" style="width:256px;" id="vm"/></span>
						</td>
						<td colspan="6">
							<span>设备ID<input type="text" class="squareinput leftmargin_5 rightmargin_20" style="width:256px;" id="vmuuid"/></span>
						</td>
					</tr>
					<tr height="38px">
						<td colspan="6">
							<span>服务提供商<input type="text" class="squareinputlt leftmargin_5 rightmargin_20" id="provider" onblur="checkSource()"/></span>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<span>操作类型<input type="text" class="squareinput leftmargin_5 rightmargin_20" id="operateType"/></span>
						</td>
						<td colspan="6">
							<span>操作结果<input type="text" class="squareinput leftmargin_5 rightmargin_20" id="result" onblur="checkLog()"/></span>
							<span id="error1" class="redletter"></span>
						</td>
					</tr>
	            </table>
	        </div>
	        <span id="searchbutton">
	        <button class="button" onclick="submitAll()">查询</button>
            <a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="showMore(this)">高级</a>
            <a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="cancelSearch()">取消</a>
            </span>
        </div>
		<div class="dottedline"></div>
		<div id="query"></div>
	</div><!--right-->
</div>
<s:include value="/template/_footer.jsp" />
</div><!--#container-->
<s:include value="/template/_common_js.jsp" />
<script type="text/javascript" src="<%=Constants.FRONT_URL%>js/public.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>js/plugin/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>js/plugin/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>js/plugin/jquery-ui-sliderAccess.js"></script>
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
    $("input[name='timeasc']").click(function(event) {
        that = $(this);
        $("input[name='timeasc']").prop("checked", false);
        that.prop("checked", true);
    });
    
    //当从用户管理界面跳至本页面时使用
    var query = window.location.href;
    query = query.substring(query.indexOf("?")+1);
    var pairs = query.split("&");
    for(var i=0; i<pairs.length; i++) {
    	var paramName = pairs[i].substring(0,pairs[i].indexOf("="));
    	var paramValue = pairs[i].substring(pairs[i].indexOf("=")+1);
    	if(paramName == "type" && paramValue == "all") {
    		searchLog("", "", "", "", "", "", "", "", "");
    	}
    }
});

function showMore(obj){
	$("#more").toggleClass("hidden");
	if($("#more").hasClass("hidden")){
		obj.innerHTML = "高级";
		$("#searchbutton").css("margin-left", "0px");
	}else{
		$("#searchbutton").css("margin-left", "550px");
		obj.innerHTML = "收起";
	}
}

function cancelSearch(){
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
}

function submitAll(){
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
	
    if(starttime == "" && endtime == "" && level == "" &&
            userId == "" && operateType == "" &&
            provider == "" && email == "" && vm == "" && email == ""
        	&& result == ""){
        if(!confirm("查询全部日志？")){
            return;
        }
    }else{
    	if(email != "" && !isEmail(email)){
            $("#error").html("用户邮箱输入的格式不正确");
        }else{
            $("#error").html("");
        }
        if(vmuuid != "" && !isString(vmuuid)){
            $("#error").html("虚拟机/硬盘标识输入的格式不正确");
        }else{
            $("#error").html("");
        }
        if(userId != "" && !isDigit(userId)){
            $("#error1").html("用户ID输入的格式不正确");
            return;
        }else{
            $("#error1").html("");
        }
        
        if(starttime != ""){
        	starttime += " 000";
        }
        if(endtime != ""){
        	endtime += " 000";
        }
    }
    
    //alert(starttime);
    //alert(endtime);
    
	$("#query").html("");
	searchLog(starttime, endtime, level, userId, vm, email, vmuuid, result, provider, operateType, timeasc);
}

function searchLog(starttime, endtime, level, userId, vm, email, vmuuid, result, provider, operateType, timeasc){
	showLoading();
    $.ajax({
        type:"post", 
        url:"runtime/searchOperateLog",
        data:{
        	startTime: starttime, endTime: endtime,
        	level: level, userId: userId, vm: vm, email:email,
            vmUuid: vmuuid, result: result, provider: provider,
			operateType: operateType, timeasc: timeasc
        },
        success:function(data){
        	var acOperateLogList = data.acOperateLogList;
        	if(data.isEmail == 1){
                if($("#query").html() == ""){
                    $("#query").html("<h3 class='centeralign'>无更多结果</h3>");
                }else{
                    $("#query #searchmore").html("<div class='divpage'>无更多结果</div>");
                }
                fillTipBox("error","用户邮箱输入有误！");
            }else if(acOperateLogList == null || acOperateLogList == "" ||
                acOperateLogList.length == 0){
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
						 //"<td width=\"60px\"></td>"+
						 "<td width=\"130px\" class=\"rightpadding_5\">时间</td>"+
						 "<td width=\"60px\"  class=\"rightpadding_5 leftpadding_5\">日志级别</td>"+
						 "<td width=\"130px\" class=\"rightpadding_5 leftpadding_5\">用户ID</td>"+
						 "<td width=\"115px\" class=\"rightpadding_5 leftpadding_5\">服务提供商</td>"+
						 "<td width=\"115px\" class=\"rightpadding_5 leftpadding_5\">设备</td>"+
						 "<td width=\"115px\" class=\"rightpadding_5 leftpadding_5\">设备ID</td>"+
						 "<td width=\"115px\" class=\"rightpadding_5 leftpadding_5\">操作类型</td>"+
						 "<td width=\"115px\" class=\"rightpadding_5 leftpadding_5\">操作结果</td>"+
						 "</tr>";
                }
                for(var i = 0;i < acOperateLogList.length;i++){
                    present += "<tr>"/* +"<td width=\"60px\">"+(st+i+1)+"</td>" */+"<td class=\"rightpadding_5\">"+
                    timeMaps[acOperateLogList[i].createdTime].substring(0, timeMaps[acOperateLogList[i].createdTime].length - 3)
                    + "</td><td class=\"rightpadding_5 leftpadding_5\">";
                    if(acOperateLogList[i].infoType == "ERROR"){
                    	present += "<span class=\"redletter\">"+acOperateLogList[i].infoType + "</span></td>";
                    }else{
                    	present += acOperateLogList[i].infoType + "</td>";
                    }
                    present += "<td class=\"rightpadding_5 leftpadding_5\">"+
                        acOperateLogList[i].userId + "</td><td class=\"rightpadding_5 leftpadding_5\">"+
                        acOperateLogList[i].provider + "</td><td class=\"rightpadding_5 leftpadding_5\">" +
                    	acOperateLogList[i].device + "<td class=\"rightpadding_5 leftpadding_5\">"+
                        acOperateLogList[i].deviceId + "<td class=\"rightpadding_5 leftpadding_5\">"+
                        acOperateLogList[i].operateType + "<td class=\"rightpadding_5 leftpadding_5\">"+
                        acOperateLogList[i].result+
                    	"</td></tr>";
                }
                
                if(flag == ""){
                    present += "</table><div class='divpage' id='searchmore'>";
                }

                var newtime = timeMaps[acOperateLogList[i-1].createdTime + 1];
                if (timeasc=="yes") {
                    if(newtime < endtime || endtime == ""){
                        pageHtml += "<a class='pagenum' href='javascript:void(0)' "+
                            "onclick=\"searchLog('"+newtime+"','"+endtime+"','"+
                            level+"','"+userId+"','"+ vm + "','" + email + "','" + vmuuid +
                            "','"+ result + "','" + provider + "')\">更多</a>";
                    }else{
                        pageHtml += "无更多结果";
                    }
				} else {
                    if(newtime < starttime || starttime == ""){
                        pageHtml += "<a class='pagenum' href='javascript:void(0)' "+
                            "onclick=\"searchLog('"+starttime+"','"+newtime+"','"+
                            level+"','"+userId+"','"+ vm + "','" + email + "','" + vmuuid +
                            "','"+ result + "','" + provider + "')\">更多</a>";
                    }else{
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
                $(".webwidget_vertical_menu").webwidget_vertical_menu();
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

function checkSource(){
	var source = $("#source").val();
	if(source != "" && !isDotString(source)){
        $("#error2").html("操作源输入的格式不正确");
    }else{
        $("#error2").html("");
    }
}

function checkLog(){
	//var logContent = $("#log").val();
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
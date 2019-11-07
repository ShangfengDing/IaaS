<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>告警设置 - 云海IaaS</title>
<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">
<s:include value="/template/_banner.jsp?menu=runtime" />
<div id="inner">
	<s:include value="/runtime/_left.jsp?menu=manage" />
    <div class="right"> 
		<div class="divline bottommargin_20">告警设置</div>
		<input type="hidden" id="emailOpen" value="<s:property value="acMailConf.lolEmail"/>"/>
		<div class="contentline">
			<div class="dottedline">
			     <h3>开启邮件功能</h3>
			     <table class="formtable">
                 <tr height="35px">
                 <td>开启邮件功能</td><td>
			     <select id="lolEmail" class="selectboxsmall topmargin_0" onchange="emailchange()">
			     <option value="false">否</option>
			     <option value="true">是</option>
			     </select></td>
			     </tr>
                 </table>
			</div>
			<div class="dottedline">
                 <h3>发送方邮箱设置</h3>
                 <table class="formtable">
                 <tr height="35px">
                 <td>服务器</td><td style="width:330px;"><input class="squareinput" id="emailHost" value="<s:property value="acMailConf.emailHost"/>" onblur="checkemailHost()"/></td>
                 <td class="leftalign topveralign"><span id="error_emailHost" class="redletter"></span></td>
                 </tr><tr height="35px">
                 <td>发件人</td><td style="width:330px;"><input class="squareinput" id="emailFrom" value="<s:property value="acMailConf.emailFrom"/>" onblur="checkemailFrom()"/></td>
                 <td class="leftalign topveralign"><span id="error_emailFrom" class="redletter"></span></td>
                 </tr><tr height="35px">
                 <td>发件人邮箱密码</td><td style="width:330px;"><input class="squareinput" id="emailPassword" value="<s:property value="acMailConf.emailPassword"/>" onblur="checkemailPassword()"/></td>
                 <td class="leftalign topveralign"><span id="error_emailPassword" class="redletter"></span></td>
                 </tr><tr height="35px">
                 <td>邮件主题</td><td style="width:330px;"><input class="squareinput" id="emailSubject" value="<s:property value="acMailConf.emailSubject"/>" onblur="checkemailSubject()"/></td>
                 <td class="leftalign topveralign"><span id="error_emailSubject" class="redletter"></span></td>
                 </tr>
                 </table>
            </div>
            <div class="dottedline">
                 <h3>模块负责人设置</h3>
                 <table class="formtable" id="moduleInCharge">
                 <s:iterator id="module" value="acMailConf.moduleInCharge">
                 <tr height="35px">
                 <td><s:property value="key"/></td><td style="width:330px;"><input id="module_<s:property value="key"/>" class="squareinput" value="<s:property value="value"/>" onblur="checkemaillist('<s:property value="key"/>')"/></td>
                 <td class="leftalign topveralign"><span id="error_<s:property value="key"/>" class="redletter"></span></td>
                 </tr>
                 </s:iterator>
                 </table>
            </div>
            <div class="topmargin_10 leftmargin_60">
                <div class="leftmargin_60">
                 <input type="button" value="保存设置" class="button leftmargin_10" onclick="submitAll()" />
                 <a href="alert/mailconf" class="blueletter leftmargin_10">取消</a>
                </div>
            </div>
		</div>
	</div><!--right-->
</div>
<s:include value="/template/_footer.jsp" />
</div><!--#container-->
<s:include value="/template/_common_js.jsp" />
<script>
$(document).ready(function(){
	var emailopen = $("#emailOpen").val();
	$("#lolEmail").val(emailopen);
	if(emailopen == "false"){
		$("#emailHost").attr("disabled","disabled");
		$("#emailFrom").attr("disabled","disabled");
		$("#emailPassword").attr("disabled","disabled");
		$("#emailSubject").attr("disabled","disabled");
	}
});

function emailchange(){
	var lol = $("#lolEmail").val();
	if(lol == "false"){
        $("#emailHost").attr("disabled","disabled");
        $("#emailFrom").attr("disabled","disabled");
        $("#emailPassword").attr("disabled","disabled");
        $("#emailSubject").attr("disabled","disabled");
        $("#error_emailHost").html("");
        $("#error_emailFrom").html("");
        $("#error_emailPassword").html("");
        $("#error_emailSubject").html("");
    }else{
    	$("#emailHost").removeAttr("disabled");
        $("#emailFrom").removeAttr("disabled");
        $("#emailPassword").removeAttr("disabled");
        $("#emailSubject").removeAttr("disabled");
        checkemailHost();
        checkemailFrom();
        checkemailPassword();
        checkemailSubject();
    }
}

function submitAll(){
	var lolEmail = $("#lolEmail").val();
	var emailHost = "";
	var emailFrom = "";
	var emailPassword = "";
	var emailSubject = "";
    if(lolEmail == "true"){
        emailHost = $("#emailHost").val();
        emailFrom = $("#emailFrom").val();
        emailPassword = $("#emailPassword").val();
        emailSubject = $("#emailSubject").val();
        
        if(emailHost == ""){
            $("#error_emailHost").html("输入不得为空");
            return;
        }else{
            $("#error_emailHost").html("");
        }
        if(emailFrom == ""){
            $("#error_emailFrom").html("输入不得为空");
            return;
        }else if(!isEmail(emailFrom)){
            $("#error_emailFrom").html("输入的格式不正确");
            return;
        }else{
            $("#error_emailFrom").html("");
        }
        if(emailPassword == ""){
            $("#error_emailPassword").html("输入不得为空");
            return;
        }else{
            $("#error_emailPassword").html("");
        }
        if(emailSubject == ""){
            $("#error_emailSubject").html("输入不得为空");
            return;
        }else{
            $("#error_emailSubject").html("");
        }
    }else{
    	$("#error_emailHost").html("");
    	$("#error_emailFrom").html("");
    	$("#error_emailPassword").html("");
    	$("#error_emailSubject").html("");
    }
    
    var moduleKey = "";
    var moduleValue = "";
    var flag = true;
    $("#moduleInCharge tr").each(function(i){
    	var key = "";
    	key = $(this).children().eq(0).html();
    	moduleKey += key + "-";
    	var value = "";
    	value = $("#module_"+key).val();
    	if(value != "" && !isEmailList(value)){
    		$("error_"+key).html("输入的格式不正确");
    		flag = false;
    		return;
    	}
        moduleValue += value +"-";
    });
    
    if(flag == false){
    	return;
    }
    if(moduleKey != ""){
    	moduleKey = moduleKey.substring(0, moduleKey.length - 1);
    }
    if(moduleValue != ""){
    	moduleValue = moduleValue.substring(0, moduleValue.length - 1);
    }
    
    showLoading();
    $.ajax({
        type:"post", 
        url:"runtime/setmailconf", 
        data:{
        	lolEmail: lolEmail,
            emailHost: emailHost,
            emailFrom: emailFrom,
            emailPassword: emailPassword,
            emailSubject: emailSubject,
            moduleInChargeKey: moduleKey,
            moduleInChargeValue: moduleValue
        },
        success:function(data){
        	hideLoading();
        	if(data.mailConf == null){
        		fillTipBox("error","告警设置失败");
        	}else{
        		fillTipBox("success","告警设置成功");
        	}
        	//location.reload();
        }
    });
}

function checkemaillist(id){
	var value = $("#module_"+id).val();
	if( value != "" && !isEmailList(value)){
		$("#error_"+id).html("输入的格式不正确");
	}else{
		$("#error_"+id).html("");
	}
}

function  checkemailHost(){
	var lolEmail = $("#lolEmail").val();
    if(lolEmail == "true"){
	    var emailHost = $("#emailHost").val();
	    if(emailHost == ""){
	    	$("#error_emailHost").html("输入不得为空");
	    }else{
	    	$("#error_emailHost").html("");
	    }
    }
}

function  checkemailFrom(){
    var lolEmail = $("#lolEmail").val();
    if(lolEmail == "true"){
        var emailFrom = $("#emailFrom").val();
        if(emailFrom == ""){
            $("#error_emailFrom").html("输入不得为空");
        }else if(!isEmail(emailFrom)){
            $("#error_emailFrom").html("输入的格式不正确");
        }else{
            $("#error_emailFrom").html("");
        }
    }
}

function  checkemailPassword(){
    var lolEmail = $("#lolEmail").val();
    if(lolEmail == "true"){
        var emailPassword = $("#emailPassword").val();
        if(emailPassword == ""){
            $("#error_emailPassword").html("输入不得为空");
        }else{
            $("#error_emailPassword").html("");
        }
    }
}

function  checkemailSubject(){
    var lolEmail = $("#lolEmail").val();
    if(lolEmail == "true"){
        var emailSubject = $("#emailSubject").val();
        if(emailSubject == ""){
            $("#error_emailSubject").html("输入不得为空");
        }else{
            $("#error_emailSubject").html("");
        }
    }
}
    
function isEmailList(str){
	var array= new Array();
	array = str.split(",");
	for(var i = 0; i < array.length; i++){
		if(!isEmail(array[i])){
			return false;
		}
	}
	return true;
}

//判断是否是email
function isEmail(str){
  var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
  return reg.test(str);
}
</script>
</body>
</html>
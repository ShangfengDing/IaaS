<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" 
        + request.getServerPort() + request.getContextPath() %>/" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>交易记录 - 云海IaaS</title>
	<s:include value="/template/_head.jsp" />
	<link rel="stylesheet" href="<%=Constants.FRONT_URL%>css/ui.daterangepicker.css" type="text/css" />
    <link rel="stylesheet" href="<%=Constants.FRONT_URL%>css/jquery-ui-1.7.1.custom.css" type="text/css" title="ui-theme" />
</head>
<body>
    <div id="container">
        <s:include value="/template/_banner.jsp?menu=price" />
        <div id="inner">
            <s:include value="/price/_left.jsp?menu=income" />
            <div class="right">
            <div class="divline bottommargin_20">交易记录</div>
            <div class="contentline">
	            <div class="dottedline bottompadding_5">
	                <table><tr>
	                    <td width="70px">用户邮箱</td>
	                    <td class="padding5" width="260px">
	                        <input type="text" class="squareinput" style="width:197px" name="email" id="email" onblur="checkEmail()"/>
	                    </td>
	                    <td><span class="redletter" id="error" style="margin-left:-40px;"></span></td>
	                </tr><tr>
	                    <td width="70px">交易时间</td>
	                    <td class="toppadding_5 leftpadding_5 rightpadding_5">
	                        <input type="text" class="datepicker" name="ptime" id="ptime" />
	                    </td>
	                    <td></td>
		            </tr><tr>
	                    <td width="70px">交易类型</td>
	                    <td class="leftpadding_5 rightpadding_5" id="radios">
	                        <input type="radio" name="billingType" value="out" id="out">
	                        <label for="out" class="rightmargin_10">支出</label>
	                        <input type="radio" name="billingType" value="in" id="in">
	                        <label for="in" class="rightmargin_10">收入</label>
	                        <input type="radio" name="billingType" value="recharge" id="recharge">
	                        <label for="recharge" class="rightmargin_10">充值</label>
	                        <input type="radio" name="billingType" value="all" id="ball">
	                        <label for="ball" class="rightmargin_10">全部</label>
	                    </td>
	                    <td class="leftpadding_5 rightpadding_5">
	                       <input type="button" value="确定" class="button" onclick="submitSearchBilling(1)" />
	                       <a class="blueletter" href="javascript:void(0)" onclick="cancelSearch();">取消</a>
	                    </td>
	                </tr></table>
	            </div>
	            <div id="billingtable"></div>
            </div>
            </div><!-- right -->
	        </div>
        <s:include value="/template/_footer.jsp"></s:include>
    </div>
    <s:include value="/template/_common_js.jsp"></s:include>
<script>
$(document).ready(function(){
    $(".datepicker").each(function(i){
        $(this).daterangepicker({
            arrows:true,
            dateFormat: 'yy-mm-dd'
        });
    });
    $("#email").keydown(function (event) {
        if (event.which == "13" || event.keyCode == "13" || 
                window.event.which == "13" || window.event.keyCode == "13") {
        	submitSearchBilling(1);//回车键，用.ajax提交表单
        }
    });
    $("#ptime").keydown(function (event) {
        if (event.which == "13" || event.keyCode == "13" || 
                window.event.which == "13" || window.event.keyCode == "13") {
        	submitSearchBilling(1);//回车键，用.ajax提交表单
        }
    });
    
    //当从用户管理界面根据虚拟机数跳至本页面时使用
    var query = window.location.href;
    query = query.substring(query.indexOf("?")+1);
    var pairs = query.split("&");
    for(var i=0; i<pairs.length; i++) {
    	var paramName = pairs[i].substring(0,pairs[i].indexOf("="));
    	var paramValue = pairs[i].substring(pairs[i].indexOf("=")+1);
    	if(paramName == "email") {
    		$("#email").val(paramValue);
    		submitSearchBilling(1);
    	}
    }
});

function cancelSearch(){
	$("input[name=email]").val("");
    $("input[name=ptime]").val("");
    $("input[name=billingType]").removeAttr("checked");
    $("#billingtable").html("");
}

function checkEmail(){
    var email = $("#email").val();
    if(email == ""){
    	$("#error").html("请输入用户邮箱");
    }else if(!isEmail(email)){
        $("#error").html("用户邮箱输入的格式不正确");
    }else{
        $("#error").html("");
    }
}

//判断是否是email
function isEmail(str){
    var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    return reg.test(str);
}

function submitSearchBilling(page){
	var email = $("input[name=email]").val();
    var ptime = $("input[name=ptime]").val();
    var billingType = "";
    var ptype = "";
    var startdate = "all";
    var enddate = "all";
    
    if(email == ""){
        $("#error").html("请输入用户邮箱");
        return;
    }else if(!isEmail(email)){
        $("#error").html("用户邮箱输入的格式不正确");
        return;
    }else{
        $("#error").html("");
    }
    
    if(ptime != ""){
        var date = new Array();
        date = ptime.split(" - ");
        if(date.length == 1){
            startdate = date[0] + " 00:00:00";
            enddate = date[0] + " 23:59:59";
        }else if(date.length == 2){
            if(date[0] > date[1]){
                alert("输入的日期有误！");
                return;
            }
            startdate = date[0] + " 00:00:00";
            enddate = date[1] + " 23:59:59";
        }
    }
    
   	
    if((email == null || email == "") && (ptime == null || ptime == "") && 
    		(billingType == null || billingType == "")){
        if(!confirm("查询全部记录？")){
            return;
        }
    }
    
    billingType = "all";
    if($("input[name=billingType]:checked").val() != undefined && $("input[name=billingType]:checked").val() != null){
        billingType = $("input[name=billingType]:checked").val();
    }
    ptype = "vm";
    
    if(billingType == "recharge"){
        ptype = "recharge";
        billingType = "recharge";
    }
    if(billingType == "all") {
    	ptype = "vm-recharge-reset";
    }
    
    searchBilling(page, email, billingType, startdate, enddate, ptype);
}

function cleanbalance(uid) {
	showLoading();
	$.ajax({
        type:"post", 
        url:"price/cleanbalance", 
        data:{
        	uid: uid
        },
        success:function(data){
        	if(data.result == "success") {
        		$("#amount1").html(0.00);
                hideLoading();
                fillTipBox("success","余额清空成功");
        	} else {
        		hideLoading();
        		fillTipBox("error","操作失败");
        	}
        }
	});
}

function searchBilling(page, email, billingType, startdate, enddate, ptype){
    $.ajax({
        type:"post", 
        url:"price/billinghistory", 
        data:{
        	email: email,
            begintime: startdate,
            endtime: enddate,
            billing_type: billingType,
            page: page,
            ptype: ptype
        },
        success:function(data){
        	if(data.isEmail == 1){
        		$("#billingtable").html("<h3 class='centeralign'>无搜索结果</h3>");
                fillTipBox("error","用户邮箱输入有误！");
            }else if(data.billingHistories == null || data.billingHistories.length == 0){//data.tmp
                var html1 = "<p class=\"strong toppadding_5\">用户余额：<span class=\"midsize\" id=\"amount1\">"+data.balance+"</span> 元"+
                "<a class=\"blueletter normal leftpadding_10\" href=\"price/balance.jsp?uid="+data.uid+"\" rel=\"facebox\" title=\"充值\" size=\"s\">充值</a>" +
                "<a class=\"blueletter normal leftpadding_10\"  href=\"javascript:void(0)\" onclick=\"cleanbalance("+data.uid+")\">余额清零</a></p>";
                
            	$("#billingtable").html(html1 + "<h3 class='centeralign'>无搜索结果</h3>");
            	$("a[rel=facebox]").facebox();
                getStyle();
            }else{
                var billingList = data.billingHistories;
                var amountMap = JSON.parse(JSON.stringify(data.amountMap));
                var st = (data.page - 1) * 10;
                var present = "<p class=\"strong toppadding_5\">用户余额：<span class=\"midsize\" id=\"amount1\">"+data.balance+"</span> 元"+
                "<a class=\"blueletter normal leftpadding_10\" href=\"price/balance.jsp?uid="+data.uid+"\" rel=\"facebox\" title=\"充值\" size=\"s\">充值</a>" +
                "<a class=\"blueletter normal leftpadding_10\"  href=\"javascript:void(0)\" onclick=\"cleanbalance("+data.uid+")\">余额清零</a></p>";
                		
                		;
                present += "<div class='rightfloat strong'>共"+ data.total +"条</div>"+
                    "<table class='datatable topmargin_20'>"+
                    "<tr><td width=\"30px\"></td>"+
                    "<td width='150px' class=\"rightpadding_0\">付款用户</td>"+
                    "<td width='120px' class=\"rightpadding_0\">收款用户</td>"+
                    "<td width='80px' class=\"rightpadding_0\">计费对象</td>"+
                    "<td class=\"rightpadding_0\">扣费原因</td>"+
                    "<td width='60px' class=\"rightpadding_0\">金额</td>"+
                    "<td width=\"60px\">时间</td></tr>";
                for(var i = 0;i < billingList.length;i++){
                    present += "<tr><td width=\"30px\">"+
                    (st+i+1)+"</td><td>"
                    +billingList[i].userEmail + "</td><td>"
                    +billingList[i].recEmail+"</td><td>"
                    +billingList[i].pname+"</td><td>"
                    +billingList[i].pid+"</td><td>"
                    +amountMap[billingList[i].id]+"</td><td>"
                    +billingList[i].time+"</td></tr>" ;
                }
                present += "</table>";
                
                var endPage = data.lastpage;
                var pageHtml = "";
                pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchBilling(1"+",'"+email+"','"+billingType+"','"+startdate+"','"+enddate+"','"+ptype+"');\">首页</a>";     
                if(page == 1){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBilling(1"+",'"+email+"','"+billingType+"','"+startdate+"','"+enddate+"','"+ptype+"');\">&lt;&lt;</a>";
                }
                else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBilling("+(page -1)+",'"+email+"','"+billingType+"','"+startdate+"','"+enddate+"','"+ptype+"');\">&lt;&lt;</a>";
                }
                if(data.lastpage - page >= 5){
                    endPage = page + 5;
                    pageNum = 5;
                }
                var tmpBegin = 1;
                var tmpEnd = endPage;
                if(page - 3 > 0){
                    tmpBegin = page - 2;
                    tmpEnd = endPage - 2;
                }
                else if(page - 2 > 0){
                    tmpBegin = page - 1;
                    tmpEnd = endPage - 1;
                }
                else if(page - 1 > 0){
                    tmpEnd = endPage - 1;
                }
                if(endPage == data.lastpage){
                    tmpEnd = endPage;
                }
                for(var i = tmpBegin;i <= tmpEnd;i++){
                    if(i == page){
                        pageHtml += "<a class='currpage'>"+i+"</a>";
                        continue;
                    }
                    pageHtml += "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBilling("+i+",'"+email+"','"+billingType+"','"+startdate+"','"+enddate+"','"+ptype+"');\">"+i+"</a>";
                }
                if(page + 1 < data.lastpage){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBilling("+(page + 1)+",'"+email+"','"+billingType+"','"+startdate+"','"+enddate+"','"+ptype+"');\">&gt;&gt;</a>";
                }
                else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBilling("+data.lastpage+",'"+email+"','"+billingType+"','"+startdate+"','"+enddate+"','"+ptype+"');\">&gt;&gt;</a>";
                }
            
                pageHtml +=   "<a class='text' id='firstpage' onclick=\"searchBilling("+data.lastpage+",'"+email+"','"+billingType+"','"+startdate+"','"+enddate+"','"+ptype+"');\">尾页</a>";     
                
                present += "<div class='divpage'><span>共"+data.lastpage+"页</span><span>";
                present += pageHtml;
                present += "</span></div>";
                $("#billingtable").html(present);
                $("a[rel=facebox]").facebox();
                getStyle();
            }
        }
    });
}


</script>
</body>
</html>
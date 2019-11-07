<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>云主机管理 - 云海IaaS</title>
  	<s:include value="/template/_head.jsp" />
	<link rel="stylesheet" href="<%=Constants.FRONT_URL%>css/ui.daterangepicker.css" type="text/css" />
    <link rel="stylesheet" href="<%=Constants.FRONT_URL%>css/jquery-ui-1.7.1.custom.css" type="text/css" title="ui-theme" />
</head>
<body>
<div id="container">	
<s:include value="/template/_banner.jsp?menu=sys" />
<div id="inner">
	<s:include value="/system/_left.jsp?menu=vm" />
    <div class="right"> 
		<div class="divline topmargin_10">云主机管理</div>
		<div class="contentline">
			<div class="topmargin_10 bottommargin_10 middleveralign">
	            <table>
                <tr>
                    <td class="rightalign" width="70px">用户邮箱</td>
                    <td width="200px">
                    <input type="text" class="leftmargin_10 squareinputlt" style="width:150px;" name="email" id="email" />
                        <!-- <input type="text" class="datepicker" name="time" id="time" /> -->
                    </td>
                    <td class="rightalign" width="70px">主机名称</td>
                    <td width="200px">
                        <input type="text" class="leftmargin_10 squareinputlt" style="width:150px;" name="name" id="name" />
                    </td>
                    <td class="rightalign" width="70px">主机状态</td>
                    <td width="200px">
                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="status" >
                        <option value="">--全部--</option>
                        <option value="active">运行中</option>
                        <option value="stopped">已关机</option>
                        <option value="building">创建中</option>
                        <option value="deleted">已删除</option>
                        <option value="error">故障</option>
                        <option value="rebuilding">ISO装机中</option>
                        <option value="suspended">已挂起</option>
                    </select>
                    </td>
                    <td><span id="error1" class="redletter leftmargin_10"></span>
                    </td>
                 </tr>
                 <tr height="45px">
                    <td class="rightalign" width="70px">数据中心</td>
                    <td width="200px">
                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="zone" onchange="checkZone()">
	                    <option value="">--全部--</option>
                        <s:iterator id="zone" value="zones">
	                    <option value="<s:property value="#zone.id"/>"><s:property value="#zone.name"/></option>
	                    </s:iterator>
	                    </select>
                    </td>
                    <td class="rightalign" width="70px">集群</td>
                    <td width="200px">
                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="cluster" onchange="checkCluster()">
                        <option value="">--全部--</option>
                        <s:iterator id="aggregate" value="aggregates">
                        <option value="<s:property value="#aggregate.id"/>"><s:property value="#aggregate.name"/></option>
                        </s:iterator>
                    </select>
                    </td>
                    <td class="rightalign" width="70px">节点</td>
                    <td width="200px">
                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="host" >
                        <option value="">--全部--</option>
                        <s:iterator id="host" value="hosts">
                        <option value="<s:property value="#host.id"/>"><s:property value="#host.ip"/></option>
                        </s:iterator>
                    </select>
                    </td>
                    <td>
                    </td>
                 </tr>
                 <tr>
                    <td class="rightalign" width="70px">主机IP</td>
                    <td width="200px">
                    <input type="text" class="leftmargin_10 squareinputlt" style="width:150px;" name="ip" id="ip" onblur="checkIP()" />
                    </td>
                    <td class="rightalign" width="70px">主机标识</td>
                    <td width="200px">
                    <input type="text" class="leftmargin_10 squareinputlt" style="width:150px;" name="uuid" id="uuid" onblur="checkUUID()" />
                    </td>
                    <td class="rightalign" width="70px">创建时间</td>
                    <td class="leftpadding_10">
                    <input type="text" class="datepicker" name="time" id="time" />
                    </td>
                    <td><span id="error2" class="redletter leftmargin_10"></span></td>
                 </tr>
                 <tr height="50px;">
                    <td class="rightalign" width="70px"></td>
                    <td width="200px"></td>
                    <td class="rightalign" width="70px"></td>
                    <td width="200px"></td>
                    <td></td>
                    <td>
                          <input type="button" value="确定" class="button leftmargin_10" onclick="submitAll(1)" />
                          <a href="javascript:void(0)" onclick="cancelSearch()" class="blueletter leftmargin_10">取消</a>
                          <a href="javascript:void(0)" class="blueletter leftmargin_10" onclick="submitAll(0)">导出报表</a>
                    </td>
                    <td></td>
                 </tr>
              </table>
	        <div class="dottedline topmargin_20"></div>
	        <input type="hidden" id="oldclusterId" value="<s:property value="clusterId"/>"/>
	        <input type="hidden" id="oldzoneId" value="<s:property value="zoneId"/>"/>
	        <input type="hidden" id="oldhostId" value="<s:property value="hostId"/>"/>
	        <div id="query"></div>
		</div>
	</div>
</div>
</div>
<s:include value="/template/_footer.jsp"></s:include>
</div>
<%-- <s:include value="/template/_common_js.jsp"></s:include> --%>
<script>
$(document).ready(function(){
   $(".datepicker").each(function(i){
        $(this).daterangepicker({
            arrows:true,
            dateFormat: 'yy-mm-dd'
        });
    });
    
    var oldhostId = $("#oldhostId").val();
    if(oldhostId != ""){
    	$("#host").val(oldhostId);
    }
    var oldzoneId = $("#oldzoneId").val();
    if(oldzoneId > 0){
        $("#zone").val(oldzoneId);
    }
    var oldclusterId = $("#oldclusterId").val();
    if(oldclusterId > 0){
        $("#cluster").val(oldclusterId);
    }
    
    if(oldhostId != "" && oldzoneId > 0 && oldclusterId){
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
    query = query.substring(query.indexOf("?")+1);
    var pairs = query.split("&");
    for(var i=0; i<pairs.length; i++) {
    	var paramName = pairs[i].substring(0,pairs[i].indexOf("="));
    	var paramValue = pairs[i].substring(pairs[i].indexOf("=")+1);
    	if(paramName == "email") {
    		$("#email").val(paramValue);
    		searchVm(1, paramValue, "", "", "", "", "", "", "", "", "");
    	}
    	if(paramName == "cluster") {
    		$("#cluster").val(paramValue);
    		$("#zone").val(1);
    		searchVm(1, "", "", "", 1, paramValue, "", "", "", "", "");
    	}
    }
    
   
});

function cancelSearch(){
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
}

function checkZone(){
	var zoneId = $("#zone").val();
	$.ajax({
		type: "post",
		url: "system/getclusterandhostbyzid",
		data:{
			zoneId: zoneId
		},
		success:function(data){
			$("#host").empty();
			$("#cluster").empty();
			var clusterList = data.aggregates;
			var hostList = data.hosts;
			if(clusterList != null && clusterList != "" && clusterList.length > 0){
				$("#cluster").removeAttr("disabled");
				$("#cluster").append("<option value=\"\">--全部--</option>");
				for(var i = 0; i < clusterList.length; i ++){
	                $("#cluster").append("<option value='"+clusterList[i].id+"'>"+clusterList[i].name+"</option>");
	            }
			}else{
                $("#cluster").attr("disabled", "disabled");
            }
			if(hostList != null && hostList != "" && hostList.length > 0){
				$("#host").removeAttr("disabled");
				$("#host").append("<option value=\"\">--全部--</option>");
				for(var i = 0; i < hostList.length; i ++){
	                $("#host").append("<option value='"+hostList[i].id+"'>"+hostList[i].ip+"</option>");
	            }
			}else{
                $("#host").attr("disabled", "disabled");
            }
		}
	});
}

function checkCluster(){
    var clusterId = $("#cluster").val();
    $.ajax({
        type: "post",
        url: "system/gethostbycid",
        data:{
        	clusterId: clusterId
        },
        success:function(data){
        	$("#host").empty();
        	var hostList = data.hosts;
        	if(hostList != null && hostList != "" && hostList.length > 0){
        		$("#host").removeAttr("disabled");
        		$("#host").append("<option value=\"\">--全部--</option>");
	        	for(var i = 0; i < hostList.length; i ++){
	        		$("#host").append("<option value='"+hostList[i].id+"'>"+hostList[i].ip+"</option>");
	        	}
        	}else{
        		$("#host").attr("disabled", "disabled");
        	}
        }
    });
}

function submitAll(page){
	var email = $("#email").val();
	var name = $("#name").val();
	var status = $("#status").val();
	var zone = $("#zone").val();
	var cluster =  $("#cluster").val();
	var host = $("#host").val();
	var ip = $("#ip").val();
	var time = $("#time").val();
	var starttime = "";
	var endtime = "";
	var uuid = $("#uuid").val();
	
	if(email == "" && name == "" && status == "" && zone == "" && cluster == "" 
			&& host == "" && ip == "" && time =="" && uuid ==""){
		if(page==0 || page=='0'){
			if(!confirm("导出全部主机？")){
	            return;
	        }
		}else{
			if(!confirm("查询全部主机？")){
	            return;
	        }
		}
	}else{
// 		if(email != "" && !isEmail(email)){
// 	        $("#error1").html("用户邮箱输入的格式不正确");
// 	        return;
// 	    }else{
	        $("#error1").html("");
// 	    }

	    if(ip != "" && !isIP(ip)){
	        $("#error2").html("IP地址输入的格式不正确");
	        return;
	    }else{
	        $("#error2").html("");
	    }
	    
	    if(uuid != "" && !isString(uuid)){
	        $("#error2").html("主机标识输入的格式不正确");
	    }else{
	        $("#error2").html("");
	    }
	    
	    if(time != ""){
	    	var date = new Array();
	        date = time.split(" - ");
	        if(date.length == 1){
	            starttime = date[0] + " 00:00:00";
	            endtime = date[0] + " 23:59:59";
	        }else if(date.length == 2){
	            if(date[0] > date[1]){
	                alert("输入的日期有误！");
	                return;
	            }
	            starttime = date[0] + " 00:00:00";
	            endtime = date[1] + " 23:59:59";
	        }else{
	        	alert("输入的日期有误！");
                return;
	        }
	    }
	    
	}
	
	if(page==0 || page=='0'){//以提交表单的形式导出云主机excel表
		var temp = document.createElement("form");        
	    temp.action = "vm/vmexcel";        
	    temp.method = "post";        
	    temp.style.display = "none"; 
	    var PARAMS={page: '1', email: email, name: name, status: status, zoneId: zone, clusterId: cluster,
	            hostId: host, ip: ip, starttime: starttime, endtime: endtime, uuid: uuid };
	    for (var par in PARAMS) {        
	        var opt = document.createElement("textarea");        
	        opt.name = par;        
	        opt.value = PARAMS[par];        
	        temp.appendChild(opt);        
	    }        
	    document.body.appendChild(temp);        
	    temp.submit();        
	    return temp;  
	}else{
		//查询云主机
		$("#query").html("");
    	searchVm(page, email, name, status, zone, cluster, host, ip, starttime, endtime, uuid);
	}
}

function searchVm(page, email, name, status, zone, cluster, host, ip, starttime, endtime, uuid){
    $.ajax({
        type:"post", 
        url:"vm/searchvm", 
        data:{
            page: page,
            email: email,
            name: name,
            status: status,
            zoneId: zone,
            clusterId: cluster,
            hostId: host,
            ip: ip,
            starttime: starttime,
            endtime: endtime,
            uuid: uuid
        },
        success:function(data){
        	if(data.isEmail == 1){
                $("#query").html("<h3 class='centeralign'>无搜索结果</h3>");
                fillTipBox("error","用户邮箱输入有误！");
            }else if(data.total == 0){//data.tmp
                $("#query").html("<h3 class='centeralign'>无搜索结果</h3>");
            }else{
                var vmList = data.servers;
                var emailMap = JSON.parse(JSON.stringify(data.emailMap));
                var timeMap = JSON.parse(JSON.stringify(data.timeMap));
                /* var publicIpMap = JSON.parse(JSON.stringify(data.publicIpMap)); */
                var privateIpMap = JSON.parse(JSON.stringify(data.privateIpMap));
                var zoneIdNameMap = JSON.parse(JSON.stringify(data.zoneIdNameMap));
                var clusterIdNameMap = JSON.parse(JSON.stringify(data.clusterIdNameMap));
                var hostIdNameMap = JSON.parse(JSON.stringify(data.hostIdNameMap));
                var statusMap = JSON.parse(JSON.stringify(data.statusMap));
                //var st = (data.page - 1) * 10;
                var present = "";
                present += "<div class='rightfloat strong'>共"+ data.total +"条</div>"+
                    "<table class='datatable topmargin_20' id=\"querytable\">"+
                    "<tr><td width=\"60px\" >用户ID</td>"+
                    "<td width=\"80px\" class=\"rightpadding_5\">主机名称</td>"+
                    "<td width=\"50px\" class=\"rightpadding_5\">状态</td>"+
                    "<td width=\"100px\" class=\"rightpadding_5\">数据中心</td>"+
                    "<td width=\"55px\" class=\"rightpadding_5\">集群</td>"+
                    "<td width=\"100px\" class=\"rightpadding_5\">节点</td>"+
                   // "<td width=\"100px\" class=\"rightpadding_5\">公网IP</td>"+
                    "<td width=\"100px\" class=\"rightpadding_5\">私网IP</td>"+
                    "<td width=\"70px\" class=\"rightpadding_5\">创建时间</td>"+
                    "<td width=\"60px\" class=\"rightpadding_5\">操作</td></tr>";
                for(var i = 0;i < vmList.length;i++){
                    present += "<tr><td class=\"rightpadding_5\">"+
                    emailMap[vmList[i].tenantId]+"</td><td class=\"rightpadding_5\">"+vmList[i].name + 
                    "</td><td class=\"rightpadding_5\">"+
                    statusMap[vmList[i].status]+"</td><td class=\"rightpadding_5\">"+
                    zoneIdNameMap[vmList[i].availabilityZone]+"</td><td class=\"rightpadding_5\">"+
                    clusterIdNameMap[vmList[i].aggregate]+"</td><td class=\"rightpadding_5\">"+
                    hostIdNameMap[vmList[i].hostId]+"</td>"
                    //+"<td class=\"rightpadding_5\">";
                    //for(var j = 0; j <  publicIpMap[vmList[i].id].length; j++){
                    //    present +=  publicIpMap[vmList[i].id][j].addr;
                    //    if(j !=  publicIpMap[vmList[i].id].length - 1){
                    //        present += "<br/>";
                    //    }
                    //}
                    //present += "</td>";
                    present += "<td class=\"rightpadding_5\">";
                    for(var k = 0; k <  privateIpMap[vmList[i].id].length; k++){
                    	present +=  privateIpMap[vmList[i].id][k].addr;
                    	if(k !=  privateIpMap[vmList[i].id].length - 1){
                    		present += "<br/>";
                    	}
                    }
                    present += "</td><td class=\"rightpadding_5\">"+
                    timeMap[vmList[i].id]+"</td><td class=\"rightpadding_5\">"+
                    "<a class=\"blueletter\" href=\"vm/vmdetail?userId="+
                    vmList[i].tenantId+"&serverId="+vmList[i].id+"&clusterId="+clusterIdNameMap[vmList[i].aggregate].substr(2)+"\">虚机操作</a>"+
                    "<a class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"showDetail(this,'"+vmList[i].tenantId+"','"+vmList[i].id+"')\" target=\"_blank\">查看详情</a>"+
                    "</td></tr>";
                }
                present += "</table>";
                
                var endPage = data.lastpage;
                //var pageNum = data.lastpage - page;
                var pageHtml = "";
                pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchVm(1"+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+cluster+"','"+host+"','"+ip+"','"+starttime+"','"+endtime+"','"+uuid+"')\">首页</a>";     
                
                if(page == 1){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchVm(1"+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+cluster+"','"+host+"','"+ip+"','"+starttime+"','"+endtime+"','"+uuid+"');\">&lt;&lt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchVm("+(page -1)+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+cluster+"','"+host+"','"+ip+"','"+starttime+"','"+endtime+"','"+uuid+"');\">&lt;&lt;</a>";
                }
                if(data.lastpage - page >= 5){
                    endPage = page + 5;
                //    pageNum = 5;
                }
                var tmpBegin = 1;
                var tmpEnd = endPage;
                if(page - 3 > 0){
                    tmpBegin = page - 2;
                    tmpEnd = endPage - 2;
                }else if(page - 2 > 0){
                    tmpBegin = page - 1;
                    tmpEnd = endPage - 1;
                }else if(page - 1 > 0){
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
                    pageHtml += "<a class='pagenum' href='javascript:void(0)' onclick=\"searchVm("+i+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+cluster+"','"+host+"','"+ip+"','"+starttime+"','"+endtime+"','"+uuid+"');\">"+i+"</a>";
                }
                if(page + 1 < data.lastpage){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchVm("+(page + 1)+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+cluster+"','"+host+"','"+ip+"','"+starttime+"','"+endtime+"','"+uuid+"');\">&gt;&gt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchVm("+data.lastpage+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+cluster+"','"+host+"','"+ip+"','"+starttime+"','"+endtime+"','"+uuid+"');\">&gt;&gt;</a>";
                }
            
                pageHtml +=   "<a class='text' id='firstpage' onclick=\"searchVm("+data.lastpage+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+cluster+"','"+host+"','"+ip+"','"+starttime+"','"+endtime+"','"+uuid+"');\">尾页</a>";     
                
                present += "<div class='divpage'><span>共"+data.lastpage+"页</span><span>";
                present += pageHtml;
                present += "</span></div>";
                $("#query").html(present);
                //$("#page").html(pageHtml);
                //$("#page").html("<a class='text' id='endPage'href=''>1</a>");
                //$("a[rel=facebox]").facebox();
                getStyle();
            };
        }
    });
};

function checkEmail(){
    var email = $("#email").val();
    if(email != "" && !isEmail(email)){
        $("#error1").html("用户邮箱输入的格式不正确");
    }else{
        $("#error1").html("");
    }
}

function checkIP(){
    var ip = $("#ip").val();
    if(ip != "" && !isIP(ip)){
        $("#error2").html("IP地址输入的格式不正确");
    }else{
        $("#error2").html("");
    }
}

function checkUUID(){
    var uuid = $("#uuid").val();
    if(uuid != "" && !isString(uuid)){
        $("#error2").html("主机标识输入的格式不正确");
    }else{
        $("#error2").html("");
    }
}

function isIP(ipAddr){
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
    if(ipAddr.match(reg)){
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

//判断是否是email
function isEmail(str){
  var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
  return reg.test(str);
}


//显示云主机详情
function showDetail(obj, userId, vmId){
	$.post("vm/vmbrief", {
		'userId' : userId,
		'serverId' : vmId},
		function(data){
			console.log("userId="+userId+";serverid="+vmId);
			console.log(data);
		$(obj).html("收起");
		$(obj).attr("onclick", "hideDetail(this,'"+userId+"','" + vmId + "')");
		
		var detailTable ="<tr class='yellowbox padding10'>" +
				"<td colspan='9'><table class='formtable'>";
		
			var flavor =//硬件配置
			data.flavor.vcpus + "个CPU," + 
			data.flavor.ram + "G内存," + 
			data.flavor.disk + "G硬盘," + 
			data.server.metadata.maxBandwidth + "M带宽";
			var publicIps = "";//公网ip
			for(var i = 0; i < data.publicIps.length; ++i){
				publicIps = publicIps + data.publicIps[i].addr + ',';
			}
			var privateIps = "";//私网ip
			for(var i = 0; i < data.privateIps.length; ++i){
				privateIps = privateIps + data.privateIps[i].addr + ',';
			}
			var securityGroup = data.server.securityGroup.name;//防火墙规则
			var createdTime = new Date(parseInt(data.server.created)).toLocaleString();//创建时间
            if(data.vmEndtime==null){
                var stopTime = "无";
                var payType = "无";
            } else{
                var stopTime = new Date(parseInt(data.vmEndtime.endTime)).toLocaleString() ;//停止时间
                var payType = data.vmEndtime.payType;//计费方式
            }
			
			detailTable	+= 
			'<tr>' + 
				'<td class="padding5 formlabel">硬件配置：</td><td class="padding5 formcontent">' + flavor + '</td>'+
			'</tr><tr>' +
				'<td class="padding5 formlabel">公网IP：</td><td class="padding5 formcontent">' + publicIps + '</td>'+
			'</tr><tr>' + 
				'<td class="padding5 formlabel">内网IP：</td><td class="padding5 formcontent"> '+ privateIps +'</td>' + 
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
function hideDetail(obj, userId, vmId){
	$(obj).html("查看详情");
	$(obj).attr("onclick", "showDetail(this,'"+userId+"','" + vmId + "')");
	var nextTr = $(obj).parent().parent().next();
	if(nextTr.attr("class").indexOf("yellowbox")>=0){
		nextTr.remove();
	}
}
</script>
</body>
</html>
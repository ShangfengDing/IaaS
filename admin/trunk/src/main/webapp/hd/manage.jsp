<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>云硬盘管理 - 云海IaaS</title>
  	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">	
<s:include value="/template/_banner.jsp?menu=sys" />
<div id="inner">
	<s:include value="/system/_left.jsp?menu=hd" />
    <div class="right"> 
		<div class="divline topmargin_10">云硬盘管理</div>
		<div class="contentline">
			<div class="topmargin_10 bottommargin_10 middleveralign">
	            <table>
                <tr height="45px">
                    <td class="rightalign" width="70px">硬盘名称</td>
                    <td width="200px">
                        <input type="text" class="leftmargin_10 squareinputlt" style="width:150px;" name="name" id="name" />
                    </td>
                    <td class="rightalign" width="70px">硬盘标识</td>
                    <td width="200px">
                    <input type="text" class="leftmargin_10 squareinputlt" style="width:150px;" name="uuid" id="uuid" onblur="checkUUID()" />
                    </td>
                    <td class="rightalign" width="70px">硬盘状态</td>
                    <td width="200px">
                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="status" >
                        <option value="">--全部--</option>
                        <option value="available">正常</option>
                        <option value="creating">创建中</option>
                        <option value="defined">格式化</option>
                        <option value="deleted">已删除</option>
                        <option value="deleting">删除中</option>
                        <option value="error">故障</option>
                        <option value="error_deleting">删除错误</option>
                        <!-- <option value="inuse">正在使用</option> -->
                    </select>
                    </td>
                    <td><span id="error1" class="redletter leftmargin_10"></span>
                    </td>
                 </tr><tr height="45px">
                    <td class="rightalign" width="70px">挂载状态</td>
                    <td width="200px">
                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="attachstatus" >
                        <option value="">--全部--</option>
                        <option value="attached">已挂载</option>
                        <option value="detached">未挂载</option>
                    </select>
                    </td>
                    <td class="rightalign" width="70px">用户邮箱</td>
                    <td width="200px">
                    <input type="text" class="leftmargin_10 squareinputlt" style="width:150px;" name="email" id="email" onblur="checkEmail()" />
                        <!-- <input type="text" class="datepicker" name="time" id="time" /> -->
                    </td>
                    <td class="rightalign" width="70px">虚拟机标识</td>
                    <td width="200px">
                    <input type="text" class="leftmargin_10 squareinputlt" style="width:150px;" name="vmuuid" id="vmuuid" onblur="checkVMUUID()" />
                    </td>
                    <td><span id="error2" class="redletter leftmargin_10"></span>
                    </td>
                 </tr><tr height="45px">
                    <td class="rightalign" width="70px">数据中心</td>
                    <td width="200px">
                    <select class="leftmargin_10 selectbox" style="width:162px;height:24px;" id="zone" onchange="checkZone()">
                        <option value="">--全部--</option>
                        <s:iterator id="zone" value="zones">
                        <option value="<s:property value="#zone.id"/>"><s:property value="#zone.name"/></option>
                        </s:iterator>
                        </select>
                    </td>
                    <td class="rightalign" width="70px"></td>
                    <td width="200px"></td>
                    <td></td>
                    <td>
                          <input type="button" value="确定" class="button leftmargin_10" onclick="submitAll(1)" />
                          <a href="javascript:void(0)" onclick="cancelSearch()" class="blueletter leftmargin_10">取消</a>
                          <a href="javascript:void(0)" onclick="submitAll(0)" class="blueletter leftmargin_10">导出报表</a>
                    </td>
                    <td></td>
                 </tr>
              </table>
	        <div class="dottedline topmargin_20"></div>
	        <div id="query"></div>
		</div>
	</div>
</div>
</div>
<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_common_js.jsp"></s:include>
<script>
$(document).ready(function(){
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
    $("#uuid").keydown(function (event) {
        if (event.which == "13" || event.keyCode == "13" || 
                window.event.which == "13" || window.event.keyCode == "13") {
            submitAll(1);//回车键，用.ajax提交表单
        }
    });
    $("#vmuuid").keydown(function (event) {
        if (event.which == "13" || event.keyCode == "13" || 
                window.event.which == "13" || window.event.keyCode == "13") {
            submitAll(1);//回车键，用.ajax提交表单
        }
    });
    
    //当从用户管理界面根据硬盘数跳至本页面时使用
    var query = window.location.href;
    query = query.substring(query.indexOf("?")+1);
    var pairs = query.split("&");
    for(var i=0; i<pairs.length; i++) {
    	var paramName = pairs[i].substring(0,pairs[i].indexOf("="));
    	var paramValue = pairs[i].substring(pairs[i].indexOf("=")+1);
    	if(paramName == "email") {
    		$("#email").val(paramValue);
    		searchHd(1, paramValue, "", "", "", "", "", "");
    	}
    	if(paramName == "zone"){
    		$("#zone").val(paramValue);
    		searchHd(1, "", "", "", 1, "", "", "");
    	}
    }
});
	
function cancelSearch(){
    $("#query").html("");
    $("#email").val("");
    $("#status").val("");
    $("#zone").val("");
    $("#error1").html("");
    $("#error2").html("");
    $("#uuid").val("");
    $("#vmuuid").val("");
    $("#name").val("");
    $("#attachstatus").val("");
}

function submitAll(page){
	var email = $("#email").val();
	var name = $("#name").val();
	var status = $("#status").val();
	var attachstatus = $("#attachstatus").val();
	var zone = $("#zone").val();
	var uuid = $("#uuid").val();
	var vmuuid = $("#vmuuid").val();
	
	if(email == "" && status == "" && zone == "" && attachstatus == "" 
			&& uuid =="" && vmuuid == "" && name == ""){
		if(page==0 || page=='0'){
			if(!confirm("导出全部硬盘？")){
	            return;
	        }
		}else{
			if(!confirm("查询全部硬盘？")){
	            return;
	        }
		}
	}else{
		if(email != "" && !isEmail(email)){
	        $("#error2").html("用户邮箱输入的格式不正确");
	        return;
	    }else{
	        $("#error2").html("");
	    }
	    
	    if(uuid != "" && !isString(uuid)){
	        $("#error1").html("硬盘标识输入的格式不正确");
	    }else{
	        $("#error1").html("");
	    }
	    
	    if(vmuuid != "" && !isString(vmuuid)){
            $("#error2").html("虚拟机标识输入的格式不正确");
        }else{
            $("#error2").html("");
        }
	}
	if(page==0 || page=='0'){//以提交表单的形式导出云硬盘excel表
		var temp = document.createElement("form");        
	    temp.action = "hd/hdexcel";        
	    temp.method = "post";        
	    temp.style.display = "none"; 
	    var PARAMS={page: '1', email: email, name: name, status: status, zone: zone, attachstatus: attachstatus, uuid: uuid, vmuuid:vmuuid };
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
		$("#query").html("");
		showLoading();
	    searchHd(page, email, name, status, zone, attachstatus, uuid, vmuuid);
	}
}

function searchHd(page, email, name, status, zone, attachstatus, uuid, vmuuid){
    $.ajax({
        type:"post", 
        url:"hd/searchhd", 
        data:{
            page: page,
            email: email,
            status: status,
            zoneId: zone,
            attachstatus: attachstatus,
            uuid: uuid,
            vmuuid: vmuuid,
            name: name
        },
        success:function(data){
        	hideLoading();
        	if(data.isEmail == 1){
                $("#query").html("<h3 class='centeralign'>无搜索结果</h3>");
                fillTipBox("error","用户邮箱输入有误！");
            }else if(data.total == 0 || data.volumes == null || data.volumes == ""){
                $("#query").html("<h3 class='centeralign'>无搜索结果</h3>");
            }else{
                var volumeList = data.volumes;
                var endtimeMap = JSON.parse(JSON.stringify(data.endtimeMap));
                var timeMap = JSON.parse(JSON.stringify(data.timeMap));
                var uidMap = JSON.parse(JSON.stringify(data.uidMap));
                var volumeIdServerNameMap = JSON.parse(JSON.stringify(data.volumeIdServerNameMap));
                var zoneIdNameMap = JSON.parse(JSON.stringify(data.zoneIdNameMap));
                var statusMap = JSON.parse(JSON.stringify(data.statusMap));
                var present = "";
                var operation = "";
                present += "<div class='rightfloat strong'>共"+ data.total +"条</div>"+
                    "<table class='datatable topmargin_20' id=\"querytable\"><tr>"+
                    /* "<td width=\"50px\" class=\"rightpadding_5\">用户ID</td>"+ */
                    "<td width=\"100px\" class=\"rightpadding_5\">硬盘名称</td>"+
                    "<td width=\"60px\" class=\"rightpadding_5\">硬盘状态</td>"+
                    "<td class=\"rightpadding_5\">挂载状态</td>"+
                    "<td width=\"60px\" class=\"rightpadding_5\">容量</td>"+
                    "<td class=\"rightpadding_5\">数据中心</td>"+
                    "<td class=\"rightpadding_5\" width='60px'>创建时间</td>"+
                    "<td width=\"100px\" class=\"rightpadding_5\">操作</td></tr>";
                for(var i = 0;i < volumeList.length;i++){
                    present += "<tr id='tr"+volumeList[i].id+"'><td class=\"rightpadding_5\">"+
                    volumeList[i].displayName+"</td><td class=\"rightpadding_5\">"+
                    statusMap[volumeList[i].status]+"</td><td class=\"rightpadding_5\">";
                    if(volumeIdServerNameMap[volumeList[i].id] == "无"){
                        present += "未挂载";
                        if(volumeList[i].status == "available"){
                        	operation = "<a class=\"blueletter rightmargin_5\""+
                            " href=\"hd/showattachhd?hduuid="+volumeList[i].id+"&uid="+
                            uidMap[volumeList[i].id]+
                            "\" rel=\"facebox\" title=\"挂载硬盘到虚拟机\" size=\"s\">挂载</a>"+
                            "<a class=\"blueletter rightmargin_5\" href=\"javascript:void(0)\""+
                            " onclick=\"hdoperate('delete','"+volumeList[i].id+"','','"+
                            endtimeMap[volumeList[i].id]+"','"+uidMap[volumeList[i].id]+
                            "')\">删除</a>";
                        }else{
                        	operation = "<a class=\"blueletter rightmargin_5\" href=\"javascript:void(0)\""+
                            " onclick=\"hdoperate('delete','"+volumeList[i].id+"','','"+
                            endtimeMap[volumeList[i].id]+"','"+uidMap[volumeList[i].id]+
                            "')\">删除</a>";
                        }
                    }else{
                        present += "挂载到<a target=\"_blank\" class=\"blueletter\" href=\"vm/vmdetail?serverId="+
                        volumeList[i].attachments.serverId+"\">"+
                        volumeIdServerNameMap[volumeList[i].id]+"</a>";
                        operation = "<a class=\"blueletter rightmargin_5\""+
                        " href=\"javascript:void(0)\" onclick=\""+
                        "hdoperate('detach','"+volumeList[i].id+"','"+
                        volumeList[i].attachments.serverId+"','"+
                        endtimeMap[volumeList[i].id]+"','"+uidMap[volumeList[i].id]+"')\">卸载</a>"/* +
                        "<a class=\"blueletter rightmargin_5\" href=\"javascript:void(0)\" "+
                        "onclick=\"fillTipBox('error','您必须先手动卸载硬盘，才可以进行删除~');\">删除</a>" */;
                    }
                    present += "</td><td class=\"rightpadding_5\">"+volumeList[i].size+
                    "G</td><td class=\"rightpadding_5\">"+zoneIdNameMap[volumeList[i].availabilityZone]+
                    "</td><td class=\"rightpadding_5\">"+timeMap[volumeList[i].id]+
                    "</td><td class=\"rightpadding_5\">"+operation+"</td></tr>";
                }
                present += "</table>";
                
                var endPage = data.lastpage;
                //var pageNum = data.lastpage - page;
                var pageHtml = "";
                pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchHd(1"+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+attachstatus+"','"+uuid+"','"+vmuuid+"')\">首页</a>";     
                
                if(page == 1){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchHd(1"+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+attachstatus+"','"+uuid+"','"+vmuuid+"')\">&lt;&lt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchHd("+(page -1)+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+attachstatus+"','"+uuid+"','"+vmuuid+"')\">&lt;&lt;</a>";
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
                    pageHtml += "<a class='pagenum' href='javascript:void(0)' onclick=\"searchHd("+i+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+attachstatus+"','"+uuid+"','"+vmuuid+"')\">"+i+"</a>";
                }
                if(page + 1 < data.lastpage){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchHd("+(page + 1)+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+attachstatus+"','"+uuid+"','"+vmuuid+"')\">&gt;&gt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchHd("+data.lastpage+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+attachstatus+"','"+uuid+"','"+vmuuid+"')\">&gt;&gt;</a>";
                }
            
                pageHtml +=   "<a class='text' id='firstpage' onclick=\"searchHd("+data.lastpage+",'"+email+"','"+name+"','"+status+"','"+zone+"','"+attachstatus+"','"+uuid+"','"+vmuuid+"');\">尾页</a>";     
                
                present += "<div class='divpage'><span>共"+data.lastpage+"页</span><span>";
                present += pageHtml;
                present += "</span></div>";
                $("#query").html(present);
                //$("#page").html(pageHtml);
                //$("#page").html("<a class='text' id='endPage'href=''>1</a>");
                $("a[rel=facebox]").facebox();
                getStyle();
            }
        }
    });
};

//硬盘卸载和删除操作
function hdoperate(operation, hdUuid, serverId, endTimeId, uid){
    var confirmMsg;
    if(operation == "detach"){
        confirmMsg = "确认进行卸载吗？(注意：该操作将关闭虚拟机！！)";
    } else {
        confirmMsg = "确认删除该硬盘吗？";
    }
    if(confirm(confirmMsg)){
        showLoading();
        $.post("hd/hdoperate",{operation:operation,hdUuid:hdUuid,serverId:serverId,
        	endTimeId:endTimeId, uid:uid},function(data){
            hideLoading();
            if(data.result == true) {
                fillTipBox("success","操作成功！");
                if(operation == "detach"){
                	$("#tr"+hdUuid).children().eq(2).html("未挂载");
                    $("#tr"+hdUuid).children().eq(6).html("<a class=\"blueletter rightmargin_5\""+
                            " href=\"hd/showattachhd?hduuid="+hdUuid+"&uid="+uid+
                            "\" rel=\"facebox\" title=\"挂载硬盘到虚拟机\" size=\"s\">挂载</a>"+
                            "<a class=\""+
                            "blueletter rightmargin_5\" href=\"javascript:void(0)\""+
                            " onclick=\"hdoperate('delete','"+hdUuid+"','','"+
                            endTimeId+"','"+uid+"')\">删除</a>");
                    $("a[rel=facebox]").facebox();
                    getStyle();
                }else{
                	$("#tr"+hdUuid).remove();
                }
            }else{
                fillTipBox("error","操作失败！");
            }
        });
    }
}

function checkEmail(){
    var email = $("#email").val();
    if(email != "" && !isEmail(email)){
        $("#error2").html("用户邮箱输入的格式不正确");
    }else{
        $("#error2").html("");
    }
}

function checkVMUUID(){
    var uuid = $("#vmuuid").val();
    if(uuid != "" && !isString(uuid)){
        $("#error2").html("虚拟机标识输入的格式不正确");
    }else{
        $("#error2").html("");
    }
}

function checkUUID(){
    var uuid = $("#uuid").val();
    if(uuid != "" && !isString(uuid)){
        $("#error1").html("硬盘标识输入的格式不正确");
    }else{
        $("#error1").html("");
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
</script>
</body>
</html>
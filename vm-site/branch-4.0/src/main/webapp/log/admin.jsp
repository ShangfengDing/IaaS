<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理员设置</title>
<s:include value="/template/_head.jsp" />
<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>/css/jquery-ui.css" />
<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>/css/jquery-ui-timepicker-addon.css" />
</head>
<body>
<div id="container">
<s:include value="/template/_pub_banner.jsp?index=v" />
<div id="inner">
	<s:include value="/template/_left.jsp?menu=admin" />
    <div class="right"> 
		<div class="divline bottommargin_20">管理员设置</div>
		<div class="contentline">
			<table>
				<td width="30px" class="rightalign">邮箱</td>
				<td width="200px"><input type="text" style="width:150px;height:24px" class="squareinput leftmargin_10" id="adminemail"/></td>
				<td width="30px" class="rightalign">状态</td>
				<td width="200"><select class="leftmargin_5 selectbox" style="width:162px;height:24px;" id="status" >
	                        <option value="actived">已激活</option>
	                        <option value="new">未处理</option>
	                        <option value="aggreed">已同意</option>
	                        <option value="denied">已拒绝</option>
	                    </select>
				<td>
					<button class="button leftmargin_5" onclick="submitSearch(1)">查询</button>
					<a class="blueletter leftmargin_5" href="javascript:void(0)" onclick="cancelSearch()">取消</a>
				</td>
				<td>
					<a class="button rightfloat" href="log/addAdminToEnterprise.jsp" rel="facebox" size="s" title="邀请加入企业">邀请加入企业</a>
				</td>
			</table>
		</div>
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
	$("#adminemail").keydown(function (event) {
		if (event.which == "13" || event.keyCode == "13" || 
	        window.event.which == "13" || window.event.keyCode == "13") {
	       	submitEmail(1);//回车键，用.ajax提交表单
	    }
	});
	
});

function cancelSearch(){
	$("#query").html("");
	$("#adminemail").val("");
}

function submitSearch(page){
	var adminemail = $("#adminemail").val().trim();
	var status = $("#status").val().trim();
	
	searchAdmin(page, adminemail, status);
}

function removeUser(userId){
	if(confirm("确认移出已此用户吗？")){
		$.ajax({
			type:"post",
			url:"log/removeUser",
			data:{
				userId:userId
			},
			success:function(data){
				if(data.result == "success"){
	                fillTipBox("success","移出成功！");
	                location.reload();
	            } else if(data.result == "owner") {
	            	facebox_close();
	                hideLoading();
	                fillTipBox("error","企业所有者无法移出自己，您可以选择解散此企业！");
	            } else{
	            	facebox_close();
	                hideLoading();
	                fillTipBox("error","移出失败");
	            }
			}
		});
	}
}
function deleteInvitation(id){
	if(confirm("确认撤销对此用户的邀请信息吗？")){
		$.ajax({
			type:"post",
			url:"log/deleteInvitation",
			data:{
				id:id
			},
			success:function(data){
				if(data.result == "success"){
	                fillTipBox("success","撤销成功！");
	                location.reload();
	            } else{
	            	facebox_close();
	                hideLoading();
	                fillTipBox("error","撤销失败");
	            }
			}
		});
	}
}

function searchAdmin(page, adminemail, status){
    $.ajax({
        type:"post", 
        url:"log/queryAdmin", 
        data:{
        	email:adminemail,
        	status:status,
            page: page,
        },
        success:function(data){
            if(data.total == 0 || (data.acUsers == null && data.enterpriseInvitations == null)){//data.tmp
                $("#query").html("<h3 class='centeralign'>无搜索结果</h3>");
            }
            else{
                var acUsers = JSON.parse(JSON.stringify(data.acUsers));
                var enterpriseInvitations = JSON.parse(JSON.stringify(data.enterpriseInvitations));
                var emailByUserIds = JSON.parse(JSON.stringify(data.emailByUserIds));
                var st = (data.page - 1)* 10;
                var present = "";
                present += "<div class='rightfloat strong'>共"+ data.total +"条</div>"+
                    "<table class='datatable topmargin_20' id=\"querytable\">"+
                    "<tr><td width=\"30px\"></td>"+
                    "<td width=\"200px\" class=\"centeralign\">用户邮箱</td>"+
                    "<td width=\"45px\" class=\"centeralign\">状态</td>"+
                    "<td class=\"centeralign\">操作</td></tr>";
                for(var i = 0; i < acUsers.length; i++){
                    present += "<tr id=\"tr"+acUsers[i].id+"\">" +
                    		       "<td>" + (st+i+1) + "</td>" + 
                    		       "<td class=\"centeralign\">" + acUsers[i].userEmail + "</td>" +
                    		       "<td class=\"centeralign\">" + "已激活" + "</td>" +
                    		       "<td class=\"centeralign\">";
                    		       
                    if (acUsers[i].userId == acUsers[i].enterpriseId) {
                    	present  +=	 "<a id=\"mod_group" + acUsers[i].id + "\" class=\"redletter\" >无法移除</a>" + "&nbsp;&nbsp;";
                    } else {
                    	present += "<a id=\"delete" + acUsers[i].userId + "\" class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"removeUser(" + acUsers[i].userId + ");\">移出</a>";
                    }
	                present +=  "</td>"+"</tr>";
                }
                for(var i = 0; i < enterpriseInvitations.length; i++){
                    present += "<tr id=\"tr"+enterpriseInvitations[i].id+"\">" +
                    		   "<td>" + (st+i+1) + "</td>" + 
                    		   "<td class=\"centeralign\">" + emailByUserIds[enterpriseInvitations[i].userId] + "</td>";
                   var status;
                   if (enterpriseInvitations[i].status == "NEW") {
                   		status = "未处理";
                   } else if (enterpriseInvitations[i].status == "AGGREED") {
                    	status = "已同意";
                   } else if (enterpriseInvitations[i].status == "DENIED") {
                    	status = "已拒绝";
                   } else {
                    	status = "已完成";
                   }
                   present += 	"<td class=\"centeralign\">" + status + "</td>";
                   
                   if (enterpriseInvitations[i].status == "NEW")	{
                    	present +=  "<td class=\"centeralign\">" +
                   		          "<a id=\"delete" + enterpriseInvitations[i].id + "\" class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"deleteInvitation(" + enterpriseInvitations[i].id + ");\">撤销</a>" +
                   		          "</td>";
                    } else {
                    	present +=  "<td class=\"centeralign\">" +  "无操作"  + "</td>";
                    }       
	                present += "</tr>";
                }
                
                present += "</table>";
                
                var endPage = data.lastpage;
                var pageNum = data.lastpage - page;
                var pageHtml = "";
                pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchAdmin(1"+",'"+adminemail+"','"+status+"')\">首页</a>";     
                
                if(page == 1){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin(1"+",'"+adminemail+"','"+status+"');\">&lt;&lt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin("+(page -1)+",'"+adminemail+"','"+status+"');\">&lt;&lt;</a>";
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
                    pageHtml += "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin("+i+",'"+adminemail+"','"+status+"');\">"+i+"</a>";
                }
                if(page + 1 < data.lastpage){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin("+(page + 1)+",'"+adminemail+"','"+status+"');\">&gt;&gt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin("+data.lastpage+",'"+adminemail+"','"+status+"');\">&gt;&gt;</a>";
                }
            
                pageHtml +=   "<a class='text' id='firstpage' onclick=\"searchAdmin("+data.lastpage+",'"+adminemail+"','"+status+"');\">尾页</a>";     
                
                present += "<div class='divpage'><span>共"+data.lastpage+"页</span><span>";
                present += pageHtml;
                present += "</span></div>";
                $("#query").html(present);
                $("a[rel=facebox]").facebox();
                getStyle();
            };
        }
    });
};
</script>
</body>
</html>
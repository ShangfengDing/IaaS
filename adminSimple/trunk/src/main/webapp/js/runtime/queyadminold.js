$(document).ready(function(){
	$("#adminname").keydown(function (event) {
		if (event.which == "13" || event.keyCode == "13" || 
	        window.event.which == "13" || window.event.keyCode == "13") {
	       	submitEmail(1);//回车键，用.ajax提交表单
	    }
	});
	
});

function cancelSearch(){
	$("#query").html("");
	$("#adminname").val("");
	$("#adminemail").val("");
}

function submitSearch(page){
	var adminname = $("#adminname").val().trim();
	var adminemail = $("#adminemail").val().trim();
	
	searchAdmin(page, adminname, adminemail);
}

function deleteAdmin(id){
	if(confirm("确认删除该管理员吗？")){
		$.ajax({
			type:"post",
			url:"runtime/deleteAdmin",
			data:{
				id:id
			},
			success:function(data){
				if(data.result == "success"){
	                fillTipBox("success","删除成功");
	                location.reload();
	            } else if(data.result == "deleted") {
	            	facebox_close();
	                hideLoading();
	                fillTipBox("error","删除失败，您已被其它超级管理员删除");
	            } else{
	            	facebox_close();
	                hideLoading();
	                fillTipBox("error","删除失败");
	            }
			}
		});
	}
}

function searchAdmin(page, adminname, adminemail){
    $.ajax({
        type:"post", 
        url:"runtime/adminmanage", 
        data:{
        	name: adminname,
        	email:adminemail,
            page: page-1,
        },
        success:function(data){
            if(data.total == 0 || data.admins == null){//data.tmp
                $("#query").html("<h3 class='centeralign'>无搜索结果</h3>");
            }
            else{
                var admins = JSON.parse(JSON.stringify(data.admins));
                var adminId = data.adminId;
                var st = data.page * 10;
                var present = "";
                present += "<div class='rightfloat strong'>共"+ data.total +"条</div>"+
                    "<table class='datatable topmargin_20' id=\"querytable\">"+
                    "<tr><td width=\"30px\"></td>"+
                    "<td>管理员名称</td>"+
                    "<td width=\"120px\">邮箱</td>"+
                    "<td width=\"100px\">联系方式</td>"+
                    "<td width=\"240px\">操作</td></tr>";
                for(var i = 0; i < admins.length; i++){
                    present += "<tr id=\"tr"+admins[i].id+"\">" +
                    		       "<td>" + (st+i+1) + "</td>" + 
                    		       "<td>" + admins[i].username + "</td>";
                    if (admins[i].email == null) {
                    	present += "<td >无</td>";
                    } else {
                    	present += "<td>" + admins[i].email + "</td>" ;
                    }
                    if (admins[i].mobile == null) {
                    	present += "<td >无</td>";
                    } else {
                    	present += "<td>" + admins[i].mobile + "</td>" ;
                    }
               		
                    present += "<td>" + 
					   			"<a class=\"blueletter\" href=\"runtime/preChangePassword?id=" + admins[i].id + "\"" +
		     					  " rel=\"facebox\" title=\"修改名称和密码\" size=\"s\">修改名称和密码</a>" + "&nbsp;&nbsp;";
                    if (admins[i].roleId <= 0) {
                    	present += "<a id=\"group_detail" + admins[i].roleId + "\" class=\"redletter\">未被分配管理组</a>" + "&nbsp;&nbsp;" ;
                    } else {
                    	present += "<a id=\"group_detail" + admins[i].roleId + "\" class=\"blueletter\" href=\"runtime/showRole?roleid=" + admins[i].roleId + "\"" +
   					  " rel=\"facebox\" title=\"查看所属管理组\">查看所属管理组</a>" + "&nbsp;&nbsp;" ;
                    }
                    
                    if (adminId == admins[i].id) {
                    	present  +=	 "&nbsp;&nbsp;&nbsp;&nbsp;<a id=\"mod_group" + admins[i].id + "\" class=\"redletter\" >无法删除</a>";
                    } else {
                    	present += "&nbsp;&nbsp;&nbsp;&nbsp;<a id=\"delete" + admins[i].id + "\" class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"deleteAdmin(" + admins[i].id + ");\">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                    
                    if (adminId == admins[i].id) {
                    	present  +=	 "<a id=\"mod_group" + admins[i].id + "\" class=\"redletter\" >无法修改所属管理组</a>" + "&nbsp;&nbsp;";
                    	
                    } else {
                    	present  +=	 "<a id=\"mod_group" + admins[i].id + "\" class=\"blueletter\" href=\"runtime/preModRole?id=" + admins[i].id + "\"" +
                    	" rel=\"facebox\" title=\"修改所属管理组\" size=\"s\">修改所属管理组</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                    present  +=	 "&nbsp;&nbsp;&nbsp;&nbsp;<a id=\"mod_group" + admins[i].id + "\" class=\"blueletter\" href=\"runtime/preModAdmin?id=" + admins[i].id + "\"" +
                	" rel=\"facebox\" title=\"修改管理员\" size=\"s\">修改管理员</a>";
                    present += "</td></tr>";
                }
                present += "</table>";
                
                var endPage = data.lastpage;
                var pageNum = data.lastpage - page;
                var pageHtml = "";
                pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchAdmin(1"+",'"+adminname+"','"+adminemail+"')\">首页</a>";     
                
                if(page == 1){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin(1"+",'"+adminname+"','"+adminemail+"');\">&lt;&lt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin("+(page -1)+",'"+adminname+"','"+adminemail+"');\">&lt;&lt;</a>";
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
                    pageHtml += "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin("+i+",'"+adminname+"','"+adminemail+"');\">"+i+"</a>";
                }
                if(page + 1 < data.lastpage){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin("+(page + 1)+",'"+adminname+"','"+adminemail+"');\">&gt;&gt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchAdmin("+data.lastpage+",'"+adminname+"','"+adminemail+"');\">&gt;&gt;</a>";
                }
            
                pageHtml +=   "<a class='text' id='firstpage' onclick=\"searchAdmin("+data.lastpage+",'"+adminname+"','"+adminemail+"');\">尾页</a>";     
                
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

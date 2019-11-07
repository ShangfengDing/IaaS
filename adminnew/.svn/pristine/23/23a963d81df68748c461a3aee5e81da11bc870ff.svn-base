$(document).ready(function(){
	$("#rolename").keydown(function (event) {
		if (event.which == "13" || event.keyCode == "13" || 
	        window.event.which == "13" || window.event.keyCode == "13") {
			submitSearch(1);//回车键，用.ajax提交表单
	    }
	});
	
});

function cancelSearch(){
	$("#query").html("");
	$("#rolename").val("");
}

function submitSearch(page){
	var rolename = $("#rolename").val().trim();
	
	searchRole(page, rolename);
}

function deleteAdminRole(id){
	if(confirm("确认删除该管理组吗？")){
		$.ajax({
			type:"post",
			url:"runtime/deleteAdminRole",
			data:{
				id:id
			},
			success:function(data){
				if(data.result == "success"){
	                fillTipBox("success","删除成功");
	                location.reload();
	            }else if (data.result == "noPrivilege"){
	            	facebox_close();
	                hideLoading();
	                fillTipBox("error","正在使用中，无法删除");
	            } else {
	            	facebox_close();
	                hideLoading();
	                fillTipBox("error","删除失败");
	            }
			}
		});
	}
}

function searchRole(page, rolename){
    $.ajax({
        type:"post", 
        url:"runtime/rolemanage", 
        data:{
        	name: rolename,
            page: page-1,
        },
        success:function(data){
            if(data.total == 0 || data.adminRoles == null){//data.tmp
                $("#query").html("<h3 class='centeralign'>无搜索结果</h3>");
            }
            else{
                var adminRoles = data.adminRoles;
                var roleid = data.roleid;
                var st = data.page * 10;
                var present = "";
                present += "<div class='rightfloat strong'>共"+ data.total +"条</div>"+
                    "<table class='datatable topmargin_20' id=\"querytable\">"+
                    "<tr><td width=\"60px\"></td>"+
                    "<td width=\"170px\">管理组名称</td>"+
                    "<td width=\"200px\">操作</td></tr>";
                for(var i = 0; i < adminRoles.length; i++){
                    present += "<tr id=\"tr"+adminRoles[i].id+"\">" +
                    		       "<td width=\"220px\">" + (st+i+1) + "</td>" +
                    		       "<td width=\"220px\">" + adminRoles[i].rolename + "</td>"; 
                    present += "<td>" + 
                    "<a id=\"role_detail" + adminRoles[i].id + "\" class=\"blueletter\" href=\"runtime/showRole?roleid=" + adminRoles[i].id + "\"" +
                    " rel=\"facebox\" title=\"查看管理组\">查看管理组</a>" + "&nbsp;&nbsp;";
               		if (roleid == adminRoles[i].id) {
               			present +=  "<a  class=\"redletter\" >无法修改管理组</a>" + "&nbsp;&nbsp;";
               			present += 	"<a  class=\"redletter\" >无法删除</a>" +
               			"</td>";
               		} else {
               			present +=  "&nbsp;&nbsp;&nbsp;&nbsp;<a id=\"role_mod" + adminRoles[i].id + "\" class=\"blueletter\" href=\"runtime/modResource?roleid=" + adminRoles[i].id + "\"" +
               			" rel=\"facebox\" title=\"修改管理组\">修改管理组</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
               			present += 	"&nbsp;&nbsp;&nbsp;&nbsp;<a id=\"delete" + adminRoles[i].id + "\" class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"deleteAdminRole(" + adminRoles[i].id + ");\">删除</a>"  + 
               			"</td>";
               		}
                    present += "</tr>";
                    
                    
                }
                present += "</table>";
                
                var endPage = data.lastpage;
                var pageNum = data.lastpage - page;
                var pageHtml = "";
                pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchRole(1"+",'"+rolename+"')\">首页</a>";     
                
                if(page == 1){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchRole(1"+",'"+rolename+"');\">&lt;&lt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchRole("+(page -1)+",'"+rolename+"');\">&lt;&lt;</a>";
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
                    pageHtml += "<a class='pagenum' href='javascript:void(0)' onclick=\"searchRole("+i+",'"+rolename+"');\">"+i+"</a>";
                }
                if(page + 1 < data.lastpage){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchRole("+(page + 1)+",'"+rolename+"');\">&gt;&gt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchRole("+data.lastpage+",'"+rolename+"');\">&gt;&gt;</a>";
                }
            
                pageHtml +=   "<a class='text' id='firstpage' onclick=\"searchAdmin("+data.lastpage+",'"+rolename+"');\">尾页</a>";     
                
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

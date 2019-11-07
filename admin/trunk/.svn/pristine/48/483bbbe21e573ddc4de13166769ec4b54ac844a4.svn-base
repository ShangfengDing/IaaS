$(document).ready(function(){
	$.ajax({
        type:"post", 
        url:"user/getgroups", 
        data:{
        },
        success:function(data){
        	
        	var acGroups = JSON.parse(JSON.stringify(data.acGroups));
        	var present="";
        	present = "<option value=''>--全部--</option>";
        	if(acGroups.length == 0) {
        		$("#group").html(present);
        	} else {
        		for(var i=0; i<acGroups.length; i++) {
        			present += "<option value='" + acGroups[i].id + "'>" + acGroups[i].name + "</option>";
        		}
        		$("#group").html(present);
        	}
        }
	});
	
	$("#email").keydown(function (event) {
		if (event.which == "13" || event.keyCode == "13" || 
	        window.event.which == "13" || window.event.keyCode == "13") {
	       	submitEmail(1);//回车键，用.ajax提交表单
	    }
	});
	
});


function changeStatus(uid) {
	var a = document.getElementById("change_status"+uid);
	
	$.post("user/changeStatus", 
			{	uid:uid, 
				},  
				function(data){
					if(data.result == "success") {
						fillTipBox("success","修改成功");
						if(a.innerHTML == "冻结")
							a.innerHTML = "解冻";
						else 
							a.innerHTML = "冻结";
					} else {
						fillTipBox("error","修改失败");
					}
				}
		);
}

function cancelSearch(){
	$("#query").html("");
	$("#email").val("");
	$("#enterpriseName").val("");
}

function submitEmail(page){
	var enterpriseName = $("#enterpriseName").val().trim();
	var email = $("#email").val().trim();
	var group = $("#group").val();
	
	searchBalance(page, email, group, enterpriseName);
}

function searchAll(page) {
	searchBalance(page, "", "", "");
}

function searchBalance(page, email, group, enterpriseName){
    $.ajax({
        type:"post", 
        url:"user/usermanage", 
        data:{
        	enterpriseName: enterpriseName,
        	group: group,
        	email: email,
            page: page
        },
        success:function(data){
            if(data.total == 0){//data.tmp
                $("#query").html("<h3 class='centeralign'>无搜索结果</h3>");
            }
            else{
                var acUsers = data.acUsers;
                var countMap = JSON.parse(JSON.stringify(data.countMap));
                var countVolumeMap = JSON.parse(JSON.stringify(data.countVolumeMap));
                var balanceMap = JSON.parse(JSON.stringify(data.balanceMap));
                var enterpriseMap = JSON.parse(JSON.stringify(data.enterpriseMap));
                var st = (data.page - 1) * 10;
                var present = "";
                present += "<div class='rightfloat strong'>共"+ data.total +"条</div>"+
                    "<table class='datatable topmargin_20' id=\"querytable\">"+
                    "<tr>"+
                    "<td width=\"160px\">邮箱</td>"+
                    "<td>所属企业</td>"+
                    "<td width=\"40px\">虚拟机</td>"+
                    "<td width=\"40px\">硬盘</td>"+
                    "<td width=\"90px\">余额</td>"+
                    "<td width=\"130px\">操作</td></tr>";
                for(var i = 0; i<acUsers.length; i++){
                    present += "<tr id=\"tr"+acUsers[i].userId+"\">" +
                    		       "<td>" + acUsers[i].userEmail + "</td>";
                    if(enterpriseMap[acUsers[i].userId] != null) {
                    	present += "<td><a id=\"enterprise\" class=\"blueletter\" rel=\"facebox\" size=\"s\" href=\"user/showEnterprise?uid="+ acUsers[i].userId + "\">" + enterpriseMap[acUsers[i].userId] + "</a></td>";
                    } else {
                    	present += "<td></td>";
                    }
                    		       
                    present += "<td><a class=\"blueletter\" target=\"_blank\" href=\"vm/presearchvm?email=" + acUsers[i].userEmail + "\">" + countMap[acUsers[i].userId] + "</a></td>" +
                    		   "<td><a class=\"blueletter\" target=\"_blank\" href=\"hd/hdmanage?email=" + acUsers[i].userEmail + "\">" + countVolumeMap[acUsers[i].userId] + "</a></td>" +
                    		   "<td><a id=\"usermanagebalance" + acUsers[i].userId + "\" class=\"blueletter\" target=\"_blank\" href=\"price/income.jsp?email=" + acUsers[i].userEmail + "\">" + balanceMap[acUsers[i].userId] + "</a></td>" +
                    		   "<td>" +
                    				"<a id=\"group_detail" + acUsers[i].userId + "\" class=\"blueletter\" href=\"group/showAcGroup?acGroupId=" + acUsers[i].groupId + "\"" +
                    					" rel=\"facebox\" title=\"查看群组\" size=\"s\">查看群组</a>" + "&nbsp;&nbsp;" +
                    				"<a class=\"blueletter\" href=\"user/preChangeAuthority?uid=" + acUsers[i].userId + "\"" +
  		     					  		" rel=\"facebox\" title=\"修改群组\" size=\"s\">修改群组</a>" + "&nbsp;&nbsp;" + 
  		     					  	"<br/>" + 
  		     					  	"<a id=\"recharge\" class=\"blueletter\" target=\"_blank\" href=\"price/income.jsp?email=" + acUsers[i].userEmail + "\">充值</a>" + "&nbsp;&nbsp;" + 
  		     					  	"<a id=\"cleanblance\" class=\"blueletter\" target=\"_blank\" href=\"price/income.jsp?email=" + acUsers[i].userEmail + "\">余额清零</a>" + "&nbsp;&nbsp;" ;
                    if(acUsers[i].isActive) {
                    	present += 
                    			"<a id=\"change_status" + acUsers[i].userId + "\" class=\"blueletter\" href=\"javascript:void(0)\" onclick=\"changeStatus(" + acUsers[i].userId + ");\" size=\"s\">冻结</a>" + "&nbsp;&nbsp;" + 
		     				"</td>";
                    } else {
                    	present += 
					   			"<a id=\"change_status" + acUsers[i].userId + "\" class=\"blueletter\" href=\"javascript:void(0)\" " +
					   			  " onclick=\"changeStatus(" + acUsers[i].userId + ");\" size=\"s\">解冻</a>" + "&nbsp;&nbsp" + 
		     				"</td>";
                    }
                    present += "</tr>";
                }
                present += "</table>";
                
                var endPage = data.lastpage;
                var pageNum = data.lastpage - page;
                var pageHtml = "";
                pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchBalance(1"+",'"+email+"','"+group+"','"+enterpriseName+"')\">首页</a>";     
                
                if(page == 1){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBalance(1"+",'"+email+"','"+group+"','"+enterpriseName+"');\">&lt;&lt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBalance("+(page -1)+",'"+email+"','"+group+"','"+enterpriseName+"');\">&lt;&lt;</a>";
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
                    pageHtml += "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBalance("+i+",'"+email+"','"+group+"','"+enterpriseName+"');\">"+i+"</a>";
                }
                if(page + 1 < data.lastpage){
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBalance("+(page + 1)+",'"+email+"','"+group+"','"+enterpriseName+"');\">&gt;&gt;</a>";
                }else{
                    pageHtml +=  "<a class='pagenum' href='javascript:void(0)' onclick=\"searchBalance("+data.lastpage+",'"+email+"','"+group+"','"+enterpriseName+"');\">&gt;&gt;</a>";
                }
            
                pageHtml +=   "<a class='text' id='firstpage' onclick=\"searchBalance("+data.lastpage+",'"+email+"','"+group+"','"+enterpriseName+"');\">尾页</a>";     
                
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

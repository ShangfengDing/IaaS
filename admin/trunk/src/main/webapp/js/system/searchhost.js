$(document).ready(function(){
	$("#ip").keydown(function (event) {
        if (event.which == "13" || event.keyCode == "13" || 
        		window.event.which == "13" || window.event.keyCode == "13") {
        	submitAll(1);//回车键，用.ajax提交表单
        }
    });
	$("#cpuCount").keydown(function (event) {
        if (event.which == "13" || event.keyCode == "13" || 
        		window.event.which == "13" || window.event.keyCode == "13") {
        	submitAll(1);//回车键，用.ajax提交表单
        }
    });
	$("#memoryCount").keydown(function (event) {
        if (event.which == "13" || event.keyCode == "13" || 
        		window.event.which == "13" || window.event.keyCode == "13") {
        	submitAll(1);//回车键，用.ajax提交表单
        }
    });
	$("#diskCount").keydown(function (event) {
        if (event.which == "13" || event.keyCode == "13" || 
        		window.event.which == "13" || window.event.keyCode == "13") {
        	submitAll(1);//回车键，用.ajax提交表单
        }
    });
	
 
    
    var query = window.location.href;
    query = query.substring(query.indexOf("?")+1);
    var pairs = query.split("&");
    var hasParam = false;
    for(var i=0; i<pairs.length; i++) {
    	var paramName = pairs[i].substring(0,pairs[i].indexOf("="));
    	var paramValue = pairs[i].substring(pairs[i].indexOf("=")+1);
    	if(paramName == "clusterId") {
    		$("#zoneId").val(1);
    		setClusterValue(paramValue);
    		searchHost(1, "", "", "", 1, paramValue, "", "", "", 
    				"", "", "", "");
    		hasParam = true;
    		break;
    	}
    }
    if(hasParam == false){
    	getCluster();
    }
});

function setClusterValue(paramValue){
    var zoneId = $("#zoneId").val();
    if(zoneId == "") {
    	$("#aggregateId").empty();
    	$("#aggregateId").append("<option value=\"\">--全部--</option>");
    	return;
    }

    $.ajax({
        type: "post",
        url: "system/getclusterandhostbyzid",
        data:{
        	zoneId : zoneId
        },
        success:function(data){
        	$("#aggregateId").empty();
        	var aggregateList = data.aggregates;
        	if(aggregateList != null && aggregateList != "" && aggregateList.length > 0){
        		$("#aggregateId").removeAttr("disabled");
        		$("#aggregateId").append("<option value=\"\">--全部--</option>");
	        	for(var i = 0; i < aggregateList.length; i++){
	        		$("#aggregateId").append("<option value='"+aggregateList[i].id+"'>"+aggregateList[i].name+"</option>");
	        	}
        	}else{
        		$("#host").attr("disabled", "disabled");
        	}
        	$("#aggregateId").val(paramValue);
        }
    });
    
}

function getCluster(){
    $.ajax({
        type: "post",
        url: "system/getclusterandhostbyzid",
        data:{
        	zoneId : 1
        },
        success:function(data){
        	$("#aggregateId").empty();
        	var aggregateList = data.aggregates;
        	if(aggregateList != null && aggregateList != "" && aggregateList.length > 0){
        		$("#aggregateId").removeAttr("disabled");
        		$("#aggregateId").append("<option value=\"\">--全部--</option>");
	        	for(var i = 0; i < aggregateList.length; i++){
	        		$("#aggregateId").append("<option value='"+aggregateList[i].id+"'>"+aggregateList[i].name+"</option>");
	        	}
        	}else{
        		$("#host").attr("disabled", "disabled");
        	}
        }
    });
    
}

function cancelSearch(){
    $("#ip").val("");
    $("#type").val("");
    $("#status").val("");
    $("#zoneId").val("");
    $("#aggregateId").val("");
    $("#service").val("");
    $("#cpuOperator").val("");
    $("#cpuCount").val("");
    $("#memoryOperator").val("");
    $("#memoryCount").html("");
    $("#diskOpertor").val("");
    $("#diskCount").val("");
    $("error").val("");
}

function submitAll(page) {
	var ip = $("#ip").val();
	var type = $("#type").val();
	var status = $("#status").val();
	var zoneId = $("#zoneId").val();
	var aggregateId =  $("#aggregateId").val();
	var service = $("#service").val();
	var cpuOperator = $("#cpuOperator").val();
	var cpuCount = $("#cpuCount").val();
	var memoryOperator = $("#memoryOperator").val();
	var memoryCount = $("#memoryCount").val();
	var diskOperator = $("#diskOperator").val();
	var diskCount = $("#diskCount").val();
	
//	if(ip == "" && type == "" && status == "" && zoneId == "" && aggregateId == "" 
//		&& service == "" && cpuCount == "" && memoryCount == "" && diskCount == ""){
//		if(!confirm("查询全部主机？")){
//        	return;
//    	}
//	} else {
	    if(ip != "" && !isIP(ip)){
	        $("#error").html("IP地址输入的格式不正确");
	        return;
	    }else{
	        $("#error").html("");
	    }
	    if(cpuCount != "" && !isInt(cpuCount)) {
	    	$("#error").html("cpu核数格式不正确，请输入正整数");
		    return;
	    }else{
	    	$("#error").html("");
	    }
	    if(memoryCount != "" && !isInt(memoryCount)) {
	    	$("#error").html("内存大小格式不正确，请输入正整数");
		    return;
	    }else{
	    	$("#error").html("");
	    }
	    if(diskCount != "" && !isInt(diskCount)) {
	    	$("#error").html("硬盘大小格式不正确，请输入正整数");
		    return;
	    }else{
	    	$("#error").html("");
	    }

	$("#query").html("");
	searchHost(1, ip, type, status, zoneId, aggregateId, service, cpuOperator, cpuCount, 
			memoryOperator, memoryCount, diskOperator, diskCount);
}

function searchHost(page, ip, type, status, zoneId, aggregateId, service, cpuOperator, cpuCount, 
		memoryOperator, memoryCount, diskOperator, diskCount){
    $.ajax({
        type:"post", 
        url:"system/searchhost", 
        data:{
        	page:page,
        	ip: ip, 
        	type: type, 
        	status: status, 
        	zoneId: zoneId,
        	aggregateId: aggregateId,
        	service: service, 
        	cpuOperator: cpuOperator,
        	cpuCount: cpuCount,
   			memoryOperator: memoryOperator, 
   			memoryCount: memoryCount,
   			diskOperator: diskOperator, 
   			diskCount: diskCount
        },
        success:function(data){
        	getStyle();
            if(data.total == 0){//data.tmp
                $("#query").html("<h3 class='centeralign'>无搜索结果</h3>");
            }else{
            	
                var hosts = data.hosts;
                var hostIdResourceMap = JSON.parse(JSON.stringify(data.hostIdResourceMap));
                var hostIdServiceMap = JSON.parse(JSON.stringify(data.hostIdServiceMap));
                var present = "";
                present += "<div class='rightfloat strong'>共"+ data.total +"条</div>"+
                    "<table class='datatable topmargin_20' id='querytable'>"+
                    "<tr><td width='40px'>集群</td>"+
                    "<td width='90px'>节点IP</td>"+
                    "<td width='80px'>类型</td>"+
                    "<td width='60px'>实时负载</td>"+
                    "<td>CPU</td>"+
                    "<td>内存/MB</td>"+
                    "<td>硬盘/GB</td>"+
                    "<td width='95px'>操作</td></tr>";
                for(var i = 0;i < hosts.length;i++){
                	present +"<tr>";
                	if(hosts[i].aggregateId == null) {
                    	present += "<td>无&nbsp;<a class='blueletter' rel='facebox' size='s' title='加入集群' "+
                    	"href='system/prehosttocluster?hostId="+ hosts[i].id + "&zoneId=" + hosts[i].availabilityZoneId + "> " +
                    	"加入集群</a></td>";
                    } else {
                    	present += "<td>" + hosts[i].aggregateName + "</td>";
                    }
                	
                    present += "<td>" + hosts[i].ip + "</td>";
                    if(hosts[i].hostType.toString() == "COMPUTE_NODE") {
                    	present += "<td>计算节点</td>";
                    } else {
                    	present += "<td>功能节点</td>";
                    }
                    
                    if(data.hosts[i].hostStatus.toString() == "NORMAL_LOAD") {
                    	present += "<td><span class='greenletter'>正常负载</span></td>";
                    }else if(hosts[i].hostStatus.toString() == "HIGH_LOAD") {
                    	present += "<td><span class='orangeletter'>高负载</span></td>";
                    }else if(hosts[i].hostStatus.toString() == "LOAD_LOAD") {
                    	present += "<td><span>低负载</span></td>";
                    }else if(hosts[i].hostStatus.toString() == "CRASH") {
                    	present += "<td><span class='redletter'>宕机</span></td>";
                    }
                    
                    
                    present += "<td>" + hostIdResourceMap[hosts[i].id].cpu + "</td>";
                    present += "<td>" + hostIdResourceMap[hosts[i].id].memoryMb + "</td>";
                    present += "<td>" + hostIdResourceMap[hosts[i].id].diskGb + "</td>";
                    //present += "<td>" + hosts[i].location + "</td>";
                    
                    if(hosts[i].aggregateId == null) {
                    	present += "<a class='leftmargin_5 blueletter' href='vm/presearchvm?clusterId=" + hosts[i].aggregateId + "&zoneId=" + hosts[i].availabilityZoneId + "&hostId=" + hosts[i].id + ">托管虚拟机</a></td>";
                    } else {
                    	present += "<td style='font-weight:normal'>" +
                    	"<a class='rightmargin_5 blueletter' title='详情' href='system/hostservice?hostId=" + hosts[i].id + "'>详情</a>" +
/*                    	"<a class='rightmargin_5 blueletter' rel='facebox' title='性能' size='s' href='system/hostperformance.jsp?hostName=" + hosts[i].id + "&hostCPU=" + hostIdResourceMap[hosts[i].id].cpu + "&hostMem=" + hostIdResourceMap[hosts[i].id].memoryMb + "&hostDisk=" + hostIdResourceMap[hosts[i].id].diskGb + "'>性能</a>" +
                     	"<a class='rightmargin_5 blueletter' rel='facebox' title='监控' size='l' href='system/hostmonitor.jsp?hostName=" + hosts[i].id + "'>监控</a>" +*/
                     	"<a class='leftmargin_5 blueletter' href='vm/presearchvm?clusterId=" + hosts[i].aggregateId + "&zoneId=" + hosts[i].availabilityZoneId + "&hostId=" + hosts[i].id + "' target='_blank'" + ">托管虚拟机</a></td>";
                    }
                  /*  present += "<tr><td></td><td colspan='3'>节点配置详情:<br/> " +
                    		   "CPU:" + hostIdResourceMap[hosts[i].id].cpu + "个," + 
                    		   "内存:" + hostIdResourceMap[hosts[i].id].memoryMb + "Mb," +
                    		   "硬盘:" + hostIdResourceMap[hosts[i].id].diskGb +"Gb</td>";
                    present += "<td colspan='2'>已有服务:" +
                    		   "<a class='leftmargin_10 blueletter' rel='facebox' size='s' title='节点服务管理' " +
                    		   "href='system/hostservice?hostId=" + hosts[i].id + "'>启动停止服务</a><br/>";
                    var serviceList =  hostIdServiceMap[hosts[i].id];
                    for(var j=0; j<serviceList.length; j++) {
                    	present += serviceList[j].type + ": " + serviceList[j].serviceStatus + "<br/>";
                    }*/
                    present += "</tr>";
                    
                }
                present += "</table>";
                
                var endPage = data.lastpage;
                var pageHtml = "";
                pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchHost(1"+", '"+ip+"', '"+type+"','"+status+"','"+zoneId+"', '"+aggregateId+"', '"+service+"', '"+cpuOperator+"', '"+cpuCount+"', '"+memoryOperator+"', '"+memoryCount+"', '"+diskOperator+"', '"+diskCount+"')\">首页</a>";     
                
                if(page == 1){
                	pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchHost(1"+",'"+ip+"','"+type+"','"+status+"','"+zoneId+"','"+aggregateId+"','"+service+"','"+cpuOperator+"','"+cpuCount+"','"+memoryOperator+"','"+memoryCount+"','"+diskOperator+"','"+diskCount+"');\">&lt;&lt;</a>";
                }else{
                	pageHtml += "<a class='text' id='firstpage' href='javascript:void(0)' onclick=\"searchHost("+(page-1)+",'"+ip+"','"+type+"','"+status+"','"+zoneId+"','"+aggregateId+"','"+service+"','"+cpuOperator+"','"+cpuCount+"','"+memoryOperator+"','"+memoryCount+"','"+diskOperator+"','"+diskCount+"');\">&lt;&lt;</a>";
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
                    pageHtml += "<a class='pagenum' href='javascript:void(0)' onclick=\"searchHost("+i+",'"+ip+"','"+type+"','"+status+"','"+zoneId+"','"+aggregateId+"','"+service+"','"+cpuOperator+"','"+cpuCount+"','"+memoryOperator+"','"+memoryCount+"','"+diskOperator+"','"+diskCount+"');\">"+i+"</a>";
                }
                if(page + 1 < data.lastpage){
                    pageHtml +=  "<a class='text' href='javascript:void(0)' onclick=\"searchHost("+(page + 1)+",'"+ip+"','"+type+"','"+status+"','"+zoneId+"','"+aggregateId+"','"+service+"','"+cpuOperator+"','"+cpuCount+"','"+memoryOperator+"','"+memoryCount+"','"+diskOperator+"','"+diskCount+"');\">&gt;&gt;</a>";
                }else{
                    pageHtml +=  "<a class='text' href='javascript:void(0)' onclick=\"searchHost("+data.lastpage+",'"+ip+"','"+type+"','"+status+"','"+zoneId+"','"+aggregateId+"','"+service+"','"+cpuOperator+"','"+cpuCount+"','"+memoryOperator+"','"+memoryCount+"','"+diskOperator+"','"+diskCount+"');\">&gt;&gt;</a>";
                }
            
                pageHtml +=   "<a class='text' id='lastpage' onclick=\"searchHost("+data.lastpage+",'"+ip+"','"+type+"','"+status+"','"+zoneId+"','"+aggregateId+"','"+service+"','"+cpuOperator+"','"+cpuCount+"','"+memoryOperator+"','"+memoryCount+"','"+diskOperator+"','"+diskCount+"');\">尾页</a>";     
                
                present += "<div class='divpage'><span>共"+data.lastpage+"页</span><span>";
                present += pageHtml;
                present += "</span></div>";
                
               // $()
                $("#query").html(present);
                getStyle();
                $("a[rel=facebox]").facebox();
            };
        }
    });
};


function getAggregate(){  //数据中心 废掉！
    var zoneId = $("#zoneId").val();
    if(zoneId == "") {
    	$("#aggregateId").empty();
    	$("#aggregateId").append("<option value=\"\">--全部--</option>");
    	return;
    }

    $.ajax({
        type: "post",
        url: "system/system/getclusterandhostbyzid",
        data:{
        	zoneId : zoneId
        },
        success:function(data){
        	$("#aggregateId").empty();
        	var aggregateList = data.aggregates;
        	if(aggregateList != null && aggregateList != "" && aggregateList.length > 0){
        		$("#aggregateId").removeAttr("disabled");
        		$("#aggregateId").append("<option value=\"\">--全部--</option>");
	        	for(var i = 0; i < aggregateList.length; i++){
	        		$("#aggregateId").append("<option value='"+aggregateList[i].id+"'>"+aggregateList[i].name+"</option>");
	        	}
        	}else{
        		$("#host").attr("disabled", "disabled");
        	}
        }
    });
}

//判断是否为正确的IP
function isIP(ipAddr){
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
    if(ipAddr.match(reg)){
        return true;
    } else {
        return false;
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

//判断是否为整数 
function isInt(str){
    var reg = /^[0-9]*[1-9][0-9]*$/;
    if(str.match(reg)){
        return true;
    } else {
        return false;
    }
}


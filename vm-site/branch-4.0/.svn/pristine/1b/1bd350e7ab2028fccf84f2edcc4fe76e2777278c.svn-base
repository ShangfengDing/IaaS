//重启云主机
function reboot(preStatus,instanceId,userEmail,regionId,defaulttime,location){
	var img = document.getElementById(instanceId+"img");
	var label = document.getElementById(instanceId+"label");
	var appname = $('#appnamemenu').text();
	if (label.innerText == "运行中"||label.innerText == "已挂起") {
		$.tipModal('confirm','info','确定要重启云主机吗？',function(result){
			if(result==true){
				$.showLoading('show');
				setTimeout("$.showLoading('reset');",1000);
				$.ajax({
					type : "POST",
					url : "vm/vmoperate",
					data : {
						operation : "reboot",
						instanceId : instanceId,
						appname : appname,
						userEmail : userEmail,
		                regionId: regionId
					},
		            success:function(data){
		            	if(data.result=="success"){
		            		$.fillTipBox({type:'success',icon:'glyphicon-ok-sign',content:'重启云主机进行中……'});
							checkStatus(preStatus,instanceId,appname,regionId,userEmail,location);
		            	}else{
		            		error_msg = "重启云主机失败:"+data.result;
		            		$.fillTipBox({type:'danger',icon:'glyphicon-alert',content:error_msg});
		            	}
		            }	
				});
			}
		});
	}
}
//停止云主机
function stop(preStatus,instanceId,userEmail,regionId,defaulttime,location){
	var img = document.getElementById(instanceId+"img");
	var label = document.getElementById(instanceId+"label");
	var appname = $('#appnamemenu').text();
	if (label.innerText == "运行中"||label.innerText == "已挂起") {
		$.tipModal('confirm','info','确定要停止云主机吗？',function(result){
			if(result==true){
				$.showLoading('show');
				setTimeout("$.showLoading('reset');",1000);
				$.ajax({
					type : "POST",
					url : "vm/vmoperate",
					data : {
						operation : "stop",
						instanceId : instanceId,
						appname : appname,
						userEmail : userEmail,
		                regionId: regionId
					},
		            success:function(data){
		            	if(data.result=="success"){
		            		$.fillTipBox({type:'success',icon:'glyphicon-ok-sign',content:'停止云主机进行中……'});
							checkStatus(preStatus,instanceId,appname,regionId,userEmail,location);
		            	}else{
		            		img.setAttribute("src","images/active.png");
							label.innerText="运行中";
							error_msg = '停止云主机失败:' + data.result;
		            		$.fillTipBox({type:'danger',icon:'glyphicon-alert',content:error_msg});
		            	}
		            }	
				});
			}
		});
	}
}
//启动云主机
//判断启动的位置，在列表页就不用checkstatus
function start(preStatus,instanceId,userEmail,regionId,endTime,defaulttime,location){
	var img = document.getElementById(instanceId+"img");    //获取状态图片DOM对象
	var label = document.getElementById(instanceId+"label");  //获取状态标签DOM对象
	var appname = $('#appnamemenu').text();
	//计算到期时间，并与当前时间进行比对
	var expired = false;
	var expiredTime = "20"+endTime.toString().replace(/-/g,"/");
	var expiredDate = (new Date(expiredTime)).getTime();
	var now = (new Date()).getTime();
	if(expiredDate<now) expired = true;
	//判断是否能执行操作
	if (label.innerText == "已关机"&&!expired) {
		//弹出确认窗口
		$.tipModal('confirm','info','确定要启动云主机吗？',function(result){
			if(result==true){
				$.showLoading('show');
				setTimeout("$.showLoading('reset');",1000);
				$.ajax({
					type : "POST",
					url : "vm/vmoperate",
					data : {
						operation : "start",
						instanceId : instanceId,
						appname : appname,
						userEmail : userEmail,
		                regionId: regionId
					},
		            success:function(data){
		            	//根据结果更改页面
		            	if(data.result=="success"){
		            		$.fillTipBox({type:'success',icon:'glyphicon-ok-sign',content:'启动云主机进行中……'});
							checkStatus(preStatus,instanceId,appname,regionId,userEmail);
		            	}else{
		            		img.setAttribute("src","images/stopped.png");
							label.innerText="已关机";
							error_msg = '启动云主机失败' + data.result;
		            		$.fillTipBox({type:'danger',icon:'glyphicon-alert',content:error_msg});
		            	}
		            }	
				});
			}
		});
	}else if(expired){
		$.fillTipBox({type:'danger',icon:'glyphicon-alert',content:'云主机已过期'});
	}
}
//检查主机的状态
function checkStatus(status,sid,appname,regionId,userEmail,location){
	var img = document.getElementById(sid+"img");
	var label = document.getElementById(sid+"label");
	$.ajax({
        type:"POST",
        url:"vm/vmstatus",
        data:{
        	instanceId:sid,
        	appname:appname,
        	regionId:regionId,
        	userEmail:userEmail
        	},
        success:function(data) {
            var newStatus = data.status;
			if (null == location) {
				if(newStatus == status){
					setTimeout(function(){checkStatus(newStatus,sid,appname,regionId,userEmail)}, 1000);
				}else if(newStatus == "other" && status != "other"){
					img.setAttribute("src","images/other.png");
					label.innerText = "任务进行中";
					setTimeout(function(){checkStatus(newStatus,sid,appname,regionId,userEmail)}, 1000);
				}else{
					location.reload();
				}
			}else {
				if (newStatus == "other" && status != "other") {
					img.setAttribute("src","images/other.png");
					label.innerText = "任务进行中";
					intervalTime = 1000;
				} else {
					intervalTime = 15000;
				}
			}
        }
	});
}
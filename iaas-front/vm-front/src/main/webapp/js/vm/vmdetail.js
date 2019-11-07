
var xdata = [];
var ydataCpu = [];
var ydataMem = [];
var ydataNetIn = [];
var ydataNetOut = [];
var xstep = 3;

$(function(){//根据稳态和瞬态更改工具按钮栏
    setTimeout(checkStatus(status, instanceId, vdAppname, regionId, userEmail), 10000);
    console.log(status);
	if ((status == "Running") || (status == "active")) {
		vmMonitor();
	}
});

function formatDate(str){
	var strarr=str.split(" ");console.log(strarr);
	return strarr[0];
}

function vmMonitor(){
	$.ajax({
		type:"POST",
		url:"vm/vmmonitor",
		data:{
			userEmail:userEmail,
			instanceId:instanceId,
			appname:vdAppname,
			regionId:regionId
		},
		success:function(data){
			console.log("success");
			switch (providerEn){
			case "yunhai":
				for (i = 0; i <= data.monitorData.length - 2; i++) {
					xdata.push(formatDate(new Date(data.monitorData[i].time).toTimeString()));
					ydataCpu.push(((data.monitorData[i].cpuPercent) * 100));
					ydataNetIn.push((data.monitorData[i].netInPercent) * 100);
					ydataNetOut.push((data.monitorData[i].netOutPercent) * 100);
				}
				break;
			case "aliyun":
				for (i = 0; i <= data.monitorDataAli.length - 2; i++) {
					xdata.push(data.monitorDataAli[i].timeStamp);
					ydataCpu.push(((data.monitorDataAli[i].cpu) * 100));
					ydataNetIn.push((data.monitorDataAli[i].netInPercent) * 100);
					ydataNetOut.push((data.monitorDataAli[i].netOutPercent) * 100);
				}
				break;
			case "amazon":
				
				break;
			}
					var dataTable = [];
					var dataline = {
						name : "cpu占用率",
						data : ydataCpu
					}
					dataTable.push(dataline);
					drawCharts("cpu" + "_highchart", xdata, dataTable);
					// if(providerEn != "aliyun"){
					// 	var dataTable = [];
					// 	var dataline = {
					// 		name : "内存占用率",
					// 		data : ydataMem
					// 	}
					// 	dataTable.push(dataline);
					// 	drawCharts("mem" + "_highchart", xdata, dataTable);
					// }
					var dataTable = [];
					var dataline = {
						name : "网络读",
						data : ydataNetIn
					}
					dataTable.push(dataline);
					var dataline = {
						name : "网络写",
						data : ydataNetOut
					}
					dataTable.push(dataline);
					drawCharts("net" + "_highchart", xdata, dataTable);

				}
			});
}

function drawCharts(id,xdata,dataTable){
	var charts = new Highcharts.Chart({
	    // Highcharts 配置
		chart: {
            renderTo: id,
            defaultSeriesType: 'line',
            reflow: true,
            margin:[22, 15, 30, 43]
        },
        legend: {
        	align: 'left',
        	verticalAlign: 'top',
        	x: 50,
        	y: -10,
//            layout: 'vertical',
//            style: {
//                bottom:'0px',
//                right:'0px'
//            }
        },
        credits : {
            enabled:false
        },
        title: {
            text: '',
            style: {
                margin: '10px 0 0 0' // center it
            }
        },
        yAxis: {
            min:0,
            title: {
                text: ''
            }
        },
        xAxis: {
        	tickInterval: xstep,
            categories:xdata,
			labels:{
//	                step:20,
//	                align:'right',

            }
        },
        tooltip: {
       	 style: {  //提示框内容的样式
                width:'10000px',
            },
           formatter: function() {
                   //return this.series.name + '<br/>' + '数据:<b>'+this.point.y+'</b><br/>时间:<b>'+xdata[this.point.x]+'<span>    </span>'+'</b>';
           	    return '<div style="width: 10000px;">' + this.series.name + '</div>' + '<br/>' + '<div>数据:' + this.point.y + '</div>' + '<br/>' +'<div>时间:' + xdata[this.point.x] +'</div>';
           }
       },
        scrollbar: {
            enabled: true
        },
        series: dataTable
    });
}

function reboot(preStatus,instanceId,userEmail,regionId,defaulttime){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要重启云主机吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "vm/vmoperate",
				data : {
					operation : "reboot",
					instanceId : instanceId,
					appname : vdAppname,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result != "fail") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '重启云主机进行中……'
						});
						checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '重启云主机失败'
						});
					}
				}
			});
		}
	});
	
}

function stop(preStatus,instanceId,userEmail,regionId,defaulttime){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要停止云主机吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "vm/vmoperate",
				data : {
					operation : "stop",
					instanceId : instanceId,
					appname : vdAppname,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result != "fail" ) {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '停止云主机进行中……'
						});
						checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '停止云主机失败'
						});
					}
				}
			});
		}
	});

}

function start(preStatus,instanceId,userEmail,regionId,endTime,defaulttime){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	// 计算到期时间，并与当前时间进行比对
	console.log(endTime);
	var expired = false;
	var expiredDate;
	var expired = false;
	if(providerEn == "aliyun"){
		expiredDate = (new Date(endTime)).getTime();
	}else{
		var expiredTime = endTime.toString().replace(/-/g, "/");
		expiredDate = (new Date(expiredTime)).getTime();
	}
	var now = (new Date()).getTime();
	if (expiredDate < now)
		expired = true;
	// 判断是否能执行操作
	if (!expired) {
		// 弹出确认窗口
		$.tipModal('confirm', 'info', '确定要执行这个操作吗？', function(result) {
			if (result == true) {
				$.showLoading('show');
				setTimeout("$.showLoading('reset');", 1000);
				$.ajax({
					type : "POST",
					url : "vm/vmoperate",
					data : {
						operation : "start",
						instanceId : instanceId,
						appname : vdAppname,
						userEmail : userEmail,
						regionId : regionId
					},
					success : function(data) {
						// 根据结果更改页面
						if (data.result != "fail") {
							$.fillTipBox({
								type : 'success',
								icon : 'glyphicon-ok-sign',
								content : '启动云主机进行中……'
							});
							checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
						} else {
							$.fillTipBox({
								type : 'danger',
								icon : 'glyphicon-alert',
								content : '启动云主机失败'
							});
						}
					}
				});
			}
		});
	} else if (expired) {
		$.fillTipBox({
			type : 'danger',
			icon : 'glyphicon-alert',
			content : '云主机已过期'
		});
	}
}

function suspend(preStatus,instanceId,userEmail,regionId,defaulttime){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要挂起云主机吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "vm/vmoperate",
				data : {
					operation : "suspend",
					instanceId : instanceId,
					appname : vdAppname,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result == "success") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '挂起云主机进行中……'
						});
						checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '挂起云主机失败'
						});
					}
				}
			});
		}
	});

}

function resume(preStatus,instanceId,userEmail,regionId,defaulttime){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要恢复云主机吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "vm/vmoperate",
				data : {
					operation : "resume",
					instanceId : instanceId,
					appname : vdAppname,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result == "success") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '恢复云主机进行中……'
						});
						checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '恢复云主机失败'
						});
					}
				}
			});
		}
	});

}

function reset(preStatus,instanceId,userEmail,regionId,defaulttime){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要重置云主机吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "vm/vmoperate",
				data : {
					operation : "reset",
					instanceId : instanceId,
					appname : vdAppname,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result == "success") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '重置云主机进行中……'
						});
						checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '重置云主机失败'
						});
					}
				}
			});
		}
	});

}

function forceStop(preStatus,instanceId,userEmail,regionId,defaulttime){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要强制关闭云主机吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "vm/vmoperate",
				data : {
					operation : "forceStop",
					instanceId : instanceId,
					appname : vdAppname,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result == "success") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '强制关闭云主机进行中……'
						});
						checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '强制关闭云主机失败'
						});
					}
				}
			});
		}
	});

}

function del(preStatus,instanceId,userEmail,regionId,defaulttime){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要删除云主机吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "vm/vmoperate",
				data : {
					operation : "delete",
					instanceId : instanceId,
					appname : vdAppname,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result != "fail") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '删除云主机进行中……'
						});
						window.location.href = "vm/vmlist.jsp";
						// checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '删除云主机失败'
						});
					}
				}
			});
		}
	});

}

function isoBoot(preStatus,instanceId,userEmail,regionId,defaulttime){
	$.frontModal({
		size : 'modal-md',
		title: 'ISO装机',
		href : 'template/iso_modal.jsp'
	}).on('shown.bs.modal', function() {
		console.log("shown");
		var modal = $(this);
		$.ajax({
			type : "POST",
			url : "vm/prevminstall",
			data : {
				appname : vdAppname,
				userEmail : userEmail,
				regionId : regionId
			},
			success : function(data) {
				        console.log("pre");
				        console.log(data.isos.length);
						for (i = 0; i < data.isos.length; i++) {
							console.log(data.isos[i].imageUuid);
							console.log(data.isos[i].imageName);
							modal.find("#myselect").append("<option value="+data.isos[i].imageUuid+">"+data.isos[i].imageName+"</option>");				
						}
					}
		});
		modal.find('#confirm').click(function(){
			var isoId = modal.find("#myselect").find("option:selected").val();
			console.log(isoId);
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "vm/vminstall",
				data : {
					instanceId : instanceId,
					appname : vdAppname,
					userEmail : userEmail,
					regionId : regionId,
					isoId : isoId
				},
				success : function(data) {
					if (data.result == "success") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '云主机装机进行中……'
						});
						checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '云主机装机失败'
						});
					}
				}
			});
		});
	});

}

function isoDetach(preStatus,instanceId,userEmail,regionId,defaulttime){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要进行iso弹出吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "vm/vmoperate",
				data : {
					operation : "isoDetach",
					instanceId : instanceId,
					appname : vdAppname,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result == "success") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : 'iso弹出进行中……'
						});
						checkStatus(preStatus, instanceId, vdAppname, regionId, userEmail);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : 'iso弹出失败'
						});
					}
				}
			});
		}
	});

}

function checkStatus(status,sid,vdAppname,regionId,userEmail){
	var img = document.getElementById("status_img");
	var label = $("#status_label");
	//changeButtonsStatus();
	$.ajax({
        type:"POST",
        url:"vm/vmstatus",
        data:{
        	instanceId:sid,
        	appname:vdAppname,
        	regionId:regionId,
        	userEmail:userEmail
        	},
        success:function(data) {
            var newStatus = data.status;
            if(newStatus.toLowerCase() == status.toLowerCase()){
            	setTimeout(function(){checkStatus(newStatus,sid,vdAppname,regionId,userEmail)}, 10000);
            }else if((newStatus == "other" )&& status != "other"){
            	img.setAttribute("src","images/other.png");
            	label.html("任务进行中");
            	changeButtonsStatus();
               	setTimeout(function(){checkStatus(newStatus,sid,vdAppname,regionId,userEmail)}, 1000);
            }else if(newStatus == "deleted"){
            	window.location.href = "vm/vmlist.jsp";
            }else{
            	location.reload();
            }
        }
	});
}

function changeButtonsStatus(){
	var children = $("#base-buttons").children();
	for(i=0;i<children.length;i++){
		if(i!=10&&i!=11){
			children.eq(i).attr("class","btn btn-default front-no-box-shadow active");
			children.eq(i).attr("disabled",true);
		}
	}
}

function migrate(regionId,zoneId,hostId,instanceId,userEmail){
    var img = document.getElementById("status_img");
    var label = $("#status_label");
    $.tipModal('confirm', 'info', '确定要迁移云主机吗？', function(result) {
        if (result == true) {
            $.showLoading('show');
            setTimeout("$.showLoading('reset');", 1000);
            $.ajax({
                type : "POST",
                url : "vm/vmoperate",
                data : {
                    operation : "migrate",
                    instanceId : instanceId,
                    zoneId : zoneId,
                    userEmail : userEmail,
                    regionId : regionId
                },
                success : function(data) {
                    if (data.result != "fail" ) {
                        $.fillTipBox({
                            type : 'success',
                            icon : 'glyphicon-ok-sign',
                            content : '迁移云主机进行中……'
                        });
                    } else {
                        $.fillTipBox({
                            type : 'danger',
                            icon : 'glyphicon-alert',
                            content : '迁移云主机失败'
                        });
                    }
                }
            });
        }
    });

}




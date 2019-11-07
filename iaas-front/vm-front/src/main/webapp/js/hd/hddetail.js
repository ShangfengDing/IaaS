
var xdata = [];
var ydataIn = [];
var ydataOut = [];
var xstep = 3;

var preStatus;
var regionId;
var userEmail;
var diskId;
var provider;
var userId;
var appName;

window.onload = function(){
	$.ajax({
		type : "POST",
		url : "vm/vmmonitor",
		data : {
			userEmail : userEmail,
			instanceId : instanceId,
			provider : providerEn,
			appname:hdAppname,
			regionId : regionId
		},
		success : function(data) {
			switch(providerEn){
			case "yunhai":
				if(data.monitorData!=null){
					for (i = 0; i <= data.monitorData.length - 2; i++) {
						xdata.push(new Date(data.monitorData[i].time).toTimeString());
						ydataIn.push(data.monitorData[i].diskReadRate);
						ydataOut.push(data.monitorData[i].diskWriteRate);
					}
				}	
				break;
			case "aliyun":
				if(data.monitorDataAli!=null){
					for (i = 0; i <= data.monitorDataAli.length - 2; i++) {
						xdata.push(data.monitorDataAli[i].timeStamp);
						ydataIn.push(data.monitorDataAli[i].iopsread);
						ydataOut.push(data.monitorDataAli[i].iopswrite);

					}
				}	
				break;
			}
				
			var dataTable = [];
			var dataline = {
				name : "硬盘读",
				data : ydataIn
			}
			dataTable.push(dataline);
			var dataline = {
				name : "硬盘写",
				data : ydataOut
			}
			dataTable.push(dataline);
			drawCharts("disk_highchart", xdata, dataTable);
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

function detach(preStatus,regionId,userEmail,diskId,instanceId,provider,userId,appName){
	console.log(preStatus+regionId+userEmail+diskId+instanceId+provider);
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要释放云硬盘吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "hd/hdoperate",
				data : {
					userId:userId,
					appName:appName,
					operation : "detach",
					instanceId : instanceId,
					diskId : diskId,
					provider : provider,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result != "fail") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '释放云硬盘进行中……'

						});
						$("#detach-button").addClass("active");
						$("#detach-button").removeAttr("onclick");
						checkStatus(preStatus,regionId,userEmail,diskId,instanceId,provider);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '释放云硬盘失败'
						});
					}
				}
			});
		}
	});
}

function del(preStatus,regionId,userEmail,diskId,provider,userId,appName){
	var label = $("#status_label");
	$.tipModal('confirm', 'info', '确定要删除云硬盘吗？', function(result) {
		if (result == true) {
			$.showLoading('show');
			setTimeout("$.showLoading('reset');", 1000);
			$.ajax({
				type : "POST",
				url : "hd/hdoperate",
				data : {
					userId:userId,
					appName:appName,
					operation : "delete",
					diskId : diskId,
					provider : provider,
					userEmail : userEmail,
					regionId : regionId
				},
				success : function(data) {
					if (data.result != "fail") {
						$.fillTipBox({
							type : 'success',
							icon : 'glyphicon-ok-sign',
							content : '删除云硬盘进行中……'
						});
						checkStatus(preStatus,regionId,userEmail,diskId,instanceId,provider);
					} else {
						$.fillTipBox({
							type : 'danger',
							icon : 'glyphicon-alert',
							content : '删除云硬盘失败'
						});
					}
				}
			});
		}
	});
}

function firstPageAttach(inipreStatus,iniregionId,iniuserEmail,inidiskId,iniprovider,inuserId,inappName){
	preStatus = inipreStatus;
	regionId = iniregionId;
	userEmail = iniuserEmail;
	diskId = inidiskId;
	provider = iniprovider;
	userId = inuserId;
	appName =  inappName;
	$.frontModal({
		size : 'modal-md',
		title: '挂载硬盘到云主机',
		href : 'template/attach_modal.jsp'
	}).on('shown.bs.modal', function() {});
	attach(1);  //之前是0
}

function divAttachPage(page){
	attach(page);
}

function attach(page){
	
		console.log("shown");
		$.ajax({
			type : "POST",
			url : "hd/showattachhd",
			data : {
				page: page,
				provider : provider,
				userEmail : userEmail,
				regionId : regionId,
				userId:userId,
				appName:appName
			},
			success : function(data) {
						$("#myselect").html("");
						$("#navigation").html("");
				        console.log(data.instanceList.length);
				        console.log(data.totalPage);
				        console.log(data.page);

						for (i = 0; i < data.instanceList.length; i++) {
							$("#myselect").append("<div class='radio'><label><input name='optionsRadios' type='radio' value="+data.instanceList[i].instanceId+">&nbsp;&nbsp;&nbsp;"+data.instanceList[i].instanceName+"&nbsp;&nbsp;&nbsp;</label></div>");				
						}
						$("#navigation").html($.getDivPageHtml(parseInt(data.page), data.totalPage, "divAttachPage"));
					}
		});	
}

function attachDisk(){
	console.log(instanceId+provider+userEmail+regionId);
	var instanceId = $("#myselect").find(":checked").val();
	$.showLoading('show');
	setTimeout("$.showLoading('reset');", 1000);
	$.ajax({
		type : "POST",
		url : "hd/hdoperate",
		data : {
			operation : "attach",
			instanceId : instanceId,
			diskId : diskId,
			provider : provider,
			userEmail : userEmail,
			regionId : regionId,
			userId:userId,
			appName:appName

		},
		success : function(data) {
			if (data.result != "fail") {
				$.fillTipBox({
					type : 'success',
					icon : 'glyphicon-ok-sign',
					content : '云硬盘挂载进行中……'
				});
				$("#attach-button").addClass("active");
				$("#attach-button").removeAttr("onclick");
				checkStatus(preStatus,regionId,userEmail,diskId,instanceId,provider);
			} else {
				$.fillTipBox({
					type : 'danger',
					icon : 'glyphicon-alert',
					content : '云主机挂载失败'
				});
			}
		}
	});
}

function checkStatus(status,regionId,userEmail,diskId,instanceId,provider){
	var label = $("#status_label");
	changeButtonsStatus();
	$.ajax({
        type:"POST",
        url:"hd/hdstatus",
        data:{
        	diskId:diskId,        	
        	provider:provider,
        	regionId:regionId,
        	userEmail:userEmail
        	},
        success:function(data) {
            var newStatus = data.status;
            if(newStatus == status){
            	setTimeout(function(){checkStatus(newStatus,regionId,userEmail,diskId,instanceId,provider)}, 10000); 
            }else if(newStatus == "other" && status != "other"){
            	label.html("任务进行中");
               	setTimeout(function(){checkStatus(newStatus,regionId,userEmail,diskId,instanceId,provider)}, 1000); 
            }else if(newStatus == "deleted"){
            	window.location.href = "hd/hd_list.jsp";
            }else{
            	location.reload();
            }
        }
	});
}

function changeButtonsStatus(){
	var children = $("#base-buttons").children();
	console.log(children.length);
	for(i=0;i<children.length;i++){
		if(i!=2){
			children.eq(i).attr("class","btn btn-default front-no-box-shadow active");
		}
	}
}


function shotOperate(operation,snapshotId,shotinstanceName,sproviderEn,suserEmail,sregionId,sdiskId,userId,appName){
	var confirmMsg = (operation == "rollback") ? "确认进行回滚操作吗？(注意：该操作将关闭云主机"+shotinstanceName+"！！)" : "确认删除该快照吗？";
	$.tipModal('confirm', 'warning', confirmMsg, function(result) {		//返回的result是bool类型
		if(result==true){		
			$.ajax({
	    		type:"POST",
	    		url:"shot/shotoperate",
	    		data:{
					userId:userId,
					appName:appName,
	    			operation:operation,
	    			snapshotId:snapshotId,
	    			providerEn:sproviderEn,
	    			userEmail:suserEmail,
	    			regionId:sregionId,
	    			diskId:sdiskId
	    		},
	    		success:function(data){
	    			if(data.result=="success"){
	    				$.fillTipBox({type:'success',icon:'glyphicon-ok-sign',content:'操作成功！'});
	                    if(operation=="delete"){
	                    	$("#tr"+snapshotId).remove();
	                    }
	                } else{
						$.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '操作出现错!'+data.result});
					}
	    		}
	    	});
		}
	});
};
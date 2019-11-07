//更新各种设备服务的数量
function updateSum(type) {
	var obj;
	var objRef;
	if("instance" == type) {
		obj = [$("#instance")];
		objRef = [$("#instanceRef")];
		refreshingStart(obj,objRef);
	}
	else if("disk" == type) {
		obj = [$("#disk")];
		objRef = [$("#diskRef")];
		refreshingStart(obj,objRef);
	}
	else if("shot" == type) {
		obj = [$("#shot")];
		objRef = [$("#shotRef")];
		refreshingStart(obj,objRef);
	}
	else if("image" == type) {
		obj = [$("#image")];
		objRef = [$("#imageRef")];
		refreshingStart(obj,objRef);
	}
	else if("security" == type) {
		obj = [$("#security")];
		objRef = [$("#securityRef")];
		refreshingStart(obj,objRef);
	}
	else {
		obj=[$("#instance"),$("#disk"),$("#shot"),$("#image"),$("#security")];
		objRef=[$("#instanceRef"),$("#diskRef"),$("#shotRef"),$("#imageRef"),$("#securityRef")];
		refreshingStart(obj,objRef);
	}


	$.ajax({
        url: 'sum/updateSum',
        type: 'GET',
        data: {
        	type:type
        },
        success: function (result) {
			refreshingEnd(obj,objRef);

			if("instance" == result.type) {
        		$("#instanceNum").html(result.instanceSum);
        		$("#instanceAcNum").html(result.instanceAcSum);
                $("#instanceRefTime").html(result.instanceRefTime);
        	}
        	
        	if("disk" == result.type) {
        		$("#diskNum").html(result.diskSum);
        		$("#diskAcNum").html(result.diskAcSum);
                $("#diskRefTime").html(result.diskRefTime);
        	}
        	
        	if("shot" == result.type) {
        		$("#shotNum").html(result.shotSum);
                $("#snapshotRefTime").html(result.snapshotRefTime);
        	}
        	
        	if("image" == result.type) {
        		$("#backupNum").html(result.backupSum);
                $("#imageRefTime").html(result.imageRefTime);
        	}
        	
        	if("security" == result.type) {
        		$("#securityGroupNum").html(result.securityGroupSum);
                $("#securityGroupRefTime").html(result.securityGroupRefTime);
        	}
        	
        	if("all" == result.type) {
        		$("#instanceNum").html(result.instanceSum);
            	$("#instanceAcNum").html(result.instanceAcSum);
            	$("#diskNum").html(result.diskSum);
            	$("#diskAcNum").html(result.diskAcSum);   
            	$("#shotNum").html(result.shotSum);
            	$("#backupNum").html(result.backupSum);
            	$("#securityGroupNum").html(result.securityGroupSum);
                $("#instanceRefTime").html(result.instanceRefTime);
                $("#diskRefTime").html(result.diskRefTime);
                $("#snapshotRefTime").html(result.snapshotRefTime);
                $("#imageRefTime").html(result.imageRefTime);
                $("#securityGroupRefTime").html(result.securityGroupRefTime);

            }
        },  
    	error: function () {
    		$.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '刷新失败'});
    	}	
    });
	
}

function refreshingStart(obj,objRef) {
	for(var i=0;i<obj.length;i++){
		obj[i].hide();
	}
	for(var i=0;i<objRef.length;i++){
		objRef[i].css('display','block');
		objRef[i].shCircleLoader({
			dots: 24,
			dotsRadius: 1
		})
	}

}

function refreshingEnd(obj,objRef) {
	for(var i=0;i<obj.length;i++){
		obj[i].show();
	}
	for(var i=0;i<objRef.length;i++){
		objRef[i].css('display', 'none');
		objRef[i].shCircleLoader('destroy');
	}
}

function  querylog() {
	$.ajax({
		url: 'common/queryLog',
		type: 'GET',
		success: function (data) {
			console.log(data);
			LogPanel(data);
		}
	});
}

$('#search_log_btn').click(function () {
	var btime = $('#begin-date').val();
	var etime = $('#end-date').val();
	var timeasc = $("input[name='timeseq']:checked").val();
	var size = $("#size").val();
	var appname =  $("#appname").val();

    if (btime>etime) {
        alert("开始时间大于结束时间！");
        return;
    }
    btime = btime.replace('T',' ');
    etime = etime.replace('T',' ');

	var reg = new RegExp("^[0-9]*$");
	if(!reg.test(size)){
		alert("请输入一个数字！");
		return;
	}


	if (btime != "" && etime !="") {
		qdata = {
			'btime':btime,
			'etime':etime,
			'timeasc':timeasc,
			'size':size,
			'appname':appname
		}
	} else {
		qdata = {
			'timeasc':timeasc,
			'size':size,
			'appname':appname
		}
	}

	// $.ajax({
	// 	url: 'common/queryLog',
	// 	type: 'GET',
	// 	data:qdata,
	// 	success: function (data) {
	// 		console.log(data);
	// 		// LogPanel(data);
     //        $('#logdetail').html(data);
	// 	}
	// });
	getPage(1);

})

function getPage(page){
	qdata.currentpage=page;
    $.ajax({
        url: 'common/queryLog',
        type: 'GET',
        data:qdata,
        success: function (data) {
			$('#logdetail').html(data);
            $('#pageColumn').html($.getDivPageHtml(page,$('#pageColumn').data('endpage'),'(' + getPage + ')'));
        }
    });

}
$(function () {
    // updateSum("all");
	// $('#search_log').removeClass('hidden');
	// $('#search_log').hide();
})

// window.onload = querylog();


// function exseachLog() {
// 	$('#search_log').slideToggle();
// }

// function LogPanel(data) {
// 	$('#newiaaslog').html('');
// 	logResult = data.logResult;
// 	if (!logResult) {
// 	    return;
//     }
// 	logResult.map(function(result, index, logResult) {
//         var normaltime = new Date(parseInt(result.createdTime));
//         var createdTime = normaltime.getFullYear() + "年" + (normaltime.getMonth() + 1) + "月" + normaltime.getDate() + "日 " +
//             normaltime.getHours() + "点" + normaltime.getMinutes() + "分" + normaltime.getSeconds() + "秒";
// 		var providerCn;
//         switch (result.provider){
//             case "aliyun":
//                 providerCn = "阿里云";
//                 break;
//             case "yunhai":
//                 providerCn = "云海";
//                 break;
//         }
//         $logtr = "<tr><td style='width: 250px;'>"+createdTime+"</td>"+
//             "<td style='width: 150px;'>"+result.infoType+"</td>"+
//             "<td>"+"操作设备:&nbsp"+result.device+"</br>"+
//             "操作类型:&nbsp"+result.operateType+"</br>"+
//             "设备ID:&nbsp&nbsp&nbsp&nbsp"+result.deviceId+"</br>"+
//             "提供商:&nbsp&nbsp&nbsp&nbsp"+providerCn+"</br>"+
//             "操作结果:&nbsp"+result.result+"</td></tr>";
//         $('#newiaaslog').append($logtr);
// 	});
// }
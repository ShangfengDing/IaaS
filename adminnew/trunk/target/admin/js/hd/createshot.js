//创建快照
$(function(){
	//检查创建快照
    $("#displayName").blur(function(){
		var str=$(this).val();
        if(str == ""){
            $("#error1_edit").html("请输入快照名称");
            flag = false;
            return;
        }else if(str.length > 20){
            $("#error1_edit").html("最多输入20个字");
            flag = false;
            return;
        }else{
        	flag = true;
        }
	});

	$("#displayDescription").blur(function(){
	    var str = $(this).val();
	    if(str == ""){
            $("#error2_edit").html("请输入快照描述");
            flag1 = false;
            return;
        }else if(str.length > 50){
            $("#error2_edit").html("最多输入50个字");
            flag1 = false;
            return;
        }else{
        	flag = true;
            $("#error2_edit").html("<br/>");
        }
	});
});

//创建快照，表单提交
function modalValue(id,providerEn,regionId,userEmail,userId,appName,zoneId){
	$('#diskId').val(id);
	$('#providerEn').val(providerEn);
	$('#regionId').val(regionId);
	$('#userEmail').val(userEmail);
    $('#userIds').val(userId);
    $('#appName').val(appName);
    $("#zoneId").val(zoneId);
	$('#myModal').modal('show');
}
//快照表单提交,云海的
function submitCheck(){
	var strName = $('#displayName').val();
    if(flag == false){
        if(strName == ""){
            $("#error1_edit").html("请输入快照名称");
            return;
        }else if(strName.length > 20){
            $("#error1_edit").html("最多输入20个字");
            return;
        }else{
        	flag = true;
            $("#error1_edit").html("<br/>");
        }
    }
    if(flag1 == false ){
        var str1 = $("#displayDescription").val();
        if(str1 == ""){
            $("#error2_edit").html("请输入快照描述");
            return;
        }else if(str1.length > 50){
            $("#error2_edit").html("最多输入50个字");
            return;
        }else{
        	flag1=true;
            $("#error2_edit").html("<br/>");
        }
    }
    $.showLoading('show');
    var diskId = $('#diskId').val();
	var providerEn = $('#providerEn').val();
	var regionId = $('#regionId').val();
	var userEmail = $('#userEmail').val();
    var userIds = $('#userIds').val();
    var appName = $('#appName').val();
    var zoneId=$('#zoneId').val();
    $.post("shot/newshot",
       {
        displayName:strName,
		displayDescription:str1,
		diskId:diskId,
		providerEn:providerEn,
		regionId:regionId,
		userEmail:userEmail,
        appName:appName,
        userIds:userIds,
        zoneId:zoneId

},function(data){
		setTimeout(function(){$.showLoading('reset')},3000);
    	if(data.result=="success"){
    		$.fillTipBox({type:'info', icon:'glyphicon-info-sign', content:'快照创建中……此操作预计需要5分钟'});
//    		setTimeout(window.location.reload(), 100000);
    	}else if(data.result == "fail"){
            $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'未知错误'});
        }else{
            $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:data.result});
        }
        $("#displayName").val("");
	    $("#displayDescription").val("");
	    flag = false;
		flag1 = false;
        $('#myModal').modal('hide');
            try {
                if (detailflag) {
                    setTimeout(function(){location.reload()},3000);
                }
            } catch (e) {
                console.log("非硬盘详情页");
            }
    });
}
function shotmodalclear(){
	flag = false;
	flag1 = false;
	$("#displayName").val("");
	$("#displayDescription").val("");
	$('#myModal').modal('hide');
}

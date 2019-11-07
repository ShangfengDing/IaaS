//新建阿里云快照模态框
function aliShotModal(alidiskId,alivmId,alicategory,userEmail,providerEn,regionId,appName){
	var href = 'hd/newalishot.jsp?diskId='+alidiskId+"&vmId="+alivmId+"&diskType="+alicategory+"&userEmail="+userEmail+"&providerEn="+providerEn+"&regionId="+regionId+"&appName="+appName;
	newalishot = $.frontModal({
		size:'modal-md',
		title:'新建云快照',
		href:href
	}).on('shown.bs.modal',function(){
		
	})
}
//名称检查
function checkName(){
	var shotname = $('#shotname').val();
	var shotprovider = $('')
	  var Gnum2 = Gname.search("^[a-zA-Z]|[\u4e00-\u9fa5]");
	    if (Gnum2 == 0){
	    	return true;
	    }else{
	    	return false;
	    }
}
//创建
function createAliShot(){
	var shotname = $('#shotname').val();
	if (shotname == ""){
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'输入不能为空!'});
		return
	}
	if (shotname.length > 20){
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'名称长度不超过20字符!'});
	}
	if (!checkName){
        $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'命名格式不正确!'});
		return
	}
	var providerEn = $('#aliproviderEn').val();
	var regionId = $('#aliregionId').val();
	var diskId = $('#alidiskId').html();
	var userEmail = $('#aliuserEmail').val();
	var appName = $('#aliappName').val();

	$.showLoading('show');
	$.post("shot/newshot",
		{
		displayName:shotname,
		displayDescription:"",
		diskId:diskId,
		providerEn:providerEn,
		regionId:regionId,
		userEmail:userEmail,
		appName:appName
		},function(data){
		setTimeout($.showLoading('reset'),3000);
	   	if(data.result=="success"){
	   		$.fillTipBox({type:'info', icon:'glyphicon-info-sign', content:'快照创建中……此操作预计需要5分钟'});
	   	}else if(data.result == "fail"){
	           $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'未知错误'});
	       }else{
	           $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:data.result});
	       }
	   	newalishot.modal('hide');
            try {
                if (detailflag) {
                    setTimeout(location.reload(),3000);
                }
            } catch (e) {
                console.log("非硬盘详情页");
            }
	   });
}
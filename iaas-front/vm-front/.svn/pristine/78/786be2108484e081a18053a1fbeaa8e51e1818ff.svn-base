function submitCheck(){
	var name = $("#accountName").val().trim();
	var provider = $("#provider").val();
	var appkeyId = $("#appkey_id").val().trim();
	var appkeySecret = $('#appkey_secret').val().trim();
	    if(name == ""){
	    	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'请输入名称'});
	        return;
	    }else if(name.length > 20){
	    	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'最多输入20字'});
	        return;
	    }
	    if(appkeyId == ""){
	    	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'请输入appkey_id'});
	        return;
	    }
	    if(appkeySecret == ""){
	    	$.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'请输入appkey_secret'});
	        return;
	    }
	checkAvailable();
    
}

function checkAvailable(){
	var name = $("#accountName").val().trim();
	var provider = $("#provider").val();
	var appkeyId = $("#appkey_id").val().trim();
	var appkeySecret = $('#appkey_secret').val().trim();
	$.post("account/checkaccount",{
		id:$("#accountId").val(),
		name:name,
		provider:provider,
		appkeyId : appkeyId,
		appkeySecret:appkeySecret,
    },function(data){
        if(data.message=="success"){
        	$.post("account/newaccount",{
        		id:$("#accountId").val(),
        		name:name,
        		provider:provider,
        		appkeyId : appkeyId,
        		appkeySecret:appkeySecret,
            },function(data){
                $("#accountInfo").html("");
                $('.modal').modal('hide')
                $("#accountInfo").html(data);
        });
        } else {
        	$.fillTipBox({type:'danger', icon:'glyphicon-alert', content:data.message})
        }
    });
	
	/*$.post("account/newaccount",{
		name:name,
		provider:provider,
		appkeyId : appkeyId,
		appkeySecret:appkeySecret,
    },function(data){
        if(data.result!="success"){
        	$.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改成功！'})
        } else {
        	$.fillTipBox({type:'danger', icon:'glyphicon-alert', content:data.result})
        }
        location.reload();
});*/
}
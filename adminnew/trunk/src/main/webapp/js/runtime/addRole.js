$(function() {
	//var array = new Array("系统管理", "用户管理", "主机管理", "硬盘管理", "财务管理", "运行管理");
	var array = new Array("系统管理", "用户管理", "财务管理", "运行管理", "操作管理");
	var flag = "";
	$.each(array,function(index, value){
		$("input[name='"+index+"']").click(function() {
			if (this.checked) {
				$("input[name='"+value+"']").each(function() {
					this.checked = true;
				});
			} else {
				$("input[name='"+value+"']").each(function() {
					this.checked = false;
				});
			}
		});
		
		$("input[name='"+value+"']").click(function(){
			$("input[name='"+value+"']").each(function() {
				if (this.checked) {
					flag += "1";
				} else {
					flag += "";
				}
			});
			if (flag.length == $("input[name='"+value+"']").length) {
				$("input[name='"+index+"']").attr("checked", true);
			} else {
				$("input[name='"+index+"']").attr("checked", false);
			}
			flag = "";
		});
	});
}); 

/*
function submit() {
	var name = $("#name").val().trim();

	if(name == "") {
		$("#error_name").html("不得为空");
		return false;
	}else if(name.length > 20) {
		$("#error_name").html("1~20个字");
		return false;
	} else {
		$("#error_name").html("");
	}
	
	var result = "";
	//var array = new Array("系统管理", "用户管理", "主机管理", "硬盘管理", "财务管理", "运行管理");
	var array = new Array("系统管理", "用户管理", "财务管理", "运行管理", "操作管理");
	$.each(array,function(index, value){
		$("input[name='"+value+"']:checked").each(function() {
			result += this.value+",";
		});
	});
	
	$.ajax({  
        url:"runtime/addRole",  
        type:"post",
        data:{name:name, value:result},  
        success:function(data) {
        	if(data.result=="success"){
        		facebox_close();
        		hideLoading();
        		fillTipBox("success","添加角色成功！");
            }else{
        		facebox_close();
            	hideLoading();
            	fillTipBox("error","添加角色失败！");
            }   
        },
        error:function(data){
        	facebox_close();
        	hideLoading();
        	fillTipBox("error","添加角色失败！");
        }
    });
}*/

function cancel() {
    location.reload();
    return false;
}

function submit() {
	var name = $("#name").val().trim();

	if(name == "") {
		$("#error_name").html("不得为空");
		return false;
	}else if(name.length > 20) {
		$("#error_name").html("1~20个字");
		return false;
	} else {
		$("#error_name").html("");
	}

	var result = "";
	//var array = new Array("系统管理", "用户管理", "主机管理", "硬盘管理", "财务管理", "运行管理");
	var array = new Array("系统管理", "用户管理", "财务管理", "运行管理", "操作管理");
	$.each(array,function(index, value){
		$("input[name='"+value+"']:checked").each(function() {
			result += this.value+",";
		});
	});

	$.ajax({
        url:"runtime/addRole",
        type:"post",
        data:{name:name, value:result},
        success:function(data) {
        	if(data.result=="success"){
        		//facebox_close();
        		//hideLoading();
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'添加角色成功！'});
            }else{
        		//facebox_close();
            	//hideLoading();
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'添加角色失败！'});
            }
        },
        error:function(data){
        	//facebox_close();
        	//
			//
			// hideLoading();
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'添加角色失败！'});
        }
    });
}


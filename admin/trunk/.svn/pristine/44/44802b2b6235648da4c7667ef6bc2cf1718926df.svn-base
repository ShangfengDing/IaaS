$(function(){
	//判断name，从而在addrule.jsp页面显示不同的项
	var name = $("input[name=name]").val();
	
	if(name == "cpu"){
		$("#aggregateselect").removeAttr("onclick");
		$(".formtable tr:lt(11)").each(function(i){
			if(i == 1 && $(this).hasClass("hidden")){
				$(this).removeClass("hidden");
			}else if (i > 1 && !($(this).hasClass("hidden"))){
				$(this).addClass("hidden");
			}
		});
	}else if(name == "mem"){
		$("#aggregateselect").removeAttr("onclick");
		$(".formtable tr:lt(11)").each(function(i){
			if(i == 2 && $(this).hasClass("hidden")){
				$(this).removeClass("hidden");
			}else if ( i != 2 && i != 0 && !($(this).hasClass("hidden"))){
				$(this).addClass("hidden");
			}
		});
	}else if(name == "hd"){
		$("#aggregateselect").removeAttr("onclick");
		$(".formtable tr:lt(11)").each(function(i){
			if(i == 3 && $(this).hasClass("hidden")){
				$(this).removeClass("hidden");
			}else if (i != 3 && i != 0 && !($(this).hasClass("hidden"))){
				$(this).addClass("hidden");
			}
		});
	}else if(name == "bw"){
		$("#aggregateselect").removeAttr("onclick");
		$(".formtable tr:lt(11)").each(function(i){
			if(i == 4 && $(this).hasClass("hidden")){
				$(this).removeClass("hidden");
			}else if (i != 4 && i != 0 && !($(this).hasClass("hidden"))){
				$(this).addClass("hidden");
			}
		});
	}else if(name == "vmpackage"){
		//显示相应的input，并且把select中的选项补全，将选中的value放到input里（提交时）
		$(".formtable tr:lt(11)").each(function(i){
			if((i >= 5 && i <= 10) && $(this).hasClass("hidden")){
				$(this).removeClass("hidden");
			}else if ((i <= 4) && i != 0 && !($(this).hasClass("hidden"))){
				$(this).addClass("hidden");
			}
		});
		//从已存在的table中读取数据
		$("#aggregateselect").attr("onclick","selectCluster()");
		selectCluster();
		
	}else if(name == "charge"){
		$("#aggregateselect").removeAttr("onclick");
		$(".formtable tr:lt(11)").each(function(i){
			if (i > 0 && !($(this).hasClass("hidden"))){
				$(this).addClass("hidden");
			}
		});
	}else if(name == "instancetype"){
		//显示相应的input，并且把select中的选项补全，将选中的value放到input里（提交时）
		$(".formtable tr:lt(11)").each(function(i){
			if((i >= 5 && i <= 10) && $(this).hasClass("hidden")){
				$(this).removeClass("hidden");
			}else if ((i <= 4) && i != 0 && !($(this).hasClass("hidden"))){
				$(this).addClass("hidden");
			}
		});
		//从已存在的table中读取数据
		$("#aggregateselect").attr("onclick","selectCluster()");
		selectCluster();	
	}
	
});

function selectCluster(){
	var nowclusterid = $("#aggregateselect").find("option:selected").val();
	$("#cpuselect").empty();
	$("#memselect").empty();
	$("#hdselect").empty();
	$("#bwselect").empty();
	
	if($("#cpus tr:gt(0)").length > 0){
		$("#cpus tr:gt(0)").each(function(i){
			if($(this).children("#td_cluster_"+nowclusterid).length >0){
				var pid = parseInt($(this).children().eq(1).html());
				$("#cpuselect").append("<option value=\""+pid+"\">"+pid+"</option>");
			}
		});
	}
	
	if($("#mems tr:gt(0)").length > 0){
		$("#mems tr:gt(0)").each(function(i){
			if($(this).children("#td_cluster_"+nowclusterid).length >0){
				var pid = parseInt($(this).children().eq(1).html());
				$("#memselect").append("<option value=\""+pid+"\">"+pid+"</option>");
			}
		});
	}
	
	if($("#hds tr:gt(0)").length > 0){
		$("#hds tr:gt(0)").each(function(i){
			if($(this).children("#td_cluster_"+nowclusterid).length >0){
				var pid = parseInt($(this).children().eq(1).html());
				$("#hdselect").append("<option value=\""+pid+"\">"+pid+"</option>");
			}
		});
	}
	
	if($("#bws tr:gt(0)").length > 0){
		$("#bws tr:gt(0)").each(function(i){
			if($(this).children("#td_cluster_"+nowclusterid).length >0){
				var pid = parseInt($(this).children().eq(1).html());
				$("#bwselect").append("<option value=\""+pid+"\">"+pid+"</option>");
			}
		});
	}
}

function checkUnitValid(obj){
    var val = $(obj).val().trim();
    if(val.length == 0){
        $(obj).next().html("请输入数字");
    }else if(!isDigit(val)){
    	$(obj).next().html("请用数字表示");
    }else if(val < 0){
    	$(obj).next().html("请输入不小于0的数字");
    }else{
    	$(obj).next().html("");
    }
}

function checkNameValid(obj){
    var val = $(obj).val().trim();
    var name = $("input[name=name]").val();
    if(name == "vmpackage"){
		if(val.length == 0){
	        $("#error2").html("请输入名称");
	    }else if(val.length > 20 ){
	    	$("input[name=packagename]").val(val.substring(0, 19));
	        $("#error2").html("名称不得超过20个字");
	    }else{
	        $("#error2").html("");
	    }
	}else if(name == "instancetype"){
		if(val.length == 0){
	        $("#error2").html("请输入名称");
	    }else if(!isInstancetype(val)){
	        $("#error2").html("名称不合法，请使用： free.字母加数字.large或medium或small");
	    }else{
	        $("#error2").html("");
	    }
	}
}

function checkDesValid(obj){
    var val = $(obj).val().trim();
    if(val.length == 0){
        $("#error3").html("请输入描述");
    }else if(val.length > 50){
    	$(obj).val(val.substring(0, 49));
        $("#error3").html("描述不得超过50个字");
    }else{
        $("#error3").html("");
    }
}

function checkYearPriceValid(obj){
    var val = $(obj).val().trim();
    if(val.length == 0){
        $("#error4").html("请输入价格");
    }else if(!isDecimal(val)){
        $("#error4").html("请用数字（小数点后两位）表示");
    }else if(val < 0){
        $("#error4").html("请输入大于0的数字");
    }else{
        $("#error4").html("");
    }
}

function checkMonthPriceValid(obj){
    var val = $(obj).val().trim();
    if(val.length == 0){
        $("#error5").html("请输入价格");
    }else if(!isDecimal(val)){
        $("#error5").html("请用数字（小数点后两位）表示");
    }else if(val < 0){
        $("#error5").html("请输入大于0的数字");
    }else{
        $("#error5").html("");
    }
}

function checkDayPriceValid(obj){
    var val = $(obj).val().trim();
    if(val.length == 0){
        $("#error6").html("请输入价格");
    }else if(!isDecimal(val)){
        $("#error6").html("请用数字（小数点后两位）表示");
    }else if(val < 0){
        $("#error6").html("请输入大于0的数字");
    }else{
        $("#error6").html("");
    }
}

function checkHourPriceValid(obj){
    var val = $(obj).val().trim();
    if(val.length == 0){
        $("#error7").html("请输入价格");
    }else if(!isDecimal(val)){
        $("#error7").html("请用数字（小数点后两位）表示");
    }else if(val < 0){
        $("#error7").html("请输入大于0的数字");
    }else{
        $("#error7").html("");
    }
}

function isDigit(str){
    var reg = /^[0-9]+$/;
    return reg.test(str);
}

function isDecimal(str){
	var reg = /^\d+(\.\d{1,2})?$/;
	return reg.test(str);
}

function isInstancetype(str){
	var reg = /^free\.[a-zA-Z]+[0-9]+\.(small|medium|large)$/;
	return reg.test(str);
}

//onsubmit返回的方法
function submitCheck(){
	var name = $("input[name=name]").val();
	$("input[name=clusterid]").val($("#aggregateselect").find("option:selected").val());
	if(name == "cpu" || name == "mem" || name == "hd" || name == "bw"){
        var val = $("input[name="+name+"]").val().trim();
        if(val.length == 0){
        	$("input[name="+name+"]").next().html("请输入数字");
            return false;
        }else if(!isDigit(val)){
        	$("input[name="+name+"]").next().html("请用数字表示");
            return false;
        }else{
        	$("input[name="+name+"]").next().html("");
        }
	}else if(name == "vmpackage" || name == "instancetype"){
		//提交前，把选择的选项填入表单
		if($("#cpuselect").find("option").length > 0 && $("#memselect").find("option").length > 0 && 
				$("#hdselect").find("option").length > 0 && $("#bwselect").find("option").length > 0){
			$("input[name=cpu]").val($("#cpuselect").find("option:selected").val());
			$("input[name=mem]").val($("#memselect").find("option:selected").val());
			$("input[name=hd]").val($("#hdselect").find("option:selected").val());
			$("input[name=bw]").val($("#bwselect").find("option:selected").val());
		}else if($("#cpuselect").find("option").length == null || $("#cpuselect").find("option").length == 0){
			$("#error8").html("没有CPU选项，请先添加CPU配置");
	        return false;
		}else if($("#memselect").find("option").length == null || $("#memselect").find("option").length == 0){
			$("#error9").html("没有内存选项，请先添加内存配置");
	        return false;
		}else if($("#hdselect").find("option").length == null || $("#hdselect").find("option").length == 0){
			$("#error10").html("没有硬盘选项，请先添加硬盘配置");
	        return false;
		}else if($("#bwselect").find("option").length == null || $("#bwselect").find("option").length == 0){
			$("#error11").html("没有带宽选项，请先添加带宽配置");
	        return false;
		}else{
			return false;
		}
		
		var val4 = $("input[name=packagename]").val().trim();
		if(name == "vmpackage"){
			if(val4.length == 0){
		        $("#error2").html("请输入名称");
		        return false;
		    }else if(val4.length > 20 ){
		    	$("input[name=packagename]").val(val4.substring(0, 19));
		        $("#error2").html("名称不得超过20个字");
		        return false;
		    }else{
		        $("#error2").html("");
		    }
		}else if(name == "instancetype"){
			if(val4.length == 0){
		        $("#error2").html("请输入名称");
		        return false;
		    }else if(!isInstancetype(val4)){
		        $("#error2").html("名称不合法，请使用： free.字母加数字.large或medium或small");
		        return false;
		    }else{
		        $("#error2").html("");
		    }
		}
	    
	    
		var val5 = $("textarea[name=description]").val().trim();
	    if(val5.length == 0){
	        $("#error3").html("请输入描述");
	        return false;
	    }else if(val5.length > 50){
	    	$("textarea[name=description]").val(val5.substring(0, 49));
	        $("#error3").html("描述不得超过50个字");
	        return false;
	    }else{
	        $("#error3").html("");
	    }
	}
    
    var val1 = $("input[name=yearPrice]").val().trim();
    if(val1.length == 0){
        $("#error4").html("请输入价格");
        return false;
    }else if(!isDecimal(val1)){
        $("#error4").html("请用数字（小数点后两位）表示");
        return false;
    }else if(val1 < 0){
        $("#error4").html("请输入大于0的数字");
        return false;
    }else{
        $("#error4").html("");
        flag1 = true;
    }
    
    var val2 = $("input[name=monthPrice]").val().trim();
    if(val2.length == 0){
        $("#error5").html("请输入价格");
        return false;
    }else if(!isDecimal(val2)){
        $("#error5").html("请用数字（小数点后两位）表示");
        return false;
    }else if(val2 < 0){
        $("#error5").html("请输入大于0的数字");
        return false;
    }else{
        $("#error5").html("");
        flag2 = true;
    }
    
    var val4 = $("input[name=dayPrice]").val().trim();
    //alert(val4);
    if(val4.length == 0){
        $("#error6").html("请输入价格");
        return false;
    }else if(!isDecimal(val4)){
        $("#error6").html("请用数字（小数点后两位）表示");
        return false;
    }else if(val4 < 0){
        $("#error6").html("请输入大于0的数字");
        return false;
    }else{
        $("#error6").html("");
        flag3 = true;
    }
    
    var val7 = $("input[name=hourPrice]").val().trim();
    //alert(val7);
    if(val7.length == 0){
        $("#error7").html("请输入价格");
        return false;
    }else if(!isDecimal(val7)){
        $("#error7").html("请用数字（小数点后两位）表示");
        return false;
    }else if(val7 < 0){
        $("#error7").html("请输入大于0的数字");
        return false;
    }else{
        $("#error7").html("");
        flag4 = true;
    }
    
    return true;
}

function submitForm(){
	if(submitCheck()){
		var clusterid = $("input[name=clusterid]").val().trim();
		var name = $("input[name=name]").val().trim();
		var packagename = $("input[name=packagename]").val().trim();
		var description = $("textarea[name=description]").val().trim();
		var cpu = $("input[name=cpu]").val().trim();
		var mem = $("input[name=mem]").val().trim();
		var hd = $("input[name=hd]").val().trim();
		var bd = $("input[name=bw]").val().trim();
		var dayPrice = $("input[name=dayPrice]").val().trim();
		var monthPrice = $("input[name=monthPrice]").val().trim();
		var yearPrice = $("input[name=yearPrice]").val().trim();
		var hourPrice = $("input[name=hourPrice]").val().trim();

		showLoading();
		$.ajax({  
	        url:"price/addrule",  
	        type:"post",
	        //async:false,
	        data:{clusterid:clusterid, packagename:packagename, description:description, name:name, 
	        	cpu:cpu, mem:mem, hd:hd, bd:bd, dayPrice:dayPrice,monthPrice:monthPrice, 
	        	yearPrice:yearPrice, hourPrice:hourPrice},  
	        success:function(data) {
	        	if(data.result=="success"){
	        		facebox_close();
	        		hideLoading();
	        		gotonext("price/getrule");
	            }else{
	        		facebox_close();
	            	hideLoading();
	            	fillTipBox("error","添加计费规则失败");
	            }   
	        },
	        error:function(data){
	        	facebox_close();
	        	hideLoading();
	        	fillTipBox("error","添加计费规则失败");
	        }
	    });
	}
}
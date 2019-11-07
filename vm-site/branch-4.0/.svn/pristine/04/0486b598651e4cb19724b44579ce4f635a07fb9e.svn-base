$(document).ready(function(){
	//根据包年、包月等确定count的个数和单位
	if($("#timeDaily").attr('checked')=='checked'){
		$("#dayselect").html("<select class='selectboxsmall leftmargin_20' id='count' onchange='changeCount()'></select>天");
		for (var i = 1; i < 21; i ++ ){
			$("#count").append("<option value=\""+i+"\">"+i+"</option>");
		}
	}else if($("#timeMonthly").attr('checked')=='checked'){
		$("#monthselect").html("<select class='selectboxsmall leftmargin_20' id='count' onchange='changeCount()'></select>月");
		for (var i = 1; i < 12; i ++ ){
			$("#count").append("<option value=\""+i+"\">"+i+"</option>");
		}
	}else if($("#timeYearly").attr('checked')=='checked'){
		$("#yearselect").html("<select class='selectboxsmall leftmargin_20' id='count' onchange='changeCount()'></select>年");
		for (var i = 1; i < 4; i ++ ){
			$("#count").append("<option value=\""+i+"\">"+i+"</option>");
		}
	}
	//计算价格
	changeCount();
})

//提交表单，创建新虚拟机
function submitForm(){
	var name = $("#name").val().trim();
	if(name.length == 0){
		$("#error").html("名称不许为空");
		return;
	}else if(name.length > 20){
		$("#error").html("名称不得超过20字");
		return;
	}else{
		$("#error").html("");
	}
	
	var des = $("#des").val().trim();
	if(des.length == 0){
		$("#error1").html("描述不得为空");
		return;
	}else if(des.length > 50){
		$("#error1").html("描述不得超过50字");
		return;
	}else{
		$("#error1").html("");
	}
	
	var zoneId = $("#zoneId").val();
	var size = $("#size").find('option:selected').val();
	
	var count = 1.0;
	count = $("#count").find("option:selected").val();
	var times = 0;
	var paymentType = 0;
	if($("#timeDaily").attr('checked')=='checked'){
		paymentType = 3;
	}else if($("#timeMonthly").attr('checked')=='checked'){
		paymentType = 2;
	}else if($("#timeYearly").attr('checked')=='checked'){
		paymentType = 1;
	}
	
	showLoading();
	$.post("hd/newhd",{name:name, zoneId:zoneId, size:size, description:des,
		paymentType:paymentType, times:times, count:count},function(data){
		hideLoading();
		if(data.result == "success"){
			gotonext("hd/hdlist");
		}else if(data.result == "fail"){
			fillTipBox("error", "未知错误！");
		}else{
			fillTipBox("error", data.result);
		}
	});
}

//转换购买时长
function getTime(){
	if($("#timeDaily").attr('checked')=='checked'){
		$("#monthselect").html("");
		$("#yearselect").html("");
		$("#dayselect").html("<select class='selectboxsmall leftmargin_20' id='count' onchange='changeCount()'></select>天");
		for (var i = 1; i < 21; i ++ ){
			$("#count").append("<option value=\""+i+"\">"+i+"</option>");
		}
	}else if($("#timeMonthly").attr('checked')=='checked'){
		$("#dayselect").html("");
		$("#yearselect").html("");
		$("#monthselect").html("<select class='selectboxsmall leftmargin_20' id='count' onchange='changeCount()'></select>月");
		for (var i = 1; i < 12; i ++ ){
			$("#count").append("<option value=\""+i+"\">"+i+"</option>");
		}
	}else if($("#timeYearly").attr('checked')=='checked'){
		$("#dayselect").html("");
		$("#monthselect").html("");
		$("#yearselect").html("<select class='selectboxsmall leftmargin_20' id='count' onchange='changeCount()'></select>年");
		for (var i = 1; i < 4; i ++ ){
			$("#count").append("<option value=\""+i+"\">"+i+"</option>");
		}
	}
	
	changeCount();
}

function changeCount(){
	//获取折扣
	var size = $("#size").find("option:selected").val();
	var monthDiscount  = $("#ddiv_"+size).attr("yeardiscount");
	var yearDiscount = $("#ddiv_"+size).attr("monthdiscount");
	$("#priceDiscountY").html(monthDiscount);
	$("#priceDiscountM").html(yearDiscount);
	
	var count = 1.0 ;
	count = $("#count").find("option:selected").val();
	
	//计算各项单价
	var dayPrice = $("#size").find('option:selected').attr("day");
	var monthPrice = $("#size").find('option:selected').attr("month");
	var yearPrice = $("#size").find('option:selected').attr("year");
	$("#priceResultD").html(dayPrice+"元/天");
	$("#priceResultM").html(monthPrice+"元/月");
	$("#priceResultY").html(yearPrice+"元/年");
	
	//计算总价格
	var paymentType = ""
	if($("#timeDaily").attr('checked')=='checked'){
		paymentType = "按需("+count+"天)";
		$("#sum").html("已选择（"+$("#size").val()+"G）："+paymentType+"，共计"+accMul(dayPrice,count)+"元");
	}else if($("#timeMonthly").attr('checked')=='checked'){
		paymentType = "包月("+count+"月)";
		$("#sum").html("已选择（"+$("#size").val()+"G）："+paymentType+"，共计"+accMul(monthPrice,count)+"元");
	}else if($("#timeYearly").attr('checked')=='checked'){
		paymentType = "包年("+count+"年)";
		$("#sum").html("已选择（"+$("#size").val()+"G）："+paymentType+"，共计"+accMul(yearPrice,count)+"元");
	}
}
//浮点数相乘精度问题
function accMul(arg1,arg2) 
{ 
	var m=0,s1=arg1.toString(),s2=arg2.toString();
	try{m+=s1.split(".")[1].length}catch(e){};
	try{m+=s2.split(".")[1].length}catch(e){};
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}

function showAdvancedSet(){
	$("#idc").toggleClass("hidden");
	$("#showAdvancedSet").toggleClass("hidden");
	$("#hideAdvancedSet").toggleClass("hidden");
}
//将tab置为不可点击项
$(document).ready(function(){
	$("#mycontainer").children("ul:first").children("li").each(function() {
		$(this).unbind();
    });
	
	//根据包年、包月等确定count的个数和单位
	if($("#timeDaily").attr('checked')=='checked'){
		for (var i = 1; i < 21; i ++ ){
			$("#count").append("<option value=\""+i+"\">"+i+"</option>");
		}
		$("#time").html("天");
	}else if($("#timeMonthly").attr('checked')=='checked'){
		for (var i = 1; i < 12; i ++ ){
			$("#count").append("<option value=\""+i+"\">"+i+"</option>");
		}
		$("#time").html("月");
	}else if($("#timeYearly").attr('checked')=='checked'){
		for (var i = 1; i < 4; i ++ ){
			$("#count").append("<option value=\""+i+"\">"+i+"</option>");
		}
		$("#time").html("年");
	}

	//计算选中时长后的价格
	changeCount();
})

//切换模板
function changeToTab(order){
	$("#mycontainer").children("ul:first").children(".current").removeClass("current");
	$("#mycontainer").children("div").removeClass("current").hide();
	$("#mycontainertab"+order).addClass("current");	
	$("#mycontainerdiv"+order).addClass("current");
	$("#mycontainerdiv"+order).css("width","100%");
	$("#mycontainerdiv"+order).show();
	$("#mycontainer").children(".linebottom").show();  
}
//模板：选择img还是iso
function imgOrIso(){
	$("#imgDiv").toggleClass("hidden");
	$("#isoDiv").toggleClass("hidden");
}

//改变选择的img镜像，更新相应描述信息
function changeImg(){
	var imgId = $("#imgId").val();
	$("#imgIntro").html($("#imgIntro"+imgId).html());
}

//硬件配置：选择套餐还是自定义
function packageOrSeldDefine(){
	$("#packageDiv").toggleClass("hidden");
	$("#selfDefineDiv").toggleClass("hidden");

	//计算选中时长后的价格
	changeCount();
}

//改变选择的防火墙规则，更新相应描述信息
function changeSecurityGroups(){
	var secId = $("#securityId").find("option:selected").val();
	$("#secIntro").html($("#secIntro"+secId).html());
}

//提交表单，创建新虚拟机
function submitForm(){
	var name = $("#name").val().trim();
	if(name.length == 0){
		$("#error").html("名称不得为空");
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
	var imgUuid = "";
	var imgIso = $('input:radio[name="imgIso"]:checked').val();
	if(imgIso == "img"){
		imgUuid = $("#imgId").val();
	}
	
	var count = 1.0;
	count = $("#count").find("option:selected").val();
	var times = 0;
	var paymentType = 0;
	var price = 0;
	if($("#package").attr('checked')=='checked' && $("#selfDefine").attr('checked')==undefined){
		var cpuNum = $("#packageId").find("option:selected").attr("cpu");
		var memNum = $("#packageId").find("option:selected").attr("mem");
		var hdNum = $("#packageId").find("option:selected").attr("hd");
		var bandNum = $("#packageId").find("option:selected").attr("bd");
		var type = "vmpackage";
		if($("#timeDaily").attr('checked')=='checked'){
			price = parseFloat($("#packageId").find('option:selected').attr("day"));
			paymentType = 3;
		}else if($("#timeMonthly").attr('checked')=='checked'){
			price = parseFloat($("#packageId").find('option:selected').attr("month"));
			paymentType = 2;
		}else if($("#timeYearly").attr('checked')=='checked'){
			price = parseFloat($("#packageId").find('option:selected').attr("year"));
			paymentType = 1;
		}
	}else{
		var cpuNum = $("#cpuId").find("option:selected").val();
		var memNum = $("#memId").find("option:selected").val();
		var hdNum = $("#hdId").find("option:selected").val();
		var bandNum = $("#bandId").find("option:selected").val();
		var type = "vm";
		if($("#timeDaily").attr('checked')=='checked'){
			price = parseFloat($("#cpuId").find('option:selected').attr("day"));
			price += parseFloat($("#memId").find('option:selected').attr("day"));
			price += parseFloat($("#hdId").find('option:selected').attr("day"));
			price += parseFloat($("#bandId").find('option:selected').attr("day"));
			paymentType = 3;
		}else if($("#timeMonthly").attr('checked')=='checked'){
			price = parseFloat($("#cpuId").find('option:selected').attr("month"));
			price += parseFloat($("#memId").find('option:selected').attr("month"));
			price += parseFloat($("#hdId").find('option:selected').attr("month"));
			price += parseFloat($("#bandId").find('option:selected').attr("month"));
			paymentType = 2;
		}else if($("#timeYearly").attr('checked')=='checked'){
			price = parseFloat($("#cpuId").find('option:selected').attr("year"));
			price += parseFloat($("#memId").find('option:selected').attr("year"));
			price += parseFloat($("#hdId").find('option:selected').attr("year"));
			price += parseFloat($("#bandId").find('option:selected').attr("year"));
			paymentType = 1;
		}
	}
	price = accMul(price, count);
	
	var secId = $("#securityId").find("option:selected").val();
	
	showLoading();
	$.post("vm/newvm",{name:name, zoneId:zoneId, imgUuid:imgUuid, displayDescription:des, 
		cpuNum:cpuNum, memNum:memNum, hdNum:hdNum, bandNum:bandNum, securityGroupId:secId,
		type:type, price:price, paymentType:paymentType, times:times, count:count},function(data){
			hideLoading();
			if(data.result == "success"){
				gotonext("vm/vmlist2");
			}else if(data.result == "nomoney"){
				fillTipBox("error","余额不足！");
			}else if(data.result == "fail"){
				fillTipBox("error","扣费失败！");
			}
	});
}

//生成预览
function showPreView(order){
	var vmNameHtml=$("#vmNameId").val();
	
	var hdConfig = $('input:radio[name="hdConfig"]:checked').val();
	//系统套餐
	if(hdConfig =='0'){
		var hdConfigHtml="(系统套餐) "+$("#packageId").find("option:selected").text();
	}else{
		var hdConfigHtml="(自定义) "+$("#cpuId").find("option:selected").text()+
						"CPU，"+$("#memId").find("option:selected").text()+
						"内存，"+$("#hdId").find("option:selected").text()+
						"硬盘，"+$("#bandId").find("option:selected").text()+"带宽";
	}
	
	var packageMoney = $('input:radio[name="packageMoney"]:checked');
	var packageMoneyHtml = '';
	//按需的，要特殊显示
	/*if($(packageMoney).val() == '0'){
		packageMoneyHtml = "按需 "+"CPU:"+
	}*/
	
	$(packageMoney).parent().siblings().each(function(){
		packageMoneyHtml+=$(this).html()+" ";
	})
	
	var imgIso = $('input:radio[name="imgIso"]:checked').val();
	//系统套餐
	if(imgIso =='0'){
		var imgIsoHtml="(系统镜像) "+$("#imgId").find("option:selected").text();
	}else{
		var imgIsoHtml="(手动安装) 申请虚拟机完成后，手动从光驱安装iso文件";
	}
	
	var clusterHtml = $("#clusterId").find("option:selected").text();
	
	$("#vmNameHtml").html(vmNameHtml);
	$("#hdConfigHtml").html(hdConfigHtml);
	$("#packageMoneyHtml").html(packageMoneyHtml);
	$("#imgIsoHtml").html(imgIsoHtml);
	$("#clusterHtml").html(clusterHtml);
	changeToTab(order);
}

//计算价格
function changePackagePrice(obj){
	//更新套餐对应的描述
	var pId = $("#packageId").find("option:selected").val();
	$("#hdIntro").html($("#hdIntro"+pId).html());
	
	//计算价格
	changeCount();
}

function changeSelDefinePrice(){
	//计算价格
	changeCount();
}

//转换购买时长
function getTime(){
	var countOld = $("#count").children().length;
	if($("#timeDaily").attr('checked')=='checked'){
		if( countOld < 20){
			for (var i = countOld +1; i < 21; i ++ ){
				$("#count").append("<option value=\""+i+"\">"+i+"</option>");
			}
		}
		$("#time").html("天");
	}else if($("#timeMonthly").attr('checked')=='checked'){
		if( countOld < 11){
			for (var i = countOld +1; i < 12; i ++ ){
				$("#count").append("<option value=\""+i+"\">"+i+"</option>");
			}
		}else if (countOld > 11){
			for (var i = 12; i <= countOld; i++){
				$("#count option[value=\""+i+"\"]").remove();
			}
		}
		$("#time").html("月");
	}else if($("#timeYearly").attr('checked')=='checked'){
		if (countOld > 4){
			for (var i = 4; i <= countOld; i++){
				$("#count option[value=\""+i+"\"]").remove();
			}
		}
		$("#time").html("年");
	}
	changeCount();
}

function changeCount(){
	$("#priceResult").html("正在计算...");
	$("#sum").html("正在计算...");
	var count = 1.0 ;
	count = $("#count").find("option:selected").val();
	//计算各项单价
	if($("#packageDiv").hasClass("hidden")){
		var dayPrice = parseFloat($("#cpuId").find('option:selected').attr("day"));
		var monthPrice = parseFloat($("#cpuId").find('option:selected').attr("month"));
		var yearPrice = parseFloat($("#cpuId").find('option:selected').attr("year"));

		dayPrice += parseFloat($("#memId").find('option:selected').attr("day"));
		monthPrice += parseFloat($("#memId").find('option:selected').attr("month"));
		yearPrice += parseFloat($("#memId").find('option:selected').attr("year"));

		dayPrice += parseFloat($("#hdId").find('option:selected').attr("day"));
		monthPrice += parseFloat($("#hdId").find('option:selected').attr("month"));
		yearPrice += parseFloat($("#hdId").find('option:selected').attr("year"));

		dayPrice += parseFloat($("#bandId").find('option:selected').attr("day"));
		monthPrice += parseFloat($("#bandId").find('option:selected').attr("month"));
		yearPrice += parseFloat($("#bandId").find('option:selected').attr("year"));
		
		$("#priceResult").html(dayPrice+"元/天，"+monthPrice+"元/月，"+yearPrice+"元/年");
	}else{
		var dayPrice = $("#packageId").find('option:selected').attr("day");
		var monthPrice = $("#packageId").find('option:selected').attr("month");
		var yearPrice = $("#packageId").find('option:selected').attr("year");
		
		$("#priceResult").html(dayPrice+"元/天，"+monthPrice+"元/月，"+yearPrice+"元/年");
	}
	//计算总价格
	if($("#timeDaily").attr('checked')=='checked'){
		$("#sum").html(accMul(dayPrice,count));
	}else if($("#timeMonthly").attr('checked')=='checked'){
		$("#sum").html(accMul(monthPrice,count));
	}else if($("#timeYearly").attr('checked')=='checked'){
		$("#sum").html(accMul(yearPrice,count));
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
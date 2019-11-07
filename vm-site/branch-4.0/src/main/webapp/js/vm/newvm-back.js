//将tab置为不可点击项
$(document).ready(function(){
	$("#mycontainer").children("ul:first").children("li").each(function() {
		$(this).unbind();
    });
	
	//限制镜像模板描述、套餐描述的长度
	$("#imgDiv").find("span[class='imgDesc']").each(function(){
		var temp = this.title.replace(new RegExp("；|;","g"),"<br>")
		if(this.title.length > 30){
			var html = shortOf(temp, 30);
			html += "<a class=\"darkblueletter leftmargin_5\" href=\"vm/description.jsp?des="
				+temp+"\" rel=\"facebox\" title=\"更多信息\" size=\"s\">更多</a>";
			$(this).html(html);
		}else{
			$(this).html(temp);
		}
	});
	
	$("a[rel=facebox]").facebox();
//	$("#packageDiv").find("span[class='packageDesc']").each(function(){
//		$(this).html(shortOf(this.title, 23));
//	});
	
});

//模板：选择img还是iso
function imgOrIso(){
	$("#imgDiv").toggleClass("hidden");
	$("#isoDiv").toggleClass("hidden");
}

//改变选择的img镜像，更新相应描述信息
function changeImg(imgId){
	if($("#imgId").val() != imgId){
		var imgName = $("#imgName_"+imgId).html();
		$("#imgId").val(imgId);
		$("#imgName").html("已选择："+imgName);
		$("#imgError").html("");
	}
}

//改变选择的防火墙规则，更新相应描述信息
function changeSecurityGroups(){
	var secId = $("#securityId").find("option:selected").val();
	$("#secIntro").html($("#secIntro"+secId).html());
}

//硬件配置：选择套餐还是自定义
function packageOrSelfDefine(){
	var cid = $("#count").find("option:selected").val(); //切换之前选中的几天、几月、几年
	if($("#package").attr('checked')=='checked' && $("#selfDefine").attr('checked')==undefined){
		$("#packageDiv").removeClass("hidden");
		$("#selfDefineDiv").addClass("hidden");
		$("input[name='lastSelfCount']").val(cid);
		var packageId = $("#packageId").val();
		if(packageId != undefined && packageId != ""){
			getPackageTime(packageId);
		}
	}else{
		$("#packageDiv").addClass("hidden");
		$("#selfDefineDiv").removeClass("hidden");
		$("input[name='lastPackageCount']").val(cid);
		getSelfTime();
	}
}

//更改选择的硬件套餐
function changePackagePrice(packageId){
	$("#packageId").val(packageId);
	var packageName = $("#packageName_"+packageId).html();
	var obj = $("#packageId_"+packageId);
	var cpu = obj.attr("cpu");
	var mem = obj.attr("mem");
	var hd = obj.attr("hd");
	var bd = obj.attr("bd");
	var dayPrice = obj.attr("day");
	var monthPrice = obj.attr("month");
	var yearPrice = obj.attr("year");
	var count = parseInt($("#count").find("option:selected").val());
	var paymentType = "";
	var paymentTypeInt;
	var price = 1.0;
	var type="";
	
	//计算总价格
	if($("#timeYear_"+packageId).attr('checked')=='checked'){
		paymentType = "包年";
		type="年";
		paymentTypeInt = 1;
		price = accMul(yearPrice,count);
	}else if($("#timeMonth_"+packageId).attr('checked')=='checked'){
		paymentType = "包月";
		type="月";
		paymentTypeInt = 2;
		price = accMul(monthPrice,count);
	}else if($("#timeDay_"+packageId).attr('checked')=='checked'){
		paymentType = "按需";
		type="天";
		paymentTypeInt = 3;
		price = accMul(dayPrice,count);
	}else{
		$("#packageError").html("请选择时长");
		return;
	}
	$("#cpuNum").val(cpu);
	$("#memNum").val(mem);
	$("#hdNum").val(hd);
	$("#bandNum").val(bd);
	$("#paymentType").val(paymentTypeInt);
	$("#finalCount").val(count);
	$("#finalPrice").val(price);
	$("#finalType").val("vmpackage");
	
	$("#packageName").html("已选择（"+packageName+"）：" +
			cpu+"核CPU，"+mem+"G内存，"+hd+"G硬盘，"+bd+"M带宽，" +
			paymentType+"("+count+type+")，共计"+price+"元");
	$("#packageError").html("");
}

//更改选择的自定义硬件配置
function changeSelfDefinePrice(){
	changeCount();	//计算价格
}

//显示购买时长_自定义
function getSelfTime(){
	var cid = $("input[name='lastSelfCount']").val();
	if($("#timeYear").attr('checked')=='checked'){
		if($("#yearDiv").html() == ""){
			$("#now").remove();
			$("#yearDiv").html(getTime("self","year"));
		}
	}else if($("#timeMonth").attr('checked')=='checked'){
		if($("#monthDiv").html() == ""){
			$("#now").remove();
			$("#monthDiv").html(getTime("self","month"));
		}
	}else if($("#timeDay").attr('checked')=='checked'){
		if($("#dayDiv").html() == ""){
			$("#now").remove();
			$("#dayDiv").html(getTime("self","day"));
		}
	}else{
		$("#selfError").html("未选择时长");
		return;
	}
	$("#count option[value=\""+cid+"\"]").attr("selected","selected");
	changeCount();
}

//显示购买时长_套餐
function getPackageTime(id){
	var cid = $("input[name='lastPackageCount']").val();
	if($("#timeYear_"+id).attr('checked')=='checked'){
		if($("#yearDiv_"+id).html() == ""){
			$("#now").remove();
			$("#yearDiv_"+id).html(getTime("package","year"));
		}
	}else if($("#timeMonth_"+id).attr('checked')=='checked'){
		if($("#monthDiv_"+id).html() == ""){
			$("#now").remove();
			$("#monthDiv_"+id).html(getTime("package","month"));
		}
	}else if($("#timeDay_"+id).attr('checked')=='checked'){
		if($("#dayDiv_"+id).html() == ""){
			$("#now").remove();
			$("#dayDiv_"+id).html(getTime("package","day"));
		}
	}else{
		$("#packageError").html("未选择时长");
		return;
	}
	$("#count option[value=\""+cid+"\"]").attr("selected","selected");
}

//更改自定义配置
function changeCount(){
	$("#dayPrice").html("……");
	$("#monthPrice").html("……");
	$("#yearPrice").html("……");
	
	//计算各项单价
	var dayPrice;
	var monthPrice;
	var yearPrice;
	dayPrice = parseFloat($("#cpuId").find('option:selected').attr("day"));
	monthPrice = parseFloat($("#cpuId").find('option:selected').attr("month"));
	yearPrice = parseFloat($("#cpuId").find('option:selected').attr("year"));

	dayPrice += parseFloat($("#memId").find('option:selected').attr("day"));
	monthPrice += parseFloat($("#memId").find('option:selected').attr("month"));
	yearPrice += parseFloat($("#memId").find('option:selected').attr("year"));

	dayPrice += parseFloat($("#hdId").find('option:selected').attr("day"));
	monthPrice += parseFloat($("#hdId").find('option:selected').attr("month"));
	yearPrice += parseFloat($("#hdId").find('option:selected').attr("year"));

	dayPrice += parseFloat($("#bandId").find('option:selected').attr("day"));
	monthPrice += parseFloat($("#bandId").find('option:selected').attr("month"));
	yearPrice += parseFloat($("#bandId").find('option:selected').attr("year"));
	
	$("#dayPrice").html(dayPrice);
	$("#monthPrice").html(monthPrice);
	$("#yearPrice").html(yearPrice);
	
	$("#yearDiscount").html((Number(yearPrice/(dayPrice*36))).toFixed(1));
	$("#monthDiscount").html((Number(monthPrice/(dayPrice*3))).toFixed(1));
	
	var cpu = $("#cpuId").find("option:selected").val();
	var mem = $("#memId").find("option:selected").val();
	var hd = $("#hdId").find("option:selected").val();
	var bd = $("#bandId").find("option:selected").val();
	
	var count = parseInt($("#count").find("option:selected").val());
	var paymentType = "";
	var paymentTypeInt;
	var price = 1.0;
	var type="";
	
	//计算总价格
	if($("#timeYear").attr('checked')=='checked'){
		paymentType = "包年";
		type="年";
		paymentTypeInt = 1;
		price = accMul(yearPrice,count);
	}else if($("#timeMonth").attr('checked')=='checked'){
		paymentType = "包月";
		type="月";
		paymentTypeInt = 2;
		price = accMul(monthPrice,count);
	}else if($("#timeDay").attr('checked')=='checked'){
		paymentType = "按需";
		type="天";
		paymentTypeInt = 3;
		price = accMul(dayPrice,count);
	}
	$("#cpuNum").val(cpu);
	$("#memNum").val(mem);
	$("#hdNum").val(hd);
	$("#bandNum").val(bd);
	$("#paymentType").val(paymentTypeInt);
	$("#finalCount").val(count);
	$("#finalPrice").val(price);
	$("#finalType").val("vm");
	
	$("#selfName").html("已选择（自定义）：" +
			cpu+"核CPU，"+mem+"G内存，"+hd+"G硬盘，"+bd+"M带宽，" +
			paymentType+"("+count+type+")，共计"+price+"元");
}

//在newvm.jsp页面点击“确定”，检查各项参数的合法性
function showvm(){
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
	
	var imgIso = $('input:radio[name="imgIso"]:checked').val();
	if(imgIso == "img"){
		imgUuid = $("#imgId").val();
		if(imgUuid == ""){
			$("#imgError").html("请选择模板镜像！");
			return;
		}else{
			$("#imgError").html("");
		}
	}
	
	if($("#package").attr('checked')=='checked' && $("#selfDefine").attr('checked')==undefined){
		var packageId = $("#packageId").val();
		if(packageId == ""){
			$("#packageError").html("请选择硬件套餐！");
			return;
		} else {
			$("#packageError").html("");
			if($("#timeDay_"+packageId).attr('checked')!='checked' 
				&& $("#timeMonth_"+packageId).attr('checked')!='checked'
				&& $("#timeYear_"+packageId).attr('checked')!='checked'){
				$("#packageError").html("请选择硬件套餐时长！");
				return;
			}else{
				$("#packageError").html("");
			}
		}
	}else{
		if($("#timeDay").attr('checked')!='checked'
			&& $("#timeMonth").attr('checked')!='checked'
			&& $("#timeYear").attr('checked')!='checked'){
			$("#selfError").html("请选择时长");
			return;
		}else{
			$("#selfError").html("");
		}
	}
	
	$("#faceBox").click();
}

//在facebox页，点击“确定”，提交表单，创建新虚拟机
function newvm(){
	var name = $("#name").val().trim();
	var des = $("#des").val().trim();
	var zoneId = $("#zoneId").val();
	var secId = $("#securityId").find("option:selected").val();
	var imgUuid = "";
	var imgIso = $('input:radio[name="imgIso"]:checked').val();
	if(imgIso == "img"){
		imgUuid = $("#imgId").val();
		if(imgUuid == ""){
			return;
		}
	}
	
	var cpuNum = $("#cpuNum").val();
	var memNum = $("#memNum").val();
	var hdNum = $("#hdNum").val();
	var bandNum = $("#bandNum").val();
	var type = $("#finalType").val();
	var price = $("#finalPrice").val();
	var paymentType = $("#paymentType").val();
	var count = $("#finalCount").val();
	var times = 0;
	
	if(name==""||des==""||zoneId==""||secId==""||
		cpuNum==""||memNum==""||hdNum==""||bandNum==""||
		type==""||price==""||paymentType==""||count==""){
		fillTipBox("error","页面上有参数错误，请重新检查");
		return;
	}
	showLoading();
	$.post("vm/newvm",{name:name, displayDescription:des, zoneId:zoneId, imgUuid:imgUuid, 
		securityGroupId:secId, cpuNum:cpuNum, memNum:memNum, hdNum:hdNum, bandNum:bandNum, 
		type:type, price:price, paymentType:paymentType, times:times, count:count},function(data){
			hideLoading();
			if(data.result == "success"){
				gotonext("vm/vmlist");
			}else if(data.result == "fail"){
				fillTipBox("error", "未知错误！");
			}else{
				fillTipBox("error", data.result);
			}
	});
}

/**
 * 以下是本js文件中用到的内部函数
 */
//浮点数相乘精度问题
function accMul(arg1,arg2) 
{ 
	var m=0,s1=String(arg1),s2=String(arg2);
	try{m+=s1.split(".")[1].length}catch(e){};
	try{m+=s2.split(".")[1].length}catch(e){};
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}

//某个字符串控制显示长度
function shortOf(str,length)
{
	if(str.length>length){
		var lastletter = str.substring(length-2,length-1);
		var lastletter2 = str.substring(length-3,length-1);
		var lastletter3 = str.substring(length-4,length-1);
		if(lastletter == "<"){
			return str.substring(0,length-2) + "...";
		}else if(lastletter2 == "<b" ){
			return str.substring(0,length-3) + "...";
		}else if(lastletter3 == "<br" ){
			return str.substring(0,length-4) + "...";
		}else{
			return str.substring(0,length-1) + "...";
		}
	} else {
		return str;
	}
}

//显示购买时长的下拉列表
function getTime(packageOrSelf, timeType)
{
	var resultDiv = "";
	if(packageOrSelf == "package"){
		resultDiv += "<div id='now'>"+
			"<select class='selectboxsmall rightmargin_5 leftmargin_10' id='count'>";
	} else {
		resultDiv += "<div id='now'>"+
			"<select class='selectboxsmall rightmargin_5 leftmargin_20' id='count' onchange='changeCount()'>";
	}
	if(timeType == "year"){
		resultDiv += 
		    "<option value='1'>1</option>"+
		    "<option value='2'>2</option>"+
		    "<option value='3'>3</option>"+
		    "</select>年</div>";
	} else if(timeType == "month"){
		resultDiv += 
			"<option value='1'>1</option>"+
			"<option value='2'>2</option>"+
			"<option value='3'>3</option>"+
			"<option value='4'>4</option>"+
			"<option value='5'>5</option>"+
			"<option value='6'>6</option>"+
			"<option value='7'>7</option>"+
			"<option value='8'>8</option>"+
			"<option value='9'>9</option>"+
			"<option value='10'>10</option>"+
			"<option value='11'>11</option>"+
			"</select>月</div>";
	} else if(timeType == "day"){
		resultDiv += 
			"<option value='1'>1</option>"+
			"<option value='2'>2</option>"+
		   	"<option value='3'>3</option>"+
		    "<option value='4'>4</option>"+
		    "<option value='5'>5</option>"+
		    "<option value='6'>6</option>"+
		    "<option value='7'>7</option>"+
		    "<option value='8'>8</option>"+
		    "<option value='9'>9</option>"+
		    "<option value='10'>10</option>"+
		    "<option value='11'>11</option>"+
		    "<option value='12'>12</option>"+
		    "<option value='13'>13</option>"+
		    "<option value='14'>14</option>"+
		    "<option value='15'>15</option>"+
		    "<option value='16'>16</option>"+
		    "<option value='17'>17</option>"+
		    "<option value='18'>18</option>"+
		    "<option value='19'>19</option>"+
		    "<option value='20'>20</option>"+          
		    "</select>天</div>";
	}
	return resultDiv;
}

/**
 * 以下两个函数暂时无用，是分tab实现时的相关代码
 */
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

function showAdvancedSet(){
	$("#firewall").toggleClass("hidden");
	$("#idc").toggleClass("hidden");
	$("#showAdvancedSet").toggleClass("hidden");
	$("#hideAdvancedSet").toggleClass("hidden");
}
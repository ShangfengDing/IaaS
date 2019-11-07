var hdPrice;
$(document).ready(function(){
	changeZone();
})

function changeZone() {
	var zoneId = $("#zoneId").val();
	$.ajax({
		type:"post",
		url:"vm/cluster",
		data:{zoneId:zoneId},//暂时不用
		success:function(data) {
			var clusters = data.acAggregates;// cluster aggregate均为集群
			showhdprice(clusters[0].id);
			for(i = 0; i < clusters.length; i++){
			    $("#clusterId").append("<option value="+clusters[i].id+">"+clusters[i].name+"</option>");
			}
		}
	});
}

function changeCluster() {
	var clusterId = $("#clusterId").val();
	showhdprice(clusterId);
}

function showhdprice(clusterId) {
	$.ajax({
		type:"post",
		url:"hd/hdprice",
		data:{clusterid:clusterId},
		success:function(data){
			//初始化页面，生成页面元素
			var present = "";
			hdPrice = data.harddiskPrice;
			var hdMin = 0;
			var hdLength = 0;
			if(hdPrice != null && hdPrice!= ""){
				hdLength = hdPrice.length;
				hdMin = hdPrice[0].harddisk;
		        present += "<div id=\"hdButton\">"+
		        "<span id=\"hd\" class=\"bluesliderbar\" style=\"display:inline-block;width: 400px; margin: 15px 5px 0 2px;\"></span>"+
		        "<a class=\"grayletter strong leftmargin_10 rightmargin_5\" onclick=\"clickHdSub()\"><img src=\"images/sub.png\" align=\"bottom\"></a>" +
		        "<font size=5><span id='hdNum'></span></font>&nbsp;GB"+
		        "<a class=\"grayletter strong leftmargin_5 rightmargin_5\" onclick=\"clickHdAdd()\"><img src=\"images/add.png\" align=\"bottom\"></a></div>";
			}
			present += "</div><div id=\"payList\"></div>"+
		    "<div id=\"resultName\" class=\"darkblueletter bottommargin_20\">当前未选中任何套餐</div>";
			$("#tdDisk").html(present);
			//初始化两个滑块控件
			$("#hd").slider({
		        orientation: "horizontal",
		        range: "min",
		        step: 1,
		        min: 0,
		        max: hdLength-1,
		        value: 0,
		        animate: true,
		        slide: showValueHd,
		        stop: showValueHd
		    });
			$('#hdNum').html(hdMin);
			//写页面的值
			changeResult(hdMin, 0);
		}
	});
}

//根据hd的值找到对应的index
function findHd(hd){
	for(var i = 0; i < hdPrice.length; i ++){
		if(hdPrice[i].harddisk == hd){
			return i;
		}
	}
	return 0;
}
//硬件配置中slider滑块——硬盘和带宽
function showValueHd(){
	//更改slider的值
	var hdI = $("#hd").slider('value');
	var phd = hdPrice[hdI].harddisk;
	$("#hdNum").html(phd);
	//计算价格修改说明
	changeResult(phd, hdI);
}
//点击加减更改硬盘配置
function clickHdSub(){
	var hdI = $("#hd").slider('value');
	if(hdI == 0){
		$("#hd").slider('value', hdI);
	}else{
		$("#hd").slider('value', hdI-1);
	}
	showValueHd();
}
function clickHdAdd(){
	var hdI = $("#hd").slider('value');
	if(hdI == hdPrice.length -1){
		$("#hd").slider('value', hdI);
	}else{
		$("#hd").slider('value', hdI+1);
	}
	showValueHd();
}
//显示购买时长的下拉列表
function getTime(timeType, pyear, pmonth, pday, phour)
{
	var resultDiv = "";
	resultDiv += "<span id='now' class='rightmargin_10'>"+
			"<select class='selectboxsmall' id='count' style='width:60px' " +
			"onchange=\"changeRadio('"+pyear+"','"+pmonth+"','"+pday+"','"+phour+"')\">";
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
		    "</select>天</span>";
	}
	return resultDiv;
}
//分别根据选中按钮的情况，重写单选按钮和最终结果
function changeResult(phd, hdI){
	var yearDays = 36;
	var monthDays = 3;
	var phour = 0;
	var pday = 0;
	var pmonth = 0;
	var pyear = 0;
	var pcount = 0;
	var ptype = "";
	var ppaymentType = "";
	var pprice = "";
	var paymentTypeInt = 0;
	var pname = "";

	pyear = parseFloat(hdPrice[hdI].yearPrice);
	pmonth = parseFloat(hdPrice[hdI].monthPrice);
	pday = parseFloat(hdPrice[hdI].dayPrice);
	phour = parseFloat(hdPrice[hdI].hourPrice);
	var pyeardiscount = (Number(pyear/(pday*yearDays))).toFixed(1);
	var pmonthdiscount = (Number(pmonth/(pday*monthDays))).toFixed(1);
	var present = "<input type=\"radio\" name=\"time\" id=\"timeYear\" checked='checked'" +
	" onclick=\"changeRadio('"+pyear+"','"+pmonth+"','"+pday+"','"+phour+"')\"/>"+
	"<label for=\"timeYear\"><span id='yearPay'></span></label>" +
	"<span id=\"yearDiv\"></span>"+
	"<input type=\"radio\" name=\"time\" id=\"timeMonth\" onclick=\"changeRadio('" +
	pyear+"','"+pmonth+"','"+pday+"','"+phour+"')\" />"+
	"<label for=\"timeMonth\"><span id='monthPay'></span></label>" +
	"<span id=\"monthDiv\"></span>"+
	"<input type=\"radio\" name=\"time\" id=\"timeDay\" onclick=\"changeRadio('" +
	pyear+"','"+pmonth+"','"+pday+"','"+phour+"')\" />"+
	"<label for=\"timeDay\"><span id='dayPay'></span></label>" +
	"<span id=\"dayDiv\"></span>"+
	"<input type=\"radio\" name=\"time\" id=\"timeHour\" onclick=\"changeRadio('" +
	pyear+"','"+pmonth+"','"+pday+"','"+phour+"')\" />"+
	"<label for=\"timeHour\"><span id='hourPay'></span></label>" +
	"<span id=\"hourDiv\"></span></div>";
	$("#payList").html(present);
	$("#yearPay").html(pyear+"元/年（"+pyeardiscount+"折）");
	$("#monthPay").html(pmonth+"元/月（"+pmonthdiscount+"折）");
	$("#dayPay").html(pday+"元/天（全价）");
	$("#hourPay").html(phour+"元/小时（按需）");
	if($("#timeYear").attr('checked')=='checked'){
		$("#yearDiv").html(getTime("year",pyear, pmonth, pday));
		pcount = parseInt($("#count").find("option:selected").val())
		ppaymentType = "包年";
		ptype="年";
		pprice = accMul(pyear,pcount);
		paymentTypeInt = 1;
	}else if($("#timeMonth").attr('checked')=='checked'){
		$("#monthDiv").html(getTime("month",pyear, pmonth, pday));
		pcount = parseInt($("#count").find("option:selected").val())
		ppaymentType = "包月";
		ptype="月";
		pprice = accMul(pmonth,pcount);
		paymentTypeInt = 2;
	}else if($("#timeDay").attr('checked')=='checked'){
		$("#dayDiv").html(getTime("day"),pyear, pmonth, pday);
		pcount = parseInt($("#count").find("option:selected").val())
		ppaymentType = "包日";
		ptype="天";
		paymentTypeInt = 3;
		pprice = accMul(pday,pcount);
	}else if($("#timeHour").attr('checked')=='checked'){
		pcount = 1;
		ppaymentType = "按需";
		ptype="小时";
		paymentTypeInt = 4;
		pprice = accMul(phour,pcount);
	}
	$("#resultName").html("已选择"+"："+phd+"G硬盘，" +"<span id='payResult'>"+ppaymentType+
			"("+pcount+ptype+")，共计"+pprice+"元</span>");
	$("#hardNum").val(phd);
	$("#finalCount").val(pcount);
	$("#finalPrice").val(pprice);
	$("#paymentType").val(paymentTypeInt);
}
//更改包年等付费方式并计算count的值显示
function changeRadio(pyear, pmonth, pday, phour){
	var pcount = 0;
	var ptype = "";
	var ppaymentType = "";
	var pprice = "";
	var paymentTypeInt = 0;
	if($("#timeYear").attr('checked')=='checked'){
		if($("#yearDiv").html() == ""){
			$("#now").remove();
			$("#yearDiv").html(getTime("year",pyear, pmonth, pday, phour));
		}
		pcount = parseInt($("#count").find("option:selected").val())
		ppaymentType = "包年";
		ptype="年";
		paymentTypeInt = 1;
		pprice = accMul(pyear,pcount);
	}else if($("#timeMonth").attr('checked')=='checked'){
		if($("#monthDiv").html() == ""){
			$("#now").remove();
			$("#monthDiv").html(getTime("month",pyear, pmonth, pday, phour));
		}
		pcount = parseInt($("#count").find("option:selected").val())
		ppaymentType = "包月";
		ptype="月";
		paymentTypeInt = 2;
		pprice = accMul(pmonth,pcount);
	}else if($("#timeDay").attr('checked')=='checked'){
		if($("#dayDiv").html() == ""){
			$("#now").remove();
			$("#dayDiv").html(getTime("day",pyear, pmonth, pday, phour));
		}
		pcount = parseInt($("#count").find("option:selected").val())
		ppaymentType = "包日";
		ptype="天";
		paymentTypeInt = 3;
		pprice = accMul(pday,pcount);
	}else if($("#timeHour").attr('checked')=='checked'){
		$("#now").remove();
		pcount = 1;
		ppaymentType = "按需";
		ptype="小时";
		paymentTypeInt = 4;
		pprice = accMul(phour,pcount);
	}
	$("#paymentType").val(paymentTypeInt);
	$("#finalCount").val(pcount);
	$("#finalPrice").val(pprice);
	$("#payResult").html(ppaymentType+"("+pcount+ptype+")"+"，共计"+pprice+"元 ");
}

function checkAgreement() {
	if($("#agreement").attr("checked")) {
		$("#eagreement").html("");
	} else {
		$("#eagreement").html("您必须同意云海服务条款！");
	}
}
//提交表单，创建新虚拟机
function submitForm(){
	var name = $("#name").val()
	
	if(name.indexOf(" ") > -1){
		$("#error").html("名称不能含空格");
		return;
	}
	if(name.indexOf("'") > -1 || name.indexOf("\"") > -1){
		$("#error").html("名称不能含引号");
		return;
	}
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
	
	//服务条款
	if($("#agreement").attr("checked")) {
		$("#eagreement").html("");
	} else {
		$("#eagreement").html("您必须同意云海服务条款！");
		return;
	}
	
	var zoneId = $("#zoneId").val();
	var clusterId = $("#clusterId").val();
	var size = $("#hardNum").val();
	var count = $("#finalCount").val();
	var times = 0;
	var price = $("#finalPrice").val();
	var paymentType = $("#paymentType").val();
	
	showLoading();
	$.post("hd/newhd",{name:name, zoneId: zoneId, clusterId:clusterId, size:size, description:des,
		paymentType:paymentType, times:times, count:count},function(data){
		hideLoading();
		if(data.result == "success"){
			//gotonext("hd/hdlist");
			window.location.href = "hd/hdlist";
		}else if(data.result == "fail"){
			fillTipBox("error", "未知错误！");
		}else{
			fillTipBox("error", data.result);
		}
	});
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
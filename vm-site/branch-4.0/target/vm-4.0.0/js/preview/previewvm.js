//将tab置为不可点击项
var hdPrice;
var bdPrice;
$(document).ready(function(){
	//限制镜像模板描述、套餐描述的长度
	$("#imgDiv").find("span[class='imgDesc']").each(function(){
		var temp = this.title.replace(new RegExp("；|;","g"),"<br>");
		var imgname = $(this).attr("name");
		if(this.title.length > 30){
			var html = shortOf(temp, 30);
			html += "<a class=\"darkblueletter leftmargin_5\" href=\"vm/description.jsp?des="
				+temp+"\" rel=\"facebox\" title=\""+imgname+"\" size=\"s\">更多</a>";
			$(this).html(html);
		}else{
			$(this).html(temp);
		}
	});

	$("a[rel=facebox]").facebox();
	changeZone();
});

//改变选择的数据中心，相应的要改变集群
function changeZone() {
	var zoneId = $("#zoneId").val();
	showHardwareCon(2);
}

//集群改变，要改变其计费规则
function changeCluster() {
	var clusterId = $("#clusterId").val();
	showHardwareCon(clusterId);
}

function showHardwareCon(clusterId) {
	$.ajax({
		type:"post",
		url:"vm/vmprice",
		data:{clusterid:clusterId}, 
		success:function(data){
			//初始化页面，生成页面元素
			var present = "";
			var packagePrice = data.packagePrice;
			var cpuPrice = data.cpuPrice;
			var memPrice = data.memoryPrice;
			hdPrice = data.harddiskPrice;
			bdPrice = data.bandwidthPrice;
			var yearDiscounts = JSON.parse(JSON.stringify(data.yearDiscounts));
			var monthDiscounts = JSON.parse(JSON.stringify(data.monthDiscounts));
			if(packagePrice != null && packagePrice != ""){
				present +="<div id=\"package\">";
				for (var i = 0; i < packagePrice.length; i++){
					if(i == 0){
						present += "<button class=\"button rightmargin_20 topmargin_5 "+
						"bottommargin_5\" id=\"package_"+i+"\" cpu=\""+packagePrice[i].cpu
						+ "\" mem=\""+packagePrice[i].memory+"\" hd=\""+
						packagePrice[i].harddisk+"\" bd=\""+packagePrice[i].bandwidth+
						"\" hour=\""+packagePrice[i].hourPrice+"\" day=\""+
						packagePrice[i].dayPrice+"\" month=\""+packagePrice[i].monthPrice+
						"\" year=\""+packagePrice[i].yearPrice+"\" yeardiscount=\"" +
						yearDiscounts[packagePrice[i].id]+"\" monthdiscount=\""+
						monthDiscounts[packagePrice[i].id]+"\" des=\""+packagePrice[i].description+
						"\" onclick=\"clickPackage(this)\">"+packagePrice[i].name+"</button>";
					}else{
						present += "<button class=\"graybutton rightmargin_20 topmargin_5 "+
						"bottommargin_5\" id=\"package_"+i+"\" cpu=\""+packagePrice[i].cpu
						+ "\" mem=\""+packagePrice[i].memory+"\" hd=\""+
						packagePrice[i].harddisk+"\" bd=\""+packagePrice[i].bandwidth+
						"\" hour=\""+packagePrice[i].hourPrice+"\" day=\""+
						packagePrice[i].dayPrice+"\" month=\""+packagePrice[i].monthPrice+
						"\" year=\""+packagePrice[i].yearPrice+"\" yeardiscount=\"" +
						yearDiscounts[packagePrice[i].id]+"\" monthdiscount=\""+
						monthDiscounts[packagePrice[i].id]+"\" des=\""+packagePrice[i].description+
						"\" onclick=\"clickPackage(this)\">"+packagePrice[i].name+"</button>";
					}
				}
				present +="<div id='packageDes'></div></div>";
			}
			present += "<div class=\"grey1border padding5 bottommargin_10\" id=\"hdList\">";
			if(cpuPrice != null && cpuPrice!= ""){
				present += "<div class=\"strong leftfloat rightalign rightmargin_20 toppadding_10\" style=\"width:60px;\">CPU</div>"+
			    "<div style=\"margin-left:100px;\" id=\"cpuButton\">";
				for (var i = 0; i < cpuPrice.length; i++){
					present += "<button class=\"graybutton rightmargin_20 topmargin_5 " +
					"bottommargin_5\" id=\"cpu_"+cpuPrice[i].cpu+"\" cpu=\""+cpuPrice[i].cpu+ 
					"\" hour=\""+cpuPrice[i].hourPrice+"\" day=\""+
					cpuPrice[i].dayPrice+"\" month=\""+cpuPrice[i].monthPrice+
					"\" year=\""+cpuPrice[i].yearPrice+"\" onclick=\"clickCpu(this)\">"+
					cpuPrice[i].cpu+"核</button>";
				}
				present += "</div>";
			}
			if(memPrice != null && memPrice!= ""){
			    present += "<div class=\"strong leftfloat rightalign rightmargin_20 toppadding_10\" style=\"width:60px;\">内存</div>"+
			    "<div style=\"margin-left:100px;\" id=\"memButton\">";
			    for (var i = 0; i < memPrice.length; i++){
					present += "<button class=\"graybutton rightmargin_20 topmargin_5" +
					" bottommargin_5\" id=\"mem_"+memPrice[i].memory+"\" mem=\""+memPrice[i].memory+
					"\" hour=\""+memPrice[i].hourPrice+"\" day=\""+
					memPrice[i].dayPrice+"\" month=\""+memPrice[i].monthPrice+
					"\" year=\""+memPrice[i].yearPrice+"\" onclick=\"clickMem(this)\">"+
					memPrice[i].memory+"G</button>";
				}
			    present += "</div>";
			}
			var hdMin = 0;
			var hdLength = 0;
			if(hdPrice != null && hdPrice!= ""){
				hdLength = hdPrice.length;
				hdMin = hdPrice[0].harddisk;
		        present += "<div class=\"strong leftfloat rightalign rightmargin_20 toppadding_10\" style=\"width:60px;\">硬盘</div>"+
		        "<div style=\"margin-left:100px;\" id=\"hdButton\">"+
		        "<span id=\"hd\" class=\"bluesliderbar\" style=\"display:inline-block;width: 380px; margin: 15px 5px 0 2px;\"></span>"+
		        "<a class=\"grayletter strong leftmargin_10 rightmargin_5\" onclick=\"clickHdSub()\"><img src=\"images/sub.png\" align=\"bottom\"></a>" +
		        "<font size=5><span id='hdNum'></span></font>&nbsp;GB"+
		        "<a class=\"grayletter strong leftmargin_5 rightmargin_5\" onclick=\"clickHdAdd()\"><img src=\"images/add.png\" align=\"bottom\"></a>" +
		        "<span class=\"redletter leftpadding_5\" id=\"error2\"></span></div>";
			}
			var bdMax = 0;
			var bdLength = 0;
			if(bdPrice != null && bdPrice != ""){
				bdLength = bdPrice.length;
				bdMin = bdPrice[0].bandwidth;
				present += "<div class=\"strong leftfloat rightalign rightmargin_20 toppadding_10\" style=\"width:60px;\">带宽</div>"+
			    "<div style=\"margin-left:100px;\" id=\"bdButton\">"+
			    "<span id=\"bd\" class=\"bluesliderbar\" style=\"display:inline-block;width: 380px; margin: 15px 5px 0 2px;\"></span>"+
			    "<a class=\"grayletter strong leftmargin_10 rightmargin_5\" onclick=\"clickBdSub()\"><img src=\"images/sub.png\" align=\"bottom\"></a>" +
			    "<font size=5><span id='bdNum'></span></font>&nbsp;MB"+
			    "<a class=\"grayletter strong leftmargin_5 rightmargin_5\" onclick=\"clickBdAdd()\"><img src=\"images/add.png\" align=\"bottom\"></a>"+
			    "<span class=\"greenletter leftpadding_5\" id=\"warning1\"></span></div>";
			}
			present += "</div><div id=\"payList\"></div>"+
		    "<div id=\"resultName\" class=\"darkblueletter bottommargin_20 strong\">当前未选中任何套餐</div>";
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
			$("#bd").slider({
		        orientation: "horizontal",
		        range: "min",
		        step: 1,
		        min: 0,
		        max: bdLength-1,
		        value: 0,
		        animate: true,
		        slide: showValueBd,
		        stop: showValueBd 
		    });
			$('#hdNum').html(hdMin);
			$('#bdNum').html(bdMin);
			
			if(bdMin<=0){
				$("#warning1").html("</br>带宽为0MB不分配公网IP");
			}else{
				$("#warning1").html("");
			}
			
			//为页面元素加样式和值，默认加载最低配的套餐
			$("#package .button").click();
			
			imgChecked();
			
		}
	});
}

//根据hd、bd的值找到对应的index
function findHd(hd){
	for(var i = 0; i < hdPrice.length; i ++){
		if(hdPrice[i].harddisk == hd){
			return i;
		}
	}
	return 0;
}
function findBd(bd){
	for(var i = 0; i < bdPrice.length; i ++){
		if(bdPrice[i].bandwidth == bd){
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
	//给按钮换颜色，去掉套餐的按钮颜色和套餐说明
	$("#package .button").removeClass("button").addClass("graybutton");
	$("#packageDes").html("");
	$("#packageDes").html("").css("margin-top","19px");
	//判断是否为套餐，若是套餐按套餐要求更改值
	var pcpu = $("#cpuButton .button").attr("cpu");
	var pmem = $("#memButton .button").attr("mem");
	var pbd = $('#bdNum').html();
	var bdI = findBd(pbd);
	var flag = false;
	$("#package button").each(function(i){
		if($(this).attr("cpu") == pcpu && $(this).attr("mem") == pmem 
				&& $(this).attr("hd") == phd && $(this).attr("bd") == pbd){
			clickPackage(this);
			flag = true;
		}
	});
	//如果不满足套餐，则计算价格修改说明
	if(!flag){
		changeResult("vm", pcpu, pmem, phd, pbd, hdI, bdI, null);
	}
	
	var imgIso=$('input:radio[name="imgIso"]:checked').val();
	if(imgIso=='img'){
	var hdNum = $("#hardNum").val();
	var imgMindisk=$("#imgMindisk").val();
	var temp=hdNum-imgMindisk;//直接比较临界值有误？？
	if(temp<0){
		$("#error2").html("</br>您选择的镜像模板需要更大的硬盘");
	}else{
		$("#error2").html("");
	}
	}
}
function showValueBd(){
	//更改slider的值
	var bdI = $("#bd").slider('value');
	var pbd = bdPrice[bdI].bandwidth;
	$("#bdNum").html(pbd);
	//给按钮换颜色，去掉套餐的按钮颜色和套餐说明
	$("#package .button").removeClass("button").addClass("graybutton");
	$("#packageDes").html("").css("margin-top","19px");
	//判断是否为套餐，若是套餐按套餐要求更改值
	var pcpu = $("#cpuButton .button").attr("cpu");
	var pmem = $("#memButton .button").attr("mem");
	var phd = $('#hdNum').html();
	var hdI = findHd(phd);
	var flag = false;
	$("#package button").each(function(i){
		if($(this).attr("cpu") == pcpu && $(this).attr("mem") == pmem 
				&& $(this).attr("hd") == phd && $(this).attr("bd") == pbd){
			clickPackage(this);
			flag = true;
		}
	});
	//如果不满足套餐，则计算价格修改说明
	if(!flag){
		changeResult("vm", pcpu, pmem, phd, pbd, hdI, bdI, null);
	}
	
	if(pbd<=0){
		$("#warning1").html("</br>带宽为0MB不分配公网IP");
	}else{
		$("#warning1").html("");
	}
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
function clickBdSub(){
	var bdI = $("#bd").slider('value');
	if(bdI == 0){
		$("#bd").slider('value', bdI);
	}else{
		$("#bd").slider('value', bdI-1);
	}
	showValueBd();
}
function clickBdAdd(){
	var bdI = $("#bd").slider('value');
	if(bdI == bdPrice.length -1){
		$("#bd").slider('value', bdI);
	}else{
		$("#bd").slider('value', bdI+1);
	}
	showValueBd();
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
function changeResult(type, pcpu, pmem, phd, pbd, hdI, bdI, obj){
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
	if(type == "vm"){
		$("#finalType").val("vm");
		phour = parseFloat($("#cpuButton .button").attr("hour"));
		pday = parseFloat($("#cpuButton .button").attr("day"));
		pmonth = parseFloat($("#cpuButton .button").attr("month"));
		pyear = parseFloat($("#cpuButton .button").attr("year"));
		
		phour += parseFloat($("#memButton .button").attr("hour"));
		pday += parseFloat($("#memButton .button").attr("day"));
		pmonth += parseFloat($("#memButton .button").attr("month"));
		pyear += parseFloat($("#memButton .button").attr("year"));
		
		phour += parseFloat(hdPrice[hdI].hourPrice);
		pday += parseFloat(hdPrice[hdI].dayPrice);
		pmonth += parseFloat(hdPrice[hdI].monthPrice);
		pyear += parseFloat(hdPrice[hdI].yearPrice);

		phour += parseFloat(bdPrice[bdI].hourPrice);
		pday += parseFloat(bdPrice[bdI].dayPrice);
		pmonth += parseFloat(bdPrice[bdI].monthPrice);
		pyear += parseFloat(bdPrice[bdI].yearPrice);
		
		phour = Number(phour).toFixed(2);
		pday = Number(pday).toFixed(2);
		pmonth = Number(pmonth).toFixed(2);
		pyear = Number(pyear).toFixed(2);
		
		var pyeardiscount = (Number(pyear/(pday*yearDays))).toFixed(1);
		var pmonthdiscount = (Number(pmonth/(pday*monthDays))).toFixed(1);
		var present = "<input type=\"radio\" name=\"time\" id=\"timeYear\" checked='checked' " +
		"onclick=\"changeRadio('"+pyear+"','"+pmonth+"','"+pday+"','"+phour+"')\"/>"+
		"<label for=\"timeYear\"><span id='yearPay'></span></label>" +
		"<span id=\"yearDiv\"></span>"+
		"<input type=\"radio\" name=\"time\" id=\"timeMonth\" onclick=\"changeRadio('"+
		pyear+"','"+pmonth+"','"+pday+"','"+phour+"')\" />"+
		"<label for=\"timeMonth\"><span id='monthPay'></span></label>" +
		"<span id=\"monthDiv\"></span>"+
		"<input type=\"radio\" name=\"time\" id=\"timeDay\" onclick=\"changeRadio('"+
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
			pprice = accMul(pday,pcount);
			paymentTypeInt = 3;
		}else if($("#timeHour").attr('checked')=='checked'){
			pcount = 1;
			ppaymentType = "按需";
			ptype="小时";
			pprice = accMul(phour,pcount);
			paymentTypeInt = 4;
		}
		$("#resultName").html("已选择："+pcpu+"核CPU，"+pmem+"G内存，"+phd+"G硬盘，" +
				pbd+"M带宽，<span id='payResult' class='strong'>"+ppaymentType+"("+pcount+ptype+")"+
				"，共计"+pprice+"元</span>");
	}else{
		$("#finalType").val("vmpackage");
		pname= $(obj).text();
		pyear = parseFloat($(obj).attr("year"));
		var pyeardiscount = parseFloat($(obj).attr("yeardiscount"));
		pmonth = parseFloat($(obj).attr("month"));
		var pmonthdiscount = parseFloat($(obj).attr("monthdiscount"));
		pday = parseFloat($(obj).attr("day"));
		phour = parseFloat($(obj).attr("hour"));
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
			pprice = accMul(phour,pcount);
			paymentTypeInt = 4;
		}
		$("#resultName").html("已选择("+pname+")："+pcpu+"核CPU，"+pmem+"G内存，"+phd+"G硬盘，" +
				pbd+"M带宽，<span id='payResult' class='strong'>"+ppaymentType+"("+pcount+ptype+")"+
				"，共计"+pprice+"元</span>");
	}
	$("#cpuNum").val(pcpu);
	$("#memNum").val(pmem);
	$("#hardNum").val(phd);
	$("#bandNum").val(pbd);
	$("#finalCount").val(pcount);
	$("#finalPrice").val(pprice);
	$("#paymentType").val(paymentTypeInt);
	$("#packageName").val(pname);
}

//点击套餐按钮时的动作
function clickPackage(obj){
	//给套餐按钮加颜色
	$("#package .button").removeClass("button").addClass("graybutton");
	$(obj).removeClass("graybutton").addClass("button");
	$("#packageDes").html($(obj).attr("des")).css("margin-top","0");
	var pcpu = $(obj).attr("cpu");
	var pmem = $(obj).attr("mem");
	var phd = $(obj).attr("hd");
	var pbd = $(obj).attr("bd");
	//给各个硬件配置按钮加颜色
	$("#cpuButton button").removeClass("button").addClass("graybutton");
	$("#cpu_"+pcpu).removeClass("graybutton").addClass("button");
	$("#memButton button").removeClass("button").addClass("graybutton");
	$("#mem_"+pmem).removeClass("graybutton").addClass("button");
	$('#hd').slider('value',findHd(phd));
	$('#hdNum').html(phd);
	
	
	$('#bd').slider('value',findBd(pbd));
	$('#bdNum').html(pbd);
	
	
	//更改计算结果
	changeResult("vmpackage", pcpu, pmem, phd, pbd, 0, 0, obj);
	
	//带宽提示信息更新
	if(pbd<=0){
		$("#warning1").html("</br>带宽为0MB不分配公网IP");
	}else{
		$("#warning1").html("");
	}
	
	//硬盘提示信息更新
	var imgIso=$('input:radio[name="imgIso"]:checked').val();
	if(imgIso=='img'){
	var hdNum = $("#hardNum").val();
	var imgMindisk=$("#imgMindisk").val();
	var temp=hdNum-imgMindisk;//直接比较临界值有误？？
	if(temp<0){
		$("#error2").html("</br>您选择的镜像模板需要更大的硬盘");
	}else{
		$("#error2").html("");
	}
	}
}

//点击cpu按钮时的动作
function clickCpu(obj){
	//给按钮换颜色，去掉套餐的按钮颜色和套餐说明
	$("#package .button").removeClass("button").addClass("graybutton");
	$("#packageDes").html("").css("margin-top","19px");
	$("#cpuButton .button").removeClass("button").addClass("graybutton");
	$(obj).removeClass("graybutton").addClass("button");
	
	//判断是否为套餐，若是套餐按套餐要求更改值
	var pcpu = $(obj).attr("cpu");
	var pmem = $("#memButton .button").attr("mem");
	var phd = $('#hdNum').html();
	var pbd = $('#bdNum').html();
	var hdI = findHd(phd);
	var bdI = findBd(pbd);
	var flag = false;
	$("#package button").each(function(i){
		if($(this).attr("cpu") == pcpu && $(this).attr("mem") == pmem 
				&& $(this).attr("hd") == phd && $(this).attr("bd") == pbd){
			clickPackage(this);
			flag = true;
		}
	});
	
	//如果不满足套餐，则计算价格修改说明
	if(!flag){
		changeResult("vm", pcpu, pmem, phd, pbd, hdI, bdI, null);
	}
}

//点击内存按钮时的动作
function clickMem(obj){
	//给按钮换颜色，去掉套餐的按钮颜色和套餐说明
	$("#package .button").removeClass("button").addClass("graybutton");
	$("#packageDes").html("").css("margin-top","19px");
	$("#memButton .button").removeClass("button").addClass("graybutton");
	$(obj).removeClass("graybutton").addClass("button");
	
	//判断是否为套餐，若是套餐按套餐要求更改值
	var pcpu = $("#cpuButton .button").attr("cpu");
	var pmem = $(obj).attr("mem");
	var phd = $('#hdNum').html();
	var pbd = $('#bdNum').html();
	var hdI = findHd(phd);
	var bdI = findBd(pbd);
	var flag = false;
	$("#package button").each(function(i){
		if($(this).attr("cpu") == pcpu && $(this).attr("mem") == pmem 
				&& $(this).attr("hd") == phd && $(this).attr("bd") == pbd){
			clickPackage(this);
			flag = true;
		}
	});
	
	//如果不满足套餐，则计算价格修改说明
	if(!flag){
		changeResult("vm", pcpu, pmem, phd, pbd, hdI, bdI, null);
	}
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

//模板：选择img还是iso
function imgChecked(){
	$("#imgDiv").show();
	$("#isoDiv").hide();
}

function isoChecked(){
	
	$("#isoDiv").show();
	$("#imgDiv").hide();
	$("#error2").html("");
}

//改变选择的img镜像，更新相应描述信息
function changeImg(imgId,imgMindisk){
	if($("#imgId").val() != imgId){
		var imgName = $("#imgName_"+imgId).html();
		$("#imgId").val(imgId);

		$("#imgName").html("已选择："+imgName);
		$("#imgError").html("");
		$("#imgMindisk").val(imgMindisk);
		//更改按鈕圖片
		$("#imgDiv button img").attr("src","images/select.png");
		$("#"+imgId+"_pic").attr("src","images/select_white.png");
		//更改按鈕顏色
		$("#imgDiv button").removeClass("sbluebutton").addClass("sgraybutton");
		$("#"+imgId+"_botton").attr("class","sbluebutton");
	}
	
	var hdNum = $("#hardNum").val();
	var imgMindisk=$("#imgMindisk").val();
	var temp=hdNum-imgMindisk;//直接比较临界值有误？？
	if(temp<0){
		$("#error2").html("</br>您选择的镜像模板需要更大的硬盘");
	}else{
		$("#error2").html("");
	}
	
}

//改变选择的防火墙规则，更新相应描述信息
function changeSecurityGroups(){
	var secId = $("#securityId").find("option:selected").val();
	$("#secIntro").html($("#secIntro"+secId).html());
}

//在newvm.jsp页面点击“确定”，检查各项参数的合法性
function showvm(url){
	var domain = $("#domain").val();
	if(confirm("请先登录")){
		window.location.href=domain;
	}
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
		var imgIsoHtml="(手动安装) 申请云主机完成后，手动从光驱安装iso文件";
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
	/*$("#firewall").toggleClass("hidden");*/
	$("#idc").toggleClass("hidden");
	$("#showAdvancedSet").toggleClass("hidden");
	$("#hideAdvancedSet").toggleClass("hidden");
}
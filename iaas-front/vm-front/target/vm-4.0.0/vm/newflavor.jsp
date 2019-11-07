<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<div class="bottommargin_5"> 
当前配置：<s:if test="kind == 'flavor'"><s:property value="flavor.vcpus"/>核CPU，<s:property value="flavor.ram"/>G内存</s:if>
<s:elseif test="kind == 'netWork'"><s:property value="bdw"/>M带宽</s:elseif><br/>
选择新配置：
</div>

<input type="hidden" id="oldHdNum" value="<s:property value="flavor.disk"/>"/>
<input type="hidden" id="oldCpuNum" value="<s:property value="flavor.vcpus"/>"/>
<input type="hidden" id="oldMemNum" value="<s:property value="flavor.ram"/>"/>
<input type="hidden" id="oldBandNum" value="<s:property value="bdw"/>"/>
<input type="hidden" id="serverId" value="<s:property value="serverId"/>"/>
<input type="hidden" id="serverName" value="<s:property value="serverName"/>"/>
<input type="hidden" id="description" value="<s:property value="description"/>" />
<input type="hidden" id="kind" value="<s:property value="kind"/>" />
<table class="formtable">
<s:if test="kind == 'flavor'">
<tr>
	<td width="80px">CPU</td>
	<td><select class="selectboxsmall" id="cpuId" onchange="changeCount();">
			<s:iterator id="price" value="cpuPrice" status="st">
				<s:if test="#price.cpu == flavor.vcpus">
				<option selected="selected" value="<s:property value="#price.cpu"/>" 
				day="<s:property value="#price.dayPrice"/>" 
				month="<s:property value="#price.monthPrice"/>" 
                year="<s:property value="#price.yearPrice"/>" 
                hour="<s:property value="#price.hourPrice"/>">
				<s:property value="#price.cpu"/>核</option>
				</s:if>
				<s:else>
				<option  value="<s:property value="#price.cpu"/>" 
				day="<s:property value="#price.dayPrice"/>" 
				month="<s:property value="#price.monthPrice"/>" 
                year="<s:property value="#price.yearPrice"/>" 
                hour="<s:property value="#price.hourPrice"/>">
				<s:property value="#price.cpu"/>核</option>
				</s:else>
			</s:iterator>
		</select>
	</td>
</tr>
<tr>
	<td>内存</td>
	<td><select class="selectboxsmall" id="memId" onchange="changeCount();">
			<s:iterator id="price" value="memoryPrice" status="st">
			<s:if test="#price.memory == flavor.ram">
				<option selected="selected" value="<s:property value="#price.memory"/>"
				day="<s:property value="#price.dayPrice"/>" 
                month="<s:property value="#price.monthPrice"/>" 
                year="<s:property value="#price.yearPrice"/>" 
                hour="<s:property value="#price.hourPrice"/>" > 
				<s:property value="#price.memory"/>G</option>
			</s:if>
			<s:else>
				<option value="<s:property value="#price.memory"/>"
				day="<s:property value="#price.dayPrice"/>" 
                month="<s:property value="#price.monthPrice"/>" 
                year="<s:property value="#price.yearPrice"/>" 
                hour="<s:property value="#price.hourPrice"/>" > 
				<s:property value="#price.memory"/>G</option>
			</s:else>
			</s:iterator>
		</select>
	</td>
</tr>
</s:if>
<s:elseif test="kind == 'netWork'">
<tr>
	<td>带宽</td>
	<td>
	<select class="selectboxsmall" id="bandId"  onchange="changeCount();">
			<s:iterator id="price" value="bandwidthPrice" status="st">
			<s:if test="#price.bandwidth == bdw">
				<option selected="selected" value="<s:property value="#price.bandwidth"/>" 
				day="<s:property value="#price.dayPrice"/>" 
                month="<s:property value="#price.monthPrice"/>" 
                year="<s:property value="#price.yearPrice"/>" 
                hour="<s:property value="#price.hourPrice"/>" >
				<s:property value="#price.bandwidth"/>M</option>
			</s:if>
			<s:else>
				<option  value="<s:property value="#price.bandwidth"/>" 
				day="<s:property value="#price.dayPrice"/>" 
                month="<s:property value="#price.monthPrice"/>" 
                year="<s:property value="#price.yearPrice"/>" 
                hour="<s:property value="#price.hourPrice"/>" >
				<s:property value="#price.bandwidth"/>M</option>
			</s:else>
			</s:iterator>
	</select>
		<span id="bandwidthTip"></span>
	</td>
</tr>
</s:elseif>
<tr>
	<td>价格</td>
	<td><span id="priceResult"></span></td>
</tr><%-- <tr>
    <td>差价：</td>
    <td><span id="result"></span></td>
</tr> --%>
<tr>
<s:if test="kind == 'flavor'">
	<td colspan="2">
			<div class="topmargin_10 strong lightyellowbox" style="height:25px;padding-top: 5px;"><img class="rightmargin_5 leftmargin_5" src="images/notice.png" align="absmiddle"/>注意：本操作将需要关闭虚拟机！</div>
	</td>
</s:if>
<s:else>
<td colspan="2"></td>
</s:else>
</tr>
<tr>
	<td></td>
	<td><br/><button class="button rightmargin_10" onclick="submitFlavor()">确定</button>
		<button class="greybutton" onclick="closeFacebox()">取消</button>
	</td>
</tr></table>
<script>
function submitFlavor(){
	var kind = $("#kind").val();
	var serverId = $("#serverId").val();
	var serverName = $("#serverName").val();
	var serverDescription = $("#description").val();
	var oldHdNum = $("#oldHdNum").val();
	var oldCpuNum = $("#oldCpuNum").val();
	var oldMemNum = $("#oldMemNum").val();
    var oldBandNum = $("#oldBandNum").val();

    if(kind == 'flavor'){
	    var cpuNum =$("#cpuId").find('option:selected').val();
		var memNum = $("#memId").find('option:selected').val();
		if(cpuNum == oldCpuNum && memNum == oldMemNum){
			fillTipBox("success","没有进行修改");
			hideLoading();
			closeFacebox();
			return 0;
		}
	    param = {
	    		serverId:serverId,
	    		serverName:serverName,
	    		description:serverDescription,
	    		oldHdNum:oldHdNum,
	    		oldCpuNum:oldCpuNum,
	    		oldMemNum:oldMemNum,
	    		oldBandNum:oldBandNum,
	    		cpuNum:cpuNum,
	    		memNum:memNum,
	    		kind:kind
	    }
	}else if(kind == 'netWork'){
		var bandNum = $("#bandId").find('option:selected').val();
		if(bandNum == oldBandNum ){
			fillTipBox("success","没有进行修改");
			hideLoading();
			closeFacebox();
			return 0;
		}
		param = {
	    		serverId:serverId,
	    		serverName:serverName,
	    		description:serverDescription,
	    		oldHdNum:oldHdNum,
	    		oldCpuNum:oldCpuNum,
	    		oldMemNum:oldMemNum,
	    		oldBandNum:oldBandNum,
	    		bandNum:bandNum,
	    		kind:kind
	    }
		
	}
	
	
	//alert(cpuNum+" "+memNum+" "+bandNum);
	 showLoading();
	 closeFacebox();
	$.post("vm/newflavor",param,function(data){
			hideLoading();
			//if(data.result == "success"){
				fillTipBox("success","修改配置成功");
				location.href="vm/vmdetail?serverId="+param.serverId;
			//}else if(data.result == "timeout"){
			//	fillTipBox("error","云主机已过期");
			// }else if(data.result == "fail"){
		//		fillTipBox("error","未知错误");
		//	}else{
		//		fillTipBox("error",data.result);
		//	}
	}); 
	
}

$(function(){
	//
	changeCount();
});

function changeCount(){
	//带宽提示
	var oldBandNum = $("#oldBandNum").val();
	var kind = $("#kind").val();
	var bandNum = $("#bandId").find('option:selected').val();
	if (oldBandNum == 0 && bandNum > 0) {
		$("#bandwidthTip").html("<font color=red>您将获得一个公网IP</font>");
	} else if (oldBandNum > 0 && bandNum == 0) {
		$("#bandwidthTip").html("<font color=red>您的公网IP将被释放</font>");
	} else if (oldBandNum > 0 && bandNum > 0){
		$("#bandwidthTip").html("");
	} else if (oldBandNum == 0 && bandNum == 0){
		$("#bandwidthTip").html("");
	}
	
	//计算机价格
	var dayPrice = 0.0;
    var monthPrice = 0.0;
    var yearPrice = 0.0;
    var hourPrice = 0.0;
    
    if(kind == 'flavor'){
	    hourPrice = parseFloat($("#cpuId").find('option:selected').attr("hour"));
	    dayPrice = parseFloat($("#cpuId").find('option:selected').attr("day"));
	    monthPrice = parseFloat($("#cpuId").find('option:selected').attr("month"));
	    yearPrice = parseFloat($("#cpuId").find('option:selected').attr("year"));
	
	    hourPrice += parseFloat($("#memId").find('option:selected').attr("hour"));
	    dayPrice += parseFloat($("#memId").find('option:selected').attr("day"));
	    monthPrice += parseFloat($("#memId").find('option:selected').attr("month"));
	    yearPrice += parseFloat($("#memId").find('option:selected').attr("year"));
    }else{
	    hourPrice = parseFloat($("#bandId").find('option:selected').attr("hour"));
	    dayPrice = parseFloat($("#bandId").find('option:selected').attr("day"));
	    monthPrice = parseFloat($("#bandId").find('option:selected').attr("month"));
	    yearPrice = parseFloat($("#bandId").find('option:selected').attr("year"));
    }
    $("#priceResult").html(hourPrice.toFixed(2)+"元/小时，"+dayPrice.toFixed(2)
    		+"元/天，"+monthPrice.toFixed(2)+"元/月，"+yearPrice.toFixed(2)+"元/年");
}

</script>
</body>
</html>
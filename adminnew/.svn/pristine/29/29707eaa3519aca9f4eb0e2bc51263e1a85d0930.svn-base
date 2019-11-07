<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<div class="bottommargin_5"> 
当前配置：公网带宽：<s:property value="mbdw"/>M 内网带宽：<s:property value="pbdw"/><br/>
选择新配置：
</div>

<input type="hidden" id="oldMaxBandNum" value="<s:property value="mbdw"/>"/>
<input type="hidden" id="oldPriBandNum" value="<s:property value="pbdw"/>"/>
<input type="hidden" id="serverId" value="<s:property value="serverId"/>"/>
<input type="hidden" id="userId" value="<s:property value="tenantId"/>"/>
<input type="hidden" id="clusterId" value="<s:property value="clusterId"/>"/>
<input type="hidden" id="serverName" value="<s:property value="serverName"/>"/>
<input type="hidden" id="kind" value="<s:property value="kind"/>" />
<input type="hidden" id="vmUserId" value="<s:property value="vmUserId"/>" />
<table class="formtable">
<tr>
	<td width="60px">公网带宽</td>
	<td width="200px">
	<select class="selectboxsmall" id="mbandId"  onchange="changeCount();">
			<s:iterator id="price" value="bandwidthPrice" status="st">
			<s:if test="#price.bandwidth == mbdw">
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
		<span class=" leftmargin_10">
			<span id="maxBandwidthTip" class=" hidden">
				释放公网IP<input type="checkbox"  id="mbandRelease" value="释放公网IP" />
			</span>
		</span>
	</td>
</tr>
<tr>
	<td width="60px">内网带宽</td>
	<td width="200px">
	<select class="selectboxsmall" id="pbandId"  onchange="changeCount();">
			<s:iterator id="price" value="bandwidthPrice" status="st">
			<s:if test="#price.bandwidth == pbdw">
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
			<s:if test="'30' == pbdw">
				<option selected="selected" value="30" >30M</option>
			</s:if>
			<s:else>
				<option  value="30" >30M</option>
			</s:else>
	</select>
		<span class=" leftmargin_10">
			<span id="priBandwidthTip" class=" hidden">
				释放内网IP<input type="checkbox"  id="pbandReleasse" value="释放内网IP" />
			</span>
		</span>
	</td>
</tr>
<tr>
	<td></td>
	<td><span id="priceResult"></span></td>
</tr>
<tr>
<td colspan="2"></td>
</tr>
<tr>
	<td></td>
	<td><br/><button class="button rightmargin_10" onclick="submitFlavor()">确定</button>
		<button class="greybutton" onclick="closeFacebox()">取消</button>
	</td>
</tr></table>
<script>
//关闭facebox浮层
function closeFacebox(){
	$(document).trigger('close.facebox');
}
function submitFlavor(){
	var kind = $("#kind").val();
	var userId = $("#userId").val();
	var serverId = $("#serverId").val();
	var clusterId = $("#clusterId").val();
	var serverName = $("#serverName").val();
    var oldMaxBandNum = $("#oldMaxBandNum").val();
	var oldPriBandNum = $("#oldPriBandNum").val();
	var mbandNum = $("#mbandId").find('option:selected').val();
	var pbandNum = $("#pbandId").find('option:selected').val();
	var vmUserId = $("#vmUserId").val();
	//当修改带宽为0时，是否释放带宽的标识 
	var mbandRelease = "true";
	var pbandReleasse = "true";
	if(mbandNum == oldMaxBandNum && pbandNum == oldPriBandNum){
		fillTipBox("success","没有进行修改");
		hideLoading();
		closeFacebox();
		return 0;
	}
	// var confirmMsg = (operation == "rollback") ? "确认进行回滚操作吗？(注意：该操作将关闭云主机！！)" : "确认删除该快照吗？";
   // if(confirm(confirmMsg)){
   /* if( mbandNum != oldMaxBandNum && mbandNum == '0'){
		console.log("turn maxBand to zero");   		
	   if(!confirm("是否释放公网IP？")){
		   mbandRelease = "false";
	   }
   
   }
   
   if( pbandNum != oldPriBandNum && pbandNum == '0'){
	   console.log("turn maxBand to zero");   
	   if(!confirm("是否释放私网IP？")){
		   pbandReleasse = "false";
	   }
   
   } */
   if($("#mbandRelease").attr("checked") != "checked"){
	   mbandRelease = "false";
   }
   if($("#pbandReleasse").attr("checked") != "checked"){
	   pbandReleasse = "false";
   }
   param = {
    		serverId:serverId,
    		serverName:serverName,
    		oldMaxBandNum:oldMaxBandNum,
    		oldPriBandNum:oldPriBandNum,
			mbandNum:mbandNum,
			pbandNum:pbandNum,
    		kind:kind,
    		vmUserId:vmUserId,
    		mbandRelease:mbandRelease,
    		pbandReleasse:pbandReleasse
    }
		
	console.log("mbandRelease:"+mbandRelease);
	console.log("pbandReleasse:"+pbandReleasse);
	console.log(oldMaxBandNum+"-->"+mbandNum+"   "+oldPriBandNum+"-->"+pbandNum);
	closeFacebox(); 
	showLoading();
	 
	   $.post("vm/newflavor",param,function(data){
			hideLoading();
			//console.log("result:"+data.oldHdNum);
			//if(data.result == "success"){
				fillTipBox("success","修改配置成功");
				location.href="vm/vmdetail?userId="+userId+"&serverId="+param.serverId+"&clusterId="+clusterId;
			/* 	
			 }else if(data.result == "timeout"){
				fillTipBox("error","云主机已过期");
			}else if(data.result == "fail"){
				fillTipBox("error","未知错误");
			}else{
				fillTipBox("error",data.result);
			}  */
	});  
	//hideLoading();
}

$(function(){
	//changeCount();
});

function changeCount(){
	//带宽提示
	var oldMaxBandNum = $("#oldMaxBandNum").val();
	var oldPriBandNum = $("#oldPriBandNum").val();
	var mbandNum = $("#mbandId").find('option:selected').val();
	var pbandNum = $("#pbandId").find('option:selected').val();
	
	$("#maxBandwidthTip").addClass("hidden");
	$("#priBandwidthTip").addClass("hidden");
	
	if(oldMaxBandNum > 0 && mbandNum == 0){
		$("#maxBandwidthTip").removeClass("hidden");
	}
	if(oldPriBandNum > 0 && pbandNum == 0){
		$("#priBandwidthTip").removeClass("hidden");
	}
	
}

</script>
</body>
</html>
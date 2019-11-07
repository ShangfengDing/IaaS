<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<input type="hidden" id="serverId" value="<s:property value="serverId"/>"/>
<table class="formtable"><tr>
	<td>防火墙规则</td>
	<td><select class="selectbox" id="groupId">
		<s:iterator id="group" value="groups">
			<option value="<s:property value="#group.id"/>"><s:property value="#group.name"/></option>
		</s:iterator>
		</select>
	</td>
</tr><tr>
	<td colspan="2">
		<div class="topmargin_10 strong lightyellowbox" style="height:25px;padding-top: 5px;"><img class="rightmargin_5 leftmargin_5" src="images/notice.png" align="absmiddle"/>注意：本操作将需要关闭云主机！</div>
	</td>
</tr><tr>
	<td></td>
	<td><br/><button class="button rightmargin_10" onclick="submitFw()">确定</button>
		<button class="greybutton" onclick="closeFacebox()">取消</button>
	</td>
</tr></table>
<script>
//选中原来的防火墙设置
$(function(){
	var groupId = "${param.groupId}";
	$("#groupId").val(groupId);
});

function submitFw(){
	var serverId = $("#serverId").val();
	var groupId = $("#groupId").val();
	var groupName = $("#groupId").find("option:selected").text();
	showLoading();
	$.post("vm/newfw",{serverId:serverId,groupId:groupId,groupName:groupName},function(data){
		closeFacebox();
		hideLoading();
        if(data.result == true){
			fillTipBox("success","修改防火墙配置成功");
			location.reload();
		} else {
			fillTipBox("error","修改防火墙配置失败");
		}
	});
}
</script>
</body>
</html>
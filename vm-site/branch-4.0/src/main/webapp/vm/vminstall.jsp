<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<table class="formtable"><tr>
	<td>选择iso安装</td>
	<td><select id="isoId" class="selectbox" onchange="changeIso()">
			<s:iterator id="iso" value="isos">
				<option value="<s:property value='#iso.id'/>">
					<s:property value="#iso.metadata['displayName']"/>
				</option>
			</s:iterator>
		</select>
	</td>
</tr><tr>
	<td><s:iterator id="iso" value="isos">
		<span id="isoIntro<s:property value='#iso.id'/>" class="hidden">
			<s:property value="#iso.metadata['displayDescription']"/>
		</span>
		</s:iterator>
	</td>
	<td id="isoIntro"><s:property value="isos[0].metadata['displayDescription']"/></td>
</tr><tr>
	<td colspan="2">
		<div class="topmargin_10 strong lightyellowbox" style="height:25px;padding-top: 5px;"><img class="rightmargin_5 leftmargin_5" src="images/notice.png" align="absmiddle"/>注意：本操作将需要关闭云主机！</div>
	</td>
</tr><tr>
	<td></td>
	<td><br/><button class="button rightmargin_10" onclick="submit()">确定</button>
		<button class="greybutton" onclick="closeFacebox()">取消</button>
	</td>
</tr></table>

<script>
//改变iso文件的描述
function changeIso(){
	var isoId = $("#isoId").val();
	$("#isoIntro").html($("#isoIntro"+isoId).html());
}

//提交表单
function submit(){
	var serverId = "${param.uuid}";
	var serverName = "${param.name}";
	var isoUuid = $("#isoId").val();
	var isoName = $("#isoId").find("option:selected").text();
	showLoading();
	$.post("vm/vminstall",{serverId:serverId,isoUuid:isoUuid,serverName:serverName,isoName:isoName},function(){
		hideLoading();
		closeFacebox();
		location.reload();
	});
}
</script>
</body>
</html>
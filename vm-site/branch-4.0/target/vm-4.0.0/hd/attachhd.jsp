<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>
<table>
<tr>
	<td>选择云主机</td>
	<td><select class="selectbox" id="selectedServer">
		<s:iterator id="server" value="servers">
			<option value="<s:property value='#server.id'/>"><s:property value='#server.name'/></option>
		</s:iterator>
	    </select>
   	</td>
</tr>
<tr>
	<td colspan="2">
		<div class="topmargin_10 strong lightyellowbox" style="height:25px;padding-top: 5px;"><img class="rightmargin_5 leftmargin_5" src="images/notice.png" align="absmiddle"/>注意：本操作将需要关闭云主机！</div>
	</td>
</tr><tr>
	<td></td>
	<td><br/>
	    <button class="button rightmargin_10" onclick="hdattach()">确定</button>
	  	<button class="graybutton" onclick="closeFacebox()">取消</button>
    </td>
</tr>
</table>
<script>
function hdattach(){
	var hdUuid = '${param.hduuid}';
	var hdName = '${param.hdname}';
	var serverId = $("#selectedServer").val();
	var serverName = $("#selectedServer").find("option:selected").text();
	
	showLoading();
	$.post("hd/hdoperate",{operation:"attach",hdUuid:hdUuid,serverId:serverId,serverName:serverName,hdName:hdName},function(data){
		hideLoading();
		closeFacebox();
		if(data.result == true){
			fillTipBox("success","操作成功！");
			setTimeout(function(){window.location.reload();}, 500);
		}
		else if(data.expired == true) {
			fillTipBox("error","硬盘已过期！");
		}
		else {
			fillTipBox("error","操作失败！");
		}
	});
}
</script>
</body>
</html>
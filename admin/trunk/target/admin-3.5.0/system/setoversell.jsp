<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<form action="system/setoversell" method="post" onsubmit="return checkAll();">
<input name="clusterId" type="text" value="${param.id}" class="hidden"/>
<table>
	<tr>
		<td class="blueletter rightalign rightpadding_10">CPU(%)</td>
		<td><input type="text" class="bottommargin_5" id="cpu" name="cpu" style="width:200px" />
		<span class="redletter leftmargin_5" id="error1"></span></td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">内存(%)</td>
		<td><input type="text" class="bottommargin_5" id="memory" name="memory" style="width:200px" />
		<span class="redletter leftmargin_5" id="error2"></span></td>
	</tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">硬盘(%)</td>
		<td><input type="text" class="bottommargin_5" id="disk" name="disk" style="width:200px" />
		<span class="redletter leftmargin_5" id="error3"></span></td>
	</tr>
	<tr>
		<td></td>
		<td>
			<input class="button" type="submit" value="确定"/>
			<button class="greybutton" onclick="facebox_close();return false;">取消</button>
		</td><td></td>
	</tr>
</table>
</form>
<script>
function checkAll(){
	var reg = /^[0-9]*[1-9][0-9]*$/;
 	if(!reg.test($("#cpu").val()) ||  parseInt($("#cpu").val()) < 100) {
		$("#error1").html("格式有误");
		return false;
	}
 	if(parseInt($("#cpu").val()) < 100) {
		$("#error1").html("必须大于100");
		return false;
	}
 	if(!reg.test($("#memory").val()) ||  parseInt($("#memory").val()) < 100) {
		$("#error2").html("格式有误");
		return false;
	}
 	if(parseInt($("#memory").val()) < 100) {
		$("#error2").html("必须大于100");
		return false;
	}
 	if(!reg.test($("#disk").val()) ||  parseInt($("#disk").val()) < 100) {
		$("#error3").html("格式有误");
		return false;
	}
 	if(parseInt($("#disk").val()) < 100) {
		$("#error3").html("必须大于100");
		return false;
	}
	return true;
}
</script>
</body>
</html>
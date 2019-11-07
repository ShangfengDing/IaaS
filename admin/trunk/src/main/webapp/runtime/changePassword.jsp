<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
</head>
<body>
<input type="hidden" id="id" value="${param.id}" />
<table>
	<tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">名称</td>
		<td>
			<input type="text" class="editline leftmargin_10" name="newname" id="newname"/>
			<span class="redletter leftmargin_5" id="error_name"></span>
		</td>
    </tr>
	<tr>
		<td class="blueletter rightalign rightpadding_10">请输入密码</td>
		<td><input type="text"  class="editline leftmargin_10" id="password1"/></td>
	</tr>
	<tr>
		<td></td>
		<td>
			<button class="button rightmargin_10" onclick="submitCheck()">确定</button>
			<button class="greybutton" onclick="cancle()">取消</button>
		</td>
		<td></td>
	</tr>
</table>
</body>
<script src="js/runtime/changePassword.js"></script>
</html>
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
		<td width="70px" class="blueletter rightalign rightpadding_10">选择群组</td>
		<td>
			<select id="roleselect" name="roleId" class="selectboxsmall" style="width:220px;">
				<s:iterator id="role" value="roleList" status="st">
					<option value="<s:property value="#role.id"/>">
						<s:property value="#role.rolename"/>
					</option>
				</s:iterator>
			</select>
		</td>
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
<script src="js/runtime/modAdminRole.js"></script>
</html>
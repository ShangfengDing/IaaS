<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<input type="hidden" id="adminid" value='<s:property value="id"/>'>
<input type="hidden" id="roleid" value='<s:property value="roleid"/>'>
<table class="formtable">
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">名称</td>
		<td>
			<input type="text" class="editline leftmargin_10" name="newname" id="newname" value='<s:property value="username"/>' readonly="readonly"/>
			<span class="redletter leftmargin_5" id="error_name"></span>
		</td>
    </tr>
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">邮箱</td>
		<td>
			<input type="text" class="editline leftmargin_10" name="newemail" id="newemail" value='<s:property value="email"/>'/>
			<span class="redletter leftmargin_5" id="error_email"></span>
		</td>
    </tr>
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">联系方式</td>
		<td>
			<input type="text" class="editline leftmargin_10" name="newmobile" id="newmobile" value='<s:property value="mobile"/>'/>
			<span class="redletter leftmargin_5" id="error_mobile"></span>
		</td>
    </tr>
    <tr>
     	<td></td>
     	<td>
     		<a class="button topmargin_20" href="javascript:void(0)" onclick="submit();">确定</a>
     		<a class="greybutton topmargin_20" href="javascript:void(0)" onclick="facebox_close()">取消</a>
     	</td>
    </tr>
</table>
</body>
<script src="js/runtime/modadmin.js"></script>
</html>
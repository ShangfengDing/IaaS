<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>

<table class="formtable">
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">名称</td>
		<td>
			<input type="text" class="squareinput bottommargin_10" name="name" id="name" placeholder="请输入1-20个字"/>
			<span class="redletter leftmargin_5" id="error_name"></span>
		</td>
    </tr>
    <!--  
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">描述</td>
		<td>
			<textarea class="squaretextbox" id="description" name="description" placeholder="请输入1-50个字"></textarea>
			<span class="redletter leftmargin_5" id="error_des"></span>
		</td>
    </tr>
    -->
	<tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">密码</td>
		<td>
			<input type="text" class="squareinput bottommargin_10" name="password" id="password"/>
			<span class="redletter leftmargin_5" id="error_pass"></span>
		</td>
    </tr>
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">邮箱</td>
		<td>
			<input type="text" class="squareinput bottommargin_10" name="email" id="email"/>
			<span class="redletter leftmargin_5" id="error_email"></span>
		</td>
    </tr>
    <tr>
	    <td class="blueletter rightalign rightpadding_10" width="75px">联系方式</td>
		<td>
			<input type="text" class="squareinput bottommargin_10" name="mobile" id="mobile"/>
			<span class="redletter leftmargin_5" id="error_mobile"></span>
		</td>
    </tr>
    <tr>
       <td class="blueletter rightalign rightpadding_10" width="75px">角色</td>
       <td>
	   	<select class="selectbox" id="roleselect">
	    <s:iterator id="role" value="roleList">
	        <option value="<s:property value="#role.id"/>"><s:property value="#role.rolename"/></option>
	    </s:iterator>
	    </select>
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
<script src="js/runtime/addadmin.js"></script>
</html>
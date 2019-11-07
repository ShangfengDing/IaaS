<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<form action="accounts/login" method="post">
<table><tr>
	<td></td>
	<td colspan="2"><s:if test="#parameters.needsauth!=null"><span class="redletter">您需要登录才能浏览下面的内容</span></s:if></td>
</tr><tr>
	<td></td>
	<td colspan=""><span class="redletter"><s:fielderror fieldName="error"></s:fielderror></span></td>
</tr><tr>
	<td align="right" width="50px">用户名</td>
	<td colspan="2"><input type="text" class="editline" name="username" id="username" /></td>
	<td><span class="e" id="eusername"><s:fielderror fieldName="username"/></span></td>
</tr><tr>
	<td align="right">密码</td>
	<td  colspan="2"><input type="password" class="editline" name="password" id="password" /></td>
	<td><span class="e" id="epassword"><s:fielderror fieldName="password"/></span></td>
</tr>
<tr>
	<td></td>
	<td><!--  <input type="checkbox" name="rememberMe" value="true" id="checkbox"/>记住登录状态--></td>
	<td align="right"><!-- <a href="accounts/getpassword.jsp">忘记密码?</a> --><input type="submit" class="button rightpadding:10px" name="submit" id="submit" value="登 录" /></td>
</tr>
</table>
</form>
<!--<a href="accounts/register.jsp"><img src="css/images/button_register.png"  border="0"/></a>-->
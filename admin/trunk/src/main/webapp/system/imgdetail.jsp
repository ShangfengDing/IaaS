<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<body>
<table>

    <tr>
        <td width="130px">账号：</td>
        <td>${param.account}
            <span class="redletter leftmargin_5" id="error1"></span></td>
    </tr>
    <tr>
        <td width="130px">已安装软件：</td>
        <td>${param.software}
            <span class="redletter leftmargin_5" id="error2"></span></td>
    </tr>
    <tr>
        <td width="130px">描述信息：</td>
        <td>${param.displayDescription}
            <span class="redletter leftmargin_5" id="error3"></span></td>
    </tr>
    <%--<tr>
        <td><br/><br/>
        </td>
        <td>
            <button class="button" type="submit">确定</button>
        </td>
    </tr>--%>
</table>
</body>
</html>
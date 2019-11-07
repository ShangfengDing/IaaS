<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<body>
<div class="modal-body">
    <div class="form-group">
        <label class=" control-label front-label">账号：</label>
        <label class=" control-label front-label" style="font-weight: normal;">${param.account}</label>
        <span id="error1" class="redletter leftmargin_5"></span>
    </div>
    <div class="form-group">
        <label class=" control-label front-label">已安装软件：</label>
        <label class=" control-label front-label" style="font-weight: normal;">${param.software}</label>
        <span id="error2" class="redletter leftmargin_5"></span>
    </div>
    <div class="form-group">
        <label class=" control-label front-label" >描述信息：</label>
        <label class=" control-label front-label" style="font-weight: normal;">${param.displayDescription}</label>
        <span id="error3" class="redletter leftmargin_5"></span>
    </div>
</div>
<!--
<table>

    <tr>
        <td width="130px">账号：</td>
        <td>${param.account}
            <span class="redletter leftmargin_5" id="error"></span></td>
    </tr>
    <tr>
        <td width="130px">已安装软件：</td>
        <td>${param.software}
            <span class="redletter leftmargin_5" id="error"></span></td>
    </tr>
    <tr>
        <td width="130px">描述信息：</td>
        <td>${param.displayDescription}
            <span class="redletter leftmargin_5" id="erro"></span></td>
    </tr>
</table> -->
</body>
</html>
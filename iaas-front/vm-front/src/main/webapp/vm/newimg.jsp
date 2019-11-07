<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="utf-8">
    <s:include value="../template/_head.jsp"/>
</head>
<body>


<div class="modal-body">

    <%--<input type="hidden" id="serverId" value="${param.serverId }"/>--%>
    <h5>此操作预计需要3到5分钟，发布成功后可以到管理员门户或者申请云主机页面查看模板！！</h5>
    <div class="form-group">
        <label for="imageName">名称</label>
        <input type="text" class="form-control" id="imageName" placeholder="请输入1-20个字"/>
    </div>
    <div class="form-group">
        <label for="imgAccount">账户信息</label>
        <textarea id="imgAccount" class="form-control front-no-box-shadow" rows="2" style="resize:none;" >账户：&#10;密码：</textarea>
    </div>
    <div class="form-group">
        <label for="imageDescription">已装软件</label>
        <textarea class="form-control" id="imageSoftware" placeholder="请输入1-50个字说明已装软件" rows="2"></textarea>
    </div>
    <div class="form-group">
        <label for="imageDescription">描述</label>
        <textarea class="form-control" id="imageDescription" placeholder="请输入1-100个字进行描述" rows="3"></textarea>
    </div>
    <div class="form-group">
        <label for="distributions">操作系统</label>
        <select id="distributions" class="form-control">
            <option value="WINDOWS">Windows</option>
            <option value="WINDOWS_SERVER">Windows Server</option>
            <option value="UBUNTU">Ubuntu</option>
            <option value="CENTOS">CentOS</option>
            <option value="REDHAT">Red Hat</option>
            <option value="FEDORA">Fedora</option>
            <option value="OTHER">其它操作系统</option>
        </select>
    </div>
    <%--<div class="form-group">--%>
    <%--<label>选择群组</label>--%>
    <%--<s:iterator id="acGroups" value="acGroups" status="st">--%>
    <%--<input type="checkbox" id="<s:property value="#acGroups.id"/>" name="selected_acgroups" value="<s:property value="#acGroups.id"/>"/>--%>
    <%--<label for="<s:property value="#acGroups.id"/><s:property value="#acGroups.name"/></label>--%>
    <%--<s:if test="(#st.index + 1) % 3 == 0">--%>
    <%--<br/>--%>
    <%--</s:if>--%>
    <%--</s:iterator>--%>
    <%--</div>--%>
    <button id="confirm" type="submit" class="btn btn-primary" onclick='imageSubmitCheck(
            "${param.appname}","${param.regionId}","${param.zoneId}","${param.snapshotId}","${param.serverId}",
            "${param.volumeId}","${param.userEmail}")
            '>确定
    </button>
    <button class="btn btn-default" onclick="facebox_close()">取消</button>
</div>
<%--<script src="js/vm/newimg.js"></script>--%>
</body>
</html>
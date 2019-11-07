<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>修改名称和密码 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage"/>
<input type="hidden" id="id" value="${param.id}" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div class="inner">
                <div>
                    <ol class="breadcrumb">
                        <li>用户管理</li>
                        <li><a href="runtime/admin_manage.jsp">管理员</a></li>
                        <li class="active">修改名称和密码</li>
                    </ol>
                </div>
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>修改名称和密码</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">名称</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" name="newname" id="newname"/>
                                </div>
                                    <label class="col-md-5 control-label front-label" style="color:red ;text-align:left" id="error_name"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">请输入密码</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" id="password1"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="text-right col-md-12">
                                    <button class="btn btn-default" onclick="cancel()">重置</button>
                                    <button class="btn btn-primary" onclick="submitCheck()">确定</button>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>

</body>
<script src="js/runtime/changePassword.js"></script>
</html>

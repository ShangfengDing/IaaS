<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>修改群组 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage" />
<input type="hidden" id="id" value="${param.id}" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div>
                <ol class="breadcrumb">
                    <li>用户管理</li>
                    <li><a href="runtime/admin_manage.jsp">管理员</a></li>
                    <li class="active">修改所属管理组</li>
                </ol>
            </div>
            <div class="inner">
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>修改所属管理组</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label"style="text-align:center">选择群组</label>
                                <div class="col-md-5">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="roleselect" name="roleId">
                                        <s:iterator id="role" value="roleList" status="st">
                                            <option value="<s:property value="#role.id"/>">
                                                <s:property value="#role.rolename"/>
                                            </option>
                                        </s:iterator>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12 text-right">
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
<script src="js/runtime/modAdminRole.js"></script>
</html>

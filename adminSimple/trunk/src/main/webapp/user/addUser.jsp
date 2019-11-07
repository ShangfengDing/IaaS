<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>新建用户 - 云海Iaas</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div>
                <ol class="breadcrumb">
                    <li>用户管理</li>
                    <li><a href="runtime/admin_manage.jsp">用户</a></li>
                    <li class="active">新建用户</li>
                </ol>
            </div>
            <div class="inner">
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>新建用户</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-1 front-label control-label" style="text-align:center" >邮箱</label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control front-no-box-shadow" name="email" id="email"/>
                                </div>
                                <label class="col-md-5 front-label control-label errors" id="error_email"
                                       style="color:red; text-align:left"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 front-label control-label" style="text-align:center" >密码</label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control front-no-box-shadow" name="password" id="password"/>
                                </div>
                                <label class="col-md-5 front-label control-label errors" id="error_pass" style="color:red;text-align:left"></label>
                            </div>

                            <div class="form-group">
                                <label class="col-md-1 front-label control-label" style="text-align:center" >群组</label>
                                <div class="col-md-4">
                                    <%--<select class="form-control front-no-radius front-no-box-shadow" id="groupselect">--%>
                                        <%--<s:iterator id="group" value="groupList">--%>
                                            <%--<option value="<s:property value="#group.id"/>"><s:property--%>
                                                    <%--value="#group.rolename"/></option>--%>
                                        <%--</s:iterator>--%>
                                    <%--</select>--%>
                                        <select class="form-control front-no-radius front-no-box-shadow" id="groupselect">
                                            <option value="super">super</option>
                                            <option value="public">public</option>
                                            <option value="free专用">free专用</option>
                                        </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12 text-right">
                                    <a class="btn btn-default" href="javascript:void(0)" onclick="cancel()">重置</a>
                                    <a class="btn btn-primary" href="javascript:void(0)" onclick="submit();">确定</a>

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
<script src="js/user/addUser.js"></script>
</body>

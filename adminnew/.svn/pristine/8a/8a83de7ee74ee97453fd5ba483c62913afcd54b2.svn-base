<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>修改管理员 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage" />
<input type="hidden" id="adminid" value='<s:property value="id"/>'>
<input type="hidden" id="roleid" value='<s:property value="roleid"/>'>
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div>
                <ol class="breadcrumb">
                    <li>用户管理</li>
                    <li><a href="runtime/admin_manage.jsp">管理员</a></li>
                    <li class="active">修改管理员</li>
                </ol>
            </div>
            <div class="inner">
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>修改管理员</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label" style="text-align:center">名称</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow"name="newname" id="newname" value='<s:property value="username"/>' readonly="readonly"/>
                                </div>
                                <label class="col-md-5 control-label front-label" style="color:red ;text-align:left" id="error_name"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label" style="text-align:center">邮箱</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" name="newemail" id="newemail" value='<s:property value="email"/>'/>
                                </div>
                                <label class="col-md-5 control-label front-label" style="color:red;text-align:left" id="error_email"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label" style="text-align:center">联系方式</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" name="newmobile" id="newmobile" value='<s:property value="mobile"/>'/>
                                </div>
                                <label class="col-md-5 control-label front-label" style="color:red;text-align:left" id="error_mobile"></label>
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
<script src="js/runtime/modadmin.js"></script>
</body>
</html>

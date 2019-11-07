<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>添加管理组-云海Iaas</title>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div>
                <ol class="breadcrumb">
                    <li>用户管理</li>
                    <li><a href="runtime/role_manage.jsp">管理组</a></li>
                    <li class="active">新建管理组</li>
                </ol>
            </div>
            <div class="inner">
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>新建管理组</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label"style="text-align:center">管理组名称</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow"  name="name" id="name" placeholder="请输入1-20个字"/>
                                </div>
                                <label class="col-md-5 control-label front-label"style="text-align:left;color:red" id="error_name"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-12 control-label front-label" style="text-align:left;font-size:15px">请选择可以管理的资源</label>
                            </div>
                            <s:iterator id="column" value="adminResourceMap" status="st">
                                <div class="form-group">
                                    <label class="col-md-2 control-label front-label" style="text-align:center; font-size:15px">
                                    <input type="checkbox" name="<s:property value="#column.key.topBarId"/>"/><s:property value="#column.key.topBarName"/>
                                    </label><br>
                               <%-- </div>
                                <div class="form-group">--%>
                                    <s:iterator id="resource" value="#column.value">
                                        <label class="control-label front-label">
                                            <input value="<s:property value="id"/>" type="checkbox" name="<s:property value="#column.key.topBarName"/>"/>
                                            <s:property value="leftBarName"/>&nbsp;&nbsp;
                                        </label>
                                    </s:iterator>
                                </div>
                            </s:iterator>
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
<script src="js/runtime/addRole.js"></script>
</body>
</html>

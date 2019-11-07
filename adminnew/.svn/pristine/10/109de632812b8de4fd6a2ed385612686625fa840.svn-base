<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>查看管理组-云海Iaas</title>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div>
                <ol class="breadcrumb">
                    <li>用户管理</li>
                    <s:if test="page==3">
                    <li><a href="runtime/admin_manage.jsp">管理员</a></li>
                    </s:if>
                    <s:else>
                        <li><a href="runtime/role_manage.jsp">管理组</a></li>
                    </s:else>
                    <li class="active">查看管理组</li>
                </ol>
            </div>
            <div class="inner">
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>查看所属管理组</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-12 control-label front-label" style="text-align:left;font-size:15px">管理组<s:property value="name"/>可以进行的操作</label>
                            </div>
                            <s:iterator id="column" value="adminResourceMap" status="st">
                                <div class="form-group">
                                    <label class="col-md-2 control-label front-label" style="text-align:center; font-size:15px">
                                        <input type="checkbox" name="<s:property value="#column.key.topBarId"/>"+st/><s:property value="#column.key.topBarName"/>
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
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>
<script src="js/runtime/showRole.js"></script>
</body>
</html>

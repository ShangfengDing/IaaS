<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>管理组设置 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group" style="margin-bottom:0px">
            <div>
                <ol class="breadcrumb">
                    <li>用户管理</li>
                    <li class="active">管理组</li>
                </ol>
            </div>
            <div class="form-group pull-right" style="margin-bottom:0px" >
                <a id="search" href="javascript:void(0)" onclick="searchit()" class="btn btn-default">搜索</a>&nbsp;
                <a href="runtime/preAddRole" rel="facebox" title="添加管理组"  class="btn btn-primary pull-right">新建</a>
            </div>
        </div>
            <%--<div class="front-toolbar other">
                <div class="front-toolbar-header clearfix">
                    <button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#admin_button" aria-expanded="false" aria-controls="admin_button">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div id="admin_button" class="front-btn-group collapse" data-toggle="buttons">
                    <label class="btn btn-default front-no-box-shadow active" data-category="getAll" style="box-shadow: none">
                        <input type="radio" name="options" autocomplete="off" checked><span class="glyphicon glyphicon-home"></span>&nbsp;全部
                    </label>
                    <label class="btn btn-default front-no-box-shadow" data-category="find" style="box-shadow:none">
                        <input type="radio" name="options" autocomplete="off"><span class="glyphicon glyphicon-move"></span>&nbsp;查找
                    </label>
                </div>
                <a href="runtime/preAddRole" rel="facebox" title="添加管理组"  class="btn btn-primary pull-right"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;新建管理组</a>
            </div>--%>
            <div class="col-md-12 form-group">
            <div class="inner">
                <div id="paneldiv" style="margin-top:15px">
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>管理组查询</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label"style="text-align:center;">名称</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" id="rolename"/>
                                </div>
                                <div class="col-md-6">
                                    <%--<button type="button" class="btn btn-primary" id="role_manage_button1" onclick="submitSearch(1)">查询</button>&nbsp;&nbsp;
                                    <a href="javascript:void(0)" id="role_manage_button2" class="btn btn-default" onclick="cancelSearch()">取消</a>--%>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="text-right col-md-12">
                                    <%-- <a href="runtime/preAddRole" rel="facebox" title="添加管理组" class="btn btn-primary">添加管理组</a>--%>
                                    <a href="javascript:void(0)" id="role_manage_button2" class="btn btn-default" onclick="cancelSearch()">取消</a>
                                    <button type="button" class="btn btn-primary" id="role_manage_button1" onclick="submitSearch(1)">查询</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
                <div class="dottedline"></div>
                <div id="pagenum"></div>
                <div id="query"></div>
                <div id="loading" class="hidden">
                    <div class="front-loading">
                        <div class="front-loading-block"></div>
                        <div class="front-loading-block"></div>
                        <div class="front-loading-block"></div>
                    </div>
                    <div class="panel-body text-center">正在加载请稍候</div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>
<script src="js/runtime/queryRole.js"></script>
</body>
</html>

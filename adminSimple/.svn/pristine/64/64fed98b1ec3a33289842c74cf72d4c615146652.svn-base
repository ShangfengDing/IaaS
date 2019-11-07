<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>用户管理 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group" style="margin-bottom:0px" >
            <div>
                <ol class="breadcrumb">
                    <li>用户管理</li>
                    <li class="active">用户</li>
                </ol>
            </div>
            <div class="text-right col-md-12" style="padding-right: 0px; margin-bottom:0px">
                    <a id="search" href="javascript:void(0)" onclick="searchit()" class="btn btn-default"><span class="glyphicon glyphicon-search"></span>&nbsp;搜索</a>
                    <a href="user/addUser.jsp" rel="facebox" title="添加用户"  class="btn btn-primary pull-right"
                       style="margin-left:
                    3px"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;新建</a>
            </div>

        </div>
        <%--  <div class="front-toolbar other">
                <div class="front-toolbar-header clearfix">
                    <button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#user_button" aria-expanded="false" aria-controls="admin_button">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div id="user_button" class="front-btn-group collapse" data-toggle="buttons">
                    &lt;%&ndash;&lt;%&ndash;<label class="btn btn-default front-no-box-shadow active" data-category="getAll" style="box-shadow: none">
                        <input type="radio" name="options" autocomplete="off" checked><span class="glyphicon glyphicon-home"></span>&nbsp;全部
                    </label>&ndash;%&gt;&ndash;%&gt;
                    <label class="btn btn-default front-no-box-shadow" data-category="find" style="box-shadow:none">
                        <input type="radio" name="options" autocomplete="off"><span class="glyphicon glyphicon-move"></span>&nbsp;查找
                    </label>
                </div>
            </div>--%>
            <div class="col-md-12 form-group">
            <div class="clearfix"></div>
            <div class="inner">
                <div id="paneldiv" style="margin-top:15px">
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>用户</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">所属企业</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control front-no-box-shadow" id="enterpriseName"/>
                                </div>
                                <label class="col-md-1 control-label front-label">所属邮箱</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control front-no-box-shadow" id="email"/>
                                </div>
                                <label class="col-md-1 control-label front-label">所在群组</label>
                                <div class="col-md-3">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="group">
                                        <option value="">--全部--</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="text-right col-md-12">
                                    <a href="javascript:void(0)" id="button3" class="btn btn-default" onclick="cancelSearch()">取消</a>
                                   <%-- <button class="btn btn-primary" id="button2" onclick="searchAll(1)">查询全部</button>--%>
                                    <button class="btn btn-primary" id="button1" onclick="submitEmail(1)">查询</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
                <div class="dottedline"></div>
                <div id="totalnum"></div>
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
<script src="js/user/usermanage.js"></script>
</body>
<%--<script src="js/user/usermanage.js"></script>--%>
</html>

<script>

</script>
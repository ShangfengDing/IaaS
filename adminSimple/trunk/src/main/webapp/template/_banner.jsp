<!DOCTYPE html>
<%@ page import="appcloud.admin.common.Constants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>css/jquery-ui.css" />
<link rel="stylesheet" media="all" type="text/css" href="<%=Constants.FRONT_URL%>css/jquery-ui-timepicker-addon.css" />
<script type="text/javascript" src="<%=Constants.FRONT_URL%>js/plugin/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>js/plugin/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="<%=Constants.FRONT_URL%>js/plugin/jquery-ui-sliderAccess.js"></script>
<link rel="stylesheet" href="<%=Constants.NEWFRONT_URL%>bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=Constants.NEWFRONT_URL%>css/front.css">
<script src="<%=Constants.NEWFRONT_URL%>js/jquery/jquery.min.js"></script>
<script src="<%=Constants.NEWFRONT_URL%>bootstrap/js/bootstrap.min.js"></script>
<script src="<%=Constants.NEWFRONT_URL%>js/plugin/front.js"></script>
<nav class="navbar navbar-default navbar-fixed-top front-nav">
    <div class="container">
        <div class="col-md-12">
        <div>
            <!-- 品牌图片大小为150 * 30：宽度不定，高度固定30px -->
            <div class="nav-brand"><a href="system/infrastructure"><img src="images/logo_01.png" alt="brand" class="img-responsive"/></a></div>
            <!-- -->
        </div>
        <div class="nav-collapse collapse" id="nav-collapse-demo">
            <ul class="nav navbar-nav">
                <li class="${param.menu=='home'?'front-active':''}">
                    <a href="monitor/home">首页</a>
                </li> <!-- 激活菜单 -->
                <%--<li class="${param.menu=='alarm'?'front-active':''}" id="active-alarm"><a href="monitor/alarm">告警</a></li>--%>
                <li class="dropdown ${param.menu=='system'?'front-active ':''}">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">系统管理<span class="caret"></span></a>
                    <ul class="dropdown-menu nav-dropdown front-min-width">
                        <li><a href="system/hostmanage?page=1"><span class="glyphicon glyphicon-hdd"></span>&nbsp;&nbsp;基础设施</a></li>
                        <%--<li><a href="system/cloud"><span class="glyphicon glyphicon-cloud"></span>&nbsp;&nbsp;分布式云</a></li>--%>
                        <%--<li><a href="system/cluster"><span class="glyphicon glyphicon-th"></span>&nbsp;&nbsp;集群</a></li>--%>
                        <%--<li><a href="system/host"><span class="glyphicon glyphicon-asterisk"></span>&nbsp;&nbsp;节点</a></li>--%>
                        <%--<li><a href="net/netlist?zid=1"><span class="glyphicon glyphicon-signal"></span>&nbsp;&nbsp;网络</a></li>--%>

                    </ul>
                </li>
                <%--<li class="dropdown">
                    <a href="#" class="${param.menu=='cloud'?'front-active dropdown-toggle':'dropdown-toggle'}" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">资源管理<span class="caret"></span></a>
                    <ul class="dropdown-menu nav-dropdown front-min-width">
                        <li><a href="vm/presearchvm"><span class="glyphicon glyphicon-blackboard"></span>&nbsp;&nbsp;云主机</a></li>
                        <li><a href="hd/hdmanage"><span class="glyphicon glyphicon-hdd"></span>&nbsp;&nbsp;云硬盘</a></li>
                        <li><a href="img/imglist"><span class="glyphicon glyphicon-folder-open"></span>&nbsp;&nbsp;云模板</a></li>


                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="${param.menu=='runtime'?'front-active dropdown-toggle':'dropdown-toggle'}" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">日志管理<span class="caret"></span></a>
                    <ul class="dropdown-menu nav-dropdown front-min-width">
                        <li><a href="monitor/alarm"><span class="glyphicon glyphicon-bell"></span>&nbsp;&nbsp;告警查询</a></li>
                        &lt;%&ndash;<li><a><span class="glyphicon glyphicon-wrench"></span>&nbsp;&nbsp;告警设置</a></li>&ndash;%&gt;
                        <li><a href="runtime/log.jsp"><span class="glyphicon glyphicon-random"></span>&nbsp;&nbsp;运行日志</a></li>
                        <li><a href="runtime/admin_log.jsp"><span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;操作日志</a></li>
                        <li><a href="runtime/accountLog.jsp"><span class="glyphicon glyphicon-yen"></span>&nbsp;&nbsp;结算日志</a></li>
                        &lt;%&ndash;<li><a href="#"><span class="glyphicon"></span>&nbsp;&nbsp;结算查询</a></li>&ndash;%&gt;

                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="${param.menu=='userManage'?'front-active dropdown-toggle':'dropdown-toggle'}" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">用户管理<span class="caret"></span></a>
                    <ul class="dropdown-menu nav-dropdown front-min-width">

                        <li><a href="user/usermanage.jsp"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;用户</a></li>
                        <li><a href="group/acGroupList"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;用户组</a></li>
                        <li><a href="runtime/admin_manage.jsp"><span class="glyphicon glyphicon-pawn"></span>&nbsp;&nbsp;管理员</a></li>
                        <li><a href="runtime/role_manage.jsp"><span class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;管理组</a></li>
                    </ul>
                </li>--%>
            </ul>
        </div>

        <div class="nav-right">
            <div class="area area-media">
                <span id="warning" class="label" style="display: none;font-size:14px;text-align: center;background-color:#d32f2e;padding-left: 15px;padding-right: 10px;margin-right:6px">
                    <a href="monitor/alarm" id="warn" title="您有告警信息未处理" style="color: white">!!</a>
                </span>
                <span style="font-size: 14px;">

                    <a class="blueletter"
                       data-toggle="front-modal"
                       data-href="runtime/preChangeProfiles?id=<s:property value='#session.adminId'/>"
                        size="s" data-title="编辑管理员信息" style="cursor: pointer;color:
                       #000000;text-decoration: none" rel="facebox">您好，<s:property
                            value="#session.username"/>&nbsp&nbsp&nbsp&nbsp&nbsp</a>&nbsp;

                    <a class="blueletter"
                       data-toggle="front-modal"
                       data-href="runtime/preChangePasswordNew?id=<s:property value="#session.adminId"/>"
                       size="s" data-title="修改管理员密码" style="cursor: pointer" rel="facebox">修改密码</a>
                    <a href="accounts/logout" class="strong blackletter leftmargin_10 rightmargin_10">退出</a>
                    </a>
                </span>
        </div></div>
    </div>
    </div>
</nav>


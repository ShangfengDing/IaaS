<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.api.enums.ImageTypeEnum"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<nav class="navbar navbar-default navbar-fixed-top front-nav">
    <div class="container">
        <div>
            <!-- 品牌图片大小为150 * 30：宽度不定，高度固定30px -->
            <div class="nav-brand"><a href="system/infrastructure"><img src="images/logo.png" alt="brand" class="img-responsive"/></a></div>
            <!-- -->
        </div>
        <div class="nav-collapse collapse" id="nav-collapse-demo">
            <ul class="nav navbar-nav">
                <li class="${param.menu=='shouye'?'front-active':''}">
                    <a href="system/infrastructure">首页</a>
                </li> <!-- 激活菜单 -->
                <li class="dropdown ${param.menu=='system'?'front-active ':''}">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">系统管理<span class="caret"></span></a>
                    <ul class="dropdown-menu nav-dropdown front-min-width">
                        <li><a href="system/cluster"><span class="glyphicon glyphicon-th"></span>&nbsp;&nbsp;集群</a></li>
                        <li><a href="system/host"><span class="glyphicon glyphicon-asterisk"></span>&nbsp;&nbsp;节点</a></li>
                        <li><a href="net/netlist?zid=1"><span class="glyphicon glyphicon-signal"></span>&nbsp;&nbsp;网络</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="${param.menu=='cloud'?'front-active dropdown-toggle':'dropdown-toggle'}" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">资源管理<span class="caret"></span></a>
                    <ul class="dropdown-menu nav-dropdown front-min-width">
                        <li><a href="vm/presearchvm"><span class="glyphicon glyphicon-blackboard"></span>&nbsp;&nbsp;云主机</a></li>
                        <li><a href="img/imglist"><span class="glyphicon glyphicon-folder-open"></span>&nbsp;&nbsp;云模板</a></li>
                        <li><a href="hd/hdmanage"><span class="glyphicon glyphicon-hdd"></span>&nbsp;&nbsp;云硬盘</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="${param.menu=='runtime'?'front-active dropdown-toggle':'dropdown-toggle'}" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">日志管理<span class="caret"></span></a>
                    <ul class="dropdown-menu nav-dropdown front-min-width">
                        <li><a href="runtime/log.jsp"><span class="glyphicon glyphicon-random"></span>&nbsp;&nbsp;运行日志</a></li>
                        <li><a href="runtime/admin_log.jsp"><span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;操作日志</a></li>
                        <li><a href="runtime/accountLog.jsp"><span class="glyphicon glyphicon-yen"></span>&nbsp;&nbsp;结算日志</a></li>
                        <%--<li><a href="#"><span class="glyphicon"></span>&nbsp;&nbsp;结算查询</a></li>--%>

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
                </li>
                <%--<li class="dropdown">--%>
                <%--<a href="#" class="${param.menu=='finance'?'front-active dropdown-toggle':'dropdown-toggle'}" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">财务管理<span class="caret"></span></a>--%>
                <%--<ul class="dropdown-menu nav-dropdown front-min-width">--%>
                <%--<li><a href="#"><span class="glyphicon"></span>&nbsp;&nbsp;计费设置</a></li>--%>
                <%--<li><a href="#"><span class="glyphicon"></span>&nbsp;&nbsp;交易记录</a></li>--%>


                <%--</ul>--%>
                <%--</li>--%>
            </ul>
        </div>
        <div class="nav-right">
        </div>
            <%--<!-- 搜索图标：可选 -->--%>
            <%--&lt;%&ndash;<div class="area area-media"><span class="glyphicon glyphicon-search nav-search-icon"></span></div>&ndash;%&gt;--%>
            <%--<!-- -->--%>
            <%--<!-- 产品导航菜单按钮 -->--%>
            <%--<div class="area area-media"><span class="glyphicon glyphicon-th nav-toggle-pro" data-gen="nav-pro" data-toggle="front-popover-bottom" data-target="#nav-pro-demo"></span></div>--%>
            <%--<!-- -->--%>
            <%--<!-- 导航栏用户头像 -->--%>
            <%--<div class="area area-avatar area-media"><img src="<%=Constants.ACCOUNT_URL%>users/users/download?uuid=<%=request.getSession().getAttribute("keyProfileImageUrl")%>" class="img-circle nav-avatar" onerror="javascript:this.src='images/user_male.png'" alt="avatar" data-toggle="front-popover-bottom" data-target="#nav-user-demo"/></div>--%>
            <%--<!-- -->--%>
            <%--<!-- 横向导航栏移动端触发：可选 -->--%>
            <%--<div class="area visible-xs nav-toggle-down" data-toggle="collapse" data-target="#nav-collapse-demo"><span class="glyphicon glyphicon-menu-hamburger" id="front-nav-toggle-down-demo"></span></div>--%>
            <%--<!-- -->--%>
            <%--<!-- 产品导航菜单 -->--%>
            <%--<div id="nav-pro-demo" data-pro="6"></div>--%>
            <%--<!-- 用户菜单 -->--%>
            <%--<div id="nav-user-demo" class="bottom nav-popover nav-popover-media nav-avatar-menu">--%>
                <%--<div class="arrow"></div>--%>
                <%--<ul>--%>
                    <%--<li class="text-center">--%>
                        <%--<a href="<%=Constants.ACCOUNT_URL%>users">--%>
                            <%--<!-- 用户头像 -->--%>
                            <%--<img src="<%=Constants.ACCOUNT_URL%>users/users/download?uuid=<%=request.getSession().getAttribute("keyProfileImageUrl")%>" alt="avatar" class="img-circle img-lg-avatar"  onerror="javascript:this.src='images/user_male.png'" />--%>
                            <%--<!-- end 用户头像 -->--%>
                        <%--</a>--%>
                        <%--<div><%=request.getSession().getAttribute("userName")%></div>--%>
                        <%--<div><small><%=request.getSession().getAttribute("email")%></small></div>--%>
                    <%--</li>--%>
                    <%--<li class="divider"></li> <!-- 分界线 -->--%>
                    <%--<li><a href="<%=Constants.ACCOUNT_URL%>users" target="_blank"><span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;账号设置</a></li>--%>
                    <%--<li><a href="javascript:void(0)" onclick="logout()"><span class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;退出</a></li>--%>
                <%--</ul>--%>
            <%--</div>--%>
            <%--<!-- end 用户菜单 -->--%>
        <%--</div>--%>
    </div>
</nav>
<%--<input id="baseURL" type="hidden" value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>"/>--%>
<%--<script>--%>
    <%--function logout(){--%>
        <%--$.ajax({--%>
            <%--url : "account/logout",--%>
            <%--type:"post",--%>
            <%--dataType:"json",--%>
            <%--async:false,--%>
            <%--data:{},--%>
            <%--success:function(data) {--%>
                <%--if(typeof(com) != "undefined"){--%>
                    <%--com.xmpp.close();--%>
                <%--}--%>
                <%--var acc_token = "<%=request.getSession().getAttribute("accToken")%>";--%>
                <%--var accountAddr = "<%=Constants.ACCOUNT_URL%>";--%>
                <%--$.ajax({--%>
                    <%--url:accountAddr+"api/oauth2/revokeoauth2?callback=?",--%>
                    <%--type:"post",--%>
                    <%--dataType:"json",--%>
                    <%--data:{'access_token':acc_token},--%>
                    <%--complete:function(result) {--%>
                        <%--if(typeof(com) != "undefined"){--%>
                            <%--com.xmpp.close();--%>
                        <%--}--%>
                        <%--location.replace($("#baseURL").val()+"/sum/sum");--%>
                    <%--},--%>
                    <%--error:function(data){--%>
                        <%--alert('post data error:' + data);--%>
                        <%--console.log(data);--%>
                    <%--}--%>
                <%--});--%>
            <%--},--%>
            <%--error:function(data){--%>
                <%--alert('logout error:' + data);--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>
<%--</script>--%>




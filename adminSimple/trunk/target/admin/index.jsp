<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="statics/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="statics/css/theme/modern/front.css">
    <link rel="stylesheet" href="js/bootstrap_table/bootstrap-table.css">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <title>欢迎进入Appcloud</title>
    <%--<s:include value="/template/_head.jsp"/>--%>
</head>

<body class="front-body">
<nav class="navbar navbar-default navbar-fixed-top front-nav">
    <div class="container">
        <div>
            <!-- 左侧栏移动端触发：可选  -->
            <%--<img class="nav-toggle-left" id="front-nav-toggle-left" alt="SidebarToggle"/>--%>
            <!-- 品牌图片大小为150 * 30：宽度不定，高度固定30px -->
            <div class="nav-brand"><img class="img-responsive" src="images/logo_01.png" alt="cloud"  alt="brand" class="img-responsive"/></div>
        </div>
        <!-- 导航栏右侧区域 -->
        <%--<div class="nav-right">
            <div class="area area-media" style="font-size: 14px">您好，请先登录！</div>
        </div>--%>
    </div>
</nav>
<div class="front-inner">
    <div class="container">
        <div class="row">
            <div class="col-md-9" style="padding-right: 5px;">
                <div class="panel panel-default front-panel" style="padding-right: 0px;background: #e7e7e7; border: 0;">
                    <div class="panel-body" style="height: 100%;padding: 0px;">
                        <img src="images/05.png" class="change-img"  style="width: 100%;max-width: 100%; display: block;">
                        <%--height: 271px;width: 847.5px;--%>
                    </div>
                </div>
            </div>
            <div class="col-md-3" style="padding-right: 5px;">
                <div class="panel panel-default front-panel" style="padding-right: 0px; border: 0; ">
                    <div class="panel-heading">
                        <img src="images/landing.png">
                    </div>
                    <div class="panel-body">
                        <form class="form-horizontal" method="post" action="accounts/login" onsubmit="return preSubmit()">
                            <div class="form-group" style="margin-left: 0px;color:#337ab7;" >
                                <%--<s:property value="name"/>--%>一站式虚拟资源管理平台(管理门户)
                            </div>
                            <div class="form-group" id="username-div">
                                <input type="text" class="form-control login-input" id="username-input" name="username" placeholder="用户名" style="margin-right: auto;margin-left: auto;width: 90%">
                            </div>
                            <div class="form-group" id="password-div">
                                <input type="password" class="form-control login-input" id="password-input" name="password" placeholder="密码" style="margin-right: auto;margin-left: auto;width: 90%">
                            </div>
                            <div class="form-group" style="height: 25px;">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" id="rmb-tel">记住用户名
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" style="margin-bottom: 0px;">
                                <button type="submit" class="btn btn-info btn-block login-input" style="margin-right: auto;margin-left: auto;width: 90%">登录</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3" style="padding-right: 5px;">
                <div class="panel panel-default front-panel" style=" height: auto;">
                    <div class="panel-body" style="padding: 10% 10%;">
                        <img src="images/log_index_1.png" style="width: 80%;max-width: 80%;  display: block; margin: 3px auto;">
                    </div>
                    <div class="panel-body" style>
                        <p class="text-center">
                            <strong>整合云中资源，让资源随需而变</strong>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-3" style="padding-right: 5px;">
                <div class="panel panel-default front-panel" style=" height: auto;">
                    <div class="panel-body" style="padding: 10% 10%;">
                        <img src="images/log_index_2.png" style="width: 80%;max-width: 80%; display: block; margin: 3px auto;">
                    </div>
                    <div class="panel-body">
                        <p class="text-center">
                            <strong>汇聚云的力量，融合私有/公有云</strong>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-3" style="padding-right: 5px;">
                <div class="panel panel-default front-panel" style="height: auto;">
                    <div class="panel-body" style="padding: 10% 10%;">
                        <img src="images/log_index_3.png" style="width: 80%;max-width: 80%; display: block; margin: 3px auto;">
                    </div>
                    <div class="panel-body">
                        <p class="text-center">
                            <strong>强化管理，有效支撑管理运维</strong>
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-3" style="padding-right: 5px;">
                <div class="panel panel-default front-panel" style=" height: auto;">
                    <div class="panel-body" style="padding: 10% 10%;">
                        <img src="images/log_index_4.png" style="width: 80%;max-width: 80%; display: block; margin: 3px auto;">
                    </div>
                    <div class="panel-body">
                        <p class="text-center">
                            <strong>开放云的能力，铺就登云之路</strong>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp" />
</div>

<script src="statics/js/jquery/jquery.min.js"></script>

<script src="js/js/jquery.md5.js"></script>
<script src="js/js/echarts.js"></script>
<%--<script src="js/js/china.js"></script>--%>
<%--<script src="js/js/world.js"></script>--%>
<script src="js/js/jquery.cookie.js"></script>
<script src="js/js/jquery.md5.js"></script>

<script src="js/bootstrap_table/bootstrap-table.js"></script>
<script src="js/bootstrap_table/bootstrap-table-zh-CN.js"></script>
<script src="js/bootstrap_table/tableExport.js"></script>
<script src="js/bootstrap_table/bootstrap-table-export.js"></script>

<script src="statics/bootstrap/js/bootstrap.min.js"></script>

<script>
    $(function () {
        if($.cookie('tel') != undefined) {
            $('#tel-input').val($.cookie('tel'));
            $('#rmb-tel').attr('checked', 'true');
        }
        $('#tel-input').blur(telInputBlur);
        $('#psw-input').blur(pswInputBlur);
        var wrong= "<s:property value="wrongOfTelOrPsw"/>";
        if(wrong == "true"){
            $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'手机或密码错误！'});
        }
    });

    function telInputBlur() {
        if($('#tel-div').hasClass("has-error")) {
            $('#tel-div').removeClass("has-error");
            $('#tel-help-block').hide();
        }
        if($('#tel-input').val().length != 11) {
            $('#tel-div').addClass("has-error");
            $('#tel-help-block').show();
        }
    }

    function pswInputBlur() {
        if($('#psw-div').hasClass("has-error")) {
            $('#psw-div').removeClass("has-error");
            $('#psw-help-block').hide();
        }
        if($('#psw-input').val() == "") {
            $('#psw-div').addClass("has-error");
            $('#psw-help-block').show();
        }
    }

    function preSubmit() {
        $('#tel-input').blur();
        $('#psw-input').blur();
        if($('#rmb-tel').is(':checked')) {
            $.cookie('tel', $('#tel-input').val());
        } else {
            $.removeCookie('tel');
        }
        if($('#tel-div').hasClass("has-error") || $('#psw-div').hasClass("has-error")) {
            return false;
        } else {
            $('#psw-input').val($.md5($('#psw-input').val()));
            return true;
        }
    }
</script>
</body>
</html>

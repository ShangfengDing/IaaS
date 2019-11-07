<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>申请云主机</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <s:include value="../../template/_head.jsp"/>
    <style>
        .slider-tick {
            opacity: 0 !important;
        }

        .slider-selection {
            background-image: -webkit-linear-gradient(top, #89cdef 0, #81bfde 100%);
            background-image: -o-linear-gradient(top, #89cdef 0, #81bfde 100%);
            background-image: linear-gradient(to bottom, #89cdef 0, #81bfde 100%);
            background-repeat: repeat-x;
        }

        .red {
            color: #ff0000;
        }

        /*slider的bug*/
        .show-later {
            visibility: hidden;
        }
    </style>
</head>
<body class="front-body">
<s:include value="../../template/_banner.jsp?menu=newvm"/>
<input class="hidden" value='${param.appname}' id="yunhai_appname"><!-- 从列表也传过来的appname -->
<input class="hidden" value='${param.ulMenuName}' id="yunhai_ulMenuName"><!--切换后的云账户名称-->
<input class="hidden" value='${param.zone}' id="yunhai_ulZone"><!--切换后的云账户名称-->
<input class="hidden" value='${param.ulMenuProviderEn}' id="yunhai_ulMenuProviderEn"><!--切换后的提供商-->
<div class="front-inner">
    <div class="container">
        <div class="fix-gutter">
            <div class="row">
                <div class="col-md-12">
                    <ul class="breadcrumb">
                        <li><a href="vm/vmlist.jsp?menu=vm">云主机</a></li>
                        <li class="active">申请云主机</li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col-md-9">
                    <div class="panel-body panel-default front-panel"
                         style="margin: -10px -15px -15px -15px;">
                        <div class="panel-body front-no-padding">
                            <table class="table table-striped front-table"
                                   style="margin-bottom: 0px;">

                                <tr>
                                    <%--基本设置--%>
                                    <div class="panel panel-default front-panel card-gutter" id="basic-setting">
                                        <div class="panel-heading">基本设置</div>
                                        <div class="panel-body front-last-no-margin">
                                            <div class="row show-later">
                                                <div class="col-md-2 col-sm-3 col-xs-4">
                                                    <h5 class="label-pos2">提供商</h5>
                                                </div>
                                                <div class="col-md-10 col-sm-9 col-xs-8 button-group-align">
                                                    <div class="front-toolbar other">
                                                        <div class="front-toolbar-header clearfix">
                                                            <button type="button"
                                                                    class="front-toolbar-toggle navbar-toggle"
                                                                    data-toggle="collapse" data-target="#provider">
                                                                <span class="icon-bar"></span> <span
                                                                    class="icon-bar"></span>
                                                                <span class="icon-bar"></span>
                                                            </button>
                                                        </div>
                                                        <div id="provider" class="front-btn-group collapse">
                                                            <a class="btn btn-default front-no-box-shadow active"
                                                               id="#yunhai">云海</a>
                                                            <a class="btn btn-default front-no-box-shadow disabled" id="aliyun" disabled="disabled" onclick="window.location.href='vm/newvm/aliyun/newaliyunvm.jsp'">阿里云</a>
                                                            <a class="btn btn-default front-no-box-shadow disabled" id="amazon" disabled="disabled">亚马逊</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2 col-sm-3 col-xs-4">
                                                    <h5 class="label-pos2">地域</h5>
                                                </div>
                                                <div class="col-md-10 col-sm-9 col-xs-8 button-group-align">
                                                    <div class="front-toolbar other">
                                                        <div class="front-toolbar-header clearfix">
                                                            <button type="button"
                                                                    class="front-toolbar-toggle navbar-toggle"
                                                                    data-toggle="collapse" data-target="#region-list">
                                                                <span class="icon-bar"></span>
                                                                <span class="icon-bar"></span>
                                                                <span class="icon-bar"></span>
                                                            </button>
                                                        </div>
                                                        <div id="region-list" class="front-btn-group collapse">
                                                            <a class="btn btn-default front-no-box-shadow active"></a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">云账户</h5>
                                                </div>
                                                <div class="col-md-3" style="padding-left: 15px;padding-bottom: 10px;">
                                                    <div class="dropdown">
                                                        <button id="appnameDropMenu" style="text-align: left;" class="btn btn-default dropdown-toggle form-control front-no-box-shadow"
                                                                type="button"id="selectAppnameBtn" data-toggle="dropdown">
                                                            <span id="appnameicon" class='glyphicon selectspan'></span><label id="appnamemenu"></label>
                                                            <span class="caret rightmiddle"></span>
                                                            <span id="appproviderEn" class="hidden"></span>
                                                        </button>
                                                        <ul id="selectAppname" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>

                                            <%--加载等待动画--%>
                                            <div class="loading">
                                                <div class="front-loading">
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                </div>
                                                <div class="panel-body text-center">正在加载请稍候</div>
                                            </div>
                                            <%--加载等待动画--%>

                                            <%--<div class="row hidden show-later">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">可用区</h5>
                                                </div>
                                                <div class="col-md-2" style="">
                                                    <select class="form-control front-no-box-shadow" id="selectZone"
                                                                onchange="changeRegion()">
                                                        <option data-icon='glyphicon-map-marker' value='lab'>lab</option>
                                                        <option data-icon='glyphicon-map-marker' value='zpark'>zpark</option>
                                                    </select>
                                                </div>
                                                &lt;%&ndash;<div class="col-md-10" style="">
                                                    <div class="btn-group">
                                                        <button type="button" class="btn btn-default" id="zone-disp">
                                                            随机分配
                                                        </button>
                                                        <button id="zone-list-btn" type="button"
                                                                class="btn btn-default dropdown-toggle"
                                                                data-toggle="dropdown" aria-haspopup="true"
                                                                aria-expanded="false">
                                                            <span class="caret"></span> <span class="sr-only">Toggle
																	Dropdown</span>
                                                        </button>
                                                        <ul class="dropdown-menu" id="zone-list">
                                                            <li><a href="javascript:void(0)">随机分配</a></li>
                                                        </ul>
                                                    </div>&ndash;%&gt;
                                                </div>--%>
                                            </div>

                                        </div>
                                    </div>
                                </tr>

                                <tr>
                                    <%--镜像--%>
                                    <div class="panel panel-default front-panel card-gutter">
                                        <div class="panel-heading">镜像</div>
                                        <div class="panel-body front-last-no-margin">
                                            <%--加载等待动画--%>
                                            <div class="loading">
                                                <div class="front-loading">
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                </div>
                                                <div class="panel-body text-center">正在加载请稍候</div>
                                            </div>
                                            <div class="row hidden show-later" style="position: relative;margin-bottom:15px">
                                                 <div id="image-public">
                                                </div>
                                            </div>
                                                <div>
                                                    已选择镜像:<p id="choosedImage" style="display: -webkit-inline-box"></p>
                                                    <a class="pull-right" href="javascript:void(0)" onclick="showMoreImage()">更多镜像</a>
                                                </div>
                                        </div>
                                    </div>
                                </tr>

                                <tr>
                                    <%--云主机--%>
                                    <div class="panel panel-default front-panel card-gutter">
                                        <div class="panel-heading">云主机</div>
                                        <div class="panel-body front-last-no-margin">
                                            <%--加载等待动画--%>
                                            <div class="loading">
                                                <div class="front-loading">
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                </div>
                                                <div class="panel-body text-center">正在加载请稍候</div>
                                            </div>
                                            <%--加载等待动画--%>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">云主机</h5>
                                                </div>
                                                <div class="col-md-10">
                                                    <button type="button" class="btn btn-default active">系列1</button>
                                                    <a href="javascript:void(0)"> <img
                                                            src="images/help.png"/>
                                                    </a>

                                                    <p>系列 I 采用 Intel Xeon CPU，DDR3 的内存。</p>
                                                </div>
                                            </div>
                                            <div class="row hidden show-later" style="margin-top: 10px;">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">I/O优化</h5>
                                                </div>
                                                <div class="col-md-10" style="padding-left: 36px">
                                                    <div class="checkbox">
                                                        <input type="checkbox" disabled="disabled">
                                                    </div>
                                                    <span>I/O优化云主机</span> <a href="javascript:void(0)"> <img
                                                        src="images/help.png"/>
                                                </a>
                                                </div>
                                            </div>
                                            <div class="row hidden show-later" style="margin-top: 20px;">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">主机规格</h5>
                                                </div>
                                                <div class="col-md-10">
                                                    <div class="btn-group">
                                                        <button type="button" class="btn btn-default"
                                                                id="instanceType-disp">
                                                            请选择版本
                                                        </button>
                                                        <button type="button"
                                                                class="btn btn-default dropdown-toggle"
                                                                data-toggle="dropdown" aria-haspopup="true"
                                                                aria-expanded="false">
                                                            <span class="caret"></span> <span class="sr-only">Toggle
																	Dropdown</span>
                                                        </button>
                                                        <ul class="dropdown-menu" id="instanceType-list">
                                                            <li><a href="javascript:void(0)">无</a></li>
                                                        </ul>
                                                    </div>

                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </tr>

                                <tr>
                                    <%--云硬盘--%>
                                    <div class="panel panel-default front-panel card-gutter">
                                        <div class="panel-heading">云硬盘</div>
                                        <div class="panel-body front-last-no-margin">
                                            <%--加载等待动画--%>
                                            <div class="loading">
                                                <div class="front-loading">
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                </div>
                                                <div class="panel-body text-center">正在加载请稍候</div>
                                            </div>
                                            <%--加载等待动画--%>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2 col-sm-12 col-xs-12">
                                                    <h5 class="label-pos2">系统盘</h5>
                                                </div>
                                                <div class="col-md-10" style="margin: 0px;padding: 0px;">
                                                    <div class="col-md-3 col-sm-12 col-xs-12">
                                                        <div class="btn-group">
                                                            <button type="button" class="btn btn-default">普通云盘</button>
                                                            <button type="button"
                                                                    class="btn btn-default dropdown-toggle"
                                                                    data-toggle="dropdown" aria-haspopup="true"
                                                                    aria-expanded="false">
                                                                <span class="caret"></span>
                                                                <span class="sr-only">Toggle	Dropdown</span>
                                                            </button>
                                                            <ul class="dropdown-menu">
                                                                <li><a href="javascript:void(0)">普通云盘</a></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3 col-sm-2 col-xs-4">
                                                        <div class="btn-group">
                                                            <button type="button" class="btn btn-default"
                                                                    id="hardDisk-sys-disp">
                                                                30G
                                                            </button>
                                                            <button type="button"
                                                                    class="btn btn-default dropdown-toggle"
                                                                    data-toggle="dropdown" aria-haspopup="true"
                                                                    aria-expanded="false">
                                                                <span class="caret"></span>
                                                                <span class="sr-only">Toggle Dropdown</span>
                                                            </button>
                                                            <ul class="dropdown-menu" id="hardDisk-sys-list">
                                                                <li><a href="javascript:void(0)">30G</a></li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <br>
                                            <br>

                                            <div class="row hidden show-later" style="margin-top: -10px">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">数据盘</h5>
                                                </div>
                                                <div id="added-disk-list" class="col-md-10">
                                                    <div class="row added-disk hidden" style="margin-top:8px;">
                                                        <div class="col-md-3 col-sm-12 col-xs-12 ">
                                                            <div class="btn-group">
                                                                <button type="button" class="btn btn-default">普通云盘
                                                                </button>
                                                                <button type="button"
                                                                        class="btn btn-default dropdown-toggle"
                                                                        data-toggle="dropdown" aria-haspopup="true"
                                                                        aria-expanded="false">
                                                                    <span class="caret"></span>
                                                                    <span class="sr-only">Toggle Dropdown</span>
                                                                </button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="javascript:void(0)">普通云盘</a></li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3 col-sm-2 col-xs-4">
                                                            <div class="btn-group">
                                                                <button type="button"
                                                                        class="btn btn-default hardDisk-added-disp">
                                                                    30G
                                                                </button>
                                                                <button type="button"
                                                                        class="btn btn-default dropdown-toggle"
                                                                        data-toggle="dropdown" aria-haspopup="true"
                                                                        aria-expanded="false">
                                                                    <span class="caret"></span>
                                                                    <span class="sr-only">Toggle Dropdown</span>
                                                                </button>
                                                                <ul class="dropdown-menu hardDisk-added-list">
                                                                    <li><a href="javascript:void(0)">30G</a></li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3 col-sm-2 col-xs-4">
                                                            <div class="btn btn-default del-hardDisk">删除</div>
                                                        </div>
                                                    </div>
                                                    <div class="row" style="margin-top: 8px;margin:8px;">
                                                        <a onclick="addHardDisk()">
                                                            <span class="glyphicon glyphicon-plus"></span>
                                                        </a>
                                                        <span>增加一块，您还可选配<span id="hardDiskAdded-left"></span>块；</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </tr>

                                <tr>
                                    <%--带宽--%>
                                    <div class="panel panel-default front-panel card-gutter">
                                        <div class="panel-heading">带宽</div>
                                        <div class="panel-body front-last-no-margin">
                                            <%--加载等待动画--%>
                                            <div class="loading">
                                                <div class="front-loading">
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                </div>
                                                <div class="panel-body text-center">正在加载请稍候</div>
                                            </div>
                                            <%--加载等待动画--%>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2 col-sm-3 col-xs-4">
                                                    <h5 class="label-pos2">公网带宽</h5>
                                                </div>
                                                <div class="col-md-10 col-sm-9 col-xs-8 button-group-align">
                                                    <div class="front-toolbar other">
                                                        <div class="front-toolbar-header clearfix">
                                                            <button type="button"
                                                                    class="front-toolbar-toggle navbar-toggle"
                                                                    data-toggle="collapse"
                                                                    data-target="#public-bandwidth">
                                                                <span class="icon-bar"></span> <span
                                                                    class="icon-bar"></span>
                                                                <span class="icon-bar"></span>
                                                            </button>
                                                        </div>
                                                        <div id="public-bandwidth"
                                                             class="front-btn-group collapse">
                                                            <a class="btn btn-default front-no-box-shadow active">按固定带宽</a>
                                                            <a class="btn btn-default front-no-box-shadow"
                                                               disabled="disabled">按使用流量</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row show-later">
                                                <div class="col-md-2 label-gutter">
                                                    <h5 class="label-pos2">带宽</h5>
                                                </div>
                                                <div class="col-md-10" style="padding-left: 26px;margin-top:6px">
                                                    <div class="front-slider noselect">
                                                        <input id="bandWidth" type="text"
                                                               data-slider-handle="round"
                                                               data-provide="slider"
                                                               data-slider-ticks="[0,1,2,3,4,5,6,7,8,9,10,11]"
                                                               data-slider-ticks-labels='["1", "2", "3","4","5","6","7","8","9","10","11","12"]'
                                                               data-slider-tooltip="hide"
                                                               value="0"
                                                               onchange="changeBand(this.value)"/>
                                                    </div>
                                                </div>

                                            </div>

                                        </div>
                                    </div>
                                </tr>

                                <tr class="hide">
                                    <%--安全组--%>
                                    <div class="panel panel-default front-panel card-gutter hide">
                                        <div class="panel-heading">安全组</div>
                                        <div class="panel-body front-last-no-margin">
                                            <%--加载等待动画--%>
                                            <div class="loading">
                                                <div class="front-loading">
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                </div>
                                                <div class="panel-body text-center">正在加载请稍候</div>
                                            </div>
                                            <%--加载等待动画--%>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">安全组</h5>
                                                </div>
                                                <div class="col-md-10">
                                                    <div class="btn-group">
                                                        <button type="button" class="btn btn-default"
                                                                id="security-disp">
                                                            请选择安全组
                                                        </button>
                                                        <button type="button"
                                                                class="btn btn-default dropdown-toggle"
                                                                data-toggle="dropdown" aria-haspopup="true"
                                                                aria-expanded="false">
                                                            <span class="caret"></span>
                                                            <span class="sr-only">Toggle Dropdown</span>
                                                        </button>
                                                        <ul class="dropdown-menu" id="security-list">
                                                            <li><a href="javascript:void(0)">无</a></li>
                                                        </ul>
                                                    </div>

                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </tr>

                                <tr>
                                    <%--购买量--%>
                                    <div class="panel panel-default front-panel card-gutter">
                                        <div class="panel-heading">购买量</div>
                                        <div class="panel-body front-last-no-margin">
                                            <%--加载等待动画--%>
                                            <div class="loading">
                                                <div class="front-loading">
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                    <div class="front-loading-block"></div>
                                                </div>
                                                <div class="panel-body text-center">正在加载请稍候</div>
                                            </div>
                                            <%--加载等待动画--%>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2 col-sm-3 col-xs-4">
                                                    <h5 class="label-pos2">付费方式</h5>
                                                </div>
                                                <div class="col-md-10 col-sm-9 col-xs-8 button-group-align">
                                                    <div class="front-toolbar other">
                                                        <div class="front-toolbar-header clearfix">
                                                            <button type="button"
                                                                    class="front-toolbar-toggle navbar-toggle"
                                                                    data-toggle="collapse" data-target="#payment">
                                                                <span class="icon-bar"></span> <span
                                                                    class="icon-bar"></span>
                                                                <span class="icon-bar"></span>
                                                            </button>
                                                        </div>
                                                        <div id="payment" class="front-btn-group collapse">
                                                            <a id="year"
                                                               class="btn btn-default front-no-box-shadow active"
                                                               onclick="year();">包年</a>
                                                            <a id="month" class="btn btn-default front-no-box-shadow"
                                                               onclick="month();">包月</a>
                                                            <a id="day" class="btn btn-default front-no-box-shadow"
                                                               onclick="day();">包天</a>
                                                            <a class="btn btn-default front-no-box-shadow"
                                                               disabled="disabled">按量付费</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row  show-later" style="margin-top: 20px">
                                                <div class="col-md-2 label-gutter">
                                                    <h5 class="label-pos2">购买时长</h5>
                                                </div>
                                                <div class="col-md-10 time-slider"
                                                     style="padding-left: 26px;height: 30px;">
                                                    <div id="year-slider" class="front-slider noselect">
                                                        <input id="year-length" type="text" style="width: 60%;"
                                                               data-slider-handle="round"
                                                               data-provide="slider"
                                                               data-slider-ticks="[0,1,2]"
                                                               data-slider-ticks-labels='["1", "2", "3"]'
                                                               data-slider-tooltip="hide"
                                                               onchange="changeTime(this.value)"/>
                                                    </div>
                                                    <div id="month-slider" class="front-slider noselect ">
                                                        <input id="month-length" type="text" style="width: 80%;"
                                                               data-slider-handle="round"
                                                               data-provide="slider"
                                                               data-slider-ticks="[0,1,2,3,4,5,6,7,8,9,10,11]"
                                                               data-slider-ticks-labels='["1", "2", "3","4","5","6","7","8","9","10","11","12"]'
                                                               data-slider-tooltip="hide"
                                                               value="0"
                                                               onchange="changeTime(this.value)"/>
                                                    </div>
                                                    <div id="day-slider" class="front-slider noselect ">
                                                        <input id="day-length" type="text" style="width: 90%;"
                                                               data-slider-handle="round"
                                                               data-provide="slider"
                                                               data-slider-ticks="[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19]"
                                                               data-slider-ticks-labels='["1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"]'
                                                               data-slider-tooltip="hide"
                                                               value="0"
                                                               onchange="changeTime(this.value)"/>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="row hidden show-later" style="margin-top: 18px">
                                                <div class="col-md-2 label-gutter">
                                                    <h5 class="label-pos2">数量</h5>
                                                </div>
                                                <div class="col-md-10" style="padding-top: 8px">
														<span id="up" style="cursor: pointer"
                                                              class="glyphicon glyphicon-plus"
                                                              onclick="changeNum(1)"></span>
                                                    <div style="display: inline-block; margin-left: 4px; margin-right: 4px;">
                                                        <span class="slider-label"
                                                              id="num">1</span><span>&nbsp;&nbsp;台</span>
                                                    </div>
														<span id="down" style="cursor: pointer"
                                                              class="glyphicon glyphicon-minus"
                                                              onclick="changeNum(-1)"></span>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </tr>

                                <tr>
                                    <%--主机设置--%>
                                    <div class="panel panel-default front-panel">
                                        <div class="panel-heading">主机设置</div>
                                        <div class="panel-body front-last-no-margin">

                                            <div class="row">
                                                <div class="col-md-2 col-sm-3 col-xs-4">
                                                    <h5 class="label-pos2">设置密码</h5>
                                                </div>
                                                <div class="col-md-10 col-sm-9 col-xs-8 button-group-align">
                                                    <div class="front-toolbar other">
                                                        <div class="front-toolbar-header clearfix">
                                                            <button type="button"
                                                                    class="front-toolbar-toggle navbar-toggle"
                                                                    data-toggle="collapse" data-target="#password">
                                                                <span class="icon-bar"></span> <span
                                                                    class="icon-bar"></span>
                                                                <span class="icon-bar"></span>
                                                            </button>
                                                        </div>
                                                        <div class="front-btn-group collapse">
                                                            <a class="btn btn-default front-no-box-shadow active"
                                                               id="setting-now">立即设置</a>
                                                            <a class="btn btn-default front-no-box-shadow"
                                                               id="setting-later">创建后设置</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <%--<div class="row">--%>
                                                <%--<div id="password-later-box">--%>
                                                <div>
                                                     <%--class="hidden col-md-10 col-md-offset-2 col-sm-3 col-xs-4">--%>
                                                    <div class="row" style="margin-bottom:5px">
                                                        <div class="col-md-2">
                                                            <h5 class="label-pos2">实例名称</h5>
                                                        </div>
                                                        <div class="col-md-4 col-sm-8">
                                                            <input class="form-control" type="text" id="instancename" oninput="refresh()">
                                                            <input hidden type="text" id="userId" value="${session.userId}">
                                                        </div>
                                                        <div class="col-md-6">
                                                            <span>长度为8-128个字符</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            <%--</div>--%>

                                            <div id="password-box">
                                                <%--<div class="row" style="margin-bottom:5px">--%>
                                                    <%--<div class="col-md-2">--%>
                                                        <%--<h5 class="label-pos2">实例名称</h5>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="col-md-4 col-sm-8">--%>
                                                        <%--<input class="form-control" type="text" id="instancename">--%>
                                                    <%--</div>--%>
                                                    <%--<div class="col-md-6">--%>
                                                        <%--<span>长度为8-128个字符</span>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                                <div class="row" style="margin-bottom: 5px">
                                                    <div class="col-md-2">
                                                        <h5 class="label-pos2">用户名</h5>
                                                    </div>
                                                    <div class="col-md-4 col-sm-8">
                                                        <input class="form-control" type="text" id="hostname">
                                                    </div>
                                                </div>
                                                <div class="row" style="margin-bottom: 5px">
                                                    <div class="col-md-2">
                                                        <h5 class="label-pos2">登录密码</h5>
                                                    </div>
                                                    <div class="col-md-4 col-sm-8">
                                                        <input class="form-control" type="password" id="password"
                                                               oninput="changePassword()">
                                                    </div>
                                                    <div class="col-md-2">
                                                        <span>8-30个字符</span>
                                                    </div>
                                                    <div class="col-md-3 hidden" id="password-error2">
                                                        <span style="color:#ff0000;">密码至少8位</span>
                                                    </div>
                                                </div>
                                                <div class="row" style="margin-bottom: 5px">
                                                    <div class="col-md-2">
                                                        <h5 class="label-pos2">确认密码</h5>
                                                    </div>
                                                    <div class="col-md-4 col-sm-8">
                                                        <input class="form-control" type="password" id="password2"
                                                               oninput="changePassword()">
                                                    </div>
                                                    <div class="col-md-6 hidden" id="password-error">
                                                        <span style="color:#ff0000;">两次输入的密码不一致</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <%--购买清单--%>
                <div class="col-md-3 col-xs-12 col-sm-12" id="right-card">

                    <div class="panel panel-default front-panel"
                         style="margin-top: 5px">
                        <div class="panel-heading">购买清单</div>
                    </div>
                    <!--<h5 style="color: #1196db">购买清单</h5>-->
                    <div class="panel panel-default front-panel"
                         style="margin-top: -8px">
                        <div class="panel-heading">当前配置</div>
                        <div class="panel-body front-last-no-margin" id="instance-panel">
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">提供商</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-provider" class="content-gutter">云海</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">地域</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-region" class="content-gutter">北京(中关村机房)</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">镜像</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-image" class="content-gutter">Centos 6.6</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">规格</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-type" class="content-gutter">1核2GB(标准型s2)</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">云硬盘</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-disk" class="content-gutter">50G</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">网络</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-band" class="content-gutter">带宽50Mbps</div>
                                </div>
                            </div>
                            <div class="row hide">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">安全组</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-security" class="content-gutter">带宽50Mbps</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">购买量</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-time" class="content-gutter">1个月 × 1台</div>
                                </div>
                            </div>
                            <div class="row">
                                <!--  <div class="col-md-4 col-sm-5 col-xs-6 right-label">
            条款：
          </div>-->
                                <div class="col-md-12 col-sm-7 col-xs-6 right-label">
                                    <div style="margin-top: -10px; margin-left: 25px">
                                        <div class="checkbox">
                                            <input type="checkbox" id="protocal-checkbox" onclick="refresh()">
                                        </div>
                                        <span>我同意</span><a href="javascript:void(0)">云海服务条款</a>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-5 right-label">
                                    <div style="margin-left: 5px">配置费用</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-offset-3 col-md-1 col-sm-1 col-xs-1">
                                    <img src="images/big-money.png"/>
                                </div>
                                <div class="col-md-2 col-sm-2 col-xs-2">
                                    <span id="instance-price"
                                          style="font-size: 32px; color: #337ab7; margin-left: -2px">100</span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-offset-4 col-sm-offset-5 col-xs-offset-5">
                                    <button type="button" class="btn btn-primary" id="btn-new-instance"
                                            onclick="newInstance()">立即购买
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="../../template/_footer.jsp"/>
</div>
<s:include value="../../template/_global.jsp"/>
<script src="js/vm/newvm/newyunhaivm.js"></script>
</body>
</html>



<%--&lt;%&ndash;加载等待动画&ndash;%&gt;--%>
<%--<div class="row hidden show-later">--%>
<%--<div class="col-md-2 col-sm-3 col-xs-4">--%>
<%--<h5 class="label-pos2">镜像类型</h5>--%>
<%--</div>--%>
<%--<div class="col-md-10 col-sm-9 col-xs-8 button-group-align">--%>
<%--<div class="front-toolbar other">--%>
<%--<div class="front-toolbar-header clearfix">--%>
<%--<button type="button"--%>
<%--class="front-toolbar-toggle navbar-toggle"--%>
<%--data-toggle="collapse" data-target="#mirror-type">--%>
<%--<span class="icon-bar"></span> <span--%>
<%--class="icon-bar"></span>--%>
<%--<span class="icon-bar"></span>--%>
<%--</button>--%>
<%--</div>--%>
<%--<div id="mirror-type" class="front-btn-group collapse">--%>
<%--<a class="btn btn-default front-no-box-shadow active">公共镜像</a>--%>
<%--<a class="btn btn-default front-no-box-shadow"--%>
<%--disabled="disabled">自定义镜像</a>--%>
<%--<a class="btn btn-default front-no-box-shadow"--%>
<%--disabled="disabled">镜像市场</a>--%>
<%--<a class="btn btn-default front-no-box-shadow"--%>
<%--disabled="disabled">空白镜像</a>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="row hidden show-later">--%>
<%--<div class="col-md-2 label-gutter">--%>
<%--<h5 class="label-pos2">公共镜像</h5>--%>
<%--</div>--%>
<%--<div class="col-md-10">--%>
<%--<div class="btn-group">--%>
<%--<button type="button" class="btn btn-default"--%>
<%--id="os-disp">请选择OS--%>
<%--</button>--%>
<%--<button type="button"--%>
<%--class="btn btn-default dropdown-toggle"--%>
<%--data-toggle="dropdown" aria-haspopup="true"--%>
<%--aria-expanded="false">--%>
<%--<span class="caret"></span> <span class="sr-only">Toggle--%>
<%--Dropdown</span>--%>
<%--</button>--%>
<%--<ul class="dropdown-menu" id="os-list">--%>
<%--<li><a href="javascript:void(0)">随机分配</a></li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--<div class="btn-group">--%>
<%--<button type="button" class="btn btn-default" id="image-disp">--%>
<%--请选择版本--%>
<%--</button>--%>
<%--<button type="button" id="image-dropdown"--%>
<%--class="btn btn-default disabled dropdown-toggle"--%>
<%--data-toggle="dropdown" aria-haspopup="true"--%>
<%--aria-expanded="false">--%>
<%--<span class="caret"></span> <span class="sr-only">Toggle--%>
<%--Dropdown</span>--%>
<%--</button>--%>
<%--<ul class="dropdown-menu" id="image-list">--%>
<%--<li><a href="javascript:void(0)">无</a></li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--</div>--%>
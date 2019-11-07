<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/20
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>申请云主机</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <s:include value="../../../template/_head.jsp"/>
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

        .tip {
            color: #4c4c4c;
            font-size: 10px;
            margin-top: 4px;
            margin-left: 4px;
        }
    </style>
</head>
<body class="front-body">
<s:include value="../../../template/_banner.jsp?menu=newvm"/>
<input class="hidden" value='${param.appname}' id="aliyun_appname"><!-- 从列表也传过来的appname -->
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
                <div class="col-md-12">
                    <div class="front-toolbar other">
                        <div class="front-toolbar-header clearfix">
                        </div>
                        <div id="iass-mygroup" class="front-btn-group collapse">
                            <a href="javascript:void(0)" id="prepaid" class="btn btn-default front-no-box-shadow active"
                               onclick="changeInstanceChargeType('prepaid')">包年包月</a>
                            <a id="postpaid" class="btn btn-default front-no-box-shadow"
                               onclick="changeInstanceChargeType('postpaid')">按量付费</a>
                        </div>
                    </div>
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
                                            <div class="row  show-later">
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
                                                            <a class="btn btn-default front-no-box-shadow disabled" id="yunhai"
                                                               onclick="window.location.href='vm/newvm/newyunhaivm.jsp'">云海</a>
                                                            <a class="btn btn-default front-no-box-shadow active"
                                                               id="aliyun">阿里云</a>
                                                            <a class="btn btn-default front-no-box-shadow disabled" id="amazon">亚马逊</a>
                                                        </div>
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
                                                        <div class="tip">不同地域之间的产品内网不互通；订购后不支持更换地域，请谨慎选择<a
                                                                target="_blank"
                                                                href="https://help.aliyun.com/knowledge_detail/40654.html">教我选择>></a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">可用区</h5>
                                                </div>
                                                <div class="col-md-10">
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
                                                    </div>
                                                    <div class="tip">
                                                        <a href="javascript:void(0)">查看实例分布详情（等待接入）>></a>
                                                        <a href="javascript:void(0)" type="button" title="可用区"
                                                           data-container="body" data-toggle="popover"
                                                           data-placement="right"
                                                           data-content="可用区：指在同一地域下，电力、
                                                       网络隔离的物理区域，可用区之间内网互通，
                                                       不同可用区之间故障隔离。如果您需要提高应用的高可用性
                                                       ，建议您将实例创建在不同的可用区内。">
                                                            <img src="images/help.png"/>
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </tr>
                                <tr>
                                    <%--安全组--%>
                                    <div class="panel panel-default front-panel card-gutter">
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
                                                    <div id="security-group" class="btn-group ">
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
                                                    <span>
                                                        <a id="set-security-tip" class="hidden" href="fw/fwlist.jsp"
                                                           style="vertical-align: middle;">无可用安全组，点击前往设置
                                                        </a>
                                                         <div class="tip">一个实例创建时必须指定加入一个安全组。安全组需要预先创建</div>
                                                    </span>
                                                </div>
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
                                                <div class="col-md-10 col-sm-9 col-xs-8 button-group-align">
                                                    <div class="front-toolbar other">
                                                        <div class="front-toolbar-header clearfix">
                                                            <button type="button"
                                                                    class="front-toolbar-toggle navbar-toggle"
                                                                    data-toggle="collapse">
                                                                <span class="icon-bar"></span>
                                                                <span class="icon-bar"></span>
                                                                <span class="icon-bar"></span>
                                                            </button>
                                                        </div>
                                                        <div id="instanceGeneration" class="front-btn-group collapse">

                                                        </div>
                                                        <div class="tip">
                                                            <span id="generation-simple-introduce"></span>
                                                            <a href="javascript:void(0)" type="button" title="实例比较"
                                                               data-container="body" data-toggle="popover"
                                                               data-placement="right"
                                                               data-content="系列 II 较系列 I 进行了硬件升级，采用 Haswell CPU、DDR4 内存，
                                                               并默认为 I/O 优化实例，同时增加了一些新的指令集，使整数和浮点运算的性能翻倍，
                                                               整体计算能力更强。系列 I 与系列 II 之间不能互相升降配。">
                                                                <img src="images/help.png"/>
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="row hidden show-later" style="margin-top: 10px;">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">I/O优化</h5>
                                                </div>
                                                <div class="col-md-10" style="padding-left: 36px">
                                                    <div id="ioCheckBox" class="checkbox">
                                                    </div>
                                                    <span>I/O优化云主机</span>
                                                    <a href="javascript:void(0)" type="button" title="IO优化云主机"
                                                       data-container="body" data-toggle="popover"
                                                       data-placement="right"
                                                       data-content="选择支持 I/O 优化的实例，挂载 SSD云盘时能够获得 SSD云盘的全部存储性能，
                                                       因为 I/O 优化为实例与云盘之间提供更好的网络能力，可保证 SSD云盘存储性能的发挥。
                                                       对于不支持 I/O 优化的实例，挂载 SSD云盘时，通常最高可获得 1000 左右的 IOPS 性能。">
                                                        <img src="images/help.png"/>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="row hidden show-later" style="margin-top: 20px;">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">主机规格</h5>
                                                </div>
                                                <div class="col-md-10">
                                                    <h5><span id="instanceType">等待选择</span></h5>
                                                    <span class="glyphicon glyphicon-th-large"></span>
                                                    <a href="javascript:void(0)"
                                                       onclick="chooseInstanceType()">点击选择实例规格</a>
                                                </div>
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
                                            <%--加载等待动画--%>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2 col-sm-3 col-xs-4">
                                                    <h5 class="label-pos2">镜像类型</h5>
                                                </div>
                                                <div class="col-md-10 col-sm-9 col-xs-8 button-group-align">
                                                    <div class="front-toolbar other">
                                                        <div class="front-toolbar-header clearfix">
                                                            <button type="button"
                                                                    class="front-toolbar-toggle navbar-toggle"
                                                                    data-toggle="collapse" data-target="#mirror-type">
                                                                <span class="icon-bar"></span> <span
                                                                    class="icon-bar"></span>
                                                                <span class="icon-bar"></span>
                                                            </button>
                                                        </div>
                                                        <div id="mirror-type" class="front-btn-group collapse">
                                                            <a class="btn btn-default front-no-box-shadow active">公共镜像</a>
                                                            <a class="btn btn-default front-no-box-shadow"
                                                               disabled="disabled">自定义镜像</a>
                                                            <a class="btn btn-default front-no-box-shadow"
                                                               disabled="disabled">镜像市场</a>
                                                            <a class="btn btn-default front-no-box-shadow"
                                                               disabled="disabled">空白镜像</a>
                                                        </div>
                                                        <div class="tip">公共镜像即基础操作系统。镜像市场在基础操作系统上，集成了运行环境和各类软件。</div>

                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2 label-gutter">
                                                    <h5 class="label-pos2">公共镜像</h5>
                                                </div>
                                                <div class="col-md-10">
                                                    <div class="btn-group">
                                                        <button type="button" class="btn btn-default"
                                                                id="os-disp">请选择OS
                                                        </button>
                                                        <button type="button"
                                                                class="btn btn-default dropdown-toggle"
                                                                data-toggle="dropdown" aria-haspopup="true"
                                                                aria-expanded="false">
                                                            <span class="caret"></span> <span class="sr-only">Toggle
																	Dropdown</span>
                                                        </button>
                                                        <ul class="dropdown-menu" id="os-list"
                                                            style="max-height:200px;overflow-y: scroll">
                                                            <li><a href="javascript:void(0)">随机分配</a></li>
                                                        </ul>
                                                    </div>
                                                    <div class="btn-group">
                                                        <button type="button" class="btn btn-default" id="image-disp">
                                                            请选择版本
                                                        </button>
                                                        <button type="button" id="image-dropdown"
                                                                class="btn btn-default disabled dropdown-toggle"
                                                                data-toggle="dropdown" aria-haspopup="true"
                                                                aria-expanded="false">
                                                            <span class="caret"></span> <span class="sr-only">Toggle
																	Dropdown</span>
                                                        </button>
                                                        <ul class="dropdown-menu" id="image-list"
                                                            style="max-height:200px;overflow-y: scroll">
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
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">系统盘</h5>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="btn-group">
                                                        <button id="system-disk-tip" type="button"
                                                                class="btn btn-default">请选择磁盘类型
                                                        </button>
                                                        <button type="button"
                                                                class="btn btn-default dropdown-toggle"
                                                                data-toggle="dropdown" aria-haspopup="true"
                                                                aria-expanded="false">
                                                            <span class="caret"></span>
                                                            <span class="sr-only">Toggle	Dropdown</span>
                                                        </button>
                                                        <ul id="system-disk" class="dropdown-menu">
                                                        </ul>
                                                    </div>
                                                    <div class="tip">如何选择 SSD云盘 / 高效云盘 / 普通云盘，请看<a target="_blank"
                                                                                                   href="https://help.aliyun.com/document_detail/25382.html">详细说明>></a>
                                                    </div>
                                                </div>

                                                <div class="col-md-1">
                                                    <h5 class="label-pos2">大小</h5>
                                                </div>
                                                <div class="col-md-5">
                                                    <span><input id="input-system-disk-size" class="form-control"
                                                                 type="number" placeholder="40-500G" min="40" max="500"
                                                                 onchange="changeDiskSize(this,'system')"> 容量范围：40-500GB</span>
                                                </div>
                                            </div>
                                            <br>
                                            <br>
                                            <div class="row hidden show-later">
                                                <div class="col-md-2">
                                                    <h5 class="label-pos2">数据盘</h5>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="btn-group">
                                                        <button id="data-disk-tip" type="button"
                                                                class="btn btn-default">请选择磁盘类型
                                                        </button>
                                                        <button type="button"
                                                                class="btn btn-default dropdown-toggle"
                                                                data-toggle="dropdown" aria-haspopup="true"
                                                                aria-expanded="false">
                                                            <span class="caret"></span>
                                                            <span class="sr-only">Toggle	Dropdown</span>
                                                        </button>
                                                        <ul id="data-disk" class="dropdown-menu">
                                                        </ul>
                                                    </div>
                                                </div>

                                                <div class="col-md-1">
                                                    <h5 class="label-pos2">大小</h5>
                                                </div>
                                                <div class="col-md-5">
                                                    <span>
                                                        <input class="form-control" type="number"
                                                               placeholder="5-2000G" min="5" max="2000"
                                                               onchange="changeDiskSize(this,'data')"> 容量范围：5-2000GB
                                                    </span>
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
                                                            <a id="pay-by-bandwidth"
                                                               class="btn btn-default front-no-box-shadow active"
                                                               onclick="changeInternetChargeType('PayByBandwidth')">按固定带宽</a>
                                                            <a id="pay-by-traffic"
                                                               class="btn btn-default front-no-box-shadow"
                                                               onclick="changeInternetChargeType('PayByTraffic')">按使用流量</a>
                                                        </div>
                                                        <a href="javascript:void(0)" type="button" title="带宽"
                                                           data-container="body" data-toggle="popover"
                                                           data-placement="right"
                                                           data-content="按固定带宽的方式：需指定公网出方向的带宽的大小，如 10Mbps(单位为 bit),如果经典网络类型的ECS实例，
                                                           费用合并在ECS实例费用中一起支付，如果专有网络类型的ECS实例，先使用后付费，计费周期为天，根据当天购买最大带宽的价格
                                                           x 实际使用小时数计费（不足1小时按照1小时计费）。按使用流量的方式：是按公网出方向的实际发生的网络流量进行收费。
                                                           先使用后付费，按小时计量计费。为了防止突然爆发的流量产生较高的费用，可以指定容许的最大网络带宽进行限制">
                                                            <img src="images/help.png"/>
                                                        </a>
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
                                                               data-slider-ticks="[0,1,2,3,4,5,6,7,8,9,10]"
                                                               data-slider-ticks-labels='["0M","2M", "5M", "10M","20M","30M","50M","60M","70M","80M","100M"]'
                                                               data-slider-tooltip="hide"
                                                               onchange="changeBand(this.value)"/>
                                                    </div>
                                                    <div id="pay-by-traffic-tip" class="tip hidden">按使用流量：是先使用后付费产品，每小时扣费。为了您的服务正常运行请保证您账户余额充足。</div>
                                                    <div class="tip">
                                                        阿里云免费提供最高 5Gbps 的恶意流量攻击防护，
                                                        <a target="_blank" href="https://help.aliyun.com/knowledge_detail/40032.html">了解更多>></a>
                                                        <a target="_blank" href="https://www.aliyun.com/product/ddos?spm=5176.2020520101.ecsPrepaySelect.27.Kbmc8r">提升防护能力>></a>
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
                                                            <a id="instance-type-tip"
                                                               class="btn btn-default front-no-box-shadow"
                                                               disabled="disabled">包年包月</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div id="period-tip" class="row  show-later" style="margin-top: 20px">
                                                <div class="col-md-2 label-gutter">
                                                    <h5 class="label-pos2">购买时长</h5>
                                                </div>
                                                <div class="col-md-10 time-slider"
                                                     style="padding-left: 26px;height: 30px;">
                                                    <div id="year-slider" class="front-slider noselect">
                                                        <input id="year-length" type="text" style="width: 60%;"
                                                               data-slider-handle="round"
                                                               data-provide="slider"
                                                               data-slider-ticks="[0,1,2,3,4,5,6,7,8,9,10,11]"
                                                               data-slider-ticks-labels='["1", "2", "3","4","5","6","7","8","9","1年","2年","3年"]'
                                                               data-slider-tooltip="hide"
                                                               onchange="changePeriod(this.value)"/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row hidden show-later" style="margin-top: 18px">
                                                <div class="col-md-2 label-gutter">
                                                    <h5 class="label-pos2">数量</h5>
                                                </div>
                                                <div class="col-md-10" style="padding-top: 8px">
                                                    <input type="number" min="1" value="1" placeholder="最低数量1台"
                                                           onchange="changeNum(this)" disabled="disabled">&nbsp;台
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </tr>
                                <tr>
                                    <%--主机设置--%>
                                    <div class="panel panel-default front-panel">
                                        <div class="panel-heading">主机设置</div>
                                        <div class="panel-body front-last-no-margin ">
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
                                            <div class="row">
                                                <div id="password-later-box"
                                                     class="hidden col-md-10 col-md-offset-2 col-sm-3 col-xs-4">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-2"></div>
                                                <div class="tip col-md-6">请牢记您所设置的密码，如遗忘可登录 ECS 控制台重置密码。</div>
                                            </div>
                                            <br/>
                                            <div id="password-box">

                                                <div class="row" style="margin-bottom: 5px">
                                                    <div class="col-md-2">
                                                        <h5 class="label-pos2">登录密码</h5>
                                                    </div>
                                                    <div class="col-md-4 col-sm-8">
                                                        <input class="form-control" type="password" id="password"
                                                               oninput="checkAliYunPassword()" max="30" min="8">
                                                    </div>
                                                    <div class="col-md-6 hidden" id="password-error2">
                                                        <span style="color:#ff0000;font-size: 10px;">8 - 30 个字符，且同时包含三项（大写字母，小写字母，数字和特殊符号） </span>
                                                    </div>
                                                </div>
                                                <div class="row" style="margin-bottom: 5px">
                                                    <div class="col-md-2">
                                                        <h5 class="label-pos2">确认密码</h5>
                                                    </div>
                                                    <div class="col-md-4 col-sm-8">
                                                        <input class="form-control" type="password" id="password2"
                                                               oninput="checkAliYunPassword()">
                                                    </div>
                                                    <div class="col-md-6 hidden" id="password-error">
                                                        <span style="color:#ff0000;font-size: 10px;">两次输入的密码不一致</span>
                                                    </div>
                                                </div>
                                                <div class="row" style="margin-bottom:5px">
                                                    <div class="col-md-2">
                                                        <h5 class="label-pos2">实例名称</h5>
                                                    </div>
                                                    <div class="col-md-4 col-sm-8">
                                                        <input class="form-control" type="text" id="instance-name"
                                                               placeholder="如不填写系统自动默认生成" maxlength="128" onblur="">
                                                    </div>
                                                    <div class="col-md-6">
                                                        <span style="font-size: 10px;">(禁止使用纯汉语)长度为2-128个字符，以大小写字母或中文开头，可包含数字，"."，"_"或"-"</span>
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
                                    <div id="instance-provider" class="content-gutter">阿里云</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">地域</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-region" class="content-gutter">请选择</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">镜像</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-image" class="content-gutter">请选择</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">规格</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-type" class="content-gutter">请选择</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">云硬盘</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-disk" class="content-gutter">请选择</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">网络</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-band" class="content-gutter">带宽0Mbps</div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">安全组</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div id="instance-security" class="content-gutter">请选择</div>
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
                                            <input type="checkbox" id="protocol-checkbox">
                                        </div>
                                        <span>我同意</span><a href="javascript:void(0)">阿里云服务条款</a>
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
                                          style="font-size: 32px; color: #337ab7; margin-left: -2px">0</span>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-offset-4 col-sm-offset-5 col-xs-offset-5">
                                    <button type="button" class="btn btn-primary" id="btn-new-instance"
                                            disabled="disabled"
                                            onclick="newAliYunInstance()">立即购买
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="../../../template/_footer.jsp"/>
</div>
<s:include value="../../../template/_global.jsp"/>
<script src="js/vm/newvm/newaliyunvm.js"></script>
<script>
    $(function () {
        $("[data-toggle='popover']").popover();
    });
</script>
</body>
</html>

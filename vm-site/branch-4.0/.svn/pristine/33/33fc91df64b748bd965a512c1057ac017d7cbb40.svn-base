<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/8/1
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>申请云硬盘</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <s:include value="../../template/_head.jsp"/>
    <style>
        .tip {
            color: #4c4c4c;
            font-size: 10px;
            margin-top: 4px;
            margin-left: 4px;
        }
    </style>
</head>
<body class="front-body">
<s:include value="../../template/_banner.jsp?menu=newhd"/>
<input class="hidden" value='${param.appname}' id="yunhai_appname"><!-- 从列表也传过来的appname -->
<input class="hidden" value='${param.ulMenuName}' id="yunhai_ulMenuName"><!--切换后的云账户名称-->
<input class="hidden" value='${param.ulMenuProviderEn}' id="yunhai_ulMenuProviderEn"><!--切换后的提供商-->
<div class="front-inner">
    <div class="container">
        <div class="fix-gutter">
            <div class="row">
                <div class="col-md-12">
                    <ul class="breadcrumb">
                        <li><a href="hd/hd_list.jsp?menu=hd">云硬盘</a></li>
                        <li class="active">购买阿里云盘</li>
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
                                    <div class="panel panel-default front-panel card-gutter">
                                        <div class="panel-heading">基本配置</div>
                                        <div class="panel-body front-last-no-margin">
                                            <%--加载等待动画--%>
                                            <div id="loading">

                                            </div>
                                            <div id="base-configuration" class="hidden">
                                                <div class="row show-later">
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
                                                <div class="row  show-later">
                                                    <div class="col-md-2 col-sm-3 col-xs-4">
                                                        <h5 class="label-pos2">地域:</h5>
                                                    </div>
                                                    <div class="col-md-10 col-sm-9 col-xs-8 button-group-align">
                                                        <div class="front-toolbar other">
                                                            <div class="front-toolbar-header clearfix">
                                                                <button type="button"
                                                                        class="front-toolbar-toggle navbar-toggle"
                                                                        data-toggle="collapse"
                                                                        data-target="#region-list">
                                                                    <span class="icon-bar"></span>
                                                                    <span class="icon-bar"></span>
                                                                    <span class="icon-bar"></span>
                                                                </button>
                                                            </div>
                                                            <div id="region-list" class="front-btn-group collapse">
                                                            </div>
                                                            <div class="tip">不同地域之间的产品内网不互通；订购后不支持更换地域，请谨慎选择<a
                                                                    target="_blank"
                                                                    href="https://help.aliyun.com/knowledge_detail/40556.html">教我选择>></a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row  show-later">
                                                    <div class="col-md-2">
                                                        <h5 class="label-pos2">可用区:</h5>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <select id="zone-list" class="form-control"
                                                                onchange="changeZone()">

                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="row  show-later" style="margin-top:10px">
                                                    <div class="col-md-2">
                                                        <h5 class="label-pos2">云盘:</h5>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <select id="disk-categories" class="form-control"
                                                                onchange="changeDiskCategory()">
                                                        </select>
                                                        <div class="tip">如何选择 SSD云盘 / 高效云盘 / 普通云盘，请看<a target="_blank"
                                                                                                       href="https://help.aliyun.com/document_detail/25382.html">详细说明>></a>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-3">
                                                        <div id="disk-size-div">

                                                        </div>
                                                        <span id="disk-range-tip" class="tip"></span>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </tr>
                                <tr>
                                    <div class="panel panel-default front-panel card-gutter">
                                        <div class="panel-heading">购买量</div>
                                        <div class="panel-body front-last-no-margin">
                                            <div class="row  show-later">
                                                <div class="col-md-2 col-sm-12 col-xs-12">
                                                    <h5 class="label-pos2">数量</h5>
                                                </div>
                                                <div class="col-md-3">
                                                    <input id="disk-count" type="number" class="form-control"
                                                           placeholder="磁盘数量" value="1" disabled>
                                                    <div class="tip">最多可开通 250块 云盘(暂时一次只能申请一块)</div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 col-xs-12 col-sm-12 right-card">

                    <div class="panel panel-default front-panel"
                         style="margin-top: 5px">
                        <div class="panel-heading">购买清单</div>
                    </div>
                    <div class="panel panel-default front-panel"
                         style="margin-top: -8px">
                        <div class="panel-heading">当前配置</div>
                        <div class="panel-body front-last-no-margin">
                            <%-- <div class="row">
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">硬盘名称</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div class="content-gutter"><span id="rightname">unknow-name</span></div>
                                </div>
                            </div> --%>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">提供商</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div class="content-gutter"><span id="rightprovider">阿里云</span></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">地域</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div class="content-gutter">
                                        <span id="span-region">请选择</span>(<span id="span-zone">请选择</span>)
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">容量</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div class="content-gutter">
                                        <span id="span-disk-size">请选择</span>(<span id="span-disk-category">请选择</span>)
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4 col-sm-6 col-xs-6 right-label">
                                    <div class="label-pos">数量</div>
                                </div>
                                <div class="col-md-8 col-sm-6 col-xs-6 right-label">
                                    <div class="content-gutter">
                                        <span id="goodnum">1</span>台
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-5 right-label">
                                    <div style="margin-left: 5px">配置费用</div>
                                </div>
                            </div>
                            <div class="row">
                                <div
                                        class="col-md-offset-3 col-sm-offset-4 col-xs-offset-4 col-md-1 col-sm-1 col-xs-1">
                                    <img src="images/big-money.png"/>
                                </div>
                                <div class="col-md-2 col-sm-2 col-xs-2">
										<span id="price"
                                              style="font-size: 32px; color: #337ab7; margin-left: -2px">100</span>
                                </div>
                            </div>
                            <input type="hidden" value="${param.appname}" id="appName"/>
                            <div class="row">
                                <div class="col-md-offset-4 col-sm-offset-5 col-xs-offset-5">
                                    <button id="buy-disk" type="button" onclick="buyAliYunDisk()"
                                            class="btn btn-primary">立即购买
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
<script src="js/hd/aliyun_buy_disk.js"></script>
</body>
</html>
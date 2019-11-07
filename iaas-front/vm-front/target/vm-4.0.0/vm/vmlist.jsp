<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.appcloud.vm.common.Constants" %>
<html>
<head>
    <title>云主机 - 云海IaaS</title>
    <base
            href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <s:include value="/template/_head.jsp"/>
</head>
<body class="front-body">

<input type="hidden" id="vm_totalCount"
       value="<s:property value="TotalCount"/>"/>
<input type="hidden" id="vm_pageSize"
       value="<s:property value="PageSize"/>"/>
<input type="hidden" id="vm_totalPage"
       value="<s:property value="totalPage"/>"/>

<!--首部标签-->
<s:include value="/template/_banner.jsp?menu=vm"/>

<div class="front-inner">
    <div class="container">
        <!---------------------------------- appname和regionId ------------------------------------->
        <div class="col-md-12 form-group">
            <!-- appname -->
            <div class="col-md-2" style="padding-left: 0;">
                <div class="dropdown">
                    <button id="appnameDropMenu" style="text-align: left;" class="btn btn-default dropdown-toggle form-control front-no-box-shadow"
                            type="button"id="selectAppnameBtn" data-toggle="dropdown">
                        <span id="appnameicon" class='glyphicon selectspan'></span>
                        <label id="appnamemenu" style="width:80px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;"></label>
                        <span class="caret rightmiddle"></span>
                        <span id="appproviderEn" class="hidden"></span>
                    </button>
                    <ul id="selectAppname" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                    </ul>
                </div>
            </div>

            <!-- 加载当前提供商的地区信息 -->
            <div class="col-md-2" style="padding-left: 0" id="regions">
                <select class="form-control front-no-box-shadow" id="selectRegion"
                        onchange="changeRegion()">
                </select>
            </div>
            <div class="text-right col-md-8" style="padding-right: 0px">
                <a href="javascript:void(0)" onclick="refreshList(this)" class="btn btn-default"><span class="glyphicon glyphicon-refresh"></span> 刷新</a>
                <a id="search_yun" href="javascript:void(0)" onclick="exsearch()" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> 搜索</a>
                <a id="apply_yun"  href="javascript:void(0)" onclick="yunapply()" class="btn btn-default"><span style="color: red;" class="glyphicon glyphicon-plus-sign"></span> 新建</a>
            </div>
        </div>
        <div class=" clearfix"></div>
        <!-------------------------------------- 云主机搜索 -------d--------------------------->
        <div id="yun_search_panel" class="hidden panel panel-default front-panel">
            <div class="panel-heading">
                <strong>云主机搜索</strong>
            </div>
            <div class="panel-body">
                <!------------- 云海主机的搜索 ------------>
                <div id="yunhai-content" class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-1 control-label front-label">状态</label>
                        <!-- 状态选择 -->
                        <div id="yunhai-search" class="col-md-11 other">
                            <div class="front-toolbar-header clearfix">
                                <button type="button" class="front-toolbar-toggle navbar-toggle"
                                        data-toggle="collapse" data-target="#yunhai-vmstatus">
                                    <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                                        class="icon-bar"></span>
                                </button>
                            </div>
                            <div id="yunhai-vmstatus" class="front-btn-group collapse"
                                 data-toggle="buttons">
                                <label id="group-all"
                                       class="btn btn-default front-no-box-shadow active" data-group="">
                                    <input type="radio" name="options" autocomplete="off" checked>全部
                                </label>
                                <label class="btn btn-default front-no-box-shadow"
                                       data-group="ACTIVE" style="box-shadow: none">
                                    <input type="radio" name="options" autocomplete="off" checked>运行中
                                </label>
                                <label class="btn btn-default front-no-box-shadow"
                                       data-group="STOPPED" style="box-shadow: none">
                                    <input type="radio" name="options" autocomplete="off" checked>已关机
                                </label>
                            </div>
                        </div>
                        <!-- 状态选择结束 -->
                    </div>
                    <div class="form-group">
                        <label class="col-md-1 control-label front-label"
                               for="vmkeyword">关键字</label>
                        <div class="col-md-11">
                            <input id="vmkeyword" type="text"
                                   class="form-control front-no-box-shadow" placeholder="输入云主机名称搜索">
                        </div>
                    </div>
                    <div class="form-group last-form-group">
                        <label class="col-md-1 control-label front-label" for="zoneId">可用区</label>

                        <div class="col-md-3">
                            <select id="zoneId"
                                    class="form-control front-no-radius front-no-box-shadow">
                                <option value="">--全部--</option>
                                <option value="zpark">zpark</option>
                            </select>
                        </div>
                        <!-- 老师让去掉这个，暂时给隐藏了 -->
                        <label class="col-md-1 hidden control-label front-label" for="status">状态</label>
                        <div class="col-md-3 hidden">
                            <select id="status"
                                    class="form-control front-no-radius front-no-box-shadow">
                                <option value="">--全部--</option>
                                <option value="ACTIVE">运行中</option>
                                <option value="STOPPED">已关机</option>
                            </select>
                        </div>
                        <div class="text-right col-md-8">
                            <button type="button" id="resetsearch" class="btn btn-default">重置</button>
                            <button type="button" id="groupsearch" class="btn btn-primary"
                                    onclick="vmsearch('keyword')">查找
                            </button>
                        </div>
                    </div>
                </div>

            <div id="select-content" class="hidden form-horizontal">
                <!--------------- 显示阿里云主机的搜索 ------------>
                <div class="form-group">
                    <label class="col-md-1 control-label front-label">状态</label>
                    <!-- 状态选择 -->
                    <div id="aliyun-search" class="col-md-11 other">
                        <div class="front-toolbar-header clearfix">
                            <button type="button" class="front-toolbar-toggle navbar-toggle"
                                    data-toggle="collapse" data-target="#aliyun-vmstatus">
                                <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                                    class="icon-bar"></span>
                            </button>
                        </div>
                        <div id="aliyun-vmstatus" class="front-btn-group collapse"
                             data-toggle="buttons">
                            <label id="aliyun-all" class="btn btn-default front-no-box-shadow active"
                                   data-group="">
                                <input type="radio" name="options" autocomplete="off" checked>全部
                            </label>
                            <label class="btn btn-default front-no-box-shadow"
                                   data-group="Running" style="box-shadow: none">
                                <input type="radio" name="options" autocomplete="off" checked>运行中
                            </label>
                            <label class="btn btn-default front-no-box-shadow"
                                   data-group="Stopped" style="box-shadow: none">
                                <input type="radio" name="options" autocomplete="off" checked>已关机
                            </label>
                        </div>
                    </div>
                    <!-- 状态选择结束 -->
                </div>
                <div class="form-group">
                    <label class="col-md-1 control-label front-label"
                           for="vmkeyword">关键字</label>
                    <div class="col-md-11">
                        <input id="alikeyword" type="text"
                               class="form-control front-no-box-shadow" placeholder="主机名称搜索">
                    </div>
                </div>
                <div class="form-group last-form-group">
                    <label class="col-md-1 control-label front-label" for="zoneId">付费方式</label>

                    <div class="col-md-3">
                        <select id="instance-pay"
                                class="form-control front-no-radius front-no-box-shadow">
                            <option value="">--全部--</option>
                            <option value="zpark">按量</option>
                        </select>
                    </div>
                    <!-- 老师让去掉这个，暂时给隐藏了 -->
                    <label class="col-md-1 hidden control-label front-label" for="status">状态</label>
                    <div class="col-md-3 hidden">
                        <select id="instance-status"
                                class="form-control front-no-radius front-no-box-shadow">
                            <option value="">--全部--</option>
                            <option value="ACTIVE">运行中</option>
                            <option value="STOPPED">已关机</option>
                        </select>
                    </div>
                    <div class="text-right col-md-8">
                        <button type="button" id="alireset" class="btn btn-default">重置</button>
                        <button type="button" id="alisearch" class="btn btn-primary"
                                onclick="alisearch('')">查找
                        </button>
                    </div>
                </div>
            </div>
            </div>
        </div>

        <!----------------------------------- 显示云主机的列表 ------------------------------->
        <div id="prosloading" class=" hidden">
            <div class="front-loading">
                <div class="front-loading-block"></div>
                <div class="front-loading-block"></div>
                <div class="front-loading-block"></div>
            </div>
            <div class="panel-body text-center">正在加载请稍候</div>
        </div>
        <div id="show_data_area">
            <!-- 插入异步请求获得的主机列表 -->
        </div>
        <!--分页标签-->
        <div style="text-align: center" id="back_divPage"></div>

    </div>

    <!--底部标签-->
    <s:include value="../template/_footer.jsp"/>
</div>
<s:include value="../template/_global.jsp"/>
<script src="js/vm/vmlistoperate.js"></script>   <!-- 主机列表获取appname和regionId -->
<script src="js/vm/vmlist.js"></script>   <!-- 主机启动、关闭、重启等操作 -->
<script src="js/vm/divpage.js"></script>    <!-- 主机列表分页、列表ajax刷新 -->
<script src="js/searchbox.js"></script>     <!-- 云海搜索框的切换 -->
<script src="js/vm/vmsearch.js"></script>     <!-- 云海和阿里云的搜索 -->
</body>
</html>
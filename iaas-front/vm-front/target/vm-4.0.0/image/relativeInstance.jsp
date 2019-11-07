<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>云主机管理</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <s:include value="/template/_head.jsp"/>
</head>
<body class="front-body">

<!--首部标签-->
<s:include value="/template/_banner.jsp?menu=vm"/>

<input class="hidden" id="img_appname" value="<s:property value='appname'/>">
<input class="hidden" id="img_Id" value="<s:property value='imageId'/>">
<input class="hidden" id="img_regionId" value="<s:property value='regionId'/>">


<div class="front-inner">
    <div class="container">
        <!--云硬盘管理提示标签-->
        <div class="col-md-12 hidden" style="padding:0">
            <div class="col-md-4 front-input-search" style="padding-left: 0">
                <div style="display: inline-block;padding-left: 0">
                    <label class="head_line"></label>
                    <label>云主机</label>
                </div>
            </div>
        </div>

        <div class="front-toolbar hidden other">
            <div class="front-toolbar-header clearfix">
                <button type="button" class="front-toolbar-toggle navbar-toggle"
                        data-toggle="collapse" data-target="#iass-mygroup">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div id="iass-mygroup" class="front-btn-group collapse" data-toggle="buttons">
                <label id="group-all" class="btn btn-default front-no-box-shadow active" onclick="hidesearch()"
                       data-group="all"> <input type="radio" name="options"
                                                autocomplete="off" checked>全部
                </label>
                <label class="btn btn-default front-no-box-shadow" onclick="hidesearch()"
                       data-group="create" style="box-shadow: none"> <input
                        type="radio" name="options" autocomplete="off" checked> <span
                        class="glyphicon glyphicon-asterisk"></span> 运行中的
                </label>
                <label class="btn btn-default front-no-box-shadow" onclick="hidesearch()"
                       data-group="manage" style="box-shadow: none"> <input
                        type="radio" name="options" autocomplete="off" checked> <span
                        class="glyphicon glyphicon-king"></span> 即将过期的
                </label>
                <label class="btn btn-default front-no-box-shadow" onclick="hidetable()"
                       data-group="search" style="box-shadow: none"> <input
                        type="radio" name="options" autocomplete="off"> <span
                        class="glyphicon glyphicon-search"></span> 搜索
                </label>
            </div>
            <%--<a--%>
            <%--href="vm/newvm.jsp"--%>
            <%--class="btn btn-primary">申请云主机</a>--%>
        </div>
        <div>
            <div id="freeshare-grouprel">
                <div class="panel panel-default hidden front-panel" id="back_search"
                     style="margin-bottom: 10px">
                    <div class="panel-heading">
                        <strong>云主机搜索</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label"
                                       for="groupkeyword">关键字</label>
                                <div class="col-md-11">
                                    <input id="groupkeyword" type="text"
                                           class="form-control front-no-box-shadow" placeholder="搜索关键字">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label"
                                       for="providerEn">提供商</label>
                                <div class="col-md-3">
                                    <select id="providerEn"
                                            class="form-control front-no-radius front-no-box-shadow">
                                        <option value="0">--全部--</option>
                                        <option value="readwrite">亚马逊</option>
                                        <option value="readonly">阿里云</option>
                                    </select>
                                </div>
                                <label class="col-md-1 control-label front-label"
                                       for="regionId">可用区</label>

                                <div class="col-md-3">
                                    <select id="regionId"
                                            class="form-control front-no-radius front-no-box-shadow">
                                        <option value="0">--全部--</option>
                                        <option value="private">华东区</option>
                                        <option value="normal">华北区</option>
                                        <option value="public">华中区</option>
                                    </select>
                                </div>
                                <label class="col-md-1 control-label front-label"
                                       for="vmtype">当前状态</label>

                                <div class="col-md-3">
                                    <select id="vmtype"
                                            class="form-control front-no-radius front-no-box-shadow">
                                        <option value="0">--全部--</option>
                                        <option value="private">可用</option>
                                        <option value="normal">即将到期</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label"
                                       for="ostype">操作系统</label>
                                <div class="col-md-3">
                                    <select id="ostype"
                                            class="form-control front-no-radius front-no-box-shadow">
                                        <option value="0">--全部--</option>
                                        <option value="readwrite">Windows</option>
                                        <option value="readonly">Linux</option>
                                        <option value="readonly">Mac</option>
                                    </select>
                                </div>
                                <label class="col-md-1 control-label front-label"
                                       for="paytype">付费方式</label>
                                <div class="col-md-3">
                                    <select id="paytype"
                                            class="form-control front-no-radius front-no-box-shadow">
                                        <option value="0">--全部--</option>
                                        <option value="private">按年</option>
                                        <option value="normal">按月</option>
                                    </select>
                                </div>
                                <div class="text-right col-md-4">
                                    <button type="button" id="resetsearch" class="btn btn-default">重置</button>
                                    <button type="button" id="groupsearch" class="btn btn-primary">查找</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- result -->
                <s:include value="relativeInstanceForPage.jsp"/>
                <!-- result -->
            </div>
        </div>

        <!--分页标签-->
        <div id="page" style="text-align: center" id="back_divPage">
        </div>

    </div>

    <!--底部标签-->
    <s:include value="../template/_footer.jsp"/>
</div>
<script src="js/searchbox.js"></script>
<script src="js/vm/vmlist.js"></script>
</body>
<s:include value="../template/_global.jsp"/>
<script>
    $("#page").html($.getDivPageHtml(<s:property value="currentPage"/>, <s:property value="totalPage"/>, "getPage"));

    function getPage(page) {
        $.post("image/getRelativeInstancePage",
                {
                    appname: $('#img_appname').val(),
                    regionId: $('#img_regionId').val(),
                    imageId: $('#img_Id').val(),
                    page: page
                },
                function (data) {
                    $("#show_data_area").html(data);
                    $("#page").html($.getDivPageHtml(page, <s:property value="totalPage" />, "getPage"));
                });
    }
</script>
</html>
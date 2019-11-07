<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>云镜像 - 云海IaaS</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <s:include value="../template/_head.jsp"/>
</head>
<body class="front-body">

<!--首部标签-->
<s:include value="../template/_banner.jsp?menu=images"/>

<div class="front-inner">
    <div class="container">
        <!---------------------------------- appname和regionId ------------------------------------->
        <div class="col-md-12 form-group">
            <!-- appname -->
            <div class="col-md-2" style="padding-left: 0;">
                <div class="dropdown">
                    <button id="appnameDropMenu" style="text-align: left;"
                            class="btn btn-default dropdown-toggle form-control front-no-box-shadow"
                            type="button" id="selectAppnameBtn" data-toggle="dropdown">
                        <span id="appnameicon" class='glyphicon selectspan'></span>
                        <label id="appnamemenu" style="width:80px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" ></label>
                        <span class="caret rightmiddle"></span>
                        <span id="appproviderEn" class="hidden"></span>
                    </button>
                    <ul id="selectAppname" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                    </ul>
                </div>
            </div>
            <!-- 加载当前提供商的地区信息 -->
            <div class="col-md-2" style="padding-left: 0" id="regions">
                <select class="form-control front-no-box-shadow" id="selectRegion" disabled="disabled"
                        onchange="getImages()">
                </select>
            </div>
            <div class="col-md-2" style="padding-left: 0;">
                <div class="dropdown">
                    <button id="imageDropMenu" style="text-align: left;padding-left:0px"
                            class="btn btn-default dropdown-toggle form-control front-no-box-shadow"
                            type="button" id="selectimageBtn" data-toggle="dropdown">
                        <span id="imageicon" class='glyphicon selectspan'></span>
                        <label id="imagemenu" style="margin-bottom:0px;" >全部镜像</label>
                        <span class="caret rightmiddle"></span>
                        <%--<span id="appproviderEn" class="hidden"></span>--%>
                    </button>
                    <ul id="selectimage" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li onclick="changeImage('PUBLIC')"role="presentation">
                           <a role="menuitem" hrep="javascript:void(0)">
                            <span class="glyphicon glyphicon-star"></span>&nbsp;公共镜像
                           </a>
                        </li>
                        <li onclick="changeImage('GROUP')" role="presentation">
                            <a role="menuitem" hrep="javascript:void(0)">
                            <span class="glyphicon glyphicon-asterisk"></span>&nbsp;共享镜像
                            </a>
                        </li>
                        <li onclick="changeImage('PRIVATE')" role="presentation">
                            <a role="menuitem" hrep="javascript:void(0)">
                            <span class="glyphicon glyphicon-king"></span>&nbsp;自定义镜像
                            </a>
                        </li>
                    </ul>

                </div>
            </div>
            <div class="text-right col-md-6" style="padding-right: 0px">
                <a id="search_yun" href="javascript:void(0)" onclick="exsearch()" class="btn btn-default"><span
                        class="glyphicon glyphicon-search"></span> 搜索</a>
            </div>
        </div>
        <%--清除固定，这样下拉菜单才能点击--%>
        <div class="clearfix"></div>
        <!-------------------------------------- 云镜像搜索 ---------------------------------->
        <div id="yun_img_search" class="hidden panel panel-default front-panel">
            <div class="panel-heading">
                <strong>云镜像搜索</strong>
            </div>
            <div class="panel-body">
                <div class="form-horizontal" id="yunhai_img_search">
                    <div class="form-group">
                        <div class="col-md-6">
                            <label class="col-md-2 control-label front-label">类型</label>
                            <div id="yunhai_search" class="col-md-10 front-toolbar other">
                                <div class="front-toolbar-header clearfix">
                                    <button type="button" class="front-toolbar-toggle navbar-toggle"
                                            data-toggle="collapse" data-target="#yunhai-imgtype">
                                        <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                                            class="icon-bar"></span>
                                    </button>
                                </div>
                                <div id="yunhai-imgtype" class="front-btn-group collapse"
                                     data-toggle="buttons">
                                    <label id="group-all" class="btn btn-default front-no-box-shadow active"
                                           data-group="PUBLIC"> <input type="radio" name="options"
                                                                       autocomplete="off" checked><span
                                            class="glyphicon glyphicon-star"></span>公共镜像
                                    </label>
                                    <label class="btn btn-default front-no-box-shadow"
                                           data-group="GROUP" style="box-shadow: none"> <input
                                            type="radio" name="options" autocomplete="off" checked> <span
                                            class="glyphicon glyphicon-asterisk"></span> 共享镜像
                                    </label>
                                    <label class="btn btn-default front-no-box-shadow"
                                           data-group="PRIVATE" style="box-shadow: none"> <input
                                            type="radio" name="options" autocomplete="off" checked> <span
                                            class="glyphicon glyphicon-king"></span> 自定义镜像
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label class="col-md-2 control-label front-label">系统</label>
                            <div class="col-md-10 front-toolbar other">
                                <select id="yunhai_ostype" class="form-control front-toolbar">
                                    <option value="ALL">全部</option>
                                    <option value="WINDOWS">windows</option>
                                    <option value="WINDOWS_SERVER">windows_server</option>
                                    <option value="CENTOS">centos</option>
                                    <option value="UBUNTU">ubuntu</option>
                                    <option value="RED_HAT">red_hat</option>
                                    <option value="REDORA">fedora</option>
                                    <option value="OTHER">其他</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-1 control-label front-label"
                               for="imagekeyword">关键字</label>
                        <div class="col-md-11">
                            <input id="imagekeyword" type="text"
                                   class="form-control front-no-box-shadow" placeholder="输入云镜像名称或软件名搜索">
                        </div>
                    </div>
                    <div class="form-group last-form-group">
                        <div class="text-right col-md-12">
                            <button type="button" id="resetsearch" class="btn btn-default" onclick="cancelSearch()">重置</button>
                            <button type="button" id="groupsearch" class="btn btn-primary"
                                    onclick="imagesearch('keyword')">查找
                            </button>
                        </div>
                    </div>
                </div>

                <!-- 阿里云 -->
                <div id="select-content"></div>
            </div>
        </div>

        <!----------------------------------- 显示云镜像的列表 ------------------------------->
        <div id="image-content"></div>
        <!--分页标签-->
        <div id="back_divPage" style="text-align: center">
        </div>
    </div>
    <!--底部标签-->
    <s:include value="../template/_footer.jsp"/>
</div>

</body>
<s:include value="../template/_global.jsp"/>
<script src="js/searchbox.js"></script>
<script src="js/image/imagesList.js"></script>
<script>
    var page = 1;
    var souType = "all";
    <%-- var result =<s:property value="imgResult"/>; --%>
</script>
</html>
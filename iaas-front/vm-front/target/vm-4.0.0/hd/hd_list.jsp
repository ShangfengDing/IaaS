<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.appcloud.vm.common.Constants" %>
<html>
<head>
    <title>云硬盘 - 云海IaaS</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <s:include value="../template/_head.jsp"/>
</head>
<body class="front-body">
<!--首部标签-->
<s:include value="../template/_banner.jsp?menu=hd"/>

<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
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
                <select class="form-control front-no-box-shadow" id="selectRegion" disabled="disabled" onchange="getHds()">
                </select>
            </div>
                <!-- 搜索新建按钮 -->
            <div class="text-right col-md-8" style="padding-right: 0px">
                <a id="search_yun" href="javascript:void(0)" onclick="exsearch()" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> 搜索</a>
                <a id="apply_yun"  href="javascript:void(0)" onclick="applydisk()" class="btn btn-default"><span style="color: red;" class="glyphicon glyphicon-plus-sign"></span> 新建</a>
            </div>
        </div>
        <div class="clearfix"></div>
        <div id="select-content" class="hidden panel panel-default front-panel">
        </div>
        <div id="hd-content"></div>
        <!--分页标签-->
        <div style="text-align:center" id="back_divPage"></div>
    </div>

    <s:include value="../template/_footer.jsp"/>
</div>
<!--底部标签-->

<!-- 创建快照的模态框 -->
<s:include value="newshotmodal.jsp"></s:include>
<s:include value="../template/_global.jsp"/>
<script>
    var page = 1;
    var flag = false;
    var flag1 = false;
</script>
<script src="js/hd/hdlist.js"></script>
<script src="js/createshot.js"></script>
</body>
</html>
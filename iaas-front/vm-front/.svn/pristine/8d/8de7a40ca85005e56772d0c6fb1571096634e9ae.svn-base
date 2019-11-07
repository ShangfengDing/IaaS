<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>云安全 - 云海IaaS</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <s:include value="/template/_head.jsp"/>
</head>
<body class="front-body">

<!--首部标签-->
<s:include value="/template/_banner.jsp?menu=fw"/>
<div class="front-inner">
    <div class="container">
        <!---------------------------------- appname和regionId ------------------------------------->
        <div class="col-md-12 form-group">
            <!-- appname -->
            <div class="col-md-2" style="padding-left: 0;">
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
            <!-- regionId -->
			<div class="col-md-2" style="padding-left: 0">
				<select id="selectRegionId" onchange="changeRegion()" class="form-control front-no-box-shadow">
					<s:iterator id="fwrg" value="fwRegionIds">
						<option value="<s:property value="#fwrg.regionId"/>"><s:property value="#fwrg.regionCn"/></option>
					</s:iterator>
				</select>
			</div>
            <div class="col-md-8 text-right" style="padding-right: 0">
                <a id="apply_yun"  href="javascript:void(0)" onclick="createSecurityGroup()" class="btn btn-default"><span style="color: red;" class="glyphicon glyphicon-plus-sign"></span> 新建</a>
            </div>
        </div>
		<div class=" clearfix"></div>

        <!---------------------------------- 防火墙列表 ------------------------------------->
        <div>
            <div id="freeshare-grouprel">
            	<div id="prosloading" class=" hidden">
					<div class="front-loading">
						<div class="front-loading-block"></div>
						<div class="front-loading-block"></div>
						<div class="front-loading-block"></div>
					</div>
					<div class="panel-body text-center">正在加载请稍候</div>
				</div>
                <div id="show_data_area">
                    <!-- 全部 -->
                </div>
            </div>
        </div>
        <!--分页标签-->
        <div style="text-align: center" id="back_divPage"></div>
    </div>

    <!--底部标签-->
    <s:include value="../template/_footer.jsp"/>
</div>
<s:include value="../template/_global.jsp"/>
<script>
	var totalPage = '<s:property value="totalPage"/>';//总的页数
	var result = '<s:property value="result"/>';
</script>
<script src="js/fw/fwlist.js"></script>
</body>
</html>
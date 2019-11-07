<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.*, appcloud.admin.common.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>节点管理 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=system"/>
<div class="front-inner front-inner-media">
    <div class="container">
        <div class="col-md-12 form-group">
        <%--<div class="front-resource-info clearfix" style="margin-top: 1%;margin-bottom: 13px">--%>
        <%--<div class="pull-left" style="margin-left:1%"><h4>&nbsp;&nbsp;<strong>节点管理</strong></h4></div>--%>
        <%--</div>--%>
        <div>
            <ol class="breadcrumb">
                <li>系统管理</li>
                <li class="active">节点</li>
            </ol>
        </div>
        <div class="panel panel-default front-panel">
            <div class="panel-body" style="margin-top:3px;padding-bottom:0px">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class=" col-lg-1 col-md-2 control-label ">主机ip</label>
                        <div class="col-md-5">
                            <input name="ip" id="ip" class="form-control front-no-radius front-no-box-shadow"
                                   type="text">
                        </div>
                        <label class="col-lg-1 col-md-2 control-label">主机类型</label>
                        <div class="col-md-5">
                            <%--<select class=" form-control front-no-radius front-no-box-shadow" style="width:162px;height:24px;" id="type" >--%>
                            <select class=" form-control front-no-radius front-no-box-shadow" id="type">

                                <option value="">--全部--</option>
                                <option value="COMPUTE_NODE">COMPUTE_NODE</option>
                                <option value="FUNCTION_NODE">FUNCTION_NODE</option>
                            </select>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-lg-1 col-md-2 control-label">主机状态</label>
                        <div class="col-md-5">
                            <%--<input id="client_num" class="form-control front-no-radius front-no-box-shadow"  type="text">--%>
                            <select class="form-control front-no-radius front-no-box-shadow" id="status">
                                <option value="">--全部--</option>
                                <option value="CRASH">CRASH</option>
                                <option value="HIGH_LOAD">HIGH_LOAD</option>
                                <option value="LOW_LOAD">LOW_LOAD</option>
                                <option value="NORMAL_LOAD">NORMAL_LOAD</option>
                            </select>
                        </div>
                        <label class="col-lg-1 col-md-2 control-label">集群</label>
                        <div class="col-md-5">
                            <select class="form-control front-no-radius front-no-box-shadow" id="aggregateId">
                                <option value="">--全部--</option>
                            </select>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-lg-1 col-md-2 control-label">运行服务</label>
                        <div class="col-md-5">
                            <%--<input id="client_num" class="form-control front-no-radius front-no-box-shadow"  type="text">--%>
                            <select class="form-control front-no-radius front-no-box-shadow" id="service">
                                <option value="">--全部--</option>
                                <option value="DHCP_CONTROLLER">DHCP_CONTROLLER</option>
                                <option value="IMAGE_SERVER">IMAGE_SERVER</option>
                                <option value="LOL_SERVER">LOL_SERVER</option>
                                <option value="NETWORK_PROVIDER">NETWORK_PROVIDER</option>
                                <option value="NODE_MONITOR">NODE_MONITOR</option>
                                <option value="RESOURCE_SCHEDULER">RESOURCE_SCHEDULER</option>
                                <option value="VM_CONTROLLER">VM_CONTROLLER</option>
                                <option value="VM_SCHEDULER">VM_SCHEDULER</option>
                                <option value="VOLUME_PROVIDER">VOLUME_PROVIDER</option>
                                <option value="VOLUME_SCHEDULER">VOLUME_SCHEDULER</option>
                                <option value="UNKNOWN">UNKNOWN</option>
                            </select>
                        </div>
                        <label class="col-lg-1 col-md-2 control-label">CPU核数</label>
                        <div class="col-md-5">
                            <select class="leftmargin_10 selectbox" style="width:13%;height:34px" id="cpuOperator">
                                <option value="0">&nbsp;&nbsp;&lt;</option>
                                <option value="1">&nbsp;&nbsp;&lt;=</option>
                                <option value="2">&nbsp;&nbsp;=</option>
                                <option value="3">&nbsp;&nbsp;&gt;=</option>
                                <option value="4">&nbsp;&nbsp;&gt;</option>
                            </select>
                            <input type="text" class="squareinputlt" style="width:86%;height:34px" name="cpuCount"
                                   id="cpuCount"/>
                        </div><!-- /btn-group -->
                    </div><!-- /input-group -->
                    <div class="form-group">
                        <label class="col-lg-1 col-md-2 control-label">内存大小</label>
                        <div class="col-md-5">

                            <select class="leftmargin_10 selectbox" style="width:13%;height:34px"
                                    id="memoryOperator">
                                <option value="0">&nbsp;&nbsp;&lt;</option>
                                <option value="1">&nbsp;&nbsp;&lt;=</option>
                                <option value="2">&nbsp;&nbsp;=</option>
                                <option value="3">&nbsp;&nbsp;&gt;=</option>
                                <option value="4">&nbsp;&nbsp;&gt;</option>
                            </select>
                            <input type="text" class="leftmargin_0 squareinputlt" style="width:86%;height:34px"
                                   name="memoryCount" id="memoryCount"/>


                        </div><!-- /input-group -->
                        <label class="col-lg-1 col-md-2 control-label">硬盘大小</label>
                        <div class="col-md-5">
                            <select class="leftmargin_10 selectbox" style="width:13%;height:34px" id="diskOperator">--%>
                                <option value="0">&nbsp;&nbsp;&lt;</option>
                                <option value="1">&nbsp;&nbsp;&lt;=</option>
                                <option value="2">&nbsp;&nbsp;=</option>
                                <option value="3">&nbsp;&nbsp;&gt;=</option>
                                <option value="4">&nbsp;&nbsp;&gt;</option>
                            </select>
                            <input type="text" class="squareinputlt" style="width:86%;height:34px" name="diskCount"
                                   id="diskCount"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-offset-1 col-lg-11 text-right" style="padding-top: 4%">
                            <a href="javascript:void(0)" type="btn " onclick="cancelSearch()" class="btn btn-default ">重置</a>
                            <input type="button" value="确定" class="btn btn-primary" onclick="submitAll(1)"/>
                        </div>
                    </div>
                </form>
                <div class="dottedline topmargin_20"></div>
                <div id="highcharts" class="hidden"></div>

            </div>
        </div>
        <div id="query"></div>
        </div>
    </div>
    <%--container--%>
    <s:include value="/template/_footer.jsp"></s:include>
</div><!--front-innner-->
<s:include value="/template/_common_js.jsp"></s:include>


<script src="js/system/searchhost.js"></script>
</body>
</html>
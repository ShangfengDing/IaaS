<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>我的虚拟机 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=cloud"/>
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div class="inner">
                <div>
                    <ol class="breadcrumb">
                        <li>云主机管理</li>
                        <li class="active"><s:property value="server.name"/> (<s:property value="server.status"/>)</li>
                    </ol>
                </div>
                    <div>
                        <div class="nav-brand">
                            <img id="avator" alt="icon" src="images/vm_logo.png" class="img-responsive"/>
                        </div>
                    </div>
                    <div>
                        <div class="nav-brand" style="padding-top:0">
                            <h4 class="midia-heading">
                                <s:property value="server.name"/>&nbsp;
                            </h4>
                            <div id="groupdesc" style="word-wrap:break-word;word-break:break-all;">
                               <%--主机名称：<s:property value="server.name"/>&nbsp;--%>
                                <%--<a data-toggle="front-modal" data-title="修改云主机的名称" data-href="vm/preeditvm?yunType=vm&target=instance_name&appname=<s:property value="appname"/>&id=<s:property value="details.instanceId"/>&name=<s:property value="details.instanceName"/>&type=name&regionId=<s:property value="details.regionIdEn"/>&userIds=${param.userId}&zoneId=<s:property value="zoneId"/>">--%>
                                    <%--<span class="glyphicon glyphicon-edit"></span>--%>
                                <%--</a> <br>--%>
                                主&nbsp;机&nbsp;ID&nbsp;：<s:property value="server.id"/><br>
                               主机状态：<s:property value='statusMap[server.status]'/><br>
                               主机描述：<s:property value="details.description" /> &nbsp;
                                <a data-toggle="front-modal" data-title="修改云主机的描述" data-href="vm/preeditvm?yunType=vm&target=instance_des&appname=<s:property value="appname"/>&id=<s:property value="details.instanceId"/>&name=<s:property value="details.description"/>&type=description&regionId=<s:property value="details.regionIdEn"/>&userIds=${param.userId}&zoneId=<s:property value="zoneId"/>">
                                <span class="glyphicon glyphicon-edit"></span>
                                </a><br>
                               付费方式：<s:property value="vmEndtime.payType"/>&nbsp;
                                (&nbsp;<s:date name="server.created" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;-&nbsp;
                                <s:date name="vmEndtime.endTime" format="yyyy-MM-dd HH:mm:ss"/>&nbsp;)
                            </div>

                        </div>
                    </div>
                    <div id="top1" style="height:150px"></div>

                <div class="front-toolbar other"
                     style="margin-top: 5px; margin-left: -4px; padding-bottom:0" id="prev-toolbar">
                    <div class="front-toolbar-header clearfix">
                        <button type="button" class="front-toolbar-toggle navbar-toggle"
                                data-toggle="collapse" data-target="#base-buttons">
                            <span class="icon-bar"></span> <span class="icon-bar"></span>
                            <span class="icon-bar"></span><span class="icon-bar"></span>
                        </button>
                    </div>
                    <div id="base-buttons" class="front-btn-group collapse" >

                <s:if test='server.status=="active"'>
                    <%--<div class="col-md-4 front-btn-group">--%>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled"><span
                            class="glyphicon glyphicon-forward"/>&nbsp;&nbsp;启动</a>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','stop','<s:property
                               value="server.id"/>')"><span class="glyphicon glyphicon-stop"/>&nbsp;&nbsp;停止</a>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','restart','<s:property
                               value="server.id"/>')"> <span class="glyphicon glyphicon-repeat"/> &nbsp;&nbsp;重启</a>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','suspend','<s:property
                               value="server.id"/>')">  <span class="glyphicon glyphicon-floppy-open"/> &nbsp;&nbsp;挂起</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-step-forward"/>&nbsp;&nbsp;恢复</a>
                    <%--</div>--%>
                </s:if>
                <s:elseif test='server.status=="suspended"'>
                    <%--<div class="col-md-4 front-btn-group">--%>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled"><span
                            class="glyphicon glyphicon-forward"/>&nbsp;&nbsp;启动</a>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','stop','<s:property
                               value="server.id"/>')"><span class="glyphicon glyphicon-stop"/>&nbsp;&nbsp;停止</a>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','restart','<s:property
                               value="server.id"/>')"> <span class="glyphicon glyphicon-repeat"/>&nbsp;&nbsp;重启</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-floppy-open"/>&nbsp;&nbsp;挂起</a>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','resume','<s:property
                               value="server.id"/>')"><span class="glyphicon glyphicon-step-forward"/>&nbsp;&nbsp;恢复</a>
                    <%--</div>--%>
                </s:elseif>
                <s:elseif test='server.status=="stopped"'>
                    <%--<div class="col-md-4 front-btn-group">--%>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','start','<s:property
                               value="server.id"/>')"><span
                            class="glyphicon glyphicon-forward"/>&nbsp;&nbsp;启动</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-stop"/>&nbsp;&nbsp;停止</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-repeat"/>&nbsp;&nbsp;重启</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-floppy-open"/>&nbsp;&nbsp;挂起</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-step-forward"/>&nbsp;&nbsp;恢复</a>
                    <%--</div>--%>
                </s:elseif>
                <s:elseif test='server.status=="building" || server.status=="rebuilding" || server.status=="error"'>
                    <%--<div class="col-md-4 front-btn-group">--%>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-forward"/>&nbsp;&nbsp;启动</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-stop"/>&nbsp;&nbsp;停止</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-repeat"/>&nbsp;&nbsp;重启</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-floppy-open"/>&nbsp;&nbsp;挂起</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                        <span class="glyphicon glyphicon-step-forward"/>&nbsp;&nbsp;恢复</a>
                    <%--</div>--%>
                </s:elseif>
                <s:if test='server.status=="building"'>
                    <%--<div class="col-md-3 front-btn-group">--%>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled"><span class="glyphicon glyphicon-remove"/> &nbsp;&nbsp;删除</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled"><span class="glyphicon glyphicon-off"/>&nbsp;&nbsp;强制关机</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled"><span class="glyphicon glyphicon-remove-circle"/> &nbsp;&nbsp;强制删除</a>
                    <%--</div>--%>
                </s:if>
                <s:else>
                    <%--<div class="front-btn-group" >--%>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','delete','<s:property
                               value="server.id"/>','<s:property value="vmEndtime.id"/>')"><span class="glyphicon glyphicon-remove"/>&nbsp;&nbsp; 删除</a>
                    <a class=" btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','forceStop','<s:property
                               value="server.id"/>')"><span class="glyphicon glyphicon-off"/>&nbsp;&nbsp;强制关机</a>
                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)"
                       onclick="vmoperate('<s:property value="server.tenantId"/>','forceDelete','<s:property
                               value="server.id"/>','<s:property value="vmEndtime.id"/>')"><span class="glyphicon glyphicon-remove-circle"/>&nbsp;&nbsp;强制删除</a>
                    <%--</div>--%>
                </s:else>

                <%--<div class="front-btn-group">--%>
                <s:if test='server.status=="active" || server.status=="suspended" || server.status=="building" || server.status=="rebuilding" || server.status=="error" || server.status=="other"'>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled"><span class="glyphicon glyphicon-trash"/>&nbsp;&nbsp;清空密码</a>
                    <a class="btn btn-default front-no-box-shadow active" disabled="disabled"><span class="glyphicon glyphicon-refresh"/>&nbsp;&nbsp;云主机离线迁移</a>
                </s:if><s:elseif test='server.status=="stopped"'>
                <a class=" btn btn-default front-no-box-shadow"
                   href="vm/modVmPasswd.jsp?userid=<s:property value="server.tenantId"/>&serverid=<s:property value="server.id"/>"
                   rel="facebox" title="修改密码" ><span class="glyphicon glyphicon-trash"/>&nbsp;&nbsp;清空密码</a>
                <a class=" btn btn-default front-no-box-shadow"
                   href="vm/premigrateserver?type=offline&userId=<s:property value="server.tenantId"/>&serverId=<s:property value="server.id"/>"
                   rel="facebox" title="选择迁移目标节点"><span class="glyphicon glyphicon-refresh"/>&nbsp;&nbsp;云主机离线迁移</a>
            </s:elseif>
                    </div>

                </div>
                <div class="row" style="margin-top: 20px">
                    <div class="col-md-3 col-sm-12">
                        <div class="row">
                            <%--<div class="col-md-12 col-sm-12">--%>
                                <%--<div class="panel panel-default front-panel">--%>
                                    <%--<div class="panel-heading">基本信息</div>--%>
                                    <%--<div class="panel-body front-last-no-margin">--%>
                                        <%--<div  class="left-card">--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">主机ID</h5>--%>
                                                    <%--&lt;%&ndash;改改改 改改改 改改改 改改改 改改改 改改改 改改改 改改改 改改改 改改改&ndash;%&gt;--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter"><s:property value="server.id"/></h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">供应商</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter"><s:property value="details.provider" /></h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">所在区</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter"><s:property value="details.regionId" /></h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">主机名称</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter"><s:property value="server.name"/>&nbsp;--%>
                                                    <%--<a data-toggle="front-modal" data-title="修改云主机的名称"--%>
                                                       <%--data-href="vm/preeditvm?yunType=vm&target=instance_name&appname=<s:property value="appname"/>&id=<s:property value="details.instanceId"/>&name=<s:property value="details.instanceName"/>&type=name&regionId=<s:property value="details.regionIdEn"/>&userIds=${param.userId}&zoneId=<s:property value="zoneId"/>">--%>

                                                        <%--<span class="glyphicon glyphicon-edit"></span>--%>
                                                    <%--</a>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">主机状态</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="label-pos">--%>
                                                    <%--<img src="images/<s:property value='server.status'/>.png" class="rightmargin_5"--%>
                                                         <%--align="absmiddle"/>--%>
                                                    <%--<span class="redletter" id="status_label"><s:property--%>
                                                            <%--value='statusMap[server.status]'/></span>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>


                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">主机描述</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter"><s:property value="details.description" />&nbsp;--%>
                                                    <%--<a data-toggle="front-modal" data-title="修改云主机的描述"--%>
                                                       <%--data-href="vm/preeditvm?yunType=vm&target=instance_des&appname=<s:property value="appname"/>&id=<s:property value="details.instanceId"/>&name=<s:property value="details.description"/>&type=description&regionId=<s:property value="details.regionIdEn"/>&userIds=${param.userId}&zoneId=<s:property value="zoneId"/>">--%>
                                                        <%--<span class="glyphicon glyphicon-edit"></span>--%>
                                                    <%--</a>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5  class="label-pos">计费方式</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<s:property value="vmEndtime.payType"/>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<!-- 所有名称和描述的修改都是公用一个action，即vm/preeditvm -->--%>
                                            <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-3">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="label-pos">镜像描述</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-9">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="content-gutter content-edit" id="hd_name">&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;<s:if test="server.image.id == null or server.image.id == \"\"">空白镜像&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;</s:if>&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;<s:else>&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;<s:property&ndash;%&gt;--%>
                                                                    <%--&lt;%&ndash;value="image.metadata['displayDescription']"/>&#10&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;<span&ndash;%&gt;--%>
                                                                <%--&lt;%&ndash;class="redletter strong">（请尽快修改密码）&ndash;%&gt;--%>
                                                            <%--&lt;%&ndash;</span>&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;</s:else>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;</h5>&ndash;%&gt;--%>


                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5  class="label-pos">创建时间</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter" >--%>
                                                        <%--<s:date name="server.created" format="yyyy-MM-dd HH:mm:ss"/>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>

                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5  class="label-pos">到期时间</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<s:date name="vmEndtime.endTime" format="yyyy-MM-dd HH:mm:ss"/>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>

                                            <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-3">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="label-pos">硬盘描述</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-9">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="content-gutter content-edit" id="hd_des">&nbsp;</h5>&ndash;%&gt;--%>

                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-3">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="label-pos">硬盘种类</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-9">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="content-gutter">NETWORK</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-3">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="label-pos">硬盘属性</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-9">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="content-gutter">qcow2</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

                                            <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-3">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="label-pos">付费方式</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-9">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="content-gutter content-edit">包日&nbsp;</h5>&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<a href="javascript:void(0)" onclick="modalshow()" >(续费)</a>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

                                            <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-3">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="label-pos">创建时间</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-9">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="content-gutter">2018-04-16 03:09:05</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-3">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="label-pos">到期时间</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-9">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="content-gutter">2018-04-21 15:09:05</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <%--<div class="col-md-12 col-sm-12">--%>
                                <%--<div class="panel panel-default front-panel">--%>
                                    <%--<div class="panel-heading">配置信息</div>--%>
                                    <%--<div class="panel-body front-last-no-margin">--%>
                                        <%--<div  class="left-card">--%>

                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">主机镜像</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9" >--%>
                                                    <%--<h5 class="content-gutter content-edit" style="word-break: break-all; word-wrap: break-word">--%>
                                                        <%--<s:if test='details.imageId==""'>--%>
                                                            <%--null--%>
                                                        <%--</s:if>--%>
                                                        <%--<s:property default="null" value="details.imageId" />--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--&lt;%&ndash;<div class="row">&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-3">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="label-pos">计算资源</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<div class="col-md-9">&ndash;%&gt;--%>
                                                    <%--&lt;%&ndash;<h5 class="content-gutter content-edit"><s:property value="flavor.vcpus"/>个CPU，&ndash;%&gt;--%>
                                                        <%--&lt;%&ndash;<s:property value="flavor.ram"/>G内存</h5>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                            <%--<div class="row">--%>

                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">操作系统</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<s:property value="details.osType" />--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">主机规格</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<s:property value="details.instanceType" />--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">CPU</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter content-edit">--%>
                                                        <%--<span id="cpuCount"><s:property value="details.vcpus" /></span>核&nbsp;--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">内存</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter content-edit">--%>
														<%--<span id="moySize"><s:property--%>
                                                                <%--value="details.memory" /></span>G&nbsp;--%>
                                                    <%--<s:if test="details.providerEn != #request.aliyun">--%>
                                                        <%--<a href="javascript:void(0)"--%>
                                                           <%--onclick='sourcemdlOpen("cpumoy")' style="display:inline"><span class="glyphicon glyphicon-edit"/></a>--%>
                                                    <%--</s:if>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">存储资源</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<s:property value="flavor.disk"/>G硬盘--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">网络资源</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--公网带宽<s:property value="server.metadata['maxBandwidth']"/>M &nbsp;--%>
                                                        <%--内网带宽<s:property--%>
                                                            <%--value="server.metadata['priBandwidth']"/>M &nbsp;--%>
                                                        <%--<s:if test='server.status=="active" || server.status=="stopped" || server.status=="suspended"'>--%>
                                                            <%--<a class="darkblueletter leftmargin_20"--%>
                                                               <%--href="vm/prenewflavor?kind=netWork&vmUserId=<s:property value="userId"/>&serverId=<s:property value="server.id"/>--%>
                                   <%--&tenantId=<s:property value="server.tenantId"/>&flavorId=<s:property value="server.flavor.id"/>--%>
                                   <%--&mbdw=<s:property value="server.metadata['maxBandwidth']"/>&clusterId=<s:property value="clusterId"/>--%>
                                   <%--&pbdw=<s:property value="server.metadata['priBandwidth']"/>" rel="facebox" title="修改配置"--%>
                                                               <%--size="s"><span class="glyphicon glyphicon-edit"/></a>--%>
                                                            <%--<s:if test="server.metadata['oldPriBandNum'] != -1 || server.metadata['oldMaxBandNum'] != -1">--%>
                                                                <%--<a class="darkblueletter leftmargin_20" href="javascript:void(0);"--%>
                                                                   <%--onclick="recoverNetWork('<s:property value='server.id'/>','<s:property value='clusterId'/>','<s:property--%>
                                                                           <%--value='userId'/>')" title="还原配置">还原配置</a>--%>
                                                            <%--</s:if>--%>
                                                        <%--</s:if>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">公网IP</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<s:iterator id="publicIp" value="publicIps" status="st">--%>
                                                            <%--<s:property value="#publicIp.addr"/>--%>
                                                            <%--<s:if test="#st.last!=true">,</s:if>--%>
                                                        <%--</s:iterator></h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>

                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">内网IP</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<s:iterator id="privateIp" value="privateIps" status="st">--%>
                                                            <%--<s:property value="#privateIp.addr"/>--%>
                                                            <%--<s:if test="#st.last!=true"></s:if>--%>
                                                        <%--</s:iterator>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>

                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5  class="label-pos">防火墙</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<s:property value="server.securityGroup.name"/>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>




                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <%--<div class="col-md-12 col-sm-12" id="system">--%>
                                <%--<div class="panel panel-default front-panel">--%>
                                    <%--<div class="panel-heading">系统盘</div>--%>
                                    <%--<div class="panel-body front-last-no-margin">--%>
                                        <%--<div class="left-card">--%>
                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">硬盘ID</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<a href='hd/hddetail?&userIds=${param.userId}&appName=<s:property value="appname"/>&diskId=<s:property value="details.systemDiskId"/>&provider=<s:property value="details.providerEn"/>&regionId=<s:property value="details.regionIdEn"/>&userEmail=<s:property value="details.userEmail"/>&zoneId=<s:property value="zoneId"/>'>--%>
                                                            <%--<s:property value="details.systemDiskId"/>--%>
                                                        <%--</a>--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<s:if test="details.systemDiskName !='' ">--%>
                                                <%--<div class="row">--%>
                                                    <%--<div class="col-md-3 text-right">--%>
                                                        <%--<h5 class="label-pos">硬盘名称</h5>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="col-md-9">--%>
                                                        <%--<h5 class="content-gutter">--%>
                                                            <%--<s:property value="details.systemDiskName"/>--%>
                                                        <%--</h5>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            <%--</s:if>--%>

                                            <%--<div class="row">--%>
                                                <%--<div class="col-md-3 text-right">--%>
                                                    <%--<h5 class="label-pos">硬盘大小</h5>--%>
                                                <%--</div>--%>
                                                <%--<div class="col-md-9">--%>
                                                    <%--<h5 class="content-gutter">--%>
                                                        <%--<span id="sysDiskSize"><s:property value="details.systemDiskSize"/></span>G--%>
                                                    <%--</h5>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<!-- data数据盘，随主机一起卸载-->--%>
                            <%--<s:iterator id="disk_inventory" value="details.diskList">--%>
                                <%--<s:if--%>
                                        <%--test="#disk_inventory.diskType=='DATA' || #disk_inventory.diskType=='data'">--%>
                                    <%--<div class="col-md-12 col-sm-12">--%>
                                        <%--<div class="panel panel-default front-panel">--%>
                                            <%--<div class="panel-heading">数据盘(随系统盘卸载)</div>--%>
                                            <%--<div class="panel-body front-last-no-margin">--%>
                                                <%--<div class="left-card">--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-md-3 text-right">--%>
                                                            <%--<h5 class="label-pos">硬盘ID</h5>--%>
                                                        <%--</div>--%>
                                                        <%--<div class="col-md-9">--%>
                                                            <%--<h5 class="content-gutter">--%>
                                                                <%--<a href='hd/hddetail?&userIds=${param.userId}&appName=<s:property value="appname"/>&diskId=<s:property value="#disk_inventory.diskId"/>&provider=<s:property value="details.providerEn"/>&regionId=<s:property value="details.regionIdEn"/>&userEmail=<s:property value="details.userEmail"/>&zoneId=<s:property value="zoneId"/>'><s:property--%>
                                                                        <%--value="#disk_inventory.diskId" /></a>--%>
                                                            <%--</h5>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                    <%--<s:if test="#disk_inventory.diskName!=''">--%>
                                                        <%--<div class="row">--%>
                                                            <%--<div class="col-md-3 text-right">--%>
                                                                <%--<h5 class="label-pos">硬盘名称</h5>--%>
                                                            <%--</div>--%>
                                                            <%--<div class="col-md-9">--%>
                                                                <%--<h5 class="content-gutter">--%>
                                                                    <%--<s:property value="#disk_inventory.diskName" />--%>
                                                                <%--</h5>--%>
                                                            <%--</div>--%>
                                                        <%--</div>--%>
                                                    <%--</s:if>--%>

                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-md-3 text-right">--%>
                                                            <%--<h5 class="label-pos">硬盘大小</h5>--%>
                                                        <%--</div>--%>
                                                        <%--<div class="col-md-9">--%>
                                                            <%--<h5 class="content-gutter">--%>
																<%--<span id="datadisksize"><s:property--%>
                                                                        <%--value="#disk_inventory.diskSize" /></span>G--%>
                                                            <%--</h5>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</s:if>--%>
                            <%--</s:iterator>--%>
                            <%--<!-- network数据盘 -->--%>
                            <%--<s:iterator id="disk_inventory" value="details.diskList">--%>
                                <%--<s:if test="#disk_inventory.diskType=='NETWORK'">--%>
                                    <%--<div class="col-md-12 col-sm-12">--%>
                                        <%--<div class="panel panel-default front-panel">--%>
                                            <%--<div class="panel-heading">数据盘</div>--%>
                                            <%--<div class="panel-body front-last-no-margin">--%>
                                                <%--<div class="left-card">--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-md-3 text-right">--%>
                                                            <%--<h5 class="label-pos ">硬盘ID</h5>--%>
                                                        <%--</div>--%>
                                                        <%--<div class="col-md-9">--%>
                                                            <%--<h5 class="content-gutter">--%>
                                                                <%--<a href='hd/hddetail?&userIds=${param.userId}&appName=<s:property value="appname"/>&diskId=<s:property value="#disk_inventory.diskId"/>&provider=<s:property value="details.providerEn"/>&regionId=<s:property value="details.regionIdEn"/>&userEmail=<s:property value="details.userEmail"/>&zoneId=<s:property value="zoneId"/>'>--%>
                                                                    <%--<s:property value="#disk_inventory.diskId" />--%>
                                                                <%--</a>--%>
                                                            <%--</h5>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-md-3 text-right" >--%>
                                                            <%--<h5 class="label-pos">硬盘名称</h5>--%>
                                                        <%--</div>--%>
                                                        <%--<div class="col-md-9">--%>
                                                            <%--<h5 class="content-gutter">--%>
                                                                <%--<s:property value="#disk_inventory.diskName" />--%>
                                                            <%--</h5>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                    <%--<div class="row">--%>
                                                        <%--<div class="col-md-3">--%>
                                                            <%--<h5 class="label-pos">硬盘大小</h5>--%>
                                                        <%--</div>--%>
                                                        <%--<div class="col-md-9">--%>
                                                            <%--<h5 class="content-gutter">--%>
                                                                <%--<s:property value="#disk_inventory.diskSize" />--%>
                                                                <%--G--%>
                                                            <%--</h5>--%>
                                                        <%--</div>--%>
                                                    <%--</div>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</s:if>--%>
                            <%--</s:iterator>--%>
                        </div>
                        <div class="row" style="margin-top:40px">
                            <img class="img-responsive" style="align:center;" src="images/vmnet.png" alt="vmnet"/>
                        </div>
                    </div>
                    <div class="col-md-9 col-sm-12" style="padding-top:15px">
                        <div class="row">
                            <div class="col-md-6 col-sm-6"  style="padding-left:0">
                                <div class="panel panel-default front-panel">
                                    <div class="panel-heading">计算资源</div>
                                    <div class="panel-body front-last-no-margin">
                                        <div class="row">
                                            <div class="col-md-3 text-right">
                                                <h5 class="label-pos">主机规格:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                CPU&nbsp;<span id="cpuCount"><s:property value="details.vcpus" /></span>核&nbsp;/&nbsp;内存&nbsp;<span id="moySize"><s:property value="details.memory" /></span>G&nbsp;
                                                <%--<s:if test="details.providerEn != #request.aliyun">--%>
                                                    <%--<a href="javascript:void(0)"--%>
                                                       <%--onclick='sourcemdlOpen("cpumoy")' style="display:inline"><span class="glyphicon glyphicon-edit"/></a>--%>
                                                <%--</s:if>--%>
                                                </h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3 text-right">
                                                <h5 class="label-pos">操作系统:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                <s:property value="details.osType" />
                                                </h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3 text-right">
                                                <h5 class="label-pos">镜像:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                <s:if test='details.imageId==""'>
                                                    null
                                                </s:if>
                                                <s:property default="null" value="details.imageId" />
                                                </h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6"  style="padding-left:0">
                                <div class="panel panel-default front-panel">
                                    <div class="panel-heading">网络资源</div>
                                    <div class="panel-body front-last-no-margin">
                                        <div class="row">
                                            <div class="col-md-3 text-right">
                                                <h5 class="label-pos">公网:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                <s:iterator id="publicIp" value="publicIps" status="st">
                                                    <s:property value="#publicIp.addr"/>
                                                    <s:if test="#st.last!=true">,</s:if>
                                                </s:iterator>
                                                &nbsp;(&nbsp;<s:property value="server.metadata['maxBandwidth']"/>M带宽&nbsp;)&nbsp;

                                                <%--<s:if test='server.status=="active" || server.status=="stopped" || server.status=="suspended"'>--%>
                                                    <%--<a class="darkblueletter leftmargin_20"--%>
                                                       <%--href="vm/prenewflavor?kind=netWork&vmUserId=${param.userId}&serverId=<s:property value="server.id"/>--%>
                                   <%--&tenantId=<s:property value="server.tenantId"/>&flavorId=<s:property value="server.flavor.id"/>--%>
                                   <%--&mbdw=<s:property value="server.metadata['maxBandwidth']"/>&clusterId=<s:property value="clusterId"/>--%>
                                   <%--&pbdw=<s:property value="server.metadata['priBandwidth']"/>" rel="facebox" title="修改配置"--%>
                                                       <%--size="s"><span class="glyphicon glyphicon-edit"/></a>--%>
                                                    <%--<s:if test="server.metadata['oldPriBandNum'] != -1 || server.metadata['oldMaxBandNum'] != -1">--%>
                                                        <%--<a class="darkblueletter leftmargin_20" href="javascript:void(0);"--%>
                                                           <%--onclick="recoverNetWork('<s:property value='server.id'/>','<s:property value='clusterId'/>','${param.userId}')" title="还原配置">还原配置</a>--%>
                                                    <%--</s:if>--%>
                                                <%--</s:if>--%>

                                                </h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3 text-right">
                                                <h5 class="label-pos">内网:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                <s:iterator id="privateIp" value="privateIps" status="st">
                                                    <s:property value="#privateIp.addr"/>
                                                    <s:if test="#st.last!=true"></s:if>
                                                </s:iterator>
                                                &nbsp;(&nbsp;<s:property value="server.metadata['priBandwidth']"/>M带宽&nbsp;)&nbsp;
                                                <%--<s:if test='server.status=="active" || server.status=="stopped" || server.status=="suspended"'>--%>
                                                    <%--<a class="darkblueletter leftmargin_20"--%>
                                                       <%--href="vm/prenewflavor?kind=netWork&vmUserId=<s:property value="userId"/>&serverId=<s:property value="server.id"/>--%>
                                   <%--&tenantId=<s:property value="server.tenantId"/>&flavorId=<s:property value="server.flavor.id"/>--%>
                                   <%--&mbdw=<s:property value="server.metadata['maxBandwidth']"/>&clusterId=<s:property value="clusterId"/>--%>
                                   <%--&pbdw=<s:property value="server.metadata['priBandwidth']"/>" rel="facebox" title="修改配置"--%>
                                                       <%--size="s"><span class="glyphicon glyphicon-edit"/></a>--%>
                                                    <%--<s:if test="server.metadata['oldPriBandNum'] != -1 || server.metadata['oldMaxBandNum'] != -1">--%>
                                                        <%--<a class="darkblueletter leftmargin_20" href="javascript:void(0);"--%>
                                                           <%--onclick="recoverNetWork('<s:property value='server.id'/>','<s:property value='clusterId'/>','<s:property--%>
                                                                   <%--value='userId'/>')" title="还原配置">还原配置</a>--%>
                                                    <%--</s:if>--%>
                                                <%--</s:if>--%>
                                                </h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3 text-right">
                                                <h5 class="label-pos">防火墙:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                <s:property value="server.securityGroup.name"/>
                                                </h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6 col-sm-6" style="padding-left:0" >
                                <div class="panel panel-default front-panel">
                                    <div class="panel-heading">存储资源 ( 系统盘 ) </div>
                                    <div class="panel-body front-last-no-margin">
                                        <div class="row">
                                            <div class="col-md-3 text-right">
                                                <h5 class="label-pos">硬盘名称:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                <s:property value="details.systemDiskName"/>
                                                </h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3 text-right">
                                                <h5 class="label-pos">硬盘ID:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                <a href='hd/hddetail?&userIds=${param.userId}&appName=<s:property value="appname"/>&diskId=<s:property value="details.systemDiskId"/>&provider=<s:property value="details.providerEn"/>&regionId=<s:property value="details.regionIdEn"/>&userEmail=<s:property value="details.userEmail"/>&zoneId=<s:property value="zoneId"/>'>
                                                    <s:property value="details.systemDiskId"/>
                                                </a>
                                                </h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3 text-right">
                                                <h5 class="label-pos">硬盘大小:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                <span id="sysDiskSize"><s:property value="details.systemDiskSize"/></span>G
                                                </h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3 text-right">

                                                <h5 class="label-pos">快照:</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                    <s:property value="snapShotCountMap[details.systemDiskId]"/>个
                                                </h5>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <s:iterator id="disk_inventory" value="details.diskList">
                                <s:if test="#disk_inventory.diskType=='NETWORK'">
                                    <div class="col-md-6 col-sm-6" style="padding-left:0">
                                        <div class="panel panel-default front-panel">
                                            <div class="panel-heading">存储资源 ( 数据盘 )</div>
                                            <div class="panel-body front-last-no-margin">
                                                <div class="row">
                                                    <div class="col-md-3 text-right">
                                                        <h5 class="label-pos">硬盘名称:</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter">
                                                        <s:property value="#disk_inventory.diskName" />
                                                        </h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3 text-right">
                                                        <h5 class="label-pos">硬盘ID:</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter">
                                                        <a href='hd/hddetail?&userIds=${param.userId}&appName=<s:property value="appname"/>&diskId=<s:property value="#disk_inventory.diskId"/>&provider=<s:property value="details.providerEn"/>&regionId=<s:property value="details.regionIdEn"/>&userEmail=<s:property value="details.userEmail"/>&zoneId=<s:property value="zoneId"/>'>
                                                            <s:property value="#disk_inventory.diskId" />
                                                        </a>
                                                        </h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3 text-right">
                                                        <h5 class="label-pos">硬盘大小:</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter">
                                                        <s:property value="#disk_inventory.diskSize" />G
                                                        </h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3 text-right">
                                                        <h5 class="label-pos">快照:</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter">
                                                            <s:property value="snapShotCountMap[#disk_inventory.diskId]"/>个
                                                        </h5>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </s:if>
                            </s:iterator>
                        </div>
                    </div>


                    <div class="col-md-12 col-sm-12" style="margin-top:30px">
                        <div class="row">
                            <%--<div class="col-md-12 col-sm-12 hide">--%>
                                <%--<div class="panel panel-default front-panel">--%>
                                    <%--<div class="panel-body front-no-padding">--%>
                                        <%--<table class="table table-striped front-table " id="black-table"--%>
                                               <%--style="margin-bottom: 0px; font-size: 14px">--%>
                                            <%--<tr style="background-color: rgba(86, 61, 124, .15); color: black">--%>
                                                <%--<td class="col-sm-1">云快照名称</td>--%>

                                                <%--<td class="col-sm-2">创建时间</td>--%>
                                                <%--<!-- <td class="col-sm-1">进度</td> -->--%>
                                                <%--<td class="col-sm-1">状态</td>--%>
                                                <%--<td class="col-sm-3" style="text-align: right;">操作</td>--%>
                                            <%--</tr>--%>

                                        <%--</table>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <div class="col-md-12 col-sm-12" style="padding-left:0">
                                <div class="panel panel-default front-panel" >
                                    <div class="panel-heading">监控-CPU(单位：%)</div>
                                    <div class="panel-body front-last-no-margin">
                                        <div id="cpu_highchart" style="height: 250px;"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-12 col-sm-12" style="padding-left:0">
                                <div class="panel panel-default front-panel">
                                    <div class="panel-heading">监控-内存(单位：%)</div>
                                    <div class="panel-body front-last-no-margin">
                                        <div id="mem_highchart" style="height: 250px;"></div>
                                    </div>
                                </div>
                            </div>

                            <%--<div class="col-md-12 col-sm-12" style="padding-left:0">--%>
                                <%--<div class="panel panel-default front-panel">--%>
                                    <%--<div class="panel-heading">监控-网络(单位：%)</div>--%>
                                    <%--<div class="panel-body front-last-no-margin">--%>
                                        <%--<div id="net_highchart" style="height: 250px;"></div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>

                        </div>

                    </div>
                </div>

            </div>
        </div>
    </div>

    <s:include value="/template/_footer.jsp"></s:include>
    <%-- <s:include value="/template/_common_js.jsp"></s:include>--%>
</div>
<script>
    $(function () {//根据稳态和瞬态更改工具按钮栏
        var sid = $("#serverId1").val();
        var sname = $("#serverName1").val();
        var status = $("#serverStatus1").val();
        var vmEndtimeId = $("#vmEndtimeId1").val();
        if (status == "other") {
            disableEverything(status, sid, sname, vmEndtimeId);
        }
        setTimeout(function () {
            checkStatus(status, sid, sname, vmEndtimeId)
        }, <%=Constants.DEFAULT_CHECKTIME%>);
    });
    var vdAppname = "<s:property value="appname"/>";
    var instanceTypeList = [];
    var hardDiskList = [];
    var bandWidthList = [];
    function sourcemdlOpen(sourceType) {
        modifyType = sourceType;
        $.ajax({
            type: "GET",
            url: "vm/getNewInstanceInfo",
            data:{
                appname:vdAppname
            },
            dataType: "json",
            success: function (data) {
                instanceTypeList = data.instanceTypeList;
                bandWidthList = data.bandWidthList;
                hardDiskList = data.hardDiskList;
                //对结果进行排序
                instanceTypeList.sort(function (a, b) {
                    return a.cpuCoreCount - b.cpuCoreCount;
                });
                bandWidthList.sort(function (a, b) {
                    return a.bandWidth - b.bandWidth;
                });

                //计算计算机初始规格
                var cpuCountTemp = $('#cpuCount').html();
                var moySizeTemp = $('#moySize').html();
                modifyBandSize = Number($('#bandsize').html())
                for (var i = 0; i < instanceTypeList.length; i++) {
                    if (cpuCountTemp == instanceTypeList[i].cpuCoreCount && moySizeTemp == instanceTypeList[i].memorySize) {
                        instanceTypePrice = instanceTypeList[i];
                        break;
                    }
                }

                switch (sourceType) {
                    case "cpumoy":
                        $('#vmBand').addClass('hidden');
                        $('#vmType').removeClass('hidden');
                        newinstanceType();
                        break;
                    case "vmBand":
                        $('#vmType').addClass('hidden');
                        $('#vmBand').removeClass('hidden');
                        newBandWidth(0);
                        break;
                }
                $('#sourceModal').css("zIndex", 1200);
                $('#sourceModal').modal('show');
            }
        });
    }

    function recoverNetWork(serverId, clusterId, vmUserId) {
        var confirmMsg = "确认恢复网络配置？";
        param = {
            serverId: serverId,
            clusterId: clusterId,
            vmUserId: vmUserId
        };
        if (confirm(confirmMsg)) {
            showLoading();
            $.post("vm/recoverNetwork", param, function (data) {
                hideLoading();
                if (data.result == true) {
                    fillTipBox("success", "恢复网络配置成功！");
                    setTimeout(function () {
                        window.location.reload();
                    }, 500);
                }
                else {
                    fillTipBox("error", "恢复网络配置操作失败！");
                }
            });
            location.reload();
        }
    }
    function showHelpcontent(uuid) {
        $.ajax({
            type: "post",
            url: "vm/helpcontent",
            data: {uuid: uuid},
            dataType: "json",
            success: function (helpcontent) {
                $('body').helpcontent_show({title: helpcontent.title, content: helpcontent.content});
            }
        });
    }

    function checkStatus(status, sid, sname, vmEndtimeId) {
        $.ajax({
            type: "post",
            url: "vm/vmstatus",
            data: {serverId: sid},
            success: function (data) {
                var newStatus = data.status;
                if (newStatus == status) {
                    setTimeout(function () {
                        checkStatus(newStatus, sid, sname, vmEndtimeId)
                    }, <%=Constants.DEFAULT_CHECKTIME%>);
                } else if (newStatus == "other" && status != "other") {
                    disableEverything(status, sid, sname, vmEndtimeId);
                    var newHtml = "<img src=\"images/other.png\" class=\"rightmargin_5\" align=\"absmiddle\"/>" +
                        "<span class=\"redletter\" id=\"serverStatus\">任务进行中</span>" +
                        "<a class=\"darkblueletter leftpadding_20\" href=\"javascript:void(0)\" onclick=\"location.reload();\">刷新</a>";
                    $("#td_status").html(newHtml);
                    setTimeout(function () {
                        checkStatus(newStatus, sid, sname, vmEndtimeId)
                    }, <%=Constants.DEFAULT_CHECKTIME%>);
                } else if (newStatus != "other" && status == "other") {
                    location.reload();
                } else {
                    location.reload();
                }
            }
        });
    }

    function disableEverything(status, sid, sname, vmEndtimeId) {
        $("#btns a").remove();
        $("a.darkblueletter").remove();
        var html = "<a class=\"graybutton leftbutton\"><img src=\"images/qidong.png\" align=\"absmiddle\"/>&nbsp;&nbsp;启动</a>" +
            "<a class=\"graybutton middlebutton\"><img src=\"images/tingzhi.png\" align=\"absmiddle\"/>&nbsp;&nbsp;停止</a>" +
            "<a class=\"graybutton middlebutton\"><img src=\"images/chongqi.png\" align=\"absmiddle\"/>&nbsp;&nbsp;重启</a>" +
            "<a class=\"graybutton middlebutton\"><img src=\"images/guaqi.png\" align=\"absmiddle\"/>&nbsp;&nbsp;挂起</a>" +
            "<a class=\"graybutton rightbutton\"><img src=\"images/huifu.png\" align=\"absmiddle\"/>&nbsp;&nbsp;恢复</a>" +
            "<a class=\"graybutton leftbutton\" href=\"javascript:void(0)\"><img src=\"images/yunxingzhuangtai.png\" align=\"absmiddle\"/>&nbsp;&nbsp;监控</a>" +
            "<a class=\"graybutton middlebutton\"><img src=\"images/yuancheng.png\" align=\"absmiddle\"/>&nbsp;&nbsp;远程</a>" +
            "<a class=\"graybutton middlebutton\" href=\"javascript:void(0)\"><img src=\"images/yuancheng.png\" align=\"absmiddle\"/>&nbsp;&nbsp;远程</a>" +
            "<a class=\"button rightbutton\" href=\"javascript:void(0)\" onclick=\"showMore(this);\">更多</a>" +
            "<div id=\"showmore\" class=\"hidden topmargin_10\">" +
            "<a class=\"graybutton leftbutton\" href=\"javascript:void(0)\">创建备份</a>" +
            "<a class=\"graybutton middlebutton\" href=\"javascript:void(0)\">创建快照</a>" +
            "<a class=\"graybutton leftbutton\">系统重置</a>" +
            "<a class=\"graybutton middlebutton\">iso安装</a>" +
            "<a class=\"graybutton rightbutton\">iso弹出</a>";

        if (status != "building") {
            html += "<a class=\"button leftbutton\" href=\"javascript:void(0)\" onclick=\"vmoperate('强制关机','" + sid + "','" + sname + "')\")>强制关机</a>" +
                "<a class=\"button rightbutton\" href=\"javascript:void(0)\" onclick=\"vmoperate('删除','" + sid + "','" + sname + "','" + vmEndtimeId + "')\">删除</a>";
        } else {
            html += "<a class=\"graybutton leftbutton\">强制关机</a><a class=\"graybutton rightbutton\">删除</a>";
        }

        if ($("#publishImage1").val() == "true") {
            html += "<a class=\"graybutton\" href=\"javascript:void(0)\">发布为模板</a>";
        }

        html += "</div>";
        $("#btns").html(html);
    }


    function hdoperate(operation, hdUuid, serverId, hdName, serverName) {
        var confirmMsg;
        if (operation == "detach") {
            confirmMsg = "确认进行卸载吗？(注意：该操作将关闭云主机！！)";
        } else {
            return;
        }
        if (confirm(confirmMsg)) {
            showLoading();
            $.post("hd/hdoperate", {
                operation: operation, hdUuid: hdUuid, serverId: serverId,
                hdName: hdName, serverName: serverName
            }, function (data) {//this is a bug, hdoperate function need user uuid!
                hideLoading();
                if (data.result == true) {
                    fillTipBox("success", "操作成功！");
                    setTimeout(function () {
                        window.location.reload();
                    }, 500);
                }
                else {
                    fillTipBox("error", "操作失败！");
                }
            });
        }
    }
    function vmoperate(userId, operation, uuid, endTimeId) {
        if (confirm("该操作会对云主机产生重大影响，您确认进行吗？")) {

            $.post("vm/vmoperate", {
                userId: userId,
                operation: operation,
                uuid: uuid,
                endTimeId: endTimeId
            }, function () {
                if (operation == "delete" || operation == "forceDelete") {
                    gotonext("vm/presearchvm");
                } else {
                    alert("success,操作成功！请刷新页面");
                    fillTipBox("success", "操作成功返回！");
                }
            });
            $.fillTipBox({type:'info', icon:'glyphicon-info-sign', content:'任务进行中，请勿重复点击'});
        }
    }

    function backupoperate(operation, backupId, backupName, serverName, serverId, volumeId) {
        var rollbackMsg = "确认进行回滚操作吗？\n注意1：该操作将关闭云主机！\n注意2：该备份之后做的快照将不复存在！";
        var deleteMsg = "确认删除该备份吗？";
        var confirmMsg = (operation == "rollback") ? rollbackMsg : deleteMsg;
        if (confirm(confirmMsg)) {
            showLoading();
            $.post("backup/backupoperate",
                {
                    operation: operation,
                    backupId: backupId,
                    serverId: serverId,
                    volumeId: volumeId,
                    serverName: serverName,
                    backupName: backupName
                },
                function (data) {
                    if (data.result == "success") {
                        fillTipBox("success", "操作成功！");
                        setTimeout(function () {
                            hideLoading();
                            window.location.reload();
                        }, 500);
                    }
                    else {
                        hideLoading();
                        fillTipBox("error", "操作失败！请稍后重试！");
                    }
                });
            $.fillTipBox({type:'info', icon:'glyphicon-info-sign', content:'任务进行中，请勿重复点击'});
        }
    }
    function shotoperate(operation, sid, shotName, serverName) {
        var confirmMsg = (operation == "rollback") ? "确认进行回滚操作吗？(注意：该操作将关闭云主机！！)" : "确认删除该快照吗？";
        if (confirm(confirmMsg)) {
            showLoading();
            $.post("shot/shotoperate", {
                operation: operation,
                snapshotId: sid,
                shotName: shotName,
                serverName: serverName
            }, function (data) {
                if (data.result == "success") {
                    fillTipBox("success", "操作成功！");
                    setTimeout(function () {
                        hideLoading();
                        window.location.reload();
                    }, 500);
                }
                else {
                    fillTipBox("error", "操作失败！请稍后重试！");
                    hideLoading();
                }
            });
            $.fillTipBox({type:'info', icon:'glyphicon-info-sign', content:'任务进行中，请勿重复点击'});
        }
    }


</script>
</div>
</body>
</html>
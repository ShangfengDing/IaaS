<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>硬盘详情 - 云海IaaS</title>
    <style>
        .slider-tick {
            opacity: 0 !important;
        }
        .slider-selection {
            background-image: -webkit-linear-gradient(top,#89cdef 0,#81bfde 100%);
            background-image: -o-linear-gradient(top,#89cdef 0,#81bfde 100%);
            background-image: linear-gradient(to bottom,#89cdef 0,#81bfde 100%);
            background-repeat: repeat-x;
        }
    </style>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=cloud"/>
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div class="inner">
                    <div>
                        <ol class="breadcrumb">
                            <li>资源管理</li>
                            <li><a href="hd/hdmanage">云硬盘管理</a></li>
                            <li class="active"><s:property value="diskDetail.DiskName"/></li>
                        </ol>
                    </div>
                    <div class="front-toolbar other"
                         style="margin-top: 5px; margin-left: -4px" id="prev-toolbar">
                        <div class="front-toolbar-header clearfix">
                            <button type="button" class="front-toolbar-toggle navbar-toggle"
                                    data-toggle="collapse" data-target="#base-buttons">
                                <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                                    class="icon-bar"></span>
                            </button>
                        </div>
                        <div id="base-buttons" class="front-btn-group collapse">

                            <%--<s:if test='diskDetail.DiskType=="NETWORK" || (diskDetail.DiskType=="data" && Aliportable)'>--%>
                                <%--<s:property value="#diskDetail.status"/>--%>
                                <%--<s:if test='diskDetail.status=="attached" || diskDetail.status=="In_use"'>--%>
                                    <%--<a class="btn btn-default front-no-box-shadow active">挂载</a>--%>
                                    <%--<a class="btn btn-default front-no-box-shadow" id="detach-button"--%>
                                       <%--onclick='detach("<s:property value="diskDetail.status"/>",--%>
                                               <%--"<s:property value="regionId"/>",--%>
                                               <%--"<s:property value="userEmail"/>",--%>
                                               <%--"<s:property value="diskDetail.DiskId"/>",--%>
                                               <%--"<s:property value="diskDetail.InstanceId"/>",--%>
                                               <%--"<s:property value="diskDetail.providerEn"/>","<s:property value="userIds"/>","<s:property value="appName"/>")'>释放</a>--%>
                                    <%--<s:if test="diskDetail.providerEn != #request.aliyun">--%>
                                        <%--<a class="btn btn-default front-no-box-shadow"--%>
                                           <%--onclick='del("<s:property value="diskDetail.status"/>",--%>
                                                   <%--"<s:property value="regionId"/>",--%>
                                                   <%--"<s:property value="userEmail"/>",--%>
                                                   <%--"<s:property value="diskDetail.DiskId"/>",--%>
                                                   <%--"<s:property value="diskDetail.providerEn"/>","<s:property value="userIds"/>","<s:property value="appName"/>")'>删除</a>--%>
                                    <%--</s:if>--%>
                                    <%--<s:else>--%>
                                        <%--<a class="btn btn-default front-no-box-shadow active">删除</a>--%>
                                    <%--</s:else>--%>

                                <%--</s:if>--%>
                                <%--<s:if test='diskDetail.status=="detached" || diskDetail.status=="Available"'>--%>
                                    <%--<a class="btn btn-default front-no-box-shadow" id="attach-button"--%>
                                       <%--onclick='firstPageAttach("<s:property value="diskDetail.status"/>",--%>
                                               <%--"<s:property value="regionId"/>",--%>
                                               <%--"<s:property value="userEmail"/>",--%>
                                               <%--"<s:property value="diskDetail.DiskId"/>",--%>
                                               <%--"<s:property value="diskDetail.providerEn"/>","<s:property value="userIds"/>","<s:property value="appName"/>")'>挂载</a>--%>
                                    <%--<a class="btn btn-default front-no-box-shadow active">释放</a>--%>
                                    <%--<a class="btn btn-default front-no-box-shadow"--%>
                                       <%--onclick='del("<s:property value="diskDetail.status"/>",--%>
                                               <%--"<s:property value="regionId"/>",--%>
                                               <%--"<s:property value="userEmail"/>",--%>
                                               <%--"<s:property value="diskDetail.DiskId"/>",--%>
                                               <%--"<s:property value="diskDetail.providerEn"/>","<s:property value="userIds"/>","<s:property value="appName"/>")'>删除</a>--%>
                                <%--</s:if>--%>
                            <%--</s:if>--%>
                            <s:if test='diskDetail.status=="attached" || diskDetail.status=="In_use"'>
                                <s:if test='diskDetail.providerEn=="yunhai"'>
                                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)" onclick='modalValue("<s:property value='diskDetail.DiskId'/>","<s:property value='diskDetail.providerEn'/>","<s:property value='regionId'/>","<s:property value='userEmail'/>","${param.userIds}","<s:property value="appName"/>","<s:property value="zoneId"/>")'>创建快照</a>
                                </s:if>
                                <s:else>
                                    <a class="btn btn-default front-no-box-shadow" href="javascript:void(0)" onclick='aliShotModal("<s:property value='diskDetail.DiskId'/>","<s:property value='diskDetail.InstanceId'/>",
                                            "<s:property value='diskDetail.DiskCategory'/>","<s:property value='userEmail'/>",
                                            "<s:property value='provider'/>","<s:property value='regionId'/>","<s:property value="appName"/>")'>创建快照</a>
                                </s:else>
                            </s:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 col-sm-12">
                            <div class="row">
                                <div class="col-md-12 col-sm-12" style="padding-right:0;">
                                    <div class="panel panel-default front-panel">
                                        <div class="panel-heading">基本信息</div>
                                        <div class="panel-body front-last-no-margin">
                                            <div  class="left-card">
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">硬盘ID</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter"><s:property value="diskDetail.DiskId"/></h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">供应商</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter"><s:property value="diskDetail.provider"/></h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">所在区</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter"><s:property value="diskDetail.region"/>-<s:property value="diskDetail.ZoneId"/></h5>
                                                    </div>
                                                </div>
                                                <!-- 所有名称和描述的修改都是公用一个action，即vm/preeditvm -->
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">硬盘名称</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter content-edit" id="hd_name"><s:property value="diskDetail.DiskName"/>&nbsp;</h5>
                                                        <a data-toggle="front-modal" data-title="修改云硬盘的名称"
                                                           data-href="vm/preeditvm?yunType=hd&target=hd_name&appname=<s:property value="appName"/>&id=<s:property value="diskDetail.DiskId"/>&name=<s:property value="diskDetail.DiskName"/>&type=name&regionId=<s:property value="diskDetail.regionId"/>&userIds=${param.userIds}&zoneId=<s:property value="zoneId"/>">
                                                            <span class="glyphicon glyphicon-edit"></span></a>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">硬盘状态</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 id="status_label" class="content-gutter"><s:property value="diskDetail.StatusCn"/></h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">硬盘描述</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter content-edit" id="hd_des"><s:property value="diskDetail.Description"/>&nbsp;</h5>
                                                        <a data-toggle="front-modal" data-title="修改云硬盘的描述"
                                                           data-href="vm/preeditvm?yunType=hd&target=hd_des&appname=<s:property value="appName"/>&id=<s:property value="diskDetail.DiskId"/>&name=<s:property value="diskDetail.Description"/>&type=description&regionId=<s:property value="diskDetail.regionId"/>&userIds=${param.userIds}&zoneId=<s:property value="zoneId"/>">
                                                            <span class="glyphicon glyphicon-edit"></span></a>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">硬盘种类</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter"><s:property value="diskDetail.DiskType"/></h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">硬盘属性</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter"><s:property value="diskDetail.DiskCategory"/></h5>
                                                    </div>
                                                </div>
                                                <s:if test="diskDetail.providerEn != #request.aliyun">
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <h5 class="label-pos">付费方式</h5>
                                                        </div>
                                                        <div class="col-md-9">
                                                            <h5 class="content-gutter content-edit"><s:property value="diskDetail.ChargeType"/>&nbsp;</h5>
                                                            <%--<a href="javascript:void(0)" onclick="modalshow()" >(续费)</a>--%>
                                                        </div>
                                                    </div>
                                                </s:if>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">创建时间</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter"><s:property value="diskDetail.CreateTime"/></h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">到期时间</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter"><s:property value="diskDetail.ExpiredTime"/></h5>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-12 col-sm-12" style="padding-right:0">
                                    <div class="panel panel-default front-panel">
                                        <div class="panel-heading">配置信息</div>
                                        <div class="panel-body front-last-no-margin">
                                            <div  class="left-card">

                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">硬盘容量</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter content-edit"><s:property value="diskDetail.Size"/>&nbsp;&nbsp;GB&nbsp;</h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">挂载实例</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter">
                                                            <s:if test="diskDetail.InstanceId == null || diskDetail.InstanceId == ''">None</s:if>
                                                            <s:else>
                                                                <s:property value="diskDetail.InstanceId"/>
                                                            </s:else>
                                                        </h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">挂载点</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <h5 class="content-gutter">
                                                            <s:property value="diskDetail.Device"/>
                                                        </h5>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-3">
                                                        <h5 class="label-pos">可卸载</h5>
                                                    </div>
                                                    <div class="col-md-9">
                                                        <s:if test="diskDetail.DiskType == 'NETWORK'|| Aliportable"><h5 class="content-gutter">支持</h5></s:if>
                                                        <s:else>
                                                            <h5 class="content-gutter">不支持</h5>
                                                        </s:else>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-6 long-label">
                                                        <h5 class="label-pos">硬盘是否随实例释放</h5>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <s:if test="diskDetail.DiskType == 'NETWORK'"><h5 class="content-gutter">不是</h5></s:if>
                                                        <s:else>
                                                            <h5 class="content-gutter">是</h5>
                                                        </s:else>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-7 long-label">
                                                        <h5 class="label-pos">自动快照是否随硬盘释放</h5>
                                                    </div>
                                                    <div class="col-md-5">
                                                        <h5 class="content-gutter">是</h5>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-8 col-sm-12">
                            <div class="row">
                                <div class="col-md-12 col-sm-12">
                                    <div class="panel panel-default front-panel">
                                        <div class="panel-body front-no-padding">
                                            <table class="table table-striped front-table" id="black-table"
                                                   style="margin-bottom: 0px;border-color: #ddd;">
                                                <thead style=" background-color: #f5f5f5">
                                                <tr>
                                                    <th class="col-sm-1" style=" background-color:#f5f5f5">云快照名称</th>

                                                    <th class="col-sm-2" style=" background-color:#f5f5f5">创建时间</th>
                                                    <!-- <td class="col-sm-1">进度</td> -->
                                                    <th class="col-sm-1" style=" background-color: #f5f5f5">状态</th>
                                                    <th class="col-sm-3" style="text-align: right; background-color: #f5f5f5">操作</th>
                                                </tr>
                                                </thead>
                                                <s:iterator id="snapshot" value="diskDetail.snapshotList">
                                                    <tr id="tr<s:property value="#snapshot.snapshotId"/>">
                                                        <td class="col-sm-1">
                                                            <div>
                                                                <label id="mainName"><s:property value="#snapshot.snapshotName"/></label>
                                                                    <%--<a--%>
                                                                    <%--data-toggle="front-modal" data-title="编辑云快照的名称"--%>
                                                                    <%--data-href="template/modal.jsp"><span--%>
                                                                    <%--class="glyphicon glyphicon-edit"></span></a>--%>
                                                            </div>
                                                        </td>

                                                        <td class="col-sm-2">
                                                            <div style="display: inline-block">
                                                                <label><s:property value="#snapshot.createTime"/></label>
                                                                    <%--<label style="width: 5px"></label>--%>
                                                                <!-- <label>11:29:59</label> -->
                                                            </div>
                                                        </td>
                                                        <td class="col-sm-1"><label><s:property value="#snapshot.status"/></label></td>
                                                        <td class="col-sm-2" style="text-align: right">
                                                            <div class="btn-group">
                                                                <a  href="javascript:void(0)" onclick="shotOperate('rollback','<s:property value="#snapshot.snapshotId"/>','<s:property value="attachInstance"/>','<s:property value="diskDetail.providerEn"/>','<s:property value="userEmail"/>','<s:property value="diskDetail.regionId"/>','<s:property value="diskDetail.DiskId"/>','${param.userIds}','<s:property value="appName"/>','<s:property value="zoneId"/>')">回滚磁盘</a>
                                                                <a  href="javascript:void(0)" onclick="shotOperate('delete','<s:property value="#snapshot.snapshotId"/>','<s:property value="attachInstance"/>','<s:property value="diskDetail.providerEn"/>','<s:property value="userEmail"/>','<s:property value="diskDetail.regionId"/>','<s:property value="diskDetail.DiskId"/>','${param.userIds}','<s:property value="appName"/>','<s:property value="zoneId"/>')">删除快照</a>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </s:iterator>
                                            </table>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-12 col-sm-12">
                                    <div class="panel panel-default front-panel">
                                        <div class="panel-heading">监控-硬盘</div>
                                        <div class="panel-body front-last-no-margin">
                                            <div id="disk_highchart" style="height: 250px;">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="newshotmodal.jsp"></s:include><!-- 创建快照的模态框 -->
<s:include value="hdmodal.jsp"></s:include><!-- 续费的jsp -->
<script>
    var userEmail = "<s:property value="userEmail"/>"
    var instanceId = "<s:property value="diskDetail.instanceId"/>"
    var diskId = "<s:property value="diskDetail.DiskId"/>"
    var providerEn = "<s:property value="diskDetail.providerEn"/>"
    var hdAppname = "<s:property value="appName"/>"
    var regionId = "<s:property value="diskDetail.regionId"/>"
    var dayPrice = parseInt("<s:property value="hdPrices[1]"/>")/100;
    var monthPrice = parseInt("<s:property value="hdPrices[2]"/>")/100;
    var yearPrice = parseInt("<s:property value="hdPrices[3]"/>")/100;
    var zoneId='<s:property value="zoneId"/>';
    var flag = false;
    var flag1 = false;
    var detailflag = true;
</script>
<script src="js/hd/hddetail.js"></script>
<%--<script src="js/hd/hdpricemodal.js"></script>--%>
<script src="js/hd/createshot.js"></script>
<script src="js/hd/alishot.js"></script>
</body>
</html>

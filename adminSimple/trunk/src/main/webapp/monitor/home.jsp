<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<html>
<head>
    <%--<s:include value="/monitor/_head.jsp"/>--%>
    <title>首页-智能云监控</title>
    <meta charset="utf-8">
    <style>
        .card {
            height: 280px;
            background-color: white;
        }

        .new-row {
            margin-top: 10px;
            height: 310px;
        }
        .block {
            padding: 5px;
        }
        .full {
            height: 100%;
        }
        .mylabel {
            margin-top: 15px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body class="front-body">
<div class="front-inner">
    <s:include value="/template/_banner.jsp?menu=home" />

    <div id="container" class="container">
        <div class="col-md-12">
        <h5 style="margin-top: 0px;">基础设施</h5>
            <div class="col-md-12 form-group" style="padding: 0px">
                <div class="inner">
                    <%--<div>
                        <ol class="breadcrumb">
                            <li>系统管理</li>
                            <li class="active">基础设施</li>
                        </ol>
                    </div>--%>
                    <input type="hidden" id="buttonType" value="${param.button}"/>
                    <%--<div class="front-toolbar other" type="hidden">
                        &lt;%&ndash;<div class="front-toolbar-header clearfix">&ndash;%&gt;
                            &lt;%&ndash;<button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse" data-target="#freeshare">&ndash;%&gt;
                                &lt;%&ndash;<span class="icon-bar"></span>&ndash;%&gt;
                                &lt;%&ndash;<span class="icon-bar"></span>&ndash;%&gt;
                                &lt;%&ndash;<span class="icon-bar"></span>&ndash;%&gt;
                            &lt;%&ndash;</button>&ndash;%&gt;
                        &lt;%&ndash;</div>&ndash;%&gt;
                        <div id="freeshare" class="front-btn-group collapse" data-toggle="buttons" type="hidden">
                            &lt;%&ndash;<a id="allbox"class="btn btn-default front-no-box-shadow selected" onclick="showAll()"><input type="radio" name="options" autocomplete="off">全&nbsp;部<span id="all"></span></a>&ndash;%&gt;
                            <a  class="btn btn-default front-no-box-shadow" id="computeNode" onclick="findNodeByType('COMPUTE_NODE','servicecount')"><input type="radio" name="options" autocomplete="off">服务器<span id="servicecount"></span></a>
                            <a class="btn btn-default front-no-box-shadow" id="functionNode" onclick="findNodeByType('FUNCTION_NODE','uncontrolcount')"><input type="radio" name="options" autocomplete="off">非受控虚拟机<span id="uncontrolcount"></span></a>
                            <a class="btn btn-default front-no-box-shadow" id="diskDevice" onclick="findByType('DISKDEVICE','diskcount')"><input type="radio" name="options" autocomplete="off">存储<span id="diskcount"></span></a>
                            <a class="btn btn-default front-no-box-shadow" id="NetDeivce" onclick="findByType('NETDEVICE','netcount')"><input type="radio" name="options" autocomplete="off">网络设备<span id="netcount"></span></a>
                            <a class="btn btn-default front-no-box-shadow" id="Other" onclick="findByType('OTHER','elsecount')"><input type="radio" name="options" autocomplete="off">其他<span id="elsecount"></span></a>
                        </div>
                        <span class="pull-right" id="buildService"><a href="javascript:void(0)" id="addService" class="btn btn-primary pull-right">新建</a></span>
                    </div>--%>
                    <div class="panel panel-default front-panel" style="margin-top:10px;border: 0px;">
                        <div class="panel-body front-no-padding">
                            <div id="tabelDiv"></div>
                            <div id="vmsummaryTable" class="hide">
                                <%--<table id="infoTable" class="table table-striped front-table table-bordered table-hover" style="margin-bottom: 0px">--%>
                                <%--<thead>--%>
                                <%--<tr>--%>
                                <%--<th style="text-align: center">名称</th>--%>
                                <%--<th class="col-md-1" style="text-align: center">UUID</th>--%>
                                <%--<th class="col-md-1" style="text-align: center">集群</th>--%>

                                <%--<th class="col-md-2" style="text-align: center">配置</th>--%>
                                <%--<th class="col-md-3" style="text-align: center">运行状态</th>--%>
                                <%--&lt;%&ndash;<th class="col-md-1">位置</th>&ndash;%&gt;--%>
                                <%--<th class="col-md-2" style="text-align: center">备注</th>--%>
                                <%--<th class="col-md-1" style="text-align: center">操作</th>--%>
                                <%--</tr>--%>
                                <%--</thead>--%>
                                <%--<div id="allHosts">--%>
                                <%--<s:if test="vmlist.size>0">--%>
                                <%--<s:iterator value="vmlist" id="vmlist">--%>
                                <%--<s:if test='#vmlist.type.toString()!="非受控虚拟机"'>--%>
                                <%--<tr id="<s:property value="#vmlist.uuid"/>" data-uuid="<s:property value="#vmlist.uuid"/>" class="host" name="<s:property value="#vmlist.type"/>">--%>
                                <%--<td style="vertical-align: middle;overflow: auto"><s:property value="#vmlist.name"/></td>--%>
                                <%--<td style="vertical-align: middle;overflow: auto"><s:property value="#vmlist.uuid"/></td>--%>
                                <%--&lt;%&ndash;<td style="vertical-align: middle; text-align: center;overflow: auto">非受控<br>虚拟机</td>&ndash;%&gt;--%>
                                <%--<td style="vertical-align: middle; text-align: center;overflow: auto" class="Nodetype"></td>--%>

                                <%--<td style="vertical-align: middle; overflow: auto"><s:property value="#vmlist.model" escape="false"/></td>--%>
                                <%--<td style="vertical-align: middle;padding: 3px;">--%>
                                <%--&lt;%&ndash;<div style="width: 50%;float: left;vertical-align: middle">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="margin: 0px;font-size: 12px;">CPU使用率:&nbsp;<span id="cpuUsed<s:property value="#vmlist.uuid"/>"></span>%</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="progress" style="margin-bottom: 2px;margin-top: 2px">&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<span class="green" style="width: 20%;"><span>20%</span></span>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<span style=><p style="margin: 0px;font-size: 12px;">内存占用率:&nbsp;<span id="memUsed<s:property value="#vmlist.uuid"/>"></span>%</p></span>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="progress" style="margin-bottom: 5px;margin-top: 2px">&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<span class="red" style="width: 80%;"><span>80%</span></span>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="margin: 0px;font-size: 12px;">内存交换率:&nbsp;<span id="memSwap<s:property value="#vmlist.uuid"/>"></span>%</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="margin-bottom: 0px;">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="font-size: 12px">硬盘占用率:&nbsp;<span id="diskUsed<s:property value="#vmlist.uuid"/>"></span>%</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="width: 50%;float: right;vertical-align: middle">&ndash;%&gt;--%>

                                <%--&lt;%&ndash;<div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<span style=><p style="margin: 0px;font-size: 12px;">磁盘坏道数:&nbsp;<span id="diskErrorCount<s:property value="#vmlist.uuid"/>"></span></p></span>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<div class="progress" style="margin-bottom: 5px;margin-top: 2px">&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;<span class="red" style="width: 80%;"><span>80%</span></span>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;&lt;%&ndash;</div>&ndash;%&gt;&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="margin-bottom: 0px;">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="font-size: 12px">网络收:&nbsp;<span id="netDownload<s:property value="#vmlist.uuid"/>"></span>MB/S</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="margin-bottom: 0px;">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="font-size: 12px">网络发:&nbsp;<span id="netUpload<s:property value="#vmlist.uuid"/>"></span>MB/S</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

                                <%--&lt;%&ndash;<div style="width: 50%;float: left;vertical-align: middle">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<span style="vertical-align: middle"><p>test</p></span>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--</td>--%>
                                <%--<td style="vertical-align: middle;overflow: auto"><s:property value="#vmlist.description" escape="false"/></td>--%>
                                <%--<td style="vertical-align:middle;text-align: center;overflow:auto">--%>
                                <%--<div style="padding-left: 1px"><a href="javascript:void(0)" data-uuid="<s:property value="#vmlist.uuid"/>" onclick="deleteService(this)">删除</a>&nbsp;--%>
                                <%--<a href="javascript:void(0)" data-uuid="<s:property value="#vmlist.uuid"/>" onclick="showEditModal(<s:property value='#vmlist.id'/>,this)">修改</a></div>--%>
                                <%--&lt;%&ndash;<a href="javascript:void(0)"   onclick="showVmSummaryInfo(<s:property value="#vmlist.uuid"/>)">详情</a>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<a href="javascript:void(0)" data-uuid="<s:property value="#vmlist.uuid"/>" onclick="showVmSummaryInfo(this)">详情</a></td>&ndash;%&gt;--%>
                                <%--<div  style="height:3px"></div>--%>
                                <%--&lt;%&ndash;<s:if test='#vmlist.type.toString()=="服务器"'>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<a href="system/vmSummaryInfo?uuid=<s:property value='#vmlist.uuid'/>&type=其他设备">详情</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</s:if>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<s:else>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<a href="system/vmSummaryInfo?uuid=<s:property value='#vmlist.uuid'/>&type=<s:property value='#vmlist.type'/>">详情</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div>详情</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&ndash;%&gt;--%>
                                <%--</td>--%>
                                <%--&lt;%&ndash;</s:else>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
                                <%--</tr>--%>
                                <%--</s:if>--%>
                                <%--</s:iterator>--%>
                                <%--</s:if>--%>
                                <%--</div>--%>
                                <%--</table>--%>
                            </div>
                        </div>
                    </div>
                </div>
                <%--edit service--%>
                <div class="modal fade" id="modifyService-modal" tabindex="-1" role="dialog"
                     aria-labelledby="food-info-modal-label">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="padding-top:10px;padding-bottom:0px">
                                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
                                <h4 class="modal-title" id="food-info-modal-label">修改物理机</h4>
                            </div>
                            <input type="hidden" id="modifiedId"/>
                            <div class="modal-body" style="padding:10px">
                                <div class='form-horizontal'>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">名称</label>
                                        <div class="col-md-10">
                                            <input type="text" class="form-control front-no-box-shadow" id="modifiedName"/>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">UUID</label>
                                        <div class="col-md-10">
                                            <input type="text" class="form-control front-no-box-shadow" id="modifiedUUID"/>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">类型</label>
                                        <div class="col-md-10">
                                            <select class="form-control front-no-radius front-no-box-shadow" id="modifiedType" name="groupId">
                                                <option value="NETDEVICE" default>网络设备</option>
                                                <option value="DISKDEVICE">存储</option>
                                                <option value="OTHER">其他</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">配置</label>
                                        <div class="col-md-10">
                                            <textarea class="form-control front-no-box-shadow" id="modifiedModel" rows="3"></textarea>
                                        </div>
                                    </div>
                                    <div class='form-group hide'>
                                        <label class="col-md-2 control-label front-label">位置</label>
                                        <div class="col-md-10">
                                            <input type="text" class="form-control front-no-box-shadow" id="modifiedPosition"/>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">备注</label>
                                        <div class="col-md-10">
                                            <textarea  class="form-control front-no-box-shadow" id="modifiedDescription" rows="3"></textarea>
                                        </div>
                                    </div>
                                    <div class="modal-footer" style="padding-top:10px;padding-bottom:0px;padding-right:0px; border-top:none; background-color:white">
                                        <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
                                        <a href="javascript: modifyService();"
                                           class="btn btn-primary">确定</a> <!-- 注意按钮换行会导致多余的外补(margin) -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%--增加service--%>
                <div class="modal fade" id="addService-modal" tabindex="-1" role="dialog"
                     aria-labelledby="food-info-modal-label">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="padding-top:10px;padding-bottom:0px">
                                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
                                <h4 class="modal-title" id="food-info-modal-label">添加设备</h4>
                            </div>
                            <div class="modal-body" style="padding:10px">
                                <div class='form-horizontal'>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">名称</label>
                                        <div class="col-md-10">
                                            <input type="text" class="form-control front-no-box-shadow" id="newName"/>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">UUID</label>
                                        <div class="col-md-10">
                                            <input type="text" class="form-control front-no-box-shadow" id="newUUID"/>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">类型</label>
                                        <div class="col-md-10">
                                            <select class="form-control front-no-radius front-no-box-shadow" id="newType" name="groupId">
                                                <option value="NETDEVICE" default>网络设备</option>
                                                <option value="DISKDEVICE">存储</option>
                                                <option value="OTHER" >其他</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">配置</label>
                                        <div class="col-md-10">
                                            <textarea class="form-control front-no-box-shadow" id="newModel" rows="3" ></textarea>
                                        </div>
                                    </div>
                                    <div class='form-group hide'>
                                        <label class="col-md-2 control-label front-label">位置</label>
                                        <div class="col-md-10">
                                            <input type="text" class="form-control front-no-box-shadow" id="newPosition"/>
                                        </div>
                                    </div>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">备注</label>
                                        <div class="col-md-10">
                                            <textarea type="text" class="form-control front-no-box-shadow" id="newDescription" rows="3"></textarea>
                                        </div>
                                    </div>
                                    <div class="modal-footer" style="padding-top:10px;padding-bottom:0px;padding-right:0px; border-top:none; background-color:white">
                                        <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
                                        <a href="javascript: addService();"
                                           class="btn btn-primary">确定</a> <!-- 注意按钮换行会导致多余的外补(margin) -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%--修改节点--%>
                <div class="modal fade" id="modifyNodeService-modal" tabindex="-1" role="dialog"
                     aria-labelledby="food-info-modal-label">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header" style="padding-top:10px;padding-bottom:0px">
                                <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
                                <h4 class="modal-title" id="food-info-modal-label">修改<span id="nodeType"></span></h4>
                            </div>
                            <input type="hidden" id="modifiedNodeId"/>
                            <input type="hidden" id="modifiedNodeType"/>
                            <div class="modal-body" style="padding:10px">
                                <div class='form-horizontal'>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">名称</label>
                                        <div class="col-md-10">
                                            <input type="text" class="form-control front-no-box-shadow" id="modifiedNodeName"/>
                                        </div>
                                    </div>
                                    <%--<div class='form-group'>--%>
                                    <%--<label class="col-md-2 control-label front-label">UUID</label>--%>
                                    <%--<div class="col-md-8">--%>
                                    <%--<input type="text" class="form-control front-no-box-shadow" id="modifiedUUID"/>--%>
                                    <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class='form-group'>--%>
                                    <%--<label class="col-md-2 control-label front-label">类型</label>--%>
                                    <%--<div class="col-md-8">--%>
                                    <%--<select class="form-control front-no-radius front-no-box-shadow" id="modifiedType" name="groupId">--%>
                                    <%--<option value="SERVICE" default>服务器</option>--%>
                                    <%--<option value="NETDEVICE">存储</option>--%>
                                    <%--<option value="DISKDEVICE">网络设备</option>--%>
                                    <%--<option value="UNCONTROLLEDVM">非受控虚拟机</option>--%>
                                    <%--</select>--%>
                                    <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class='form-group'>--%>
                                    <%--<label class="col-md-2 control-label front-label">配置</label>--%>
                                    <%--<div class="col-md-8">--%>
                                    <%--<textarea class="form-control front-no-box-shadow" id="modifiedModel" rows="3"></textarea>--%>
                                    <%--</div>--%>
                                    <%--</div>--%>
                                    <%--<div class='form-group hide'>--%>
                                    <%--<label class="col-md-2 control-label front-label">位置</label>--%>
                                    <%--<div class="col-md-8">--%>
                                    <%--<input type="text" class="form-control front-no-box-shadow" id="modifiedPosition"/>--%>
                                    <%--</div>--%>
                                    <%--</div>--%>
                                    <div class='form-group'>
                                        <label class="col-md-2 control-label front-label">备注</label>
                                        <div class="col-md-10">
                                            <textarea type="text" class="form-control front-no-box-shadow" id="modifiedNodeExtend" rows="3"></textarea>
                                        </div>
                                    </div>
                                    <div class="modal-footer" style="padding-top:10px;padding-bottom:0px;padding-right:0px; border-top:none; background-color:white">
                                        <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
                                        <a href="javascript: modifyNodeService();"
                                           class="btn btn-primary">确定</a> <!-- 注意按钮换行会导致多余的外补(margin) -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        <%--<div class="row" style="height:310px">
            <div class="col-md-4 full" style="text-align: center;padding-right:5px;">
                <div class="full" style="background-color:#5078f1;color:white;">
                    <p style="height: 14px"></p>
                    <p style="font-size: 15px">当前平台整体运行情况</p>
                    <p style="font-size: 65px" id="healthgrade"><s:property value="lastHealth"/></p>
                    <br/>
                    <p>·当前平台整体运行情况良好，基础设施<br>和各平台都保持了良好的运行状态。</p>
                    <p>·近30天平台整体运行稳定，且一直保持<br>在良好的状态</p>
                </div>
            </div>
            <div class="col-md-8 full" style="text-align: center;padding-right: 5px">
                <div class="full" style="height: 310px;"  id="healthTable"></div>
            </div>
        </div>--%>

        <%--<h5 class="mylabel">平台信息&nbsp;(&nbsp;地域:&nbsp;<span id="regionId"></span>&nbsp;/&nbsp;可用区:&nbsp;<span id="zoneId"></span>&nbsp;)</h5>

        <div class="row new-row" style="margin-bottom: 20px">
            <div class="col-md-4 full"style="padding-right: 0px">
                <div style="padding-left: 0px;padding-right: 0px;margin-bottom: 15px">
                    <div style="background-color: white;height: 150px;padding-right: 8px">
                        <br/>
                        <div class="col-md-4">
                            <a href="system/hostmanage?page=1&button=1" style="color:black;text-decoration: none;">  <div id="servicenum" style="height:55%"><p style="font-size: 50px;text-align: center;"><span style="font-size:12px;"><br><br>数据加载中...</span></p></div>
                            </a>
                                <p style="font-size: 15px;text-align: center"><a href="system/hostmanage?page=1&button=1" style="color:black">服务器</a></p>
                        </div>
                        <div class="col-md-4">
                            <a href="system/hostmanage?page=1&button=3" style="color:black;text-decoration:none"><div id="diskcount" style="height:55%"><p style="font-size: 50px;text-align: center"><span style="font-size:12px;"><br><br>数据加载中...</span></p></div>
                            </a>
                            <p style="font-size: 15px;text-align: center"><a href="system/hostmanage?page=1&button=3" style="color:black">存储</a></p>
                        </div>
                        <div class="col-md-4">
                            <!--    <p style="font-size: 38px;text-align: center">12</p>   -->
                            <a href="system/hostmanage?page=1&button=4" style="color:black;text-decoration:none;"> <div id="netcount" style="height:55%"><p style="font-size: 50px;text-align: center;"><span style="font-size:12px;"><br><br>数据加载中...</span></p></div>
                            </a>
                                <p style="font-size: 15px;text-align: center"><a href="system/hostmanage?page=1&button=4" style="color:black">网络设备</a></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-6" style="padding-left: 0px;padding-right: 7.5px">
                    <div style="background-color: white;height: 150px">
                        <br/>
                        <div style="height:55%"><p style="text-align: center;">
                            <a href="user/usermanage.jsp" style="color:black;text-decoration:none">
                                <span style="font-size: 50px;text-align: center;" id="usercount"></span></a>
                            <a href="group/acGroupList" style="color:black;text-decoration:none"><span style="font-size: 50px;text-align: center;" id="usergroup"><span style="font-size:12px;"><br><br>数据加载中...</span></span></a></p></div>

                        <p style="font-size: 15px;text-align: center"><a href="user/usermanage.jsp" style="color:black">用户</a>/<a href="group/acGroupList" style="color:black">用户组</a></p>
                    </div>
                </div>
                <div class="col-md-6" style="padding-right: 0px;padding-left: 7.5px">
                    <div style="background-color: white;height: 150px">
                        <br/>
                        <div style="height:55%"><a href="system/cluster" style="color:black;text-decoration: none"><p style="font-size: 50px;text-align: center;height:100%" id="clusternum"><span style="font-size:12px;"><br><br>数据加载中...</span></p></a></div>
                        <p style="font-size: 15px;text-align: center"><a href="system/cluster" style="color:black">集群</a></p>
                    </div>
                </div>
            </div>
            <div class="col-md-4 full"style="padding-right: 0px"><a href="vm/presearchvm">
                <div id="cloudHost" style="height: 315px"></div></a>
            </div>
            <div class="col-md-4 full"style="padding-right: 15px">
                <div style="height: 315px">
                    <a href="img/imglist"><div id="hd" class="col-md-6" style="height: 100%">2</div></a>
                    <a href="hd/hdmanage"><div id="img" class="col-md-6" style="height: 100%">1</div></a>
                </div>
            </div>
        </div>--%>

        <h5 class="mylabel">基础设施状态</h5>

        <div class="col-md-12 placeholder" style="padding-left: 1px; padding-right: 1px">
            <div class="panel panel-default front-panel" style="padding-bottom:10px;">
                <%--<div class="panel-heading">Physical Host Status</div>--%>
                <div id="host1" class="panel-body" style="height: 400px;"></div>
            </div>
        </div>
        <%--<h5 class="mylabel">平台服务</h5>
        <div class="row new-row" style="margin-bottom: 20px">
            <div class="col-md-12 full"style="padding-right: 15px">
                <div class="col-md-2" style="padding-left: 0px;padding-right: 7.5px"><a id="VOLUME_SCHEDULERurl" style="color:black;text-decoration: none;">
                    <div style="background-color: white;height: 250px">
                        <div style="height: 40px;padding-right: 10px;padding-top: 10px" align="right">
                            <img id="VOLUME_SCHEDULER" src="images/service_green.png" alt="light" height="20px">
                        </div>
                        <div style="height: 125px" align="center">
                            <img src="images/volume.png" alt="VOLUME_SCHEDULER" height="110px">
                        </div>
                        <p style="font-size: 15px;text-align: center;"><strong id="VOLUME_SCHEDULERname">数据加载中...</strong></p>
                        <p style="font-size: 13px;text-align: center" name="type">VOLUME<BR>SCHEDULER</p>
                    </div></a>
                </div>
                <div class="col-md-2" style="padding-left: 7.5px;padding-right: 7.5px"><a id="NETWORK_PROVIDERurl" style="color:black;text-decoration: none;">
                    <div style="background-color: white;height: 250px">
                        <div style="height: 40px;padding-right: 10px;padding-top: 10px" align="right">
                            <img id="NETWORK_PROVIDER" src="images/service_green.png" alt="light" height="20px">
                        </div>
                        <div style="height: 125px" align="center">
                            <img src="images/network.png" alt="NETWORK_PROVIDER" height="110px">
                        </div>
                        <p style="font-size: 15px;text-align: center;"><strong id="NETWORK_PROVIDERname">数据加载中...</strong></p>
                        <p style="font-size: 13px;text-align: center">NETWORK<br>PROVIDER</p>
                    </div></a>
                </div>
                <div class="col-md-2" style="padding-left: 7.5px;padding-right: 7.5px"><a id="VM_SCHEDULERurl" style="color:black;text-decoration: none;">
                    <div style="background-color: white;height: 250px">
                        <div style="height: 40px;padding-right: 10px;padding-top: 10px" align="right">
                            <img id="VM_SCHEDULER" src="images/service_green.png" alt="light" height="20px">
                        </div>
                        <div style="height: 125px" align="center">
                            <img src="images/vm_service.png" alt="VM_SCHEDULER" height="110px">
                        </div>
                        <p style="font-size: 15px;text-align: center;"><strong id="VM_SCHEDULERname">数据加载中...</strong></p>
                        <p style="font-size: 13px;text-align: center">VM<br>SCHEDULER</p>
                    </div></a>
                </div>
                <div class="col-md-2" style="padding-left: 7.5px;padding-right: 7.5px"><a id="RESOURCE_SCHEDULERurl" style="color:black;text-decoration: none;">
                    <div style="background-color: white;height: 250px">
                        <div style="height: 40px;padding-right: 10px;padding-top: 10px" align="right">
                            <img id="RESOURCE_SCHEDULER" src="images/service_green.png" alt="light" height="20px">
                        </div>
                        <div style="height: 125px" align="center">
                            <img src="images/resource_service.png" alt="RESOURCE_SCHEDULER" height="110px">
                        </div>
                        <p style="font-size: 15px;text-align: center;"><strong id="RESOURCE_SCHEDULERname">数据加载中...</strong></p>
                        <p style="font-size: 13px;text-align: center">RESOURCE<br>SCHEDULER</p>
                    </div></a>
                </div>
                <div class="col-md-2" style="padding-left: 7.5px;padding-right: 7.5px"><a id="LOL_SERVERurl" style="color:black;text-decoration: none;">
                    <div style="background-color: white;height: 250px">
                        <div style="height: 40px;padding-right: 10px;padding-top: 10px" align="right">
                            <img id="LOL_SERVER" src="images/service_green.png" alt="light" height="20px">
                        </div>
                        <div style="height: 125px" align="center">
                            <img src="images/LOL_service.png" alt="LOL_SERVER" height="110px">
                        </div>
                        <p style="font-size: 15px;text-align: center;"><strong id="LOL_SERVERname">数据加载中...</strong></p>
                        <p style="font-size: 13px;text-align: center">LOL<br>SERVER</p>
                    </div></a>
                </div>
                <div class="col-md-2" style="padding-left: 7.5px;padding-right: 0px"><a id="IMAGE_SERVERurl"  style="color:black;text-decoration: none;">
                    <div style="background-color: white;height: 250px">
                        <div style="height: 40px;padding-right: 10px;padding-top: 10px" align="right">
                            <img id="IMAGE_SERVER" src="images/service_green.png" alt="light" height="20px">
                        </div>
                        <div style="height: 125px" align="center">
                            <img src="images/image_service.png" alt="IMAGE_SERVER" height="110px">
                        </div>
                        <p style="font-size: 15px;text-align: center;"><strong id="IMAGE_SERVERname">数据加载中...</strong></p>
                        <p style="font-size: 13px;text-align: center">IMAGE<br>SERVER</p>
                    </div></a>
                </div>
            </div>
        </div>--%>



        <%--<div class="col-lg-4 placeholder" style="padding-left: 1px; padding-right: 5px">--%>
        <%--<div class="panel panel-default front-panel" style="margin-bottom:10px;">--%>
        <%--<div class="panel-heading">Cloud Plant Health Status</div>--%>
        <%--<div id="health" class="panel-body" style="height: 400px"></div>--%>
        <%--</div>--%>
        <%--</div>--%>

        <%--<div class="col-lg-8 placeholder" style="padding-left: 5px;padding-right: 1px">--%>
        <%--<div class="panel panel-default front-panel" style="margin-bottom: 10px">--%>
        <%--&lt;%&ndash;<div class="panel-heading">Cloud Plant Health Status Tendency</div>&ndash;%&gt;--%>
        <%--<div id="healthTable" class="panel-body" style="height: 400px"></div>--%>
        <%--</div>--%>
        <%--</div>--%>

        <%--<div class="col-lg-12 placeholder" style="padding-left: 1px; padding-right: 5px">--%>
        <%--<div class="panel panel-default front-panel" style="margin-bottom:10px;">--%>
        <%--&lt;%&ndash;<div class="panel-heading">Cloud Service</div>&ndash;%&gt;--%>
        <%--<div id="serviceTable" class="panel-body" style="height: 500px;">--%>

        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>



    </div>
    </div>
    <jsp:include page="/template/_footer.jsp"/>
</div>

<%--<script src="statics/js/jquery/jquery.min.js"></script>--%>
<script src="statics/echart/echarts.js"></script>
<script src="statics/echart/echarts-liquidfill.js"></script>

<%--云主机饼图--%>
<script type="text/javascript">
    $('.Nodetype').each(function(){
        if($(this).html()=="非受控虚拟机"){
            $(this).html("非受控<br>虚拟机");
        }
    })
    $(document).ready(function(){
        //do something
        findNodeByType('COMPUTE_NODE','servicecount');
    })

    //
    var type=$('#buttonType').val();
    if(type==5){
        $("#Other").click();
    }else if(type==2){
        $('#functionNode').click();
    }else if(type==3){
        $('#diskDevice').click();
    }else if(type==4){
        $('#NetDeivce').click();
    }else{
        $('#computeNode').click();
    }

    function findNodeByType(types,id){
        $('#buildService').addClass('hidden');
        hidecount();
        var url='system/findNodeByType';
        $.get(url,{
            type:types
        },function(data){
            $('#tabelDiv').html(data);
            $('#'+id).html("("+$('#nodeCount').val()+")");
            showHostDetailInfo();
        });
    }

    function findByType(types,id){
        $('#buildService').removeClass('hidden');
        hidecount();
        var url='system/findDeviceByType';
        $.get(url,{
            type:types
        },function(data){
            $('#tabelDiv').html(data);
            $('#'+id).html("("+$('#deviceCount').val()+")");
        });
    }

    function hidecount(){
        // $('#all').html("");
        $('#servicecount').html("");
        $('#diskcount').html("");
        $('#netcount').html("");
        $('#uncontrolcount').html("");
        $('#elsecount').html("");
    }
    <%--显示详情--%>

    // function showVmSummaryInfo(item){
    //     var uuid_vm=$(item).data('uuid');
    //     alert(uuid_vm);
    //     var url = 'system/vmSummaryInfo';
    //     $.get(url,{
    //         uuid:uuid_vm
    //     },function (data){
    //         alert(data.cpuUsed)
    //     })
    // }

    <%--服务器实时信息显示--%>


    $(function(){
        $('#addService').on('click', function () {
            $("#addService-modal").modal();
        });

    });

    function showEditModal(id,item){
        var uuid=$(item).data('uuid');
        var url='system/findServiceByUUID';
        $('#modifiedId').val(id);
        $.get(url,{
            uuid:uuid,
            id:id
        },function(data){
            if(data.result=="true"){
                var model=data.vmSummary.model;
                model=model.replace(/<br>/g,'\r\n');
                var description=data.vmSummary.description;
                description=description.replace(/<br>/g,'\r\n');
                $('#modifiedName').val(data.vmSummary.name);
                $('#modifiedModel').val(model);
                $('#modifiedType').val(data.vmSummary.type);
                $('#modifiedUUID').val(uuid);
                $('#modifiedPosition').val(data.vmSummary.position);
                $('#modifiedDescription').val(description);
                $('#modifyService-modal').modal();
            }
        })
    }

    function showHostDetailInfo(){
        var uuids=[];
//        $('#infoTable').find('.host').each(function(){
//            uuids.push($(this).data('uuid'));
//        });
        $('#nodeTable').find('.host').each(function(){
            uuids.push($(this).data('uuid'));
        });
        for(i=0;i<uuids.length;i++){
            getStatus(uuids[i]);
        }

    }

    function getStatus(uuid){
        var url='system/findState';
        $.post(url,{
            uuidOne:uuid,
        },function(data){
            var cpuUsed=data.mapMetrics['cpuUsed'];
            var memUsed=data.mapMetrics['memUsed'];
            var memSwap=data.mapMetrics['memSwap'];
            var diskUsed=data.mapMetrics['diskUsed'];
            var diskErrorCount=data.mapMetrics['diskErrorCount'];
            var netDownload=((data.mapMetrics['netDownload'])/1000).toFixed(2);
            var netUpload=((data.mapMetrics['netUpload'])/1000).toFixed(2);
            $('#cpuUsed'+uuid).html(cpuUsed);
            $('#memUsed'+uuid).html(memUsed);
            $('#memSwap'+uuid).html(memSwap);
            $('#diskUsed'+uuid).html(diskUsed);
            $('#diskErrorCount'+uuid).html(diskErrorCount);
            $('#netDownload'+uuid).html(netDownload);
            $('#netUpload'+uuid).html(netUpload);

            // host1.setOption(option13,true);
        });
    }
    function deleteService(id,item){
        var uuid= $(item).data('uuid');
        $.tipModal('confirm', 'warning', '确认删除该设备吗？', function(result) {
            if(result==true){
                var url="system/deleteService";
                $.get(url,{
                    uuid:uuid,
                    id:id
                },function(data){
                    if(data.result=="true"){
                        $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'删除成功！'});
                    }else{
                        $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'操作失败请重试'});
                    }
                    setTimeout(function(){
                        location.reload();
                    },1000);
                })
            }else{
            }
        });
    }
    function modifyService(){
        var model=document.getElementById("modifiedModel").value;
        model=model.replace(/\r\n/g,'<br>');
        model=model.replace(/\n/g,'<br>');
        var description=document.getElementById("modifiedDescription").value;
        description=description.replace(/\r\n/g,'<br>');
        description=description.replace(/\n/g,'<br>');
        var url='system/modifyService';
        var type=$('#modifiedType').val();
        $.get(url,{
            id:$('#modifiedId').val(),
            uuid:$('#modifiedUUID').val(),
            name:$('#modifiedName').val(),
            type:$('#modifiedType').val(),
            description:description,
            position:$('#modifiedPosition').val(),
            model:model
        },function(data){
            if(data.result=="true"){
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改成功！'});
            }else{
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'操作失败请重试'});
            }
            setTimeout(function(){
                $('#modifyService-modal').modal('hide');
                var buttons=3;
                if(type == 'NETDEVICE'){
                    buttons=4;
                }else if(type == 'OTHER'){
                    buttons=5;
                }else{
                    buttons=3;
                }
                window.location.href="system/hostmanage?page=1&button="+buttons;
            },1000);
        })
    }
    function addService(){
        var uuid=$('#newUUID').val();
        var name=$('#newName').val();
        var type=$('#newType').val();
        var model=document.getElementById("newModel").value;
        model=model.replace(/\r\n/g,'<br>');
        model=model.replace(/\n/g,'<br>');
        var description=document.getElementById("newDescription").value;
        description=description.replace(/\r\n/g,'<br>');
        description=description.replace(/\n/g,'<br>');
        var position=$('#newPosition').val();
        $('#newUUID').html("");
        $('#newName').html("");
        $('#newType').html("");
        $('#newModel').html("");
        $('#newPosition').html("");
        $('#newDescription').html("");
        var url="system/addService";
        $.post(url,{
            uuid:uuid,
            name:name,
            type:type,
            model:model,
            position:position,
            description:description
        },function(data){
            if(data.result=="true"){
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'添加成功！'});
            }else{
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'操作失败请重试'});
            }
            setTimeout(function(){
                $('#fileupload-modal').modal('hide');
                var buttons=3;
                if(type=='NETDEVICE'){
                    buttons=4;
                }else if(type=='OTHER'){
                    buttons=5;
                }else{
                    buttons=3;
                }
                window.location.href="system/hostmanage?page=1&button="+buttons;
            },1000);
        });
    }

    <%--function doLink(strURL){--%>
    <%--window.location = strURL;--%>
    <%--}--%>

    function showNodeEditModal(id,item){
        var uuid=$(item).data('uuid');
        var url='system/findNodeByUUID'
        $.get(url,{
            uuid:uuid,
        },function(data){
            if(data.result=="success"){
                var extend=data.host.extend;
                extend=extend.replace(/<br>/g,'\r\n');
                $('#modifiedNodeName').val(data.host.name);
                $('#modifiedNodeExtend').val(extend);
                $('#modifiedNodeId').val(uuid);
                $('#modifiedNodeType').val(data.host.type.toString());
                if(data.host.type.toString()=="COMPUTE_NODE"){
                    $("#nodeType").html("节点");
                }else{
                    $('#nodeType').html("非受控虚拟机");
                }
            }

        });
        $('#modifyNodeService-modal').modal();
    }

    function modifyNodeService(){
        var name=$('#modifiedNodeName').val();
        var extend= document.getElementById("modifiedNodeExtend").value;
        extend=extend.replace(/\r\n/g,'<br>');
        extend=extend.replace(/\n/g,'<br>');
        var uuid=$('#modifiedNodeId').val();
        var url='system/modifyNode';
        $.get(url,{
            name:name,
            extend:extend,
            uuid:uuid
        },function(data){
            if(data.result=="success"){
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改成功！'});

            }else{
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'操作失败请重试'});
            }
            $('#modifyNodeService-modal').modal('hide');
            setTimeout(function(){
                var type=$("#modifiedNodeType").val();
                var buttons=1;
                if(type=="COMPUTE_NODE"){
                    buttons=1;
                }else{
                    buttons=2;
                }
                window.location.href="system/hostmanage?page=1&button="+buttons;
            },1000);

        })

    }
    var refreshFlag;
    var cloudHost = document.getElementById('cloudHost');
    var myChart10 = echarts.init(cloudHost);
    option10 = {
        backgroundColor:'#fdfdfd',
        title:{
            left:'center',
            top: '13px',
            text:'云主机\n\n\n\n\n',
            itemGap:10,
            subtext:''

        },
        tooltip: {
            show:true,
            trigger:'item'
        },
        legend: {

            orient: 'vertical',
            x: 'right',
            y:'bottom',
            data:[]
//                ['运行中','创建中','已关机','故障','已挂起']
        },
        grid:{
            backgroundColor:'white'
        },
        series: [
            {
                name:'正常运行',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: false,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[]
            }
        ],
        // color1:['#5078f1','#fe9702','#dbdadc','#ffda36','#dc3b1a']
        color:['#5078f1','#fe9702','#dbdadc','#dc3b1a','#ffda36']

    };

    myChart10.setOption(option10);
    myChart10.on('click',function(e){
        window.location.href="vm/presearchvm";
    })
    myChart10.showLoading({
        text:'数据加载中...',
        x:"left",
        y:"center",
        color:'#c23531',
        textColor:'#000000',
        textStyle:{
            fontSize:'20',
            align:'center',
            verticalAlign:'center'
        },
        maskColor:'rgba(255,255,255,0)',
        zlevel:0
    });
    $(window).resize(function() {
        myChart10.resize();
    });
    var names=[];
    var brower=[];
    $.ajax({
        type: "post",
        url: "vm/countCloudClient",
        data: {},
        datatype: "json",
        success: function (result) {
            result = eval('('+result+')');
            var datal = ['运行中','创建中','已关机','故障','已挂起']

            var brower = [];
            brower.push({value:result.active,name:'运行中'});
            brower.push({value:result.building,name:'创建中'});
            brower.push({value:result.stopped,name:'已关机'});
            brower.push({value:result.error,name:'故障'});
            brower.push({value:result.suspended,name:'已挂起'});
            var num = result.active+result.building+result.stopped+result.suspended;
            myChart10.hideLoading();
            myChart10.setOption({        //加载数据图表
                legend: {
                    data:datal
                },
                series: [{
                    data: brower
                }],
//                tooltip:{
//                    trigger: 'axis',
//                    formatter: function(params, ticket, callback){
//                        var num = result.active+result.building+result.stopped+result.error+result.suspended;
//                        var str =
//                            '<div style="text-align: center" id="toolTipBox"><p style="font-size:40px;margin:0px">'+
//                            num + '</p><p style="font-size:15px;margin:20px;color:black;">总数</p></div>'
//                        return str
//                    },
//                    position: function (point, params, dom, rect, size) {
//                        //size参数可以拿到提示框的outerWidth和outerheight，dom参数可以拿到提示框div节点。
//                        //console.log(dom)可以看到，提示框是用position定位
//                        var marginW = Math.ceil(size.contentSize[0]/2);
//                        var marginH = Math.ceil(size.contentSize[1]/2);
//                        dom.style.marginLeft='-' + marginW + 'px';
//                        dom.style.marginTop='-' + marginH + 'px';
//                        return ['50%', '55%'];
//                    },
//                    alwaysShowContent:true,
//                    backgroundColor:'#ffffff',	//设置提示框的背景色
//                    textStyle:{
//                        color:'#333'
//                    }
//                },
                tooltip: {
                    trigger: 'item',
                    formatter: "<br/>{b}: {c} ({d}%)"
                },
                title:{
                    subtext:''+num,
                    subtextStyle:{
                        fontSize:45,
                        color:'#000000'
                    }
//                    textStyle: {//主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
//                        fontFamily: 'Arial, Verdana, sans...',
//                        fontSize: 20,
//                    },
//                    subtextStyle: {//副标题文本样式{"color": "#aaa"}
//                        fontFamily: 'Arial, Verdana, sans...',
//                        fontSize: 45,
//                        fontStyle: 'normal',
//                        fontWeight: 'normal',
//                    },
                }

            });
        }
    });
</script>
<%--<script src="js/system/home1.js"></script>--%>
<script type="text/javascript">
    var hd = document.getElementById('hd');
    var myChart11 = echarts.init(hd);

    option11 = {
        backgroundColor:'#fdfdfd',
        title:{
            left:'center',
            top: '15px',
            text:'云硬盘\n\n\n\n\n',
            itemGap:10,
            subtext:''
        },
        tooltip: {
            trigger: 'item',
            formatter: "<br/>{b}: {c} ({d}%)"
        },
        legend: {
            show: true,
//            itemWidth: 20,
//            itemHeight: 10,
//            itemGap: 8,
            orient: 'vertical',
            x: 'right',
            y:'bottom',
            data:[]
//                ['正常','创建中']
        },
        grid:{
            backgroundColor:'white'
        },
        series: [
            {
                name:'正常运行',
                type:'pie',
                radius: ['69%', '93%'],
                avoidLabelOverlap: false,
                hoverOffset: 5,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: false,
                        textStyle: {
                            fontSize: '18',
                            fontWeight: 'bold'
                        },
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[]
            }
        ],
        color:['#5078f1','#dbdadc']
    };
    myChart11.on('click',function(e){
        window.location.href="img/imglist";
    })
    myChart11.setOption(option11);
    myChart11.showLoading({
        text:'数据加载中...',
        x:"left",
        y:"center",
        color:'#c23531',
        textColor:'#000000',
        textStyle:{

        },
        maskColor:'rgba(255,255,255,0)',
    });
    $(window).resize(function() {
        myChart11.resize();
    });
    $.ajax({
        type: "post",
        url: "hd/counthd",
        data: {},
        datatype: "json",
        success: function (result) {console.log(result)
            result = eval('('+result+')');
            var brower = [];
            var data0 = ['已挂载','未挂载'];
            var num = result.total;
            brower.push({value:result.available,name:'已挂载'});
            brower.push({value:result.total-result.available,name:'未挂载'});
            myChart11.hideLoading();
            myChart11.setOption({        //加载数据图表
                legend: {
                    data:data0
                },
                series: [{
                    data: brower
                }],
                title:{
                    subtext:''+num,
                    subtextStyle: {
                        fontSize: 40,
                        color: '#000000'
                    }
                }
            });
        }
    });

</script>
<script type="text/javascript">
    var img = document.getElementById('img');
    var myChart12 = echarts.init(img);

    option12 = {
        backgroundColor:'#fdfdfd',
        title:{
            left:'center',
            top: '15px',
            text:'云模板\n\n\n\n\n',
            itemGap:10,
            subtext:''
        },
        tooltip: {
            trigger: 'item',
            formatter: "<br/>{b}: {c} ({d}%)"
        },
        legend: {
            show: true,
//            itemWidth: 20,
//            itemHeight: 10,
//            itemGap: 8,
            orient: 'vertical',
            x: 'right',
            y:'bottom',
            data:[]
//                ['正常','创建中']
        },
        grid:{
            backgroundColor:'white'
        },
        series: [
            {
                name:'正常运行',
                type:'pie',
                radius: ['69%', '93%'],
                avoidLabelOverlap: false,
                hoverOffset: 5,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: false,
                        textStyle: {
                            fontSize: '18',
                            fontWeight: 'bold'
                        },
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[]
            }
        ],
        color:['#5078f1','#dbdadc']
    };

    myChart12.setOption(option12);
    $(window).resize(function() {
        myChart12.resize();
    });

    myChart12.on('click',function(e){
        window.location.href="hd/hdmanage";
    })
    myChart12.showLoading({
        text:'数据加载中...',
        x:"left",
        y:"center",
        color:'#c23531',
        textColor:'#000000',
        textStyle:{
            fontSize:20,
        },
        maskColor:'rgba(255,255,255,0)',
        zlevel:0
    });
    $.ajax({
        type: "post",
        url: "img/countimg",
        data: {},
        datatype: "json",
        success: function (result) {
            result = eval('('+result+')');
            var brower = [];
            var data0 = ['正常','创建中'];
            var num = result.available+result.creating;
            brower.push({value:result.available,name:'正常'});
            brower.push({value:result.creating,name:'创建中'});
            myChart12.hideLoading();
            myChart12.setOption({        //加载数据图表
                legend: {
                    data:data0
                },
                series: [{
                    data: brower
                }],
                title:{
                    subtext:''+num,
                    subtextStyle: {
                        fontSize: 40,
                        color: '#000000'
                    }
                }
            });
        }
    });


</script>

<%--<s:include value="/template/_common_js.jsp" />--%>
<%--平台状态折线图--%>
<script type="text/javascript">
//    var timeLine=new Array();
//    var healthList=new Array();
//    var physicalHealthList=new Array();
//    var serviceHealthList=new Array();
    var containerCloud = document.getElementById('healthTable');
    //    var containerService = document.getElementById('service');
    //    var containerPhysical = document.getElementById('physical');

    //基于准备好的dom，初始化echarts实例
    var myChartCloud = echarts.init(containerCloud);
    //    var myChartService = echarts.init(containerService);
    //    var myChartPhysical = echarts.init(containerPhysical);

    var timeLine = <s:property value="timeLine"/>;
    var healthList = <s:property value="health"/>;
    var physicalHealthList = <s:property value="physicalHealth"/>;
    var serviceHealthList = <s:property value="serviceHealth"/>;

    var colors =['#5078f1', '#fe9702', '#dc3b1a'];
    optionCloud = {
        color: colors,
        title:{
            left:'center',
            top:"20px",
            text:'近30天平台整体运行情况',
            textStyle: {
                fontStyle: "normal",
                fontWeight: 'normal',
                fontFamily: "sans-serif",
                fontSize: 15
            }
        },
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '80px',
            right: '110px',
            bottom: '15%',
            containLabel: true
        },
        legend: {
            data:['平台','基础设施','服务'],
            x:'right',
            top:'15',
            selected:{
                '平台': true,
                '基础设施': false,
                '服务': false
            }
        },

        xAxis : [
            {
                type : 'category',
                data : timeLine,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series : [
            {
                name:'平台',
                type:'line',
                data: healthList
            },
            {
                name:'基础设施',
                type:'line',
                data: physicalHealthList
            },
            {
                name:'服务',
                type:'line',
                data:serviceHealthList
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChartCloud.setOption(optionCloud);
    //    myChartPhysical.setOption(optionPhysical);
    //    myChartService.setOption(optionService);
//    myChartCloud.showLoading({
//        text:'数据加载中...',
//        x:"left",
//        y:"center",
//        color:'#c23531',
//        textColor:'#000000',
//        textStyle:{
//            fontSize:20,
//        },
//        maskColor:'rgba(255,255,255,0)',
//        zlevel:0
//    });
//    $.get('system/getInfrastructureInfo',{},function(data){
//         timeLine = data.timeLine;
//         healthList = data.health;
//         physicalHealthList = data.physicalHealth;
//         serviceHealthList = data.serviceHealth;
//         $("#healthgrade").html(data.lastHealth);
//         myChartCloud.hideLoading();
//         myChartCloud.setOption(optionCloud,true);
//    })
    $(window).resize(function() {
        myChartCloud.resize();
//        myChartPhysical.resize();
//        myChartService.resize();
    });
</script>

<%--基础设施热力图--%>
<script type="text/javascript">
    var hostids=[];
    var hosts=[];
    var originaldata=[];
    var hostuuids=[];
//    var map=new Map();
    dataAll=new Array();
    findIP();
    var hours = ['CPU使用率','内存占用率','内存交换率','磁盘占用率','磁盘坏道数','网络接收','网络发出'];
    var types=['cpu','memUsed','memSwap','diskUsed','diskErrorCount','netDownload','netUpload'];
    var days=hostids;
    //findData();
    var data0 = [];
    data0=dataAll;
    var host1 = echarts.init(document.getElementById('host1'));



    option13 = {
        tooltip: {
            position: 'top'
        },
        animation: false,
        grid: {
            height: '70%',
            y: '5%'
        },
        xAxis: {
            type: 'category',
            data: hours,
            splitArea: {
                show: true
            }
        },
        yAxis: {
            type: 'category',
            data: days,
            splitArea: {
                show: true
            },
            max:'10'
        },
        visualMap: {
            min: 0,
            max: 10,
            calculable: true,
            orient: 'horizontal',
            left: 'center',
            bottom: '0%',
            color:['#dc3b1a','#f6efa6']
        },mytextstyle:{
            color:'#020202'
        },
        series: [{
            name: 'Punch Card',
            type: 'heatmap',
            data: data0,
            label: {
                normal: {
                    show: false
                }
            },
            tooltip:{
                trigger:'item',
                formatter:function(params){
                    var str=hostids[params.value[1]]+'<br/>'+ hours[params.value[0]]+': '+originaldata[params.value[1]][params.value[0]]
                        +'<br/>健康分数: '+params.value[2];
                    ;
                    return str;
                }
            },
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: 'rgba(0,0,0, 0.5)'
                }
            }
        }]

    };
    host1.setOption(option13);
    host1.showLoading({
        text:'数据加载中...',
        x:"left",
        y:"center",
        color:'#c23531',
        textColor:'#000000',
        textStyle:{
            fontSize:20,
        },
        maskColor:'rgba(255,255,255,0)',
        zlevel:0
    });

    host1.on('click',function(e){
        var uuid=hosts[e.value[1]];
        var type=types[e.value[0]];
        window.location.href="system/nodeDetail?uuid="+uuid+"&button=3&type="+type;
    });
    function findIP(){
        var num = 0;
        // $.ajaxSettings.async=false;
        $.ajax({
            type: "post",
            url: "system/findAllNode",
            data: {
            },
            success: function (data) {
                for(i=0;i<data.hostList.length;i++){
//                    if(data.vmlist[i].type == "SERVICE" || data.vmlist[i].type == "UNCONTROLLEDVM"){
                    hostids[num]=data.hostList[i].name;
                    hosts[num]=data.hostList[i].uuid;
                    num=num+1;
//                    }
                }

                showHostDetailInfo(hosts);
            }
        });
    }

    function showHostDetailInfo(uuids) {


        //
        // for(i=0;i<uuids.length;i++){
        //     if(i<(uuids.length-1))
        //         str=str+uuids[i]+',';
        //     else str=str+uuids[i];
        //
        // }
        var counter = 0;
        var uuidtem;
        var hostNum = uuids.length;
        var url = 'system/findState';
        // $.ajaxSettings.async=false;
        for (i = 0; i < uuids.length; i++) {
            uuidtem = uuids[i];
            $.post(url, {
                uuidOne: uuidtem,
            }, function (data) {
                var index=uuids.indexOf(data.uuidOne);
                originaldata[index] = [];
                var datas = [];
                datas[0] = data.mapHealth['cpuUsed'];
                datas[1] = data.mapHealth['memUsed'];
                datas[2] = data.mapHealth['memSwap'];
                datas[3] = data.mapHealth['diskUsed'];
                datas[4] = data.mapHealth['diskErrorCount'];
                datas[5] = data.mapHealth['netDownload'];
                datas[6] = data.mapHealth['netUpload'];
                for (count=0;count<7;count++) {
                    if (!datas[count]) datas[count]="0";
                }

                originaldata[index][0] = data.mapMetrics['cpuUsed'];
                originaldata[index][1] = data.mapMetrics['memUsed'];
                originaldata[index][2] = data.mapMetrics['memSwap'];
                originaldata[index][3] = data.mapMetrics['diskUsed'];
                originaldata[index][4] = data.mapMetrics['diskErrorCount'];
                originaldata[index][5] = data.mapMetrics['netDownload'];
                originaldata[index][6] = data.mapMetrics['netUpload'];

                for (k = 0; k < 7; k++) {
                    var a = [k, index, datas[k]];
                    dataAll.push(a);

                }
                counter++;
                if (counter == hostNum) {
                    host1.hideLoading();
                    host1.setOption(option13, true);
                }

            });
        }

    }


    function findData(){
        var type="second";
        var url = "system/findState";
        var datas=[];
        dataAll=new Array();
        // for(j=0;j<hosts.length;j++){
        //     originaldata[j]=[];
        //     var hostid=hosts[j];
        $.ajaxSettings.async=false;
        $.post(url, {type: type, hostName: hostid   }, function (data) {

            <%--var m = JSON.parse('${data}');--%>
            //console.log(data.cpuPercent);
            // datas[0]=(data.cpuPercent[0]/10.).toFixed(2);
            // datas[1]=(data.memPercent[0]/10).toFixed(2);
            // if(isNaN(data.diskPercent[0])) {
            //     datas[2] = 0;
            // }else{
            //     datas[2]=(data.diskPercent[0]/10).toFixed(2);
            // }
            // datas[3]=(data.loadaverage[0]*20).toFixed(2);
            // datas[4]=0;
            // datas[5]=(data.netInPercent[0]/20).toFixed(2);
            // datas[6]=(data.netOutPercent[0]/20).toFixed(2);
            // originaldata[j][0]=(data.cpuPercent[0]).toFixed(2);
            // originaldata[j][1]=(data.memPercent[0]).toFixed(2);
            // if(isNaN(data.diskPercent[0])) {
            //     originaldata[j][2] = 0;
            // }else{
            //     originaldata[j][2]=(data.diskPercent[0]).toFixed(2);
            // }
            // originaldata[j][3]=(data.loadaverage[0]).toFixed(2);
            // originaldata[j][4]=0;
            // originaldata[j][5]=(data.netInPercent[0]).toFixed(2);
            // originaldata[j][6]=(data.netOutPercent[0]).toFixed(2);
        });

        for(k=0;k<7;k++){
            var a=[k,j,datas[k]];
            dataAll.push(a);
        }

        // }


    }

</script>
<script src="js/system/home.js"></script>
</body>
</html>


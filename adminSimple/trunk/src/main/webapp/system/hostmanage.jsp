<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/4/9
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants,appcloud.admin.action.system.VmSummaryAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>基础设施管理 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=system" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
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
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>

</body>
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
    //
    ////按钮组控制显示类型
    //  // var service = document.getElementsByName("服务器");
    //   var diskdevice = document.getElementsByName("存储");
    //   var netdevice = document.getElementsByName("网络设备");
    //   var uncontrolledvm = document.getElementsByName("管理节点");
    //   var elsedevice = document.getElementsByName("服务器");
    //
    //   showHosts();
    //    //document.getElementById("allbox").click();
    ////
    //   function disAll() {
    ////       for(var i = 0; i<service.length;i++){
    ////           $(service[i]).addClass("hidden");
    ////       }
    //       for(var i = 0; i<diskdevice.length;i++){
    //           $(diskdevice[i]).addClass("hidden");
    //       }
    //       for(var i = 0; i<netdevice.length;i++){
    //           $(netdevice[i]).addClass("hidden");
    //       }
    //       for(var i = 0; i<uncontrolledvm.length;i++){
    //           $(uncontrolledvm[i]).addClass("hidden");
    //       }
    //       for(var i = 0; i<elsedevice.length;i++){
    //           $(elsedevice[i]).addClass("hidden");
    //       }
    //   }
    //
    //   function serviceShow() {
    //       for(var i = 0; i<service.length;i++){
    //           $(service[i]).removeClass("hidden");
    //       }
    //   }
    //   function diskShow() {
    //       for(var i = 0; i<diskdevice.length;i++){
    //           $(diskdevice[i]).removeClass("hidden");
    //       }
    //   }
    //   function netShow() {
    //       for(var i = 0; i<netdevice.length;i++){
    //           $(netdevice[i]).removeClass("hidden");
    //       }
    //   }
    //   function unvmShow() {
    //       for(var i = 0; i<uncontrolledvm.length;i++){
    //           $(uncontrolledvm[i]).removeClass("hidden");
    //       }
    //   }
    //   function elseShow() {
    //       if(elsedevice!=undefined){
    //       for(var i = 0; i<elsedevice.length;i++){
    //           $(elsedevice[i]).removeClass("hidden");
    //       }
    //       }
    //   }
    //   function showAll() {
    //       //serviceShow();
    //       diskShow();netShow();unvmShow();elseShow();
    //       hidecount();
    //       num=$('#infoTable').find('tr').length-$("#infoTable").find('.hidden').length-1;
    //       var str="("+num+")";
    //       $('#all').html(str);
    //
    //   }
    //   function showHosts() {
    //      // disAll();
    //       $('#buildService').addClass("hide");
    //       hidecount();
    //       $('#vmsummaryTable').addClass('hide');
    //       $('#nodeTable').removeClass('hide');
    //       getPage(1);
    //
    //   }
    //   function getPage(page){
    //       $.get('system/SearchAllNode',{
    //           page:page-1
    //       },function(data){
    //           $('#nodeTable').html(data);
    //           num=$('#nodeTable').find('tr').length-$("#nodeTable").find('.hide').length-1;
    //           var str="("+num+")";
    //           $('#servicecount').html(str);
    ////           if(refreshFlag!=1) {
    //               refreshFlag = 1;
    //               showHostDetailInfo();
    ////           }
    //       })
    //   }
    //   function showDisks() {
    //       $('#buildService').removeClass("hide");
    //       $('#nodeTable').addClass('hide');
    //       $('#vmsummaryTable').removeClass('hide');
    //       disAll();
    //       diskShow();
    //       hidecount();
    //       num=$('#infoTable').find('tr').length-$("#infoTable").find('.hidden').length-1;
    //       var str="("+num+")";
    //       $('#diskcount').html(str);
    //
    //   }
    //   function showNets() {
    //       $('#buildService').removeClass("hide");
    //       $('#nodeTable').addClass('hide');
    //       $('#vmsummaryTable').removeClass('hide');
    //       disAll();
    //       netShow();
    //       hidecount();
    //       num=$('#infoTable').find('tr').length-$("#infoTable").find('.hidden').length-1;
    //       var str="("+num+")";
    //       $('#netcount').html(str);
    //   }
    //   function showUnvm() {
    //       $('#buildService').addClass("hide");
    //       hidecount();
    //       $('#vmsummaryTable').addClass('hide');
    //       $('#nodeTable').removeClass('hide');
    //       disAll();
    //       $("tr[name='管理结点']").each(function(){
    //           $(this).removeClass('hide');
    //       });
    //       $("tr[name='计算节点']").each(function(){
    //           $(this).addClass('hide');
    //       })
    //       num=$('#nodeTable').find('tr').length-$("#nodeTable").find('.hide').length-1;
    //       var str="("+num+")";
    //       $('#uncontrolcount').html(str);
    //   }
    //   function showElse() {
    //       $('#buildService').removeClass("hide");
    //       $('#nodeTable').addClass('hide');
    //       $('#vmsummaryTable').removeClass('hide');
    //       disAll();
    //       elseShow();
    //       hidecount();
    //       num=$('#infoTable').find('tr').length-$("#infoTable").find('.hidden').length-1;
    //       var str="("+num+")";
    //       $('#elsecount').html(str);
    //   }
</script>
</html>
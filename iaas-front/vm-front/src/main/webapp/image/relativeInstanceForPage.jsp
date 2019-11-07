<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="com.appcloud.vm.common.Constants" pageEncoding="UTF-8" %>
<select class="hidden" name="prov" id="selectAppname"><s:property value="appname"></s:property></select>
<div id="show_data_area">
    <div class="panel panel-default front-panel" id="back_table">
        <div class="panel-body front-no-padding">
            <table class="table table-striped front-table"
                   style="margin-bottom: 0px;font-size: 15px" id="serverList">
                <thead style="vertical-align: bottom;border-bottom: 2px solid #ddd;">
                    <th class="col-sm-1">云主机名称</th>
                    <th class="col-sm-1">提供商</th>
                    <th class="col-sm-2" style="padding-left:10px">可用区</th>
                    <th class="col-sm-1">状态</th>
                    <th class="col-sm-1">操作系统</th>
                    <th class="col-sm-3" class="vm_configure" style="padding-left:30px">配置</th>
                    <th class="col-sm-1" style="padding-right: 20px">IP地址</th>
                    <th class="col-sm-1" style="padding: 8px 0">付费方式</th>
                    <th class="col-sm-1" style="text-align:right;">操作</th>
                </thead>
                <s:iterator id="instance" value="result">
                    <tr>
                        <td class="col-sm-1">
                            <div>
                                <div>
                                    <label id="serverName"><s:property value="#instance.instanceName"/></label>
                                    <a data-toggle="front-modal" data-title="修改云主机的名称"
                                       data-href="template/rename.jsp"><span
                                            class="glyphicon glyphicon-edit"></span></a>
                                </div>
                            </div>
                        </td>
                        <td class="col-sm-1" id="serverProvider"><s:property value="#instance.provider"/></td>
                        <td class="col-sm-2" style="padding-left:10px">
                            <label id="serverZone"><s:property value="#instance.region"/>-<s:property
                                    value="#instance.zoneId"/></label>
                        </td>
                        <td class="col-sm-1">
                            <div>
                                <img id='<s:property value="#instance.instanceId"/>img'
                                     src="images/<s:property value='#instance.status'/>.png"/>
                                <label id='<s:property value="#instance.instanceId"/>label'><s:property
                                        value='#instance.statusCn'/></label>
                            </div>
                        </td>
                        <td class="col-sm-1" id="serverOsType"><s:property value="#instance.osType"/></td>
                        <td class="col-sm-3" class="vm_configure" style="padding-left:30px">
                            <div style="display: inline-block">
                                <label>CPU：</label>
                                <label id="serverVcpus"><s:property value="#instance.vcpus"/>核</label>
                                <label style="width: 10px"></label>
                                <label>内存：</label>
                                <label id="serverMem"><s:property value="#instance.memory"/>G</label>
                                <a href="#"><span class="glyphicon glyphicon-edit"></span></a>
                            </div>
                            <br/>
                            <div style="display: inline-block;">
                                <label>带宽：</label>
                                <label id="serverBand"><s:property value="#instance.bandwidth"/>Mbps </label>
                                <a href="#"><span class="glyphicon glyphicon-edit"></span></a>
                            </div>
                        </td>
                        <td class="col-sm-1" style="padding-right: 20px">
                            <div>
                                <div style="display: inline-block" id="publicIp"><s:property
                                        value="#instance.publicIpAddress"/>(公)
                                </div>
                                <br/>
                                <div style="display: inline-block" id="privateIp"><s:property
                                        value="#instance.privateIpAddress"/>(内)
                                </div>
                            </div>
                        </td>
                        <td class="col-sm-1" style="padding: 8px 0">
                            <label id="serverPayType"><s:property value="#instance.payType"/></label><br/>
                            <label id="serverEndTime"><s:property value="#instance.endTime"/>到期</label>
                        </td>
                        <td class="col-sm-1" style="text-align: right">
                            <div class="btn-group">
                                <a href="javascript:void(0)"
                                   onclick='start("<s:property value="#instance.status"/>","<s:property value='#instance.instanceId'/>",
                                           "<s:property value="#instance.userEmail"/>",
                                           "<s:property value='#instance.regionId'/>","<s:property value="#instance.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>启动</a>
                                <a href="javascript:void(0)"
                                   onclick='stop("<s:property value="#instance.status"/>","<s:property
                                           value="#instance.instanceId"/>","<s:property value="#instance.userEmail"/>",
                                           "<s:property value="#instance.regionId"/>",<%=Constants.DEFAULT_CHECKTIME%>);'
                                   class="vmlist_op_dis">关闭</a><br/>
                                <a href="javascript:void(0)"
                                   onclick='reboot("<s:property value="#instance.status"/>","<s:property
                                           value="#instance.instanceId"/>","<s:property value="#instance.userEmail"/>",
                                           "<s:property value="#instance.regionId"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>重启</a>
                                <a href='vm/vmdetail?provider=<s:property value="#instance.providerEn"/>&appname=<s:property value="#instance.appname"></s:property>&regionId=<s:property value="#instance.regionId"/>&instanceId=<s:property value="#instance.instanceId"/>&userEmail=<s:property value="#instance.userEmail"/>'
                                   title="管理" class="vmlist_op_dis">管理</a>
                            </div>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </div>
</div>
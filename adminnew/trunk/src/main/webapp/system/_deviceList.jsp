<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants,appcloud.admin.action.system.VmSummaryAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" id="deviceCount" value="<s:property value="count"/>"/>
<table id="infoTable" class="table table-striped front-table table-bordered table-hover" style="margin-bottom: 0px">
    <thead>
    <tr>
        <th class="col-md-1" style="text-align: center">名称</th>
        <th class="col-md-2" style="text-align: center">UUID</th>
        <th class="col-md-1" style="text-align: center">集群</th>

        <th class="col-md-2" style="text-align: center">配置</th>
        <th class="col-md-3" style="text-align: center">运行状态</th>
        <%--<th class="col-md-1">位置</th>--%>
        <th class="col-md-2" style="text-align: center">备注</th>
        <th class="col-md-1" style="text-align: center">操作</th>
    </tr>
    </thead>
    <div id="allHosts">
        <s:if test="vmlist.size>0">
            <s:iterator value="vmlist" id="vmlist">
                <s:if test='#vmlist.type.toString()!="非受控虚拟机"'>
                    <tr id="<s:property value="#vmlist.uuid"/>" data-uuid="<s:property value="#vmlist.uuid"/>" class="host" name="<s:property value="#vmlist.type"/>">
                        <td style="vertical-align: middle;overflow: auto"><s:property value="#vmlist.name"/></td>
                        <td style="vertical-align: middle;overflow: auto"><s:property value="#vmlist.uuid"/></td>
                            <%--<td style="vertical-align: middle; text-align: center;overflow: auto">非受控<br>虚拟机</td>--%>
                        <td style="vertical-align: middle; text-align: center;overflow: auto" class="Nodetype"></td>

                        <td style="vertical-align: middle; overflow: auto"><s:property value="#vmlist.model" escape="false"/></td>
                        <td style="vertical-align: middle;padding: 3px;">
                                <%--<div style="width: 50%;float: left;vertical-align: middle">--%>
                                <%--<div>--%>
                                <%--<div style="margin: 0px;font-size: 12px;">CPU使用率:&nbsp;<span id="cpuUsed<s:property value="#vmlist.uuid"/>"></span>%</div>--%>
                                <%--&lt;%&ndash;<div class="progress" style="margin-bottom: 2px;margin-top: 2px">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<span class="green" style="width: 20%;"><span>20%</span></span>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                <%--<span style=><p style="margin: 0px;font-size: 12px;">内存占用率:&nbsp;<span id="memUsed<s:property value="#vmlist.uuid"/>"></span>%</p></span>--%>
                                <%--&lt;%&ndash;<div class="progress" style="margin-bottom: 5px;margin-top: 2px">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<span class="red" style="width: 80%;"><span>80%</span></span>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                <%--<div style="margin: 0px;font-size: 12px;">内存交换率:&nbsp;<span id="memSwap<s:property value="#vmlist.uuid"/>"></span>%</div>--%>
                                <%--</div>--%>
                                <%--<div style="margin-bottom: 0px;">--%>
                                <%--<div style="font-size: 12px">硬盘占用率:&nbsp;<span id="diskUsed<s:property value="#vmlist.uuid"/>"></span>%</div>--%>
                                <%--</div>--%>

                                <%--</div>--%>
                                <%--<div style="width: 50%;float: right;vertical-align: middle">--%>

                                <%--<div>--%>
                                <%--<span style=><p style="margin: 0px;font-size: 12px;">磁盘坏道数:&nbsp;<span id="diskErrorCount<s:property value="#vmlist.uuid"/>"></span></p></span>--%>
                                <%--&lt;%&ndash;<div class="progress" style="margin-bottom: 5px;margin-top: 2px">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<span class="red" style="width: 80%;"><span>80%</span></span>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                                <%--</div>--%>
                                <%--<div style="margin-bottom: 0px;">--%>
                                <%--<div style="font-size: 12px">网络收:&nbsp;<span id="netDownload<s:property value="#vmlist.uuid"/>"></span>MB/S</div>--%>
                                <%--</div>--%>
                                <%--<div style="margin-bottom: 0px;">--%>
                                <%--<div style="font-size: 12px">网络发:&nbsp;<span id="netUpload<s:property value="#vmlist.uuid"/>"></span>MB/S</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>

                                <%--<div style="width: 50%;float: left;vertical-align: middle">--%>
                                <%--<span style="vertical-align: middle"><p>test</p></span>--%>
                                <%--</div>--%>
                        </td>
                        <td style="vertical-align: middle;overflow: auto"><s:property value="#vmlist.description" escape="false"/></td>
                        <td style="vertical-align:middle;text-align: center;overflow:auto">
                            <div style="padding-left: 1px"><a href="javascript:void(0)" data-uuid="<s:property value="#vmlist.uuid"/>" onclick="deleteService(<s:property value='#vmlist.id'/>,this)">删除</a>&nbsp;
                                <a href="javascript:void(0)" data-uuid="<s:property value="#vmlist.uuid"/>" onclick="showEditModal(<s:property value='#vmlist.id'/>,this)">修改</a></div>
                                <%--<a href="javascript:void(0)"   onclick="showVmSummaryInfo(<s:property value="#vmlist.uuid"/>)">详情</a>--%>
                                <%--<a href="javascript:void(0)" data-uuid="<s:property value="#vmlist.uuid"/>" onclick="showVmSummaryInfo(this)">详情</a></td>--%>
                            <div  style="height:3px"></div>
                                <%--<s:if test='#vmlist.type.toString()=="服务器"'>--%>
                                <%--<a href="system/vmSummaryInfo?uuid=<s:property value='#vmlist.uuid'/>&type=其他设备">详情</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>--%>
                                <%--</s:if>--%>
                                <%--<s:else>--%>
                                <%--<a href="system/vmSummaryInfo?uuid=<s:property value='#vmlist.uuid'/>&type=<s:property value='#vmlist.type'/>">详情</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>--%>
                                <%--<div>详情</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
                        </td>
                            <%--</s:else>--%>
                            <%--</td>--%>
                    </tr>
                </s:if>
            </s:iterator>
        </s:if>
    </div>
</table>

<%--
  Created by IntelliJ IDEA.
  User: bxt
  Date: 2016/7/31
  Time: 9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="hd-total-page" value='<s:property value="totalPage"/>'/>
<div id = "show_data_area">
    <!--全部云硬盘信息-->
    <div class="panel panel-default front-panel" id="hd_table">
        <div class="panel-body front-no-padding">
            <table class="table table-striped front-table"
                   style="margin-bottom: 0px;font-size: 15px">
                <thead style="vertical-align: bottom;
                                border-bottom: 2px solid #ddd;">
                    <th class="col-sm-2">云硬盘名称</th>
                    <th class="col-sm-1">提供商</th>
                    <th class="col-sm-1">可用区</th>
                    <th class="col-sm-2">挂载状态</th>
                    <th class="col-sm-2">硬盘种类</th>
                    <th class="col-sm-1">硬盘属性</th>
                    <th class="col-sm-1" style="padding:8px 0">付费方式</th>
                    <th class="col-sm-1">可卸载</th>
                    <th class="col-sm-1" style="text-align: right">操作</th>
                </thead>
                <s:iterator id="server" value="diskServers">
                    <tr>
                        <td class="col-sm-2">
                            <div>
                                <div>
                                    <label id="<s:property value="#server.diskId"/>"><s:property value="#server.diskName"/></label>
                                   <%-- <a data-toggle="front-modal" data-title="修改云硬盘的名称"
                                       data-href="vm/preeditvm?yunType=hd&target=<s:property value="#server.diskId"/>&appname=<s:property value="appName"/>&id=<s:property value="#server.diskId"/>&name=<s:property value="#server.diskName"/>&type=name&regionId=<s:property value="#server.regionId"/>">
                                        <span class="glyphicon glyphicon-edit"></span></a>--%>
                                </div>
                            </div>
                        </td>
                        <td class="col-sm-1"><s:property value="#server.provider"/></td>
                        <td class="col-sm-1"><s:property value="#server.region"/>-<s:property value="#server.zoneId"/></td>
                        <td class="col-sm-2">
                            <div>
                                <label><s:property value="#server.attachStatusCn"/></label>
                                <label><s:property value="#server.instanceName"/></label>
                            </div>
                        </td>
                        <td class="col-sm-2">
                            <label><s:property value="#server.diskType"/></label>
                            <label>(<s:property value="#server.size"/>G)</label>
                        </td>
                        <td class="col-sm-1"><s:property value="#server.diskCategory"/></td>
                        <td class="col-sm-1" style="padding: 8px 0">
                            <label><s:property value="#server.payType"/></label><br/>
                            <label><s:property value="#server.endTime"/>到期</label>
                        </td>
                        <td class="col-sm-1">支持</td>
                        <td class="col-sm-1" style="text-align: right">
                            <div class="btn-group">
                                <a href='hd/hddetail?userId=<s:property value="userId"/>&appName=<s:property value="appName"/>&diskId=<s:property value="#server.diskId"/>&provider=<s:property value="#server.providerEn"/>&regionId=<s:property value="#server.regionId"/>&userEmail=<s:property value="#server.userEmail"/>'>管理</a><br/>
                                <s:if test='#server.attachStatus=="attached"'>
                                    <a href="javascript:void(0)" onclick='modalValue("<s:property value='#server.diskId'/>","<s:property value='#server.providerEn'/>","<s:property value='#server.regionId'/>","<s:property value='#server.userEmail'/>","<s:property value="userId"/>","<s:property value="appName"/>")'>创建快照</a>
                                </s:if>
                            </div>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </div>

</div>

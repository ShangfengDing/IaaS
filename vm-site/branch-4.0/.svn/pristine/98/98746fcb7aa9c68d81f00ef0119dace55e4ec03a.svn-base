<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/30
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="shot-total-page" value='<s:property value="totalPage"/>'/>
<div>
    <div>
        <div id="show_data_area">
            <!--云快照信息-->
            <div class="panel panel-default front-panel" id="back_table">
                <div class="panel-body front-no-padding">
                    <table class="table table-striped front-table"
                           style="margin-bottom: 0px;font-size: 15px">
                        <thead>
                            <tr>
                                <th class="col-sm-2">名称</th>
                                <th class="col-sm-2">硬盘名称</th>
                                <th class="col-sm-1">硬盘容量</th>
                                <th class="col-sm-1">硬盘属性</th>
                                <th class="col-sm-2">创建时间</th>
                                <th class="col-sm-1">状态</th>
                                <th class="col-sm-3" style="text-align:right;">操作</th>
                            </tr>
                        </thead>
                        <s:iterator id="shotServer" value="shotServers">
                            <tr id="tr<s:property value="#shotServer.shotId"/>">
                                <td class="col-sm-2">
                                    <div>
                                        <label id="mainName"><s:property value="#shotServer.shotName"/></label>
                                        <%-- <a data-toggle="front-modal" data-title="修改云快照的名称"
                                           data-href="template/rename.jsp"><span
                                                class="glyphicon glyphicon-edit"></span></a> --%>
                                    </div>
                                </td>
                                <td class="col-sm-2">
                                    <label><s:property value="#shotServer.diskName"/></label>
                                </td>
                                <td class="col-sm-1">
                                    <label><s:property value="#shotServer.shotSize"/>G</label>
                                </td>
                                <td class="col-sm-1">
                                    <label><s:property value="#shotServer.diskType"/></label>
                                </td>
                                <td class="col-sm-2">
                                    <div style="display: inline-block">
                                        <label><s:property value="#shotServer.createTime"/></label>
                                    </div>
                                </td>
                                <td class="col-sm-1">
                                    <label><s:property value="#shotServer.shotStatus"/></label>
                                </td>
                                <td class="col-sm-2" style="text-align: right">
                                    <div>
                                        <div>
                                            <a href="javascript:void(0)"
                                               onclick="rollback('rollback','<s:property
                                                       value="#shotServer.shotId"/>','<s:property
                                                       value="#shotServer.instanceName"/>','<s:property
                                                       value="#shotServer.providerEn"/>','<s:property
                                                       value="#shotServer.userEmail"/>','<s:property
                                                       value="#shotServer.regionId"/>','<s:property
                                                       value="#shotServer.diskId"/>','<s:property
                                                       value="#shotServer.appName"/>')">回滚磁盘</a>
                                            <a href="javascript:void(0)"
                                               onclick="rollback('delete','<s:property
                                                    value="#shotServer.shotId"/>','<s:property
                                                    value="#shotServer.instanceName"/>','<s:property
                                                    value="#shotServer.providerEn"/>','<s:property
                                                    value="#shotServer.userEmail"/>','<s:property
                                                    value="#shotServer.regionId"/>','<s:property
                                                    value="#shotServer.diskId"/>','<s:property
                                                    value="#shotServer.appName"/>')">删除快照</a>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </s:iterator>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="com.appcloud.vm.common.Constants"%>--%>
<input type="hidden" id="div_totalCount" value="<s:property value="TotalCount"/>" />
<input type="hidden" id="div_totalPage" value="<s:property value="totalPage"/>" />
<input type="hidden" id="div_result" value="<s:property value="result"/>" />
<s:if test="servers.size()!=0">
    <!-- 全部 -->

    <div class="form-group">
        <label class="col-md-1 text-left"
               style="font-weight:normal;padding-top: 17px">共<s:property value="total"/>条
        </label>
        <a href="javascript:void(0)" class="btn btn-primary col-md-offset-10 text-right" onclick="submitAll(0)">导出报表</a></div>
    <div class="panel panel-default front-panel" id="div_table">
        <div class="panel-body front-no-padding">
            <table class="table table-striped front-table"
                   style="margin-bottom: 0px;font-size: 15px;" id="serverList">
                <thead>
                <tr>
                    <th >用户邮箱/名称/UUID</th>

                    <th class="col-sm-1" style="text-align: center">数据中心</th>
                    <th class="col-sm-1" style="text-align: center">集群</th>
                    <th class="col-sm-1" style="text-align: center">服务器</th>
                    <th class="col-sm-1" style="text-align: center">私网IP</th>
                    <th class="col-sm-1" style="text-align: center">状态</th>
                    <th class="col-sm-2" style="text-align: center;">创建时间</th>
                    <th class="col-sm-1" style="text-align: center">操作</th>
                </tr>
                </thead>
                <s:iterator id="server" value="servers">
                    <tr>
                        <td style="vertical-align: middle;border-top: 1px solid #ddd;">
                            <div>
                                <div id="<s:property value="#server.instanceId"/>"><s:property
                                    value="emailMap[#server.tenantId]"/></div>
                            </div>
                            <div>
                                <s:property value="#server.name"/><br><s:property value="#server.id"/>
                            </div>
                        </td>

                        <td class="col-sm-1" style="vertical-align: middle;text-align: center"><s:property
                                value="zoneIdNameMap[#server.availabilityZone]"/></td>
                        <td class="col-sm-1" style="vertical-align: middle;text-align: center">
                            <div id='<s:property value="#server.instanceId"/>label'><s:property
                                    value='clusterIdNameMap[#server.aggregate]'/></div>
                        </td>
                        <td class="col-sm-1" style="vertical-align: middle;text-align: center">
                            <div id='<s:property value="#server.instanceId"/>label'><s:property
                                    value='hostIdNameMap[#server.hostId]'/></div>
                        </td>
                        <td class="col-sm-1" style="vertical-align: middle;text-align: center">
                                <div><s:property value=" privateIpMap[#server.id][0].addr"/></div>
                        </td>
                        <td class="col-sm-1" style="vertical-align: middle;text-align: center">
                            <div id="serverZone"><s:property value="statusMap[#server.status]"/></div>
                        </td>
                        <td class="col-sm-1" style="vertical-align: middle;text-align: center">
                            <div id="serverPayType"><s:property value="timeMap[#server.id]"/></div>
                            <%--<label id="serverEndTime"><s:property value="#server.endTime"/>到期</label>--%>
                        </td>

                        <td class="col-sm-1" style="vertical-align: middle;text-align: center">
                            <a href="vm/vmdetail?userId=<s:property value="#server.tenantId"/>&serverId=<s:property
                            value="#server.id"/>&clusterId=<s:property
                            value="clusterIdNameMap[#server.aggregate].substr(2)"/>&regionId=beijing">管理</a><br>
                            <a href="javascript:void(0)"
                               onclick="vmoperate('<s:property value="#server.tenantId"/>','forceDelete','<s:property
                                       value="#server.id"/>','<s:property value="vmEndTimeMap[#server.id]"/>')">强制删除</a>
                           <%-- <a href="javascript:void(0)" onclick="showDetail(this,'<s:property value="#server.tenantId"/>','<s:property value="#server.id"/>')">查看详情</a>  --%>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
    </div>
    <div class="lineheight" id="pageColumn" data-endPage="<s:property value='lastpage'/>"></div>
</s:if>
<s:else>
    <div class="panel panel-default front-panel">
        <div class="panel-body front-last-no-margin">
            无查询结果
        </div>
    </div>
</s:else>
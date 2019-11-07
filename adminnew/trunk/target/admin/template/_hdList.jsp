<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="com.appcloud.vm.common.Constants"%>--%>
<input type="hidden" id="div_totalCount" value="<s:property value="TotalCount"/>" />
<input type="hidden" id="div_totalPage" value="<s:property value="totalPage"/>" />
<input type="hidden" id="div_result" value="<s:property value="result"/>" />
<s:if test="total!=0">
    <!-- 全部 -->

    <div class="form-group">
        <label class="col-md-1 text-left"
               style="font-weight:normal;padding-top: 17px">共<s:property value="total"/>条
        </label>
        <a href="javascript:void(0)" class="btn btn-primary col-md-offset-10 text-right" onclick="submitAll(0)">导出报表</a></div>
    <div class='form-group'><div class=' panel panel-default front-panel' style='margin-top: 10px'>
    <div class='panel-body front-no-padding'>
        <table class='table table-striped front-table col-md-12' style='margin-bottom: 0px' id="querytable"><thead><tr>
            <th>名称</th>
            <th class='col-md-1' style="text-align: center">状态</th>
            <th class='col-md-1' style="text-align: center">容量</th>
            <th class='col-md-1' style="text-align: center">数据中心</th>
            <th class='col-md-2' style="text-align: center">挂载状态</th>
            <th class='col-md-2' style="text-align: center;">ID</th>
            <th class='col-md-2' style="text-align: center">创建时间</th>
            <th class='col-md-1' style="text-align: center">操作</th>
                </tr>
                </thead>
                <s:iterator id="volume" value="volumes">
                    <tr>
                        <td style="vertical-align: middle;border-top: 1px solid #ddd;">
                            <div>
                                <s:property value="#volume.displayName"/>
                            </div>
                        </td>
                        <td class="col-md-1" style="vertical-align: middle;text-align: center">
                                <s:property value="statusMap[#volume.status]"/>
                        </td>
                        <td class="col-md-1" style="vertical-align: middle;text-align: center;">
                            <s:property value="#volume.size"/>
                        </td>
                        <td class="col-md-1" style="vertical-align: middle;text-align: center">
                            <s:property value='zoneIdNameMap[#volume.availabilityZone]'/>
                        </td>
                        <td class="col-md-1" style="vertical-align: middle">
                            <s:if test="volumeIdServerNameMap[#volume.id]==\"无\"">
                                未挂载
                            </s:if>
                            <s:else>
                                挂载到:<a target="_blank" class="blueletter"
                                href="vm/vmdetail?serverId=<s:property
                                    value='#volume.attachments.serverId'/>"><s:property
                                    value='volumeIdServerNameMap[#volume.id]'/></a>
                            </s:else>
                        </td>
                        <td class="col-md-2" style="vertical-align: middle;text-align: center;"
                            onmouseover="showRealName(this,'<s:property value="#volume.id"/>')">
                            <s:if test="#volume.id.length()>16">
                                <s:property value="#volume.id.substring(0,15)"/>...
                            </s:if>
                            <s:else>
                                <s:property value="#volume.id"/>
                            </s:else>
                        </td>
                        <td class="col-md-1" style="vertical-align: middle;text-align: center">
                            <s:property value='timeMap[#volume.id]'/>
                        </td>
                        <td class="col-md-1" style="vertical-align: middle;text-align: center"><div>
                            <s:if test="volumeIdServerNameMap[#volume.id]==\"无\""> <s:if
                                test="#volume.status=='available'">
                                <a data-toggle="front-modal" class="blueletter"
                                   data-href="hd/showattachhd?hduuid=+<s:property value='#volume.id'/>&uid=<s:property
                                    value='uidMap[#volume.id]'/>" data-title="挂载硬盘到虚拟机" size="s" style="cursor: pointer">挂载  </a>
                                <a href="javascript:void(0)" class='blueletter'
                                   onclick="hdoperate('delete','<s:property value='#volume.id'/>','','<s:property
                        value='endtimeMap[#volume.id]'/>','<s:property value='uidMap[#volume.id]'/>')">
                                    删除</a>
                            </s:if>
                            <s:else>
                                <a href="javascript:void(0)" class='blueletter'
                                   onclick="hdoperate('delete','<s:property value='#volume.id'/>','','<s:property
                                           value='endtimeMap[#volume.id]'/>','<s:property value='uidMap[#volume.id]'/>')">
                                    删除</a>
                            </s:else>
                            </s:if>
                            <s:else>
                                <a href="javascript:void(0)"
                                   onclick="hdoperate('detach','<s:property
                                    value='#volume.id'/>','<s:property value='#volume.attachments.serverId'/>','<s:property
                                           value='endtimeMap[#volume.id]'/>','<s:property value='uidMap[#volume.id]'/>')">卸载</a>
                            </s:else>
                            <a href="hd/hddetail?userIds=<s:property value='userIdMap[#volume.id]'/>&diskId=<s:property value='#volume.id'/>">
                                管理
                            </a>
                        </div>
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
<script>
    function showRealName(obj,value) {
        obj.title = value;
    }
</script>
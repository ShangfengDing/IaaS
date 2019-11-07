<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/30
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="shot-total-page" value='<s:property value="totalPage"/>'/>
<div>
    <div id="freeshare-grouprel">
        <div class="panel panel-default hidden front-panel" id="back_search"
             style="margin-bottom: 10px">
            <div class="panel-heading">
                <strong>云快照搜索</strong>
            </div>
            <div class="panel-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-1 control-label front-label"
                               for="shotkeyword">关键字</label>
                        <div class="col-md-11">
                            <input id="shotkeyword" type="text"
                                   class="form-control front-no-box-shadow" placeholder="搜索关键字">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-1 control-label front-label"
                               for="shotdisktype">硬盘属性</label>
                        <div class="col-md-3">
                            <select id="shotdisktype"
                                    class="form-control front-no-radius front-no-box-shadow">
                                <option value="">--全部--</option>
                                <option value="SYSTEM">系统盘</option>
                                <option value="DATA">数据盘</option>
                                <option value="ISO">ISO盘</option>
                            </select>
                        </div>
                        <div class="text-right col-sm-4">
                            <button type="button" id="resetsearch" class="btn btn-default">重置</button>
                            <button type="button" id="groupsearch"
                                    onclick="shotsearch('YUNHAI','beijing','keyword')" class="btn btn-primary">
                                查找
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="show_data_area">
            <!--云快照信息-->
            <div class="panel panel-default front-panel" id="back_table">
                <div class="panel-body front-no-padding">
                    <table class="table table-striped front-table"
                           style="margin-bottom: 0px;font-size: 15px">
                        <thead style="vertical-align: bottom;border-bottom: 2px solid #ddd;">
                            <th class="col-sm-2">名称</th>
                            <th class="col-sm-2">硬盘名称</th>
                            <th class="col-sm-1">硬盘容量</th>
                            <th class="col-sm-2">硬盘属性</th>
                            <th class="col-sm-2">创建时间</th>
                            <th class="col-sm-1">状态</th>
                            <th class="col-sm-2" style="text-align:right;">操作</th>
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
                                <td class="col-sm-2">
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
                                                       value="#shotServer.appName"/>')">删除快照</a><br>
                                            <s:if test="#shotServer.diskType=='system'">
                                                <a href="javascript:void(0)" onclick="createAliImage('<s:property value="#shotServer.shotId"/>')">创建镜像</a>
                                            </s:if>
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

<script>
function preAction(action,diskId,userEmail,shotId){
	var providerEn = $('#selectProvider').val();
	var regionId = $('#selectRegion').val();

	rollback(action,shotId,"",providerEn,userEmail,regionId,diskId);
}
</script>
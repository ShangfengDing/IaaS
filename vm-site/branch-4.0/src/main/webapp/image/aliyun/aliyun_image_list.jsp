<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/29
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="image-total-page" value='<s:property value="totalPage"/>'/>
<input type="hidden" id="image-result" value='<s:property value="aliYunResult"/>'/>
<!--阿里云镜像信息-->
<div class="panel panel-default front-panel" id="image-table">
    <div class="panel-body front-no-padding">
        <table class="table table-striped front-table"
               style="margin-bottom: 0px;font-size: 15px">
            <thead style="vertical-align: bottom;
                                border-bottom: 2px solid #ddd;">
            <th class="col-sm-2">ID/名称</th>
            <th class="col-sm-2">类型</th>
            <th class="col-sm-2">平台</th>
            <th class="col-sm-1">系统位数</th>
            <th class="col-sm-1">创建时间</th>
            <th class="col-sm-1">状态</th>
            <th class="col-sm-1">进度</th>
            <th class="col-sm-2" style="text-align:right;">操作</th>
            </thead>
            <s:iterator value="aliYunImages" id="image">
                <tr>
                    <td class="col-sm-2">
                        <div>
                            <s:if test="#image.imageOwnerAlias=='self'">
                                <label id="mainName"><s:property value="#image.imageName"/></label>
                            </s:if>
                            <s:else>
                                <label id="mainName"><s:property value="#image.imageId"/></label>
                            </s:else>
                        </div>
                    </td>
                    <td class="col-sm-1"><s:property value="#image.imageOwnerAlias"/></td>
                    <td class="col-sm-2">
                        <label><s:property value="#image.platform"/></label>
                    </td>
                    <td class="col-sm-1">
                        <label><s:property value="#image.architecture.stringValue"/> </label>
                    </td>
                    <td class="col-sm-1">
                        <label><s:property value="#image.creationTime"/> </label>
                    </td>
                    <td class="col-sm-1">
                        <div style="display: inline-block">
                            <label><s:property value="#image.status"/></label>
                        </div>
                    </td>
                    <td class="col-sm-1">
                        <label><s:property value="#image.progress"/> </label>
                    </td>
                    <td class="col-sm-2" style="text-align: right">
                        <div class="btn-group">
                            <s:if test="#image.imageOwnerAlias=='system'">
                                <a href="javascript:void(0)" onclick="relatedImg('<s:property value="#image.imageId"/>')">实例</a>
                            </s:if>
                            <s:else>
                                <a href="javascript:void(0)" onclick="alert('暂不支持此操作')">编辑</a>
                                <a href="javascript:void(0)" onclick="relatedImg('<s:property value="#image.imageId"/>')">实例</a><br>
                                <a href="javascript:void(0)" onclick="alert('暂不支持此操作')">共享镜像</a>
                            </s:else>
                        </div>
                    </td>
                </tr>
            </s:iterator>
        </table>
    </div>
</div>

<script>
    function relatedImg(imageId) {
        var appname = $('#appnamemenu').text();
        var regionId = $("#selectRegion").val();
        href="image/relativeInstance?imageId="+imageId+"&appname="+appname+"&regionId="+regionId;
        window.location.href=href;
    }
</script>
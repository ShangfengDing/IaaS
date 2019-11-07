<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/29
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<input type="hidden" id="image-total-page" value='<s:property value="totalPage"/>'/>
<input type="hidden" id="imgdiv_result" value='<s:property value="imgResult"/>'/>
<!--云海镜像信息-->
<div class="panel panel-default front-panel" id="back_table">
    <div class="panel-body front-no-padding">
        <table class="table table-striped front-table"
               style="margin-bottom: 0px;font-size: 15px">
            <thead style="vertical-align: bottom;
                                border-bottom: 2px solid #ddd;">
                <th class="col-sm-2">名称</th>
                <th class="col-sm-1">容量</th>
                <th class="col-sm-2 branch_kind">类型</th>
                <th class="col-sm-1">操作系统</th>
                <th class="col-sm-1">系统位数</th>
                <th class="col-sm-2">创建时间</th>
                <th class="col-sm-1">硬盘格式</th>
                <th class="col-sm-1">状态</th>
                <th class="col-sm-1" style="text-align:right;">操作</th>
            </thead>
            <s:iterator value="result" id="image">
                <tr>
                    <td class="col-sm-1">
                        <div>
                            <label id="<s:property value='#image.ImageUuid'/>"><s:property value="#image.ImageName"/> </label>
                        </div>
                    </td>
                    <td class="col-sm-1"><s:property value="#image.size"/></td>
                    <td class="col-sm-2 branch_kind">
                        <s:if test="#image.type=='PUBLIC'">
                            公共镜像
                        </s:if>
                        <s:elseif test="#image.type=='GROUP'">
                            共享镜像
                        </s:elseif>
                        <s:else>
                            自定义镜像
                        </s:else>
                    </td>
                    <td class="col-sm-1">
                        <label><s:property value="#image.distribution"/></label>
                    </td>
                    <td class="col-sm-1">
                        <label><s:property value="#image.osArch"/> </label>
                    </td>
                    <td class="col-sm-2">
                        <div style="display: inline-block">
                            <label><s:date name="#image.created" format="yy-MM-dd HH:mm"/></label>
                        </div>
                    </td>
                    <td class="col-sm-1">
                        <label><s:property value="#image.diskFormat"/> </label>
                    </td>
                    <td class="col-sm-1">
                        <label>
                            <s:if test="#image.status=='available'">
                                可用
                            </s:if>
                            <s:else>
                                不可用
                            </s:else>
                        </label>
                    </td>
                    <td class="col-sm-1" style="text-align: right">
                        <s:if test="#image.type=='PRIVATE'">
                            <div class="btn-group">
                                <a href="image/relativeInstance?imageId=<s:property value="#image.ImageUuid"/>&appname=<s:property value="#image.appname"/>&regionId=<s:property value='#image.regionId'/>">实例</a>
                                <!--<a href="javascript:void(0)" onclick='editYunhaiImg("<s:property value='#image.ImageUuid'/>","<s:property value='#image.ImageName'/>","<s:property value='#image.description'/>","<s:property value='#image.account'/>","<s:property value='#image.software'/>")'>编辑</a></br>-->
                                <a onclick='publishYunhaiImg("<s:property value='#image.ImageUuid'/>")'>发布镜像</a>
                            </div>
                        </s:if>
                        <s:else>
                            <div class="btn-group">
                                <a href="image/relativeInstance?imageId=<s:property value="#image.ImageUuid"/>&appname=<s:property value="#image.appname"/>&regionId=<s:property value='#image.regionId'/>">实例</a>
                                <%--<a href="javascript:void(0)" onclick='editYunhaiImg("<s:property value='#image.ImageUuid'/>","<s:property value='#image.ImageName'/>","<s:property value='#image.description'/>","<s:property value='#image.account'/>","<s:property value='#image.software'/>")'>编辑</a>--%>
                            </div>
                        </s:else>
                    </td>
                </tr>
            </s:iterator>
        </table>
    </div>
</div>

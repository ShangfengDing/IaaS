<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!--              搜索结果：查询列表    -->

            <table id="resultTable" class="table table-striped front-table" style="margin-bottom: 0px">
                <thead>
                <tr>
                    <th><div>名称</div></th>
                    <th class="col-md-1"><div>操作系统</div></th>
                    <th class="col-md-1"><div>类型</div></th>
                    <th class="col-md-1"><div>用户</div></th>
                    <th class="col-md-1"><div>群组</div></th>
                    <th class="col-md-1"><div>状态</div></th>
                    <th class="col-md-1"><div>操作</div></th>
                </tr>
                </thead>
                <s:iterator id="image" value="images" status="st">
                <tr id="tr<s:property value="#image.id"/>">
                    <td id="name<s:property value="#image.id"/>"><s:property value="(#image).metadata['displayName']"/></td>
                    <td id="desc<s:property value="#image.id"/>"><s:property value="#image.distribution" escape="false"/></td>
                    <td><s:property value="#image.metadata['type']"/></td>
                    <td id="<s:property value="#image.id"/>"><s:property value="#image.tenantId" escape="false"/></td>
                    <td id="<s:property value="#image.metadata['groupId']"/>"><s:property value="#image.metadata['groupId']"/></td>
                    <td>
                        <s:if test="#image.status == 'AVAILABLE' or #image.status == 'available'">正常</s:if>
                        <s:elseif test="#image.status == 'CREATING' or #image.status == 'creating'">创建中</s:elseif>
                        <s:elseif test="#image.status == 'DELETING' or #image.status == 'deleting'">已删除</s:elseif>
                        <s:elseif test="#image.status == 'ERROR' or #image.status == 'error'">
                            <span class="redletter">错误</span></s:elseif>
                        <s:elseif test="#image.status == 'ERROR_DELETING' or #image.status == 'error_deleting'">
                            <span class="redletter">删除失败</span></s:elseif>
                    </td>
                    <td><a class="blueletter" data-toggle="front-modal"
                           data-href="system/imgmod.jsp?type=${param.type}&iid=<s:property value="#image.id"/>"
                           rel="facebox" size="s" data-title="编辑模板信息">修改</a>&nbsp;
                        <a class="buleletter" href="javascript:void(0)"
                           onclick="delImage('<s:property value="#image.id"/>','<s:property
                                   value="#image.metadata['groupId']"/>')">删除</a>
                        <a data-toggle="front-modal" class="blueletter"
                           data-href="system/imgdetail.jsp?displayDescription=<s:property value="#image.metadata['displayDescription']"/>&software=<s:property value="#image.metadata['software']"/>&account=<s:property value="#image.metadata['account']"/>"
                           data-title="详情" rel="facebox" size="s">详情</a>
                        <s:if test="#image.metadata['type'] == 'GROUP' or #image.metadata['type'] == 'PRIVATE'">
                            <a  class="buleletter" href="javascript:void(0)"
                                onclick="release('<s:property value="#image.id"/>')">发布</a>
                        </s:if>
                    </td>
                </tr>
                </s:iterator>
            </table>


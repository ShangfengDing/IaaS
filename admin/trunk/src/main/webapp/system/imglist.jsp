<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<table id="resultTable" class="datatable">
    <tr class="tabletitle">
        <td width="120px">名称</td>
        <td width="60px">操作系统</td>
        <td width="60px">类型</td>
        <td width="60px">用户</td>
        <td width="60px">群组</td>
        <td width="70px">状态</td>
        <td width="120px">操作</td>
    </tr>
    <s:iterator id="image" value="images" status="st">
        <tr id="tr<s:property value="#image.id"/>">
            <td id="name<s:property value="#image.id"/>"><s:property
                    value="(#image).metadata['displayName']"/></td>
            <td id="desc<s:property value="#image.id"/>"><s:property
                    value="#image.distribution" escape="false"/></td>
            <td><s:property value="#image.metadata['type']"/></td>
            <td id="<s:property value="#image.id"/>"><s:property value="#image.tenantId"
                                                                 escape="false"/></td>
            <td id="<s:property value="#image.metadata['groupId']"/>"><s:property
                    value="#image.metadata['groupId']"/></td>
            <td><%-- <s:property value="#image.status"/> --%>
                <s:if test="#image.status == 'AVAILABLE' or #image.status == 'available'">正常</s:if>
                <s:elseif test="#image.status == 'CREATING' or #image.status == 'creating'">创建中</s:elseif>
                <s:elseif test="#image.status == 'DELETING' or #image.status == 'deleting'">已删除</s:elseif>
                <s:elseif test="#image.status == 'ERROR' or #image.status == 'error'"><span
                        class="redletter">错误</span></s:elseif>
                <s:elseif
                        test="#image.status == 'ERROR_DELETING' or #image.status == 'error_deleting'"><span
                        class="redletter">删除失败</span></s:elseif>
            </td>
            <td>
                <a class="blueletter"
                   href="system/imgmod.jsp?type=${param.type}&iid=<s:property value="#image.id"/>"
                   rel="facebox" size="s" title="编辑模板信息">修改</a>&nbsp;
                <a class="blueletter" href="javascript:void(0)"
                   onclick="delImage('<s:property value="#image.id"/>','<s:property
                           value="#image.metadata['groupId']"/>')">删除</a>
                <a class="blueletter"
                   href="system/imgdetail.jsp?displayDescription=<s:property value="#image.metadata['displayDescription']"/>&software=<s:property value="#image.metadata['software']"/>&account=<s:property value="#image.metadata['account']"/>"
                   title="详情" rel="facebox" size="s">详情</a>
                <s:if test="#image.metadata['type'] == 'GROUP' or #image.metadata['type'] == 'PRIVATE'">
                    <a class="blueletter" href="javascript:void(0)"
                       onclick="release('<s:property value="#image.id"/>')">发布</a>
                </s:if>
            </td>
        </tr>
    </s:iterator>
</table>

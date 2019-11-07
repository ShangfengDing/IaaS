<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.api.enums.ImageTypeEnum"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    int i=1;
%>
<s:if test="total==0 || adminRoles.size()==0">
    <h3>无搜索结果1a</h3>
</s:if>
<s:else>
    <%--<h4 class="pull-right">共<s:property value="total"/>页</h4>--%>
    <div class="panel panel-default front-panel" style="margin-top:10px">
        <div class="panel-body front-no-padding">
            <table class="table table-striped multi-table" style="margin-bottom:0px">
                <thead>
                <tr>
                    <th class="col-sm-1">序号</th>
                    <th>管理组名称</th>
                    <th class="col-sm-3">操作</th>
                </tr>
                </thead>
                <s:iterator id="adminRoles" value="adminRoles">
                    <tr id='<s:property value="#adminRoles.id"/>' >
                        <td><%=i++%></td>
                        <td style="text-align:left"><s:property value="#adminRoles.rolename"/></td>
                        <td>
                            <a id="role_detail<s:property value="#adminRoles.id"/>" href="runtime/showRole?roleid=<s:property value="#adminRoles.id"/>&page=4" rel="facebox" title="查看管理组">查看管理组</a>
                            &nbsp;&nbsp;
                            <s:if test="#adminRoles.id==roleid">
                                <a style="color:red">无法修改管理组</a>&nbsp;&nbsp;
                                <a style="color:red">无法删除</a>
                            </s:if>
                            <s:else>
                                <a id="role_mod<s:property value="#adminRoles.id"/>" href="runtime/modResource?roleid=<s:property value="#adminRoles.id"/>" rel="facebox" title="修改管理组">
                                    修改管理组
                                </a>&nbsp;&nbsp;
                                <a id="delete<s:property value="#adminRoles.id"/>" href="javascript:void(0)" onclick="deleteAdminRole(<s:property value="#adminRoles.id"/>)">
                                    删除
                                </a>
                            </s:else>
                        </td>
                    </tr>
                </s:iterator>
            </table>

        </div>
    </div>
    <div class="lineheight" id="pageColumn" data-endPage="<s:property value='lastpage'/>"></div>
</s:else>
<script>
    <%--$("#pagenum").html("共<s:property value="total"/>条");--%>
    $("#totalnum").html("共<s:property value="total"/>条");
</script>
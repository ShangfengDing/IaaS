<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.api.enums.ImageTypeEnum"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="total==0 || admins==null">
    <h3>无搜索结果</h3>
</s:if>
<s:else>
    <div class="panel panel-default front-panel" style="margin-top:10px">
        <div class="panel-body front-no-padding">
            <table class="table table-striped multi-table" style="margin-bottom:0px">
                <thead>
                <tr>
                    <%--<th class="col-sm-1">序号</th>--%>
                    <th >管理员名称</th>
                    <th class="col-sm-2">邮箱</th>
                    <th class="col-sm-2">联系方式</th>
                    <th class="col-sm-3">操作</th>
                </tr>
                </thead>
                <s:iterator id="admins" value="admins" status="item">
                    <tr id="tr<s:property value="#admins.id"/>">
                        <%--<td><s:property value="%{#item.getIndex()+1}"/></td>--%>
                        <td><s:property value="#admins.username"/></td>
                        <s:if test="#admins.email==null">
                            <td>无</td>
                        </s:if>
                        <s:else>
                            <td><s:property value="#admins.email"/></td>
                        </s:else>
                        <s:if test="#admins.mobile==null">
                            <td>无</td>
                        </s:if>
                        <s:else>
                            <td><s:property value="#admins.mobile"/></td>
                        </s:else>
                        <td>
                            <a href="runtime/preChangePassword?id=<s:property value="#admins.id"/>"
                               rel="facebox" title="修改名称和密码">
                                修改名称和密码
                            </a>&nbsp;&nbsp;
                            <s:if test="#admins.roleId<=0">
                                <a id="group_detail<s:property value="#admin.roleId"/>" style="color:red">未被分配管理组</a>&nbsp;&nbsp;
                            </s:if>
                            <s:else>
                                <a id="group_detail<s:property value="#admins.roleId"/>" href="runtime/showRole?roleid=<s:property value="#admins.roleId"/>&page=3"
                                rel="facebox" title="查看所属管理组">查看所属管理组</a> &nbsp;&nbsp;<br>
                            </s:else>
                            <s:if test="adminId==#admins.id">
                                <a id="mod_group<s:property value="#admins.id"/>" style="color:red">无法修改所属管理组</a>&nbsp;&nbsp;
                            </s:if>
                            <s:else>
                                <a id="mod_group<s:property value="#admins.id"/>" href="runtime/preModRole?id=<s:property value="#admins.id"/>"
                                   rel="facebox" title="修改所属管理组">修改所属管理组</a>&nbsp;&nbsp;
                            </s:else>
                            <a id="mod_group<s:property value="#admins.id"/>" href="runtime/preModAdmin?id=<s:property value="#admins.id"/>"
                               rel="facebox" title="修改管理员">修改管理员</a>
                        </td>
                    </tr>
                </s:iterator>
            </table>

        </div>
    </div>
    <div class="lineheight" id="pageColumn" data-endPage="<s:property value='lastpage'/>"></div>
</s:else>
<script>
    $("#totalnum").html("");
    //$("#totalnum").append("<span class='pull-right' style='font-weight:bold;font-size:14px'>共<s:property value="total"/>条</span>");
    $("#totalnum").html("共<s:property value="total"/>条");
</script>

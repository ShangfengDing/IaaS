<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.api.enums.ImageTypeEnum"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%String s="";%>
<s:if test="total==0">
    <h3>无搜索结果</h3>
</s:if>
<s:else>

    <div class="panel panel-default front-panel" style="margin-top:10px">
        <div class="panel-body front-no-padding">
            <table class="table table-striped multi-table" style="margin-bottom:0px">
                <thead>
                <tr>
                    <th class="col-md-1">邮箱</th>
                    <th class="col-md-1">群组</th>
                    <th class="col-md-1">appkey</th>
                    <th class="col-md-1">secretkey</th>
                    <th class="col-md-1">虚拟机</th>
                    <th class="col-md-1">硬盘</th>
                    <th class="col-md-1">余额</th>
                    <th class="col-md-2">操作</th>
                </tr>
                </thead>
                <s:iterator id="acUsers" value="acUsers">
                    <tr id="tr<s:property value="#acUsers.userId"/>">
                        <td><s:property value="#acUsers.userEmail"/></td>
                        <s:set name="userid3" value="#acUsers.userId"/>
                      <%-- <%s=(String)pageContext.getAttribute("userid3");
                        request.setAttribute("s",Integer.parseInt(s));
                        %>--%>
                        <%--<td><s:property value="enterpriseMap[${requestScope.s}]"/></td>--%>
                        <%--<s:if test="enterpriseMap[#userid3]!=null">--%>
                            <%--<td><a id="enterprise" rel="facebox" href="user/showEnterprise?uid=<s:property value="#acUsers.userId"/>">--%>
                               <%--<s:property value="enterpriseMap[#userid3]"/>--%>
                            <%--</a></td>--%>
                        <%--</s:if>--%>
                        <%--<s:else>--%>
                            <%--<td></td>--%>
                        <%--</s:else>--%>
                        <td>
                           <s:if test="#acUsers.groupId=='1'.toString()">
                               super
                           </s:if>
                            <s:if test="#acUsers.groupId=='2'.toString()">
                                public
                            </s:if>
                            <s:if test="#acUsers.groupId=='3'.toString()">
                                free专用
                            </s:if>
                        </td>
                        <td onmouseover="showRealName(this,'<s:property value="#acUsers.appkeyId"/>')">
                            <s:if test="#acUsers.appkeyId!=null">
                                <s:property value="#acUsers.appkeyId.substring(0,15)"></s:property>...
                            </s:if>
                            <s:else>
                                <%--<s:property value="#acUsers.appkeyId.substring(0,15)"></s:property>--%>
                            </s:else>
                        </td>
                        <td onmouseover="showRealName(this,'<s:property value="#acUsers.appkeySecret"/>')">
                            <s:if test="#acUsers.appkeySecret!=null">
                                <s:property value="#acUsers.appkeySecret.substring(0,15)"/>...
                            </s:if>
                        </td>
                        <td><a target="_blank" href="vm/presearchvm?email=<s:property value="#acUsers.userEmail"/>">
                            <s:property value="countMap[#userid3]"/></a></td>
                        <td><a target="_blank" href="hd/hdmanage?email=<s:property value="#acUsers.userEmail"/>">
                                <s:property value="countVolumeMap[#userid3]"/></a></td>
                        <td><%--<a id="usermanagebalance<s:property value="#acUsers.userId"/>" target="_blank"
                            href="price/income.jsp?email=<s:property value="#acUsers.userEmail"/>"><s:property value="balanceMap[#userid3]"/></a>--%>
                            <a id="usermanagebalance<s:property value="#acUsers.userId"/>" style="color:gray;"><s:property value="balanceMap[#userid3]"/></a>

                            </td>
                        <td>
                            <div>
                                <div style="dispaly:inline-block;">
                            <a id="group_detail<s:property value="#acUsers.userId"/>" href="group/showAcGroup?acGroupId=<s:property value="#acUsers.groupId"/>&page=1"
                               rel="facebox" title="查看群组">查看群组</a>&nbsp;&nbsp;
                            <a href="user/preChangeAuthority?uid=<s:property value="#acUsers.userId"/>" rel="facebox"
                               title="修改群组">修改群组</a>&nbsp;&nbsp;<br>
                            <%--<a id="recharge" target="_blank" href="price/income.jsp?email=<s:property value="#acUsers.userEmail"/>">充值</a>&nbsp;&nbsp;--%>
                                    <a id="recharge"  style="color:gray;" >充值</a>&nbsp;&nbsp;
                            <%--<a id="cleanblance" target="_blank" href="price/income.jsp?email=<s:property value="#acUsers.useEmail"/>">余额清零</a>&nbsp;&nbsp;--%>
                                    <a id="cleanblance" style="color:gray;">余额清零</a>&nbsp;&nbsp;
                            <s:if test="#acUsers.isActive==true">
                                <%--<a id="change_status<s:property value="#acUsers.userId"/>" href="javascript:void(0)" onclick="changeStatus(<s:property value="#acUsers.userId"/>)">
                                    冻结
                                </a>--%>
                                <a id="change_status<s:property value="#acUsers.userId"/>" style="color:gray;">
                                    冻结
                                </a>
                            </s:if>
                            <s:else>
                                <%--<a id="change_Status<s:property value="#acUsers.userId"/>" href="javascript:void(0)" onclick="changeStatus(<s:property value="#acUsers.userId"/>)">
                                    解冻
                                </a>--%>
                                <a id="change_Status<s:property value="#acUsers.userId"/>" href="javascript:void(0)">
                                    解冻
                                </a>
                            </s:else>
                                </div>
                            </div>
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
    function showRealName(obj,value) {
        obj.title = value;
    }
</script>
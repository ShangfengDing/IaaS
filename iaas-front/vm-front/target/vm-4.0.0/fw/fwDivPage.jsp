<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--<htmL>--%>
<%--<head>--%>
	<%--<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />--%>
  	<%--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />--%>
  	<%--<s:include value="/template/_head.jsp" />--%>
<%--</head>--%>
<input class='hidden' id="result_totalpage" value='<s:property value="totalPage"/>'/>
<input class='hidden' id="fw_result" value='<s:property value="result"/>'/>
<!-- 全部 -->
<div class="panel panel-default front-panel" id="div_table">
    <div class="panel-body front-no-padding">
        <table class="table table-striped front-table"
               style="margin-bottom: 0px;font-size: 15px" id="serverList">
            <thead style="vertical-align: bottom;border-bottom: 2px solid #ddd;">
                <th class="col-sm-2">防火墙名称</th>
                <th class="col-sm-8">描述</th>
                <th class="col-sm-2">操作</th>
            </thead>
            <s:iterator id="securityGroupDetailItem" value="securityGroupDetailItems">
                <tr>
                    <td class="col-sm-2">
                        <div>
                            <label><s:property
                                    value="#securityGroupDetailItem.SecurityGroupName"/></label>
                        </div>
                    </td>
                    <td class="col-sm-8">
                        <div>
                            <label>
                                <s:property
                                        value="#securityGroupDetailItem.Description"></s:property>
                            </label>
                        </div>
                    </td>
                    <td class="col-sm-2">
                        <div class="btn-group">
                            <a id="manage_rule" href="javascript:void(0)" onclick='showDetail(this,"<s:property
                                    value="#securityGroupDetailItem.SecurityGroupId"/>","<s:property
                                    value="#securityGroupDetailItem.userEmail"/>")'>规则</a>
                            <a href="javascript:void(0)" onclick='modifySecurityGroup("<s:property
                                    value="#securityGroupDetailItem.SecurityGroupId"/>","<s:property
                                    value="#securityGroupDetailItem.SecurityGroupName"/>","<s:property
                                    value="#securityGroupDetailItem.Description"></s:property>","<s:property
                                    value="#securityGroupDetailItem.userEmail"/>")'>编辑</a>
                            <a href="javascript:void(0)" onclick='deleteSecurityGroup("<s:property
                                    value="#securityGroupDetailItem.SecurityGroupId"/>","<s:property
                                    value="#securityGroupDetailItem.userEmail"/>")'>删除</a><br/>
                        </div>
                    </td>
                </tr>
                <tr id="ruleadd_<s:property value='#securityGroupDetailItem.SecurityGroupId'/>" class="hidden">
                    <td class="col-sm-12" colspan="12">
                        <a href="javascript:void(0)" class="col-md-1 text-right" style="padding: 0px;padding-bottom: 5px;float: right;"
                           onclick='addNewRule("<s:property  value="#securityGroupDetailItem.SecurityGroupId"/>")'>添加规则</a>
                    </td>
                </tr>
                <tr id="ruledetail_<s:property value="#securityGroupDetailItem.SecurityGroupId"/>"
                    class="hidden">
                    <td class="col-sm-12" colspan="12">
                        <table class="table table-striped front-table"
                               style="border: 1px #ddd solid;"
                               id="ruleresult_<s:property value="#securityGroupDetailItem.SecurityGroupId"/>">
                            <%--<tbody id="ruleresult_<s:property value="#securityGroupDetailItem.SecurityGroupId"/>">--%>
                            <%--</tbody>--%>
                        </table>
                    </td>
                </tr>
            </s:iterator>
        </table>
    </div>
</div>
<%--</htmL>--%>
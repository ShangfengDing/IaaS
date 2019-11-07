<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>群组管理 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12">
            <div>
                <ol class="breadcrumb">
                    <li>用户管理</li>
                    <li class="active">用户组</li>
                </ol>
            </div>
            <div class="inner">
                <div style="margin-bottom:10px;">
                    <%--<span class="pull-left" style="margin-top:16px; margin-left:10px; font-size:15px; font-weight:bold;">群组管理</span>--%>
                    <label class="col-md-2 front-no-padding" style="padding-left: 8px; line-height: 33px; margin: 0px;">群组管理</label>
                    <a href="group/preNewAcGroup" rel="facebox" title="新建群组" class="btn btn-primary pull-right">
                        <%--<span class="glyphicon glyphicon-plus-sign"></span>&nbsp;--%>
                        新建
                    </a>
                    <div class="clearfix"></div>
                </div>
                <div class="panel panel-default front-panel" style="margin-top: 10px">
                    <div class="panel-body front-no-padding">
                <s:if test='acGroups.size() == 0'>
                    <h3 class="centeralign">还没有群组</h3>
                </s:if>
                <s:else>
                        <table class="table table-striped multi-table" style="margin-bottom: 0px">
                            <thead>
                            <tr>
                                <th class="col-sm-2" >
                                    <div>群组</div>
                                </th>
                                <th>
                                    <div>描述</div>
                                </th>
                                <th class="col-sm-1" style="text-align: center;">
                                    <div>操作</div>
                                </th>
                            </tr>
                            </thead>
                            <s:iterator id="acGroup" value="acGroups">
                            <tr>
                                <td><s:property value="#acGroup.name"/></td>
                                <td><s:property value="#acGroup.description"/></td>
                                <td class="centeralign" style="text-align: center;">
                                    <a href="group/showAcGroup?acGroupId=<s:property value="#acGroup.id"/>&page=2" rel="facebox" title="群组详情">详情</a>&nbsp;
                                    <a href="javascript:void(0)" onclick="delAcGroup(this,'<s:property value="#acGroup.id"/>')">删除</a>
                                </td>
                            </tr>
                            </s:iterator>
                            </table>
                        </s:else>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>
<script src="js/group/acgroupmanage.js"></script>
</body>
</html>

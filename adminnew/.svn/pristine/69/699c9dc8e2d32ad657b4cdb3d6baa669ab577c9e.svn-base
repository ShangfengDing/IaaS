<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants,appcloud.admin.action.system.VmSummaryAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<div id="serviceTable" class="panel panel-default front-panel" style="margin-top:10px">
    <table class="table table-striped multi-table" style="margin-bottom: 0px">
        <thead>
        <tr>
            <th >名称</th>
            <th class="col-md-2 text-center">状态</th>
            <th class="col-md-2 text-center">操作</th>
        </tr>
        </thead>
        <s:iterator id="serviceStatus" value="serviceStatusMap">
            <s:if test="#serviceStatus.key!='UNKNOWN'">
                <tr>
                    <td>
                        <s:property value="#serviceStatus.key"/>
                    </td>
                    <td class="text-center">
                        <s:if test="#serviceStatus.value=='RUNNING'">
                            <%--<img src="images/inf_normal.png"/>--%>
                            <span style="color: #4cb050;font-size: 20px" class="glyphicon glyphicon-play-circle"/>
                        </s:if>
                        <s:if test="#serviceStatus.value=='STOPED'">
                            <%--<img src="images/inf_error.png"/>--%>
                            <span style="color: #dc3b1a;font-size: 20px" class="glyphicon glyphicon-remove-sign"/>
                        </s:if>
                        <s:if test="#serviceStatus.value=='NOT USED'">
                            <%--<img src="images/inf_alert.png"/>--%>
                            <span style="color: #e7c236;font-size: 20px" class="glyphicon glyphicon-info-sign"/>
                        </s:if>
                    </td>
                    <td class="text-center">
                        <s:if test="#serviceStatus.value=='RUNNING'">
                            <a href="javascript:void(0)"
                               onclick="stop('<s:property value="#serviceStatus.key"/>')">停止</a>
                        </s:if>
                        <s:if test="#serviceStatus.value=='STOPED'">
                            <a href="javascript:void(0)"
                               onclick="start('<s:property value="#serviceStatus.key"/>')">启动</a>
                        </s:if>
                        <s:if test="#serviceStatus.value=='NOT USED'">
                            <a href="javascript:void(0)"
                               onclick="start('<s:property value="#serviceStatus.key"/>')">启动</a>
                        </s:if>
                    </td>
                </tr>
            </s:if>
        </s:iterator>
    </table>
</div>
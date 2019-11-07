<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>群组详情</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=userManage" />
<input type="hidden" id="acGroupId" name="acGroupId" value='<s:property value="acGroup.id"/>'/>
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div>
                <ol class="breadcrumb">
                    <li>用户管理</li>
                    <s:if test="page==1">
                        <li><a href="user/usermanage.jsp">用户</a></li>
                    </s:if>
                    <s:else>
                        <li><a href="group/acGroupList">用户组</a></li>
                    </s:else>
                    <li class="active">群组详情</li>
                </ol>
            </div>
            <div class="inner">
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>群组详情</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">名称</label>
                                <div class="col-md-5">
                                    <input disabled="true" type="text" class="form-control front-no-box-shadow" id="name" name="name" value="<s:property value="acGroup.name"/>"/>
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error1"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">描述</label>
                                <div class="col-md-5">
                                    <textarea disabled="true" id="description" name="description" rows="3"
                                              style="width: 100%;"><s:property
                                            value="acGroup.description"/></textarea>
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error2"></label>
                            </div>

                            <div class="form-group">
                                    <label class="col-md-2 control-label front-label" style="text-align:center">硬盘上限</label>
                                    <div class="col-md-5">
                                        <input disabled='true' type="text" class="form-control front-no-box-shadow" id="max_number_of_disk" name="max_number_of_disk" value="<s:property value="acGroup.maxNumberOfDisk"/>" />
                                    </div>
                                    <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error4"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">备份上限</label>
                                <div class="col-md-5">
                                    <input disabled="true" type="text" class="form-control front-no-box-shadow" id="max_number_of_backup" name="max_number_of_backup" value="<s:property value="acGroup.maxNumberOfBackup"/>"/>
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error5"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">快照上限</label>
                                <div class="col-md-5">
                                    <input disabled="true" type="text" class="form-control front-no-box-shadow" id="max_number_of_snapshot" name="max_number_of_snapshot" value="<s:property value="acGroup.maxNumberOfSnapshot"/>" />
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error6"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">虚拟机上限</label>
                                <div class="col-md-5">
                                    <input type="text" disabled='true' class="form-control front-no-box-shadow" id="max_number_of_instance" name="max_number_of_instance" value="<s:property value="acGroup.maxNumberOfInstance"/>"/>
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error3"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">发布模板</label>
                                <s:if test="acGroup.publishImage == true">
                                    <label class="col-md-1 control-label front-label" style="text-align:left">
                                    <input disabled="true" type="checkbox" style="text-align:right" id="publish_image" name="publish_image" value="publish_image" checked/>
                                        允许</label>
                                </s:if>
                                <s:else>
                                    <label class="col-md-1 control-label front-label text-right" style="text-align:left">
                                    <input disabled="true" type="checkbox" id="publish_image" name="publish_image" value="publish_image"/>
                                        允许</label>
                                </s:else>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">选择集群</label>
                                <s:if test="acGroup.selectCluster == true">
                                <label class="col-md-1 control-label front-label" style="text-align:left">
                                    <input disabled="true" type="checkbox" id="select_cluster" name="select_cluster" value="select_cluster" checked/>
                                        允许</label>
                                </s:if>
                                <s:else>
                                <label class="col-md-1 control-label front-label" style="text-align:left">
                                    <input disabled="true" type="checkbox" id="select_cluster" name="select_cluster" value="select_cluster"/>
                                        允许</label>
                                </s:else>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">可选集群</label>
                                <s:set name="count" value="0"/>
                                <s:iterator id="acAggregate" value="acAggregates">
                                    <s:set name="has_selected" value="0"></s:set>
                                    <s:iterator id="selected_acAggregate" value="acGroup.availableClusters">
                                        <s:if test="#acAggregate.id == #selected_acAggregate">
                                            <s:set name="has_selected" value="1"></s:set>
                                        </s:if>
                                    </s:iterator>
                                    <label class="col-md-1 control-label front-label" style="text-align:left">
                                    <s:if test="#has_selected == 1">
                                        <input disabled="true" type="checkbox" id="<s:property value="#acAggregate.id"/>" name="selected_aggregates" checked value="<s:property value="#acAggregate.id"/>"/>
                                    </s:if>
                                    <s:else>
                                        <input disabled="true" type="checkbox" id="<s:property value="#acAggregate.id"/>" name="selected_aggregates" value="<s:property value="#acAggregate.id"/>"/>
                                    </s:else>
                                    <s:property value="#acAggregate.name"/></label>
                                    <s:if test="#count == 2">
                                        <s:set name="count" value="0"/>

                                    </s:if>
                                    <s:else>
                                        <s:set name="count" value="#count + 1"/>
                                        &nbsp;&nbsp;
                                    </s:else>
                                </s:iterator>
                            </div>
                            <div class="form-group">
                                <label class="col-md-12 control-label front-label" style="text-align:center; color:red" id="error7"></label>
                            </div>
                            <div class="form-group">
                                <div class="text-right col-md-12">
                                    <a id="cancel" style="display:none" class="btn btn-default" href="javascript:void(0)" onclick="cancel()">取消</a>
                                <a id="submit" style="display:none" class="btn btn-primary" href="javascript:void(0)" onclick="submit();">确定</a>
                                <a id="enable_edit" class="btn btn-primary" href="javascript:void(0)" onclick="enable_edit();">编辑</a>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>
<script src="js/group/showacgroup.js"></script>
</body>
<script>

</script>
</html>

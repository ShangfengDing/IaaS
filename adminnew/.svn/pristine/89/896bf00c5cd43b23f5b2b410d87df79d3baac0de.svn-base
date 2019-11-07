<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>新建群组</title>
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
                    <li><a href="group/acGroupList">用户组</a></li>
                    <li class="active">新建群组</li>
                </ol>
            </div>
            <div class="inner">
                <div class="panel panel-default front-panel">
                    <div class="panel-heading">
                        <strong>新建群组</strong>
                    </div>
                    <div class="panel-body">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">名称</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" id="name" name="name" placeholder="请输入1-20个字"/>
                                </div>
                                <label class="col-md-5 control-label front-label"style="text-align:left; color:red" id="error1"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">描述</label>
                                <div class="col-md-5">
                                    <textarea id="description" name="description" placeholder="请输入1-50个字" ></textarea>
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error2"></label>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">硬盘上限</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" id="max_number_of_disk" name="max_number_of_disk"/>
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error4"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">备份上限</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" id="max_number_of_backup" name="max_number_of_backup" />
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error5"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">快照上限</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" id="max_number_of_snapshot" name="max_number_of_snapshot"/>
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error6"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">虚拟机上限</label>
                                <div class="col-md-5">
                                    <input type="text" class="form-control front-no-box-shadow" id="max_number_of_instance" name="max_number_of_instance"/>
                                </div>
                                <label class="col-md-5 control-label front-label" style="text-align:left; color:red" id="error3"></label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">发布模板</label>
                                    <label class="col-md-1 control-label front-label" style="text-align:left">
                                        <input type="checkbox" style="text-align:right" id="publish_image" name="publish_image" value="publish_image"/>
                                        允许</label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">选择集群</label>
                                    <label class="col-md-1 control-label front-label" style="text-align:left">
                                        <input type="checkbox" id="select_cluster" name="select_cluster" value="select_cluster"/>
                                        允许</label>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label front-label" style="text-align:center">可选集群</label>
                                <s:set name="count" value="0"/>
                                <s:iterator id="aggregate" value="acAggregates">
                                    <label class="col-md-1 control-label front-label" style="text-align:left">
                                    <input type="checkbox" id="#aggregate.id" name="selected_aggregates" value="<s:property value="#aggregate.id"/>"/>
                                    <s:property value="#aggregate.name"/></label>
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
                                    <a class="btn btn-default" href="javascript:void(0)" onclick="cancel()">重置</a>
                                    <a class="btn btn-primary" href="javascript:void(0)" onclick="submit();">确定</a>

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
<script src="js/group/newacgroup.js"></script>
</body>
</html>

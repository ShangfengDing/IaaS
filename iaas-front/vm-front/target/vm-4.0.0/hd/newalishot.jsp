<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="utf-8">
    <s:include value="../template/_head.jsp"/>
</head>
<body>
<!-- 获取参数 -->
<input class="hidden" value='${param.userEmail}' id="aliuserEmail">
<input class="hidden" value='${param.providerEn}' id="aliproviderEn">
<input class="hidden" value='${param.regionId}' id="aliregionId">
<input class="hidden" value='${param.appName}' id="aliappName">

<!-- 新建阿里云云快照的模态框 -->
<div class="modal-body">
    <form class="form-horizontal">
    	<div class="form-group">
    		<label class="col-md-3 control-label">云硬盘ID:</label>
    		<div class="col-md-5">
    			<label class="control-label" id='alidiskId'>${param.diskId}</label>
    		</div>
    	</div>
    	<div class="form-group">
    		<label class="col-md-3 control-label">云主机ID/名称:</label>
    		<div class="col-md-5">
    			<label class="control-label">${param.vmId}</label>
    		</div>
    	</div>
    	<div class="form-group">
    		<label class="col-md-3 control-label">云硬盘类型:</label>
    		<div class="col-md-5">
    			<label class="control-label">${param.diskType}</label>
    		</div>
    	</div>
    	<div class="form-group">
    		<span class="text-danger">*</span><label class="col-md-3 control-label">快照名称:</label>
    		<div class="col-md-5">
    			<input class="form-control" id='shotname' class="form-control"/>
    			<span style='font-size: 10px;'>快照名称为2-20个字符，快照名不能以auto开头,以大小写字母或中文开头，可包含数字，"."，"_"或"-"。</span>
    		</div>
    	</div>
    </form>
</div>
<div class='modal-footer'>
    <button class="btn btn-primary" onclick="createAliShot()">确定</button>
    <button class="btn btn-default" onclick="newalishot.modal('hide')">取消</button>
</div>
</body>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="utf-8">
    <s:include value="../template/_head.jsp"/>
</head>
<body>
<!-- 新建阿里云云快照的模态框 -->
<div class="modal-body">
    <form class="form-horizontal">
		<div class="form-group" style="    background-color: #f0f040;margin: 0;padding-top: 8px;">
			<label class="col-md-12" style="color:#f04040; font-size: 10px">
				请您在使用linux系统创建自定义镜像时，注意不要在/etc/fstab文件中加载数据盘的信息，否则使用该镜像创建的实例无法启动。
			</label>
		</div>
		<div class="form-group" style="margin-top: 15px">
			<label class="col-md-3 control-label">系统快照ID:</label>
			<label id="image_shotId" class="col-md-8">${param.shotId}</label>
		</div>
    	<div class="form-group" style="margin-top: 15px">
			<span class="text-danger">*</span><label class="col-md-3 control-label">模板名称:</label>
    		<div class="col-md-8">
				<input class="form-control" id='image_name' class="form-control"/>
				<label>
					长度为2-20个字符，以大小写字母或者中文开头，可包含数字 ，"."，"_"或者"-",不能以 http:// 和 https:// 开头。
				</label>
			</div>
    	</div>
    	<div class="form-group">
			<span class="text-danger">*</span><label class="col-md-3 control-label">模板描述:</label>
    		<div class="col-md-8">
                <textarea class="form-control" id='image_des' class="form-control"/>
                <label>
                    长度为2-100个字符，不能以http://和https://开头。
                </label>
    		</div>
    	</div>
    </form>
</div>
<div class='modal-footer'>
    <button class="btn btn-primary" onclick="aliImageSubmit()">确定</button>
    <button class="btn btn-default" onclick="aliImageModal.modal('hide')">取消</button>
</div>
</body>
</html>

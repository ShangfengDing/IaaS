<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<meta charset="utf-8">
	<s:include value="../template/_head.jsp"/>
</head>
<body>
<div class="modal-body">
	<h4>新建防火墙-默认拒绝任何连接</h4>
	<div class="form-group">
		<label for="newfwName">防火墙名称</label>
		<input class="form-control" placeholder="必须以大小字母或中文开头,不能以 http://和 https://开头，可包含数字，”.”，”_”或”-”" type="text" id="newfwName">
	</div>
	<div class="form-group">
		<label>防火墙描述</label>
		<textarea rows="3" class="form-control" placeholder="不能以 http://和 https://开头" type="text" id="newfwDescription"></textarea>
	</div>
	<button class="btn btn-primary" onclick="submitFw()">确定</button>
	<button class="btn btn-default" onclick="closeFacebox()">取消</button>
</div>
</body>
</html>
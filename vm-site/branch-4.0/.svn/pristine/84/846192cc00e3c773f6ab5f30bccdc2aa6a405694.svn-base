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
	<h4>编辑信息</h4>
	<div class="form-group">
		<label for="fwName">防火墙名称</label>
		<input class="form-control" type="text" id="fwName" value="${param.securityGroupName}">
	</div>
	<div class="form-group">
		<label>防火墙描述</label>
		<textarea class="form-control" type="text" id="fwDescription" rows="3">${param.securityGroupDescription}</textarea>
	</div>
	<button class="btn btn-primary" onclick="modFw('${param.securityGroupId}','${param.fwuserEmail}')">确定</button>
	<button class="btn btn-default" onclick="modfiyFWModal.modal('hide')">取消</button>
</div>
</body>
</html>
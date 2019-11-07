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
    <div>
        <input type="radio" id="portStyle1" name="radioPort" value="常用端口" checked="checked"
               onclick="commonPort()">
        <label for="portStyle1">常用端口</label>
        <input type="radio" id="portStyle2" name="radioPort" value="自定义端口"
               onclick="customPort()">
        <label for="portStyle2">自定义端口</label>
    </div>
    <div id="commonPort">
        <div class="form-group">
            <label for="comPort">常用端口</label>
            <select class="form-control" id="comPort">
                <option>TCP|SSH(22)</option>
                <option>TCP|HTTP(80)</option>
                <option>UDP|DNS(53)</option>
                <option>ICMP|(无端口)</option>
                <option>TCP|MYSQL(3306)</option>
            </select>
        </div>
        <div class="form-group">
            <label for="allowedIP_comm">可访问IP （形如192.168.0.0/16）</label>
            <input class="form-control" type="text" id="allowedIP_comm">
        </div>

    </div>
    <div id="customPort" class="hidden">
        <div class="form-group">
            <label>开放协议</label>
            <select class="form-control" id="protocol">
                <option>TCP</option>
                <option>UDP</option>
            </select>
        </div>
        <div class="form-group">
            <label>起始端口</label>
            <input type="text" id="fromPort" class="form-control">
        </div>
        <div class="form-group">
            <label>截止端口</label>
            <input type="text" id="toPort" class="form-control">
        </div>
        <div class="form-group">
            <label>可访问IP（形如192.168.0.0/16）</label>
            <input type="text" id="allowedIP" class="form-control">
        </div>
    </div>
</div>
<div class='modal-footer'>
    <button class="btn btn-primary" onclick="submitRules('${param.securityGroupId}')">确定</button>
    <button class="btn btn-default" onclick="newRuleModal.modal('hide')">取消</button>
</div>
</body>
</html>
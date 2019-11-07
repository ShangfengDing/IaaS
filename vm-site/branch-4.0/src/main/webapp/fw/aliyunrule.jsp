<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="utf-8">
    <s:include value="../template/_head.jsp"/>
</head>
<body>
<!-- 新建阿里云的规则 -->
<div class="modal-body">
    <form class="form-horizontal">
    	<div class="form-group">
    		<label class="col-md-3 control-label">网卡类型:</label>
    		<div class="col-md-5">
    			<select id='fwnicType' class="form-control" disabled="disabled">
    				<option value="internet">公网</option>
    			</select>
    		</div>
    	</div>
    	<div class="form-group">
    		<label class="col-md-3 control-label">规则类型:</label>
    		<div class="col-md-5">
    			<select id='fwdirection' class="form-control" disabled="disabled">
    				<option value="indirction">入方向</option>
    			</select>
    		</div>
    	</div>
    	<div class="form-group">
    		<label class="col-md-3 control-label">授权策略:</label>
    		<div class="col-md-5">
    			<select id='fwpolicy' class="form-control">
    				<option value="accept">允许</option>
    				<option value="drop">拒绝</option>
    			</select>
    		</div>
    	</div>
    	<div class="form-group">
    		<label class="col-md-3 control-label">协议类型:</label>
    		<div class="col-md-5">
    			<select id='fwIpProtocol' class="form-control">
    				<option value="all">全部</option>
    				<option value="tcp">TCP</option>
    				<option value="udp">UDP</option>
    				<option value="icmp">ICMP</option>
    				<option value="gre">GRE</option>
    			</select>
    		</div>
    	</div>
    	<div class="form-group">
    		<span class="text-danger">*</span><label class="col-md-3 control-label">端口范围:</label>
    		<div class="col-md-5">
    			<input class="form-control" id='fwPortRange' class="form-control" 
    				value="-1/-1" disabled="disabled"></input>
    			<span style='font-size: 10px;'>取值范围为1~65535；例如“1/200”、“80/80”。</span>
    		</div>
    	</div>
    	<div class="form-group">
    		<label class="col-md-3 control-label">授权类型:</label>
    		<div class="col-md-5">
    			<select id='fwpromission' class="form-control" disabled="disabled">
    				<option value="site">地址段访问</option>
    			</select>
    		</div>
    	</div>
    	<div class="form-group">
    		<label class="col-md-3 control-label">授权对象:</label>
    		<div class="col-md-5">
    			<input class="form-control" id='fwSourceCidrIp'></input>
    			<span style="font-size: 10px;">请谨慎设置授权对象，根据授权策略的不同，0.0.0.0/0代表允许或拒绝所有IP的访问。</span>
    			<span style='font-size: 10px;'>
    				<a href="https://help.aliyun.com/document_detail/25475.html?spm=5176.2020520101.0.0.deqYbI" target="_blank" >教我设置</a>
    			</span>
    		</div>
    	</div>
    	<div class="form-group">
    		<label class="col-md-3 control-label">授权对象:</label>
    		<div class="col-md-5">
    			<input type="number" class="form-control" id='fwPriority' value='1'></input>
    			<span style="font-size: 10px;">优先级可选范围为1-100，默认值为1，即最高优先级。</span>
    		</div>
    	</div>
    </form>
</div>
<div class='modal-footer'>
    <button class="btn btn-primary" onclick="submitAliRules('${param.securityGroupId}')">确定</button>
    <button class="btn btn-default" onclick="newRuleModal.modal('hide')">取消</button>
</div>

<script>
//端口由协议决定
	$('#fwIpProtocol').change(function(){
		var fwp = $('#fwIpProtocol').val();
		if((fwp=='icmp')|(fwp=='gre')|(fwp=='all')){
			$('#fwPortRange').val("-1/1");
			$('#fwPortRange').attr('disabled',"disabled");
		}else{
			$('#fwPortRange').val("1/65535");
			$('#fwPortRange').removeAttr('disabled');
		}
	});
</script>
</body>
</html>
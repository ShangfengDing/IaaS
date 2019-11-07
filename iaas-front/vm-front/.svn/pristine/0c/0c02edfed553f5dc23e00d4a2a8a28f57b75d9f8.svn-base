<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal-header">
  <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">&times;</button>
  <h4 class="modal-title">编辑账户</h4>
</div>
<div class="modal-body">
	<input class="hidden" value="<s:property value="appkey.id"/>" id="accountId">
	<div class="form-horizontal">
		<div class="form-group">
			<label for="accountName" class="col-sm-2 control-label">账户名称</label>
			<div class="col-sm-10">
				<input type="text" class="form-control front-no-box-shadow" value="<s:property value="appkey.appname"/>"
					id="accountName">
			</div>
		</div>
		<div class="form-group ">
			<label for="provider" class="col-sm-2 control-label">账户类型</label>
			<div class="col-sm-5">
				<select class="form-control front-no-radius front-no-box-shadow "
					id="provider" name="provider">
					<option value="yunhai">云海</option>
					<option value="aliyun">阿里云</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="appkey_id" class="col-sm-2 control-label">appkey_id</label>
			<div class="col-sm-10">
				<input type="text" class="form-control front-no-box-shadow" value="<s:property value="appkey.appkeyId"/>"
					id="appkey_id">
			</div>
		</div>
		<div class="form-group">
			<label for="appkey_secret" class="col-sm-2 control-label">appkey_secret</label>
			<div class="col-sm-10">
				<input type="text" class="form-control front-no-box-shadow" value="<s:property value="appkey.appkeySecret"/>"
					id="appkey_secret">
			</div>
		</div>
	</div>
</div>
<div class="modal-footer">
	<a href="#" class="btn btn-default" data-dismiss="modal">取消</a><a
		href="javascript:void(0)" onclick="submitCheck()"
		class="btn btn-primary">确定</a>
</div>
<script>
$(function(){
	var options = $("#provider").find("option");
	for(var i=0;i<options.length;i++){
		if($(options[i]).val() == "<s:property value='appkey.provider'/>"){
			$("#provider").val($(options[i]).val());
			break;
		}
	}
})
</script>
<script src="js/account/account.js"></script>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<s:iterator id="appkey" value="appkeyList">
	<div class="col-md-4">
		<div class="panel panel-default front-panel">
			<div class="panel-body">
				<div class="media">
					<div class="media-left">
						<img class="media-object img-circle"
							src="images/<s:property value="#appkey.provider"/>.png"
							alt="App Logo">
					</div>
					<div class="media-body">
						<h4 class="media-heading">
							<s:property value="#appkey.appname" />
						</h4>
						<div class="as-desc">
							<div style="display: inline-block"></div>

						</div>
						<div class="text-right">
							<a href="#" data-toggle="front-modal" class="sum_span_blank"
								data-href="account/editAccount?id=<s:property value="#appkey.id"/>" data-size="modal-md"><span class="glyphicon glyphicon-edit"></span> 编辑</a>
							<a href="javascript:void(0)" onclick="deleteAccount(<s:property value="#appkey.id"/>)" ><span class="glyphicon glyphicon-trash"></span> 删除</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:iterator>
<script>
function deleteAccount(id){
	$.tipModal('confirm', 'warning', '确定要删除此账户？', function(result) {
	    if(result){
	    	$.post("account/deleteAccount",{
	    		id:id,
	        },function(data){
	            $("#accountInfo").html("");
	            $('.modal').modal('hide')
	            $("#accountInfo").html(data);
	    	});
	    }
	})
	
}
</script>
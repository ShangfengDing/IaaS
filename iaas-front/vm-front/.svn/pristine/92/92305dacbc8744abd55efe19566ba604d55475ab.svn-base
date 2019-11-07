<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="modal-body">
	<input id="imgappname" value="${param.appname}" class="hidden">
	<input id="imgId" value="${param.id}" class="hidden">
	<input id="imgreionId" value="${param.regionId}" class="hidden">
	<div class="form-group">
		<label for="newimgName">名称</label>
		<input class="form-control" value="${param.imgname}" type="text" id="newimgName">
		<label class="rename" id="img-name-label">长度限制在2-20个字符内</label>
	</div>
	<div class="form-group">
		<label for="newimgAccount">账户信息</label>
		<textarea id="newimgAccount" class="form-control front-no-box-shadow" rows="2" style="resize:none;">${param.imageAccount}</textarea>
		<%--<input class="form-control" type="text" id="newimgPassword" value="${param.password}">--%>
	</div>
	<div class="form-group">
		<label for="newimgSoftware">已装软件</label>
		<textarea rows="2" class="form-control" style="resize:none;" type="text" id="newimgSoftware">${param.software}</textarea>
	</div>
	<div class="form-group">
		<label for="newimgDes">描述</label>
		<textarea rows="3" class="form-control" type="text" style="resize:none;" id="newimgDes">${param.imgdes}</textarea>
		<label class="rename" id="img-des-label">长度限制在2-100个字符内</label>
	</div>
	<button class="btn btn-primary" onclick="editImg()">确定</button>
	<button class="btn btn-default" onclick="closeEditModal()">取消</button>
</div>

<script>
	function editImg() {
		var imageName = $('#newimgName').val();
		var imageDes = $('#newimgDes').val().trim();
		var imageAccount = $('#newimgAccount').val().trim();
		var imageSoftware = $('#newimgSoftware').val().trim();
		var appname = $('#imgappname').val();
		var imageId = $('#imgId').val();
		var regionId = $('#imgreionId').val();
		if (!editFormat(imageName, imageDes)) {
			return;
		} else {
			$.ajax({
				type: "POST",
				url: "image/editimage",
				data: {
					appname: appname,
					regionId: regionId,
					imageId: imageId,
					imageName: imageName,
					imageDes: imageDes,
                    imageAccount: imageAccount,
					imageSoftware: imageSoftware
				},
				success: function (data) {
					var editresult = data.result;
					if (editresult == "success") {
						$.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '编辑云镜像成功'});
					} else {
						$.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: editresult});
					}
					$("#"+imageId).text(imageName);
					closeEditModal();
				}
			});
		}
	}

	function editFormat(name, description) {
		if (name.length == 0) {
			$.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得为空'});
			return false;
		} else if (name.length > 20) {
			$.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得超过20字'});
			return false;
		}
		if (description.length == 0) {
			$.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '描述不得为空'});
			return false;
		} else if (description.length > 100) {
			$.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '描述不得超过100字'});
			return false;
		}
		return true;
	}
</script>

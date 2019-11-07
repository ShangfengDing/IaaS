$(function() {
	//var array = new Array("系统管理", "用户管理", "主机管理", "硬盘管理", "财务管理", "运行管理");
	var array = new Array("系统管理", "用户管理", "财务管理", "运行管理", "操作管理");
	var flag = "";
	$.each(array,function(index, value){
		$("input[name='"+index+"']").each(function() {
			this.checked = true;
			$("input[name='"+index+"']").attr('disabled',true);
			$("input[name='"+value+"']").each(function() {
				this.checked = true;
				$("input[name='"+value+"']").attr('disabled',true);
			});
		});
	});
}); 
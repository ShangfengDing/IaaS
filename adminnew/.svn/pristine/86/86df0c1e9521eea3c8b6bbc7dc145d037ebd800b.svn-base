function delAcGroup(obj,acGroupId){
	if(confirm("确认删除该群组吗？")){
		$.post(
				"group/delAcGroup",
				{
					acGroupId:acGroupId
				},
				function(data){
					if(data.result == "success"){
						fillTipBox("success","删除成功");
						location.reload();
					} else if(data.result == "using"){
						fillTipBox("using","该群组正在被使用，无法删除");
					} else {
						fillTipBox("error","该群组已被使用，无法删除！");
					}
				}
		);
	}
}
/*
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
*/

function delAcGroup(obj,acGroupId){
    $.tipModal('confirm', 'info', '确认删除该群组吗？', function (result) {
    	if(result==true) {
                  $.post(
                        "group/delAcGroup",
                        {
                            acGroupId:acGroupId
                        },
                        function(data){
                            if(data.result == "success"){
                                //$.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'删除成功'});
                                //location.reload();
                                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'删除成功'});
                                setInterval(function(){location.reload()},1000);
                            } else if(data.result == "using"){
                                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'该群组正在被使用，无法删除'});
                            } else {
                                $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'该群组已被使用，无法删除！'});
                            }
                        }
                    );
        }
    });
 }
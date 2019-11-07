$(function(){
	getStyle();
});

function submitCheck(){
	var id = $("#id").val();
	var roleid = $("#roleselect").val();
    $.ajax({
        type:"post", 
        url:"runtime/modAdminRoleAction", 
        data:{
            id : id,
            roleId : roleid
        },
        success:function(data){
            if(data.result == "success"){
            	facebox_close();
            	location.reload();
                fillTipBox("success","角色修改成功");
            }else{
            	facebox_close();
                hideLoading();
                fillTipBox("error","角色修改失败");
            }
        }
    });
}

function cancle(){
	facebox_close();
	return false;
}

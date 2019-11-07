$(function(){
    //getStyle();
});

function cancel() {
    location.reload();
    return false;
}

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
               // facebox_close();
                //location.reload();
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'角色修改成功'});
            }else{
                //facebox_close();
               // hideLoading();
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'角色修改失败'});
            }
        }
    });
}


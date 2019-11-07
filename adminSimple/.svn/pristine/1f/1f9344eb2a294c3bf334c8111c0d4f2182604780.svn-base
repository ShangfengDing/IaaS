$(function(){
    //getStyle();
});

function cancel() {
    location.reload();
    return false;
}
function submitCheck(){
    var uid = $("#uid").val();
    var groupId = $("#groupId").val();
    var a = document.getElementById("group_detail"+uid);
    $.ajax({
        type:"post",
        url:"user/changeAuthority",
        data:{
            uid: uid,
            groupId: groupId
        },
        success:function(data){
            if(data.result == "success"){
                //facebox_close();
               // hideLoading();
                //a.href = "group/showAcGroup?acGroupId=" + groupId;
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改群组成功'});
                setInterval(function(){location.reload()},1000);
            }else{
                //facebox_close();
                //hideLoading();
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'修改群组失败'});
            }
        }
    });
}


$(function(){

});

function cancel() {
    location.reload();
    return false;
}

function submitCheck(){
    var id = $("#id").val();
    var name = $("#newname").val();
    var password1 = $("#password1").val();

    if (name == "") {
        $("#error_name").html("不得为空");
        return false;
    }else if(name.length > 20) {
        $("#error_name").html("1~20个字");
        return false;
    } else {
        $("#error_name").html("");
    }

    if (password1 == "") {
        $("#error").html("不得为空");
        return false;
    } else {
        $("#error").html("");
    }

    $.ajax({
        type:"post",
        url:"runtime/changePassword",
        data:{
            id : id,
            name : name,
            password : password1
        },
        success:function(data){
            if(data.result == "success"){
                //facebox_close();
                //hideLoading();
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'重置密码成功'});
            }else{
                //facebox_close();
                //hideLoading();
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'重置密码失败'});
            }
        }
    });
}

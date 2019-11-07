function cancel() {
    location.reload();
    return false;
}

function isPhone(phone) {
    var regTel = /^(0[0-9]{2,3}\-)?([0-9]{7,8})+(\-[0-9]{1,4})?$/;;
    var regMobile = /^(13[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$/;
    if(phone.match(regTel) || phone.match(regMobile))
        return true;
    else
        return false;
}


function isEmail(str){
    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    return reg.test(str);
}

function submit() {
    var password = $("#password").val().trim();
    var email = $("#email").val().trim();
    var groupId = $("#groupselect").val();
    //$(".errors").html("");

    $("#error_pass").html("");
    $("#error_email").html("");

    if(password == "") {
        $("#error_pass").html("不得为空");
        return false;
    } else {
        $("#error_pass").html("");
    }

    if (email == "") {
        $("#error_email").html("不得为空");
        return false;
    } else if (!isEmail(email)) {
        $("#error_email").html("邮件格式不正确");
        return false;
    } else {
        $("#error_email").html("");
    }


    $.ajax({
        url:"user/AddUser",
        type:"post",
        data:{password:password, email:email,groupId:groupId},
        success:function(data) {
            if(data.result=="success"){
                //facebox_close();
                //hideLoading();
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'添加用户成功！！'});
                setInterval(function(){ location.reload();},1000);
            }else{
                // facebox_close();
                //hideLoading();
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'添加用户失败！'});
            }
        },
        error:function(data){
            //facebox_close();
            //hideLoading();
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'添加用户失败！'});
        }
    });
}
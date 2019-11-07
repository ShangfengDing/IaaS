$(function() {
    //var array = new Array("系统管理", "用户管理", "主机管理", "硬盘管理", "财务管理", "运行管理");
    var array = new Array("系统管理", "用户管理", "财务管理", "运行管理", "操作管理");
    var flag = "";
    $.each(array,function(index, value){
        $("input[name='"+index+"']").click(function() {
            if (this.checked) {
                $("input[name='"+value+"']").each(function() {
                    this.checked = true;
                });
            } else {
                $("input[name='"+value+"']").each(function() {
                    this.checked = false;
                });
            }
        });

        $("input[name='"+value+"']").click(function(){
            $("input[name='"+value+"']").each(function() {
                if (this.checked) {
                    flag += "1";
                } else {
                    flag += "";
                }
            });
            if (flag.length == $("input[name='"+value+"']").length) {
                $("input[name='"+index+"']").attr("checked", true);
            } else {
                $("input[name='"+index+"']").attr("checked", false);
            }
            flag = "";
        });
    });
    var roleid = $("#roleid").val().trim();
    $.ajax({
        url:"runtime/getRole",
        type:"post",
        data:{
            roleid:roleid
        },
        success:function(data) {
            var list =  JSON.parse(JSON.stringify(data.adminResources));
            $.each(list, function(i, value){
                $("#"+value.id+"").attr("checked", true);
            });
            $.each(array,function(index, value){
                var flag = "";
                $("input[name='"+value+"']").each(function() {
                    if (this.checked) {
                        flag += "1";
                    } else {
                        flag += "";
                    }
                });
                if (flag.length == $("input[name='"+value+"']").length) {
                    $("input[name='"+index+"']").attr("checked", true);
                } else {
                    $("input[name='"+index+"']").attr("checked", false);
                }
                flag = "";
            });
        }
    });
});

function cancel() {
    location.reload();
    return false;
}

function submit() {
    var name = $("#newname").val().trim();
    var roleid = $("#roleid").val().trim();

    if(name == "") {
        $("#error_name").html("不得为空");
        return false;
    }else if(name.length > 20) {
        $("#error_name").html("1~20个字");
        return false;
    } else {
        $("#error_name").html("");
    }

    var result = "";
    //var array = new Array("系统管理", "用户管理", "主机管理", "硬盘管理", "财务管理", "运行管理");
    var array = new Array("系统管理", "用户管理", "财务管理", "运行管理", "操作管理");
    $.each(array,function(index, value){
        $("input[name='"+value+"']:checked").each(function() {
            result += this.value+",";
        });
    });
    $.ajax({
        url:"runtime/modRole",
        type:"post",
        data:{
            roleid:roleid,
            name:name,
            value:result
        },
        success:function(data) {
            if(data.result=="success"){
                //facebox_close();
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改管理组成功！'});
                //location.reload();
                //setInterval(function(){location.reload()},1000);
            }else{
               //facebox_close();
               // hideLoading();
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'修改管理组失败！'});
            }
        },
        error:function(data){
            //facebox_close();
           // hideLoading();
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'修改管理组失败！'});
        }
    });
}
function is_num(num){
    var reg = /^\d+$/;
    if(num.match(reg)){
        return true;
    } else {
        return false;
    }
}

function cancel() {
    location.reload();
    return false;
}

function submit() {
    var name = $("#name").val().trim();
    var description = $("#description").val().trim();
    var max_number_of_instance = $("#max_number_of_instance").val();
    var max_number_of_disk = $("#max_number_of_disk").val();
    var max_number_of_backup = $("#max_number_of_backup").val();
    var max_number_of_snapshot = $("#max_number_of_snapshot").val();
    var publish_image = document.getElementsByName("publish_image");
    var select_cluster = document.getElementsByName("select_cluster");
    var selected_aggregates = document.getElementsByName("selected_aggregates");
    var aggregates = new Array();
    $("#error1").html("");
    $("#error2").html("");
    $("#error3").html("");
    $("#error4").html("");
    $("#error5").html("");
    $("#error6").html("");
    if(name == "") {
        $("#error1").html("不得为空");
        return false;
    }else if(name.length > 20) {
        $("#error1").html("1~20个字");
        return false;
    } else {
        $("#error1").html("");
    }

    if(description == "") {
        $("#error2").html("不得为空");
        return false;
    }else if(description.length > 50) {
        $("#error2").html("1~50个字");
        return false;
    } else {
        $("#error2").html("");
    }

    if(max_number_of_instance == "") {
        $("#error3").html("不得为空");
        return false;
    }else if(!is_num(max_number_of_instance)) {
        $("#error3").html("格式错误");
        return false;
    }else {
        $("#error3").html("");
    }

    if(max_number_of_disk == "") {
        $("#error4").html("不得为空");
        return false;
    }else if(!is_num(max_number_of_disk)) {
        $("#error4").html("格式错误");
        return false;
    }else {
        $("#error4").html("");
    }

    if(max_number_of_backup == "") {
        $("#error5").html("不得为空");
        return false;
    }else if(!is_num(max_number_of_backup)) {
        $("#error5").html("格式错误");
        return false;
    }else {
        $("#error5").html("");
    }
    if(max_number_of_snapshot == "") {
        $("#error6").html("不得为空");
        return false;
    }else if(!is_num(max_number_of_snapshot)) {
        $("#error6").html("格式错误");
        return false;
    }else {
        $("#error6").html("");
    }

    var selected = false;
    for(var i=0 ;i<selected_aggregates.length; i++) {
        if(selected_aggregates[i].checked) {
            selected = true;
        }
    }
    if(!selected) {
        $("#error7").html("至少选择一个集群");
        return false;
    } else {
        $("#error7").html("");
    }



    for(var i=0 ;i<selected_aggregates.length; i++) {
        if(selected_aggregates[i].checked) {
            aggregates.push(selected_aggregates[i].value);
        }
    }

    $.post("group/newAcGroup",
        {	name:name,
            description:description,
            max_number_of_instance:max_number_of_instance,
            max_number_of_disk:max_number_of_disk,
            max_number_of_backup:max_number_of_backup,
            max_number_of_snapshot:max_number_of_snapshot,
            publish_image:publish_image[0].checked,
            select_cluster:select_cluster[0].checked,
            selected_aggregates:aggregates.toString()
        },
        function(){
            $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'添加成功'});
            setInterval(function(){location.reload()},1000);
            //location.reload();
        }
    );
}
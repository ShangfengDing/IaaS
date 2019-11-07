
    $.ajax({
        type:"post",
        url:"user/usercount",
        async:true,
        success:function(data) {
            // $("#usercount").html("<p style=\"font-size:50px;text-align: center\">"+
            //     data+"</p>")
            if(data!=null){
            $("#usercount").html(data+"/");
            }else{
                $("#usercount").html(0+"/");
            }
        },
    })

    $.ajax({
        type:"post",
        url:"group/countGroup",
        success:function(data) {
            // $("#usergroup").html("<p style=\"font-size:50px;text-align: center\">"+
            //     data+"</p>")
            if(data!=null) {
                $('#usergroup').html(data);
            }else{
                $('#usergroup').html(0);
            }
        },
    })

    $.ajax({
        type:"post",
        url:"system/countVm",
        data: {
        },
        success:function (data) {
            if(data.netcount!=null) {
                $("#netcount").html("<p style=\"font-size:50px;text-align: center\">" +
                    data.netcount + "</p>");
            }else{
                $("#netcount").html("<p style=\"font-size:50px;text-align: center\">" +
                    "0" + "</p>");
            }
            if(data.diskcount!=null) {
                $("#diskcount").html("<p style=\"font-size:50px;text-align: center\">" +
                    data.diskcount + "</p>");
            }else{
                $("#diskcount").html("<p style=\"font-size:50px;text-align: center\">" +
                    "0" + "</p>");
            }
            // $("#vm1").html("<p style=\"font-size: 15px;text-align: center;\"><strong>"+data.vm1+"</strong></p>");
        }

    })

    // $.ajax({
    //     type:"post",
    //     url:"system/findNodeByUUID",
    //     data: {
    //         uuid:"525400A4F506"
    //     },
    //     success:function (data) {
    //         $("#service1").html(data.host.name);
    //         $("#service2").html(data.host.name);
    //         $("#service3").html(data.host.name);
    //         $("#service4").html(data.host.name);
    //         $("#service5").html(data.host.name);
    //     }
    // })
    //
    // $.ajax({
    //     type:"post",
    //     url:"system/findNodeByUUID",
    //     data: {
    //         uuid:"F8BC124230C4"
    //     },
    //     success:function (data) {
    //         $("#service6").html(data.host.name);
    //     }
    // })
    var uuids=[];
    $.get('system/findInfrastructure',{

    },function(data){
        var services=data.acServices;
        for(i=0;i<services.length;i++){
            if(services[i].serviceStatus.toString()=='RUNNING'){
                $('#'+services[i].type.toString()).attr("src","images/service_green.png");
            }else if(services[i].serviceStatus.toString()=='STOPED'){
                $('#'+services[i].type.toString()).attr("src","images/service_red.png");
            }else if(services[i].serviceStatus.toString()=='INIT'){
                $('#'+services[i].type.toString()).attr("src","images/service_yellow.png");
            }
            $('#'+services[i].type.toString()+"url").attr('href','system/nodeDetail?uuid='+services[i].hostUuid+'&button=2');
            getServiceName(services[i].hostUuid,services[i].type.toString());
        }

    });

    $.get('system/countCluster',{

    },function(data){
        if(data.clusterList!=null) {
            $('#clusternum').html("<p style=\"font-size:50px;text-align: center\">" +
                data.clusterList.length + "</p>");
        }else{
            $('#clusternum').html("<p style=\"font-size:50px;text-align: center\">" +
                "0" + "</p>");
        }
    })
    $.ajax({
            type: "post",
            url: "system/countNode",
            data: {type:'COMPUTE_NODE'},
            success: function (data) {
                $('#servicenum').html("<p style=\"font-size:50px;text-align: center\">"+
                    data.hostList.length+"</p>");
            }
        }
    )

    function getServiceName(uuid,type){
        $.ajax({
            type:"post",
            url:"system/findNodeByUUID",
            data: {
                uuid:uuid
            },
            success:function (data) {
                $('#'+type+'name').html(data.host.name);
            }
        })

    }

$.get('system/getZone',{},function(data){console.log(data);
        if(data.vmZone!=null){
            $("#regionId").html(data.vmZone.regionId);
            $('#zoneId').html(data.vmZone.zoneId);

        }
})

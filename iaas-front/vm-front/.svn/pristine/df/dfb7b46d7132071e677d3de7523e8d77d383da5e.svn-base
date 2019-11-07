var imageModal;
var createImageloading;
//创建镜像
function showLoading() {
    createImageloading = $.showLoading('show') // 全屏loading样式--出现
}
function hideLoading() {
    if (createImageloading != null) {
        $.showLoading('reset')
    }
}
function imageSubmitCheck(appname, regionId, zoneId, snapShotId, serverId, volumeId, userEmail) {

    var str = $("#imageName").val();
    if (str == "") {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入名称'})
        return;
    } else if (str.length > 20) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '最多输入20个字'})
        return;
    }
    var str1 = $("#imageDescription").val();
    if (str1 == "") {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入描述'})
        return;
    } else if (str1.length > 100) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '最多输入100个字'})
        return;
    }
    var software = $('#imagesoftware').val();
    var password = $('#imagePassword').val();
    var distribution = $("#distributions").find("option:selected").val();
    var des = $("#imageDescription").val().replace(new RegExp("\\n", "g"), "；");
    $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: '开始发布模板，所需时间较长，请耐心等待'})
    facebox_close();
    $.ajax({
        async:true,
        type:"POST",
        url:"vm/newimg",
        data:{
            appname: appname,
            regionId: regionId,
            zoneId: zoneId,
            snapshotId: snapShotId,
            serverId: serverId,
            volumeId: volumeId,
            imageName: $("#imageName").val(),
            imageDescription: des,
            distribution: distribution,
            imageSoftware: software,
            imagePassword: password,
            userEmail: userEmail
        },
        success:function(data){
            console.log(data)
            if (data.result == "success") {
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'模板发布成功，在云镜像中查看'})
            } else {
                $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:data.result})
            }
        },
        error:function(data){
            $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:data.result})
            facebox_close();
        }
    });
}

//云海模板的发布
function createImage( regionId, zoneId, snapShotId, serverId, volumeId, userEmail) {
    var href = 'vm/newimg.jsp' +
        '?appname=' + vdAppname +
        '&regionId=' + regionId +
        '&zoneId=' + zoneId +
        '&snapshotId=' + snapShotId +
        '&serverId=' + serverId +
        '&volumeId=' + volumeId +
        "&userEmail=" + userEmail;
    imageModal = $.frontModal({
        size: 'modal-md',
        title: '发布模板',
        href: href
    }).on('shown.bs.modal', function () {

    });
}
//阿里云详情页，点击发布模板会提示创建快照，然后再发布模板
function alertAliImage() {
    $.tipModal('alert', 'warning', '请先系统盘创建快照，然后基于快照创建镜像模板!');
}


function facebox_close() {
    if (imageModal != null) {
        imageModal.modal('hide')
    }

}


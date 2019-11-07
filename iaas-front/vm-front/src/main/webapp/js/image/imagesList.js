//公共操作
var souType = null;
var imageOwnerAlias = null;
//获取appName
$(function () {
    $('#yun_img_search').hide();
    getAppname(changeAppname);        //changeAppName 是改变appname后引用的参数
});

function changeAppname() {
    $("#back_divPage").html("");
    $('#yun_img_search').removeClass('hidden');
    $('#yun_img_search').hide(1);
    $("#image-content").load("template/_loading_style_1.jsp");
    $("#selectRegion").attr("disabled", true);
    var provider = $('#appproviderEn').text();
    var appname = $('#appnamemenu').text();
    $.ajax({
        url: 'common/getRegionId',
        type: 'GET',
        data: {
            appname: appname
        },
        success: function (result) {
            var json = eval(result);
            $("#selectRegion").empty();
            $.each(json, function (index) {
                var region = json[index];
                var item = null;
                if (region.localName == null) {
                    item = "<option value=\'" + region.regionId + "\'>" + region.regionId + "</option>"
                } else {
                    item = "<option value=\'" + region.regionId + "\'>" + region.localName + "</option>"

                }
                $("#selectRegion").append(item);
            });
            $("#selectRegion").attr("disabled", false);
            if (provider == "aliyun") {
                $('#yunhai_img_search').addClass('hidden');
                $("#select-content").load("image/aliyun/aliyun_image_search_bar.jsp");
            } else {
                $('#yunhai_img_search').remove('hidden');
                $("#select-content").html("");
            }
            getImages();
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取地域信息失败，请刷新再试'});
        }
    });
}
//搜索隐藏与显示
function exsearch() {
    $('#yun_img_search').slideToggle();
}

//通过提供商和regionId即可获取列表
function getImages(page) {
    $("#back_divPage").html("");
    $("#image-content").load("template/_loading_style_1.jsp");
    var appname = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    if(page==null){
        page=1;
    }
    $.ajax({
        type: 'GET',
        url: 'image/getImagesListPage',
        data: {
            appname: appname,
            regionId: regionId,
            page:page,
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云镜像列表成功'});
            $("#image-content").html(data);
            var totalPage = $("#image-total-page").val();
            if(totalPage!=null){
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getImages"));
            }
        },
        error: function (data) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云镜像列表失败'});
        }
    });
}

//————————————————云海云镜像精确查找——————————————
//通过 image 和 iso 来进行筛选
function changeSouType(type){
    souType = type;
    imgshow();
}
function imgshow(page) {
    $("#back_divPage").html("");
    $("#image-content").load("template/_loading_style_1.jsp");
    var appname = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    if(page==null){
        page=1;
    }
    $.ajax({
        type: 'GET',
        url: 'image/getImagesListPage',
        data: {
            appname: appname,
            regionId: regionId,
            page:page,
            souType:souType
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云镜像列表成功'});
            $("#image-content").html(data);
            var totalPage = $("#image-total-page").val();
            $("#back_divPage").html($.getDivPageHtml(page, totalPage, "imgshow"));

        },
        error: function (data) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云镜像列表失败'});
        }
    });
}
//——————————————————云海镜像查找————————————————————
function imagesearch(type){
    page = 1;
    souType = type;
    imageKeyword = $('#imagekeyword').val().trim();
    appname = $('#appnamemenu').text();
    regionId = $('#selectRegion').val();
    var imgtype = $('#yunhai-imgtype .active').attr("data-group");
    var imgOsType = $('#yunhai_ostype').val();
    if(outside==true){
        outside=false;
        imageKeyword=''
        imgtype=selectImageType;
        imgOsType='';
    }
    // var software = $('#imageSoftware').val();
    $("#back_divPage").html("");
    $("#image-content").load("template/_loading_style_1.jsp");
    $.ajax({type:"GET",
        url:'image/getImagesListPage',
        data:{skeyword:imageKeyword,
            appname:appname,
            regionId:regionId,
            page:page,
            imgType:imgtype,
            imgOsType:imgOsType,
            // software:software,
            souType:souType
        },
        success:function(data){
            var totalPage = $("#image-total-page").val();
            if (typeof(totalPage)!='undefined') {
                $("#image-content").html(data);
                var totalPage = $("#image-total-page").val();
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "imgshow"));
            } else {
                $("#image-content").html(data);
            }
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云镜像列表成功'});
        },
        error: function (data) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云镜像列表失败'});
        }
    });
}

function editYunhaiImg(imageId,imageName,imageDes, account, software) {
    var appname = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    var edithref = "image/yunhai/edit_yunhai_img.jsp?appname="+appname+"&id="+imageId+"&imgdes="+imageDes+
        "&imgname="+imageName+"&regionId="+regionId+"&imageAccount="+account+"&software="+software;
    editImgModal = $.frontModal({
        size: 'modal-md',
        title: '编辑云镜像',
        href: edithref
    }).on('show.bs.modal',function () {
        
    })
}

function publishYunhaiImg(imageId) {
    $.fillTipBox({type:'info', icon:'glyphicon-info-sign', content:'将镜像发布为群组共享镜像需要等待一段时间'})
    var appname = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    $.ajax({
        type: "POST",
        url: "image/publishImage",
        data: {
            appname: appname,
            regionId: regionId,
            imageId: imageId
        },
        success: function (data) {
            var publishresult = data.result;
            if (publishresult == "success") {
                $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '发布云镜像成功'});
            } else {
                $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: publishresult});
            }
        }
    });

}

function closeEditModal() {
    editImgModal.modal('hide');
}

//
//function deleteimage() {
//    $.ajax({
//        type: 'POST',
//        url: 'image/deleteimage',
//        data: {
//            providerEn: imgprovider,
//            userEmail: imguEmail,
//            regionId: imgregionId,
//            imageId: imgId
//        },
//        success: function (data) {
//            switch (data.result) {
//                case "0":
//                    $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '删除成功！'});
//                    break;
//                case "1":
//                    $.fillTipBox({type: 'warning', icon: 'glyphicon-exclamation-sign', content: '参数出错！'});
//                    break;
//            }
//        }
//    });
//}

//————————————————阿里云云镜像精确查找——————————————
function changeSearchType() {
    var type = $("#search-type").val();
    var text = $("#search-type option:selected").text();
    $("#image-search-content").attr("placeholder", "请输入" + text + "进行精确查询");
}
//根据输入的信息进行精确查找
function searchAliImagesBySearchType(page) {
    if(page==null){
        page=1;
    }
    var searchType = 'image-name';
    var searchKey = $("#image-search-content").val();
    $("#back_divPage").html("");
    $("#image-content").load("template/_loading_style_1.jsp");
    var provider = $("#selectAppname").val();
    var appname = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    var imgType = $('#aliyun-imgtype .active').attr("data-group");
    $.ajax({
        type: 'GET',
        url: 'image/getImagesListPage',
        data: {
            appname: appname,
            regionId: regionId,
            searchType: searchType,
            searchKey: searchKey,
            imageOwnerAlias:imgType,
            page:page
        },
        success: function (data) {
            var result = $("#image-result").val();
            if (result == 'success') {
                $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云镜像列表成功'});
                $("#image-content").html(data);
            } else if (result == '0') {
                $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: '没有查询到数据'})
                $("#image-content").html(data);
            } else if (result == 'error') {
                $("#image-content").html(data);
            } else {
                $("#image-content").html(data);
            }
            var totalPage = $("#image-total-page").val();
            if(totalPage!=null){
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "searchAliImagesBySearchType"));
            }
        },
        error: function (data) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云镜像列表失败'});
        }
    });
}
//改变镜像拥有者
function changeImageOwnerAlias(imageOwner){
    imageOwnerAlias = imageOwner;
    searchAliImagesByOwnerAlias();
}
//通过镜像拥有者来查找
function searchAliImagesByOwnerAlias(page){
    if(page==null){
        page=1;
    }
    $("#back_divPage").html("");
    $("#image-content").load("template/_loading_style_1.jsp");
    var provider = $("#selectAppname").val();
    var appname = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    $.ajax({
        type: 'GET',
        url: 'image/getImagesListPage',
        data: {
            appname: appname,
            regionId: regionId,
            imageOwnerAlias:imageOwnerAlias,
            page:page
        },
        success: function (data) {
            var result = $("#image-result").val();
            if (result == 'success') {
                $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云镜像列表成功'});
                $("#image-content").html(data);
            } else if (result == '0') {
                $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: '没有查询到数据'})
                $("#image-content").html(data);
            } else if (result == 'error') {
                $("#image-content").html(data);
            } else {
                $("#image-content").html(data);
            }
            var totalPage = $("#image-total-page").val();
            if(totalPage!=null){
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "searchAliImagesByOwnerAlias"));
            }

        },
        error: function (data) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云镜像列表失败'});
        }
    });
}

function cancelSearch() {
    $("#imagekeyword").val("");
    $("#yunhai_ostype").val("ALL");
}

//select image type
function changeImage(id){
    selectImageType=id;
    outside=true;
    imagesearch('keyword');
    if(id=='PUBLIC')
        $('#imagemenu').html('<span class="glyphicon glyphicon-star"></span>&nbsp;公共镜像');
    else if(id=='GROUP')
        $('#imagemenu').html('<span class="glyphicon glyphicon-asterisk"></span>&nbsp;共享镜像');
    else
        $('#imagemenu').html('<span class="glyphicon glyphicon-king"></span>&nbsp;自定义镜像');
}
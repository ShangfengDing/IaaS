//————————————公共操作————————————————————
$(document).ready(function () {
    $('#select-content').hide();
    $("#shot-content").load("template/_loading_style_1.jsp");//加载提示
    getAppname(changeAppname);
});

//获取地域
function changeAppname() {
    $('#select-content').removeClass('hidden');
    $('#select-content').hide(1);
    $("#shot-content").load("template/_loading_style_1.jsp");
    $("#selectRegion").attr("disabled", true);
    var appName = $('#appnamemenu').text();

    $.ajax({
        url: 'common/getRegionId',
        type: 'GET',
        data: {
            appname: appName
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
            getHds();
        }
    });
}

//获取磁盘列表
function getHds(page) {
    if (page == null) {
        page = 1;
    }
    $("#select-content").empty();
    $("#hd-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").empty();
    var appName = $('#appnamemenu').text();
    var provider = $('#appproviderEn').text();
    var regionId = $("#selectRegion").val();
    $.ajax({
        url: 'hd/hd_list',
        type: 'GET',
        data: {
            appName: appName,
            regionId: regionId,
            page: page
        },
        success: function (data) {

            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云硬盘列表成功'});

            if (provider == 'yunhai') {
                $("#select-content").load('hd/yunhai/yunhai_hd_search_bar.jsp');
            } else if (provider == 'aliyun') {
                $("#select-content").load('hd/aliyun/aliyun_hd_search_bar.jsp');
            } else {
                $("#select-content").empty();
            }

            $("#hd-content").html(data);
            var totalPage = $("#hd-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getHds"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云硬盘列表失败'});
        }
    });

}


//获取appName,没有用
function getAppName(){
    $.ajax({
        type: "POST",
        url: "common/getAppName",
        success: function (data) {
            $('#selectAppName').html('');
            for (var i in data) {
                if(i == 0){
                    var node = $("<option value='" + data[i].providerEn + "' selected >" + data[i].appName + "</option>");
                }else {
                    var node = $("<option value='" + data[i].providerEn + "'>" + data[i].appName + "</option>");
                }

               $('#selectAppName').append(node);
            }
            getRegions();
        }
    })
}

//搜素的toggle切换。
function exsearch() {
    $('#select-content').slideToggle();
}

//申请云硬盘，区分厂商
function applydisk() {
    var providerEn = $('#appproviderEn').text();
    var vmappname = $('#appnamemenu').text();
    switch (providerEn) {
        case 'yunhai':
            window.location.href="hd/newhd.jsp?menu=newhd&appname="+vmappname;
            break;
        case 'aliyun':
            window.location.href="hd/aliyun/aliyun_buy_hd.jsp?appname="+vmappname;
            break;
    }
}

//————————————云海硬盘相关的操作————————————————————

var souType = null;
//选择云海磁盘状态
function chooseDiskStatus(status) {
    souType = status;
    searchByDiskStatus();
}
//根据选择的磁盘状态进行查询
function searchByDiskStatus(page) {
    if (page == null) {
        page = 1;
    }
    $("#hd-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").html("");//empty();
    var appName = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    $.ajax({
        url: 'hd/hd_list',
        type: 'GET',
        data: {
            appName: appName,
            regionId: regionId,
            souType: souType,
            page: page
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云硬盘列表成功'});
            $("#hd-content").html(data);
            var totalPage = $("#hd-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage,"searchByDiskStatus"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云硬盘列表失败'});
        }
    });
}
//隐藏或者显示云海的搜索框
// function showHdSearchBox() {
//     $("#hd-search").toggleClass('hidden');
// }
//根据胡输入的信息进行精确查找
function searchByDetailInfo(page) {
    souType = 'keyword';
    var yunHaiDiskType = $("#disk-type").val();
    var yunHaiKeyword = $("#hd-keyword").val();
    var yunHaiDiskAttachStatus = $('#yunhai-hdstatus .active').attr('data-group');
    if (page == null) {
        page = 1;
    }
    $("#hd-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").empty();

    var regionId = $("#selectRegion").val();
    var appName = $('#appnamemenu').text();

    $.ajax({
        url: 'hd/hd_list',
        type: 'GET',
        data: {
            appName:appName,
            regionId: regionId,
            souType: souType,
            yunHaiKeyword: yunHaiKeyword,
            yunHaiDiskAttachStatus: yunHaiDiskAttachStatus,
            yunHaiDiskType: yunHaiDiskType,
            page: page
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云硬盘列表成功'});
            $("#hd-content").html(data);
            var totalPage = $("#hd-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "searchByDetailInfo"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云硬盘列表失败'});
        }
    });
}

//进入新建硬盘页面
function newhdPage(){




}


//————————————阿里云硬盘相关的操作————————————————————

//根据输入的信息进行精确查找
function searchBySearchKeyWord(page){
    souType = 'keyword';
    var searchType = "disk-id";//搜索硬盘名称
    var searchKeyWord = $("#hd-search-keyword").val();  //关键字
    var aliYunDiskCategory = $("#disk-category").val();  //硬盘种类
    var aliYunDiskType = $("#disk-type").val();  //硬盘属性，系统盘和数据盘
    var aliYunDiskStatus = $('#aliyun-hdstatus .active').attr("data-gruop");
    if (page == null) {
        page = 1;
    }
    $("#hd-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").html("");
    var appName = $('#appnamemenu').text();

    var regionId = $("#selectRegion").val();
    $.ajax({
        url: 'hd/hd_list',
        type: 'GET',
        data: {
            appName: appName,
            regionId: regionId,
            searchType:searchType,
            searchKeyWord:searchKeyWord,
            aliYunDiskCategory: aliYunDiskCategory,
            aliYunDiskStatus: aliYunDiskStatus,
            aliYunDiskType:aliYunDiskType,
            page: page
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云硬盘列表成功'});
            $("#hd-content").html(data);
            var totalPage = $("#hd-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "searchBySearchKeyWord"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云硬盘列表失败'});
        }
    });
}

var aliYunDiskStatus;

function divByCondition(page) {
    searchByManyCondition(aliYunDiskStatus,page);
}

//选择一系列属性触发的搜索
function searchByManyCondition(alistatus,page){
    var aliYunDiskCategory = $("#disk-category").val();
    aliYunDiskStatus = alistatus;
    var  portable = "";
    var portableStr =  null;
    if(portable=="true"){
        portable = true;
        portableStr = "true";
    }else if(portable=='false'){
        portable = false;
        portableStr = "false";
    }else{
        portable = null;
    }
    var aliYunDiskType = $("#disk-type").val();
    if (page == null) {
        page = 1;
    }
    $("#hd-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").empty();
    var appName = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    $.ajax({
        url: 'hd/hd_list',
        type: 'GET',
        data: {
            appName: appName,
            regionId: regionId,
            aliYunDiskCategory: aliYunDiskCategory,
            aliYunDiskStatus: aliYunDiskStatus,
            portable: portable,
            portableStr:portableStr,
            aliYunDiskType:aliYunDiskType,
            page: page
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云硬盘列表成功'});
            $("#hd-content").html(data);
            var totalPage = $("#hd-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "divByCondition"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云硬盘列表失败'});
        }
    });

}


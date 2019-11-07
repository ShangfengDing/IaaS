/**
 * Created by bxt on 2016/7/30.
 */

//————————————公共操作————————————————————
$(document).ready(function () {
    $('#yun-shot-search').hide();
	getAppname(changeAppname);
});

//获取地域
function changeAppname() {
    $("#shot-content").load("template/_loading_style_1.jsp");
    $("#selectRegion").attr("disabled", true);
    $('#yun-shot-search').removeClass('hidden');
    $('#yun-shot-search').hide();
    var appname = $('#appnamemenu').text();
    $.ajax({
        url: 'common/getRegionId',
        type: 'GET',
        data: {
            //providerEn: provider
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
            getSnapShots();
        }
    });
}
 

//获取快照
function getSnapShots(page) {
    if (page == null) {
        page = '1';
    }
    $("#select-content").empty();
    $("#shot-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").empty();
    var shotprovider = $('#appproviderEn').text();
    var appName = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    $.ajax({
        url: 'shot/shot',
        type: 'GET',
        data: {
        	appname: appName,
            regionId: regionId,
            page: page
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云快照列表成功'});
            
            if (shotprovider == 'yunhai') {
                $("#select-content").load('shot/yunhai/yunhai_shot_search_bar.jsp');
            } else if (shotprovider == 'aliyun') {
                $("#select-content").load('shot/aliyun/aliyun_shot_search_bar.jsp');
            } else {
                $("#select-content").empty();
            }

            $("#shot-content").html(data);
            var totalPage = $("#shot-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getSnapShots"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云快照列表失败'});
        }
    });
}

//搜搜的隐藏与显示
function exsearch() {
    $('#yun-shot-search').slideToggle();
}

//————————————云海快照相关的操作————————————————————

//列表分页
var souType = "all";  //"system"表示系统盘，"data"表示数据盘
//搜索
function changeDiskType(type) {
    souType = type;
    searchByDiskType();
}
function searchByDiskType(page) {
    if (page = null) {
        page = '1';
    }
    $("#shot-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").empty();
    var provider = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    $.ajax({
        url: 'shot/shot',
        type: 'GET',
        data: {
            appname: provider,
            regionId: regionId,
            souType: souType,
            page: page
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云快照列表成功'});
            $("#shot-content").html(data);
            var totalPage = $("#shot-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getSnapShots"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云快照列表失败'});
        }
    });

}
//是否显示搜索框
function showSearchBox() {
    $("#search-box").toggleClass("hidden");
}

//多条件搜索
function searchByKeyWord(page) {
    if (page == null) {
        page = 1;
    }
    $("#shot-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").empty();
    var provider = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    var shotKeyword = $('#shot-key-word').val();
    var sdiskType = "";
    $.ajax({
        type: "GET",
        url: 'shot/shot',
        data: {
            souType: 'keyword',
            skeyword: shotKeyword,
            appname: provider,
            regionId: regionId,
            shotDiskType: sdiskType,
            page: page
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云快照列表成功'});
            $("#shot-content").html(data);
            var totalPage = $("#shot-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getSnapShots"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云快照列表失败'});
        }
    });
}
//快照的回滚和删除操作
function rollback(operation, snapshotId, shotinstanceName, sproviderEn, suserEmail, sregionId, sdiskId,sappName) {
    var confirmMsg = (operation == "rollback") ? "确认进行回滚操作吗？(注意：该操作将关闭云主机" + shotinstanceName + "！！)" : "确认删除该快照吗？";
    $.tipModal('confirm', 'warning', confirmMsg, function (result) {		//返回的result是bool类型
        if (result == true) {
            $.ajax({
                type: "POST",
                url: "shot/shotoperate",
                data: {
                    operation: operation,
                    snapshotId: snapshotId,
                    providerEn: sproviderEn,
                    userEmail: suserEmail,
                    regionId: sregionId,
                    diskId: sdiskId,
                    appName: sappName
                },
                success: function (data) {
                    if (data.result == "success") {
                        $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '操作成功！'});
                        if (operation == "delete") {
                            $("#tr" + snapshotId).remove();
                        }
                    }else{
                        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '操作出现错!'+data.result});
                    }
                }
            });
        }
    });
}


//————————————阿里云快照相关的操作————————————————————

var aliYunSearchType = null;
var aliYunKeyWord = null;
var aliSourceDiskType = null;
//改变阿里云的搜索参数
function changeShotSearchType() {
    var type = $("#search-type").val();
    var text = $("#search-type option:selected").text();
    $("#shot-search-keyword").attr("placeholder", "请输入" + text + "进行精确查询");
}
//根据输入的值进行搜索
function searchAliShotBySearchType(page) {
    // aliYunSearchType = $("#search-type").val();
    aliYunSearchType = "shot-name";
    aliYunKeyWord = $("#shot-search-keyword").val();
    var alishotType = $('#aliyun-shottype .active').attr("data-group");
    if (page == null) {
        page = '1';
    }
    $("#shot-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").empty();
    var provider = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    $.ajax({
        url: 'shot/shot',
        type: 'GET',
        data: {
            appname: provider,
            regionId: regionId,
            aliYunSearchType: aliYunSearchType,
            aliYunKeyWord: aliYunKeyWord,
            aliSourceDiskType:alishotType,
            page: page
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云快照列表成功'});

            $("#shot-content").html(data);
            var totalPage = $("#shot-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "searchAliShotBySearchType"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云快照列表失败'});
        }
    });
}
//改变磁盘属性
function changeSourceDiskType(alidiskType){
    aliSourceDiskType = alidiskType;
    searchAliSHotBySourceDiskType();
}
//改变磁盘属性后进行快照查询
function searchAliSHotBySourceDiskType(page){
    if (page == null) {
        page = '1';
    }
    $("#shot-content").load("template/_loading_style_1.jsp");
    $("#back_divPage").empty();
    var provider = $('#appnamemenu').text();
    var regionId = $("#selectRegion").val();
    $.ajax({
        url: 'shot/shot',
        type: 'GET',
        data: {
            appname: provider,
            regionId: regionId,
            aliSourceDiskType:aliSourceDiskType,
            page: page
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云快照列表成功'});
            $("#shot-content").html(data);
            var totalPage = $("#shot-total-page").val();
            if (totalPage != null) {
                $("#back_divPage").html($.getDivPageHtml(page, totalPage, "searchAliSHotBySourceDiskType"));
            }
        },
        error: function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云快照列表失败'});
        }
    });
}

//打开阿里云镜像的模态框
function createAliImage(shotId) {
    var href = 'vm/aliyun_image.jsp?' +
        '&shotId=' + shotId;
    aliImageModal = $.frontModal({
        size: 'modal-md',
        title: '发布模板',
        href: href
    }).on('shown.bs.modal', function () {

    });
}
//发布阿里云镜像,appname和regionId
function aliImageSubmit() {
    var shotAppname = $('#appnamemenu').text();
    var shotRegionId = $('#selectRegion').val();
    var alishotId = $('#image_shotId').text();
    var aliname = $('#image_name').val();
    var alidesc = $('#image_des').val();
    if (!checkName(aliname)){
        return;
    }
    if (!checkDescription(alidesc)){
        return;
    }
    $.ajax({
        async:true,
        type:"POST",
        url:"vm/newimg",
        data:{
            appname:shotAppname,
            regionId:shotRegionId,
            snapshotId:alishotId,
            imageName:aliname,
            imageDescription:alidesc
        },
        success:function(data){
            aliImageModal.modal('hide');
            if (data.result == "success") {
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'模板发布成功，在云镜像中查看'})
            } else {
                $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:data.result})
            }
        },
        error:function(data){
            $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:data.result})
        }
    });
}
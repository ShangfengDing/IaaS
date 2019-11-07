/**
 * Created by dell on 2016/8/1.
 */
var regionList = null;
var zoneList = null;
var availableDiskCategories = null;
var disk = {
    region: null,
    zone: null,
    diskCategory: null,
    diskCategoryName: null,
    diskSize: null,
    diskCount: 1
}
//————————————公共操作————————————————————
$(document).ready(function () {
    $("#buy-disk").attr("disabled", true);
    getAppname();
    getRegions();
});

function getAppname() {
    $.ajax({
        type: "POST",
        url: "common/getAppName",
        success: function (data) {
            appdata = data;
            $('#selectAppname').html('');
            var appName;
            var providerEn;
            for (var i in data) {
                appName = data[i].appName;
                providerEn = data[i].providerEn;
                switch (providerEn) {
                    case "aliyun":
                        $appname = $("<li onclick='changeName("+i+")' role='presentation'><a role='menuitem' href='javascript:void(0)'><span class='glyphicon icon-freeshare-aliyun selectspan'></span>"+
                            data[i].appName + "</a><span class='hidden'>"+data[i].providerEn+"</span></li>");
                        break;
                    case "yunhai":
                        $appname = $("<li onclick='changeName("+i+")' role='presentation'><a role='menuitem' href='javascript:void(0)'><span class='glyphicon glyphicon-cloud selectspan'></span>" +
                            data[i].appName + "</a><span class='hidden'>"+data[i].providerEn+"</span></li>");
                        break;
                }
                $('#selectAppname').append($appname);
            }
            var ulMenuName = $("#yunhai_ulMenuName").val();
            var ulMenuProviderEn = $("#yunhai_ulMenuProviderEn").val();
            if(ulMenuName == ""){
                ulMenuName = appName;
                ulMenuProviderEn = providerEn;
                $("#yunhai_appname").val(ulMenuName) ;
            }
            clickChangeAppName(ulMenuProviderEn, ulMenuName);
        }
    })
}

function clickChangeAppName(providerEn, appname) {
    if (providerEn=="yunhai") {
        $('#appnameicon').addClass("glyphicon-cloud");
    } else if (providerEn == "aliyun") {
        $('#appnameicon').addClass("icon-freeshare-aliyun");
    }
    $('#appnamemenu').html(appname);
    $('#appproviderEn').html(providerEn);
}

function changeName(i) {
    var provierEn = appdata[i].providerEn;
    var appName = appdata[i].appName;
    clickChangeAppName(provierEn, appName);
    switch (provierEn) {
        case 'yunhai':
            window.location.href="hd/newhd.jsp?menu=newhd&appname="+appName+"&&ulMenuName="+appName+"&&ulMenuProviderEn="+provierEn;
            break;
        case 'aliyun':
            window.location.href="hd/aliyun/aliyun_buy_hd.jsp?appname="+appName+"&&ulMenuName="+appName+"&&ulMenuProviderEn="+provierEn;
            break;
    }
}

//获取地域
function getRegions() {
    $("#loading").load("template/_loading_style_1.jsp");
    var appName = $("#appName").val();

    $.ajax({
        url: 'common/getRegionId',
        type: 'GET',
        data: {
            appname:appName
        },
        success: function (result) {
            var json = eval(result);
            regionList = json;
            refreshRegionIList();
            getZones();
        },
        error: function () {

        }
    });
}

function refreshRegionIList() {
    $("#region-list").html("");
    for (var i = 0; i < regionList.length; i++) {
        var region = regionList[i];
        var $item = $("<a class=\"btn btn-default front-no-box-shadow\" onclick='changeRegion(\"" + i + "\")'>" +
            region.localName + "</a>");
        if (disk.region == null) {
            disk.region = regionList[0];
        }
        if (region.regionId == disk.region.regionId) {
            $item.addClass("active");
        }
        $("#region-list").append($item);
    }
}
function changeRegion(index) {
    $("#buy-disk").attr("disabled", true);
    disk.region = regionList[index];
    disk.zone = null;
    disk.diskCategory = null;
    disk.diskCategoryName = null;
    disk.diskSize = null;
    refreshRegionIList();
    getZones();
}

function getZones() {
    $("#zone-list").attr("disabled", true);
    $("#disk-categories").attr("disabled", true);
    $.ajax({
        url: 'common/getZoneList',
        type: 'GET',
        data: {
            provider: 'aliyun',
            regionId: disk.region.regionId
        },
        success: function (result) {
            var json = eval(result);
            zoneList = json;
            console.log(zoneList)
            refreshZoneList();
            $("#base-configuration").removeClass("hidden");
            $("#loading").empty();

        }
    });
}
function refreshZoneList() {
    $("#buy-disk").attr("disabled", true);
    $("#zone-list").empty();
    $.each(zoneList, function (index) {
        var zone = zoneList[index];
        var item = $("<option value='" + index + "'>" + zone.localName + "</option>");
        $("#zone-list").append(item);
        if (disk.zone == null) {
            disk.zone = zoneList[0];
        }
    });
    $("#zone-list").attr("disabled", false);
    refreshDiskCategories();
}

function changeZone() {
    $("#disk-categories").attr("disabled", true);
    var zone = zoneList[$("#zone-list").val()];
    disk.zone = zone;
    disk.diskCategory = null;
    disk.diskCategoryName = null;
    disk.diskSize = null;
    refreshDiskCategories();

}
function refreshDiskCategories() {
    availableDiskCategories = disk.zone.availableDiskCategories;
    $("#disk-categories").empty();
    $.each(availableDiskCategories, function (index) {
        var diskCategory = availableDiskCategories[index];
        var item = $("<option value='" + diskCategory.diskCategory + "'>" + diskCategory.diskCategoryName + "</option>");
        $("#disk-categories").append(item);
        if (disk.diskCategory == null) {
            disk.diskCategory = diskCategory.diskCategory;
            disk.diskCategoryName = diskCategory.diskCategoryName;
        }
    });
    $("#disk-categories").attr("disabled", false);
    refreshDiskSize()
}

function changeDiskCategory() {
    disk.diskCategory = $("#disk-categories").val();
    refreshDiskSize();

}

function refreshDiskSize() {
    var item = null;
    if (disk.diskCategory == 'cloud') {
        $("#disk-range-tip").html("容量范围5 - 2000GB");
        item = $(" <input id='disk-size' type='number' class='form-control'min='5'max='2000'placeholder='磁盘容量' onblur='checkDiskSize(\"cloud\")'/>");
        $("#disk-size-div").html(item);
    } else {
        $("#disk-range-tip").html("容量范围20 - 32768GB");
        item = $(" <input id='disk-size' type='number' class='form-control'min='20'max='32768'placeholder='磁盘容量' onblur='checkDiskSize()'/>");
        $("#disk-size-div").html(item);
    }
    refreshPriceList();
}
function checkDiskSize(type) {
    var text = $("#disk-size").val();
    if ((/^(\+|-)?\d+$/.test(text)) && text > 0) {
        if (type == "cloud") {
            if (text < 5) {
                $("#disk-size").val(5);
            } else if (text > 2000) {
                $("#disk-size").val(2000);
            }
            disk.diskSize = $("#disk-size").val();
        } else {
            if (text < 20) {
                $("#disk-size").val(20);
            } else if (text > 32768) {
                $("#disk-size").val(32768);
            }
            disk.diskSize = $("#disk-size").val();
        }
    } else {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入正整数'});
        $("#disk-size").val("");
    }
    refreshPriceList();
}

function refreshPriceList() {
    $("#span-region").html(disk.region.localName == null ? "请选择" : disk.region.localName);
    $("#span-zone").html(disk.zone.localName == null ? "请选择" : disk.zone.localName);
    $("#span-disk-size").html(disk.diskSize == null ? "请选择" : disk.diskSize + "GB");
    $("#span-disk-category").html(disk.diskCategoryName == null ? "请选择" : disk.diskCategoryName);
    $("#buy-disk").attr("disabled", false);

}

function buyAliYunDisk() {
    if (check()) {
        $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: '正在购买阿里云硬盘'})
        $.showLoading('show');
        var appName = $("#appName").val();
        $.ajax({
            type: 'POST',
            url: 'hd/newhd',
            dataType: 'json',
            data: {
                appname: appName,
                providerEn: 'aliyun',
                regionId: disk.region.regionId,
                zoneId: disk.zone.zoneId,
                diskCategory: disk.diskCategory,
                diskSize: disk.diskSize,
                diskNum: disk.diskCount
            },
            success: function (result) {
                console.log("success" + result);
                if (result.resultCode == 'success') {
                    $.showLoading('reset');
                    $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '购买云硬盘成功'})
                    window.location.href="hd/hd_list.jsp";
                } else if (result.resultCode == 'error') {
                    $.showLoading('reset');
                    $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '购买云硬盘失败' + result.resultMessage});
                }
            },
            error: function (result) {
                $.showLoading('reset');
                $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '购买云硬盘失败，请检查您的网络连接'});
            }
        });
    }
}

function check() {
    if (disk.region == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择地域'});
        return false;
    } else if (disk.zone == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择可用区'});
        return false;
    } else if (disk.diskCategory == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择磁盘类型'});
        return false;
    } else if (disk.diskCategoryName == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择磁盘名字'});
        return false;
    } else if (disk.diskSize == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请填写磁盘大小'});
        return false;
    } else if (disk.diskCount == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请填写磁盘数量'});
        return false;
    }
    return true;
}

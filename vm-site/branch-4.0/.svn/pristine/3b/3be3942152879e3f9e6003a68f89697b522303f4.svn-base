/**
 * Created by dell on 2016/7/20.
 * 每次修改该文件后请在此处注明，当前所支持修改的选项，这些选项的值将会在创建主机时传回到后方Action
 * 当前支持修改的选项：
 * 1.regionId  2.zonedId  3.ioIoOptimized  4.instanceType  5.imageId
 * 6.systemDiskCategory  7.systemDiskSize  8.dataDiskCategory  9.dataDiskSize 10.securityGroupId
 * 11.internetChargeType 网络计费类型，按流量计费还是按固定带宽计费。与网络出带宽有关，详细说明请看NewAliYunVmAction的说明
 * 12.internetMaxBandwidthOut 请看详细说明，与instanceChargeType有关
 * 13.instanceName  14.hostname  15.password
 * 16.instanceChargeType PrePaid：预付费，即包年包月 PostPaid：后付费，即按量付费
 * 17.period  购买时长 只有是包年包月时才能选择
 *
 *
 * 此界面逻辑较复杂，数据较多，现将逻辑说明：
 * 主要核心逻辑：
 * 每次的点击事件发生后，在change*函数中改变instance对字段的值，然后调用对应刷新函数，
 * 在刷新函数中，根据当前instance中各个字段的值来设置界面。
 * 详细说明：
 * instance刚被声明时的状态称为初始状态，此时所有字段均为空
 * 修改 instanceChargeType 后，即选择包年包月还是按量付费，将页面中所有数据刷新为原始状态，清空 instance 所有其他状态。
 *
 *
 *
 */
var instanceTypeFamily_1_Modal;
var instanceTypeFamily_2_Modal;
var regionList = [];
var zoneList = [];
var imageList = [];
var osList = [];
var availableResourceList = [];
var zoneAvailableResource = [];
var instanceTypes = [];
var securityGroupList = [];
var instance = {
    instanceChargeType: null,
    region: null,
    zone: null,
    ioOptimized: true,
    instanceGeneration: null,
    instanceType: null,
    image: null,
    os: null,
    systemDiskCategory: null,
    systemDiskSize: null,
    dataDiskCategory: null,
    dataDiskSize: null,
    securityGroup: null,
    internetChargeType: null,
    internetMaxBandwidthOut: null,
    instanceName: null,
    hostname: null,
    password: null,
    period: null
};
$(document).ready(function () {
    testWindowSize();
    $("#setting-now").click(function () {
        $('#password-box').removeClass('hidden');
        $('#password-later-box').addClass('hidden');
        $('#setting-now').addClass("active");
        $("#setting-later").removeClass("active");
        instance.settingLater = false;
        refresh();
    });

    $("#setting-later").click(function () {
        $('#password-later-box').removeClass('hidden');
        $('#password-box').addClass('hidden');
        $('#setting-now').removeClass("active");
        $("#setting-later").addClass("active");

        instance.settingLater = true;
    });
    getAliYunInfo();
});



function testWindowSize() {
    var width = $(window).width();

    if (width >= 1200) {
        var leftGutter = (width - 1170) / 2 + 1170 * 0.75;
        var cardWidth = 1170 * 0.25;
        $('#right-card').attr("style", "position: fixed;left: " + leftGutter + "px;" + "width: " + cardWidth + "px");
    } else if (width >= 970) {
        var leftGutter = (width - 970) / 2 + 970 * 0.75;
        var cardWidth = 970 * 0.25;
        $('#right-card').attr("style", "position: fixed;left: " + leftGutter + "px;" + "width: " + cardWidth + "px");
    } else {
        $('#right-card').attr("style", "");
    }
}
function refreshAliYun() {
    refreshInstanceChargeType();
    refreshAliYunRegion();
    refreshAliYunZone();
    refreshAliYunInstanceGeneration();
    refreshAliYunIOOptimized();
    refreshAliYunInstanceType();
    refreshAliYunImage();
    refreshAliYunDisk();
    refreshInternetChargeType();
    refreshAliYunSecurityGroup();
    refreshAliYunBandWidth();
    refreshAliYunNewInstanceButton()
    //refreshTime();
    //refreshInstanceInfo();
    //创建主机的按钮
    //if (check()) {
    //    $("#btn-new-instance").removeAttr("disabled");
    //} else {
    //    $("#btn-new-instance").attr("disabled", "disabled");
    //}
    ////计算主机的价格
    //$("#instance-price").html(countPrice());

    $(".loading").css("display", "none");
    $(".show-later").removeClass("hidden").removeClass("show-later");
}
function refreshInstanceChargeType() {
    if (instance.instanceChargeType == null) {
        instance.instanceChargeType = 'prepaid';
    }
    if (instance.instanceChargeType == "prepaid") {
        if (instance.period == null) {
            $("#instance-time").html("<font color='red'>请选择购买量</font>");
        } else {
            $("#instance-time").html(instance.period + "个月 x" + 1 + "台");
        }
    } else {
        $("#instance-time").html(1 + "台");
    }
}
function changeInstanceChargeType(type) {
    instance.instanceChargeType = type;
    if (type == 'prepaid') {
        $("#postpaid").removeClass("active");
        $("#prepaid").addClass("active");
        $("#period-tip").removeClass("hidden");
        $("#instance-type-tip").text("包年包月");

    } else if (type == 'postpaid') {
        $("#prepaid").removeClass("active");
        $("#postpaid").addClass("active");
        $("#period-tip").addClass("hidden");
        $("#instance-type-tip").text("按量付费");
    }
    refreshInstanceChargeType();
}
function getAliYunInfo() {
    $.ajax({
        type: "GET",
        url: "vm/getAliyunNewInstanceInfo",
        data:{appname:$('#aliyun_appname').val()},
        dataType: "json",
        success: function (data) {
            regionList = data.regionList;
            zoneList = data.zoneList;
            imageList = data.imageInfo.imageList;
            osList = data.imageInfo.osList;
            availableResourceList = data.availableResourceList;
            securityGroupList = data.securityGroupList;
            refreshAliYun();
        }
    });
}

function refreshAliYunRegion() {
    $("#region-list").html("");
    for (var i = 0; i < regionList.length; i++) {
        var region = regionList[i];
        var $item = $("<a class=\"btn btn-default front-no-box-shadow\" onclick='changeRegion(" + i + ")'>" +
            region.localName + "</a>");
        if (instance.region == null) {
            instance.region = regionList[0];
        }
        if (region.regionId == instance.region.regionId) {
            $item.addClass("active");
        }
        $("#region-list").append($item);
    }
}
function changeRegion(index) {
    $.showLoading('show');
    $.fillTipBox({type: 'info', icon: 'glpyhicon-info-sign', content: '正在切换地域'});
    instance.region = regionList[index];
    instance.zone = null;
    instance.ioOptimized = true;
    instance.instanceGeneration = null;
    instance.instanceType = null;
    instance.image = null;
    instance.os = null;
    instance.systemDiskCategory = null;
    instance.systemDiskSize = null;
    instance.dataDiskCategory = null;
    instance.dataDiskSize = null;
    instance.securityGroup = null;
    instance.internetChargeType = null;
    instance.internetMaxBandwidthOut = null;
    instance.instanceName = null;
    instance.hostname = null;
    instance.password = null;
    instance.period = null;
    $.ajax({
        type: "GET",
        url: "vm/getAliyunNewInstanceInfo",
        data: {
            regionId: instance.region.regionId,
            appname:$('#aliyun_appname').val()
        },
        success: function (data) {
            zoneList = data.zoneList;
            availableResourceList = data.availableResourceList;
            imageList = data.imageInfo.imageList;
            osList = data.imageInfo.osList;
            availableResourceList = data.availableResourceList;
            securityGroupList = data.securityGroupList;
            refreshAliYun();
            $.showLoading('reset');
        }
    });
}
function refreshAliYunZone() {
    $("#zone-list").html("");
    for (var i = 0; i < zoneList.length; i++) {
        var zone = zoneList[i];
        var $item = $("<li><a onclick='changeZone(" + i + ")'>" + zone.localName + "</a></li>");
        if (instance.zone == null) {
            instance.zone = zone;
        }
        zoneAvailableResource = $.extend({}, zoneAvailableResource,availableResourceList[i]);
        if (zone.zoneId == instance.zone.zoneId) {
            $("#zone-disp").html(zone.localName);
        }
        $("#zone-list").append($item);
    }
    //右边信息栏
    $("#instance-region").html(instance.region.localName + "(" + instance.zone.localName + ")");
}

function changeZone(index) {
    instance.zone = zoneList[index];
    zoneAvailableResource = availableResourceList[index];
    instance.ioOptimized = true;
    instance.instanceGeneration = null;
    instance.instanceType = null;
    instance.image = null;
    instance.os = null;
    instance.systemDiskCategory = null;
    instance.systemDiskSize = null;
    instance.dataDiskCategory = null;
    instance.dataDiskSize = null;
    instance.securityGroup = null;
    instance.internetChargeType = null;
    instance.internetMaxBandwidthOut = null;
    instance.instanceName = null;
    instance.hostname = null;
    instance.password = null;
    instance.period = null;
    refreshAliYun();
}

//设置两个标志位来确定系列一的可用主机列表中 是否需要展现 IO优化的选项
//flag[0]是标志系列一主机可用资源列表中有IOOptimized为true的
//flag[1]是标志系列一主机可用资源列表中有IOOptimized为false的
function isIOOptimized() {
    var instanceGeneration = null;
    var flag = [false, false];
    var resources = zoneAvailableResource[instance.zone.zoneId];
    for (var i = 0; i < resources.length; i++) {
        instanceGeneration = resources[i].instanceGeneration;
        if (instanceGeneration == "ecs-1") {
            if (resources[i].ioOptimized == true) {
                flag[0] = true;
            } else if (resources[i].ioOptimized == false) {
                flag[1] = true;
            }
        }
    }
    return flag;
}
function refreshAliYunInstanceGeneration() {

    if (instance.instanceGeneration == null) {
        var instanceGeneration = null;
        var io = null;
        var resources = zoneAvailableResource[instance.zone.zoneId];
        if (resources != null) {
            resources.sort(function (a, b) {
                return a.instanceGeneration > b.instanceGeneration ? 1 : -1;
            });
            var isEcs1 = true;
            var isEcs2 = true;
            $("#instanceGeneration").html("");
            for (var i = 0; i < resources.length; i++) {
                instanceGeneration = resources[i].instanceGeneration;
                io = resources[i].ioOptimized;
                var $item = null;
                if (instanceGeneration == "ecs-1" && isEcs1) {
                    $item = $("<button id='instanceGeneration_" + instanceGeneration + "' class='btn btn-default front-no-box-shadow' value='" + instanceGeneration + "' onclick='changeInstanceGeneration(" + "1" + ")'>" + "系列I" + "</button>");
                    isEcs1 = false;
                } else if (instanceGeneration == "ecs-2" && isEcs2) {
                    $item = $("<button id='instanceGeneration_" + instanceGeneration + "' class='btn btn-default front-no-box-shadow' value='" + instanceGeneration + "' onclick='changeInstanceGeneration(" + "2" + ")'>" + "系列II" + "</button>");
                    isEcs2 == false;
                }
                $("#instanceGeneration").append($item);
            }
            var generation = $("#instanceGeneration").children("button")[0];
            $(generation).addClass("active");
            instance.instanceGeneration = $(generation).val();
            if (instance.instanceGeneration == 'ecs-1') {
                $("#generation-simple-introduce").text("系列 I 采用 Intel Xeon CPU，DDR3 的内存。");

            } else {
                $("#generation-simple-introduce").text("系列 II 采用 Intel Haswell CPU、DDR4 内存，拥有更好的内存计算能力；默认为 I/O 优化实例，搭配 SSD云盘可获得更好的存储性能。");
            }
        }
    }

}

function changeInstanceGeneration(instanceGeneration) {
    if (instanceGeneration == 1) {
        instance.instanceGeneration = "ecs-1";
        $("#generation-simple-introduce").text("系列 I 采用 Intel Xeon CPU，DDR3 的内存。");
        $("#instanceGeneration_" + "ecs-1").addClass("active");
        $("#instanceGeneration_" + "ecs-2").removeClass("active");
    } else {
        instance.instanceGeneration = "ecs-2";
        $("#generation-simple-introduce").text("系列 II 采用 Intel Haswell CPU、DDR4 内存，拥有更好的内存计算能力；默认为 I/O 优化实例，搭配 SSD云盘可获得更好的存储性能。");
        $("#instanceGeneration_" + "ecs-2").addClass("active");
        $("#instanceGeneration_" + "ecs-1").removeClass("active");
    }
    instance.instanceType = null;
    instance.ioOptimized = true;
    refreshAliYun();
}
function refreshAliYunIOOptimized() {
    if (instance.instanceGeneration == "ecs-1") {
        var flag = isIOOptimized();
        //如果第一个true第二个false表明该可用资源列表中 全部可以调优，不需要选择。
        //其他情况均需要来判断是否选择了调优
        if (flag[0] == true && flag[1] == false) {
            instance.ioOptimized = true;
            $("#ioCheckBox").html("");
        } else {
            var item = $("<input type='checkbox' id='ioOptimized' onchange='changeIOOptimized()'>");
            $("#ioCheckBox").html(item);
            instance.ioOptimized = false;
        }
    } else if (instance.instanceGeneration == "ecs-2") {
        instance.ioOptimized = true;
        $("#ioCheckBox").html("");
    }
}
function changeIOOptimized() {
    var ioValue = $("#ioOptimized").is(":checked");
    if (ioValue == true) {
        instance.ioOptimized = true;
    } else {
        instance.ioOptimized = false;
    }
    instance.instanceType = null;
    refreshAliYunDisk();
    refreshAliYunInstanceType();
}
function getAvailableResource(isIoOptimized, instanceGeneration) {
    var resources = zoneAvailableResource[instance.zone.zoneId];
    //console.log(instance.zone.zoneId + isIoOptimized + instanceGeneration + resources);
    if (resources != null) {
        resources.sort(function (a, b) {
            return a.instanceGeneration > b.instanceGeneration ? 1 : -1;
        });
        for (var i = 0; i < resources.length; i++) {
            var generation = resources[i].instanceGeneration;
            var ioOptimized = resources[i].ioOptimized;
            if (generation == instanceGeneration && ioOptimized == isIoOptimized) {
                return resources[i];
            }
        }
    }
}


function refreshAliYunInstanceType() {
    var resources = getAvailableResource(instance.ioOptimized, instance.instanceGeneration);
    if (resources != null) {
        if (instance.instanceType == null) {
            $("#instanceType").html("点击选择实例规格");
            $("#instance-type").html("<font color='red'>请选择实例规格</font>")
        }
        instanceTypes = resources.instanceTypes;
        instanceTypes = instanceTypes.sort(function (a, b) {
            return (a.cpuCores - b.cpuCores);
        });
        instanceTypes = instanceTypes.sort(function (a, b) {
            return (a.memory - b.memory);
        });
    }

}
function changeInstanceType(num) {
    instance.instanceType = instanceTypes[num];
    var item = instance.instanceType.cpuCores + "核" + instance.instanceType.memory + "GB" + "(" + instance.instanceType.instanceTypeId + ")"
    $("#instanceType").html(item)
    $("#instance-type").html(item)
}
function chooseInstanceType() {
    if (instance.instanceGeneration == 'ecs-1') {
        var href = 'vm/newvm/aliyun/aliyunvm_instancetypefamily_1.jsp';
        instanceTypeFamily_1_Modal = $.frontModal(
            {
                size: 'modal-md',
                href: href
            }).on('shown.bs.modal',
            function () {

            })
    } else if (instance.instanceGeneration == 'ecs-2') {
        instanceTypeFamily_2_Modal = $.frontModal(
            {
                size: 'modal-md',
                href: 'vm/newvm/aliyun/aliyunvm_instancetypefamily_2.jsp'
            }).on('shown.bs.modal',
            function () {

            })
    }
}
function refreshAliYunImage() {
    if (instance.os == null) {
        $("#os-disp").html("请选择系统类型");
        $("#instance-image").html("请选择系统类型");
    } else {
        $("#instance-image").html(instance.os);

    }
    if (instance.image == null) {
        $("#image-disp").html("请选择镜像");

        $("#instance-image").addClass("red");
    } else {
        //显示新的主机相关信息
        $("#password-later-box").html(instance.image.description);

        $("#instance-image").removeClass("red");
    }
    ;
    var $list1 = $("#os-list");
    $list1.html("");
    for (var i = 0; i < osList.length; i++) {
        var item = $("<li><a onclick='changeOS(" + i + ")'>" + osList[i] + "</a></li>");
        if (osList[i] == instance.os) {
            $("#os-disp").html(osList[i]);
        }
        $list1.append(item);
    }
    var $list2 = $("#image-list");
    $list2.html("");
    for (var i = 0; i < imageList.length; i++) {
        if (imageList[i].os == instance.os) {
            var item = $("<li><a onclick='changeImage(" + i + ")'>" + imageList[i].name + "</a></li>");
            if (imageList[i] == instance.image) {
                $("#image-disp").html(imageList[i].name);
            }
            $list2.append(item);
        }
    }
}
function changeOS(index) {
    $('#image-dropdown').removeClass('disabled');
    instance.os = osList[index];
    instance.image = null;
    refreshAliYun();
}
function changeImage(index) {
    instance.image = imageList[index];
    refreshAliYun();
}


function refreshAliYunDisk() {
    var resources = getAvailableResource(instance.ioOptimized, instance.instanceGeneration);
    if (resources != null) {
        var systemDisks = resources.systemDiskCategories;
        if (instance.systemDiskCategory == null) {
            $("#system-disk").html("");
            $("#system-disk-tip").html("请选择磁盘类型");
            $.each(systemDisks, function (index) {
                var disk = systemDisks[index];
                var item = $("<li><a href='javascript:void(0)' onclick='changeSystemDiskType(" + index + ")'>" + disk.diskName + "</a></li>");
                $("#system-disk").append(item);
            });
        }

        var dataDisks = resources.dataDiskCategories;
        if (instance.dataDiskCategory == null) {
            $("#data-disk").html("");
            $("#data-disk-tip").text("请选择磁盘类型");
            $.each(dataDisks, function (index) {
                var disk = dataDisks[index];
                var item = $("<li><a href='javascript:void(0)' onclick='changeDataDiskType(" + index + ")'>" + disk.diskName + "</a></li>");
                $("#data-disk").append(item);
            });
        }

        var disk = instance.systemDiskSize + "G+" + instance.dataDiskSize + "G";
        if (instance.systemDiskCategory == null || instance.systemDiskSize == null) {
            disk = "<font color='red'>请选择系统盘类型和大小</font>";
        } else if (instance.dataDiskCategory == null || instance.dataDiskSize == null) {
            disk = "<font color='red'>请选择数据盘类型和大小</font>";
        } else {
            disk = instance.systemDiskSize + "G+" + instance.dataDiskSize + "G";
        }

        $("#instance-disk").html(disk);

    }


}

function changeSystemDiskType(index) {
    var resources = getAvailableResource(instance.ioOptimized, instance.instanceGeneration);
    if (resources != null) {
        var systemDisks = resources.systemDiskCategories;
        instance.systemDiskCategory = systemDisks[index].diskId;
        $("#system-disk-tip").text(systemDisks[index].diskName)
        refreshAliYunDisk();
    }

}
function changeDataDiskType(index) {
    var resources = getAvailableResource(instance.ioOptimized, instance.instanceGeneration);
    if (resources != null) {
        var dataDisks = resources.dataDiskCategories;
        instance.dataDiskCategory = dataDisks[index].diskId;
        $("#data-disk-tip").text(dataDisks[index].diskName)
        refreshAliYunDisk();
    }

}
function changeDiskSize(obj, type) {
    var text = $(obj).val();
    if ((/^(\+|-)?\d+$/.test(text)) && text > 0) {
        if (type == "system") {
            if (text < 40) {
                $(obj).val(40);
            } else if (text > 500) {
                $(obj).val(500);
            }
            instance.systemDiskSize = $(obj).val();
        } else if (type == "data") {
            if (text < 5) {
                $(obj).val(5);
            } else if (text > 2000) {
                $(obj).val(2000);
            }
            instance.dataDiskSize = $(obj).val();
        }
    } else {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入正整数'});
        $(obj).val("");
        if (type == 'system') {
            instance.systemDiskSize = null;
        } else {
            instance.dataDiskSize = null;
        }
    }
    refreshAliYunDisk();
}
function refreshInternetChargeType() {
    if (instance.internetChargeType == null) {
        instance.internetChargeType = 'PayByBandwidth';
    }
}
function changeInternetChargeType(type) {
    if (type == 'PayByBandwidth') {
        $("#pay-by-traffic-tip").addClass("hidden")
        $("#pay-by-bandwidth").addClass("active");
        $("#pay-by-traffic").removeClass("active");
    } else if (type == 'PayByTraffic') {
        $("#pay-by-traffic-tip").removeClass("hidden")
        $("#pay-by-bandwidth").removeClass("active");
        $("#pay-by-traffic").addClass("active");
    }
    instance.internetChargeType = type;
}
function refreshAliYunBandWidth() {
    if (instance.internetMaxBandwidthOut == null) {
        $("#instance-band").html("<font color='red'>请选择带宽</font>")
    } else {
        $("#instance-band").html(instance.internetMaxBandwidthOut + "Mbps");
    }
}
function changeBand(num) {
    var bandList = ["0", "2", "5", "10", "20", "30", "50", "60", "70", "80", "100"];
    instance.internetMaxBandwidthOut = bandList[num];
    refreshAliYunBandWidth();
}
function refreshAliYunSecurityGroup() {
    if (securityGroupList.length == 0) {
        $("#set-security-tip").removeClass("hidden");
        $("#security-group").addClass("hidden");
        $("#instance-security").html("请选择安全组");
        $("#instance-security").addClass("red");
        return;
    } else {
        $("#set-security-tip").addClass("hidden");
        $("#security-group").removeClass("hidden");
        if (instance.securityGroup == null) {
            $("#security-disp").html("请选择安全组");
            $("#instance-security").html("请选择安全组");
            $("#instance-security").addClass("red");
        } else {
            $("#security-disp").html(instance.securityGroup.groupName);
            $("#instance-security").html(instance.securityGroup.groupName);
            $("#instance-security").removeClass("red");
        }

        $("#security-list").html("");
        for (var i = 0; i < securityGroupList.length; i++) {
            var item = securityGroupList[i];
            var $item = $("<li><a onclick='changeSecurityGroup(" + i + ")'>" + item.groupName + "</a></li>");
            $("#security-list").append($item);
        }
    }

}
function changeSecurityGroup(index) {
    instance.securityGroup = securityGroupList[index];
    $("#security-disp").html(instance.securityGroup.groupName);
    $("#instance-security").html(instance.securityGroup.groupName);
    refreshAliYunSecurityGroup();
}
function changePeriod(num) {
    var periodList = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "12", "24", "36"];
    instance.period = periodList[num];
    $("#instance-time").html(instance.period + "个月 x" + 1 + "台");
}
function changeNum(obj) {
    var text = $(obj).val();
    if ((/^(\+|-)?\d+$/.test(text)) && text > 0) {

    } else {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请输入正整数'});
        $(obj).val("");
    }
}


function checkAliYunInstanceName() {
    var text = $("#instance-name").val();
    if (text != null) {
        if (text.length >= 2 && text.length <= 128) {
            var regs = /^[https://|http://]/;
            if(regs.test(text)){
                return false;
            }
            var reg = /^[\u4E00-\u9FA5a-zA-Z]+/;
            if (reg.test(text)) {
                reg = /[A-Za-z0-9_\-\.]+$/;
                if (reg.test(text)) {
                    instance.instanceName = text;
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    return false;

}

function checkAliYunPassword() {

    var reg = /^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{8,30}$/;
    if ($("#password").val() != ""||$("#password2").val() != "") {
        if (!reg.test($("#password").val())) {
            instance.password = null;
            $("#password-error2").removeClass("hidden");
            return false;
        } else {
            $("#password-error2").addClass("hidden");
        }

        if ($("#password").val() == $("#password2").val()) {
            instance.password = $("#password").val();
            $("#password-error").addClass("hidden");
            return true;
        } else {
            instance.password = null;
            $("#password-error").removeClass("hidden");
            return false;
        }
    } else {
        instance.password = null;
        $("#password-error").addClass("hidden");
        return false;
    }

}

function refreshAliYunNewInstanceButton() {
    $("#btn-new-instance").attr("disabled", false);
}
//创建主机的逻辑
function newAliYunInstance() {
    if (check()) {
        $.fillTipBox({type:'success',icon:"glyphicon-ok-sign",content:'主机申请中，请勿重复点击！'})
        $.showLoading('show');
        // $.fillTipBox({type: 'info', icon: 'glyphicon-info-sign', content: '开始创建云主机，稍后到列表中查看'})
        $.ajax({
            type: "POST",
            url: "vm/newAliYunInstance",
            dataType: "json",
            data: {
                appname: $('#aliyun_appname').val(),
                instanceChargeType: instance.instanceChargeType,
                regionId: instance.region.regionId,
                zoneId: instance.zone.zoneId,
                ioIoOptimized: instance.ioOptimized,
                instanceType: instance.instanceType.instanceTypeId,
                imageId: instance.image.id,
                systemDiskCategory: instance.systemDiskCategory,
                systemDiskSize: instance.systemDiskSize,
                dataDiskCategory: instance.dataDiskCategory,
                dataDiskSize: instance.dataDiskSize,
                securityGroupId: instance.securityGroup.groupId,
                internetChargeType: instance.internetChargeType,
                internetMaxBandwidthOut: instance.internetMaxBandwidthOut,
                instanceName: instance.instanceName,
                hostname: instance.hostName,
                password: instance.password,
                period: instance.period
            },
            success: function (data) {
                $.showLoading('reset');
                if (data.resultCode == 'success') {
                    $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '云主机创建成功'});
                    window.location.href = "vm/vmlist.jsp";
                } else if (data.resultCode == 'error') {
                    $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '云主机创建失败' + data.resultMessage})
                }
            },
            error: function (data) {
                $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '云主机创建失败,请检查网络连接'})
                $.showLoading('reset');
            }
        });
    }

}

function check() {
    if (instance.region == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择地域'});
        return false;
    }
    if (instance.zone == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择可用区'});
        return false;
    }
    if (instance.ioOptimized == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '是否进行IO优化实例'});
        return false;
    }
    if (instance.instanceType == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择实例规格'});
        return false;
    }
    if (instance.image == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择系统镜像'});
        return false;
    }
    if (instance.os == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择操作系统类型'});
        return false;
    }
    if (instance.systemDiskCategory == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择系统盘类型'});
        return false;
    }
    if (instance.systemDiskSize == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请填写系统盘容量'});
        return false;
    }
    if (instance.dataDiskCategory == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择数据盘类型'});
        return false;
    }
    if (instance.dataDiskSize == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请填写数据盘容量'});
        return false;
    }
    if (instance.securityGroup == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择安全组'});
        return false;
    }
    if (instance.internetMaxBandwidthOut == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择带宽'});
        return false;
    }
    if (instance.instanceChargeType == null) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择付费类型'});
        return false;
    }
    if (instance.instanceChargeType == 'PostPaid') {
        $.tipModal('confirm', 'danger', '包年包月价格非常昂贵，请慎重购买，尽量选择按量付费', function (result) {
        });
        if (instance.period == null) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请选择包月时间'});
            return false;
        }
    }
    if ($("#instance-name").val() != "") {
        if(checkAliYunInstanceName()==false){
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '实例名称格式错误，长度为2-128个字符，以大小写字母或中文开头，可包含数字，"."，"_"或"-"'});
            return false;
        }
    }
    if ($("#password").val() != ""||$("#password2").val()!="") {
        if(checkAliYunPassword()==false){
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '密码格式错误，8 - 30 个字符，且同时包含三项（大写字母，小写字母，数字和特殊符号）'});
            return false;
        }
    }
    if (!$("#protocol-checkbox").is(":checked")) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '请勾选阿里云服务条款'});
        return false;
    }
    return true;
}
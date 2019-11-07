var instanceTypeList = [];
var hardDiskList = [];
var bandWidthList = [];
//var regionId = "beijing";//自身的regionId
var firstTime = true;
var modifyType;
var modifyCpuNum;
var modifyRamSize;
var modifyBandSize;
function modifySource() {
    $.ajax({
        type: "GET",
        url: "vm/modifyresource",
        data: {
            regionId: regionId,
            instanceId: instanceId,
            appname: vdAppname,
            userEmail: userEmail,
            modifyType: modifyType,
            modifyCpuNum: modifyCpuNum,
            modifyRamSize: modifyRamSize,
            modifyBandSize: modifyBandSize
        },
        success: function (data) {
            var result = data.result;
            if (result=="success") {
                $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: result});
                $('#sourceModal').modal('hide');
                window.location.reload();
            } else {
                $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: result});
                $('#sourceModal').modal('hide');
                window.location.reload();
            }
        }
    })
}
//关闭摸态框
function sourcemdlClose() {
    $('#sourceModal').css('zIndex', -1);
    window.location.reload();
}
//打开摸态框
function sourcemdlOpen(sourceType) {
    modifyType = sourceType;
    $.ajax({
        type: "GET",
        url: "vm/getNewInstanceInfo",
        data:{
            appname:vdAppname
        },
        dataType: "json",
        success: function (data) {
            instanceTypeList = data.instanceTypeList;
            bandWidthList = data.bandWidthList;
            hardDiskList = data.hardDiskList;
            //对结果进行排序
            instanceTypeList.sort(function (a, b) {
                return a.cpuCoreCount - b.cpuCoreCount;
            });
            bandWidthList.sort(function (a, b) {
                return a.bandWidth - b.bandWidth;
            });

            //计算计算机初始规格
            var cpuCountTemp = $('#cpuCount').html();
            var moySizeTemp = $('#moySize').html();
            modifyBandSize = Number($('#bandsize').html())
            for (var i = 0; i < instanceTypeList.length; i++) {
                if (cpuCountTemp == instanceTypeList[i].cpuCoreCount && moySizeTemp == instanceTypeList[i].memorySize) {
                    instanceTypePrice = instanceTypeList[i];
                    break;
                }
            }

            switch (sourceType) {
                case "cpumoy":
                    $('#vmBand').addClass('hidden');
                    $('#vmType').removeClass('hidden');
                    newinstanceType();
                    break;
                case "vmBand":
                    $('#vmType').addClass('hidden');
                    $('#vmBand').removeClass('hidden');
                    newBandWidth(0);
                    break;
            }
            $('#sourceModal').css("zIndex", 1200);
            $('#sourceModal').modal('show');
        }
    });
}

function newinstanceType() {
    $("#instanceType-list").html("");
    for (var i = 0; i < instanceTypeList.length; i++) {
        var item = instanceTypeList[i];
        if (regionId == item.regionId) {
            var text = item.cpuCoreCount + "核" + item.memorySize + "G(" + item.instanceTypeId + ")";
            var $item = $("<li><a onclick='changeInstanceType(" + i + ")'>" + text + "</a></li>");
            $("#instanceType-list").append($item);
        }
    }
}

function changeInstanceType(i) {
    var typeItem = instanceTypeList[i];
    instanceTypePrice = instanceTypeList[i];
    var text = typeItem.cpuCoreCount + "核" + typeItem.memorySize + "G(" + typeItem.instanceTypeId + ")";
    $('#instanceType-disp').html(text);
    modifyCpuNum = typeItem.cpuCoreCount;
    modifyRamSize = typeItem.memorySize;
    countPrice();
}

function newBandWidth(length) {
    //if (firstTime) {
    var text = "[";
    var text2 = "[";
    for (var i = 0; i < bandWidthList.length - 1; i++) {
        text += i + ",";
        text2 += "\"" + bandWidthList[i].bandWidth + "Mbps\",";
    }
    text += bandWidthList.length - 1 + "]";
    text2 += "\"" + bandWidthList[bandWidthList.length - 1].bandWidth + "Mbps\"" + "]";

    $("#bandWidth").attr("data-slider-ticks", text);
    $("#bandWidth").attr("data-slider-ticks-labels", text2);
    $("#bandWidth").parent().find("*:first").remove();
    $("#bandWidth").attr("display", "block");
    sliderBandWidth = new Slider("#bandWidth");
    sliderBandWidth.setValue(length);  //换成其他的值
    firstTime = false;
    // }
}

function changeBand(num) {
    modifyBandSize = bandWidthList[num].bandWidth;
    countPrice();
}
//修改防火墙
function changeSecurityGroupId(provider, instanceId, regionId) {
    var href = 'vm/vmdetail/changeSecurityGroup.jsp?instanceId=' + instanceId + "&regionId=" + regionId + "&provider=" + provider+"&appname="+vdAppname;
    changeSecurityGroup = $.frontModal({
        size: 'modal-md',
        title: '修改防火墙',
        href: href
    }).on('shown.bs.modal', function () {

        function countPrice() {
            var datasizetemp = Number($('#datadisksize').html()) + Number($('#sysDiskSize').html());//系统盘加数据盘的大小
            for (var i = 0; i < hardDiskList.length; i++) {
                if (datasizetemp == hardDiskList[i].hardDisk) {
                    var hardPrice = hardDiskList[i];
                    break;
                }
            }
            var bandsizetemp = modifyBandSize;
            for (var j = 0; j < bandWidthList.length; j++) {
                if (bandsizetemp == bandWidthList[j].bandWidth) {
                    var bandPrice = bandWidthList[j];
                    break;
                }
            }
            var payTypeCn = $('#payTypeCn').html();
            switch (payTypeCn) {
                case "包年":
                    $('#showprice').removeClass('hidden');
                    var basePrice = Number(hardPrice.yearPrice) + Number(instanceTypePrice.yearPrice) + Number(bandPrice.yearPrice);
                    var finalPrice = basePrice / 100;
                    $('#finalprice').html("共每年" + finalPrice + "元");
                    break;
                case "包月":
                    $('#showprice').removeClass('hidden');
                    var basePrice = Number(hardPrice.monthPrice) + Number(instanceTypePrice.monthPrice) + Number(bandPrice.monthPrice);
                    var finalPrice = basePrice / 100;
                    $('#finalprice').html("共每月" + finalPrice + "元");
                    break;
                case "包日":
                    $('#showprice').removeClass('hidden');
                    var basePrice = Number(hardPrice.dayPrice) + Number(instanceTypePrice.dayPrice) + Number(bandPrice.dayPrice);
                    var finalPrice = basePrice / 100;
                    $('#finalprice').html("共每日" + finalPrice + "元");
                    break;
            }
        }
    });
}
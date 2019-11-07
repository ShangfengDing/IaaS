/**
 * Created by rain on 2016/11/2.
 */

var page = 1;
var souType = "all";  //active运行中的，expiring即将过期的

// 获取到所有的appName
$(function () {
    $('#yun_search_panel').hide();
    getYunhaiAccount();
});

function getYunhaiAccount() {
    $.ajax({
        type: "GET",
        url: "account/getYunhaiAcount",
        success: function (data) {
            var disRegionIds = data.disRegionIds;
            disZoneIds = data.disZoneIds;  //全局变量
            if (disRegionIds == null) {
                $.fillTipBox({type: 'warn', icon: 'glyphicon-alert', content: '暂时没有云账户'});
                return;
            }
            // 获得regionId
            for (var i in disRegionIds) {
                $regionItem = $("<option data-icon='glyphicon-map-marker' value='" + disRegionIds[i] + "'>" + disRegionIds[i] + "</option>");
                $('#selectRegion').append($regionItem);
            }
            // 获得zoneId（也就获得appname）
            var regionIdTemp = disRegionIds[0];
            var zoneAndAppname = disZoneIds[regionIdTemp];
            $('#selectZone').html('');
            for (var k in zoneAndAppname) {
                $zoneItem = $("<option data-icon='glyphicon-map-marker' value='" + zoneAndAppname[k].zoneId + "'>" + zoneAndAppname[k].appname + "</option>");
                $('#selectZone').append($zoneItem);
            }

            changeZone();
        },
        error:function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云账户所属可用区失败'});
        }
    });
}

//地域的select选择改变，将调用这个获得主机列表
function changeRegion() {
    var regionIdTemp = $("#selectRegion").val();
    var zoneAndAppname = disZoneIds[regionIdTemp];
    for (var k in zoneAndAppname) {
        $('#selectZone').html('');
        $zoneItem = $("<option data-icon='glyphicon-map-marker' value='" + disRegionIds[k].zoneId + "'>" + disRegionIds[i].appname + "</option>");
        $('#selectZone').append($zoneItem);
    }
    changeZone();
}

function changeZone() {
    vmshow(souType, false);
}


//搜索的toggle函数
function exsearch() {
    $('#yun_search_panel').slideToggle();
}

//决定是显示那个厂商的搜索panel
function searchShow() {
    var providerEn = $('#appproviderEn').text();
    if (providerEn == 'aliyun') {
        $('#yunhai-content').addClass('hidden');
        $('#select-content').removeClass('hidden');
    } else if(providerEn == "yunhai"){
        $('#yunhai-content').removeClass('hidden');
        $('#select-content').addClass('hidden');
    }
}

//申请云主机,区分厂商
function yunapply() {
    // var providerEn = $('#appproviderEn').text();
    var providerEn = "yunhai";
    var vmappname = $('#selectZone :selected').text();
    var vmzone = $('#selectZone :selected').val();
    switch (providerEn) {
        case 'yunhai':
            window.location.href="vm/newvm/newyunhaivm.jsp?appname="+vmappname+"&ulMenuName="+vmappname+"&ulMenuProviderEn="+providerEn+"&zone="+vmzone;
            break;
        case 'aliyun':
            window.location.href="vm/newvm/aliyun/newaliyunvm.jsp?appname="+vmappname+"&ulMenuName="+vmappname+"&ulMenuProviderEn="+providerEn+"&zone="+vmzone;
            break;
    }
}
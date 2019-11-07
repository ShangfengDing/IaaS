/**
 * Created by rain on 2016/11/2.
 */

var page = 1;
var souType = "all";  //active运行中的，expiring即将过期的

// 获取到所有的appName
$(function () {
    $('#yun_search_panel').hide();
    getAppname(changeAppname);        //changeAppName 是改变appname后引用的参数
});

function getAppname(changeAppname) {
    var checkOnece = true;
    $.ajax({
        type: "POST",
        url: "common/getAppName",
        success: function (data) {
            appdata = data;
            $('#selectAppname').html('');
            for (var i in data) {
                var appName = data[i].appName;
                var providerEn = data[i].providerEn;
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
            var ulMenuName = $("#selectAppname li:first a").text();
            var ulMenuProviderEn = $("#selectAppname li:first span").text();
            $.ajax({
                type: "POST",
                url: "account/getAccountZoneId",
                data: {
                    name: ulMenuName
                },
                success: function (data) {
                    $("#selectZone").val(data.zone)
                    $("#selectZone").attr("disabled","disabled").css("background-color","#EEEEEE;");
                },
                error:function () {
                    $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云账户所属可用区失败'});
                }
            });
            clickChangeAppName(ulMenuProviderEn, ulMenuName);
        }
    })
}

//appName发生改变，将调用此函数
function changeAppname() {
    $('#yun_search_panel').removeClass('hidden');
    $('#yun_search_panel').hide(1);
    var appName = $('#appnamemenu').text();
    $.ajax({
        type: "POST",
        url: "account/getAccountZoneId",
        data: {
            name: appName
        },
        success: function (data) {
            $("#selectZone").val(data.zone)
            $("#selectZone").attr("disabled","disabled").css("background-color","#EEEEEE;");
        },
        error:function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云账户所属可用区失败'});
        }
    });
    $.ajax({
        type: "POST",
        url: "common/getRegionId",
        data: {
            appname: appName
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云主机列表成功'});
            var appProviderEn = $('#appproviderEn').text();//提供商
            $('#selectRegion').html('');
            for (var i in data) {
                $regionItem = $("<option data-icon='glyphicon-map-marker' value='" + data[i].regionId + "'>" + data[i].localName + "</option>");
                $('#selectRegion').append($regionItem);
            }
            searchShow();
            changeRegion();
        },
        error:function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云主机列表失败'});
        }
    });
}

//地域的select选择改变，将调用这个获得主机列表
function changeRegion() {
    var vmappname = $('#appnamemenu').text();
    var appProviderEn = $('#appproviderEn').text();
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
function yunapply() { alert("asdf");
    var providerEn = $('#appproviderEn').text();
    var vmappname = $('#appnamemenu').text();
    switch (providerEn) {
        case 'yunhai':
            window.location.href="vm/newvm/newyunhaivm.jsp?appname="+vmappname+"&&ulMenuName="+vmappname+"&&ulMenuProviderEn="+providerEn;
            break;
        case 'aliyun':
            window.location.href="vm/newvm/aliyun/newaliyunvm.jsp?appname="+vmappname+"&&ulMenuName="+vmappname+"&&ulMenuProviderEn="+providerEn;
            break;
    }
}
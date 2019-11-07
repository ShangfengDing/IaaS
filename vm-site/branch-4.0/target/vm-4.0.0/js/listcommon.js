/**
 * Created by rain on 2016/9/18.
 * 列表页的公共js
 */
var appdata = [];

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

function changeName(i) {
    var provierEn = appdata[i].providerEn;
    var appName = appdata[i].appName;
    clickChangeAppName(provierEn, appName);
}

function clickChangeAppName(providerEn, appname) {
    if (providerEn=="yunhai") {
        $('#appnameicon').removeClass("icon-freeshare-aliyun");
        $('#appnameicon').addClass("glyphicon-cloud");
        $('#selectZone').css("display", "block");
    } else if (providerEn == "aliyun") {
        $('#appnameicon').removeClass("glyphicon-cloud");
        $('#appnameicon').addClass("icon-freeshare-aliyun");
        $('#selectZone').css("display", "none");
    }
    $('#appnamemenu').html(appname);
    $('#appproviderEn').html(providerEn);
    changeAppname();
}
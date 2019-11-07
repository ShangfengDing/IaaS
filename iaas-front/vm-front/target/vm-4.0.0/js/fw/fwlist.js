var createFWModal;
var modfiyFWModal;
var newRuleModal;
var fwLoading;

//传递的参数,全局变量
var fwproviderEn;
var fwregionId;
var fwuserEmail;

//------------------------------出事化操作，得到appname和regionId---------------------------//
//获取appname
$(function () {
    getAppname(changeAppname);
});

//改变appname调用的函数
function changeAppname() {
    var fwappname = $('#appnamemenu').text();
    $.ajax({
        type: "POST",
        url: "common/getRegionId",
        data: {
            appname: fwappname
        },
        success: function (data) {
            $('#selectRegionId').html('');
            for (var i in data) {
                $regionItem = $("<option value='" + data[i].regionId + "'>" + data[i].localName + "</option>");
                $('#selectRegionId').append($regionItem);
            }
            $('#selectRegionId').change(changeRegion());
        }
    });
}
//点击不同的地域
function changeRegion() {
    var fwappname = $('#appnamemenu').text();
    var fwregionId = $('#selectRegionId').val();
    $('#show_data_area').addClass('hidden');
    $("#prosloading").removeClass("hidden");//进度加载条
    $("#back_divPage").addClass('hidden');
    $.ajax({
        type: "POST",
        url: "fw/fwlistDiv",
        data: {
            selectAppname: fwappname,
            selectRegionId: fwregionId,
            clickPage: "1",
            fwflag: false
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云安全列表成功'});
            $("#prosloading").addClass("hidden");
            $('#show_data_area').html(data);
            var result = $('#fw_result').val();
            var totalPage = $('#result_totalpage').val();
            checkResult(result, totalPage, "1");
        },
        error:function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云安全列表失败'});
        }
    })
}

//------------------------------分页的操作---------------------------//
//分页,首页不会到fDivPage
function getPage(page) {
    var fwappname = $('#appnamemenu').text();
    var fwregionId = $('#selectRegionId').val();
    $('#show_data_area').addClass('hidden');
    $("#prosloading").removeClass("hidden");//进度加载条
    $("#back_divPage").addClass('hidden');
    $.ajax({
        type: 'POST',
        url: "fw/fwlistDiv",
        data: {
            selectAppname: fwappname,
            selectRegionId: fwregionId,
            clickPage: page,
            fwflag: false
        },
        success: function (data) {
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '获取云安全列表成功'});
            $("#show_data_area").html(data);
            $("#prosloading").addClass("hidden");
            var result = $('#fw_result').val();
            var totalPage = $('#result_totalpage').val();
            checkResult(result, totalPage, page);
        },
        error:function () {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '获取云安全列表失败'});
        }
    });
}

//对获取页面结果的处理
function checkResult(result, totalPage,page) {
    if (result == 'success') {
        if (totalPage == '0') {
            $("#show_data_area").addClass("hidden");
            $("#back_divPage").addClass('hidden');
        } else {
            $("#back_divPage").removeClass('hidden');
            $("#show_data_area").removeClass("hidden");
            $("#back_divPage").html($.getDivPageHtml(page, totalPage, "getPage"));
        }
    } else {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: result});
    }
}

//------------------------------防火墙的操作---------------------------//
//显示某条防火墙规则详情,传递厂商
function showDetail(obj, id, fwuserEmail/*, list*/) {
    var fwappname = $('#appnamemenu').text();
    var fwproviderEn = $('#appproviderEn').text();
    var fwregionId = $('#selectRegionId').val();
    if ($("#ruledetail_" + id).hasClass("hidden")) {
        $.ajax({
            async: false,
            type: "POST",
            url: "fw/rulelist",
            dataType: "json",
            data: {
                appname: fwappname,
                regionId: fwregionId,
                securityGroupId: id
            },
            success: function (json) {      //判断是否是云海和阿里云
                var result = eval(json);
                var rule_result = $("#ruleresult_" + id);
                rule_result.empty();
                if (fwproviderEn == 'yunhai') {
                    rule_result.append(
                        '<thead><tr>' +
                        '<th class=\'col-sm-2\'>' + "协议类型" + '</th>' +
                        '<th class=\'col-sm-2\'>' + "开放端口" + '</th>' +
                        '<th class=\'col-sm-4\'>' + "可访问源IP段" + '</th>' +
                        '<th class=\'col-sm-2\'>' + "操作" + '</th>' +
                        '</tr></thead>'
                    );
                    if (result.length == 0) {
                        rule_result.append(
                            '<tr><td class="col-sm-12" style="text-align: center;" colspan="12">暂无</td>');
                    } else {
                        $.each(result, function (index) {
                            var ipProtocol = result[index].ipProtocol;
                            var portRange = result[index].portRange;
                            var sourceCidrIp = result[index].sourceCidrIp;
                            rule_result.append(
                                '<tr>' +
                                '<td class=\'col-sm-2\'>' + result[index].ipProtocol + '</td>' +
                                '<td class=\'col-sm-2\'>' + result[index].portRange + '</td>' +
                                '<td class=\'col-sm-4\'>' + sourceCidrIp + '</td>' +
                                '<td class=\'col-sm-2\'> ' +
                                '<a href=\'javascript:void(0)\' onclick=\'deleteRule("' + id + '","' + ipProtocol + '","' + portRange + '","' + sourceCidrIp + '")\' ' +
                                '>删除</a>' +
                                '</td>' +
                                '</tr>'
                            );
                        });
                    }
                } else if (fwproviderEn == 'aliyun') {
                    rule_result.append(
                        '<thead><tr>' +
                        '<th class=\'col-sm-2\'>' + "协议类型" + '</th>' +
                        '<th class=\'col-sm-2\'>' + "开放端口" + '</th>' +
                        '<th class=\'col-sm-4\'>' + "可访问源IP段" + '</th>' +
                        '<th class=\'col-sm-2\'>' + "优先级" + '</th>' +
                        '<th class=\'col-sm-2\'>' + "操作" + '</th>' +
                        '</tr></thead>'
                    );
                    if (result.length == 0) {
                        rule_result.append(
                            '<tr><td class="col-sm-12" style="text-align: center;" colspan="12">暂无</td>');
                    } else {
                        $.each(result, function (index) {
                            var ipProtocol = result[index].ipProtocol;
                            var portRange = result[index].portRange;
                            var sourceCidrIp = result[index].sourceCidrIp;
                            if (sourceCidrIp != "") {
                                rule_result.append(
                                    '<tr>' +
                                    '<td class=\'col-sm-2\'>' + result[index].ipProtocol + '</td>' +
                                    '<td class=\'col-sm-2\'>' + result[index].portRange + '</td>' +
                                    '<td class=\'col-sm-4\'>' + sourceCidrIp + '</td>' +
                                    '<td class=\'col-sm-2\'>' + result[index].priority + '</td>' +
                                    '<td class=\'col-sm-2\'> ' +
                                    '<a href=\'javascript:void(0)\' onclick=\'deleteRule("' + id + '","' + ipProtocol + '","' + portRange + '","' + sourceCidrIp + '")\' ' +
                                    '>删除</a>' +
                                    '</td>' +
                                    '</tr>'
                                );
                            }
                        });
                    }
                }

                if (obj != null) {
                    obj.innerHTML = "收起";
                    $("#ruledetail_" + id).removeClass("hidden");
                    $("#ruleadd_" + id).removeClass("hidden");
                }
            },
            error: function () {

            }
        });
    } else {
        if (obj != null) {
            $("#ruledetail_" + id).addClass("hidden");
            $("#ruleadd_" + id).addClass("hidden");
            obj.innerHTML = "规则";
        }
    }
}

//删除防火墙
function deleteSecurityGroup(groupId, fwuserEmail) {
    var fwappname = $('#appnamemenu').text();
    var fwproviderEn = $('#appproviderEn').text();
    var fwregionId = $('#selectRegionId').val()
    $.tipModal('confirm', 'danger', '确定删除吗？', function (result) {
        if (result == true) {
            $.showLoading('show');
            $.ajax({
                async: false,
                type: 'POST',
                url: 'fw/delfw',
                dataType: 'json',
                data: {
                    securityGroupId: groupId,
                    appname: fwappname,
                    regionId: fwregionId,
                    userEmail: fwuserEmail
                },
                success: function (data) {
                    var result = eval(data);
                    $.showLoading('reset');
                    $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '删除防火墙成功'});
                    changeRegion();
                },
                error: function () {
                    $.showLoading('reset');
                    $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '删除防火墙失败'});
                }

            });
        }
    });
}

//更改，选择常用端口，还是自定义端口
function commonPort() {
    $('#commonPort').removeClass('hidden');
    $('#customPort').addClass('hidden');
}

function customPort() {
    $('#commonPort').addClass('hidden');
    $('#customPort').removeClass('hidden');
}

//新增防火墙
function submitFw() {
    var name = $("#newfwName").val().trim();
    var description = $("#newfwDescription").val().trim();
    var fwproviderEn = $('#appproviderEn').text();
    var fwappname = $('#appnamemenu').text();
    var fwregionId = $('#selectRegionId').val();

    if (name.length == 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得为空'});
        return;
    } else if (name.length > 20) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得超过20字'});
        return;
    }
    if (description.length == 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '描述不得为空'});
        return;
    } else if (description.length > 100) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '描述不得超过100字'});
        return;
    }
    if (!checkGroupName(name)) {
        $.fillTipBox({type: 'danger', icon: "glyphicon-alert", content: '名称命名不合法!'});
        return;
    }
    if (!checkGroupDescription(description)) {
        $.fillTipBox({type: 'danger', icon: "glyphicon-alert", content: "描述命名不合法!"});
        return;
    }
    $.showLoading('show');
    $.ajax({
        async: true,
        type: "POST",
        url: "fw/newfw",
        data: {
            securityGroupName: name,
            securityGroupDescription: description,
            appname: fwappname,
            regionId: fwregionId
        },
        success: function (data) {
            var result = eval(data);
            $.showLoading('reset');
            closeFacebox();
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '新建防火墙成功'});
            changeRegion();
        },
        error: function () {
            $.showLoading('reset');
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '新建防火墙失败'});
        }
    });
}

//修改防火墙名称
function modFw(id, fwuserEmail) {
    var name = $("#fwName").val().trim();
    var description = $("#fwDescription").val().trim();
    var fwproviderEn = $('#appproviderEn').text();
    var fwappname =  $('#appnamemenu').text();
    var fwregionId = $('#selectRegionId').val();

    if (name.length == 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得为空'});
        return;
    } else if (name.length > 20) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '名称不得超过20字'});
        return;
    }
    if (description.length == 0) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '描述不得为空'});
        return;
    } else if (description.length > 100) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '描述不得超过100字'});
        return;
    }
    if (!checkGroupName(name)) {
        $.fillTipBox({type: 'danger', icon: "glyphicon-alert", content: '名称命名不合法!'});
        return;
    }
    if (!checkGroupDescription(description)) {
        $.fillTipBox({type: 'danger', icon: "glyphicon-alert", content: "描述命名不合法!"});
        return;
    }
    $.showLoading('show');
    $.ajax({
        async: false,
        type: 'POST',
        url: 'fw/modfw',
        data: {
            securityGroupId: id,
            securityGroupName: name,
            securityGroupDescription: description,
            appname: fwappname,
            regionId: fwregionId,
            userEmail: fwuserEmail
        },
        success: function () {
            $.showLoading('reset');
            modfiyFWModal.modal('hide');
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '修改防火墙成功'});
            changeRegion();
        },
        error: function () {
            $.showLoading('reset');
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '修改防火墙失败'});
        }


    });
}
//创建安全组的模态框
function createSecurityGroup() {
    var href = 'fw/newfw.jsp';
    createFWModal = $.frontModal({
        size: 'modal-md',
        title: '新建防火墙',
        href: href
    }).on('shown.bs.modal', function () {

    });
}
//修改安全组的模态框
function modifySecurityGroup(securityGroupId, securityGroupName, description, fwuserEmail) {
    var href = 'fw/modfw.jsp?securityGroupId=' + securityGroupId + "&securityGroupName=" + securityGroupName + "&securityGroupDescription=" + description + "&fwuserEmail=" + fwuserEmail;
    modfiyFWModal = $.frontModal({
        size: 'modal-md',
        title: '编辑防火墙信息',
        href: href
    }).on('shown.bs.modal', function () {

    });
}

//------------------------------规则的操作---------------------------//
//新增防火墙规则
function submitRules(id) {
    var common = $("#commonPort").hasClass('hidden');
    var custom = $("#customPort").hasClass('hidden');
    var fwproviderEn = $('#appproviderEn').text();
    var fwappname = $('#appnamemenu').text();
    var fwregionId = $('#selectRegionId').val();
    if (custom == false) {//当前为custom
        var protocol = $("#protocol").val();
        var fromPort = $("#fromPort").val().trim();
        var toPort = $("#toPort").val().trim();
        var allowedIP = $("#allowedIP").val().trim();

        if (!checkPort(parseInt(fromPort))) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '起始端口需在0~65535之间!'});
            return false;
        }
        else if (!checkPort(parseInt(toPort))) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '截止端口需在0~65535之间!'});

            return false;
        }
        else if (!checkIp(allowedIP)) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: 'IP段不合法!'});
            return false;
        }
    }
    if (common == false) {//当前为common
        var commonPort = $("#comPort").val();
        var allowedIP = $("#allowedIP_comm").val().trim();
        if (!checkIp(allowedIP)) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: 'IP段不合法!'});
            return false;
        }
        var array = commonPort.split("|");
        var protocol = array[0];
        if (protocol == "ICMP") {
            var fromPort = -1;
            var toPort = -1;
        } else {
            var fromPort = array[1].substring(array[1].indexOf("(") + 1, array[1].indexOf(")"));
            var toPort = fromPort;
        }
    }
    $.showLoading('show');
    $.ajax({
        async: false,
        type: 'POST',
        url: 'fw/newrule',
        dataType: 'json',
        data: {
            appname: fwappname,
            regionId: fwregionId,
            securityGroupId: id,
            ipProtocol: protocol,
            portRange: fromPort + '/' + toPort,
            sourceCidrIp: allowedIP,
            policy: null,
            priority: null,
            sourceGroupId: null,
            sourceGroupOwnerAccount: null
        },
        success: function () {
            newRuleModal.modal('hide');
            $.showLoading('reset');
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '新建防火墙规则成功'});
            $("#ruledetail_" + id).addClass("hidden");
            showDetail($("#manage_rule"), id)
        },
        error: function () {
            $.showLoading('reset');
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '新建防火墙规则失败'});
        }
    });

}

//新增防火墙规则
function submitAliRules(id) {
    var fwproviderEn = $('#appproviderEn').text();
    var fwappname = $('#appnamemenu').text();
    var fwregionId = $('#selectRegionId').val();
    var fwnicType = 'internet';     //网卡类型，只有公网
    var fwpolicy = $('#fwpolicy').val();     //授权策略
    var fwIpProtocol = $('#fwIpProtocol').val();      //协议
    var fwPortRange = $('#fwPortRange').val().trim();    //端口号
    var fwSourceCidrIp = $("#fwSourceCidrIp").val().trim();       //Ip段
    var fwPriority = $('#fwPriority').val().trim();
    //检查端口号
    if ((fwIpProtocol == 'tcp') | (fwIpProtocol == 'udp')) {
                var newPortRange = fwPortRange.split('/');
                if (newPortRange.length != 2) {
                    $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '端口不合法!'});
                    return false;
        } else if (Number(newPortRange[0]) < 1) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '端口不合法!'});
            return false;
        } else if (Number(newPortRange[1]) > 65535) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '端口不合法!'});
            return false;
        } else if (Number(newPortRange[0]) > Number(newPortRange[1])) {
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '端口不合法!'});
            return false;
        }
    }
    //检查IP地址
    if (!checkIp(fwSourceCidrIp)) {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: 'IP段不合法!'});
        return false;
    }
    if (Number(fwPriority) >= 1 & Number(fwPriority) <= 100) {
        //是对的
    } else {
        $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '优先级不合法!'});
        return false;
    }
    $.showLoading('show');
    $.ajax({
        async: false,
        type: 'POST',
        url: 'fw/newrule',
        dataType: 'json',
        data: {
            appname: fwappname,
            regionId: fwregionId,
            securityGroupId: id,
            ipProtocol: fwIpProtocol,
            portRange: fwPortRange,
            sourceCidrIp: fwSourceCidrIp,
            policy: fwpolicy,
            priority: fwPriority,
            sourceGroupId: null,
            sourceGroupOwnerAccount: null
        },
        success: function () {
            newRuleModal.modal('hide');
            $.showLoading('reset');
            $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '新建防火墙规则成功'});
            $("#ruledetail_" + id).addClass("hidden");
            showDetail($("#manage_rule"), id)
        },
        error: function () {
            $.showLoading('reset');
            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '新建防火墙规则失败'});
        }
    });

}

//删除防火墙规则
function deleteRule(id, protocol, portRange, allowedIP) {
    var fwproviderEn = $('#appproviderEn').text();
    var fwappname = $('#appnamemenu').text();
    var fwregionId = $('#selectRegionId').val();
    $.tipModal('confirm', 'danger', '确定删除该条规则？', function (result) {
        if (result == true) {
            $.showLoading('show');
            $.ajax({
                async: false,
                type: 'POST',
                url: 'fw/delrule',
                dataType: 'json',
                data: {
                    appname: fwappname,
                    regionId: fwregionId,
                    securityGroupId: id,
                    ipProtocol: protocol,
                    portRange: portRange,
                    sourceCidrIp: allowedIP,
                    policy: null,
                    priority: null,
                    sourceGroupId: null,
                    sourceGroupOwnerAccount: null
                },
                success: function () {
                    $.showLoading('reset');
                    $.fillTipBox({type: 'success', icon: 'glyphicon-ok-sign', content: '删除防火墙规则成功'});
                    $("#ruledetail_" + id).addClass("hidden");
                    showDetail($("#manage_rule"), id)
                },
                error: function () {
                    $.showLoading('reset');
                    $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: '删除防火墙规则失败'});
                }
            });
        }
    });
}

//检查端口是否在0~65535之间
function checkPort(port) {
    if (port >= 0 && port <= 65535) {
        return true;
    } else {
        return false;
    }
}
//检查IP段是否满足要求，形如192.168.1.13/16
function checkIp(ip) {
    if (ip.indexOf("/") == -1) {
        return false;
    }
    var array = ip.split("/");
    var ipAddr = array[0].trim();
    var netmask = array[1].trim();
    var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
    var reg1 = /^(([0-9])|((1|2)[0-9])|(3[0-2]))$/;
    if (ipAddr.match(reg)) {
        if (reg1.test(netmask)) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}

function addNewRule(securityGroupId) {
    var fwproviderEn = $('#appproviderEn').text();
    var fwappname = $('#appnamemenu').text();
    var fwregionId = $('#selectRegionId').val();
    switch (fwproviderEn) {
        case "yunhai":
            var href = 'fw/newrule.jsp?securityGroupId=' + securityGroupId;
            break;
        case "aliyun":
            var href = 'fw/aliyunrule.jsp?securityGroupId=' + securityGroupId;
            break;
    }
    newRuleModal = $.frontModal({
        size: 'modal-md',
        title: '添加规则',
        href: href
    }).on('shown.bs.modal', function () {

    });
}
function closeFacebox() {
    if (createFWModal != null) {
        createFWModal.modal('hide');
    }
}

//检查阿里云名称，安全组名称，不填则为空，默认值为空，[2, 128] 英文或中文字符，
//必须以大小字母或中文开头，可包含数字，”.”，”_”或”-”，磁盘名称会展示在控制台。
//不能以 http:// 和 https:// 开头
function checkGroupName(Gname) {
    //匹配http://和https://
    var Gnum1 = Gname.search("^https://|http://");
    if (Gnum1 == 0) {
        return false;
    }
    //必须以大小字母或中文开头
    var Gnum2 = Gname.search("^[a-zA-Z]|[\u4e00-\u9fa5]");
    if (Gnum2 == 0) {
        return true;
    } else {
        return false;
    }
}
//安全组描述信息，[2, 256] 个字符。不能以 http:// 和 https:// 开头。
function checkGroupDescription(Gdescription) {
    //匹配http://和https://
    var Gnum = Gdescription.search("^https://|http://");
    if (Gnum == 0) {
        return false;
    } else {
        return true;
    }
}

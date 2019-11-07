<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/21
  Time: 16:54
  修改防火请规则
--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="utf-8">
    <s:include value="../../template/_head.jsp"/>
</head>
<body>
<div class="modal-body">
    <div id="commonPort">
        <div class="form-group">
            <label for="comPort">常用端口</label>
            <select class="form-control" id="comPort">
            </select>
        </div>
    </div>
    <div id="aliyun-operate" class="hidden">
        <button class="btn btn-primary"
                onclick="submitChange()">添加新安全组
        </button>
        <button class="btn btn-primary"
                onclick="alert('开发哥哥正在努力ing')">替换现有安全组
        </button>
        <button class="btn btn-default" onclick="changeSecurityGroup.modal('hide')">取消</button>
    </div>

    <div id="yunhai-operate" class="hidden">
        <button class="btn btn-primary"
                onclick="submitChange()">确定
        </button>
        <button class="btn btn-default" onclick="changeSecurityGroup.modal('hide')">取消</button>
    </div>

</div>
</body>
<script>
//    var regionId = getQueryString("regionId");
//    var instanceId = getQueryString("instanceId");
//    var provider = getQueryString("provider");
//    var appname = getQueryString("appname");
    var originSecurityGroupId = $("");
    if(provider=='yunhai'){
        $("#yunhai-operate").removeClass('hidden');
    }else if(provider=='aliyun'){
        $("#aliyun-operate").removeClass('hidden');
    }
    //防火墙，获取规则列表
    $(document).ready(function () {
        $.showLoading('show');
        if (provider == 'yunhai') {
            $.ajax({
                url: 'fw/fwlistbyregionid',
                type: 'GET',
                data: {
                    selectRegionId: regionId
                },
                success: function (result) {
                    var json = eval(result);
                    var div = $("#comPort");
                    div.empty();
                    $.each(json, function (index) {
                        var securityGroup = json[index];
                        var item = "<option value=\'" + securityGroup.securityGroupId + "\'>" + securityGroup.securityGroupName + "</option>"
                        div.append(item);
                    });
                    $.showLoading('reset');
                },
                error: function () {
                    $.showLoading('reset');

                }
            });
        } else if (provider == 'aliyun') {
            $.ajax({
                url: 'fw/aliyunfwlistbyregionid',
                type: 'GET',
                data: {
                    selectRegionId: regionId
                },
                success: function (result) {
                    var json = eval(result);
                    var div = $("#comPort");
                    div.empty();
                    $.each(json, function (index) {
                        var securityGroup = json[index];
                        var item = "<option value=\'" + securityGroup.securityGroupId + "\'>" + securityGroup.securityGroupName + "</option>"
                        div.append(item);
                    });
                    $.showLoading('reset');
                },
                error: function () {
                    $.showLoading('reset');

                }
            });

        }
    })
    function getQueryString(value) {
        var reg = new RegExp("(^|&)" + value + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
    function submitChange() {
        $.tipModal('confirm', 'danger', '修改防火墙云主机将会被关闭，请稍后重新手动开启', function (result) {
            if (result == true) {
                var securityGroupId = $('#comPort').val();
                if (securityGroupId != null) {
                    $.showLoading('show');
                    $.fillTipBox({type: "info", icon: "glyphicon-info-sign", content: "正在修改安全组"});
                    $.ajax({
                        url: 'vm/modifysecuritygroup',
                        type: 'POST',
                        data: {
                            appname: vdAppname,
                            instanceId: instanceId,
                            regionId: regionId,
                            securityGroupId: securityGroupId
                        },
                        success: function (result) {
                            console.log(result)
                            if(provider=='aliyun'&&result.resultCode=='success'){
                                $.fillTipBox({type: "success", icon: "glyphicon-ok-sign", content: "修改成功"});
                                $.showLoading('reset');
                                window.location.reload();
                                changeSecurityGroup.modal('hide')
                            }else if(provider=='aliyun'&&result.resultCode=='error'){
                                $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: "修改失败" + result.resultMessage});
                                $.showLoading('reset');
                            }else{
                                $.showLoading('reset');
                                window.location.reload();
                                changeSecurityGroup.modal('hide')
                            }
                        },
                        error: function () {
                            $.fillTipBox({type: 'danger', icon: 'glyphicon-alert', content: "修改失败"});
                            $.showLoading('reset');
                            changeSecurityGroup.modal('hide')

                        }
                    });
                }
            }
        })
    }
</script>
</html>
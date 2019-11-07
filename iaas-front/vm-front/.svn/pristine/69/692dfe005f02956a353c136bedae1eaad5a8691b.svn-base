<%--
  Created by IntelliJ IDEA.
  User: rain
  Date: 2016/10/16
  Time: 16:32
  function: 修建云主机和云硬盘时需要选择appname
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<div class="modal-body">
    <input class="hidden" id="type" value="${param.type}"/>
    <form class="form-horizontal">
        <div class="form-group" style="background-color: #f0f040;margin: 0;padding-top: 8px;">
            <label class="col-md-12" style="color:#f04040; font-size: 10px">
                请选择账户，选定后将在该账号下创建云产品！
            </label>
        </div>
        <div class="form-group" style="margin-top: 15px">
            <label class="col-sm-2 control-label">账户名称</label>
            <div class="col-sm-5">
                <select class="form-control front-no-radius front-no-box-shadow" id="sumAppname">
                    <!--- 显示用户的appname -->
                </select>
            </div>
        </div>
    </form>
</div>
<div class="modal-footer">
    <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
    <a href="javascript:void(0)" onclick="sumNewApply()" class="btn btn-primary">确定</a>
</div>
<script>
    $(function () {
        $.ajax({
            type: "POST",
            url: "common/getAppName",
            success: function (data) {
                $('#sumAppname').html('');
                for (var i in data) {
                    $appname = $("<option value='" + data[i].providerEn + "'>" + data[i].appName+"["+ data[i].providerCn +"]" + "</option>");
                    $('#sumAppname').append($appname);
                }
            }
        });
    });


    function sumNewApply() {
        var newValue = $('#sumAppname option:selected').text();
        var newProviderEn = $('#sumAppname').val();
        console.log(newProviderEn+newValue);
        var newAppname = newValue.split('[')[0];  //split将其'['分开，得到appname
        var newtype = $('#type').val();
        console.log(newAppname+newtype);
        if (newtype == 'vm') {
            switch (newProviderEn) {
                case "yunhai":
                    window.open('vm/newvm/newyunhaivm.jsp?appname=' + newAppname);
                    break;
                case "aliyun":
                    window.open('vm/newvm/aliyun/newaliyunvm.jsp?appname=' + newAppname);
            }
        } else if (newtype == 'hd') {
            switch (newProviderEn) {
                case "yunhai":
                    window.open('hd/newhd.jsp?menu=newhd&appname=' + newAppname);
                    break;
                case "aliyun":
                    window.open('hd/aliyun/aliyun_buy_hd.jsp?appname=' + newAppname);
            }
        }
    }
</script>
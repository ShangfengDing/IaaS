<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/4/9
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>云硬盘管理 - 云海IaaS</title>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=cloud" />
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div class="inner">
                <div>
                    <ol class="breadcrumb">
                        <li>资源管理</li>
                        <li class="active">云硬盘管理</li>
                    </ol>
                </div>
                <div class="panel panel-default front-panel">
                    <div class="panel-body" style="margin-top:3px;padding-bottom:0px">
                        <div id="yunhai-content" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">名称</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control front-no-box-shadow" name="name" id="name" />
                                </div>
                                <label class="col-md-1 control-label front-label">ID</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control front-no-box-shadow" name="uuid" id="uuid" onblur="checkHDID()"/>
                                </div>
                                <label class="col-md-1 control-label front-label">状态</label>
                                <div class="col-md-3">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="status">
                                        <option value="">--全部--</option>
                                        <option value="available">正常</option>
                                        <option value="creating">创建中</option>
                                        <option value="defined">格式化</option>
                                        <option value="deleted">已删除</option>
                                        <option value="deleting">删除中</option>
                                        <option value="error">故障</option>
                                        <option value="error_deleting">删除错误</option>
                                    </select>
                                </div>
                            </div>
                            <span id="error1" class="redletter leftmargin_10"></span>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">挂载状态</label>
                                <div class="col-md-3">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="attachstatus">
                                        <option value="">--全部--</option>
                                        <option value="attached">已挂载</option>
                                        <option value="detached">未挂载</option>
                                    </select>
                                </div>
                                <label class="col-md-1 control-label front-label">用户邮箱</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control front-no-box-shadow" name="email" id="email" onblur="checkEmail()"/>
                                </div>
                                <label class="col-md-1 control-label front-label">数据中心</label>
                                <div class="col-md-3">
                                    <select class="form-control front-no-radius front-no-box-shadow" id="zone" >
                                        <option value="">--全部--</option>
                                        <s:iterator id="zone" value="zones">
                                            <s:if test="#zone.name=='lab'">
                                            </s:if>
                                            <s:else>
                                                <option value="<s:property value="#zone.id"/>">
                                                    <s:property value="#zone.name"/>
                                                </option>
                                            </s:else>
                                        </s:iterator>
                                    </select>
                                </div>
                            </div>
                            <span id="error2" class="redletter leftmargin_10"></span>
                            <div class="form-group">
                                <label class="col-md-1 control-label front-label">UUID</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control front-no-box-shadow" name="vmuuid" id="vmuuid" onblur="checkVMID()"/>
                                </div>
                                <span id="error3" class="redletter leftmargin_10"></span>
                                <div class="col-lg-offset-1 col-lg-11 text-right" style="padding-top:20px">
                                    <a href="javascript:void(0)" class="btn btn-default" onclick="cancelSearch()">取消</a>
                                    <button type="button" class="btn btn-primary" onclick="submitAll(1)">确定</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                <div id="query">

                </div>
                <div id="loading" class="hidden">
                    <div class="front-loading">
                        <div class="front-loading-block"></div>
                        <div class="front-loading-block"></div>
                        <div class="front-loading-block"></div>
                    </div>
                    <div class="panel-body text-center">正在加载请稍候</div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>

<script>
    $(document).ready(function(){
        //当从用户管理界面根据硬盘数跳至本页面时使用
        var query = window.location.href;
        query = query.substring(query.indexOf("?")+1);
        var pairs = query.split("&");
        for(var i=0; i<pairs.length; i++) {
            var paramName = pairs[i].substring(0,pairs[i].indexOf("="));
            var paramValue = pairs[i].substring(pairs[i].indexOf("=")+1);
            if(paramName == "email") {
                $("#email").val(paramValue);
                searchHd(1, paramValue, "", "", "", "", "", "");
            }
            if(paramName == "zone"){
                $("#zone").val(paramValue);
                searchHd(1, "", "", "", 1, "", "", "");
            }
        }

    });

    function cancelSearch(){
        $("#query").html("");
        $("#name").val("");
        $("#uuid").val("");
        $("#status").val("");
        $("#attachstatus").val("");
        $("#email").val("");
        $("#vmuuid").val("");
        $("#zone").val("");
        $("#error1").html("");
        $("#error2").html("");
        $("#error3").html("");
    }
    function submitAll(p){
        var name =  $("#name").val();//获取输入数据
        var uuid = $("#uuid").val();
        var status = $("#status").val();
        var attachstatus = $("#attachstatus").val();
        var email = $("#email").val();
        var vmuuid = $("#vmuuid").val();
        var zone = $("#zone").val();

        if(name == "" && uuid == "" && status == "" && attachstatus == ""
            && email =="" && vmuuid == "" && zone == ""){
            if(p==0 || p=='0'){
                if(!confirm("导出全部硬盘？")){
                    return;
                }
            }else{
                if(!confirm("查询全部硬盘？")){
                    return;
                }
            }
        }else{
            if(uuid != "" && !isString(uuid)){
                $("#error1").html("硬盘标识输入的格式不正确");
            }else{
                $("#error1").html("");
            }

            if(email != "" && !isEmail(email)){
                $("#error2").html("用户邮箱输入的格式不正确");
                return;
            }else{
                $("#error2").html("");
            }

            if(vmuuid != "" && !isString(vmuuid)){
                $("#error3").html("虚拟机标识输入的格式不正确");
            }else{
                $("#error3").html("");
            }
        }

        if(p==1||p=='1'){
            //查询云主机
            $("#query").html("");
            var loading = $('#loading').clone();
            loading.removeClass('hidden');
            $('#query').append(loading);
            $('#query').css("display", "block");
        }

        if(p==0 || p=='0'){//以提交表单的形式导出云硬盘excel表
            var temp = document.createElement("form");
            temp.action = "hd/hdexcel";
            temp.method = "post";
            temp.style.display = "none";
            var PARAMS={page: '1', email: email, name: name, status: status, zone: zone, attachstatus: attachstatus, uuid: uuid, vmuuid:vmuuid };
            for (var par in PARAMS) {
                var opt = document.createElement("textarea");
                opt.name = par;
                opt.value = PARAMS[par];
                temp.appendChild(opt);
            }
            document.body.appendChild(temp);
            temp.submit();
            return temp;
        }else{//查询云硬盘
            $("#query").html("");
            var loading = $('#loading').clone();
            loading.removeClass('hidden');
            $('#query').append(loading);
            searchHd(p, email, name, status, zone, attachstatus, uuid, vmuuid);
        }
    }

    var e;
    var n;
    var s;
    var z;
    var c;
    var uid;
    var vmuu;
    function searchHd(page, email, name, status, zone, attachstatus, uuid, vmuuid){
        e = email;
        n = name;
        s = status;
        z = zone;
        c = attachstatus;
        uid = uuid;
        vmuu = vmuuid;
        getPage(page);
        function getPage(page){
            $.post('hd/searchhd', {
                page: page,
                name: n,
                email: e,
                status: s,
                zone: z,
                attachstatus: c,
                uuid: uid,
                vmuuid: vmuu,
            }, function (data) {
                    $('#query').html(data);
                    console.log(data);
                   $('#pageColumn').html($.getDivPageHtml(page, $('#pageColumn').data('endpage'), '(' + getPage +
                        ')'));
            });
        }
    }


    //硬盘卸载和删除操作
    function hdoperate(operation, hdUuid, serverId, endTimeId, uid){
        var confirmMsg;
        if(operation == "detach"){
            confirmMsg = "确认进行卸载吗？(注意：该操作将关闭虚拟机！！)";
        } else {
            confirmMsg = "确认删除该硬盘吗？";
        }
        if(confirm(confirmMsg)){
            $.post("hd/hdoperate",{operation:operation,hdUuid:hdUuid,serverId:serverId,
                endTimeId:endTimeId, uid:uid},function(data){
               // hideLoading();
                if(data.result == true) {
                    //fillTipBox("success","操作成功！");
                    alert("success,成功！");
                    if(operation == "detach"){
                        $("#tr"+hdUuid).children().eq(2).html("未挂载");
                        $("#tr"+hdUuid).children().eq(6).html("<a"+
                            " href=\"hd/showattachhd?hduuid="+hdUuid+"&uid="+uid+
                            "\" rel=\"facebox\" title=\"挂载硬盘到虚拟机\" size=\"s\">挂载</a>"+
                            "<a href=\"javascript:void(0)\""+
                            " onclick=\"hdoperate('delete','"+hdUuid+"','','"+
                            endTimeId+"','"+uid+"')\">删除</a>");
                        $("a[rel=facebox]").facebox();
                        getStyle();
                    }else{
                        $("#tr"+hdUuid).remove();
                    }
                }else{
                    alert("error,操作失败");
                    fillTipBox("error","操作失败！");
                }
            });
        }
    }


    //判断硬盘标识格式正确与否
    function isString(str){  //判断是否是硬盘标识格式
        var reg = /^[A-Za-z0-9]+$/;
        if(str.match(reg)){
            return true;
        } else {
            return false;
        }
    }
    function checkHDID(){  //判断硬盘标识输入格式是否正确
        var uuid = $("#uuid").val();
        if(uuid != "" && !isString(uuid)){
            $("#error1").html("硬盘标识输入的格式不正确");
        }else{
            $("#error1").html("");
        }
    }

    function checkVMID(){ //判断虚拟机标识驶入格式是否正确
        var vmuuid = $("#vmuuid").val();
        if(vmuuid != "" && !isString(vmuuid)){
            $("#error3").html("虚拟机标识输入的格式不正确");
        }else{
            $("#error3").html("");
        }
    }

    //判断是否是email
    function isEmail(str){
        var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        return reg.test(str);
    }
    function checkEmail(){
        var email = $("#email").val();
        if(email != "" && !isEmail(email)){
            $("#error2").html("用户邮箱输入的格式不正确");
        }else{
            $("#error2").html("");
        }
    }

</script>
</body>
</html>

<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>新建IP池</title>

<body class="front-body">
<s:include value="/template/_banner.jsp?menu=system"/>
<div class="front-inner ">
    <div class="container">
        <div class="col-md-12 form-group">
        <div class="panel panel-default front-panel">
            <div class="panel-heading">
                <strong>新建IP池</strong>
            </div>
            <div class="panel-body"style="padding-bottom:0px">

                <form action="net/newnet" method="post" onsubmit="return checkAll();" class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-md-1 control-label front-label">选择集群</label>
                        <div class="col-md-5">
                            <select id="aggregateId" name="aggregateId"
                                    class="form-control front-no-radius front-no-box-shadow">
                                <s:iterator id="aggregate" value="aggregates">
                                    <option value="<s:property value="#aggregate.id"/>"><s:property
                                            value="#aggregate.name"/></option>
                                </s:iterator>
                            </select>
                        </div>
                        <label class="col-md-1 control-label front-label">IP池类型</label>
                        <div class="col-md-5">
                            <select id="addressType" name="addressType"
                                    class="form-control front-no-radius front-no-box-shadow">
                            <option value="private">内网</option>
                            <option value="public">外网</option>
                            </select>
                        <!--    <span class="redletter leftmargin_5" id="error1"></span>   -->
                        </div>
                    </div>
                    <div class="form-group">

                        <label class="col-md-1 control-label front-label">IP段</label>

                        <div class="col-md-2">
                            <input type="text" class="form-control front-no-box-shadow"  id="ipStart" name="ipStart"/>
                        </div>
                        <lable class="col-md-1 control-label front-label" style="text-align:center" >---</lable>
                        <div class="col-md-2">
                            <input type="text" class="form-control front-no-box-shadow" id="ipEnd" name="ipEnd"/>
                        </div>
                        <span class="redletter leftmargin_5" id="error3"></span>
                        <label class="col-md-1 control-label front-label">子网掩码</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control front-no-box-shadow" id="netmask" name="netmask"/>
                        </div>
                        <span class="redletter leftmargin_5" id="error4"></span></td>
                    </div>
                    <div class="form-group">
                        <label class="col-md-1 control-label front-label">网关</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control front-no-box-shadow" id="gateway" name="gateway"/>
                        </div>
                        <span class="redletter leftmargin_5" id="error5"></span>
                        <label class="col-md-1 control-label front-label">DNS</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control front-no-box-shadow" id="dns" name="dns"/>
                        </div>

                        <span class="redletter leftmargin_5" id="error6"></span>
                    </div>
                    <div class="form-group">
                        <div class="pull-right" style="padding-top: 2%;margin-right:15px">
                            <a href="net/netlist?zid=1" class="btn btn-default ">取消</a>
                            <input type="submit" value="确定" class="btn btn-primary"  />

                        </div>
                    </div>


                    <input type="hidden" value="<s:property value="zoneId"/>" name="zoneId" id="zoneId"/>
                </form>
            </div>
        </div>
        </div>
    </div><!--container-->
</div>
<s:include value="/template/_footer.jsp"></s:include>
<script>
    $(function () {
        getStyle();
    });

    function checkAll() {
        var aggregateId = $("#aggregateId").val();
        var addressType = $("#addressType").val();
        var ipStart = $("#ipStart").val();
        var ipEnd = $("#ipEnd").val();
        var netmask = $("#netmask").val();
        var gateway = $("#gateway").val();
        var dns = $("#dns").val();
        var zoneId = $("#zoneId").val();
        var ipStartPrefix;
        var ipStartNum;
        var ipEndPrefix;
        var ipEndNum;

        if (aggregateId == "") {
         //   $("#error1").html("请选择集群");
         //   $.fillTipBox('alert','danger','请选择集群');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'请选择集群'});
            return false;
        } else {
            $("#error1").html("");
        }

        if (addressType == "") {
         //   $("#error2").html("请选择IP池类型");
         //   $.fillTipBox('alert','danger','请选择IP池类');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'请选择IP池类'});
            return false;
        } else {
            $("#error2").html("");
        }

        if (ipStart == "") {
        //    $("#error3").html("IP段不得为空");
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP段不得为空'});
            return false;
        } else if (!isIP(ipStart)) {
        //    $("#error3").html("IP段格式不正确");
        //    $.tipModal('alert','danger','IP段格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP段格式不正确'});
            return false;
        } else {
            ipStartPrefix = ipStart.slice(0, ipStart.lastIndexOf('.'));
            ipStartNum = ipStart.slice(ipStart.lastIndexOf('.') + 1);
            $("#error3").html("");
        }

        if (ipEnd == "") {
        //    $("#error3").html("IP段不得为空");
        //    $.tipModal('alert','danger','IP段不得为空');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP段不得为空'});
            return false;
        } else if (!isIP(ipEnd)) {
        //    $("#error3").html("IP段格式不正确");
        //    $.tipModal('alert','danger','IP段格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP段格式不正确'});
            return false;
        } else {
            ipEndPrefix = ipEnd.slice(0, ipEnd.lastIndexOf('.'));
            ipEndNum = ipEnd.slice(ipEnd.lastIndexOf('.') + 1);

            if (parseInt(ipStartNum) > parseInt(ipEndNum)) {
        //        $("#error3").html("起始段不得大于结束段");
        //        $.tipModal('alert','danger','起始段不得大于结束段');
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'起始段不得大于结束段'});
                return false;
            } else if (!(ipStartPrefix == ipEndPrefix)) {
        //        $("#error3").html("IP段区间前三段不一致");
        //        $.tipModal('alert','danger','IP段区间前三段不一致');
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP段区间前三段不一致'});
                return false;
            } else {
                $("#error3").html("");
            }
        }

        if (netmask == "") {
        //    $("#error4").html("子网掩码不得为空");
        //    $.tipModal('alert','danger','子网掩码不得为空');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'子网掩码不得为空'});
            return false;
        } else if (!isIP(netmask)) {
        //    $("#error4").html("子网掩码格式不正确");
        //    $.tipModal('alert','danger','子网掩码格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'子网掩码格式不正确'});
            return false;
        } else {
            $("#error4").html("");
        }


        /* if(gateway == ""){
            $("#error5").html("网关不得为空");
            return false;
        }else  */
        if (gateway != "" && !isIP(gateway)) {
        //    $("#error5").html("网关格式不正确");
        //    $.tipModal('alert','danger','网关格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'网关格式不正确'});
            return false;
        } else {
            $("#error5").html("");
        }

        /* if(dns == ""){
            $("#error6").html("DNS不得为空");
            return false;
        }else  */
        if (dns != "" && !isIP(dns)) {
        //    $("#error6").html("DNS格式不正确");
        //    $.tipModal('alert','danger','DNS格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'DNS格式不正确'});
            return false;
        } else {
            $("#error6").html("");
        }


        showLoading();
        return true;
    }

    function checkIPStart() {
        var ipStart = $("#ipStart").val();

        if (ipStart == "") {
        //    $("#error3").html("IP开始段不得为空");
        //    $.tipModal('alert','danger','IP开始段不得为空');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP开始段不得为空'});
            return;
        } else if (!isIP(ipStart)) {
        //    $("#error3").html("IP开始段格式不正确");
        //    $.tipModal('alert','danger','IP开始段格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP开始段格式不正确'});
            return;
        } else {
            $("#error3").html("");
        }
    }

    function checkIPEnd() {
        var ipEnd = $("#ipEnd").val();

        if (ipEnd == "") {
        //    $("#error3").html("IP结束段不得为空");
        //    $.tipModal('alert','danger','IP结束段不得为空');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP结束段不得为空'});
            return;
        } else if (!isIP(ipEnd)) {
        //    $("#error3").html("IP结束段格式不正确");
        //    $.tipModal('alert','danger','IP结束段格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP结束段格式不正确'});
            return;
        } else {
            $("#error3").html("");
        }
    }


    function checkIP() {

        var ipEnd = $("#ipEnd").val();
        var ipStartPrefix;
        var ipEndPrefix;
        //var ipStartPrefix = ipStart.slice(0, ipStart.lastIndexOf('.'));

        if (ipStart == "") {
        //    $("#error3").html("IP开始段不得为空");
        //    $.tipModal('alert','danger','IP开始段不得为空');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP开始段不得为空'});
        } else if (!isIP(ipStart)) {
        //    $("#error3").html("IP开始段格式不正确");
        //    $.tipModal('alert','danger','IP开始段格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP开始段格式不正确'});
        } else {
            ipStart
            $("#error3").html("");
        }

        if (ipEnd == "") {
        //    $("#error3").html("IP结束段不得为空");
        //    $.tipModal('alert','danger','IP结束段不得为空');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP结束段不得为空'});
        } else if (!isIP(ipEnd)) {
        //    $("#error3").html("IP段格式不正确");
        //    $.tipModal('alert','danger','IP段格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'IP段格式不正确'});
        } else {
            $("#error3").html("");
        }
    }

    function checkNetmask() {
        var netmask = $("#netmask").val();
        if (netmask == "") {
        //    $("#error4").html("子网掩码不得为空");
        //    $.tipModal('alert','danger','子网掩码不得为空');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'子网掩码不得为空'});
        } else if (!isIP(netmask)) {
        //    $("#error4").html("子网掩码格式不正确");
        //    $.tipModal('alert','danger','子网掩码格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'子网掩码格式不正确'});
        } else {
            $("#error4").html("");
        }
    }

    function checkGateway() {
        var gateway = $("#gateway").val();
        /* if(gateway == ""){
            $("#error5").html("网关不得为空");
        }else  */
        if (gateway != "" && !isIP(gateway)) {
        //    $("#error5").html("网关格式不正确");
        //    $.tipModal('alert','danger','网关格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'网关格式不正确'});
        } else {
            $("#error5").html("");
        }
    }

    function checkDNS() {
        var dns = $("#dns").val();
        /*  if(dns == ""){
             $("#error6").html("DNS不得为空");
         }else  */
        if (dns != "" && !isIP(dns)) {
        //    $("#error6").html("DNS格式不正确");
        //    $.tipModal('alert','danger','DNS格式不正确');
            $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'DNS格式不正确'});
        } else {
            $("#error6").html("");
        }
    }

    function isIP(ipAddr) {
        var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
        if (ipAddr.match(reg)) {
            return true;
        } else {
            return false;
        }
    }
</script>
</body>
</html>
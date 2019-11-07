<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="modal-header">
    <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">网络资源</h4>
</div>
<div class="modal-body">
    <form action="system/newnet" method="post" onsubmit="return checkAll();" class="form-horizontal" role="form">
        <input name="aggregateId" type="text" value="${param.id}" class="hidden"/>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">CPU(%)</label>
            <div class="col-md-9">
                <input type="text" class="form-control front-no-box-shadow" id="cpu" name="cpu"/>
                <span class="redletter leftmargin_5" id="error1"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">IP池类型</label>
            <div class="col-md-9">
                <select id="addressType" name="addressType" class="form-control front-no-radius front-no-box-shadow">
                    <option value="private">内网</option>
                    <option value="public">外网</option>
                </select>
            </div>
            <span class="redletter leftmargin_5" id="error2"></span>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label front-label">IP段</label>
            <div class="col-md-4">
                <input type="text" class="form-control front-no-box-shadow" id="ipStart" name="ipStart"/>
            </div>

            <label class="col-md-1 control-label front-label" style="text-align:center" >-</label>

            <div class="col-md-4">
                <input type="text" class="form-control front-no-box-shadow" id="ipEnd" name="ipStart"/>
            </div>
            <span class="redletter leftmargin_5" id="error3"></span>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">子网掩码</label>
            <div class="col-md-9">
                <input type="text" class="form-control front-no-box-shadow" id="netmask" name="netmask"/>
                <span class="redletter leftmargin_5" id="error4"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">网关</label>
            <div class="col-md-9">
                <input type="text" class="form-control front-no-box-shadow" id="gateway" name="gateway"/>
                <span class="redletter leftmargin_5" id="error5"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">DNS</label>
            <div class="col-md-9">
                <input type="text" class="form-control front-no-box-shadow" id="dns" name="dns"/>
                <span class="redletter leftmargin_5" id="error6"></span>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-1 col-lg-11 text-right">
                <a class="btn btn-default" class="close modal-close" data-dismiss="modal" aria-hidden="true">取消</a>
                <input class="btn  btn-primary" type="submit" value="确定"/>
            </div>
        </div>
        <input type="hidden" value="<s:property value="zoneId"/>" name="zoneId" id="zoneId"/>
    </form>
</div>
<script>
    //不备注模态框就显示不出来，暂时不知道这个getStyle什么用，没有定义
    // $(function () {
    //     // getStyle();
    // });

    function checkAll() {
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

        if (addressType == "") {
            $("#error2").html("请选择IP池类型");
            return false;
        } else {
            $("#error2").html("");
        }

        if (ipStart == "") {
            $("#error3").html("IP段不得为空");
            return false;
        } else if (!isIP(ipStart)) {
            $("#error3").html("IP段格式不正确");
            return false;
        } else {
            ipStartPrefix = ipStart.slice(0, ipStart.lastIndexOf('.'));
            ipStartNum = ipStart.slice(ipStart.lastIndexOf('.') + 1);
            $("#error3").html("");
        }

        if (ipEnd == "") {
            $("#error3").html("IP段不得为空");
            return false;
        } else if (!isIP(ipEnd)) {
            $("#error3").html("IP段格式不正确");
            return false;
        } else {
            ipEndPrefix = ipEnd.slice(0, ipEnd.lastIndexOf('.'));
            ipEndNum = ipEnd.slice(ipEnd.lastIndexOf('.') + 1);

            if (parseInt(ipStartNum) > parseInt(ipEndNum)) {
                $("#error3").html("起始段不得大于结束段");
                return false;
            } else if (!(ipStartPrefix == ipEndPrefix)) {
                $("#error3").html("IP段区间前三段不一致");
                return false;
            } else {
                $("#error3").html("");
            }
        }

        if (netmask == "") {
            $("#error4").html("子网掩码不得为空");
            return false;
        } else if (!isIP(netmask)) {
            $("#error4").html("子网掩码格式不正确");
            return false;
        } else {
            $("#error4").html("");
        }


        /* if(gateway == ""){
            $("#error5").html("网关不得为空");
            return false;
        }else  */
        if (gateway != "" && !isIP(gateway)) {
            $("#error5").html("网关格式不正确");
            return false;
        } else {
            $("#error5").html("");
        }

        /* if(dns == ""){
            $("#error6").html("DNS不得为空");
            return false;
        }else  */
        if (dns != "" && !isIP(dns)) {
            $("#error6").html("DNS格式不正确");
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
            $("#error3").html("IP开始段不得为空");
            return;
        } else if (!isIP(ipStart)) {
            $("#error3").html("IP开始段格式不正确");
            return;
        } else {
            $("#error3").html("");
        }
    }

    function checkIPEnd() {
        var ipEnd = $("#ipEnd").val();

        if (ipEnd == "") {
            $("#error3").html("IP结束段不得为空");
            return;
        } else if (!isIP(ipEnd)) {
            $("#error3").html("IP结束段格式不正确");
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
            $("#error3").html("IP开始段不得为空");
        } else if (!isIP(ipStart)) {
            $("#error3").html("IP开始段格式不正确");
        } else {
            ipStart
            $("#error3").html("");
        }

        if (ipEnd == "") {
            $("#error3").html("IP结束段不得为空");
        } else if (!isIP(ipEnd)) {
            $("#error3").html("IP段格式不正确");
        } else {
            $("#error3").html("");
        }
    }

    function checkNetmask() {
        var netmask = $("#netmask").val();
        if (netmask == "") {
            $("#error4").html("子网掩码不得为空");
        } else if (!isIP(netmask)) {
            $("#error4").html("子网掩码格式不正确");
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
            $("#error5").html("网关格式不正确");
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
            $("#error6").html("DNS格式不正确");
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


<%--<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--<%@ taglib prefix="s" uri="/struts-tags" %>--%>
<%--<div class="modal-header">--%>
<%--<button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">×</button>--%>
<%--<h4 class="modal-title">超售比例</h4>--%>
<%--</div>--%>
<%--<div class="modal-body">--%>
<%--<form action="system/setoversell" method="post" onsubmit="return checkAll();" class="form-horizontal" role="form">--%>
<%--<input name="clusterId" type="text" value="${param.id}" class="hidden"/>--%>
<%--<div class="form-group">--%>
<%--<label class="col-md-3 control-label front-label">CPU(%)</label>--%>
<%--<div class="col-md-9">--%>
<%--<input type="text" class="form-control front-no-box-shadow" id="cpu" name="cpu"/>--%>
<%--<span class="redletter leftmargin_5" id="error1"></span>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--<label class="col-md-3 control-label front-label">内存(%)</label>--%>
<%--<div class="col-md-9">--%>
<%--<input type="text" class="form-control front-no-box-shadow" id="memory" name="memory"/>--%>
<%--<span class="redletter leftmargin_5" id="error2"></span>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--<label class="col-md-3 control-label front-label">硬盘(%)</label>--%>
<%--<div class="col-md-9">--%>
<%--<input type="text" class="form-control front-no-box-shadow" id="disk" name="disk"/>--%>
<%--<span class="redletter leftmargin_5" id="error3"></span>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="form-group">--%>
<%--<div class="col-lg-offset-1 col-lg-11 text-right" >--%>
<%--<a class="btn btn-default" class="close modal-close" data-dismiss="modal" aria-hidden="true" >取消</a>--%>
<%--<input id="success" type="submit" class="btn btn-primary" value="确定"/>--%>
<%--</div>--%>
<%--</div>--%>
<%--</form>--%>
<%--</div>--%>
<%--<script>--%>
<%--function checkAll() {--%>
<%--var reg = /^[0-9]*[1-9][0-9]*$/;--%>
<%--if (!reg.test($("#cpu").val()) || parseInt($("#cpu").val()) < 100) {--%>
<%--$("#error1").html("格式有误");--%>
<%--return false;--%>
<%--}--%>
<%--if (parseInt($("#cpu").val()) < 100) {--%>
<%--$("#error1").html("必须大于100");--%>
<%--return false;--%>
<%--}--%>
<%--if (!reg.test($("#memory").val()) || parseInt($("#memory").val()) < 100) {--%>
<%--$("#error2").html("格式有误");--%>
<%--return false;--%>
<%--}--%>
<%--if (parseInt($("#memory").val()) < 100) {--%>
<%--$("#error2").html("必须大于100");--%>
<%--return false;--%>
<%--}--%>
<%--if (!reg.test($("#disk").val()) || parseInt($("#disk").val()) < 100) {--%>
<%--$("#error3").html("格式有误");--%>
<%--return false;--%>
<%--}--%>
<%--if (parseInt($("#disk").val()) < 100) {--%>
<%--$("#error3").html("必须大于100");--%>
<%--return false;--%>
<%--}--%>
<%--return true;--%>
<%--}--%>
<%--</script>--%>







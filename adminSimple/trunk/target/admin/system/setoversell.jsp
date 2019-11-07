<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="modal-header">
    <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">超售比例</h4>
</div>
<div class="modal-body">
    <form action="system/setoversell" method="post" onsubmit="return checkAll();" class="form-horizontal" role="form">
        <input name="clusterId" type="text" value="${param.id}" class="hidden"/>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">CPU(%)</label>
            <div class="col-md-9">
                <input type="text" class="form-control front-no-box-shadow" id="cpu" name="cpu"/>
                <span class="redletter leftmargin_5" id="error1"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">内存(%)</label>
            <div class="col-md-9">
                <input type="text" class="form-control front-no-box-shadow" id="memory" name="memory"/>
                <span class="redletter leftmargin_5" id="error2"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">硬盘(%)</label>
            <div class="col-md-9">
                <input type="text" class="form-control front-no-box-shadow" id="disk" name="disk"/>
                <span class="redletter leftmargin_5" id="error3"></span>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-1 col-lg-11 text-right" >
                <a class="btn btn-default" class="close modal-close" data-dismiss="modal" aria-hidden="true" >取消</a>
                <input id="success" type="submit" class="btn btn-primary" value="确定"/>
            </div>
        </div>
    </form>
</div>
<script>
    function checkAll() {
        var reg = /^[0-9]*[1-9][0-9]*$/;
        if (!reg.test($("#cpu").val()) || parseInt($("#cpu").val()) < 100) {
            $("#error1").html("格式有误");
            return false;
        }
        if (parseInt($("#cpu").val()) < 100) {
            $("#error1").html("必须大于100");
            return false;
        }
        if (!reg.test($("#memory").val()) || parseInt($("#memory").val()) < 100) {
            $("#error2").html("格式有误");
            return false;
        }
        if (parseInt($("#memory").val()) < 100) {
            $("#error2").html("必须大于100");
            return false;
        }
        if (!reg.test($("#disk").val()) || parseInt($("#disk").val()) < 100) {
            $("#error3").html("格式有误");
            return false;
        }
        if (parseInt($("#disk").val()) < 100) {
            $("#error3").html("必须大于100");
            return false;
        }
        return true;
    }
</script>

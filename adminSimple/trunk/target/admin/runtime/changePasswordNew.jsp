<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<form action="runtime/changePasswordNew?id=<s:property value="#session.adminId"/>" method="post" style="margin-bottom:
0px;">
    <div class="modal-body">
        <div class="form-group"
             style="vertical-align: middle;padding-right: 0px;padding-top: 15px;padding-bottom: 15px;">
            <label class="col-md-2 control-label front-label"
                   style="text-align: right;padding-right: 0px;padding-top: 8px;">名称</label>
            <div class="col-md-10">
                <span class="form-control front-no-box-shadow" id="userName" name="userName"
                       style="padding-left: 0px"><s:property value="#session.username"/></span>
            </div>
        </div>

        <div class="form-group"
             style="vertical-align: middle;padding-right: 0px;padding-top: 15px;padding-bottom: 30px;">
            <label class="col-md-2 control-label front-label"
                   style="text-align: right;padding-right: 0px;padding-top: 8px;">请输入密码</label>
            <div class="col-md-10">
                <input type="text" class="form-control front-no-box-shadow" id="password" name="password"
                       style="padding-left: 0px"/>
            </div>
        </div>
        <%--<input type="hidden" name="type" value="${param.type}"/>--%>
        <%--<input type="hidden" name="iid" id="iid" value="${param.iid}"/>--%>
    </div>

    <div class="modal-footer">
        <button class="btn btn-primary" id="submitMod">确定</button>
        <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
    </div>
</form>

</body>

<script>

    $("#submitMod").click(function () {
        var id ="<s:property value="#session.adminId"/>";
        var password = $("#password").val();
        var name = $("#userName").val();
        $.ajax({
            type:"post",
            url:"runtime/changePasswordNew",
            data:{
                password:password,
                id:id,
            },
            success:function(data){
//                $.fillTipBox("success","glyphicon-ok-sign","修改成功");
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改成功'});
//               fillTipBox("success", "修改成功");
//                alert("success");

            },
            error:function (data) {
//                $.fillTipBox("error","修改失败");
                $.fillTipBox({type:'danger', icon:'glyphicon-alert', content:'修改失败'});
            }
        })
    })
    <%--function submitForm() {--%>
    <%--var name = $("#userName").val();--%>
    <%--var email = $("#email").val();--%>
    <%--var mobile = $("#mobile").val();--%>
    <%--$.ajax({--%>
    <%--type:"post",--%>
    <%--url:"runtime/changeProfiles?id=<s:property value='#session.adminId'/>",--%>
    <%--data:{--%>
    <%--email:email,--%>
    <%--mobile:mobile,--%>
    <%--userName:name--%>
    <%--},--%>
    <%--success:function() {--%>
    <%--fillTipBox("success", "修改成功");--%>
    <%--},--%>
    <%--error:function(){--%>
    <%--fillTipBox("error","修改失败");--%>
    <%--}--%>

    <%--});--%>

    <%--}--%>

</script>
</html>

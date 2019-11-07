<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<form action="runtime/changeProfiles?id=<s:property value="#session.adminId"/>" method="post" style="margin-bottom: 0px;">
    <div class="modal-body">
        <div class="form-group"
             style="vertical-align: middle;padding-right: 0px;padding-top: 15px;padding-bottom: 15px;">
            <label class="col-md-2 control-label front-label"
                   style="text-align: right;padding-right: 0px;padding-top: 8px;">管理员名称</label>
            <div class="col-md-10">
                <input type="text" class="form-control front-no-box-shadow" id="userName" name="userName"
                       style="padding-left: 0px" value="<s:property value="#session.name"/>"/>
                <label class="col-md-12 front-label control-label errors" id="error_name"
                       style="color:red; text-align:right"></label>
            </div>
        </div>

        <div class="form-group"
             style="vertical-align: middle;padding-right: 0px;padding-top: 15px;padding-bottom: 15px;">
            <label class="col-md-2 control-label front-label"
                   style="text-align: right;padding-right: 0px;padding-top: 8px;">邮箱</label>
            <div class="col-md-10">
                <input type="text" class="form-control front-no-box-shadow" id="email" name="email"
                       style="padding-left: 0px" value="<s:property value="#session.email"/>"/>
            </div>
            <label class="col-md-12 front-label control-label errors" id="error_email"
                   style="color:red; text-align:right"></label>
        </div>

        <div class="form-group"
             style="vertical-align: middle;padding-right: 0px;padding-top: 15px;padding-bottom: 30px;">
            <label class="col-md-2 control-label front-label"
                   style="text-align: right;padding-right: 0px;padding-top: 8px;">联系方式</label>
            <div class="col-md-10">
                <input type="text" class="form-control front-no-box-shadow" id="mobile" name="mobile"
                       style="padding-left: 0px" value="<s:property value="#session.mobile"/>"/>
            </div>
            <label class="col-md-12 front-label control-label errors" id="error_phone"
                   style="color:red; text-align:right"></label>
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
    function isPhone(phone) {
        var regTel = /^(0[0-9]{2,3}\-)?([0-9]{7,8})+(\-[0-9]{1,4})?$/;;
        var regMobile = /^(13[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$/;
        if(phone.match(regTel) || phone.match(regMobile))
            return true;
        else
            return false;
    }


    function isEmail(str){
        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        return reg.test(str);
    }

    $("#submitMod").click(function () {
        var id ="<s:property value="#session.adminId"/>";
        var URL = $("#submitMod").attr("action");
        var email = $("#email").val();
        var mobile = $("#mobile").val();
        var name = $("#userName").val();
        if (name.equals("")) {
            $("#error_name").html("不得为空");
            return false;
        }
        if (!isEmail(email)&&(email)) {
            $("#error_email").html("邮件格式不正确");
            return false;}

        if (!isPhone(mobile)&&(mobile)) {
            $("#error_phone").html("电话格式不正确");
            return false;}

        $.ajax({
            type:"post",
            url:URL,
            data:{
                email:email,
                userName:name,
                mobile:mobile,
                adminId:id,
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
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<form action="img/modimg" method="post" onsubmit="return submitCheck();">
    <div class="modal-body">
        <div class="form-group"
             style="vertical-align: middle;padding-right: 0px;padding-top: 15px;margin-bottom: 0px;">
            <label class="col-md-1 control-label front-label"
                   style="text-align: right;padding-right: 0px;padding-top: 8px;">名称</label>
            <div class="col-md-11">
            <input type="text" class="form-control front-no-box-shadow" id="displayName" name="displayName"
                   placeholder="请输入1-20个字" style="padding-left: 0px"/>
            </div>
                <span class="redletter leftmargin_5" style="margin-left: 60px;" id="error1"></span></td>
        </div>
        <%--<div class="form-group">--%>
            <%--<label class="col-md-2 control-label front-label">名称</label>--%>
            <%--<div class="col-md-4">--%>
                <%--<input type="text" class="form-control front-no-box-shadow" name="name" id="name" />--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="form-group" style="vertical-align: middle;padding-right:0px;margin-bottom: 0px;">
            <label class="col-md-1 control-label front-label"
                   style="text-align: right;padding-right: 0px;padding-top: 8px">描述</label>
            <div class="col-md-11">
                <%--<input type="textarea" class="form-control front-no-box-shadow" id="displayDescription"--%>
                       <%--name="displayDescription" placeholder="请输入1-50个字" />--%>
                <textarea rows="3" id="displayDescription"
                          name="displayDescription" placeholder="请输入1-50个字"
                style="width: 100%;"/>
            </div>
            <span class="redletter" style="margin-left: 60px;" id="error2"></span>
        </div>
        <input type="hidden" name="type" value="${param.type}"/>
        <input type="hidden" name="iid" id="iid" value="${param.iid}"/>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" type="submit">确定</button>
        <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
    </div>
   <!--<table>

        <tr>
            <td width="46px">名称：</td>
            <td><input type="text" class="editline" id="displayName" name="displayName" placeholder="请输入1-20个字"/>
                <span class="redletter leftmargin_5" id="error1"></span></td>
        </tr>
        <tr>
            <td>描述：</td>
            <td><textarea class="editbox" id="displayDescription" name="displayDescription" placeholder="请输入1-50个字"></textarea>
                <span class="redletter leftmargin_5" id="error2"></span></td>
        </tr>
        <tr>
            <td><br/><br/>
                <input type="hidden" name="type" value="${param.type}"/>
                <input type="hidden" name="iid" id="iid" value="${param.iid}"/>
            </td>
            <td>
                <button class="button" type="submit">确定</button>
                <button class="graybutton" onclick="facebox_close();return false;">取消</button>
            </td>
        </tr>
    </table>-->
</form>
<script>
    var flag = false;
    var flag1 = false;
    $(function(){
        var iid = $("#iid").val();
        $("#displayName").val($("#name"+iid).html());
        $("#displayDescription").val($("#desc"+iid).html().replace(new RegExp("；","gm"),"\r\n"));
        if($("#isPublic"+iid).html() == "是"){
            $("#isPublic").attr("checked","checked");
        };

        $("#displayName").blur(function(){
            var str=$(this).val();
            if(str == ""){
                $("#error1").html("请输入名称");
                flag = false;
                return;
            }else if(str.length > 20){
                $("#error1").html("最多输入20个字");
                flag = false;
                return;
            }else{
                flag = true;
                $("#error1").html("<br/>");
            }
        });

        $("#displayDescription").blur(function(){
            var str = $(this).val();
            if(str == ""){
                $("#error2").html("请输入描述");
                flag1 = false;
                return;
            }else if(str.length > 50){
                $("#error2").html("最多输入50个字");
                flag1 = false;
                return;
            }else{
                $("#error2").html("<br/>");
                flag1 = true;
            }
        });
    });

    //onsubmit返回的方法
    function submitCheck(){
        if(flag == false){
            var str=$("#displayName").val();
            if(str == ""){
                $("#error1").html("请输入名称");
                return false;
            }else if(str.length > 20){
                $("#error1").html("最多输入20个字");
                return false;
            }else{
                flag = true;
                $("#error1").html("<br/>");
            }
        }
        if(flag1 == false ){
            var str1 = $("#displayDescription").val();
            if(str1 == ""){
                $("#error2").html("请输入描述");
                return false;
            }else if(str1.length > 50){
                $("#error2").html("最多输入50个字");
                return false;
            }else{
                flag1=true;
                $("#error2").html("<br/>");
            }
        }
        return true;
    }
</script>
</body>
</html>
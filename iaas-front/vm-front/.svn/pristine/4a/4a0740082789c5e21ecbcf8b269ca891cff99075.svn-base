<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<form id="form">
<table class="formtable">
	<tr>
		<td width="65px">备份名称</td>
		<td><input type="text" class="editline" id="displayName" name="displayName" placeholder="请输入1-20个字"/>
		<span class="redletter leftmargin_5" id="error1"></span>
		</td>
	</tr><tr>
        <td>备份描述</td>
        <td><textarea cols="30" rows="5" class="editbox" id="displayDescription" name="displayDescription" placeholder="请输入1-50个字"></textarea>
        <span class="redletter leftmargin_5" id="error2"></span>
    </tr>
    <tr>
		<td colspan="2">
		    <input type="hidden" value="${param.serverId}" name="serverId" />
		    <input type="hidden" value="${param.serverName}" name="serverName" />
		    <%-- <div class="topmargin_5">
			说明：每个虚拟机最多创建${session.backupNum}个备份，达到最大值后再创建，将自动删除最早创建的备份。
			</div> --%>
			<div class="topmargin_5 strong lightyellowbox" style="height:25px;padding-top: 5px;"><img class="rightmargin_5 leftmargin_5" src="images/notice.png" align="absmiddle"/>注意：本操作将需要关闭云主机！</div>
		</td>
	</tr>
</table>
</form>
<div class="topmargin_10">
	<button class="button rightmargin_10" style=" margin-left: 70px;" onclick="submitCheck()">确定</button>
	<button class="greybutton" onclick="facebox_close()">取消</button>
</div>

<script>
var flag = false;
var flag1 = false;
$(function(){
	$("#displayName").blur(function(){
		var str=$(this).val();
        if(str == ""){
            $("#error1").html("请输入备份名称");
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
            $("#error2").html("请输入备份描述");
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
})

//onsubmit返回的方法
function submitCheck(){
    if(flag == false){
        var str=$("#displayName").val();
        if(str == ""){
            $("#error1").html("请输入备份名称");
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
            $("#error2").html("请输入备份描述");
            return false;
        }else if(str1.length > 50){
            $("#error2").html("最多输入50个字");
            return false;
        }else{
        	flag1=true;
            $("#error2").html("<br/>");
        }
    }
    showLoading();
    $.post("backup/newbackup",$("#form").serialize(),function(data){
    	if(data.result=="success"){
            fillTipBox("success","备份创建中……此操作预计需要5分钟");
    	}else if(data.result == "fail"){
    		fillTipBox("error","未知错误");
    	}else{
    		fillTipBox("error",data.result);
    	}
    	facebox_close();
    	hideLoading();
    	/* setTimeout(function(){gotonext("backup/backup");},3000); */
    });
}
</script>
</body>
</html>
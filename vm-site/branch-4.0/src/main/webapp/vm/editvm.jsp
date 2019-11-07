<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<input type="hidden" id="serverId_editvm" value="<s:property value="serverId"/>" />
<input type="hidden" id="serverName_editvm" value="<s:property value="serverName"/>"/>
<input type="hidden" id="serverType_editvm" value="<s:property value="type"/>"/>
<table>
    <tr id="newNameTr">
	    <td width="60px">名称</td>
		<td><input type="text" class="editline" id="vmName_editvm" placeholder="请输入1-20个字" value = "<s:property value="serverName"/>"/>
		<span class="redletter leftmargin_5" id="error1_editvm"></span></td>
    </tr><tr id="newDesciptionTr">
		<td>描述</td>
		<td><textarea class="editbox" id="vmDescription_editvm" placeholder="请输入1-100个字" ><s:property value="serverDescription"/></textarea>
		<span class="redletter leftmargin_5" id="error2_editvm"></span></td>
	</tr>
	<tr>
        <td><br/><br/></td>
        <td><br/>
            <button class="button" onclick="submitCheck()">确定</button>
            <button class="graybutton" onclick="facebox_close()">取消</button>        
        </td> 
    </tr>
</table>
<script>
var flag_editvm = false;
var flag1_editvm = false;
$(function(){
	var type = $("#serverType_editvm").val();
	if(type == "name"){
		$("#newDesciptionTr").addClass("hidden");
		$("#vmName_editvm").blur(function(){
	        var str=$(this).val();
	        if(str == ""){
	            $("#error1_editvm").html("请输入名称");
	            flag_editvm = false;
	            return;
	        }else if(str.length > 20){
	            $("#error1_editvm").html("最多输入20个字");
	            flag_editvm = false;
	            return;
	        }else{
	            flag_editvm = true;
	            $("#error1_editvm").html("<br/>");
	        }
	    });
	}else if(type == "description"){
		$("#newNameTr").addClass("hidden");
		$("#vmDescription_editvm").blur(function(){
	        var str = $(this).val();
	        if(str == ""){
	            $("#error2_editvm").html("请输入描述");
	            flag1_editvm = false;
	            return;
	        }else if(str.length > 100){
	            $("#error2_editvm").html("最多输入100个字");
	            flag1_editvm = false;
	            return;
	        }else{
	            $("#error2_editvm").html("<br/>");
	            flag1_editvm = true;
	        }
	    });
	}

});

//创建镜像
function submitCheck(){
	var type = $("#serverType_editvm").val();
	var str = $("#vmName_editvm").val();
	var str1 = $("#vmDescription_editvm").val();
    if(type == "name" && flag_editvm == false){
	    if(str == ""){
	        $("#error1_editvm").html("请输入名称");
	        return;
	    }else if(str.length > 20){
	        $("#error1_editvm").html("最多输入20个字");
	        return;
	    }else{
	        flag = true;
	        $("#error1_editvm").html("<br/>");
	    }
    }else if(type == "description" && flag1_editvm == false){
	    if(str1 == ""){
	        $("#error2_editvm").html("请输入描述");
	        return;
	    }else if(str1.length > 100){
	        $("#error2_editvm").html("最多输入100个字");
	        return;
	    }else{
	        flag1=true;
	        $("#error2_editvm").html("<br/>");
	    }
        str1 = str1.replace(new RegExp("\\n","g"),"；");
    }
    
    showLoading();
    $.post("vm/editvm",{
            serverId: $("#serverId_editvm").val(),
            serverName : $("#serverName_editvm").val(),
            displayName: str,
            displayDescription: str1,
            type: type
        },function(data){
            hideLoading();
            if(data.result=="success"){
                fillTipBox("success","修改成功！");
                if(type == "name"){
                	 $("#serverTitleName").html(data.displayName);
                     $("#newServerName1").html(data.displayName);
                }else if(type == "description"){
                	$("#newServerDes1").html(data.displayDescription);
                }
            } else {
                fillTipBox("error", data.result);
            }
            facebox_close();
    });
}
</script>
</body>
</html>
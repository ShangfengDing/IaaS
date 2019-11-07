<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<body>
<input type="hidden" id="uid" value="${param.uid}" />
<table>
	<tr>
		<td width="65px" class="blueletter">充值金额</td>
		<td><input type="text" class="editline" id="amount" name="amount" placeholder="请输入金额"/>
		<span class="redletter leftmargin_5" id="error1"></span>
		</td>
	</tr><tr>
        <td></td>
        <td class="topmargin_10"><br /><button class="button rightmargin_10" onclick="submitCheck()">确定</button>
        <button class="greybutton" onclick="facebox_close()">取消</button></td>
    </tr>
</table>
<script>
var flag = false;
$(function(){
	getStyle();
	$("#amount").blur(function(){
		var str=$(this).val();
        if(str == ""){
            $("#error1").html("请输入金额");
            flag = false;
            return;
        }else if(!isDecimal(str)){
            $("#error1").html("请正确输入");
            flag = false;
            return;
        }else{
        	flag = true;
            $("#error1").html("");
        }
	});
});

function isDecimal(str){
    var reg = /^\d+(\.\d{1,2})?$/;
    return reg.test(str);
}

//表单提交
function submitCheck(){
	var uid = $("#uid").val();
    if(flag == false){
        var str=$("#amount").val();
        if(str == ""){
            $("#error1").html("请输入金额");
            flag = false;
            return;
        }else if(!isDecimal(str)){
            $("#error1").html("请正确输入");
            flag = false;
            return;
        }else{
            flag = true;
            $("#error1").html("");
        }
    }
    
    var amount = $("#amount").val();
    showLoading();
    
    $.ajax({
        type:"post", 
        url:"price/recharge", 
        data:{
            uid: uid,
            recharge: amount
        },
        success:function(data){
            if(data.result == "success"){
            	$("#amount1").html(data.balance);
            	$("#usermanagebalance"+uid).html(data.balance);
            	
            	facebox_close();
                hideLoading();
                fillTipBox("success","充值成功");
            }else{
            	facebox_close();
                hideLoading();
                fillTipBox("error","充值失败");
            }
        }
    });
}
</script>
</body>
</html>
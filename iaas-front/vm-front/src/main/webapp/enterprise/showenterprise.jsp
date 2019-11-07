<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
<table class="datatable" id="showAll">
<tr>
    <td>选项</td>
    <td>内容</td>
</tr>
<tr>
    <td>企业名称</td>
    <td id="showname"></td>
</tr>
<tr>
    <td>企业描述</td>
    <td id="showdes"></td>
</tr>
<tr>
    <td>企业电话</td>
    <td id="showphone"></td>
</tr>
<tr>
    <td>企业邮箱</td>
    <td id="showemail"></td>
</tr>
<tr>
    <td>企业地址</td>
    <td id="showaddress"></td>
</tr>
<tr>
    <td>企业邮编</td>
    <td id="showpost"></td>
</tr>
<tr>
    <td>企业主页</td>
    <td id="showhomepage"></td>
</tr>
</table>
<table>
<tr>
    <td></td>
    <td class="rightfloat"><button class="button topmargin_10" onclick="saveenterprise()">提交</button>
    <button class="greybutton topmargin_10" onclick="closeFacebox()">取消</button>
    </td>
</tr>
</table>
<s:if test="#session.loginUserId != null">
	<input type="hidden" id="userId" value="<s:property value='#session.loginUserId'/>"/>
</s:if>
<s:else>
	<input type="hidden" id="userId" value=""/>
</s:else>
</body>
<script>
$(document).ready(function(){
	var name = $("#name").val().trim();
	var des = $("#des").val().trim();
	var phone = $("#phone").val().trim();
	var email = $("#email").val().trim();
	var address = $("#address").val().trim();
	var post = $("#post").val().trim();
	var homepage = $("#homepage").val().trim();
	
	$("#showname").html(name);
	$("#showdes").html(des);
	$("#showphone").html(phone);
	$("#showemail").html(email);
	
	if(address == "")
		$("#showaddress").html("无");
	else
		$("#showaddress").html(address);
	if(post == "") 
		$("#showpost").html("无");
	else 
		$("#showpost").html(post);
	if(homepage == "") 
		$("#showhomepage").html("无");
	else 
		$("#showhomepage").html(homepage);
});

function saveenterprise(){
	var userId =  $("#userId").val();
	var name = $("#name").val();
	var des = $("#des").val();
	var phone = $("#phone").val();
	var email = $("#email").val();
	var address = $("#address").val();
	var post = $("#post").val();
	var homepage = $("#homepage").val();
	
	showLoading();
	$.post("enterprise/newenterprise",
			{userId:userId,name:name,phone:phone,email:email,
			 address:address,post:post,homepage:homepage,
			 discription:des},
			function(data){
				if(data.result == "success"){
		 			gotonext("sum/sum");
				}else if(data.result == "fail"){
					fillTipBox("error", "创建企业失败");
				}else if(data.result == "member_of_enterprise") {
					fillTipBox("error", "您已经是企业成员");
				}
				hideLoading();
				facebox_close();
		});
	
}


</script>
</html>
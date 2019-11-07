<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.appcloud.vm.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>修改企业信息</title>
  	<s:include value="/template/_head.jsp" />
  	<link rel="stylesheet" media="all" type="text/css"  href="<%=Constants.FRONT_URL%>/css/jquery-ui.css" />
    <style>
    	.bluesliderbar .ui-slider-range { background: #74B8FD; }
    </style>
</head>
<body>
	<div id="container">
		<s:include value="/template/_pub_banner.jsp?index=v" />
		<div id="inner">
			<s:include value="/template/_left.jsp?menu=sum" />
			<div class="right">
<!-- 			<div class="divline">修改企业信息</div> -->
			<div class="divline" >
    			  <span class="greyletter midsize strong">
    			  <a class="greyletter" href="/vm/sum/sum"">综述首页</a>
    			  &nbsp;&gt;&nbsp;
    			  <a class="greyletter">修改企业信息</a></span>
   			 </div>
			<div class="dottedbox padding10 topmargin_20">
			<table class="formtable">
				<tr>
					<td>企业名称<span class="redletter">*</span></td>
					<td>
						<input type="text" value="<s:property value='enterprise.name'/>" class="editline bottommargin_10" id="changename" onblur="checkName()"/>
						<span class="redletter leftpadding_5" id="error1"></span>
					</td>
				</tr>
				<tr>
					<td>企业描述<span class="redletter">*</span></td>
					<td>
						<textarea  cols="30" rows="5" class="editbox" style="height:60px;" id="changedes" onblur="checkDes()"><s:property value="enterprise.description"/></textarea>
		               	<span class="redletter leftpadding_5" id="error2"></span>
		            </td>
				</tr>
				<tr>
					<td>企业电话<span class="redletter">*</span></td>
					<td>
						<input type="text" value="<s:property value='enterprise.phone'/>" class="editline bottommargin_10" id="changephone" onblur="checkPhone()" />
						<span class="redletter leftpadding_5" id="error3"></span>
					</td>
				</tr>
				<tr>
					<td>企业邮箱<span class="redletter">*</span></td>
					<td>
						<input type="text" value="<s:property value='enterprise.email'/>" class="editline bottommargin_10" id="changeemail" onblur="checkEmail()"/>
						<span class="redletter leftpadding_5" id="error4"></span>
					</td>
				</tr>
				<tr>
					<td>企业地址&nbsp;</td>
					<td>
						<input type="text" value="<s:property value='enterprise.address'/>" class="editline bottommargin_10" id="changeaddress" onblur="checkAddress()"/>
						<span class="redletter leftpadding_5" id="error5"></span>
					</td>
				</tr>
				<tr>
					<td>企业邮编&nbsp;</td>
					<td>
						<input type="text" value="<s:property value='enterprise.postcode'/>" class="editline bottommargin_10" id="changepost" onblur="checkPost()"/>
						<span class="redletter leftpadding_5" id="error6"></span>
					</td>
				</tr>
				<tr>
					<td>企业主页&nbsp;</td>
					<td>
						<input type="text" value="<s:property value='enterprise.homepage'/>" class="editline bottommargin_10" id="changehomepage" onblur="checkHomepage()"/>
						<span class="redletter leftpadding_5" id="error7"></span>
					</td>
				</tr>
				<tr>
				<td></td>
				<td><div class="topmargin_20">
					<a class="button rightmargin_20" href="javascript:void(0)" onclick="checkAll()">确定</a>
					<a class="greybutton" href="sum/sum">取消</a>
					</div>
				</td>
				</tr>
			</table>
			<s:if test="#session.loginUserId != null">
				<input type="hidden" id="userId" value="<s:property value='#session.loginUserId'/>"/>
			</s:if>
			<s:else>
				<input type="hidden" id="userId" value=""/>
			</s:else>
			<input type="hidden" id="enterpriseId" value="<s:property value='enterprise.id'/> " />
			</div>
		</div>
	</div>
	<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_commonjs.jsp"></s:include>
<script>
function checkAll() {
	if(!checkName()) 
		return false;
	if(!checkDes())
		return false;
	if(!checkPhone())
		return false;
	if(!checkEmail())
		return false;
	if(!checkAddress())
		return false;
	if(!checkPost())
		return false;
	if(!checkHomepage())
		return false;
	
	changeinformation();
}

function changeinformation() {
	var userId = $("#userId").val().trim();
	var enterpriseId = $("#enterpriseId").val().trim();
	var name = $("#changename").val();
	var des = $("#changedes").val().trim();
	var phone = $("#changephone").val();
	var email = $("#changeemail").val();
	var address = $("#changeaddress").val();
	var post = $("#changepost").val();
	var homepage = $("#changehomepage").val();
	showLoading();
	//
	$.post("enterprise/changeinformation",
		{userId:userId,
		 enterpriseId:enterpriseId,
		 name:name, 
		 discription:des, 
		 phone:phone, 
		 email:email, 
		 address:address, 
		 post:post, 
		 homepage:homepage},
		function(data){
			if(data.result == "success"){
				gotonext("sum/sum");
			}else if(data.result == "fail"){
				fillTipBox("error", "修改企业信息失败");
			}
			hideLoading();
			facebox_close();
	});
}

function checkName(){
	var name = $("#changename").val();
    if(name == ""){
        $("#error1").html("名称不得为空");
        return false;
    }else if(name.length > 20){
        $("#error1").html("名称不得超过20个字");
        return false;
    }else {
    	$("#error1").html("");
    	return true;
    }
}

function checkDes(){
	var des = $("#changedes").val();
    if(des == ""){
        $("#error2").html("描述不得为空");
        return false;
    }else if(des.length > 50){
        $("#error2").html("描述不得超过50个字");
        return false;
    }else {
    	$("#error2").html("");
    	return true;
    }
}

function isPhone(phone) {
	//分机号1-6位
	var regTel = /^(0[0-9]{2,3}\-)?([0-9]{6,9})(\-[0-9]{1,6})?$/;
	var regMobile = /^(13[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$/;
	if(phone.match(regTel) || phone.match(regMobile)) {
		return true;
	} else {
		return false;
	}
}
function checkPhone(){
	var phone = $("#changephone").val();
    if(phone == ""){
        $("#error3").html("电话不得为空");
        return false;
    }else if(!isPhone(phone)){
        $("#error3").html("无效电话号码，请检查电话号码是否正确 ");
        return false;
    }else {
    	$("#error3").html("");
    	return true;
    }
}

function isEmail(email) {
	var reg =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	if(email.match(reg))
		return true; 
	else
		return false;
}
function checkEmail() {
	var email = $("#changeemail").val();
    if(email == ""){
        $("#error4").html("邮箱不得为空");
        return false;
    }else if(!isEmail(email)){
        $("#error4").html("无效邮箱，请检查邮箱是否正确(提示:是否形如free@free4lab.com)");
        return false;
    }else {
    	$("#error4").html("");
    	return true;
    }
}

function checkAddress() {
	var address = $("#changeaddress").val();
    if(address.length > 50 ){
        $("#error5").html("地址不得超过20个字");
        return false;
    } else {
    	$("#error5").html("");
    	return true;
    }
}

function isPost(post) {
	var reg = /^[0-9]{6}$/;
	if(post.match(reg))
		return true; 
	else 
		return false;
}
function checkPost() {
	var post = $("#changepost").val();
	if(post == "") {
		return true;
	}else if(!isPost(post)){
        $("#error6").html("无效邮编，请检查邮编是否正确(提示:是否为6位数字)");
        return false;
    } else {
    	$("#error6").html("");
    	return true;
    }
}

function isWebsite(website) {
	var reg = /^(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?$/;
	if(website.match(reg))
		return true;
	else
		return false;
}
function checkHomepage() {
	var homepage = $("#changehomepage").val();
	if(homepage == "") {
		return true;
	}else if(!isWebsite(homepage)){
        $("#error7").html("无效网址，请检查网址是否正确(提示:是否形如http://iaas.free4lab.com)");
        return false;
    } else {
    	$("#error7").html("");
    	return true;
    }
}
</script>
</body>
</html>
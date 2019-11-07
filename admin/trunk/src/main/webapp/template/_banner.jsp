<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.api.enums.ImageTypeEnum"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:include value="/template/_common_js.jsp"></s:include>
<script>
	$(document).ready(function(){
		$.ajax({
			type:"post",
			url:"accounts/getRolelist",
			data:{topBarId:0},
			success:function(data) {
				var topBarList = data.topBarList;
				var leftBarByTopBar = JSON.parse(JSON.stringify(data.leftBarListByTopBar));
				var firstLeftBarId;
				var present = "";
				$.each(topBarList, function(i, item){
					firstLeftBarId = leftBarByTopBar[item][0];
					if (item == 0) { //系统管理
						if (firstLeftBarId == 0) {
							present += '<a href="system/infrastructure"> ';
						} else if (firstLeftBarId == 1)  {
							present += '<a href="system/zone">';
						} else {
							present += '<a href="img/imglist?type=<%=ImageTypeEnum.IMAGE%>">';
						}
						present += '<img class= ' + "'${param.menu == "sys"?"cur":""}'" +' src="images/banner_sys.png" border="0" /></a>';
					}
					if (item == 1) { //用户管理
						if (firstLeftBarId == 0) {
							present += '<a href="user/usermanage.jsp">';
						} else if (firstLeftBarId == 1)  {
							present += '<a href="user/userlist">';
						} else {
							present += '<a href="group/acGroupList">';
						}
						present += '<img class= ' + "'${param.menu == "user"?"cur":""}'" +' src="images/banner_user.png"  border="0" /></a>';
					}
					if (item == 2) { //财务管理
						/* if (firstLeftBarId == 0) {
							present += '<a href="vm/vmlist"> ';
						} else {
							present += '<a href="vm/presearchvm">';
						}
						present += '<img class= ' + "'${param.menu == "vm"?"cur":""}'" +' src="images/banner_vm.png"  border="0" /></a>'; */
						if (firstLeftBarId == 0) {
							present += '<a href="price/getrule?itemproducts=vm,vmpackage">';
						} else {
							present += '<a href="price/income.jsp">';
						}
						present += '<img class= ' + "'${param.menu == "price"?"cur":""}'" +' src="images/banner_bill.png"  border="0" /></a>';
					}
					if (item == 3) { //运行管理
						if (firstLeftBarId == 0) {
							present += '<a href="runtime/log.jsp">';
						} else if(firstLeftBarId == 1){
							present += '<a href="runtime/mailconf">';
						} else {
                            present += '<a href="runtime/accountLog.jsp">';
                        }
						present += '<img class= ' + "'${param.menu == "runtime"?"cur":""}'" +' src="images/admin_runtime.png"  border="0" /></a>';
						/* if (firstLeftBarId == 0) {
							present += '<a href="hd/hdlist"> ';
						} else {
							present += '<a href="hd/hdmanage">';
						}
						present += '<img class= ' + "'${param.menu == "hd"?"cur":""}'" +' src="images/banner_hd.png"  border="0" /></a>'; */
					}
					if (item == 4) { //操作管理
						/* if (firstLeftBarId == 0) {
							present += '<a href="price/getrule?itemproducts=vm,vmpackage"> ';
						} else {
							present += '<a href="price/income.jsp">';
						}
						present += '<img class= ' + "'${param.menu == "price"?"cur":""}'" +' src="images/banner_bill.png"  border="0" /></a>'; */
						if (firstLeftBarId == 0){
							present += '<a href="runtime/admin_manage.jsp">';
						} else if (firstLeftBarId == 1){
							present += '<a href="runtime/role_manage.jsp">';
						} else {
							present += '<a href="runtime/admin_log.jsp">';
						}
						present += '<img class= ' + "'${param.menu == "operation"?"cur":""}'" +' src="images/banner_operation.png"  border="0" /></a>';
					}
					/* if (item == 5) {
						 if (firstLeftBarId == 0) {
							present += '<a href="runtime/log.jsp"> ';
						} else if (firstLeftBarId == 1)  {
							present += '<a href="runtime/mailconf">';
						} else if (firstLeftBarId == 2){
							present += '<a href="runtime/admin_manage.jsp">';
						} else if (firstLeftBarId == 3){
							present += '<a href="runtime/role_manage.jsp">';
						} else {
							present += '<a href="runtime/admin_log.jsp">';
						}
						present += '<img class= ' + "'${param.menu == "runtime"?"cur":""}'" +' src="images/admin_runtime.png"  border="0" /></a>'; 
					} */
				});
				$('#topBar').html(present);
			}
		});
	});
</script>
<div class="banner">
  <div class="content">
    <img id="logo" src="images/logo.png" border="0"/>
    <span class="nav" id="topBar">
<%--         
        <a href="system/host" id="sys"><img class='${ param.menu=="1"?"cur":"" }' src="images/banner_sys.png" border="0" /></a>
        <a href="user/user" id="user"><img class='${ param.menu=="2"?"cur":"" }' src="images/banner_user.png" border="0" /></a>
        <a href="service/appverify" id="service"><img class='${ param.menu=="3"?"cur":"" }' src="images/banner_app.png" border="0" /></a>
        <a href="vm/vm" id="vm"><img class='${ param.menu=="4"?"cur":"" }' src="images/banner_vm.png" border="0" /></a>
        <a href="security/adminlog" id="security"><img class='${ param.menu=="5"?"cur":"" }' src="images/banner_security.png" border="0" /></a>
        <a href="warn/alertmsg" id="warn"><img class='${ param.menu=="6"?"cur":"" }' src="images/banner_warn.png" border="0" /></a>

        <a href="system/infrastructure"><img class='${ param.menu=="sys"?"cur":"" }' src="images/banner_sys.png" border="0" /></a>
        <a href="user/userlist"><img class='${ param.menu=="user"?"cur":"" }' src="images/banner_user.png" border="0" /></a>
        <a href="vm/vmlist"><img class='${ param.menu=="vm"?"cur":"" }' src="images/banner_vm.png" border="0" /></a>
        <a href="hd/hdlist"><img class='${ param.menu=="hd"?"cur":"" }' src="images/banner_hd.png" border="0" /></a>
        <a href="price/getrule"><img class='${ param.menu=="price"?"cur":"" }' src="images/banner_bill.png" border="0" /></a>
    	<a href="alert/log.jsp"><img class='${ param.menu=="alert"?"cur":"" }' src="images/banner_warn.png" border="0" /></a>
    	 --%>
    </span>
    <div class="userinfo topmargin_10">        
        <div>登录为
            <span id="username" class="strong leftmargin_5"><s:property value="#session.username"/></span>
            <span><a href="accounts/logout" class="strong blackletter leftmargin_5 rightmargin_10">退出</a></span>
        </div>
    </div>  
    </div>
</div>

<!DOCTYPE html>
<%@page import="com.appcloud.vm.common.SessionConstants"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="left">
<!-- <div class="imgbutton"><a href="vm/prenewvm"><img alt="购买虚拟机" src="images/vm_create.png" /></a></div>
<div class="imgbutton"><a href="hd/prenewhd"><img alt="购买网络硬盘" src="images/newdisk.png" /></a></div> -->
<dl>
	<dt class='${param.menu=="sum"?"selected":""}'>
		<a href="sum/sum"><img src="images/left1_sum.png" /></a>
	</dt>
	<dt class="${param.menu=='newvm'||param.menu=='newhd'||param.menu=='newip'?'selected':''}">
		<a href="javascript:void(0)"><img src="images/left2_newapply.png" /></a>
	</dt>
	<dd class="${param.menu=='newvm'||param.menu=='newhd'||param.menu=='newip'?'':'close'}">
		<ul>
			<li class="padding5 ${param.menu=='newvm'?'selected':''}"><a href="vm/prenewvm"><img src="images/left21_newvm.png" /></a></li>
			<li class="padding5 ${param.menu=='newhd'?'selected':''}"><a href="hd/prenewhd"><img src="images/left22_newhd.png" /></a></li>
			<%-- <li class="padding5 ${param.menu=='newip'?'selected':''}"><a href="ip/newip.jsp"><img src="images/left23_newip.png" /></a></li> --%>
		</ul>
	</dd>
	<dt class="${param.menu=='vm'||param.menu=='hd'||param.menu=='ip'||param.menu=='shot'||param.menu=='backup'||param.menu=='fwlist'?'selected':''}">
		<a href="javascript:void(0)"><img src="images/left3_my.png" /></a>
	</dt>
	<dd class="${param.menu=='vm'||param.menu=='hd'||param.menu=='ip'||param.menu=='shot'||param.menu=='backup'||param.menu=='fwlist'?'':'close'}">
		<ul>
			<li class="padding5 ${param.menu=='vm'?'selected':''}"><a href="vm/vmlist"><img src="images/left31_myvm.png" /></a></li>
			<li class="padding5 ${param.menu=='hd'?'selected':''}"><a href="hd/hdlist"><img src="images/left32_myhd.png" /></a></li>
			<%-- <li class="padding5 ${param.menu=='ip'?'selected':''}"><a href="ip/iplist.jsp"><img src="images/left33_myip.png" /></a></li> --%>
			<li class="padding5 ${param.menu=='shot'?'selected':''}"><a href="shot/shot"><img src="images/left34_mysnapshot.png" /></a></li>
			<li class="padding5 ${param.menu=='backup'?'selected':''}"><a href="backup/backup"><img src="images/left35_mybackup.png" /></a></li>
			<li class="padding5 ${param.menu=='fwlist'?'selected':''}"><a href="fw/fwlist"><img src="images/left36_myfw.png" /></a></li>
		</ul>
	</dd>
	<dt class='${param.menu=="log" || param.menu=="admin"?"selected":""}'>
        <a href="javascript:void(0)"><img src="images/left4_log.png" /></a>
    </dt>
    <dd class="${param.menu=='log'||param.menu=='admin'?'':'close'}">
		<ul>
			<li class="padding5 ${param.menu=='log'?'selected':''}"><a href="log/log.jsp"><img src="images/left41_log.png" /></a></li>
			<%
			String isEnterprise = (String) session.getAttribute(SessionConstants.IsEnterpriseAdmin);
			if (isEnterprise != null && isEnterprise.equals(SessionConstants.ENTERPRISE_OWNER)) { %>
				<li class="padding5 ${param.menu=='admin'?'selected':''}"><a href="log/admin.jsp"><img src="images/left42_admin.png" /></a></li>
			<% } %> 
		</ul>
	</dd>
	<%-- <dt class='${param.menu=="shot"?"selected":""}'>
		<a href="shot/shot">我的快照</a>
	</dt>
	<dt class='${param.menu=="backup"?"selected":""}'>
		<a href="backup/backup">我的备份</a>
	</dt>
	<dt class='${param.menu=="fwlist"?"selected":""}'>
		<a href="fw/fwlist">防火墙设置</a>
	</dt> --%>
</dl>
</div><!--left-->
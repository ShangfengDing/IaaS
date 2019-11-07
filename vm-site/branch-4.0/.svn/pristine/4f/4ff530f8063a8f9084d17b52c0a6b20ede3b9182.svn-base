<%@ page import="com.appcloud.vm.common.Constants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Properties" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登录首页</title>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <s:include value="/template/_head.jsp"/>

<style type="text/css">
	
</style>
</head>
<body>
	<nav class="navbar navbar-default" style="border-radius: 0">
		<div class="container">
			<div class="row">
				<div  style="margin-left: 4%;float:left">					
			       <a class="navbar-brand front-brand"><div class="iass-brand-img"></div></a>
				</div>
				<div class="pull-right">
				<ul class="nav navbar-nav" style="float:left;margin:0">
				  <li><a href="<%=Constants.ACCOUNT_URL%>register/freeshare_reg_pri.jsp" class="free_reg" style="text-decoration:underline">免费注册</a></li>
			      <li><a href="./account/landing?redirect_url=<%=request.getParameter("redirect_url")%>"  style="text-decoration:underline;padding-right:0">登录</a></li>
				</ul>
			     
			    </div>
			</div>
		</div>
	</nav>
	<div class="iasshero jumbotron no_margin_bottom">
	  <div class="container">
	  	<div class="row">
	  		<div style="margin-left:4%"><img src="images/iassicon.png"></img>
	  			<h2 style="color: white">一站式混合云管理平台</h2>
	  		</div>
	  	</div>
	  	<div class="row top_margin30">
	  		<div style="margin-left:4%">
	  			<a class="btn btn-info btn-lg login-page-btn free_reg" href="<%=Constants.ACCOUNT_URL%>register/freeshare_reg_pri.jsp">免费注册</a>&nbsp;
	  			<a class="btn btn-info btn-lg login-page-btn" href="./account/landing?redirect_url=<%=request.getParameter("redirect_url")%>" >立即登录</a>
	  		</div>
	  	</div>
	  </div>
	</div>

		<!-- 云资源-->
		<div class="jumbotron no_margin_bottom bgwhite" style="margin-top:-70px">
		  <div class="container">
			  <div class="row">
					<div class="col-md-6 text-left pmiddle">			
						<h2><strong>整合云中资源，让资源随需而变</strong></h2>
						<div>
							<p>灵活提供各种配置（CPU、内存、硬盘容量、网络带宽等）</p>
							<p>各种模板（操作系统、应用软件）的云主机和云硬盘</p>
							<p>轻松创建云快照，让数据及时恢复如初</p>
						</div>
					</div>
					<div class="col-md-6 cloudsrc"></div>
				</div>
		  </div>		
		</div>
		
		<!-- 综合云资源 -->
		<div class="jumbotron no_margin_bottom">
			<div class="container">
				<div class="row bggray">
					<div class="col-md-6 sumsrc pmiddle" style="background-image:url(images/cloudsum.png)"></div>
					<div class="col-md-6 text-left pmiddle">			
						<h2><strong>汇聚云的力量，融合私有云和公有云</strong></h2>
						<div>
							<p>便捷搭建覆盖多个机房的私有云</p>
							<p>高效整合多家公有云（阿里云、Amazon等）</p>
						</div>
					</div>		
				</div>
			</div>
		</div>
		
		<!-- 管理控制台-->
		<div class="jumbotron no_margin_bottom bgwhite">
			<div class="container">
				<div class="row">
					<div class="col-md-6 text-left pmiddle">			
						<h2><strong>强化云的管理，提供全方位的管理功能</strong></h2>
						<div>
							<p>实时查看各类云资源状态，随时掌控各项运行指标</p>
							<p>及时获得各类警告和通知，有效降低运维成本</p>
						</div>
					</div>
					<div class="col-md-6 cloudcontrol"></div>
				</div>
			</div>
		</div>
		
		<!-- 开放云接口-->
		<div class="jumbotron no_margin_bottom">
			<div class="container">
				<div class="row bggray">
					<div class="col-md-6 cloudopen"></div>
					<div class="col-md-6 text-left pmiddle">			
						<h2><strong>开放云的能力，为应用铺就登云之路</strong></h2>
						<div>
							<p>提供完备的云资源管控接口，让应用直接调配云资源</p>
							<p>为应用提供基于云的高可用、弹性伸缩等优势特性</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	
	<!--底部标签-->
	<footer class="footer text-center" style="margin-top:0px">
		<p class="footer-copyright" style="margin-top:40px">
			Copyright © 1996-2016 自邮之翼, All Rights Reserved
		</p>
	</footer>
</body>
<s:include value="template/_global.jsp"/>
</html>

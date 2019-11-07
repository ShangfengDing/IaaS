<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>欢迎进入Appcloud</title>
	<s:include value="/template/_head.jsp"/>
</head>
<body>

<div class="indexbg">
<div id="inner">
<div class="logo">
	<img src="images/logo_index.png" border="0" />
</div><!--logo-->
<div class="indexbanner">
<div class="content">
	<div class="slider">
		<div id="lofslidecontent45" class="lof-slidecontent">
			<div class="preload"><div></div></div>
			<div class="lof-main-outer">
			    <ul class="lof-main-wapper">
					<li><img src="images/01.png"/></li> 
			       	<li><img src="images/02.png"/></li>
				</ul>     
			</div>
			<div class="lof-navigator-wapper">
				<div class="lof-navigator-outer">
				    <ul class="lof-navigator">
				        <li><span>1</span></li>
				        <li><span>2</span></li>
				    </ul>
				</div>
			</div> 
		</div> 
	</div><!--slider-->
	
	<div class="login">
		<span class="login_text"><img src="images/login_text.png" border="0" /></span>
		<div class="login_form">
		<s:if test="%{#session.devId==null}">
			<s:include value="_indexlogin.jsp" />
		</s:if>
		<s:else>
			<s:action name="indexdevinfo" namespace="/accounts" executeResult="true"></s:action>
		</s:else>
		</div><!--login_form-->
	</div><!--login-->
</div><!--content-->
</div><!--indexbanner-->

<div class="content">
	<img src="images/provideyou.png" border="0" />
	<div class="clear"></div>
	<div class="introbox">
	<img src="images/1.png" border="0" />多数据中心管理
	<ul class="indexul">
	    <li>一套管理平台综合管理多个数据中心(idc)</li>
	    <li>一个数据中心可划分为多个集群，满足用户差异化服务的需求</li>
	</ul>
	</div>
	<div class="introbox">
	<img src="images/2.png" border="0" />物理资源管理
	<ul class="indexul">
	    <li>综合管理平台中全部物理资源（服务器、存储、网络）</li>
	    <li>从部署到运行监控全覆盖</li>
	</ul>
	</div>
	<div class="introbox">
	<img src="images/3.png" border="0" />虚拟资源管理
	<ul class="indexul">
	    <li>综合管理平台中全部虚拟资源（云服务器、云硬盘）</li>
	    <li>高效的虚拟资源调度策略提高物理资源利用率</li>
	</ul>
	</div>
	<div class="introbox">
	<img src="images/4.png" border="0" />运营计费管理
	<ul class="indexul">
	    <li>全面的运营管理，从用户管理到产品管理</li>
	    <li>完备的计费管理，从套餐管理到财务管理</li>
	</ul>
	</div>
	<div class="clear"></div>
</div>
</div><!--#inner-->
<s:include value="/template/_footer.jsp" />
</div>
<s:include value="/template/_common_js.jsp" />
</body>
</html>

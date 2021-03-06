<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.appcloud.vm.common.Constants"%>
<%
	request.setAttribute("aliyun",
			com.appcloud.vm.fe.common.Constants.ALI_YUN);
%>
<!DOCTYPE html>
<html>
<head>
<title>详情 - 云主机 - 云海IaaS</title>
<s:include value="../template/_head.jsp" />
<style>

.slider-tick {
	opacity: 0 !important;
}

.slider-selection {
	background-image: -webkit-linear-gradient(top, #89cdef 0, #81bfde 100%);
	background-image: -o-linear-gradient(top, #89cdef 0, #81bfde 100%);
	background-image: linear-gradient(to bottom, #89cdef 0, #81bfde 100%);
	background-repeat: repeat-x;
}
</style>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()%>/" />

</head>
<body class="front-body">
	<s:include value="../template/_banner.jsp?menu=vmdetail" />
	<div class="front-inner" style="background-color: #eee;">
		<div class="container">
			<div class="fix-gutter">
				<div class="row">
					<div class="col-md-12">
						<ul class="breadcrumb">
							<li><a href="vm/vmlist.jsp">云主机</a></li>
							<li class="active"><s:property value="details.instanceName" /></li>
						</ul>
					</div>

				</div>
				<div class="front-toolbar other"
					style="margin-top: 5px; margin-left: -4px" id="prev-toolbar">
					<div class="front-toolbar-header clearfix">
						<button type="button" class="front-toolbar-toggle navbar-toggle"
							data-toggle="collapse" data-target="#base-buttons">
							<span class="icon-bar"></span> <span class="icon-bar"></span>
							<span class="icon-bar"></span><span class="icon-bar"></span>
						</button>
					</div>
                    <div id="base-buttons" class="front-btn-group collapse">
                        <!-- 云海和阿里云的过渡状态 -->
                        <s:if test='details.status=="other"|| details.status=="Stopping"|| details.status=="Starting"'>
                            <a class="btn btn-default front-no-box-shadow" disabled="disabled"><img
                                    src="images/qidong.png" />&nbsp;&nbsp;启动</a>
                            <a class="btn btn-default front-no-box-shadow" disabled="disabled">
                                <img src="images/tingzhi.png" />&nbsp;&nbsp;停止
                            </a>
                            <a class="btn btn-default front-no-box-shadow" disabled="disabled">
                                <img src="images/chongqi.png" />&nbsp;&nbsp;重启
                            </a>
                            <s:if test="details.providerEn != #request.aliyun">
                                <a class="btn btn-default front-no-box-shadow" disabled="disabled">
                                    <img src="images/guaqi.png" />&nbsp;&nbsp;挂起</a>
                                <a class="btn btn-default front-no-box-shadow active" disabled="disabled">
                                    <img src="images/huifu.png" />&nbsp;&nbsp;恢复</a>
                                <!-- 远程，需要用域名 -->
                                <a class="btn btn-default front-no-box-shadow" disabled="disabled">
                                    <img src="images/yuancheng.png" />&nbsp;&nbsp;远程</a>
                            </s:if>
                            <a class="btn btn-default front-no-box-shadow" disabled="disabled">创建快照</a>
                            <s:if test="details.providerEn != #request.aliyun">
                                <a class="btn btn-default front-no-box-shadow" disabled="disabled">系统重置</a>
                                <a class="btn btn-default front-no-box-shadow" disabled="disabled">iso安装</a>
                                <a class="btn btn-default front-no-box-shadow active" disabled="disabled">iso弹出</a>
                                <a class="btn btn-default front-no-box-shadow"
                                   onclick='forceStop("<s:property value="details.status"/>","<s:property
                                           value="details.instanceId"/>","<s:property value="userEmail"/>",
                                           "<s:property value="regionId"/>","<s:property
                                           value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>强制关机</a>
                            </s:if>
                            <!-- 阿里云主机只有状态为stopped的时候才能删除 -->
                            <s:if test="details.providerEn == 'aliyun'">
                                <a class="btn btn-default front-no-box-shadow active" disabled="disabled">删除</a>
                                <a class="btn btn-danger" disabled="disabled">发布模板</a>
                            </s:if>
                            <s:else>
                                <a class="btn btn-default front-no-box-shadow"
                                   onclick='del("<s:property value="details.status"/>","<s:property
                                           value="details.instanceId"/>","<s:property value="userEmail"/>",
                                           "<s:property value="regionId"/>","<s:property value="details.endTime"/>",
                                           <%=Constants.DEFAULT_CHECKTIME%>);'>删除</a>
                                <a class="btn btn-default" disabled="disabled">发布模板</a>
                            </s:else>
                        </s:if>

                        <%--云主机处于活动状态时--%>
                        <s:if test='details.status=="active" || details.status=="Running"'>
							<a class="btn btn-default front-no-box-shadow" disabled="disabled"><img
								src="images/qidong.png" />&nbsp;&nbsp;启动</a>
							<a class="btn btn-default front-no-box-shadow"
								onclick='stop("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>
								<img src="images/tingzhi.png" />&nbsp;&nbsp;停止
							</a>
							<a class="btn btn-default front-no-box-shadow"
								onclick='reboot("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>
								<img src="images/chongqi.png" />&nbsp;&nbsp;重启
							</a>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow"
									onclick='suspend("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>
									<img src="images/guaqi.png" />&nbsp;&nbsp;挂起
								</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
									src="images/huifu.png" />&nbsp;&nbsp;恢复</a>
								<!-- 远程，需要用域名 -->
								<a class="btn btn-default front-no-box-shadow" href="vnc/index?token=<s:property value="details.instanceId"/>&regionId=<s:property value="details.regionIdEn"/>&appname=<s:property value="appname"/>" target="_blank"><img
									src="images/yuancheng.png" />&nbsp;&nbsp;远程</a>
							</s:if>
							<s:if test="provider=='yunhai'">
								<a class="btn btn-default front-no-box-shadow"
								onclick='modalValue("<s:property value='details.systemDiskId'/>","<s:property
                                   value='provider'/>","<s:property value='regionId'/>","<s:property value='userEmail'/>","<s:property value='userId'/>","<s:property value='appname'/>")'>创建快照</a>
								<a class="btn btn-default front-no-box-shadow " onclick='migrate("<s:property value='regionId'/>","<s:property value='details.zoneId'/>",null,"<s:property
										value="details.instanceId"/>","<s:property
										value='userEmail'/>")' >云主机迁移</a>
								<a class="btn btn-default front-no-box-shadow " onclick='backup("<s:property value='regionId'/>","<s:property value='zoneId'/>","<s:property
										value="details.instanceId"/>","<s:property value='details.systemDiskId'/>","<s:property value='userEmail'/>","<s:property value='appname'/>")' >备份</a>
							</s:if>
							<s:else>
								<a class="btn btn-default front-no-box-shadow"
								onclick='aliShotModal("<s:property value='details.systemDiskId'/>","<s:property
                                   value='details.instanceId'/>","SYSTEM","<s:property value='userEmail'/>","<s:property value='provider'/>",
										"<s:property value='regionId'/>", "<s:property value='appname'/>")'>创建快照</a>
							</s:else>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow"
									onclick='reset("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>系统重置</a>
								<a class="btn btn-default front-no-box-shadow"
									onclick='isoBoot("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>iso安装</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled">iso弹出</a>
								<a class="btn btn-default front-no-box-shadow"
									onclick='forceStop("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>强制关机</a>
							</s:if>
							<!-- 阿里云主机只有状态为stopped的时候才能删除 -->
							<s:if test="details.providerEn == 'aliyun'">
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled">删除</a>
                                <a class="btn btn-danger"
                                   onclick='alertAliImage()'>发布模板</a>
                            </s:if>
							<s:else>
								<a class="btn btn-default front-no-box-shadow"
									onclick='del("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>删除</a>
                                <a class="btn btn-danger"
                                   onclick='createImage(
                                           "<s:property value="regionId" default="beijing"/>",
                                           "<s:property value="zoneId" default="spark"/>","<s:property value="snapshotId" default=""/>",
                                           "<s:property value="details.instanceId" default=""/>","<s:property value="details.systemDiskId" default=""/>",
                                           "<s:property value="userEmail" default=""/>"
                                           );'>发布模板</a>
							</s:else>

						</s:if>
						<%--云主机处于停止状态时--%>
						<s:elseif
							test='details.status=="stopped" || details.status=="Stopped"'>
							<a class="btn btn-default front-no-box-shadow"
								onclick='start("<s:property value="details.status"/>","<s:property
                                    value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'><img
								src="images/qidong.png" />&nbsp;&nbsp;启动</a>
							<a class="btn btn-default front-no-box-shadow active" disabled="disabled"> <img
								src="images/tingzhi.png" />&nbsp;&nbsp;停止
							</a>
							<a class="btn btn-default front-no-box-shadow active" disabled="disabled"> <img
								src="images/chongqi.png" />&nbsp;&nbsp;重启
							</a>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
									src="images/guaqi.png" />&nbsp;&nbsp;挂起</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
									src="images/huifu.png" />&nbsp;&nbsp;恢复</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
									src="images/yuancheng.png" />&nbsp;&nbsp;远程</a>
							</s:if>

							<s:if test="provider=='yunhai'">
								<a class="btn btn-default front-no-box-shadow"
								onclick='modalValue("<s:property value='details.systemDiskId'/>","<s:property
                                   value='provider'/>","<s:property value='regionId'/>","<s:property
                                   value='userEmail'/>","<s:property value='userId'/>","<s:property value='appname'/>")'>创建快照</a>
								<a class="btn btn-default front-no-box-shadow " onclick='migrate("<s:property value='regionId'/>","<s:property value='details.zoneId'/>",null,"<s:property
										value="details.instanceId"/>","<s:property
										value='userEmail'/>")' >云主机迁移</a>
								<a class="btn btn-default front-no-box-shadow " onclick='backup("<s:property value='regionId'/>","<s:property value='zoneId'/>","<s:property
										value="details.instanceId"/>","<s:property value='details.systemDiskId'/>","<s:property value='userEmail'/>","<s:property value='appname'/>")' >备份</a>
							</s:if>
							<s:else>
								<a class="btn btn-default front-no-box-shadow"
								onclick='aliShotModal("<s:property value='details.systemDiskId'/>","<s:property
                                   value='details.instanceId'/>","SYSTEM","<s:property
                                   value='userEmail'/>","<s:property value='provider'/>","<s:property value='regionId'/>","<s:property value='appname'/>")'>创建快照</a>
							</s:else>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow"
									onclick='reset("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>系统重置</a>
								<a class="btn btn-default front-no-box-shadow"
									onclick='isoBoot("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>iso安装</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled">iso弹出</a>
								<a class="btn btn-default front-no-box-shadow"
									onclick='forceStop("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>强制关机</a>
							</s:if>

							<a class="btn btn-default front-no-box-shadow"
								onclick='del("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>删除</a>
                            <s:if test="details.providerEn == 'aliyun'">
                                <a class="btn btn-danger" onclick='alertAliImage()'>发布模板</a>
                            </s:if>
                            <s:else>
                                <a class="btn btn-danger"
                                   onclick='createImage(
                                           "<s:property value="regionId" default=""/>",
                                           "<s:property value="zoneId" default=""/>","<s:property value="snapshotId" default=""/>",
                                           "<s:property value="details.instanceId" default=""/>","<s:property value="details.systemDiskId" default=""/>",
                                           "<s:property value="userEmail" default=""/>"
                                           );'>发布模板</a>
                            </s:else>

						</s:elseif>
						<%--云主机处于挂起状态时--%>
						<s:elseif test='details.status=="suspended"'>
							<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
								src="images/qidong.png" />&nbsp;&nbsp;启动</a>
							<a class="btn btn-default front-no-box-shadow"
								onclick='stop("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>
								<img src="images/tingzhi.png" />&nbsp;&nbsp;停止
							</a>
							<a class="btn btn-default front-no-box-shadow"
								onclick='reboot("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>
								<img src="images/chongqi.png" />&nbsp;&nbsp;重启
							</a>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
									src="images/guaqi.png" />&nbsp;&nbsp;挂起</a>
								<a class="btn btn-default front-no-box-shadow"
									onclick='resume("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>
									<img src="images/huifu.png" />&nbsp;&nbsp;恢复
								</a>
								<a class="btn btn-default front-no-box-shadow" disabled="disabled"><img
									src="images/yuancheng.png" />&nbsp;&nbsp;远程</a>
							</s:if>

							<s:if test="provider=='yunhai'">
								<a class="btn btn-default front-no-box-shadow"
								onclick='modalValue("<s:property value='details.systemDiskId'/>","<s:property
                                   value='provider'/>","<s:property value='regionId'/>","<s:property
                                   value='userEmail'/>","<s:property value='userId'/>","<s:property value='appname'/>")'>创建快照</a>
								<a class="btn btn-default front-no-box-shadow " onclick='migrate("<s:property value='regionId'/>","<s:property value='details.zoneId'/>",null,"<s:property
										value="details.instanceId"/>","<s:property
										value='userEmail'/>")' >云主机迁移</a>
								<a class="btn btn-default front-no-box-shadow " onclick='backup("<s:property value='regionId'/>","<s:property value='zoneId'/>","<s:property
										value="details.instanceId"/>","<s:property value='details.systemDiskId'/>","<s:property value='userEmail'/>","<s:property value='appname'/>")' >备份</a>
							</s:if>
							<s:else>
								<a class="btn btn-default front-no-box-shadow"
								onclick='aliShotModal("<s:property value='details.systemDiskId'/>","<s:property
                                   value='details.instanceId'/>","SYSTEM","<s:property
                                   value='userEmail'/>","<s:property value='provider'/>","<s:property value='regionId'/>", "<s:property value='appname'/>")'>创建快照</a>
							</s:else>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow"
									onclick='reset("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>系统重置</a>
								<a class="btn btn-default front-no-box-shadow"
									onclick='isoBoot("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>iso安装</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled">iso弹出</a>
								<a class="btn btn-default front-no-box-shadow"
									onclick='forceStop("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>强制关机</a>
							</s:if>

							<a class="btn btn-default front-no-box-shadow"
								onclick='del("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>删除</a>
                            <s:if test="details.providerEn == 'aliyun'">
                                <a class="btn btn-danger"
                                   onclick='alertAliImage()'>发布模板</a>
                            </s:if>
                            <s:else>
                                <a class="btn btn-danger"
                                   onclick='createImage(
                                           "<s:property value="regionId" default=""/>",
                                           "<s:property value="zoneId" default=""/>","<s:property value="snapshotId" default=""/>",
                                           "<s:property value="details.instanceId" default=""/>","<s:property value="details.systemDiskId" default=""/>",
                                           "<s:property value="userEmail" default=""/>"
                                           );'>发布模板</a>
                            </s:else>
						</s:elseif>
						<%--云主机处于building状态时--%>
						<s:elseif test='details.status=="building"'>
							<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
								src="images/qidong.png" />&nbsp;&nbsp;启动</a>
							<a class="btn btn-default front-no-box-shadow active" disabled="disabled"> <img
								src="images/tingzhi.png" />&nbsp;&nbsp;停止
							</a>
							<a class="btn btn-default front-no-box-shadow active" disabled="disabled"> <img
								src="images/chongqi.png" />&nbsp;&nbsp;重启
							</a>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled"> <img
									src="images/guaqi.png" />&nbsp;&nbsp;挂起
								</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
									src="images/huifu.png" />&nbsp;&nbsp;恢复</a>
								<a class="btn btn-default front-no-box-shadow" disabled="disabled"><img
									src="images/yuancheng.png" />&nbsp;&nbsp;远程</a>
							</s:if>

							<s:if test="provider=='yunhai'">
								<a class="btn btn-default front-no-box-shadow"
								onclick='modalValue("<s:property value='details.systemDiskId'/>","<s:property
                                   value='provider'/>","<s:property value='regionId'/>","<s:property
                                   value='userEmail'/>","<s:property value='userId'/>","<s:property value='appname'/>")'>创建快照</a>
								<a class="btn btn-default front-no-box-shadow " onclick='migrate("<s:property value='regionId'/>","<s:property value='details.zoneId'/>",null,"<s:property
										value="details.instanceId"/>","<s:property
										value='userEmail'/>")' >云主机迁移</a>
								<a class="btn btn-default front-no-box-shadow " onclick='backup("<s:property value='regionId'/>","<s:property value='zoneId'/>","<s:property
										value="details.instanceId"/>","<s:property value='details.systemDiskId'/>","<s:property value='userEmail'/>","<s:property value='appname'/>")' >备份</a>
							</s:if>
							<s:else>
								<a class="btn btn-default front-no-box-shadow"
								onclick='aliShotModal("<s:property value='details.systemDiskId'/>","<s:property
                                   value='details.instanceId'/>","SYSTEM","<s:property
                                   value='userEmail'/>","<s:property value='provider'/>","<s:property value='regionId'/>", "<s:property value='appname'/>")'>创建快照</a>
							</s:else>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow"
									onclick='reset("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>系统重置</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled">iso安装</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled">iso弹出</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled">强制关机</a>
							</s:if>

							<a class="btn btn-default front-no-box-shadow active" disabled="disabled">删除</a>
                            <s:if test="details.providerEn == #request.aliyun">
                                <a class="btn btn-danger"
                                   onclick='alertAliImage()'>发布模板</a>
                            </s:if>
                            <s:else>
                                <a class="btn btn-danger"
                                   onclick='createImage(
                                           "<s:property value="regionId" default=""/>",
                                           "<s:property value="zoneId" default=""/>","<s:property value="snapshotId" default=""/>",
                                           "<s:property value="details.instanceId" default=""/>","<s:property value="details.systemDiskId" default=""/>",
                                           "<s:property value="userEmail" default=""/>"
                                           );'>发布为模板</a>
                            </s:else>
						</s:elseif>
						<%--云主机处于rebuilding状态时--%>
						<s:elseif test='details.status=="rebuilding"'>
							<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
								src="images/qidong.png" />&nbsp;&nbsp;启动</a>
							<a class="btn btn-default front-no-box-shadow active" disabled="disabled"> <img
								src="images/tingzhi.png" />&nbsp;&nbsp;停止
							</a>
							<a class="btn btn-default front-no-box-shadow active" disabled="disabled"> <img
								src="images/chongqi.png" />&nbsp;&nbsp;重启
							</a>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled"> <img
									src="images/guaqi.png" />&nbsp;&nbsp;挂起
								</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled"><img
									src="images/huifu.png" />&nbsp;&nbsp;恢复</a>
								<a class="btn btn-default front-no-box-shadow" href="vnc/index?token=<s:property value="details.instanceId"/>&regionId=<s:property value="details.regionIdEn"/>&appname=<s:property value="appname"/>" target="_blank">
									<img src="images/yuancheng.png" />&nbsp;&nbsp;远程</a>
							</s:if>

							<s:if test="provider=='yunhai'">
								<a class="btn btn-default front-no-box-shadow"
								onclick='modalValue("<s:property value='details.systemDiskId'/>","<s:property
                                   value='provider'/>","<s:property value='regionId'/>","<s:property
                                   value='userEmail'/>","<s:property value='userId'/>","<s:property value='appname'/>")'>创建快照</a>
								<a class="btn btn-default front-no-box-shadow " onclick='migrate("<s:property value='regionId'/>","<s:property value='details.zoneId'/>",null,"<s:property
										value="details.instanceId"/>","<s:property
										value='userEmail'/>")' >云主机迁移</a>
								<a class="btn btn-default front-no-box-shadow " onclick='backup("<s:property value='regionId'/>","<s:property value='zoneId'/>","<s:property
										value="details.instanceId"/>","<s:property value='details.systemDiskId'/>","<s:property value='userEmail'/>","<s:property value='appname'/>")' >备份</a>
							</s:if>
							<s:else>
								<a class="btn btn-default front-no-box-shadow"
								onclick='aliShotModal("<s:property value='details.systemDiskId'/>","<s:property
                                   value='details.instanceId'/>","SYSTEM","<s:property
                                   value='userEmail'/>","<s:property value='provider'/>","<s:property value='regionId'/>", "<s:property value='appname'/>")'>创建快照</a>
							</s:else>
							<s:if test="details.providerEn != #request.aliyun">
								<a class="btn btn-default front-no-box-shadow"
									onclick='reset("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>系统重置</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled">iso安装</a>
								<a class="btn btn-default front-no-box-shadow"
									onclick='isoDetach("<s:property value="details.status"/>","<s:property
                                   value="details.instanceId"/>","<s:property value="userEmail"/>",
                                   "<s:property value="regionId"/>","<s:property
                                   value="details.endTime"/>",<%=Constants.DEFAULT_CHECKTIME%>);'>iso弹出</a>
								<a class="btn btn-default front-no-box-shadow active" disabled="disabled">强制关机</a>
							</s:if>

							<a class="btn btn-default front-no-box-shadow active" disabled="disabled">删除</a>
                            <s:if test="details.providerEn == #request.aliyun">
                                <a class="btn btn-danger"
                                   onclick='alertAliImage()'>发布模板</a>
                            </s:if>
                            <s:else>
                                <a class="btn btn-danger"
                                   onclick='createImage(
                                           "<s:property value="regionId" default=""/>",
                                           "<s:property value="zoneId" default=""/>","<s:property value="snapshotId" default=""/>",
                                           "<s:property value="details.instanceId" default=""/>","<s:property value="details.systemDiskId" default=""/>",
                                           "<s:property value="userEmail" default=""/>"
                                           );'>发布为模板</a>
                            </s:else>
						</s:elseif>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 col-sm-12">
						<div class="row">
							<div class="col-md-12 col-sm-12">
								<div class="panel panel-default front-panel">
									<div class="panel-heading">基本信息</div>
									<div class="panel-body front-last-no-margin">
										<div class="left-card">
											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">主机ID</h5>
												</div>
												<div class="col-md-9 ">
													<h5 class="content-gutter">
														<s:property value="details.instanceId" />
													</h5>
												</div>
											</div>
											<div class="row">
												<div class="col-md-3 ">
													<h5 class="label-pos">供应商</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter">
														<s:property value="details.provider" />
                                                    </h5>
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">所在区</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter">
														<s:property value="details.regionId" />
													</h5>
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">主机名称</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter content-edit" id="instance_name">
														<s:property value="details.instanceName" />
														&nbsp;
													</h5>
													<a data-toggle="front-modal" data-title="修改云主机的名称"
														data-href="vm/preeditvm?yunType=vm&target=instance_name&appname=<s:property value="appname"/>&id=<s:property value="details.instanceId"/>&name=<s:property value="details.instanceName"/>&type=name&regionId=<s:property value="details.regionIdEn"/>">
														<span class="glyphicon glyphicon-edit"></span>
													</a>
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">主机状态</h5>
												</div>
												<div class="col-md-9">
													<s:if test="details.status == 'Running'">
														<img id="status_img" class="content-gutter"
															src='images/active.png' />
													</s:if>
													<s:elseif test="details.status == 'Stopped'">
														<img id="status_img" class="content-gutter"
															src='images/stopped.png' />
													</s:elseif>
													<s:else>
														<img id="status_img" class="content-gutter"
															src='images/<s:property value="details.status"/>.png' />
													</s:else>
													<h5 id="status_label" style="display: inline-block">
														<s:property value='statusMap[details.status]' />
													</h5>
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">主机描述</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter content-edit" id="instance_des">
														<s:property value="details.description" />
														&nbsp;
													</h5>
													<a data-toggle="front-modal" data-title="修改云主机的描述"
														data-href="vm/preeditvm?yunType=vm&target=instance_des&appname=<s:property value="appname"/>&id=<s:property value="details.instanceId"/>&name=<s:property value="details.description"/>&type=description&regionId=<s:property value="details.regionIdEn"/>">
														<span class="glyphicon glyphicon-edit"></span>
													</a>
												</div>
											</div>
											<s:if test="details.providerEn != #request.aliyun">
												<div class="row">
													<div class="col-md-3">
														<h5 class="label-pos">付费方式</h5>
													</div>
													<div class="col-md-9">
														<h5 class="content-gutter content-edit">
															<span id="payTypeCn"><s:property
																	value="details.payType" /></span> &nbsp;
														</h5>
														<a href="javascript:void(0)" onclick="modalshow()">(续费)</a>
													</div>
												</div>
											</s:if>


											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">创建时间</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter">
														<s:property value="details.creationTime" />
													</h5>
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">到期时间</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter">
														<s:property value="details.endTime" />
													</h5>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-12 col-sm-12">
								<div class="panel panel-default front-panel">
									<div class="panel-heading">配置信息</div>
									<div class="panel-body front-last-no-margin">
										<div class="left-card">
											<div class="row">
												<div class="col-md-3 ">
													<h5 class="label-pos">主机镜像</h5>
												</div>
												<div class="col-md-9 ">
													<h5 class="content-gutter"
														style="word-break: break-all; word-wrap: break-word">
														<s:if test='details.imageId==""'>
                                                        null
                                                    </s:if>
														<s:property default="null" value="details.imageId" />
													</h5>
												</div>
											</div>
											<div class="row">

												<div class="col-md-3">
													<h5 class="label-pos">操作系统</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter">
														<s:property value="details.osType" />
													</h5>
												</div>
											</div>

											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">主机规格</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter">
														<s:property value="details.instanceType" />
													</h5>
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">CPU</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter content-edit">
														<span id="cpuCount"><s:property value="details.vcpus" /></span>核&nbsp;
													</h5>
												</div>
											</div>
											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">内存</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter content-edit">
														<span id="moySize"><s:property
																value="details.memory" /></span>G&nbsp;
													</h5>
													<s:if test="details.providerEn != #request.aliyun">
														<a href="javascript:void(0)"
															onclick='sourcemdlOpen("cpumoy")'>(修改配置)</a>
													</s:if>
												</div>
											</div>

											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">带宽</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter content-edit">
														<span id="bandsize"><s:property
                                                                value="details.bandwidth"/></span> Mbps&nbsp;
                                                </h5>
                                                <s:if test="details.providerEn != #request.aliyun">
                                                    <a href="javascript:void(0)"
                                                       onclick='sourcemdlOpen("vmBand")'>(修改带宽)</a>
                                                </s:if>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3">
                                                <h5 class="label-pos">公网IP</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                    <s:property value="details.publicIpAddress"/>
                                                </h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3">
                                                <h5 class="label-pos">内网IP</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                    <s:property value="details.privateIpAddress"/>
                                                </h5>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <h5 class="label-pos">防火墙</h5>
                                                </div>
                                                <div class="col-md-9">
                                                    <h5 class="content-gutter" style="display: inline-block"><s:property
                                                            value="details.SecurityGroupName"></s:property></h5>
                                                    <a onclick="changeSecurityGroupId('<s:property
                                                            value="details.providerEn"/>','<s:property
                                                            value="details.instanceId"/>','<s:property
                                                            value="details.regionId"/>')"> <span
                                                            class="glyphicon glyphicon-edit"></span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 系统盘 -->
                        <div class="col-md-12 col-sm-12" id="system">
                            <div class="panel panel-default front-panel">
                                <div class="panel-heading">系统盘</div>
                                <div class="panel-body front-last-no-margin">
                                    <div class="left-card">
                                        <div class="row">
                                            <div class="col-md-3">
                                                <h5 class="label-pos">硬盘ID</h5>
                                            </div>
                                            <div class="col-md-9">
                                                <h5 class="content-gutter">
                                                    <a href='hd/hddetail?&userId=<s:property value="userId"/>&appName=<s:property value="appname"/>&diskId=<s:property value="details.systemDiskId"/>&provider=<s:property value="details.providerEn"/>&regionId=<s:property value="details.regionIdEn"/>&userEmail=<s:property value="details.userEmail"/>'>
                                                        <s:property value="details.systemDiskId"/>
                                                    </a>
                                                </h5>
                                            </div>
                                        </div>
                                        <s:if test="details.systemDiskName !='' ">
                                            <div class="row">
                                                <div class="col-md-3">
                                                    <h5 class="label-pos">硬盘名称</h5>
                                                </div>
                                                <div class="col-md-9">
                                                    <h5 class="content-gutter">
                                                        <s:property value="details.systemDiskName"/>
                                                    </h5>
                                                </div>
                                            </div>
                                        </s:if>

											<div class="row">
												<div class="col-md-3">
													<h5 class="label-pos">硬盘大小</h5>
												</div>
												<div class="col-md-9">
													<h5 class="content-gutter">
														<span id="sysDiskSize"><s:property value="details.systemDiskSize"/></span>G
													</h5>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- data数据盘，随主机一起卸载-->
							<s:iterator id="disk_inventory" value="details.diskList">
								<s:if
									test="#disk_inventory.diskType=='DATA' || #disk_inventory.diskType=='data'">
									<div class="col-md-12 col-sm-12">
										<div class="panel panel-default front-panel">
											<div class="panel-heading">数据盘(随系统盘卸载)</div>
											<div class="panel-body front-last-no-margin">
												<div class="left-card">
													<div class="row">
														<div class="col-md-3">
															<h5 class="label-pos">硬盘ID</h5>
														</div>
														<div class="col-md-9">
															<h5 class="content-gutter">
																<a href='hd/hddetail?&userId=<s:property value="userId"/>&appName=<s:property value="appname"/>&diskId=<s:property value="#disk_inventory.diskId"/>&provider=<s:property value="details.providerEn"/>&regionId=<s:property value="details.regionIdEn"/>&userEmail=<s:property value="details.userEmail"/>'><s:property
																		value="#disk_inventory.diskId" /></a>
															</h5>
														</div>
													</div>
													<s:if test="#disk_inventory.diskName!=''">
														<div class="row">
															<div class="col-md-3">
																<h5 class="label-pos">硬盘名称</h5>
															</div>
															<div class="col-md-9">
																<h5 class="content-gutter">
																	<s:property value="#disk_inventory.diskName" />
																</h5>
															</div>
														</div>
													</s:if>

													<div class="row">
														<div class="col-md-3">
															<h5 class="label-pos">硬盘大小</h5>
														</div>
														<div class="col-md-9">
															<h5 class="content-gutter">
																<span id="datadisksize"><s:property
																		value="#disk_inventory.diskSize" /></span>G
															</h5>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</s:if>
							</s:iterator>
							<!-- network数据盘 -->
							<s:iterator id="disk_inventory" value="details.diskList">
								<s:if test="#disk_inventory.diskType=='NETWORK'">
									<div class="col-md-12 col-sm-12">
										<div class="panel panel-default front-panel">
											<div class="panel-heading">数据盘</div>
											<div class="panel-body front-last-no-margin">
												<div class="left-card">
													<div class="row">
														<div class="col-md-3">
															<h5 class="label-pos">硬盘ID</h5>
														</div>
														<div class="col-md-9">
															<h5 class="content-gutter">
																<a href='hd/hddetail?&userId=<s:property value="userId"/>&appName=<s:property value="appname"/>&diskId=<s:property value="#disk_inventory.diskId"/>&provider=<s:property value="details.providerEn"/>&regionId=<s:property value="details.regionIdEn"/>&userEmail=<s:property value="details.userEmail"/>'>
																	<s:property value="#disk_inventory.diskId" />
																</a>
															</h5>
														</div>
													</div>
													<div class="row">
														<div class="col-md-3">
															<h5 class="label-pos">硬盘名称</h5>
														</div>
														<div class="col-md-9">
															<h5 class="content-gutter">
																<s:property value="#disk_inventory.diskName" />
															</h5>
														</div>
													</div>
													<div class="row">
														<div class="col-md-3">
															<h5 class="label-pos">硬盘大小</h5>
														</div>
														<div class="col-md-9">
															<h5 class="content-gutter">
																<s:property value="#disk_inventory.diskSize" />
																G
															</h5>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</s:if>
							</s:iterator>
						</div>
					</div>

					<div class="col-md-8 col-sm-12">
						<div class="row">
							<div class="col-md-12 col-sm-12">
								<div class="panel panel-default front-panel">
									<div class="panel-heading">监控-CPU(单位：%)</div>
									<div class="panel-body front-last-no-margin">
										<div id="cpu_highchart" style="height: 250px;"></div>
									</div>
								</div>
							</div>
							<s:if test="details.providerEn != #request.aliyun">
								<div class="col-md-12 col-sm-12">
									<div class="panel panel-default front-panel">
										<div class="panel-heading">监控-内存(单位：%)</div>
										<div class="panel-body front-last-no-margin">
											<div id="mem_highchart" style="height: 250px;"></div>
										</div>
									</div>
								</div>
							</s:if>
							<div class="col-md-12 col-sm-12">
								<div class="panel panel-default front-panel">
									<div class="panel-heading">监控-网络(单位：%)</div>
									<div class="panel-body front-last-no-margin">
										<div id="net_highchart" style="height: 250px;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<s:include value="../template/_footer.jsp" />
	</div>
	<s:include value="../hd/newshotmodal.jsp"></s:include>     <!-- 创建快照的模态框 -->
	<s:include value="modal.jsp" />    <!-- modal.jsp是续费的模态框-->
	<s:include value="../template/_global.jsp" />
	<s:include value="resource.jsp"></s:include>
	<script>
    var userEmail = "<s:property value="userEmail"/>"
    var instanceId = "<s:property value="details.instanceId"/>"
    var provider = "<s:property value="provider"/>"
    var regionId = "<s:property value="regionId"/>"
    var status = "<s:property value="details.status"/>"
    var providerEn = "<s:property value="details.providerEn"/>";
	var vdAppname = "<s:property value="appname"/>"  <!--action获取的appname-->
    var flag = false;
    var flag1 = false;
</script>
	<script src="js/highcharts.js"></script>    <!--- 绘制监控表格 -->
	<script src="js/vm/pricemodal.js"></script>    <!-- 续费的js -->
	<script src="js/vm/vmdetail.js"></script>		<!-- 主机上方tab的各种操作 -->
	<script src="js/createshot.js"></script>		<!-- 创建快照 -->
	<script src="js/alishot.js"></script>			<!-- 阿里云创建快照 -->
	<script src="js/vm/newimg.js"></script>        <!-- 镜像操作 -->
	<script src="js/vm/resource.js"></script>
</body>
</html>
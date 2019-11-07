<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Properties" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>

<head>
    <title>云海IaaS</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <s:include value="../template/_head.jsp"/>
</head>
<body class="front-body">
<s:include value="../template/_banner.jsp?menu=sum"/>

<div class="front-inner">
    <div class="container">
        <div class="row">
            <!-- 账户信息 -->
            <div class="col-md-12 as-carousel" style="padding-left: 0;margin: 0 15px">
                <div class="col-md-2" style="display: inline-block;padding-left: 0">
                    <div class="text-left"><label>云账户</label></div>
                </div>
            </div>
            <div id="accountInfo">
                 <s:include value="account.jsp"/>
            </div>
            <div class="col-md-4" style="cursor:pointer">
                    <a data-toggle="front-modal" data-title="添加新账户" data-href="sum/addNewAccount.jsp" data-size="modal-md">
                    <div class="panel panel-default front-panel">
                        <div class="panel-body" id="plusHome" >
                               <span class="glyphicon glyphicon-plus" ></span>
                        </div>
                    </div>
                    </a>
            </div>
            <div class="col-md-12 as-carousel" style="padding-left: 0;margin: 0 15px">
                <div class="col-md-2" style="display: inline-block;padding-left: 0">
                    <div class="text-left"><label>云资源</label><a id="all" href="javascript:void(0)" onclick="updateSum(this.id)" style="margin-left: 10px">刷新</a></div>
                </div>
            </div>
            <!--云主机-->
            <div class="col-md-4">
                <div class="panel panel-default front-panel">
                    <div class="panel-body">
                        <div class="media">
                            <div class="media-left">
                                <img class="media-object img-circle" src="images/iassvm.png" alt="App Logo">
                            </div>

                            <div class="media-body">
                                <%--<div id="instanceRef" style="height: 14px;width: 14px;float:right;display: none"></div>--%>
                                <a href="javascript:void(0)" id="instance"style="float:right" class="glyphicon glyphicon-refresh" onclick="updateSum(this.id)"></a>
                                <h4 class="media-heading">云主机</h4>
                                <div class="as-desc">
                                    <div style="display: inline-block">
                                        <label class="label_sum" id="instanceNum"><s:property value="instanceSum"/></label>
                                        <label>台云主机</label>
                                    </div>
                                    <div>
                                        <img src="images/active.png"/>
                                        <label>运行中</label>
                                        <label id="instanceAcNum"><s:property value="instanceAcSum"/></label>
                                        <%-- <img src="images/expired.png"/>
                                        <label>即将过期</label>
                                        <label><s:property value="instanceExpSum"/></label> --%>
                                    </div>
                                </div>
                            </div>
                            <div style="float: left">
                                <span id="instanceRefTime"><s:property value="instanceRefTime"/></span>
                            </div>
                            <div class="text-right">
                                <a data-toggle="front-modal" class="sum_span_blank" data-title="创建云主机" data-href="sum/appnamefornew.jsp?type=vm" data-size="modal-md">
                                    <span class="glyphicon glyphicon-plus-sign"></span> 新建</a>
                                <a href="vm/vmlist.jsp" ><span class="glyphicon glyphicon-cog"></span> 管理</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--云硬盘-->
            <div class="col-md-4">
                <div class="panel panel-default front-panel">
                    <div class="panel-body">
                        <div class="media">
                            <div class="media-left">
                                <img class="media-object img-circle" src="images/iasshd.png" alt="App Logo">
                            </div>
                            <div class="media-body">
                                <div id="diskRef" style="height: 14px;width: 14px;float:right;display: none"></div>
                                <a href="javascript:void(0)" id="disk"style="float:right" class="glyphicon glyphicon-refresh" onclick="updateSum(this.id)"></a>                                                       
                                <h4 class="media-heading">云硬盘</h4>
                                <div class="as-desc">
                                    <div style="display: inline-block">
                                        <label class="label_sum" id="diskNum"><s:property value="diskSum"/></label>
                                        <label>块硬盘</label>
                                    </div>
                                    <div>
                                        <img src="images/active.png"/>
                                        <label>使用中</label>
                                        <label id="diskAcNum"><s:property value="diskAcSum"/></label>
                                       <%--  <img src="images/expired.png"/>
                                        <label>即将过期</label>
                                        <label><s:property value="diskExpSum"/></label> --%>
                                    </div>
                                </div>
                            </div>
                            <div style="float: left">
                                <span id="diskRefTime"><s:property value="diskRefTime"/></span>
                            </div>
                            <div class="text-right">
                                <a data-toggle="front-modal" class="sum_span_blank" data-title="创建云硬盘" data-href="sum/appnamefornew.jsp?type=hd" data-size="modal-md">
                                    <span class="glyphicon glyphicon-plus-sign"></span> 新建</a>
                                <a href="hd/hd_list.jsp" ><span class="glyphicon glyphicon-cog"></span> 管理</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!--快照-->
            <div class="col-md-4">
                <div class="panel panel-default front-panel">
                    <div class="panel-body">
                        <div class="media">
                            <div class="media-left">
                                <img class="media-object img-circle" src="images/iassshot.png" alt="App Logo">
                            </div>
                            <div class="media-body">
                                <div id="shotRef" style="height: 14px;width: 14px;float:right;display: none"></div>
                                <a href="javascript:void(0)" id="shot"style="float:right" class="glyphicon glyphicon-refresh" onclick="updateSum(this.id)"></a>
                                <h4 class="media-heading">云快照</h4>
                                <div class="as-desc">
                                    <div style="display: inline-block">
                                        <label class="label_sum" id="shotNum"><s:property value="shotSum"/></label>
                                        <label>份快照</label>
                                    </div>
                                </div>
                            </div>

                            <div style="float: left">
                                <span id="snapshotRefTime"><s:property value="snapshotRefTime"/></span>
                            </div>
                            <div class="text-right">
                                <%--<a href="javascript:void(0)" class="sum_span_blank" style="margin-left: 10px"><span class="glyphicon glyphicon-plus-sign"></span> 新建</a>--%>
                                <a href="shot/shotsList.jsp?menu=shot" ><span class="glyphicon glyphicon-cog"></span> 管理</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!--镜像-->
            <div class="col-md-4">
                <div class="panel panel-default front-panel">
                    <div class="panel-body">
                        <div class="media">
                            <div class="media-left">
                                <img class="media-object img-circle" src="images/iassbranch.png" alt="App Logo">
                            </div>
                            <div class="media-body">
                                <div id="imageRef" style="height: 14px;width: 14px;float:right;display: none"></div>
                                <a href="javascript:void(0)" id="image"style="float:right" class="glyphicon glyphicon-refresh" onclick="updateSum(this.id)"></a>
                                <h4 class="media-heading" >云镜像</h4>
                                <div class="as-desc">
                                    <div style="display: inline-block">
                                        <label class="label_sum" id="backupNum"><s:property value="backupSum"/></label>
                                        <label>块镜像</label>
                                    </div>
                                </div>
                            </div>
                            <div style="float: left">
                                <span id="imageRefTime"><s:property value="imageRefTime"/></span>
                            </div>
                            <div class="text-right">
                                <%--<a href="javascript:void(0)" class="sum_span_blank" style="margin-left: 10px"><span class="glyphicon glyphicon-plus-sign"></span> 新建</a>--%>
                                <a href="image/imagesList.jsp"><span class="glyphicon glyphicon-cog"></span> 管理</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--防火墙-->
            <div class="col-md-4 hide">
                <div class="panel panel-default front-panel">
                    <div class="panel-body">
                        <div class="media">
                            <div class="media-left">
                                <img class="media-object img-circle" src="images/iaassecurity.png" alt="App Logo">
                            </div>
                            <div class="media-body">
                                <div id="securityRef" style="height: 14px;width: 14px;float:right;display: none"></div>
                                <a href="javascript:void(0)" id="security"style="float:right" class="glyphicon glyphicon-refresh" onclick="updateSum(this.id)"></a>
                                <h4 class="media-heading" >云安全</h4>
                                <div class="as-desc">
                                    <div style="display: inline-block">
                                        <label class="label_sum" id="securityGroupNum"><s:property value="securityGroupSum"/></label>
                                        <label>个规则</label>
                                    </div>
                                </div>
                            </div>
                            <div style="float: left">
                                <span id="securityGroupRefTime"><s:property value="securityGroupRefTime"/></span>
                            </div>
                            <div class="text-right">
                                <a href="javascript:void(0)" class="sum_span_blank" style="margin-left: 10px"><span class="glyphicon glyphicon-plus-sign"></span> 新建</a>
                                <a href="fw/fwlist.jsp"><span class="glyphicon glyphicon-cog"></span> 管理</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--<!--账户-->
            <div class="col-md-4">
                <div class="panel panel-default front-panel">
                    <div class="panel-body">
                        <div class="media">
                            <div class="media-left">
                                <img class="media-object img-circle" src="images/user.png" alt="App Logo">
                            </div>
                            <div class="media-body">
                                <div class="text-left"><label style="font-size: 18px;height:19px">账户</label></div>
                                <div class="as-desc">
                                    <div style="display: inline-block">
                                        <label class="label_sum"><s:property value="balance"/></label>
                                        <label>元账户余额</label>
                                    </div>
                                </div>
                                <div class="text-right">
                                    <a href="javascript:void(0)" class="sum_span_blank" style="margin-left: 10px"><span class="glyphicon glyphicon-plus-sign"></span> 充值</a>
                                    <a href="javascript:void(0)" target="_blank"><span class="glyphicon glyphicon-cog"></span> 管理</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>--%>



        </div>


    </div>


    <!--底部标签-->
    <s:include value="../template/_footer.jsp"/>
</div>
</body>
<s:include value="../template/_global.jsp"/>

<script src="js/jquery.shCircleLoader.js"></script>
<script src="js/jquery.shCircleLoader-min.js"></script>
<script src="js/sum/updatesum.js"></script>

</html>

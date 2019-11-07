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
                <a href="#" data-toggle="front-modal" class="pull-right sum_span_blank" style="padding-right: 15px"
                   data-href="account/newAppcloudAccountModal" data-size="modal-md"><span class="glyphicon glyphicon-plus-sign"></span> 新建云账户</a>
            </div>
            <div id="accountInfo">
                 <s:include value="account.jsp"/>
            </div>
            <div class="col-md-4" style="cursor:pointer">
                    <a data-toggle="front-modal" data-title="绑定云账户" data-href="sum/addNewAccount.jsp" data-size="modal-md">
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
                                <%--<a data-toggle="front-modal" class="sum_span_blank" data-title="创建云主机" data-href="sum/appnamefornew.jsp?type=vm" data-size="modal-md">
                                    <span class="glyphicon glyphicon-plus-sign"></span> 新建</a>--%>
                                <a href="vm/newInstancePage" ><span class="glyphicon glyphicon-plus-sign"></span> 新建</a>
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
                                <%--<a data-toggle="front-modal" class="sum_span_blank" data-title="创建云硬盘" data-href="sum/appnamefornew.jsp?type=hd" data-size="modal-md">
                                    <span class="glyphicon glyphicon-plus-sign"></span> 新建</a>--%>
                                <a href="hd/newhd.jsp?menu=newhd" ><span class="glyphicon glyphicon-plus-sign"></span> 新建</a>
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
                                <a href="javascript:void(0)" class="sum_span_blank" style="margin-left: 10px"><span class="glyphicon glyphicon-plus-sign"></span> 新建</a>
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
                                <a href="javascript:void(0)" class="sum_span_blank" style="margin-left: 10px"><span class="glyphicon glyphicon-plus-sign"></span> 新建</a>
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
            <!--账户-->
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
            </div>

        <!--信息标签-->
            <div class="col-md-12 as-carousel" style="padding-left: 0;margin: 0 15px">
                <div class="col-md-2" style="display: inline-block;padding-left: 0">
                    <div class="text-left">
                        <label>操作记录</label>
                        <a href="javascript:void(0)" onclick="exseachLog()" style="margin-left: 10px">搜索</a>
                    </div>
                </div>
            </div>

        </div>

        <!--用户操作表格-->
        <div class="row">
            <!-- 自定义查询 -->
            <div id="search_log" class="hidden panel  panel-default front-panel clear form-horizontal" style="margin-left: 15px;margin-right: 15px">
                <div class="panel-heading">自定义查询</div>
                <div class="panel-body" style="margin-bottom: 0px">
                    <div class="form-group">
                        <label class="col-md-2 control-label">时间选择</label>
                        <div class="col-md-5">
                            <input type="datetime-local" class="form-control" id="begin-date" placeholder="起始时间" style="width: 210px">
                        </div>
                        <div class="col-md-5">
                            <input type="datetime-local" class="form-control" id="end-date" placeholder="终止时间" style="width: 210px">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">查找顺序</label>
                        <div class="col-md-10">
                            <label class="radio-inline">
                                <input type="radio" name="timeseq"  value="yes"/>顺序
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="timeseq" checked="true" value="no">倒序
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label" for="size">日志条目</label>
                        <div class="col-md-5">
                            <input class="form-control" type="text" id="size" value="10">
                        </div>
                        <div class="col-md-5">
                            <a class="hidden btn btn-primary" href="javascript:void(0)" id="fields-filter-display">显示字段过滤输入框</a>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label" for="appname">账户名称</label>
                        <div class="col-md-5">
                            <select class="form-control" id="appname">
                                <option class="form-control">全部</option>
                                <s:iterator id="appkey" value="appkeyList">
                                    <option><s:property value="#appkey.appname"></s:property></option>
                                </s:iterator>
                            </select>
                        </div>
                    </div>

                    <div id="fields-filter-form" style="display:none">
                        <!--这里是字段过滤器表单-->
                        <%--<s:iterator id="f" value="fields">--%>
                            <%--<div class="form-group">--%>
                                <%--<div class="query-fields">--%>
                                    <%--<label class="qk col-md-2 control-label " id="<s:property value="#f.id"/>"><s:property value="#f.fieldName"/></label>--%>
                                    <%--<div class="col-md-10"><input class="form-control qv" type="text" name="<s:property value="#f.fieldName"/>"></div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</s:iterator>--%>
                    </div>

                    <div class="form-group" style="margin-right: 0">
                        <button type="submit" id="search_log_btn" class="btn btn-primary pull-right">
                            搜索
                        </button>
                    </div>
                </div>
            </div>

            <!-- 日志表格 -->
            <div class="panel panel-default front-panel" style="margin-left: 15px;margin-right: 15px">
                <div class="panel-body front-no-padding">
                    <!-- 也可以用table-strip -->
                    <table class="table table-striped front-table"
                           style="margin-bottom: 0px;font-size: 15px" id="newiaaslog">
                        <thead style="vertical-align: bottom;
                                border-bottom: 2px solid #ddd;">
                            <th style="width: 250px;">时间</th>
                            <th style="width: 100px;">类别</th>
                            <th>内容</th>
                        </thead>
                        <!-- 日志内容 -->
                    </table>
                </div>
            </div>
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

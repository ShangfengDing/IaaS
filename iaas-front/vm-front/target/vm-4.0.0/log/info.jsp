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
<s:include value="../template/_banner.jsp?menu=info"/>

<div class="front-inner">
    <div class="container">
        <%--<div class="row">--%>


            <%--<!--信息标签-->--%>
            <%--<div class="col-md-12 as-carousel" style="padding-left: 0;margin: 0 15px">--%>
                <%--<div class="col-md-2" style="display: inline-block;padding-left: 0">--%>
                    <%--<div class="text-left">--%>
                        <%--<label>操作记录</label>--%>
                        <%--<a href="javascript:void(0)" onclick="exseachLog()" style="margin-left: 10px">搜索</a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>

        <%--</div>--%>

        <!--用户操作表格-->
        <div class="row">
            <!-- 自定义查询 -->
            <div id="search_log" class="panel  panel-default front-panel clear form-horizontal" style="margin-left: 15px;margin-right: 15px">
                <div class="panel-heading"><b>日志查询</b></div>
                <div class="panel-body" style="margin-bottom: 0px">
                    <div class="form-group">
                        <label class="col-md-2 control-label">起止时间</label>
                        <div class="col-md-2" style="width:233px">
                            <input type="datetime-local" class="form-control" id="begin-date" placeholder="起始时间" style="width: 210px">

                        </div>
                        <div class="col-md-2" style="width:226px">
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
                            <input class="form-control" type="text" id="size" value="100">
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

                    <div class="form-group" style="margin-right: 0;padding-bottom: 0px;margin-bottom: 0px;">
                        <button type="submit" id="search_log_btn" class="btn btn-primary pull-right">
                            查询
                        </button>
                    </div>
                </div>
            </div>

            <div id="logdetail"></div>
            <!-- 日志表格 -->
            <%--<div class="panel panel-default front-panel" style="margin-left: 15px;margin-right: 15px">--%>
                <%--<div class="panel-body front-no-padding">--%>
                    <%--<!-- 也可以用table-strip -->--%>
                    <%--<table class="table table-striped front-table"--%>
                           <%--style="margin-bottom: 0px;font-size: 15px" id="newiaaslog">--%>
                        <%--<thead style="vertical-align: bottom;--%>
                                <%--border-bottom: 2px solid #ddd;">--%>
                        <%--<th style="width: 250px;">时间</th>--%>
                        <%--<th style="width: 100px;">类别</th>--%>
                        <%--<th>内容</th>--%>
                        <%--</thead>--%>
                        <%--<!-- 日志内容 -->--%>
                    <%--</table>--%>
                <%--</div>--%>
            <%--</div>--%>
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

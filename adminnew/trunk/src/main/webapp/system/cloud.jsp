<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 2018/5/21
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>分布式云 - 云海IaaS</title>
    <style type="text/css">
        .datatable td {
            text-align: center;
        }
    </style>
</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=system"/>
<div class="front-inner front-inner-media">
    <div class="container">
        <div class="col-md-12 form-group">
            <div class="front-resource-info clearfix" style="margin-top: 2%;margin-bottom: 13px">
                <div class="pull-left" style="margin-top:9px;font-size:15px"><span
                        style="font-size:15px;font-weight:bold"><strong>分布式云集群管理</strong></span></div>

                <div class="front-toolbar other my-toolbar" style="padding-bottom: 0">
                    <div class="front-toolbar-header">
                        <button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse"
                                data-target="#freeshare-resource">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div id="freeshare-resource" class="front-btn-group collapse pull-right" data-toggle="buttons">
                        <label class="btn btn-default front-no-box-shadow active" onclick="reloadAll(1)">
                            <input id="top50" type="radio" name="options" autocomplete="off">全部
                        </label>
                        <label class="btn btn-default front-no-box-shadow" onclick="reloadRunning(1)">
                            <input id="top100" type="radio" name="options" autocomplete="off">运行中
                        </label>
                        <label class="btn btn-default front-no-box-shadow" onclick="reloadStopped(1)">
                            <input id="top500" type="radio" name="options" autocomplete="off">已停止
                        </label>
                    </div>
                </div>

                <%--<div class="pull-right">--%>
                <%--<a type="button" class="btn btn-primary" data-toggle="front-modal"  data-size="modal-lg" data-href="system/prenewcluster?zoneId=<s:property value="zid"/>">新建集群</a></div>--%>
                <%--<a  data-toggle="front-modal"  data-size="modal-lg" data-href="system/clusternewnet.jsp?id=<s:property value='aggregate.id'/>" title="新建" > 新建</a>--%>

                <%--<a class="button rightfloat" rel="facebox" href="system/prenewcluster?zoneId=<s:property value="zid"/>" title="新建集群" size="s">新建集群</a>--%>
            </div>
            <div class="panel panel-default font-panel">
                <h3 id="none-cloud" class="centeralign" style="display: none">抱歉没有分布式云信息</h3>
                <div  id="cloud-table" style="display: none">

                    <table class="table table-striped multi-table" style="margin-bottom: 0px">
                        <thead>
                        <tr>
                            <%--<th>uuid</th>--%>
                            <th>名称</th>
                            <th>OpenAPI-Server</th>
                            <th>平台地址</th>
                            <th>角色</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>

                    </table>
                    <div class="lineheight" id="page">
                        <div class="text-center">
                            <ul class="pagination">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"/>
</body>
<script type="text/javascript">
    $(function () {
        reloadAll(1);
    });

    function reloadAll(page) {
        reload(page, 0);
    }

    function reloadRunning(page) {
        reload(page, 1)
    }

    function reloadStopped(page) {
        reload(page, 2)
    }

    function reload(page, type) {
        $.post(
            "system/cloudInfo",
            {
                page: page,
                size: 10,
                type: type
            },
            function (data) {
                if (data.totalPages === 0) {
                    $("#none-cloud").show();
                    $("#cloud-table").hide();
                } else {
                    $("#cloud-table").show();
                    $("#none-cloud").hide();
                    switch (type) {
                        case 0:
                            $("#page").html($.getDivPageHtml(data.page + 1, data.totalPages, "reloadAll"));
                            break;
                        case 1:
                            $("#page").html($.getDivPageHtml(data.page + 1, data.totalPages, "reloadRunning"));
                            break;
                        case 2:
                            $("#page").html($.getDivPageHtml(data.page + 1, data.totalPages, "reloadStopped"));
                            break;
                    }
                    load(data);
                }

            }
        )
    }

    function load(data) {
        var table = "";
        var tbody = $("tbody");
        tbody.empty();
        for (var i in data.aliveCloudInfoList) {
            table = "<tr>" +
                // "<td>" + data.aliveCloudInfoList[i].uuid + "</td>" +
                "<td>" + data.aliveCloudInfoList[i].dataCenter + "</td>" +
                "<td>" + data.aliveCloudInfoList[i].domainName + "</td>" +
                "<td>" + data.aliveCloudInfoList[i].addr + "</td>" +
                "<td>" + data.aliveCloudInfoList[i].role + "</td>" +
                "<td>运行中</td>" +
                "<td><a href='javascript:return false;' onclick='return false;' style='opacity: 0.2;cursor: default;'>删除</a></td>" +
                "<tr>";
            tbody.append(table);
        }
        for (var i in data.deadCloudInfoList) {
            table = "<tr>" +
                // "<td>" + data.deadCloudInfoList[i].uuid + "</td>" +
                "<td>" + data.deadCloudInfoList[i].dataCenter + "</td>" +
                "<td>" + data.deadCloudInfoList[i].domainName + "</td>" +
                "<td>" + data.deadCloudInfoList[i].addr + "</td>" +
                "<td>" + data.deadCloudInfoList[i].role + "</td>" +
                "<td>已停止</td>" +
                "<td><a href='javascript:void(0)' onclick='remove(\"" + data.deadCloudInfoList[i].uuid + "\")'>删除</a></td>" +
                "<tr>";
            tbody.append(table);
        }
    }

    function remove(uuid) {
        $.tipModal('confirm', 'warning', '确定删除停止的云信息？', function (result) {
            if (result) {
                $.post(
                    "system/removeDeadCloud",
                    {uuid: uuid},
                    function (data) {

                        load(data);
                    }
                )
            }
        });

    }


</script>

</html>

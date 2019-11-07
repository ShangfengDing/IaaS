<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>网络管理 - 云海IaaS</title>

</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=system"/>
<div class="front-inner front-inner-media">

    <div class="container">
        <div class="col-md-12 form-group">
        <input type="hidden" id="zid" value='<s:property value="zid"/>'/>
        <input type="hidden" id="zname" value='<s:property value="zname"/>'/>
        <div class="front-resource-info clearfix" style="margin-top: 2%;margin-bottom: 10px">
            <div>
                <ol class="breadcrumb">
                    <li>系统管理</li>
                    <li class="active">网络管理</li>
                </ol>
            </div>
        </div>
        <span class="pull-right" style="padding-bottom: 5px"><a type="button" class="btn btn-primary" title="新建IP池" rel="facebox"
                                                                href="net/prenewnet?zoneId=<s:property value="zid"/>">新建IP池</a></span>
        <s:if test='addressPools.size() == 0'>
            <label class="col-lg-1 col-md-2 control-label ">还没有网络管理</label>

        </s:if>
        <s:else>
        <s:iterator value="addrPoolByCluster" id="addrPoolList">
        <div class="front-resource-info clearfix" style="margin-top: 2%">

                <span class="col-lg-1 col-md-2 " style="padding-left:0px"><s:property value="aggregateMap[key].name"/></span>
                <span class="col-lg-3 col-md-2">
                    <span style="margin-right:5%">外网IP总量：&nbsp;<s:property
                            value="ipNumMap[key]['totalPublic']"/></span>
                    <span >剩余量：&nbsp;<s:property value="ipNumMap[key]['public']"/></span>
                </span>
                <span class="col-lg-3 col-md-2 control-label">
                    <span class=" control-label" style="margin-right:7%">内网IP总量：&nbsp;<s:property
                            value="ipNumMap[key]['totalPrivate']"/></span>
                    <span class=" control-label">剩余量：&nbsp;<s:property value="ipNumMap[key]['private']"/></span>
                </span>
        </div>
     <!--   <div class="panel panel-default front-panel" style="margin-top:5px">  -->
            <div class="panel panel-default front-panel" style="margin-top: 10px">
                <div class="panel-body front-no-padding">

                    <table class="table table-striped front-table"
                           style="margin-bottom: 0px">
                <thead>
                <tr>
                    <th style="width:11%">类型</th>
                    <th style="width:28%">IP段</th>
                    <th>子网掩码</th>
                    <th style="width:14%">网关</th>
                    <th style="width:14%">DNS</th>
                    <th style="width:15%">操作</th>
                </tr>
                </thead>
                <s:iterator id="addrpool" value="value">
                    <s:if test="#addrpool.aggregateId >=0">
                        <tr>
                                <%-- <td><s:property value="aggregateMap[#addrpool.aggregateId].name"/></td>
                                <td><s:property value="aggregateMap[#addrpool.aggregateId].availabilityZoneName"/></td> --%>
                            <td><s:property value="addrTypeMap[#addrpool.type]"/></td>
                            <td><s:property value="#addrpool.ipStart"/>&nbsp;-&nbsp;<s:property
                                    value="#addrpool.ipEnd"/></td>
                            <td><s:property value="#addrpool.netmask"/></td>
                            <td><s:property value="#addrpool.gateway"/></td>
                            <td><s:property value="#addrpool.dns"/></td>
                            <td>
                                <a class="blueletter" href="javascript:void(0)"
                                   onclick="showDetail(this,'<s:property value="#addrpool.id"/>')">详情</a>
                                <a class="blueletter leftmargin_10" href="javascript:void(0)"
                                   onclick="delNet(this,'<s:property value="#addrpool.id"/>')">删除</a>
                            </td>
                        </tr>
                    </s:if>
                </s:iterator>
            </table>
                </div>
            </div>
        </s:iterator>
            </s:else>
    <!--    </div>  -->
                </div>
           <%--</div><!--inner-->--%>
    </div><!--container-->
    <s:include value="/template/_footer.jsp"></s:include>
</div><!--front inner-->
</body>

<script>
    //显示集群IP详情
    function showDetail(obj, poolId) {
        $.post("net/netdetail", {poolId: poolId}, function (data) {
            $(obj).html("收起");
            $(obj).attr("onclick", "hideDetail(this,'" + poolId + "')");
            var detailTable =
                "<tr class='yellowbox padding10'><td></td>" +
                "<td colspan='7'>";
            if (data.addressPool.ips == null || data.addressPool.ips.length == 0) {
                detailTable += "该网段的ip地址还没有被使用过</td></tr>";
            } else {
                var ipStart = data.addressPool.ipStart;
                var ipEnd = data.addressPool.ipEnd;
                var allCount = ipEnd.slice(ipEnd.lastIndexOf('.') + 1) -
                    ipStart.slice(ipStart.lastIndexOf('.') + 1) + 1;
                var usedCount = 0;
                $.each(data.addressPool.ips, function (i, ip) {
                    usedCount = usedCount + 1;
                });

                detailTable +=
                    " <div style=\"margin-top:1%;margin-bottom:1%\"><label class=\"control-label front-label\">IP总量：" + allCount + " </label>" + "<label class=\" control-label front-label\" style=\" margin-left: 12%\">剩余IP数量：" + (allCount - usedCount) + "</label></div>" +
                    "<div class=\"panel panel-default front-panel\" >\n" +
                    "            <table class=\"table table-striped multi-table\">\n" +
                    "                <thread>";

                // "<h3>IP总量：" + allCount + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "剩余IP数量：" + (allCount - usedCount) + "</h3>" +
                    // "<table border='1'>";

                detailTable += "<tr><th>IP地址</th><th>状态</th><th>MAC地址</th><th>虚拟机</th></tr>";
                $.each(data.addressPool.ips, function (i, ip) {
                    detailTable +=
                        "<tr>" +
                        "<td>" + ip.ipAddress + "</td>" +
                        "<td>" + ip.status + "</td>" +
                        "<td>" + ip.macAddress + "</td>" +
                        "<td>" + ip.serverName + "</td>" +
                        "</tr>";
                });

                detailTable += "</table></td></tr>";
            }
            $(obj).parent().parent().after(detailTable);
        });
    }

    //隐藏集群IP详情
    function hideDetail(obj, poolId) {
        $(obj).html("详情");
        $(obj).attr("onclick", "showDetail(this,'" + poolId + "')");
        var nextTr = $(obj).parent().parent().next();
        if (nextTr.attr("class").indexOf("yellowbox") >= 0) {
            nextTr.remove();
        }
    }

    //删除ip段
    function delNet(obj, poolId) {
        if (confirm("确认删除该IP段吗？")) {
            $.post("net/delnet", {poolId: poolId}, function (data) {
                if (data.result == "success") {
                    fillTipBox("success", "删除成功");
                    location.reload();
                } else {
                    fillTipBox("error", "该IP段已被使用，无法删除！");
                }
            });
        }
    }

</script>
</html>
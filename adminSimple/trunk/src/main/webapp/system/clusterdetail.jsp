<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.*, appcloud.admin.common.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>集群详情 - 云海IaaS</title>
    <%--<style type="text/css">--%>
        <%--.modal > div { /* 追加此行 */--%>
            <%--display: table;--%>
            <%--width: 100%;--%>
            <%--height: 100%;--%>
        <%--}--%>

        <%--.modal-dialog {--%>
            <%--/* 略去展示原有内容，此处只显示追加内容 */--%>
            <%--width: 400px;--%>
            <%--display: table-cell;--%>
            <%--vertical-align: middle;--%>
            <%--margin: 0px 20px;--%>
        <%--}--%>

        <%--.modal-content {--%>
            <%--/* 略去展示原有内容，此处只显示追加内容 */--%>
            <%--display: block;--%>
            <%--width: 400px;--%>
            <%--margin: 0 auto;--%>
            <%--margin-top: 10%;--%>
        <%--}--%>
    <%--</style>--%>

</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=system"/>
<div class="front-inner ">
    <div class="container">
        <div class="col-md-12">
        <%--<div class="clearfix" style="margin-top: 2%;font-size:15px">--%>
        <%--<label class="control-label front-label"><a href="system/cluster?zid=1"  >集群管理</a></label> &nbsp;&gt;&gt;&nbsp;--%>
        <%--<label class=" control-label front-label" >集群详情&nbsp;(&nbsp;<s:property--%>
        <%--value="aggregate.name"/>&nbsp;)</label>--%>
        <%--</div>--%>
        <div>
            <ol class="breadcrumb">
                <li><a href="system/cluster?zid=1">集群管理</a></li>
                <li class="active">集群详情&nbsp;(&nbsp;<s:property value="aggregate.name"/>&nbsp;)</li>
            </ol>
        </div>
        <div style="margin-top:2%; height: 33px;">
            <div class="panel-body front-no-padding" style="height:33px;">
                <label class="col-md-2 front-no-padding" style=" padding-left: 8px; line-height: 33px; margin: 0px;">基本信息</label>
            </div>
        </div>
        <div class="panel panel-default front-panel" style="margin-top: 10px">
            <div class="panel-body front-no-padding">
               <table class="table table-striped front-table col-md-12" style="margin-bottom: 0px;">
                <thead>
                <tr>
                    <th class="col-md-5">
                        <div>集群名称</div>
                    </th>
                    <th class="col-md-2">
                        <div>CPU超售</div>
                    </th>
                    <th class="col-md-2">
                        <div>内存超售</div>
                    </th>
                    <th class="col-md-2">
                        <div>硬盘超售</div>
                    </th>
                    <th class="col-md-1">
                        <div style="text-align: center;">操作</div>
                    </th>
                </tr>
                </thead>
                <tr>
                    <td>
                        <div><s:property value="aggregate.name"/></div>
                    </td>
                    <td>
                        <div><s:property value="cpu_sell_rate"/>%</div>
                    </td>
                    <td>
                        <div><s:property value='aggregate.memory_oversell'/>%</div>
                    </td>
                    <td>
                        <div><s:property value="disk_sell_rate"/>%</div>
                    </td>
                    <td style="text-align: center;">
                        <div>
                            <a class="blueletter" data-toggle="front-modal" data-size="modal-lg"
                               data-href="system/setoversell.jsp?id=<s:property value='aggregate.id'/>" title="设置" rel="facebox" size="s">设置</a>
                        </div>
                    </td>
                </tr>
            </table>
            </div>
        </div>

        <div style="margin-top:3%; height: 33px;">
            <label class="col-md-2 front-no-padding" style=" padding-left: 8px; line-height: 33px; margin: 0px;">资源调度</label>
            <a class="btn btn-primary pull-right" style="margin: 0px;" data-toggle="front-modal" data-size="modal-lg"
               data-href="system/prechangers?id=<s:property value='aggregate.id'/>" title="设置" rel="facebox" size="s">设置</a>
        </div>
        <div class="panel panel-default front-panel" style="margin-top: 10px">
            <div class="panel-body front-no-padding">
                <table class="table table-striped multi-table col-md-12" style="margin-bottom: 0px;">
                <thead>
                <tr>
                    <th class="col-md-5">
                        <div>调度类型</div>
                    </th>
                    <th class="col-md-2">
                        <div>算法名称</div>
                    </th>
                    <th class="col-md-2">
                        <div>算法描述</div>
                    </th>
                    <th class="col-md-2">
                        <div>算法参数</div>
                    </th>
                    <th class="col-md-1"></th>
                </tr>
                </thead>
                <s:iterator id="list" value="curRSList">
                    <tr>
                        <td>
                            <div><s:property value="#list.type"/></div>
                        </td>
                        <td>
                            <div><s:property value="#list.name"/></div>
                        </td>
                        <td>
                            <div><s:property value="#list.description"/></div>
                        </td>
                        <td>
                            <div><s:property value="#list.params"/></div>
                        </td>
                        <td></td>
                    </tr>
                </s:iterator>
                </table>
            </div>
        </div>

        <div style="margin-top:3%; height: 33px;">
            <label class="col-md-2 front-no-padding" style=" padding-left: 8px; line-height: 33px; margin: 0px;">资源占用</label>
        </div>
        <div class="panel panel-default front-panel" style="margin-top: 10px">
            <div class="panel-body front-no-padding">
                <table class="table table-striped multi-table col-md-13" style="margin-bottom: 0px">
                <thead>
                <tr>
                    <th class="col-md-5">
                        <div></div>
                    </th>
                    <th class="col-md-2">
                        <div>CPU</div>
                    </th>
                    <th class="col-md-2">
                        <div>内存/MB</div>
                    </th>
                    <th class="col-md-2">
                        <div>硬盘/GB</div>
                    </th>
                    <th class="col-md-1">
                    </th>
                </tr>
                </thead>
                <tr>
                    <td>硬件配置</td>
                    <td><s:property value="cpu_conf"/></td>
                    <td><s:property value="memory_conf"/></td>
                    <td><s:property value="disk_conf"/></td>
                    <td></td>
                <tr>
                    <%--<tr>--%>
                    <%--<td>超售比例(%)</td>--%>
                    <%--<td><s:property value='aggregate.cpu_oversell'/>%</td>--%>
                    <%--<td><s:property value='aggregate.memory_oversell'/>%</td>--%>
                    <%--<td><s:property value='aggregate.disk_oversell'/>%</td>--%>
                    <%--</tr>--%>
                <tr>
                    <td>总量</td>
                    <td><s:property value="cpu_all"/></td>
                    <td><s:property value="memory_all"/></td>
                    <td><s:property value="disk_all"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td>已用</td>
                    <td><s:property value="cpu_sell"/></td>
                    <td><s:property value="memory_sell"/></td>
                    <td><s:property value="disk_sell"/></td>
                    <td></td>
                </tr>
                <tr>
                    <td>销售比例(%)</td>
                    <td><s:property value="cpu_sell_rate"/>%</td>
                    <td><s:property value="memory_sell_rate"/>%</td>
                    <td><s:property value="disk_sell_rate"/>%</td>
                    <td></td>
                </tr>
                </table>
            </div>
        </div>


        <div style="margin-top:3%; height: 33px;">
            <label class="front-no-padding" style=" padding-left: 8px; line-height: 33px; margin: 0px;">网络资源
                <s:iterator value="addrPoolByCluster" id="addrPoolList">
                <span style="font-weight: lighter">（&nbsp;外网IP总量：<s:property value="ipNumMap[key]['totalPublic']"/>
                    &nbsp;&nbsp; 剩余量：<s:property value="ipNumMap[key]['public']"/>
                </span>
                <span style="font-weight: lighter">&nbsp;&nbsp;&nbsp;&nbsp;剩内网IP总量：<s:property value="ipNumMap[key]['totalPrivate']"/>
                    &nbsp;&nbsp;剩余量：<s:property value="ipNumMap[key]['private']"/>&nbsp;）
                </span>
                </s:iterator>
            </label>
            <a class="btn btn-primary pull-right" style="margin: 0px" data-toggle="front-modal"
               data-size="modal-lg" data-href="system/clusternewnet.jsp?id=<s:property value='aggregate.id'/>" title="新建">新建</a>
        </div>
        <div class="panel panel-default front-panel" style="margin-top: 10px">
            <div class="panel-body front-no-padding">
                <table class="table table-striped multi-table col-md-12" style="margin-bottom: 0px">
                    <thead>
                        <tr>
                            <th class="col-md-1">
                                <div>类型</div>
                            </th>
                            <th class="col-md-4">
                                <div>IP段</div>
                            </th>
                            <th class="col-md-2">
                                <div>子网掩码</div>
                            </th>
                            <th class="col-md-2">
                                <div>网关</div>
                            </th>
                            <th class="col-md-2">
                                <div>DNS</div>
                            </th>
                            <th class="col-md-1">
                                <div style="text-align: center;">操作</div>
                            </th>
                        </tr>
                    </thead>
                    <s:iterator value="addrPoolByCluster" id="addrPoolList">
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
                                    <td style="text-align: center;">
                                        <a class="blueletter" href="javascript:void(0)"
                                           onclick="showDetail(this,'<s:property
                                                   value="#addrpool.id"/>')">详情</a>
                                        <a class="blueletter leftmargin_10" href="javascript:void(0)"
                                           onclick="delNet(this,'<s:property value="#addrpool.id"/>')">删除</a>
                                    </td>
                                </tr>
                            </s:if>
                        </s:iterator>
                    </s:iterator>
                </table>
            </div>
        </div>


        <div style="margin-top:3%; height: 33px;">
            <label class="col-md-2 front-no-padding" style=" padding-left: 8px; line-height: 33px; margin: 0px;">服务器列表</label>
        </div>
        <div class="panel panel-default front-panel" style="margin-top: 10px">
            <table class="table table-striped multi-table">
                <thread>
                    <tr>
                        <th>名称</th>
                        <th class="col-md-2" >UUID/IP</th>
                        <%--<th class="col-md-2" >集群</th>--%>
                        <th class="col-md-2" >配置</th>
                        <th class="col-md-2" >备注</th>
                        <th class="col-md-1" style="text-align: center;">操作</th>
                    </tr>
                </thread>
                <s:iterator value="hosts" id="hosts">
                    <tr>
                        <td style="vertical-align: middle;overflow: auto"><s:property value="#hosts.name"/></td>
                        <td style="vertical-align: middle;overflow: auto"><s:property value="#hosts.uuid"/><br><s:property value="#hosts.ip"/></td>
                        <%--<td style="vertical-align: middle;overflow: auto"><s:property value="aggregate.name"/></td>--%>
                        <td style="vertical-align: middle;overflow: auto">
                            CPU：<s:property value="#hosts.cpuCore"/><br>
                            硬盘：<s:property value="#hosts.diskMb"/><br>
                            内存：<s:property value="#hosts.memoryMb"/><br>
                        </td>
                        <td style="vertical-align: middle;overflow: auto"><s:property value="#hosts.extend" escape="false"/></td>
                        <td style="vertical-align:middle;text-align: center;overflow:auto">
                            <%--<a href="javascript:void(0)" data-uuid="<s:property value="#hostlist.uuid"/>" onclick="showNodeEditModal(<s:property value='#hostlist.id'/>,this)">修改</a>--%>
                            <a href="system/nodeDetail?uuid=<s:property value='#hosts.uuid'/>">详情</a>
                        </td>
                        <%--<td class="centeralign" width="354px"><s:property value="#hostList.ip"/></td>--%>
                        <%--<td class="centeralign" style="text-align: center;"><a--%>
                                <%--href="system/hostservice?hostId=<s:property value="#hostList.id"/>">详情</a>--%>
                        <%--</td>--%>
                    </tr>
                </s:iterator>
            </table>
        </div>

        <div style="margin-top:3%; height: 33px;">
            <label class="col-md-2 front-no-padding" style=" padding-left: 8px; line-height: 33px; margin: 0px;">用户群组列表</label>
        </div>
        <div class="panel panel-default front-panel" style="margin-top: 10px">
            <table class="table table-striped multi-table">
                <thread>
                    <tr>
                        <th class="centeralign">群组名称</th>
                        <th class="centeralign" style="text-align: center">集群名称</th>
                    </tr>
                </thread>
                <s:iterator value="groupClusterMap" id="map">
                    <tr>
                        <td class="centeralign"><s:property value="key.name"/></td>
                        <td class="centeralign" style="text-align: center">
                            <s:iterator value="value" id="cluster">
                                <a href="system/clusterdetail?id=<s:property value='#cluster.id'/>"><s:property value="#cluster.name"/></a>&nbsp;
                            </s:iterator>
                        </td>
                    </tr>
                </s:iterator>
            </table>
        </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"/>
</div>


<%--<s:include value="/template/_common_js.jsp"></s:include>--%>

</body>
<%--<s:include value="/template/_footer.jsp"></s:include>--%>


<script>
    $(function () {

    });

    function showDetail(obj, poolId) {
        $.post("net/netdetail", {poolId: poolId}, function (data) {
            $(obj).html("收起");
            $(obj).attr("onclick", "hideDetail(this,'" + poolId + "')");
            var detailTable =
                "<tr class='yellowbox padding10'><td></td>" +
                "<td colspan='7'>";
            if (data.addressPool.ips == null || data.addressPool.ips.length == 0) {
                detailTable += "<label class=\"col-md-3 control-label front-label\">该网段的ip地址还没有被使用过</label>";
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

                detailTable += "<tr><th>IP地址</th><th>状态</th><th>MAC地址</th><th>虚拟机</th></tr></thread>";
                $.each(data.addressPool.ips, function (i, ip) {
                    detailTable +=
                        "<tr>" +
                        "<td>" + ip.ipAddress + "</td>" +
                        "<td>" + ip.status + "</td>" +
                        "<td>" + ip.macAddress + "</td>" +
                        "<td>" + ip.serverName + "</td>" +
                        "</tr>";
                });

                detailTable += "</table></div></td></tr>";
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
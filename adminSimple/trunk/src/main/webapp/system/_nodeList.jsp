<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*, appcloud.admin.common.Constants,appcloud.admin.action.system.VmSummaryAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<input type="hidden" id="nodeCount" value="<s:property value="count"/>"/>
<table id="nodeTable" class="table table-striped front-table table-bordered table-hover" style="margin-bottom: 0px">
    <thead>
    <tr>
        <th style="text-align: center">名称</th>
        <th class="col-md-2" style="text-align: center">UUID/IP</th>
        <%--<th class="col-md-1" style="text-align: center">集群</th>--%>

        <th class="col-md-5" style="text-align: center">配置</th>
        <%--<th class="col-md-3" style="text-align: center">运行状态</th>--%>
        <%--<th class="col-md-1">位置</th>--%>
        <th class="col-md-2" style="text-align: center">备注</th>
        <th class="col-md-1" style="text-align: center">操作</th>
    </tr>
    </thead>
   <tbody>
   <s:iterator value="hostList" id="hostlist">
       <%--<s:if test='#hostlist.type.toString()!="管理结点"'>--%>
       <tr id="<s:property value="#hostlist.uuid"/>" data-uuid="<s:property value="#hostlist.uuid"/>" class="host" name="<s:property value="#hostlist.type"/>">
       <%--</s:if>--%>
       <%--<s:else>--%>
           <%--<tr id="<s:property value="#hostlist.uuid"/>" data-uuid="<s:property value="#hostlist.uuid"/>" class="hide host" name="<s:property value="#hostlist.type"/>">--%>
       <%--</s:else>--%>
       <s:set name="clusterName" value="#hostlist.clusterId"/>
           <td style="vertical-align: middle;overflow: auto"><s:property value="#hostlist.name"/></td>
           <td style="vertical-align: middle;overflow: auto"><s:property value="#hostlist.uuid"/><br><s:property value="#hostlist.ip"/></td>
               <%--<td style="vertical-align: middle; text-align: center;overflow: auto">非受控<br>虚拟机</td>--%>

           <%--<td style="vertical-align: middle; text-align: center;overflow: auto"><s:property value="clusterMap[#clusterName]"/></td>--%>
           <td style="vertical-align: middle;overflow: auto">
               CPU：<s:property value="#hostlist.cpuCore"/>&nbsp;&nbsp;&nbsp;
               硬盘：<s:property value="#hostlist.diskMb"/>&nbsp;&nbsp;&nbsp;
               内存：<s:property value="#hostlist.memoryMb"/>&nbsp;&nbsp;&nbsp;
           </td>
           <%--<td style="vertical-align: middle;padding: 3px;">
               <div style="width: 50%;float: left;vertical-align: middle">
                   <div>
                       <div style="margin: 0px;font-size: 12px;">CPU使用率:&nbsp;<span id="cpuUsed<s:property value="#hostlist.uuid"/>"></span>%</div>
                           &lt;%&ndash;<div class="progress" style="margin-bottom: 2px;margin-top: 2px">&ndash;%&gt;
                           &lt;%&ndash;<span class="green" style="width: 20%;"><span>20%</span></span>&ndash;%&gt;
                           &lt;%&ndash;</div>&ndash;%&gt;
                   </div>
                   <div>
                       <span style=><p style="margin: 0px;font-size: 12px;">内存占用率:&nbsp;<span id="memUsed<s:property value="#hostlist.uuid"/>"></span>%</p></span>
                           &lt;%&ndash;<div class="progress" style="margin-bottom: 5px;margin-top: 2px">&ndash;%&gt;
                           &lt;%&ndash;<span class="red" style="width: 80%;"><span>80%</span></span>&ndash;%&gt;
                           &lt;%&ndash;</div>&ndash;%&gt;
                   </div>
                   <div>
                       <div style="margin: 0px;font-size: 12px;">内存交换率:&nbsp;<span id="memSwap<s:property value="#hostlist.uuid"/>"></span>%</div>
                   </div>
                   <div style="margin-bottom: 0px;">
                       <div style="font-size: 12px">硬盘占用率:&nbsp;<span id="diskUsed<s:property value="#hostlist.uuid"/>"></span>%</div>
                   </div>

               </div>
               <div style="width: 50%;float: right;vertical-align: middle">

                   <div>
                       <span style=><p style="margin: 0px;font-size: 12px;">磁盘坏道数:&nbsp;<span id="diskErrorCount<s:property value="#hostlist.uuid"/>"></span></p></span>
                           &lt;%&ndash;<div class="progress" style="margin-bottom: 5px;margin-top: 2px">&ndash;%&gt;
                           &lt;%&ndash;<span class="red" style="width: 80%;"><span>80%</span></span>&ndash;%&gt;
                           &lt;%&ndash;</div>&ndash;%&gt;
                   </div>
                   <div style="margin-bottom: 0px;">
                       <div style="font-size: 12px">网络收:&nbsp;<span id="netDownload<s:property value="#hostlist.uuid"/>"></span>B/S</div>
                   </div>
                   <div style="margin-bottom: 0px;">
                       <div style="font-size: 12px">网络发:&nbsp;<span id="netUpload<s:property value="#hostlist.uuid"/>"></span>B/S</div>
                   </div>
               </div>

                   &lt;%&ndash;<div style="width: 50%;float: left;vertical-align: middle">&ndash;%&gt;
                   &lt;%&ndash;<span style="vertical-align: middle"><p>test</p></span>&ndash;%&gt;
                   &lt;%&ndash;</div>&ndash;%&gt;
           </td>--%>
           <td style="vertical-align: middle;overflow: auto"><s:property value="#hostlist.extend" escape="false"/></td>
           <td style="vertical-align:middle;text-align: center;overflow:auto">
                   <a href="javascript:void(0)" data-uuid="<s:property value="#hostlist.uuid"/>" onclick="showNodeEditModal(<s:property value='#hostlist.id'/>,this)">修改</a>
                   <a href="system/nodeDetail?uuid=<s:property value='#hostlist.uuid'/>">详情</a>
           </td>
           </td>
       </tr>
   </s:iterator>
   </tbody>
</table>


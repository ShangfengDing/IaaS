<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>集群管理 - 云海IaaS</title>
    <%--<link rel="stylesheet" href="http://newfront.free4lab.com/bootstrap/css/bootstrap.min.css">--%>
    <%--<link rel="stylesheet" href="http://newfront.free4lab.com/css/front.css">--%>

</head>
<body class="front-body">
<s:include value="/template/_banner.jsp?menu=system"/>
<div class="front-inner front-inner-media">
    <div class="container">
        <div class="col-md-12 form-group" style="margin-bottom:0px">
            <div>
                <ol class="breadcrumb">
                    <li>系统管理</li>
                    <li class="active">集群管理</li>
                </ol>
            </div>
            <div class="text-right col-md-12" style="padding-right: 0px; margin-bottom:0px">
                <a type="button" class="btn btn-primary" data-toggle="front-modal"  data-size="modal-lg" data-href="system/prenewcluster?zoneId=<s:property value="zid"/>">新建</a>
            </div>
        </div>
        <div class="col-md-12 form-group" style="margin-top:10px">
        <%--<input type="hidden" id="zid" value='<s:property value="zid"/>'/>--%>
        <div class="panel panel-default front-panel" style="margin-top:10px">
            <%--<div class="front-no-padding" >--%>
            <%--<div class="contentline">--%>
            <s:if test='clusters.size() == 0'>
                <h3 class="centeralign">还没有集群,请新建集群</h3>
            </s:if>
            <s:else>
                <div class="panel-body front-no-padding">
                    <table class="table table-striped multi-table col-md-12" style="margin-bottom: 0px">
                    <thead>
                    <tr>
                        <!-- 名称	节点数	云主机数	云硬盘数	操作 -->
                        <th >&nbsp;&nbsp;名称</th>
                        <th class="col-md-1" style="text-align: center;">服务器</th>
                        <th class="col-md-1" style="text-align: center;">云主机</th>
                        <th class="col-md-1" style="text-align: center;">云硬盘</th>
                        <th class="col-md-1" style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <s:iterator id="list" value="clusters">
                            <tr id="<s:property value="#list.id"/>" class="cluster">
                                <td>&nbsp;&nbsp;<s:property value="#list.name"/></td>
                                <td style="text-align: center"><a class="blueletter" id="host<s:property value="#list.id"/>" href="system/hostmanage?page=1">loading</a></td>
                                <%--<td><a class="blueletter" id="host<s:property value="#list.id"/>" href="system/host?clusterId=<s:property value='#list.id'/>">loading</a></td>--%>
                                <td style="text-align: center;"><a class="blueletter" id="instance<s:property value="#list.id"/>" href="vm/presearchvm?cluster=<s:property value='#list.id'/>">loading</a></tds>
                                <td style="text-align: center;"><a class="blueletter" id="volume<s:property value="#list.id"/>" href="hd/hdmanage?zone=1">loading</a></td>
                                <td style="text-align: center;"><a class="blueletter" href="system/clusterdetail?id=<s:property value='#list.id'/>">详情</a></td>
                            </tr>
                    </s:iterator>
                </table>
                </div>

            </s:else>
            <%--</div>--%>
        </div>
        </div>
        <%--</div><!--#inner-->--%>
    </div><!--#container-->
    <s:include value="/template/_footer.jsp"/>
</div>
</body>
<script>
    var ids=[];
    count();
    function count(){
        $('.cluster').each(function(){
         ids.push($(this).attr("id"));
        });
         for(i=0;i<ids.length;i++) {
             getClusterDevices(ids[i]);
         }
    }
    function getClusterDevices(id){
          $.get('system/countClusterDevices',{
              id:id
          },function(data){
              if(data.result=="success"){
                  $('#host'+id).html(data.hostcount);
                  $('#instance'+id).html(data.servercount);
                  $('#volume'+id).html(data.volumecount);
              }
          });
    }
</script>
</html>
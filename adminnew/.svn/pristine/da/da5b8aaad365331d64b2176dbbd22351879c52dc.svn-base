<%--<!DOCTYPE html>--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<%--正常jsp页面写法，下面是模态框写法，看需要选取--%>
<%--<html>--%>
<%--<head>--%>
<%--<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>--%>
<%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>--%>
<%--<title>云硬盘管理 -新建集群 </title>--%>
<%--<link rel="stylesheet" href="http://newfront.free4lab.com/bootstrap/css/bootstrap.min.css">--%>
<%--<link rel="stylesheet" href="http://newfront.free4lab.com/css/front.css">--%>
<%--</head>--%>
<%--<body class="front-body">--%>
<%--<s:include value="/template/_banner.jsp?menu=system"/>--%>
<%--<div class="front-inner ">--%>
<%--<input type="hidden" id="zoneId" value='<s:property value="zoneId"/>'/>--%>
<%--<div class="container">--%>
<%--<div class="panel panel-default front-panel">--%>
<%--<div class="panel-heading">--%>
<%--<strong>新建集群</strong>--%>
<%--</div>--%>
<%--<div class="panel-body">--%>
<%--<form class="form-horizontal" role="form">--%>
<%--<div class="form-group">--%>
<%--<label class="col-md-1 control-label front-label">集群名称</label>--%>
<%--<div class="col-md-5">--%>
<%--<input type="text" id="name" class="form-control front-no-box-shadow" onblur="checkname()"/>--%>
<%--<span class="redletter" id="nametip"></span>--%>
<%--</div>--%>
<%--<div class="text-right" style="margin-right:2%">--%>

<%--&lt;%&ndash;<a class="btn btn-default" href="javascript:void(0)" onclick="facebox_close()">取消</a>&ndash;%&gt;--%>
<%--<a class="btn btn-default"  href="system/cluster" >取消</a>--%>
<%--<a class="btn btn-primary" href="javascript:void(0)" onclick="submit();">确定</a>--%>
<%--</div>--%>
<%--</div>--%>
<%--</form>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<s:include value="/template/_footer.jsp"></s:include>--%>
<%--</div>--%>

<%--模态框写法--%>
<div class="modal-header">
    <button type="button" class="close modal-close" data-dismiss="modal" aria-hidden="true">×</button>
    <h4 class="modal-title">新建集群</h4>
</div>
<div class="modal-body">
    <form class="form-horizontal" role="form">
        <input type="hidden" id="zoneId" value='<s:property value="zoneId"/>'/>
        <div class="form-group">
            <label class="col-md-2 control-label front-label">集群名称</label>
            <div class="col-md-9">
                <input type="text" id="name" class="form-control front-no-box-shadow" onblur="checkname()"/>
                <span class="redletter" id="nametip"></span>
            </div>
        </div>

        <%--<div class="form-group">--%>
            <%--<div class="text-right" style="margin-right:4%">--%>
                <%--<a class="btn btn-default" class="close modal-close" data-dismiss="modal" aria-hidden="true">取消</a>--%>
                <%--<a class="btn btn-primary" href="javascript:void(0)" onclick="submit();">确定</a>--%>
            <%--</div>--%>
            <%----%>
        <%--</div>--%>
    </form>

</div>

<div class="modal-footer">
    <button class="btn btn-primary" type="submit">确定</button>
    <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
</div>


<script>
    function submit() {
        var name = $("#name").val().trim();
        var zoneId = $("#zoneId").val();
//	var publicVlanId = $("#publicVlanId").val();
//	var privateVlanId = $("#privateVlanId").val();
        var publicVlanId = 1;
        var privateVlanId = 2;

        if (name == "" || name.length > 20) {
            $("#nametip").html("名称需1~20个字");
            return false;
        } else {
            $.post("system/newcluster",
                {
                    name: name,
                    zoneId: zoneId,
                    publicVlanId: publicVlanId,
                    privateVlanId: privateVlanId
                }, function () {
                    location.reload();
                });
        }
    }

    function checkname() {
        var name = $("#name").val().trim();

        if (name == "" || name.length > 20) {
            $("#nametip").html("名称需1~20个字");
        } else {
            $("#nametip").html("");
        }
    }
</script>
<%--<script src="http://newfront.free4lab.com/js/jquery/jquery.min.js"></script>--%>
<%--<script src="http://newfront.free4lab.com/bootstrap/js/bootstrap.min.js"></script>--%>
<%--<script src="http://newfront.free4lab.com/js/plugin/front.js"></script>--%>
<%--</body>--%>
<%--</html>--%>
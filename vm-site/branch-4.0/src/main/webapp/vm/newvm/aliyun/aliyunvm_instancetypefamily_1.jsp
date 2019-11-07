<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/26
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>系列I</title>
    <style>
        .title {
            margin-right: 50px;;
        }
    </style>
</head>
<body>
<div class="modal-body">
    <h4>选择型号</h4>
    <span class="title">1核</span>
    <div id="cores_1" class="clearfix btn-group">
    </div>
    <br>
    <br>
    <span class="title">2核</span>
    <div id="cores_2" class="clearfix btn-group">
    </div>
    <br>
    <br>
    <span class="title">4核</span>
    <div id="cores_4" class="clearfix btn-group">
    </div>
    <br>
    <br>
    <span class="title">8核</span>
    <div id="cores_8" class="clearfix btn-group">
    </div>
    <br>
    <br>
    <span class="title" style="margin-left: -8px;">16核</span>
    <div id="cores_16" class="clearfix btn-group">
    </div>
    <br>
    <br>
    <button class="btn btn-primary" onclick="instanceTypeFamily_1_Modal.modal('hide')">确定</button>
    <button class="btn btn-default" onclick="instanceTypeFamily_1_Modal.modal('hide')">取消</button>

</div>
<script>
    $(document).ready(function () {
        $.each(instanceTypes, function (index) {
            var instanceType = instanceTypes[index];
            var item = $("<button class='btn btn-default front-no-box-shadow' onclick='changeInstanceType("+index+")'>" + instanceType.cpuCores + "核" + instanceType.memory + "GB</button>");
            $("#cores_" + instanceType.cpuCores).append(item);
        });
    });
</script>
</body>
</html>

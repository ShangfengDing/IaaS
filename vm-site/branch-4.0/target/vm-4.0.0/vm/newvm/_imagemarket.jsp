<%--
  Created by IntelliJ IDEA.
  User: lizhenhao
  Date: 2017/9/26
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<div class="modal-body">

    <div class="front-btn-group" style="margin-left: 15px">
        <a href="javascript:void(0)" class="btn btn-default" onclick="showImageDiv('public',1)" id="public-image"><span class="glyphicon glyphicon-star"></span>公共镜像</a>
        <a href="javascript:void(0)" class="btn btn-default" onclick="showImageDiv('group',1)" id="group-image"><span class="glyphicon glyphicon-asterisk"></span>共享镜像</a>
        <a href="javascript:void(0)" class="btn btn-default" onclick="showImageDiv('private',1)" id="private-image"><span class="glyphicon glyphicon-king"></span>自定义镜像</a>
    </div>

    <div id="image-detail" style="height: 500px;width:100%;margin-top:60px">

    </div>

    <%--分页标签--%>
    <div style="text-align: center" id="image_divpage"></div>


    <div style="height: 30px;">
        <a class="btn btn-primary float-right" style="margin-right: 15px" href="javascript:void(0)" onclick="hideModel()">确定</a>
    </div>


</div>

<script>
    $(document).ready(function () {
        showImageDiv('public',1)
    })
</script>


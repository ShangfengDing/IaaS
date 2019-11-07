<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/31
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 阿里云的搜索函数要改 -->
<div class="form-group">
    <label class="col-md-1 control-label front-label">状态</label>
    <!-- 状态选择 -->
    <div id="yunhai-search" class="col-md-11 front-toolbar other">
        <div class="front-toolbar-header clearfix">
            <button type="button" class="front-toolbar-toggle navbar-toggle"
                    data-toggle="collapse" data-target="#yunhai-vmstatus">
                <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>
        </div>
        <div id="yunhai-vmstatus" class="front-btn-group collapse"
             data-toggle="buttons">
            <label id="group-all"
                   class="btn btn-default front-no-box-shadow active"
                   onclick="vmshow('all')" data-group="all">
                <input type="radio" name="options" autocomplete="off" checked>全部
            </label>
            <label class="btn btn-default front-no-box-shadow"
                   onclick="vmshow('active')" data-group="create"
                   style="box-shadow: none">
                <input type="radio" name="options" autocomplete="off" checked>运行中
            </label>
            <label class="btn btn-default front-no-box-shadow"
                   onclick="vmshow('expiring')" data-group="manage"
                   style="box-shadow: none">
                <input type="radio" name="options" autocomplete="off" checked>已关机
            </label>
        </div>
    </div>
    <!-- 状态选择结束 -->
</div>
<div class="form-group">
    <label class="col-md-1 control-label front-label"
           for="vmkeyword">关键字</label>
    <div class="col-md-11">
        <input id="vmkeyword" type="text"
               class="form-control front-no-box-shadow" placeholder="输入云主机名称搜索">
    </div>
</div>
<div class="form-group">
    <label class="col-md-1 control-label front-label" for="zoneId">付费方式</label>

    <div class="col-md-3">
        <select id="zoneId"
                class="form-control front-no-radius front-no-box-shadow">
            <option value="">--全部--</option>
            <option value="zpark">按量</option>
        </select>
    </div>
    <!-- 老师让去掉这个，暂时给隐藏了 -->
    <label class="col-md-1 hidden control-label front-label" for="status">状态</label>
    <div class="col-md-3 hidden">
        <select id="status"
                class="form-control front-no-radius front-no-box-shadow">
            <option value="">--全部--</option>
            <option value="ACTIVE">运行中</option>
            <option value="STOPPED">已关机</option>
        </select>
    </div>
    <div class="text-right col-md-8">
        <button type="button" id="resetsearch" class="btn btn-default">重置</button>
        <button type="button" id="groupsearch" class="btn btn-primary"
                onclick="vmsearch('keyword')">查找
        </button>
    </div>
</div>

<script>
    function newalivm() {
        var vmappname = $('#selectAppname option:selected').text();//appname
        $('#buy-vm').attr('href',"vm/newvm/aliyun/newaliyunvm.jsp?appname=" + vmappname);
    }
</script>
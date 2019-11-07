<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/31
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--阿里云硬盘搜索框-->
<div class="panel-heading">
    <strong>云主机搜索</strong>
</div>
<div class="panel-body">
    <div class="form-horizontal">
    <!-- 搜索的button数组 -->
        <div class="form-group">
            <label class="col-md-1 control-label front-label">状态</label>
            <div class="col-md-11 other">
                <div class="front-toolbar-header clearfix">
                    <button type="button" class="front-toolbar-toggle navbar-toggle"
                            data-toggle="collapse" data-target="#aliyun-hdstatus"
                            aria-expanded="false" aria-controls="freeshare-reource-btns">
                        <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                </div>
                <div id="aliyun-hdstatus" class="front-btn-group collapse"
                     data-toggle="buttons">
                    <label id="group-all" class="btn btn-default front-no-box-shadow active"
                           data-group="">
                        <input type="radio" name="options" autocomplete="off" checked>全部
                    </label>
                    <label class="btn btn-default front-no-box-shadow"
                           data-group="Attaching" style="box-shadow: none">
                        <input type="radio" name="options" autocomplete="off" checked>
                        <span class="glyphicon glyphicon-asterisk"></span> 已挂载
                    </label>
                    <label class="btn btn-default front-no-box-shadow"
                           data-group="Available" style="box-shadow: none">
                        <input type="radio" name="options" autocomplete="off" checked>
                        <span class="glyphicon glyphicon-king"></span> 未挂载
                    </label>
                </div>
            </div>
        </div>
    <!-- 关键字和select选择 -->
            <div class="form-group">
                <label class="col-md-1 control-label front-label">关键字</label>
                <div class="col-md-11">
                    <input id="hd-search-keyword" type="text"
                           class="form-control front-no-box-shadow" placeholder="输入云硬盘名称搜索">
                </div>
            </div>
            <div class="form-group last-form-group">
                <label class="col-md-1 control-label front-label">种类</label>
                <div class="col-md-3">
                    <select id="disk-category" class="form-control col-md-3 front-no-radius front-no-box-shadow">
                        <option value="all">全部</option>
                        <option value="cloud">普通云盘</option>
                        <option value="cloud_efficiency">高效云盘</option>
                        <option value="cloud_ssd">SSD云盘</option>
                        <option value="ephemeral">本地SSD盘</option>
                        <option value="ephemeral_ssd">本地磁盘</option>
                    </select>
                </div>

                <label class="col-md-1 control-label front-label">属性</label>
                <div class="col-md-3">
                    <select id="disk-type" class="form-control col-md-3 front-no-radius front-no-box-shadow">
                        <option value="all">全部</option>
                        <option value="system">系统盘</option>
                        <option value="data">数据盘</option>
                    </select>
                </div>
                <div class="text-right col-md-4">
                    <button type="button" id="aliresetsearch" class="btn btn-default" onclick="aliresetsearch()">重置</button>
                    <button type="button" id="alihdsearch" class="btn btn-primary" onclick="searchBySearchKeyWord()">查找</button>
                </div>
            </div>
        </div>
</div>
<script>
    function aliresetsearch() {
        $('#hd-search-keyword').val("");
        $('#disk-category').val("");
        $('#disk-type').val("");
    }
</script>

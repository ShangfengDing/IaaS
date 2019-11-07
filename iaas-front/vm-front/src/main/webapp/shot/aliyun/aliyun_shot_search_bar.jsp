<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/30
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--阿里云快照搜索框-->
<div class="form-horizontal">
    <div class="form-group">
        <label class="col-md-1 control-label front-label">属性</label>
        <div class="col-md-11 front-toolbar other">
            <div class="front-toolbar-header clearfix">
                <button type="button" class="front-toolbar-toggle navbar-toggle"
                        data-toggle="collapse" data-target="#aliyun-shottype">
                    <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                        class="icon-bar"></span>
                </button>
            </div>
            <div id="aliyun-shottype" class="front-btn-group collapse" data-toggle="buttons">
                <label id="group-all" class="btn btn-default front-no-box-shadow active"
                       data-group=""> <input type="radio" name="options"
                                                autocomplete="off" checked>全部
                </label>
                <label class="btn btn-default front-no-box-shadow"
                       data-group="System" style="box-shadow: none"> <input
                        type="radio" name="options" autocomplete="off" checked> <span
                        class="glyphicon glyphicon-asterisk"></span> 系统盘
                </label>
                <label class="btn btn-default front-no-box-shadow"
                       data-group="Data" style="box-shadow: none"> <input
                        type="radio" name="options" autocomplete="off" checked> <span
                        class="glyphicon glyphicon-king"></span> 数据盘
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-1 control-label front-label"
               for="shot-search-keyword">关键字</label>
        <div class="col-md-11">
            <input id="shot-search-keyword" type="text"
                   class="form-control front-no-box-shadow" placeholder="输入云快照名称搜索">
        </div>
    </div>
    <div class="form-group last-form-group">
        <div class="text-right col-sm-12">
            <button type="button" class="btn btn-default" onclick="yunhairestsearch()">重置</button>
            <button type="button" onclick="searchAliShotBySearchType()" class="btn btn-primary">查找</button>
        </div>
    </div>
</div>

<%--<div class="panel panel-default front-panel" id="shot-search"--%>
           <%--style="margin-bottom: 10px">--%>
    <%--<div class="panel-body">--%>
        <%--<div class="form-horizontal">--%>
            <%--<div class="form-group">--%>
                <%--<div class="col-md-3">--%>
                    <%--<select id="search-type" class="form-control" onchange="changeShotSearchType()">--%>
                        <%--<option value="shot-name">快照名称</option>--%>
                        <%--<option value="shot-id">快照ID</option>--%>
                        <%--<option value="instance-id">实例D</option>--%>
                        <%--<option value="disk-id">磁盘ID</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
                <%--<div class="col-md-6">--%>
                    <%--<input id="shot-search-keyword" type="text"--%>
                           <%--class="form-control front-no-box-shadow" placeholder="搜索关键字">--%>
                <%--</div>--%>
                <%--<div class="col-md-1">--%>
                    <%--<button type="button" id="groupsearch"--%>
                            <%--onclick="searchAliShotBySearchType()" class="btn btn-primary">--%>
                        <%--查找--%>
                    <%--</button>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="form-horizontal">--%>
            <%--<div class="form-group">--%>
                <%--<div class="col-md-3">--%>
                    <%--<select id="source-disk-type" class="form-control" onchange="changeSourceDiskType()">--%>
                        <%--<option value="">硬盘属性(全部)</option>--%>
                        <%--<option value="System">硬盘属性(系统盘)</option>--%>
                        <%--<option value="Data">硬盘属性(数据盘)</option>--%>
                    <%--</select>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<script>
    function yunhairestsearch() {
        $('#shot-search-keyword').val("");
    }
</script>
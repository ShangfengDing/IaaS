<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/30
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <div class="form-horizontal">
        <%--<div class="form-group">
            <label class="col-md-1 control-label front-label">属性</label>
            <div class="col-md-11 front-toolbar other">
                <div class="front-toolbar-header clearfix">
                    <button type="button" class="front-toolbar-toggle navbar-toggle"
                            data-toggle="collapse" data-target="#yunhai-shottype">
                        <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                </div>
                <div id="yunhai-shottype" class="front-btn-group collapse" data-toggle="buttons">
                    <label id="group-all" class="btn btn-default front-no-box-shadow active" onclick="changeDiskType('all')"
                           data-group="all"> <input type="radio" name="options"
                                                    autocomplete="off" checked>全部
                    </label>
                    <label class="disabled btn btn-default front-no-box-shadow"
                           &lt;%&ndash;onclick="changeDiskType('system')"&ndash;%&gt;
                           data-group="create" style="box-shadow: none"> <input
                            type="radio" name="options" autocomplete="off" checked> <span
                            class="glyphicon glyphicon-asterisk"></span> 系统盘
                    </label>
                    <label class="disabled btn btn-default front-no-box-shadow"
                           &lt;%&ndash;onclick="changeDiskType('datanet')"&ndash;%&gt;
                           data-group="manage" style="box-shadow: none"> <input
                            type="radio" name="options" autocomplete="off" checked> <span
                            class="glyphicon glyphicon-king"></span> 数据盘
                    </label>
                </div>
            </div>
        </div>--%>
        <div class="form-group">
            <label class="col-md-1 control-label front-label"
                   for="shot-key-word">关键字</label>
            <div class="col-md-11">
                <input id="shot-key-word" type="text"
                       class="form-control front-no-box-shadow" placeholder="输入云快照名称搜索">
            </div>
        </div>
        <div class="form-group last-form-group">
            <div class="text-right col-sm-12">
                <button type="button" class="btn btn-default" onclick="yunhairestsearch()">重置</button>
                <button type="button" onclick="searchByKeyWord()" class="btn btn-primary">查找</button>
            </div>
        </div>
    </div>

<script>
    function yunhairestsearch() {
        $('#shot-key-word').val("");
    }
</script>
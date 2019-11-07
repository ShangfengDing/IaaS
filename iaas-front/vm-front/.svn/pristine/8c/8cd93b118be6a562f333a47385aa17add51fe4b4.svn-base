<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/29
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--阿里云镜像搜索-->
        <div class="form-horizontal" id="yunhai_img_search">
            <div class="form-group">
                <label class="col-md-1 control-label front-label">类型</label>
                <div id="yunhai_search" class="col-md-11 front-toolbar other">
                    <div class="front-toolbar-header clearfix">
                        <button type="button" class="front-toolbar-toggle navbar-toggle"
                                data-toggle="collapse" data-target="#aliyun-imgtype">
                            <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                                class="icon-bar"></span>
                        </button>
                    </div>
                    <div id="aliyun-imgtype" class="front-btn-group collapse"
                         data-toggle="buttons">
                        <label id="group-all" class="btn btn-default front-no-box-shadow active"
                               data-group=""> <input type="radio" name="options"
                                                        autocomplete="off" checked>全部
                        </label>
                        <label class="btn btn-default front-no-box-shadow"
                               data-group="system" style="box-shadow: none"> <input
                                type="radio" name="options" autocomplete="off" checked> <span
                                class="glyphicon glyphicon-asterisk"></span> 公共镜像
                        </label>
                        <label class="btn btn-default front-no-box-shadow"
                               data-group="others" style="box-shadow: none"> <input
                                type="radio" name="options" autocomplete="off" checked> <span
                                class="glyphicon glyphicon-king"></span> 共享镜像
                        </label>
                        <label class="btn btn-default front-no-box-shadow"
                               data-group="self" style="box-shadow: none"> <input
                                type="radio" name="options" autocomplete="off" checked> <span
                                class="glyphicon glyphicon-king"></span> 自定义镜像
                        </label>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-1 control-label front-label">关键字</label>
                <div class="col-md-11">
                    <input id="image-search-content" type="text"
                           class="form-control front-no-box-shadow" placeholder="输入云镜像名称搜索">
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-12 text-right">
                    <button type="button" id="resetsearch" class="btn btn-default" onclick="yunhairestsearch()">重置</button>
                    <button type="button" id="groupsearch"
                            onclick="searchAliImagesBySearchType()" class="btn btn-primary">
                        查找
                    </button>
                </div>
            </div>
        </div>

<script>
    function yunhairestsearch() {
        $('#image-search-content').val("");
    }
</script>


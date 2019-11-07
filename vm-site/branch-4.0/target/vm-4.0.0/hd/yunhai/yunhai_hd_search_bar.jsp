<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2016/7/31
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel-heading">
    <strong>云主机搜索</strong>
</div>
<div class="panel-body">
    <div id="yunhai-content" class="form-horizontal">
        <div class="form-group">
            <label class="col-md-1 control-label front-label">状态</label>
            <div class="col-md-11 other">
                <div class="front-toolbar-header clearfix">
                    <button type="button" class="front-toolbar-toggle navbar-toggle"
                            data-toggle="collapse" data-target="#yunhai-hdstatus"
                            aria-expanded="false" aria-controls="freeshare-reource-btns">
                        <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                </div>
                <div id="yunhai-hdstatus" class="front-btn-group collapse"
                     data-toggle="buttons">
                    <label id="group-all" class="btn btn-default front-no-box-shadow active"
                           data-group="">
                        <input type="radio" name="options" autocomplete="off" checked>全部
                    </label>
                    <label class="btn btn-default front-no-box-shadow"
                           data-group="ATTACHED" style="box-shadow: none">
                        <input type="radio" name="options" autocomplete="off" checked>
                        <span class="glyphicon glyphicon-asterisk"></span> 已挂载
                    </label>
                    <label class="btn btn-default front-no-box-shadow"
                           data-group="DETACHED" style="box-shadow: none">
                        <input type="radio" name="options" autocomplete="off" checked>
                        <span class="glyphicon glyphicon-king"></span> 未挂载
                    </label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-1 control-label front-label"
                   for="hd-keyword">关键字</label>
            <div class="col-md-11">
                <input id="hd-keyword" type="text"
                       class="form-control front-no-box-shadow" placeholder="输入云硬盘名称搜索">
            </div>
        </div>
        <div class="form-group last-form-group">
            <label class="col-md-1 control-label front-label" for="disk-type">类型</label>
            <div class="col-md-3">
                <select id="disk-type"
                        class="form-control front-no-radius front-no-box-shadow">
                    <option value="">--全部--</option>
                    <option value="SYSTEM">SYSTEM</option>
                    <option value="NETWORK">NETWORK</option>
                    <option value="DATA">DATA</option>
                    <option value="ISO">ISO</option>
                </select>
            </div>
            <div class="text-right col-sm-8">
                <button type="button" id="yunhairesetsearch" class="btn btn-default" onclick="yunhairesetsearch()">重置</button>
                <button type="button" id="vmsearch" class="btn btn-primary" onclick="searchByDetailInfo()">查找</button>
            </div>
        </div>
    </div>
</div>

<script>
    function yunhairesetsearch() {
        $('#hd-keyword').val("");
        $('#disk-type').val("");
    }
</script>
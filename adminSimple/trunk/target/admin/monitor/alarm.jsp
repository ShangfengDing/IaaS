<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
<html>
<head>
    <%--<s:include value="/monitor/_head.jsp"/>--%>
    <title>告警-智能云监控</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="statics/plugin/bootstrap-datetimepicker/ver2/bootstrap-datetimepicker.min.css"/>
</head>
<body class="front-body">
<jsp:include page="/template/_banner.jsp?menu=runtime"/>
<div class="front-inner front-inner-media">

    <div id="container" class="container">
        <div class="col-md-12">
        <%--<div class="front-toolbar other">--%>
            <%--<div class="front-toolbar-header">--%>
                <%--<button type="button" class="front-toolbar-toggle navbar-toggle" data-toggle="collapse"data-target="#freeshare-resource">--%>
                    <%--<span class="icon-bar"></span>--%>
                    <%--<span class="icon-bar"></span>--%>
                    <%--<span class="icon-bar"></span>--%>
                <%--</button>--%>
            <%--</div>--%>
            <%--<div id="freeshare-resource" class="front-btn-group collapse" data-toggle="buttons">--%>
                <%--<label id="label_1" class="btn btn-default front-no-box-shadow" onclick="getAlarmHistory(1, this)">--%>
                    <%--<input type="radio" name="options" autocomplete="off">一天--%>
                <%--</label>--%>
                <%--<label class="btn btn-default front-no-box-shadow" onclick="getAlarmHistory(7, this)">--%>
                    <%--<input type="radio" name="options" autocomplete="off">七天--%>
                <%--</label>--%>
                <%--<label class="btn btn-default front-no-box-shadow" onclick="getAlarmHistory(30, this)">--%>
                    <%--<input type="radio" name="options" autocomplete="off">三十天--%>
                <%--</label>--%>
                <%--&lt;%&ndash;<a class="btn btn-default front-no-box-shadow" herf="javascript:void(0)" onclick="getAlarmHistory(1)">一天</a>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<a class="btn btn-default front-no-box-shadow" herf="javascript:void(0)" onclick="getAlarmHistory(7)">七天</a>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<a class="btn btn-default front-no-box-shadow" herf="javascript:void(0)" onclick="getAlarmHistory(30)">三十天</a>&ndash;%&gt;--%>
            <%--</div>--%>
        <%--</div>--%>
            <div class="row front-canvas" id="front-canvas">
                <div class="col-md-12">
                    <ol class="breadcrumb">
                        <li>日志管理</li>
                        <li class="active">告警查询</li>
                    </ol>
                </div>
            </div>
            <div id="custom-query-div" class="panel  panel-default front-panel clear form-horizontal">
                <div class="panel-heading">查询条件</div>
                <div class="panel-body" style="margin-bottom: 0px">
                    <div class="form-group">
                        <label class="col-md-1 control-label">时间选择</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="begin-date" placeholder="起始时间">
                        </div>
                        <div class="col-md-1"></div>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="end-date" placeholder="终止时间">
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 0px;">
                        <div class="col-md-12">
                            <button type="submit" id="search-log" class="btn btn-primary pull-right">
                                搜索
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="alarmHistory">
            <%--此处是告警列表--%>
            </div>

            <div class="modal fade" id="addComment-modal" tabindex="-1" role="dialog"
                 aria-labelledby="food-info-modal-label">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header" style="padding-top:10px;padding-bottom:0px">
                            <%--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>--%>
                            <h4 class="modal-title" id="food-info-modal-label">添加备注</h4>
                        </div>
                        <input type="hidden" id="modifiedId"/>
                        <div class="modal-body" style="padding:10px">
                            <div class='form-horizontal'>
                                <div class='form-group'>
                                    <label class="col-md-2 control-label front-label">备注</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control front-no-box-shadow" id="newComment"/>
                                    </div>
                                </div>
                                <div class="modal-footer" style="padding-top:10px;padding-bottom:0px;padding-right:0px; border-top:none; background-color:white">
                                    <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
                                    <a href="javascript: addComment();"
                                       class="btn btn-primary">确定</a> <!-- 注意按钮换行会导致多余的外补(margin) -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </div>



    <%--加载样式--%>
    <div id="loading">
        <div class="front-loading">
            <div class="front-loading-block"></div>
            <div class="front-loading-block"></div>
            <div class="front-loading-block"></div>
        </div>
        <div class="panel-body text-center">正在加载请稍候</div>
    </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>
<%--<script src="statics/js/jquery/jquery.min.js"></script>--%>
<%--<script src="statics/js/plugin/front.min.js"></script>--%>
<script src="statics/plugin//bootstrap-datetimepicker/ver2/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript">
    $(function() {
        $('#begin-date').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            autoclose:true
        });
        $('#end-date').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            autoclose:true
        });
    });
    getAlarmHistory(null,null)
    $("#search-log").click(function () {
        var btime = $("#begin-date").val();
        var etime = $("#end-date").val();
        var start = new Date().setHours(0, 0, 0, 0);

        if (btime != "") {
            btime = new Date($("#begin-date").val().substring(0, 19).replace(/-/g, '/')).getTime();
        } else {
            btime = start;
            $("#begin-date").attr("placeholder",formatDateTime(start));
        }
        if(etime != "") {
            etime = new Date($("#end-date").val().substring(0, 19).replace(/-/g, '/')).getTime();
        }else {
            etime = new Date().getTime();
        }
        getAlarmHistory(btime,etime);
    });

    function formatDateTime(inputTime) {
        var date = new Date(inputTime);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    };
    var btime,etime;
    function getAlarmHistory(begin, end){
        btime=begin;
        etime=end;
        getPage(1);
    }

    function getPage(page) {
        $('#alarmHistory').html($('#loading'));
        $.get('monitor/alarmHistory', {
            btime:btime,
            etime:etime,
            page:page-1
        }, function(data){
            $('#alarmHistory').html(data);
            $('#pageColumn').html($.getDivPageHtml(page,$('#pageColumn').data('endpage'),'(' + getPage + ')'));
        });
    }

    function changeStatus(id,status) {
        if(status == "IGNORED"){
            $.tipModal('confirm', 'danger', '您确定要忽略这条告警信息吗？', function (result) {
                if (result) {
                    $.post("monitor/changeStatus", {
                        id: id,
                        status: status
                    }, function () {
                        $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改成功'});
                        setTimeout(function(){
                            getAlarmHistory(null,null);
                        },1000);

//                        $.post('/alarmTwenty', {
//                        }, function (data) {
//                            $('#alarm-list-table').html(data);
//                        });
                    });
                }
            });
        }else {
            $.post("monitor/changeStatus", {
                id: id,
                status: status
            }, function () {
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改成功'});
                setTimeout(function(){
                    getAlarmHistory(null,null);
                },1000);
//                $.post('/alarmTwenty', {
//                }, function (data) {
//                    $('#alarm-list-table').html(data);
//                });
            });
        }
    }
    function showCommentModal(id){
        $("#modifiedId").val(id);
        $('#addComment-modal').modal();
    }
    function newComment(Id) {
        $('#addComment-modal').modal('show')
        $('#modifiedId').val(Id);
    }
    function addComment() {

        var id=$("#modifiedId").val();
        var comment=$('#newComment').val();
        $('#newComment').val("");
        $("#modifiedId").val("");
        $('#addComment-modal').modal('hide');
        $.post('monitor/addComment',{
            id:id,
            comment:comment
        },function(data){
            if(data.result=="success"){
                $.fillTipBox({type:'success', icon:'glyphicon-ok-sign', content:'修改成功'});
            }else{
                $.fillTipBox({type:'warning', icon:'glyphicon-exclamation-sign', content:'操作失败请重试'})
            }
            setTimeout(function(){
                getAlarmHistory(null,null);
            },1000);

        });

    }
//    getAlarmHistory(1, $('#label_1'));
//
//    $('#nav-collapse-demo').find('li').removeClass('front-active');
//    $('#active-alarm').addClass('front-active');
</script>
</body>
</html>
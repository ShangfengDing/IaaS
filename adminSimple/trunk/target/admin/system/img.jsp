<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/4/9
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.*, appcloud.admin.common.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>云模板管理 - 云海IaaS</title>
</head>
<body class="front-body" onload="imgsearch()">
<s:include value="/template/_banner.jsp?menu=cloud"/>
<div class="front-inner">
    <div class="container">
        <div class="col-md-12 form-group">
            <div class="inner">
                <div>
                    <ol class="breadcrumb">
                        <li>资源管理</li>
                        <li class="active">云模板管理</li>
                    </ol>
                </div>
                <div class="panel panel-default front-panel">
                    <div class="panel-body" style="margin-top:3px;padding-bottom:0px">
                        <div id="yunhai-content" class="form-horizontal">
                            <div class="form-group">
                                    <label class="col-md-1 control-label front-label" style="padding-top: 0px;">模板类型</label>
                                    <div>
                                        <span class=" control-label front-label" style="padding-left: 13px; padding-top: 0px">
                                            <input type="radio" style="left: 12px;" onclick="imgsearch()"
                                                   id="imgButton"
                                                   name="y"
                                                   checked/>&nbsp;镜像模板列表</span>&nbsp&nbsp&nbsp&nbsp
                                        <span class="control-label front-label" style="padding-top: 0px;">
                                            &nbsp;<input type="radio" onclick="isosearch()" id="isoButton" name="y"/>&nbsp;ISO文件列表</span>
                                    </div>
                            </div>

                            <!--  镜像搜索  -->
                            <div id="img_search" style="display: none">
                                <div class="form-group">
                                    <label class="col-md-1 control-label front-label">镜像类型</label>
                                    <div class="col-md-4 front-btn-group">
                                        <a class="btn btn-default front-no-box-shadow selected" onclick="allimg()"
                                           id="allButton">全部镜像</a>
                                        <a class="btn btn-default front-no-box-shadow" onclick="platformimg()"
                                           id="platformButton">平台镜像</a>
                                        <a class="btn btn-default front-no-box-shadow" onclick="groupimg()"
                                           id="groupButton">群组镜像</a>
                                    </div>
                                </div>
                                <!--  所有镜像  -->
                                <div id="all" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-1 control-label front-label">关键字</label>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control front-no-box-shadow"
                                                   name="keywordAll" id="keywordAll"/>
                                        </div>
                                        <label class="col-md-1 control-label front-label">用户邮箱</label>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control front-no-box-shadow" name="uemail"
                                                   id="uemail"/>
                                        </div>
                                    </div>
                                    <div class="form-group">

                                        <div class="col-lg-offset-1 col-lg-11 text-right" style="padding-top: 5px;">
                                            <a href="javascript:void(0)" class="btn btn-default"
                                               onclick="cancelSearch()">取消</a>
                                            <a href="javascript:void(0)" class="btn btn-primary"
                                               onload="searchAllImg()">确定</a>
                                        </div>
                                    </div>
                                </div>
                                <!-- 平台镜像-->
                                <div id="platform" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-1 control-label front-label">关键字</label>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control front-no-box-shadow"
                                                   name="keywordPlatform"
                                                   id="keywordPlatform"/>
                                        </div>
                                        <div class="col-lg-offset-1 col-lg-11 text-right" style="padding-top: 20px;">
                                            <a href="javascript:void(0)" class="btn btn-default" onclick="cancelSearch()">取消</a>
                                            <a href="javascript:void(0)" class="btn btn-primary pull-right" onclick="searchPlatImg()">确定</a>
                                        </div>
                                    </div>
                                </div>
                                <!--    群组镜像-->
                                <div id="group" style="display: none">
                                    <div class="form-group">
                                        <label class="col-md-1 control-label front-label">关键字</label>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control front-no-box-shadow"
                                                   name="keywordGroup" id="keywordGroup"/>
                                        </div>

                                        <label class="col-md-1 control-label front-label">群组</label>
                                        <div class="col-md-5">
                                            <select class="form-control front-no-radius front-no-box-shadow"
                                                    id="groupstatus">
                                                <option value="">--全部--</option>
                                                <option value="1">zpark</option>
                                                <%--<option value="2">lab</option>--%>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">

                                        <div class="col-lg-offset-1 col-lg-11 text-right" style="padding-top: 5px">
                                            <a href="javascript:void(0)" class="btn btn-default"
                                               onclick="cancelSearch()">取消</a>
                                            <a href="javascript:void(0)" class="btn btn-primary" onclick="searchGroupImg()">确定</a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!--              ISO搜索-->
                            <div id="iso_search"  style="display: none">
                                <div class="form-group">
                                    <label class="col-md-1 control-label front-label">关键字</label>
                                    <div class="col-md-5">
                                        <input type="text" class="form-control front-no-box-shadow" name="isokeyword"
                                               id="isokeyword"/>
                                    </div>
                                    <div class="col-lg-offset-1 col-lg-11 text-right" style="padding-top: 20px">
                                        <a href="javascript:void(0)" class="btn btn-default"
                                           onclick="cancelSearch()">取消</a>
                                        <a href="javascript:void(0)" class="btn btn-primary" onclick="searchIso()">确定</a>
                                    </div>
                                </div>
                            </div>



                        </div>
                    </div>
                </div>

                <!--              搜索结果：查询列表    -->
                <div class="form-group" id="resultlist">
                    <div class=" panel panel-default front-panel"
                         style="margin-top: 10px">
                        <div class="panel-body front-no-padding">
                            <div id="searchResult">
                            <table class="table table-striped front-table col-md-12" style="margin-bottom: 0px">
                                <thead>
                                <tr>
                                    <th ><div>名称</div></th>
                                    <th class="col-md-1" style="text-align: center"><div>操作系统</div></th>
                                    <th class="col-md-1" style="text-align: center"><div>类型</div></th>
                                    <th class="col-md-1" style="text-align: center"><div>用户</div></th>
                                    <th class="col-md-1" style="text-align: center"><div>群组</div></th>
                                    <th class="col-md-1" style="text-align: center"><div>状态</div></th>
                                    <th class="col-md-1" style="text-align: center"><div>操作</div></th>
                                </tr>
                                </thead>
                                <s:iterator id="image" value="images" status="st">
                                    <tr id="tr<s:property value="#image.id"/>">
                                        <td id="name<s:property value="#image.id"/>" style="vertical-align:
                                        middle;border-top: 1px solid #ddd;"><s:property
                                                value="(#image).metadata['displayName']"/></td>
                                        <td id="desc<s:property value="#image.id"/>" style="vertical-align:
                                        middle;text-align: center"><s:property
                                                value="#image.distribution" escape="false"/></td>
                                        <td style="vertical-align: middle;text-align: center"><s:property
                                                value="#image.metadata['type']"/></td>
                                        <td id="<s:property value="#image.id"/>" style="vertical-align:
                                        middle;text-align: center"><s:property value="#image.tenantId"
                                                                                              escape="false"/></td>
                                        <td id="<s:property value="#image.metadata['groupId']"/>"
                                            style="vertical-align: middle;text-align: center"><s:property
                                                value="#image.metadata['groupId']"/></td>
                                        <td style="vertical-align: middle;text-align: center">
                                            <s:if test="#image.status == 'AVAILABLE' or #image.status == 'available'">正常</s:if>
                                            <s:elseif test="#image.status == 'CREATING' or #image.status == 'creating'">创建中</s:elseif>
                                            <s:elseif test="#image.status == 'DELETING' or #image.status == 'deleting'">已删除</s:elseif>
                                            <s:elseif test="#image.status == 'ERROR' or #image.status == 'error'">
                                                <span class="redletter">错误</span></s:elseif>
                                            <s:elseif test="#image.status == 'ERROR_DELETING' or #image.status == 'error_deleting'">
                                                <span class="redletter">删除失败</span></s:elseif>
                                        </td>
                                        <td style="vertical-align: middle;text-align: center"><a class="blueletter"
                                                                               data-toggle="front-modal"
                                               data-href="system/imgmod.jsp?type=${param.type}&iid=<s:property value="#image.id"/>"
                                               rel="facebox" size="s" data-title="编辑模板信息" style="cursor: pointer">修改</a>&nbsp;
                                            <a class="blueletter" href="javascript:void(0)"
                                               onclick="delImage('<s:property value="#image.id"/>','<s:property
                                                       value="#image.metadata['groupId']"/>')">删除</a>&nbsp;
                                            <a data-toggle="front-modal" class="blueletter"
                                               data-href="system/imgdetail.jsp?displayDescription=<s:property value="#image.metadata['displayDescription']"/>&software=<s:property value="#image.metadata['software']"/>&account=<s:property value="#image.metadata['account']"/>"
                                               data-title="详情" rel="facebox" size="s" style="cursor: pointer">详情
                                            </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <s:if test="#image.metadata['type'] == 'GROUP' or #image.metadata['type'] == 'PRIVATE'">
                                                <a  class="blueletter" href="javascript:void(0)"
                                                   onclick="release('<s:property value="#image.id"/>')" style="cursor: pointer">发布</a>
                                            </s:if>
                                        </td>
                                    </tr>
                                </s:iterator>
                            </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="loading" class="hidden">
                    <div class="front-loading">
                        <div class="front-loading-block"></div>
                        <div class="front-loading-block"></div>
                        <div class="front-loading-block"></div>
                    </div>
                    <div class="panel-body text-center">正在加载请稍候</div>
                </div>
            </div>
        </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>

<script>
    //--------------------------按钮设置---------------------
    function imgsearch() { //点击镜像模板列表
        //$("#imgButton").css("text", "#fff");  //设置按钮颜色
       // $("#isoButton").css("text", "#f3f3f3");
        $('#iso_search').css("display", "none");   //关闭iso_search
        $('#img_search').css("display", "block");   //显示img_search
        //$("#isoButtonC").prop("checked",false);
        //$("#imgButtonC").prop("checked",true);
        allimg();
    }
    function isosearch() { //点击“ISO文件列表”
      //  $("#isoButton").css("background", "#fff");
       // $("#imgButton").css("background", "#f3f3f3");
       // $("#imgButtonC").prop("checked",false);
       // $("#isoButtonC").prop("checked",true);
        $('#img_search').css("display", "none");
        $('#iso_search').css("display", "block");
    }
    function cancelSearch() {  //关闭镜像和ISO文件搜索
        $("#keywordAll").val("");
        $("#uemail").val("");
        $("#keywordPlatform").val("");
        $("#keywordGroup").val("");
        $("#isokeyword").val("");
        $('#iso_search').css("display", "none");
        $('#img_search').css("display", "none");
       // $("#imgButton").css("background", "#f3f3f3");
       // $("#isoButton").css("background", "#f3f3f3");
        $("#isoButtonC").prop("checked",false);
        $("#imgButtonC").prop("checked",false);
    }

    function allimg() { //点击所有镜像
        $("#all").css("display", "block");
        $("#group").css("display", "none");
        $("#platform").css("display", "none");
        $("#allButton").css("background", "#f3f3f3");
        $("#platformButton").css("background", "#fff");
        $("#groupButton").css("background","#fff" );
    }
    function platformimg() { //点击平台镜像
        $("#all").css("display", "none");
        $("#group").css("display", "none");
        $("#platform").css("display", "block");
        $("#allButton").css("background", "#fff");
        $("#platformButton").css("background", "#f3f3f3");
        $("#groupButton").css("background", "#fff");
    }
    function groupimg() { //点击群组镜像
        $("#all").css("display", "none");
        $("#group").css("display", "block");
        $("#platform").css("display", "none");
        $("#allButton").css("background", "#fff");
        $("#platformButton").css("background", "#fff");
        $("#groupButton").css("background", "#f3f3f3");
    }

    //----------------------------跳转操作-----------------------
    //删除模板
    function delImage(iid, groupId) {
        if (confirm("确认删除模板？")) {
            $.post("img/delimg", {imageId: iid, groupId: groupId}, function (data) {
                console.log("result:" + data.result);
                if (data.result == "success") {
                    //fillTipBox("success", "操作成功！");   //自动消失框
                    alert(data.result+"删除成功！");
                    $("#tr" + iid).remove();
                }
                else {
                    fillTipBox("error", "操作出现错误，请稍后重试！");
                }
            });
        }
    }

    //发布模板
    function release(uuid) {
        if (confirm("确认发布为公共模板？")) {
            $.post("img/relimg", {imageId: uuid}, function (data) {
                console.log("result:" + data.result);
                if (data.result == "success") {
                   // fillTipBox("success", "操作成功！");
                    alert(data.result+"操作成功，请刷新页面");
                }
                else {
                    fillTipBox("error", "操作出现错误，请稍后重试！");
                }
            });
        }
    }

    //搜索所有镜像
    function searchAllImg() {
        //显示加载动图：正在加载中
        $("#searchResult").html("");
        var loading = $('#loading').clone();
        loading.removeClass('hidden');
        $('#searchResult').append(loading);
        var imgKeywordAll = $("#keywordAll").val();
        var email = $("#uemail").val();
        $.get("img/imgList", {
            imageName: imgKeywordAll,
            software: imgKeywordAll,
            description: imgKeywordAll,
            email:email,
            diskFormat: "IMAGE"
        }, function (html) {
            $("#searchResult").html(html);
            $.parser.parse();//解析页面
            $("#resultTable").addClass("table");
        })
    }

    //搜索平台镜像
    function searchPlatImg() {
        $("#searchResult").html("");
        var loading = $('#loading').clone();
        loading.removeClass('hidden');
        $('#searchResult').append(loading);
        var imgKeywordPlatform = $("#keywordPlatform").val();
        $.get("img/imgList", {
            imageName: imgKeywordPlatform,
            software: imgKeywordPlatform,
            description: imgKeywordPlatform,
            diskFormat: "IMAGE",
            type:"PUBLIC"
        }, function (html) {
            $("#searchResult").html(html);
            $("#resultTable").addClass("table");
        })
    }

    //搜索群组镜像
    function searchGroupImg() {
        $("#searchResult").html("");
        var loading = $('#loading').clone();
        loading.removeClass('hidden');
        $('#searchResult').append(loading);
        var imgKeywordGroup = $("#keywordGroup").val();
        var groupId = $("#groupstatus").val();
        $.post("img/imgList", {
            imageName: imgKeywordGroup,
            software: imgKeywordGroup,
            description: imgKeywordGroup,
            groupId: groupId,
            diskFormat: "IMAGE",
            type:"GROUP"
        }, function (html) {
            $("#searchResult").html(html);
        })
    }


    //所搜ISO文件列表
    function searchIso() {
        $("#searchResult").html("");
        var loading = $('#loading').clone();
        loading.removeClass('hidden');
        $('#searchResult').append(loading);
        var isoKeyword=$("#isokeyword").val();
        $.post("img/imgList", {
            imageName: isoKeyword,
            software: isoKeyword,
            description: isoKeyword,
            diskFormat: "ISO",
        }, function (html) {
            $("#searchResult").html(html);dmin
            $.parser.parse();
//            $("#resultTable").addClass("table");
        })

    }
</script>

</body>
</html>

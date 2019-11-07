<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="appcloud.api.enums.ImageTypeEnum" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>模板管理 - 云海IaaS</title>
    <s:include value="/template/_head.jsp"/>
</head>
<body>
<div id="container">
    <s:include value="/template/_banner.jsp?menu=sys"/>
    <div id="inner">
        <s:include value="/system/_left.jsp?menu=img"/>
        <div class="right">
            <div class="topmargin_10 bottommargin_10">
                <%--<a class="leftbutton greybutton ${param.type=='IMAGE'?'selected':''}"
                href="img/imglist?type=<%=ImageTypeEnum.IMAGE%>">镜像模板列表</a>
                <a class="rightbutton greybutton ${param.type=='ISO'?'selected':''}"
                href="img/imglist?type=<%=ImageTypeEnum.ISO%>">iso文件列表</a>--%>
                <a class="leftbutton greybutton ${param.type=='IMAGE'?'selected':''}" id="test"
                   onclick="imgsearch()" id="imgButton">镜像模板列表</a>
                <a class="rightbutton greybutton ${param.type=='ISO'?'selected':''}"
                   onclick="isosearch()" id="isoButton">iso文件列表</a>
            </div>
            <!-------------------------------------- 镜像搜索 ---------------------------------->
            <div id="img_search" style="display: none;">
                <a class="leftbutton greybutton selected" id="allButton" onclick="allImg()">所有镜像</a>
                <a class="leftbutton greybutton" id="platformButton" onclick="platImg()">平台镜像</a>
                <a class="leftbutton greybutton" id="groupButton" onclick="groupImg()">群组镜像</a>
                <table id="all" style="margin-top:10px">
                    <tbody>
                    <tr>
                        <td class="leftalign" width="300px">关键字</td>
                        <td width="600px">
                            <input type="text" class="leftmargin_10 squareinputlt" style="width:580px;"
                                   id="imgKeywordAll">
                        </td>
                    </tr>
                    <tr id="userEmail">
                        <td class="leftalign" width="400px">用户邮箱</td>
                        <td>
                            <input type="text" class="leftmargin_10 squareinputlt" style="width:580px;" name="email"
                                   id="email">
                        </td>
                    </tr>
                    <tr height="50px;">
                        <td class="rightalign" width="80px"></td>
                        <td width="100px"></td>
                        <td class="rightalign" width="80px"></td>
                        <td width="100px"></td>
                        <td></td>
                        <td>
                            <input type="button" value="搜索" class="button rightalign" style="margin-right:0px;"
                                   onclick="searchAllImg()">
                        </td>
                        <td width="140px"><a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="cancelSearch()">取消</a></td>
                    </tr>
                    </tbody>
                </table>
                <table id="platform" style="display: none;margin-top:10px">
                    <tbody>
                    <tr>
                        <td class="leftalign" width="300px">关键字</td>
                        <td width="600px">
                            <input type="text" class="leftmargin_10 squareinputlt" style="width:580px;"
                                   id="imgKeywordPlatform">
                        </td>
                    </tr>
                    <tr height="50px;">
                        <td class="rightalign" width="80px"></td>
                        <td width="220px"></td>
                        <td class="rightalign" width="80px"></td>
                        <td width="220px"></td>
                        <td></td>
                        <td class="rightalign">
                            <input type="button" value="搜索" class="button rightalign" style="margin-right:0px;"
                                   onclick="searchPlatImg(1)">
                        </td>
                        <td width="140px"><a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="cancelSearch()">取消</a></td>
                    </tr>
                    </tbody>
                </table>
                <table id="group" style="display: none;margin-top:10px">
                    <tbody>
                    <tr>
                        <td class="leftalign" width="300px">关键字</td>
                        <td width="600px">
                            <input type="text" class="leftmargin_10 squareinputlt" style="width:580px;"
                                   id="imgKeywordGroup">
                        </td>
                    </tr>
                    <tr>
                        <td class="leftalign" width="60px">群组</td>
                        <td width="180px">
                            <select class="leftmargin_10 selectbox" style="width:146.5px;height:24px;" id="status">
                                <s:iterator id="group" value="group">
                                    <option value='<s:property value="#group.id"/>'><s:property value="#group.name"/></option>
                                </s:iterator>
                            </select>
                        </td>
                    </tr>
                    <tr height="50px;">
                        <td class="rightalign" width="80px"></td>
                        <td width="220px"></td>
                        <td class="rightalign" width="80px"></td>
                        <td width="220px"></td>
                        <td></td>
                        <td class="rightalign">
                            <input type="button" value="搜索" class="button rightalign" style="margin-right:0px;"
                                   onclick="searchGroupImg()">
                        </td>
                        <td width="140px"><a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="cancelSearch()">取消</a></td>
                    </tr>
                    </tbody>
                </table>

            </div>
            <!-------------------------------------- iso搜索 ---------------------------------->
            <div id="iso_search" style="display: none;">
                <table>
                    <tbody>
                    <tr>
                        <td class="leftalign" width="300px">关键字</td>
                        <td width="600px">
                            <input type="text" class="leftmargin_10 squareinputlt" style="width:580px;" name="name"
                                   id="isoKeyword">
                        </td>
                    </tr>
                    <tr height="50px;">
                        <td class="rightalign" width="80px"></td>
                        <td width="220px"></td>
                        <td class="rightalign" width="80px"></td>
                        <td width="220px"></td>
                        <td></td>
                        <td class="rightalign">
                            <input type="button" value="搜索" class="button rightalign" style="margin-right:0px;"
                                   onclick="searchISO(1)">
                        </td>
                        <td width="140px"><a class="blueletter leftmargin_10" href="javascript:void(0)" onclick="cancelSearch()">取消</a></td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <!-------------------------------------- 搜索结果 ---------------------------------->
            <div id="searchResult">
                <table class="datatable">
                    <tr>
                        <td width="120px">名称</td>
                        <td width="60px">操作系统</td>
                        <td width="60px">类型</td>
                        <td width="60px">用户</td>
                        <td width="60px">群组</td>
                        <td width="70px">状态</td>
                        <td width="120px">操作</td>
                    </tr>
                    <s:iterator id="image" value="images" status="st">
                        <tr id="tr<s:property value="#image.id"/>">
                            <td id="name<s:property value="#image.id"/>"><s:property
                                    value="(#image).metadata['displayName']"/></td>
                            <td id="desc<s:property value="#image.id"/>"><s:property
                                    value="#image.distribution" escape="false"/></td>
                            <td><s:property value="#image.metadata['type']"/></td>
                            <td id="<s:property value="#image.id"/>"><s:property value="#image.tenantId"
                                                                                 escape="false"/></td>
                            <td id="<s:property value="#image.metadata['groupId']"/>"><s:property
                                    value="#image.metadata['groupId']"/></td>
                            <td><%-- <s:property value="#image.status"/> --%>
                                <s:if test="#image.status == 'AVAILABLE' or #image.status == 'available'">正常</s:if>
                                <s:elseif
                                        test="#image.status == 'CREATING' or #image.status == 'creating'">创建中</s:elseif>
                                <s:elseif
                                        test="#image.status == 'DELETING' or #image.status == 'deleting'">已删除</s:elseif>
                                <s:elseif test="#image.status == 'ERROR' or #image.status == 'error'"><span
                                        class="redletter">错误</span></s:elseif>
                                <s:elseif
                                        test="#image.status == 'ERROR_DELETING' or #image.status == 'error_deleting'"><span
                                        class="redletter">删除失败</span></s:elseif>
                            </td>
                            <td><a class="blueletter"
                                   href="system/imgmod.jsp?type=${param.type}&iid=<s:property value="#image.id"/>"
                                   rel="facebox" size="s" title="编辑模板信息">修改</a>&nbsp;
                                <a class="blueletter" href="javascript:void(0)"
                                   onclick="delImage('<s:property value="#image.id"/>','<s:property
                                           value="#image.metadata['groupId']"/>')">删除</a>
                                <a class="blueletter"
                                   href="system/imgdetail.jsp?displayDescription=<s:property value="#image.metadata['displayDescription']"/>&software=<s:property value="#image.metadata['software']"/>&account=<s:property value="#image.metadata['account']"/>"
                                   title="详情" rel="facebox" size="s">详情</a>
                                <s:if test="#image.metadata['type'] == 'GROUP' or #image.metadata['type'] == 'PRIVATE'">
                                    <a class="blueletter" href="javascript:void(0)"
                                       onclick="release('<s:property value="#image.id"/>')">发布</a>
                                </s:if>
                            </td>
                        </tr>
                    </s:iterator>
                </table>
            </div>

        </div>
    </div>
    <s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_common_js.jsp"></s:include>

<script>
    function delImage(iid, groupId) {
        if (confirm("确认删除模板？")) {
            $.post("img/delimg", {imageId: iid, groupId: groupId}, function (data) {
                console.log("result:" + data.result);
                if (data.result == "success") {
                    fillTipBox("success", "操作成功！");
                    $("#tr" + iid).remove();
                }
                else {
                    fillTipBox("error", "操作出现错误，请稍后重试！");
                }
            });
        }
    }

    function release(uuid) {
        if (confirm("确认发布为公共模板？")) {
            $.post("img/relimg", {imageId: uuid}, function (data) {
                console.log("result:" + data.result);
                if (data.result == "success") {
                    fillTipBox("success", "操作成功！");
                }
                else {
                    fillTipBox("error", "操作出现错误，请稍后重试！");
                }
            });
        }
    }

    function cancelSearch() {
        $('#iso_search').css("display","none");
        $('#img_search').css("display","none");
    }

    function imgsearch() {
        $("#imgButton").css("background", "#fff");
        $("#isoButton").css("background", "#f3f3f3");
        $('#iso_search').css("display","none");
        $('#img_search').css("display","block");
    }

    function isosearch() {
        $("#isoButton").css("background", "#fff");
        $("#imgButton").css("background", "#f3f3f3");
        $('#img_search').css("display","none");
        $('#iso_search').css("display","block");
    }

    function allImg() {
        $("#all").css("display", "block");
        $("#group").css("display", "none");
        $("#platform").css("display", "none");
        $("#allButton").css("background", "#fff");
        $("#platformButton").css("background", "#f3f3f3");
        $("#groupButton").css("background", "#f3f3f3");
    }

    function platImg() {
        $("#all").css("display", "none");
        $("#group").css("display", "none");
        $("#platform").css("display", "block");
        $("#allButton").css("background", "#f3f3f3");
        $("#platformButton").css("background", "#fff");
        $("#groupButton").css("background", "#f3f3f3");
    }

    function groupImg() {
        $("#all").css("display", "none");
        $("#group").css("display", "block");
        $("#platform").css("display", "none");
        $("#allButton").css("background", "#f3f3f3");
        $("#platformButton").css("background", "#f3f3f3");
        $("#groupButton").css("background", "#fff");
    }

    function searchAllImg() {
        var imgKeywordAll = $("#imgKeywordAll").val();
        var email = $("#email").val();
        $.get("img/imgList", {
            imageName: imgKeywordAll,
            software: imgKeywordAll,
            description: imgKeywordAll,
            email:email,
            diskFormat: "IMAGE",
        }, function (html) {
            $("#searchResult").html(html);
            $.parser.parse();
            $("#resultTable").addClass("datatable");
        })
    }

    function searchPlatImg() {
        var imgKeywordPlatform = $("#imgKeywordPlatform").val();
        $.get("img/imgList", {
            imageName: imgKeywordPlatform,
            software: imgKeywordPlatform,
            description: imgKeywordPlatform,
            diskFormat: "IMAGE",
            type:"PUBLIC"
        }, function (html) {
            $("#searchResult").html(html);
        })
    }

    function searchGroupImg() {
        var imgKeywordGroup = $("#imgKeywordGroup").val();
        var groupId = $("#status").val();
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

    function searchISO() {
        var isoKeyword = $("#isoKeyword").val();
        $.post("img/imgList", {
            imageName: isoKeyword,
            software: isoKeyword,
            description: isoKeyword,
            diskFormat: "ISO",
        }, function (html) {
            $("#searchResult").html(html);
            $.parser.parse();
//            $("#resultTable").addClass("datatable");
//            $("#resultTable").className="datatable";
//            $("#searchResult").setAttribute("class", "datatable");
        })
    }
</script>
</body>
</html>
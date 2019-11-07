<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>

<body>
<div class="modal-body">
    <div class="form-group">
        <label class=" control-label front-label" style="font-weight: normal">注意：该操作将关闭虚拟机！！</label>
        <!--<div class="redletter bottommargin_20">注意：该操作将关闭虚拟机！！</div>-->
    </div>
    <div class="form-group">
        <label class="control-label front-label" style="font-weight: normal">选择虚拟机</label>
        <select class="selectbox" id="selectedServer">
            <s:iterator id="server" value="servers">
                <option value="<s:property value='#server.id'/>"><s:property value='#server.name'/></option>
            </s:iterator>
        </select>
    </div>
    <!--<div class="form-group">
        <input type="hidden" id="hduuid" name="hduuid" value="${param.hduuid}"/>
        <input type="hidden" name="uid" id="uid" value="${param.uid}"/>
    </div>-->

</div>
<div class="modal-footer">
    <a class="btn btn-primary" data-dismiss="modal" onclick="hdattach()">确定</a>
    <a href="#" class="btn btn-default" data-dismiss="modal">取消</a>
</div>

<!--   <button class="button rightmargin_10" onclick="hdattach()">确定</button>
   <button class="graybutton" onclick="facebox_close()">取消</button>   -->
<script>

    function hdattach() {
        //var iid = $("#uid").val();
        //var hdUuid=$("#hduuid").val;
        var hdUuid = '${param.hduuid}';
        var uid = '${param.uid}';
        var serverId = $("#selectedServer").val();
        var servarName = $("#selectedServer").text();
        //showLoading();
        $.post("hd/hdoperate", {
            operation: "attach",
            hdUuid: hdUuid,
            uid: uid,
            serverId: serverId
        }, function (data) {
           // hideLoading();
            //facebox_close();
            if (data.result == true) {
                //fillTipBox("success", "操作成功！");
                alert("操作成功！");
                $("#tr" + hdUuid).children().eq(2).html("挂载到<a class=\"blueletter\" " +
                    "href=\"vm/vmdetail?serverId=" + serverId + "\">" + servarName + "</a>");
                $("#tr" + hdUuid).children().eq(6).html("<a class=\"blueletter rightmargin_5\"" +
                    " href=\"javascript:void(0)\" onclick=\"" +
                    "hdoperate('detach','" + hdUuid + "','" + serverId + "','','" + uid + "')\">卸载</a>");
            }
            else if (data.expired == true) {
                alert("error,硬盘已过期");
                fillTipBox("error", "硬盘已过期！");
            }
            else {
                alert("error,操作失败");
                fillTipBox("error", "操作失败！");
            }
        });
    }
</script>
</body>
</html>
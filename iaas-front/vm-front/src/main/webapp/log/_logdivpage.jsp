<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<input type="hidden" id="logs" value="<s:property value='logResult'/>"></input>--%>
<span id="logs" style="display: none;"><s:property value='logResult'/></span>
<div class="panel panel-default front-panel" style="margin-left: 15px;margin-right: 15px">
    <div class="panel-body front-no-padding">
        <!-- 也可以用table-strip -->
        <table class="table table-striped front-table"
               style="margin-bottom: 0px;font-size: 15px" id="newiaaslog">
            <thead style="vertical-align: bottom;
                                border-bottom: 2px solid #ddd;">
            <th style="width: 250px;">时间</th>
            <th style="width: 100px;">类别</th>
            <th>内容</th>
            </thead>
            <s:iterator value="logResult" id="list">
                <tr>
                    <td style='width: 250px;'class="timestamps" name="<s:property value='#list.createdTime'/>">
                        <%--<s:date name="#list.createdTime" format="yyyy年MM月dd日 hh时mm分ss秒"/>--%>
                        <%--<s:property value="#list.createdTime"></s:property>--%>
                    </td>
                    <td style='width: 150px;'>
                        <s:if test="#list.provider=='yunhai'">
                            云海
                        </s:if>
                        <s:else>
                            阿里云
                        </s:else>
                    </td>
                    <td>
                        操作设备:&nbsp;<s:property value="#list.device"/><br>
                        操作类型:&nbsp;<s:property value="#list.operateType"/><br>
                        设备ID:&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#list.deviceId"/><br>
                        提供商:&nbsp;&nbsp;&nbsp;&nbsp;
                        <s:if test="#list.provider=='yunhai'">
                        云海
                    </s:if>
                        <s:else>
                            阿里云
                        </s:else><br>
                        操作结果:&nbsp;<s:property value="#list.result"/>
                    </td>
                </tr>
            </s:iterator>
            <!-- 日志内容 -->
        </table>
    </div>
</div>
<div class="lineheight" id="pageColumn" data-endPage="<s:property value='lastpage'/>"></div>
<script>
$(".timestamps").each(function(){
    var normaltime=new Date(parseInt($(this).attr('name')));
    var createdTime = normaltime.getFullYear() + "年" + (normaltime.getMonth() + 1) + "月" + normaltime.getDate() + "日 " +
        normaltime.getHours() + "点" + normaltime.getMinutes() + "分" + normaltime.getSeconds() + "秒";
    $(this).html(createdTime);
});
</script>
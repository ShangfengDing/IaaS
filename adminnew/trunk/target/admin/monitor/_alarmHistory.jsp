<%@ page contentType="text/html; charset=UTF-8" errorPage="errorPage.jsp"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<s:if test="alarmHistoryList==null||alarmHistoryList.isEmpty()">
    <div class="col-md-12" style="padding-left:0;padding-right:0">
        <div class="panel panel-default front-panel">
            <div class="panel-body">
                    没有搜索结果

            </div>
        </div>
    </div>
</s:if>
<s:else>
    <div class="panel panel-default front-panel clear">
        <table class="table table-striped">
            <thead class="front-no-border">
                <tr>
                    <th class="col-md-2" style="font-size: 15px">告警时间</th>
                    <th class="col-md-2" style="text-align:center;font-size: 15px;">告警表达式</th>
                    <th class="col-md-2" style="text-align:center;font-size: 15px;">描述</th>
                    <th class="col-md-3" style="text-align:center;font-size: 15px;">日志内容</th>
                    <th class="col-md-1" style="text-align:center;font-size: 15px;">状态</th>
                    <th class="col-md-2" style="text-align:center;font-size: 15px;">备注</th>
                </tr>
            </thead>
            <tbody>
                <s:iterator id="alarm" value="alarmHistoryList">
                    <tr>
                        <s:set name="dateofit" value="#alarm.id"/>
                            <td style="vertical-align: middle"><s:property value="dateMap[#dateofit]"/></td>
                        <td
                                style="word-break:break-all;text-align: center;vertical-align: middle;"><s:property
                                value="#alarm.content"/></td>
                        <td style="word-break:break-all;text-align: center;vertical-align:middle"><s:property
                                value="#alarm.description"/></td>
                        <td style="word-break:break-all"><div
                                style="overflow:auto;"><s:property
                                value="#alarm.log"/></div></td>
                        <td style="vertical-align: middle">
                            <div class="dropdown">
                                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                        data-toggle="dropdown" aria-expanded="false">
                                        <s:if test='#alarm.status=="IGNORED"'>
                                            已忽略
                                        </s:if>
                                        <s:if test='#alarm.status=="TREATED"'>
                                            已处理
                                        </s:if>
                                        <s:if test='#alarm.status=="UNTREATED"'>
                                            未处理
                                        </s:if>
                                        <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                    <li role="presentation" id="state2"><a role="menuitem" tabindex="-1" onclick="changeStatus(<s:property value="#alarm.id"/>,'TREATED')" href="javascript:void(0)">已处理</a></li>
                                    <li role="presentation" id="state1"><a role="menuitem" tabindex="-1" onclick="changeStatus(<s:property value="#alarm.id"/>,'UNTREATED')" href="javascript:void(0)">未处理</a></li>
                                    <li role="presentation" id="state0"><a role="menuitem" tabindex="-1" onclick="changeStatus(<s:property value="#alarm.id"/>,'IGNORED')" href="javascript:void(0)">已忽略</a></li>
                                </ul>
                            </div>
                        </td>
                        <td style="word-break:break-all;vertical-align: middle" align="center">
                            <s:if test='#alarm.comment=="" || #alarm.comment==null'>
                                <a href="jacascript::void(0)" class="btn btn-primary" onclick="newComment(<s:property
                                        value="#alarm.id"/>)">添加</a>
                            </s:if>
                            <s:else>
                                <a href="jacascript::void(0)" onclick="showCommentModal(<s:property
                                        value="#alarm.id"/>)"><div style="text-align: center"><s:property value="#alarm.comment"/></div></a>
                            </s:else>
                        </td>
                    </tr>
                </s:iterator>
            </tbody>
        </table>
    </div>
    <div class="lineheight" id="pageColumn" data-endPage="<s:property value='endPage'/>"></div>
</s:else>


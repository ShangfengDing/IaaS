<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="appcloud.admin.common.Constants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() %>/" />
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  	<title>我的虚拟机 - 云海IaaS</title>
  	<s:include value="/template/_head.jsp" />
</head>
<body>
<div id="container">	
	<s:include value="/template/_banner.jsp?menu=sys" />
	<div id="inner">
		<div class="content">
			<div class="divline bottommargin_20 topmargin_20">
				<a href="vm/presearchvm" class="blueletter">云主机管理</a>&nbsp;&gt;&gt;&nbsp;
				<s:property value="server.name"/>
				（<span class="redletter"><s:property value="server.status"/></span>）
			</div>
			<s:if test='server == null'>
            <h2 class="centeralign">不存在该云主机!</h2>
            </s:if>
            <s:elseif test='server.status == "deleted"'>
            <h2 class="centeralign">该云主机已被删除!</h2>
            </s:elseif>
            <s:else>
			<s:if test='server.status=="rebuilding"'>
				<div class="redletter bottommargin_10">
				请远程查看iso安装进度，确保安装完成后，点击“iso弹出”将光驱弹出，才可以对虚拟机进行各种操作！
				</div>
			</s:if>
			
			<div>
				<s:if test='server.status=="active"'>
					<span class="graybutton leftbutton">启动</span>
					<a class="button middlebutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','stop','<s:property value="server.id"/>')">停止</a>
					<a class="button middlebutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','restart','<s:property value="server.id"/>')">重启</a>
					<a class="button middlebutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','suspend','<s:property value="server.id"/>')">挂起</a>
					<span class="graybutton rightbutton">恢复</span>
				</s:if><s:elseif test='server.status=="suspended"'>
					<span class="graybutton leftbutton">启动</span>
					<a class="button middlebutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','stop','<s:property value="server.id"/>')">停止</a>
					<a class="button middlebutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','restart','<s:property value="server.id"/>')">重启</a>
					<span class="graybutton middlebutton">挂起</span>
					<a class="button rightbutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','resume','<s:property value="server.id"/>')">恢复</a>
				</s:elseif><s:elseif test='server.status=="stopped"'>
					<a class="button leftbutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','start','<s:property value="server.id"/>')">启动</a>
					<span class="graybutton middlebutton">停止</span>
					<span class="graybutton middlebutton">重启</span>
					<span class="graybutton middlebutton">挂起</span>
					<span class="graybutton rightbutton">恢复</span>
				</s:elseif><s:elseif test='server.status=="building" || server.status=="rebuilding" || server.status=="error"'>
					<span class="graybutton leftbutton">启动</span>
					<span class="graybutton middlebutton">停止</span>
					<span class="graybutton middlebutton">重启</span>
					<span class="graybutton middlebutton">挂起</span>
					<span class="graybutton rightbutton">恢复</span>
				</s:elseif>
				
				<%-- <a class="button leftbutton" href="vm/vmmonitor.jsp?uuid=<s:property value="server.id"/>" rel="facebox" title="运行监控">监控</a>
				<s:if test='server.status=="stopped" || server.status=="error"'>
					<span class="graybutton middlebutton">远程</span>
				</s:if><s:else>
					<a class="button middlebutton" href="http://webvnc2.free4lab.com/vnc/<s:property value="server.id"/>" target="_blank" title="远程登录">远程</a>
				</s:else>
				<s:if test='server.status=="active" || server.status=="stopped" || server.status=="suspended"'>
					<a class="button middlebutton" href="backup/newbackup.jsp?serverId=<s:property value="server.id"/>" rel="facebox" title="创建备份" size="s">创建备份</a>
					<a class="button middlebutton" href="shot/newshot.jsp?serverId=<s:property value="server.id"/>" rel="facebox" title="创建快照" size="s">创建快照</a>
					<a class="button middlebutton" href="vm/prevminstall?uuid=<s:property value="server.id"/>" rel="facebox" title="装系统" size="s">iso安装</a>
				</s:if><s:else>
					<span class="graybutton middlebutton">创建备份</span>
					<span class="graybutton middlebutton">创建快照</span>
					<span class="graybutton middlebutton">iso安装</span>
				</s:else>
				<s:if test='server.status=="rebuilding"'>
					<a class="button rightbutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','detachIso','<s:property value="server.id"/>')">iso弹出</a>
				</s:if><s:else>
					<span class="graybutton rightbutton">iso弹出</span>
				</s:else> --%>
				
				<s:if test='server.status=="building"'>
					<span class="graybutton leftbutton">删除</span>
					<span class="graybutton middlebutton">强制关机</span>
					<span class="graybutton rightbutton">强制删除</span>
				</s:if><s:else>
					<a class="button leftbutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','delete','<s:property value="server.id"/>','<s:property value="vmEndtime.id"/>')">删除</a>
					<a class="button middlebutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','forceStop','<s:property value="server.id"/>')">强制关机</a>
					<a class="button rightbutton" href="javascript:void(0)" onclick="vmoperate('<s:property value="server.tenantId"/>','forceDelete','<s:property value="server.id"/>','<s:property value="vmEndtime.id"/>')">强制删除</a>
				</s:else>
				
				<s:if test='server.status=="active" || server.status=="suspended" || server.status=="building" || server.status=="rebuilding" || server.status=="error" || server.status=="other"'>
					<a class="graybutton middlebutton">清空密码</a>
					<a class="graybutton middlebutton" >云主机离线迁移</a>
				</s:if><s:elseif test='server.status=="stopped"'>
					<a class="button middlebutton" href="vm/modVmPasswd.jsp?userid=<s:property value="server.tenantId"/>&serverid=<s:property value="server.id"/>" rel="facebox" title="修改密码" size="s">清空密码</a>
					<a class="button middlebutton" href="vm/premigrateserver?type=offline&userId=<s:property value="server.tenantId"/>&serverId=<s:property value="server.id"/>" rel="facebox" title="选择迁移目标节点" >云主机离线迁移</a>
				</s:elseif>
				<a class="button middlebutton" href="vm/premigrateserver?type=online&userId=<s:property value="server.tenantId"/>&serverId=<s:property value="server.id"/>" rel="facebox" title="选择迁移目标节点" >云主机在线迁移</a>
			</div>
			
			<div class="dottedbox">
			<table class="formtable">
				<tr>
					<td class="padding5">名称：</td>
					<td class="padding5"><s:property value="server.name"/></td>
				</tr><tr>
                    <td class="padding5">状态</td>
                    <td class="padding5" id="td_status">
                        <img src="images/<s:property value='server.status'/>.png" class="rightmargin_5" align="absmiddle"/>
                        <!--  <input type="checkbox" id="serverStatus" value='statusMap[server.status]'>
                            <label for="serverStatus"><s:property value='server.status'/></label>-->
                        <span class="redletter" id="serverStatus"><s:property value='statusMap[server.status]'/></span>
                    </td>
                </tr><tr>
					<td class="padding5">UUID：</td>
					<td class="padding5"><s:property value="server.id"/></td>
				</tr><tr>
					<td class="padding5">描述：</td>
					<td class="padding5"><s:property value="server.metadata['displayDescription']"/></td>
				</tr><tr class="dottedline">
                    <td class="padding5">镜像描述</td>
                    <td class="padding5">
                    <s:if test="server.image.id == null or server.image.id == \"\"">空白镜像
                    </s:if>
                    <s:else>
                    <s:property value="image.metadata['displayDescription']"/><span class="redletter strong">（请尽快修改密码）</span>
                    </s:else></td>
                </tr><%-- <tr class="dottedline">
					<td class="padding5">数据中心：</td>
					<td class="padding5"><s:property value="zoneName"/></td>
				</tr> --%>
				<tr>
					<td class="padding5">计算资源</td>
					<td class="padding5">
						<s:property value="flavor.vcpus"/>个CPU，
						<s:property value="flavor.ram"/>G内存
						<%-- <s:if test='server.status=="active" || server.status=="stopped" || server.status=="suspended"'>
							<a class="darkblueletter leftmargin_20" 
							href="vm/prenewflavor?kind=flavor&serverId=<s:property value="server.id"/>&serverName=<s:property value="server.name"/>
							&clusterid=<s:property value="clusterid"/>&flavorId=<s:property value="server.flavor.id"/>
							&bdw=<s:property value="server.metadata['maxBandwidth']"/>
							&description=<s:property value="server.metadata['displayDescription']"/>" rel="facebox" title="修改配置" size="s">
							修改配置</a>
						</s:if>--%>
					</td>
				</tr>
				<tr>
					<td class="padding5">存储资源</td>
					<td class="padding5">
						<s:property value="flavor.disk"/>G硬盘
					</td>
				</tr>
				<tr>
					<td class="padding5">网络资源</td>
					<td class="padding5">
					    公网带宽<s:property value="server.metadata['maxBandwidth']"/>M，内网带宽<s:property value="server.metadata['priBandwidth']"/>M
						<s:if test='server.status=="active" || server.status=="stopped" || server.status=="suspended"'>
							<a class="darkblueletter leftmargin_20" href="vm/prenewflavor?kind=netWork&vmUserId=<s:property value="userId"/>&serverId=<s:property value="server.id"/>
							&tenantId=<s:property value="server.tenantId"/>&flavorId=<s:property value="server.flavor.id"/>
							&mbdw=<s:property value="server.metadata['maxBandwidth']"/>&clusterId=<s:property value="clusterId"/>
							&pbdw=<s:property value="server.metadata['priBandwidth']"/>"  rel="facebox" title="修改配置" size="s">修改配置</a>
							<s:if test="server.metadata['oldPriBandNum'] != -1 || server.metadata['oldMaxBandNum'] != -1">
							<a class="darkblueletter leftmargin_20" href="javascript:void(0);" onclick="recoverNetWork('<s:property value='server.id'/>','<s:property value='clusterId'/>','<s:property value='userId'/>');" title="还原配置" >还原配置</a>
							</s:if>
						</s:if>
					</td>
				</tr>
				<tr>
					<td class="padding5">公网IP：</td>
					<td class="padding5"><s:iterator id="publicIp" value="publicIps" status="st">
							<s:property value="#publicIp.addr"/>
							<s:if test="#st.last!=true">,</s:if>
						</s:iterator>
					</td>
				</tr><tr>
					<td class="padding5">内网IP：</td>
					<td class="padding5"><s:iterator id="privateIp" value="privateIps" status="st">
							<s:property value="#privateIp.addr"/>
							<s:if test="#st.last!=true">,</s:if>
						</s:iterator>
					</td>
				</tr><tr class="dottedline">
					<td class="padding5">防火墙规则：</td>
					<td class="padding5"><s:property value="server.securityGroup.name"/>
						<%-- <s:if test='server.status=="active" || server.status=="stopped" || server.status=="suspended"'>
						<a href="vm/prenewfw?serverId=<s:property value="server.id"/>&groupId=<s:property value="server.metadata['securityGroupId']"/>" 
							class="darkblueletter leftmargin_20" rel="facebox" title="防火墙设置" size="s">防火墙设置</a>
						</s:if> --%>
					</td>
				</tr><%-- <tr>
					<td class="padding5">镜像模板：</td>
					<td class="padding5"><s:property value="image.metadata['displayName']"/></td>
				</tr><tr class="dottedline">
					<td class="padding5">镜像描述：</td>
					<td class="padding5"><s:property value="image.metadata['displayDescription']"/></td>
				</tr> --%><tr>
					<td class="padding5">创建时间：</td>
					<td class="padding5"><s:date name="server.created" format="yyyy-MM-dd HH:mm:ss"/></td>
				</tr><tr>
					<td class="padding5">停止时间：</td>
					<td class="padding5"><s:date name="vmEndtime.endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				</tr><tr>
					<td class="padding5">计费方式：</td>
					<td class="padding5"><s:property value="vmEndtime.payType"/></td>
				</tr>
				<s:if test="snapshots.size()>0"><tr class="dottedline"><td colspan="2"></td></tr>
                <tr>
                    <td class="padding5">快照</td>
                    <td class="padding5">
                    <table border="0" width="100%" style="border:0;">
                    <s:iterator id="snapshot" value="snapshots" status="st">
                    <tr>
                    <td style="width:50px !important;vertical-align:text-top;" class="blueletter">名称：</td>
                    <td style="width:20% !important;vertical-align:text-top;"><s:property value="#snapshot.displayName"/></td>
                    <td style="width:50px !important;vertical-align:text-top;" class="blueletter">描述：</td>
                    <td style="vertical-align:text-top;"><s:property value="#snapshot.displayDescription"/></td>
                    <td style="width:15% !important"><a class="darkblueletter" href="javascript:void(0)" onclick="shotoperate('rollback','<s:property value="#snapshot.id"/>','<s:property value="#snapshot.displayName"/>','<s:property value="server.name"/>')">回滚</a>
                        <a class="darkblueletter leftmargin_5" href="javascript:void(0)" onclick="shotoperate('delete','<s:property value="#snapshot.id"/>','<s:property value="#snapshot.displayName"/>','<s:property value="server.name"/>')">删除</a>
                    </td>
                    </tr>
                    </s:iterator>
                    </table>
                    </td>
                </tr>
                </s:if>
                <s:if test="backups.size()>0"><tr class="dottedline"><td colspan="2"></td></tr>
                <tr>
                    <td class="padding5">备份</td>
                    <td class="padding5">
                    <table border="0" width="100%" style="border:0">
                    <s:iterator id="backup" value="backups" status="st">
                    <tr><td style="width:50px !important;vertical-align:text-top;" class="blueletter">名称：</td>
                        <td style="width:20% !important;vertical-align:text-top;"><s:property value="#backup.displayName"/></td>
                        <td style="width:50px !important;vertical-align:text-top;" class="blueletter">描述：</td>
                        <td style="vertical-align:text-top;"><s:property value="#backup.displayDescription"/></td>
                        <td style="width:15% !important;">
                        <s:if test="#backup.attachStatus.toString() == 'ATTACHED'"><span class="darkblueletter">正在使用</span>
                        </s:if><s:else>
                            <a class="darkblueletter" href="javascript:void(0)" onclick="backupoperate('rollback','<s:property value="#backup.id"/>','<s:property value="#backup.displayName"/>','<s:property value="#backup.instanceName"/>','<s:property value="#backup.instanceId"/>','<s:property value="#backup.volumeId"/>')">回滚</a>
                            <a class="darkblueletter leftmargin_5" href="javascript:void(0)" onclick="backupoperate('delete','<s:property value="#backup.id"/>','<s:property value="#backup.displayName"/>','<s:property value="#backup.instanceName"/>')">删除</a>
                        </s:else>
                        </td>
                    </tr>
                    </s:iterator>
                    </table>
                    </td>
                </tr>
                </s:if>
                <s:if test="volumes.size()>0"><tr class="dottedline"><td colspan="2"></td></tr>
                <tr>
                    <td class="padding5">硬盘</td>
                    <td class="padding5">
                    <table border="0" width="100%" style="border:0">
                    <s:iterator id="volume" value="volumes" status="st">
                    <tr><td style="width:50px !important;vertical-align:text-top;" class="blueletter">名称：</td>
                        <td style="width:20% !important;vertical-align:text-top;"><s:property value="#volume.displayName"/></td>
                        <td style="width:50px !important;vertical-align:text-top;" class="blueletter">描述：</td>
                        <td style="vertical-align:text-top;"><s:property value="#volume.displayDescription"/></td>
                        <td style="width:15% !important;">
                            <a class="darkblueletter" href="javascript:void(0)" onclick="hdoperate('detach','<s:property value="#volume.id"/>','<s:property value="server.id"/>','<s:property value="#volume.displayName"/>','<s:property value="server.name"/>')">卸载</a>
                        </td>
                    </tr>
                    </s:iterator>
                    </table>
                    </td>
                </tr>
                </s:if>
            </table>
            </div>
            </s:else>
		</div>
	</div>
	<s:include value="/template/_footer.jsp"></s:include>
</div>
<s:include value="/template/_common_js.jsp"></s:include>
<script>
$(function(){//根据稳态和瞬态更改工具按钮栏
    var sid = $("#serverId1").val();
    var sname = $("#serverName1").val();
    var status = $("#serverStatus1").val();
    var vmEndtimeId = $("#vmEndtimeId1").val();
    if(status == "other"){
        disableEverything(status,sid,sname,vmEndtimeId);
    }
    setTimeout(function(){checkStatus(status,sid,sname,vmEndtimeId)}, <%=Constants.DEFAULT_CHECKTIME%>);
});

function recoverNetWork(serverId,clusterId,vmUserId){
	 var confirmMsg = "确认恢复网络配置？";
	 param = {
			 serverId:serverId,
			 clusterId:clusterId,
			 vmUserId:vmUserId
	 };
    if(confirm(confirmMsg)){
        showLoading();
        $.post("vm/recoverNetwork",param,function(data){
            hideLoading();
            if(data.result == true) {
                fillTipBox("success","恢复网络配置成功！");
                setTimeout(function(){window.location.reload();}, 500);
            }
            else {
                fillTipBox("error","恢复网络配置操作失败！");
            }
        });
        location.reload();
    }
}
function showHelpcontent(uuid){
    $.ajax({
        type:"post",
        url:"vm/helpcontent",
        data:{uuid:uuid},
        dataType: "json",  
        success:function(helpcontent) {
            $('body').helpcontent_show({title:helpcontent.title, content:helpcontent.content});
        }
    });
}

function checkStatus(status,sid,sname,vmEndtimeId){
    $.ajax({
        type:"post",
        url:"vm/vmstatus",
        data:{serverId:sid},
        success:function(data) {
            var newStatus = data.status;
            if(newStatus == status){
                setTimeout(function(){checkStatus(newStatus,sid,sname,vmEndtimeId)}, <%=Constants.DEFAULT_CHECKTIME%>); 
            }else if(newStatus == "other"  && status != "other"){
                disableEverything(status,sid,sname,vmEndtimeId);
                var newHtml = "<img src=\"images/other.png\" class=\"rightmargin_5\" align=\"absmiddle\"/>"+
                "<span class=\"redletter\" id=\"serverStatus\">任务进行中</span>"+
                "<a class=\"darkblueletter leftpadding_20\" href=\"javascript:void(0)\" onclick=\"location.reload();\">刷新</a>";
                $("#td_status").html(newHtml);
                setTimeout(function(){checkStatus(newStatus,sid,sname,vmEndtimeId)}, <%=Constants.DEFAULT_CHECKTIME%>); 
            }else if(newStatus != "other"  && status == "other"){
                location.reload();
            }else{
                location.reload(); 
            }
        }
    });
}

function disableEverything(status,sid,sname,vmEndtimeId){
    $("#btns a").remove();
    $("a.darkblueletter").remove();
    var html = "<a class=\"graybutton leftbutton\"><img src=\"images/qidong.png\" align=\"absmiddle\"/>&nbsp;&nbsp;启动</a>"+
    "<a class=\"graybutton middlebutton\"><img src=\"images/tingzhi.png\" align=\"absmiddle\"/>&nbsp;&nbsp;停止</a>"+
    "<a class=\"graybutton middlebutton\"><img src=\"images/chongqi.png\" align=\"absmiddle\"/>&nbsp;&nbsp;重启</a>"+
    "<a class=\"graybutton middlebutton\"><img src=\"images/guaqi.png\" align=\"absmiddle\"/>&nbsp;&nbsp;挂起</a>"+
    "<a class=\"graybutton rightbutton\"><img src=\"images/huifu.png\" align=\"absmiddle\"/>&nbsp;&nbsp;恢复</a>"+
    "<a class=\"graybutton leftbutton\" href=\"javascript:void(0)\"><img src=\"images/yunxingzhuangtai.png\" align=\"absmiddle\"/>&nbsp;&nbsp;监控</a>"+
    "<a class=\"graybutton middlebutton\"><img src=\"images/yuancheng.png\" align=\"absmiddle\"/>&nbsp;&nbsp;远程</a>"+
    "<a class=\"graybutton middlebutton\" href=\"javascript:void(0)\"><img src=\"images/yuancheng.png\" align=\"absmiddle\"/>&nbsp;&nbsp;远程</a>"+
    "<a class=\"button rightbutton\" href=\"javascript:void(0)\" onclick=\"showMore(this);\">更多</a>"+
    "<div id=\"showmore\" class=\"hidden topmargin_10\">"+
    "<a class=\"graybutton leftbutton\" href=\"javascript:void(0)\">创建备份</a>"+
    "<a class=\"graybutton middlebutton\" href=\"javascript:void(0)\">创建快照</a>"+
    "<a class=\"graybutton leftbutton\">系统重置</a>"+
    "<a class=\"graybutton middlebutton\">iso安装</a>"+
    "<a class=\"graybutton rightbutton\">iso弹出</a>";
    
    if(status != "building"){
        html += "<a class=\"button leftbutton\" href=\"javascript:void(0)\" onclick=\"vmoperate('强制关机','"+sid+"','"+sname+"')\")>强制关机</a>"+
        "<a class=\"button rightbutton\" href=\"javascript:void(0)\" onclick=\"vmoperate('删除','"+sid+"','"+sname+"','"+vmEndtimeId+"')\">删除</a>";
    }else{
        html += "<a class=\"graybutton leftbutton\">强制关机</a><a class=\"graybutton rightbutton\">删除</a>";
    }
    
    if($("#publishImage1").val() == "true"){
        html += "<a class=\"graybutton\" href=\"javascript:void(0)\">发布为模板</a>";
    }
    
    html += "</div>";
    $("#btns").html(html);
}


function hdoperate(operation, hdUuid, serverId, hdName, serverName){
    var confirmMsg;
    if(operation == "detach"){
        confirmMsg = "确认进行卸载吗？(注意：该操作将关闭云主机！！)";
    } else {
        return;
    }
    if(confirm(confirmMsg)){
        showLoading();
        $.post("hd/hdoperate",{operation:operation,hdUuid:hdUuid,serverId:serverId,
            hdName:hdName, serverName:serverName},function(data){//this is a bug, hdoperate function need user uuid!
            hideLoading();
            if(data.result == true) {
                fillTipBox("success","操作成功！");
                setTimeout(function(){window.location.reload();}, 500);
            }
            else {
                fillTipBox("error","操作失败！");
            }
        });
    }
}
function vmoperate(userId, operation, uuid, endTimeId){
	if(confirm("该操作会对云主机产生重大影响，您确认进行吗？")){
		showLoading();
		$.post("vm/vmoperate",{userId:userId, operation:operation,uuid:uuid,endTimeId:endTimeId},function(){
			if(operation == "delete" || operation == "forceDelete"){
				gotonext("vm/presearchvm");
			} else {
				hideLoading();
				fillTipBox("success","操作成功返回！");
			}
		});
	}
}

function backupoperate(operation,backupId,backupName,serverName,serverId,volumeId){
    var rollbackMsg = "确认进行回滚操作吗？\n注意1：该操作将关闭云主机！\n注意2：该备份之后做的快照将不复存在！";
    var deleteMsg = "确认删除该备份吗？";
    var confirmMsg = (operation == "rollback") ? rollbackMsg : deleteMsg;
    if(confirm(confirmMsg)){
        showLoading();
        $.post("backup/backupoperate",
            {operation:operation,backupId:backupId,serverId:serverId,volumeId:volumeId,serverName:serverName,backupName:backupName},
            function(data){
                if(data.result=="success"){
                    fillTipBox("success","操作成功！");
                    setTimeout(function(){hideLoading();window.location.reload();}, 500);
                }
                else{
                    hideLoading();
                    fillTipBox("error","操作失败！请稍后重试！");
                }
        });
    }
}
function shotoperate(operation,sid,shotName,serverName){
    var confirmMsg = (operation == "rollback") ? "确认进行回滚操作吗？(注意：该操作将关闭云主机！！)" : "确认删除该快照吗？";
    if(confirm(confirmMsg)){
        showLoading();
        $.post("shot/shotoperate",{operation:operation,snapshotId:sid,shotName:shotName,serverName:serverName},function(data){
            if(data.result=="success"){
                fillTipBox("success","操作成功！");
                setTimeout(function(){hideLoading();window.location.reload();}, 500);
            }
            else{
                fillTipBox("error","操作失败！请稍后重试！");
                hideLoading();
            }
        });
    }
}


</script>
</body>
</html>
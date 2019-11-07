package com.appcloud.vm.action.vm;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.api.client.ServerClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

@SuppressWarnings("serial")
public class NewFwAction extends BaseAction{
	private Logger logger = Logger.getLogger(NewFwAction.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	
	private String serverId;
	private Integer groupId;
	private String groupName;
	
	private boolean result = false;
	
	//修改虚拟机的防火墙配置
	public String execute() {
		String userId = this.getSessionUserID().toString();
		logger.info("serverId:"+serverId+",groupId:"+groupId);
		
		try {
			result = serverClient.changeSecurityGroup(userId, serverId, groupId);
			logger.info("修改虚拟机的防火墙配置结果："+result);
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"修改云主机防火墙配置", "修改云主机的防火墙配置为\""+groupName+"\"", 
					"NewFwAction.class", null, AcLogLevelEnum.INFO,
					new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			logger.warn("修改虚拟机的防火墙配置出现异常");
			addAcMessageLog(AcModuleEnum.VM_FRONT, 
					UUID.randomUUID().toString().replace("-", ""), userId.toString(), serverId,
					"修改云主机防火墙配置", "修改云主机的防火墙配置为\""+groupName+"\"失败", 
					"NewFwAction.class", null, AcLogLevelEnum.WARN,
					new Date(System.currentTimeMillis()));
		}		
		return SUCCESS;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}

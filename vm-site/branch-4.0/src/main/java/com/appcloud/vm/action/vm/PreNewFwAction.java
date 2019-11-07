package com.appcloud.vm.action.vm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.SecurityGroup;
import appcloud.api.client.SecurityGroupClient;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

@SuppressWarnings("serial")
public class PreNewFwAction extends BaseAction{
	private Logger logger = Logger.getLogger(PreNewFwAction.class);
	private SecurityGroupClient securityGroupClient = ClientFactory.getSecurityGroupClient();
	
	private String serverId;	//虚拟机uuid
	private List<SecurityGroup> groups = new ArrayList<SecurityGroup>();
	
	public String execute() {
		String userId = this.getSessionUserID().toString();
		groups = securityGroupClient.getSecurityGroups(userId);
		logger.info("查询所有防火墙成功");
		return SUCCESS;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public List<SecurityGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<SecurityGroup> groups) {
		this.groups = groups;
	}
	
}
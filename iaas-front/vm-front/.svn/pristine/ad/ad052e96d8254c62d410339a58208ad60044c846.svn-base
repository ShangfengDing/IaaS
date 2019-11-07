package com.appcloud.vm.action.vm;

import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcGroup;
import appcloud.api.client.AcGroupClient;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class PreNewImgAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1347510279185036995L;

	private Logger logger = Logger.getLogger(PreNewImgAction.class);
	
	private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();
	private List<AcGroup> acGroups;
	private String serverId;
	
	/**
	 * 获取群组列表
	 */
	@Override
	public String execute() throws Exception {
		
		acGroups = acGroupClient.getAcGroups();
		
		return SUCCESS;
	}


	public List<AcGroup> getAcGroups() {
		return acGroups;
	}


	public void setAcGroups(List<AcGroup> acGroups) {
		this.acGroups = acGroups;
	}


	public String getServerId() {
		return serverId;
	}


	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
	
}

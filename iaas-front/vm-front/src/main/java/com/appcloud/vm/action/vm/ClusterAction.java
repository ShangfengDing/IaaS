package com.appcloud.vm.action.vm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcGroup;
import appcloud.api.beans.AcUser;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcUserClient;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.action.hd.PreNewHdAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class ClusterAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(PreNewHdAction.class);
	private AcAggregateClient aggregateClient = ClientFactory.getAggregateClient();
	private AcUserClient acUserClient = ClientFactory.getAcUserClient();
	//private AcGroupClient acGroupClient = ClientFactory.getAcGroupClient();
	private List<AcAggregate> acAggregates = new ArrayList<AcAggregate>();
	private List<Integer> clusters = new ArrayList<Integer>();
	private int zoneId = 0;//暂时不用
	
	public String execute() {
		//取得用户ID
		String userId = this.getSessionUserID().toString();
		//通过用户取得组
		AcUser acUser = acUserClient.get(userId);
		AcGroup acGroup = acUser.group;
		//通过组，取得本组可用集群ID
		clusters = acGroup.availableClusters;
		//通过集群ID，取得集群bean，以备显示集群名称
		for (Integer aggregateId : clusters) {
			acAggregates.add(aggregateClient.get(aggregateId));
		}
		logger.info("获取"+userId+"可用集群成功");
		return SUCCESS;
	}
	
	public List<AcAggregate> getAcAggregates() {
		return acAggregates;
	}

	public void setAcAggregates(List<AcAggregate> acAggregates) {
		this.acAggregates = acAggregates;
	}

	public List<Integer> getClusters() {
		return clusters;
	}

	public void setClusters(List<Integer> clusters) {
		this.clusters = clusters;
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
}

package com.appcloud.vm.action.vm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.Flavor;
import appcloud.api.client.FlavorClient;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.billing.BillingAPI;
import com.appcloud.vm.fe.billing.Billingrate;
import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.util.ClientFactory;

public class PreNewFlavorAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(PreNewFlavorAction.class);
	private FlavorClient flavorClient = ClientFactory.getFlavorClient();
	
	private List<Billingrate> bandwidthPrice = new ArrayList<Billingrate>();
	private List<Billingrate> cpuPrice = new ArrayList<Billingrate>();
	private List<Billingrate> memoryPrice = new ArrayList<Billingrate>();
	private String serverId;	//虚拟机uuid
	private String serverName;	//虚拟机名称
	private Integer flavorId;	//虚拟机原flavorId
	private String description; //虚拟机的描述，要重新写进metadata
	private Flavor flavor;		//虚拟机原硬件配置信息
	private Integer bdw;	//虚拟机原带宽
	private int clusterid;
	private String kind; 		//修改操作的类型
	
	@SuppressWarnings("unchecked")
	public String execute() {
		String userId = this.getSessionUserID().toString();
		flavor = flavorClient.get(userId, flavorId);
		logger.info("查询原硬件配置成功");
		
		//修改虚拟机硬件配置，首先查询所有硬件配置信息
		@SuppressWarnings("unused")
		List<String> itemProducts = new ArrayList<String>();
		logger.info(clusterid);
		List<Billingrate> all = BillingAPI.getRate(Constants.BILLING_ALL, clusterid, -1, -1, -1, -1);
		
		if (all != null) {
			logger.info("获取计费规则成功");
		} else {
			logger.info("获取计费规则失败");
		}
		
		for (int i = 0; i < all.size(); i++) {
			Billingrate rate = new Billingrate();
			rate = all.get(i);
			if (rate.getPtype().equals(Constants.BILLING_CPU_ABBR)) {
				cpuPrice.add(rate);
			} else if (rate.getPtype().equals(Constants.BILLING_BW_ABBR)) {
				bandwidthPrice.add(rate);
			} else if (rate.getPtype().equals(Constants.BILLING_MEM_ABBR)) {
				memoryPrice.add(rate);
			} 
		}	
		
		ComparatorCpu comparatorCpu = new ComparatorCpu();
		Collections.sort(cpuPrice, comparatorCpu);
		ComparatorMemory comparatorMemory = new ComparatorMemory();
		Collections.sort(memoryPrice, comparatorMemory);
		ComparatorBandwidth comparatorBandwidth = new ComparatorBandwidth();
		Collections.sort(bandwidthPrice, comparatorBandwidth);
		logger.info("查询系统硬件配置成功");
		logger.info("查到带宽为："+bdw);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public class ComparatorCpu implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			return price0.getCpu().compareTo(price1.getCpu());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorMemory implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			return price0.getMemory().compareTo(price1.getMemory());
		}
	}
	
	@SuppressWarnings("rawtypes")
	public class ComparatorBandwidth implements Comparator{
		public int compare(Object arg0, Object arg1) {
			Billingrate price0=(Billingrate)arg0;
			Billingrate price1=(Billingrate)arg1;
			return price0.getBandwidth().compareTo(price1.getBandwidth());
		}
	}
	
	public int getClusterid() {
		return clusterid;
	}

	public void setClusterid(int clusterid) {
		this.clusterid = clusterid;
	}

	public List<Billingrate> getBandwidthPrice() {
		return bandwidthPrice;
	}

	public void setBandwidthPrice(List<Billingrate> bandwidthPrice) {
		this.bandwidthPrice = bandwidthPrice;
	}

	public List<Billingrate> getCpuPrice() {
		return cpuPrice;
	}

	public void setCpuPrice(List<Billingrate> cpuPrice) {
		this.cpuPrice = cpuPrice;
	}

	public List<Billingrate> getMemoryPrice() {
		return memoryPrice;
	}

	public void setMemoryPrice(List<Billingrate> memoryPrice) {
		this.memoryPrice = memoryPrice;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public Integer getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(Integer flavorId) {
		this.flavorId = flavorId;
	}

	public Flavor getFlavor() {
		return flavor;
	}

	public void setFlavor(Flavor flavor) {
		this.flavor = flavor;
	}


	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Integer getBdw() {
		return bdw;
	}

	public void setBdw(Integer bdw) {
		this.bdw = bdw;
	}
	
}

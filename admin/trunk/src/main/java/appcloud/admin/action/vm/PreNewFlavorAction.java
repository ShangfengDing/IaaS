package appcloud.admin.action.vm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.Flavor;
import appcloud.api.beans.Server;
import appcloud.api.client.FlavorClient;
import appcloud.api.client.ServerClient;

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
	private String serverId;	//虚拟机uuid
	private String vmUserId;	//虚拟机的用户id
	private String serverName;	//虚拟机名称
	private Integer flavorId;	//虚拟机原flavorId
	private String description; //虚拟机的描述，要重新写进metadata
	private Flavor flavor;		//虚拟机原硬件配置信息
	private Integer mbdw;		//虚拟机原公网带宽
	private Integer pbdw;		//虚拟机原私网带宽
	private int clusterId;
	private String kind; 		//修改操作的类型
	private String tenantId;
	
	@SuppressWarnings("unchecked")
	public String execute() {
		//修改虚拟机硬件配置，首先查询所有硬件配置信息
		@SuppressWarnings("unused")
		List<String> itemProducts = new ArrayList<String>();
		logger.info(clusterId);
		List<Billingrate> all = BillingAPI.getRate(Constants.BILLING_ALL, clusterId, -1, -1, -1, -1);
		
		if (all != null) {
			logger.info("获取计费规则成功");
		} else {
			logger.info("获取计费规则失败");
		}
		
		for (int i = 0; i < all.size(); i++) {
			Billingrate rate = new Billingrate();
			rate = all.get(i);
			if (rate.getPtype().equals(Constants.BILLING_BW_ABBR)) {
				bandwidthPrice.add(rate);
			} 
		}	
		
		ComparatorBandwidth comparatorBandwidth = new ComparatorBandwidth();
		Collections.sort(bandwidthPrice, comparatorBandwidth);
		logger.info("查询网络配置成功 共"+bandwidthPrice.size());
		logger.info("查到公网带宽为："+mbdw+" 私网带宽："+pbdw);
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
	

	public List<Billingrate> getBandwidthPrice() {
		return bandwidthPrice;
	}

	public void setBandwidthPrice(List<Billingrate> bandwidthPrice) {
		this.bandwidthPrice = bandwidthPrice;
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


	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Integer getMbdw() {
		return mbdw;
	}

	public void setMbdw(Integer mbdw) {
		this.mbdw = mbdw;
	}

	public Integer getPbdw() {
		return pbdw;
	}

	public void setPbdw(Integer pbdw) {
		this.pbdw = pbdw;
	}

	public int getClusterId() {
		return clusterId;
	}

	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	public String getVmUserId() {
		return vmUserId;
	}

	public void setVmUserId(String vmUserId) {
		this.vmUserId = vmUserId;
	}

	
}

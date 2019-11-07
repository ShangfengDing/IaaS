package appcloud.vmcontroller.model;

import appcloud.common.model.VmVirtualInterface;

public class NetworkConfig {
	private String publicOrPrivate = null; //public network or private network
	private String networkType = null; // bridge
	private String networkMac = null;
	private String networkIp = null;
	private String networkBridge = null; // br0 br1
	private String modelType = null; // virto
	private String filterName = null;

	private String inbound = null;
	private String outbound = null;

	private NetworkConfig(String networkType, String networkMac,
			String networkIp, String networkBridge, String modelType,
			String filterName) {
		this.networkType = networkType;
		this.networkMac = networkMac;
		this.networkIp = networkIp;
		this.networkBridge = networkBridge;
		this.modelType = modelType;
		this.filterName = filterName;
	}

	public NetworkConfig(VmVirtualInterface vif, String network,
			String privateNetwork, String publicNetwork, String filter) {
		this(network, // bridge(default)
			 vif.getMac(), // mac
			 vif.getAddress(), // ip
			 null, // br0/br1
			 "virtio", filter); // filter

		// 设置连接到宿主机的网桥, 设置网卡类型
		if (vif.getNetworkType().equals("private")) {
			this.setNetworkBridge(privateNetwork);
			this.setPublicOrPrivate(vif.getNetworkType());
		} else if (vif.getNetworkType().equals("public")) {
			this.setNetworkBridge(publicNetwork);
			this.setPublicOrPrivate(vif.getNetworkType());
		}
		
	}
	
	public String getPublicOrPrivate() {
		return publicOrPrivate;
	}

	public void setPublicOrPrivate(String publicOrPrivate) {
		this.publicOrPrivate = publicOrPrivate;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getNetworkMac() {
		return networkMac;
	}

	public void setNetworkMac(String networkMac) {
		this.networkMac = networkMac;
	}

	public String getNetworkIp() {
		return networkIp;
	}

	public void setNetworkIp(String networkIp) {
		this.networkIp = networkIp;
	}

	public String getNetworkBridge() {
		return networkBridge;
	}

	public void setNetworkBridge(String networkBridge) {
		this.networkBridge = networkBridge;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getInbound() {
		return inbound;
	}

	public void setInbound(String inbound) {
		this.inbound = inbound;
	}

	public String getOutbound() {
		return outbound;
	}

	public void setOutbound(String outbound) {
		this.outbound = outbound;
	}

}

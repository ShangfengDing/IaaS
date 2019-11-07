package appcloud.common.model;

import appcloud.common.util.IpUtils;

public class VmIpSegMent {
    private Integer id;
    
    private Integer dhcpId;
    private String netMask;
    private String router;
    private String dns;
    private Integer segment;
    private String ipStartRange;
    private String ipEndRange;

    public VmIpSegMent() {
        super();
    }

	public VmIpSegMent(Integer id, Integer dhcpId, String ipSeg,
			String netMask, String ipStartRange, String ipEndRange,
			String router, String dns, Integer type) {
		super();
		this.id = id;
		this.dhcpId = dhcpId;
		this.netMask = netMask;
		this.router = router;
		this.dns = dns;
		this.segment = type;
		this.ipStartRange = ipStartRange;
		this.ipEndRange = ipEndRange;
	}

	public VmIpSegMent(Integer id, Integer dhcpId,
			String netMask, String ipStartRange, String ipEndRange,
			String router, String dns, Integer type) {
		
		super();
		this.id = id;
		this.dhcpId = dhcpId;

		this.netMask = netMask;
		this.router = router;
		this.dns = dns;
		this.segment = type;
		this.ipStartRange = ipStartRange;
		this.ipEndRange = ipEndRange;
		
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDhcpId() {
		return dhcpId;
	}

	public void setDhcpId(Integer dhcpId) {
		this.dhcpId = dhcpId;
	}

	public String getIpSeg() {
		return IpUtils.ipToString(IpUtils.getSegment(IpUtils.stringToIp(ipStartRange), IpUtils.getNetmaskInt(netMask)));
	}

	public String getNetMask() {
		return netMask;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

	public String getDns() {
		return dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}

	public Integer getSegment() {
		return segment;
	}

	public void setSegment(Integer segment) {
		this.segment = segment;
	}

    public String getIpStartRange() {
        return ipStartRange;
    }

    public void setIpStartRange(String ipStartRange) {
        this.ipStartRange = ipStartRange;
    }

    public String getIpEndRange() {
        return ipEndRange;
    }

    public void setIpEndRange(String ipEndRange) {
        this.ipEndRange = ipEndRange;
    }

	@Override
	public String toString() {
		return "VmIpSegMent [id=" + id + ", dhcpId=" + dhcpId + ", ipSeg="
				+ getIpSeg() + ", netMask=" + netMask + ", router=" + router
				+ ", dns=" + dns + ", segment=" + segment + ", ipStartRange="
				+ ipStartRange + ", ipEndRange=" + ipEndRange + "]";
	}

}

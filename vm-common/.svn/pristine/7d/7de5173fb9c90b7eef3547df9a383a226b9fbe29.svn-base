package appcloud.common.model;
import appcloud.common.util.IpUtils;
/**
 * @ClassName: Subnet
 * @Description: TODO
 * @author wenchaoz361
 * @date 2013-4-17 上午9:37:38
 */
public class Subnet {
    
    public	enum Type {Private, Public};
    
	Integer ipSegId;
    Integer clusterId;
    Type type;
    String ipSeg;
    String netmask;
    String rangeStart;
    String rangeEnd;
    String router;
    String dns;
    
    public Subnet(Integer id, String ipSeg, Integer netmask, String rangeStart, String rangeEnd, String router, String dns) {
        this.ipSegId = id;
    	this.ipSeg = ipSeg;
        this.netmask = IpUtils.getNetmask(netmask);
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.router = router;
        this.dns = dns;
        
        checkDataValid();
    }
    
    public Subnet(Integer id, String ipSeg, String netmaskStr, String rangeStart, String rangeEnd, String router, String dns) {
    	this.ipSegId = id;
    	this.ipSeg = ipSeg;
        this.netmask = netmaskStr;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.router = router;
        this.dns = dns;
        
        checkDataValid();
    }
    
    public Subnet(Integer id, Integer clusterid, Type type, String ipSeg, String netmaskStr, String rangeStart, String rangeEnd, String router, String dns) {
        this.ipSegId = id;
        this.clusterId = clusterid;
        this.type = type;
    	this.ipSeg = ipSeg;
        this.netmask = netmaskStr;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.router = router;
        this.dns = dns;
        
        checkDataValid();
    }
    
    public Integer getIpSegId() {
    	return ipSegId;
    }
    
    public void setIpSegId(Integer id) {
    	ipSegId = id;
    }
    
    public String getIpSegStr() {
        return ipSeg;
    }
    
    public Integer getIpSegNum() {
        return IpUtils.stringToIp(ipSeg);
    }
    
    public String getRangeStartIpStr() {
        return rangeStart;
    }
    
    public Integer getRangeStartIpNum() {
        
        return IpUtils.stringToIp(rangeStart);
    }
    
    public String getRangeEndIpStr() {
        return rangeEnd;
    }
    
    public Integer getRangeEndIpNum() {
        return IpUtils.stringToIp(rangeEnd);    
    }
    
    public Integer getNetmask() {
        return IpUtils.getNetmaskInt(netmask);
    }
    
    public String getNetmaskStr() {
        return netmask;
    }
    
    public String getRouter() {
        return router;
    }
    /**
     * 
     * @return null means no router num.
     */
    public Integer getRouterNum() {
    	Integer ret = 0;
    	try {
    		ret = IpUtils.stringToIp(router);
    	} catch (Exception ex) {
    		return null;
    	}
    	return ret;
     }
    
    public String getDns() {
        return dns;
    }
    /**
     * 
     * @return null means no router num.
     */
    public Integer getDnsNum() {
    	Integer ret = 0;
    	try {
    		ret = IpUtils.stringToIp(dns);
    	} catch (Exception ex) {
    		return null;
    	}
        return ret; 
    }
    
    public Type getType() {
    	return type;
    }
    
    public void setType(Type type) {
    	this.type = type;
    }
    
    public Integer getClusterId() {
    	return clusterId;
    }
    
    public void setClusterId(Integer clusterid) {
    	this.clusterId = clusterid;
    }
    
    void checkDataValid() {
        assert(IpUtils.contain(this.getIpSegNum(), this.getNetmask(), this.getRangeStartIpNum()));
        assert(IpUtils.contain(this.getIpSegNum(), this.getNetmask(), this.getRangeEndIpNum()));
        assert(this.getRangeStartIpNum() <= this.getRangeEndIpNum());
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Subnet) {
    		 Subnet o = (Subnet) obj;
    		 if(o.rangeStart.equals(this.rangeStart) &&
    				 o.rangeEnd.equals(this.rangeEnd)) 
    			 return true;
    		 else
    			 return false;
    	} 
    	return false;
    }
    
    public String toString() {
        return  "IpSegId:" + this.ipSegId
        		+"ipSeg:" + this.ipSeg 
                + "-netMask:" + this.netmask 
                + "-rangeStart:" + this.rangeStart 
                + "-rangeEnd:" + this.rangeEnd
                + "-router:" + this.router 
                + "-dns:" + this.dns;
    }
    
    public static boolean conject(Subnet net1, Subnet net2) {
        Integer min_netmask = net1.getNetmask() > net2.getNetmask() ? net2.getNetmask() : net1.getNetmask();
        Integer ip_seg1 = IpUtils.getSegment(net1.getIpSegNum(), min_netmask);
        Integer ip_seg2 = IpUtils.getSegment(net2.getIpSegNum(), min_netmask);
        if(! ip_seg1.equals(ip_seg2)) return false;
        
        Integer min_net1 = net1.getRangeStartIpNum();
        Integer max_net1 = net1.getRangeEndIpNum();
        Integer min_net2 = net2.getRangeStartIpNum();
        Integer max_net2 = net2.getRangeEndIpNum();
        
        if(min_net1 > max_net2 || max_net1 < min_net2) return false;
        else return true;
    }
    
}

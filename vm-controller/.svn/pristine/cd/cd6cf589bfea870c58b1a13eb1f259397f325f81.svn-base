package appcloud.vmcontroller.virt;

import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.util.IpUtils;

public class RuleConfig {
	/*
	 * protocol:	tcp \ udp \ icmp
	 * dstportStart:	允许访问的起始端口
	 * srcIpFrom:	允许访问的起始IP
	 * srcIpTp:	允许访问的终止IP
	 * direction：in、out、inout
	 */
    private String protocol;		
    private Integer dstportStart;
    private Integer dstportEnd;
    private String srcIpFrom;
    private String srcIpTo;
    private String direction;

    /**
     * 实例化rule，目前使用字段：
     *  private String protocol; // 协议类型 tcp udp icmp
	    private Integer portStart; // 目的端口号 如：22
	    private Integer portEnd;
	    private String ipRange; // 源ip区段 如 :172.16.0.0/23
	    private String direction; // in or out not in db?
     * @param vmSecurityGroupRule
     */
    public RuleConfig(VmSecurityGroupRule vmSecurityGroupRule) {
    	//direction：default为inout
    	this.direction = "inout";
    	
    	//protocol: 需要转换为小写
        this.protocol = vmSecurityGroupRule.getProtocol().toLowerCase();	
        
        /* 
         * port：
         */
        this.dstportStart = vmSecurityGroupRule.getPortStart();
        this.dstportEnd = vmSecurityGroupRule.getPortEnd();
        
        /*
         * IP：
         * TODO：IpUtils需要修改
         */
        if (vmSecurityGroupRule.getIpRange() == null){
        	this.srcIpFrom = null;
            this.srcIpTo = null;            
        } else if (vmSecurityGroupRule.getIpRange().endsWith("/0")) {
        	this.srcIpFrom = null;
            this.srcIpTo = null;
        } else {
        	String[] strs =  vmSecurityGroupRule.getIpRange().split("/");
            this.srcIpFrom = IpUtils.getStartIp(strs[0], Integer.parseInt(strs[1]));
            this.srcIpTo = IpUtils.getEndIp(strs[0], Integer.parseInt(strs[1]));
        }
        
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getDstportStart() {
        return dstportStart;
    }

    public void setDstportStart(Integer dstportEnd) {
        this.dstportEnd = dstportEnd;
    }
    
    public Integer getDstportEnd() {
        return dstportEnd;
    }

    public void setDstportEnd(Integer dstportStart) {
        this.dstportStart = dstportStart;
    }

    public String getSrcIpFrom() {
        return srcIpFrom;
    }

    public void setSrcIpFrom(String srcIpFrom) {
        this.srcIpFrom = srcIpFrom;
    }

    public String getSrcIpTo() {
        return srcIpTo;
    }

    public void setSrcIpTo(String srcIpTo) {
        this.srcIpTo = srcIpTo;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

}

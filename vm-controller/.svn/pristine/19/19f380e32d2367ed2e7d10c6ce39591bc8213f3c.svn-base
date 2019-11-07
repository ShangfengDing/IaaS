package appcloud.vmcontroller.virt;

import appcloud.common.model.VmSecurityGroupRule;
import appcloud.common.util.IpUtils;

public class RuleConfig {
    private String protocol;
    private Integer dstportStart;
    private String srcIpFrom;
    private String srcIpTo;
    private String direction;
 

    public RuleConfig() {
        super();

    }

    public RuleConfig(String protocol, Integer dstportStart, String srcIpFrom, String srcIpTo, String direction, String status) {
        super();
        this.protocol = protocol;
        this.dstportStart = dstportStart;
        this.srcIpFrom = srcIpFrom;
        this.srcIpTo = srcIpTo;
        this.direction = direction;
    }

    public RuleConfig(VmSecurityGroupRule vmSecurityGroupRule) {
        super();
        this.protocol = vmSecurityGroupRule.getProtocol();
        this.dstportStart = vmSecurityGroupRule.getPortStart();
        if(vmSecurityGroupRule.getIpRange() != null){
            String[] strs =  vmSecurityGroupRule.getIpRange().split("/");
            this.srcIpFrom = IpUtils.getStartIp(strs[0], Integer.parseInt(strs[1]));
            this.srcIpTo = IpUtils.getEndIp(strs[0], Integer.parseInt(strs[1]));
        }
        this.srcIpFrom = null;
        this.srcIpTo = null;
        this.direction = vmSecurityGroupRule.getDirection();
       
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

    public void setDstportStart(Integer dstportStart) {
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

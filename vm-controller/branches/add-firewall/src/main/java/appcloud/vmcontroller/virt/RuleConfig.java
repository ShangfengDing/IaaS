package appcloud.vmcontroller.virt;

import appcloud.common.model.VmSecurityGroupRule;

public class RuleConfig {
    private String protocol;
    private String dstportStart;
    private String srcIpFrom;
    private String srcIpTo;
    private String direction;
    private String status;

    public RuleConfig() {
        super();

    }

    public RuleConfig(String protocol, String dstportStart, String srcIpFrom, String srcIpTo, String direction, String status) {
        super();
        this.protocol = protocol;
        this.dstportStart = dstportStart;
        this.srcIpFrom = srcIpFrom;
        this.srcIpTo = srcIpTo;
        this.direction = direction;
        this.status = status;
    }

    public RuleConfig(VmSecurityGroupRule vmSecurityGroupIngressRule) {
        super();
        this.protocol = vmSecurityGroupIngressRule.getProtocol();
        this.dstportStart = vmSecurityGroupIngressRule.getDstportStart();
        this.srcIpFrom = vmSecurityGroupIngressRule.getSrcIpFrom();
        this.srcIpTo = vmSecurityGroupIngressRule.getSrcIpTo();
        this.direction = vmSecurityGroupIngressRule.getDirection();
        this.status = vmSecurityGroupIngressRule.getStatus();
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDstportStart() {
        return dstportStart;
    }

    public void setDstportStart(String dstportStart) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

}

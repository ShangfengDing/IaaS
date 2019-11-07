package appcloud.common.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jid")
public class VmSecurityGroupRule {
    private Integer id; // 自增主键
    private Integer groupId; // fk
    private String uuid;

    /* 目前已经使用的 */
    private String protocol; // 协议类型 tcp udp icmp
    private Integer portStart; // 目的端口号 如：22
    private Integer portEnd;
    private String ipRange; // 源ip区段 如 :172.16.0.0/23
    private String direction; // in or out not in db?

    /* 暂时未用 */
    private String ethertype;
    private String remoteIpPrefix;
    private String remoteGroupId;
    private Integer userId;

    private VmSecurityGroup vmSecurityGroup;

    public VmSecurityGroupRule() {
        super();
    }

    public VmSecurityGroupRule(Integer id,
                               Integer groupId,
                               String uuid,
                               String protocol,
                               Integer portStart,
                               Integer portEnd,
                               String ipRange,
                               String ipFrom,
                               String ipTo,
                               String direction,
                               String ethertype,
                               String remoteIpPrefix,
                               String remoteGroupId,
                               Integer userId) {
        super();
        this.id = id;
        this.groupId = groupId;
        this.uuid = uuid;
        this.protocol = protocol;
        this.portStart = portStart;
        this.portEnd = portEnd;
        this.ipRange = ipRange;
        this.direction = direction;
        this.ethertype = ethertype;
        this.remoteIpPrefix = remoteIpPrefix;
        this.remoteGroupId = remoteGroupId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getPortStart() {
        return portStart;
    }

    public void setPortStart(Integer portStart) {
        this.portStart = portStart;
    }

    public Integer getPortEnd() {
        return portEnd;
    }

    public void setPortEnd(Integer portEnd) {
        this.portEnd = portEnd;
    }

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEthertype() {
        return ethertype;
    }

    public void setEthertype(String ethertype) {
        this.ethertype = ethertype;
    }

    public String getRemoteIpPrefix() {
        return remoteIpPrefix;
    }

    public void setRemoteIpPrefix(String remoteIpPrefix) {
        this.remoteIpPrefix = remoteIpPrefix;
    }

    public String getRemoteGroupId() {
        return remoteGroupId;
    }

    public void setRemoteGroupId(String remoteGroupId) {
        this.remoteGroupId = remoteGroupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public VmSecurityGroup getVmSecurityGroup() {
        return vmSecurityGroup;
    }

    public void setVmSecurityGroup(VmSecurityGroup vmSecurityGroup) {
        this.vmSecurityGroup = vmSecurityGroup;
    }

	@Override
	public String toString() {
		return "VmSecurityGroupRule [id=" + id + ", groupId=" + groupId
				+ ", uuid=" + uuid + ", protocol=" + protocol + ", portStart="
				+ portStart + ", portEnd=" + portEnd + ", ipRange=" + ipRange
				+ ", direction=" + direction + ", ethertype=" + ethertype
				+ ", remoteIpPrefix=" + remoteIpPrefix + ", remoteGroupId="
				+ remoteGroupId + ", userId=" + userId + ", vmSecurityGroup="
				+ vmSecurityGroup + "]";
	}

}

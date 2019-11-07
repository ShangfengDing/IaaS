package appcloud.dbproxy.mysql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appcloud.common.model.VmSecurityGroupRule;



@Entity
@Table(name = "vm_security_group_rule")
public class VmSecurityGroupRuleTable extends VmSecurityGroupRule {

	public VmSecurityGroupRuleTable() {
		super();
	}
	
	public VmSecurityGroupRuleTable(VmSecurityGroupRule rule) {
		this.setId(rule.getId());
		this.setGroupId(rule.getGroupId());
		this.setProtocol(rule.getProtocol());
		this.setIpRange(rule.getIpRange());
		this.setUuid(rule.getUuid());
		this.setPortStart(rule.getPortStart());
		this.setPortEnd(rule.getPortEnd());
		this.setDirection(rule.getDirection());
		this.setEthertype(rule.getEthertype());
		this.setRemoteIpPrefix(rule.getRemoteIpPrefix());
		this.setRemoteGroupId(rule.getRemoteGroupId());
		this.setUserId(rule.getUserId());
	}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    public Integer getId() {
        return super.getId();
    }
    public void setId(Integer id) {
        super.setId(id);
    }
    
    @Column(name="group_id")
    public Integer getGroupId() {
        return super.getGroupId();
    }
    public void setGroupId(Integer groupId) {
        super.setGroupId(groupId);
    }

    @Column(name = "uuid")
    public String getUuid() {
        return super.getUuid();
    }
    public void setUuid(String uuid) {
        super.setUuid(uuid);
    }
    @Column(name="ip_protocol")
    public String getProtocol() {
        return super.getProtocol();
    }
    public void setProtocol(String protocol) {
        super.setProtocol(protocol);
    }
    
    @Column(name="port_start")
    public Integer getPortStart() {
    	return super.getPortStart();
    }
    public void setPortStart(Integer portStart) {
    	super.setPortStart(portStart);
    }
    
    @Column(name="port_end")
    public Integer getPortEnd() {
    	return super.getPortEnd();
    }
    public void setPortEnd(Integer portEnd) {
    	super.setPortEnd(portEnd);
    }

    @Column(name="ip_range")
    public String getIpRange(){
    	return super.getIpRange();
    }
    public void setIpRange(String ipRange) {
    	super.setIpRange(ipRange);
    }
    
    @Column(name="direction") 
    public String getDirection() {
    	return super.getDirection();
    }
    public void setDirection(String direction) {
        super.setDirection(direction);
    }

    @Column(name="ethertype") 
    public String getEthertype() {
        return super.getEthertype();
    }
    public void setEthertype(String ethertype) {
        super.setEthertype(ethertype);
    }

    @Column(name="remote_ip_prefix") 
    public String getRemoteIpPrefix() {
        return super.getRemoteIpPrefix();
    }
    public void setRemoteIpPrefix(String remoteIpPrefix) {
        super.setRemoteIpPrefix(remoteIpPrefix);
    }

    @Column(name="remote_group_id") 
    public String getRemoteGroupId() {
        return super.getRemoteGroupId();
    }
    public void setRemoteGroupId(String remoteGroupId) {
        super.setRemoteGroupId(remoteGroupId);
    }

    @Column(name="user_id") 
    public Integer getUserId() {
        return super.getUserId();
    }
    public void setUserId(Integer userId) {
        super.setUserId(userId);
    }

}
